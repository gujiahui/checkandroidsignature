package com.joloplay.checkandroidsignature.common.enums;

import lombok.Getter;

/**
 * 运行环境枚举
 *
 */
@Getter
public enum EnvironmentEnum {

    /**
     * 开发环境
     */
    DEV("dev"),
    /**
     * 生产环境
     */
    PROD("prod"),;

    private String name;

    EnvironmentEnum(String name) {
        this.name = name;
    }
    

}
