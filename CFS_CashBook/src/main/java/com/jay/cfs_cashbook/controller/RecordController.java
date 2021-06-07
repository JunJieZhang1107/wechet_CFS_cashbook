package com.jay.cfs_cashbook.controller;

import com.jay.cfs_cashbook.service.RecordService;
import com.jay.cfs_cashbook.views.RecordView;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.util.List;

@RestController
@Slf4j
public class RecordController {
    // 获取信息操作
    // 通过userId 查看对应账户信息
    // 通过账户信息获取对应的收入支出信息
    //　获取到的信息要对时间进行排序。
    @GetMapping("/record")
    public List<RecordView> index(@RequestParam("userId") int userId){
        log.info(String.valueOf(userId));
        List<RecordView> recordViews = recordService.getRecordViews(userId);
        log.info(recordViews.toString());
        return recordViews;
    }
    @Autowired
    RecordService recordService;
}
