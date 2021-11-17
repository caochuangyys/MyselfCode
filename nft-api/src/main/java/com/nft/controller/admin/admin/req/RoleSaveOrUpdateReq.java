package com.nft.controller.admin.admin.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * @author caoc
 * @createDate 2021/11/15
 */
@Data
@ApiModel(value = "角色创建或更新对象")
public class RoleSaveOrUpdateReq {

    @ApiModelProperty(value = "主键id")
    private Integer id;

    @NotNull(message = "请输入角色名称")
    @ApiModelProperty(value = "角色名称", required = true)
    private String name;

    @ApiModelProperty(value = "描述")
    private String description;

    @Size(min = 1, message = "请至少选择一个权限")
    @ApiModelProperty(value = "权限集合")
    private List<Integer> permissions;

}
