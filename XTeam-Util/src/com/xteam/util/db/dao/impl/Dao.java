/**
 * Copyright (c) 2011-2012 XTEAM
 * All rights reserved.
 */
/**
 * File：Dao.java
 * History:
 *         2011-3-21: Initially created, 袁孝均.
 */
package com.xteam.util.db.dao.impl;

import java.util.List;
import java.util.Map;

import com.xteam.util.db.dao.IDao;
import com.xteam.util.db.serv.IDBServer;

/**
 * 通用数据库访问对象。
 * @author 袁孝均
 */
@SuppressWarnings("unchecked")
public class Dao implements IDao {
	private IDBServer server;
	
	/**
	 * @see com.xteam.util.db.dao.IDao#initialize(com.xteam.util.db.serv.IDBServer)
	 */
	public void initialize(IDBServer server) {
		this.server = server;
	}
	
	/**
	 * @see com.xteam.util.db.dao.IDao#countByHql(java.lang.String)
	 */
	public long countByHql(String hql) throws Exception {
		return this.server.countByHql(hql, null);
	}
	
	/**
	 * @see com.xteam.util.db.dao.IDao#countByHql(java.lang.String, java.util.Map)
	 */
	public long countByHql(String hql, Map<Object, Object> param) throws Exception {
		return this.server.countByHql(hql, param);
	}
	
	/**
	 * @see com.xteam.util.db.dao.IDao#countBySql(java.lang.String)
	 */
	public long countBySql(String sql) throws Exception {
		return this.server.countBySql(sql, null);
	}
	
	/**
	 * @see com.xteam.util.db.dao.IDao#countBySql(java.lang.String, java.util.Map)
	 */
	public long countBySql(String sql, Map<Object, Object> param) throws Exception {
		return this.server.countBySql(sql, param);
	}
	
	/**
	 * @see com.xteam.util.db.dao.IDao#query(java.lang.String)
	 */
	public List<Object> query(String hql) throws Exception {
		return this.server.query(hql, null, null, null);
	}
	
	/**
	 * @see com.xteam.util.db.dao.IDao#query(java.lang.String, java.util.Map)
	 */
	public List<Object> query(String hql, Map<Object, Object> param) throws Exception {
		return this.server.query(hql, param, null, null);
	}
	
	/**
	 * @see com.xteam.util.db.dao.IDao#query(java.lang.String, java.lang.Integer, java.lang.Integer)
	 */
	public List<Object> query(String hql, Integer index, Integer max) throws Exception {
		return this.server.query(hql, null, index, max);
	}
	
	/**
	 * @see com.xteam.util.db.dao.IDao#query(java.lang.String, java.util.Map, java.lang.Integer, java.lang.Integer)
	 */
	public List<Object> query(String hql, Map<Object, Object> param, Integer index, Integer max) throws Exception {
		return this.server.query(hql, param, index, max);
	}
	
	/**
	 * @see com.xteam.util.db.dao.IDao#insert(java.lang.Object)
	 */
	public void insert(Object object) throws Exception {
		this.server.insert(object);
	}
	
	/**
	 * @see com.xteam.util.db.dao.IDao#insertList(java.util.List)
	 */
	public void insertList(List<Object> list) throws Exception {
		this.server.insertList(list);
	}
	
	/**
	 * @see com.xteam.util.db.dao.IDao#insertOrUpdate(java.lang.Object)
	 */
	public void insertOrUpdate(Object object) throws Exception {
		this.server.insertOrUpdate(object);
	}
	
	/**
	 * @see com.xteam.util.db.dao.IDao#insertOrUpdateList(java.util.List)
	 */
	public void insertOrUpdateList(List<Object> list) throws Exception {
		this.server.insertOrUpdateList(list);
	}
	
	/**
	 * @see com.xteam.util.db.dao.IDao#update(java.lang.Object)
	 */
	public void update(Object object) throws Exception {
		this.server.update(object);
	}
	
	/**
	 * @see com.xteam.util.db.dao.IDao#updateList(java.util.List)
	 */
	public void updateList(List<Object> list) throws Exception {
		this.server.updateList(list);
	}
	
	/**
	 * @see com.xteam.util.db.dao.IDao#delete(java.lang.Object)
	 */
	public void delete(Object object) throws Exception {
		this.server.delete(object);
	}
	
	/**
	 * @see com.xteam.util.db.dao.IDao#deleteList(java.util.List)
	 */
	public void deleteList(List<Object> list) throws Exception {
		this.server.deleteList(list);
	}
	
	/**
	 * @see com.xteam.util.db.dao.IDao#executeHql(java.lang.String)
	 */
	public int executeHql(String hql) throws Exception {
		return this.server.executeHql(hql, null);
	}
	
	/**
	 * @see com.xteam.util.db.dao.IDao#executeHql(java.lang.String, java.util.Map)
	 */
	public int executeHql(String hql, Map<Object, Object> param) throws Exception {
		return this.server.executeHql(hql, param);
	}
	
	/**
	 * @see com.xteam.util.db.dao.IDao#querySql(java.lang.String)
	 */
	public List<Map> querySql(String sql) throws Exception {
		return this.server.querySql(sql, null, null, null);
	}
	
	/**
	 * @see com.xteam.util.db.dao.IDao#querySql(java.lang.String, java.util.Map)
	 */
	public List<Map> querySql(String sql, Map<Object, Object> param) throws Exception {
		return this.server.querySql(sql, param, null, null);
	}
	
	/**
	 * @see com.xteam.util.db.dao.IDao#querySql(java.lang.String, java.lang.Integer, java.lang.Integer)
	 */
	public List<Map> querySql(String sql, Integer index, Integer max) throws Exception {
		return this.server.querySql(sql, null, index, max);
	}
	
	/**
	 * @see com.xteam.util.db.dao.IDao#querySql(java.lang.String, java.util.Map, java.lang.Integer, java.lang.Integer)
	 */
	public List<Map> querySql(String sql, Map<Object, Object> param, Integer index, Integer max) throws Exception {
		return this.server.querySql(sql, param, index, max);
	}
	
	/**
	 * @see com.xteam.util.db.dao.IDao#executeSql(java.lang.String)
	 */
	public int executeSql(String sql) throws Exception {
		return this.server.executeSql(sql, null);
	}
	
	/**
	 * @see com.xteam.util.db.dao.IDao#executeSql(java.lang.String, java.util.Map)
	 */
	public int executeSql(String sql, Map<Object, Object> param) throws Exception {
		return this.server.executeSql(sql, param);
	}
	
	/**
	 * @see com.xteam.util.db.dao.IDao#queryObject(java.lang.Class, java.lang.String)
	 */
	public <T> T queryObject(Class<T> type, String id) throws Exception {
		return this.server.queryObject(type, id);
	}
	
	/**
	 * @see com.xteam.util.db.dao.IDao#deleteObject(java.lang.Class, java.lang.String)
	 */
	public int deleteObject(Class<?> type, String id) throws Exception {
		return this.server.deleteObject(type, id);
	}
	
	/**
	 * @see com.xteam.util.db.dao.IDao#query(java.lang.Class)
	 */
	public <T> List<T> query(Class<T> type) throws Exception {
		return this.server.query(type, null, null);
	}
	
	/**
	 * @see com.xteam.util.db.dao.IDao#query(java.lang.Class, java.lang.Integer, java.lang.Integer)
	 */
	public <T> List<T> query(Class<T> type, Integer index, Integer max) throws Exception {
		return this.server.query(type, index, max);
	}
}
