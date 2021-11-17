package com.nft.core.model.copy;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cglib.beans.BeanCopier;

/**
 * @author caoc
 * @createDate 2021/11/12
 */
@Slf4j
public class BaseCopy {

    /**
     * 属性拷贝
     *
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
