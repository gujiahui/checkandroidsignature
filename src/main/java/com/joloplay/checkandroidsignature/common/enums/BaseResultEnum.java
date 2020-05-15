package com.joloplay.checkandroidsignature.common.enums;

import lombok.Getter;

/**
 * BaseResult的code编码枚举
 *
 */
@Getter
public enum BaseResultEnum {
    /**
     * SUCCESS: 200 成功
     * FAIL: 400 失败
     * NOT_FOUND： 404 不存在
     * SERVER_ERROR: 500 网络服务异常
     */
    SUCCESS(200, "成功"),
    FAIL(400, "失败"),
    NOT_FOUND(404, "不存在"),
    SERVER_ERROR(500, "服务异常"),
    NOT_NETWORK(400, "系统繁忙，请稍后再试。"),
    LOGIN_VERIFY_FALL(452, "登录失效"),
    PARAM_VERIFY_FALL(453, "参数验证错误"),
    AUTH_FAILED(454, "权限验证失败"),
    DATA_NOT(455, "没有相关数据"),
    DATA_CHANGE(456, "数据没有任何更改"),
    DATA_REPEAT(457, "数据已存在"),;
	

    private int code;

    private String message;

    BaseResultEnum(int code, String message) {
        this.code = code;
    }

}
