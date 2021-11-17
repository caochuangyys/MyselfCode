package com.nft.controller.admin.admin.req;

import com.nft.core.model.page.PageQuery;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author caoc
 * @createDate 2021/11/15
 */
@Data
@ApiModel("管理员后台搜索请求")
public class AdminPageQuery extends PageQuery {

    @ApiModelProperty("手机号， 模糊搜索")
    private String phone;

    @ApiModelProperty("名称，模糊搜索")
    private String username;

    @ApiModelProperty("状态 0.冻结 1.激活")
    private Integer status;

}
