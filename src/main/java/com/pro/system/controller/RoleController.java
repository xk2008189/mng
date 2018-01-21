package com.pro.system.controller;


import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.cxf.common.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pro.common.Json.JsonMapper;
import com.pro.common.Util.UserUtil;
import com.pro.common.base.Page;
import com.pro.system.entity.Role;
import com.pro.system.service.RoleService;

/** 
 * 用户管理
 *  
 * @author xiangkun 
 * 
 */  
@Controller
@RequestMapping("/role")
public class RoleController {  
	@Autowired  
    private RoleService roleService;  
      
    
    @RequestMapping("/roleList.do")
    public String index(Model model,
    		 @RequestParam(value="roleCode", required=false) String roleCode,
    		 @RequestParam(value="name", required=false) String name,
    		 HttpServletRequest request) throws ParseException{
    	try {
    		// 1.设置查询条件
			Role role = new Role();
			Map<String, Object> sqlMap = new HashMap<String, Object>();
			if (!StringUtils.isEmpty(roleCode)) {
				sqlMap.put("roleCode", "%" + roleCode + "%");
				model.addAttribute("roleCode", roleCode);
			}
			if (!StringUtils.isEmpty(name)) {
				sqlMap.put("name", "%" + name + "%");
				model.addAttribute("name", name);
			}
			role.setSqlMap(sqlMap);
			// 2.查询分页
			Page<Role> page = new Page<Role>(request);
			page = roleService.findPage(page, role);
			
			model.addAttribute("page", page);
		} catch (Exception e) {
			e.printStackTrace();
		}
        return "system/role/roleList";
    }
    
    
    
    
    @RequestMapping("/roleAdd.do")
    public String roleAdd( Model model){
        return "system/role/roleAdd";
    }
    
    /**
     * 添加
     * @return
     */
    @RequestMapping("/add.do")
    @ResponseBody
    public Map<String,Object> add(Role role){
    	Map<String,Object> resultMap = new HashMap<String,Object>();
    	try {
    		String username = UserUtil.getLoginUserName();
			Date now = new Date();
			role.setId(UUID.randomUUID().toString());
			role.setDelFlag("0");
			role.setUseable("0");
			role.setCreateBy(username);
			role.setCreateDate(now);
			role.setUpdateBy(username);
			role.setUpdateDate(now);
			roleService.insert(role);
			resultMap.put("OK", 1);
		} catch (Exception e) {
			e.printStackTrace();
			resultMap.put("OK", -1);
			resultMap.put("msg", e.getMessage());
		}
    	return resultMap;
    }
    
    @RequestMapping("/roleUpdate.do")
    public String roleUpdate( Model model, @RequestParam(value="roleId", required=true) String roleId){
    	Role role = roleService.selectByPrimaryKey(roleId);
    	model.addAttribute("role", role);
        return "system/role/roleUpdate";
    }
    
    /**
     * 修改
     * @return
     */
    @RequestMapping("/update.do")
    @ResponseBody
    public Map<String,Object> update(Role role){
    	Map<String,Object> resultMap = new HashMap<String,Object>();
    	try {
    		String rolename = UserUtil.getLoginUserName();
			Date now = new Date();
			role.setUpdateBy(rolename);
			role.setUpdateDate(now);
			roleService.updateByPrimaryKeySelective(role);
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
    public Map<String,Object> delete(@RequestParam(value="roleId", required=true) String roleId){
    	Map<String,Object> resultMap = new HashMap<String,Object>();
    	try {
    		roleService.deleteByPrimaryKey(roleId);
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
    			for (Object roleId : seleteIdList) {
    				roleService.deleteByPrimaryKey(roleId.toString());
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
