package com.pro.system.dao;

import com.pro.common.base.BaseDao;
import com.pro.system.entity.User;

public interface UserDao extends BaseDao<User> {
    
    User selectUserByName(String userName);
}