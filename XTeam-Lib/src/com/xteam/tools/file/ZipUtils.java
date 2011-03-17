/*
 * Copyright (c) 2005-2008 成均科技
 * All rights reserved. 
 */
/*
 * File：ZipUtils.java
 * History:
 *       2007-11-16: Initially created, tangkf.
 */
package com.xteam.tools.file;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.List;

import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipFile;

/**
 * 压缩处理工具
 * @author tangkf
 */
public class ZipUtils {
	
	public static final String ZIP_FILE	= "zip";
	public static final String SLASH		= "/";

	public static void main(String[] args){
			//ZipUtils.deCompressZipFile(new File("G:\\test.zip"),null);
			ZipUtils.deCompressZipFile(new File("e:/software/ff.zip"));
	}

	
	/**
	 * 解压一个zip文件 到指定位置 目前只支持单层压缩文件
	 * @author tangkf
	 * @param file
	 * @param targetFile
	 */
	public static int deCompressZipFile(File zipFile,File targetFile){
			int rtn	= 0;
			if(!zipFile.exists()) return rtn;
			try{
				if(isZipFile(zipFile)){
					ZipFile zFile = new ZipFile(zipFile.getAbsolutePath());
					Enumeration<?> zList = zFile.getEntries();
					ZipEntry ze = null;
					byte[] buf = new byte[1024];
					//---取到其中一个压缩文件中的每一个文件
					while(zList.hasMoreElements()){
						ze = (ZipEntry)zList.nextElement();
						
						String fileName = ze.getName();
						
						//---组装被解压出来的文件路径
						String parentPath = "";
						if(targetFile !=null){
							parentPath = targetFile.getPath();
						}else{
							parentPath = zipFile.getParent();
						}
						File outFile	= null;
						String path = parentPath + SLASH + fileName;
						outFile = new File(path);
						if(!outFile.exists()) outFile.createNewFile();
						OutputStream os = new BufferedOutputStream(new FileOutputStream(outFile));
						InputStream is 	= new BufferedInputStream(zFile.getInputStream(ze));
						int readLen = 0;
						//---将压缩文件中的文件流读出来并写入一个新文件内
						while ((readLen = is.read(buf, 0, 1024))!=-1) {
							os.write(buf, 0, readLen);
						}
						is.close();
						os.close();
					}
					zFile.close();
				}
			}catch(Exception e){
				e.printStackTrace();
				rtn	= 1;
			}
			return rtn;
	}
	
	/**
	 * 解压一个zip文件 到 当前目录
	 * @author tangkf
	 * @param file
	 */
	public static int deCompressZipFile(File file){
			return deCompressZipFile(file,null);
	}

	/**
	 * 解压一个目录下的所有zip文件到指定位置
	 * @author tangkf
	 * @param file
	 * @param targetFile
	 */
	public static int deCompressZipDir(File file,File targetFile){
			int rtn	= 0;
			if(file.isDirectory()){
				List<File> zipFileList	= FileUtils.getFileListByPath(file, ZIP_FILE);
				for(File zipFile:zipFileList){
					if(ZipUtils.deCompressZipFile(zipFile, targetFile)==1) rtn = 1;
				}
			}
			return rtn;
	}
	
	/**
	 * 解压一个目录下的所有zip文件到当前位置位置
	 * @author tangkf
	 * @param file
	 */
	public static int deCompressZipDir(File file){
			return deCompressZipDir(file,null);
	}
	
	public static int deCompressZipFiles(List<File> fileList){
			for(File f:fileList){
				deCompressZipFile(f);
			}
			return 0;
	}
	
	
	/**
	 * 
	 * 压缩一个文件目录到指定位置
	 * @author tangkf
	 * @param file
	 * @param targetFile
	 */
	public static void compressZipDir(File file,File targetFile){
		//TODO
	}
	
	/**
	 * 压缩一个文件目录到当前位置
	 * @author tangkf
	 * @param file
	 */
	public static void compressZipDir(File file){
		//TODO
	}
	
	/**
	 * 判断是否zip结尾,不区分大小写
	 * @author tangkf
	 * @param file
	 * @return
	 */
	public static boolean isZipFile(File file){
			if(file!=null && file.getName()!=null && file.getName().toLowerCase().endsWith(ZIP_FILE.toLowerCase())) 
				return true;
			else 
				return false;
	}
}
