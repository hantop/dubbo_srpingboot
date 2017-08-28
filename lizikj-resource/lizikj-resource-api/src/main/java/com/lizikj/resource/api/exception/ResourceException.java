package com.lizikj.resource.api.exception;


import com.lizikj.resource.api.enums.ResourceErrorEnum;

import java.text.MessageFormat;

public class ResourceException extends RuntimeException {

    private static final long serialVersionUID = -5006042022910975725L;

    private ResourceErrorEnum resourceErrorEnum;

    private String extMsg;

    /**
     * 错误信息模板
     */
    private final static String MSG_TEMPLATE = "错误码:{0}, 描述:{1}";

    private final static String MSG_FULL_TEMPLATE = "错误码:{0}, 描述:{1}, 异常信息:{2}";

    public ResourceException(ResourceErrorEnum resourceErrorEnum) {
        super(MessageFormat.format(MSG_TEMPLATE
                , resourceErrorEnum.getCode(), resourceErrorEnum.getMessage()));
        this.resourceErrorEnum = resourceErrorEnum;
    }

    public ResourceException(ResourceErrorEnum resourceErrorEnum, String exMsg) {
        super(MessageFormat.format(MSG_FULL_TEMPLATE
                , resourceErrorEnum.getCode(), resourceErrorEnum.getMessage(), exMsg));
        this.resourceErrorEnum = resourceErrorEnum;
        this.extMsg = exMsg;
    }

}
