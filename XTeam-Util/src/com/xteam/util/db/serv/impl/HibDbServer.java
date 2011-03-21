/**
 * Copyright (c) 2011-2012 XTEAM
 * All rights reserved.
 */
/**
 * File：HibDbServer.java
 * History:
 *         2011-3-21: Initially created, 袁孝均.
 */
package com.xteam.util.db.serv.impl;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.transform.Transformers;

import com.xteam.util.db.serv.AbsDbServer;

/**
 * Hibernate数据库服务。
 * @author 袁孝均
 */
@SuppressWarnings("unchecked")
public class HibDbServer extends AbsDbServer {
	private SessionFactory sessionFactory;
	private ThreadLocal<Session> sessionCache;
	
	/**
	 * @see com.xteam.util.db.serv.IDBServer#initialize()
	 */
	public void initialize() throws Exception {
		Configuration config = new Configuration();
		this.buildConfig(config);
		this.addResource(config);
		this.sessionFactory = config.buildSessionFactory();
		this.sessionCache = new ThreadLocal<Session>();
	}
	
	/**
	 * @see com.xteam.util.db.serv.IDBServer#close()
	 */
	public void close() {
		this.sessionCache = null;
		this.sessionFactory.close();
	}
	
	/**
	 * @see com.xteam.util.db.serv.AbsDbServer#countByHql(java.lang.String, java.util.Map)
	 */
	public long countByHql(String hql, Map<Object, Object> param) throws Exception {
		long count = 0L;
		List<Object> list = this.query(hql, param, null, null);
		if (list != null && !list.isEmpty()) {
			Object o = list.get(0);
			if (o != null) {
				count = Long.valueOf(o.toString());
			}
		}
		return count;
	}
	
	/**
	 * @see com.xteam.util.db.serv.AbsDbServer#countBySql(java.lang.String, java.util.Map)
	 */
	public long countBySql(String sql, Map<Object, Object> param) throws Exception {
		long count = 0L;
		List<Map> list = this.querySql(sql, param, null, null);
		if (list != null && !list.isEmpty()) {
			Iterator it = list.get(0).values().iterator();
			if (it.hasNext()) {
				Object o = it.next();
				count = Long.valueOf(o.toString());
			}
		}
		return count;
	}
	
	/**
	 * @see com.xteam.util.db.serv.AbsDbServer#query(java.lang.String, java.util.Map, java.lang.Integer, java.lang.Integer)
	 */
	public List<Object> query(String hql, Map<Object, Object> param, Integer index, Integer max) throws Exception {
		try {
			Session session = this.openSession();
			Query query = session.createQuery(hql);
			if (param != null && !param.isEmpty()) {
				Iterator<Entry<Object, Object>> pi = param.entrySet().iterator();
				while (pi.hasNext()) {
					Entry<Object, Object> e = pi.next();
					Object key = e.getKey();
					Object value = e.getValue();
					if (Integer.class.equals(key.getClass())) {
					//参数使用位置标识
						query.setString(Integer.valueOf(key.toString()), value.toString());
					} else {
					//参数使用名称标识
						query.setString(key.toString(), value.toString());
					}
				}
			}
			if (index != null && max != null) {
				query.setFirstResult(index);
				query.setMaxResults(max);
			}
			List<Object> list = query.list();
			return list;
		} finally {
			this.closeSession();
		}
	}
	
	/**
	 * @see com.xteam.util.db.serv.AbsDbServer#insert(java.lang.Object)
	 */
	public void insert(Object object) throws Exception {
		if (object == null) {
			throw new NullPointerException();
		}
		Session session = this.openSession();
		Transaction tran = null;
		try {
			tran = session.beginTransaction();
			session.save(object);
			tran.commit();
		} catch (Exception e) {
			if (tran != null) {
				tran.rollback();
			}
			throw e;
		} finally {
			this.closeSession();
		}
	}
	
	/**
	 * @see com.xteam.util.db.serv.AbsDbServer#insertList(java.util.List)
	 */
	public void insertList(List<Object> list) throws Exception {
		if (list == null || list.isEmpty()) {
			throw new NullPointerException();
		}
		Session session = this.openSession();
		Transaction tran = null;
		try {
			tran = session.beginTransaction();
			for (Object object : list) {
				if (object != null) {
					session.save(object);
				}
			}
			tran.commit();
		} catch (Exception e) {
			if (tran != null) {
				tran.rollback();
			}
			throw e;
		} finally {
			this.closeSession();
		}
	}
	
