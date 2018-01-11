package com.pj.conf.base.page;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;

/**
 * 自定义分页类
 * 
 * @author GFF
 * @date 2017年1月11日下午1:37:42
 * @version 1.0.0
 * @parameter
 * @since 1.8
 */
public class Page implements Serializable{

	private static final long serialVersionUID = 1L;
	@ApiModelProperty(value = "当前页码", required = false)
	protected Integer pageNo = 1;
	@ApiModelProperty(value = "", required = false)
	protected Integer startRow;
	@ApiModelProperty(value = "显示条数", required = false)
	protected Integer pageSize = 10;

	public Page() {
		super();
	}
	

	public void setPageNo(Integer pageNo) {
		this.pageNo = pageNo;
		if(pageNo != null){
			this.startRow = (pageNo - 1) * this.pageSize;
		}
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
		if(pageSize != null){
			this.startRow = (pageNo - 1) * this.pageSize;
		}
	}

	public Integer getStartRow() {
		return startRow;
	}

	public void setStartRow(Integer startRow) {
		this.startRow = startRow;
	}

	public Integer getPageNo() {
		return pageNo;
	}

	public Integer getPageSize() {
		return pageSize;
	}
	
	
}
