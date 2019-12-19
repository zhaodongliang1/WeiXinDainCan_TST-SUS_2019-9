package com.zdl.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.zdl.entity.ProductCategory;

public interface ProductCategoryRepository extends JpaRepository<ProductCategory, Integer> {
	List<ProductCategory> findByCategoryTypeIn(List<Integer> categoryTypeList);

}
