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
@TableName("t_permission")
@ApiModel(value = "Permission对象", description = "权限表")
public class PermissionDO extends BaseDO {

    private static final long serialVersionUID=1L;
    @ApiModelProperty(value = "归属菜单,前端判断并展示菜单使用,")
    private String menuCode;

    @ApiModelProperty(value = "菜单的中文释义")
    private String menuName;

    @ApiModelProperty(value = "权限的代码/通配符,对应代码中@RequiresPermissions 的value")
    private String permissionCode;

    @ApiModelProperty(value = "本权限的中文释义")
    private String permissionName;

    @ApiModelProperty(value = "是否本菜单必选权限, 1.必选 2非必选 通常是'列表'权限是必选")
    private Boolean requiredPermission;



}
