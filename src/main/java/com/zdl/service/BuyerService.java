package com.zdl.service;

import com.zdl.dto.OrderDTO;
/**
 * 买家服务端
 * @author DELL
 *
 */
public interface BuyerService {
	 //查询一个订单
    OrderDTO findOrderOne(String openid, String orderId);

    //取消订单
    OrderDTO cancelOrder(String openid, String orderId);
}
