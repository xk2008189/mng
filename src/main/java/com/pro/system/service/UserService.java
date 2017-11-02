package com.pro.system.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pro.system.dao.UserDao;
import com.pro.system.entity.User;

@Service  
public class UserService{  
    @Autowired  
    private UserDao userDao;  
  
    public User selectByPrimaryKey(String userId) {  
        return userDao.selectByPrimaryKey(userId);  
    }  
    
    public User selectUserByName(String userName) {  
        return userDao.selectUserByName(userName);  
    }  
    
    
    public int deleteByPrimaryKey(String id){
    	  return userDao.deleteByPrimaryKey(id);
    };

    public int insert(User record){
    	return userDao.insert(record);
    };

    public int insertSelective(User record){
    	return userDao.insertSelective(record);
    };


    public int updateByPrimaryKeySelective(User record){
    	return userDao.updateByPrimaryKeySelective(record);
    };
    
    public  int updateByPrimaryKey(User record){
    	return userDao.updateByPrimaryKey(record);
    };
  
} 