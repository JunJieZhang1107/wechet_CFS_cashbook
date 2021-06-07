package com.jay.cfs_cashbook.views;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class BorrowView {
    private Integer accountId;
    private Integer id;
    private BigDecimal borrowMoney;
    private long backTime;
    private String accountName;
    private String borrowName;
}
