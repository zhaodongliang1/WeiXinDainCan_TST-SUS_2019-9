
package com.zdl.test.impl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.zdl.dto.OrderDTO;
import com.zdl.service.OrderService;
import com.zdl.service.impl.PushMessageServiceImpl;

/**
 * Created by 廖师兄 2017-07-30 22:19
 */
@RunWith(SpringRunner.class)

@SpringBootTest
public class PushMessageServiceImplTest {

	@Autowired
	private PushMessageServiceImpl pushMessageService;

	@Autowired
	private OrderService orderService;

	@Test
	public void orderStatus() throws Exception {

		OrderDTO orderDTO = orderService.findOne("1499097346204378750");
		pushMessageService.orderstatus(orderDTO);
	}

}
