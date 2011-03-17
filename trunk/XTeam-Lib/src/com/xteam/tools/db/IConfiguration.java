/*
 * Copyright (c) 2005-2008 成均科技
 * All rights reserved. 
 */
/*
 * File：IConfiguration.java
 * History:
 *       2007-12-10: Initially created, tangkf.
 */
package com.xteam.tools.db;

import java.io.File;
import java.util.List;
import java.util.Map;

/**
 * 配置文件数据读写接口
 * @author tangkf
 *
 */
public interface IConfiguration {
	/**
	 * 初始化默认路径下的配置文件信息
	 * @author tangkf
	 */
	public void initialize();
	
	/**
	 * 初始化指定路径下的配置文件信息
	 * @author tangkf
	 * @param filePath
	 */
	public void initialize(String filePath);
	
	/**
	 * 初始化一个指定的文件对象做为配置文件
	 * @author tangkf
	 * @param file
	 */
	public void initialize(File file);
	
	/**
	 * 开始生成配置
	 * @author tangkf
	 */
	public void config();
	
	/**
	 * 获取服务器配置信息
	 * @author tangkf
	 * @return
	 */
	public Map<String, Map<String, String>> getServers();
		
	/**
	 * 设置服务器信息
	 * @author tangkf
	 * @param servers
	 */
	public void setServers(Map<String, Map<String, String>> servers);
	
	/**
	 * <P>
	 * 获取默认的数据库服务器资源名
	 * </P>
	 * @author 汤垲峰 2009-2-6
	 * @return
	 */
	public String getDefaultServer();
	
	/**
	 * 获取资源文件信息
	 * @author tangkf
	 * @return
	 */
	public Map<String, List<String>> getMappings();
}
