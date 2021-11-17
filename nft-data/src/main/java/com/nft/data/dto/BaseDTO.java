package com.nft.data.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cglib.beans.BeanCopier;

import java.util.Date;

/**
 * @author caoc
 * @createDate 2021/11/12
 */
@Slf4j
@Data
public class BaseDTO {

    @ApiModelProperty(value = "主键id")
    private Integer id;


    /**
     * 更新时间
     */
    @JsonIgnore
    @ApiModelProperty(value = "更新时间")
    private Date gmtUpdate;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    private Date gmtCreate;

    /**
     * 属性拷贝
     * @param cla 目标
     * @param <T> 当前对象
     * @return
     */
    public <T> T toDo(Class<T> cla) {
        T t = null;
        try {
            t = cla.newInstance();
            /*高性能*/
            BeanCopier copier = BeanCopier.create(this.getClass(), cla, false);
            copier.copy(this, t, null);
        } catch (Exception e) {
            log.warn("toDo失败！" + e.getLocalizedMessage());
        }
        return t;
    }
}
