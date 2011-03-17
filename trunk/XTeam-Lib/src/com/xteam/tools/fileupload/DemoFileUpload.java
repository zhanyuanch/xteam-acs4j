/*
 * Copyright (c) 2005-2008 旭鸣软件
 * All rights reserved. 
 */
/*
 * File：DemoFileUpload.java
 * History:
 *       2009-3-17: Initially created, 汤垲峰.
 */
package com.xteam.tools.fileupload;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;

/**
 * <p>
 *
 * </p>
 * @author 汤垲峰
 *
 */
public class DemoFileUpload  extends HttpServlet{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5208073044022366578L;
	
	
	/**
	 * @see javax.servlet.http.HttpServlet#doPost(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		FileUploadUtils fuu	= new FileUploadUtils(req,"xls|csv|txt",256000000);
		  
		  List<FileItem> fis	= fuu.getFileItems();
		  if(fis!=null && fis.size()>0){
			  for(FileItem fi:fis){
				try {
					fuu.saveFile(fi,"c:/"+fi.getName().substring(fi.getName().lastIndexOf("\\")+1));
				} catch (Exception e) {
					e.printStackTrace();
				}
			  }
		  }
	  fuu.clear();
	}
}
