package com.xteam.tools.bean;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.xteam.tools.CommonUtils;


public class BeanHelper {
	public static void main(String args[]) throws IllegalAccessException,
			InvocationTargetException {

	}

	/**
	 * 将一个request中的多个 BEAN 提取到list中
	 * 
	 * @param type
	 *            obj 类型
	 * @param req
	 *            request
	 * @param key
	 *            obj中需要提取的bean值
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public static List requestToList(Class type, HttpServletRequest req,
			String[] key) throws Exception {
		List list = new ArrayList();
		Map m = new HashMap();
		for (int i = 0; i < key.length; ++i) {
			String[] ps = ReqHelper.getRequestStrings(req, key[i]);
			m.put(key[i], ps);
		}
		String[] pkey = (String[]) m.get(key[0]);

		if (pkey.length > 0) {
			for (int i = 0; i < pkey.length; ++i) {
				Map mo = new HashMap();
				for (int j = 0; j < key.length; ++j) {
					String[] psk = (String[]) m.get(key[j]);
					String v = "";
					if (psk.length > i)
						v = psk[i];
					mo.put(key[j], v);
				}
				Object o = type.newInstance();
				mapToBean(o, mo);
				list.add(o);
			}
		}
		return list;
	}

	/**
	 * 将一个 REQUEST 对象中的属性值组装到 Map 中，起规则按找所给定的 Class 类型进行
	 * 
	 * @param type
	 *            给定的 Class
	 * @param req
	 *            Request 对象
	 * @return map 对象
	 * @throws Exception
	 *             抛出
	 */
	@SuppressWarnings("unchecked")
	public static Map requestToMap(Class type, HttpServletRequest req)
			throws Exception {
		Map m = new HashMap();
//		Enumeration<String> e = req.getParameterNames();
		Field[] fields = type.getDeclaredFields(); // 所有属性
		for (int fi = 0; fi < fields.length; ++fi) {
			Field fd = fields[fi]; // 属性
			String fv = ReqHelper.getRequestString(req, fd.getName()); // 取到字符串参数
			m.put(fd.getName(), fv);
		}
//		while(e.hasMoreElements()){
//			String key=e.nextElement();
//			System.err.println(key+"========");
//			String fv = ReqHelper.getRequestString(req, key);
//			m.put(key, fv);
//		}
		return m;
	}

	/**
	 * 将一个 Request 对象中的 属性值组装到给定的对象 o 中，并返回;
	 * 
	 * @param o
	 *            给定的一个对象
	 * @param req
	 *            request 对象
	 * @throws Exception
	 *             抛出
	 */
	@SuppressWarnings("unchecked")
	public static void requestToBean(Object o, HttpServletRequest req)
			throws Exception {
		Map m = requestToMap(o.getClass(), req);
		// BeanUtils.populate(o,m);
		mapToBean(o, m); // 似乎这样支持的类型更多？？
	}

	@SuppressWarnings("unchecked")
	public static void mapToBean(Object o, Map m) {
		Class type = o.getClass();
		Field[] fks = type.getDeclaredFields();
		for (int i = 0; i < fks.length; ++i) {
			Field fk = fks[i];

			try {
				if (fk.getModifiers() == 2
						&& !fk.getName().equalsIgnoreCase("serialVersionUID")) {
					if (m.get(fk.getName()) != null) {
						// 修改赋值方法
//						fk.set(o, convertObj(fk.getType(), String
//								.valueOf(m.get(fk.getName()))));
						Method method = type.getMethod(getWriteMethod(fk), fk
								.getType());
						method.invoke(o, convertObj(fk.getType(), String
								.valueOf(m.get(fk.getName()))));
					}
				}
				//
//				 BeanUtils.setProperty(o, fk.getName(),
//				 convertObj(fk.getType(),
//				 (String) m.get(fk.getName())));
			} catch (Exception e) {
				System.out.println("[waring:  自动设置属性时出现错误！]");
				e.printStackTrace();
			}
		}
		return;
	}

	public static String getReadMethod(Field f) {
		if (f == null)
			return null;
		return "get" + capFirstCharacter(f.getName());
	}

	public static String getWriteMethod(Field f) {
		if (f == null)
			return null;
		return "set" + capFirstCharacter(f.getName());
	}

	public static String capFirstCharacter(String s) {
		StringBuffer sb = new StringBuffer(s.substring(0, 1).toUpperCase());
		sb.append(s.substring(1));
		return sb.toString();
	}

	/**
	 * 定义一个简单类型转换器
	 * 
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public static Object convertObj(Class<?> ftype, String ovalue) {
		Object o = ovalue;
		try {
			if ( int.class == ftype || Integer.class == ftype) {
				if (CommonUtils.isEmpty(ovalue))
					ovalue = "0";
				o = new Integer(Double.valueOf(ovalue).intValue());
			} else if ( String.class == ftype ) {
				o = ovalue;
			} else if ( Date.class == ftype) {
				o = CommonUtils.parseDateTime(ovalue);
			} else if ( Timestamp.class == ftype) {
				o = CommonUtils.getTimestamp(ovalue);
			} else if ( float.class == ftype || Float.class == ftype) {
				if (CommonUtils.isEmpty(ovalue))
					ovalue = "0";
				o = new Float(Float.parseFloat(ovalue));
			} else if ( long.class == ftype || Long.class == ftype) {
				if (CommonUtils.isEmpty(ovalue))
					ovalue = "0";
				o = new Long(Long.parseLong(ovalue));
			} else if ( double.class == ftype || Double.class == ftype) {
				if (CommonUtils.isEmpty(ovalue))
					ovalue = "0";
				o = new Double(Double.parseDouble(ovalue));
			} else if ( StringBuffer.class == ftype) {
				o = new StringBuffer(ovalue);
			} else {
				o = ovalue;
			}
		} catch (ParseException e) {
			System.out.println("[waring:  类型转换时出现错误！]");
			e.printStackTrace();
		}
		return o;
	}

}
