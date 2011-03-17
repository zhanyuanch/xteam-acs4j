package com.xteam.tools.db;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: tom
 * Date: 2005-9-15
 * Time: 17:10:18
 * 用于 保存 通用 静态函数部分
 */
public class DB_CommonUtils {


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
    
    /**
     * 判断一个字符串对象是否为空 除去null 和 ""
     * @param strobj
     * @return 为空 返回 true, 不为空 返回 false
     */
    public static boolean isEmpty(String strobj) {
    	boolean rtn	= false;
    	if(strobj==null || "".equals(strobj)) rtn	= true;
    	return rtn;
    }
    
    /**
     * 判断一个字符串对象是否为不为空 除去null 和 ""
     * @param strobj
     * @return 为空 返回 false, 不为空 返回 true
     */
    public static boolean notEmpty(String strobj) {
    	return !isEmpty(strobj);
    }

    /**
     * 反回一个用做 数据库 ID 关键字的 串 格式为 年月日时分秒
     * @return String ID
     */
    public static String getRandomId() {
        String rid;
        Date nd = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        rid=sdf.format(nd);
        return rid;
    }

    /**
     * 获取当前的TIMESTAMP
     * @return Timestamp
     */
    public static Timestamp getTimestamp(){
        return new Timestamp(new Date().getTime());
    }

    /**
     *
     * @param dtme
     * @return Timestamp
     */
    public static Timestamp getTimestamp(Date dtme){
        return new Timestamp(dtme.getTime());
    }

    /**
     *
     * @param dtime
     * @return
     * @throws ParseException
     */
    public static Timestamp getTimestamp(String dtime) throws ParseException {
        return new Timestamp(DB_CommonUtils.parseDateTime(dtime).getTime());
    }
    public static Timestamp getTimestampOnlyDate(String dtime) throws ParseException {
        return new Timestamp(DB_CommonUtils.parseDate(dtime).getTime());
    }

    public static String getDate(String datetime){
    	if(isEmpty(datetime)) return "1900-01-01";
        String[] dt = datetime.trim().split(" ");
        return dt.length>0?dt[0]:"1900-01-01";
    }
    public static String getTime(String datetime){
        String[] dt = datetime.split(" ");
        return dt.length>1?dt[1]:"00:00:00";
    }

    /**
     * 将日期字符串转化为日期对象 (yyyy-MM-dd)
     * @param dateString
     * @return
     * @throws ParseException
     */
    public static Date parseDate(String dateString) throws ParseException {
        try {
            return parseDtime(dateString,"yyyy-MM-dd");
        } catch (ParseException e) {
            throw e;
        }
    }

    /**
     * 将日期时间字符串转化为日期对象 (yyyy-MM-dd HH:mm:ss)
     * @param dateString
     * @return
     * @throws ParseException
     */
    public static Date parseDateTime(String dateString) throws ParseException {
        try {
            return parseDtime(dateString,"yyyy-MM-dd HH:mm:ss");
        } catch (ParseException e) {
            throw e;
        }
    }

    /**
     * 将时间字符串转化为日期对象 (HH:mm:ss)
     * @param timeString
     * @return
     * @throws ParseException
     */
    public static Date parseTime(String timeString) throws ParseException {
        try {
            return parseDtime(timeString,"HH:mm:ss");
        } catch (ParseException e) {
            throw e;
        }
    }
    public static Date parseDtime(String DtimeStr,String formatStr) throws ParseException {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(formatStr);
            return sdf.parse(DtimeStr);
        } catch (ParseException e) {
            throw e;
        }
    }



    /**
     * 返回当前标准日期字符串
     * @return date
     */
    public static String getStDate(){
        return getStDate(new Date());
    }

    /**
     * 返回 指定 标准日期字符串
     * @return date
     */
    public static String getStDate(Date date){
        String rid;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        rid=sdf.format(date);
        return rid;
    }

    /**
     * 返回当前标准时间字符串
     * @return time
     */
    public static String getStTime(){
        return getStTime(new Date());
    }

    /**
     * 返回 指定 标准时间字符串
     * @return time
     */
    public static String getStTime(Date date){
        String rid;
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        rid=sdf.format(date);
        return rid;
    }

    /**
     * 返回当前标准日期时间字符串
     * @return datetime
     */
    public static String getStDateTime(){
        return getStDateTime(new Date());
    }

    /**
     * 返回 指定 标准日期时间字符串
     * @return datetime
     */
    public static String getStDateTime(Date date){
        String rid;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        rid=sdf.format(date);
        return rid;
    }


    /**
     * 将字符串数组按指定的分隔组成字符串。大小写敏感。
     * @param array 字符串数组
     * @param delim 字符串
     * @return 包含时返回true，否则返回false
     * @since  0.4
     */
    public static String combineStringArray(String[] array, String delim) {
        if(array==null || array.length<1) return "";
        if(array.length==1) return array[0];
        int length = array.length - 1;
        if (delim == null) {
            delim = "";
        }
        StringBuffer result = new StringBuffer(length * 8);
        for (int i = 0; i < length; i++) {
        	if(array[i]==null) array[i]="";
            result.append(array[i]);
            result.append(delim);
        }
        result.append(array[length]);
        return result.toString();
    }
    
    /**
     * 将字符串数组按指定的分隔组成字符串。大小写敏感。
     * @author 汤垲峰
     * @param array
     * @param delim
     * @return
     */
    public static String combineStringArray(Object[] array, String delim) {
        String[] tmp    = new String[array.length];
        for(int i=0;i<array.length;i++){
            tmp[i]  = array[i].toString();
        }
        return combineStringArray(tmp, delim);
    }

    /**
     * 将字符串数组按指定的分隔组成字符串。大小写敏感。
     * @param array
     * @param delim
     * @return str
     */
    public static String combineStringArray(List<String> array, String delim) {
        if(array==null || array.size()<1) return "";
        if(array.size()==1) return (String)array.get(0);
        int length = array.size() - 1;
        if (delim == null) {
            delim = "";
        }
        StringBuffer result = new StringBuffer(length * 8);
        for (int i = 0; i < length; i++) {
        	if(array.get(i)==null) array.set(i, "");
            result.append(array.get(i).toString());
            result.append(delim);
        }
        result.append(array.get(length).toString());
        return result.toString();
    }

 
    public static String toUtf8String(String s) {
        StringBuffer sb = new StringBuffer();
        for (int i=0;i<s.length();i++) {
            char c = s.charAt(i);
            if (c >= 0 && c <= 255) {
                sb.append(c);
            } else {
                byte[] b;
                try {
                    b = Character.toString(c).getBytes("utf-8");
                } catch (Exception ex) {
                    System.out.println(ex);
                    b = new byte[0];
                }
                for (int j = 0; j < b.length; j++) {
                    int k = b[j];
                    if (k < 0) k += 256;
                    sb.append("%").append( Integer.toHexString(k).toUpperCase());
                }
            }
        }
        return sb.toString();
    }
}

