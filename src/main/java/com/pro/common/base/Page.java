package com.pro.common.base;

/**
 * 分页类
 * @author xiangkun
 *
 */
public class Page {
	private int currentPageNo = 1; // 当前页码
	
	private int pageSize = 10; //每页数量
	
	private long count = 0; //总数量

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
}
