package com.xteam.asc4j.face.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;
import org.hibernate.SQLQuery;
import org.hibernate.Session;

import com.tangkf.utils.CommonUtils;
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
		this.funNode.save(node);
	}

	public int deleteAllRoleFunNode(String roleid) {
		try {
			roleFunData.deleteBathByIds("roleId", new String[] { roleid });
			return 1;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return 0;
	}

	public int deleteAllRoleFunPermission(String roleid, String funid) {
		try {
			String hql = "Delete from PurviewRoleFunDatarule where roleId=? and funId=?";
			roleFunData.executeHQL(hql, new Object[] { roleid, funid });
			return 1;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return 0;
	}

	public int deleteAllUserFunNode(String userid) {
		try {
			userFunData.deleteBathByIds("userId", new String[] { userid });
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return 0;
	}

	public int deleteAllUserFunPermission(String userid, String funid) {
		try {
			String hql = "Delete from PurviewUserFunDatarule where userId=? and funId=?";
			userFunData.executeHQL(hql);
			return 1;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return 0;
	}

	public int deleteFunNodes(String[] ids) {
		try {
			funNode.deleteBathByIds("id", ids);
			return 1;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return 0;
	}

	public int deleteRoleFunNode(String roleid, String[] funid) {
		try {
			String hql = "Delete from PurviewRoleFunDatarule where roleId=? and funId in "
					+ CommonUtils.spliceCondition(funid);
			ArrayList<Object> params = new ArrayList<Object>();
			params.add(roleid);
			params.addAll(Arrays.asList(funid));
			roleFunData.executeHQL(hql, params.toArray());
			return 1;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return 0;
	}

	public int deleteRoleFunPermission(String roleid, String funid, int[] p) {
		try {
			String hql = "Delete from PurviewRoleFunDatarule where "
					+ "roleId=? and funId = ? and druleId in "
					+ CommonUtils.spliceCondition(p.length);
			Object[] op = CommonUtils.intToOArray(p);
			ArrayList<Object> params = new ArrayList<Object>();
			params.add(roleid);
			params.add(funid);
			params.addAll(Arrays.asList(op));
			roleFunData.executeHQL(hql, params.toArray());
			return 1;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return 0;
	}

	public int deleteUserFunNode(String userid, String[] funid) {
		try {
			String hql = "Delete from PurviewUserFunDatarule where userId=? "
					+ "and funId in " + CommonUtils.spliceCondition(funid);
			ArrayList<Object> params = new ArrayList<Object>();
			params.add(userid);
			params.addAll(Arrays.asList(funid));
			userFunData.executeHQL(hql, params.toArray());
			return 1;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return 0;
	}

	public int deleteUserFunPermission(String userid, String funid, int[] p) {
		try {
			String hql = "Delete from PurviewUserFunDatarule where userId=? and funId =? "
					+ "and funPlist in "
					+ CommonUtils.spliceCondition(p.length);
			Object[] op = CommonUtils.intToOArray(p);
			ArrayList<Object> params = new ArrayList<Object>();
			params.add(userid);
			params.add(funid);
			params.addAll(Arrays.asList(op));
			userFunData.executeHQL(hql, params.toArray());
			return 1;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return 0;
	}

	public void editFunNode(PurviewFunNode node) {
		funNode.update(node);
	}

	public PageInfo<PurviewFunNode> getFunNodeList(int start, int length,
			Map<String, UserSqlValue> node) {
		// 查询条件
		PurviewFunNode pf = new PurviewFunNode();
		StringBuilder sb = new StringBuilder(" where 1=1 ");
		ArrayList<Object> ps = new ArrayList<Object>();
		try {
			for (String key : node.keySet()) {
				Object o = PropertyUtils.getProperty(pf, key);
				if (o != null) {
					UserSqlValue v = node.get(key);
					sb.append("and ");
					sb.append(key + v.getModifier() + "? ");
					ps.add(v.getValue());
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		LinkedHashMap<String, String> sort = new LinkedHashMap<String, String>();
		sort.put("pfunId", "asc");
		sort.put("id", "asc");
		return funNode.getScrollData(start, length, sb.toString(),
				ps.toArray(), sort);
	}

	@SuppressWarnings("unchecked")
	public PageInfo<PurviewFunNode> getFunNodesOfUser(int start, int length,
			String userid) {
		PageInfo<PurviewFunNode> page = new PageInfo<PurviewFunNode>();
		try {
			String sql = "select f.* from PURVIEW_FUN_NODE f,PURVIEW_USER_FUN_DATARULE uf"
					+ " where f.id = ur.FUN_ID and ur.USER_ID=?";
			String csql = "select count(1) from PURVIEW_FUN_NODE f," +
					"PURVIEW_USER_FUN_DATARULE uf"
					+ " where f.id = ur.FUN_ID and ur.USER_ID=?";
			page.setTotalCount(funNode.getCountBySQLParams(csql, new Object[]{userid}));
			Session ses = funNode.getDaoSession();
			SQLQuery q = ses.createSQLQuery(sql);
			q.setParameter(0, userid);
			q.addEntity(PurviewFunNode.class);
			if(start!=-1 && length!=0){
				q.setFirstResult(start);
				q.setMaxResults(length);
			}
			page.setDataList(q.list());
		} catch (Exception ex) {

		}
		return page;
	}

	public void setRoleFunNode(PurviewRole role, PurviewFunNode[] node) {
		String hql="";
		for(PurviewFunNode n :node){
			hql ="delete from PurviewRoleFunDatarule where roleId=? and funId=?";
			this.funNode.executeHQL(hql, new Object[]{role.getId(),n.getId()});//删除之前的模块角色信息
			//信件角色模块信息
			PurviewRoleFunDatarule rfd = new PurviewRoleFunDatarule();
			rfd.setRoleId(role.getId());
			rfd.setRoleName(role.getName());
			rfd.setFunId(n.getId());
			rfd.setFunName(n.getName());
			this.roleFunData.save(rfd);
		}
	}

	public void setRoleFunPermission(String roleid, String funid, int[] p) {
		String hql ="";
		for(int n :p){
			
		}
	}

	public void setUserFunNode(PurviewUser user, PurviewFunNode[] node) {

	}

	public void setUserFunPermission(String userid, String funid, int[] p) {

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
