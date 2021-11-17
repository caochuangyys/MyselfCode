package com.nft.data.consts;

/**
 * @author caoc
 * @createDate 2021/11/12
 */
public class SystemConstant {
    /**
     * 贸易商用户登录token
     */
    public static final String USER_ACCESS_TOKEN = "USER-ACCESSTOKEN";

    /**
     * 用户状态过期时间
     */
    public static final int USER_SESSION_EXPIRE_TIME = 15 * 24 * 60 * 60;

    /**
     * 管理员登录token
     */
    public static final String ADMIN_ACCESS_TOKEN = "ADMIN-ACCESSTOKEN";

    /**
     * 资质商员工登录token
     */
    public static final String EMPLOYEE_ACCESS_TOKEN = "EMPLOYEE-ACCESSTOKEN";

    /**
     * 管理员状态过期时间
     */
    public static final int ADMIN_SESSION_EXPIRE_TIME = 15 * 24 * 60 * 60;

    /**
     * 地区树的key
     */
    public static final String AREA_TREE_DATA = "AREA_TREE_DATA";

    /**
     * 城市树的key
     */
    public static final String CITY_TREE_DATA = "CITY_TREE_DATA";

    /**
     * 城市数据key
     */
    public static final String CITY_BY_LETTER_TREE_DATA = "CITY_BY_LETTER_TREE_DATA";

}
