package com.pro.system.dao;

import java.util.List;

import com.pro.common.base.BaseDao;
import com.pro.system.entity.Department;

public interface DepartmentDao extends BaseDao<Department> {
    
	List<Department> findChildList(Department department);
	
	List<Department> findAllChildList(Department department);
}