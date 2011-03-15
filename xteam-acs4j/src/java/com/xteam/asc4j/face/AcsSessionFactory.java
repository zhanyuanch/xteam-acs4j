/*
 * 创建时间：2011-3-15 下午02:46:56
 * 工程名称：XTeam-acs4j
 * 文   件  名：com.xteam.asc4j.face.AcsSessionFactory.java
 * Author:Leo
 * 
 */
package com.xteam.asc4j.face;

import org.hibernate.SessionFactory;

/**
 * @author Leo
 * 
 */
public interface AcsSessionFactory {
	
	/**
	 * 获得一个hibernate的 SessionFatory对象，用来产生Session
	 * 
	 * @return SessionFactory
	 * @author:Leo
	 */
	public SessionFactory generateSessionFactory();

}
