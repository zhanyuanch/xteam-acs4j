package com.xteam.tools;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TimeZone;
import java.util.UUID;


/**
 * Created by IntelliJ IDEA. User: tom Date: 2005-9-15 Time: 17:10:18 用于 保存 通用
 * 静态函数部分
 */
public class CommonUtils {

	public static String TRUE_STR = "Y|yes|y|1|YES|Yes|TRUE|T|True|true";

	public static String EMAIL_PATTERN = "^\\s*\\w+(?:\\.{0,1}[\\w-]+)*@[a-zA-Z0-9]+(?:[-.][a-zA-Z0-9]+)*\\.[a-zA-Z]+\\s*$";

	/**
	 * 求a除以b，按进一法取整
	 * 
	 * @param a
	 *            被除数
	 * @param b
	 *            除数
	 * @return int
	 */
	public static int ceil(int a, int b) {
		int c = a % b;
		if (c > 0) {
			return a / b + 1;
		} else {
			return a / b;
		}
	}

	/**
	 * 返回指定时间所处的早，中，晚字符串
	 * 
	 * @author 汤垲峰 2009-10-19
	 * @param dt
	 * @return
	 */
	public static String getDayTime(Date dt) {
		String rtn = "早";
		Calendar cd = Calendar.getInstance();
		cd.setTime(dt);
		int h = cd.get(Calendar.HOUR_OF_DAY);
		if (h >= 11 && h < 18)
			rtn = "午";
		if (h >= 18 || h < 6)
			rtn = "晚";
		return rtn;
	}

	/**
	 * 返回 start 时间 与 end 时间之间的间隔
	 * 
	 * @param start
	 * @param end
	 * @return
	 */
	public static String getDateSpace(Date start, Date end) {
		long x = (end.getTime() - start.getTime()) / 1000;
		return getSecond2String(x);
	}

	/**
	 * 将秒转换为中文字符串，支持到天
	 * 
	 * @author 汤垲峰 2009-12-4
	 * @param second
	 * @return
	 */
	public static String getSecond2String(long second) {
		String tstr = "";
		if (second >= 31536000) {
			tstr += second / 31536000 + "年";
			second = second % 31536000;
		}

		if (second >= 86400) {
			tstr += second / 86400 + "天";
			second = second % 86400;
		}

		if (second >= 3600) {
			tstr += second / 3600 + "小时";
			second = second % 3600;
		}

		if (second >= 60) {
			tstr += second / 60 + "分钟";
			second = second % 60;
		}

		if (second > 0) {
			tstr += second + "秒";
		}
		return tstr;
	}

	/**
	 * 返回 start 时间 与 当前 时间之间的间隔
	 * 
	 * @param start
	 * @return
	 */
	public static String getDateSpace(Date start) {
		return getDateSpace(start, new Date());
	}

	public static boolean parseBoolean(String bstr, boolean defvalue) {
		boolean rtn = false;
		if (TRUE_STR.indexOf(bstr) != -1) {
			rtn = true;
		}
		return rtn;
	}

	public static boolean parseBoolean(String bstr) {
		return parseBoolean(bstr, false);
	}

	/**
	 * <P>
	 * 将字符串转换为 BYTE 类型，如果为空，返回默认值
	 * </P>
	 * 
	 * @author 汤垲峰 2008-11-4
	 * @param sbyte
	 * @param defaultValue
	 * @return
	 */
	public static byte parseByte(String sbyte, byte defaultValue) {
		byte rtn = defaultValue;
		if (sbyte != null) {
			rtn = Byte.parseByte(sbyte);
		}
		return rtn;
	}

	/**
	 * <P>
	 * 将字符串转换为 BYTE 类型，如果为空返回空byte
	 * </P>
	 * 
	 * @author 汤垲峰 2008-11-4
	 * @param sbyte
	 * @return
	 */
	public static byte parseByte(String sbyte) {
		return parseByte(sbyte, Byte.parseByte("0"));
	}

	/**
	 * <p>
	 * 字符串转换为整型,出错时默认 0;
	 * </p>
	 * 
	 * @author tangkf
	 * @param sint
	 * @return
	 */
	public static int parseInt(String sint) {
		return parseInt(sint, 0);
	}

