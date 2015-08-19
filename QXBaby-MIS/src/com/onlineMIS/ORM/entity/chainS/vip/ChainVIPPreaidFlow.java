package com.onlineMIS.ORM.entity.chainS.vip;

import java.io.Serializable;
import java.util.Date;

import com.onlineMIS.ORM.entity.chainS.user.ChainUserInfor;
import com.onlineMIS.ORM.entity.headQ.user.UserInfor;

public class ChainVIPPreaidFlow implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1032080404175097695L;
	public static final String OPERATION_TYPE_DEPOSIT = "D";
	public static final String OPERATION_TYPE_CONSUMP = "C";
	public static final String DEPOSIT_TYPE_CASH = "C";
	public static final String DEPOSIT_TYPE_CARD = "D";
	private int id;
	private int chainId;
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
	private Date date;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getChainId() {
		return chainId;
	}
	public void setChainId(int chainStoreId) {
		this.chainId = chainStoreId;
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
	public void setDate(Date date) {
		this.date = date;
	}
	
	
}
