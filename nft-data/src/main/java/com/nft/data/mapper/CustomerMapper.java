package com.nft.data.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.nft.data.entity.CustomerDO;
import org.springframework.stereotype.Repository;

/**
 * <p>
 *  资信用户表 Mapper 接口
 * </p>
 *
 * @author caoc
 * @createDate 2021/11/12
 */
@Repository
public interface CustomerMapper extends BaseMapper<CustomerDO> {

}
