package com.mycompany.restlet.search.model;

public class SearchResultVo {
	private int totalCount = 0;
	private int page = 0;
	private int pagePerNum = 0;
	private ResultMap result = null;
	
	public int getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	public int getPagePerNum() {
		return pagePerNum;
	}
	public void setPagePerNum(int pagePerNum) {
		this.pagePerNum = pagePerNum;
	}
	public ResultMap getResult() {
		return result;
	}
	public void setResult(ResultMap result) {
		this.result = result;
	}
}
