package com.zdl.entity;

import java.math.BigDecimal;
import java.util.Date;


import javax.persistence.Id;




import javax.persistence.Entity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.zdl.enums.ProductStatusEnum;
import com.zdl.util.EnumUtil;

import lombok.Data;
/**
 * 商品详情
 * @author DELL
 *
 */

@Data
@Entity
public class ProductInfo {
	@Id
	private String productId;

	/** 名字. */
	private String productName;

	/** 单价. */
	private BigDecimal productPrice;

	/** 库存. */
	private Integer productStock;

	/** 描述. */
	private String productDescription;

	/** 小图. */
	private String productIcon;

	/** 状态, 0正常1下架. */
	private Integer productStatus = ProductStatusEnum.UP.getCode();

	/** 类目编号. */
	private Integer categoryType;

	private Date createTime;

	private Date updateTime;

	@JsonIgnore
	public ProductStatusEnum getProductStatusEnum() {
	
		return EnumUtil.getByCode(productStatus, ProductStatusEnum.class);
	}

}
