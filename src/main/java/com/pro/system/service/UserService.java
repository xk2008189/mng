package com.pro.system.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pro.system.dao.UserDao;
import com.pro.system.entity.User;

@Service  
public class UserService{  
    @Autowired  
    private UserDao userDao;  
  
    public User selectUserById(Integer userId) {  
        return userDao.selectUserById(userId);  
    }  
    
    public User selectUserByName(String userName) {  
        return userDao.selectUserByName(userName);  
    }  
  
} 