package com.pro.common.base;

/**
 * 
 * @author xiangkun
 *
 * @param <T>
 */
public interface BaseDao<T> {
	
	/**
	 * 根据主键查询 
	 * @param id 主键
	 * @return
	 */
	T selectByPrimaryKey(String id);
	
	/**
	 * 根据主键删除
	 * @param id 主键
	 * @return
	 */
	int deleteByPrimaryKey(String id);

	/**
	 * 新增数据
	 * @param t
	 * @return
	 */
    int insert(T t);

    /**
     * 新增非空数据
     * @param t
     * @return
     */
    int insertSelective(T t);


    /**
     * 更行非空数据
     * @param t
     * @return
     */
    int updateByPrimaryKeySelective(T t);

    /**
     * 更新所有数据
     * @param t
     * @return
     */
    int updateByPrimaryKey(T t);
	
}