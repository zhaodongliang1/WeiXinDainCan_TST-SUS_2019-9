package com.zdl.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.DynamicUpdate;

import lombok.Data;

/**
 * 类目
 * @author DELL
 *
 */
@Entity
@DynamicUpdate//自动更新时间
@Data
public class ProductCategory {
	/**类目id. */
	@Id
	@GeneratedValue//自增类型
	private Integer categoryId;
	/** 类目名字. */
	private String categoryName;
	/** 类目编号. */
	private Integer categoryType;
	/**创建日期. */
	private Date createTime;
	/**修改日期. */
	private Date updateTime;
	

	
    
}

	  
	

	

	
	

