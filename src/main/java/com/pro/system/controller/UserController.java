package com.pro.system.controller;


import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.cxf.common.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pro.common.Json.JsonMapper;
import com.pro.system.entity.User;
import com.pro.system.service.UserService;

/** 
 * 用户管理
 *  
 * @author xiangkun 
 * 
 */  
@Controller
@RequestMapping("/user")
public class UserController {  
	@Autowired  
    private UserService userService;  
      
    
    @RequestMapping("/userList.do")
    public String index(Model model, @RequestParam(value="minCreateDate", required=false) String minCreateDate,
    		 @RequestParam(value="maxCreateDate", required=false) String maxCreateDate,
    		 @RequestParam(value="name", required=false) String name) throws ParseException{
    	try {
			User user = new User();
			Map<String, Object> sqlMap = new HashMap<String, Object>();
			if (!StringUtils.isEmpty(minCreateDate)) {
				sqlMap.put("minCreateDate", minCreateDate);
			}
			if (!StringUtils.isEmpty(maxCreateDate)) {
				sqlMap.put("maxCreateDate", maxCreateDate);
			}
			if (!StringUtils.isEmpty(name)) {
				sqlMap.put("name", "%" + name + "%");
			}
			user.setSqlMap(sqlMap);
			List<User> userList = userService.findList(user);
			model.addAttribute("list", userList);
			model.addAttribute("count", userList.size());
		} catch (Exception e) {
			e.printStackTrace();
		}
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
    
    /**
     * 修改
     * @return
     */
    @RequestMapping("/update.do")
    @ResponseBody
    public Map<String,Object> update(User user){
    	Map<String,Object> resultMap = new HashMap<String,Object>();
    	try {
			Date now = new Date();
			user.setDelFlag("0");
			user.setLoginFlag("0");
			user.setDepartmentId("1");
			user.setUpdateBy("xiangkun");
			user.setUpdateDate(now);
			userService.updateByPrimaryKeySelective(user);
			resultMap.put("OK", 1);
		} catch (Exception e) {
			e.printStackTrace();
			resultMap.put("OK", -1);
			resultMap.put("msg", e.getMessage());
		}
    	return resultMap;
    }
    
    /**
     * 删除
     * @return
     */
    @RequestMapping("/delete.do")
    @ResponseBody
    public Map<String,Object> delete(@RequestParam(value="userId", required=true) String userId){
    	Map<String,Object> resultMap = new HashMap<String,Object>();
    	try {
    		userService.deleteByPrimaryKey(userId);
			resultMap.put("OK", 1);
		} catch (Exception e) {
			e.printStackTrace();
			resultMap.put("OK", -1);
			resultMap.put("msg", e.getMessage());
		}
    	return resultMap;
    }
    
    /**
     * 删除选中
     * @return
     */
    @RequestMapping("/deleteSelect.do")
    @ResponseBody
    public Map<String,Object> deleteSelect(@RequestParam(value="seleteIds", required=true) String seleteIds){
    	Map<String,Object> resultMap = new HashMap<String,Object>();
    	try {
    		List<?> seleteIdList = (List<?>) JsonMapper.fromJsonString(seleteIds, List.class);
    		if (seleteIds != null && seleteIdList.size() > 0) {
    			for (Object userId : seleteIdList) {
    				userService.deleteByPrimaryKey(userId.toString());
    			}
    			resultMap.put("OK", 1);
    		}
		} catch (Exception e) {
			e.printStackTrace();
			resultMap.put("OK", -1);
			resultMap.put("msg", e.getMessage());
		}
    	return resultMap;
    }
} 
