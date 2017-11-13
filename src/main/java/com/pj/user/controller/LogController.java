package com.pj.user.controller;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.pj.conf.base.BaseController;
import com.pj.conf.base.page.Pagination;
import com.pj.user.pojo.Operation;
import com.pj.user.pojo.Permissions;
import com.pj.user.pojo.RequestParam;
import com.pj.user.service.LogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
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


    @ApiOperation(value = "日志- 1.操作日志查询 ；" , httpMethod = "POST" , response = Object.class)
    @RequestMapping("/operation")
    @ResponseBody
    public Map<String, Object> findOperationList(@ModelAttribute("requestParam")RequestParam requestParam ){
        // 获取 分页元数据信息

            Page<Object> page = PageHelper.startPage(Pagination.cpn(requestParam.getPageNo()), requestParam.getPageSize(), true);
            List<Operation> operationLsit =  logService.findOPerationList(requestParam.getStartDate(),requestParam.getEndDate(),null);
            Pagination pagination = new Pagination(page.getPageNum(), page.getPageSize(), (int) page.getTotal(), operationLsit);
            return  this.success(pagination);

    }



    @ApiOperation(value = "日志-1.。权限日志查询  " , httpMethod = "POST" , response = Object.class)
    @RequestMapping("/permissions")
    @ResponseBody
    public Map<String, Object> findPermissionsList(@ModelAttribute("requestParam")RequestParam requestParam ){

            // 获取 分页元数据信息
            Page<Object> page = PageHelper.startPage(Pagination.cpn(requestParam.getPageNo()), requestParam.getPageSize(), true);
            List<Permissions> operationLsit = logService.findPermissionsBydate(requestParam.getStartDate(),requestParam.getEndDate(),null);
            Pagination pagination = new Pagination(page.getPageNum(), page.getPageSize(), (int) page.getTotal(), operationLsit);
            return  this.success(pagination);

    }
    @ApiOperation(value = "日志-  2. 首页日志 展示当天数据  与 展示一周数据" , httpMethod = "POST" , response = Object.class)
    @RequestMapping("/home")
    @ResponseBody
    public Map<String, Object> findHomeLog(){
        Map<String,Object>   map  = new HashMap<String,Object>();
        List<Operation> operationLsit =  logService.findOPerationList(null,null,true);
        List<Permissions> permissionsLsit = logService.findPermissionsBydate(null,null,true);
        map.put("operationOneDayLsit",operationLsit);
        map.put("permissionsOneWeekLsit",permissionsLsit);
        return  this.success(map);
    }

}