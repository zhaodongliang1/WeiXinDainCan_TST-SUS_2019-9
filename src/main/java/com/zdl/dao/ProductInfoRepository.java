package com.zdl.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.zdl.entity.ProductInfo;

public interface ProductInfoRepository extends JpaRepository<ProductInfo, String> {
	/**根据商品状态查询.*/
	List<ProductInfo> findByProductStatus(Integer productStatus);
}
