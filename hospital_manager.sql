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
-- Demo accounts (password 123456):
-- Admin 22401717, Doctor 1000, Patient 2000
INSERT INTO `admini` VALUES (22401717, '123456', 'Sarah Mitchell', 'Female', '49091850', '021 555 0101', 'sarah.mitchell@hospital.nz');

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
INSERT INTO `checks` VALUES (1, 'Ultrasound', 180.00);
INSERT INTO `checks` VALUES (2, 'CT', 850.00);
INSERT INTO `checks` VALUES (3, 'MRI', 1400.00);
INSERT INTO `checks` VALUES (4, 'X-Ray', 95.00);
INSERT INTO `checks` VALUES (5, 'ECG', 75.00);
INSERT INTO `checks` VALUES (6, 'Echocardiogram', 320.00);
INSERT INTO `checks` VALUES (7, 'CBC', 45.00);
INSERT INTO `checks` VALUES (8, 'Liver Function', 55.00);
INSERT INTO `checks` VALUES (9, 'Blood Glucose', 25.00);
INSERT INTO `checks` VALUES (10, 'Thyroid Function', 60.00);
INSERT INTO `checks` VALUES (11, 'Vision Test', 40.00);

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
INSERT INTO `doctor` VALUES (1000, '3d7dd7b26500bd0595573b651d0080fd', 'James Wilson', 'Male', '021 555 0188', '458219', 'james.wilson@hospital.nz', 'Consultant', 'Consultant neurologist at Auckland City Hospital', 'Neurology', 1, 85.00, 0, 0.00, NULL);
INSERT INTO `doctor` VALUES (1001, '3d7dd7b26500bd0595573b651d0080fd', 'Emma Clarke', 'Female', '021 555 0189', '458220', 'emma.clarke@hospital.nz', 'Specialist', 'Paediatric specialist at Starship Children''s Hospital', 'Pediatrics', 1, 75.00, 0, 0.00, NULL);
INSERT INTO `doctor` VALUES (1002, '3d7dd7b26500bd0595573b651d0080fd', 'Hemi Ngata', 'Male', '027 555 0190', '458221', 'hemi.ngata@hospital.nz', 'Registrar', 'Cardiology registrar at Wellington Hospital', 'Cardiology', 1, 55.00, 0, 0.00, NULL);

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
INSERT INTO `drug` VALUES (1, 'Penicillin', 8.00, 23, 'Pharmac', 'Pack');
INSERT INTO `drug` VALUES (2, 'Oxacillin', 11.00, 35, 'Pharmac', 'Box');
INSERT INTO `drug` VALUES (3, 'Ampicillin', 12.00, 52, 'Pharmac', 'Box');
INSERT INTO `drug` VALUES (4, 'Piperacillin', 18.00, 7, 'Pharmac', 'Box');
INSERT INTO `drug` VALUES (5, 'Amoxicillin', 13.00, 20, 'Pharmac', 'Box');
INSERT INTO `drug` VALUES (6, 'Cefazolin', 9.00, 32, 'Pharmac', 'Box');
INSERT INTO `drug` VALUES (7, 'Cefalexin', 7.00, 43, 'Pharmac', 'Box');
INSERT INTO `drug` VALUES (8, 'Cefuroxime', 14.00, 54, 'Pharmac', 'Box');
INSERT INTO `drug` VALUES (9, 'Amikacin', 22.00, 54, 'Medsafe NZ', 'Pack');
INSERT INTO `drug` VALUES (10, 'Gentamicin', 16.00, 64, 'Medsafe NZ', 'Pack');
INSERT INTO `drug` VALUES (11, 'Erythromycin', 10.00, 76, 'Pharmac', 'Pack');
INSERT INTO `drug` VALUES (12, 'Azithromycin', 15.00, 52, 'Pharmac', 'Pack');
INSERT INTO `drug` VALUES (13, 'Clindamycin', 19.00, 21, 'Pharmac', 'Pack');
INSERT INTO `drug` VALUES (14, 'Co-trimoxazole', 8.00, 54, 'Pharmac', 'Pack');
INSERT INTO `drug` VALUES (15, 'Norfloxacin', 17.00, 33, 'Pharmac', 'Pack');
INSERT INTO `drug` VALUES (16, 'Levofloxacin', 21.00, 43, 'Pharmac', 'Pack');
INSERT INTO `drug` VALUES (17, 'Paracetamol', 4.50, 40, 'Pharmacy Brands', 'Box');

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
INSERT INTO `orders` VALUES (3989, 2000, 1000, 'Influenza-like illness', '2023-07-27 08:30-09:30', '2023-07-27 22:26:5', 1, 'Amoxicillin*13(NZD)*1 Paracetamol*4.5(NZD)*1  Drug total 17.5 NZD ', 'CBC*45(NZD)  Exam total 45 NZD ', 62.50, 0, 'Rest, fluids, review in 48 hours if fever persists');

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
INSERT INTO `patient` VALUES (2000, '3d7dd7b26500bd0595573b651d0080fd', 'Liam Thompson', 'Male', '027 555 0142', 'ZZZ0016', 'liam.thompson@gmail.com', 1, '2004-03-18', 22);
INSERT INTO `patient` VALUES (2001, '3d7dd7b26500bd0595573b651d0080fd', 'Aroha Williams', 'Female', '022 555 0143', 'ZZZ0024', 'aroha.williams@gmail.com', 1, '1998-11-05', 27);

SET FOREIGN_KEY_CHECKS = 1;
