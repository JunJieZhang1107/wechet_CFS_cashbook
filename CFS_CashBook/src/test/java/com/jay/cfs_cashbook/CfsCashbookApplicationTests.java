package com.jay.cfs_cashbook;

import com.alibaba.fastjson.JSON;
import com.jay.cfs_cashbook.bean.Account;
import com.jay.cfs_cashbook.bean.User;
import com.jay.cfs_cashbook.service.AccountService;
import org.apache.tomcat.jni.Local;
import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;

@SpringBootTest
class CfsCashbookApplicationTests {


    @Autowired
    AccountService accountService;
    @Test
    void contextLoads() {
        LocalDate now = LocalDate.now();
        DateTimeFormatter dateTimeFormatter =  DateTimeFormatter.ofPattern("yyyy-MM");
        System.out.println(now.format(dateTimeFormatter));
    }

}
