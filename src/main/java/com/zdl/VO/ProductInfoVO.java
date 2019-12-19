package com.zdl.VO;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * 商品详情
 * 
 * @author DELL
 *
 */
@Data
public class ProductInfoVO {
	@JsonProperty("id")
	private String productId;
	@JsonProperty("name")
	private String productName;
	@JsonProperty("price")
	private BigDecimal productPrice;
	@JsonProperty("description")
	private String productDescription;
	@JsonProperty("icon")
	private String productIcon;
}
