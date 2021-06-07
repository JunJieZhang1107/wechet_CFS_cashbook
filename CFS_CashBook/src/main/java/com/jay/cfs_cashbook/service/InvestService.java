package com.jay.cfs_cashbook.service;

import com.jay.cfs_cashbook.bean.Account;
import com.jay.cfs_cashbook.bean.Invest;
import com.jay.cfs_cashbook.mapper.AccountMapper;
import com.jay.cfs_cashbook.mapper.InvestMapper;
import com.jay.cfs_cashbook.views.InvestView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
public class InvestService {

    // 保存投资数据
    @Transactional
    public Map<String,String> insert(Invest invest){
        Integer insert = investMapper.insert(invest);
        accountMapper.updateDelteIncomeOutgo(invest.getAccountId(),BigDecimal.valueOf(-1).multiply(invest.getInvestMoney()));
        Map<String, String> map = accountService.judgeInteger(insert, "保存失败", "保存成功");
        return map;
    }

    // 获取未结算的投资数据
    @Transactional
    public List<Invest> getAllInvest(Integer userId){
        List<Integer> allCountId = accountMapper.getAllCountIdByUserId(userId);
        List<Invest> lists = investMapper.getAllInvestByAccountIds(allCountId);
        return lists;
    }

    // 获取详细数据
    @Transactional
    public InvestView getDetailMsg(int accountId,int investId){
        Invest invest = investMapper.getInvestById(investId);
        Account account = accountMapper.getAccountNameByAccountId(accountId);
        InvestView investView = new InvestView();
        investView.setAccount(account);
        investView.setInvest(invest);
        return investView;
    }

    // 更新数据
    @Transactional
    public Map<String,String> update(Invest invest){
        if(!Objects.equals(invest.getBackMoney(),null))
        {
            BigDecimal subtract = invest.getBackMoney().subtract(invest.getInvestMoney());
            invest.setMinuDiff(subtract);
            invest.setBackTime(new Date(new java.util.Date().getTime()));
        }
        Integer update = investMapper.update(invest);
        accountMapper.updateDelteIncomeOutgo(invest.getAccountId(),invest.getBackMoney());
        Map<String, String> map = accountService.judgeInteger(update, "更新数据失败", "更新数据成功");
        return map;
    }
    @Autowired
    AccountMapper accountMapper;
    @Autowired
    InvestMapper investMapper;
    @Autowired
    AccountService accountService;
}
