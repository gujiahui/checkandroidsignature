package com.joloplay.checkandroidsignature.common.enums;

import lombok.Getter;

/**
 * boolean枚举，YES/NO
 *
 */
@Getter
public enum BooleanEnum {

    /**
     * YES: 1
     */
    YES(1),

    /**
     * NO: 0
     */
    NO(0);

    private int value;

    BooleanEnum(int value) {
        this.value = value;
    }

}
