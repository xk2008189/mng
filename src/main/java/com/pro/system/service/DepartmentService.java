package com.pro.system.service;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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
    
    /**
     * 查询树表格数据
     * @param departmentId
     * @return
     */
    public List<Department> getTreeTableData(String departmentId){
    	Department department = this.selectByPrimaryKey(departmentId);
    	List<Department> departmentList = this.findAllChildList(department);
    	Collections.sort(departmentList, new Comparator(){  
            @Override  
            public int compare(Object o1, Object o2) {  
            	Department dep1=(Department)o1;  
            	Department dep2=(Department)o2;  
            	String sort1 = dep1.getSort();
            	String sort2 = dep2.getSort();
            	if (sort1.equals(sort2)) {
            		return 0;
            	}
            	String[] sorts1 = sort1.split("_");
            	String[] sorts2 = sort2.split("_");
            	int size = 1;
            	if (sorts1.length >= sorts2.length) {
            		size  = sorts2.length;
            	} else {
            		size  = sorts1.length;
            	}
            	
            	for (int i = 0; i < size; i++) {
            		if (sorts1[i].equals(sorts2[i])) {
            			if (sorts1.length == i + 1) {
            				return -1;
            			}
            			if (sorts2.length == i + 1) {
            				return 1;
            			}
            			continue;
            		}
            		if (Integer.parseInt(sorts1[i]) > Integer.parseInt(sorts2[i])) {
            			return 1;
            		} else {
            			return -1;
            		}
            	}
            	return 0;
            }             
        });  
    	
    	return departmentList;
    }
    
    /**
     * 查询子节点数据
     * @param department
     * @return
     */
    public List<Department> findChildList(Department department) {
    	return departmentDao.findChildList(department);
    }
    
    /**
     * 查询所有子节点数据
     * @param department
     * @return
     */
    public List<Department> findAllChildList(Department department) {
    	department.setSort(department.getSort() + "%");
    	return departmentDao.findAllChildList(department);
    }
} 