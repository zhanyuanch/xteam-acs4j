/*
 * Copyright (c) 2005-2008 成均科技
 * All rights reserved. 
 */
/*
 * File：MySqlImpl.java
 * History:
 *       2007-12-10: Initially created, tangkf.
 */
package com.xteam.tools.db;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.connection.ConnectionProvider;
import org.hibernate.connection.DriverManagerConnectionProvider;

import com.xteam.tools.CommonUtils;
import com.xteam.tools.db.page.Page;


/**
 * <p>
 * 实现MYSQL数据库服务器的相关初始化
 * </p>
 * 
 * @author tangkf
 */
@SuppressWarnings("unchecked")
public class DefaultDBSImpl implements IHibernate, IDBUtils {

	/**
	 * 配置文件信息
	 */
	protected Map<String,String> properties;
	protected String dbServerName;
	protected List<String> mappingList;

	protected SessionFactory sessionFactory;

	protected ConnectionProvider conncp;

	protected DriverManagerConnectionProvider dmcp;

	/**
	 * 用于保存线程副本的session变量
	 */
	protected ThreadLocal<Session> sessionCache;

	/**
	 * @author Implements by tangkf
	 * @throws Exception
	 * @Date 2008-1-12
	 * @see cn.com.chengjun.dbserver.IDBServer#initialize()
	 */
	public void initialize() throws Exception {
		sessionCache = new ThreadLocal<Session>();

		try {
			// 初始化数据库
			Configuration conf = this.addResources(this.buildConfig());
			this.sessionFactory = conf.buildSessionFactory();
			this.dmcp = new DriverManagerConnectionProvider();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	/**
	 * 将SESSION 放回缓存
	 * 
	 * @author Implements by tangkf
	 * @Date 2008-1-12
	 * @see cn.com.chengjun.dbserver.IHibernate#releaseSession(org.hibernate.Session)
	 */
	public void releaseSession(Session s) {
		if(s!=null){
			s.flush();
			s.clear();
			s.close();
		}
		this.sessionCache.set(null);
		this.sessionCache.remove();
		if(DBConfiguration.isViewLog) System.err.println(DB_CommonUtils.getStDateTime()+" ------> CLOSE SESSION");
	}

	/**
	 * 将配置属性值组装成hibernate的配置对象
	 * 
	 * @author tangkf
	 * @return
	 */
	public Configuration buildConfig() {
		Iterator<Entry<String,String>> pits = this.properties.entrySet().iterator();
		Configuration conf = new Configuration();
		while (pits.hasNext()) {
			Entry<String,String> e = pits.next();
			String name = e.getKey();
			String value = e.getValue();
			conf = conf.setProperty(name, value);
		}
		return conf;
	}

	/*
	 * 增加资源影射关系
	 */
	public Configuration addResources(Configuration conf) {
		for (Iterator<String> it = this.mappingList.iterator(); it.hasNext();) {
			String rsc = it.next();
			conf.addResource(rsc);
		}
		return conf;
	}

	/**
	 * @author Implements by tangkf
	 * @Date 2008-1-12
	 * @see cn.com.chengjun.dbserver.IHibernate#getConnection()
	 */
	public Connection getConnection() {
		Connection conn = null;
		try {
			conn = this.dmcp.getConnection();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return conn;
	}

	/**
	 * @author Implements by tangkf
	 * @Date 2008-1-12
	 * @see cn.com.chengjun.dbserver.IHibernate#releaseConnection(java.sql.Connection)
	 */
	public void releaseConnection(Connection conn) {
		try {
			this.dmcp.closeConnection(conn);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获取一个 hibernate 的session
	 * 
	 * @see cn.com.chengjun.dbserver.IHibernate#getSession()
	 */
	public Session getSession() {
		Session ses = this.sessionCache.get();
		if (ses == null || !ses.isConnected() || !ses.isOpen()) {
			if (this.sessionFactory != null) {
				ses = this.sessionFactory.openSession();
				this.sessionFactory.openSession();
				if(DBConfiguration.isViewLog) System.err.println(DB_CommonUtils.getStDateTime()+" ------> OPEN SESSION");
				this.sessionCache.set(ses);
			}
		}
		return ses;
	}

	/**
	 * 获取一个 session的工厂
	 * 
	 * @see cn.com.chengjun.dbserver.IHibernate#getSessionFactory()
	 */
	public SessionFactory getSessionFactory() {
		return this.sessionFactory;
	}

	/**
	 * 设置配置信息
	 * 
	 * @see cn.com.chengjun.dbserver.IDBServer#setConfigInfo(java.util.Map)
	 */
	public void setConfigInfo(Map<String,String> config) {
		this.properties = config;
	}

	/**
	 * @author Implements by tangkf
	 * @Date 2008-1-12
	 * @see cn.com.chengjun.dbserver.IDBServer#setDBServerName(java.lang.String)
	 */
	public void setDBServerName(String dbname) {
		this.dbServerName = dbname;
	}

	/**
	 * @author Implements by tangkf
	 * @Date 2008-1-12
	 * @see cn.com.chengjun.dbserver.IDBServer#getDBServerName()
	 */
	public String getDBServerName() {
		return this.dbServerName;
	}

	/**
	 * 设置映射文件信息
	 * 
	 * @see cn.com.chengjun.dbserver.IDBServer#setMapping(java.util.List)
	 */
	public void setMapping(List<String> mapping) {
		// ----得到映射文件表---
		this.mappingList = mapping;
		for (int i = 0; i < mapping.size(); i++) {
			if(DBConfiguration.isViewLog) System.err.println(DB_CommonUtils.getStDateTime()+ " mapping ----> " + mapping.get(i));
		}
	}

	/**
	 * @athor tangkf
	 * @date 2008-1-5
	 * @see cn.com.chengjun.dbserver.IDBUtils#executeHQLUpdate(java.lang.String)
	 */
	public int executeHQLUpdate(String hql) {
		int rtn	= 1;
		Session ses = this.getSession();
		Transaction tx = null;
		try {
			tx = ses.beginTransaction();
			Query q = ses.createQuery(hql);
			q.executeUpdate();
			ses.flush();
			tx.commit();
			rtn	= 0;
		} catch (Exception e) {
			e.printStackTrace();
			tx.rollback();
			rtn	= 1;
		} finally {
			this.releaseSession(ses);
		}
		return rtn;
	}
	
	
	/**
	 * 执行一个HQL语句并不立即flush
	 * @see com.toms.commons.dbserver.IDBUtils#executeHQLUpdate2Cache(java.lang.String)
	 */
	public int executeHQLUpdate2Cache(String hql) {
		int rtn	= 1;
		Session ses = this.getSession();
		Transaction tx = null;
		try {
			tx = ses.beginTransaction();
			Query q = ses.createQuery(hql);
			q.executeUpdate();
			tx.commit();
			rtn	= 0;
		} catch (Exception e) {
			e.printStackTrace();
			tx.rollback();
			rtn	= 1;
		} finally {
			this.releaseSession(ses);
		}
		return rtn;
	}
	
	/**
	 * 执行一批HQL语句，执行完成后立即 flush
	 * @author 汤垲峰 2009-12-27
	 * @param hql
	 * @return
	 */
	public int executeBathHQLUpdate(String[] hqls) {
		int rtn	= 1;
		Session ses = this.getSession();
		Transaction tx = null;
		try {
			tx = ses.beginTransaction();
			for(String hql:hqls){
				Query q = ses.createQuery(hql);
				q.executeUpdate();
			}
			ses.flush();
			tx.commit();
			rtn	= 0;
		} catch (Exception e) {
			e.printStackTrace();
			tx.rollback();
			rtn	= 1;
		} finally {
			this.releaseSession(ses);
		}
		return rtn;
	}

	/**
	 * 销毁对象
	 * 
	 * @author tangkf
	 */
	public void destroy() {
		Session ses = this.getSession();
		if (ses != null) {
			ses.close();
		}
		this.sessionCache.remove();
		this.sessionCache.set(null);
		this.sessionFactory.close();
		this.sessionFactory	= null;
	}
	

	// ============================以下是数据库操作接口类的实现=====================
	/**
	 * @athor tangkf
	 * @date 2008-1-4
	 * @see cn.com.chengjun.dbserver.IDBUtils#executeSQLQuery(Class,
	 *      java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	public <T> List<T> executeSQLQuery(Class<T> type, String sql) {
		Session ses = this.getSession();
		List<T> list = null;
		try {
			SQLQuery query = ses.createSQLQuery(sql);
			list = query.list();
			ses.flush();
			ses.clear();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			this.releaseSession(ses);
		}
		return list;
	}

	/**
	 * 执行SQL语句
	 * 
	 * @see com.toms.commons.dbserver.IDBUtils#executeSQLQuery(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	public List<Object[]> executeSQLQuery(String sql, Map<Integer, Object> parms) {
		Session ses = this.getSession();
		List<Object[]> list = null;
		try {
			SQLQuery query = ses.createSQLQuery(sql);
			System.err.println(query.getQueryString());
			if (parms != null) {
				Set<Integer> idxs = parms.keySet();
				for (Integer idx : idxs) {
					query.setParameter(idx, parms.get(idx));
				}
			}
			list = query.list();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			this.releaseSession(ses);
		}
		return list;
	}
	
	/**
	 * 执行指定了条件MAP的SQL语句
	 * @author 汤垲峰 2009-12-27
	 * @param sql
	 * @param parms
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Object[]> executeSQLQuerySIDX(String sql, Map<String, Object> parms) {
		Session ses = this.getSession();
		List<Object[]> list = null;
		try {
			SQLQuery query = ses.createSQLQuery(sql);
			System.err.println(query.getQueryString());
			if (parms != null) {
				Set<String> idxs = parms.keySet();
				for (String idx : idxs) {
					query.setParameter(idx, parms.get(idx));
				}
			}
			list = query.list();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			this.releaseSession(ses);
		}
		return list;
	}

	/**
	 * @athor tangkf
	 * @date 2008-1-4
	 * @see cn.com.chengjun.dbserver.IDBUtils#executeSQLUpdate(java.lang.String)
	 */
	public int executeSQLUpdate(String sql) {
		Session ses = this.getSession();
		Transaction tx = ses.beginTransaction();
		try {
			SQLQuery query = ses.createSQLQuery(sql);
			query.executeUpdate();
			ses.flush();
			tx.commit();
		} catch (Exception e) {
			tx.rollback();
			e.printStackTrace();
			return 1;
		} finally {
			this.releaseSession(ses);
		}
		return 0;
	}
	
	/**
	 * 执行一个SQL并不立即 FLUSH，可能被HIBERNAT 缓存
	 * @athor tangkf
	 * @date 2008-1-4
	 */
	public int executeSQLUpdate2Cache(String sql) {
		Session ses = this.getSession();
		Transaction tx = ses.beginTransaction();
		try {
			SQLQuery query = ses.createSQLQuery(sql);
			query.executeUpdate();
			tx.commit();
		} catch (Exception e) {
			tx.rollback();
			e.printStackTrace();
			return 1;
		} finally {
			this.releaseSession(ses);
		}
		return 0;
	}
	
	/**
	 * 批量执行SQL语句，批量执行完成后立即 flush
	 * @author 汤垲峰 2009-12-27
	 * @param sqls
	 * @return
	 */
	public int executeBathSQLUpdate(String[] sqls) {
		Session ses = this.getSession();
		Transaction tx = ses.beginTransaction();
		try {
			for(String sql:sqls){
				SQLQuery query = ses.createSQLQuery(sql);
				query.executeUpdate();
			}
			ses.flush();
			tx.commit();
		} catch (Exception e) {
			tx.rollback();
			e.printStackTrace();
			return 1;
		} finally {
			this.releaseSession(ses);
		}
		return 0;
	}

	/**
	 * @athor tangkf
	 * @date 2008-1-4
	 * @see cn.com.chengjun.dbserver.IDBUtils#getObject(java.lang.Class)
	 */
	@SuppressWarnings("unchecked")
	public <T> T getObject(Class<T> type, String id) {
		T obj = null;
		Session ses = this.getSession();
		obj = (T)ses.load(type, id);
		this.releaseSession(ses);
		return obj;
	}

	/**
	 * @athor tangkf
	 * @date 2008-1-4
	 * @see cn.com.chengjun.dbserver.IDBUtils#getObject(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	public <T> T getObject(String hql) {
		T obj = null;
		List<T> lst = (List<T>)this.getQueryResult(hql);
		if (lst != null && lst.size() > 0) {
			obj = lst.get(0);
		}
		return obj;
	}

	/**
	 * @athor tangkf
	 * @date 2008-1-4
	 * @see cn.com.chengjun.dbserver.IDBUtils#getQueryResult(java.lang.Class)
	 */
	public <T> List<T> getQueryResult(Class<T> type) {
		List<T> lst = null;
		lst = (List<T>)this.getQueryResult(SQLFROM + type.getName());
		return lst;
	}

	/**
	 * 获取查询结果列表
	 * 
	 * @see cn.com.chengjun.dbserver.IDBUtils#getQueryResult(java.lang.String,
	 *      java.util.Map)
	 */
	public <T> List<T> getQueryResult(String hql, Map<String, Object> parametersMap) {
		Session ses = this.getSession();
		List<T> list = null;
		try {
			Query query = ses.createQuery(hql);
			if (parametersMap != null) {
				Iterator<Entry<String, Object>> it = parametersMap.entrySet().iterator();
				while (it.hasNext()) {
					Entry<String, Object> e = it.next();
					String key = e.getKey();
					Object value = parametersMap.get(key);
					query.setParameter(key, value);
				}
			}
			list = query.list();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			this.releaseSession(ses);
		}
		return list;
	}

	/**
	 * @see com.toms.commons.dbserver.IDBUtils#getQueryResult(java.lang.String)
	 */
	public <T> List<T> getQueryResult(String hql) {
		Session ses = this.getSession();
		List<T> list = null;
		try {
			Query query = ses.createQuery(hql);
			list = query.list();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			this.releaseSession(ses);
		}
		return list;
	}

	// ====================以下是对数据库写操作的方法实现==============
	/**
	 * @author Implements by tangkf
	 * @Date 2008-1-6
	 * @see cn.com.chengjun.dbserver.IDBUtils#deleteBathById(java.lang.Class,
	 *      java.lang.String, java.lang.String[])
	 */
	public <T> int deleteBathById(Class<T> type, String idName, String[] idValues) {
		String ids = DB_CommonUtils.combineStringArray(idValues, "','");
		String hql = IDBUtils.SQLDELETE + IDBUtils.SQLFROM + type.getName()
				+ IDBUtils.SQLWHERE + idName + IDBUtils.SQLIN + "('" + ids
				+ "')";
		// ---删除单个对象---
		return this.executeHQLUpdate(hql);
	}

	/**
	 * 删除一个对象
	 * 
	 * @see cn.com.chengjun.dbserver.IDBUtils#delete(java.lang.Object)
	 */
	public <T> int delete(T object) {
		int rst	= 0;
		Session ses = this.getSession();
		Transaction tx = ses.beginTransaction();
		try {
			// ---删除单个对象---
			if (object != null)
				ses.delete(object);
			ses.flush();
			tx.commit();
			rst	= 0;
		} catch (HibernateException e) {
			rst	= 1;
			System.err.println("操作(增加,删除,修改)数据对象时出错!");
			e.printStackTrace();
			if (tx != null)
				tx.rollback();
		} finally {
			if (ses != null){
				this.releaseSession(ses);
			}
		}
		return rst;
	}

	/**
	 * 删除一个对象列表
	 * 
	 * @see cn.com.chengjun.dbserver.IDBUtils#deleteObjects(java.util.List)
	 */
	public <T> int deleteObjects(List<T> objects) {
		int rst	= 0;
		Session ses = this.getSession();
		Transaction tx = ses.beginTransaction();
		try {
			// ---批量删除---
			List<?> l = (List<?>) objects;
			Iterator<?> it = l.iterator();
			while (it.hasNext()) {
				Object o = it.next();
				if (o != null)
					ses.delete(o);
				ses.flush();
			}
			tx.commit();
		} catch (HibernateException e) {
			rst	= 1;
			System.err.println("操作(增加,删除,修改)数据对象时出错!");
			e.printStackTrace();
			if (tx != null)
				tx.rollback();
		} finally {
			if (ses != null)
				this.releaseSession(ses);
		}
		return rst;
	}

	/**
	 * @author Implements by tangkf
	 * @Date 2008-1-12
	 * @see cn.com.chengjun.dbserver.IDBUtils#saveObject(java.lang.Object)
	 */
	public int saveObject(Object obj) {
		int rst	= 0;
		Session ses = this.getSession();
		Transaction tx = ses.beginTransaction();
		try {
			// ---保存单个对象---
			if (obj != null)
				ses.save(obj);
			ses.flush();
			tx.commit();
		} catch (HibernateException e) {
			rst	= 1;
			System.err.println("操作(增加,删除,修改)数据对象时出错!");
			e.printStackTrace();
			if (tx != null)
				tx.rollback();
		} finally {
			if (ses != null)
				this.releaseSession(ses);
		}
		return rst;
	}

	/**
	 * @author Implements by tangkf
	 * @Date 2008-1-12
	 * @see cn.com.chengjun.dbserver.IDBUtils#saveObjects(java.util.List)
	 */
	public <T> int saveObjects(List<T> objects) {
		int rst	= 0;
		Session ses = this.getSession();
		Transaction tx = ses.beginTransaction();
		try {
			// ---批量保存---
			List<?> l = (List<?>) objects;
			Iterator<?> it = l.iterator();
			while (it.hasNext()) {
				Object o = it.next();
				if (o != null)
					ses.save(o);
				ses.flush();
			}
			tx.commit();
		} catch (HibernateException e) {
			rst	= 1;
			System.err.println("操作(增加,删除,修改)数据对象时出错!");
			e.printStackTrace();
			if (tx != null)
				tx.rollback();
		} finally {
			if (ses != null)
				this.releaseSession(ses);
		}
		return rst;
	}

	/**
	 * @author Implements by tangkf
	 * @Date 2008-1-12
	 * @see cn.com.chengjun.dbserver.IDBUtils#saveOrUpdateObj(java.lang.Object)
	 */
	public int saveOrUpdateObj(Object object) {
		int rst	=0;
		Session ses = this.getSession();
		Transaction tx = ses.beginTransaction();
		try {
			// ---单个更新---
			if (object != null)
				ses.merge(object);
			ses.flush();
			tx.commit();
		} catch (HibernateException e) {
			rst	= 1;
			System.err.println("操作(增加,删除,修改)数据对象时出错!");
			e.printStackTrace();
			if (tx != null)
				tx.rollback();
		} finally {
			if (ses != null)
				this.releaseSession(ses);
		}
		return rst;
	}

	/**
	 * @author Implements by tangkf
	 * @Date 2008-1-12
	 * @see cn.com.chengjun.dbserver.IDBUtils#saveOrUpdateObjs(java.util.List)
	 */
	public int saveOrUpdateObjs(List<?> objects) {
		int rst	= 0;
		Session ses = this.getSession();
		Transaction tx = ses.beginTransaction();
		try {
			// ---批量更新---
			List<?> l = (List<?>) objects;
			Iterator<?> it = l.iterator();
			while (it.hasNext()) {
				Object o = it.next();
				if (o != null) {
					ses.merge(o);
					ses.flush();
				}
			}
			tx.commit();
		} catch (HibernateException e) {
			rst	=1;
			System.err.println("操作(增加,删除,修改)数据对象时出错!");
			e.printStackTrace();
			if (tx != null)
				tx.rollback();
		} finally {
			if (ses != null)
				this.releaseSession(ses);
		}
		return rst;
	}

	/**
	 * @author Implements by tangkf
	 * @Date 2008-1-12
	 * @see cn.com.chengjun.dbserver.IDBUtils#updateObject(java.lang.Object)
	 */
	public <T> int updateObject(T object) {
		int rst	= 0;
		Session ses = this.getSession();
		Transaction tx = ses.beginTransaction();
		try {
			// ---单个更新---
			if (object != null)
				ses.update(object);
			ses.flush();
			tx.commit();
		} catch (HibernateException e) {
			rst	= 1;
			System.err.println("操作(增加,删除,修改)数据对象时出错!");
			e.printStackTrace();
			if (tx != null)
				tx.rollback();
		} finally {
			if (ses != null)
				this.releaseSession(ses);
		}
		return rst;
	}

	/**
	 * @author Implements by tangkf
	 * @Date 2008-1-12
	 * @see cn.com.chengjun.dbserver.IDBUtils#updateObjects(java.util.List)
	 */
	public <T> int updateObjects(List<T> objects) {
		int rst	= 0;
		Session ses = this.getSession();
		Transaction tx = ses.beginTransaction();
		try {
			// ---批量更新---
			List<?> l = (List<?>) objects;
			Iterator<?> it = l.iterator();
			while (it.hasNext()) {
				Object o = it.next();
				if (o != null) {
					ses.update(o);
					ses.flush();
				}
			}
			tx.commit();
		} catch (HibernateException e) {
			rst	= 1;
			System.err.println("操作(增加,删除,修改)数据对象时出错!");
			e.printStackTrace();
			if (tx != null)
				tx.rollback();
		} finally {
			if (ses != null)
				this.releaseSession(ses);
		}
		return rst;
	}

	public <T> List<T> executeSQLQuery(Class<T> type, String sql, int firstResult,
			int maxResult) {
		Session ses = this.getSession();
		List<T> list = null;
		try {
			SQLQuery query = ses.createSQLQuery(sql);
			query.setFirstResult(firstResult);
			query.setMaxResults(maxResult);
			list = query.list();
			ses.flush();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			this.releaseSession(ses);
		}
		return list;
	}

	/**
	 * 分页
	 * @see com.toms.commons.dbserver.IDBUtils#executeSQLQuery(java.lang.String, java.util.Map, int, int)
	 */
	@SuppressWarnings("unchecked")
	public List<Object[]> executeSQLQuery(String sql,
			Map<Integer, Object> parms, int firstResult, int maxResult) {
		Session ses = this.getSession();
		List<Object[]> list = null;
		try {
			SQLQuery query = ses.createSQLQuery(sql);
			System.err.println(query.getQueryString());
			if (parms != null) {
				Set<Integer> idxs = parms.keySet();
				for (Integer idx : idxs) {
					query.setParameter(idx, parms.get(idx));
				}
			}
			query.setFirstResult(firstResult);
			query.setMaxResults(maxResult);
			list = query.list();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			this.releaseSession(ses);
		}
		return list;
	}
	
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
	public List<Object[]> executeSQLQuerySIDX(String sql,
			Map<String, Object> parms, int firstResult, int maxResult) {
		Session ses = this.getSession();
		List<Object[]> list = null;
		//List<Object[]> rets = new ArrayList<Object[]>();
		try {
			SQLQuery query = ses.createSQLQuery(sql);
			System.err.println(query.getQueryString());
			if (parms != null) {
				Set<String> idxs = parms.keySet();
				for (String idx : idxs) {
					query.setParameter(idx, parms.get(idx));
				}
			}
			//rets.add(query.getReturnAliases());
			query.setFirstResult(firstResult);
			query.setMaxResults(maxResult);
			list = query.list();
			//rets.addAll(list);
			// ses.flush();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			this.releaseSession(ses);
		}
		return list;
	}

	/**
	 * 分页
	 * @see com.toms.commons.dbserver.IDBUtils#getQueryResult(java.lang.Class, int, int)
	 */
	public <T> List<T> getQueryResult(Class<T> type, int firstResult, int maxResult) {
		List<T> lst = null;
		lst = (List<T>)this.getQueryResult(IDBUtils.SQLFROM + type.getName(),firstResult,maxResult);
		return lst;
	}

	/**
	 * 分页参数
	 * @see com.toms.commons.dbserver.IDBUtils#getQueryResult(java.lang.String, java.util.Map, int, int)
	 */
	public <T> List<T> getQueryResult(String hql, Map<String,Object> parametersMap,
			int firstResult, int maxResult) {
		Session ses = this.getSession();
		List<T> list = null;
		try {
			Query query = ses.createQuery(hql);
			if (parametersMap != null) {
				Iterator<Entry<String, Object>> it = parametersMap.entrySet().iterator();
				while (it.hasNext()) {
					Entry<String, Object> e = it.next();
					String key = e.getKey();
					Object value = parametersMap.get(key);
					query.setParameter(key, value);
				}
			}
			query.setFirstResult(firstResult);
			query.setMaxResults(maxResult);
			list = query.list();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			this.releaseSession(ses);
		}
		return list;
	}

	/**
	 * 分页参数
	 * @see com.toms.commons.dbserver.IDBUtils#getQueryResult(java.lang.String, int, int)
	 */
	public <T> List<T> getQueryResult(String hql, int firstResult, int maxResult) {
		Session ses = this.getSession();
		List<T> list = null;
		try {
			Query query = ses.createQuery(hql);
			query.setFirstResult(firstResult);
			query.setMaxResults(maxResult);
			list = query.list();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			this.releaseSession(ses);
		}
		return list;
	}
	
	/**
	 * <P>
	 * 返回 指定返回记录数的SQL语句;
	 * </P>
	 * @author 汤垲峰 2009-4-27
	 * @param sql
	 * @return
	 */
	public long getSQLCount(String sql){
		long rtn	= 0;
		List<Object[]> rlst	= executeSQLQuery(sql, null);
		if(CommonUtils.notEmpty(rlst)){
			Object obs	= rlst.get(0);
			if(obs!=null){
				rtn	= Long.parseLong(""+obs);
			}
		}
		return rtn;
	}
	
	/**
	 * <P>
	 * 返回 指定HQL语句执行返回的记录数
	 * </P>
	 * @author 汤垲峰 2009-4-27
	 * @param hql
	 * @return
	 */
	public long executeHQLCount(String hql){
		long rtn	= 0;
		List<?> rlst	= getQueryResult(hql);
		if(CommonUtils.notEmpty(rlst)){
			Object obs	= rlst.get(0);
			if(obs!=null){
				rtn	= Long.parseLong(""+obs);
			}
		}
		return rtn;
	}

	/**
	 * @see com.toms.commons.dbserver.IDBUPage#executeSQLQuery(java.lang.String, java.util.Map, com.toms.commons.dbserver.page.Page)
	 */
	public List<Object[]> executeSQLQuery(String sql,
			Map<Integer, Object> parms, Page page) {
		//设置总的记录数
		if(CommonUtils.notEmpty(page.getRowCountSQL())){
			page.setRowsCount(getSQLCount(page.getRowCountSQL()));
		}else{
			List<Object[]> objs	= executeSQLQuery(sql,parms);
			if(objs!=null){
				page.setRowsCount(objs.size());
			}else{
				page.setRowsCount(0);
			}
		}
		int fr	= page.getFirstRow();	//起始位置
		int mr	= page.getPageSize();	//最大记录
		return executeSQLQuery(sql,parms,fr,mr);
	}
	
	/**
	 * 执行一个带条件MAP的SQL语句
	 * @author 汤垲峰 2009-12-27
	 * @param sql
	 * @param parms
	 * @param page
	 * @return
	 */
	public List<Object[]> executeSQLQuerySIDX(String sql, Map<String, Object> parms, Page page) {
		//设置总的记录数
		if(CommonUtils.notEmpty(page.getRowCountSQL())){
			page.setRowsCount(getSQLCount(page.getRowCountSQL()));
		}else{
			List<Object[]> objs	= executeSQLQuerySIDX(sql,parms);
			if(objs!=null){
				page.setRowsCount(objs.size());
			}else{
				page.setRowsCount(0);
			}
		}
		int fr	= page.getFirstRow();	//起始位置
		int mr	= page.getPageSize();	//最大记录
		return executeSQLQuerySIDX(sql,parms,fr,mr);
	}

	/**
	 * @see com.toms.commons.dbserver.IDBUPage#executeSQLQuery(java.lang.Class, java.lang.String, com.toms.commons.dbserver.page.Page)
	 */
	public <T> List<T> executeSQLQuery(Class<T> type, String sql, Page page) {
		//设置总的记录数
		if(CommonUtils.notEmpty(page.getRowCountSQL())){
			page.setRowsCount(getSQLCount(page.getRowCountSQL()));
		}else{
			List<Object[]> objs	= executeSQLQuerySIDX(sql,null);
			if(objs!=null){
				page.setRowsCount(objs.size());
			}else{
				page.setRowsCount(0);
			}
		}
		int fr	= page.getFirstRow();	//起始位置
		int mr	= page.getPageSize();	//最大记录
		return executeSQLQuery(type,sql,fr,mr);
	}

	/**
	 * 返回分页记录
	 * @see com.toms.commons.dbserver.IDBUPage#getQueryResult(java.lang.Class, com.toms.commons.dbserver.page.Page)
	 */
	public <T> List<T> getQueryResult(Class<T> type, Page page) {
		//设置总的记录数
		if(CommonUtils.notEmpty(page.getRowCountSQL())){
			page.setRowsCount(getSQLCount(page.getRowCountSQL()));
		}if(CommonUtils.notEmpty(page.getRowCountHQL())){
			page.setRowsCount(executeHQLCount(page.getRowCountHQL()));
		}else{
			List<?> rclst	= getQueryResult(type);
			if(rclst!=null){
				page.setRowsCount(rclst.size());
			}else{
				page.setRowsCount(0);
			}
		}
		int fr	= page.getFirstRow();	//起始位置
		int mr	= page.getPageSize();	//最大记录
		return getQueryResult(type,fr,mr);
	}

	/**
	 * 返回分页记录
	 * @see com.toms.commons.dbserver.IDBUPage#getQueryResult(java.lang.String, java.util.Map, com.toms.commons.dbserver.page.Page)
	 */
	public <T> List<T> getQueryResult(String hql, Map<String, Object> parametersMap, Page page) {
		//设置总的记录数
		if(CommonUtils.notEmpty(page.getRowCountSQL())){
			page.setRowsCount(getSQLCount(page.getRowCountSQL()));
		}if(CommonUtils.notEmpty(page.getRowCountHQL())){
			page.setRowsCount(executeHQLCount(page.getRowCountHQL()));
		}else{
			List<?> rclst	= getQueryResult(hql,parametersMap);
			if(rclst!=null){
				page.setRowsCount(rclst.size());
			}else{
				page.setRowsCount(0);
			}
		}
		int fr	= page.getFirstRow();	//起始位置
		int mr	= page.getPageSize();	//最大记录
		return getQueryResult(hql,parametersMap,fr,mr);
	}

	/**
	 * 返回分页记录
	 * @see com.toms.commons.dbserver.IDBUPage#getQueryResult(java.lang.String, com.toms.commons.dbserver.page.Page)
	 */
	public <T> List<T> getQueryResult(String hql, Page page) {
		//设置总的记录数
		if(CommonUtils.notEmpty(page.getRowCountSQL())){
			page.setRowsCount(getSQLCount(page.getRowCountSQL()));
		}if(CommonUtils.notEmpty(page.getRowCountHQL())){
			page.setRowsCount(executeHQLCount(page.getRowCountHQL()));
		}else{
			List<?> rclst	= getQueryResult(hql);
			if(rclst!=null){
				page.setRowsCount(rclst.size());
			}else{
				page.setRowsCount(0);
			}
		}
		int fr	= page.getFirstRow();	//起始位置
		int mr	= page.getPageSize();	//最大记录
		return getQueryResult(hql,fr,mr);
	}

	/**
	 * @see com.toms.commons.dbserver.IDBUtils#executeSQLCount(java.lang.String)
	 */
	public long executeSQLCount(String sql) {
		return this.getSQLCount(sql);
	}

	/**
	 * @see com.toms.commons.dbserver.IDBUtils#executeSQLQuery(java.lang.String)
	 */
	public <T> List<T> executeSQLQuery(String sql) {
		return this.executeSQLQuery(null, sql);
	}

	/**
	 * @see com.toms.commons.dbserver.IDBUtils#saveOrUpdateObject(java.lang.Object)
	 */
	public int saveOrUpdateObject(Object o) {
		return this.saveOrUpdateObj(o);
	}

	/**
	 * @see com.toms.commons.dbserver.IDBUtils#saveOrUpdateObjects(java.util.List)
	 */
	public <T> int saveOrUpdateObjects(List<T> objects) {
		return this.saveOrUpdateObjs(objects);
	}
}
