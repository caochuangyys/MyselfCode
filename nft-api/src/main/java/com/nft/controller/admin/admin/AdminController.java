package com.nft.controller.admin.admin;

import cn.hutool.core.lang.Validator;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.nft.controller.admin.admin.req.*;
import com.nft.core.component.CacheComponent;
import com.nft.core.exception.AdminException;
import com.nft.core.exception.ExceptionCode;
import com.nft.core.model.page.ZPage;
import com.nft.core.sms.ISmsClient;
import com.nft.core.util.MD5Util;
import com.nft.core.util.StrUtils;
import com.nft.data.consts.SystemConstant;
import com.nft.data.dto.admin.AdminDTO;
import com.nft.data.entity.AdminDO;
import com.nft.data.entity.RoleDO;
import com.nft.service.admin.AdminService;
import com.nft.service.admin.RoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 管理员表
 *
 * @author caoc
 * @createDate 2021/11/15
 */
@RestController
@RequestMapping("/admin/admin")
@Api(tags = "管理员", description = "")
public class AdminController {

    private static final String VERIFY_CODE_PREFIX = "ADMIN_VERIFY_CODE_";

    @Autowired
    private AdminService adminService;

    @Autowired
    private ISmsClient smsClient;

    @Autowired
    private CacheComponent cacheComponent;

    @Autowired
    private RoleService roleService;


    @ApiOperation(value = "登录", httpMethod = "POST", notes = "登录成功后，将token设置到header中，key为ADMIN-ACCESSTOKEN")
    @PostMapping(value = "/login")
    public String login(@RequestBody @Valid AdminLoginReq loginReq, HttpServletRequest request) {

        QueryWrapper<AdminDO> wrapper = new QueryWrapper<>();
        wrapper.eq("username", loginReq.getUsername());
        AdminDO adminDO = adminService.getOne(wrapper);
        if (adminDO == null) {
            throw new AdminException(ExceptionCode.ADMIN_NOT_EXIST);
        }
        if (adminDO.getStatus() == 0) {
            throw new AdminException(ExceptionCode.ADMIN_ACCOUNT_DISABLE);
        }
        if (!
                MD5Util.verify(loginReq.getPassword(), loginReq.getUsername(), adminDO.getPassword())) {
            throw new AdminException(ExceptionCode.ADMIN_PASSWORD_ERR);
        }

        adminDO.setGmtLastLogin(new Date());
        adminDO.setLastLoginIp(StrUtils.getIp(request));
        adminService.updateById(adminDO);

        AdminDTO adminDTO = new AdminDTO();
        BeanUtils.copyProperties(adminDO, adminDTO);

        //设置角色
        List<String> permissionNames = new LinkedList<>();
        if (adminDO.getIsSuper() == 1) {
            permissionNames.add("*");
        } else {
            List<RoleDO> roleDOList = roleService.findByAdminId(adminDO.getId());
            if (CollectionUtils.isEmpty(roleDOList)) {
                throw new AdminException(ExceptionCode.ADMIN_ROLE_IS_EMPTY);
            }
            List<Integer> roleIds = roleDOList.stream().map(roleDO -> roleDO.getId()).collect(Collectors.toList());
            List<String> roleNames = new LinkedList<>();
            roleDOList.forEach(item -> {
                roleNames.add(item.getName());
            });

            adminDTO.setRoles(roleNames);
            adminDTO.setRoleIds(roleIds);
            permissionNames.addAll(roleService.selectByRoleIds(roleIds));
        }

        String token = StrUtils.generateAccessToken();
        adminDTO.setPerms(permissionNames);
        cacheComponent.putObj(token, adminDTO, SystemConstant.ADMIN_SESSION_EXPIRE_TIME);

        return token;
    }

    @ApiOperation(value = "管理员信息")
    @GetMapping(value = "/info")
    public AdminDTO info() {
        return adminService.getCurrentAdmin();

    }

    @ApiOperation(value = "分页列表", httpMethod = "GET")
    @GetMapping(value = "/page")
    public ZPage<AdminDTO> list(AdminPageQuery pageQuery) {

        QueryWrapper<AdminDO> wrapper = new QueryWrapper<>();
        if (StrUtils.isNotBlank(pageQuery.getUsername())) {
            wrapper.like("username", pageQuery.getUsername());
        }
        if (pageQuery.getStatus() != null) {
            wrapper.eq("status", pageQuery.getStatus());
        }
        wrapper.orderByDesc("id");

        Page<AdminDO> page = adminService.page(pageQuery.toPage(), wrapper);

        return ZPage.transferFrom(page, item -> {
            AdminDTO dto = new AdminDTO();
            BeanUtils.copyProperties(item, dto);
            List<RoleDO> roleList = roleService.findByAdminId(item.getId());
            dto.setRoleIds(roleList.stream().map(role -> role.getId()).collect(Collectors.toList()));
            dto.setRoles(roleList.stream().map(role -> role.getName()).collect(Collectors.toList()));
            if (item.getIsSuper() == 1) {
                dto.getRoles().add("超级管理员");
            }
            return dto;
        });
    }

