package com.pj.user.controller;

import com.pj.conf.base.BaseController;
import com.pj.mail.AutomaticReport.ScheduledEmail;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.boot.autoconfigure.web.BasicErrorController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.naming.ldap.BasicControl;
import java.util.Map;

/***
 * @ClassName: EmailController
 * @Description: 手动发送邮件支持  email测试
 * @author SenevBoy
 * @date 2017/12/9 12:50   
 **/
@RestController
@RequestMapping("/email")
@Api(value ="自动报表"  )
public class EmailController extends BaseController {


    @Resource
   private ScheduledEmail scheduledEmail;

    private static final  String  success_data_one_day = "上月新增Partner清单发送成功";
    private static final  String  error_data_one_day = "上月新增Partner清单发送失败";

    private static final  String  success_data_eight_hour = "签约在途Partner清单已发送成功";
    private static final  String  error_data_eight_hour = "签约在途Partner清单发送失败";

    private static final  String  success_data_maturity_date= "即将到期Partner清单发送成功";
    private static final  String  error_data_maturity_date = "即将到期Partner清单发送失败";
    //  用 hu信用等级
    @RequestMapping("/lastNew")
    @ResponseBody
    @ApiOperation(value = "上月新增Partner清单" , httpMethod = "GET" , response = Object.class)
    public Map<String ,Object> sendEmail1day() throws Exception {
        try {
           scheduledEmail.findPartnerDetailsLastMonthDate();
            return this.success(success_data_one_day);
        }catch (Exception e){
            System.out.println(e);
            return this.error(error_data_one_day);
        }

    }

    @RequestMapping("/signing")
    @ResponseBody
    @ApiOperation(value = "签约在途Partner清单" , httpMethod = "GET" , response = Object.class)
    public Map<String, Object> sendEmail8day() throws Exception {
        try {
           scheduledEmail.signingInTransit();
            return this.success(success_data_eight_hour);
        }catch (Exception e){
            System.out.println(e);
            return this.error(error_data_eight_hour);
        }
    }

    @RequestMapping("/warning")
    @ResponseBody
    @ApiOperation(value = "即将到期Partner清单" , httpMethod = "GET" , response = Object.class)
    public Map<String, Object> sendEmailwarning() throws Exception {
        try {
             System.err.println( "方法执行");
           scheduledEmail.IsAboutToExpire();
            return this.success(success_data_maturity_date);
        }catch (Exception e){
            System.out.println(e);
            return this.error(error_data_maturity_date);
        }
    }
}