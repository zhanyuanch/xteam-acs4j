/*
 * Copyright (c) 2005-2008 旭鸣软件
 * All rights reserved. 
 */
/*
 * File：ExcelUtils.java
 * History:
 *       2009-3-17: Initially created, 汤垲峰.
 */
package com.xteam.tools.excel;

import java.io.OutputStream;
import java.util.List;

/**
 * <p>
 * Excel 操作接口
 * </p>
 * @author 汤垲峰
 */
public interface ExcelUtils {

	/**
	 * <P>
	 * 获取制定ID 的工作表
	 * </P>
	 * @author 汤垲峰 2009-3-17
	 * @param sheetIndex
	 * @return
	 */
	public List<List<String>> getExcelSheetByIndex(int sheetIndex);

	/**
	 * <P>
	 * 读取工作表 返回为链表数组
	 * </P>
	 * @author 汤垲峰 2009-3-17
	 * @param is
	 * @param sheetName
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<List<String>> getExcelSheetByName(String sheetName);
	
	
	/**
	 * <P>
	 * 获取字段为对象
	 * </P>
	 * @author 汤垲峰 2009-3-17
	 * @param sheetName
	 * @return
	 */
	public List<List<Object>> getExcelSheet(String sheetName);
	
	
	/**
	 * <P>
	 * 获取字段为对象
	 * </P>
	 * @author 汤垲峰 2009-3-17
	 * @param sheetName
	 * @return
	 */
	public List<List<Object>> getExcelSheet(int index);
	
	/**
	 * <P>
	 * 创建一个指定名称的工作表，不返回任何值
	 * </P>
	 * @author 汤垲峰 2009-10-22
	 * @param sheetName
	 */
	public void createSheet(String sheetName);
	
	/**
	 * <P>
	 * 向 指定的 sheet 表的末尾追加一行
	 * </P>
	 * @author 汤垲峰 2009-10-22
	 * @param row 行数据
	 * @param sheetName 工作表名称
	 */
	public void appendRow(List<String> row,String sheetName);
	
	/**
	 * <P>
	 * 向指定的 index 的sheet 表末尾追加一行
	 * </P>
	 * @author 汤垲峰 2009-10-22
	 * @param row 行数据
	 * @param sheetIndex 工作表索引
	 */
	public void appendRow(List<String> row,int sheetIndex);
	
	/**
	 * 在指定位置写入一行记录
	 * @author 汤垲峰 2009-10-22
	 * @param row 行数据
	 * @param rowNum 行标号
	 * @param sheetIndex 工作表索引号
	 */
	public void writeRow(List<String> row,int rowNum,int sheetIndex);
	
	/**
	 * <P>
	 * 在指定位置写入一行记录
	 * </P>
	 * @author 汤垲峰 2009-10-22
	 * @param row 行数据
	 * @param rowNum 行索引
	 * @param sheetName 工作表名
	 */
	public void writeRow(List<String> row,int rowNum,String sheetName);
	
	/**
	 * <P>
	 * 在指定单元格写入数据
	 * </P>
	 * @author 汤垲峰 2009-10-22
	 * @param cvalue 单元格数据
	 * @param rowNum 行号
	 * @param colNum 列号
	 * @param sheetIndex 工作表索引
	 */
	public void writeCell(String cvalue,int rowNum,int colNum, int sheetIndex);
	
	/**
	 * <P>
	 * 在指定单元格写入数据
	 * </P>
	 * @author 汤垲峰 2009-10-22
	 * @param cvalue 单元格数据
	 * @param rowNum 行号
	 * @param colNum 列好
	 * @param sheetName 工作表名称
	 */
	public void writeCell(String cvalue,int rowNum,int colNum, String sheetName);
	
	
	
	/**
	 * <P>
	 * 关闭EXCEL对象
	 * </P>
	 * @author 汤垲峰 2009-3-17
	 */
	public void close();
	
	/**
	 * <P>
	 * 将打开的工作簿输出到一个指定的目标流对象 os
	 * </P>
	 * @author 汤垲峰 2009-10-22
	 * @param os
	 */
	public void write(OutputStream os);
	
	/**
	 * <P>
	 * 保存工作簿
	 * </P>
	 * @author 汤垲峰 2009-10-22
	 */
	public void save();
}
