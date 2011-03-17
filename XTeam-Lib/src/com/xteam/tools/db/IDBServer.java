package com.xteam.tools.db;

import java.util.List;
import java.util.Map;

public interface IDBServer {
	
	/**
	 * 设置数据库服务器名
	 * @author tangkf
	 * @param dbname
	 */
	public void setDBServerName(String dbname);
	
	/**
	 * 设置配置文件信息MAP
	 * @author tangkf
	 * @param config
	 */
	public void setConfigInfo(Map<String,String> config);
	
	/**
	 * 设置影射文件信息
	 * @author tangkf
	 * @param mapping
	 */
	public void setMapping(List<String> mapping);
	
	/**
	 * 获取数据库服务器名
	 * @author tangkf
	 * @return
	 */
	public String getDBServerName();
	
	/**
	 * 初始化系统
	 * @author tangkf
	 * @throws Exception 
	 */
	public void initialize() throws Exception;

	/**
	 * 销毁对象
	 * @author tangkf
	 */
	public void destroy();
}
