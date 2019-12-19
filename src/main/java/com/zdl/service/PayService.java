package com.zdl.service;
/**
 * 支付
 * @author DELL
 *
 */

import com.lly835.bestpay.model.PayResponse;
import com.lly835.bestpay.model.RefundResponse;
import com.zdl.dto.OrderDTO;

public interface PayService {
	/**
	 * 创建订单
	 * @param orderDTO
	 * @return
	 */
	PayResponse create(OrderDTO orderDTO);
	/**
	 * 验证订单
	 * @param notifyData
	 * @return
	 */
	PayResponse notify(String notifyData);
	/**
	 * 退款
	 * @param OrderDTO
	 * @return
	 */
	RefundResponse refund(OrderDTO orderDTO);
}
