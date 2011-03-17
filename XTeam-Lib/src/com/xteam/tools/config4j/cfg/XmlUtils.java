
package com.xteam.tools.config4j.cfg;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Branch;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentFactory;
import org.dom4j.DocumentHelper;
import org.dom4j.DocumentType;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.xml.sax.InputSource;


/**
 * @author  tangkf
 * xml 工具类
 */
public class XmlUtils {

	private static DocumentFactory documentFactory = DocumentFactory.getInstance();
	
	private XmlUtils() throws Exception{
		throw new Exception("静态工具类，不允许被创建！");
	}
	
	private static File initFile(String filepath) {
		File file = new File(filepath);
		URL url = XmlUtils.class.getResource(filepath);
		if (!file.exists()) {
			file = new File(url.getPath());
		}
		if (!file.exists()) {
			System.err.println("对不起,指定两个路径下不存在配置文件:" + "\r\n--->"
					+ url.getPath() + "\r\n--->" + file.getPath());
		}
		return file;
	}
	
	public static Document getDOcument(String filepath){
		File file	= initFile(filepath);
		return getDocument(file);
	}
	
	/**
	 * 将一段文本解析为XML 文档对象
	 * @author 汤垲峰 2009-10-21
	 * @param xml
	 * @param encoding
	 * @return
	 */
	public static Document getDocumentByString(String xml,String encoding){
		Document document	= null;
		try {
			document = DocumentHelper.parseText(xml);
			if(encoding!=null && !"".equals(encoding)){
				document.setXMLEncoding(encoding);
			}
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		return document;
	}
	
	public static Document getDocument(File file){
		SAXReader saxReader = new SAXReader();
		Document document	= null;
		try {
			document = saxReader.read(new InputSource(new FileInputStream(file)));
		} catch (DocumentException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return document;
	}
	
	public static Node getChildNodeOf(Node node, String tagName) {
		if (node instanceof Branch) {
			Iterator<?> it = ((Branch) node).content().iterator();
			while (it.hasNext()) {
				Node temp = (Node) it.next();
				if (temp instanceof Element && tagName.equals(temp.getName()))
					return temp;
			}
		}
		return null;
	}

	public static String getChildNodeValueOf(Node node, String tagName) {
		if (tagName.equals(node.getName())) {
			return getValueOf(node);
		}
		if (node instanceof Branch) {
			Iterator<?> it = ((Branch) node).content().iterator();
			while (it.hasNext()) {
				Node temp = (Node) it.next();
				if (temp instanceof Element && tagName.equals(temp.getName()))
					return getValueOf(temp);
			}
		}
		return null;
	}

	public static final String getValueOf(Node node) {
		if (node == null) {
			return null;
		}
		return node.getText().trim();
	}

	public static final String getAtrributeValueOf(Node node, String attribute) {
		if (node instanceof Element) {
			return ((Element) node).attributeValue(attribute);
		}
		return null;
	}

	public static Iterator<?> getElementsByTagName(Element element, String tag) {
		if (element != null && tag != null) {
			return element.elements(tag).iterator();
		}
		return null;
	}

	public static Iterator<Node> getElementsByTagNames(Element element, String[] tags) {
		List<Node> children = new ArrayList<Node>();
		if (element != null && tags != null) {
			List<String> tagList = Arrays.asList(tags);
			List<?> childElements = element.elements();
			for (int i = 0; i < childElements.size(); i++) {
				Node child = (Element) childElements.get(i);
				if (tagList.contains(child.getName())) {
					children.add(child);
				}
			}
		}
		return children.iterator();
	}

	public static DocumentType getDocType(Document document) {
		return document.getDocType();
	}

	public static Document addDocType(Document document, String name,
			String publicId, String systemId) {
		return document.addDocType(name, publicId, systemId);
	}

	public static void setRootElement(Document document, Element rootElement) {
		document.setRootElement(rootElement);
	}

	public static Element addElement(Element parentElement, String childName) {
		return parentElement.addElement(childName);
	}

	public static Element addAttribute(Element element, String attrName,
			String attrValue) {
		return element.addAttribute(attrName, attrValue);
	}

	public static void setText(Element element, String text) {
		element.setText(text);
	}

	public static boolean removeAttribute(Element element, String attribute) {
		return element.remove(element.attribute(attribute));
	}

	public static boolean removeText(Element element, String text) {
		return element.remove(documentFactory.createText(text));
	}

	public static void appendAttributes(Element currentElement,
			Element newElement) {
		currentElement.appendAttributes(newElement);
	}
	
	/**
	 * 写xml文件
	 * @author tangkf
	 * @param document
	 * @param xmlFile
	 * @param encoding
	 */
	public static void writeDocToFile(Document document, File xmlFile,String encoding) {
		try {
			if (!xmlFile.exists()) {
				File parentDir = xmlFile.getParentFile();
				if (!parentDir.exists())
					parentDir.mkdirs();
				xmlFile.createNewFile();
			}
			FileOutputStream fos	= new FileOutputStream(xmlFile);
			//FileWriter fw = new FileWriter(xmlFile);
			OutputFormat format = OutputFormat.createPrettyPrint();
			format.setEncoding(encoding);
			document.setXMLEncoding(encoding);
			XMLWriter writer = new XMLWriter(fos, format);
			writer.write(document);
			fos.close();
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
