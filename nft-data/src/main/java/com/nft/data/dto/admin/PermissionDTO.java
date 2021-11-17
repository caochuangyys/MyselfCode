package com.nft.data.dto.admin;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;

import java.util.List;

/**
 * @author caoc
 * @createDate 2021/11/12
 */
@Data
public class PermissionDTO {

    private static final long serialVersionUID = 1L;

    private String menuCode;

    private String menuName;

    List<JSONObject> permissions;
}
