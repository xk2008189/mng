package com.pro.system.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pro.common.base.BaseService;
import com.pro.common.base.Page;
import com.pro.system.dao.RoleDao;
import com.pro.system.entity.Role;
import com.pro.system.entity.User;

/**
 * 
 * @author xiangkun
 *
 */
@Service  
public class RoleService extends BaseService<RoleDao, Role>{  
    @Autowired  
    private RoleDao roleDao;  
  
    /**
     * 查询分页
     * @param page
     * @param user
     * @return
     */
    public Page<Role> findPage(Page<Role> page,Role role) {
    	int count = this.count(role);
    	page.setCount(count);
    	role.getSqlMap().put("index", (page.getCurrentPageNo() - 1) *  page.getPageSize());
    	role.getSqlMap().put("pageSize", page.getPageSize());
    	page.setList(this.findList(role));
    	return page;
    }
} 