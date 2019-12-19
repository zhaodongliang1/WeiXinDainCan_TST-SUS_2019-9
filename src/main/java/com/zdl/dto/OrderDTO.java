package com.zdl.dto;


import java.math.BigDecimal;
import java.util.Date;
import java.util.List;



import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.zdl.entity.OrderDetail;
import com.zdl.enums.OrderStatusEnum;
import com.zdl.enums.PayStatusEnum;
import com.zdl.util.EnumUtil;
import com.zdl.util.serializer.Date2LongSerializer;

import lombok.Data;
/**
 * 订单总数据，用于向订单列表和订单详情存放数据
 * @author DELL
 *
 */
@Data

public class OrderDTO {
	 /** 订单id. */
    private String orderId;

    /** 买家名字. */
    private String buyerName;

    /** 买家手机号. */
    private String buyerPhone;

    /** 买家地址. */
    private String buyerAddress;

    /** 买家微信Openid. */
    private String buyerOpenid;

    /** 订单总金额. */
    private BigDecimal orderAmount;

    /** 订单状态, 默认为0新下单. */
    private Integer orderStatus;

    /** 支付状态, 默认为0未支付. */
    private Integer payStatus;

    /** 创建时间. */
    //此注解用于属性或者getter方法上，用于在序列化时嵌入我们自定义的代码
    @JsonSerialize(using = Date2LongSerializer.class)
    private Date createTime;

    /** 更新时间. */
    @JsonSerialize(using = Date2LongSerializer.class)
    private Date updateTime;

    List<OrderDetail> orderDetailList;
    //注解用于属性或者方法上，生成json 时不生成age属性 
    @JsonIgnore
    public OrderStatusEnum getOrderStatusEnum() {
    	
        return EnumUtil.getByCode(orderStatus, OrderStatusEnum.class);
    }

    @JsonIgnore
    public PayStatusEnum getPayStatusEnum() {
        return EnumUtil.getByCode(payStatus, PayStatusEnum.class);
    }
}
