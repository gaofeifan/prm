package com.pj.user.mapper;

import com.pj.conf.base.BaseMapper;
import com.pj.partner.pojo.PartnerDetails;
import org.apache.ibatis.annotations.Mapper;


import java.util.List;

/**
 * Created by SenevBoy on 2017/11/8.
 */


@Mapper
public interface EmailMapper extends  BaseMapper<PartnerDetails>{
    //TODO支持 邮件内容获取的接口  待 复制之后有本人进行删除代码操作

    List<PartnerDetails> findPartnerDetailsLastMonthDate();    // 获取  上月新增的 partner

    List<PartnerDetails> findPartnerDetailsGsigningInTransit();  // 获取 签约在途 超过15 天的的信息
}
