/*
 * 创建时间：2010-11-12 下午03:30:12
 * 工程名称：CommonTools
 * 文   件  名：com.xuming.tools.IpAddress.java
 * Author:Leo
 * 
 */
package com.xteam.tools;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Leo
 * 
 */
public class IpAddress {
	/**
	 * 多级转发获得用户IP.
	 * 如果通过了多级反向代理的话，X-Forwarded-For的值并不止一个，而是一串Ｉｐ值；
	 * 取X-Forwarded-For中第一个非unknown的有效IP字符串。
	 * 如：
	 * X-Forwarded-For：192.168.1.110, 192.168.1.120, 192.168.1.130, 192.168.1.100,
	 * 用户真实IP为： 192.168.1.110
	 * @param request
	 * @return String
	 * @author:Leo
	 */
	public static String getIpAddr(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		if(ip.indexOf(',')!=-1){
			ip = ip.split(",")[0].trim();
		}
		return ip;
	}

}
