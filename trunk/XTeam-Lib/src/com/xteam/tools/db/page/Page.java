/*
 * Copyright (c) 2005-2008 旭鸣软件
 * All rights reserved. 
 */
/*
 * File：IDaoPage.java
 * History:
 *       2009-1-16: Initially created, 汤垲峰.
 */
package com.xteam.tools.db.page;

/**
 * <p>
 * 分页接口
 * </p>
 * @author 汤垲峰
 */
public interface Page {
	
	/**
	 * 默认每页大小
	 */
	public static int PAGE_SIEZ	= 32;
	
    /**
     * 返回总页数
     */
    int getPageCount();

    /**
     * 返回当前页的记录条数
     */
    int getPageRowsCount();

    /**
     * 返回分页大小
     */
    int getPageSize();

    /**
     * 转到指定页
     */
    void gotoPage(int page);

    /**
     * 设置分页大小
     */
    void setPageSize(int pageSize);

    /**
     * 返回总记录行数
     */
    long getRowsCount();

    /**
     * 转到首页
     */
    void gotoFirstPage();

    /**
     * 转到最后页
     */
    void gotoLastPage();

    /**
     * 返回当前页号
     */
    int getCurtPage();

	/**
	 * <P>
	 * 返回当前起始记录号
	 * </P>
	 * @author 汤垲峰 2009-4-27
	 * @return
	 */
	int getFirstRow();

	/**
	 * 返回 获取总记录数的SQL语句 一般建议为：select count(<ID>) from <TABLE>
	 * @return 属性rowCountSQL的值.
	 */
	public String getRowCountSQL();

	/**
	 * 设置 获取总记录数的SQL语句 一般建议为：select count(<ID>) from <TABLE>
	 * @param rowCountSQL 属性rowCountSQL.
	 */
	public void setRowCountSQL(String rowCountSQL);

	/**
	 * @return 属性curtRowCount的值.
	 */
	public int getCurtRowCount();

	/**
	 * @param rowsCount 属性rowsCount.
	 */
	public void setRowsCount(long rowsCount);

	/**
	 * @return 属性rowCountHQL的值.
	 */
	public String getRowCountHQL();

	/**
	 * @param rowCountHQL 属性rowCountHQL.
	 */
	public void setRowCountHQL(String rowCountHQL);
}
