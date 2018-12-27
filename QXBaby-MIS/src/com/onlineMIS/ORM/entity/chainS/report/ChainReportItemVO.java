package com.onlineMIS.ORM.entity.chainS.report;

import java.io.Serializable;
import java.util.Date;

import com.onlineMIS.common.Common_util;

public class ChainReportItemVO implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -917163636081888918L;
	public static final String STATE_CLOSED = "closed";
	public static final String STATE_OPEN = "open";
	public static final String spliter = "@";
	private int parentId;
	private int chainId;
	private int yearId;
	private int quarterId;
	private int brandId;
	private int pbId;
	private String state;
	private String name;
	private String id;
	private boolean seeCost = false;
	private Date startDate = new Date();
	private Date endDate = new Date();
	
	public ChainReportItemVO(){
		
	}
	public ChainReportItemVO(String name, int parentId, int chainId, int yearId, int quarterId, int brandId, int pbId, String state){
		super();
		this.setId(Common_util.getUUID());
		this.setName(name);
		this.setParentId(parentId);
		this.setYearId(yearId);
		this.setQuarterId(quarterId);
		this.setBrandId(brandId);
		this.setState(state);
		this.setChainId(chainId);
		this.setPbId(pbId);
	}
	
	public int getPbId() {
		return pbId;
	}
	public void setPbId(int pbId) {
		this.pbId = pbId;
	}
	public boolean getSeeCost() {
		return seeCost;
	}
	public void setSeeCost(boolean seeCost) {
		this.seeCost = seeCost;
	}
	public int getParentId() {
		return parentId;
	}
	public void setParentId(int parentId) {
		this.parentId = parentId;
	}
	public int getChainId() {
		return chainId;
	}
	public void setChainId(int chainId) {
		this.chainId = chainId;
	}
	public int getYearId() {
		return yearId;
	}
	public void setYearId(int yearId) {
		this.yearId = yearId;
	}
	public int getQuarterId() {
		return quarterId;
	}
	public void setQuarterId(int quarterId) {
		this.quarterId = quarterId;
	}
	public int getBrandId() {
		return brandId;
	}
	public void setBrandId(int brandId) {
		this.brandId = brandId;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	
	public String generateKeys(){
		return 	parentId + spliter + chainId + spliter + yearId + spliter + quarterId + spliter + brandId +spliter + pbId ;
	}
	
}
