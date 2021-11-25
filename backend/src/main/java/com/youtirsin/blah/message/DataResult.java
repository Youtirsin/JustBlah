package com.youtirsin.blah.message;

import java.io.Serializable;

public class DataResult<T> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private final Integer code;
    private T data;
    
	public DataResult() {
		code = 200;
	}

	public DataResult(T data) {
		this.data = data;
		code = 200;
	}

	public Integer getCode() {
		return code;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}
}
