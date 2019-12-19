package com.zdl.service;

import com.zdl.dto.OrderDTO;

/**
 * 推送消息	
 * @author DELL
 *
 */
public interface PushMessageService {
	/**
	 * 订单变更消息
	 * @param orderDTO
	 */
	void orderstatus(OrderDTO orderDTO);
}
