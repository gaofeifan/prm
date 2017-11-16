package com.pj.mail.AutomaticReport;

import com.alibaba.dubbo.common.logger.Logger;
import com.alibaba.dubbo.common.logger.LoggerFactory;
import com.pj.auth.pojo.User;
import com.pj.mail.util.SendEmailUtils;
import com.pj.partner.pojo.PartnerDetails;
import com.pj.user.service.EmailService;

import com.pj.auth.service.AuthUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.*;
/**
 * Created by SenevBoy on 2017/11/15.
 */
@Component
public class ScheduledEmail {


    private static final Logger logger = LoggerFactory.getLogger(ScheduledEmail.class);

    @Autowired(required=false)
    private EmailService emailService;


    @Autowired(required=false)
    private AuthUserService authUserService;

    //7.1     月新增Partner清单 每月一日，把上月所有新增的Partner，触发邮件列表给Admin；

    //邮件内容如下：  XXXX年XX月新增Partner如下，如有问题，请联系相应Owner。

    //序号 代码 助记码 中文全称 中文简称 英文全称 英文简称 提醒接受者 业务范畴 合作伙伴分类

   // @Scheduled(cron="60*1000 * * * * ?")
    public void findPartnerDetailsLastMonthDate() throws Exception {
        // 获取  上月新增的 partner
        List<PartnerDetails> lastPtData = this.emailService.findPartnerDetailsLastMonthDate();
        StringBuffer mesagesVal = getMesagesVal(lastPtData);
        try {
            SendEmailUtils.sendEWmail(mesagesVal, "sevenboyliu@pj-l");
        }catch (Exception e){
            System.out.println(e);
        }
    }


    //  7.2     签约在途告警邮件
    //   7.2.1     每日早上8点，信用等级为签约在途的客户（外部客户 / 互为代理 / 海外代理），保存时间超过15个自然日的，即触发邮件列表给提醒接收者（Owner）；
    //   7.2.2     每日早上8点，信用等级为签约在途的客户（外部客户 / 互为代理 / 海外代理），保存时间超过30个自然日的，即触发邮件列表给Admin；
    //   邮件内容如下：
    //   以下客户信用等级为签约在途，保存时间已经超过15/30个自然日，请注意跟进。
    //      序号 代码 助记码 中文全称 中文简称 英文全称 英文简称 提醒接受者 业务范畴 合作伙伴分类

    // @Scheduled(cron="60*10 * * * * ?")
    public void signingInTransit() throws Exception {
        // 获取 签约在途 超过15 天的的信息
        List<PartnerDetails>  PartnerDetailsSigningInTransit = this.emailService.findPartnerDetailsGsigningInTransit();
        Integer lastReceiverID =0;
        if(null!=PartnerDetailsSigningInTransit){
            Set<Integer> checkDuplicates = new HashSet<Integer>();
            Set<Integer> checkDuplicates2 = new HashSet<Integer>();
            // 获取 hash集合 去除重复接受者 id
            for (PartnerDetails partnerDetails : PartnerDetailsSigningInTransit){
                checkDuplicates.add(partnerDetails.getReceiverId());
            }
            //遍历  hash结合 存储相同数据
            try {
            if(checkDuplicates.size()!=0){
                List<PartnerDetails> PartnerDetailsList2 = new ArrayList<PartnerDetails>();
                for (Integer it : checkDuplicates){
                    List<PartnerDetails> PartnerDetailsList = new ArrayList<PartnerDetails>();
                    // 循环list集合
                    for (PartnerDetails partnerDetails : PartnerDetailsSigningInTransit){
                        if(partnerDetails.getReceiverId()==it){
                            // 所有大于  15 天的 相同提醒人
                            PartnerDetailsList.add(partnerDetails);
                            int num = differentDaysByMillisecond(partnerDetails.getCreateDate(), new Date());
                            if(num>30){
                                // 所有大于  30 天的 相同提醒人
                                PartnerDetailsList2.add(partnerDetails);
                            }
                        }
                    }
                    try {
                        // 调用接口 获取 email 发给 接收者
                        User user = authUserService.selectUserByEmail(PartnerDetailsList.get(0).getReceiverId());
                        // 邮件正文
                        StringBuffer mesagesVal = getMesagesVal(PartnerDetailsList);
                        SendEmailUtils.sendEWmail(mesagesVal,user.getEmail());
                    }catch (Exception e){
                        // 发送失败之后  存储
                         checkDuplicates2.add(PartnerDetailsList.get(0).getReceiverId());
                        logger.error("-- 发送给提醒接受者 的邮件发送失败 已存储并已转发通知管理员--"+e);
                    }
                }

            /*  将所有超过30天的信息发送给管理者    */
                if(PartnerDetailsList2.size()!=0){
                    try {
                        // 邮件正文
                        StringBuffer mesagesVal = getMesagesVal(PartnerDetailsList2);
                        SendEmailUtils.sendEWmail(mesagesVal,"");
                    }catch (Exception e){
                        logger.error("---发送给管理者的 签约在途信息统计邮件发送失败   "+e);

                    }
                }
                // 发送给接受者邮件失败后整体发送给 管理者
                List<User> userList = new ArrayList<>();
                if(checkDuplicates2.size()!=0){
                    for (Integer ids : checkDuplicates2){
                        // 调用接口 获取 接收者email
                        userList.add( authUserService.selectUserByEmail(ids));
                    }
                        try {
                            // 邮件正文
                            StringBuffer mesagesVal = getExceptionMesagesVal(userList);
                            SendEmailUtils.sendEWmail(mesagesVal,"");
                        }catch (Exception e){
                            logger.error("---发送给管理者的异常信息统计邮件发送失败  ： "+e);
                        }
                }
            }
            }catch (Exception e){
                logger.error("---信用等级为签约在途的客户存储超越15/30天邮件信息处理异常  ： "+e);
            }
        }
    }


/*



            7.3     付款买单告警邮件（预留）
            7.3.1     每日，信用等级为付款买单的客户（外部客户 / 互为代理 / 海外代理），如应收账龄超过7个自然日的，即触发邮件列表给提醒接收者（Owner）；
            7.3.2     每日，信用等级为付款买单的客户（外部客户 / 互为代理 / 海外代理），如应收账龄超过15个自然日的，即触发邮件列表给Admin；
            7.3.3     预留功能，应收数据来源于财务系统或报表系统；
            序号 姓名 所属公司 所属部门 岗位 手机号 邮箱 名下合作伙伴中文简称
*/

