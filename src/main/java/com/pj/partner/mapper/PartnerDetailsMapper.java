package com.pj.partner.mapper;

import com.pj.conf.base.BaseMapper;
import com.pj.partner.pojo.PartnerDetails;
import com.pj.partner.service.PartnerDetailsService;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

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

    /**
     *  根据条件查询
     * @param pd
     * @return
     */
    List<PartnerDetails> selectListByQuery(PartnerDetails pd);

    /**
     *  查询所有父集
     * @param id
     * @return
     */
    List<PartnerDetails> getParentList(Integer id);

    /**
     *  查询最大的code
     * @return
     */
    String selectDetailsMaxCode();
}
