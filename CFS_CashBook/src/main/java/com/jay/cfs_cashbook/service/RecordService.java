package com.jay.cfs_cashbook.service;

import com.jay.cfs_cashbook.bean.Account;
import com.jay.cfs_cashbook.bean.IncomeOutgo;
import com.jay.cfs_cashbook.mapper.AccountMapper;
import com.jay.cfs_cashbook.mapper.IncomeOutgoMapper;
import com.jay.cfs_cashbook.views.RecordView;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class RecordService {

    public List<RecordView> getRecordViews(Integer userId){
        List<Account> accountIds = accountMapper.getAllAccountByUserId(userId);
        List<IncomeOutgo> allByUserId = incomeOutgoMapper.getAllByUserId(userId);
        List<RecordView> lists = new ArrayList<>();
        for(IncomeOutgo it : allByUserId){
            RecordView view = getView(accountIds, it);
            lists.add(view);
        }
        return lists;
    }
    private RecordView getView(List<Account> accounts, IncomeOutgo it){
        RecordView recordView = new RecordView();
        for(Account account : accounts){
            if(account.getAccountId() == it.getAccountId()){
                recordView.setAccountId(account.getAccountId());
                recordView.setAccountName(account.getAccountName());
                recordView.setMoney(it.getMoney());
                recordView.setType(it.getType());
                recordView.setReason(it.getReason());
                recordView.setTime(it.getTime());
            }
        }
        return recordView;
    }
    @Autowired
    AccountMapper accountMapper;
    @Autowired
    IncomeOutgoMapper incomeOutgoMapper;
}
