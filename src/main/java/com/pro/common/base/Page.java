package com.pro.common.base;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.pro.common.Util.StringUtils;

/**
 * 分页类
 * @author xiangkun
 *
 */
public class Page<T> {
	private int currentPageNo = 1; //  当前页码
	
	private int pageSize = 10; // 每页数量
	
	private long count = 0; // 总数量
	
	private List<T> list = new ArrayList<T>(); // 分页查询结果
	
	/**
	 * 构造方法
	 * @param request 传递 repage 参数，来记住页码
	 * @param response 用于设置 Cookie，记住页码
	 */
	public Page(HttpServletRequest request){
		String currentPageNo = request.getParameter("currentPageNo");
		if (StringUtils.isNotBlank(currentPageNo) && StringUtils.isNumeric(currentPageNo)) {
			this.setCurrentPageNo(Integer.parseInt(currentPageNo));
		}
		String pageSize = request.getParameter("pageSize");
		if (StringUtils.isNotBlank(pageSize) && StringUtils.isNumeric(pageSize)) {
			this.setPageSize(Integer.parseInt(pageSize));
		}

	}
	

	public int getCurrentPageNo() {
		return currentPageNo;
	}

	public void setCurrentPageNo(int currentPageNo) {
		this.currentPageNo = currentPageNo;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public long getCount() {
		return count;
	}

	public void setCount(long count) {
		this.count = count;
	}

	public List<T> getList() {
		return list;
	}

	public void setList(List<T> list) {
		this.list = list;
	}
}
