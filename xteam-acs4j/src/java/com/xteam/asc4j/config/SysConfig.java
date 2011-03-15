/*
 * Copyright (c) 2005-2008 旭鸣软件
 * All rights reserved. 
 */
/*
 * File：SysConfig.java
 * History:
 *       2011-3-10: Initially created, 汤垲峰.
 */
package com.xteam.asc4j.config;

import com.tangkf.utils.CommonUtils;
import com.toms.config.ConfigBuilder;
import com.toms.config.ConfigNode;

/**
 * <p>
 * 配置信息读取
 * </p>
 * @author 汤垲峰
 */
public class SysConfig {
	
	private static ConfigNode root;

	/**
	 * <P>
	 * 初始化配置信息
	 * </P>
	 * @author 汤垲峰 2009-2-20
	 * @param cfgpath
	 */
	public static void initialize(String cfgpath){
		try {
			root = new ConfigBuilder(cfgpath).getRoot();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 返回根节点下的nodeName 节点，如果节点名重复，将返回第一个节点
	 * @author 汤垲峰 2009-11-16
	 * @param nodeName
	 * @return
	 */
	public static ConfigNode getNode(String nodeName){
		ConfigNode cn	= root.getOneChildByName(nodeName);
		return cn;
	}
	
	/**
	 * 返回根节点下 nodeName 节点的 key 属性值
	 * @author 汤垲峰 2009-11-11
	 * @param nodeName
	 * @param key
	 * @return
	 */
	public static String getProperty(String nodeName,String key){
		ConfigNode cn	= root.getOneChildByName(nodeName);
		return cn.getProperty(key);
	}
	
	/**
	 * 返回根节点下 nodeName 节点的 key 属性的值，并转换为整形
	 * @author 汤垲峰 2009-11-25
	 * @param nodeName
	 * @param key
	 * @return
	 */
	public static int getProperty2Int(String nodeName,String key){
		String intvalue	= getProperty(nodeName,key);
		return CommonUtils.parseInt(intvalue);
	}
	
	/**
	 * 返回根节点下 nodeName 节点的 key 属性的值，并转换为浮点型
	 * @author 汤垲峰 2009-12-14
	 * @param nodeName
	 * @param key
	 * @return
	 */
	public static double getProperty2Double(String nodeName,String key){
		return CommonUtils.parseFloat(getProperty(nodeName,key));
	}
	
	/**
	 * 返回根节点下 nodeName 节点的 key 属性的值，并转换为boolean
	 * @author 汤垲峰 2009-11-25
	 * @param nodeName
	 * @param key
	 * @return
	 */
	public static boolean getProperty2Boolean(String nodeName,String key){
		String bvalue	= getProperty(nodeName,key);
		return CommonUtils.parseBoolean(bvalue);
	}
	
	/**
	 * 返回指定的节点序列下的属性
	 * @author 汤垲峰 2009-11-12
	 * @param names
	 * @param key
	 * @return
	 */
	public static String getProperty(String[] names,String key){
		ConfigNode cn	= null;
		for(int i=0;i<names.length;i++){
			if(cn==null){
				cn	= root.getOneChildByName(names[i]);
			}else{
				cn	= cn.getOneChildByName(names[i]);
			}
		}
		return cn.getProperty(key);
	}
	
	/**
	 * 返回根节点下 nodeName 节点下 childNodeName 节点的节点值
	 * @author 汤垲峰 2009-11-11
	 * @param nodeName
	 * @param childNodeName
	 * @return
	 */
	public static String getNodeValue(String nodeName,String childNodeName){
		ConfigNode cn	= root.getOneChildByName(nodeName);
		return cn.getOneChildByName(childNodeName).getNodeValue();
	}
	
	/**
	 * <P>
	 * 初始化配置信息
	 * </P>
	 * @author 汤垲峰 2009-2-20
	 */
	public static void initialize(){
		initialize("/asc4j-config.xml");
	}
	
	public static void main(String[] args){
		initialize();
		System.err.println(SysConfig.getNode("session-factory-class").getNodeValue());
	}
}
