package com.nft.controller.admin.admin.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author caoc
 * @createDate 2021/11/15
 */
@ApiModel("修改密码")
@Data
public class ChangePwdReq {

    @NotBlank(message = "请输入原密码")
    @ApiModelProperty(value = "原密码", required = true)
    private String oldPassword;

    @NotBlank(message = "请输入新密码")
    @ApiModelProperty(value = "新密码", required = true)
    private String newPassword;
}
