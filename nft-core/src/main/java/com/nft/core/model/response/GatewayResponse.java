package com.nft.core.model.response;

import lombok.Data;

/**
 * @author caoc
 * @createDate 2021/11/12
 */
@Data
public class GatewayResponse {
    private int errno;
    private String errmsg;
    private Object data;
    private long timestamp = System.currentTimeMillis();
}
