package com.pj.partner.mapper;

import com.pj.conf.base.BaseMapper;
import com.pj.partner.pojo.PartnerDetailsShifFile;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface PartnerDetailsShifFileMapper extends BaseMapper<PartnerDetailsShifFile> {

    public List<PartnerDetailsShifFile> getChildList(@Param("id") Integer id);
}
