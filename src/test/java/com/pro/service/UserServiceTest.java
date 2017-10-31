package com.pro.service;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.pro.baseTest.SpringTestCase;
import com.pro.system.entity.User;
import com.pro.system.service.UserService;

/** 
 * 功能概要：UserService单元测试 
 *  
 * @author linbingwen 
 * @since  2015年9月28日  
 */  
public class UserServiceTest extends SpringTestCase {  
    @Autowired  
    private UserService userService;  
    Logger logger = Logger.getLogger(UserServiceTest.class);  
      
    @Test  
    public void selectUserByIdTest(){  
        User user = userService.selectUserById(10);  
        logger.debug("查找结果" + user);  
    }  
      
  
}  