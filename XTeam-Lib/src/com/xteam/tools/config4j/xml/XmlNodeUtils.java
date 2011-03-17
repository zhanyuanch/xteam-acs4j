/*
 * Copyright (c) 2005-2008 
 * All rights reserved. 
 */
/*
 * File：ConfigUtils.java
 * History:
 *       2008-6-4: Initially created, tangkf.
 */
package com.xteam.tools.config4j.xml;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.dom4j.Element;

/**
 * @author  tangkf
 *
 */
public class XmlNodeUtils {

	public static final String VALUE		= "$value";
	public static final String NODE		= "$node";
	public static final String PROPERTIES	= "$properties";
	public static final String CHILDREN	= "$children";
	
	/**
	 * 解析结点条件字符串
	 * @author  tangkf
	 * @param nodename
	 * @return
	 */
	public static String[] parseNodeStr(String nodename){
		String nodecnd[]	= nodename.split("\\[|\\]|\\=");
		if(nodecnd!=null && nodecnd.length>2){
			return nodecnd;
		}
		return null;
	}
	
	/**
	 * 将 cnd 结点 增加到指定的 src Element 结点下
	 * @author  tangkf
	 * @param src
	 * @param cnd
	 */
	public static Element nodeAdd2Element(Element src,XmlNode node){
		Element elm	= src;
		//====设置结点名和值========
		//elm.setName(node.getNodeName());
		if(node.getNodeValue()!=null)  elm.setText(node.getNodeValue());
		//====设置结点属性======
		Map<String,String> pts	= node.getProperties();
		if(pts!=null&&pts.size()>0){
			Iterator<Entry<String,String>> its	= pts.entrySet().iterator();
			while(its.hasNext()){
				Entry<String,String> ent	= its.next();
				elm.addAttribute(ent.getKey(), ent.getValue());
			}
		}
		
		//====设置子结点================
		List<XmlNode> clist	= node.getChildrenList();
		if(clist!=null && clist.size()>0){
			Iterator<XmlNode> it	= clist.iterator();
			while(it.hasNext()){
				XmlNode child	= it.next();
				nodeAdd2Element(elm.addElement(child.getNodeName()),child);
			}
		}
		
		return elm;
	}
	
	/**
	 * 分解结点路径
	 * @author  tangkf
	 * @param nodeValueStr
	 * @return
	 */
	public static String[] parseValueStr(String nodePath){
		String vstrs[]	= nodePath.split("\\:|@");
		if(vstrs.length>0){
			vstrs = vstrs[0].split("\\.");
			return vstrs;
		}
		return null;
	}
	
	/**
	 * 获取取值关键字
	 * @author  tangkf
	 * @param nodePath
	 * @return
	 */
	public static String parseKeyStr(String nodePath){
		String vstrs[]	= nodePath.split("\\:|@");
		if(vstrs!=null && vstrs.length>1){
			return vstrs[1];
		}
		return null;
	}
	
	
	/**
	 * 自定义路径 
	 * 例子：
	 * ROOT.NODE.CNODE@att1       末结点路径下的属性值 		返回 String
	 * 
	 * ROOT.NODE.CNODE:$children   末结点的所有孩子列表 	返回 List
	 * ROOT.NODE.CNODE:$value      末结点的TEXT值 			返回 String
	 * ROOT.NODE.CNODE:$node       末结点的结点 			返回 XmlNode
	 * ROOT.NODE.CNODE:$properties 末结点的属性表(MAP) 		返回 Map 
	 * 
	 * 每个结点在均支持[]括号内的条件：
	 * ROOT.NODE[name=bb].CNODE[name=ccc]@att1  安条件获取属性值	返回 String
	 * 
	 * @author  tangkf
	 * @param path
	 * @return
	 */
	public static Object getValueByPath(XmlNode configNode,String path){
		Object rtn	= null;
		if(path==null || "".equals(path)){
			System.err.println("路径无效：--> "+path);
		}
		
		if(configNode==null){
			System.err.println("结点无效：--> "+path);
		}
		
		XmlNode last	= getLastNode(configNode, path);	//获取路径中最后一个结点
		String key		= parseKeyStr(path);				//获取路径中最后的取值关键字 
		
		rtn	= getValueByKey(last, key);
		return rtn;
	}
	
	/**
	 * 获取最后一个结点的值
	 * @author  tangkf
	 * @param configNode
	 * @param key
	 * @return
	 */
	public static Object getValueByKey(XmlNode configNode,String key){
		Object rtn	= null;
		if(configNode==null) return rtn;				//结点为空
		
		if(key==null || "".equals(key)) key = NODE; 	//关键字为空
		
		if(key.indexOf("$")!=-1){
		//$符号开头表示变量【$value:取text,$node:取结点,$properties:取属性表,$children:取孩子表】
			if(VALUE.equalsIgnoreCase(key)){
				rtn	= configNode.getNodeValue();
			}else if(NODE.equalsIgnoreCase(key)){
				rtn	= configNode;
			}else if(PROPERTIES.equalsIgnoreCase(key)){
				rtn = configNode.getProperties();
			}else if(CHILDREN.equalsIgnoreCase(key)){
				rtn	= configNode.getChildrenList();
			}
		}else{
		//表示直接去属性值，即 properties 中的某个值
			rtn	= configNode.getProperty(key);
		}
		return rtn;
	}
	
	/**
	 * 获取一个结点指定路径的最后一个结点
	 * @author  tangkf
	 * @param configNode
	 * @param path
	 * @return
	 */
	public static XmlNode getLastNode(XmlNode configNode,String path){
		XmlNode rtn	= configNode;
		String[] ps	= parseValueStr(path);
		if(ps!=null){
			for(int i=1;i<ps.length;i++){
				if(rtn!=null){
					String[] cps	= XmlNodeUtils.parseNodeStr(ps[i]);
					if(cps!=null){
					//----获取指定条件的儿子结点
						rtn	= XmlNodeUtils.getOneNodeByAttribute(rtn,cps[0], cps[1], cps[2]);
					}else{
					//----没有条件获取默认儿子结点
						rtn	= XmlNodeUtils.getOneChildNode(rtn,ps[i]);
					}
				}
			}
		}
		return rtn;
	}
	
	/**
	 * 获取一个儿子，如果有多个获取第一个
	 * @author  tangkf
	 * @param cfgnode
	 * @param name
	 * @return
	 */
	public static XmlNode getOneChildNode(XmlNode cfgnode,String name){
		XmlNode node	= null;
		for(XmlNode cnode :cfgnode.getChildrenList()){
			if(name.equalsIgnoreCase(cnode.getNodeName())){
				node	= cnode;
				break;
			}
		}
		return node;
	}
	
	
	/**
	 * 获取 指定 名称和属性值的儿子结点
	 * @author  tangkf
	 * @param nodename 结点名称
	 * @param attname  属性名称
	 * @param attvalue 属性值
	 * @return
	 */
	public static XmlNode getOneNodeByAttribute(XmlNode configNode,String nodename,String attname,String attvalue){
		List<XmlNode> nlist	= configNode.getChildrenByName(nodename);
		XmlNode rtn	= null;
		if(nlist!=null && nlist.size()>0){
			for(XmlNode cnode:nlist){
				if(attvalue.equals(cnode.getProperty(attname))){
					rtn	= cnode;
					break;
				}
			}
		}
		return rtn;
	}
}