	/**
	 * <p>
	 * 字符串转换为整型,出现错误时采用给定的默认值
	 * </p>
	 * 
	 * @author tangkf
	 * @param sint
	 * @param defaultValue
	 * @return
	 */
	public static int parseInt(String sint, int defaultValue) {
		int i;
		try {
			i = Integer.parseInt(sint);
		} catch (Exception e) {
			System.err.println("[" + sint + "]转换为 int 时出现错误:" + e.getMessage());
			return defaultValue;
		}
		return i;
	}

	/**
	 * <P>
	 * 将字符转换为long 型，转换错误默认为 0
	 * </P>
	 * 
	 * @author 汤垲峰 2009-2-20
	 * @param slong
	 * @return
	 */
	public static long parseLong(String slong) {
		return parseLong(slong, 0L);
	}

	/**
	 * <P>
	 * 将字符转换为long 型，转换错误默认为 defaultValue
	 * </P>
	 * 
	 * @author 汤垲峰 2009-2-20
	 * @param slong
	 * @param defaultValue
	 * @return
	 */
	public static long parseLong(String slong, long defaultValue) {
		long i = defaultValue;
		try {
			i = Long.parseLong(slong);
		} catch (Exception e) {
			System.err.println("[" + slong + "]转换为 Long 时出现错误:"
					+ e.getMessage());
			i = defaultValue;
		}
		return i;
	}

	/**
	 * <P>
	 * 将字符转换为float 型，转换错误默认为 0
	 * </P>
	 * 
	 * @author 汤垲峰 2009-2-21
	 * @param sfloat
	 * @return
	 */
	public static float parseFloat(String sfloat) {
		return parseFloat(sfloat, 0);
	}

	/**
	 * <P>
	 * 将字符转换为float 型，转换错误默认为 dvalue
	 * </P>
	 * 
	 * @author 汤垲峰 2009-2-21
	 * @param sfloat
	 * @param dvalue
	 * @return
	 */
	public static float parseFloat(String sfloat, float dvalue) {
		float i = dvalue;
		try {
			i = Float.parseFloat(sfloat);
		} catch (Exception e) {
			System.err.println("[" + sfloat + "]转换为 int 时出现错误:"
					+ e.getMessage());
			i = dvalue;
		}
		return i;
	}

	/**
	 * 判断一个字符串对象是否为空 除去null 和 ""
	 * 
	 * @param strobj
	 * @return 为空 返回 true, 不为空 返回 false
	 */
	public static boolean isEmpty(String strobj) {
		boolean rtn = false;
		if (strobj == null || "".equals(strobj.trim()))
			rtn = true;
		return rtn;
	}

	/**
	 * <P>
	 * 判断一个字符串缓冲对象是否为空 除去null 和 ""
	 * </P>
	 * 
	 * @author 汤垲峰 2008-11-18
	 * @param strBfr
	 * @return
	 */
	public static boolean isEmpty(StringBuffer strBfr) {
		boolean rtn = false;
		if (strBfr == null || "".equals(strBfr))
			rtn = true;
		return rtn;
	}

	/**
	 * <p>
	 * 判断一个 LIST 类型是否为NULL 或 size < 1
	 * </p>
	 * 
	 * @author tangkf
	 * @param list
	 * @return
	 */
	public static boolean isEmpty(List<?> list) {
		boolean rtn = false;

		if (list == null || list.isEmpty() || list.size() < 1)
			rtn = true;

		return rtn;
	}

	/**
	 * <P>
	 * 判断一个 MAP 是否为空，为空返回TRUE 不为空 返回 FALSE;
	 * </P>
	 * 
	 * @author 汤垲峰 2008-11-5
	 * @param map
	 * @return
	 */
	public static boolean isEmpty(Map<?, ?> map) {
		boolean rtn = false;

		if (map == null || map.isEmpty() || map.size() < 1)
			rtn = true;

		return rtn;
	}

