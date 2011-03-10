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
import com.xteam.asc4j.module.entities.PurviewUser;

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
	
	/**
	 * 新增角色信息
	 * @param role 
	 * @return void
	 * @author:Leo
	 */
	void addRole(PurviewRole role);
	
	/**
	 * 修改角色信息
	 * @param role 
	 * @return void
	 * @author:Leo
	 */
	void editRole(PurviewRole role);
	
	/**
	 * 批量修改角色信息
	 * @param role 目标值
	 * @param con  更新条件，eg：and id='xx'
	 * @return void
	 * @author:Leo
	 */
	void updateRoles(PurviewRole role,String con);
	
	/**
	 * 批量删除角色信息
	 * @param rids 角色id数组
	 * @return void
	 * @author:Leo
	 */
	void deleteRoles(String[] rids);
	
	/**
	 * 设置用户角色
	 * @param user 用户信息，需含id和用户名
	 * @param role[] 用户角色数组，一个用户可以有多个角色信息
	 * @return void
	 * @author:Leo
	 */
	void setUserRole(PurviewUser user,PurviewRole[] role);
	
	/**
	 * 删除用户的指定角色
	 * @param userid 用户id
	 * @param roleid 用于删除的角色id，数组类型 
	 * @return void
	 * @author:Leo
	 */
	void deleteRelation(String userid,String[] roleid);
	
	/**
	 * 删除指定用户和所有角色之间的关系
	 * @param userid 用户ID 
	 * @return void
	 * @author:Leo
	 */
	void deleteAllRelation(String userid);
}
