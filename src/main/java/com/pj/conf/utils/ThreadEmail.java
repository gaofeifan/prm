package com.pj.conf.utils;

import com.pj.auth.service.AuthUserService;
import com.pj.mail.AutomaticReport.ScheduledEmail;
import com.pj.partner.pojo.PartnerDetails;
import com.pj.partner.pojo.PartnerLinkman;
import com.pj.partner.service.PartnerDetailsService;
import com.pj.partner.service.PartnerLinkmanService;
import com.pj.user.service.EmailService;
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
public class ThreadEmail  {

    @Autowired
    private PartnerLinkmanService partnerLinkmanService;

    @Autowired
    private   AuthUserService authUserService;

    @Autowired
    private     EmailService emailService;

    @Autowired
    private     PartnerDetailsService partnerDetailsService;

    public ThreadEmail(    PartnerDetailsService partnerDetailsService, AuthUserService authUserService, EmailService emailService,PartnerLinkmanService partnerLinkmanService) {
        this.partnerDetailsService =partnerDetailsService;
        this.authUserService=authUserService;
        this.emailService=emailService;
        this.partnerLinkmanService = partnerLinkmanService;
    }



    /**
     *  createBY   sevenBoyLiu
     *  验证 是否存在重复联系人 并发送邮件到提醒接受者
     */
    public   void checkPhoneSendEmail(HttpServletRequest requseet, List<PartnerLinkman> partnerLinkmanList, PartnerDetails partnerDetails){
        List<PartnerLinkman> partnerLinkmenAl = new ArrayList<PartnerLinkman>();
        if(null!=partnerLinkmanList  && partnerLinkmanList.size()!=0){
            for (PartnerLinkman parList : partnerLinkmanList){        // 循环联系人检测 是否存在重复联系人电话
                List<PartnerLinkman> partnerLinkmen = this.partnerLinkmanService.selectListByPhone(parList);
                // 循环集合排除本身信息
                if(null!=parList.getId()){//m  判断 此信息为更新 或者 新增
                    for (PartnerLinkman par:partnerLinkmen){   //更新 则 循环添加入集合中
                        if(!par.getId().equals(parList.getId())){
                            //  赋值 新旧合作伙伴中文名去全称
                            par.setNewchineseName(partnerDetails.getChineseName());
                            partnerLinkmenAl.add(par);
                        }
                    }
                } else {
                    partnerLinkmenAl.addAll(partnerLinkmen);  // 若为新增 则 存储所有
                }
            }
        }
        // 线程执行获取所有 提醒接受者信息 然后发送邮件
        if(null!=partnerLinkmenAl&& partnerLinkmenAl.size()!=0){
            requseet.getSession().setAttribute("partnerLinkmenAl", partnerLinkmenAl);
            CheckPhoneToSendEmail checkPhoneToSendEmail = new CheckPhoneToSendEmail(requseet,partnerDetailsService,   authUserService , emailService);
            checkPhoneToSendEmail.start();
        }
    }
};
@Component
class CheckPhoneToSendEmail extends Thread{

    private Queue<Integer> queue =  new LinkedList<Integer>();
    private int maxSize=1;
    private HttpServletRequest request;
    private PartnerDetailsService partnerDetailsService;
    private AuthUserService authUserService;
    private EmailService emailService;
    public CheckPhoneToSendEmail(HttpServletRequest request, PartnerDetailsService partnerDetailsService, AuthUserService authUserService, EmailService emailService) {
        this.request =request;
        this.partnerDetailsService =partnerDetailsService;
        this.authUserService=authUserService;
        this.emailService=emailService;
    }

    @Override
    public void run() {
        synchronized (queue) {
            try {
                ScheduledEmail scheduledEmail = new ScheduledEmail();
                // 发送邮件
                scheduledEmail.sendPhoneEmail(request,partnerDetailsService,authUserService,emailService);
            } catch (Exception ex) {
                ex.printStackTrace();
            }finally {
                //queue=null;
                System.out.println("重复电话邮件发送完毕！");
                //queue.notifyAll();
            }
        }
    }

};