    @ApiOperation(value = "创建", httpMethod = "POST")
    @PostMapping(value = "/create")
    public void create(@RequestBody @Valid AdminSaveOrUpdateReq req) {

        if (StrUtil.isNotBlank(req.getPhone()) && Validator.isMobile(req.getPhone()) == false) {
            throw new AdminException(ExceptionCode.COMMON_PHONE_NUMBER_ERR);
        }
        if (StrUtil.isBlank(req.getPassword())) {

        }

        QueryWrapper<AdminDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", req.getUsername());
        int count = adminService.count(queryWrapper);
        if (count > 0) {
            throw new AdminException(ExceptionCode.ADMIN_USERNAME_BE_USED);
        }
        if (req.getIsSuper() == 0 && CollectionUtils.isEmpty(req.getRoleIds())) {
            throw new AdminException(ExceptionCode.ADMIN_ROLE_REQUIRED);
        }

        AdminDO adminDO = new AdminDO();
        BeanUtils.copyProperties(req, adminDO);
        adminDO.setPassword(MD5Util.md5(req.getPassword(), req.getUsername()));
        adminDO.setStatus(1);

        if (adminService.save(adminDO) == true && adminDO.getIsSuper() == 0) {
            roleService.insertAdminRoles(adminDO.getId(), req.getRoleIds());
        }

    }

    @ApiOperation(value = "更新", httpMethod = "POST")
    @PostMapping(value = "/update")
    public void update(@RequestBody @Valid AdminSaveOrUpdateReq req) {

        if (StrUtil.isNotBlank(req.getPhone()) && Validator.isMobile(req.getPhone()) == false) {
            throw new AdminException(ExceptionCode.COMMON_PHONE_NUMBER_ERR);
        }

        AdminDO adminDO = adminService.getById(req.getId());
        if (adminDO == null) {
            throw new AdminException(ExceptionCode.ADMIN_NOT_EXIST);
        }
        //如果更改手机号，查看是否有其他占用
        QueryWrapper<AdminDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", req.getUsername());
        queryWrapper.ne("id", req.getId());

        int count = adminService.count(queryWrapper);
        if (count > 0) {
            throw new AdminException(ExceptionCode.ADMIN_USERNAME_BE_USED);
        }

        if (req.getIsSuper() == 0 && CollectionUtils.isEmpty(req.getRoleIds())) {
            throw new AdminException(ExceptionCode.ADMIN_ROLE_REQUIRED);
        }
        BeanUtils.copyProperties(req, adminDO);
        if (StrUtil.isNotBlank(req.getPassword())) {
            adminDO.setPassword(MD5Util.md5(req.getPassword(), req.getUsername()));
        }
        if (adminService.updateById(adminDO) == true) {
            //删除原先角色
            roleService.deleteAdminRoles(adminDO.getId(), null);
            if (adminDO.getIsSuper() == 0) {
                roleService.insertAdminRoles(adminDO.getId(), req.getRoleIds());
            }
        }

    }

    @ApiOperation(value = "激活或禁用", httpMethod = "POST")
    @PostMapping(value = "/activeOrLock")
    public boolean activeOrLock(@RequestBody @Valid AdminActiveOrLockReq req) {

        AdminDO adminDO = adminService.getById(req.getId());
        if (adminDO == null) {
            throw new AdminException(ExceptionCode.ADMIN_NOT_EXIST);
        }
        Integer currentId = adminService.getCurrentAdminId();
        if (currentId.intValue() == req.getId().intValue()) {
            throw new AdminException(ExceptionCode.ADMIN_CANNOT_OPERATION_SELF);
        }
        adminDO.setStatus(req.getStatus());
        return adminService.updateById(adminDO);


    }

    @ApiOperation(value = "删除管理员", httpMethod = "POST")
    @PostMapping(value = "/delete")
    public void delete(@RequestBody @Valid AdminDelReq req) {
        Integer currentId = adminService.getCurrentAdminId();
        if (currentId.intValue() == req.getId().intValue()) {
            throw new AdminException(ExceptionCode.ADMIN_CANNOT_OPERATION_SELF);
        }
        if (adminService.removeById(req.getId()) == true) {
            roleService.deleteAdminRoles(req.getId(), null);
        }
    }


    @ApiOperation(value = "更新管理员信息", httpMethod = "POST")
    @PostMapping(value = "/updateProfile")
    public void updateProfile(@RequestBody @Valid AdminProfileUpdateReq req) {

        if (StrUtil.isNotBlank(req.getPhone()) && Validator.isMobile(req.getPhone()) == false) {
            throw new AdminException(ExceptionCode.COMMON_PHONE_NUMBER_ERR);
        }

        Integer currentId = adminService.getCurrentAdminId();
        adminService.updateAdminProfile(currentId, req.getRealname(), req.getPhone(), req.getAvatarUrl());

    }

    @ApiOperation(value = "更新密码", httpMethod = "POST")
    @PostMapping(value = "/changePwd")
    public void changePwd(@RequestBody @Valid ChangePwdReq req) {

        AdminDO adminDO = adminService.getById(adminService.getCurrentAdminId());
        if (!adminDO.getPassword().equalsIgnoreCase(MD5Util.md5(req.getOldPassword(), adminDO.getUsername()))) {
            throw new AdminException(ExceptionCode.ADMIN_OLD_PWD_ERR);
        }
        adminDO.setPassword(MD5Util.md5(req.getNewPassword(), adminDO.getUsername()));
        adminService.updateById(adminDO);

    }

    @ApiOperation(value = "登出", httpMethod = "POST")
    @PostMapping(value = "/logout")
    public void logout(HttpServletRequest request) {

        String accessToken = request.getHeader(SystemConstant.ADMIN_ACCESS_TOKEN);
        cacheComponent.del(accessToken);
    }
}
