package com.nft.service.admin.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nft.data.dto.admin.PermissionDTO;
import com.nft.data.dto.admin.RoleListDTO;
import com.nft.data.entity.PermissionDO;
import com.nft.data.entity.RoleDO;
import com.nft.data.mapper.PermissionMapper;
import com.nft.data.mapper.RoleMapper;
import com.nft.service.admin.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author caoc
 * @createDate 2021/11/15
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, RoleDO> implements RoleService {


    @Autowired
    private PermissionMapper permissionMapper;

    @Override
    public List<RoleListDTO> listRoles() {
        return baseMapper.listRoles();
    }

    @Override
    public List<RoleDO> findByAdminId(Integer adminId) {
        return baseMapper.findByAdminId(adminId);
    }

    @Override
    public int insertAdminRoles(Integer adminId, List<Integer> roleIds) {
        return baseMapper.insertAdminRoles(adminId, roleIds);
    }

    @Override
    public void deleteAdminRoles(Integer adminId, List<Integer> roleIds) {

        baseMapper.deleteAdminRoles(adminId, roleIds);
    }

    @Override
    public void deleteByAdminIds(List<Integer> adminIds) {

        baseMapper.deleteByAdminIds(adminIds);
    }

    @Override
    public List<PermissionDTO> listAllPermission() {
        return permissionMapper.listAllPermission();
    }

    @Override
    public int insertRolePermission(Integer roleId, List<Integer> permissions) {
        return permissionMapper.insertRolePermission(roleId, permissions);
    }

    @Override
    public int deleterRolePermission(Integer roleId, List<Integer> permissions) {
        return permissionMapper.deleterRolePermission(roleId, permissions);
    }

    @Override
    public List<PermissionDO> selectByRoleId(Integer roleId) {
        return permissionMapper.selectByRoleId(roleId);
    }

    @Override
    public List<String> selectByRoleIds(List<Integer> roleIds) {
        return permissionMapper.selectByRoleIds(roleIds);
    }
}
