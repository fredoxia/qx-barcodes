package com.onlineMIS.ORM.entity.chainS.vip;

import java.io.Serializable;
import java.util.Date;

import com.onlineMIS.ORM.entity.chainS.user.ChainUserInfor;
import com.onlineMIS.ORM.entity.headQ.user.UserInfor;
import com.onlineMIS.ORM.entity.chainS.user.ChainStore;

public class ChainVIPPrepaidFlow implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1032080404175097695L;
	public static final String OPERATION_TYPE_DEPOSIT = "D";
	public static final String OPERATION_TYPE_CONSUMP = "C";
	public static final String DEPOSIT_TYPE_CASH = "C";
	public static final String DEPOSIT_TYPE_CARD = "D";
	private int id;

	private ChainStore chainStore;
	private ChainVIPCard vipCard; 
	private ChainUserInfor operator;
	private int status;
	/**
	 * D:存钱
	 * C:消费
	 */
	private String operationType = "";
	/**
	 * C:现金
	 * D:刷卡
	 */
	private String depositType = "";
	private double amount;
	private String comment = "";
	private java.sql.Date date;
	private Date createDate;
	
	/**
	 * 页面展示用
	 */
	private double accumulateVipPrepaid = 0;
	
	
	public double getAccumulateVipPrepaid() {
		return accumulateVipPrepaid;
	}
	public void setAccumulateVipPrepaid(double accumulateVipPrepaid) {
		this.accumulateVipPrepaid = accumulateVipPrepaid;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

	public ChainStore getChainStore() {
		return chainStore;
	}
	public void setChainStore(ChainStore chainStore) {
		this.chainStore = chainStore;
	}
	public ChainVIPCard getVipCard() {
		return vipCard;
	}
	public void setVipCard(ChainVIPCard vipCard) {
		this.vipCard = vipCard;
	}
	public ChainUserInfor getOperator() {
		return operator;
	}
	public void setOperator(ChainUserInfor operator) {
		this.operator = operator;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getOperationType() {
		return operationType;
	}
	public void setOperationType(String operationType) {
		this.operationType = operationType;
	}
	public String getDepositType() {
		return depositType;
	}
	public void setDepositType(String depositType) {
		this.depositType = depositType;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(java.sql.Date date) {
		this.date = date;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public void clearData() {
		this.setAmount(0);
		this.setDate(null);
		this.setComment("");
		this.setDepositType("");
		this.setOperationType("");
	}
	
	
}