	/**
	 * @see com.xteam.util.db.serv.AbsDbServer#insertOrUpdate(java.lang.Object)
	 */
	public void insertOrUpdate(Object object) throws Exception {
		if (object == null) {
			throw new NullPointerException();
		}
		Session session = this.openSession();
		Transaction tran = null;
		try {
			tran = session.beginTransaction();
			session.saveOrUpdate(object);
			tran.commit();
		} catch (Exception e) {
			if (tran != null) {
				tran.rollback();
			}
			throw e;
		} finally {
			this.closeSession();
		}
	}
	
	/**
	 * @see com.xteam.util.db.serv.AbsDbServer#insertOrUpdateList(java.util.List)
	 */
	public void insertOrUpdateList(List<Object> list) throws Exception {
		if (list == null || list.isEmpty()) {
			throw new NullPointerException();
		}
		Session session = this.openSession();
		Transaction tran = null;
		try {
			tran = session.beginTransaction();
			for (Object object : list) {
				if (object != null) {
					session.saveOrUpdate(object);
				}
			}
			tran.commit();
		} catch (Exception e) {
			if (tran != null) {
				tran.rollback();
			}
			throw e;
		} finally {
			this.closeSession();
		}
	}
	
	/**
	 * @see com.xteam.util.db.serv.AbsDbServer#update(java.lang.Object)
	 */
	public void update(Object object) throws Exception {
		if (object == null) {
			throw new NullPointerException();
		}
		Session session = this.openSession();
		Transaction tran = null;
		try {
			tran = session.beginTransaction();
			session.update(object);
			tran.commit();
		} catch (Exception e) {
			if (tran != null) {
				tran.rollback();
			}
			throw e;
		} finally {
			this.closeSession();
		}
	}
	
	/**
	 * @see com.xteam.util.db.serv.AbsDbServer#updateList(java.util.List)
	 */
	public void updateList(List<Object> list) throws Exception {
		if (list == null || list.isEmpty()) {
			throw new NullPointerException();
		}
		Session session = this.openSession();
		Transaction tran = null;
		try {
			tran = session.beginTransaction();
			for (Object object : list) {
				if (object != null) {
					session.merge(object);
				}
			}
			tran.commit();
		} catch (Exception e) {
			if (tran != null) {
				tran.rollback();
			}
			throw e;
		} finally {
			this.closeSession();
		}
	}
	
	/**
	 * @see com.xteam.util.db.serv.AbsDbServer#delete(java.lang.Object)
	 */
	public void delete(Object object) throws Exception {
		if (object == null) {
			throw new NullPointerException();
		}
		Session session = this.openSession();
		Transaction tran = null;
		try {
			tran = session.beginTransaction();
			session.delete(object);
			tran.commit();
		} catch (Exception e) {
			if (tran != null) {
				tran.rollback();
			}
			throw e;
		} finally {
			this.closeSession();
		}
	}
	
	/**
	 * @see com.xteam.util.db.serv.AbsDbServer#deleteList(java.util.List)
	 */
	public void deleteList(List<Object> list) throws Exception {
		if (list == null || list.isEmpty()) {
			throw new NullPointerException();
		}
		Session session = this.openSession();
		Transaction tran = null;
		try {
			tran = session.beginTransaction();
			for (Object object : list) {
				if (object != null) {
					session.delete(object);
				}
			}
			tran.commit();
		} catch (Exception e) {
			if (tran != null) {
				tran.rollback();
			}
			throw e;
		} finally {
			this.closeSession();
		}
	}
	
	/**
	 * @see com.xteam.util.db.serv.AbsDbServer#executeHql(java.lang.String, java.util.Map)
	 */
	public int executeHql(String hql, Map<Object, Object> param) throws Exception {
		Session session = this.openSession();
		Transaction tran = null;
		try {
			tran = session.beginTransaction();
			Query query = session.createQuery(hql);
			if (param != null && !param.isEmpty()) {
				Iterator<Entry<Object, Object>> pi = param.entrySet().iterator();
				while (pi.hasNext()) {
					Entry<Object, Object> e = pi.next();
					Object key = e.getKey();
					Object value = e.getValue();
					if (Integer.class.equals(key.getClass())) {
					//参数使用位置标识
						query.setString(Integer.valueOf(key.toString()), value.toString());
					} else {
					//参数使用名称标识
						query.setString(key.toString(), value.toString());
					}
				}
			}
			int count = query.executeUpdate();
			tran.commit();
			return count;
		} catch (Exception e) {
			if (tran != null) {
				tran.rollback();
			}
			throw e;
		} finally {
			this.closeSession();
		}
	}
	
