/**
 * Copyright (c) 2011-2012 XTEAM
 * All rights reserved.
 */
/**
 * File：DaoFactory.java
 * History:
 *         2011-3-21: Initially created, 袁孝均.
 */
package com.xteam.util.db.factory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import com.toms.config.ConfigBuilder;
import com.toms.config.ConfigNode;
import com.xteam.util.ClazzLoader;
import com.xteam.util.db.dao.IDao;
import com.xteam.util.db.dao.impl.Dao;
import com.xteam.util.db.serv.IDBServer;

/**
 * 数据库访问对象工厂。
 * <BR>
 * 单例模式工厂，可直接使用以创建通用数据库访问对象。
 * <BR>
 * 若实现了特有的数据库访问对象，也可以从该工厂继承实现新的工厂以创建特有的数据库访问对象。
 * @author 袁孝均
 */
public class DaoFactory {
	private static DaoFactory instance = null;
	/**
	 * 获取数据库访问对象工厂实例。
	 * @author 袁孝均 2011-3-21
	 * @return
	 */
	public final static synchronized DaoFactory getInstance() {
		if (instance == null) {
			instance = new DaoFactory();
		}
		return instance;
	}
	
	private String defServName;
	private Map<String, IDBServer> servCache;
	private Map<String, IDao> daoCache;
	
	/**
	 * 数据库访问对象工厂保护构造方法。
	 */
	protected DaoFactory() {
		this.defServName = "";
		this.servCache = new ConcurrentHashMap<String, IDBServer>();
		this.daoCache = new ConcurrentHashMap<String, IDao>();
	}
	
	/**
	 * 初始化数据库访问对象工厂。
	 * @author 袁孝均 2011-3-21
	 * @throws Exception
	 */
	public final void initialize() throws Exception {
		this.initialize("config/db-config.xml");
	}
	
	/**
	 * 初始化数据库访问对象工厂。
	 * @author 袁孝均 2011-3-21
	 * @param cfg
	 * @throws Exception
	 */
	public final void initialize(String cfg) throws Exception {
		ConfigNode root = new ConfigBuilder(cfg).getRoot();
		this.defServName = root.getProperty("default");
		
		List<ConfigNode> servs = root.getChildrenByName("server");
		for (ConfigNode serv : servs) {
			String servname = serv.getProperty("name");
			String classname = serv.getProperty("class");
			
			Map<String, String> property = new HashMap<String, String>();
			List<ConfigNode> pcns = serv.getChildrenByName("property");
			for (ConfigNode pcn : pcns) {
				String key = pcn.getProperty("name");
				String value = pcn.getNodeValue();
				property.put(key, value);
			}
			
			List<String> mapping = new ArrayList<String>();
			List<ConfigNode> mcns = serv.getChildrenByName("mapping");
			for (ConfigNode mcn : mcns) {
				String resource = mcn.getProperty("resource");
				mapping.add(resource);
			}
			
			IDBServer server = (IDBServer)ClazzLoader.loadClassByName(classname);
			server.setDbServName(servname);
			server.setDbServProperty(property);
			server.setDbServMapping(mapping);
			server.initialize();
			
			this.servCache.put(servname, server);
		}
	}
	
	/**
	 * 清理数据库访问对象工厂。
	 * @author 袁孝均 2011-3-21
	 */
	public final void clear() {
		Iterator<Entry<String, IDBServer>> servs = this.servCache.entrySet().iterator();
		while (servs.hasNext()) {
			Entry<String, IDBServer> e = servs.next();
			IDBServer serv = e.getValue();
			serv.close();
		}
		this.defServName = "";
		this.servCache.clear();
		this.daoCache.clear();
	}
	
	/**
	 * 创建一个通用数据库访问对象，它使用默认的数据库服务。
	 * @author 袁孝均 2011-3-21
	 * @return
	 * @throws Exception
	 */
	public final IDao createDao() throws Exception {
		return this.createDao(Dao.class, this.defServName);
	}
	
	/**
	 * 创建一个通用数据库访问对象，它使用指定服务名的数据库服务。
	 * @author 袁孝均 2011-3-21
	 * @param servname
	 * @return
	 * @throws Exception
	 */
	public final IDao createDao(String servname) throws Exception {
		return this.createDao(Dao.class, servname);
	}
	
	/**
	 * 创建一个指定类型的数据库访问对象，它使用指定服务名的数据库服务。
	 * @author 袁孝均 2011-3-21
	 * @param type
	 * @param servname
	 * @return
	 * @throws Exception
	 */
	public final IDao createDao(Class<?> type, String servname) throws Exception {
		String name = type.getName();
		IDao dao = this.daoCache.get(name);
		if (dao == null) {
			IDBServer server = this.servCache.get(servname);
			if (server == null) {
				throw new NullPointerException("未找指定的数据库服务。");
			}
			dao = (IDao)ClazzLoader.loadClassByType(type);
			dao.initialize(server);
			this.daoCache.put(name, dao);
		}
		return dao;
	}
	
	/**
	 * 创建一个指定类名的数据库访问对象，它使用指定服务名的数据库服务。
	 * @author 袁孝均 2011-3-21
	 * @param name
	 * @param servname
	 * @return
	 * @throws Exception
	 */
	public final IDao createDao(String name, String servname) throws Exception {
		IDao dao = this.daoCache.get(name);
		if (dao == null) {
			IDBServer server = this.servCache.get(servname);
			if (server == null) {
				throw new NullPointerException("未找指定的数据库服务。");
			}
			dao = (IDao)ClazzLoader.loadClassByName(name);
			dao.initialize(server);
			this.daoCache.put(name, dao);
		}
		return dao;
	}
}
