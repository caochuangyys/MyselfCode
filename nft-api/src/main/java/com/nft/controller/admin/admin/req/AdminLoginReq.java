package com.nft.controller.admin.admin.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author caoc
 * @createDate 2021/11/15
 */
@ApiModel(value = "管理员登录请求")
@Data
public class AdminLoginReq {

    @NotBlank(message = "用户名不能为空")
    @ApiModelProperty(value = "登录用户名", required = true)
    private String username;

    @NotBlank(message = "密码不能为空")
    @ApiModelProperty(value = "密码", required = true)
    private String password;

}
