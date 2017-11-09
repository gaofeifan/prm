package com.pj.user.mapper;

import com.pj.conf.base.BaseMapper;
import com.pj.user.pojo.Operation;
import com.pj.user.pojo.UserLevel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * Created by SenevBoy on 2017/11/8.
 */
@Mapper
public interface LogOperationMapper {

    // 分頁 查詢
    List<Operation> findOperationBydate(@Param("startDate") String startDate, @Param("endDate") String endDate);
}
