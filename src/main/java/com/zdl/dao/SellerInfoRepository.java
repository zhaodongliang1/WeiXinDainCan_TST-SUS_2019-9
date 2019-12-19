package com.zdl.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.zdl.entity.SellerInfo;

public interface SellerInfoRepository extends JpaRepository<SellerInfo, String> {
    SellerInfo findByOpenid(String openid);
}
