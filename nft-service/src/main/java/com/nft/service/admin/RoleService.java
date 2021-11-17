package com.nft.service.admin;

import com.baomidou.mybatisplus.extension.service.IService;
import com.nft.data.dto.admin.PermissionDTO;
import com.nft.data.dto.admin.RoleListDTO;
import com.nft.data.entity.PermissionDO;
import com.nft.data.entity.RoleDO;

import java.util.List;

/**
 * @author caoc
 * @createDate 2021/11/15
 */
public interface RoleService extends IService<RoleDO> {

    /**
     * 角色列表
     * @return
     */
    List<RoleListDTO> listRoles();

    /**
     * 查询管理员角色
     * @param adminId
     * @return
     */
    List<RoleDO> findByAdminId(Integer adminId);

    /**
     * 插入管理员和角色关联
     * @param adminId
     * @param roleIds
     * @return
     */
    int insertAdminRoles(Integer adminId, List<Integer> roleIds);

    /**
     * 删除管理员权限
     * @param adminId
     * @param roleIds
     */
    void deleteAdminRoles(Integer adminId, List<Integer> roleIds);

    /**
     * 通过管理员ids，删除关联
     * @param adminIds
     */
    void deleteByAdminIds(List<Integer> adminIds);


    /**
     * 所有权限
     * @return
     */
    List<PermissionDTO> listAllPermission();


    /**
     * 添加角色权限
     * @param roleId
     * @param permissions
     * @return
     */
    int insertRolePermission(Integer roleId, List<Integer> permissions);

    /**
     * 删除角色和权限关联
     * @param roleId
     * @param permissions
     * @return
     */
    int deleterRolePermission(Integer roleId, List<Integer> permissions);

    /**
     * 查询角色权限
     * @param roleId
     * @return
     */
    List<PermissionDO> selectByRoleId(Integer roleId);

    /**
     * 查询权限code
     * @param roleIds
     * @return
     */
    List<String> selectByRoleIds(List<Integer> roleIds);

}
