/*
 * 创建时间：2011-3-16 下午03:58:52
 * 工程名称：XTeam-acs4j
 * 文   件  名：com.xteam.asc4j.module.dao.impl.PurviewUserFunDataruleDaoImpl.java
 * Author:Leo
 * 
 */
package com.xteam.asc4j.module.dao.impl;

import com.xteam.asc4j.module.dao.IPurviewUserFunDataruleDao;
import com.xteam.asc4j.module.entities.PurviewUserFunDatarule;

/**
 * @author Leo
 *
 */
public class PurviewUserFunDataruleDaoImpl extends GenericDaoImple<PurviewUserFunDatarule> implements IPurviewUserFunDataruleDao{
	private static PurviewUserFunDataruleDaoImpl instance;
	private PurviewUserFunDataruleDaoImpl(){}
	public static PurviewUserFunDataruleDaoImpl getInstance(){
		if(null==instance){
			instance = new PurviewUserFunDataruleDaoImpl();
		}
		return instance;
	}
}
