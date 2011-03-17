/**
 * Copyright (c) 2005-2009 旭鸣软件
 * All rights reserved.
 */
/**
 * File：MDFiveEncrypt.java
 * History:
 *         2009-10-30: Initially created, 袁孝均.
 */
package com.xteam.tools;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * MD5加密
 * 
 * @author 袁孝均
 */
public class MDFiveEncrypt {
	/**
	 * 十六进制小写形式
	 */
	private static final String[] L_HEX_DIGITS = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c",
			"d", "e", "f" };
	/**
	 * 十六进制大写形式
	 */
	private static final String[] U_HEX_DIGITS = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "A", "B", "C",
			"D", "E", "F" };

	/**
	 * 用MD5算法加密指定字符串
	 * 
	 * @author 袁孝均 2009-10-30
	 * @param s
	 * @return
	 */
	public static String getMD5Encode(String s) {
		return getMD5Encode(s, true);
	}

	/**
	 * 用MD5算法加密指定字符串
	 * 
	 * @author 袁孝均 2009-10-30
	 * @param s
	 * @param lowerCase
	 * @return
	 */
	public static String getMD5Encode(String s, boolean lowerCase) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			return byte2HexString(md.digest(s.getBytes()), lowerCase);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 字节转换成十六进制字符串
	 * 
	 * @author 袁孝均 2009-10-30
	 * @param b
	 * @param lowerCase
	 * @return
	 */
	private static String byte2HexString(byte b, boolean lowerCase) {
		int n = b;
		if (n < 0) {
			n += 256;
		}
		int hp = n / 16;
		int lp = n % 16;
		if (lowerCase) {
			return L_HEX_DIGITS[hp] + L_HEX_DIGITS[lp];
		}
		return U_HEX_DIGITS[hp] + U_HEX_DIGITS[lp];
	}

	/**
	 * 字节转换成十六进制字符串
	 * 
	 * @author 袁孝均 2009-10-30
	 * @param bs
	 * @param lowerCase
	 * @return
	 */
	private static String byte2HexString(byte[] bs, boolean lowerCase) {
		StringBuffer sb = new StringBuffer();
		for (byte b : bs) {
			sb.append(byte2HexString(b, lowerCase));
		}
		return sb.toString();
	}

	/**
	 * 测试
	 * 
	 * @author 袁孝均 2009-10-30
	 * @param args
	 */
	public static void main(String[] args) {
		System.err.println(getMD5Encode("xuming"));
	}
}
