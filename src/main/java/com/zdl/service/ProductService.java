package com.zdl.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.zdl.dto.CartDTO;
import com.zdl.entity.ProductInfo;

/**
 * @author DELL
 *
 */
public interface ProductService {
	/**
	 * 根据id查询单个商品
	 * @param productId
	 * @return
	 */
	ProductInfo findOne(String productId);
	/**
	 * 查询所有上架商品
	 * @return
	 */
	List<ProductInfo> findUpAll();
	/**
	 * 分页查询所有商品
	 * @param pageable
	 * @return 返回的是page對象
	 */
	Page<ProductInfo> findAll(Pageable pageable);
	/**
	 * 添加商品
	 * @param productInfo
	 * @return
	 */
    ProductInfo save(ProductInfo productInfo); 
    //加库存
    void increaseStock(List<CartDTO> cartDTOList);
    //减库存
    void decreaseStock(List<CartDTO> cartDTOList);
    //上架
    ProductInfo onSale(String productId);
    //下架
    ProductInfo offSale(String productId);
    
}
