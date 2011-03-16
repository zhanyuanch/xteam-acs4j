/*
 * 创建时间：2011-3-16 上午10:25:16
 * 工程名称：XTeam-acs4j
 * 文   件  名：com.xteam.asc4j.module.dao.impl.GenericDaoImple.java
 * Author:Leo
 * 
 */
package com.xteam.asc4j.module.dao.impl;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.LinkedHashMap;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.tangkf.utils.CommonUtils;
import com.xteam.asc4j.base.PageInfo;
import com.xteam.asc4j.db.AcsSessions;
import com.xteam.asc4j.module.dao.base.GenericDao;

/**
 * @author Leo
 * 
 */
public class GenericDaoImple<T> implements GenericDao<T> {

	private Class<T> cls;

	public GenericDaoImple() {
		cls = (Class<T>) ((ParameterizedType) this.getClass()
				.getGenericSuperclass()).getActualTypeArguments()[0];
	}

	private Session getSession() {
		return AcsSessions.currentSession();
	}

	public void delete(T entity) {
		Session s = this.getSession();
		try {
			s.delete(entity);
		} finally {
			s.close();
		}
	}

	public void deleteBathByIds(String idName, String[] idValues) {
		String hql = "Delete from " + cls.getName() + " where " + idName
				+ " in " + CommonUtils.spliceCondition(idValues);
		executeHQL(hql, idValues);
	}

	public void executeHQL(String hql) {
		this.executeHQL(hql, null);
	}

	public void executeHQL(String hql, Object[] params) {
		Session ses = this.getDaoSession();
		Transaction tx = ses.beginTransaction();
		try {
			Query q = ses.createQuery(hql);
			if (params != null && params.length != 0) {
				for (int i = 0; i < params.length; i++) {
					q.setParameter(i, params[i]);
				}
			}
			q.executeUpdate();
			tx.commit();
		} catch (RuntimeException e) {
			tx.rollback();
			throw e;
		} finally {
			ses.close();
		}
	}

	public void executeSQL(String sql) {
		this.executeSQL(sql, null);
	}

	public void executeSQL(String sql, Object[] params) {
		Session ses = this.getDaoSession();
		Transaction tx = ses.beginTransaction();
		try {
			SQLQuery q = ses.createSQLQuery(sql);
			if (params != null && params.length != 0) {
				for (int i = 0; i < params.length; i++) {
					q.setParameter(i, params[i]);
				}
			}
			q.executeUpdate();
			tx.commit();
		} catch (RuntimeException e) {
			tx.rollback();
			throw e;
		} finally {
			ses.close();
		}
	}

	@SuppressWarnings("unchecked")
	public List<T> findAll() {
		Session ses = this.getSession();
		List<T> r =null;
		try {
			 r = ses.createCriteria(cls).list();
		} finally {
			ses.close();
		}
		return r;
	}

	public T findById(Serializable id) {
		return null;
	}

	public List<T> findByProperty(String propertyName, Object value) {
		return null;
	}

	public Long getCountBySQLParams(String sql, Object[] params) {
		return null;
	}

	public int getCountOfAll() {
		return 0;
	}

	public Session getDaoSession() {
		return null;
	}

	public List getListByHQL(String hql, Object[] params, int start, int length) {
		return null;
	}

	public List<Object[]> getListBySQL(String sql, Object[] params, int start,
			int length) {
		return null;
	}

	public Long getMaxByProperty(String propertyName, String tableName,
			String wherehql, Object[] params) {
		return null;
	}

	public Long getMinByProperty(String propertyName, String tableName,
			String wherehql, Object[] params) {
		return null;
	}

	public PageInfo<T> getScrollData(int firstindex, int maxresult,
			String wherehql, Object[] queryParams,
			LinkedHashMap<String, String> orderby) {
		return null;
	}

	public void save(T entity) {

	}

	public void update(T e) {

	}
}
