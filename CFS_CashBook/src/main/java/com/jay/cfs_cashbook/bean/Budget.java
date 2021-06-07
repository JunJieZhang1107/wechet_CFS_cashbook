package com.jay.cfs_cashbook.bean;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class Budget {
    private Integer id;
    private Integer userId;
    private BigDecimal eat;
    private BigDecimal drink;
    private BigDecimal play;
    private BigDecimal funny;
    private BigDecimal improve;
    private Date created;
}
