package com.pj.user.service.impl;


import com.pj.partner.pojo.PartnerDetails;
import com.pj.user.mapper.EmailMapper;
import com.pj.user.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by SenevBoy on 2017/11/15.
 * .
 */

@Service
@Transactional
public class EmailServiceImpl implements EmailService {
    //TODO支持 邮件内容获取的接口  待 复制之后有本人进行删除代码操作
    @Autowired
    private EmailMapper emailMapper;

    // 获取  上月新增的 partner
    @Override
    public List<PartnerDetails> findPartnerDetailsLastMonthDate() {
        return emailMapper.findPartnerDetailsLastMonthDate();
    }

    // 获取 签约在途 超过15 天的的信息
    @Override
    public List<PartnerDetails> findPartnerDetailsGsigningInTransit() {
        return emailMapper.findPartnerDetailsGsigningInTransit();
    }

    @Override
    public List<PartnerDetails> findPartnerDetailsLastIsAboutToExpire() {
        return emailMapper.findPartnerDetailsLastIsAboutToExpire();
    }
}
