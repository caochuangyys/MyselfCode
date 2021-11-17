package com.nft.core.exception;

import lombok.Getter;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author caoc
 * @createDate 2021/11/12
 */
@Getter
public enum ExceptionCode {

    //系统模块
    SYSTEM_ERR(10000, "服务器开小差了~"),
    METHOD_ARGUMENT_VALID_ERR(10001, "参数校验错误：{0}"),
    METHOD_NOT_SUPPORT(10002, "请求方式不支持"),
    METHOD_ARGUMENT_TYPE_MISSMATCH(10003, "参数转换错误"),
    THIRD_PART_SERVICE_ERR(10004, "第三方服务异常"),
    SMS_SERVICE_ERR(10005, "短信发送失败"),
    HTTP_REMOTE_REQUEST_ERR(10006, "远程网络请求失败"),
    ESIGN_REQUEST_ERR(10007, "E签宝认证失败：{0}"),

    //用户模块
    USER_NOT_LOGIN(20000, "登录已失效，请重新登录"),
    USER_ACCOUNT_NOT_VALID(20002, "该用户已被冻结，请联系客服"),
    USER_PHONE_NOT_EXIST(20003, "该用户手机账号未注册"),
    USER_PHONE_DUPLICATED(20004, "用户手机号已存在"),
    USER_PWD_ERR(20005, "登录密码不匹配"),
    USER_PHONE_ALREADY_EXIST(20006, "该手机号已被注册"),
    USER_GENDER_ERR(20007, "性别类型传值异常"),
    USER_HAS_REAL_AUTHED(20008, "您已完成实名认证，请勿重复操作"),
    USER_NOT_REAL_AUTH(20009, "请先完成用户实名认证"),
    USER_NOT_TRADER_CERTIFICATION(20010, "请先完成企业认证"),

    //管理员模块
    ADMIN_NOT_LOGIN(30000, "管理员登录已失效，请重新登录"),
    ADMIN_NOT_EXIST(30001, "管理员不存在，请联系客服"),
    ADMIN_ACCOUNT_DISABLE(30002, "您的账户已被冻结，请联系管理员"),
    ADMIN_ROLE_IS_EMPTY(30003, "管理员角色为空,请先分配角色！"),
    ADMIN_USERNAME_BE_USED(30004, "该用户名已被其他用户使用!"),
    ADMIN_ROLE_REQUIRED(30005, "请至少选择一个角色"),
    ADMIN_ROLE_NAME_EXIST(30006, "角色名已存在"),
    ADMIN_ROLE_NOT_EXIST(30007, "角色数据不存在！"),
    ADMIN_CANNOT_OPERATION_SELF(30008, "您不能操作自己账号的状态！"),
    ADMIN_PASSWORD_ERR(30009, "密码输入错误!"),
    ADMIN_PWD_REQUIRED(30010, "请输入密码"),
    ADMIN_OLD_PWD_ERR(30011, "原密码输入错误"),


    //公共模块
    COMMON_PHONE_NUMBER_ERR(40000, "请输入正确的手机号"),
    COMMON_VERIFIY_CODE_EXPIRED(40001, "验证码已过期"),
    COMMON_VERIFY_CODE_NOT_MATCH(40002, "验证码不匹配"),
    COMMON_AREA_CITY_REQUIRED(40003, "请选择一个城市"),
    COMMON_SEARCH_TYPE_ERR(40004, "请传递正确的搜索类型"),

    ;


    private int code;

    private String msg;

    ExceptionCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public static Map<Integer, String> toValueMap() {
        return Arrays.stream(values()).collect(Collectors.toMap(ExceptionCode::getCode, ExceptionCode::getMsg));
    }
}
