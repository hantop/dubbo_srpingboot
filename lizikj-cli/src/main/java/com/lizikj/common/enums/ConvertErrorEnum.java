
package com.lizikj.common.enums;

import java.io.Serializable;

/**
 * Created by Michael.Huang on 2017/4/1.
 */
public enum ConvertErrorEnum implements Serializable {
	/**
	 * dto转换成model或者model转换成dto出错
	 */
	DTO_CONVERT_TO_MODEL_ERROR("101", "dto转换model异常"),
	MODEL_CONVERT_TO_DTO_ERROR("102", "model转换dto异常");
	private String code;
	private String message;
	private ConvertErrorEnum(){}
	
	private ConvertErrorEnum(String code, String message){
		this.code = code;
		this.message = message;
	}
	public String getCode() {
		return code;
	}
	void setCode(String code) {
		this.code = code;
	}
	public String getMessage() {
		return message;
	}
	void setMessage(String message) {
		this.message = message;
	}
	public static ConvertErrorEnum getByCode(String code) {
        for (ConvertErrorEnum errorEnum : values()) {
            if (errorEnum.getCode().equals(code)) {
                return errorEnum;
            }
        }
        return null;
    }
}
