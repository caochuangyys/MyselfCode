package com.nft.controller.admin.admin.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author caoc
 * @createDate 2021/11/15
 */
@ApiModel("管理员删除请求")
@Data
public class AdminDelReq {

    @NotNull(message = "请传递管理员id")
    @ApiModelProperty(value = "管理员id", required = true)
    private Integer id;
}