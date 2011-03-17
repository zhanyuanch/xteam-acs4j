/*
 * Copyright (c) 2005-2008 成均科技
 * All rights reserved. 
 */
/*
 * File：DBConfiguration.java
 * History:
 *       2007-11-26: Initially created, tangkf.
 */
package com.xteam.tools.db;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.xteam.tools.config4j.cfg.ConfigBuilder;
import com.xteam.tools.config4j.cfg.ConfigNode;

/**
 * 实现配置信息读取
 * @author tangkf
 */
public class DBConfiguration implements IConfiguration{
	//===================配置文件标签名字=================
	public static final String SERVERS		= "servers";
	public static final String DBSERVER		= "dbserver";
	public static final String NAME			= "name";
	public static final String PROPERTY		= "property";
	public static final String MAPPING		= "mapping";
	public static final String RESOURCE		= "resource";
	
	public static final String IMPL_CLASS		= "implclass";
	public static final String DEFAULT		= "default";
	public static final String TRUE			= "true,yes,1,t,y,是,真";
	public static boolean isViewLog			= true;
	//===================配置文件路径信息==================
	private String defaultFilePath				= "config/db-config.xml";
	
	private ConfigBuilder configBuilder		= null;
	private ConfigNode rootNode				= null;
	
	/**
	 * 配置文件对象
	 */
	private File configFile					= null;
	
	/**
	 * 配置文件内容信息
	 */
	private Map<String,Map<String,String>> 	servers;
	
	/**
	 * 针对 HIBERNATE 的映射信息对象
	 */
	private Map<String,List<String>>	mappings;
	
	/**
	 * 默认数据库服务名
	 */
	private String defaultServer		= "";
	
	public static void main(String args[]){
		DBConfiguration dbc	= new DBConfiguration();
		dbc.initialize();
	}
	
	/**
	 * @author tangkf
	 */
	public DBConfiguration(){
		servers	= new HashMap<String,Map<String,String>>();
		mappings= new HashMap<String,List<String>>();
	}
	
	/**
	 * @author Implements by tangkf
	 * @Date 2008-1-12
	 * @see cn.com.chengjun.dbserver.IConfiguration#initialize()
	 */
	public void initialize(){
		this.initialize(defaultFilePath);
	}
	
	/**
	 * @author Implements by tangkf
	 * @Date 2008-1-12
	 * @see cn.com.chengjun.dbserver.IConfiguration#initialize(java.lang.String)
	 */
	public void initialize(String filePath){
		try {
			File file	= new File(filePath);
			URL url	= this.getClass().getResource(filePath);
			if(!file.exists()){
				file	= new File(url.getPath());
			}
			if(!file.exists()){
				throw new Exception("对不起,指定两个路径下不存在配置文件:" +
						"\r\n--->"+url.getPath()+
						"\r\n--->"+file.getPath());
			}
			this.initialize(file);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
	/**
	 * 获取默认的数据库资源名
	 * @see com.toms.commons.dbserver.IConfiguration#getDefaultServer()
	 */
	public String getDefaultServer() {
		return defaultServer;
	}

	/**
	 * @author Implements by tangkf
	 * @Date 2008-1-12
	 * @see cn.com.chengjun.dbserver.IConfiguration#initialize(java.io.File)
	 */
	public void initialize(File file){
		this.configFile	= file;
		try {
			configBuilder	= new ConfigBuilder(this.configFile);
		} catch (Exception e) {
			System.err.println("数据库配置文件初始化出错!");
			e.printStackTrace();
		}
		rootNode		= configBuilder.getRoot();
	}
	
	
	/**
	 * @author Implements by tangkf
	 * @Date 2008-1-12
	 * @see cn.com.chengjun.dbserver.IConfiguration#config()
	 */
	public void config(){
		loadDBServerInfo(this.rootNode);
	}
	
	/**
	 * @author tangkf
	 * @param configNode
	 */
	public void loadDBServerInfo(ConfigNode configNode){
		try {
			//获取默认的数据库服务名
			this.defaultServer	= configNode.getProperty(DEFAULT);
			isViewLog		= "true".equalsIgnoreCase(configNode.getProperty("isViewLog"));
			
			//获取所有 dbserver 列表
			List<ConfigNode> dblist	= configNode.getChildrenByName(DBSERVER);
			for(ConfigNode dbsvr:dblist){
				String dbServerName	= dbsvr.getProperty(NAME);
				//PROPERTY 属性列表
				List<ConfigNode> propList	= dbsvr.getChildrenByName(PROPERTY);
				//MAPPING 属性列表
				List<ConfigNode> mappList	= dbsvr.getChildrenByName(MAPPING);
				
				Map<String,String> prop	= this.loadPropertyInfo(propList);
				List<String> mapping	= this.loadMappingInfo(mappList);
				
				this.servers.put(dbServerName, prop);
				this.mappings.put(dbServerName, mapping);
			}
		} catch (Exception e) {
			System.err.println("数据库配置文件初始化出错!");
			e.printStackTrace();
		}
	}
	
	/**
	 * 装载标准属性文件
	 * @author tangkf
	 * @param properties
	 * @return
	 */
	public Map<String,String> loadPropertyInfo(List<ConfigNode> propList){
		Map<String,String> dbs	= new HashMap<String,String>();
		for(ConfigNode pnode:propList){
			String pname	= pnode.getProperty(NAME);
			String pvalue	= pnode.getNodeValue();
			dbs.put(pname, pvalue);
		}
		return dbs;
	}
	
	/**
	 * 装载mapping影射文件
	 * @author tangkf
	 * @param properties
	 * @return
	 */
	public List<String> loadMappingInfo(List<ConfigNode> mappList){
		List<String> mappinglist= new ArrayList<String>();
		for(ConfigNode mnode:mappList){
			String resource	= mnode.getProperty(RESOURCE);
			mappinglist.add(resource);
		}
		return mappinglist;
	}

	/**
	 * @return 属性servers的值.
	 */
	public Map<String, Map<String, String>> getServers() {
		return this.servers;
	}

	/**
	 * @param servers 属性servers.
	 */
	public void setServers(Map<String, Map<String, String>> servers) {
		this.servers = servers;
	}

	/**
	 * @return 属性mappings的值.
	 */
	public Map<String, List<String>> getMappings() {
		return this.mappings;
	}
	
}
