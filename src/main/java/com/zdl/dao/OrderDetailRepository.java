package com.zdl.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.zdl.entity.OrderDetail;

public interface OrderDetailRepository extends JpaRepository<OrderDetail, String> {
	/**
	 * 根据订单id查询订单详情
	 * @param orderId
	 * @return
	 */
	List<OrderDetail> findByOrderId(String orderId);
	
}
