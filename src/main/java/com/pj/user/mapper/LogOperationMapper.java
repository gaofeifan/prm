package com.pj.user.mapper;

import com.pj.user.pojo.Operation;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by SenevBoy on 2017/11/8.
 */
@Mapper
public interface LogOperationMapper {

    // 分頁 查詢
    List<Operation> findOperationBydate(@Param("startDate") String startDate, @Param("endDate") String endDate,  @Param("showDay") Boolean showDay);
}
