package com.zdl.converter;

import java.util.ArrayList;
import java.util.List;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.zdl.dto.OrderDTO;
import com.zdl.entity.OrderDetail;
import com.zdl.enums.ResultEnum;
import com.zdl.exception.SellException;
import com.zdl.form.OrderForm;

import lombok.extern.slf4j.Slf4j;
/**
 * 转换器，orderDetail转为OrderDTO
 * @author DELL
 *
 */
@Slf4j
public class OrderForm2OrderDTOConverter {
	public static OrderDTO convert(OrderForm orderForm) {
		Gson gson=new Gson();
		OrderDTO orderDTO = new OrderDTO();
		orderDTO.setBuyerName(orderForm.getName());
		orderDTO.setBuyerAddress(orderForm.getAddress());
		orderDTO.setBuyerOpenid(orderForm.getOpenid());
		orderDTO.setBuyerPhone(orderForm.getPhone());
		List<OrderDetail> orderDetailList = new ArrayList<>();
		//item为string类型，将json字符串转换为List集合
		try {
			orderDetailList=gson.fromJson(orderForm.getItems(),
					new TypeToken<List<OrderDetail>>(){
			}.getType());
		} catch (Exception e) {
			log.error("【对象转换】错误, string={}", orderForm.getItems());
			throw new SellException(ResultEnum.PARAM_ERROR);//参数不正确
		}
		orderDTO.setOrderDetailList(orderDetailList);

        return orderDTO;
	}
}
