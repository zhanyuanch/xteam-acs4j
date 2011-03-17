/*
 * Copyright (c) 2005-2008 成均科技
 * All rights reserved. 
 */
/*
 * File：IJdbc.java
 * History:
 *       2007-11-26: Initially created, tangkf.
 */
package com.xteam.tools.db;

import java.sql.Connection;


/**
 * 用于描述一个JDBC管理器所需要实现的接口规范
 * @author tangkf
 *
 */
public interface IJDBC extends IDBServer{
	
	/**
	 * 
	 * 获取 JDBC 数据库连接
	 * @author tangkf
	 * @return
	 */
	public Connection getConnection();

	/**
	 * 关闭连接
	 * @author tangkf
	 * @param conn
	 */
	public void close(Connection conn);
}
