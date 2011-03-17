package com.xteam.tools.bean;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspWriter;

/**
 * Created by IntelliJ IDEA.
 * User: tom
 * Date: 2006-10-21
 * Time: 12:27:20
 * REQUEST 对象的辅助方法实现类
 */
public class ReqHelper {

    public static int parseInt(String sint){
        int i;
        try{
            i = Integer.parseInt(sint);
        }catch(Exception e){
            e.printStackTrace();
            return 0;
        }
        return i;
    }

    public static void writeNotNull(JspWriter out,String src) throws Exception{
    	if(src==null) src	= "";
    	out.println(src);
    }
    
    /**
     * 返回一个参数的值 去除不必要的NULL
     * @param request
     * @param key
     * @return String
     */
    public static String getAttributeString(HttpServletRequest request,String key){
        Object rtn = request.getAttribute(key);
        if(rtn==null) return "";
        return (String)rtn;
    }
    public static int getAttributeInt(HttpServletRequest request,String key){
        String rtn  = getAttributeString(request,key);
        if("".equals(rtn)) rtn ="0";
        return parseInt(rtn);
    }
    public static String getAttributeString(HttpSession session,String key){
        Object rtn = session.getAttribute(key);
        if(rtn==null) return "";
        return (String)rtn;
    }
    public static int getAttributeInt(HttpSession session,String key){
        String rtn  = getAttributeString(session,key);
        if("".equals(rtn)) rtn ="0";
        return parseInt(rtn);
    }

    /**
     * 返回一个参数的值 去除不必要的NULL
     * @param request
     * @param key
     * @return String
     * @throws UnsupportedEncodingException 
     */
    public static String getRequestString(HttpServletRequest request,String key) throws IOException{
        return getRequestString(request,key,"");
    }
    public static String getRequestString(HttpServletRequest request,String key,String dfvalue) throws IOException{
        String rtn = request.getParameter(key);
        if(rtn==null) rtn =dfvalue;
        return rtn;
    }
    
    public static int getRequestInt (HttpServletRequest request,String key) throws IOException {
        return getRequestInt(request,key,0);
    }
    public static int getRequestInt (HttpServletRequest request,String key,int dfvalue) throws IOException {
        String rtn  = getRequestString(request, key,dfvalue+"");
        return parseInt(rtn);
    }

    public static String[] getRequestStrings(HttpServletRequest request,String key){
        String rtn[]    = request.getParameterValues(key);
        return (rtn==null || rtn.length<1)?new String[]{}:rtn;
    }

}
