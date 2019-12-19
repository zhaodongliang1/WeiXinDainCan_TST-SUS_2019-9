package com.zdl.exception;

import com.zdl.enums.ResultEnum;

/**
 * 订单异常
 * @author DELL
 *
 */
public class SellException extends RuntimeException {
	//错误码
	private Integer code;
	
	public SellException(ResultEnum resultEnum) {
		super(resultEnum.getMessage());//继承并拿到错误信息
		this.code=resultEnum.getCode();//返回错误码
	}
	 public SellException(Integer code, String message) {
	        super(message);
	        this.code = code;
	    }
}
