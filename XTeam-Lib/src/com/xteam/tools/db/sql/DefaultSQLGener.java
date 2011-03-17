/*
 * Copyright (c) 2005-2008 旭鸣软件
 * All rights reserved. 
 */
/*
 * File：SQLGener.java
 * History:
 *       2009-5-4: Initially created, 汤垲峰.
 */
package com.xteam.tools.db.sql;

/**
 * <p>
 * SQL 查询条件生成器
 * </p>
 * @author 汤垲峰
 */
public class DefaultSQLGener implements ISQLGener {
	
	StringBuffer sql	= new StringBuffer("");
	
	boolean isAddKey	= true;

	public static void main(String[] args) {

	}
	
	/**
	 * 增加一个条件字段 无任何逻辑符号
	 * @see com.toms.commons.dbserver.sql.ISQLGener#addField(java.lang.String)
	 */
	public void addField(String name){
		if(!isAddKey) sql.append(SQLAND);	//如果没有添加逻辑运算符 就默认添加一个AND操作符
		sql.append(name);
	}
	
	/**
	 * <P>
	 * 增加
	 * </P>
	 * @author 汤垲峰 2009-5-4
	 * @param name
	 * @param value
	 */
	public void addFieldEQU(String name,String value){
		if(!isAddKey) sql.append(SQLAND);	//如果没有添加逻辑运算符 就默认添加一个AND操作符
		sql.append(name).append(SQLEQU).append(value);
		isAddKey	= false;
	}

	/**
	 * 增加一个条件字段（= 操作）
	 * @see com.toms.commons.dbserver.sql.ISQLGener#addFieldName(java.lang.String)
	 */
	public void addFieldEQU(String name) {
		if(!isAddKey) sql.append(SQLAND);	//如果没有添加逻辑运算符 就默认添加一个AND操作符
		sql.append(name).append(SQLEQU).append(SQLQUE);
		isAddKey	= false;
	}
	
	/**
	 * <P>
	 * 增加一个条件字段（IN 操作）
	 * </P>
	 * @author 汤垲峰 2009-5-4
	 * @param name
	 */
	public void addFieldIN(String name){
		if(!isAddKey) sql.append(SQLAND);	//如果没有添加逻辑运算符 就默认添加一个AND操作符
		sql.append(name).append(SQLEQU).append(SQLLK+SQLQUE+SQLRK);
		isAddKey	= false;
	}
	public void addFieldIN(String name,String value){
		if(!isAddKey) sql.append(SQLAND);	//如果没有添加逻辑运算符 就默认添加一个AND操作符
		sql.append(name).append(SQLEQU).append(SQLLK+value+SQLRK);
		isAddKey	= false;
	}
	
	/**
	 * <P>
	 * 增加一个条件字段（LIKE 操作）
	 * </P>
	 * @author 汤垲峰 2009-5-4
	 * @param name
	 */
	public void addFieldLIKE(String name){
		if(!isAddKey) sql.append(SQLAND);	//如果没有添加逻辑运算符 就默认添加一个AND操作符
		sql.append(name).append(SQLLIKE).append(SQLQUE);
		isAddKey	= false;
	}
	public void addFieldLIKE(String name,String value){
		if(!isAddKey) sql.append(SQLAND);	//如果没有添加逻辑运算符 就默认添加一个AND操作符
		sql.append(name).append(SQLLIKE).append(value);
		isAddKey	= false;
	}

	/**
	 * <P>
	 * 添加与操作符
	 * </P>
	 * @author 汤垲峰 2009-5-4
	 */
	public void addAnd(){
		isAddKey	= true;
		sql.append(SQLAND);
	}
	
	/**
	 * <P>
	 * 添加或操作符
	 * </P>
	 * @author 汤垲峰 2009-5-4
	 */
	public void addOr(){
		isAddKey	= true;
		sql.append(SQLOR);
	}
	
	/**
	 * <P>
	 * 添加左括号
	 * </P>
	 * @author 汤垲峰 2009-5-4
	 */
	public void addLeftK(){
		sql.append(SQLLK);
	}
	
	/**
	 * <P>
	 * 添加右括号
	 * </P>
	 * @author 汤垲峰 2009-5-4
	 */
	public void addRightK(){
		sql.append(SQLRK);
	}
	
	
	
	public void clean(){
		sql	= new StringBuffer("");
	}
	
	public String toString(){
		return getSQL();
	}
	
	public String getSQL(){
		return sql.toString();
	}
}
