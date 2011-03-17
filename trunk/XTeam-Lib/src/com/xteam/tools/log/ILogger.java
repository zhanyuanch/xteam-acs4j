/*
 * Copyright (c) 2005-2008 成均科技
 * All rights reserved. 
 */
/*
 * File：ILoger.java
 * History:
 *       2007-12-19: Initially created, tangkf.
 */
package com.xteam.tools.log;

import org.apache.log4j.Logger;

/**
 * 用于规范日志功能接口
 * @author tangkf
 *
 */
public interface ILogger {
	
	/**
	 * 信息
	 * @author  tangkf
	 * @param message
	 */
	public void info(Object message);
	
	/**
	 * 错误
	 * @author  tangkf
	 * @param message
	 */
	public void error(Object message);
	
	/**
	 * 警告信息
	 * @author  tangkf
	 * @param message
	 */
	public void warn(Object message);
	
	/**
	 * 调试信息
	 * @author  tangkf
	 * @param message
	 */
	public void debug(Object message);
	
	/**
	 * 致命错误
	 * @author  tangkf
	 * @param message
	 */
	public void fatal(Object message);
	
	/**
	 * 常规信息日志
	 * @author tangkf
	 * @param cls
	 * @param message
	 */
	public void info(String cls,Object message);
	
	/**
	 * 错误信息日志
	 * @author tangkf
	 * @param cls
	 * @param message
	 */
	public void error(String cls,Object message);
	
	/**
	 * 警告信息日志
	 * @author tangkf
	 * @param cls
	 * @param message
	 */
	public void warn(String cls,Object message);
	
	/**
	 * 调试信息日志
	 * @author tangkf
	 * @param cls
	 * @param message
	 */
	public void debug(String cls,Object message);
	
	/**
	 * 致命错误日志
	 * @author tangkf
	 * @param cls
	 * @param message
	 */
	public void fatal(String cls,Object message);

	/**
	 * 常规信息日志
	 * @author tangkf
	 * @param cls
	 * @param message
	 */
	public void info(Class<?> cls,Object message);
	
	/**
	 * 错误信息日志
	 * @author tangkf
	 * @param cls
	 * @param message
	 */
	public void error(Class<?> cls,Object message);
	
	/**
	 * 警告信息日志
	 * @author tangkf
	 * @param cls
	 * @param message
	 */
	public void warn(Class<?> cls,Object message);
	
	/**
	 * 调试信息日志
	 * @author tangkf
	 * @param cls
	 * @param message
	 */
	public void debug(Class<?> cls,Object message);
	
	/**
	 * 致命错误日志
	 * @author tangkf
	 * @param cls
	 * @param message
	 */
	public void fatal(Class<?> cls,Object message);
	
	/**
	 * 常规日志信息
	 * @author tangkf
	 * @param cls
	 * @param throwable
	 */
	public void info(Class<?> cls,Throwable throwable);
	
	/**
	 * 错误日志信息
	 * @author tangkf
	 * @param cls
	 * @param throwable
	 */
	public void error(Class<?> cls,Throwable throwable);
	
	/**
	 * 警告日志信息
	 * @author tangkf
	 * @param cls
	 * @param throwable
	 */
	public void warn(Class<?> cls,Throwable throwable);
	
	/**
	 * 调试日志信息
	 * @author tangkf
	 * @param cls
	 * @param throwable
	 */
	public void debug(Class<?> cls,Throwable throwable);
	
	/**
	 * 致命日志信息
	 * @author tangkf
	 * @param cls
	 * @param throwable
	 */
	public void fatal(Class<?> cls,Throwable throwable);

	public Logger getLogger();
	
	public void setLogger(String name);

}
