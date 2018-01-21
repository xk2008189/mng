package com.pro.system.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pro.common.base.BaseService;
import com.pro.common.base.Page;
import com.pro.system.dao.UserDao;
import com.pro.system.entity.User;

/**
 * 
 * @author xiangkun
 *
 */
@Service  
public class UserService extends BaseService<UserDao, User>{  
    @Autowired  
    private UserDao userDao;  
  
    public User selectUserByName(String userName) {  
        return userDao.selectUserByName(userName);  
    }  
    
    /**
     * 查询分页
     * @param page
     * @param user
     * @return
     */
    public Page<User> findPage(Page<User> page,User user) {
    	int count = this.count(user);
    	page.setCount(count);
    	user.getSqlMap().put("index", (page.getCurrentPageNo() - 1) *  page.getPageSize());
    	user.getSqlMap().put("pageSize", page.getPageSize());
    	page.setList(this.findList(user));
    	return page;
    }
  
} 