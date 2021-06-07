package com.jay.cfs_cashbook.mapper;

import com.jay.cfs_cashbook.bean.IncomeOutgo;

import java.math.BigDecimal;
import java.util.List;

public interface IncomeOutgoMapper {
    Integer save(IncomeOutgo incomeOutgo);

    List<IncomeOutgo> getAllByUserId(Integer userId);

    Integer delete(IncomeOutgo incomeOutgo);

    List<BigDecimal> getSpendNumByUserId(int userId);
}
