package com.jay.cfs_cashbook.controller;
import com.jay.cfs_cashbook.bean.IncomeOutgo;
import com.jay.cfs_cashbook.forms.CreateExpend;
import com.jay.cfs_cashbook.service.IncomeOutgoService;
import com.jay.cfs_cashbook.views.IncomeOutgoView;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@Slf4j
public class IncomeOutgoController {
    // 保存支出、收入数据
    @PostMapping("/charge")
    public Map<String, String> index(@RequestBody IncomeOutgo incomeOutgo) {
        Map<String, String> save = incomeOutgoService.save(incomeOutgo);
        return save;
    }

    // 删除记录
    @DeleteMapping("/charge")
    public Map<String,String> destroy(@RequestBody IncomeOutgo incomeOutgo){
        Map<String, String> destroy = incomeOutgoService.destroy(incomeOutgo);
        return destroy;
    }

    // 获取所有列表数据
    @GetMapping("/charge")
    public List<IncomeOutgoView> getView(Integer userId) {
        List<IncomeOutgoView> update = incomeOutgoService.getData(userId);
        return update;
    }

    // 处理支出模块
    @PostMapping("/solveExpend")
    public Map<String, String> solveExpend(@RequestBody CreateExpend createExpend){ // 预算类型用来判断是什么金额的。
        log.info(createExpend.toString()) ;
        Map<String, String> expend = incomeOutgoService.expend(createExpend);
        return expend;
    }
    @Autowired
    IncomeOutgoService incomeOutgoService;

}
