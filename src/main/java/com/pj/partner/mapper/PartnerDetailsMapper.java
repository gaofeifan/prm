package com.pj.partner.mapper;

import com.pj.conf.base.BaseMapper;
import com.pj.partner.pojo.PartnerDetails;
import com.pj.partner.service.PartnerDetailsService;
import org.apache.ibatis.annotations.Mapper;

/**
 * Created by Administrator on 2017/11/8.
 */
@Mapper
public interface PartnerDetailsMapper extends BaseMapper<PartnerDetails>{

    default String toUnderlineJSONString(String param){
        if (param==null||"".equals(param.trim())){
            return "";
        }
        int len=param.length();
        StringBuilder sb=new StringBuilder(len);
        for (int i = 0; i < len; i++) {
            char c=param.charAt(i);
            if (Character.isUpperCase(c)){
                sb.append(PartnerDetailsService.UNDERLINE);
                sb.append(Character.toLowerCase(c));
            }else{
                sb.append(c);
            }
        }
        return sb.toString();
    }
}
