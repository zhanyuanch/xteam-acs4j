/*
 * Copyright (c) 2005-2008 
 * All rights reserved. 
 */
/*
 * File：ConfigBuilder.java
 * History:
 *       2008-6-4: Initially created, tangkf.
 */
package com.xteam.tools.config4j.cfg;

import java.io.File;
import java.net.URL;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.Element;

/**
 * @author tangkf
 * 
 */
public class ConfigBuilder {

	private File file;

	private ConfigNode root;

	private Document doc;

	private Element rootElement;
	
	public static void main(String[] args) throws Exception{
		ConfigBuilder cb	= new ConfigBuilder("config/db-config.xml");
		
		/**
		 * 这个是 getValueByPath(path) 的注释
		 * 
		 * 自定义路径 
		 * 例子：
		 * ROOT.NODE.CNODE@att1       末结点路径下的属性值 		返回 String
		 * 
		 * ROOT.NODE.CNODE:$children   末结点的所有孩子列表 	返回 List<ConfigNode>
		 * ROOT.NODE.CNODE:$value      末结点的TEXT值 			返回 String
		 * ROOT.NODE.CNODE:$node       末结点的结点 			返回 ConfigNode
		 * ROOT.NODE.CNODE:$properties 末结点的属性表(MAP) 		返回 Map<String,String>
		 * 
		 * 每个结点在均支持[]括号内的条件：
		 * ROOT.NODE[name=bb].CNODE[name=ccc]@att1  安条件获取属性值	返回 String
		 * 
		 * @author  tangkf
		 * @param path
		 * @return
		 */
		
		try {
			cb.initinalize();
			
			System.out.println(cb.getRoot());
			
			//--获取 ccc 结点自己 
			ConfigNode obj1	= (ConfigNode)cb.getRoot().getValueByPath("servers.dbserver.ccc:$node");
			
			//--获取ccc 结点的 text 值
			String   obj2	= cb.getRoot().getValueByPath("servers.dbserver.ccc:$value").toString();
			
			//--获取ccc 结点的 attributes 值 
			Map<?, ?>   obj3		= (Map<?, ?>)cb.getRoot().getValueByPath("servers.dbserver.ccc:$properties");
			
			//--获取dbserver 结点的 孩子结点表 List<ConfigNode>
			List<?>  obj4		= (List<?>)cb.getRoot().getValueByPath("servers.dbserver:$children");
			
			//--获取结点 property[name=isdefault] 中name 的值，
			//--当存在多个 property 结点时 取 其属性中 name=isdefault 的结点，当然这个返回值如果存在哪么就是 isdefault
			String  obj5	= cb.getRoot().getValueByPath("servers.dbserver.property[name=isdefault]:name").toString();
			
			System.out.println(obj1+"\r\n"+obj2+"\r\n"+obj3+"\r\n"+obj4+"\r\n"+obj5);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 将文档对象保存
	 * @author  tangkf
	 */
	public void save(String encoding){
		XmlUtils.writeDocToFile(this.doc, this.file, encoding);
	}
	
	
	/**
	 * 通过一段xml 文本，和该文本的编码方式自动解析为一个ConfigBuilder 对象
	 * @param xmlTxt
	 * @param encoding
	 */
	public ConfigBuilder(String xmlTxt,String encoding){
		if(root==null) this.root	= new ConfigNode();
		if(doc==null) this.doc		= XmlUtils.getDocumentByString(xmlTxt, encoding);
		if(rootElement==null) rootElement = doc.getRootElement();
		this.readConfig(root, rootElement);
	}

	/**
	 * 文件路径
	 * @param filePath
	 * @throws Exception 
	 */
	public ConfigBuilder(String filePath) throws Exception {
		this.file = initFile(filePath);
		this.initinalize();
	}

	public ConfigBuilder(File file) throws Exception {
		this.file = file;
		this.initinalize();
	}

	public void reload(String filePath) throws Exception {
		this.file = initFile(filePath);
		this.initinalize();
	}

	public void reload(File file) throws Exception {
		this.file = file;
		this.initinalize();
	}

	public void initinalize() throws Exception {
		if (file == null) {
			throw new Exception("找不到配置文件路径！");
		}
		buildConfig();
	}

	private File initFile(String filepath) {
		File file = new File(filepath);
		URL url = this.getClass().getResource(filepath);
		if (!file.exists()) {
			file = new File(url.getPath());
		}
		if (!file.exists()) {
			System.err.println("对不起,指定两个路径下不存在配置文件:" + "\r\n--->"
					+ url.getPath() + "\r\n--->" + file.getPath());
		}
		this.root	= null;
		this.doc	= null;
		return file;
	}

	private void buildConfig() {
		if(root==null) this.root	= new ConfigNode();
		if(doc==null) doc = XmlUtils.getDocument(this.file);
		if(rootElement==null) rootElement = doc.getRootElement();
		this.readConfig(root, rootElement);
	}
	
	/**
	 * 重新读取xml文件内容
	 * @author  tangkf
	 */
	public void reload(){
		try {
			initinalize();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 取根结点
	 * 
	 * @author  tangkf
	 * @return
	 */
	public Element getRootElement(){
		return	this.rootElement;
	}
	
	/**
	 * 获取文档对象
	 * 
	 * @author  tangkf
	 * @return
	 */
	public Document getDocument(){
		return this.doc;
	}
	
	/**
	 * 递归读取xml文件内容到confignode 中
	 * @author  tangkf
	 * @param parent
	 * @param e
	 */
	private void readConfig(ConfigNode parent,Element e){
		parent.setNodeName(e.getName());
		parent.setNodeValue(e.getTextTrim());
		parent.setElement(e);
		List<?> lst	= e.elements();
		Iterator<?> it	= e.attributeIterator();
		
		while(it.hasNext()){
			Attribute attr	= (Attribute)it.next();
			parent.addProperty(attr.getName(),attr.getValue());
		}
		
		for(int i=0;i<lst.size();i++){
			Element elm	= (Element)lst.get(i);
			ConfigNode ccn	= new ConfigNode();
			parent.addChild(ccn);
			readConfig(ccn,elm);
		}
	}
	

	/**
	 * 获取配置文件根
	 * 
	 * @author tangkf
	 * @return
	 */
	public ConfigNode getRoot() {
		if (root == null) {
			try {
				this.initinalize();
			} catch (Exception e) {
				System.err.println("配置文件路径初始化出错！");
				e.printStackTrace();
			}
		}
		return root;
	}

}
