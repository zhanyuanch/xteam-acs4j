/**
 * Copyright (c) 2005-2009 旭鸣软件
 * All rights reserved.
 */
/**
 * File：GenericDao.java
 * History:
 *         2010-6-11: Initially created, huangchun.
 */
package com.xteam.asc4j.module.dao.base;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.List;

import org.hibernate.Session;

import com.xteam.asc4j.base.PageInfo;
/**
 * 基于泛型的通用Dao接口
 * 
 * @author huangchun
 */
public interface GenericDao<T> {
	/**
	 * 保存
	 * 
	 * @datetime 创建时间:2010-6-17 下午02:35:33
	 * @param entity
	 * 
	 * @author huangchun
	 */
	void save(T entity);

	/**
	 * 删除
	 * 
	 * @datetime 创建时间:2010-6-17 下午02:35:38
	 * @param entity
	 * 
	 * @author huangchun
	 */
	void delete(T entity);

	/**
	 * 批量删除
	 * 
	 * @datetime:2010-6-22 下午05:07:37
	 * @param idName
	 *            主键字段名称
	 * @param idValues
	 *            主键字段值
	 * 
	 * @author huangchun
	 */
	void deleteBathByIds(String idName, String[] idValues);

	/**
	 * 更新
	 * 
	 * @datetime 创建时间:2010-6-17 下午02:35:47
	 * @param entity
	 * 
	 * @author huangchun
	 */
	void merge(T entity);

	/**
	 * 更新数据
	 * @param e void
	 * @author:Leo
	 */
	void update(T e);
	/**
	 * 按ID查询
	 * 
	 * @datetime 创建时间:2010-6-17 下午02:35:54
	 * @param id
	 * @return
	 * 
	 * @author huangchun
	 */
	T findById(Serializable id);

	/**
	 * 查询全部
	 * 
	 * @datetime 创建时间:2010-6-17 下午02:36:05
	 * @return
	 * 
	 * @author huangchun
	 */
	List<T> findAll();

	/**
	 * 分页查询
	 * 
	 * @datetime 创建时间:2010-6-17 下午02:36:13
	 * @param start
	 * @param length
	 * @return
	 * 
	 * @author huangchun
	 */
	List<T> findList(int start, int length);

	/**
	 * 获得全部数据条数
	 * 
	 * @datetime 创建时间:2010-6-17 下午02:36:24
	 * @return
	 * 
	 * @author huangchun
	 */
	int getCountOfAll();

	/**
	 * 按条件查询
	 * 
	 * @datetime:2010-6-22 上午10:39:38
	 * @param t
	 * @return
	 * 
	 * @author huangchun
	 */
	List<T> findByExample(T t);

	/**
	 * 按条件分页查询
	 * 
	 * @datetime 创建时间:2010-6-17 下午03:26:36
	 * @param t
	 * @param start
	 * @param length
	 * @return
	 * 
	 * @author huangchun
	 */
	List<T> findByExampleByPage(T t, int start, int length);

	/**
	 * 按属性查询
	 * 
	 * @datetime 创建时间:2010-6-17 下午03:27:14
	 * @param propertyName
	 * @param value
	 * @return
	 * 
	 * @author huangchun
	 */
	List<T> findByProperty(String propertyName, Object value);

	/**
	 * 按属性名称查询该字段的最大值
	 * 
	 * @param propertyName
	 * @return
	 */
	Long getMaxByProperty(String propertyName, String tableName, String wherehql, Object[] params);

	/**
	 * 按属性名称查询该字段的最小值
	 * 
	 * @param propertyName
	 * @return
	 */
	Long getMinByProperty(String propertyName, String tableName, String wherehql, Object[] params);

	/**
	 * 分页查询
	 * 
	 * @datetime:2010-6-22 下午10:04:25
	 * @param firstindex
	 * @param maxresult
	 * @param wherehql
	 * @param queryParams
	 * @param orderby
	 * @return
	 * 
	 * @author huangchun
	 */
	public PageInfo<T> getScrollData(int firstindex, int maxresult, String wherehql, Object[] queryParams,
			LinkedHashMap<String, String> orderby);

	/**
	 * 执行查询sql语句
	 * 
	 * @param sql
	 * @param params
	 * @param start
	 * @param length
	 * @return
	 * 
	 * @author huangchun
	 */
	public List<Object[]> getListBySQL(String sql, Object[] params, int start, int length);

	/**
	 * 根据查询语句查询列表(使用 Hibernate HQL)
	 * 
	 * @param hql
	 *            查询语句
	 * @param start
	 *            起始记录
	 * @param length
	 *            最大记录数
	 * @return 查询的结果列表
	 * @author huangchun
	 */
	@SuppressWarnings("unchecked")
	public List getListByHQL(String hql, Object[] params, int start, int length);

	/**
	 * 根据sql语句,获取数据条数
	 * 
	 * @param sql
	 * @param params
	 * @return
	 */
	public Long getCountBySQLParams(String sql, Object[] params);

	/**
	 * 执行SQL语句
	 * 
	 * @param sql
	 *            语句
	 * @author huangchun
	 */
	public void executeSQL(String sql);
	
	/**
	 * 执行SQL语句，参数列表
	 * @param sql
	 * @param params void
	 * @author:Leo
	 */
	public void executeSQL(String sql,Object[] params);

	/**
	 * 执行HQL语句
	 * 
	 * @param hql
	 *            语句
	 * @author huangchun
	 */
	public void executeHQL(String hql);
	
	/**
	 * 执行HQL语句,参数列表
	 * 
	 * @param hql
	 *            语句
	 * @author huangchun
	 */
	public void executeHQL(String hql,Object[] params);

	/**
	 * 执行HQL语句 推荐批量删除 使用
	 * 
	 * @param hql
	 *            语句
	 * @param param
	 *            hql 语句中的参数
	 * 
	 * @author huangchun
	 */
	public int executeDelHQL(String hql, String param);

	/**
	 * 获得Session句柄
	 * 
	 * @return Session
	 * @author:Leo
	 */
	public Session getDaoSession();
	
	/**
	 * 获取列表，按指定字段排序
	 * @param startRecord
	 * @param length
	 * @param order
	 * @return List<T>
	 * @author:Leo
	 */
	List<T> findList(int startRecord, int length, String order);
}