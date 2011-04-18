/*
 * 创建时间：2011-3-16 下午03:58:00
 * 工程名称：XTeam-acs4j
 * 文   件  名：com.xteam.asc4j.module.dao.impl.PurviewUserDaoImpl.java
 * Author:Leo
 * 
 */
package com.xteam.asc4j.module.dao.impl;

import com.xteam.asc4j.module.dao.IPurviewUserDao;
import com.xteam.asc4j.module.entities.PurviewUser;

/**
 * @author Leo
 *
 */
public class PurviewUserDaoImpl extends GenericDaoImple<PurviewUser> implements IPurviewUserDao {
	private static PurviewUserDaoImpl instance;
	private PurviewUserDaoImpl(){}
	public static PurviewUserDaoImpl getInstance(){
		if(null==instance){
			instance = new PurviewUserDaoImpl();
		}
		return instance;
	}
}
