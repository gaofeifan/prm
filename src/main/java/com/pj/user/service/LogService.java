package com.pj.user.service;

import com.pj.user.pojo.Operation;
import com.pj.user.pojo.Permissions;

import java.util.List;

/**
 * Created by SenevBoy on 2017/11/9.
 */
public interface LogService {
    // 分页 获取 用户操作日志
    List<Operation> findOPerationList(String startDate, String endDate, Boolean showDay);

    // 分页 获取 权限操作日志
    List<Permissions> findPermissionsBydate(String startDate, String endDate, Boolean showWeekData);

}
