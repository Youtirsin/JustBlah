package com.youtirsin.blah.message;

import java.io.Serializable;

public class MessageResult implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer code;
    private String msg;
    
    
	public MessageResult(ResultCode resultCode) {
		this.code = resultCode.getCode();
		this.msg = resultCode.getMessage();
	}

	public MessageResult() {
		this.code = ResultCode.COMMON_FAIL.getCode();
		this.msg = ResultCode.COMMON_FAIL.getMessage();
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
		this.msg = ResultCode.getMessageByCode(code);
	}

	public String getErrorMsg() {
		return msg;
	}
}
