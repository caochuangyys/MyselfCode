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
@TableName("t_role")
@ApiModel(value = "Role对象", description = "角色表")
public class RoleDO extends BaseDO {

    private static final long serialVersionUID=1L;
    @ApiModelProperty(value = "角色名称")
    private String name;

    @ApiModelProperty(value = "描述")
    private String description;

}