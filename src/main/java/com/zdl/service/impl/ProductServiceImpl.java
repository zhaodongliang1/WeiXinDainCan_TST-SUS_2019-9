package com.zdl.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zdl.dao.ProductInfoRepository;
import com.zdl.dto.CartDTO;
import com.zdl.entity.ProductInfo;
import com.zdl.enums.ProductStatusEnum;
import com.zdl.enums.ResultEnum;
import com.zdl.exception.SellException;
import com.zdl.service.ProductService;
@Service
public class ProductServiceImpl implements ProductService {
	@Autowired
	private ProductInfoRepository repository;
	@Override
	public ProductInfo findOne(String productId) {
		
		return repository.findOne(productId);
	}

	@Override
	public List<ProductInfo> findUpAll() {
		// TODO Auto-generated method stub
		return repository.findByProductStatus(ProductStatusEnum.UP.getCode());
	}

	@Override
	public Page<ProductInfo> findAll(Pageable pageable) {
		// TODO Auto-generated method stub
		return repository.findAll(pageable);
	}

	@Override
	public ProductInfo save(ProductInfo productInfo) {
		// TODO Auto-generated method stub
		return repository.save(productInfo);
	}
	//加库存
	@Override
	@Transactional
	public void increaseStock(List<CartDTO> cartDTOList) {
		// TODO Auto-generated method stub
		for (CartDTO cartDTO : cartDTOList) {
			
			ProductInfo productInfo = repository.findOne(cartDTO.getProductId());
			
			if(productInfo==null) {
				throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
			}
			
			Integer result=productInfo.getProductStock()+cartDTO.getProductQuantity();;
			productInfo.setProductStock(result);
			
			repository.save(productInfo);
			
			
		}
	}

	//减库存
	@Override
	/* @Transactional */
	public void decreaseStock(List<CartDTO> cartDTOList) {
		// TODO Auto-generated method stub
		for (CartDTO cartDTO : cartDTOList) {
			ProductInfo productInfo = repository.findOne(cartDTO.getProductId());
			if(productInfo==null) {
				throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);//商品不存在
			}
			Integer result=productInfo.getProductStock()-cartDTO.getProductQuantity();//库存数
			System.out.println(result);
			if(result<0) {
				throw new SellException(ResultEnum.PRODUCT_STOCK_ERROR);//商品库存不正确
			}
			//更新库存数
			productInfo.setProductStock(result);
			repository.save(productInfo);//修改
		}
	}
	//上架
	@Override
	public ProductInfo onSale(String productId) {
		// TODO Auto-generated method stub
		ProductInfo productInfo = repository.findOne(productId);
        if (productInfo == null) {
            throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
        }
        //产品状态==上架
        if(productInfo.getProductStatusEnum()==ProductStatusEnum.UP) {
        	throw new SellException(ResultEnum.PRODUCT_STATUS_ERROR);//商品状态不正确
        }
        //更新,赋值为0上架
        productInfo.setProductStatus(ProductStatusEnum.UP.getCode());
		return repository.save(productInfo);
	}

	@Override
	public ProductInfo offSale(String productId) {
		// TODO Auto-generated method stub
		 ProductInfo productInfo = repository.findOne(productId);
	        if (productInfo == null) {
	            throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
	        }
	        if (productInfo.getProductStatusEnum() == ProductStatusEnum.DOWN) {
	            throw new SellException(ResultEnum.PRODUCT_STATUS_ERROR);
	        }
	      //更新,赋值为1下架
	        productInfo.setProductStatus(ProductStatusEnum.DOWN.getCode());
	        return repository.save(productInfo);//修改
	}



	

}
