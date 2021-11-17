package com.nft.controller.admin.admin.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author caoc
 * @createDate 2021/11/15
 */
@Data
@ApiModel("财务管理员绑定贸易商")
public class AdminRelationTraderReq {

    @NotNull(message = "请上传财务管理员id")
    @ApiModelProperty(value = "财务管理员id", required = true)
    private Integer adminId;

    @NotEmpty(message = "请上传多个或单个贸易商id")
    @ApiModelProperty(value = "多个或单个贸易商id", required = true)
    private List<Integer> traderIds;
}