	/**
	 * <P>
	 * 判断一个 MAP 是否不为空，为空返回False 不为空 返回 True;
	 * </P>
	 * 
	 * @author 汤垲峰 2008-11-5
	 * @param map
	 * @return
	 */
	public static boolean notEmpty(Map<?, ?> map) {
		return !isEmpty(map);
	}

	/**
	 * <P>
	 * 判断一个LIST 是否不为空，不为空返回TRUE，为空返回FALSE
	 * </P>
	 * 
	 * @author 汤垲峰 2008-11-5
	 * @param list
	 * @return
	 */
	public static boolean notEmpty(List<?> list) {
		return !isEmpty(list);
	}

	/**
	 * 判断一个字符串对象是否为不为空 除去null 和 ""
	 * 
	 * @param strobj
	 * @return 为空 返回 false, 不为空 返回 true
	 */
	public static boolean notEmpty(String strobj) {
		return !isEmpty(strobj);
	}

	/**
	 * 反回一个用做 数据库 ID 关键字的 串 格式为 年月日时分秒
	 * 
	 * @return String ID
	 */
	public static String getRandomId() {
		String rid;
		Date nd = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		rid = sdf.format(nd);
		return rid;
	}

	/**
	 * 获取当前的TIMESTAMP
	 * 
	 * @return Timestamp
	 */
	public static Timestamp getTimestamp() {
		return new Timestamp(new Date().getTime());
	}

	/**
	 * 
	 * @param dtme
	 * @return Timestamp
	 */
	public static Timestamp getTimestamp(Date dtme) {
		return new Timestamp(dtme.getTime());
	}

	/**
	 * 
	 * @param dtime
	 * @return
	 * @throws ParseException
	 */
	public static Timestamp getTimestamp(String dtime) throws ParseException {
		return new Timestamp(CommonUtils.parseDateTime(dtime).getTime());
	}

	public static Timestamp getTimestampOnlyDate(String dtime)
			throws ParseException {
		return new Timestamp(CommonUtils.parseDate(dtime).getTime());
	}

	public static String getDate(String datetime) {
		if (isEmpty(datetime))
			return "1900-01-01";
		String[] dt = datetime.trim().split(" ");
		return dt.length > 0 ? dt[0] : "1900-01-01";
	}

	public static String getTime(String datetime) {
		String[] dt = datetime.split(" ");
		return dt.length > 1 ? dt[1] : "00:00:00";
	}

	/**
	 * 将日期字符串转化为日期对象 (yyyy-MM-dd)
	 * 
	 * @param dateString
	 * @return
	 * @throws ParseException
	 */
	public static Date parseDate(String dateString) throws ParseException {
		try {
			return parseDtime(dateString, "yyyy-MM-dd");
		} catch (ParseException e) {
			throw e;
		}
	}

	/**
	 * 将日期时间字符串转化为日期对象 (yyyy-MM-dd HH:mm:ss)
	 * 
	 * @param dateString
	 * @return
	 * @throws ParseException
	 */
	public static Date parseDateTime(String dateString) {
		try {
			if (dateString.indexOf(" ") == -1 || dateString.length() < 12)
				dateString += " 00:00:00";
			return parseDtime(dateString, "yyyy-MM-dd HH:mm:ss");
		} catch (ParseException e) {
			try {
				return parseDtime(dateString, "yyyy-MM-dd");
			} catch (ParseException e1) {
				e1.printStackTrace();
			}
		}
		return null;
	}

	/**
	 * 将时间字符串转化为日期对象 (HH:mm:ss)
	 * 
	 * @param timeString
	 * @return
	 * @throws ParseException
	 */
	public static Date parseTime(String timeString) throws ParseException {
		try {
			return parseDtime(timeString, "HH:mm:ss");
		} catch (ParseException e) {
			throw e;
		}
	}

