package com.jay.cfs_cashbook.mapper;

import com.jay.cfs_cashbook.bean.Budget;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public interface BudgetMapper {
    Budget getMonthDataByDateAndUserId(Budget budget);
    Integer update(Budget budget);

    void save(Budget budget);

    Integer updatePartMoney(Budget budget);
}
