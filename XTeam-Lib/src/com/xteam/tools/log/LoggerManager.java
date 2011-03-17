/*
 * Copyright (c) 2005-2008 旭鸣软件
 * All rights reserved. 
 */
/*
 * File：LogerFactory.java
 * History:
 *       2007-12-19: Initially created, tangkf.
 */
package com.xteam.tools.log;

import org.apache.log4j.Logger;


/**
 * 静态工厂类，用于负责接管日志接口实现类
 * 
 * 这个工厂类必须在 系统 配置文件信息初始化后才能使用，
 * 因为他需要用到配置文件中的日志类路径.
 * @author tangkf
 */
public class LoggerManager {
	

	protected static ILogger logger		= new DefaultLogger();
	
	protected static ILogger clog		= new ConsoleLogger();
	
	protected static String logClass	= "com.tangkf.log.DefaultLogger";
	
	public static void main(String[] args){
		//ILogger log	= getLogger("app");
		ILogger log	= getLogger("app");
		//Logger log	= Logger.getLogger("sys");
		log.warn("错误信息3");
	}
	
	/**
	 * 获取/创建一个默认的日志处理类
	 * @author tangkf
	 * @return 默认的日志处理类
	 */
	public static ILogger getDefaultLogger(){
		return  new DefaultLogger();
	}
	
	public static ILogger getLogger(String name){
		return new DefaultLogger(name);
	}
	
	public static ILogger getSystemLogger(){
		if(clog==null){
			clog	= new ConsoleLogger();
		}
		return clog;
	}
	
	/**
	 * <P>
	 * 从Log4j中获取 Logger 对象
	 * </P>
	 * @author 汤垲峰 2009-2-13
	 * @param className
	 * @return
	 */
	public static Logger getLoggerByName(String className){
		Logger log	= Logger.getLogger(className);
		return log;
	}
	
	/**
	 * <P>
	 * 设置默认日志类路径
	 * </P>
	 * @author 汤垲峰 2009-2-11
	 * @param logClass
	 */
	public static void setDefaultLogger(String logClass){
		LoggerManager.logClass	= logClass;
	}
}
