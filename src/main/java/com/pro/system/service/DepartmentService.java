package com.pro.system.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pro.common.base.BaseService;
import com.pro.common.base.Page;
import com.pro.system.dao.DepartmentDao;
import com.pro.system.dao.RoleDao;
import com.pro.system.entity.Department;
import com.pro.system.entity.Role;
import com.pro.system.entity.User;

/**
 * 
 * @author xiangkun
 *
 */
@Service
@Transactional
public class DepartmentService extends BaseService<DepartmentDao, Department>{  
    @Autowired  
    private DepartmentDao departmentDao;  
  
    /**
     * 查询分页
     * @param page
     * @param user
     * @return
     */
    public Page<Department> findPage(Page<Department> page,Department department) {
    	int count = this.count(department);
    	page.setCount(count);
    	department.getSqlMap().put("index", (page.getCurrentPageNo() - 1) *  page.getPageSize());
    	department.getSqlMap().put("pageSize", page.getPageSize());
    	page.setList(this.findList(department));
    	return page;
    }
} 