package com.zdl.service.impl;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.simpleframework.xml.core.ElementException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.zdl.converter.OrderMaster2OrderDTOConverter;
import com.zdl.dao.OrderDetailRepository;
import com.zdl.dao.OrderMasterRepository;
import com.zdl.dto.CartDTO;
import com.zdl.dto.OrderDTO;
import com.zdl.entity.OrderDetail;
import com.zdl.entity.OrderMaster;
import com.zdl.entity.ProductInfo;
import com.zdl.enums.OrderStatusEnum;
import com.zdl.enums.PayStatusEnum;
import com.zdl.enums.ResultEnum;
import com.zdl.exception.SellException;
import com.zdl.service.OrderService;
import com.zdl.service.PayService;
import com.zdl.service.ProductService;
import com.zdl.service.PushMessageService;
import com.zdl.service.WebSocket;
import com.zdl.util.KeyUtil;


import lombok.extern.slf4j.Slf4j;


@Slf4j
@Service
public class OrderServiceImpl implements OrderService {
	@Autowired
    private ProductService productService;

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Autowired
    private OrderMasterRepository orderMasterRepository;

	/*
	 * @Autowired private PayService payService;
	 * 
	 * @Autowired private PushMessageService pushMessageService;
	 * 
	 * @Autowired private WebSocket webSocket;
	 */
	@Override
	@Transactional
	public OrderDTO create(OrderDTO orderDTO) {
		
		//订单总价
		BigDecimal orderAmount = new BigDecimal(BigInteger.ZERO);
		System.out.println("orderAmount:"+orderAmount);
		//订单id
		String orderId = KeyUtil.genUniqueKey();
		System.out.println("orderId:"+orderId);
		
		//1.查询商品（数量，价格）
		for (OrderDetail orderDetail : orderDTO.getOrderDetailList()) {
			
			
			ProductInfo productInfo = productService.findOne(orderDetail.getProductId());
			System.out.println("productInfo:"+productInfo);
			if(productInfo==null) {
				throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
			}
			//2.计算总价（multiply乘法）
			 orderAmount = productInfo.getProductPrice().multiply(new BigDecimal(orderDetail.getProductQuantity())).add(orderAmount);
			System.out.println("orderAmount1:"+orderAmount);
			 //订单详情入库
			 orderDetail.setDetailId(KeyUtil.genUniqueKey());
			 orderDetail.setOrderId(orderId);
			BeanUtils.copyProperties(productInfo, orderDetail);//将productInfo的数据填写到orderDetail中
			System.out.println("orderDetail2:"+orderDetail.toString());
			OrderDetail orderDetail2 = orderDetailRepository.save(orderDetail);
			System.out.println("orderDetail2:"+orderDetail2.toString());
		}
		
		//3.写入订单数据库（orderMaster和orderDetail）
		OrderMaster orderMaster = new OrderMaster();
		orderDTO.setOrderId(orderId);
		BeanUtils.copyProperties(orderDTO, orderMaster);
		
		orderMaster.setOrderAmount(orderAmount);
		orderMaster.setOrderStatus(OrderStatusEnum.NEW.getCode());//新订单
		orderMaster.setPayStatus(PayStatusEnum.WAIT.getCode());//未支付
		OrderMaster orderMaster3 = orderMasterRepository.save(orderMaster);
		System.out.println("orderMaster3:"+orderMaster3.toString());
		//4.扣库存
		List<CartDTO> cartDTOs=orderDTO.getOrderDetailList().stream().map(e ->
			new CartDTO(e.getProductId(),e.getProductQuantity())
				).collect(Collectors.toList());
		System.out.println("cartDTOs:"+cartDTOs.toString());
		productService.decreaseStock(cartDTOs);
		System.out.println("cartDTOs2:"+cartDTOs.toString());
		return orderDTO;
	}

	@Override
	public OrderDTO findOne(String orderId) {
		OrderMaster orderMaster = orderMasterRepository.findOne(orderId);
		if(orderMaster==null) {
			throw new SellException(ResultEnum.ORDER_NOT_EXIST);
		}
		List<OrderDetail> orderDetailList = orderDetailRepository.findByOrderId(orderId);
		if(CollectionUtils.isEmpty(orderDetailList)) {
			throw new SellException(ResultEnum.ORDERDETAIL_NOT_EXIST);
		}
		OrderDTO orderDTO = new OrderDTO();
		BeanUtils.copyProperties(orderMaster, orderDTO);
		orderDTO.setOrderDetailList(orderDetailList);
		return orderDTO;
	}

