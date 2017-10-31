package com.pro.system.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.pro.system.entity.User;
import com.pro.system.service.UserService;

/** 
 * ¹¦ÄÜ¸ÅÒª£ºUserController 
 *  
 * @author xiangkun 
 * 
 */  
@Controller  
public class UserController {  
    @Resource  
    private UserService userService;  
      
    @RequestMapping("/")    
    public ModelAndView getIndex(){      
    	System.out.println("2222");
        ModelAndView mav = new ModelAndView("index");   
        User user = userService.selectUserById(1);  
        mav.addObject("user", user);   
        return mav;    
    }  
    
    @RequestMapping("/index.do")
    public String index(){
        return "index";
    }
} 
