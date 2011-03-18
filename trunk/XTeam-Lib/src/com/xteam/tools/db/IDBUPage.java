/*
 * Copyright (c) 2005-2008 旭鸣软件
 * All rights reserved. 
 */
/*
 * File：IDBUPage.java
 * History:
 *       2009-1-16: Initially created, 汤垲峰.
 */
package com.xteam.tools.db;

import java.util.List;
import java.util.Map;

import com.xteam.tools.db.page.Page;

/**
 * <p>
 * 分页使用的接口
 * </p>
 * @author 汤垲峰
 *
 */
public interface IDBUPage {
	/**
	 * <P>
	 * 执行SQL语句
	 * </P>
	 * @author 汤垲峰 2009-1-16
	 * @param sql
	 * @param parms
	 * @param firstResult
	 * @param maxResult
	 * @return
	 */
	public List<Object[]> executeSQLQuery(String sql,Map<Integer,Object> parms,int firstResult,int maxResult);
	
	/**
	 * <P>
	 * 执行分页功能的SQL语句
	 * </P>
	 * @author 汤垲峰 2009-4-27
	 * @param sql
	 * @param parms
	 * @param page
	 * @return
	 */
	public List<Object[]> executeSQLQuery(String sql,Map<Integer,Object> parms,Page page);

	/**
	 * <P>
	 * 执行一个有返回值的SQL语句
	 * </P>
	 * @author 汤垲峰 2009-1-16
	 * @param type
	 * @param sql
	 * @param firstResult
	 * @param maxResult
	 * @return
	 */
	public <T> List<T> executeSQLQuery(Class<T> type, String sql,int firstResult,int maxResult);
	
	/**
	 * <P>
	 * 执行一个有返回值的SQL语句
	 * </P>
	 * @author 汤垲峰 2009-4-27
	 * @param type
	 * @param sql
	 * @param page
	 * @return
	 */
	public <T> List<T> executeSQLQuery(Class<T> type, String sql,Page page);
	
	/**
	 * <P>
	 *  获取一个指定类型的结果列表
	 * </P>
	 * @author 汤垲峰 2009-1-16
	 * @param type
	 * @param firstResult
	 * @param maxResult
	 * @return
	 */
	public <T> List<T> getQueryResult(Class<T> type,int firstResult,int maxResult);
	
	/**
	 * <P>
	 * 执行分页功能的SQL 语句
	 * </P>
	 * @author 汤垲峰 2009-4-27
	 * @param type
	 * @param page
	 * @return
	 */
	public <T> List<T> getQueryResult(Class<T> type,Page page);
	
	/**
	 * <P>
	 * 根据字符串匹配来查询相应结果
	 * </P>
	 * @author 汤垲峰 2009-1-16
	 * @param hql
	 * @param parametersMap
	 * @param firstResult
	 * @param maxResult
	 * @return
	 */
	public <T> List<T> getQueryResult(String hql, Map<String,Object> parametersMap,int firstResult,int maxResult);
	
	/**
	 * <P>
	 * 返回分页结果
	 * </P>
	 * @author 汤垲峰 2009-4-27
	 * @param hql
	 * @param parametersMap 参数
	 * @param page 分页接口
	 * @return
	 */
	public <T> List<T> getQueryResult(String hql, Map<String, Object> parametersMap,Page page);
	
	/**
	 * <P>
	 * 查询一个HQL的结果
	 * </P>
	 * @author 汤垲峰 2009-1-16
	 * @param hql
	 * @param firstResult
	 * @param maxResult
	 * @return
	 */
	public <T> List<T> getQueryResult(String hql,int firstResult,int maxResult);
	
	/**
	 * <P>
	 * 分页功能HQL
	 * </P>
	 * @author 汤垲峰 2009-4-27
	 * @param hql
	 * @param firstResult
	 * @param maxResult
	 * @return
	 */
	public <T> List<T> getQueryResult(String hql,Page page);

	/**
	 * 执行一个带条件和分页的SQL语句
	 * @author 汤垲峰 2009-12-27
	 * @param sql
	 * @param parms
	 * @param firstResult
	 * @param maxResult
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Object[]> executeSQLQuerySIDX(String sql, Map<String, Object> parms, int firstResult,
			int maxResult);

	/**
	 * 执行一个带条件MAP的SQL语句
	 * @author 汤垲峰 2009-12-27
	 * @param sql
	 * @param parms
	 * @param page
	 * @return
	 */
	public List<Object[]> executeSQLQuerySIDX(String sql, Map<String, Object> parms, Page page);
}
