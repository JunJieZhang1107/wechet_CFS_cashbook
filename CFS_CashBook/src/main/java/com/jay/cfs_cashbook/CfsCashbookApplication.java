package com.jay.cfs_cashbook;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan(value="com.jay.cfs_cashbook.mapper")
@SpringBootApplication
public class CfsCashbookApplication {

    public static void main(String[] args) {
        SpringApplication.run(CfsCashbookApplication.class, args);
    }

}
