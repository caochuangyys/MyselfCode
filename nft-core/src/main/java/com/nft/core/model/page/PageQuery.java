package com.nft.core.model.page;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * @author caoc
 * @createDate 2021/11/12
 */
@ApiModel(value = "分页请求对象", description = "全局分页请求父对象")
public class PageQuery implements Serializable {

    private static final int DEFAULT_PAGESIZE = 10;

    @ApiModelProperty(value = "页码")
    private Integer pageNo;

    @ApiModelProperty(value = "每页条数")
    private Integer pageSize;

    public PageQuery() {
        this(1, DEFAULT_PAGESIZE);
    }

    public PageQuery(Integer pageNo, Integer pageSize) {
        this.pageNo = Math.max(pageNo, 1);
        this.pageSize = pageSize <= 0 ? DEFAULT_PAGESIZE : pageSize;
    }

    public Integer getPageNo() {
        return pageNo;
    }

    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize <= 0 ? DEFAULT_PAGESIZE : pageSize;
    }

    public Integer calcOffset() {
        return (this.pageNo - 1) * pageSize;
    }

    public <T> Page<T> toPage() {
        Page<T> page = new Page<>(this.getPageNo(), this.getPageSize());
        return page;
    }
}
