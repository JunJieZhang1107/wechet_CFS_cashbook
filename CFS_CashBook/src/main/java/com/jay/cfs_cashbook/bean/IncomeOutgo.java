package com.jay.cfs_cashbook.bean;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import org.springframework.data.annotation.Id;

import java.math.BigDecimal;
import java.util.Date;

@Data
@ApiModel("资金收支信息")
public class IncomeOutgo {
    private Integer id;
    private Integer accountId;
    private Boolean type;
    private BigDecimal money;
    private String reason;
    private Date time;
}
