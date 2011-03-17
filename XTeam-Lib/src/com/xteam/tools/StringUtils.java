/*
 * Copyright (c) 2005-2008 旭鸣软件
 * All rights reserved. 
 */
/*
 * File：StringUtils.java
 * History:
 *       2009-2-18: Initially created, 汤垲峰.
 */
package com.xteam.tools;

import java.util.UUID;

/**
 * <p>
 * 字符串操作工具类
 * </p>
 * 
 * @author 汤垲峰
 * 
 */
public class StringUtils {

	public static final String NULL = "null";

	public static final String SPAC = "";

	public static final String ZEROCHAR = String.valueOf((char) 0);

	public static String trim(String src, String trim) {
		if (src.startsWith(trim)) {
			src = src.substring(src.indexOf(trim) + 1);
		}
		if (src.endsWith(trim)) {
			src = src.substring(0, src.indexOf(trim));
		}
		return src;
	}

	/**
	 * <P>
	 * 将字符串按照BYTE方式输出到屏幕
	 * </P>
	 * 
	 * @author 汤垲峰 2009-2-19
	 * @param x
	 */
	public static void printBytes(String x) {
		byte[] xbt = x.getBytes();
		for (int i = 0; i < xbt.length; i++) {
			System.out.print(xbt[i] + ",");
		}
	}

	/**
	 * <P>
	 * 将字符串总 ASC值为 0 的字符替换为空
	 * </P>
	 * 
	 * @author 汤垲峰 2009-2-18
	 * @param src
	 * @return
	 */
	public static String replaceAllZeroAsc(String src) {
		if (CommonUtils.isEmpty(src))
			src = "";
		String des = src.replaceAll(ZEROCHAR, SPAC);
		return des;
	}

	/**
	 * <P>
	 * 判断输入的字符串是否为 "null" 字符串
	 * </P>
	 * 
	 * @author 汤垲峰 2009-2-18
	 * @param src
	 * @return
	 */
	public static boolean isNullString(String src) {
		if (NULL.equalsIgnoreCase(src)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 把数组转换成用单引号应用的字符串
	 * 
	 * @param sources
	 * @return
	 */
	public static String convertArray2String(String[] sources) {
		String result = "";
		if (sources == null || sources.length == 0) {
			return null;
		}
		for (String id : sources) {
			result += "'" + id + "',";
		}
		result = result.substring(0, result.length() - 1);
		return result;
	}

	/**
	 * 生成32的UUID字符串
	 * 
	 * @return
	 */
	public static String generate32UUID() {
		String s = UUID.randomUUID().toString();
		return s.replace("-", "");
	}

	/**
	 * 生成16的UUID字符串
	 * 
	 * @return
	 */
	public static String generate16UUID() {
		return generate32UUID().substring(0, 16);
	}

	/**
	 * 生成对应长度的 ch 字符串
	 * 
	 * @param n
	 * @return String
	 * @author:Leo
	 */
	public static String createChar(int n, String ch) {
		if (n < 1) {
			return null;
		}
		String ret = "";
		for (int i = 0; i < n; i++) {
			ret += ch;
		}
		return ret;
	}

	/**
	 * 把逗号连成的字符串转换成用单引号应用的字符串
	 * 
	 * @param sources
	 * @return
	 */
	public static String convertToString(String sources) {
		String result = "";
		if (sources == null || "".equals(sources)) {
			return null;
		}
		String[] strs = sources.split(",");
		for (String id : strs) {
			result += "'" + id + "',";
		}
		result = result.substring(0, result.length() - 1);
		return result;
	}

	public static Long convertToLong(String str) {
		return convertToLong(str, 0L);
	}

	public static Long convertToLong(String str, long defValue) {
		if (!str.matches("[0-9]+[.]?[0-9]+")) {
			return defValue;
		}
		String[] temp = str.split("\\.");
		return Long.parseLong(temp[0]);
	}

	public static void main(String[] args) {
		Long a = convertToLong("s3.99", 0L);
		System.out.println(a);
		// String str = "11.2" ;
		// boolean flag = str.matches("[0-9]+[.]?[0-9]+");
		// System.out.println(flag);
	}
}