    /*
    7.5     僵尸Partner提醒邮件（预留）
    7.5.1     每日，对于300天（等于，只当天提醒一次）没有业务的僵尸Partner，且没有停用，即触发邮件列表给提醒接收者（Owner）；
    7.5.2     每日，对于365天以上没有业务的僵尸Partner，且没有停用，即触发邮件列表给Admin；
    7.5.3     预留功能，是否有业务的数据来源于各BSS；
    */



    public static String getLastdata( ) {
        Calendar calendar = Calendar.getInstance();//日历对象
        calendar.setTime(new Date());//设置当前日期
        calendar.add(Calendar.MONTH, 1);//月份减一
        SimpleDateFormat simpledate = new SimpleDateFormat("YYYY-MM");
        return  simpledate.format(calendar.getTime()) ;// 上个月的日期
    }

    // 获取邮件内容
    public StringBuffer getMesagesVal(List<PartnerDetails> PartnerDetailsList){
        StringBuffer theMessage = new StringBuffer();
        String lastdata = getLastdata();
        theMessage.append("<h2><font >"+lastdata+"月 新增Partner如下，如有问题，请联系相应Owner.</font></h2>");
        theMessage.append("<hr>");
        theMessage.append("<table  border='1'>");
        theMessage.append("<tr><td>序号</td><td>代码</td><td>助记码</td><td>中文全称</td><td>中文简称</td><td>英文全称</td><td>英文简称</td>" +
                "<td>提醒接受者</td><td>业务范畴</td><td>合作伙伴分类</td></tr>");
        if(null!=PartnerDetailsList){
            Integer i   = 1;
            for (PartnerDetails partnerDetails : PartnerDetailsList){
                theMessage.append("<tr><td>"+ i++ +"</td><td>"+partnerDetails.getCode()+"</td><td>"+partnerDetails.getMnemonicCode()+"</td><td>"+partnerDetails.getChineseName()+"</td><td>"+partnerDetails.getChineseAbbreviation()+"</td><td>"+partnerDetails.getEnglishName()+"</td>" +
                        "<td>"+partnerDetails.getEnglishAbbreviation()+"</td><td>"+partnerDetails.getReceiverName()+"</td><td>"+partnerDetails.getScopeBusiness()+"</td><td>"+partnerDetails.getPartnerCategory()+"</td></tr>");
            }
        }
        theMessage.append("</table>");
        return theMessage;
    }

    /*异常通知邮件*/
    public StringBuffer getExceptionMesagesVal(List<User> user){
        StringBuffer theMessage = new StringBuffer();
        theMessage.append("<h2><font >以下提醒接收者（Owner）无效，请注意跟进。</font></h2>");
        theMessage.append("<hr>");
        theMessage.append("<table  border='1'>");
        theMessage.append("<tr><td>序号</td><td>姓名</td><td>所属公司</td><td>所属部门</td><td>岗位</td><td>手机号</td><td>邮箱</td>");
        Integer i   = 1;
        if(null!=user){
            for (User use : user){
                theMessage.append("<tr><td>"+ i++ +"</td><td>"+use.getUsername()+"</td><td>"+use.getCompanyname()+"</td><td>"+use.getDempname()+"</td><td>"+use.getPostname()+"</td><td>"+use.getEmail()+"</td></tr>");
            }
        }
        theMessage.append("</table>");
        return theMessage;
    }


    // 时差计算
    /**
     * 通过时间秒毫秒数判断两个时间的间隔
     * @param date1
     * @param date2
     * @return
     */
    public static int differentDaysByMillisecond(Date date1,Date date2)
    {
        int days = (int) ((date2.getTime() - date1.getTime()) / (1000*3600*24));
        return days;
    }


}
