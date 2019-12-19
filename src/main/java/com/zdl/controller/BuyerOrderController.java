package com.zdl.controller;

import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.zdl.VO.ResultVO;
import com.zdl.converter.OrderForm2OrderDTOConverter;
import com.zdl.dto.OrderDTO;
import com.zdl.enums.ResultEnum;
import com.zdl.exception.SellException;
import com.zdl.form.OrderForm;
import com.zdl.service.BuyerService;
import com.zdl.service.OrderService;
import com.zdl.util.ResultVOUtil;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/buyer/order")
@Slf4j
public class BuyerOrderController {
	@Autowired
	private OrderService orderService;
	@Autowired
	private BuyerService buyerService;

	// 创建订单
	@PostMapping("/create")
	public ResultVO<Map<String, String>> create(@Valid OrderForm orderForm, BindingResult bindingResult) {
		//订单参数验证
		if (bindingResult.hasErrors()) {
			log.error("【创建订单】参数不正确, orderForm={}", orderForm);
			throw new SellException(ResultEnum.PARAM_ERROR.getCode(),
					bindingResult.getFieldError().getDefaultMessage());
		}
		OrderDTO orderDTO = OrderForm2OrderDTOConverter.convert(orderForm);
		//订单详情为空==true
		if (CollectionUtils.isEmpty(orderDTO.getOrderDetailList())) {
            log.error("【创建订单】购物车不能为空");
            throw new SellException(ResultEnum.CART_EMPTY);//购物车为空
        }
		OrderDTO createResult = orderService.create(orderDTO);
		 Map<String, String> map = new HashMap<>();
		 map.put("orderId", createResult.getOrderId());
		return ResultVOUtil.success(map);
	}
	// 订单列表
	@GetMapping("/list")
	public ResultVO<OrderDTO> list(@RequestParam(value="openid",defaultValue = "1101110") String openid,
			@RequestParam(value="page",defaultValue = "0") Integer page,
			@RequestParam(value="size",defaultValue = "10") Integer size){
		
		if(StringUtils.isEmpty(openid)) 
		{
			 log.error("【查询订单列表】openid为空");
	         throw new SellException(ResultEnum.PARAM_ERROR);//参数不正确
		}
		PageRequest pageRequest = new PageRequest(page, size);
		Page<OrderDTO> orderDTOPage = orderService.findList(openid,pageRequest);
		//getContent()返回list集合
		return ResultVOUtil.success(orderDTOPage.getContent());
	}
	// 订单详情
	@GetMapping("/detail")
    public ResultVO<OrderDTO> detail(@RequestParam("openid") String openid,
                                     @RequestParam("orderId") String orderId) {
        OrderDTO orderDTO = buyerService.findOrderOne(openid, orderId);
        return ResultVOUtil.success(orderDTO);
    }
	// 取消订单
	@PostMapping("/cancel")
    public ResultVO cancel(@RequestParam("openid") String openid,
                           @RequestParam("orderId") String orderId) {
        buyerService.cancelOrder(openid, orderId);
        return ResultVOUtil.success();
    }
}
