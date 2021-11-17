package com.nft.service.admin;

import com.baomidou.mybatisplus.extension.service.IService;
import com.nft.data.dto.admin.AdminDTO;
import com.nft.data.entity.AdminDO;

/**
 * 管理员表 服务类
 *
 * @author caoc
 * @createDate 2021/11/15
 */
public interface AdminService extends IService<AdminDO> {

    /**
     * 获取当前管理员
     * @return
     */
    AdminDTO getCurrentAdmin();

    /**
     * 获取当前管理员id
     * @return
     */
    Integer getCurrentAdminId();


    /**
     * 更新管理员信息
     * @param id
     * @param name
     * @param phone
     * @param avatarUrl
     */
    void updateAdminProfile(Integer id, String name, String phone, String avatarUrl);
}