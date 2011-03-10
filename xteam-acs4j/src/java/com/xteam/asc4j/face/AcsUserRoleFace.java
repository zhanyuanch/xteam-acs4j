/*
 * 创建时间：2011-3-9 下午02:53:29
 * 工程名称：XTeam-acs4j
 * 文   件  名：com.xteam.asc4j.face.AcsUserFace.java
 * Author:Leo
 * 
 */
package com.xteam.asc4j.face;

import com.xteam.asc4j.base.PageInfo;
import com.xteam.asc4j.module.entities.PurviewRole;

/**
 * 
 * 权限管理系统用户访问接口
 * @author Leo
  */
public interface AcsUserRoleFace {
	/**
	 * 获得所有的角色列表，带分页信息
	 * @param start 开始记录
	 * @param length 长度，长度为0时，表示不分页
	 * @param con 查询条件
	 * @return List<PurviewRole>
	 * @author:Leo
	 */
	PageInfo<PurviewRole> getRoleList(int start,int length,PurviewRole con);
	
	/**
	 * 获得对应用的角色信息
	 * @param start 开始记录
	 * @param length 获取信息的长度，为0时表示不分页
	 * @param userid 用户的ID
	 * @return PageInfo<PurviewRole>
	 * @author:Leo
	 */
	PageInfo<PurviewRole> getRoleListOfUser(int start,int length,String userid);
	
	
}
