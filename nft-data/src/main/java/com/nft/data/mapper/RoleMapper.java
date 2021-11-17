package com.nft.data.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.nft.data.dto.admin.RoleListDTO;
import com.nft.data.entity.RoleDO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 角色表 Mapper 接口
 *
 * @author caoc
 * @createDate 2021/11/12
 */
@Repository
public interface RoleMapper extends BaseMapper<RoleDO> {

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
    List<RoleDO> findByAdminId(@Param("adminId") Integer adminId);

    /**
     * 插入管理员和角色关联
     * @param adminId
     * @param roleIds
     * @return
     */
    int insertAdminRoles(@Param("adminId") Integer adminId, @Param("roleIds") List<Integer> roleIds);

    /**
     * 删除管理员权限
     * @param adminId
     * @param roleIds
     */
    void deleteAdminRoles(@Param("adminId") Integer adminId, @Param("roleIds") List<Integer> roleIds);

    /**
     * 通过管理员ids，删除关联
     * @param adminIds
     */
    void deleteByAdminIds(@Param("adminIds") List<Integer> adminIds);
}
