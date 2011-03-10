/*
 * Copyright (c) 2005-2008 旭鸣软件
 * All rights reserved. 
 */
/*
 * File：AppConfig.java
 * History:
 *       2008-12-18: Initially created, 汤垲峰.
 */
package com.xteam.asc4j.config;

import java.util.List;

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
	 * 返回整个游戏节点
	 * @author 汤垲峰 2009-11-16
	 * @param gameType
	 * @return
	 */
	public static ConfigNode getNode(String gameType){
		ConfigNode cn	= root.getOneChildByName(gameType);
		return cn;
	}
	
	/**
	 * 返回游戏的孩子节点表
	 * @author 汤垲峰 2009-11-16
	 * @param gameType
	 * @param name
	 * @return
	 */
	public static List<ConfigNode> getNodeChildren(String gameType,String name){
		ConfigNode cn	= root.getOneChildByName(gameType);
		return cn.getChildrenByName(name);
	}
	
	/**
	 * 返回指定游戏类型的，指定属性的值
	 * @author 汤垲峰 2009-11-11
	 * @param gameType
	 * @param keyName
	 * @return
	 */
	public static String getNodeProperty(String gameType,String keyName){
		ConfigNode cn	= root.getOneChildByName(gameType);
		return cn.getProperty(keyName);
	}
	
	/**
	 * 返回整形数字
	 * @author 汤垲峰 2009-11-25
	 * @param nodeName
	 * @param key
	 * @return
	 */
	public static int getNodePropertyInt(String nodeName,String key){
		String intvalue	= getNodeProperty(nodeName,key);
		return CommonUtils.parseInt(intvalue);
	}
	
	
	/**
	 * 返回整形属性
	 * @author 汤垲峰 2009-12-14
	 * @param gameType
	 * @param keyName
	 * @return
	 */
	public static String getProperty(String gameType,String keyName){
		return getNodeProperty(gameType,keyName);
	}
	
	/**
	 * 返回整形属性
	 * @author 汤垲峰 2009-12-14
	 * @param gameType
	 * @param keyName
	 * @return
	 */
	public static int getIntProperty(String gameType,String keyName){
		return CommonUtils.parseInt(getNodeProperty(gameType,keyName));
	}
	
	/**
	 * 返回浮点型属性
	 * @author 汤垲峰 2009-12-14
	 * @param gameType
	 * @param keyName
	 * @return
	 */
	public static double getDoubleProperty(String gameType,String keyName){
		return CommonUtils.parseFloat(getNodeProperty(gameType,keyName));
	}
	
	/**
	 * 返回布尔型
	 * @author 汤垲峰 2009-11-25
	 * @param nodeName
	 * @param key
	 * @return
	 */
	public static boolean getNodePropertyBoolean(String nodeName,String key){
		String bvalue	= getNodeProperty(nodeName,key);
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
	 * 返回指定游戏类型的制定字段值
	 * @author 汤垲峰 2009-11-11
	 * @param gameType
	 * @param nodeName
	 * @return
	 */
	public static String getNodeValue(String gameType,String nodeName){
		ConfigNode cn	= root.getOneChildByName(gameType);
		return cn.getOneChildByName(nodeName).getNodeValue();
	}
	
	/**
	 * <P>
	 * 初始化配置信息
	 * </P>
	 * @author 汤垲峰 2009-2-20
	 */
	public static void initialize(){
		initialize("config/sys-config.xml");
	}
	
	public static void main(String[] args){
		initialize();
		//System.err.println(SysConfig.getIntProperty("sys", "xxx"));
	}
}
