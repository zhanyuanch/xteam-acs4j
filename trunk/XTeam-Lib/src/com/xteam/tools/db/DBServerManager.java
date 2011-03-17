/*
 * Copyright (c) 2005-2008 成均科技
 * All rights reserved. 
 */
/*
 * File：DBServerFactory.java
 * History:
 *       2007-11-26: Initially created, tangkf.
 */
package com.xteam.tools.db;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;


/**
 * 数据库服务器对象管理器
 * @author tangkf
 */
public class DBServerManager {
	
	/**
	 * 缓存数据库服务器
	 */
	private Map<String,IDBServer> SERVER_CACHE;
	
	private IDBServer defaultDBServer;
	
	private IConfiguration dbConfig;
	
	private static DBServerManager instance;
	
	/**
	 * 采用默认配置文件配置数据库服务管理器
	 * @author tangkf
	 * @throws Exception 
	 */
	public void configuration() throws Exception{
		configuration("");
	}
	
	/**
	 * 采用指定路径的配置文件配置数据库服务管理器
	 * @author tangkf
	 * @param configPath
	 * @throws Exception 
	 */
	public void configuration(String configPath) throws Exception{
		IConfiguration dbc	= new DBConfiguration();
		if(configPath==null || "".equals(configPath)){
			dbc.initialize();
		}else{
			dbc.initialize(configPath);
		}
		dbc.config();
		this.configuration(dbc);
	}
	
	/**
	 * 采用指定的配置对象来配置数据库服务管理器
	 * @author tangkf
	 * @param dbc
	 * @throws Exception 
	 */
	public void configuration(IConfiguration dbc) throws Exception{
		dbConfig	= dbc;
		initializeDBServer();
	}
	
	/**
	 * 获取 数据库服务管理器实例
	 * @author tangkf
	 * @return
	 */
	public static DBServerManager getInstance(){
		if(instance==null){
			instance	= new DBServerManager();
		}
		return instance;
	}
	
	/**
	 * 构造时初始化数据库服务器
	 */
	private DBServerManager(){
		this.SERVER_CACHE	= new HashMap<String, IDBServer>();
	}
	
	/**
	 * <P>
	 * 销毁一个数据服务器
	 * </P>
	 * @author 汤垲峰 2009-3-21
	 * @param dbname
	 */
	public void destory(String dbname){
		synchronized (SERVER_CACHE) {
			IDBServer dbs	= this.SERVER_CACHE.remove(dbname);
			if(dbs!=null) dbs.destroy();
		}
	}
	
	
	/**
	 * <P>
	 * 重新初始化数据库服务器
	 * </P>
	 * @author 汤垲峰 2009-3-21
	 * @param dbname
	 * @throws Exception
	 */
	public void initinalize(String dbname) throws Exception{
		destory(dbname);	//首先销毁
		
		Map<String,String> dbinfo	= dbConfig.getServers().get(dbname);
		if(dbinfo!=null){
			List<String> ml	= dbConfig.getMappings().get(dbname);
			
			//----取配置文件中的类路径 与 是否默认服务器----
			String classStr	= dbinfo.get(DBConfiguration.IMPL_CLASS);
			
			//----从配置文件中的类路径产生一个实例----
			IDBServer dbserver	= this.loadDBServer(classStr);
			//----设置名称----
			dbserver.setDBServerName(dbname);
			//----设置映射文件表----
			dbserver.setMapping(ml);
			//----设置配置信息-----
			dbserver.setConfigInfo(dbinfo);
			//----初始化操作----
			dbserver.initialize();
			//----保存到缓存中----
			this.SERVER_CACHE.put(dbname, dbserver);
			
			if(dbserver.getDBServerName().equalsIgnoreCase(dbConfig.getDefaultServer())){
				this.setDefaultDBServer(dbname);
			}
		}
	}
	
	
	/**
	 * 初始化数据库服务器
	 * @author tangkf
	 * @throws Exception 
	 */
	private void initializeDBServer() throws Exception{
		if(dbConfig==null){
			System.err.println("初始化数据库出错,数据库配置文件信息为空!");
		}else{
			Map<String,Map<String,String>> dbs	= dbConfig.getServers();
			Iterator<Entry<String,Map<String,String>>> it	= dbs.entrySet().iterator();
			while(it.hasNext()){
				Entry<String,Map<String,String>> en	= it.next();
				String name		= en.getKey();
				Map<String,String> dbinfo	= en.getValue();
				
				List<String> ml	= (List<String>)dbConfig.getMappings().get(name);
				
				//----取配置文件中的类路径 与 是否默认服务器----
				String classStr	= (String)dbinfo.get(DBConfiguration.IMPL_CLASS);
				
				//----从配置文件中的类路径产生一个实例----
				IDBServer dbserver	= this.loadDBServer(classStr);
				//----设置名称----
				dbserver.setDBServerName(name);
				//----设置映射文件表----
				dbserver.setMapping(ml);
				//----设置配置信息-----
				dbserver.setConfigInfo(dbinfo);
				//----初始化操作----
				dbserver.initialize();
				//----保存到缓存中----
				this.SERVER_CACHE.put(name, dbserver);
				
				if(dbserver.getDBServerName().equalsIgnoreCase(dbConfig.getDefaultServer())){
					this.setDefaultDBServer(name);
				}
			}
		}
	}
	
	
	/**
	 * 将一个类路径采用简单的方式装载成一个class对象
	 * @author tangkf
	 * @param className
	 * @return
	 */
	private IDBServer loadDBServer(String className){
		IDBServer dbserver	= null;
		try {
			dbserver	= (IDBServer)DB_ClassLoader.newObjectByName(className);
		} catch (Exception e) {
			System.err.println("数据库实现类初始化错误,请检查配置文件!["+className+"]");
			e.printStackTrace();
			dbserver	= null;
		}
		return dbserver;
	}
	
	/**
	 * 获取一个指定名字的 数据库服务器
	 * @author tangkf
	 * @param name
	 * @return
	 */
	public IDBServer getDBServer(String name){
		return SERVER_CACHE.get(name);
	}
	
	/**
	 * 设置默认的数据库服务器
	 * @author tangkf
	 * @param name
	 */
	public void setDefaultDBServer(String name){
		defaultDBServer	= getDBServer(name);
	}
	
	
	/**
	 * <P>
	 * 返回全部的数据库服务名数组
	 * </P>
	 * @author 汤垲峰 2009-3-21
	 * @return
	 */
	public String[] getAllDBServerName(){
		String[] ks	= {};
		ks	= SERVER_CACHE.keySet().toArray(ks);
		return ks;
	}
	
	/**
	 * <P>
	 * 获取所有的数据库服务
	 * </P>
	 * @author 汤垲峰 2009-3-21
	 * @return
	 */
	public Map<String,IDBServer> getAllDBServers(){
		return SERVER_CACHE;
	}
	
	/**
	 * 获取默认的数据库服务器
	 * @author tangkf
	 * @return
	 */
	public IDBServer getDefaultDBServer(){
		return defaultDBServer;
	}

	/**
	 * @return 属性dbConfig的值.
	 */
	public IConfiguration getDbConfig() {
		return this.dbConfig;
	}
}
