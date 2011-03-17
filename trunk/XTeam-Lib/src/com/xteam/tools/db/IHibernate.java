/*
 * Copyright (c) 2005-2008 成均科技
 * All rights reserved. 
 */
/*
 * File：IHibernate.java
 * History:
 *       2007-11-26: Initially created, tangkf.
 */
package com.xteam.tools.db;

import java.sql.Connection;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

/**
 * 用于描述一个Hibernate管理器所需要实现的接口规范,他继承了IJDBC规范
 * @author tangkf
 *
 */
public interface IHibernate extends IDBServer {
	/**
	 * 获取一个hibernate的session对象
	 * @author tangkf
	 * @return
	 */
	public Session getSession();
	
	/**
	 * 获取一个hibernate的session对象
	 * @author tangkf
	 * @return
	 */
	public SessionFactory getSessionFactory();
	
	/**
	 * 释放SESSION
	 * @author tangkf
	 * @param ses
	 */
	public void releaseSession(Session ses);
	
	/**
	 * 获取一个数据库连接
	 * @return
	 */
	public Connection getConnection();
	
	/**
	 * 释放一个数据库连接
	 * @param conn
	 */
	public void releaseConnection(Connection conn);
}
