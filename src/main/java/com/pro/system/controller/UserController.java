package com.pro.system.controller;


import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pro.system.entity.User;
import com.pro.system.service.UserService;

/** 
 * 用户管理
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
    	model.addAttribute("count", userList.size());
        return "system/user/userList";
    }
    
    @RequestMapping("/userAdd.do")
    public String userAdd( Model model){
        return "system/user/userAdd";
    }
    
    /**
     * 添加
     * @return
     */
    @RequestMapping("/add.do")
    @ResponseBody
    public Map<String,Object> add(User user){
    	Map<String,Object> resultMap = new HashMap<String,Object>();
    	try {
			Date now = new Date();
			user.setId(UUID.randomUUID().toString());
			user.setDelFlag("0");
			user.setLoginFlag("0");
			user.setDepartmentId("1");
			user.setCreateBy("xiangkun");
			user.setCreateDate(now);
			user.setUpdateBy("xiangkun");
			user.setUpdateDate(now);
			userService.insert(user);
			resultMap.put("OK", 1);
		} catch (Exception e) {
			e.printStackTrace();
			resultMap.put("OK", -1);
			resultMap.put("msg", e.getMessage());
		}
    	return resultMap;
    }
    
    @RequestMapping("/userUpdate.do")
    public String userUpdate( Model model, @RequestParam(value="userId", required=true) String userId){
    	User user = userService.selectByPrimaryKey(userId);
    	model.addAttribute("user", user);
        return "system/user/userUpdate";
    }
} 