	@Override
	public Page<OrderDTO> findList(String buyerOpenid, Pageable pageable) {
		Page<OrderMaster> orderMasterPage = orderMasterRepository.findByBuyerOpenid(buyerOpenid, pageable);
		//Page对象的getContent()方法将分页的数据转换为List集合
		List<OrderDTO> orderDTOList = OrderMaster2OrderDTOConverter.convert(orderMasterPage.getContent());
		//Page接口PageImpl实现类
		Page<OrderDTO> PageOrderDTODao = new PageImpl<OrderDTO>(orderDTOList,pageable,orderMasterPage.getTotalElements());
		return PageOrderDTODao;
	}

	@Override
	@Transactional
	public OrderDTO cancel(OrderDTO orderDTO) {
		OrderMaster orderMaster = new OrderMaster();
		//判断订单状态
		if(!orderDTO.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())) {//只有新订单才能取消
			log.error("【取消订单】订单状态不正确，orderId={},orderStatus={}",orderDTO.getOrderId(),orderDTO.getOrderStatus());
			throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
		}
		//修改订单状态
		orderDTO.setOrderStatus(OrderStatusEnum.CANCEL.getCode());//已取消
		BeanUtils.copyProperties(orderDTO, orderMaster);
		OrderMaster updateResult = orderMasterRepository.save(orderMaster);
		if(updateResult==null) {
			log.error("【取消订单】更新失败，orderMaster={}",orderMaster);
			throw new SellException(ResultEnum.ORDER_UPDATE_FAIL);
		}
		//返回库存
		if(CollectionUtils.isEmpty(orderDTO.getOrderDetailList())) {
			log.error("【取消订单】订单中无商品详情，orderDTO={}",orderDTO);
			throw new SellException(ResultEnum.ORDER_DETAIL_EMPTY);//订单详情为空
		}
		List<CartDTO> cartDTOList = orderDTO.getOrderDetailList().stream().map(e ->
			new CartDTO(e.getProductId(),e.getProductQuantity())
				).collect(Collectors.toList());
		//加库存
		productService.increaseStock(cartDTOList);
		//如果已支付，需要退款
		if(orderDTO.getPayStatus().equals(PayStatusEnum.SUCCESS.getCode())) {
			//TODO
		}
		return null;
	}

	@Override
	@Transactional
	public OrderDTO finish(OrderDTO orderDTO) {
		
		//判断订单状态
		if(!orderDTO.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())) {//只有新订单才能取消
			log.error("【完结订单】订单状态不正确，orderId={},orderStatus={}",orderDTO.getOrderId(),orderDTO.getOrderStatus());
			throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
		}
		//修改订单状态
		orderDTO.setOrderStatus(OrderStatusEnum.FINISHED.getCode());//完结
		OrderMaster orderMaster = new OrderMaster();
		BeanUtils.copyProperties(orderDTO, orderMaster);
		OrderMaster updateResult = orderMasterRepository.save(orderMaster);
		if(updateResult==null) {
			log.error("【完结订单】更新失败，orderMaster={}",orderMaster);
			throw new SellException(ResultEnum.ORDER_UPDATE_FAIL);
		}
		
		return orderDTO;
	}

	@Override
	@Transactional
	public OrderDTO paid(OrderDTO orderDTO) {
		//判断订单状态
		if(!orderDTO.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())) {//只有新订单才能取消
			log.error("【订单支付成功】订单状态不正确，orderId={},orderStatus={}",orderDTO.getOrderId(),orderDTO.getOrderStatus());
			throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
		}
		//判断支付状态
		if(!orderDTO.getPayStatus().equals(PayStatusEnum.WAIT.getCode())) {
			log.error("【订单支付成功】支付状态不正确，orderDTO={}",orderDTO);
			throw new SellException(ResultEnum.ORDER_PAY_STATUS_ERROR);
		}
		//修改支付状态
		orderDTO.setPayStatus(PayStatusEnum.SUCCESS.getCode());//支付成功
		OrderMaster orderMaster = new OrderMaster();
		BeanUtils.copyProperties(orderDTO, orderMaster);
		OrderMaster updateResult = orderMasterRepository.save(orderMaster);
		if(updateResult==null) {
			log.error("【订单支付订单】更新失败，orderMaster={}",orderMaster);
			throw new SellException(ResultEnum.ORDER_UPDATE_FAIL);
		}
		return orderDTO;
	}

	@Override
	public Page<OrderDTO> findList(Pageable pageable) {
		 Page<OrderMaster> orderMasterPage = orderMasterRepository.findAll(pageable);

	        List<OrderDTO> orderDTOList = OrderMaster2OrderDTOConverter.convert(orderMasterPage.getContent());

	        return new PageImpl<>(orderDTOList, pageable, orderMasterPage.getTotalElements());
	}

	

}
