SET NAMES utf8;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for admini
-- ----------------------------
DROP TABLE IF EXISTS `admini`;
CREATE TABLE `admini`  (
  `a_id` int(0) NOT NULL,
  `a_password` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `a_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `a_gender` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `a_card` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `a_phone` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `a_email` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`a_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of admini
-- ----------------------------
INSERT INTO `admini` VALUES (22401717, '123456', 'admin', 'Male', '440111111111111111', '13544444444', '123@qq.com');

-- ----------------------------
-- Table structure for arrange
-- ----------------------------
DROP TABLE IF EXISTS `arrange`;
CREATE TABLE `arrange`  (
  `ar_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `ar_time` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `d_id` int(0) NULL DEFAULT NULL,
  PRIMARY KEY (`ar_id`) USING BTREE,
  INDEX `arTOd`(`d_id`) USING BTREE,
  CONSTRAINT `arTOd` FOREIGN KEY (`d_id`) REFERENCES `doctor` (`d_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of arrange
-- ----------------------------
INSERT INTO `arrange` VALUES ('10002023-07-27', '2023-07-27', 1000);
INSERT INTO `arrange` VALUES ('10002023-07-28', '2023-07-28', 1000);

-- ----------------------------
-- Table structure for bed
-- ----------------------------
DROP TABLE IF EXISTS `bed`;
CREATE TABLE `bed`  (
  `b_id` int(0) NOT NULL,
  `p_id` int(0) NULL DEFAULT NULL,
  `b_state` int(0) NULL DEFAULT NULL,
  `b_start` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `d_id` int(0) NULL DEFAULT NULL,
  `b_reason` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `version` int(0) NULL DEFAULT NULL,
  PRIMARY KEY (`b_id`) USING BTREE,
  INDEX `bTOp`(`p_id`) USING BTREE,
  INDEX `bTOd`(`d_id`) USING BTREE,
  CONSTRAINT `bTOd` FOREIGN KEY (`d_id`) REFERENCES `doctor` (`d_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `bTOp` FOREIGN KEY (`p_id`) REFERENCES `patient` (`p_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of bed
-- ----------------------------
INSERT INTO `bed` VALUES (10, -1, 0, NULL, -1, NULL, NULL);

-- ----------------------------
-- Table structure for checks
-- ----------------------------
DROP TABLE IF EXISTS `checks`;
CREATE TABLE `checks`  (
  `ch_id` int(0) NOT NULL AUTO_INCREMENT,
  `ch_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `ch_price` decimal(10, 2) NULL DEFAULT NULL,
  PRIMARY KEY (`ch_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 11 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of checks
-- ----------------------------
INSERT INTO `checks` VALUES (1, 'B-Ultrasound', 201.00);
INSERT INTO `checks` VALUES (2, 'CT', 435.00);
INSERT INTO `checks` VALUES (3, 'MRI', 653.00);
INSERT INTO `checks` VALUES (4, 'MRI', 534.00);
INSERT INTO `checks` VALUES (5, 'ECG', 634.00);
INSERT INTO `checks` VALUES (6, 'Color Doppler', 213.00);
INSERT INTO `checks` VALUES (7, 'Blood Routine', 434.00);
INSERT INTO `checks` VALUES (8, 'Liver Function', 543.00);
INSERT INTO `checks` VALUES (9, 'Blood Glucose', 434.00);
INSERT INTO `checks` VALUES (10, 'Thyroid', 434.00);
INSERT INTO `checks` VALUES (11, 'Vision', 50.00);

-- ----------------------------
-- Table structure for doctor
-- ----------------------------
DROP TABLE IF EXISTS `doctor`;
CREATE TABLE `doctor`  (
  `d_id` int(0) NOT NULL,
  `d_password` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `d_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `d_gender` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `d_phone` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `d_card` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `d_email` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `d_post` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `d_introduction` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `d_section` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `d_state` int(0) NOT NULL,
  `d_price` decimal(10, 2) NULL DEFAULT NULL,
  `d_people` int(0) NULL DEFAULT NULL,
  `d_star` decimal(10, 2) NULL DEFAULT NULL,
  `d_avg_star` decimal(10, 2) NULL DEFAULT NULL,
  PRIMARY KEY (`d_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of doctor
-- ----------------------------
INSERT INTO `doctor` VALUES (-1, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL);
INSERT INTO `doctor` VALUES (1000, '3d7dd7b26500bd0595573b651d0080fd', 'Zhang San', 'Male', '13899999999', '444111199901011111', '456@qq.com', 'Chief Physician', 'Full-time neurologist', 'Neurology', 1, 10.00, 0, 0.00, NULL);

-- ----------------------------
-- Table structure for drug
-- ----------------------------
DROP TABLE IF EXISTS `drug`;
CREATE TABLE `drug`  (
  `dr_id` int(0) NOT NULL AUTO_INCREMENT,
  `dr_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `dr_price` decimal(10, 2) NULL DEFAULT NULL,
  `dr_number` int(0) NULL DEFAULT NULL,
  `dr_publisher` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `dr_unit` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`dr_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 17 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of drug
-- ----------------------------
INSERT INTO `drug` VALUES (1, 'Penicillin', 23.00, 23, 'National Medical Administration', 'Bag');
INSERT INTO `drug` VALUES (2, 'Oxacillin', 11.00, 35, 'National Medical Administration', 'Box');
INSERT INTO `drug` VALUES (3, 'Ampicillin', 13.00, 52, 'National Medical Administration', 'Box');
INSERT INTO `drug` VALUES (4, 'Piperacillin', 2.00, 7, 'National Medical Administration', 'Box');
INSERT INTO `drug` VALUES (5, 'Amoxicillin', 13.00, 20, 'National Medical Administration', 'Box');
INSERT INTO `drug` VALUES (6, 'Cefazolin', 3.00, 32, 'National Medical Administration', 'Box');
INSERT INTO `drug` VALUES (7, 'Cefalexin', 4.00, 43, 'National Medical Administration', 'Box');
INSERT INTO `drug` VALUES (8, 'Cefuroxime', 8.00, 54, 'National Medical Administration', 'Box');
INSERT INTO `drug` VALUES (9, 'Amikacin', 5.00, 54, 'National Medical Administration', 'Bag');
INSERT INTO `drug` VALUES (10, 'Gentamicin', 7.00, 64, 'National Medical Administration', 'Bag');
INSERT INTO `drug` VALUES (11, 'Erythromycin', 6.00, 76, 'National Medical Administration', 'Bag');
INSERT INTO `drug` VALUES (12, 'Azithromycin', 54.00, 52, 'National Medical Administration', 'Bag');
INSERT INTO `drug` VALUES (13, 'Clindamycin', 65.00, 21, 'National Medical Administration', 'Bag');
INSERT INTO `drug` VALUES (14, 'Co-trimoxazole', 76.00, 54, 'National Medical Administration', 'Bag');
INSERT INTO `drug` VALUES (15, 'Norfloxacin', 65.00, 33, 'National Medical Administration', 'Bag');
INSERT INTO `drug` VALUES (16, 'Levofloxacin', 76.00, 43, 'National Medical Administration', 'Bag');
INSERT INTO `drug` VALUES (17, 'Gankang', 15.00, 10, 'Gankang Group', 'Box');

-- ----------------------------
-- Table structure for orders
-- ----------------------------
DROP TABLE IF EXISTS `orders`;
CREATE TABLE `orders`  (
  `o_id` int(0) NOT NULL AUTO_INCREMENT,
  `p_id` int(0) NULL DEFAULT NULL,
  `d_id` int(0) NULL DEFAULT NULL,
  `o_record` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `o_start` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `o_end` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `o_state` int(0) NULL DEFAULT NULL,
  `o_drug` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `o_check` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `o_total_price` decimal(10, 2) NULL DEFAULT NULL,
  `o_price_state` int(0) NULL DEFAULT NULL,
  `o_advice` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`o_id`) USING BTREE,
  INDEX `oTOp`(`p_id`) USING BTREE,
  INDEX `0TOd`(`d_id`) USING BTREE,
  CONSTRAINT `0TOd` FOREIGN KEY (`d_id`) REFERENCES `doctor` (`d_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `oTOp` FOREIGN KEY (`p_id`) REFERENCES `patient` (`p_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 211209 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of orders
-- ----------------------------
INSERT INTO `orders` VALUES (3989, 2000, 1000, 'Severe cold', '2023-07-27 08:30-09:30', '2023-07-27 22:26:5', 1, 'Penicillin*23(CNY)*1 Oxacillin*11(CNY)*1  Drug total 34 CNY ', 'CT*435(CNY)  Exam total 435 CNY ', 469.00, 0, NULL);

-- ----------------------------
-- Table structure for patient
-- ----------------------------
DROP TABLE IF EXISTS `patient`;
CREATE TABLE `patient`  (
  `p_id` int(0) NOT NULL,
  `p_password` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `p_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `p_gender` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `p_phone` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `p_card` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `p_email` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `p_state` int(0) NULL DEFAULT NULL,
  `p_birthday` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `p_age` int(0) NULL DEFAULT NULL,
  PRIMARY KEY (`p_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of patient
-- ----------------------------
INSERT INTO `patient` VALUES (-1, NULL, NULL, NULL, NULL, NULL, NULL, 1, '1997-01-20', 1);
INSERT INTO `patient` VALUES (2000, '3d7dd7b26500bd0595573b651d0080fd', 'Patient Xu', 'Male', '15977777777', '551222200201013333', '789@qq.com', 1, '2023-07-27', 21);

SET FOREIGN_KEY_CHECKS = 1;
