package com.xteam.asc4j.face.impl;

import java.util.Map;

import com.xteam.asc4j.base.PageInfo;
import com.xteam.asc4j.base.UserSqlValue;
import com.xteam.asc4j.face.AcsUserFunFace;
import com.xteam.asc4j.module.dao.base.GenericDao;
import com.xteam.asc4j.module.dao.impl.PurviewFunDtypeDaoImpl;
import com.xteam.asc4j.module.dao.impl.PurviewFunNodeDaoImpl;
import com.xteam.asc4j.module.dao.impl.PurviewRoleFunDataruleDaoImpl;
import com.xteam.asc4j.module.dao.impl.PurviewUserFunDataruleDaoImpl;
import com.xteam.asc4j.module.entities.PurviewFunDtype;
import com.xteam.asc4j.module.entities.PurviewFunNode;
import com.xteam.asc4j.module.entities.PurviewRole;
import com.xteam.asc4j.module.entities.PurviewRoleFunDatarule;
import com.xteam.asc4j.module.entities.PurviewUser;
import com.xteam.asc4j.module.entities.PurviewUserFunDatarule;

public class AcsUserFun implements AcsUserFunFace {
	private GenericDao<PurviewFunNode> funNode = PurviewFunNodeDaoImpl
			.getInstance();

	private GenericDao<PurviewFunDtype> funTypeNode = PurviewFunDtypeDaoImpl
			.getInstance();

	private GenericDao<PurviewRoleFunDatarule> roleFunData = PurviewRoleFunDataruleDaoImpl
			.getInstance();

	private GenericDao<PurviewUserFunDatarule> userFunData = PurviewUserFunDataruleDaoImpl
			.getInstance();

	public void addFunNode(PurviewFunNode node) {
		// TODO Auto-generated method stub

	}

	public int deleteAllRoleFunNode(String roleid) {
		// TODO Auto-generated method stub
		return 0;
	}

	public int deleteAllRoleFunPermission(String roleid, String funid) {
		// TODO Auto-generated method stub
		return 0;
	}

	public int deleteAllUserFunNode(String userid) {
		// TODO Auto-generated method stub
		return 0;
	}

	public int deleteAllUserFunPermission(String userid, String funid) {
		// TODO Auto-generated method stub
		return 0;
	}

	public int deleteFunNodes(String[] ids) {
		// TODO Auto-generated method stub
		return 0;
	}

	public int deleteRoleFunNode(String roleid, String[] funid) {
		// TODO Auto-generated method stub
		return 0;
	}

	public int deleteRoleFunPermission(String roleid, String funid, int[] p) {
		// TODO Auto-generated method stub
		return 0;
	}

	public int deleteUserFunNode(String userid, String[] funid) {
		// TODO Auto-generated method stub
		return 0;
	}

	public int deleteUserFunPermission(String userid, String funid, int[] p) {
		// TODO Auto-generated method stub
		return 0;
	}

	public void editFunNode(PurviewFunNode node) {
		// TODO Auto-generated method stub

	}

	public PageInfo<PurviewFunNode> getFunNodeList(int start, int length,
			Map<String, UserSqlValue> node) {
		// TODO Auto-generated method stub
		return null;
	}

	public PageInfo<PurviewFunNode> getFunNodesOfUser(int start, int length,
			String userid) {
		// TODO Auto-generated method stub
		return null;
	}

	public void setRoleFunNode(PurviewRole role, PurviewFunNode[] node) {
		// TODO Auto-generated method stub

	}

	public void setRoleFunPermission(String roleid, String funid, int[] p) {
		// TODO Auto-generated method stub

	}

	public void setUserFunNode(PurviewUser user, PurviewFunNode[] node) {
		// TODO Auto-generated method stub

	}

	public void setUserFunPermission(String userid, String funid, int[] p) {
		// TODO Auto-generated method stub

	}

	public int updateFunNodes(Map<String, UserSqlValue> columns, String con) {
		// TODO Auto-generated method stub
		return 0;
	}

	public GenericDao<PurviewFunNode> getFunNode() {
		return funNode;
	}

	public void setFunNode(GenericDao<PurviewFunNode> funNode) {
		this.funNode = funNode;
	}

	public GenericDao<PurviewFunDtype> getFunTypeNode() {
		return funTypeNode;
	}

	public void setFunTypeNode(GenericDao<PurviewFunDtype> funTypeNode) {
		this.funTypeNode = funTypeNode;
	}

	public GenericDao<PurviewRoleFunDatarule> getRoleFunData() {
		return roleFunData;
	}

	public void setRoleFunData(GenericDao<PurviewRoleFunDatarule> roleFunData) {
		this.roleFunData = roleFunData;
	}

	public GenericDao<PurviewUserFunDatarule> getUserFunData() {
		return userFunData;
	}

	public void setUserFunData(GenericDao<PurviewUserFunDatarule> userFunData) {
		this.userFunData = userFunData;
	}

}
