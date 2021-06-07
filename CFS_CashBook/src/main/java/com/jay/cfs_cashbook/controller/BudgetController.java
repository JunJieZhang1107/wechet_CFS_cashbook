package com.jay.cfs_cashbook.controller;

import com.jay.cfs_cashbook.bean.Budget;
import com.jay.cfs_cashbook.service.BudgetService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@RestController
@Slf4j
public class BudgetController {
    // 查看本月是否有数据
    @GetMapping("/budget")
    public Budget getView(@RequestParam("userId") Integer userId){
        Budget data = budgetService.getData(userId);
        return data;
    }
    // 提交
    @PostMapping("/budget")
    public Map<String,String> save(@RequestBody Budget budget){
        log.info("post请求: "+ budget.toString());
        Map<String, String> save = budgetService.save(budget);
        return save;
    }

    // 更新
    @PutMapping("/budget")
    public Map<String,String> update(@RequestBody Budget budget) {
        log.info(budget.toString());
        Map<String, String> update = budgetService.update(budget);
        return update;
    }

    @Autowired
    BudgetService budgetService;
}
