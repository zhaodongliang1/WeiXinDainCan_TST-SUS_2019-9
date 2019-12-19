package com.zdl.converter;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;

import com.zdl.dto.OrderDTO;
import com.zdl.entity.OrderMaster;
/**
 * 转换器，orderMaster转为OrderDTO
 * @author DELL
 *
 */
public class OrderMaster2OrderDTOConverter {
	//单个客户信息放进订单总数居
	public static OrderDTO convert(OrderMaster orderMaster) {
		OrderDTO orderDTO = new OrderDTO();
		BeanUtils.copyProperties(orderMaster, orderDTO);
		return orderDTO;
	}
	//多个客户信息放进订单总数居，返回订单总数居集合
	public static List<OrderDTO> convert(List<OrderMaster> orderMaList){
		
		return orderMaList.stream().map(e ->
			convert(e)
				).collect(Collectors.toList());
	}
}
