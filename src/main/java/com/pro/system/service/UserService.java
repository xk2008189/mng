package com.pro.system.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pro.common.base.BaseService;
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
  
} 