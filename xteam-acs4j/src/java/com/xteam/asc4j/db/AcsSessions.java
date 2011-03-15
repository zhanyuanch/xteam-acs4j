/*
 * 创建时间：2011-3-15 下午03:25:17
 * 工程名称：XTeam-acs4j
 * 文   件  名：com.xteam.asc4j.db.AcsSessions.java
 * Author:Leo
 * 
 */
package com.xteam.asc4j.db;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.tangkf.utils.CommonUtils;
import com.toms.config.ConfigBuilder;
import com.toms.config.ConfigNode;
import com.xteam.asc4j.face.AcsSessionFactory;

/**
 * @author Leo
 * 
 */
public class AcsSessions {
	private static SessionFactory factory;

	private static ConfigNode root;

	private static final ThreadLocal<Session> local = new ThreadLocal<Session>();

	static {
		try {
			ConfigBuilder cb = new ConfigBuilder("/asc4j-config.xml");
			root = cb.getRoot();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获得SessionFatory对象
	 * 
	 * @return SessionFactory
	 * @author:Leo
	 */
	public static SessionFactory getSessionFactory() {
		if (null == root) {
			System.err.println("【读取配置文件失败】...");
			return null;
		}
		ConfigNode n1 = root.getOneChildByName("session-factory-class");
		if (null == n1) {
			System.err.println("【读取工厂类(session-factory-class)错误】...");
			return null;
		}
		if (null != factory) {// 工厂类不为空，直接返回
			return factory;
		}
		// 不为空，使用用户自定的实现
		if (!CommonUtils.isEmpty(n1.getNodeValue())) {
			try {
				Class cls = Class.forName(n1.getNodeValue());// 装载对象
				AcsSessionFactory af = (AcsSessionFactory) cls.newInstance();
				factory = af.generateSessionFactory();
				return factory;
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}
		ConfigNode n2 = root.getOneChildByName("db-xml");
		if (null == n2) {
			System.err.println("【读取Hibernate配置文件出错！】...");
			return null;
		}
		if (!CommonUtils.isEmpty(n2.getNodeValue())) {
			Configuration cfg = new Configuration()
					.configure("n2.getNodeValue()");
			factory = cfg.buildSessionFactory();
			return factory;
		}
		return null;
	}

	/**
	 * 获得当前线程的Session
	 * 
	 * @return Session
	 * @author:Leo
	 */
	public static Session currentSession() {
		Session session = local.get();
		if (null == session || !session.isOpen()) {
			if (null == factory) {
				factory = getSessionFactory();
			}
			session = (factory != null) ? factory.openSession() : null;
			local.set(session);
		}
		return session;
	}
}
