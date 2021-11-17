package com.nft.controller.admin.admin.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * @author caoc
 * @createDate 2021/11/15
 */
@Data
@ApiModel("管理员新增或者更新对象")
public class AdminSaveOrUpdateReq {

    @ApiModelProperty(value = "主键id")
    private Integer id;

    @NotBlank(message = "请填写登录用户名")
    @ApiModelProperty(value = "登录用户名", required = true)
    private String username;

    @ApiModelProperty(value = "密码")
    private String password;

    @ApiModelProperty("管理员头像")
    private String avatarUrl;

    @ApiModelProperty(value = "手机号")
    private String phone;

    @ApiModelProperty(value = "真实姓名")
    private String realname;

    @ApiModelProperty(value = "是否超级管理员，0：否，1：是")
    private Integer isSuper = 0;

    @ApiModelProperty(value = "角色相关id")
    private List<Integer> roleIds;

}
