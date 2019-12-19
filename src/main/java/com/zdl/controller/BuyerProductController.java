package com.zdl.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zdl.VO.ProductInfoVO;
import com.zdl.VO.ProductVO;
import com.zdl.VO.ResultVO;
import com.zdl.entity.ProductCategory;
import com.zdl.entity.ProductInfo;
import com.zdl.service.CategoryService;
import com.zdl.service.ProductService;
import com.zdl.util.ResultVOUtil;

@Controller
@RequestMapping("/buyer/product")
public class BuyerProductController {
	@Autowired
	private ProductService productService;
	@Autowired
	private CategoryService categoryService;

	@GetMapping("/list")
	public ResultVO list() {
		// 1.查询所有的上架商品
		List<ProductInfo> productInfoList = productService.findUpAll();
		// 2.查询类目（一次性查询）
		/** 上架商品的类别编号 */
		List<Integer> categoryTypeList = productInfoList.stream().map(e -> e.getCategoryType())
				.collect(Collectors.toList());
		List<ProductCategory> productCategoryList = categoryService.findByCategoryTypeIn(categoryTypeList);
		// 3.数据拼装
		List<ProductVO> productVOList = new ArrayList<>();
		for (ProductCategory productCategory : productCategoryList) {
			// 第一步
			ProductVO productVO = new ProductVO();
			productVO.setCategoryType(productCategory.getCategoryType());
			productVO.setCategoryName(productCategory.getCategoryName());
			// 第二步
			List<ProductInfoVO> productInfoVOList = new ArrayList<>();
			// 找出该商品类型下的所有商品
			for (ProductInfo productInfo : productInfoList) {

				if (productInfo.getCategoryType().equals(productCategory.getCategoryType())) {
					ProductInfoVO productInfoVO = new ProductInfoVO();
					// 将一个对象的值拷贝到另一个对象里
					BeanUtils.copyProperties(productInfo, productInfoVO);
					productInfoVOList.add(productInfoVO);
				}
			}
		}
		return ResultVOUtil.success(productVOList);
	}
}
