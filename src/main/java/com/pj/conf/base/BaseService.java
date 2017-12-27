package com.pj.conf.base;

import java.io.Serializable;
import java.util.List;

import tk.mybatis.mapper.entity.Example;

/**
 * 	通用service
 *	@author		GFF
 *	@date		2017年3月17日下午5:09:08
 *	@version	1.0.0
 *	@parameter	
 *  @since		1.8
 */
public interface BaseService<T, ID extends Serializable> {
	/**
	 * 根据条件查询
	 * 
	 * @author GFF
	 * @date 2017年3月21日下午2:38:30
	 * @param record
	 * @return
	 */
	public List<T> select(T record);

	/**
	 * 根据Example查询
	 * 
	 * @author GFF
	 * @date 2017年3月21日下午2:38:30
	 * @param example
	 * @return
	 */
	public List<T> selectByExample(Example example);

	/**
	 * 查询所有
	 * 
	 * @author GFF
	 * @date 2017年3月21日下午2:37:06
	 * @return
	 */
	public List<T> selectAll();

	/**
	 * 根据主键查询
	 * 
	 * @author GFF
	 * @date 2017年3月21日下午2:37:22
	 * @param id
	 * @return
	 */
	public T selectByPrimaryKey(ID id);

	/**
	 * 新增
	 * 
	 * @author GFF
	 * @date 2017年3月21日下午2:37:33
	 * @param record
	 * @return
	 */
	public int insert(T record);

	/**
	 * 新增（排除null值）
	 * 
	 * @author GFF
	 * @date 2017年3月21日下午2:37:53
	 * @param record
	 * @return
	 */
	public int insertSelective(T record);

	/**
	 * 新增(返回主键id)
	 * 
	 * @author GFF
	 * @date 2017年3月21日下午2:38:12
	 * @param record
	 * @return
	 */
	public int insertUseGeneratedKeys(T record);

	/**
	 * 批量新增
	 * 
	 * @author GFF
	 * @date 2017年9月13日上午10:00:12
	 * @param t
	 * @return
	 */

	public int insertList(List<T> t);

	/**
	 * 根据主键更新
	 * 
	 * @author GFF
	 * @date 2017年3月21日下午2:55:59
	 * @param record
	 * @return
	 */
	public int updateByPrimaryKey(T record);

	/**
	 * 根据主键更新（排除null值）
	 * 
	 * @author GFF
	 * @date 2017年3月21日下午2:56:16
	 * @param record
	 * @return
	 */
	public int updateByPrimaryKeySelective(T record);

	/**
	 * 根据example更新
	 * 
	 * @author GFF
	 * @date 2017年3月21日下午2:56:47
	 * @param record
	 * @param example
	 * @return
	 */
	public int updateByExample(T record, Example example);

	/**
	 * 根据example更新（排除null值）
	 * 
	 * @author GFF
	 * @date 2017年3月21日下午2:57:15
	 * @param record
	 * @param example
	 * @return
	 */
	public int updateByExampleSelective(T record, Example example);

	/**
	 * 根据example删除
	 * 
	 * @author GFF
	 * @date 2017年3月22日下午2:23:06
	 * @param example
	 */
	public int deleteByExample(Example example);

	/**
	 * 删除对象
	 * 
	 * @author GFF
	 * @date 2017年9月19日下午3:16:20
	 * @param record
	 * @return
	 */
	public int delete(T record);

	/**
	 * 主键删除
	 * 
	 * @author: x.gao
	 * @createTime: 2017年9月22日 下午5:43:55
	 * @history:
	 * @param primaryKey
	 * @return int
	 */
	public int deleteByKey(Integer primaryKey);
	
}
