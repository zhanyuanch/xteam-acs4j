package com.xteam.tools.jdbc;


import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sun.org.apache.commons.beanutils.BeanUtils;
import com.xteam.tools.CommonUtils;
import com.xteam.tools.jdbc.page.PageBean;
import com.xteam.tools.jdbc.page.PageResultSet;

/**
 * Created by IntelliJ IDEA.
 * User: tanghj
 * Date: 2006-3-29
 * Time: 17:05:50
 * 工具 DAO 数据访问操作类
 */
public class DaoUtils {

	private static ConnGeter connGeter;
	
	public static void setConnGeter(ConnGeter cg){
		connGeter	= cg;
	}

	public static Connection getConnection() throws Exception {
		return connGeter.getConnection();
	}


	/**
	 * 执行一个没有返回结果的SQL语句
	 * @param sqlString SQL语句
     * @throws Exception 抛出所有的错误信息
	 */
	public static void executeUpdate(String sqlString) throws Exception {
		Connection conn = null;
		Statement stmt;
		try {
			conn = getConnection();
			conn.setAutoCommit(false);
			stmt = conn.createStatement();
			stmt.execute(sqlString);
			conn.commit();
			close(stmt);
		} catch (Exception e) {
			e.printStackTrace();
			rollback(conn);
			throw e;
		} finally {
			close(conn);
		}
	}

	/**
	 * 将一个map对象从数据库中删除
	 * @param m map对象
	 * @param table 数据表名
     * @param indexField 关键字段
	 * @throws Exception 抛出所有的错误信息
	 */
	public static void executeDelete(Map m, String table, String indexField) throws Exception {
		String SQL = "DELETE FROM " + table + " WHERE " + indexField + "='"
				+ m.get(indexField) + "'";
		executeUpdate(SQL);
		m.clear();
	}

	/**
	 * 将一个map对象从数据库中删除
	 * @param table 数据表名
	 * @param condition where 后所跟的条件，自定义。不需要带where
	 * @throws Exception 抛出所有的错误信息
	 */
	public static void executeDelete(String table, String condition) throws Exception {
		String SQL = "DELETE FROM " + table + " WHERE " + condition;
		executeUpdate(SQL);
	}

	/**
	 * 将一个object 对象从数据库中删除
	 * @param o 对象
	 * @param table 表名称
	 * @throws Exception 抛出所有的错误信息
     * @noinspection InfiniteRecursion
     * @param indexField 关键字段
	 */
	public static void executeDelete(Object o, String table,String indexField,String[] extKeys) throws Exception {
		Map m = objectToMap(o,extKeys);//BeanUtils.describe(o);
		executeDelete(m, table,indexField);
	}

	/**
	 * 删除数据库中一批对象
	 * @param ids 对象关键字数组
	 * @param table 表名
     * @param indexField 关键字段
	 * @throws Exception 抛出所有的错误信息
	 */
	public static void executeDelSome(String[] ids, String table,String indexField) throws Exception {
		String idstr = CommonUtils.combineStringArray(ids, "','");
		String SQL = "DELETE FROM " + table + " WHERE " + indexField + " IN ('"
				+ idstr + "')";
		executeUpdate(SQL);
	}

	/**
	 * 将一个map对象 插入到数据库中
	 * @param m map对象
	 * @param table 表名
     * @param indexField 关键字段
	 * @throws Exception 抛出所有的错误信息
	 */
	public static void executeInsert(Map m, String table,String indexField) throws Exception {
		m.remove("class");
		String fields = CommonUtils.combineStringArray(m.keySet().toArray(), ",");
		String values = CommonUtils
				.combineStringArray(m.values().toArray(), "','");
		String SQL = "INSERT INTO " + table + "(" + fields + ") VALUES('"
				+ values + "')";
		//System.out.println(SQL);
		executeUpdate(SQL);
		m.clear();
	}

	/**
	 * 将一个object对象插入到数据库中
	 * @param o object 对象
	 * @param table 表名
	 * @throws Exception 抛出所有的错误信息
     * @param indexField 关键字段
	 */
	public static void executeInsert(Object o, String table,String indexField,String[] extKeys) throws Exception {
		Map m =  objectToMap(o,extKeys);//
		executeInsert(m, table,indexField);
	}

	/**
	 * 将一个map对象更新到数据库中
	 * @param m map对象
	 * @param table 表名
     * @param indexField 关键字段
     * @throws Exception 抛出所有的错误信息
	 */
	@SuppressWarnings("unchecked")
	public static void executeUpdateForIndex(Map m, String table, String indexField) throws Exception {
		m.remove("class");
		Object[] fields = m.keySet().toArray();
		StringBuffer SQL = new StringBuffer("UPDATE ");
		SQL.append(table).append(" SET ");
		List fvs = new ArrayList();
		String where = " WHERE ";
		for (int i = 0; i < fields.length; ++i) {
			if (!indexField.equalsIgnoreCase(fields[i].toString())) {
				fvs.add(fields[i] + "='" + m.get(fields[i]) + "'");
			} else {
				where += fields[i] + "='" + m.get(fields[i]) + "'";
			}
		}
		String fvss = CommonUtils.combineStringArray(fvs, ",");
		SQL.append(fvss).append(where);
		executeUpdate(SQL.toString());
		m.clear();
	}

