package com.zdl.service;

import com.zdl.entity.SellerInfo;

/**
 * 卖家端
 * @author DELL
 *
 */
public interface SellerService {
	 /**
     * 通过openid查询卖家端信息
     * @param openid
     * @return
     */
	SellerInfo findSellerInfoByOpenid(String openid);
}
