package com.pro.system.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.pro.system.entity.User;
import com.pro.system.service.UserService;

/** 
 * ���ܸ�Ҫ��UserController 
 *  
 * @author xiangkun 
 * 
 */  
@Controller  
public class UserController {  
    @Resource  
    private UserService userService;  
      
    
    @RequestMapping("/index.do")
    public String index(){
        return "index";
    }
} 
