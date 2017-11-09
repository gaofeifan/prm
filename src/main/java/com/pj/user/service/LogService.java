package com.pj.user.service;

import com.pj.user.pojo.Operation;
import com.pj.user.pojo.RequestParam;

import java.util.Date;
import java.util.List;

/**
 * Created by SenevBoy on 2017/11/9.
 */
public interface LogService {
    // 分页 获取 普通用户操作日志
    List<Operation> findOPerationList(String startDate, String endDate);
}