	/**
	 * @see com.xteam.util.db.serv.AbsDbServer#querySql(java.lang.String, java.util.Map, java.lang.Integer, java.lang.Integer)
	 */
	public List<Map> querySql(String sql, Map<Object, Object> param, Integer index, Integer max) throws Exception {
		try {
			Session session = this.openSession();
			Query query = session.createSQLQuery(sql);
			if (param != null && !param.isEmpty()) {
				Iterator<Entry<Object, Object>> pi = param.entrySet().iterator();
				while (pi.hasNext()) {
					Entry<Object, Object> e = pi.next();
					Object key = e.getKey();
					Object value = e.getValue();
					if (Integer.class.equals(key.getClass())) {
					//参数使用位置标识
						query.setString(Integer.valueOf(key.toString()), value.toString());
					} else {
					//参数使用名称标识
						query.setString(key.toString(), value.toString());
					}
				}
			}
			if (index != null && max != null) {
				query.setFirstResult(index);
				query.setMaxResults(max);
			}
			query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
			List<Map> list = query.list();
			return list;
		} finally {
			this.closeSession();
		}
	}
	
	/**
	 * @see com.xteam.util.db.serv.AbsDbServer#executeSql(java.lang.String, java.util.Map)
	 */
	public int executeSql(String sql, Map<Object, Object> param) throws Exception {
		Session session = this.openSession();
		Transaction tran = null;
		try {
			tran = session.beginTransaction();
			Query query = session.createSQLQuery(sql);
			if (param != null && !param.isEmpty()) {
				Iterator<Entry<Object, Object>> pi = param.entrySet().iterator();
				while (pi.hasNext()) {
					Entry<Object, Object> e = pi.next();
					Object key = e.getKey();
					Object value = e.getValue();
					if (Integer.class.equals(key.getClass())) {
					//参数使用位置标识
						query.setString(Integer.valueOf(key.toString()), value.toString());
					} else {
					//参数使用名称标识
						query.setString(key.toString(), value.toString());
					}
				}
			}
			int count = query.executeUpdate();
			tran.commit();
			return count;
		} catch (Exception e) {
			if (tran != null) {
				tran.rollback();
			}
			throw e;
		} finally {
			this.closeSession();
		}
	}
	
	/**
	 * @see com.xteam.util.db.serv.AbsDbServer#queryObject(java.lang.Class, java.lang.String)
	 */
	public <T> T queryObject(Class<T> type, String id) throws Exception {
		try {
			Session session = this.openSession();
			T t = (T)session.get(type, id);
			return t;
		} finally {
			this.closeSession();
		}
	}
	
	/**
	 * @see com.xteam.util.db.serv.AbsDbServer#deleteObject(java.lang.Class, java.lang.String)
	 */
	public int deleteObject(Class<?> type, String id) throws Exception {
		Session session = this.openSession();
		Transaction tran = null;
		try {
			String hql = "DELETE FROM " + type.getName() + " WHERE ID = '" + id + "'";
			tran = session.beginTransaction();
			Query query = session.createQuery(hql);
			int count = query.executeUpdate();
			tran.commit();
			return count;
		} catch (Exception e) {
			if (tran != null) {
				tran.rollback();
			}
			throw e;
		} finally {
			this.closeSession();
		}
	}
	
	/**
	 * @see com.xteam.util.db.serv.AbsDbServer#query(java.lang.Class, java.lang.Integer, java.lang.Integer)
	 */
	public <T> List<T> query(Class<T> type, Integer index, Integer max) throws Exception {
		try {
			Session session = this.openSession();
			Criteria c = session.createCriteria(type);
			if (index != null && max != null) {
				c.setFirstResult(index);
				c.setMaxResults(max);
			}
			List<T> list = c.list();
			return list;
		} finally {
			this.closeSession();
		}
	}
	
	/**
	 * 组装数据库配置。
	 * @author 袁孝均 2011-3-21
	 * @param config
	 */
	private void buildConfig(Configuration config) {
		Iterator<Entry<String, String>> cfg = this.dbServProperty.entrySet().iterator();
		while (cfg.hasNext()) {
			Entry<String, String> e = cfg.next();
			config.setProperty(e.getKey(), e.getValue());
		}
	}
	
	/**
	 * 添加数据库资源。
	 * @author 袁孝均 2011-3-21
	 * @param config
	 */
	private void addResource(Configuration config) {
		for (String res : this.dbServMapping) {
			config.addResource(res);
		}
	}
	
	/**
	 * 打开Session。
	 * @author 袁孝均 2011-3-21
	 * @return
	 * @throws Exception
	 */
	private Session openSession() throws Exception {
		Session session = this.sessionCache.get();
		if (session == null || !session.isConnected() || !session.isOpen()) {
			if (this.sessionFactory != null) {
				session = this.sessionFactory.openSession();
				this.sessionCache.set(session);
			}
		}
		return session;
	}
	
	/**
	 * 关闭Session。
	 * @author 袁孝均 2011-3-21
	 * @throws Exception
	 */
	private void closeSession() throws Exception {
		Session session = this.sessionCache.get();
		if (session != null) {
			session.close();
		}
		this.sessionCache.set(null);
	}
}
