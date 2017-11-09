package com.pj.user.service.impl;

import com.pj.user.mapper.LogOperationMapper;
import com.pj.user.pojo.Operation;
import com.pj.user.pojo.RequestParam;
import com.pj.user.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by SenevBoy on 2017/11/9.
 */
@Service
public class LogServiceImpl implements LogService {

    @Autowired
   private LogOperationMapper logOperationMapper;


    @Override
    public List<Operation> findOPerationList(String startDate, String endDate) {
        return logOperationMapper.findOperationBydate(startDate,endDate);
    }
}
