package com.pj.partner.service;

import com.pj.conf.base.BaseService;
import com.pj.partner.pojo.PartnerDetails;
import com.pj.partner.pojo.PartnerDetailsShifFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by Administrator on 2017/11/8.
 */
public interface PartnerDetailsService extends BaseService<PartnerDetails,Integer> {

    public static final char UNDERLINE='_';

    List<PartnerDetails> selectPartnerDetailsList();

    List<PartnerDetails> selectListByQuery(String name, Integer offPartner, Integer blacklistPartner);

    void updateByPrimaryKey(PartnerDetails partnerDetails , HttpServletRequest request);

    void insertSelective(PartnerDetails partnerDetails , HttpServletRequest request);

    /**
     *  校验字段值
     * @param fieldName
     * @param fieldValue
     * @return
     */
    boolean verifyValueRepeat(String fieldName, String fieldValue);

    /**
     *  根据主键删除id
     * @param id
     */
    void deletePartnerDetailsById(Integer id);

    /**
     *  查询转移文件
     * @param ids
     * @return
     */
    List<PartnerDetailsShifFile> selectShiftFile(Integer[] ids);

    /**
     *  转移文件
     * @param ids
     * @param id
     */
    void shiftPartnerDetailsFileByIds(Integer[] ids, Integer id);

    /**
     *  判断是否删除id
     * @param id
     */
    boolean isDeletePartnerDetails(Integer id);
}
