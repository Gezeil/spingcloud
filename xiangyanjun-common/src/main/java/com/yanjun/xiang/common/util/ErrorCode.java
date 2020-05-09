package com.yanjun.xiang.common.util;

import java.io.Serializable;

public class ErrorCode implements Serializable {
    private static final long serialVersionUID = 2938403856515968992L;

    public static final org.spin.core.ErrorCode OTHER = new org.spin.core.ErrorCode(-1, "其他");
    public static final org.spin.core.ErrorCode OK = new org.spin.core.ErrorCode(200, "OK");

    /////////////////////////////////// 内部错误，不应暴露给客户端 ////////////////////////////////////////////////
    public static final org.spin.core.ErrorCode DATEFORMAT_UNSUPPORT = new org.spin.core.ErrorCode(5, "时间/日期格式不支持");
    public static final org.spin.core.ErrorCode KEY_FAIL = new org.spin.core.ErrorCode(10, "获取密钥失败");
    public static final org.spin.core.ErrorCode ENCRYPT_FAIL = new org.spin.core.ErrorCode(11, "加密算法执行失败");
    public static final org.spin.core.ErrorCode DEENCRYPT_FAIL = new org.spin.core.ErrorCode(15, "解密算法执行失败");
    public static final org.spin.core.ErrorCode SIGNATURE_FAIL = new org.spin.core.ErrorCode(20, "签名验证失败");
    public static final org.spin.core.ErrorCode BEAN_CREATE_FAIL = new org.spin.core.ErrorCode(40, "创建bean实例错误");
    public static final org.spin.core.ErrorCode IO_FAIL = new org.spin.core.ErrorCode(70, "IO异常");
    public static final org.spin.core.ErrorCode NETWORK_EXCEPTION = new org.spin.core.ErrorCode(100, "网络连接异常");
    public static final org.spin.core.ErrorCode SERIALIZE_EXCEPTION = new org.spin.core.ErrorCode(120, "JSON序列化错误");

    /////////////////////////////////// 可通过Restful接口暴露给客户端的错误 //////////////////////////////////////
    // 4** 访问及权限错误
    public static final org.spin.core.ErrorCode LOGGIN_DENINED = new org.spin.core.ErrorCode(400, "登录失败");
    public static final org.spin.core.ErrorCode ACCESS_DENINED = new org.spin.core.ErrorCode(401, "未授权的访问");
    public static final org.spin.core.ErrorCode ASSERT_FAIL = new org.spin.core.ErrorCode(410, "数据验证失败");
    public static final org.spin.core.ErrorCode INVALID_PARAM = new org.spin.core.ErrorCode(412, "参数不合法");
    public static final org.spin.core.ErrorCode NO_BIND_USER = new org.spin.core.ErrorCode(413, "无关联用户");
    public static final org.spin.core.ErrorCode SMS_VALICODE_ERROR = new org.spin.core.ErrorCode(420, "短信验证码错误");

    // 5** 服务端运行错误
    public static final org.spin.core.ErrorCode INTERNAL_ERROR = new org.spin.core.ErrorCode(500, "服务端内部错误");

    // 6** Token相关错误
    public static final org.spin.core.ErrorCode TOKEN_EXPIRED = new org.spin.core.ErrorCode(601, "Token已过期");
    public static final org.spin.core.ErrorCode TOKEN_INVALID = new org.spin.core.ErrorCode(602, "无效的Token");
    public static final org.spin.core.ErrorCode SECRET_EXPIRED = new org.spin.core.ErrorCode(651, "密钥已过期");
    public static final org.spin.core.ErrorCode SECRET_INVALID = new org.spin.core.ErrorCode(653, "无效的密钥");
    public static final org.spin.core.ErrorCode SESSION_INVALID = new org.spin.core.ErrorCode(700, "会话已经失效");
    public static final org.spin.core.ErrorCode SESSION_EXPIRED = new org.spin.core.ErrorCode(702, "会话已经过期");

    public static final org.spin.core.ErrorCode USER_OPERATE_NO_PERMISSION = new org.spin.core.ErrorCode(10403, "用户操作未授权");

    //流控
    public static final org.spin.core.ErrorCode BLOCK_ERROR = new org.spin.core.ErrorCode(710, "超过流量控制");
    public static final org.spin.core.ErrorCode DEGRADE_ERROR = new org.spin.core.ErrorCode(711, "服务降级处理");

    private final int code;
    private final String desc;

    public ErrorCode(int value, String desc) {
        this.code = value;
        this.desc = desc;
    }

    public static org.spin.core.ErrorCode with(int value, String desc) {
        return new org.spin.core.ErrorCode(value, desc);
    }

    public int getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    @Override
    public String toString() {
        return "ERROR CODE[" + code + "-" + desc + ']';
    }
}
