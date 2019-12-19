package com.zdl.test.dao;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;

import org.hibernate.annotations.Source;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.zdl.entity.ProductCategory;
import com.zdl.service.CategoryService;
@SpringBootTest()
@RunWith(SpringRunner.class)
public class ProductCategoryRepositoryTest {
	@Autowired
	private CategoryService repository;
	@Test
	public void testFindByCategoryTypeIn() {
		List<Integer> asList = Arrays.asList(2,3,4);
		List<ProductCategory> findByCategoryTypeIn = repository.findByCategoryTypeIn(asList);
		Assert.assertNotEquals(0, findByCategoryTypeIn);
	}
	@Test
	public void testFindOne() {
		ProductCategory findOne = repository.findOne(1);
		System.out.println(findOne.toString());
	}
	@Test
	@Transactional
    public void saveTest() {
        ProductCategory productCategory = new ProductCategory();
        productCategory.setCategoryName("女生最爱");
        productCategory.setCategoryType(100);
        ProductCategory result = repository.save(productCategory);
        System.out.println(result);
        Assert.assertNotNull(result);//返回结果不为空，则测试通过，报错测试不通过（assert断言是个宏，只在debug版有效，如果返回错误，终止程序，防止引起更大的错误）       
    }
	
	@Test
	@Transactional
	public void updateTest() {
		 ProductCategory productCategory = new ProductCategory();
		 productCategory.setCategoryName("男生罪爱");
		 productCategory.setCategoryType(1);
	     ProductCategory result = repository.save(productCategory);
	     Assert.assertEquals(productCategory, result);//判断两个对象是否相等
	}

}
