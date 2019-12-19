/*
Navicat MySQL Data Transfer

Source Server         : root
Source Server Version : 50722
Source Host           : 127.0.0.1:3306
Source Database       : weixin

Target Server Type    : MYSQL
Target Server Version : 50722
File Encoding         : 65001

Date: 2019-12-19 11:12:29
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for order_detail
-- ----------------------------
DROP TABLE IF EXISTS `order_detail`;
CREATE TABLE `order_detail` (
  `detail_id` varchar(32) NOT NULL,
  `order_id` varchar(32) NOT NULL,
  `product_id` varchar(32) NOT NULL,
  `product_name` varchar(64) NOT NULL COMMENT '商品名称',
  `product_price` decimal(8,2) NOT NULL COMMENT '商品价格',
  `product_icon` varchar(512) DEFAULT NULL COMMENT '小图',
  `product_quantity` int(11) NOT NULL COMMENT '商品数量',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`detail_id`),
  KEY `idx_order_id` (`order_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='订单详情';

-- ----------------------------
-- Records of order_detail
-- ----------------------------
INSERT INTO `order_detail` VALUES ('1234567810', '1234567', '123456', '皮蛋粥', '3.20', 'https://s1.cdn.xiangha.com/caipu/201606/2518/25182941390.jpg/NjAwX2MyXzQwMA.webp', '3', '2019-09-10 09:27:11', '2019-09-18 20:01:14');
INSERT INTO `order_detail` VALUES ('1568165103413823617', '1568165103248937139', '123456', '皮蛋粥', '3.20', 'https://s1.cdn.xiangha.com/caipu/201606/2518/25182941390.jpg/NjAwX2MyXzQwMA.webp', '1', '2019-09-11 09:25:03', '2019-09-18 20:01:19');
INSERT INTO `order_detail` VALUES ('1568165103436773845', '1568165103248937139', '123457', '皮皮虾', '5.00', 'https://img02.sogoucdn.com/net/a/04/link?url=https%3A%2F%2Fi02piccdn.sogoucdn.com%2F3521c03e293b78a1&appid=122', '2', '2019-09-11 09:25:03', '2019-09-18 20:01:01');

-- ----------------------------
-- Table structure for order_master
-- ----------------------------
DROP TABLE IF EXISTS `order_master`;
CREATE TABLE `order_master` (
  `order_id` varchar(32) NOT NULL,
  `buyer_name` varchar(64) NOT NULL COMMENT '买家名字',
  `buyer_phone` varchar(32) NOT NULL COMMENT '买家电话',
  `buyer_address` varchar(128) NOT NULL COMMENT '买家地址',
  `buyer_openid` varchar(64) NOT NULL COMMENT '买家微信openid',
  `order_amount` decimal(8,2) NOT NULL COMMENT '订单总金额',
  `order_status` tinyint(3) NOT NULL DEFAULT '0' COMMENT '订单状态，默认为0新下单',
  `pay_status` tinyint(3) NOT NULL DEFAULT '0' COMMENT '支付状态，默认为0未支付',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`order_id`),
  KEY `idx_buyer_openid` (`buyer_openid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='订单表';

-- ----------------------------
-- Records of order_master
-- ----------------------------
INSERT INTO `order_master` VALUES ('1234567', '师兄', '12345678912', '幕课网', '1101110', '10.00', '2', '1', '2019-09-10 09:19:59', '2019-09-18 18:53:51');
INSERT INTO `order_master` VALUES ('1568165103248937139', '廖师兄', '123456789012', '幕课网', '1101110', '13.20', '0', '0', '2019-09-11 09:25:03', '2019-09-11 09:25:03');

-- ----------------------------
-- Table structure for product_category
-- ----------------------------
DROP TABLE IF EXISTS `product_category`;
CREATE TABLE `product_category` (
  `category_id` int(11) NOT NULL AUTO_INCREMENT,
  `category_name` varchar(64) NOT NULL COMMENT '类目名字',
  `category_type` int(11) NOT NULL COMMENT '类目编号',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`category_id`),
  UNIQUE KEY `uqe_category_type` (`category_type`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8 COMMENT='类目表';

-- ----------------------------
-- Records of product_category
-- ----------------------------
INSERT INTO `product_category` VALUES ('1', '皮皮虾', '1', '2019-08-25 18:11:50', '2019-08-25 18:11:50');
INSERT INTO `product_category` VALUES ('3', '男生最爱', '4', '2019-08-27 13:48:50', '2019-08-27 13:48:50');
INSERT INTO `product_category` VALUES ('5', '女生最爱', '3', '2019-08-27 13:59:27', '2019-08-27 13:59:27');
INSERT INTO `product_category` VALUES ('13', '皮蛋粥', '2', '2019-08-28 17:47:03', '2019-08-28 20:54:08');
INSERT INTO `product_category` VALUES ('14', '男生专享', '10', '2019-08-28 18:21:06', '2019-08-28 18:21:06');
INSERT INTO `product_category` VALUES ('16', '八宝粥', '9', '2019-09-18 18:03:10', '2019-09-18 18:03:10');

-- ----------------------------
-- Table structure for product_info
-- ----------------------------
DROP TABLE IF EXISTS `product_info`;
CREATE TABLE `product_info` (
  `product_id` varchar(32) NOT NULL,
  `product_name` varchar(64) NOT NULL COMMENT '商品名称',
  `product_price` decimal(8,2) NOT NULL COMMENT '单价',
  `product_stock` int(11) NOT NULL COMMENT '库存',
  `product_description` varchar(64) DEFAULT NULL COMMENT '描述',
  `product_icon` varchar(512) DEFAULT NULL COMMENT '小图',
  `category_type` int(11) NOT NULL COMMENT '类目编号',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `product_status` int(2) DEFAULT '0' COMMENT '0上架1下架',
  PRIMARY KEY (`product_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='商品表';

-- ----------------------------
-- Records of product_info
-- ----------------------------
INSERT INTO `product_info` VALUES ('123456', '皮蛋粥', '3.20', '102', '很好喝的粥', 'https://s1.cdn.xiangha.com/caipu/201606/2518/25182941390.jpg/NjAwX2MyXzQwMA.webp', '1', '2019-09-10 09:01:27', '2019-09-18 18:47:59', '0');
INSERT INTO `product_info` VALUES ('123457', '皮皮虾', '5.00', '98', '很好吃的虾', 'https://img02.sogoucdn.com/net/a/04/link?url=https%3A%2F%2Fi02piccdn.sogoucdn.com%2F3521c03e293b78a1&appid=122', '1', '2019-09-10 09:20:53', '2019-09-18 18:49:25', '0');

-- ----------------------------
-- Table structure for seller_info
-- ----------------------------
DROP TABLE IF EXISTS `seller_info`;
CREATE TABLE `seller_info` (
  `sellerid` varchar(50) NOT NULL,
  `username` varchar(50) NOT NULL,
  `password` varchar(50) NOT NULL,
  `openid` varchar(50) NOT NULL,
  PRIMARY KEY (`sellerid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of seller_info
-- ----------------------------
INSERT INTO `seller_info` VALUES ('1', '赵栋梁', '123456', 'www');
