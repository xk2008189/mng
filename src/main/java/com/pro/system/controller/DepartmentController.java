package com.pro.system.controller;


import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pro.common.Util.UserUtil;
import com.pro.system.entity.Department;
import com.pro.system.service.DepartmentService;


/** 
 * 用户管理
 *  
 * @author xiangkun 
 * 
 */  
@Controller
@RequestMapping("/department")
public class DepartmentController {  
	@Autowired  
    private DepartmentService departmentService;  
      
    
    @RequestMapping("/departmentList.do")
    public String index(Model model,
    		 HttpServletRequest request) throws ParseException{
        return "system/department/departmentList";
    }
    
    
    
    
    @RequestMapping("/departmentAdd.do")
    public String departmentAdd( Model model){
        return "system/department/departmentAdd";
    }
    
    /**
     * 添加
     * @return
     */
    @RequestMapping("/add.do")
    @ResponseBody
    public Map<String,Object> add(Department department){
    	Map<String,Object> resultMap = new HashMap<String,Object>();
    	try {
    		String username = UserUtil.getLoginUserName();
			Date now = new Date();
			department.setId(UUID.randomUUID().toString());
			department.setDelFlag("0");
			department.setUseable("0");
			department.setCreateBy(username);
			department.setCreateDate(now);
			department.setUpdateBy(username);
			department.setUpdateDate(now);
			departmentService.insert(department);
			resultMap.put("OK", 1);
		} catch (Exception e) {
			e.printStackTrace();
			resultMap.put("OK", -1);
			resultMap.put("msg", e.getMessage());
		}
    	return resultMap;
    }
    
    @RequestMapping("/departmentUpdate.do")
    public String departmentUpdate( Model model, @RequestParam(value="departmentId", required=true) String departmentId){
    	Department department = departmentService.selectByPrimaryKey(departmentId);
    	model.addAttribute("department", department);
        return "system/department/departmentUpdate";
    }
    
    /**
     * 修改
     * @return
     */
    @RequestMapping("/update.do")
    @ResponseBody
    public Map<String,Object> update(Department department){
    	Map<String,Object> resultMap = new HashMap<String,Object>();
    	try {
    		String departmentname = UserUtil.getLoginUserName();
			Date now = new Date();
			department.setUpdateBy(departmentname);
			department.setUpdateDate(now);
			departmentService.updateByPrimaryKeySelective(department);
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
    public Map<String,Object> delete(@RequestParam(value="departmentId", required=true) String departmentId){
    	Map<String,Object> resultMap = new HashMap<String,Object>();
    	try {
    		departmentService.deleteByPrimaryKey(departmentId);
			resultMap.put("OK", 1);
		} catch (Exception e) {
			e.printStackTrace();
			resultMap.put("OK", -1);
			resultMap.put("msg", e.getMessage());
		}
    	return resultMap;
    }
    
} 
