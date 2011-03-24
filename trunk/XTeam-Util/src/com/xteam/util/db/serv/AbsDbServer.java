/**
 * Copyright (c) 2011-2012 XTEAM
 * All rights reserved.
 */
/**
 * File：AbsDbServer.java
 * History:
 *         2011-3-21: Initially created, 袁孝均.
 */
package com.xteam.util.db.serv;

import java.util.List;
import java.util.Map;

/**
 * 数据库服务基类。
 * @author 袁孝均
 */
@SuppressWarnings("unchecked")
public abstract class AbsDbServer implements IDBServer {
	private static final String WIN_APP_SRC = "bin/";
	private static final String WEB_APP_SRC = "classes/";
	
	protected String appRoot;
	protected String dbServName;
	protected String dbConfigPath;
	
	/**
	 * 数据库服务基类构造方法。
	 */
	public AbsDbServer() {
		String root = Thread.currentThread().getContextClassLoader().getResource("").toString();
		if (root.endsWith(WIN_APP_SRC)) {
			this.appRoot = root.substring(0, root.lastIndexOf(WIN_APP_SRC));
		} else if (root.endsWith(WEB_APP_SRC)) {
			this.appRoot = root.substring(0, root.lastIndexOf(WEB_APP_SRC));
		} else {
			this.appRoot = root;
		}
		this.dbServName = "";
		this.dbConfigPath = "";
	}
	
	/**
	 * @see com.xteam.util.db.serv.IDBServer#getDbServName()
	 */
	public final String getDbServName() {
		return this.dbServName;
	}
	
	/**
	 * @see com.xteam.util.db.serv.IDBServer#setDbServName(java.lang.String)
	 */
	public final void setDbServName(String name) {
		this.dbServName = name;
	}
	
	/**
	 * @see com.xteam.util.db.serv.IDBServer#getDbConfigPath()
	 */
	public String getDbConfigPath() {
		return this.dbConfigPath;
	}
	
	/**
	 * @see com.xteam.util.db.serv.IDBServer#setDbConfigPath(java.lang.String)
	 */
	public void setDbConfigPath(String path) {
		this.dbConfigPath = (path.startsWith("/")) ? path.substring(1) : path;
	}
	
	/**
	 * @see com.xteam.util.db.serv.IDBServer#countByHql(java.lang.String, java.util.Map)
	 */
	public long countByHql(String hql, Map<Object, Object> param) throws Exception {
		return 0L;
	}
	
	/**
	 * @see com.xteam.util.db.serv.IDBServer#countBySql(java.lang.String, java.util.Map)
	 */
	public long countBySql(String sql, Map<Object, Object> param) throws Exception {
		return 0L;
	}
	
	/**
	 * @see com.xteam.util.db.serv.IDBServer#query(java.lang.String, java.util.Map, java.lang.Integer, java.lang.Integer)
	 */
	public List<Object> query(String hql, Map<Object, Object> param, Integer index, Integer max) throws Exception {
		return null;
	}
	
	/**
	 * @see com.xteam.util.db.serv.IDBServer#insert(java.lang.Object)
	 */
	public void insert(Object object) throws Exception {
		
	}
	
	/**
	 * @see com.xteam.util.db.serv.IDBServer#insertList(java.util.List)
	 */
	public void insertList(List<Object> list) throws Exception {
		
	}
	
	/**
	 * @see com.xteam.util.db.serv.IDBServer#insertOrUpdate(java.lang.Object)
	 */
	public void insertOrUpdate(Object object) throws Exception {
		
	}
	
	/**
	 * @see com.xteam.util.db.serv.IDBServer#insertOrUpdateList(java.util.List)
	 */
	public void insertOrUpdateList(List<Object> list) throws Exception {
		
	}
	
	/**
	 * @see com.xteam.util.db.serv.IDBServer#update(java.lang.Object)
	 */
	public void update(Object object) throws Exception {
		
	}
	
	/**
	 * @see com.xteam.util.db.serv.IDBServer#updateList(java.util.List)
	 */
	public void updateList(List<Object> list) throws Exception {
		
	}
	
	/**
	 * @see com.xteam.util.db.serv.IDBServer#delete(java.lang.Object)
	 */
	public void delete(Object object) throws Exception {
		
	}
	
	/**
	 * @see com.xteam.util.db.serv.IDBServer#deleteList(java.util.List)
	 */
	public void deleteList(List<Object> list) throws Exception {
		
	}
	
	/**
	 * @see com.xteam.util.db.serv.IDBServer#executeHql(java.lang.String, java.util.Map)
	 */
	public int executeHql(String hql, Map<Object, Object> param) throws Exception {
		return 0;
	}
	
	/**
	 * @see com.xteam.util.db.serv.IDBServer#querySql(java.lang.String, java.util.Map, java.lang.Integer, java.lang.Integer)
	 */
	public List<Map> querySql(String sql, Map<Object, Object> param, Integer index, Integer max) throws Exception {
		return null;
	}
	
	/**
	 * @see com.xteam.util.db.serv.IDBServer#executeSql(java.lang.String, java.util.Map)
	 */
	public int executeSql(String sql, Map<Object, Object> param) throws Exception {
		return 0;
	}
	
	/**
	 * @see com.xteam.util.db.serv.IDBServer#queryObject(java.lang.Class, java.lang.String)
	 */
	public <T> T queryObject(Class<T> type, String id) throws Exception {
		return null;
	}
	
	/**
	 * @see com.xteam.util.db.serv.IDBServer#deleteObject(java.lang.Class, java.lang.String)
	 */
	public int deleteObject(Class<?> type, String id) throws Exception {
		return 0;
	}
	
	/**
	 * @see com.xteam.util.db.serv.IDBServer#query(java.lang.Class, java.lang.Integer, java.lang.Integer)
	 */
	public <T> List<T> query(Class<T> type, Integer index, Integer max) throws Exception {
		return null;
	}
}
