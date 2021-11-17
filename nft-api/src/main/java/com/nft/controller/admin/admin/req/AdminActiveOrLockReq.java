package com.nft.controller.admin.admin.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * @author caoc
 * @createDate 2021/11/15
 */
@Data
@ApiModel("管理员激活或冻结请求")
public class AdminActiveOrLockReq {

    @NotNull(message = "请传递管理员id")
    @ApiModelProperty(value = "管理员id", required = true)
    private Integer id;

    @NotNull(message = "请传递状态")
    @Max(value = 1, message = "请传递正确状态数据")
    @Min(value = 0, message = "请传递正确状态数据")
    @ApiModelProperty(value = "状态：0.使冻结 1.使激活", required = true)
    private Integer status;
}
