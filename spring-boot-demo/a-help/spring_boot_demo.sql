/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 80031
 Source Host           : localhost:3306
 Source Schema         : spring_boot_demo

 Target Server Type    : MySQL
 Target Server Version : 80031
 File Encoding         : 65001

 Date: 11/03/2023 17:53:56
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for authorize
-- ----------------------------
DROP TABLE IF EXISTS `authorize`;
CREATE TABLE `authorize`  (
  `id` int UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'id',
  `p_id` int UNSIGNED NOT NULL COMMENT 'parentId',
  `authorize_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '权限名称（后端授权）',
  `authorize_name_cn` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '权限名称中文（前端展示）',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `authorize_name`(`authorize_name` ASC) USING BTREE COMMENT '权限名称唯一索引'
) ENGINE = InnoDB AUTO_INCREMENT = 16 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '权限' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of authorize
-- ----------------------------
INSERT INTO `authorize` VALUES (1, 0, 'user', '用户管理');
INSERT INTO `authorize` VALUES (2, 1, 'user:insert', '新增用户');
INSERT INTO `authorize` VALUES (3, 1, 'user:select', '查询用户');
INSERT INTO `authorize` VALUES (4, 1, 'user:update', '修改用户');
INSERT INTO `authorize` VALUES (5, 1, 'user:delete', '删除用户');
INSERT INTO `authorize` VALUES (6, 0, 'role', '角色管理');
INSERT INTO `authorize` VALUES (7, 6, 'role:insert', '新增角色');
INSERT INTO `authorize` VALUES (8, 6, 'role:select', '查询角色');
INSERT INTO `authorize` VALUES (9, 6, 'role:update', '修改角色');
INSERT INTO `authorize` VALUES (10, 6, 'role:delete', '删除角色');
INSERT INTO `authorize` VALUES (11, 0, 'authorize', '权限管理');
INSERT INTO `authorize` VALUES (12, 11, 'authorize:insert', '新增权限');
INSERT INTO `authorize` VALUES (13, 11, 'authorize:select', '查询权限');
INSERT INTO `authorize` VALUES (14, 11, 'authorize:update', '修改权限');
INSERT INTO `authorize` VALUES (15, 11, 'authorize:delete', '删除权限');

-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role`  (
  `id` int UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'id',
  `role_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '角色名称',
  `is_ban` int NOT NULL COMMENT '禁用角色（0：否 1：是）',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `role_name`(`role_name` ASC) USING BTREE COMMENT '角色名称唯一索引'
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '角色' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of role
-- ----------------------------
INSERT INTO `role` VALUES (1, '超级管理员', 0, '2023-03-11 13:58:25', '2023-03-11 13:58:27');

-- ----------------------------
-- Table structure for role_authorize
-- ----------------------------
DROP TABLE IF EXISTS `role_authorize`;
CREATE TABLE `role_authorize`  (
  `id` int UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'id',
  `role_id` int UNSIGNED NOT NULL COMMENT '角色id',
  `authorize_id` int UNSIGNED NOT NULL COMMENT '权限id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '角色关联权限' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of role_authorize
-- ----------------------------

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` int UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'id',
  `username` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户名',
  `salt` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '盐',
  `pwd` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '密码',
  `nickname` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '昵称',
  `sex` int UNSIGNED NOT NULL COMMENT '性别（0：女 1：男）',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `is_ban` int UNSIGNED NOT NULL COMMENT '禁用（0：否 1：是）',
  `is_delete` int UNSIGNED NOT NULL COMMENT '删除（0：否 1：是）',
  `last_login_time` datetime NOT NULL COMMENT '最后登录时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `username`(`username` ASC) USING BTREE COMMENT 'username唯一索引'
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1, 'admin', 'salt', 'password', '超级管理员', 0, '2023-03-10 15:42:10', '2023-03-10 15:42:13', 0, 0, '2023-03-10 15:42:20');
INSERT INTO `user` VALUES (2, 'user', 'salt', 'user', '普通用户', 0, '2023-03-10 18:19:51', '2023-03-10 18:19:53', 0, 0, '2023-03-10 18:20:03');

-- ----------------------------
-- Table structure for user_role
-- ----------------------------
DROP TABLE IF EXISTS `user_role`;
CREATE TABLE `user_role`  (
  `id` int UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'id',
  `user_id` int UNSIGNED NOT NULL COMMENT '用户ID',
  `role_id` int UNSIGNED NOT NULL COMMENT '角色ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户关联角色' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of user_role
-- ----------------------------
INSERT INTO `user_role` VALUES (1, 1, 1);

SET FOREIGN_KEY_CHECKS = 1;
