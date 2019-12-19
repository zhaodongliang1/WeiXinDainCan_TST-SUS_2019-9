package com.zdl.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.zdl.entity.OrderMaster;

/**
 * @author DELL
 *
 */
public interface OrderMasterRepository extends JpaRepository<OrderMaster, String> {
	/**
	 * 根据微信号分页查询已支付的订单
	 * @param buyerOpenid
	 * @param pageable
	 * @return
	 */
	Page<OrderMaster> findByBuyerOpenid(String buyerOpenid,Pageable pageable);
	
}
