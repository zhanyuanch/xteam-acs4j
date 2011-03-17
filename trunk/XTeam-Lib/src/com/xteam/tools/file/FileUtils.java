/*
 * Copyright (c) 2005-2008 成均科技
 * All rights reserved. 
 */
/*
 * File：FileUtils.java
 * History:
 *       2007-11-17: Initially created, tangkf.
 */
package com.xteam.tools.file;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileFilter;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Vector;

/**
 * 文件操作
 * @author tangkf
 * 文件操作工具类
 */
public class FileUtils {
	
	public static final String SLASH		= "/";
	
	public static void main(String[] args){
		
		FileUtils.writeFile("c:/aaa/bbb/u.txt", "fdsafdsafd\r\n","utf-8");
		
//		FileUtils.makeDir(new File("c:/aaa/bb/ccc/dd"));
//		List<File> fl	= FileUtils.getFileListByPath("C:/","ini,com");
//		for(Iterator<File> it=fl.iterator();it.hasNext();){
//			File f	= it.next();
//			System.out.println(f.getName());
//		}
//
//		FileUtils.moveFileByPath("c:/eeg/ccf", "c:/ddc/ccc");
	}
	
	
	
	/**
	 * 获取一个路径下的所有目录
	 * @author tangkf
	 * @param path
	 * @return
	 */
	public static List<File> getDirListByPath(String path){
		List<File> files	= getObjListByPath(path,0,null);
		return files;
	}
	
	/**
	 * 获取一个路径下的所有对象表
	 * @author tangkf
	 * @param path
	 * @return
	 */
	public static List<File> getAllListByPath(String path){
		List<File> files	= getObjListByPath(path,2,null);
		return files;
	}
	
	/**
	 * 获取一个路径下的指定类型文件列表
	 * @author tangkf
	 * @param path 路径
	 * @param fileType 文件类型 为空(null)时表示所有类型
	 * @return
	 */
	public static List<File> getFileListByPath(String path,String fileTypes){
		List<File> files	= getObjListByPath(path,1,fileTypes);
		return files;
	}
	
	/**
	 * 获取一个路径下的指定类型文件列表
	 * @author tangkf
	 * @param file 路径
	 * @param fileType 文件类型  文件类型字符串,如:"exe,bat,ocx,sys" 这种类型 为空(null)时表示所有类型
	 * @return
	 */
	public static List<File> getFileListByPath(File file,String fileTypes){
		List<File> files	= getObjListByPath(file,1,fileTypes);
		return files;
	}
	
	/**
	 * 获取一个路径下的所有文件列表
	 * @author tangkf
	 * @param path
	 * @return
	 */
	public static List<File> getFileListByPath(String path){
		List<File> files	= getObjListByPath(path,1,null);
		return files;
	}
	
	/**
	 * 
	 * 获取一个路径下的文件系统对象
	 * 
	 * @author tangkf
	 * @param path
	 * @param isFile 0:所有目录,1:所有文件,2:所有对象
	 * @param fileTypes 文件类型字符串,如:"exe,bat,ocx,sys" 这种类型
	 * @return
	 */
	public static List<File> getObjListByPath(String path,int isFile,String fileTypes){
		File src	= new File(path);
		return getObjListByPath(src,isFile,fileTypes);
	}
	
