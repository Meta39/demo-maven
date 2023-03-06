/*
 Navicat MySQL Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 80023
 Source Host           : localhost:3306
 Source Schema         : demo

 Target Server Type    : MySQL
 Target Server Version : 80023
 File Encoding         : 65001

 Date: 20/05/2022 17:28:17
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for dept
-- ----------------------------
DROP TABLE IF EXISTS `dept`;
CREATE TABLE `dept`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '部门ID',
  `p_id` bigint NULL DEFAULT NULL COMMENT '父部门ID',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '部门名称',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  `creator` bigint NULL DEFAULT NULL COMMENT '创建者',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `name`(`name`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '部门' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of dept
-- ----------------------------
INSERT INTO `dept` VALUES (1, 0, '全部部门', '全部部门', 0, '2022-05-18 16:34:41');
INSERT INTO `dept` VALUES (2, 1, '研发部门', '研发部门', 1, '2022-05-19 09:07:14');
INSERT INTO `dept` VALUES (3, 1, '财务部门', '财务部门', 1, '2022-05-19 09:10:10');

-- ----------------------------
-- Table structure for menu
-- ----------------------------
DROP TABLE IF EXISTS `menu`;
CREATE TABLE `menu`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '菜单ID',
  `p_id` bigint NULL DEFAULT NULL COMMENT '父菜单ID，存储导航栏层级关系',
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '菜单名称，与 Vue 路由中的 name 属性对应',
  `name_zh` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '菜单中文名称，用于渲染导航栏（菜单）界面',
  `path` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '与 Vue 路由中的 path 对应，即地址路径',
  `icon` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '菜单icon，element 图标类名，用于渲染菜单名称前的小图标',
  `component` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '组件名，用于解析路由对应的组件',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  `creator` bigint NULL DEFAULT NULL COMMENT '创建人ID',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '菜单（运维谨慎用超级用户去新增、修改、删除）' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of menu
-- ----------------------------
INSERT INTO `menu` VALUES (1, 0, '', '全部菜单', NULL, NULL, NULL, NULL, 1, '2022-05-18 16:59:05');
INSERT INTO `menu` VALUES (2, 1, '', '用户中心', NULL, NULL, NULL, NULL, 1, '2022-05-18 16:59:19');
INSERT INTO `menu` VALUES (3, 2, '', '新增用户', NULL, NULL, NULL, NULL, 1, '2022-05-18 16:59:46');
INSERT INTO `menu` VALUES (4, 2, '', '修改用户', NULL, NULL, NULL, NULL, 1, '2022-05-18 17:00:01');
INSERT INTO `menu` VALUES (5, 2, '', '查询用户', NULL, NULL, NULL, NULL, 1, '2022-05-18 17:00:20');
INSERT INTO `menu` VALUES (6, 2, '', '删除用户', NULL, NULL, NULL, NULL, 1, '2022-05-18 17:00:25');

-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '角色ID',
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '角色名称',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  `disabled` tinyint NULL DEFAULT 0 COMMENT '禁用：0否，1是',
  `creator` bigint NULL DEFAULT NULL COMMENT '创建人ID',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '角色' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of role
-- ----------------------------
INSERT INTO `role` VALUES (1, '超级管理员', '超级管理员', 0, 0, '2022-05-18 17:01:42');
INSERT INTO `role` VALUES (2, '总经理', '总经理', 0, 1, '2022-05-19 09:06:40');

-- ----------------------------
-- Table structure for role_menu
-- ----------------------------
DROP TABLE IF EXISTS `role_menu`;
CREATE TABLE `role_menu`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '角色菜单ID',
  `role_id` bigint NULL DEFAULT NULL COMMENT '角色ID',
  `menu_id` bigint NULL DEFAULT NULL COMMENT '菜单ID',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `role_id`(`role_id`) USING BTREE,
  INDEX `menu_id`(`menu_id`) USING BTREE,
  CONSTRAINT `role_menu_ibfk_1` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`) ON DELETE CASCADE ON UPDATE RESTRICT,
  CONSTRAINT `role_menu_ibfk_2` FOREIGN KEY (`menu_id`) REFERENCES `menu` (`id`) ON DELETE CASCADE ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 11 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '角色关联菜单' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of role_menu
-- ----------------------------
INSERT INTO `role_menu` VALUES (1, 2, 2);
INSERT INTO `role_menu` VALUES (2, 2, 3);
INSERT INTO `role_menu` VALUES (3, 2, 4);
INSERT INTO `role_menu` VALUES (4, 2, 5);
INSERT INTO `role_menu` VALUES (10, 1, 2);

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '用户ID（唯一）',
  `name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户名（唯一）',
  `password` varchar(24) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'MD5密码',
  `salt` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'UUID盐（前端校验ID或账号成功时返回给前端）',
  `public_key` varchar(216) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '公钥',
  `private_key` varchar(848) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '私钥',
  `phone` varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '手机号',
  `e_mail` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '邮箱地址',
  `disabled` tinyint NULL DEFAULT 0 COMMENT '禁用：0否，1是',
  `deleted` tinyint NULL DEFAULT 0 COMMENT '删除：0否，1是',
  `creator` bigint NULL DEFAULT NULL COMMENT '创建人',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `last_login_time` datetime NULL DEFAULT NULL COMMENT '最后登录时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `name`(`name`) USING BTREE COMMENT '用户名唯一'
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1, 'root', '6VRr+drdRLG5takDowCS7A==', 'c9d2b671064a409bb8d8d713ecc1287e', 'MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCbrS2k1SugvfHxxtLuO2m+7LPLVrBvX4Blg0ORZ+HiOd+DFNCAR7rzv4eGX8JvOvHmZFOp2ouVnnQo6RJY7v7qWxKAw4bN4cb3PUbOO7tZFDVjBrcdyxGav2z3SoxrBxJ4IOvQ68n+IxSHow/Uk8JpWTMCUYWaftCYMfl7YFeMvQIDAQAB', 'MIICdQIBADANBgkqhkiG9w0BAQEFAASCAl8wggJbAgEAAoGBAJutLaTVK6C98fHG0u47ab7ss8tWsG9fgGWDQ5Fn4eI534MU0IBHuvO/h4Zfwm868eZkU6nai5WedCjpElju/upbEoDDhs3hxvc9Rs47u1kUNWMGtx3LEZq/bPdKjGsHEngg69Dryf4jFIejD9STwmlZMwJRhZp+0Jgx+XtgV4y9AgMBAAECgYAiQqeT6hIS6xqPAhmzskGgcglTi72ClTr1nEDHhFwy5FerSm5kfOTI9fLGgNaSkh9ge93puJ4VjGy2AC04g0h4Q67YqjGTvLT6jWsmkjmME+fkZhHSgYrD8CAEWLe7znBgK2yjSOo2OBpoVrjJO5Il+v4sMFhY7RHGYRcekuWVxQJBAPC7vMzDTzlusLd29NX9CdjKWwLAZUfq9IjClvrkJtzM0ik3LMvWXb8EsgE86n+YaBn5u7mcuerWA09jdde0c7cCQQCljI7NvYzH9it3LITnS1vclVu482QyMOZhMadrikvCQdbRTJ3roqItj1s2RyNwRt+Xb4hT/agyzleQLy/46ssrAkBTfimLMyolFzj/SKi+Fxb1M6nBGI2IFftd+2918O9xoTA6z0IgKGc3Ox+pwkDqpCdm6vCq/aqVwkg/npVejN2dAkAVHNXhezkHD2f/rNfOP5gZi5rE/ZVbqUzYPGLjniGHRqpEywag1H7mXI//Xhcw7Hj7cSfrvj/DKvmT09DEZ/09AkAs9I86lGCPPVXSg9CHl4o6NZ1tjmPcu5+0aXbJ9gl6FraIxoGUeOGa0xyLhJ+rXvM57Z7i/pDPbYRmdAgJ0/YS', '12345678910', '53995537@qq.com', 0, 0, 0, '超级用户', '2022-05-20 17:20:00', '2022-05-18 16:33:25');
INSERT INTO `user` VALUES (2, 'x', '6VRr+drdRLG5takDowCS7A==', 'c9d2b671064a409bb8d8d713ecc1287e', 'MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCbrS2k1SugvfHxxtLuO2m+7LPLVrBvX4Blg0ORZ+HiOd+DFNCAR7rzv4eGX8JvOvHmZFOp2ouVnnQo6RJY7v7qWxKAw4bN4cb3PUbOO7tZFDVjBrcdyxGav2z3SoxrBxJ4IOvQ68n+IxSHow/Uk8JpWTMCUYWaftCYMfl7YFeMvQIDAQAB', 'MIICdQIBADANBgkqhkiG9w0BAQEFAASCAl8wggJbAgEAAoGBAJutLaTVK6C98fHG0u47ab7ss8tWsG9fgGWDQ5Fn4eI534MU0IBHuvO/h4Zfwm868eZkU6nai5WedCjpElju/upbEoDDhs3hxvc9Rs47u1kUNWMGtx3LEZq/bPdKjGsHEngg69Dryf4jFIejD9STwmlZMwJRhZp+0Jgx+XtgV4y9AgMBAAECgYAiQqeT6hIS6xqPAhmzskGgcglTi72ClTr1nEDHhFwy5FerSm5kfOTI9fLGgNaSkh9ge93puJ4VjGy2AC04g0h4Q67YqjGTvLT6jWsmkjmME+fkZhHSgYrD8CAEWLe7znBgK2yjSOo2OBpoVrjJO5Il+v4sMFhY7RHGYRcekuWVxQJBAPC7vMzDTzlusLd29NX9CdjKWwLAZUfq9IjClvrkJtzM0ik3LMvWXb8EsgE86n+YaBn5u7mcuerWA09jdde0c7cCQQCljI7NvYzH9it3LITnS1vclVu482QyMOZhMadrikvCQdbRTJ3roqItj1s2RyNwRt+Xb4hT/agyzleQLy/46ssrAkBTfimLMyolFzj/SKi+Fxb1M6nBGI2IFftd+2918O9xoTA6z0IgKGc3Ox+pwkDqpCdm6vCq/aqVwkg/npVejN2dAkAVHNXhezkHD2f/rNfOP5gZi5rE/ZVbqUzYPGLjniGHRqpEywag1H7mXI//Xhcw7Hj7cSfrvj/DKvmT09DEZ/09AkAs9I86lGCPPVXSg9CHl4o6NZ1tjmPcu5+0aXbJ9gl6FraIxoGUeOGa0xyLhJ+rXvM57Z7i/pDPbYRmdAgJ0/YS', 'x', 'x@x.com', 0, 0, 1, '测试用户', '2022-05-19 09:09:07', NULL);
INSERT INTO `user` VALUES (3, 'a', 'a', NULL, NULL, NULL, 'a', 'a@a.cn', 0, 0, 1, '运维用户', '2022-05-19 09:12:06', NULL);
INSERT INTO `user` VALUES (4, 'fu', 'fu', NULL, NULL, NULL, NULL, NULL, 0, 0, NULL, NULL, '2022-05-19 16:46:14', NULL);

-- ----------------------------
-- Table structure for user_dept
-- ----------------------------
DROP TABLE IF EXISTS `user_dept`;
CREATE TABLE `user_dept`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '用户关联部门ID',
  `user_id` bigint NOT NULL COMMENT '用户ID',
  `dept_id` bigint NOT NULL COMMENT '部门ID',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `user_id`(`user_id`) USING BTREE,
  INDEX `dept_id`(`dept_id`) USING BTREE,
  CONSTRAINT `user_dept_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE RESTRICT,
  CONSTRAINT `user_dept_ibfk_2` FOREIGN KEY (`dept_id`) REFERENCES `dept` (`id`) ON DELETE CASCADE ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户关联部门' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user_dept
-- ----------------------------
INSERT INTO `user_dept` VALUES (1, 2, 2);
INSERT INTO `user_dept` VALUES (2, 3, 3);
INSERT INTO `user_dept` VALUES (3, 2, 3);

-- ----------------------------
-- Table structure for user_role
-- ----------------------------
DROP TABLE IF EXISTS `user_role`;
CREATE TABLE `user_role`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '用户角色ID',
  `user_id` bigint NULL DEFAULT NULL COMMENT '用户ID',
  `role_id` bigint NULL DEFAULT NULL COMMENT '角色ID',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `user_id`(`user_id`) USING BTREE,
  INDEX `role_id`(`role_id`) USING BTREE,
  CONSTRAINT `user_role_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE RESTRICT,
  CONSTRAINT `user_role_ibfk_2` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`) ON DELETE CASCADE ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户关联角色' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user_role
-- ----------------------------
INSERT INTO `user_role` VALUES (1, 2, 2);
INSERT INTO `user_role` VALUES (3, 3, 2);
INSERT INTO `user_role` VALUES (5, 2, 1);

SET FOREIGN_KEY_CHECKS = 1;
