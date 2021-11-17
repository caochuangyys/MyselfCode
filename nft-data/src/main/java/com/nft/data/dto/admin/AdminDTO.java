package com.nft.data.dto.admin;

import com.nft.data.dto.BaseDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;
import java.util.List;

/**
 * @author caoc
 * @createDate 2021/11/12
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "Admin管理员对象", description = "管理员表")
public class AdminDTO extends BaseDTO {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "用户名")
    private String username;

    @ApiModelProperty("管理员头像")
    private String avatarUrl;


    @ApiModelProperty(value = "手机号")
    private String phone;

    @ApiModelProperty(value = "真实姓名")
    private String realname;

    @ApiModelProperty(value = "0.冻结 1.激活")
    private Integer status;

    @ApiModelProperty(value = "是否超级管理员，0：否，1：是")
    private Integer isSuper;

    @ApiModelProperty(value = "上次登录ip")
    private String lastLoginIp;

    @ApiModelProperty(value = "上次登录时间")
    private Date gmtLastLogin;

    @ApiModelProperty(value = "拥有角色")
    private List<String> roles;

    @ApiModelProperty(value = "拥有角色id")
    private List<Integer> roleIds;

    @ApiModelProperty(value = "权限集合")
    private List<String> perms;


}
