/*
 * 创建时间：2011-2-25 上午10:46:30
 * 工程名称：CommonTools
 * 文   件  名：com.xuming.tools.MemoryMonitor.java
 * Author:Leo
 * 
 */
package com.xteam.tools;

import java.util.Timer;
import java.util.TimerTask;


/**
 * @author Leo
 *
 */
public class MemoryMonitor {
	private static Timer t = new Timer();
	private MemoryMonitor(){
	}
	public static void Monitoring(){
		TimerTask task = new TimerTask(){
			@Override
			public void run() {
				System.out.println("********************"+CommonUtils.getStDateTime()+" JVM 内存使用情况********************");
				System.out.println("* JVM最大内存："+Runtime.getRuntime().maxMemory()/1024/1024+" MB");
				System.out.println("* JVM已使用内存："+Runtime.getRuntime().totalMemory()/1024/1024+" MB");
				System.out.println("* JVM可用内存："+Runtime.getRuntime().freeMemory()/1024/1024+" MB");
				System.out.println("**************************************************************************");
			}
		};
		t.schedule(task, 0,10*60*1000);//每隔10分钟扫描一次内存使用情况
	}
	public static void main(String[] args) {
		MemoryMonitor.Monitoring();
	}
}
