package com.jay.cfs_cashbook.views;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class RecordView {
    private Integer accountId;
    private String accountName;
    private BigDecimal money;
    private Boolean type;
    private String reason;
    private Date time;
}
