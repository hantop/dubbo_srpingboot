package com.lizikj.common.exception;

import com.lizikj.common.enums.ConvertErrorEnum;

import java.text.MessageFormat;

/**
 * Created by Michael.Huang on 2017/4/1.
 * dto,model之间转换异常
 */
public class ConvertException extends BaseException {

    private static final long serialVersionUID = 1L;

    /**
     * 错误信息模板
     */
    private static final String MSG_TEMPLATE = "错误码:{0}, 描述:{1}";
    private static final String MSG_FULL_TEMPLATE = "错误码:{0}, 描述:{1}, 异常信息:{2}";

    private ConvertErrorEnum convertErrorEnum;

    public ConvertException(ConvertErrorEnum convertErrorEnum) {
        super(MessageFormat.format(MSG_TEMPLATE
                , convertErrorEnum.getCode(), convertErrorEnum.getMessage()));
        this.convertErrorEnum = convertErrorEnum;
    }

    public ConvertException(ConvertErrorEnum convertErrorEnum, String exMsg) {
        super(MessageFormat.format(MSG_FULL_TEMPLATE
                , convertErrorEnum.getCode(), convertErrorEnum.getMessage(), exMsg));
        this.convertErrorEnum = convertErrorEnum;
    }

    public ConvertErrorEnum getConvertErrorEnum() {
        return convertErrorEnum;
    }

}
