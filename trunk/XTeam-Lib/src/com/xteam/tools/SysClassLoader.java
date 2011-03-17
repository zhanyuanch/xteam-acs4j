/*
 * Copyright (c) 2005-2008 成均科技
 * All rights reserved. 
 *
 * File：PurviewClassLoader.java
 * History:
 *       2007-12-29: Initially created, tangkf.
 */
package com.xteam.tools;

/**
 * 实现一个简单的类装载器
 * 
 * @author tangkf
 */
public class SysClassLoader {

	/**
	 * 测试方法
	 * 
	 * @author tangkf
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		System.out.println("TEST");
		Class.forName("cn.com.chengjun.purview.log.DefaultLoger");
		System.out.println("TEST");
		loadClassByName("cn.com.chengjun.purview.log.DefaultLoger1");
	}

	/**
	 * 根据指定的类路径装载一个类
	 * 
	 * @author tangkf
	 * @param name
	 * @return
	 */
	public static Class<?> loadClassByName(String name) {
		Class<?> c = null;
		try {

			if (c == null) {
				c = Class.forName(name);
				// System.out.println("Class.forName(name)--->name="+name+"\r\n装载类:Class--->"+c);
			}
			if (c == null) {
				try {
					c = ClassLoader.getSystemClassLoader().loadClass(name);
				} catch (Exception e) {
					e.printStackTrace();
				}
				// System.out.println("ClassLoader.getSystemClassLoader().loadClass(name)--->name="+name+"\r\n装载类:Class--->"+c);
			}
			if (c == null) {
				try {
					c = Thread.currentThread().getContextClassLoader().loadClass(name);
				} catch (Exception e) {
					e.printStackTrace();
				}
				// System.out.println("Thread.currentThread().getContextClassLoader().loadClass(name)--->name="+name+"\r\n装载类:Class--->"+c);
			}
		} catch (ClassNotFoundException e) {
			// System.out.println("\""+name+"\" 未找到!\r\n初始化类是出现错误,可能是配置文件不正确引起的，请检查!");
			e.printStackTrace();
			c = null;
		}
		return c;
	}

	/**
	 * 根据指定类型名 创建 一个新的对象.
	 * 
	 * @author tangkf
	 * @param name
	 * @return
	 * @throws Exception
	 */
	public static Object newObjectByName(String name) throws Exception {
		Object o = null;
		try {
			o = loadClassByName(name).newInstance();
		} catch (InstantiationException e) {
			throw e;
		} catch (IllegalAccessException e) {
			throw e;
		}
		return o;
	}

	/**
	 * 根据指定 类型 创建 一个新的对象
	 * 
	 * @author tangkf
	 * @param type
	 *            指定类型
	 * @return
	 */
	public static Object newObjectByClass(Class<?> type) {
		Object o = null;
		try {
			o = type.newInstance();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return o;
	}
}
