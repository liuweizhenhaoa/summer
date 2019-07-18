package com.summer.common.exception;

import lombok.Data;

/**
 * 自定义异常
 * 
 * @author taiqirui
 * @email taiqirui@jtkjbike.com
 * @date 2018年10月27日 下午10:11:27
 */
@Data
public class BussinessException extends RuntimeException {
	private static final long serialVersionUID = 1L;

    private final String msg;
    private final Integer code=500;

    public BussinessException(String msg) {
		super(msg);
		this.msg = msg;
	}

	public BussinessException(String msg, Throwable e) {
		super(msg, e);
		this.msg = msg;
	}

	public BussinessException(String msg, Integer code) {
		super(msg);
		this.msg = msg;
		this.code = code;
	}

	public BussinessException(String msg, Integer code, Throwable e) {
		super(msg, e);
		this.msg = msg;
		this.code = code;
	}

}
