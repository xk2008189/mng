package com.pro.common.base;

import java.util.List;

/**
 * 基础持久层。
 * @author xiangkun
 * @param <T> 泛型
 */
public interface BaseDao<T> {
		
	/**
	 * 根据主键查询 
	 * @param id 主键
	 * @return return
	 */
	T selectByPrimaryKey(String id);
	
	/**
	 * 根据主键删除
	 * @param id 主键
	 * @return return
	 */
	int deleteByPrimaryKey(String id);

	/**
	 * 新增数据
	 * @param t 实体类
	 * @return return
	 */
    int insert(T t);

    /**
     * 新增非空数据
     * @param t 实体类
     * @return return
     */
    int insertSelective(T t);


    /**
     * 更行非空数据
     * @param t 实体类
     * @return return
     */
    int updateByPrimaryKeySelective(T t);

    /**
     * 更新所有数据
     * @param t 实体类
     * @return return
     */
    int updateByPrimaryKey(T t);
    
    /**
     * 查询所有数据
     * @return return
     */
    List<T> findList(T t);
    
    /**
     * 查询数据个数
     * @return return
     */
    int count(T t);
	
}