package com.pro.common.base;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;


@Transactional(readOnly = true)
public  class BaseService<D extends BaseDao<T>,T> {
	
	/**
	 * 日志对象
	 */
	protected Logger logger = LoggerFactory.getLogger(getClass());
	
	/**
	 * 持久层对象
	 */
	@Autowired
	protected D dao;
	
	public T selectByPrimaryKey(String id) {  
        return dao.selectByPrimaryKey(id);  
    }  
    
    
    public int deleteByPrimaryKey(String id){
    	  return dao.deleteByPrimaryKey(id);
    };

    public int insert(T t){
    	return dao.insert(t);
    };

    public int insertSelective(T t){
    	return dao.insertSelective(t);
    };
    
    public  int updateByPrimaryKey(T t){
    	return dao.updateByPrimaryKey(t);
    };


    public int updateByPrimaryKeySelective(T t){
    	return dao.updateByPrimaryKeySelective(t);
    };
    
    public List<T> findList(){
    	return dao.findList();
    }
}