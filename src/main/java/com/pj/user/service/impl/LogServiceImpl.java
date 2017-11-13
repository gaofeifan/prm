package com.pj.user.service.impl;

import com.pj.user.mapper.LogOperationMapper;
import com.pj.user.mapper.LogPermissionsMapper;
import com.pj.user.pojo.Operation;
import com.pj.user.pojo.Permissions;
import com.pj.user.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by SenevBoy on 2017/11/9.
 */
@Service
public class LogServiceImpl implements LogService {

    @Autowired
   private LogOperationMapper logOperationMapper;
    @Autowired
    private LogPermissionsMapper logPermissionsMapper;

    @Override
    public List<Operation> findOPerationList(String startDate, String endDate, Boolean showDay) {
        return logOperationMapper.findOperationBydate(startDate,endDate,showDay);
    }

    @Override
    public List<Permissions> findPermissionsBydate(String startDate, String endDate, Boolean showWeekData) {
        return logPermissionsMapper.findPermissionsBydate(startDate,endDate,showWeekData);
    }

    @Override
    public void addOperationlLog(Operation operation) {
      logOperationMapper.insert(operation);
    }
}
