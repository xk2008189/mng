package com.pro.system.controller;


import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
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
    
    
    /**
     * 获取部门树数据
     * @return
     */
    @RequestMapping("/getTreeData.do")
    @ResponseBody
    public Map<String,Object> getTreeData(){
    	Map<String,Object> resultMap = new HashMap<String,Object>();
    	try {
    		Department department =  new Department();
    		List<Map<String,Object>> treeData = departmentService.getTreeData(department);
			resultMap.put("OK", 1);
			resultMap.put("treeData", treeData);
		} catch (Exception e) {
			e.printStackTrace();
			resultMap.put("OK", -1);
			resultMap.put("msg", e.getMessage());
		}
    	return resultMap;
    }
    
    /**
     * 获取部门数据
     * @return
     */
    @RequestMapping("/getTreeTableData.do")
    @ResponseBody
    public Map<String,Object> getTreeTableData(@RequestParam(value="departmentId", required=false) String departmentId){
    	Map<String,Object> resultMap = new HashMap<String,Object>();
    	try {
    		if (StringUtils.isBlank(departmentId)) {
    			departmentId = "1";
    		}
    		List<Department> departmentList = departmentService.getTreeTableData(departmentId);
			resultMap.put("OK", 1);
			resultMap.put("departmentList", departmentList);
		} catch (Exception e) {
			e.printStackTrace();
			resultMap.put("OK", -1);
			resultMap.put("msg", e.getMessage());
		}
    	return resultMap;
    }
    
    @RequestMapping("/departmentAdd.do")
    public String departmentAdd( Model model, @RequestParam(value="parentId", required=true) String parentId){
    	model.addAttribute("parentId", parentId);
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
    		
    		String parentId = department.getParentId();
    		if (StringUtils.isBlank(parentId)) {
    			resultMap.put("OK", -1);
    			resultMap.put("msg", "缺少父节点ID");
    		}
    		Department parentDepartment = 
    				departmentService.selectByPrimaryKey(parentId);
    		
    		String parentGrade = parentDepartment.getGrade();
    		department.setGrade((Integer.parseInt(parentGrade) + 1) + "");
    		
    		// 更新排序
    		String parentSort = parentDepartment.getSort();
    		List<Department> departmentChildList = departmentService.findChildList(parentDepartment);
    		if (departmentChildList != null && departmentChildList.size() > 0) {
    			String lastSort = departmentChildList.get(0).getSort();
    			String lastIndextStr = lastSort.replace(parentSort + "_", "");
    			int lastIndex = Integer.parseInt(lastIndextStr);
    			String sort = parentSort + "_" + (lastIndex + 1);
    			department.setSort(sort);
    		} else {
    			String sort = parentSort + "_1";
    			department.setSort(sort);
    		}
    		
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
    		Department  department = departmentService.selectByPrimaryKey(departmentId);
    		List<Department> departmentChildList = departmentService.findChildList(department);
    		if (departmentChildList != null && departmentChildList.size() > 0) {
    			resultMap.put("OK", -1);
    			resultMap.put("msg", "请先删除子节点");
    		} else {
    			departmentService.deleteByPrimaryKey(departmentId);
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