	/**
	 * 
	 * 获取一个File路径下的文件系统对象
	 * 
	 * @author tangkf
	 * @param file
	 * @param isFile 0:所有目录,1:所有文件,2:所有对象
	 * @param fileTypes 文件类型字符串,如:"exe,bat,ocx,sys" 这种类型
	 * @return
	 */
	public static List<File> getObjListByPath(File file,int isFile,String fileTypes){
		List<File> files= new Vector<File>();
		if(!file.exists()) return files;
		try{
			File[] fs	= file.listFiles(getFileFilter(fileTypes));
			for(int i=0;i<fs.length;++i){
				File f	= fs[i];
				if(isFile==1 && f.isFile()){
					files.add(f);
				}else if(isFile==0 && f.isDirectory()){
					files.add(f);
				}else{
					files.add(f);
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return files;
	}
	

	/**
	 * 创建一个空文件
	 * @author tangkf
	 * @param filePath
	 * @return
	 */
	public static File createFile(String filePath){
		File rtn	= null;
		try {
			File file	= new File(filePath);
			makeParentDir(file);
			file.createNewFile();
			rtn	= file;
		} catch (IOException e) {
			rtn	= null;
			e.printStackTrace();
		}
		return rtn;
	}
	
	/**
	 * 创建并写入文件内容
	 * @param file 文件路径
	 * @param text 文件内容
	 * @param encoding 编码方式
	 * @return
	 */
	public static int createFile(String file,String text,String encoding){
			int rtn	= 0;
			FileOutputStream fo	= null;
			File f	= createFile(file);
			try{
				if(f!=null){
					fo	= new FileOutputStream(f);
					if(encoding==null){
						fo.write(text.getBytes());
					}else{
						fo.write(new String(text.getBytes(),encoding).getBytes());
					}
					fo.flush();
					fo.close();
				}
			}catch(Exception e){
				e.printStackTrace();
				rtn	= 1;
			}finally{
				try {
					if(fo!=null) fo.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			return rtn;
	}
	
	/**
	 * 写入文件内容,不带编码
	 * 如果文件不存在将自动创建，如果存在将被追加
	 * @param file
	 * @param text
	 * @return
	 */
	public static int writeFile(String file,String text){
		return writeFile(file,text,null);
	}
	
	/**
	 * 写入文件内容,并带编码
	 * 如果文件不存在将自动创建，如果存在将被追加
	 * @param file
	 * @param text
	 * @param encoding
	 * @return
	 */
	public static int writeFile(String file,String text,String encoding){
		File f	= new File(file);
		if(!f.exists()){
			return createFile(file,text,encoding);
		}else{
			return appendFile(file,text,encoding);
		}
	}
	
	/**
	 * 追加文件内容，默认编码
	 * @author tangkf
	 * @param file
	 * @param text
	 * @return
	 */
	public static int appendFile(String file, String text){
		return appendFile(file,text,null);
	}
	
	/**
	 * 追加文件内容，指定编码
	 * @author tangkf
	 * @param file
	 * @param text
	 * @param encoding
	 * @return
	 */
	public static int appendFile(String file, String text, String encoding) {
		int rtn	= 0;
		try{
			FileWriter fw = new FileWriter(file,true);
			if(fw!=null){
				BufferedWriter bw = new BufferedWriter(fw);
				if(encoding==null){
					bw.append(text);
				}else{
					bw.append(new String(text.getBytes(),encoding));
				}
				bw.flush();
				bw.close();
				fw.close();
			}
		}catch(Exception e){
			e.printStackTrace();
			rtn	= 1;
		}
		return rtn;
	}



	/**
	 * 创建并写入文件内容
	 * @param file
	 * @param text
	 * @return
	 */
	public static int createFile(String file,String text){
			return createFile(file,text,null);
	}
	
	/**
	 * 删除一个文件
	 * @author tangkf
	 * @param filePath
	 * @return
	 */
	public static int deleteFile(String filePath){
		try{
		File file	= new File(filePath);
		if(file.exists()) file.delete();
		}catch(Exception e){
			return 1;
		}
		return 0;
	}
	
	/**
	 * 删除一个目录,目录必须为空
	 * @author tangkf
	 * @param path
	 * @return
	 */
	public static int deleteDir(String dirPath){
		File file	= new File(dirPath);
		
		if(file.exists()) file.delete();
		return 0;
	}
	
	/**
	 * 删除一个指定的路径下的所有文件
	 * @author tangkf
	 * @param path
	 * @return
	 */
	public static int deleteFileInPath(String path){
		List<File> files	= getObjListByPath(path,1,null);
		for(File f:files){
			if(f.exists()) f.delete();
		}
		return 0;
	}
	
	/**
	 * 删除一个指定的路径下的指定类型的文件
	 * @param path
	 * @param fileType
	 * @return
	 */
	public static int deleteFileInPath(String path,String fileType){
		List<File> files	= getObjListByPath(path,1,fileType);
		for(File f:files){
			if(f.exists()) f.delete();
		}
		return 0;
	}

	/**
	 * 移动一个路径下的所有指定类型文件到目标路径下
	 * @param srcPath
	 * @param targetPath
	 * @param fileTypes  文件类型字符串,如:"exe,bat,ocx,sys" 这种类型
	 * @return
	 */
	public static int moveFileByPath(String srcPath,String targetPath,String fileTypes){
		List<File> flist	= getFileListByPath(srcPath, fileTypes);
		for(File f:flist){
			File tf	= new File(targetPath+SLASH+f.getName());
			makeParentDir(tf);
			moveFile(f,tf);
		}
		return 0;
	}
	
	public static int moveFileByPath(String srcPath,String targetPath){
		return moveFileByPath(srcPath,targetPath,null);
	}
	
	/**
	 * 移动一个文件或目录到目标路径并改名称
	 * @param srcFile
	 * @param targetFile
	 * @return
	 */
	public static int moveFile(String srcFile,String targetFile){
		int rtn	= 0;
		try{
			File src	= new File(srcFile);
			File tag	= new File(targetFile);
			makeParentDir(tag);
			moveFile(src,tag);
		}catch(Exception e){
			e.printStackTrace();
			rtn	= 1;
		}
		return rtn;
	}
	
	/**
	 * 移动一个文件或目录到目标路径并改名称
	 * @param src
	 * @param target
	 * @return
	 */
	public static int moveFile(File src,File target){
		int rtn	= 0;
		try{
			if(!src.exists()) src.createNewFile();
			makeParentDir(target);
			src.renameTo(target);
		}catch(Exception e){
			e.printStackTrace();
			rtn	= 1;
		}
		return rtn;
	}
	
	/**
	 * 创建目录
	 * @author tangkf
	 * @param file
	 * @return
	 */
	public static int makeDir(File file){
		int rtn	= 0;
		if(file.exists()) return 0;
		if(file.isFile()) return 0;
		if(file.isDirectory()) return 0;
		if(!file.isDirectory()) file.mkdirs();
		return rtn;
	}
	
	public static int makeParentDir(File file){
		return makeDir(file.getParentFile());
	}
	
	/**
	 * 获取文件过滤器
	 * 通过标准的FileFilter接口来实现
	 * @author tangkf
	 * @param fileTypes 文件类型,可以是 "exe,rar,add,fdd" 这种形式的字符串
	 * @return
	 */
	public static FileFilter getFileFilter(String fileTypes){
		
		/**
		 * 该类实现了标准的文件过滤接口 
		 * @author tangkf
		 */
		class MyFileFilter implements FileFilter{
			private String fileTypes	= null;
			
			private String ALL_FILES	= "*|*.*";
			
			/**
			 * @return 属性fileTypes的值.
			 */
			public String getFileTypes() {
				return this.fileTypes;
			}

			/**
			 * @param fileTypes 属性fileTypes.
			 */
			public void setFileTypes(String fileTypes) {
				this.fileTypes = fileTypes;
			}

			public boolean accept(File pathname) {
				if(this.getFileTypes()==null || "".equals(this.getFileTypes()) || ALL_FILES.indexOf(this.getFileTypes())!=-1) return true;
				
				String fName	= pathname.getName();
				String ftype	= null;
				int p			= fName.lastIndexOf(".");
				if(p>0) ftype	= fName.substring(p+1).toLowerCase();
				if(ftype!=null && this.getFileTypes().toLowerCase().indexOf(ftype)!=-1){
					return true;
				}else{
					return false;
				}
			}
		}
		
		MyFileFilter	mff	= new MyFileFilter();
						mff.setFileTypes(fileTypes);
		return mff;
	}
	
}
