/*
 * Copyright (c) 2005-2008 成均科技
 * All rights reserved. 
 *
 * File：SetupDBSImpl.java
 * History:
 *       2008-1-4: Initially created, tangkf.
 */
package com.xteam.tools.db;

import java.util.Iterator;
import java.util.Map.Entry;

import org.hibernate.Session;
import org.hibernate.cfg.Configuration;

/**
 * <p>
 * 专门针对安装服务时使用的实现类
 * </p>
 * @author tangkf
 */
public class SetupDBSImpl extends DefaultDBSImpl {
	
	protected String dbName		= "";
	
	protected String URL_TARG	= "hibernate.connection.url";
	protected String URL_DB		= "\\$\\{ACTIVE_DBNAME\\}";
	
	/**
	 * @return 属性dbName的值.
	 */
	public String getDbName() {
		return this.dbName;
	}

	/**
	 * @param dbName 属性dbName.
	 */
	public void setDbName(String dbName) {
		this.dbName = dbName;
	}

	/**
	 * 让管理类在初始化时并不buildsession
	 * @athor tangkf
	 * @date  2008-1-4
	 * @see cn.com.chengjun.dbserver.DefaultDBSImpl#initialize()
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void initialize() {
		sessionCache		= new ThreadLocal<Session>();
	}
	
	/**
	 * <p>
	 * 初始化session
	 * </p>
	 * @author tangkf
	 */
	@SuppressWarnings("unchecked")
	public void buildSession(){
		Configuration conf	= this.addResources(this.buildConfig());
		this.sessionFactory	= conf.buildSessionFactory();
	}

	/**
	 * @athor tangkf
	 * @date  2008-1-4
	 * @see cn.com.chengjun.dbserver.DefaultDBSImpl#buildConfig()
	 */
	@Override
	public Configuration buildConfig() {
		Iterator<?> pits	= super.properties.entrySet().iterator();
		Configuration conf = new Configuration();
		while(pits.hasNext()){
			Entry<?,?> e	= (Entry<?,?>)pits.next();
			String name	= (String)e.getKey();
			String value= (String)e.getValue();
			if(this.URL_TARG.equalsIgnoreCase(name)){
				value	= value.replaceAll(this.URL_DB, this.dbName);
			}
			conf	= conf.setProperty(name, value);
		}
		return conf;
	}
	
	/**
	 * <p>
	 * 关闭SESSION 工厂
	 * </p>
	 * @author tangkf
	 */
	public void close(){
		this.destroy();
	}
}
