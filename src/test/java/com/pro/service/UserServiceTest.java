package com.pro.service;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.pro.baseTest.SpringTestCase;
import com.pro.system.entity.User;
import com.pro.system.service.UserService;

/** 
 * ���ܸ�Ҫ��UserService��Ԫ���� 
 *  
 * @author linbingwen 
 * @since  2015��9��28��  
 */  
public class UserServiceTest extends SpringTestCase {  
    @Autowired  
    private UserService userService;  
    Logger logger = Logger.getLogger(UserServiceTest.class);  
      
    @Test  
    public void selectUserByIdTest(){  
    }  
      
  
}  