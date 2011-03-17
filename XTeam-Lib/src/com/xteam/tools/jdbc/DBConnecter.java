/**
 * Copyright (c) 2005-2008 旭鸣软件
 * All rights reserved. 
 */
/**
 * File：DBConnecter.java
 * History:
 *       2010-10-21: Initially created, tangkf.
 */
package com.xteam.tools.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

/**
 * 实现一个简易的
 * JDBC连接，加上一个简易的链接池，用于某些必须而又少用的地方
 * @author tangkf
 */
public class DBConnecter implements ConnGeter{
	
	String url;
	String user;
	String passwd;
	
	List<Connection> connpool	= new java.util.Vector<Connection>();

	public static void main(String[] args) {
		DBConnecter dbc	= new DBConnecter();
		dbc.initialize("oracle.jdbc.OracleDriver", "jdbc:oracle:thin:@218.201.218.174:1521/cxtdb", "cxter", "123#@!");
		Connection conn	= dbc.getConnection();
		System.err.println(dbc.getPoolSize());
		Connection conn1	= dbc.getConnection();
		dbc.close(conn);
		dbc.close(conn1);
		System.err.println(dbc.getPoolSize());
		conn	= dbc.getConnection();
		System.err.println(dbc.getPoolSize());
		dbc.close(conn);
		System.err.println(dbc.getPoolSize());
		
	}
	
	public void initialize(String driver,String url,String user,String passwd){
		try {
			Class.forName(driver);
			this.user	= user;
			this.url	= url;
			this.passwd	= passwd;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 返回一个新的数据库连接
	 * @see com.tangkf.jdbc.ConnGeter#getConnection()
	 */
	public Connection getConnection(){
		Connection rconn	= this.popConnection();
		if(rconn==null){
			try {
				System.err.println("---->创建新连接!");
				rconn	= DriverManager.getConnection(url,user,passwd);
			} catch (SQLException e) {
				System.err.println("数据库连接获取出错！");
				e.printStackTrace();
				rconn	= null;
			}
		}
		return rconn;
	}
	
	/**
	 * 从池中返回一个连接
	 * @author tangkf
	 * @return
	 */
	public Connection popConnection(){
		synchronized (connpool) {
			if(connpool.size()>0){
				Connection conn= connpool.remove(0);
				try {
					if(conn.isValid(15)){
						return conn;
					}else{
						System.err.println("=======链接失效=======");
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
				return null;
			}else {
				return null;
			}
		}
	}
	
	public int getPoolSize(){
		return this.connpool.size();
	}
	
	/**
	 * 放回池中
	 * @author tangkf
	 * @param conn
	 */
	public void close(Connection conn){
		synchronized (connpool) {
			connpool.add(conn);
		}
	}
}