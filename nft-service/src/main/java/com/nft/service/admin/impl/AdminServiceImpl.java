package com.nft.service.admin.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nft.core.component.CacheComponent;
import com.nft.core.exception.AdminException;
import com.nft.core.exception.ExceptionCode;
import com.nft.core.util.StrUtils;
import com.nft.data.consts.SystemConstant;
import com.nft.data.dto.admin.AdminDTO;
import com.nft.data.entity.AdminDO;
import com.nft.data.mapper.AdminMapper;
import com.nft.service.admin.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * @author caoc
 * @createDate 2021/11/15
 */
@Service
public class AdminServiceImpl extends ServiceImpl<AdminMapper, AdminDO> implements AdminService {

    @Autowired
    private CacheComponent cacheComponent;

    @Override
    public AdminDTO getCurrentAdmin() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String accessToken = request.getHeader(SystemConstant.ADMIN_ACCESS_TOKEN);
        if (StrUtils.isEmpty(accessToken)){
            return null;
        }
        AdminDTO adminDTO = cacheComponent.getObj(accessToken, AdminDTO.class);
        return adminDTO;
    }

    @Override
    public Integer getCurrentAdminId() {

        AdminDTO adminDTO = this.getCurrentAdmin();
        if (adminDTO != null){
            return adminDTO.getId();
        }
        return null;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateAdminProfile(Integer id, String name, String phone, String avatarUrl) {
        AdminDO adminDO = super.getById(id);
        if (adminDO == null){
            throw new AdminException(ExceptionCode.ADMIN_NOT_EXIST);
        }
        QueryWrapper<AdminDO> qw = new QueryWrapper<>();
        qw.eq("username", phone);
        qw.ne("id", id);
        int count = super.count(qw);
        if (count > 0){
            throw new AdminException(ExceptionCode.ADMIN_USERNAME_BE_USED);
        }

        adminDO.setPhone(phone);
        adminDO.setAvatarUrl(avatarUrl);
        adminDO.setRealname(name);
        super.updateById(adminDO);

        //更新session
        AdminDTO adminDTO = this.getCurrentAdmin();
        if (adminDTO != null){
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            String accessToken = request.getHeader(SystemConstant.ADMIN_ACCESS_TOKEN);
            adminDTO.setAvatarUrl(avatarUrl);
            adminDTO.setRealname(name);
            adminDTO.setPhone(phone);
            cacheComponent.putObj(accessToken, adminDTO, SystemConstant.ADMIN_SESSION_EXPIRE_TIME);
        }

    }
}
