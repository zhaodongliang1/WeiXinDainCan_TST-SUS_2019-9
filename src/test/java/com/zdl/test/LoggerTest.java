package com.zdl.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.zdl.SellApplication;

import lombok.extern.slf4j.Slf4j;

@RunWith(SpringRunner.class)
@SpringBootTest(classes=SellApplicationTests.class)
@Slf4j
public class LoggerTest {
	//private final Logger logger=LoggerFactory.getLogger(LoggerTest.class);
	@Test
	public void test() {
		// TODO Auto-generated method stub
		String name="imooc";
		String password="123456";
		log.debug("debug...");
		log.info("name:"+name+",password:"+password);
		log.info("name:{},password:{}",name,password);
		log.error("error...");
		log.warn("warn...");
	}
}
