package com.zdl.test.impl;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import com.zdl.enums.ProductStatusEnum;
import com.zdl.entity.ProductInfo;
import com.zdl.service.impl.ProductServiceImpl;
@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductServiceImplTest {
	@Autowired
    private ProductServiceImpl productService;
	@Test
	public void testFindOne() {
		
		ProductInfo findOne = productService.findOne("123456");
		
		Assert.assertEquals("123456", findOne.getProductId());
	}

	@Test
	public void testFindUpAll() {
		 List<ProductInfo> productInfoList = productService.findUpAll();
	     Assert.assertNotEquals(0, productInfoList.size());
	}

	@Test
	public void testFindAll() {
		 //pageRequest是实体类，pageable是接口
		 PageRequest request = new PageRequest(0, 2);//page:0,size:2
	     Page<ProductInfo> productInfoPage = productService.findAll(request);
	     Assert.assertNotEquals(0, productInfoPage.getTotalElements());
	}

	@Test
	public void testSave() {
		ProductInfo productInfo = new ProductInfo();
        productInfo.setProductId("123457");
        productInfo.setProductName("皮皮虾");
        productInfo.setProductPrice(new BigDecimal(3.2));
        productInfo.setProductStock(100);
        productInfo.setProductDescription("很好吃的虾");
        productInfo.setProductIcon("http://xxxxx.jpg");
        productInfo.setProductStatus(ProductStatusEnum.DOWN.getCode());
        productInfo.setCategoryType(2);

        ProductInfo result = productService.save(productInfo);
        Assert.assertNotNull(result);
	}

	@Test
	public void testIncreaseStock() {
		fail("Not yet implemented");
	}

	@Test
	public void testDecreaseStock() {
		fail("Not yet implemented");
	}

	@Test
	public void testOnSale() {
		ProductInfo result = productService.onSale("123456");
        Assert.assertEquals(ProductStatusEnum.UP, result.getProductStatusEnum());
	}

	@Test
	public void testOffSale() {
		ProductInfo result = productService.offSale("123456");
        Assert.assertEquals(ProductStatusEnum.DOWN, result.getProductStatusEnum());
	}

}
