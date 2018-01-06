package com.pro.system.controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pro.system.entity.User;
import com.pro.system.service.UserService;

/** 
 *  
 * @author xiangkun 
 * 
 */  
@Controller  
public class LoginController {  
	@Autowired  
    private UserService userService;  
      
    /*@RequestMapping("login.do")    
    public ModelAndView getIndex(){      
        ModelAndView mav = new ModelAndView("index");   
        User user = userService.selectUserById(1);  
        mav.addObject("user", user);   
        return mav;    
    } */ 
    
    @RequestMapping("/login.do")
    public String login(){
        return "login";
    }
    
    @RequestMapping("/index.do")
    public String index(){
        return "index";
    }
    
    @RequestMapping("/submit.do")
    @ResponseBody
    public Map<String,Object> submit(@RequestParam(value="username", required=true) String username,
    		@RequestParam(value="password", required=true) String password){
    	Map<String,Object> resultMap = new HashMap<String,Object>();
	    UsernamePasswordToken token = new UsernamePasswordToken(username,password);  
        Subject currentUser = SecurityUtils.getSubject(); 
        try {
			if (!currentUser.isAuthenticated()){
			    //使用shiro来验证  
			    token.setRememberMe(true);  
			    currentUser.login(token);//验证角色和权限  
			} 
			resultMap.put("st", 1);
		} catch (AuthenticationException e) {
			resultMap.put("st", -1);
			resultMap.put("msg", e.getMessage());
			e.printStackTrace();
		}
        
    	return resultMap;
    }
    
} 
