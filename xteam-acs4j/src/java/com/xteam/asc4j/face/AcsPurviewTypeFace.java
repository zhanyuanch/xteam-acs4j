/*
 * 创建时间：2011-3-11 下午03:22:28
 * 工程名称：XTeam-acs4j
 * 文   件  名：com.xteam.asc4j.face.AcsPermissionType.java
 * Author:Leo
 * 
 */
package com.xteam.asc4j.face;

import java.util.List;
import java.util.Map;

import com.xteam.asc4j.module.entities.PurviewType;

/**
 * @author Leo
 * 
 */
public interface AcsPurviewTypeFace {
	
	/**
	 * 获得权限类型列表
	 * @param start 起始记录
	 * @param leng 获取记录长度
	 * @param con 查询条件
	 * @return List<PurviewType>
	 * @author:Leo
	 */
	List<PurviewType> getPurviewTypeList(int start, int leng,
			Map<String, Object> con);
	
	/**
	 * 添加一个权限数据
	 * @param type 权限数据 
	 * @return void
	 * @author:Leo
	 */
	void addType(PurviewType type);
	
	/**
	 * 修改一个权限数据
	 * @param type void
	 * @author:Leo
	 */
	void editType(PurviewType type);
	
	/**
	 * 批量修改一组数据
	 * @param param 修改内容
	 * @param con 条件
	 * @return int 收到影响的数据
	 * @author:Leo
	 */
	int updateTypes(Map<String, Object> param,String con);
	
	/**
	 * 删除权限数据
	 * @param tid
	 * @return int
	 * @author:Leo
	 */
	int deleteType(String[] tid);
}
