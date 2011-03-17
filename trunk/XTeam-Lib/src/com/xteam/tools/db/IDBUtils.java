/*
 * Copyright (c) 2005-2008 成均科技
 * All rights reserved. 
 */
/*
 * File：IDBWriter.java
 * History:
 *       2007-12-11: Initially created, tangkf.
 */
package com.xteam.tools.db;

import java.util.List;
import java.util.Map;


/**
 * 实现数据库的读写等操作的接口
 * @author tangkf
 *
 */
public interface IDBUtils<T> extends IDBUPage<T>{
	
	public static final String SQLDELETE		= " DELETE ";
	public static final String SQLUPDATE		= " UPDATE ";
	public static final String SQLFROM		= " FROM ";
	public static final String SQLWHERE		= " WHERE ";
	public static final String SQLAND			= " AND ";
	public static final String SQLOR			= " OR ";
	public static final String SQLIN			= " IN ";
	public static final String SQLSELECT		= " SELECT ";
	public static final String SQLNOTIN		= " NOT IN ";
	public static final String SQLORDER_BY	= " ORDER BY ";
	public static final String SQLGROUPBY   = " GROUP BY ";
	public static final String SQLHAVING    = " HAVING ";
	
	/**
	 * <p>
	 * 执行一个没有返回值的 SQL 语句
	 * </p>
	 * @author tangkf
	 * @param sql
	 */
	public int executeSQLUpdate(String sql);
	
	/**
	 * <p>
	 * 执行一个有返回值的SQL语句
	 * </p>
	 * @author tangkf
	 * @param type
	 * @param sql
	 * @return
	 */
	public List<T> executeSQLQuery(Class<T> type, String sql);
	
	/**
	 * <p>
	 * 执行一个有返回值的SQL语句
	 * </p>
	 * @author tangkf
	 * @param sql
	 * @return
	 */
	public List<T> executeSQLQuery(String sql);

	/**
	 * <P>
	 * 执行SQL语句
	 * </P>
	 * @author 汤垲峰 2008-11-24
	 * @param sql
	 * @return
	 */
	public List<Object[]> executeSQLQuery(String sql,Map<Integer,Object> parms);

	/**
	 * <p>
	 * 获取一个指定类型的 数据对象
	 * </p>
	 * @author tangkf
	 * @param type
	 * @param id
	 * @return
	 */
	public T getObject(Class<T> type,String id);
	
	/**
	 * <p>
	 * 获取一个指定类型的结果列表
	 * </p>
	 * @author tangkf
	 * @param type 类型
	 * @return 一个该类型的对象列表
	 */
	public List<T> getQueryResult(Class<T> type);

	/**
	 * <p>
	 * 立即执行一个没有返回的HQL语句，立即flush
	 * </p>
	 * @author tangkf
	 * @param hql
	 */
	public int executeHQLUpdate(String hql);
	
	/**
	 * 执行一个SQL语句，不立即 flush
	 * @author 汤垲峰 2009-12-27
	 * @param hql
	 * @return
	 */
	public int executeHQLUpdate2Cache(String hql);
	
	/**
	 * <p>
	 * 返回一个指定的 HQL 语句查询结果的第一个对象
	 * </p>
	 * @author tangkf
	 * @param hql
	 * @return
	 */
	public T getObject(String hql);
	
	/**
	 * 根据字符串匹配来查询相应结果
	 * @author tnagkf
	 * @param hql 查询语句
	 * @param parametersMap key为自定义的key,value为需要匹配的字符串
	 * @return 根据字符串匹配来查询相应结果
	 */
	public List<T> getQueryResult(String hql, Map<String, Object> parametersMap);

	/**
	 * 查询一个HQL的结果
	 * @author tangkf
	 * @param hql
	 * @return
	 */
	public List<T> getQueryResult(String hql);
	
	/**
	 * 保存一个对象
	 * @author  tangkf
	 * @param obj: 要保存的对象
	 */
	public int saveObject(T obj);
	

	/**
	 * 保存多个对象
	 * @author tangkf
	 * @param objects
	 */
	public int saveObjects(List<T> objects);
	
	/**
	 * 删除一个对象
	 * @author tangkf
	 * @param obj
	 */
	public int delete(T obj);
	
	/**
	 * 一次删除多个对象
	 * @author tangkf
	 * @param objects: 将要删除的对象列表
	 */
	public int deleteObjects(List<T> objects) ;
	
	/**
	 * 由指定的类型,ID名称和ID值列表删除对象
	 * @author tangkf
	 * @param type
	 * @param idName
	 * @param idValues
	 */
	public int deleteBathById(Class<T> type,String idName,String[] idValues);
	
	/**
	 * 保存或更新一个对象，这个对象是一个entity包中的类
	 * @author  tangkf
	 * @param o: 将要更新的对象
	 */
	public int saveOrUpdateObj(Object o);
	
	/**
	 * 保存或更新一个对象，这个对象是一个entity包中的类
	 * @author  tangkf
	 * @param o: 将要更新的对象
	 */
	public int saveOrUpdateObject(T o);
	
	/**
	 * 保存或更新一个对象列表，这个对象是一个entity包中的类
	 * @author  tangkf
	 * @param objects: 将要更新的对象列表
	 */
	public int saveOrUpdateObjs(List<?> objects);
	
	/**
	 * 保存或更新一个对象列表，这个对象是一个entity包中的类
	 * @author  tangkf
	 * @param objects: 将要更新的对象列表
	 */
	public int saveOrUpdateObjects(List<T> objects);
	
	/**
	 * 更新一个对象
	 * @author  tangkf
	 * @param obj: 要更新的对象
	 */
	public int updateObject(T obj);
	
	/**
	 * 更新多个对象并且更新之后关闭数据库连接
	 * @author  tangkf
	 * @param objects:要更新的对象列表
	 */
	public int updateObjects(List<T> objects);

	/**
	 * <P>
	 * 返回 指定返回记录数的SQL语句;
	 * </P>
	 * @author 汤垲峰 2009-4-27
	 * @param sql
	 * @return
	 */
	@Deprecated
	public long getSQLCount(String sql);
	
	/**
	 * <P>
	 * 返回 指定返回记录数的SQL语句;
	 * </P>
	 * @author 汤垲峰 2009-4-27
	 * @param sql
	 * @return
	 */
	public long executeSQLCount(String sql);

	/**
	 * <P>
	 * 返回 指定HQL语句执行返回的记录数
	 * </P>
	 * @author 汤垲峰 2009-4-27
	 * @param hql
	 * @return
	 */
	public long executeHQLCount(String hql);

	/**
	 * 执行一批HQL语句，执行完成后立即 flush
	 * @author 汤垲峰 2009-12-27
	 * @param hql
	 * @return
	 */
	public int executeBathHQLUpdate(String[] hqls);

	/**
	 * 执行一个SQL并不立即 FLUSH，可能被HIBERNAT 缓存
	 * @athor tangkf
	 * @date 2008-1-4
	 */
	public int executeSQLUpdate2Cache(String sql);

	/**
	 * 批量执行SQL语句，批量执行完成后立即 flush
	 * @author 汤垲峰 2009-12-27
	 * @param sqls
	 * @return
	 */
	public int executeBathSQLUpdate(String[] sqls);

	/**
	 * 执行指定了条件MAP的SQL语句
	 * @author 汤垲峰 2009-12-27
	 * @param sql
	 * @param parms
	 * @return
	 */
	public List<Object[]> executeSQLQuerySIDX(String sql, Map<String, Object> parms);
}
