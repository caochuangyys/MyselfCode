package com.nft.controller.admin.admin.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author caoc
 * @createDate 2021/11/15
 */
@Data
@ApiModel("管理员信息更新请求")
public class AdminProfileUpdateReq {

    @ApiModelProperty("管理员头像")
    private String avatarUrl;

    @ApiModelProperty(value = "手机号")
    private String phone;

    @NotBlank(message = "请填写姓名")
    @ApiModelProperty(value = "真实姓名")
    private String realname;

}

