package com.nft.data.dto.admin;

import com.nft.data.dto.BaseDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author caoc
 * @createDate 2021/11/12
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "角色对象", description = "角色表")
public class RoleDTO extends BaseDTO {

    private static final long serialVersionUID=1L;

    @NotNull(message = "请输入角色名称")
    @ApiModelProperty(value = "角色名称")
    private String name;

    @ApiModelProperty(value = "描述")
    private String description;

    @ApiModelProperty(value = "权限集合")
    private List<Integer> permissions;


}

