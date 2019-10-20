-- 创建数据库 school
DROP DATABASE IF EXISTS `school`;
CREATE DATABASE `school` CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- 创建学生表
CREATE  TABLE `school`.`student` (
  `id` BIGINT NOT NULL AUTO_INCREMENT ,
  `name` VARCHAR(45) NOT NULL ,
  PRIMARY KEY (`id`)
)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_general_ci;

-- 学生表添加数据
INSERT INTO `school`.`student` (`name`) VALUES ('张三'),('李四'),('王五');

-- 创建老师表
CREATE  TABLE `school`.`teacher` (
  `id` BIGINT NOT NULL AUTO_INCREMENT ,
  `name` VARCHAR(45) NOT NULL ,
  PRIMARY KEY (`id`)
)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_general_ci;

-- 老师表插入数据
INSERT INTO `school`.`teacher` (`name`) VALUES ('刘老师'),('李老师');


-- 创建关系表
CREATE  TABLE `school`.`relationship` (
  `student_id` BIGINT NOT NULL,
  `teacher_id` BIGINT NOT NULL ,
  INDEX `idx_student_id` (`student_id`),
  INDEX `idx_teacher_id` (`teacher_id`)
)
  ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci;

-- 插入数据
INSERT INTO `school`.`relationship` (`student_id`, `teacher_id`)
VALUES
  (1, 1),
  (2, 1),
  (2, 2),
  (3, 2);
