package com.zdl.service;

import java.util.List;



import com.zdl.entity.ProductCategory;

/**
 * @author DELL
 *
 */
public interface CategoryService {
	/**查询单个商品分类.*/
	ProductCategory findOne(Integer categoryId);
	/**查询所有商品分类.*/
	List<ProductCategory> findAll();
	/**查询一组商品分类.*/	
	List<ProductCategory> findByCategoryTypeIn(List<Integer> categoryTypeList);
	/**新增商品分类.*/
	ProductCategory save(ProductCategory productCategory);

}
