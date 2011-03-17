/*
 * Copyright (c) 2005-2008 成均科技
 * All rights reserved. 
 */
/*
 * File：DefaultLoger.java
 * History:
 *       2007-12-19: Initially created, tangkf.
 */
package com.xteam.tools.log;

/**
 * 实现了日志接口的一个默认的类,用于处理日志信息,该类由LogerManager 负责创建,接管和输出
 * @author tangkf
 */
public class ConsoleLogger extends AbsLogger implements ILogger {
	public ConsoleLogger(){
		super("con");
	}
}
