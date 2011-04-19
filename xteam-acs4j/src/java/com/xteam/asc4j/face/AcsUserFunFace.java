/*
 * 创建时间：2011-3-11 上午11:32:26
 * 工程名称：XTeam-acs4j
 * 文   件  名：com.xteam.asc4j.face.AcsUserFunFace.java
 * Author:Leo
 * 
 */
package com.xteam.asc4j.face;

import java.util.Map;

import com.xteam.asc4j.base.PageInfo;
import com.xteam.asc4j.base.UserSqlValue;
import com.xteam.asc4j.module.entities.PurviewFunNode;
import com.xteam.asc4j.module.entities.PurviewRole;
import com.xteam.asc4j.module.entities.PurviewUser;

/**
 * 功能模块
 * 
 * @author Leo
 * 
 */
public interface AcsUserFunFace {
	/**
	 * 获得功能节点类表
	 * 
	 * @param start
	 *            起始记录
	 * @param length
	 *            获取记录长度
	 * @param node
	 *            查询数据的条件
	 * @return List<PurviewFunNode>
	 * @author:Leo
	 */
	PageInfo<PurviewFunNode> getFunNodeList(int start, int length,
			Map<String,UserSqlValue> node);
	
	/**
	 * 根据用户id，获得对应的功能列表信息
	 * @param start 起始记录
	 * @param length 获取记录的长度
	 * @param userid 用户id
	 * @return List<PurviewFunNode>
	 * @author:Leo
	 */
	PageInfo<PurviewFunNode> getFunNodesOfUser(int start,int length,String userid);
	
	/**
	 * 添加一个功能节点
	 * @param node void
	 * @author:Leo
	 */
	void addFunNode(PurviewFunNode node);
	
	/**
	 * 编辑功能节点信息
	 * @param node void
	 * @author:Leo
	 */
	void editFunNode(PurviewFunNode node);
	
	/**
	 * 更新指定条件的功能节点信息
	 * @param columns 要修改的字段的值
	 * @param con 条件 
	 * @return int 受影响的数据条数
	 * @author:Leo
	 */
	int updateFunNodes(Map<String, UserSqlValue> columns,String con);
	
	/**
	 * 删除功能节点
	 * @param ids 
	 * @return int 受影响的数据条数
	 * @author:Leo
	 */
	int deleteFunNodes(String[] ids);
	
	/**
	 * 设置用户的功能模块
	 * @param user 用户对象
	 * @param node 功能模块组
	 * @return void
	 * @author:Leo
	 */
	void setUserFunNode(PurviewUser user,PurviewFunNode[] node);
	
	/**
	 * 删除指定用户的指定模块
	 * @param userid 用户id
	 * @param funid 功能模块id 数组
	 * @return void
	 * @author:Leo
	 */
	int deleteUserFunNode(String userid,String[] funid);
	
	/**
	 * 删除指定用户的所有模块
	 * @param userid 用户id
	 * @return int 受影响的数据条数
	 * @author:Leo
	 */
	int deleteAllUserFunNode(String userid);
	
	
	/**
	 * 设置角色的模块，删除之前的信息，并新建
	 * @param role 角色对象
	 * @param node 功能模块组 
	 * @return void
	 * @author:Leo
	 */
	void setRoleFunNode(PurviewRole role,PurviewFunNode[] node);
	
	/**
	 * 删除指定角色的指定模块
	 * @param roleid 角色id
	 * @param funid 功能模块 id 数组
	 * @return int 受影响的数据条数
	 * @author:Leo
	 */
	int deleteRoleFunNode(String roleid,String[] funid);
	
	/**
	 * 删除该角色下所有模块
	 * @param roleid 角色ID
	 * @return int 受影响的数据条数
	 * @author:Leo
	 */
	int deleteAllRoleFunNode(String roleid);
	
	/**
	 * 设置用户的功能模块前线
	 * @param userid 用户id
	 * @param funid 功能模块id
	 * @param p  权限表数据id
	 * @return void
	 * @author:Leo
	 */
	void setUserFunPermission(String userid,String funid,int[] p);
	
	/**
	 * 删除指定用户、指定模块的权限
	 * @param userid 用户id
	 * @param funid 功能模块id
	 * @param p 权限表
	 * @return int 受影响的数据条数
	 * @author:Leo
	 */
	int deleteUserFunPermission(String userid,String funid,int[] p);
	
	/**
	 * 删除指定用户下的该功能模块的所有权限
	 * @param userid 用户id
	 * @param funid 功能模块id
	 * @return int
	 * @author:Leo
	 */
	int deleteAllUserFunPermission(String userid,String funid);
	
	/**
	 * 设置角色的功能模块权限
	 * @param roleid 角色id
	 * @param funid 功能id
	 * @param p 权限数据id
	 * @return void
	 * @author:Leo
	 */
	void setRoleFunPermission(String roleid,String funid,int[] p);
	
	/**
	 * 删除指定角色、指定模块的权限
	 * @param roleid 角色ID
	 * @param funid 功能模块id
	 * @param p 权限表
	 * @return int 受影响的数据条数
	 * @author:Leo
	 */
	int deleteRoleFunPermission(String roleid,String funid,int[] p);
	
	/**
	 * 删除指定角色下的该功能模块的所有权限
	 * @param roleid 角色id
	 * @param funid 功能模块id
	 * @return int 受影响的数据条数
	 * @author:Leo
	 */
	int deleteAllRoleFunPermission(String roleid,String funid);
}
