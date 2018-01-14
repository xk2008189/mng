package com.pro.common.base;

import java.io.Serializable;
import java.util.Map;

public abstract class BaseEntity<T> implements Serializable {

	/**
	 * 序列化
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 需要拼接的SQL
	 */
	private Map<String,Object> sqlMap;

	public Map<String,Object> getSqlMap() {
		return sqlMap;
	}

	public void setSqlMap(Map<String,Object> sqlMap) {
		this.sqlMap = sqlMap;
	}


}

