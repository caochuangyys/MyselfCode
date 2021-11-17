package com.nft.data.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author caoc
 * @createDate 2021/11/12
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("t_role_permission")
@ApiModel(value = "RolePermission对象", description = "角色权限表")
public class RolePermissionDO extends BaseDO {

    private static final long serialVersionUID=1L;
    @ApiModelProperty(value = "角色ID")
    private Integer roleId;

    @ApiModelProperty(value = "权限id")
    private Integer permissionId;



}
