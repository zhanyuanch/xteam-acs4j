/*
 * 创建时间：2011-3-16 下午03:28:11
 * 工程名称：XTeam-acs4j
 * 文   件  名：com.xteam.asc4j.module.dao.impl.PurviewDataTypeDaoImpl.java
 * Author:Leo
 * 
 */
package com.xteam.asc4j.module.dao.impl;

import com.xteam.asc4j.module.dao.IPurviewDataTypeDao;
import com.xteam.asc4j.module.entities.PurviewDataType;

/**
 * @author Leo
 * 
 */
public class PurviewDataTypeDaoImpl extends GenericDaoImple<PurviewDataType>
		implements IPurviewDataTypeDao {
	private static PurviewDataTypeDaoImpl instance;
	private PurviewDataTypeDaoImpl(){}
	public static PurviewDataTypeDaoImpl getInstance(){
		if(null==instance){
			instance = new PurviewDataTypeDaoImpl();
		}
		return instance;
	}
}
