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
		Transaction tx=null;
		try {
			tx  = ses.beginTransaction();
			Query q = ses.createQuery(hql);
			if (params != null && params.length != 0) {
				for (int i = 0; i < params.length; i++) {
					q.setParameter(i, params[i]);
				}
			}
			q.executeUpdate();
			tx.commit();
		} catch (RuntimeException e) {
			if(null!=tx){
				tx.rollback();
			}
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
		Transaction tx =null;
		try {
			tx = ses.beginTransaction();
			SQLQuery q = ses.createSQLQuery(sql);
			if (params != null && params.length != 0) {
				for (int i = 0; i < params.length; i++) {
					q.setParameter(i, params[i]);
				}
			}
			q.executeUpdate();
			tx.commit();
		} catch (RuntimeException e) {
			if(null!=tx){
				tx.rollback();
			}
			throw e;
		} finally {
			ses.close();
		}
	}

	@SuppressWarnings("unchecked")
	public List<T> findAll() {
		Session ses = this.getSession();
		List<T> r = null;
		try {
			r = ses.createCriteria(cls).list();
		} finally {
			ses.close();
		}
		return r;
	}

	@SuppressWarnings("unchecked")
	public T findById(Serializable id) {
		Session ses = this.getSession();
		T ret = null;
		try {
			ret = (T) ses.load(cls, id);
		} finally {
			ses.close();
		}
		return ret;
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
		return this.getSession();
	}

	public List getListByHQL(String hql, Object[] params, int start, int length) {
		Session ses = this.getSession();
		try {
			Query query = ses.createQuery(hql);
			if (params != null && params.length != 0) {
				for (int i = 0; i < params.length; i++) {
					query.setParameter(i, params[i]);
				}
			}
			if (length != 0) {
				query.setMaxResults(length);
				query.setFirstResult(start);
			}
			List rs = query.list();
			return rs;
		} catch (RuntimeException re) {
			throw re;
		} finally {
			ses.close();
		}
	}

	@SuppressWarnings("unchecked")
	public List<Object[]> getListBySQL(String sql, Object[] params, int start,
			int length) {
		Session ses = this.getSession();
		List<Object[]> list = null;
		try {
			SQLQuery query = ses.createSQLQuery(sql);
			if (params != null && params.length != 0) {
				for (int i = 0; i < params.length; i++) {
					query.setParameter(i, params[i]);
				}
			}
			if (length != 0) {
				query.setMaxResults(length);
				query.setFirstResult(start);
			}
			list = query.list();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			ses.close();
		}
		return list;
	}

	public Long getMaxByProperty(String propertyName, String tableName,
			String wherehql, Object[] params) {
		Session ses = this.getSession();
		String hql = "select max(o." + propertyName + ") from " + tableName
				+ " o where 1=1 ";
		if (!CommonUtils.isEmpty(wherehql)) {
			hql += wherehql;
		}
		Query query = ses.createQuery(hql);
		if (params != null && params.length != 0) {
			for (int i = 0; i < params.length; i++) {
				query.setParameter(i, params[i]);
			}
		}
		try {
			Long max = (Long) query.uniqueResult();
			max = max == null ? 0 : max;
			return max;
		} catch (RuntimeException re) {
			throw re;
		} finally {
			ses.close();
		}
	}

	public Long getMinByProperty(String propertyName, String tableName,
			String wherehql, Object[] params) {
		Session ses = this.getSession();
		String hql = "select min(o." + propertyName + ") from " + tableName
				+ " o where 1=1 ";
		if (!CommonUtils.isEmpty(wherehql)) {
			hql += wherehql;
		}
		Query query = ses.createQuery(hql);
		if (params != null && params.length != 0) {
			for (int i = 0; i < params.length; i++) {
				query.setParameter(i, params[i]);
			}
		}
		try {
			Long max = (Long) query.uniqueResult();
			return max;
		} catch (RuntimeException re) {
			throw re;
		} finally {
			ses.close();
		}
	}

	@SuppressWarnings("unchecked")
	public PageInfo<T> getScrollData(int firstindex, int maxresult,
			String wherehql, Object[] queryParams,
			LinkedHashMap<String, String> orderby) {
		Session ses = this.getSession();
		PageInfo<T> queryResult = new PageInfo<T>();
		try {
			String hql = "select o from "
					+ cls.getName()
					+ " o "
					+ (wherehql == null || "".equals(wherehql.trim()) ? ""
							: "where " + wherehql) + buildOrderby(orderby);
			Query query = ses.createQuery(hql);
			setQueryParams(query, queryParams);
			queryResult.setTotalCount(query.list().size());
			if (firstindex != -1 && maxresult != 0) {
				query.setFirstResult(firstindex).setMaxResults(maxresult);
			}
			queryResult.setDataList(query.list());
		} finally {
			ses.close();
		}
		return queryResult;
	}

	private void setQueryParams(Query query, Object[] queryParams) {
		if (queryParams != null && queryParams.length > 0) {
			for (int i = 0; i < queryParams.length; i++) {
				query.setParameter(i, queryParams[i]);
			}
		}
	}

	private String buildOrderby(LinkedHashMap<String, String> orderby) {
		StringBuffer orderbyhql = new StringBuffer("");
		if (orderby != null && orderby.size() > 0) {
			orderbyhql.append(" order by ");
			for (String key : orderby.keySet()) {
				orderbyhql.append("o.").append(key).append(" ").append(
						orderby.get(key)).append(",");
			}
			orderbyhql.deleteCharAt(orderbyhql.length() - 1);
		}
		return orderbyhql.toString();
	}

	public void save(T entity) {
		Session ses = this.getSession();
		try{
			ses.save(entity);
		}finally{
			ses.close();
		}
	}

	public void update(T e) {
		Session ses = this.getSession();
		try{
			ses.merge(e);
		}finally{
			ses.close();
		}
	}
}
