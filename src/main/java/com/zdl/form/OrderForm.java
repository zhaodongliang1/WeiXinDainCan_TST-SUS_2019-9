package com.zdl.form;

import org.hibernate.validator.constraints.NotEmpty;

import lombok.Data;
@Data
public class OrderForm {
	/**
	 * 买家姓名
	 */
	//@NotEmpty是不能为null或者长度为0
	@NotEmpty(message="姓名必填")
	private String name;
	/**
	 * 买家手机号
	 */
	@NotEmpty(message="手机号必填")
	private String phone;
	/**
	 * 买家地址
	 */
	@NotEmpty(message="地址必填")
	private String address;
	/**
	 * 买家微信号
	 */
	@NotEmpty(message="openid必填")
	private String openid;
	/**
	 * 
	 */
	@NotEmpty(message="购物车必填")
	private String items;
	
}
