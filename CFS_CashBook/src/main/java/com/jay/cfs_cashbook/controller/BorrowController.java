package com.jay.cfs_cashbook.controller;

import com.jay.cfs_cashbook.bean.Account;
import com.jay.cfs_cashbook.bean.Borrow;
import com.jay.cfs_cashbook.bean.Debt;
import com.jay.cfs_cashbook.forms.UpdateBorrow;
import com.jay.cfs_cashbook.mapper.AccountMapper;
import com.jay.cfs_cashbook.mapper.BorrowMapper;
import com.jay.cfs_cashbook.mapper.DebtMapper;
import com.jay.cfs_cashbook.service.AccountService;
import com.jay.cfs_cashbook.views.BorrowView;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
public class BorrowController {
    @GetMapping("/borrow")
    @Transactional
    public List<BorrowView> index(@RequestParam("userId") Integer userId){
        List<Integer> accountIds = accountMapper.getAllCountIdByUserId(userId);
        List<Account> accounts = accountMapper.getAllAccountByUserId(userId);
        List<Borrow> borrows = borrowMapper.getBorrowsByAccountIds(accountIds);
        List<BorrowView> borrowViews = new ArrayList<>();
        for(Borrow borrow : borrows){
            borrowViews.add(toView(accounts,borrow));
        }
        return borrowViews;
    }
    private BorrowView toView(List<Account> lists, Borrow it){
        for(Account account : lists){
            if(account.getAccountId() == it.getAccountId()){
                BorrowView view = new BorrowView();
                view.setAccountId(it.getAccountId());
                view.setAccountName(account.getAccountName());
                view.setId(it.getBorrowId());
                view.setBorrowMoney(it.getBorrowMoney());
                LocalDate localDate = it.getBackTime().toLocalDate();
                LocalDate now = LocalDate.now();
                view.setBackTime(localDate.toEpochDay() - now.toEpochDay());
                view.setBorrowName(it.getBorrowName());
                return view;
            }
        }
        return null;
    }
    @PostMapping("/borrow")
    @Transactional
    public Map<String,String> add(@RequestBody Borrow borrow){
        log.info(borrow.toString());
        // 更新对应的accountId的金额扣除
        accountMapper.miuMoney(borrow.getAccountId(),borrow.getBorrowMoney());
        // 保存操作
        Integer save = borrowMapper.save(borrow);
        Map<String, String> map = accountService.judgeInteger(save, "插入失败", "插入成功");
        return map;
    }
    @PutMapping("/borrow")
    @Transactional
    public Map<String,String> update(@RequestBody UpdateBorrow borrow){
        BigDecimal borrowMoney = borrow.getBorrowMoney();
        Integer integer = accountMapper.updateDelteIncomeOutgo(borrow.getAccountId(), borrowMoney);
        borrowMapper.delete(borrow.getId());
       return accountService.judgeInteger(integer,"回款失败","回款成功");
    }
    @Autowired
    AccountMapper accountMapper;
    @Autowired
    BorrowMapper borrowMapper;
    @Autowired
    AccountService accountService;
    @Autowired
    DebtMapper debtMapper;
}