	public static Date parseDtime(String DtimeStr, String formatStr)
			throws ParseException {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(formatStr);
			return sdf.parse(DtimeStr);
		} catch (ParseException e) {
			throw e;
		}
	}

	/**
	 * 返回当前标准日期字符串
	 * 
	 * @return date
	 */
	public static String getStDate() {
		return getStDate(new Date());
	}

	/**
	 * 返回 指定 标准日期字符串
	 * 
	 * @return date
	 */
	public static String getStDate(Date date) {
		String rid;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		rid = sdf.format(date);
		return rid;
	}
	
	/**
	 * 返回 指定 标准日期字符串
	 * 
	 * @return date
	 */
	public static String getStDate(Date date,String pattern) {
		String rid;
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		rid = sdf.format(date);
		return rid;
	}

	/**
	 * 返回当前标准时间字符串
	 * 
	 * @return time
	 */
	public static String getStTime() {
		return getStTime(new Date());
	}

	/**
	 * 返回 指定 标准时间字符串
	 * 
	 * @return time
	 */
	public static String getStTime(Date date) {
		String rid;
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
		rid = sdf.format(date);
		return rid;
	}

	/**
	 * 返回当前标准日期时间字符串
	 * 
	 * @return datetime
	 */
	public static String getStDateTime() {
		return getStDateTime(new Date());
	}

	/**
	 * <P>
	 * 将制定日期按照 formatStr 所规定的格式转换为字符串
	 * </P>
	 * 
	 * @author 汤垲峰 2009-2-7
	 * @param date
	 * @param formatStr
	 * @return
	 */
	public static String getDateTime(Date date, String formatStr) {
		if (isEmpty(formatStr))
			formatStr = "yyyy-MM-dd HH:mm:ss";
		String rid;
		SimpleDateFormat sdf = new SimpleDateFormat(formatStr);
		rid = sdf.format(date);
		return rid;
	}

	/**
	 * 获得一个时间，这个时间的天数被加上了一个偏移变量
	 * 
	 * @author Leo
	 * @param date
	 * @param offset
	 *            加上多少天，如果为减多少天，offset为负
	 * @return Date
	 */
	public static Date getDateAfterAdd(Date date, int offset) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.DAY_OF_MONTH, offset);
		return c.getTime();
	}

	/**
	 * 返回 指定 标准日期时间字符串
	 * 
	 * @return datetime
	 */
	public static String getStDateTime(Date date) {
		if (date == null)
			return "";
		String rid = "";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		rid = sdf.format(date);
		return rid;
	}

	/**
	 * 返回 指定 标准时区日期时间字符串
	 * 
	 * @param date
	 * @param ID
	 *            时区时差 GMT+0,GMT+8,GMT+16..etc
	 * @return
	 */
	public static String getStDateTime(Date date, String ID) {
		if (date == null)
			return "";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		sdf.setTimeZone(TimeZone.getTimeZone(ID));
		return sdf.format(date);
	}

	/**
	 * 将字符串数组按指定的分隔组成字符串。大小写敏感。
	 * 
	 * @param array
	 *            字符串数组
	 * @param delim
	 *            字符串
	 * @return 包含时返回true，否则返回false
	 * @since 0.4
	 */
	public static String combineStringArray(String[] array, String delim) {
		if (array == null || array.length < 1)
			return "";
		if (array.length == 1)
			return array[0];
		int length = array.length - 1;
		if (delim == null) {
			delim = "";
		}
		StringBuffer result = new StringBuffer(length * 8);
		for (int i = 0; i < length; i++) {
			if (array[i] == null)
				array[i] = "";
			result.append(array[i]);
			result.append(delim);
		}
		result.append(array[length]);
		return result.toString();
	}

	public static String combineStringArray(Object[] array, String delim) {
		String[] tmp = new String[array.length];
		for (int i = 0; i < array.length; i++) {
			tmp[i] = array[i].toString();
		}
		return combineStringArray(tmp, delim);
	}

	/**
	 * 将对象数组按指定的分隔组成字符串。大小写敏感。
	 * 
	 * @param array
	 * @param delim
	 * @return str
	 */
	public static String combineStringArray(List<String> array, String delim) {
		if (array == null || array.size() < 1)
			return "";
		if (array.size() == 1)
			return (String) array.get(0);
		int length = array.size() - 1;
		if (delim == null) {
			delim = "";
		}
		StringBuffer result = new StringBuffer(length * 8);
		for (int i = 0; i < length; i++) {
			if (array.get(i) == null)
				array.set(i, "");
			result.append(array.get(i).toString());
			result.append(delim);
		}
		result.append(array.get(length).toString());
		return result.toString();
	}

	/**
	 * 将对象数组按指定的分隔组成字符串。大小写敏感。
	 * 
	 * @param array
	 * @param delim
	 * @return str
	 */
	public static String combineStringList(List<Object> array, String delim) {
		if (array == null || array.size() < 1)
			return "";
		if (array.size() == 1)
			return (String) array.get(0);
		int length = array.size() - 1;
		if (delim == null) {
			delim = "";
		}
		StringBuffer result = new StringBuffer(length * 8);
		for (int i = 0; i < length; i++) {
			if (array.get(i) == null)
				array.set(i, "");
			result.append(array.get(i).toString());
			result.append(delim);
		}
		result.append(array.get(length).toString());
		return result.toString();
	}

	public static String toUtf8String(String s) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < s.length(); i++) {
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
					if (k < 0)
						k += 256;
					sb.append("%").append(Integer.toHexString(k).toUpperCase());
				}
			}
		}
		return sb.toString();
	}

	/**
	 * 数组转换成 '12321','3131','fafs','2142','31'
	 * 
	 * @param sourceStr
	 * @return
	 * 
	 * @author huangchun
	 */
	public static String changeString(String[] sourceStr) {
		String id = "";
		if (sourceStr != null && sourceStr.length != 0) {
			for (int i = 0; i < sourceStr.length; i++) {
				id += sourceStr[i] + ",";
			}
			id = id.substring(0, id.length() - 1);
		}
		return changeString(id);
	}

	/**
	 * 12321,3131,fafs,2142,31 转换成 '12321','3131','fafs','2142','31'
	 * 
	 * @param sourceStr
	 * @return
	 */
	public static String changeString(String sourceStr) {
		return changeString(sourceStr, "'");
	}

	/**
	 * 把Number对象，转化成long
	 * 
	 * @return long
	 * @author:Leo
	 */
	public static long parseNObjectToLong(Object o) {
		if (o == null) {
			return 0L;
		}
		return ((Number) o).longValue();
	}

	/**
	 * 按指定字符串
	 * 
	 * @param sourceStr
	 * @param str
	 * @return
	 */
	public static String changeString(String sourceStr, String str) {
		if (isEmpty(sourceStr)) {
			return "";
		}
		String[] arr = sourceStr.split(",");
		String result = "";
		for (String temp : arr) {
			result += str + temp + str + ",";
		}
		result = result.substring(0, result.length() - 1);
		return result;
	}

	/**
	 * 创建sql查询的in条件
	 * 
	 * @param sids
	 * @return String
	 * @author:Leo
	 */
	public static StringBuilder spliceCondition(String[] sids) {
		StringBuilder ids = new StringBuilder("(");
		for (int i = 0; i < sids.length; i++) {
			if (i != 0) {
				ids.append(",");
			}
			ids.append("?");
		}
		ids.append(")");
		return ids;
	}

	/**
	 * 获得有效的Email信息，并删除重复的email地址
	 * 
	 * @param emails
	 * @return String[]
	 * @author:Leo
	 */
	public static String[] getAvailEmail(String[] emails) {
		Set<String> e = new HashSet<String>();
		for (String m : emails) {
			if (m.matches(CommonUtils.EMAIL_PATTERN)) {
				e.add(m);
			}
		}
		String[] ret = new String[e.size()];
		Iterator<String> it = e.iterator();
		int index = 0;
		while (it.hasNext()) {
			ret[index] = it.next();
			index++;
		}
		return ret;
	}

	public static void main(String[] args) {
		String[] a = new String[] { "leo@qq.com", "jay@qq.com", "leo@qq.com",
				"2222", "@333a.co","LEOJAY1@QQ.COM" };
		System.out.println(Arrays.toString(getAvailEmail(a)));
		System.err.println(md5("123456"));
	}

	public static String md5(String s) {
		return MDFiveEncrypt.getMD5Encode(s);
	}

	public static String uuid() {
		return UUID.randomUUID().toString();
	}
}
