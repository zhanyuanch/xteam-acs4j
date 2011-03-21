/**
 * Copyright (c) 2011-2012 XTEAM
 * All rights reserved.
 */
/**
 * File：IDBServer.java
 * History:
 *         2011-3-21: Initially created, 袁孝均.
 */
package com.xteam.util.db.serv;

import java.util.List;
import java.util.Map;

/**
 * 数据库服务接口规范。
 * @author 袁孝均
 */
@SuppressWarnings("unchecked")
public interface IDBServer {
	/**
	 * 获取数据库服务名。
	 * @author 袁孝均 2011-3-21
	 * @return
	 */
	public String getDbServName();
	
	/**
	 * 设置数据库服务名。
	 * @author 袁孝均 2011-3-21
	 * @param name
	 */
	public void setDbServName(String name);
	
	/**
	 * 获取数据库服务配置。
	 * @author 袁孝均 2011-3-21
	 * @return
	 */
	public Map<String, String> getDbServProperty();
	
	/**
	 * 设置数据库服务配置。
	 * @author 袁孝均 2011-3-21
	 * @param property
	 */
	public void setDbServProperty(Map<String, String> property);
	
	/**
	 * 获取数据库服务映射。
	 * @author 袁孝均 2011-3-21
	 * @return
	 */
	public List<String> getDbServMapping();
	
	/**
	 * 设置数据库服务映射。
	 * @author 袁孝均 2011-3-21
	 * @param mapping
	 */
	public void setDbServMapping(List<String> mapping);
	
	/**
	 * 初始化数据库服务。
	 * @author 袁孝均 2011-3-21
	 * @throws Exception
	 */
	public void initialize() throws Exception;
	
	/**
	 * 关闭数据库服务。
	 * @author 袁孝均 2011-3-21
	 */
	public void close();
	
	/**
	 * 查询计数。
	 * @author 袁孝均 2011-3-21
	 * @param hql
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public long countByHql(String hql, Map<Object, Object> param) throws Exception;
	
	/**
	 * 查询计数。
	 * @author 袁孝均 2011-3-21
	 * @param sql
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public long countBySql(String sql, Map<Object, Object> param) throws Exception;
	
	/**
	 * 查询结果集。
	 * @author 袁孝均 2011-3-21
	 * @param hql
	 * @param param
	 * @param index
	 * @param max
	 * @return
	 * @throws Exception
	 */
	public List<Object> query(String hql, Map<Object, Object> param, Integer index, Integer max) throws Exception;
	
	/**
	 * 单个插入对象。
	 * @author 袁孝均 2011-3-21
	 * @param object
	 * @throws Exception
	 */
	public void insert(Object object) throws Exception;
	
	/**
	 * 批量插入对象。
	 * @author 袁孝均 2011-3-21
	 * @param list
	 * @throws Exception
	 */
	public void insertList(List<Object> list) throws Exception;
	
	/**
	 * 单个插入或更新对象。
	 * @author 袁孝均 2011-3-21
	 * @param object
	 * @throws Exception
	 */
	public void insertOrUpdate(Object object) throws Exception;
	
	/**
	 * 批量插入或更新对象。
	 * @author 袁孝均 2011-3-21
	 * @param list
	 * @throws Exception
	 */
	public void insertOrUpdateList(List<Object> list) throws Exception;
	
	/**
	 * 单个更新对象。
	 * @author 袁孝均 2011-3-21
	 * @param object
	 * @throws Exception
	 */
	public void update(Object object) throws Exception;
	
	/**
	 * 批量更新对象。
	 * @author 袁孝均 2011-3-21
	 * @param list
	 * @throws Exception
	 */
	public void updateList(List<Object> list) throws Exception;
	
	/**
	 * 单个删除对象。
	 * @author 袁孝均 2011-3-21
	 * @param object
	 * @throws Exception
	 */
	public void delete(Object object) throws Exception;
	
	/**
	 * 批量删除对象。
	 * @author 袁孝均 2011-3-21
	 * @param list
	 * @throws Exception
	 */
	public void deleteList(List<Object> list) throws Exception;
	
	/**
	 * 执行HQL语句。
	 * @author 袁孝均 2011-3-21
	 * @param hql
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public int executeHql(String hql, Map<Object, Object> param) throws Exception;
	
	/**
	 * 查询结果集。
	 * @author 袁孝均 2011-3-21
	 * @param sql
	 * @param param
	 * @param index
	 * @param max
	 * @return
	 * @throws Exception
	 */
	public List<Map> querySql(String sql, Map<Object, Object> param, Integer index, Integer max) throws Exception;
	
	/**
	 * 执行SQL语句。
	 * @author 袁孝均 2011-3-21
	 * @param sql
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public int executeSql(String sql, Map<Object, Object> param) throws Exception;
	
	/**
	 * 查询对象。
	 * @author 袁孝均 2011-3-21
	 * @param <T>
	 * @param type
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public <T> T queryObject(Class<T> type, String id) throws Exception;
	
	/**
	 * 删除对象。
	 * @author 袁孝均 2011-3-21
	 * @param type
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public int deleteObject(Class<?> type, String id) throws Exception;
	
	/**
	 * 查询结果集。
	 * @author 袁孝均 2011-3-21
	 * @param <T>
	 * @param type
	 * @param index
	 * @param max
	 * @return
	 * @throws Exception
	 */
	public <T> List<T> query(Class<T> type, Integer index, Integer max) throws Exception;
}
