package com.nft.data.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
public class BaseDO {

    @TableId(value="id",type= IdType.AUTO)
    private Integer id;

    /**
     * 更新时间
     */
    @JsonIgnore
    @TableField("gmt_update")
    private Date gmtUpdate;

    /**
     * 创建时间
     */
    @TableField("gmt_create")
    private Date gmtCreate;

    /**
     * 逻辑删除
     */
    @JsonIgnore
    @TableLogic
    private Boolean deleted;


    /**
     * 属性拷贝
     * @param cla 目标
     * @param <T> 当前对象
     * @return
     */
    public <T> T toCopy(Class<T> cla) {
        T t = null;
        try {
            t = cla.newInstance();
            /*高性能*/
            BeanCopier copier = BeanCopier.create(this.getClass(), cla, false);
            copier.copy(this, t, null);
        } catch (Exception e) {
            log.warn("toCopy失败！" + e.getLocalizedMessage());
        }
        return t;
    }
}
