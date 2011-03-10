package com.xteam.asc4j.module.entities;

/**
 * PurviewType generated by MyEclipse Persistence Tools
 */

public class PurviewType implements java.io.Serializable {

	// Fields

	private Integer purId;

	private String type;

	private String name;

	private String remark;

	// Constructors

	/** default constructor */
	public PurviewType() {
	}

	/** minimal constructor */
	public PurviewType(Integer purId) {
		this.purId = purId;
	}

	/** full constructor */
	public PurviewType(Integer purId, String type, String name, String remark) {
		this.purId = purId;
		this.type = type;
		this.name = name;
		this.remark = remark;
	}

	// Property accessors

	public Integer getPurId() {
		return this.purId;
	}

	public void setPurId(Integer purId) {
		this.purId = purId;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}