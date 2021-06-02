-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Server version:               10.0.20-MariaDB - mariadb.org binary distribution
-- Server OS:                    Win64
-- HeidiSQL Version:             11.2.0.6213
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


-- Dumping database structure for stu_att_001
CREATE DATABASE IF NOT EXISTS `stu_att_001` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `stu_att_001`;

-- Dumping structure for table stu_att_001.attendance
CREATE TABLE IF NOT EXISTS `attendance` (
  `attendance_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `attendance_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `attendance_student_id` int(10) unsigned DEFAULT NULL,
  `attendance_student_name` varchar(450) DEFAULT NULL,
  `attendance_subject_id` int(10) unsigned DEFAULT NULL,
  `attendance_subject_name` varchar(450) DEFAULT NULL,
  `attendance_lecturer_id` int(10) unsigned DEFAULT NULL,
  `attendance_lecturer_name` varchar(450) DEFAULT NULL,
  `attendance_day_payment` decimal(10,2) NOT NULL DEFAULT '0.00',
  `attendance_is_monthly_pay` tinyint(1) unsigned DEFAULT '0',
  `attendance_paid_for_month` varchar(450) DEFAULT NULL,
  `attendance_paid_for_year` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`attendance_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Dumping data for table stu_att_001.attendance: ~0 rows (approximately)
DELETE FROM `attendance`;
/*!40000 ALTER TABLE `attendance` DISABLE KEYS */;
/*!40000 ALTER TABLE `attendance` ENABLE KEYS */;

-- Dumping structure for table stu_att_001.attend_students
CREATE TABLE IF NOT EXISTS `attend_students` (
  `attend_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `attend_student_id` int(10) unsigned DEFAULT NULL,
  `is_student_paid` tinyint(1) unsigned DEFAULT '0',
  `attend_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `attend_status` tinyint(1) unsigned DEFAULT '0',
  `attend_remark` varchar(450) DEFAULT NULL,
  `attend_subject_id` int(10) unsigned DEFAULT NULL,
  `attend_lec_id` int(10) unsigned DEFAULT NULL,
  `attend_room` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`attend_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Dumping data for table stu_att_001.attend_students: ~0 rows (approximately)
DELETE FROM `attend_students`;
/*!40000 ALTER TABLE `attend_students` DISABLE KEYS */;
/*!40000 ALTER TABLE `attend_students` ENABLE KEYS */;

-- Dumping structure for table stu_att_001.batch
CREATE TABLE IF NOT EXISTS `batch` (
  `batch_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `batch_year` varchar(15) DEFAULT NULL,
  `batch_level` varchar(150) DEFAULT NULL,
  `batch_detail` varchar(450) DEFAULT NULL,
  `batch_status` tinyint(1) unsigned DEFAULT '1',
  PRIMARY KEY (`batch_id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=latin1;

-- Dumping data for table stu_att_001.batch: ~3 rows (approximately)
DELETE FROM `batch`;
/*!40000 ALTER TABLE `batch` DISABLE KEYS */;
INSERT INTO `batch` (`batch_id`, `batch_year`, `batch_level`, `batch_detail`, `batch_status`) VALUES
	(4, 'Y2S2.SE.4.3', 'SE', 'Y2S2.SE.5.2', 1),
	(5, 'Y1S2', 'SE', 'Y1S2.SE.4.3', 1),
	(7, 'Y1S1', 'IT', 'Y1S1.IT.1.1', 1);
/*!40000 ALTER TABLE `batch` ENABLE KEYS */;

-- Dumping structure for table stu_att_001.cla160
CREATE TABLE IF NOT EXISTS `cla160` (
  `idcla160` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `d_range` date DEFAULT NULL,
  `cla_key` varchar(450) DEFAULT NULL,
  PRIMARY KEY (`idcla160`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- Dumping data for table stu_att_001.cla160: ~0 rows (approximately)
DELETE FROM `cla160`;
/*!40000 ALTER TABLE `cla160` DISABLE KEYS */;
INSERT INTO `cla160` (`idcla160`, `d_range`, `cla_key`) VALUES
	(1, '2022-01-31', 'key101');
/*!40000 ALTER TABLE `cla160` ENABLE KEYS */;

-- Dumping structure for table stu_att_001.course
CREATE TABLE IF NOT EXISTS `course` (
  `course_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `course_name` varchar(150) DEFAULT NULL,
  `course_type` varchar(500) DEFAULT NULL,
  `course_detail` varchar(450) DEFAULT NULL,
  `course_satus` tinyint(1) unsigned DEFAULT '1',
  PRIMARY KEY (`course_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

-- Dumping data for table stu_att_001.course: ~2 rows (approximately)
DELETE FROM `course`;
/*!40000 ALTER TABLE `course` DISABLE KEYS */;
INSERT INTO `course` (`course_id`, `course_name`, `course_type`, `course_detail`, `course_satus`) VALUES
	(1, '2', '4:0', '[[Monday], [Tuesday], null, null, null, null, null]', 1),
	(2, '2', '2:2', '[[Monday], [Tuesday], null, null, null, null, null]', 1);
/*!40000 ALTER TABLE `course` ENABLE KEYS */;

-- Dumping structure for table stu_att_001.group_info
CREATE TABLE IF NOT EXISTS `group_info` (
  `group_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `group_name` varchar(150) DEFAULT NULL,
  `group_batch_id` int(10) unsigned DEFAULT NULL,
  `group_type` varchar(150) DEFAULT NULL,
  `group_detail` varchar(450) DEFAULT NULL,
  `group_status` tinyint(1) unsigned DEFAULT '1',
  PRIMARY KEY (`group_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Dumping data for table stu_att_001.group_info: ~0 rows (approximately)
DELETE FROM `group_info`;
/*!40000 ALTER TABLE `group_info` DISABLE KEYS */;
/*!40000 ALTER TABLE `group_info` ENABLE KEYS */;

-- Dumping structure for table stu_att_001.lecturer
CREATE TABLE IF NOT EXISTS `lecturer` (
  `lecturer_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `lecturer_title` varchar(100) DEFAULT NULL,
  `lecturer_name` varchar(450) DEFAULT NULL,
  `lecturer_email` varchar(150) DEFAULT NULL,
  `lecturer_contact_no` varchar(45) DEFAULT NULL,
  `lecturer_detail` varchar(450) DEFAULT NULL,
  `lecturer_status` tinyint(1) unsigned DEFAULT '1',
  `lecturer_address` varchar(450) DEFAULT NULL,
  `lecturer_rank` varchar(50) DEFAULT NULL,
  `lecturer_dpt` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`lecturer_id`)
) ENGINE=InnoDB AUTO_INCREMENT=78 DEFAULT CHARSET=latin1;

-- Dumping data for table stu_att_001.lecturer: ~4 rows (approximately)
DELETE FROM `lecturer`;
/*!40000 ALTER TABLE `lecturer` DISABLE KEYS */;
INSERT INTO `lecturer` (`lecturer_id`, `lecturer_title`, `lecturer_name`, `lecturer_email`, `lecturer_contact_no`, `lecturer_detail`, `lecturer_status`, `lecturer_address`, `lecturer_rank`, `lecturer_dpt`) VALUES
	(74, 'Senior Lecturer(HG)', 'Dr. Anuradha Jayakody', '0032', 'Computing', 'IT', 1, '3.0032', 'Malabe', 'C-block'),
	(75, 'Senior Lecturer', 'Dr. Janaka Wijekoon', '0033', 'Computing', 'IT', 1, '4.0033', 'Malabe', 'New building'),
	(76, 'Lecturer', 'Mr. Kamal Ranathunga', '0038', 'Business', 'IT', 1, '5.0038', 'Metro', 'C-block'),
	(77, 'Senior Lecturer', 'Mr. Kalinga Kariyawasam', '0036', 'Engineering', 'SE', 1, '4.0036', 'Kandy', 'D-block');
/*!40000 ALTER TABLE `lecturer` ENABLE KEYS */;

-- Dumping structure for table stu_att_001.payment
CREATE TABLE IF NOT EXISTS `payment` (
  `payment_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `payment_student_id` int(10) unsigned DEFAULT NULL,
  `payment_is_monthly_payment` tinyint(1) unsigned DEFAULT '0',
  `payment_amount` decimal(10,2) DEFAULT NULL,
  `payment_for_year` varchar(45) DEFAULT NULL,
  `payment_for_month` varchar(45) DEFAULT NULL,
  `payment_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `payment_class` varchar(25) DEFAULT NULL,
  `payment_status` tinyint(1) unsigned DEFAULT '0',
  `payment_detail` varchar(50) DEFAULT NULL,
  `payment_subject_id` int(10) unsigned DEFAULT NULL,
  `payment_subject_name` varchar(50) DEFAULT NULL,
  `payment_lecturer_id` int(11) DEFAULT NULL,
  `payment_lecturer_name` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`payment_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Dumping data for table stu_att_001.payment: ~0 rows (approximately)
DELETE FROM `payment`;
/*!40000 ALTER TABLE `payment` DISABLE KEYS */;
/*!40000 ALTER TABLE `payment` ENABLE KEYS */;

-- Dumping structure for table stu_att_001.room
CREATE TABLE IF NOT EXISTS `room` (
  `room_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `room_name` varchar(150) DEFAULT NULL,
  `room_code` varchar(150) DEFAULT NULL,
  `room_detail` varchar(450) DEFAULT NULL,
  `room_status` tinyint(1) unsigned DEFAULT '1',
  PRIMARY KEY (`room_id`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=latin1;

-- Dumping data for table stu_att_001.room: ~4 rows (approximately)
DELETE FROM `room`;
/*!40000 ALTER TABLE `room` DISABLE KEYS */;
INSERT INTO `room` (`room_id`, `room_name`, `room_code`, `room_detail`, `room_status`) VALUES
	(14, 'A', 'A101', 'LH-150', 1),
	(15, 'D', 'D502', 'LB-40', 1),
	(16, 'B', 'B202', 'LH-90', 1),
	(17, 'B', 'B404', 'LB-40', 1);
/*!40000 ALTER TABLE `room` ENABLE KEYS */;

-- Dumping structure for table stu_att_001.sesion
CREATE TABLE IF NOT EXISTS `sesion` (
  `subject_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `subject_name` varchar(450) DEFAULT NULL,
  `subject_module_code` varchar(100) DEFAULT NULL,
  `subject_detail` varchar(450) DEFAULT NULL,
  `subject_status` int(10) unsigned DEFAULT '1',
  `subject_course_id` int(10) unsigned DEFAULT NULL,
  `subject_course_level` varchar(150) DEFAULT NULL,
  `subject_semester` varchar(150) DEFAULT NULL,
  PRIMARY KEY (`subject_id`)
) ENGINE=InnoDB AUTO_INCREMENT=117 DEFAULT CHARSET=latin1;

-- Dumping data for table stu_att_001.sesion: ~0 rows (approximately)
DELETE FROM `sesion`;
/*!40000 ALTER TABLE `sesion` DISABLE KEYS */;
INSERT INTO `sesion` (`subject_id`, `subject_name`, `subject_module_code`, `subject_detail`, `subject_status`, `subject_course_id`, `subject_course_level`, `subject_semester`) VALUES
	(115, 'Lecture', 'Y1S1.IT.1.1', 'IP', 1, 2, 'Dr. Janaka Wijekoon', '25'),
	(116, 'tutorial', 'Y1S1.IT.1.1', 'CS', 1, 2, 'Mr. Kalinga Kariyawasam', '35');
/*!40000 ALTER TABLE `sesion` ENABLE KEYS */;

-- Dumping structure for table stu_att_001.student
CREATE TABLE IF NOT EXISTS `student` (
  `student_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `student_name` varchar(450) DEFAULT NULL,
  `student_email_1` varchar(150) DEFAULT NULL,
  `student_email_2` varchar(150) DEFAULT NULL,
  `student_reg_no` varchar(150) DEFAULT NULL,
  `student_contact_no` varchar(45) DEFAULT NULL,
  `student_detail` varchar(450) DEFAULT NULL,
  `student_status` tinyint(1) unsigned DEFAULT '1',
  `student_batch_id` varchar(450) DEFAULT NULL,
  `student_group_id` varchar(450) DEFAULT NULL,
  `student_special_id` varchar(450) DEFAULT NULL,
  `student_address` varchar(450) DEFAULT NULL,
  `student_contact_no_2` varchar(50) DEFAULT NULL,
  `student_image_path` varchar(2000) DEFAULT NULL,
  `student_guardian_name` varchar(450) DEFAULT NULL,
  `student_guardian_contact_no` varchar(45) DEFAULT NULL,
  `student_dob` datetime DEFAULT NULL,
  PRIMARY KEY (`student_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;

-- Dumping data for table stu_att_001.student: ~0 rows (approximately)
DELETE FROM `student`;
/*!40000 ALTER TABLE `student` DISABLE KEYS */;
INSERT INTO `student` (`student_id`, `student_name`, `student_email_1`, `student_email_2`, `student_reg_no`, `student_contact_no`, `student_detail`, `student_status`, `student_batch_id`, `student_group_id`, `student_special_id`, `student_address`, `student_contact_no_2`, `student_image_path`, `student_guardian_name`, `student_guardian_contact_no`, `student_dob`) VALUES
	(1, 'assd asd', 'sss@asda.com', 'aa aaaa', 'SW-1', '0777724874', 'aaaaaaaaaaaaaaaaaaaaaaaaa', 1, 'Female', '0713032404', 'ss\nsss', NULL, '03 Apr 2021', 'P', NULL, NULL, '2021-04-06 00:00:00'),
	(2, 'dq', 'qwffqf', 'qfffwf', '', 'fqqf', 'fqffq ffqweff fq', 1, 'Male', 'fqffq', 'qfqwfqwqf', NULL, NULL, NULL, NULL, NULL, '2021-04-06 00:00:00'),
	(3, 'nawa sathsarani', 'blabala@gmail.com', 'asd asd', 'IT-213', '0719408879', 'NNM njs ini', 1, 'Female', '', 'kandy', NULL, NULL, NULL, NULL, NULL, '2021-04-05 00:00:00');
/*!40000 ALTER TABLE `student` ENABLE KEYS */;

-- Dumping structure for table stu_att_001.stu_free_card
CREATE TABLE IF NOT EXISTS `stu_free_card` (
  `freeCardID` int(11) NOT NULL AUTO_INCREMENT,
  `studentID` int(11) DEFAULT NULL,
  `subjectID` int(11) DEFAULT NULL,
  `lecID` int(11) DEFAULT NULL,
  `stu_grade` varchar(50) DEFAULT NULL,
  `issueDate` datetime DEFAULT NULL,
  `lastUpdateDate` datetime DEFAULT CURRENT_TIMESTAMP,
  `isActive` tinyint(4) DEFAULT NULL,
  `offPercentage` double DEFAULT NULL,
  `amount` decimal(10,2) DEFAULT '0.00',
  PRIMARY KEY (`freeCardID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Dumping data for table stu_att_001.stu_free_card: ~0 rows (approximately)
DELETE FROM `stu_free_card`;
/*!40000 ALTER TABLE `stu_free_card` DISABLE KEYS */;
/*!40000 ALTER TABLE `stu_free_card` ENABLE KEYS */;

-- Dumping structure for table stu_att_001.subject_details
CREATE TABLE IF NOT EXISTS `subject_details` (
  `subject_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `subject_name` varchar(450) DEFAULT NULL,
  `subject_module_code` varchar(100) DEFAULT NULL,
  `subject_detail` varchar(450) DEFAULT NULL,
  `subject_status` tinyint(1) unsigned DEFAULT '1',
  `subject_course_id` int(10) unsigned DEFAULT NULL,
  `subject_course_level` varchar(150) DEFAULT NULL,
  `subject_semester` varchar(150) DEFAULT NULL,
  PRIMARY KEY (`subject_id`)
) ENGINE=InnoDB AUTO_INCREMENT=119 DEFAULT CHARSET=latin1;

-- Dumping data for table stu_att_001.subject_details: ~1 rows (approximately)
DELETE FROM `subject_details`;
/*!40000 ALTER TABLE `subject_details` DISABLE KEYS */;
INSERT INTO `subject_details` (`subject_id`, `subject_name`, `subject_module_code`, `subject_detail`, `subject_status`, `subject_course_id`, `subject_course_level`, `subject_semester`) VALUES
	(115, 'IP', 'IT1010', '2-2-2-2', 1, 0, '1st Year', '1st Semester'),
	(116, 'CS', 'IT1020', '2-2-2-2', 1, 0, '1st Year', '1st Semester'),
	(117, 'MC', 'IT1030', '2-2-1-1', 1, 0, '1st Year', '1st Semester'),
	(118, 'OOC', 'IT1090', '2-2-2-2', 1, 0, '1st Year', '2nd Semester');
/*!40000 ALTER TABLE `subject_details` ENABLE KEYS */;

-- Dumping structure for table stu_att_001.tag
CREATE TABLE IF NOT EXISTS `tag` (
  `batch_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `batch_year` varchar(15) DEFAULT NULL,
  `batch_level` varchar(150) DEFAULT NULL,
  `batch_detail` varchar(450) DEFAULT NULL,
  `batch_status` tinyint(1) unsigned DEFAULT '1',
  PRIMARY KEY (`batch_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;

-- Dumping data for table stu_att_001.tag: ~3 rows (approximately)
DELETE FROM `tag`;
/*!40000 ALTER TABLE `tag` DISABLE KEYS */;
INSERT INTO `tag` (`batch_id`, `batch_year`, `batch_level`, `batch_detail`, `batch_status`) VALUES
	(1, 'lec', '01', 'Lecture', 1),
	(2, 'tute', '02', 'tutorial', 1),
	(3, 'lab', '03', 'practical', 1);
/*!40000 ALTER TABLE `tag` ENABLE KEYS */;

-- Dumping structure for table stu_att_001.user
CREATE TABLE IF NOT EXISTS `user` (
  `user_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `user_name` varchar(100) DEFAULT NULL,
  `user_pw` varchar(450) DEFAULT NULL,
  `user_type` varchar(45) NOT NULL,
  `user_email` varchar(255) DEFAULT NULL,
  `user_password` varchar(255) DEFAULT NULL,
  `user_status` int(11) DEFAULT '1',
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- Dumping data for table stu_att_001.user: ~2 rows (approximately)
DELETE FROM `user`;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` (`user_id`, `user_name`, `user_pw`, `user_type`, `user_email`, `user_password`, `user_status`) VALUES
	(1, 'admin', '123', 'Admin', NULL, '123', 1),
	(2, 'it16102156', '942723024v', 'Admin', NULL, '1', 1);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
