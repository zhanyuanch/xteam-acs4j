/*
 * Copyright (c) 2005-2008 旭鸣软件
 * All rights reserved. 
 */
/*
 * File：AbstractLogger.java
 * History:
 *       2008-7-9: Initially created, tangkf.
 */
package com.xteam.tools.log;

import org.apache.log4j.Logger;

/**
 * @author  tangkf
 *
 */
public abstract class AbsLogger implements ILogger {
	
	private Logger logger	= null;
	
	public Logger getLogger(){
		if(logger==null) logger	= Logger.getLogger("sys");
		if(logger==null) logger	= Logger.getRootLogger();
		return logger;
	}
	
	public void setLogger(String name){
		if(logger==null) logger	= Logger.getLogger(name);
		if(logger==null) logger	= Logger.getLogger("sys");
		if(logger==null) logger	= Logger.getRootLogger();
	}
	
	public Logger getLogger(String name){
		Logger log	= null;
		if(log==null) log	= Logger.getLogger(name);
		if(log==null) log	= Logger.getLogger("sys");
		if(log==null) log	= logger;
		return log;
	}
	
	public AbsLogger(String name){
		this.setLogger(name);
	}
	
	/**
	 * 
	 * @see net.sourceforge.ussdpan.log.ILogger#debug(java.lang.Object)
	 */
	public void debug(Object message) {
		Logger log	= getLogger();
		log.debug(message);
	}

	/**
	 * 
	 * @see net.sourceforge.ussdpan.log.ILogger#error(java.lang.Object)
	 */
	public void error(Object message) {
		Logger log	= getLogger();
		log.error(message);
	}

	/**
	 * 
	 * @see net.sourceforge.ussdpan.log.ILogger#fatal(java.lang.Object)
	 */
	public void fatal(Object message) {
		Logger log	= getLogger();
		log.fatal(message);
	}

	/**
	 * 
	 * @see net.sourceforge.ussdpan.log.ILogger#info(java.lang.Object)
	 */
	public void info(Object message) {
		Logger log	= getLogger();
		log.info(message);
	}

	/**
	 * 
	 * @see net.sourceforge.ussdpan.log.ILogger#warn(java.lang.Object)
	 */
	public void warn(Object message) {
		Logger log	= getLogger();
		log.warn(message);
	}

	/**
	 * 
	 * @see net.sourceforge.ussdpan.log.ILogger#debug(java.lang.String, java.lang.Object)
	 */
	public void debug(String cls, Object message) {
		Logger log	= getLogger();
		log.debug(message);
	}

	/**
	 * 
	 * @see net.sourceforge.ussdpan.log.ILogger#debug(java.lang.Class, java.lang.Object)
	 */
	public void debug(Class<?> cls, Object message) {
		Logger log	= this.getLogger(cls.getName());
		log.debug(message);
	}

	/**
	 * 
	 * @see net.sourceforge.ussdpan.log.ILogger#debug(java.lang.Class, java.lang.Throwable)
	 */
	public void debug(Class<?> cls, Throwable throwable) {
		Logger log	= getLogger(cls.getName());
		log.debug(throwable);
	}

	/**
	 * 
	 * @see net.sourceforge.ussdpan.log.ILogger#error(java.lang.String, java.lang.Object)
	 */
	public void error(String cls, Object message) {
		Logger log	= getLogger(cls);
		log.error(message);
	}

	/**
	 * 
	 * @see net.sourceforge.ussdpan.log.ILogger#error(java.lang.Class, java.lang.Object)
	 */
	public void error(Class<?> cls, Object message) {
		Logger log	= this.getLogger(cls.getName());
		log.error(message);
	}

	/**
	 * 
	 * @see net.sourceforge.ussdpan.log.ILogger#error(java.lang.Class, java.lang.Throwable)
	 */
	public void error(Class<?> cls, Throwable throwable) {
		Logger log	= this.getLogger(cls.getName());
		log.error(throwable);
	}

	/**
	 * 
	 * @see net.sourceforge.ussdpan.log.ILogger#fatal(java.lang.String, java.lang.Object)
	 */
	public void fatal(String cls, Object message) {
		Logger log	= this.getLogger(cls);
		log.fatal(message);
	}

	/**
	 * 
	 * @see net.sourceforge.ussdpan.log.ILogger#fatal(java.lang.Class, java.lang.Object)
	 */
	public void fatal(Class<?> cls, Object message) {
		Logger log	= this.getLogger(cls.getName());
		log.fatal(message);
	}

	/**
	 * 
	 * @see net.sourceforge.ussdpan.log.ILogger#fatal(java.lang.Class, java.lang.Throwable)
	 */
	public void fatal(Class<?> cls, Throwable throwable) {
		Logger log	= this.getLogger(cls.getName());
		log.fatal(throwable);
	}

	/**
	 * 
	 * @see net.sourceforge.ussdpan.log.ILogger#info(java.lang.String, java.lang.Object)
	 */
	public void info(String cls, Object message) {
		Logger log	= this.getLogger(cls);
		log.info(message);
	}

	/**
	 * 
	 * @see net.sourceforge.ussdpan.log.ILogger#info(java.lang.Class, java.lang.Object)
	 */
	public void info(Class<?> cls, Object message) {
		Logger log	= this.getLogger(cls.getName());
		log.info(message);
	}

	/**
	 * 
	 * @see net.sourceforge.ussdpan.log.ILogger#info(java.lang.Class, java.lang.Throwable)
	 */
	public void info(Class<?> cls, Throwable throwable) {
		Logger log	= this.getLogger(cls.getName());
		log.info(throwable);
	}

	/**
	 * 
	 * @see net.sourceforge.ussdpan.log.ILogger#warn(java.lang.String, java.lang.Object)
	 */
	public void warn(String cls, Object message) {
		Logger log	= this.getLogger(cls);
		log.warn(message);
	}

	/**
	 * 
	 * @see net.sourceforge.ussdpan.log.ILogger#warn(java.lang.Class, java.lang.Object)
	 */
	public void warn(Class<?> cls, Object message) {
		Logger log	= this.getLogger(cls.getName());
		log.warn(message);
	}

	/**
	 * 
	 * @see net.sourceforge.ussdpan.log.ILogger#warn(java.lang.Class, java.lang.Throwable)
	 */
	public void warn(Class<?> cls, Throwable throwable) {
		Logger log	= this.getLogger(cls.getName());
		log.warn(throwable);
	}
}
