
  package com.zdl.service.impl;
  
  import org.springframework.beans.factory.annotation.Autowired; import
  org.springframework.stereotype.Service;
  
  import com.zdl.dao.SellerInfoRepository; import com.zdl.entity.SellerInfo;
  import com.zdl.service.SellerService;
  
  @Service public class SellerServiceImpl implements SellerService {
  
  @Autowired private SellerInfoRepository repository;
  
  @Override public SellerInfo findSellerInfoByOpenid(String openid) { return
  repository.findByOpenid(openid); } }
 