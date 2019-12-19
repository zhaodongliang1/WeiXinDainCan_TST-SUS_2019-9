package com.zdl.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity
public class SellerInfo {
	/**客户编号.*/
    @Id
    private String sellerid;

    private String username;
    
    private String password;
    /**微信号.*/
    private String openid;
}

