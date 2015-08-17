package com.onlineMIS.ORM.entity.chainS.vip;

import java.io.Serializable;
import java.util.Date;

import com.onlineMIS.ORM.entity.headQ.user.UserInfor;

public class ChainVIPPreaidFlow implements Serializable{
	private int id;
	private int chainId;
	private UserInfor operator;
	private int status;
	private char operationType;
	private char depositType;
	private double amount;
	private String comment;
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
	public UserInfor getOperator() {
		return operator;
	}
	public void setOperator(UserInfor operator) {
		this.operator = operator;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public char getOperationType() {
		return operationType;
	}
	public void setOperationType(char operationType) {
		this.operationType = operationType;
	}
	public char getDepositType() {
		return depositType;
	}
	public void setDepositType(char depositType) {
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
