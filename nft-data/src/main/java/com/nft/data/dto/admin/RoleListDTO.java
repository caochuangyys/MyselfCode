package com.nft.data.dto.admin;

import lombok.Data;

import java.util.List;

/**
 * @author caoc
 * @createDate 2021/11/12
 */
@Data
public class RoleListDTO {


    private Long id;

    private String name;

    private String description;

    /**
     * 权限
     */
    private List<RoleMenuDTO> menus;

}
