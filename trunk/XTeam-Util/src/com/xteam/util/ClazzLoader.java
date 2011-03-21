/**
 * Copyright (c) 2011-2012 XTEAM
 * All rights reserved.
 */
/**
 * File：ClazzLoader.java
 * History:
 *         2011-3-21: Initially created, 袁孝均.
 */
package com.xteam.util;

/**
 * 类加载器。
 * @author 袁孝均
 */
public final class ClazzLoader {
	/**
	 * 根据指定的类型加载一个类。
	 * @author 袁孝均 2011-3-21
	 * @param c
	 * @return
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	public static Object loadClassByType(Class<?> c) throws InstantiationException, IllegalAccessException {
		Object o = c.newInstance();
		return o;
	}
	
	/**
	 * 根据类的完全限定名加载一个类。
	 * @author 袁孝均 2011-3-21
	 * @param name
	 * @return
	 * @throws ClassNotFoundException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws NullPointerException
	 */
	public static Object loadClassByName(String name) throws ClassNotFoundException, InstantiationException, IllegalAccessException, NullPointerException {
		Class<?> c = null;
		if (c == null) {
			c = Thread.currentThread().getContextClassLoader().loadClass(name);
			System.out.println("Thread.currentThread().getContextClassLoader()\r\n加载类：" + name);
		}
		if (c == null) {
			c = ClassLoader.getSystemClassLoader().loadClass(name);
			System.out.println("ClassLoader.getSystemClassLoader()\r\n加载类：" + name);
		}
		if (c == null) {
			c = Class.forName(name);
			System.out.println("Class.forName()\r\n加载类：" + name);
		}
		if (c == null) {
			System.out.println("加载类失败：" + name);
			throw new NullPointerException();
		}
		Object o = c.newInstance();
		return o;
	}
}
