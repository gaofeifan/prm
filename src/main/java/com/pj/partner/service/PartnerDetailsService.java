package com.pj.partner.service;

import com.pj.aeserviceapi.pojo.ResponseData;
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


    List<PartnerDetails> selectPartnerDetailsList( );

    List<PartnerDetails> selectListByQuery(String name, Integer offPartner, Integer blacklistPartner, String partnerCategory);

    void updateByPrimaryKey(PartnerDetails partnerDetails, HttpServletRequest request, String email);

    void insertSelective(PartnerDetails partnerDetails , HttpServletRequest request, String email);

    /**
     *  校验字段值
     *
     * @param id
     * @param fieldName
     * @param fieldValue
     * @return
     */
    boolean verifyValueRepeat(Integer id, String fieldName, String fieldValue,Integer pId);

    /**
     *  根据主键删除id
     * @param id
     */
    void deletePartnerDetailsById(Integer id, String email);

    /**
     *  查询转移文件
     * @param ids
     * @return
     */
    List<PartnerDetailsShifFile> selectShiftFile(Integer[] ids);

    /**
     *  转移文件
     * @param id
     * @param email
     */
    boolean shiftPartnerDetailsFileByIds( Integer id,String email);

    /**
     *  判断是否可以删除
     * @param id
     * @return
     */
    boolean isDeletePartnerDetails(Integer id);

    /**
     *  查询父集代码集
     * @param id
     * @return
     */
    Object[] getParentCodeList(Integer id);

    /**
     *  查询是否可以修改code
     * @param id
     * @return
     */
    boolean isEditCode(Integer id);

    /**
     *  查询是否有子集
     * @param id
     * @return
     */
    boolean selectIsChild(Integer id);


    /**
     * PRM 支持 AE 项目 航司 对外接口
     * @return
     * @param
     * @param responseData
     */
    List<ResponseData> aeAirlineFindPartnerDateilsList(ResponseData responseData);


    /**
     * PRM 支持 AE 项目 客商 对外接口
     * @return
     * @param
     * @param responseData
     */
    List<ResponseData> aePartnerFindPartnerDateilsList(ResponseData responseData);
}
