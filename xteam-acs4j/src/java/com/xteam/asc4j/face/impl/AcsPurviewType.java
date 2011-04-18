/*
 * 创建时间：2011-4-18 下午02:27:42
 * 工程名称：XTeam-acs4j
 * 文   件  名：com.xteam.asc4j.face.impl.AcsPurviewType.java
 * Author:Leo
 * 
 */
package com.xteam.asc4j.face.impl;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;

import com.tangkf.utils.CommonUtils;
import com.xteam.asc4j.base.PageInfo;
import com.xteam.asc4j.base.UserSqlValue;
import com.xteam.asc4j.face.AcsPurviewTypeFace;
import com.xteam.asc4j.module.dao.base.GenericDao;
import com.xteam.asc4j.module.dao.impl.PurviewTypeDaoImpl;
import com.xteam.asc4j.module.entities.PurviewType;

/**
 * @author Leo
 * 
 */
public class AcsPurviewType implements AcsPurviewTypeFace {
	private GenericDao<PurviewType> purviewType = PurviewTypeDaoImpl
			.getInstance();

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.xteam.asc4j.face.AcsPurviewTypeFace#addType(com.xteam.asc4j.module.entities.PurviewType)
	 */
	public void addType(PurviewType type) {
		purviewType.save(type);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.xteam.asc4j.face.AcsPurviewTypeFace#deleteType(java.lang.String[])
	 */
	public int deleteType(String[] tid) {
		try {
			purviewType.deleteBathByIds("id", tid);
			return 1;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.xteam.asc4j.face.AcsPurviewTypeFace#editType(com.xteam.asc4j.module.entities.PurviewType)
	 */
	public void editType(PurviewType type) {
		purviewType.update(type);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.xteam.asc4j.face.AcsPurviewTypeFace#getPurviewTypeList(int, int,
	 *      java.util.Map)
	 */
	public PageInfo<PurviewType> getPurviewTypeList(int start, int leng,
			Map<String, UserSqlValue> con) {
		// PropertyUtils.setProperty("", "", "")
		// 构造查询条件
		PurviewType pt = new PurviewType();
		StringBuilder sb = new StringBuilder(" where 1=1 ");
		ArrayList<Object> ps = new ArrayList<Object>();
		try {
			for (String key : con.keySet()) {
				Object o = PropertyUtils.getProperty(pt, key);
				if (o != null) {
					UserSqlValue v = con.get(key);
					sb.append("and ");
					sb.append(key + v.getModifier() + "? ");
					ps.add(v.getValue());
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		LinkedHashMap<String, String> sort = new LinkedHashMap<String, String>();
		sort.put("id", "asc");
		return this.purviewType.getScrollData(start, leng, sb.toString(), ps
				.toArray(), sort);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.xteam.asc4j.face.AcsPurviewTypeFace#updateTypes(java.util.Map,
	 *      java.lang.String)
	 */
	public int updateTypes(Map<String, UserSqlValue> param, String con) {
		// 构造查询条件
		PurviewType pt = new PurviewType();
		StringBuilder sb = new StringBuilder("update PurviewType set id=id ");
		ArrayList<Object> ps = new ArrayList<Object>();
		try {
			for (String key : param.keySet()) {
				Object o = PropertyUtils.getProperty(pt, key);
				if (o != null) {
					UserSqlValue v = param.get(key);
					sb.append("," + key + v.getModifier() + "?");
					ps.add(v.getValue());
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		if (!CommonUtils.isEmpty(con)) {
			sb.append(" where " + con);
		}
		try {
			this.purviewType.executeHQL(sb.toString(), ps.toArray());
			return 1;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return 0;
	}

	public GenericDao<PurviewType> getPurviewType() {
		return purviewType;
	}

	public void setPurviewType(GenericDao<PurviewType> purviewType) {
		this.purviewType = purviewType;
	}

}
