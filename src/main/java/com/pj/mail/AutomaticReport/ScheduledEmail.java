package com.pj.mail.AutomaticReport;
import com.alibaba.dubbo.common.logger.Logger;

import com.alibaba.dubbo.common.logger.LoggerFactory;
import com.pj.auth.pojo.User;
import com.pj.mail.util.SendEmailUtils;
import com.pj.partner.pojo.PartnerDetails;
import com.pj.partner.pojo.PartnerLinkman;
import com.pj.partner.service.PartnerDetailsService;
import com.pj.user.service.EmailService;
import com.pj.auth.service.AuthUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.*;
/**
 * Created by SenevBoy on 2017/11/15.
 */
@Component
public class ScheduledEmail {

    private static final Logger logger = LoggerFactory.getLogger(ScheduledEmail.class);
  //  private static final String basic_myEmailAccount = "gaofeifan@pj-l.com";   // 个人
  // private static final String basic_myEmailPassword ="PJgff.1234";
    private static final String basic_myEmailAccount = "eams@pj-l.com"; // 测试
    private static final String basic_myEmailPassword ="qwe.12345";
 //   private static final String basic_manager ="sevenboyliu@pj-l.com"; // 测试
 private static final String basic_manager ="";


    @Autowired
    private EmailService emailService;

    @Autowired
    private AuthUserService authUserService;

    //7.1     月新增Partner清单 每月一日，把上月所有新增的Partner，触发邮件列表给Admin；

    //邮件内容如下：  XXXX年XX月新增Partner如下，如有问题，请联系相应Owner。

    //序号 代码 助记码 中文全称 中文简称 英文全称 英文简称 提醒接受者 业务范畴 合作伙伴分类

    //@Scheduled(cron="0 15 0 1 * ?")
    public void findPartnerDetailsLastMonthDate() throws Exception {
        // 获取  上月新增的 partner
        try {
        List<PartnerDetails> lastPtData = this.emailService.findPartnerDetailsLastMonthDate();
        if (null ==lastPtData){

            String total = getLastdata()+"月 本月无新增人员";
            StringBuffer theMessage = new StringBuffer();
            SendEmailUtils.sendEWmail(theMessage,basic_myEmailAccount ,basic_myEmailPassword,getadmin());
        }else{
            String total = getLastdata()+"月 新增Partner如下，如有问题，请联系相应Owner.";
            StringBuffer mesagesVal = getMesagesVal(lastPtData,total);
            SendEmailUtils.sendEWmail(mesagesVal,basic_myEmailAccount ,basic_myEmailPassword,getadmin());
        }
        }catch (Exception e){
          logger.error("月新增Partner清单 邮件信息获取异常请检查 exception   :" +e );
        }
    }


    //  7.2     签约在途告警邮件
    //   7.2.1     每日早上8点，信用等级为签约在途的客户（外部客户 / 互为代理 / 海外代理），保存时间超过15个自然日的，即触发邮件列表给提醒接收者（Owner）；
    //   7.2.2     每日早上8点，信用等级为签约在途的客户（外部客户 / 互为代理 / 海外代理），保存时间超过30个自然日的，即触发邮件列表给Admin；
    //   邮件内容如下：
    //   以下客户信用等级为签约在途，保存时间已经超过15/30个自然日，请注意跟进。
    //      序号 代码 助记码 中文全称 中文简称 英文全称 英文简称 提醒接受者 业务范畴 合作伙伴分类

