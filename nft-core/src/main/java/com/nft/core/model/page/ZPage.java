package com.nft.core.model.page;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author caoc
 * @createDate 2021/11/12
 */
@Data
@NoArgsConstructor
@ApiModel(value = "分页响应对象", description = "全局分页响应对象")
public class ZPage<T> implements Serializable {
    @ApiModelProperty(value = "实体集合")
    private List<T> items;

    @ApiModelProperty(value = "页码")
    private long pageNo = 1;

    @ApiModelProperty(value = "页面尺寸")
    private long pageSize = 10;

    @ApiModelProperty(value = "数据总数")
    private long count;

    public ZPage(List<T> items, long pageNo, long pageSize, long count) {
        this.items = items;
        this.pageNo = pageNo;
        this.pageSize = pageSize;
        this.count = count;
    }

    public ZPage(Page<T> page) {
        this.items = page.getRecords();
        this.pageNo = page.getCurrent();
        this.pageSize = page.getSize();
        this.count = page.getTotal();
    }

    public static <R, T> ZPage<T> transferFrom(Page<R> page, Function<R, T> mapFunc) {
        List<R> list = page.getRecords();
        if (CollectionUtil.isNotEmpty(list)) {
            if (Objects.nonNull(mapFunc)) {
                List<T> collect = list.stream().map(mapFunc)
                        .collect(Collectors.toList());
                return new ZPage<T>(collect, page.getCurrent(), page.getSize(), page.getTotal());
            } else {
                throw new IllegalArgumentException("mapFunc can't be null");
            }
        } else {
            return new ZPage<T>(Collections.EMPTY_LIST, page.getCurrent(), page.getSize(), page.getTotal());
        }
    }

    public long getTotalPageNo() {
        return count / pageSize + (count % pageSize == 0 ? 0 : 1);
    }

    public List<T> getItems() {
        return items;
    }

    public boolean hasNext() {
        return getPageNo() < getTotalPageNo();
    }

    public boolean hasPrevious() {
        return getPageNo() > 1;
    }

    public String getMsg() {
        return "第" + pageNo + "页,共" + count + "条";
    }

    public long getCount() {
        return this.count;
    }

    public long getTotal() {
        return this.count;
    }
}