	/**
	 * 将一个object更新到数据库中
	 * @param o 对象
	 * @param table 表名
	 * @throws Exception 抛出所有的错误信息
     * @param indexField keyField
	 */
	public static void executeUpdateForIndex(Object o, String table,String indexField,String[] extKeys) throws Exception {
		Map m =  objectToMap(o,extKeys);//
		executeUpdateForIndex(m, table,indexField);
	}

	/**
	 * 将一个map对象更新到数据库中
	 * @param m map对象
	 * @param table 表名
	 * @param condition 条件 WHERE 语句以后的部分，不包含where语句
	 * @throws Exception 抛出所有的错误信息
	 */
	@SuppressWarnings("unchecked")
	public static void executeUpdateForCondition(Map m, String table, String condition)
			throws Exception {
		m.remove("class");
		Object[] fields = m.keySet().toArray();
		StringBuffer SQL = new StringBuffer("UPDATE ");
		SQL.append(table).append(" SET ");
		List fvs = new ArrayList();
		String where = " WHERE ";
		for (int i = 0; i < fields.length; ++i) {
			fvs.add(fields[i] + "='" + m.get(fields[i]) + "'");
		}
		String fvss = CommonUtils.combineStringArray(fvs, ",");
		SQL.append(fvss).append(where).append(condition);
		executeUpdate(SQL.toString());
		m.clear();
	}

	/**
	 * 将一个object更新到数据库中
	 * @param o 对象
	 * @param table 表名
	 * @param condition 条件 WHERE 语句以后的部分，不包含where语句
	 * @throws Exception 抛出所有的错误信息
	 */
	public static void executeUpdateForCondition(Object o, String table, String condition, String[] extKeys)
			throws Exception {
		Map m =  objectToMap(o,extKeys);//
		executeUpdateForCondition(m, table, condition);
	}
	
