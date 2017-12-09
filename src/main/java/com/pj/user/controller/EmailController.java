package com.pj.user.controller;

import com.pj.mail.AutomaticReport.ScheduledEmail;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;

/***
 * @ClassName: EmailController
 * @Description: 手动发送邮件支持  email测试
 * @author SenevBoy
 * @date 2017/12/9 12:50   
 **/
@RestController
@RequestMapping("/log")
@Api(value ="日志查询模块"  )
public class EmailController {


    @Resource
   private ScheduledEmail scheduledEmail;

    //  用 hu信用等级
    @RequestMapping("/one")
    @ResponseBody
    @ApiOperation(value = "每月1号邮件" , httpMethod = "GET" , response = Object.class)
    public void sendEmail1day() throws Exception {

        scheduledEmail.findPartnerDetailsLastMonthDate();
    }

    @RequestMapping("/eight")
    @ResponseBody
    @ApiOperation(value = "每天八点邮件" , httpMethod = "GET" , response = Object.class)
    public void sendEmail8day() throws Exception {

        scheduledEmail.signingInTransit();

    }


}
