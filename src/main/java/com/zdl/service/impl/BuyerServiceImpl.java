package com.zdl.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zdl.dto.OrderDTO;
import com.zdl.enums.ResultEnum;
import com.zdl.exception.SellException;
import com.zdl.service.BuyerService;
import com.zdl.service.OrderService;

import lombok.extern.slf4j.Slf4j;
@Service
@Slf4j
public class BuyerServiceImpl implements BuyerService {
	@Autowired
	private OrderService orderService;
	@Override
	public OrderDTO findOrderOne(String openid, String orderId) {
		OrderDTO orderDTO = orderService.findOne(orderId);
		if(orderDTO==null) {
			return null;
		}
		
		/*
		 * equals是重写object的方法，而
		 * 
		 * equalsIgnoreCase是String自己定义的方法,可以在org.apache.commons.lang3.StringUtils中找到
		 * 
		 * 前者用于比较两个对象是否相等，而后者用于比较字符串忽略大小写的情况下是否相等
		 */
		//判斷是否是自己的订单总数居,微信号忽略大小写
		if(!orderDTO.getBuyerOpenid().equalsIgnoreCase(openid)) {
			log.error("【查询订单】订单的openid不一致. openid={}, orderDTO={}", openid, orderDTO);
            throw new SellException(ResultEnum.ORDER_OWNER_ERROR);//该订单不属于当前用户
		}
		return orderDTO;
	}

	@Override
	public OrderDTO cancelOrder(String openid, String orderId) {
		OrderDTO orderDTO = orderService.findOne(orderId);
		
		if (orderDTO == null) {
            log.error("【取消订单】查不到改订单, orderId={}", orderId);
            throw new SellException(ResultEnum.ORDER_NOT_EXIST);
        }

		return orderService.cancel(orderDTO);
	}

}
