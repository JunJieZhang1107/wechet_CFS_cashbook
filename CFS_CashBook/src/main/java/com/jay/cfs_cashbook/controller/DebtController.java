package com.jay.cfs_cashbook.controller;

import com.jay.cfs_cashbook.bean.Account;
import com.jay.cfs_cashbook.bean.Debt;
import com.jay.cfs_cashbook.forms.UpdateDebt;
import com.jay.cfs_cashbook.mapper.AccountMapper;
import com.jay.cfs_cashbook.mapper.DebtMapper;
import com.jay.cfs_cashbook.service.AccountService;
import com.jay.cfs_cashbook.views.DebtView;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
public class DebtController { // 债务控制类
    @GetMapping("/debt")
    public List<DebtView> index(Integer userId){
        List<Account> accounts = accountMapper.getAllAccountByUserId(userId);
        List<Integer> accountIds = accountMapper.getAllCountIdByUserId(userId);
        log.info(accounts.toString());
        log.info(accountIds.toString());
        // 获取对应的id,通过查找信息.
        List<Debt> debts = debtMapper.getDebtsByAccountList(accountIds);
        List<DebtView> lists = new ArrayList<>();
        log.info(debts.toString());
        for(Debt debt : debts){
            DebtView debtView = toView(accounts, debt);
            lists.add(debtView);
        }
        return lists;
    }

    private DebtView toView(List<Account> accounts,Debt it){
        DebtView debtView = new DebtView();
        for(Account account : accounts){
            if(account.getAccountId() == it.getAccountId()){
                debtView.setId(it.getDebtId());
                debtView.setAccountId(account.getAccountId());
                debtView.setAccountName(account.getAccountName());
                debtView.setDebtPay(it.getDebtPay());
                LocalDate localDate = it.getPayTime().toLocalDate();
                LocalDate localDate1 = LocalDate.now();
                long l = localDate1.toEpochDay() - localDate.toEpochDay();
                debtView.setPayTime(l);
            }
        }
        return debtView;
    }
    @PostMapping("/debt")
    public Map<String,String> add(@RequestBody Debt debt){
        log.info("debt:" + debt.toString());
        Integer insert = debtMapper.insert(debt);
        Map<String, String> map = accountService.judgeInteger(insert, "插入失败", "插入成功");
        return map;
    }
    @PutMapping("/debt")
    @Transactional
    public Map<String,String> update(@RequestBody UpdateDebt it){
        log.info(it.toString());
        BigDecimal debtPay = it.getDebtPay();
        debtPay = BigDecimal.valueOf(-1).multiply(debtPay);
        Integer integer = accountMapper.updateDelteIncomeOutgo(it.getAccountId(), debtPay);
        debtMapper.updateIsPay(it.getId(),true);
        return accountService.judgeInteger(integer,"删除失败","删除成功");
    }
    @Autowired
    DebtMapper debtMapper;
    @Autowired
    AccountService accountService;
    @Autowired
    AccountMapper accountMapper;
}
