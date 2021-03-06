package com.pj.user.mapper;

import com.pj.conf.base.BaseMapper;
import com.pj.user.pojo.Operation;
import com.pj.user.pojo.Permissions;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by SenevBoy on 2017/11/9.
 */
@Mapper
public interface LogPermissionsMapper extends BaseMapper<Permissions> {


    List<Permissions> findPermissionsBydate(@Param("startDate") String startDate, @Param("endDate") String endDate, @Param("showWeekData")Boolean showWeekData);
}