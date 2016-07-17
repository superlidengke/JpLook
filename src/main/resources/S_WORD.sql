/*
Navicat MySQL Data Transfer

Source Server         : localMysql
Source Server Version : 50523
Source Host           : localhost:3306
Source Database       : jpword

Target Server Type    : MYSQL
Target Server Version : 50523
File Encoding         : 65001

Date: 2015-08-31 14:37:32
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `s_word`
-- ----------------------------
DROP TABLE IF EXISTS `s_word`;
CREATE TABLE `s_word` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `pronounce` varchar(255) DEFAULT NULL COMMENT '假名',
  `roman` varchar(255) DEFAULT NULL COMMENT '罗马音',
  `name` varchar(255) NOT NULL COMMENT '单词的汉字或无汉字时的平、片假名',
  `tone` varchar(255) DEFAULT NULL COMMENT '音调',
  `meaning` varchar(5000) DEFAULT NULL COMMENT '文本类型的解释',
  `htmlMean` varchar(10000) DEFAULT NULL COMMENT 'hmtl的解释',
  PRIMARY KEY (`id`),
  INDEX `WORD_NAME_INDEX` (`name`),
  INDEX `PRONOU_INDEX` (`pronounce`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='适用ajax取词，划译等，只有基本部队解释，无例句';

-- ----------------------------
-- Records of s_word
-- ----------------------------
