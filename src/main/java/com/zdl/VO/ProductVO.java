package com.zdl.VO;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
/**
 * 商品（包含类目）
 * @author DELL
 *
 */

@Data
public class ProductVO {
	@JsonProperty("name")//返回给前端使用
	 private String categoryName;
	 @JsonProperty("type")
	 private Integer categoryType;
	 @JsonProperty("foods")
	 private List<ProductInfoVO> productInfoVOList;

}
