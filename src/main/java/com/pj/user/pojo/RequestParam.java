package com.pj.user.pojo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * Created by SenevBoy on 2017/11/9.
 */
@Data
public class RequestParam {

    @ApiModelProperty(value = "当前页", required = false)
    private Integer pageNo;

    @ApiModelProperty(value = "每页展示条数", required = false)
    private Integer pageSize;

    @ApiModelProperty(value = "开始时间", required = false)
    private String   startDate;

    @ApiModelProperty(value = "结束时间", required = false)
    private String   endDate;


}