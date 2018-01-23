package com.pro.system.service;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pro.common.base.BaseService;
import com.pro.common.base.Page;
import com.pro.system.dao.DepartmentDao;
import com.pro.system.entity.Department;

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

    /**
     * 查询树数据
     * @return
     */
    public List<Map<String,Object>> getTreeData(Department department){
    	List<Map<String,Object>> treeList = new ArrayList<Map<String,Object>>();
    	List<Department> departmentList = departmentDao.findList(department);
    	if (departmentList != null && departmentList.size() > 0) {
    		for (Department department1 : departmentList) {
    			Map<String,Object> tree = new HashMap<String,Object>();
    			tree.put("id", department1.getId());
    			tree.put("pId", department1.getParentId());
    			tree.put("name", department1.getName());
    			if (department1.getParentId().equals("0")) {
    				tree.put("open", true);
    			} else {
    				tree.put("open", false);
    			}
    			treeList.add(tree);
    		}
    	}
    	return treeList;
    }
} 