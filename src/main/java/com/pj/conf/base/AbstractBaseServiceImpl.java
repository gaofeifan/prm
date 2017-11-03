package com.pj.conf.base;

import java.io.Serializable;
import java.util.List;

import tk.mybatis.mapper.entity.Example;

/**
 * 通用service的实现类
 * 
 * @author GFF
 * @date 2017年3月17日下午5:10:43
 * @version 1.0.0
 * @parameter
 * @since 1.8
 */
public abstract class AbstractBaseServiceImpl<T, ID extends Serializable> implements BaseService<T, ID> {
	/**
	 * 获取mapper（用于spring初始化base时nullpointerexception）
	 * 
	 * @author GFF
	 * @date 2017年3月21日下午3:03:33
	 * @return
	 */
	public abstract BaseMapper<T> getMapper();

	/**
	 * 根据条件查询
	 */
	@Override
	public List<T> select(T record) {
		return getMapper().select(record);
	}

	/**
	 * 根据Example查询
	 */
	@Override
	public List<T> selectByExample(Example example) {
		return getMapper().selectByExample(example);
	}

	/**
	 * 查询所有
	 */
	@Override
	public List<T> selectAll() {
		return getMapper().selectAll();
	}

	/**
	 * 根据主键查询
	 */
	@Override
	public T selectByPrimaryKey(ID key) {
		return getMapper().selectByPrimaryKey(key);
	}

	/**
	 * 新增
	 */
	@Override
	public int insert(T t) {
		return getMapper().insert(t);
	}

	/**
	 * 新增（排除非空）
	 */
	@Override
	public int insertSelective(T t) {
		return getMapper().insertSelective(t);
	}

	/**
	 * 新增(返回主键id)
	 */
	@Override
	public int insertUseGeneratedKeys(T t) {
		return getMapper().insertUseGeneratedKeys(t);
	}

	/**
	 * 新增批量(返回主键id)
	 */
	@Override
	public int insertList(List<T> t) {
		return getMapper().insertList(t);
	}

	/**
	 * 根据主键更新
	 */
	@Override
	public int updateByPrimaryKey(T record) {
		return getMapper().updateByPrimaryKey(record);
	}

	/**
	 * 根据主键更新（排除null值）
	 */
	@Override
	public int updateByPrimaryKeySelective(T record) {
		return getMapper().updateByPrimaryKeySelective(record);
	}

	/**
	 * 根据example更新
	 */
	@Override
	public int updateByExample(T record, Example example) {
		return getMapper().updateByExample(record, example);
	}

	/**
	 * 根据example更新（排除null值）
	 */
	@Override
	public int updateByExampleSelective(T record, Example example) {
		return getMapper().updateByExampleSelective(record, example);
	}

	/**
	 * 根据example
	 */
	@Override
	public int deleteByExample(Example example) {
		return getMapper().deleteByExample(example);
	}

	/**
	 * 删除
	 */
	@Override
	public int delete(T record) {
		return getMapper().delete(record);
	}

	/**
	 * 通过主键删除
	 */
	@Override
	public int deleteByKey(Integer primaryKey) {
		return getMapper().deleteByPrimaryKey(primaryKey);
	}

}
