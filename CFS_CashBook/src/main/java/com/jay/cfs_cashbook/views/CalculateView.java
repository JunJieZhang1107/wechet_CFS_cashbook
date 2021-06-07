package com.jay.cfs_cashbook.views;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class CalculateView {
    List<String> spendNameItems;
    List<BigDecimal> spendNumItems;
    List<String> accountNameItems;
    List<BigDecimal> flowMoneyItems;
    List<BigDecimal> investMoneyItems;
}
