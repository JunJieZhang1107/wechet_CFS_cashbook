package com.jay.cfs_cashbook.controller;


import com.jay.cfs_cashbook.bean.Account;
import com.jay.cfs_cashbook.mapper.AccountMapper;
import com.jay.cfs_cashbook.mapper.IncomeOutgoMapper;
import com.jay.cfs_cashbook.mapper.InvestMapper;
import com.jay.cfs_cashbook.views.CalculateView;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RestController
@Slf4j
public class CalculateController {
    @GetMapping("/calculate")
    public CalculateView index(int userId)
    {   log.info(String.valueOf(userId));
        // 获取本月
        LocalDate now = LocalDate.now();
        List<Account> accountIds = accountMapper.getAllAccountByUserId(userId);
        List<String> spendNameItems = new ArrayList<>();
        List<String> accountNameItems = new ArrayList<>();
        List<BigDecimal>  flowMoneyItems= new ArrayList<>();

        String[] item = {"吃","喝","玩","乐","提高"};
        for(String it : item){
            spendNameItems.add(it);
        }
        for(Account it : accountIds){
            accountNameItems.add(it.getAccountName());
            flowMoneyItems.add(it.getAccountFreeMoney());
        }
        List<BigDecimal> spendNumItems= incomeOutgoMapper.getSpendNumByUserId(userId);
        List<BigDecimal> investMoneyItems = investMapper.getInvestMoneyByUserId(userId);
        return toView(spendNameItems,accountNameItems,spendNumItems,flowMoneyItems,investMoneyItems);
    }

    public CalculateView toView(List<String> spendNameItems, List<String> accountNameItems, List<BigDecimal> spendNum,List<BigDecimal> flowMoney, List<BigDecimal> investMoney){
        CalculateView calculateView = new CalculateView();
        calculateView.setAccountNameItems(accountNameItems);
        calculateView.setSpendNameItems(spendNameItems);
        calculateView.setSpendNumItems(spendNum);
        calculateView.setFlowMoneyItems(flowMoney);
        calculateView.setInvestMoneyItems(investMoney);
        return calculateView;
    }

    @Autowired
    AccountMapper accountMapper;
    @Autowired
    IncomeOutgoMapper incomeOutgoMapper;
    @Autowired
    InvestMapper investMapper;
}