	/**
	 * 从数据库中搜索一个对象到给定的 type 实体对象中
	 * @param sql 一个SQL语句
     * @param type 类型
     * @throws Exception  抛出所有的错误信息
     * @return Object 对象
	 */
	public static Object queryObject(String sql, Class type) throws Exception {
		Object o	= null;
		Connection conn = null;
		PreparedStatement pstmt;
		ResultSet rs;
		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				o = type.newInstance();
				BeanUtils.populate(o, rsToMap(rs)); //采用 commons 中的方法复制内容
			}
			close(rs);
			close(pstmt);
		} catch (Exception e) {
			e.printStackTrace();
			rollback(conn);
			throw e;
		} finally {
			close(conn);
		}
		return o;
	}


	/**
	 * 读取内容如果是取一个对象，那么返回的list长度为1 ，未分页
	 * @param sql 语句
     * @param type 类型
	 * @return list
     * @throws Exception 抛出所有错误
	 */
	@SuppressWarnings("unchecked")
	public static List executeQuery(String sql, Class type) throws Exception {
		List list = new ArrayList();
		Connection conn = null;
		PreparedStatement pstmt;
		ResultSet rs;
		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				Object o = type.newInstance();
				BeanUtils.populate(o, rsToMap(rs)); //采用 commons 中的方法复制内容
				list.add(o);
			}
			close(rs);
			close(pstmt);
		} catch (Exception e) {
			e.printStackTrace();
			rollback(conn);
			throw e;
		} finally {
			close(conn);
		}
		return list;
	}
	/**
	 * 读取内容如果是取一个对象，那么返回的list长度为1 ，未分页
	 * @param table 表名称
     * @param type 类型
	 * @return list
     * @throws Exception 抛出所有错误
	 */
	public static List executeQueryForTable(String table, Class type) throws Exception {
		return executeQuery("SELECT * FROM "+table,type);
	}
	
	@SuppressWarnings("unchecked")
	public static List executeQueryToMap(String sql) throws Exception {
		List list = new ArrayList();
		Connection conn = null;
		PreparedStatement pstmt;
		ResultSet rs;
		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				Map m	= rsToMap(rs); 
				list.add(m);
			}
			close(rs);
			close(pstmt);
		} catch (Exception e) {
			e.printStackTrace();
			rollback(conn);
			throw e;
		} finally {
			close(conn);
		}
		return list;
	}
	
	/**
	 * 读取指定分页的内容
	 * @param sql		查询语句
	 * @param page 页对象
     * @throws Exception 抛出所有错误
     * @return list  返回列表
	 */
	@SuppressWarnings("unchecked")
	public static List executePageQueryToMap(String sql, PageBean page) throws Exception {
		List list = new ArrayList();
		Connection conn = null;
		PreparedStatement pstmt;
		PageResultSet prs;
		ResultSet rs;
		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(sql,
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			rs = pstmt.executeQuery();
			prs = new PageResultSet(rs);
			prs.setPageSize(page.getPageSize()); 				//每页N个记录
			prs.gotoPage(page.getCurtPage()); 					//跳转到第N页

            page.setPageCount(prs.getPageCount());      		//设置总页数
            page.setRowCount(prs.getRowsCount()+1);       		//设置总记录数

            for (int i = 0; i < prs.getPageRowsCount(); ++i) { //循环处理
				Map m	= rsToMap(prs.getResultSet());
				list.add(m);
				prs.getResultSet().next();
			}
			close(rs);
			close(pstmt);
		} catch (Exception e) {
			e.printStackTrace();
			rollback(conn);
			throw e;
		} finally {
			close(conn);
		}
		return list;
	}
	/**
	 * 读取指定分页的内容
	 *
	 * @param sql		查询语句
     * @param type 类型
	 * @param page 页对象
     * @throws Exception 抛出所有错误
     * @return list  返回列表
	 */
	@SuppressWarnings("unchecked")
	public static List executePageQuery(String sql, Class type, PageBean page)
			throws Exception {
		List list = new ArrayList();
		Connection conn = null;
		PreparedStatement pstmt;
		PageResultSet prs;
		ResultSet rs;
		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(sql,
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			rs = pstmt.executeQuery();
			prs = new PageResultSet(rs);
			prs.setPageSize(page.getPageSize()); 		//每页N个记录
			prs.gotoPage(page.getCurtPage()); 			//跳转到第N页

            page.setPageCount(prs.getPageCount());      //设置总页数
            page.setRowCount(prs.getRowsCount()+1);     //设置总记录数

            for (int i = 0; i < prs.getPageRowsCount(); ++i) { //循环处理
				Object o = type.newInstance();
				//ClassLoader.getSystemClassLoader().loadClass(c).newInstance();
				//Object o    = Class.forName(c).newInstance();

				BeanUtils.populate(o, rsToMap(prs.getResultSet())); //复制内容

				//BeanHandler bh=new BeanHandler(c);
				//Object co	= bh.handle(prs.getResultSet());

				//o	= BeanUtils.cloneBean(co);

				//BeanListHandler bh = new BeanListHandler(type);
				//aList = (ArrayList)bh.handle(rs);
				list.add(o);
				prs.getResultSet().next();
			}
			close(rs);
			close(pstmt);
		} catch (Exception e) {
			e.printStackTrace();
			rollback(conn);
			throw e;
		} finally {
			close(conn);
		}
		return list;
	}
	/**
	 * 取表中所有字段分页显示
	 * @param table
	 * @param type
	 * @param page
	 * @return
	 * @throws Exception
	 */
	public static List executePageQueryForTable(String table, Class type, PageBean page)
	throws Exception {
		return executePageQuery("SELECT * FROM "+table,type,page);
	}

	//-----------数据回滚--------------------------
	public static void rollback(Connection conn) throws SQLException {
		if (conn != null)
			conn.rollback();
	}

	//-----------关闭数据库相关对象-----------------
	public static void close(Connection conn) throws SQLException {
		if (conn != null)
			conn.close();
	}

	public static void close(Statement stmt) throws SQLException {
		if (stmt != null)
			try {
				stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
				throw e;
			}
	}

	public static void close(ResultSet rs) throws SQLException {
		if (rs != null)
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
				throw e;
			}
	}

	@SuppressWarnings("unchecked")
	public static Map rsToMap(ResultSet rs) throws SQLException {
		Map m = new HashMap();
		for (int i = rs.getMetaData().getColumnCount(); i > 0;) {
			Object k = rs.getMetaData().getColumnName(i);
			Object o = rs.getObject(i--);
			m.put(k, o);
		}
		return m;
	}

    @SuppressWarnings("unchecked")
	public static Map objectToMap(Object o,String[] extKeys) throws IllegalAccessException, InvocationTargetException {
        Map m           = new HashMap();
        Class	type	= o.getClass();
        Method[] mts    = type.getDeclaredMethods();
        for(int fi=0;fi<mts.length;++fi) {
            Object vo;
            String mtn  = mts[fi].getName().substring(0,3);
            if("get".equalsIgnoreCase(mtn)){
                vo   = mts[fi].invoke(o,new Object());
                m.put(mts[fi].getName().replaceAll("get","").toLowerCase(),vo);
            }
        }
        if(extKeys!=null) {for(int i=0;i<extKeys.length;++i) m.remove(extKeys[i]);}
        return m;
    }
}
