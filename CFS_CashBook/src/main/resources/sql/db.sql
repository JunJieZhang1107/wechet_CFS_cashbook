DROP TABLE IF EXISTS `budget`; -- 预算表
DROP TABLE IF EXISTS `income_outgo`;  -- 支出收入表
DROP TABLE IF EXISTS `invest`; -- 投资表
DROP TABLE IF EXISTS `debt`; -- 负债表
DROP TABLE IF EXISTS `borrow`; -- 外借表
DROP TABLE IF EXISTS `account`; -- 账户表, 有外键的存在
DROP TABLE IF EXISTS `t_user`; -- 注册表

CREATE TABLE `t_user`( -- 注册表
    `user_id` bigint(20) NOT NULL AUTO_INCREMENT, -- 注册id
    `email` varchar(30) NOT NULL, -- 邮箱验证
    `password` varchar(20) NOT NULL,  -- 用户密码
    primary KEY (`user_id`)
)ENGINE=InnoDB AUTO_INCREMENT = 1 DEFAULT CHARSET=utf8;

CREATE TABLE `account`( -- 账号表
    `account_id` bigint(20) NOT NULL AUTO_INCREMENT, -- 账户id
    `account_name` varchar(20) NOT NULL, -- 账户名
    `account_free_money` decimal(10,2) DEFAULT 0.00, -- 流动金额
    `user_id` bigint(20) NOT NULL, -- 注册id
    PRIMARY KEY (`account_id`),
    CONSTRAINT fk_account_user_id FOREIGN KEY(`user_id`) REFERENCES t_user(`user_id`)
        ON DELETE RESTRICT
        ON UPDATE CASCADE
)ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

CREATE TABLE `borrow`( -- 外借表
    `borrow_id` bigint(20) NOT NULL AUTO_INCREMENT, -- 主键
    `borrow_money` decimal(10,2) NOT NULL, -- 外借金额
    `borrow_name` varchar(20) NOT NULL, -- 外借人的姓名
    `back_time` DATE NOT NULL, -- 预计归还时间
    `account_id` bigint(20) NOT NULL, -- 外键
     PRIMARY KEY(`borrow_id`),
     CONSTRAINT fk_borrow_account_id FOREIGN KEY(`account_id`) REFERENCES account(`account_id`)
        ON DELETE RESTRICT
        ON UPDATE CASCADE
)ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

CREATE TABLE `debt`( -- 负债表
    `debt_id` bigint(20) NOT NULL AUTO_INCREMENT, -- 负债id,主键
    `debt_pay` decimal(10,2) NOT NULL , -- 负债金额
    `pay_time` DATE NOT NULL, -- 归还时间
    `is_pay` tinyint(1) DEFAULT false, -- 0 为还 1已还
    `account_id` bigint(20) NOT NULL, -- 外键
    PRIMARY KEY (`debt_id`),
    CONSTRAINT fk_debt_account_id FOREIGN KEY(`account_id`) REFERENCES account(`account_id`)
        ON DELETE RESTRICT
        ON UPDATE CASCADE
)ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

CREATE TABLE `invest`( -- 投资表
    `invest_id` bigint(20) NOT NULL AUTO_INCREMENT, -- 主键
    `invest_product_name` varchar(30) NOT NULL , -- 投资产品名
    `invest_type` int(10) NOT NULL, -- 投资的类型
    `invest_money` decimal(10,2) NOT NULL, -- 投资金额
    `back_time` DATE NOT NULL, -- 卖出时间
    `back_money` decimal(10,2)  NULL, -- 卖出获取的金额为多少
    `minu_diff` decimal (10,2) NULL , -- 卖出-投资 得到的区别
    `account_id` bigint(20) NOT NULL, -- 外键
     PRIMARY KEY(`invest_id`),
     CONSTRAINT fk_invest_account_id FOREIGN KEY(`account_id`) REFERENCES account(`account_id`)
        ON DELETE RESTRICT
        ON UPDATE CASCADE
)ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

CREATE TABLE `income_outgo`( -- 收支表
    `income_outgo_id` bigint(20) NOT NULL AUTO_INCREMENT, -- 收支单号
    `type` tinyint(1) NOT NULL,  -- 0 为收入 1为支出
    `money` DECIMAL(10,2) NOT NULL,  -- 支出金额
    `reason` varchar(20) NOT NULL, -- 原因
    `income_outgo_time` DATETIME NOT NULL, -- 收支时间
    `account_id` bigint(20) NOT NULL, -- 外键
     PRIMARY KEY(`income_outgo_id`),
     CONSTRAINT fk_income_outgo_account_id FOREIGN KEY(`account_id`) REFERENCES account(`account_id`)
        ON DELETE RESTRICT
        ON UPDATE CASCADE
)ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

CREATE TABLE `budget`( -- 预算表
    `id` bigint(20) NOT NULL AUTO_INCREMENT, -- 收支单号
    `eat` DECIMAL(10,2) NOT NULL,
    `drink` DECIMAL(10,2) NOT NULL,
    `play` DECIMAL(10,2) NOT NULL,
    `funny`  DECIMAL(10,2) NOT NULL,
    `improve` DECIMAL(10,2) NOT NULL,
    `created` DATE  NOT NULL,
    `user_id` bigint(20) NOT NULL, -- 注册id
     PRIMARY KEY(`id`),
   CONSTRAINT fk_budget_user_id FOREIGN KEY(`user_id`) REFERENCES t_user(`user_id`)
    ON DELETE RESTRICT
    ON UPDATE CASCADE
)ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;