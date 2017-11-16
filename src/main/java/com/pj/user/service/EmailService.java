package com.pj.user.service;

import com.pj.partner.pojo.PartnerDetails;

import java.util.List;

/**
 * Created by SenevBoy on 2017/11/15.
 *
 *
 * 支持 邮件内容获取的接口
 */

public interface EmailService {
//TODO支持 邮件内容获取的接口  待 复制之后有本人进行删除代码操作

    List<PartnerDetails> findPartnerDetailsLastMonthDate();   // 获取  上月所有新增的Partner  2017年11月15日13:38:48


    List<PartnerDetails> findPartnerDetailsGsigningInTransit();  // 获取 签约在途 超过15 天的的信息
}
