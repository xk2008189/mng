package com.pro.system.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

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
	@Autowired  
    private UserService userService;  
      
    
    @RequestMapping("/userList.do")
    public String index(User user, Model model){
    	List<User> userList = userService.findList();
    	model.addAttribute("list", userList);
        return "system/user/userList";
    }
} 