    // @Scheduled(cron="60*10 * * * * ?")
     //@Scheduled(cron="0 0 8 * * ?")
    public void signingInTransit() throws Exception {
        // 获取 签约在途 超过15 天的的信息
        List<PartnerDetails>  PartnerDetailsSigningInTransit = this.emailService.findPartnerDetailsGsigningInTransit();
        Integer lastReceiverID =0;
        if(null!=PartnerDetailsSigningInTransit && PartnerDetailsSigningInTransit.size()!=0){
            Set<Integer> checkDuplicates = new HashSet<Integer>();
            Set<Integer> checkDuplicates2 = new HashSet<Integer>();
            // 获取 hash集合 去除重复接受者 id
            for (PartnerDetails partnerDetails : PartnerDetailsSigningInTransit){
                checkDuplicates.add(partnerDetails.getReceiverId());
            }
            //遍历  hash结合 存储相同数据
            try {
            if(checkDuplicates.size()!=0){
                List<PartnerDetails> PartnerDetailsList2 = new ArrayList<PartnerDetails>();      //   /*大于30 天的 集合*/
                for (Integer it : checkDuplicates){
                    List<PartnerDetails> PartnerDetailsList = new ArrayList<PartnerDetails>();              //  /*大于15 天的集合*/
                    // 循环list集合
                    for (PartnerDetails partnerDetails : PartnerDetailsSigningInTransit){
                        if(partnerDetails.getReceiverId().equals(it)){
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
                        User user = authUserService.selectPersonById(PartnerDetailsList.get(0).getReceiverId());
                        // 邮件正文
                      //  checkDuplicates2.add(PartnerDetailsList.get(0).getReceiverId());
                        if(PartnerDetailsList.size()!=0){
                        String total = " 以下客户信用等级为签约在途，保存时间已经超过15/30个自然日，请注意跟进。";
                        StringBuffer mesagesVal = getMesagesVal(PartnerDetailsList,total);
                        SendEmailUtils.sendEWmail(mesagesVal, basic_myEmailAccount, basic_myEmailPassword, user.getEmail()); // user.getEmail()
                        }
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
                        String total = " 以下客户信用等级为签约在途，保存时间已经超过15/30个自然日，请注意跟进。";
                        StringBuffer mesagesVal = getMesagesVal(PartnerDetailsList2,total);
                        SendEmailUtils.sendEWmail(mesagesVal, basic_myEmailAccount, basic_myEmailPassword, getadmin());
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

                /*
                7.5     僵尸Partner提醒邮件（预留）
                7.5.1     每日，对于300天（等于，只当天提醒一次）没有业务的僵尸Partner，且没有停用，即触发邮件列表给提醒接收者（Owner）；
                7.5.2     每日，对于365天以上没有业务的僵尸Partner，且没有停用，即触发邮件列表给Admin；
                7.5.3     预留功能，是否有业务的数据来源于各BSS；
                */
                            // 邮件正文
                            String total = " 以下提醒接收者（Owner）无效，请注意跟进。";
                            StringBuffer mesagesVal = getExceptionMesagesVal(userList);
                            SendEmailUtils.sendEWmail(mesagesVal, basic_myEmailAccount, basic_myEmailPassword, getadmin());
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



    //   邮件自动报表里面增加，有效期即将到期的提醒（距离到期日相差45天或者小于45天）：
    //           1、根据合作伙伴中的有效期结束日期和当前日期进行计算
    //   每天发一次，发给相应的提醒接受者，邮件格式见详情


    // @Scheduled(cron="60*10 * * * * ?")
    //@Scheduled(cron="0 0 8 * * ?")
    public void IsAboutToExpire() throws Exception {
                try {
                    // 获取所有快到期的合作伙伴信息
                    List<PartnerDetails> IsAboutToExpireData = this.emailService.findPartnerDetailsLastIsAboutToExpire();
                    if(null!=IsAboutToExpireData || IsAboutToExpireData.size()!=0) {
                        // 发送邮件
                        //整理提醒接受者
                        HashSet<Integer> receiverIds = new HashSet<Integer>();
                        for (PartnerDetails datasList : IsAboutToExpireData) {
                            receiverIds.add(datasList.getReceiverId());
                        }

                        // 存储失败接受者人员信息
                        HashSet<Integer> failurePersonnel = new HashSet<Integer>();
                        // 遍历 接受者 并发送邮件
                        for (Integer receiverId : receiverIds) {

                            List<PartnerDetails> sendEmailsDatas = new ArrayList<PartnerDetails>();
                            for (PartnerDetails datasList : IsAboutToExpireData) {
                                if (datasList.getReceiverId().equals(receiverId)) {
                                    sendEmailsDatas.add(datasList);
                                }
                            }
                            //发送邮件到接受者
                            if (null != sendEmailsDatas && sendEmailsDatas.size() != 0) {
                                // 调用接口 获取 email 发给 接收者
                                User user = authUserService.selectUserByEmail(sendEmailsDatas.get(0).getReceiverId());
                                try {
                                    // 邮件正文
                                    String total = "以下即将到期清单，请注意跟进。";
                                    StringBuffer mesagesVal = getMesagesValExpiry(sendEmailsDatas, total);
                                    SendEmailUtils.sendEWmail(mesagesVal, basic_myEmailAccount, basic_myEmailPassword, user.getEmail());
                                } catch (Exception e) {
                                    failurePersonnel.add(sendEmailsDatas.get(0).getReceiverId());
                                    logger.error("有效期即将到期Partner清单 邮件信息获取异常请检查 exception 信息已存储稍后发送给管理员失败接受者信息   :" + e);

                                }
                            }
                        }
                        // 发送失败人员信息到管理员
                        // 发送给接受者邮件失败后整体发送给 管理者
                        List<User> userList = new ArrayList<>();
                        if (failurePersonnel.size() != 0) {
                            for (Integer ids : failurePersonnel) {
                                // 调用接口 获取 接收者email
                                userList.add(authUserService.selectUserByEmail(ids));
                            }

                        try {
                            String total = " 以下提醒即将到期Partner清单接收者（Owner）无效，请注意跟进。";
                            StringBuffer mesagesVal = getExceptionMesagesVal(userList);
                            SendEmailUtils.sendEWmail(mesagesVal, basic_myEmailAccount, basic_myEmailPassword, getadmin());
                        }catch (Exception e){
                            logger.error("---即将到期Partner清单接收者（Owner）无效发送给管理者的异常信息统计邮件发送失败  ： "+e);
                        }
                        }
                    }
                }catch (Exception e){
                    logger.error("有效期即将到期Partner清单 邮件信息获取异常请检查 exception   :" +e );
                }
    }


    //  联系人电话重复提醒邮件
    public void sendPhoneEmail(HttpServletRequest request, PartnerDetailsService partnerDetailsService, AuthUserService authUserService, EmailService emailService) throws Exception {
        this.authUserService = authUserService;
        this.emailService = emailService;
        HashSet<Integer> partnerDetaisl = new HashSet<Integer>();  // 所有合作伙伴id集合

        // 获取请求中的  联系人集合
        List<PartnerLinkman> partnerLinkmenAl = (  ArrayList<PartnerLinkman>)request.getSession().getAttribute("partnerLinkmenAl");
        for  ( PartnerLinkman parData : partnerLinkmenAl){
            partnerDetaisl.add(parData.getDetailsId());
        }
        // 存储失败接受者人员信息
        HashSet<Integer> failurePersonnel = new HashSet<Integer>();

        for (Integer  detailsId  : partnerDetaisl){
            if(null!=detailsId) {
                List<PartnerLinkman> emailListData = new ArrayList<PartnerLinkman>(); // 所有待发送邮件的 人员集合 根据 接受者分类
                for (PartnerLinkman parData : partnerLinkmenAl) {
                    if (parData.getDetailsId().equals(detailsId)) {
                        PartnerDetails partnerDetails = partnerDetailsService.selectByPrimaryKey(detailsId);
                        parData.setOldchineseName(partnerDetails.getChineseName());
                        parData.setReceiverId(partnerDetails.getReceiverId());
                        emailListData.add(parData);
                    }
                }

                if (null != emailListData && emailListData.size() != 0) {
                    // 获取 邮箱并发送邮件
                    //发送邮件到接受者
                    // 调用接口 获取 email 发给 接收者
                    User user = this.authUserService.selectUserByEmail(emailListData.get(0).getReceiverId());
                    try {

                        // 邮件正文
                        String total = "新增合作伙伴（中文全称）维护了新的联系人，与您名下的合作伙伴（中文全称）中的以下联系人重复，请核实。。";
                        StringBuffer mesagesVal = getMesagesValPartnerLinkman(emailListData, total);
                        SendEmailUtils.sendEWmail(mesagesVal, ScheduledEmail.basic_myEmailAccount, basic_myEmailPassword, user.getEmail());
                    } catch (Exception e) {

                        failurePersonnel.add(emailListData.get(0).getReceiverId());
                        logger.error("  邮件信息获取异常请检查 exception 信息已存储稍后发送给管理员失败接受者信息   :" + e);
                    }
                }
            }
        }

        // 发送失败人员信息到管理员
        // 发送给接受者邮件失败后整体发送给 管理者
        List<User> userList = new ArrayList<>();
        if (failurePersonnel.size() != 0) {
            for (Integer ids : failurePersonnel) {
                // 调用接口 获取 接收者email
                userList.add(this.authUserService.selectUserByEmail(ids));
            }

        try {
            String total = " 以下提醒重複（Owner）无效，请注意跟进。";
            StringBuffer mesagesVal = getExceptionMesagesVal(userList);
            SendEmailUtils.sendEWmail(mesagesVal, basic_myEmailAccount, basic_myEmailPassword, getadmin());
        }catch (Exception e){
            logger.error("--- 接收者（Owner）无效发送给管理者的异常信息统计邮件发送失败  ： "+e);
        }
        }
    }


    public static String getLastdata( ) {
        Calendar calendar = Calendar.getInstance();//日历对象
        calendar.setTime(new Date());//设置当前日期
        calendar.add(Calendar.MONTH, -1);//月份减一
        SimpleDateFormat simpledate = new SimpleDateFormat("YYYY-MM");
        return  simpledate.format(calendar.getTime()) ;// 上个月的日期
    }


    // 获取邮件内容 联系人重复邮件信息
    public StringBuffer getMesagesValPartnerLinkman(List<PartnerLinkman> PartnerDetailsList, String total){
        StringBuffer theMessage = new StringBuffer();

        theMessage.append("<h2><font >"+total+"</font></h2>");
        theMessage.append("<hr>");
        theMessage.append("<table  border='1'>");
        theMessage.append("<tr><td>序号</td><td>联系人名称</td><td>新合作伙伴中文全称</td><td>新合作伙伴中文全称</td><td>职责</td><td>部门</td><td>职务</td><td>固话</td>" +
                "<td>手机</td><td>邮件</td><td>微信</td><td>QQ</td><td>地址</td></tr>");
        if(null!=PartnerDetailsList){
            Integer i   = 1;
            for (PartnerLinkman partnerDetails : PartnerDetailsList){
                theMessage.append("<tr><td>"+ i++ +"</td><td>"+partnerDetails.getNewchineseName() +"</td><td>"+partnerDetails.getOldchineseName() +"</td><td>"+ (partnerDetails.getObligation()==null?"":partnerDetails.getObligation()) +"</td><td>"+(partnerDetails.getDemp()==null?"":partnerDetails.getDemp()) +"</td><td>"+(partnerDetails.getFixPhone()==null?"":partnerDetails.getFixPhone()) +"</td>" +
                        "<td>"+ (partnerDetails.getPhone()==null?"":partnerDetails.getPhone()) +"</td><td>"+(partnerDetails.getEmail()==null?"":partnerDetails.getEmail())+"</td><td>"+(partnerDetails.getWeChat()==null?"":partnerDetails.getWeChat())+"</td><td>"+(partnerDetails.getQq()==null?"":partnerDetails.getQq()) +"</td>" +
                        "<td>"+(partnerDetails.getAddress()==null?"":partnerDetails.getAddress())+"</td></tr>");
            }
        }
        theMessage.append("</table>");
        return theMessage;
    }


    // 获取邮件内容
    public StringBuffer getMesagesVal(List<PartnerDetails> PartnerDetailsList,String total){
        StringBuffer theMessage = new StringBuffer();

        theMessage.append("<h2><font >"+total+"</font></h2>");
        theMessage.append("<hr>");
        theMessage.append("<table  border='1'>");
        theMessage.append("<tr><td>序号</td><td>代码</td><td>助记码</td><td>中文全称</td><td>中文简称</td><td>英文全称</td><td>英文简称</td>" +
                "<td>提醒接受者</td><td>业务范畴</td><td>合作伙伴分类</td></tr>");
        if(null!=PartnerDetailsList){
            Integer i   = 1;
            for (PartnerDetails partnerDetails : PartnerDetailsList){
                theMessage.append("<tr><td>"+ i++ +"</td><td>"+partnerDetails.getCode() +"</td><td>"+partnerDetails.getMnemonicCode() +"</td><td>"+partnerDetails.getChineseName() +"</td><td>"+partnerDetails.getChineseAbbreviation() +"</td><td>"+partnerDetails.getEnglishName() +"</td>" +
                        "<td>"+partnerDetails.getEnglishAbbreviation() +"</td><td>"+partnerDetails.getReceiverName() +"</td><td>"+partnerDetails.getScopeBusiness() +"</td><td>"+partnerDetails.getPartnerCategory() +"</td></tr>");
            }
        }
        theMessage.append("</table>");
        return theMessage;
    }



    // 获取邮件内容 即将到期邮件正文
    public StringBuffer getMesagesValExpiry(List<PartnerDetails> PartnerDetailsList,String total){
        StringBuffer theMessage = new StringBuffer();

        theMessage.append("<h2><font >"+total+"</font></h2>");
        theMessage.append("<hr>");
        theMessage.append("<table  border='1'>");
        theMessage.append("<tr><td>序号</td><td>代码</td><td>助记码</td><td>中文全称</td><td>中文简称</td><td>英文全称</td><td>英文简称</td>" +
                "<td>提醒接受者</td><td>业务范畴</td><td>合作伙伴分类</td><td>有效期开始日</td><td>有效期结束日</td></tr>");
        if(null!=PartnerDetailsList){
            Integer i   = 1;
            for (PartnerDetails partnerDetails : PartnerDetailsList){
                theMessage.append("<tr><td>"+ i++ +"</td><td>"+partnerDetails.getCode() +"</td><td>"+partnerDetails.getMnemonicCode() +"</td><td>"+partnerDetails.getChineseName() +"</td><td>"+partnerDetails.getChineseAbbreviation() +"</td><td>"+partnerDetails.getEnglishName() +"</td>" +
                        "<td>"+partnerDetails.getEnglishAbbreviation() +"</td><td>"+partnerDetails.getReceiverName() +"</td><td>"+partnerDetails.getScopeBusiness() +"</td><td>"+partnerDetails.getPartnerCategory() +"</td>" +
                        "<td>"+partnerDetails.getMaturityDateBegan() +"</td><td>"+partnerDetails.getMaturityDateEnd() +"</td></tr>");
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
                theMessage.append("<tr><td>"+ i++ +"</td><td>"+use.getUsername() +"</td><td>"+use.getCompanyname() +"</td><td>"+use.getDempname() +"</td><td>"+use.getPostname() +"</td><td>"+use.getPhone() +"</td><td>"+use.getEmail() +"</td></tr>");
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
    public static int differentDaysByMillisecond(Date date1,Date date2) {
        int days = (int) ((date2.getTime() - date1.getTime()) / (1000*3600*24));
        return days;
    }
/*获取管理员 email*/
 private String getadmin(){
     return authUserService.selectAdminUserById().getEmail();
 }


}
