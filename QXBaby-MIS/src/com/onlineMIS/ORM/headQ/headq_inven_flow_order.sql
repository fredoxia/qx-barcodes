/*
Navicat MySQL Data Transfer

Source Server         : MyConn
Source Server Version : 50610
Source Host           : localhost:3306
Source Database       : pysdb

Target Server Type    : MYSQL
Target Server Version : 50610
File Encoding         : 65001

Date: 2019-06-03 17:31:45
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `headq_inven_flow_order`
-- ----------------------------
DROP TABLE IF EXISTS `headq_inven_flow_order`;
CREATE TABLE `headq_inven_flow_order` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `order_date` datetime NOT NULL,
  `order_type` smallint(6) NOT NULL,
  `comment` text NOT NULL,
  `creator` int(11) NOT NULL,
  `status` tinyint(4) NOT NULL,
  `total_inventory_Q` int(11) NOT NULL,
  `total_quantity_diff` int(11) NOT NULL,
  `total_quantity` smallint(6) NOT NULL,
  `total_cost` double NOT NULL DEFAULT '0',
  `total_whole_price` double NOT NULL,
  PRIMARY KEY (`id`),
  KEY `orderDateIndex` (`order_date`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of headq_inven_flow_order
-- ----------------------------

-- ----------------------------
-- Table structure for `headq_inven_flow_order_product`
-- ----------------------------
DROP TABLE IF EXISTS `headq_inven_flow_order_product`;
CREATE TABLE `headq_inven_flow_order_product` (
  `order_id` int(11) NOT NULL,
  `product_id` int(11) NOT NULL,
  `quantity` smallint(6) NOT NULL,
  `comment` varchar(20) NOT NULL,
  `index_num` smallint(6) NOT NULL,
  `inventory_quantity` smallint(6) NOT NULL,
  `quantity_diff` smallint(6) NOT NULL,
  `whole_price` double NOT NULL DEFAULT '0',
  `total_whole_price` double NOT NULL DEFAULT '0',
  `cost` double NOT NULL,
  `total_cost` double NOT NULL,
  PRIMARY KEY (`order_id`,`product_id`),
  KEY `invenFlowOrderFk` (`order_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of headq_inven_flow_order_product
-- ----------------------------
