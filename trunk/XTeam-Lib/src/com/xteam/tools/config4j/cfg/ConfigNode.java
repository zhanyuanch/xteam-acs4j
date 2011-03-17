/*
 * Copyright (c) 2005-2008 
 * All rights reserved. 
 */
/*
 * File：ConfigModel.java
 * History:
 *       2008-6-4: Initially created, tangkf.
 */
package com.xteam.tools.config4j.cfg;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

/**
 * @author  tangkf
 * 配置文件节点模型
 */
public class ConfigNode {
	
	private ConfigNode	parent	= null;
	
	private Element element;

	private List<ConfigNode> childrenList;
	
	/**
	 * 节点属性表 <p a="cc" b="dd"></p> 中 a=cc b=dd 就保存到该MAP中
	 */
	private Map<String, String> properties;
	
	/**
	 * 节点名称 比如 <p>value</p> 中间 p 就是该值
	 */
	private String nodeName;
	
	/**
	 * 节点值 比如 <p>value</p> 中间 value 就是该值
	 */
	private String nodeValue;
	
	public ConfigNode(){
		this.childrenList	= new ArrayList<ConfigNode>();
		this.properties		= new HashMap<String, String>();
	}
	
	/**
	 * 解析为一个字符串
	 * @author  tangkf
	 * @return
	 */
	public String asXmlText(){
		String xmlstr	= "";
		xmlstr	= this.getElement().asXML();
		return xmlstr;
	}
	
	/**
	 * 在该结点下增加一个Element 结点
	 * @author  tangkf
	 * @param elm
	 */
	public void addElement(Element elm){
		if(this.getElement()!=null) this.getElement().add(elm);
	}
	
	/**
	 * 将一个结点字符串增加到该结点下
	 * @author  tangkf
	 * @param nodeString
	 * @throws DocumentException
	 */
	public void addNodeString(String nodeString) throws DocumentException{
		Document tdoc	= DocumentHelper.parseText(nodeString);
		Element tmp		= tdoc.getRootElement();
		this.addElement(tmp);
	}
	
	/**
	 * 删除一个结点
	 * @author  tangkf
	 * @param nodeName
	 */
	public void delNode(Element elem){
		removeElement(elem);
		String name	= elem.getName();
		Iterator<ConfigNode> it	= this.getChildrenByName(name).iterator();
		while(it.hasNext()){
			ConfigNode cn	= it.next();
			if(cn.getNodeName().equalsIgnoreCase(name)){
				it.remove();
				break;
			}
		}
	}
	
	/**
	 * 删除结点
	 * @author  tangkf
	 * @param ename
	 */
	public void delNode(String ename){
		removeElement(ename);
		Iterator<ConfigNode> it	= this.getChildrenByName(ename).iterator();
		while(it.hasNext()){
			ConfigNode cn	= it.next();
			if(cn.getNodeName().equalsIgnoreCase(ename)) it.remove();
		}
	}
	
	/**
	 * 移除一个节点下制定名称的节点
	 * @author 汤垲峰 2009-9-21
	 * @param name
	 */
	public void removeElement(String name){
		List<ConfigNode> cns	= this.getChildrenByName(name);
		if(cns!=null){
			for(ConfigNode cn:cns){
				this.getElement().remove(cn.getElement());
			}
		}
	}
	
	/**
	 * <P>
	 * 移除一个节点
	 * </P>
	 * @author 汤垲峰 2009-9-21
	 * @param elem
	 */
	public void removeElement(Element elem){
		if(this.getElement()!=null){
			this.getElement().remove(elem);
		}
	}
	
	/**
	 * <P>
	 * 清空该节点的所有孩子及其子节点
	 * </P>
	 * @author 汤垲峰 2009-9-21
	 */
	public void clearContent(){
		this.getChildrenList().clear();
		this.getElement().clearContent();
	}

	
	/**
	 * 取父亲结点
	 * @author  tangkf
	 * @return
	 */
	public ConfigNode getParent(){
		return parent;
	}

	/**
	 * @return 属性element的值.
	 */
	public Element getElement() {
		return this.element;
	}

