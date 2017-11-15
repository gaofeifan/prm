package com.pj.partner.service.impl;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.pj.conf.base.AbstractBaseServiceImpl;
import com.pj.conf.base.BaseMapper;
import com.pj.partner.mapper.PartnerDetailsMapper;
import com.pj.partner.pojo.PartnerAddress;
import com.pj.partner.pojo.PartnerDetails;
import com.pj.partner.pojo.PartnerLinkman;
import com.pj.partner.service.PartnerAddressService;
import com.pj.partner.service.PartnerDetailsService;
import com.pj.partner.service.PartnerLinkmanService;
import org.apache.commons.lang3.StringUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Field;
import java.util.List;

/**
 * Created by Administrator on 2017/11/8.
 */
@Service
@Transactional
public class PartnerDetailsServiceImpl extends AbstractBaseServiceImpl<PartnerDetails,Integer> implements PartnerDetailsService {
    @Autowired
    private PartnerDetailsMapper partnerDetailsMapper;
    @Autowired
    private PartnerAddressService partnerAddressService;
    @Autowired
    private PartnerLinkmanService partnerLinkmanService;
    @Override
    public BaseMapper<PartnerDetails> getMapper() {
        return partnerDetailsMapper;
    }

    @Override
    public List<PartnerDetails> selectPartnerDetailsList() {
        PartnerDetails pd = new PartnerDetails();
        pd.setIsDelete(0);
        return this.partnerDetailsMapper.select(pd);
    }

    @Override
    public List<PartnerDetails> selectListByQuery(String name, Integer offPartner, Integer blacklistPartner) {
        Example example = new Example(PartnerDetails.class);
        Example.Criteria criteria = example.createCriteria();
        if(offPartner != null){
            criteria.andCondition("is_disable" , offPartner);
        }
        if(blacklistPartner != null){
            criteria.andCondition("is_blacklist" , blacklistPartner);
        }
        if(StringUtils.isNotBlank(name)){
            criteria.andLike("name",name);
        }
        criteria.andCondition("is_delete",0);
        List<PartnerDetails> pds = this.partnerDetailsMapper.selectByExample(example);
        return pds;
    }

    @Override
    public PartnerDetails selectByPrimaryKey(Integer key) {
        PartnerDetails pd = super.selectByPrimaryKey(key);
        PartnerAddress address = new PartnerAddress();
        address.setDetailsId(key);
        List<PartnerAddress> addresss = this.partnerAddressService.select(address);
        pd.setAddress(addresss);
        PartnerLinkman linkman = new PartnerLinkman();
        linkman.setDetailsId(key);
        List<PartnerLinkman> linkmens = this.partnerLinkmanService.select(linkman);
        pd.setLinkmans(linkmens);
        return pd;
    }


    @Override
    public void updateByPrimaryKey(PartnerDetails record, HttpServletRequest request){
        List<PartnerAddress> address = record.getAddress();
        List<PartnerLinkman> linkmans = record.getLinkmans();
        /**
         *  将原数据与新数据添加到作用与中
         */
        request.getSession().setAttribute("old_partnerLinkman",this.partnerLinkmanService.selectPartnerLinkmansByDetailsId(record.getId()));
        request.getSession().setAttribute("new_partnerLinkman",linkmans);
        request.getSession().setAttribute("old_partnerAddress",this.partnerAddressService.selectPartnerAddressesByDetailsId(record.getId()));
        request.getSession().setAttribute("new_partnerAddress",address);
        request.getSession().setAttribute("old_partnerDetails",this.partnerDetailsMapper.selectByPrimaryKey(record.getId()));
        request.getSession().setAttribute("new_partnerDetails",record);
        this.partnerLinkmanService.deletePartnerLinkmanByDetailsId(record.getId());
        this.partnerAddressService.deletePartnerAddressByDetails(record.getId());
        if(linkmans != null){
            for(PartnerLinkman pl : linkmans){
                pl.setDetailsId(record.getId());
            }
            this.partnerLinkmanService.insertList(linkmans);
        }
        if(address != null){
            for (PartnerAddress pa: address ) {
                pa.setDetailsId(record.getId());
            }
            this.partnerAddressService.insertList(address);
        }
       super.updateByPrimaryKey(record);
    }

    @Override
    public void insertSelective(PartnerDetails partnerDetails, HttpServletRequest request) {
        super.insertSelective(partnerDetails);
        List<PartnerLinkman> linkmans = partnerDetails.getLinkmans();
        List<PartnerAddress> address = partnerDetails.getAddress();
        request.getSession().setAttribute("new_partnerAddress",address);
        request.getSession().setAttribute("new_partnerLinkman",linkmans);
        request.getSession().setAttribute("new_partnerDetails",partnerDetails);
       /* if(linkmans != null){*/
            for(PartnerLinkman pl : linkmans){
                pl.setDetailsId(partnerDetails.getId());
            }
            this.partnerLinkmanService.insertList(linkmans);
       /* }*/
        if(address != null){
            for (PartnerAddress pa: address ) {
                pa.setDetailsId(partnerDetails.getId());
            }
            this.partnerAddressService.insertList(address);
        }
    }

    @Override
    public boolean verifyValueRepeat(String fieldName, String fieldValue) {
        try {
            boolean b = verifyfeildIsExist(fieldName);
        } catch (NoSuchFieldException e) {
            throw new RuntimeException("the field is not exist");
        }
        fieldName = toUnderlineJSONString(fieldName);
        Example example = new Example(PartnerDetails.class);
        example.createCriteria().andCondition(fieldName,fieldValue);
        List<PartnerDetails> pds =super.selectByExample(example);
        if(pds.size() != 0){
            return false;
        }
        return true;

    }

    private boolean verifyfeildIsExist(String fieldName) throws NoSuchFieldException {
            Field field =  PartnerDetails.class.getDeclaredField(fieldName);
            return true;

    }

    private String toUnderlineJSONString(String param){
        if (param==null||"".equals(param.trim())){
            return "";
        }
        int len=param.length();
        StringBuilder sb=new StringBuilder(len);
        for (int i = 0; i < len; i++) {
            char c=param.charAt(i);
            if (Character.isUpperCase(c)){
                sb.append(UNDERLINE);
                sb.append(Character.toLowerCase(c));
            }else{
                sb.append(c);
            }
        }
        return sb.toString();
    }


}
