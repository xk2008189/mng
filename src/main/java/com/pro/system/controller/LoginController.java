package com.pro.system.controller;

import java.util.HashMap;
import java.util.Map;


import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/** 
 *  用户登录
 * @author xiangkun 
 * 
 */  
@Controller  
public class LoginController {  
     
	/**
	 * 登录界面
	 * @return
	 */
    @RequestMapping("/login.do")
    public String login(){
        return "login";
    }
    
    /**
     * 主页
     * @return
     */
    @RequestMapping("/index.do")
    public String index(){
        return "index";
    }
    
    /**
     * 登录
     * @param username
     * @param password
     * @return
     */
    @RequestMapping("/submit.do")
    @ResponseBody
    public Map<String,Object> submit(@RequestParam(value="username", required=true) String username,
    		@RequestParam(value="password", required=true) String password){
    	Map<String,Object> resultMap = new HashMap<String,Object>();
	    UsernamePasswordToken token = new UsernamePasswordToken(username,password);  
        Subject currentUser = SecurityUtils.getSubject(); 
        try {
			if (!currentUser.isAuthenticated()){
			    // 使用shiro来验证  
			    token.setRememberMe(true);  
			    currentUser.login(token); //验证角色和权限  
			} 
			resultMap.put("st", 1);
		} catch (AuthenticationException e) {
			resultMap.put("st", -1);
			resultMap.put("msg", e.getMessage());
			e.printStackTrace();
		}
        
    	return resultMap;
    }
    
    /**
     * 登出
     * @param username
     * @param password
     * @return
     */
    @RequestMapping("/loginout.do")
    @ResponseBody
    public Map<String,Object> loginout(){
    	Map<String,Object> resultMap = new HashMap<String,Object>();
        Subject currentUser = SecurityUtils.getSubject(); 
        try {
        	// 登出
        	currentUser.logout();
			resultMap.put("st", 1);
		} catch (Exception e) {
			resultMap.put("st", -1);
			resultMap.put("msg", e.getMessage());
			e.printStackTrace();
		}
        
    	return resultMap;
    }
    
} 
