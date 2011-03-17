/*
 * Copyright (c) 2005-2008 旭鸣软件
 * All rights reserved. 
 */
/*
 * File：ExcelUtils4JXL.java
 * History:
 *       2009-3-17: Initially created, 汤垲峰.
 */
package com.xteam.tools.excel;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import jxl.BooleanCell;
import jxl.Cell;
import jxl.CellType;
import jxl.DateCell;
import jxl.NumberCell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

import com.xteam.tools.CommonUtils;


/**
 * <p>
 * 以JXL 为核心的操作工具类
 * </p>
 * @author 汤垲峰
 */
public class ExcelUtils4JXL implements ExcelUtils {
	
	private InputStream fis	= null;
	
	private Workbook workBook;
	
	private WritableWorkbook writableWorkBook;
	
	private File file;

	/**
	 * <P>
	 * 效率很低
	 * </P>
	 * @author 汤垲峰 2009-3-17
	 * @param args
	 */
	public static void main(String[] args) {
		//testRead();
		testWrite();
	}
	
	public static void testWrite(){
		long a	= System.currentTimeMillis();
		for(int i=0;i<1;i++){
			ExcelUtils eu	= new ExcelUtils4JXL("F:/gggt.xls",true);
			
			eu.createSheet("表格");
			
			String[] ab	= {"12321321321","32321321","2009-03-22 17:33:33","你好"};
			for(int x=0;x<30;x++){
				eu.writeCell("-"+x+"-", x, 0,"表格");
				for(int c=1;c<ab.length;c++){
					eu.writeCell(ab[c], x, c,"表格");
				}
			}
			eu.appendRow(Arrays.asList(ab), "表格");	//追加一行
			
			eu.writeRow(Arrays.asList(ab), 0,"表格");	//在指定位置写入一行
			
			eu.save();
			eu.close();
		}
		long b	= System.currentTimeMillis();
		System.err.println((b-a));
	}
	
	public static void testRead(){
		long a	= System.currentTimeMillis();
		for(int i=0;i<20;i++){
			ExcelUtils eu	= new ExcelUtils4JXL("F:/menu.xls");
			
			List<List<String>> rst	= eu.getExcelSheetByIndex(2);
			for(List<String> ls:rst){
				for(String s:ls){
					System.out.print(s+"   ");
				}
				System.out.println();
			}
			eu.close();
		}
		long b	= System.currentTimeMillis();
		System.err.println((b-a));
	}
	
	
	/**
	 * 文件路径
	 * @param filePath
	 */
	public ExcelUtils4JXL(String filePath){
		File file	= new File(filePath);
		openWorkBookReadOnly(file);
	}
	
	/**
	 * 文件对象
	 * @param file
	 */
	public ExcelUtils4JXL(File file){
		openWorkBookReadOnly(file);
	}
	
	/**
	 * 打开一个可写的工作簿
	 * @param filePath 文件路径
	 * @param writable 是否可写
	 */
	public ExcelUtils4JXL(String filePath, boolean writable){
		File file	= new File(filePath);
		if(!writable){
			openWorkBookReadOnly(file);
		}else{
			openWorkBookWritable(file);
		}
	}

	/**
	 * 打开一个可写的工作簿
	 * @param file
	 * @param writable
	 */
	public ExcelUtils4JXL(File file, boolean writable){
		if(!writable){
			openWorkBookReadOnly(file);
		}else{
			openWorkBookWritable(file);
		}
	}
	
