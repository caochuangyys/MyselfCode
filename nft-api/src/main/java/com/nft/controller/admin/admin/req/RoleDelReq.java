package com.nft.controller.admin.admin.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author caoc
 * @createDate 2021/11/15
 */
@ApiModel("角色删除请求")
@Data
public class RoleDelReq {

    @NotNull(message = "请传递角色id")
    @ApiModelProperty(value = "角色id", required = true)
    private Integer id;

}
