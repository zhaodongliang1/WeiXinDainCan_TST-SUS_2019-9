/*
 * package com.zdl.controller;
 * 
 * import org.springframework.web.bind.annotation.GetMapping; import
 * org.springframework.web.bind.annotation.RequestMapping; import
 * org.springframework.web.bind.annotation.RequestParam; import
 * org.springframework.web.bind.annotation.RestController; import
 * org.springframework.web.client.RestTemplate;
 * 
 * import lombok.extern.slf4j.Slf4j;
 *//**
	 * 接收信息并打印
	 * 
	 * @author DELL
	 *
	 *//*
		 * @RequestMapping("/weixin")
		 * 
		 * @RestController
		 * 
		 * @Slf4j public class WeixinController {
		 * 
		 * @GetMapping("/auth") public void auth(@RequestParam("code") String code) {
		 * log.info("进入auth方法。。。"); log.info("code={}",code); String url=""+code+"";
		 * RestTemplate restTemplate = new RestTemplate(); String response =
		 * restTemplate.getForObject(url,String.class);
		 * log.info("response={}",response); } }
		 */