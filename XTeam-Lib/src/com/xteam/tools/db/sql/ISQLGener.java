/*
 * Copyright (c) 2005-2008 旭鸣软件
 * All rights reserved. 
 */
/*
 * File：ISQLGener.java
 * History:
 *       2009-5-4: Initially created, 汤垲峰.
 */
package com.xteam.tools.db.sql;

/**
 * <p>
 * 查询条件生成接口
 * </p>
 * @author 汤垲峰
 */
public interface ISQLGener {
	public static final String SQLDELETE		= " DELETE ";
	public static final String SQLUPDATE		= " UPDATE ";
	public static final String SQLFROM			= " FROM ";
	public static final String SQLWHERE			= " WHERE ";
	public static final String SQLAND			= " AND ";
	public static final String SQLOR			= " OR ";
	public static final String SQLIN			= " IN ";
	public static final String SQLSELECT		= " SELECT ";
	public static final String SQLNOTIN			= " NOT IN ";
	public static final String SQLORDER_BY		= " ORDER BY ";
	public static final String SQLDESC			= " DESC ";
	
	public static final String SQLLIKE			= " LIKE ";
	
	public static final String SQLPER			= "%";
	public static final String SQLLK			= "(";
	public static final String SQLRK			= ")";
	public static final String SQLQUE			= "?";
	public static final String SQLCOMMA			= ",";
	public static final String SQLEQU			= "=";
	
	
	/**
	 * 增加一个条件字段 无任何逻辑符号
	 * @see com.toms.commons.dbserver.sql.ISQLGener#addField(java.lang.String)
	 */
	public void addField(String name);

	/**
	 * <P>
	 * 增加
	 * </P>
	 * @author 汤垲峰 2009-5-4
	 * @param name
	 * @param value
	 */
	public void addFieldEQU(String name, String value);

	/**
	 * 增加一个条件字段（= 操作）
	 * @see com.toms.commons.dbserver.sql.ISQLGener#addFieldName(java.lang.String)
	 */
	public void addFieldEQU(String name);

	/**
	 * <P>
	 * 增加一个条件字段（IN 操作）
	 * </P>
	 * @author 汤垲峰 2009-5-4
	 * @param name
	 */
	public void addFieldIN(String name);


	public void addFieldIN(String name, String value);


	/**
	 * <P>
	 * 增加一个条件字段（LIKE 操作）
	 * </P>
	 * @author 汤垲峰 2009-5-4
	 * @param name
	 */
	public void addFieldLIKE(String name);

	public void addFieldLIKE(String name, String value);

	/**
	 * <P>
	 * 添加与操作符
	 * </P>
	 * @author 汤垲峰 2009-5-4
	 */
	public void addAnd();

	/**
	 * <P>
	 * 添加或操作符
	 * </P>
	 * @author 汤垲峰 2009-5-4
	 */
	public void addOr();

	/**
	 * <P>
	 * 添加左括号
	 * </P>
	 * @author 汤垲峰 2009-5-4
	 */
	public void addLeftK();

	/**
	 * <P>
	 * 添加右括号
	 * </P>
	 * @author 汤垲峰 2009-5-4
	 */
	public void addRightK();

	public void clean();

	public String getSQL();
}
