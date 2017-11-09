package com.pj.user.controller;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.pj.conf.base.BaseController;
import com.pj.conf.base.page.Pagination;
import com.pj.user.pojo.Operation;
import com.pj.user.pojo.RequestParam;
import com.pj.user.service.LogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * Created by SenevBoy on 2017/11/9.
 */
@RestController
@RequestMapping("/log")
@Api(value ="日志查询模块"  )
public class LogController extends BaseController {

            @Autowired
            LogService logService;


    @ApiOperation(value = "日志-普通用户-分页与时间条件查询" , httpMethod = "POST" , response = Object.class)
    @RequestMapping("/operation")
    @ResponseBody
    public Map<String, Object> findOperationList(@ModelAttribute("requestParam")RequestParam requestParam ){
        // 获取 分页元数据信息
        Page<Object> page = PageHelper.startPage(Pagination.cpn(requestParam.getPageNo()), requestParam.getPageSize(), true);
        List<Operation> operationLsit =  logService.findOPerationList(requestParam.getStartDate(),requestParam.getEndDate());
        Pagination pagination = new Pagination(page.getPageNum(), page.getPageSize(), (int) page.getTotal(), operationLsit);
        return  this.success(pagination);
    }
}