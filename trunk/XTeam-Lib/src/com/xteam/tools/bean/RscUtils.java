package com.xteam.tools.bean;

import javax.servlet.http.HttpSession;
import java.util.ResourceBundle;

public class RscUtils {
	
	
	public static final String DEFAULT_LANGUAGE	= "";
	public static final String LANGUAGE			= "";
	
	/**
	 * 根据语言字符串取 属性文件
	 * @param rsPath 路径
	 * @param language 语言
	 * @return 字符串
	 */
	public static ResourceBundle getResource(String rsPath,String language) {
		if(language==null || "".equals(language))	language	= DEFAULT_LANGUAGE;
		//Locale lc	= new Locale(language);
        try{
            return ResourceBundle.getBundle(rsPath+"_"+language);
        }catch(Exception e){
            System.out.println("[Error] This resource path ["+rsPath+"_"+language+"] Not Found!");
            return null;
        }
    }
	
	/**
	 * 根据session 和语言取属性文件
	 * @param rsPath 资源路径
	 * @param session session对象
	 * @return ResourceBundle
	 */
	public static ResourceBundle getResource(String rsPath,HttpSession session) {
		String language	= (String)session.getAttribute(LANGUAGE);
        return getResource(rsPath,language);
	}
	
	/**
	 * 读取指定 ResourceBundle 的属性值，防止因为属性不存在而抛出异常。
	 * @param rb 资源包
	 * @param key 关键字
	 * @return string 字符串
	 */
	public static String getValue(ResourceBundle rb,String key) {
		String kv	= "";
		if(rb!=null) {
			try{
				kv	= rb.getString(key);
			}catch(Exception e) {
				System.out.println("[Waring] This properties ["+key+"] Not found!");
				kv	= key+"=?";
			}
		}else{
            kv  = key+"=?[path=null]";
        }
		return kv;
	}
}
