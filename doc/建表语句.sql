/*
 Navicat Premium Data Transfer

 Source Server         : yxa
 Source Server Type    : MySQL
 Source Server Version : 50716
 Source Host           : 47.101.37.124:3306
 Source Schema         : test2

 Target Server Type    : MySQL
 Target Server Version : 50716
 File Encoding         : 65001

 Date: 23/05/2019 15:50:49
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for processinfo
-- ----------------------------
DROP TABLE IF EXISTS `processinfo`;
CREATE TABLE `processinfo`  (
  `processID` int(10) NOT NULL AUTO_INCREMENT COMMENT '流程id',
  `currentTaskType` int(1) UNSIGNED ZEROFILL NOT NULL COMMENT '当前任务类型 0需求 1开发 2测试 3bug',
  `devTaskID` int(10) NULL DEFAULT NULL COMMENT '开发任务id',
  `devUserID` int(10) NULL DEFAULT NULL COMMENT '开发人员id',
  `testTaskID` int(10) NULL DEFAULT NULL COMMENT '测试任务id',
  `testUserID` int(10) NULL DEFAULT NULL COMMENT '测试人员id',
  `isComplete` int(1) NOT NULL COMMENT '是否完成 0未完成 1完成',
  `createUserID` int(10) NOT NULL COMMENT '创始人id',
  `fileUrl` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '文件地址(文件夹 流程id;文件 文件名+上传人id)',
  PRIMARY KEY (`processID`)
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of processinfo
-- ----------------------------
INSERT INTO `processinfo` VALUES (1, 0, NULL, 11, NULL, NULL, 0, 999, '');
INSERT INTO `processinfo` VALUES (2, 1, 1, 11, NULL, NULL, 0, 999, '');
INSERT INTO `processinfo` VALUES (3, 1, 2, 11, NULL, NULL, 0, 999, '');
INSERT INTO `processinfo` VALUES (4, 2, NULL, 11, 21, 21, 0, 999, '');
INSERT INTO `processinfo` VALUES (5, 3, NULL, 11, 22, 21, 0, 999, '');
INSERT INTO `processinfo` VALUES (6, 3, NULL, 11, 23, 21, 1, 999, '');

-- ----------------------------
-- Table structure for reportinfo
-- ----------------------------
DROP TABLE IF EXISTS `reportinfo`;
CREATE TABLE `reportinfo`  (
  `reportID` int(10) NOT NULL AUTO_INCREMENT COMMENT '提问编号',
  `reportTarget` int(10) NULL DEFAULT NULL COMMENT '提问对象',
  `reportType` int(1) NOT NULL COMMENT '提问类型(0仅组长 1 公开)',
  `reportContent` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '提问内容',
  `createUser` int(10) NOT NULL COMMENT '提问发起人',
  `answer` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '回答',
  `taskID` int(10) NOT NULL COMMENT '任务编号',
  PRIMARY KEY (`reportID`)
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of reportinfo
-- ----------------------------
INSERT INTO `reportinfo` VALUES (1, NULL, 1, 'String类型如何cast成int', 999, 'Interger.parse(str)', 1);
INSERT INTO `reportinfo` VALUES (2, NULL, 0, '老板能不能给个及格分', 999, '不能', 11);
INSERT INTO `reportinfo` VALUES (3, NULL, 0, '离开学校为什么会这么难过?', 999, '因为答辩不通过', 3);
INSERT INTO `reportinfo` VALUES (4, NULL, 1, '长期写代码到底会不会秃头?', 999, '', 4);

-- ----------------------------
-- Table structure for taskinfo
-- ----------------------------
DROP TABLE IF EXISTS `taskinfo`;
CREATE TABLE `taskinfo`  (
  `taskID` int(10) NOT NULL AUTO_INCREMENT COMMENT '任务id',
  `taskType` int(1) NOT NULL COMMENT '任务类型 0需求 1开发 2测试 3bug',
  `processID` int(10) NOT NULL COMMENT '流程id',
  `leaderID` int(10) NOT NULL COMMENT '领导id',
  `workerIDs` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '执行者ids(用逗号拼接)',
  `taskState` int(1) NOT NULL COMMENT ' 任务状态(0 已建议 1活动的 2已解决 3已关闭)',
  `taskLevel` int(1) NOT NULL COMMENT '任务级别(0 轻 1中 2重)',
  `reportIDs` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '提问编号(用逗号隔开)',
  PRIMARY KEY (`taskID`)
) ENGINE = InnoDB AUTO_INCREMENT = 12 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of taskinfo
-- ----------------------------
INSERT INTO `taskinfo` VALUES (1, 0, 2, 11, '', 0, 2, '3');
INSERT INTO `taskinfo` VALUES (2, 0, 1, 1, '2,12', 2, 0, '1');
INSERT INTO `taskinfo` VALUES (3, 2, 1, 21, '22', 3, 0, '4');
INSERT INTO `taskinfo` VALUES (4, 3, 2, 21, '22,12', 1, 2, '5');
INSERT INTO `taskinfo` VALUES (11, 1, 1, 11, '12', 1, 1, '2');

-- ----------------------------
-- Table structure for userinfo
-- ----------------------------
DROP TABLE IF EXISTS `userinfo`;
CREATE TABLE `userinfo`  (
  `userID` int(10) NOT NULL AUTO_INCREMENT COMMENT '用户编号',
  `roleID` int(10) NOT NULL COMMENT '角色编号  0需求人员,1开发人员 2测试人员 3超级管理员',
  `isLeader` int(1) NOT NULL COMMENT '是否为领导 0不是 1是',
  `password` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '密码 最多20位 md5加密',
  `userName` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户名,显示名称',
  PRIMARY KEY (`userID`) 
) ENGINE = InnoDB AUTO_INCREMENT = 1000 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of userinfo
-- ----------------------------
INSERT INTO `userinfo` VALUES (1, 0, 1, '49ba59abbe56e057', '需求(组长)');
INSERT INTO `userinfo` VALUES (2, 0, 0, '49ba59abbe56e057', '需求甲');
INSERT INTO `userinfo` VALUES (11, 1, 1, '49ba59abbe56e057', '开发(组长)');
INSERT INTO `userinfo` VALUES (12, 1, 0, '49ba59abbe56e057', '开发甲');
INSERT INTO `userinfo` VALUES (21, 2, 1, '49ba59abbe56e057', '测试(组长)');
INSERT INTO `userinfo` VALUES (22, 2, 0, '49ba59abbe56e057', '测试甲');
INSERT INTO `userinfo` VALUES (999, 3, 1, '49ba59abbe56e057', '超级管理员');

SET FOREIGN_KEY_CHECKS = 1;
