package com.zdl.test.impl;

import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.zdl.entity.ProductCategory;
import com.zdl.service.impl.CategoryServiceImpl;

@SpringBootTest
@RunWith(SpringRunner.class)
public class CategoryServiceImplTest {// idea,Go To-->test,eclipse右键other，搜junit第一个直接生成测试类
	@Autowired
	private CategoryServiceImpl categoryService;

	@Test
	public void findOne() {
		ProductCategory productCategory = categoryService.findOne(1);
		Assert.assertEquals(new Integer(1), productCategory.getCategoryId());
	}

	@Test
	public void findByCategoryTypeIn() throws Exception {
		List<ProductCategory> productCategoryList = categoryService.findByCategoryTypeIn(Arrays.asList(1, 2, 3, 4));
		Assert.assertNotEquals(0, productCategoryList.size());
	}
	//整理代码ctrl+shift+f或右键source-->format
	/*
	 * @Test
	 * 
	 * @Transactional public void save() throws Exception { ProductCategory
	 * productCategory = new ProductCategory("男生专享", 10); ProductCategory result =
	 * categoryService.save(productCategory); Assert.assertNotNull(result); }
	 */
}
