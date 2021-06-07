package com.jay.cfs_cashbook.service;

import com.jay.cfs_cashbook.bean.Budget;
import com.jay.cfs_cashbook.mapper.AccountMapper;
import com.jay.cfs_cashbook.mapper.BudgetMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Service
public class BudgetService {
    // 获取数据
    public Budget getData(Integer userId){
        Budget tmp = new Budget();
        tmp.setCreated(new Date());
        tmp.setUserId(userId);
        Budget budget = budgetMapper.getMonthDataByDateAndUserId(tmp);
        return budget;
    }

    // 更新数据
    @Transactional
    public Map<String,String> update(Budget budget){
        Map<String,String> map = new HashMap<>();
        if(budget.getId() == null){
            map.put("msg","无法更新");
            return map;
        }
        budgetMapper.update(budget);
        map.put("msg","更新成功");
        return map;
    }

    // 提交数据
    @Transactional
    public Map<String,String> save(Budget budget){
        Budget data = getData(budget.getUserId());
        Map<String,String> map = new HashMap<>();
        if(Objects.equals(data,null)){
            budget.setCreated(new Date());
            budgetMapper.save(budget);
            map.put("msg","提交成功");
            return map;
        }
        map.put("msg","已有数据");
        return map;
    }

    // 扣除对应的预算金额, id一定要有, 用mybatis有值就减掉, type == 1 则扣除 == 0 则回退
    @Transactional
    public Map<String,String> updatePartMoney(Budget budget,Integer type){
        if(type == 0)
        {
            Integer integer = budgetMapper.updatePartMoney(budget);
            Map<String, String> map = accountService.judgeInteger(integer, "回退失败", "回退成功");
            return map;
        }
        Integer integer = budgetMapper.updatePartMoney(budget);
        Map<String, String> map = accountService.judgeInteger(integer, "扣款失败", "扣款成功");
        return map;
    }

    @Autowired
    BudgetMapper budgetMapper;
    @Autowired
    AccountService accountService;
}
