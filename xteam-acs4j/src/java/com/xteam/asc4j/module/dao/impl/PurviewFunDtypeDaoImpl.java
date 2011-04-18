/*
 * 创建时间：2011-3-16 下午03:34:54
 * 工程名称：XTeam-acs4j
 * 文   件  名：com.xteam.asc4j.module.dao.impl.PurviewFunDtypeDaoImpl.java
 * Author:Leo
 * 
 */
package com.xteam.asc4j.module.dao.impl;

import com.xteam.asc4j.module.dao.IPurviewFunDtypeDao;
import com.xteam.asc4j.module.entities.PurviewFunDtype;

/**
 * @author Leo
 *
 */
public class PurviewFunDtypeDaoImpl extends GenericDaoImple<PurviewFunDtype> implements IPurviewFunDtypeDao {
	private static PurviewFunDtypeDaoImpl instance;
	private PurviewFunDtypeDaoImpl(){}
	public static PurviewFunDtypeDaoImpl getInstance(){
		if(null==instance){
			instance = new PurviewFunDtypeDaoImpl();
		}
		return instance;
	}
}
