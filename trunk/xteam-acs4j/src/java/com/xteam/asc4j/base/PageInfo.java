package com.xteam.asc4j.base;

import java.util.List;

public class PageInfo<T> {
	/**
	 * 数据集合
	 */
	private List<T> dataList;
	
	/**
	 * 数据总条数
	 */
	private long totalCount;

	public List<T> getDataList() {
		return dataList;
	}

	public void setDataList(List<T> dataList) {
		this.dataList = dataList;
	}

	public long getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(long totalCount) {
		this.totalCount = totalCount;
	}

}
