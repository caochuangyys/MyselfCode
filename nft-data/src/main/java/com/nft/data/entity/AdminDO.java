package com.nft.data.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * @author caoc
 * @createDate 2021/11/12
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("t_admin")
@ApiModel(value = "Admin对象", description = "管理员表")
public class AdminDO extends BaseDO {

    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "用户名")
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
    private Integer isSuper;

    @ApiModelProperty(value = "0.冻结 1.激活")
    private Integer status;

    @ApiModelProperty(value = "上次登录ip")
    private String lastLoginIp;

    @ApiModelProperty(value = "上次登录时间")
    private Date gmtLastLogin;



}

