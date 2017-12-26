package com.pj.conf.utils;

import com.pj.auth.pojo.User;
import com.pj.auth.service.AuthUserService;
import com.pj.mail.AutomaticReport.ScheduledEmail;
import com.pj.mail.util.SendEmailUtils;
import com.pj.partner.pojo.PartnerDetails;
import com.pj.partner.pojo.PartnerLinkman;
import com.pj.partner.service.PartnerDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/***
 * @ClassName: Thread
 * @Description: (这里用一句话描述这个类的作用)
 * @author SenevBoy
 * @date 2017/12/22 16:15   
 **/
@Component
public class ThreadEmail  extends  Thread{

    @Autowired
    private AuthUserService authUserService;
    @Autowired
    private PartnerDetailsService partnerDetailsService;

    private Queue<Integer> queue =  new LinkedList<Integer>();
    private int maxSize=1;
    private HttpServletRequest request;
    public ThreadEmail(HttpServletRequest request) {
        this.request =request;

    }

    @Override
    public void run() {
        while (true) {
            synchronized (queue) {
                    try {
                        ScheduledEmail scheduledEmail = new ScheduledEmail();
                        // 发送邮件
                        scheduledEmail.sendPhoneEmail(request,partnerDetailsService);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }finally {
                        queue=null;
                    }
            }
        }
    }
}
