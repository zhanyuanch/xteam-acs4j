/*
 * 创建时间：2011-4-18 下午03:22:45
 * 工程名称：XTeam-acs4j
 * 文   件  名：com.xteam.asc4j.base.UserSqlValue.java
 * Author:Leo
 * 
 */
package com.xteam.asc4j.base;

/**
 * 用户定义的sql参数对象
 * 
 * @author Leo
 * 
 */
public class UserSqlValue implements Modifier {
	private String modifier = EQUAL;

	private Object value;

	public String getModifier() {
		return modifier;
	}

	public void setModifier(String modifier) {
		this.modifier = modifier;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

}
