/*
 * Copyright (c) 2005-2008 旭鸣软件
 * All rights reserved. 
 */
/*
 * File：FileUploadUtils.java
 * History:
 *       2009-3-17: Initially created, 汤垲峰.
 */
package com.xteam.tools.fileupload;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

/**
 * <p>
 * 文件上传工具类
 * </p>
 * @author 汤垲峰
 *
 */
public class FileUploadUtils {
	
	/**
	 * 用来保存表单域
	 */
	private List<String> formFieldValues	= new ArrayList<String>();
	
	private List<String> formFieldNames	= new ArrayList<String>();
	
	private List<FileItem> fileItems		= new ArrayList<FileItem>();
	
	private List<InputStream> inputStreams	= new ArrayList<InputStream>();
	
	private List<File> fileList			= new ArrayList<File>();
	
	private Map<String,String> fields		= new HashMap<String, String>();
	
	private String errorInfo	= "OK";

	private int errorCode;
	/**
	 * <P>
	 * 测试
	 * </P>
	 * @author 汤垲峰 2009-3-17
	 * @param args
	 */
	public static void main(String[] args) {

		String a	= "afdsafdsa.cc.ppt";
		
		System.err.println(a.substring(a.lastIndexOf(".")+1));
	}
	
	
	/**
	 * 构造文件上传对象
	 * @param request
	 * @param fileTypes
	 * @param maxSize
	 */
	public FileUploadUtils(HttpServletRequest request,String fileTypes,long maxSize){
		initialize(request,fileTypes,maxSize);
	}
	
	
	/**
	 * <P>
	 * 在指定的路劲保存文件对象
	 * </P>
	 * @author 汤垲峰 2009-3-17
	 * @param fileItem
	 * @param filePath
	 * @throws Exception
	 */
	public void saveFile(FileItem fileItem,String filePath) throws Exception{
		File file	= new File(filePath);
		fileItem.write(file);
	}
	
	
	@SuppressWarnings("unchecked")
	private void initialize(HttpServletRequest request,String fileTypes,long maxSize){
		boolean isMultipart = ServletFileUpload.isMultipartContent(request);
		if(isMultipart){
			FileItemFactory factory = new DiskFileItemFactory();
			ServletFileUpload upload = new ServletFileUpload(factory);
			upload.setFileSizeMax(maxSize);
			try {
				List filist	= upload.parseRequest(request);
				for(Object o:filist){
					if(o!=null && o instanceof FileItem){
						FileItem fi	= (FileItem)o;
						String fname= fi.getName();
						if(fi.isFormField()){
						//表单域的处理
							processField(fi,fname);
						}else if(fi.getSize()>0){
						//文件的处理
							processFile(fi, fileTypes, maxSize);
						}else{
							System.err.println("空表单域");
						}
					}
				}
			} catch (FileUploadException e) {
				System.err.println("文件流读取错误:"+e.getMessage());
				errorInfo	= "文件流读取错误:"+e.getMessage();
				errorCode	= 3;
			}
		}
	}
	
	/**
	 * <P>
	 * 处理表单域
	 * </P>
	 * @author 汤垲峰 2009-3-17
	 * @param fi
	 */
	private void processField(FileItem fi,String fname){
		String v	= this.fields.get(fname);
		formFieldNames.add(fname);
		formFieldValues.add(v);
		if(v==null){
			v	= fi.getString();
		}else{
			v	= v+","+fi.getString();
		}
		this.fields.put(fname, v);
	}
	
	/**
	 * <P>
	 * 处理文件
	 * </P>
	 * @author 汤垲峰 2009-3-17
	 * @param fi
	 * @param fileTypes
	 * @param maxSize
	 */
	private void processFile(FileItem fi,String fileTypes,long maxSize){
		String fname	= fi.getName();
		String fileName	= fname.substring(fname.lastIndexOf("\\")+1);
		System.err.println("文件名："+fileName);
		String ftype	= fileName.substring(fileName.lastIndexOf(".")+1);
		if(fileTypes.indexOf(ftype)!=-1){
			long bytesize	= fi.getSize();
			if(bytesize<maxSize){
				try {
					System.err.println("文件类型："+ftype);
					inputStreams.add(fi.getInputStream());
					fileList.add(new File(fname));
					fileItems.add(fi);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}else{
				System.err.println("超过最大限制："+bytesize+"/"+maxSize);
				errorInfo	= "超过最大限制："+bytesize+"/"+maxSize;
				errorCode	= 1;
			}
		}else{
			System.err.println("不支持的文件类型:"+fileName);
			errorInfo	= "不支持的文件类型:"+fileName;
			errorCode	= 2;
		}
	}
	
	
	public String getErrorInfo(){
		return errorInfo;
	}
	
	public int getErrorCode(){
		return errorCode;
	}

	
	/**
	 * <P>
	 * 获取文件列表
	 * </P>
	 * @author 汤垲峰 2009-3-17
	 * @return
	 */
	public List<File> getFileList(){
		return this.fileList;
	}
	
	/**
	 * <P>
	 * 获取文件流列表
	 * </P>
	 * @author 汤垲峰 2009-3-17
	 * @return
	 */
	public List<InputStream> getStreamList(){
		return this.inputStreams;
	}
	
	/**
	 * <P>
	 * 返回文件对象
	 * </P>
	 * @author 汤垲峰 2009-3-17
	 * @return
	 */
	public List<FileItem> getFileItems(){
		return this.fileItems;
	}
	
	/**
	 * <P>
	 * 获取表单域名称
	 * </P>
	 * @author 汤垲峰 2009-3-17
	 * @return
	 */
	public List<String> getFieldNames(){
		return this.formFieldNames;
	}
	
	
	/**
	 * <P>
	 * 获取制定表单域的值
	 * </P>
	 * @author 汤垲峰 2009-3-17
	 * @param key
	 * @return
	 */
	public String getFieldValue(String key){
		return this.fields.get(key);
	}
	
	
	/**
	 * <P>
	 * 获取表单域值
	 * </P>
	 * @author 汤垲峰 2009-3-17
	 * @return
	 */
	public List<String> getFieldValues(){
		return this.formFieldValues;
	}

	
	/**
	 * <P>
	 * 清除
	 * </P>
	 * @author 汤垲峰 2009-3-17
	 */
	public void clear(){
		this.fields.clear();
		this.fileList.clear();
		this.formFieldNames.clear();
		this.formFieldValues.clear();
		this.inputStreams.clear();
		this.fileItems.clear();
	}
	
	/**
	 * <P>
	 * 清除
	 * </P>
	 * @author 汤垲峰 2009-3-17
	 */
	public void close(){
		this.clear();
	}
}
