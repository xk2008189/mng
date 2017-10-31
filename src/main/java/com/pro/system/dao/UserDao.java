package com.pro.system.dao;

import com.pro.system.entity.User;

public interface UserDao {

	 public User selectUserById(Integer userId); 
	 
	 public User selectUserByName(String userName); 
}
