-- 若有则删除
DROP DATABASE IF EXISTS `blog_db`;

-- 创建数据库
CREATE DATABASE `blog_db` CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- 用户表
CREATE  TABLE `blog_db`.`user` (
  `id` BIGINT NOT NULL AUTO_INCREMENT ,
  `name` VARCHAR(45) NOT NULL ,
  `email` VARCHAR(45) NOT NULL ,
  `password` VARCHAR(45) NOT NULL ,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `uk_name` (`name`) -- name 保证唯一
)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_general_ci;

-- 用户表添加数据
INSERT INTO `blog_db`.`user` (`name`, `email`, `password`) VALUES ('letian', 'letian@111.com', '123');
INSERT INTO `blog_db`.`user` ( `name`, `email`, `password`) VALUES ('xiaosi', 'xiaosi@111.com', '123');

-- 博客表
CREATE  TABLE `blog_db`.`blog` (
  `id` BIGINT NOT NULL AUTO_INCREMENT ,
  `owner_id` BIGINT NOT NULL ,  -- 所属用户的id
  `title` TEXT NOT NULL ,
  `content` TEXT NOT NULL ,
  PRIMARY KEY (`id`) 
)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_general_ci;

-- 博客表插入数据
INSERT INTO `blog_db`.`blog` (`owner_id`, `title`, `content`) VALUES ('1', '标题1', '文本1');
INSERT INTO `blog_db`.`blog` (`owner_id`, `title`, `content`) VALUES ('1', '标题2', '文本2');
INSERT INTO `blog_db`.`blog` (`owner_id`, `title`, `content`) VALUES ('1', '标题3', '文本3');
INSERT INTO `blog_db`.`blog` (`owner_id`, `title`, `content`) VALUES ('1', '标题4', '文本4');
INSERT INTO `blog_db`.`blog` (`owner_id`, `title`, `content`) VALUES ('1', '标题5', '文本5');
INSERT INTO `blog_db`.`blog` (`owner_id`, `title`, `content`) VALUES ('2', '标题21', '文本21');
INSERT INTO `blog_db`.`blog` (`owner_id`, `title`, `content`) VALUES ('1', '你好, World', '你好, 😆');