	/**
	 * 打开可写的工作簿
	 * @param file
	 */
	private void openWorkBookWritable(File file){
		try {
			this.writableWorkBook	= Workbook.createWorkbook(file);
			this.file		= file;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void openWorkBookReadOnly(File file){
		try {
			this.fis	= new FileInputStream(file);
			this.file	= file;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		openWorkBook();
	}
	
	/**
	 * 输入流
	 * @param is
	 */
	public ExcelUtils4JXL(InputStream is){
		this.fis	= is;
		openWorkBook();
	}
	
	
	private void openWorkBook(){
		try {
			workBook	= Workbook.getWorkbook(fis);
		} catch (BiffException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @see com.tangkf.utils.excel.ExcelUtils#getExcelSheetByIndex(int)
	 */
	public List<List<String>> getExcelSheetByIndex(int sheetIndex) {
		List<List<String>> rst	= new ArrayList<List<String>>();
		Sheet sheet	= null;
		if(workBook!=null)
			sheet	= workBook.getSheet(sheetIndex);
		
		if(sheet!=null){
			int rs	= sheet.getRows();
			for(int i=0;i<rs;i++){
				Cell[] cs	= sheet.getRow(i);
				if(cs!=null){
					List<String> row	= new ArrayList<String>();
					for(int j=0;j<cs.length;j++){
						Cell c	= cs[j];
						String value	= "";
						if(CellType.DATE.equals(c.getType())){
							DateCell dc	= (DateCell)c;
							value	= CommonUtils.getStDateTime(dc.getDate(),"GMT+0");
						}else if(CellType.NUMBER.equals(c.getType())){
							NumberCell dc	= (NumberCell)c;
							value	= String.valueOf(dc.getValue());
						}else if(CellType.BOOLEAN.equals(c.getType())){
							BooleanCell dc	= (BooleanCell)c;
							value	= String.valueOf(dc.getValue());
						}else{
							value = c.getContents();
						}
						row.add(value);
					}
					if(CommonUtils.notEmpty(row)) rst.add(row);
				}
			}
		}
		return rst;
	}

	/**
	 * @see com.tangkf.utils.excel.ExcelUtils#getExcelSheetByName(java.lang.String)
	 */
	public List<List<String>> getExcelSheetByName(String sheetName) {
		List<List<String>> rst	= new ArrayList<List<String>>();
		Sheet sheet	= null;
		if(workBook!=null)
		if(CommonUtils.isEmpty(sheetName)){
			sheet	= workBook.getSheet(0);
		}else{
			sheet	= workBook.getSheet(sheetName);
		}
		
		if(sheet!=null){
			int rs	= sheet.getRows();
			for(int i=0;i<rs;i++){
				Cell[] cs	= sheet.getRow(i);
				if(cs!=null){
					List<String> row	= new ArrayList<String>();
					for(int j=0;j<cs.length;j++){
						Cell c	= cs[j];
						String value	= "";
						if(CellType.DATE.equals(c.getType())){
							DateCell dc	= (DateCell)c;
							value	= CommonUtils.getStDateTime(dc.getDate());
						}else if(CellType.NUMBER.equals(c.getType())){
							NumberCell dc	= (NumberCell)c;
							value	= String.valueOf(dc.getValue());
						}else if(CellType.BOOLEAN.equals(c.getType())){
							BooleanCell dc	= (BooleanCell)c;
							value	= String.valueOf(dc.getValue());
						}else{
							value = c.getContents();
						}
						row.add(value);
					}
					if(CommonUtils.notEmpty(row)) rst.add(row);
				}
			}
		}
		return rst;
	}

	/**
	 * @see com.tangkf.utils.excel.ExcelUtils#close()
	 */
	public void close() {
			try {
				if(fis!=null) fis.close();
				if(workBook!=null) workBook.close();
				if(writableWorkBook!=null){
					writableWorkBook.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			} catch (WriteException e) {
				e.printStackTrace();
			}
	}


	/**
	 * @see com.tangkf.utils.excel.ExcelUtils#getExcelSheet(java.lang.String)
	 */
	public List<List<Object>> getExcelSheet(String sheetName) {
		List<List<Object>> rst	= new ArrayList<List<Object>>();
		Sheet sheet	= null;
		if(workBook!=null)
		if(CommonUtils.isEmpty(sheetName)){
			sheet	= workBook.getSheet(0);
		}else{
			sheet	= workBook.getSheet(sheetName);
		}
		
		if(sheet!=null){
			int rs	= sheet.getRows();
			for(int i=0;i<rs;i++){
				Cell[] cs	= sheet.getRow(i);
				if(cs!=null){
					List<Object> row	= new ArrayList<Object>();
					for(int j=0;j<cs.length;j++){
						Cell c	= cs[j];
						Object value	= "";
						if(CellType.DATE.equals(c.getType())){
							DateCell dc	= (DateCell)c;
							value	= dc.getDate();
						}else if(CellType.NUMBER.equals(c.getType())){
							NumberCell dc	= (NumberCell)c;
							value	= dc.getValue();
						}else if(CellType.BOOLEAN.equals(c.getType())){
							BooleanCell dc	= (BooleanCell)c;
							value	= dc.getValue();
						}else{
							value = c.getContents();
						}
						row.add(value);
					}
					if(CommonUtils.notEmpty(row)) rst.add(row);
				}
			}
		}
		return rst;
	}


	/**
	 * @see com.tangkf.utils.excel.ExcelUtils#getExcelSheet(int)
	 */
	public List<List<Object>> getExcelSheet(int sheetIndex) {
		List<List<Object>> rst	= new ArrayList<List<Object>>();
		Sheet sheet	= null;
		if(workBook!=null)
			sheet	= workBook.getSheet(sheetIndex);
		
		if(sheet!=null){
			int rs	= sheet.getRows();
			for(int i=0;i<rs;i++){
				Cell[] cs	= sheet.getRow(i);
				if(cs!=null){
					List<Object> row	= new ArrayList<Object>();
					for(int j=0;j<cs.length;j++){
						Cell c	= cs[j];
						Object value	= "";
						if(CellType.DATE.equals(c.getType())){
							DateCell dc	= (DateCell)c;
							value	= dc.getDate();
						}else if(CellType.NUMBER.equals(c.getType())){
							NumberCell dc	= (NumberCell)c;
							value	= dc.getValue();
						}else if(CellType.BOOLEAN.equals(c.getType())){
							BooleanCell dc	= (BooleanCell)c;
							value	= dc.getValue();
						}else{
							value = c.getContents();
						}
						row.add(value);
					}
					if(CommonUtils.notEmpty(row)) rst.add(row);
				}
			}
		}
		return rst;
	}
	
	/**
	 * @see com.tangkf.utils.excel.ExcelUtils#appendRow(java.util.List, java.lang.String)
	 */
	public void appendRow(List<String> row, String sheetName) {
		WritableSheet sheet	= getWritableSheet(sheetName);
		if(sheet!=null){
			int nextRow	= sheet.getRows();
			this.writeRow(row, nextRow, sheetName);
		}
	}


	/**
	 * @see com.tangkf.utils.excel.ExcelUtils#appendRow(java.util.List, int)
	 */
	public void appendRow(List<String> row, int sheetIndex) {
		WritableSheet sheet	= getWritableSheet(sheetIndex);
		if(sheet!=null){
			int nextRow	= sheet.getRows();
			this.writeRow(row, nextRow, sheetIndex);
		}
	}


	/**
	 * @see com.tangkf.utils.excel.ExcelUtils#writeCell(java.lang.String, int, int, int)
	 */
	public void writeCell(String cvalue, int rowNum, int colNum, int sheetIndex) {
		WritableSheet sheet	= getWritableSheet(sheetIndex);
		if(sheet!=null){
			writeCell(cvalue,rowNum,colNum,sheet);
		}
	}


	/**
	 * @see com.tangkf.utils.excel.ExcelUtils#writeCell(java.lang.String, int, int, java.lang.String)
	 */
	public void writeCell(String cvalue, int rowNum, int colNum, String sheetName) {
		WritableSheet sheet	= getWritableSheet(sheetName);
		if(sheet!=null){
			writeCell(cvalue,rowNum,colNum,sheet);
		}
	}


	/**
	 * @see com.tangkf.utils.excel.ExcelUtils#writeRow(java.util.List, int, int)
	 */
	public void writeRow(List<String> row, int rowNum, int sheetIndex) {
		WritableSheet sheet	= getWritableSheet(sheetIndex);
		if(sheet!=null){
			writeRow(row,rowNum,sheet);
		}
	}


	/**
	 * @see com.tangkf.utils.excel.ExcelUtils#writeRow(java.util.List, int, java.lang.String)
	 */
	public void writeRow(List<String> row, int rowNum, String sheetName) {
		WritableSheet sheet	= getWritableSheet(sheetName);
		if(sheet!=null){
			writeRow(row,rowNum,sheet);
		}
	}
	
	private void writeRow(List<String> row, int rowNum, WritableSheet sheet){
		int i=0;
		if(sheet!=null)
		for(String cell:row){
			writeCell(cell,rowNum,i,sheet);
			i++;
		}
	}
	
	private void writeCell(Object cvalue,int rowNum,int colNum, WritableSheet sheet){
		Label cell	= new Label(colNum,rowNum,cvalue+"");
		if(sheet!=null){
			try {
				sheet.addCell(cell);
			} catch (RowsExceededException e) {
				e.printStackTrace();
			} catch (WriteException e) {
				e.printStackTrace();
			}
		}
	}
	
	private WritableSheet getWritableSheet(String sheetName){
		WritableSheet sheet	= null;
		if(writableWorkBook!=null){
			if(CommonUtils.isEmpty(sheetName)){
				try{
					sheet	= writableWorkBook.getSheet(0);
				}catch(Exception e){
					sheet	= null;
				}	//默认返回第一张表
			}else{
				try{
					sheet	= writableWorkBook.getSheet(sheetName);
				}catch(Exception e){
					sheet	= null;
				}
			}
			if(sheet==null){
				sheet	= this.writableWorkBook.createSheet(sheetName, 0);	//创建一张新表
			}
		}
		return sheet;
	}
	
	private WritableSheet getWritableSheet(int sheetIndex){
		WritableSheet sheet	= null;
		if(writableWorkBook!=null){
			try{
				sheet	= writableWorkBook.getSheet(sheetIndex);
			}catch(Exception e){
				sheet	= null;
			}
			if(sheet==null){
				sheet	= this.writableWorkBook.createSheet(sheetIndex+"_auto", 0);	//创建一张新表
			}
		}
		return sheet;
	}

	/**
	 * @see com.tangkf.utils.excel.ExcelUtils#save()
	 */
	public void save() {
		try {
			this.writableWorkBook.write();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @see com.tangkf.utils.excel.ExcelUtils#createSheet(java.lang.String)
	 */
	public void createSheet(String sheetName) {
		this.writableWorkBook.createSheet(sheetName, 0);
	}

	/**
	 * @see com.tangkf.utils.excel.ExcelUtils#write(java.io.OutputStream)
	 */
	public void write(OutputStream os) {
		if(this.file!=null){
			int len	= 0;
			byte[] buf = new byte[1024];
			BufferedInputStream br;
			try {
				br = new BufferedInputStream(new FileInputStream(file));
	            while((len=br.read(buf))!=-1){    
	            	os.write(buf, 0, len);    
	            } 
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
