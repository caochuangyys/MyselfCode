package com.nft.data.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.nft.data.dto.admin.PermissionDTO;
import com.nft.data.entity.PermissionDO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 *  权限表 Mapper 接口
 * </p>
 *
 * @author caoc
 * @createDate 2021/11/12
 */
@Repository
public interface PermissionMapper extends BaseMapper<PermissionDO> {

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
    int insertRolePermission(@Param("roleId") Integer roleId, @Param("permissions") List<Integer> permissions);

    /**
     * 删除角色和权限关联
     * @param roleId
     * @param permissions
     * @return
     */
    int deleterRolePermission(@Param("roleId") Integer roleId, @Param("permissions") List<Integer> permissions);

    /**
     * 查询角色权限
     * @param roleId
     * @return
     */
    List<PermissionDO> selectByRoleId(@Param("roleId") Integer roleId);

    /**
     * 查询权限code
     * @param roleIds
     * @return
     */
    List<String> selectByRoleIds(@Param("roleIds") List<Integer> roleIds);

}
