/*
 * 创建时间：2011-3-16 下午03:59:35
 * 工程名称：XTeam-acs4j
 * 文   件  名：com.xteam.asc4j.module.dao.impl.PurviewUserRoleDaoImpl.java
 * Author:Leo
 * 
 */
package com.xteam.asc4j.module.dao.impl;

import com.xteam.asc4j.module.dao.IPurviewUserRoleDao;
import com.xteam.asc4j.module.entities.PurviewUserRole;

/**
 * @author Leo
 *
 */
public class PurviewUserRoleDaoImpl extends GenericDaoImple<PurviewUserRole> implements IPurviewUserRoleDao{
	private static PurviewUserRoleDaoImpl instance;
	private PurviewUserRoleDaoImpl(){}
	public static PurviewUserRoleDaoImpl getInstance(){
		if(null==instance){
			instance = new PurviewUserRoleDaoImpl();
		}
		return instance;
	}
}
