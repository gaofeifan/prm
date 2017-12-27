package com.pj.partner.service;

import com.pj.partner.pojo.PartnerDetails; /*** 
 * @ClassName: PartnerDetailsServiceUtil
 * @Description: (这里用一句话描述这个类的作用)
 * @author SenevBoy
 * @date 2017/12/27 15:18   
 **/
public interface PartnerDetailsUtilService {

    void updateByPrimaryKey(PartnerDetails pd, String email);
}