	/**
	 * @param element 属性element.
	 */
	public void setElement(Element element) {
		this.element = element;
	}

	/**
	 * 设置父亲结点
	 * @author  tangkf
	 * @param cn
	 */
	public void setParent(ConfigNode cn){
		this.parent	= cn;
	}
	
	/**
	 * 自定义路径 
	 * 例子：
	 * ROOT.NODE.CNODE@att1       末结点路径下的属性值 		返回 String
	 * 
	 * ROOT.NODE.CNODE:$children   末结点的所有孩子列表 	返回 List
	 * ROOT.NODE.CNODE:$value      末结点的TEXT值 			返回 String
	 * ROOT.NODE.CNODE:$node       末结点的结点 			返回 ConfigNode
	 * ROOT.NODE.CNODE:$properties 末结点的属性表(MAP) 		返回 Map 
	 * 
	 * 每个结点在均支持[]括号内的条件：
	 * ROOT.NODE[name=bb].CNODE[name=ccc]@att1  安条件获取属性值	返回 String
	 * 
	 * @author  tangkf
	 * @param path
	 * @return
	 */
	public Object getValueByPath(String path){
		Object rtn	= null;
		rtn			= ConfigUtils.getValueByPath(this,path);
		return rtn;
	}

	
	/**
	 * @return 属性childrenList的值.
	 */
	public List<ConfigNode> getChildrenList() {
		return this.childrenList;
	}
	
	/**
	 * 获取制定名字的儿子结点表
	 * @author  tangkf
	 * @param name
	 * @return
	 */
	public List<ConfigNode> getChildrenByName(String name){
		List<ConfigNode> clst	= new ArrayList<ConfigNode>();
		for(int i=0;i<this.childrenList.size();i++){
			if(name.equalsIgnoreCase(this.childrenList.get(i).getNodeName())){
				clst.add(this.childrenList.get(i));
			}
		}
		return clst;
	}
	
	/**
	 * 获取单个结点对象
	 * @author  tangkf
	 * @param name
	 * @return
	 */
	public ConfigNode getOneChildByName(String name){
		ConfigNode cn	= null;
		List<ConfigNode> cns	= this.getChildrenByName(name);
		if(cns!=null && cns.size()>0){
			cn	= cns.get(0);
		}
		return cn;
	}

	/**
	 * @param childrenList 属性childrenList.
	 */
	public void setChildrenList(List<ConfigNode> childrenList) {
		this.childrenList = childrenList;
	}
	
	/**
	 * 增加一个儿子
	 * @author  tangkf
	 * @param child
	 */
	public void addChild(ConfigNode child){
		this.childrenList.add(child);
		child.setParent(this);
	}

	/**
	 * @return 属性nodeName的值.
	 */
	public String getNodeName() {
		return this.nodeName;
	}

	/**
	 * @param nodeName 属性nodeName.
	 */
	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
		if(this.getElement()!=null){
			this.getElement().setName(nodeName);
		}
	}

	/**
	 * @return 属性nodeValue的值.
	 */
	public String getNodeValue() {
		return this.nodeValue;
	}

	/**
	 * @param nodeValue 属性nodeValue.
	 */
	public void setNodeValue(String nodeValue) {
		this.nodeValue = nodeValue;
		if(this.getElement()!=null){
			this.getElement().setText(nodeValue);
		}
	}

	/**
	 * @return 属性properties的值.
	 */
	public Map<String, String> getProperties() {
		return this.properties;
	}
	
	/**
	 * 获取属性
	 * @author  tangkf
	 * @param key
	 * @return
	 */
	public String getProperty(String key){
		return this.properties.get(key);
	}

	/**
	 * @param properties 属性properties.
	 */
	public void setProperties(Map<String, String> properties) {
		this.properties = properties;
	}
	
	/**
	 * @author  tangkf
	 * @param key
	 * @param value
	 */
	public void addProperty(String key,String value){
		this.properties.put(key, value);
		if(this.getElement()!=null){
			this.getElement().addAttribute(key, value);
		}
	}
	
	/**
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "["+this.getNodeName()+"="+this.getNodeValue()+"]";
	}
}
