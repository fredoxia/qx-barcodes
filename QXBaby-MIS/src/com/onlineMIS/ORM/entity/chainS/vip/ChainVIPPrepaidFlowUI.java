package com.onlineMIS.ORM.entity.chainS.vip;

import org.springframework.beans.BeanUtils;

import com.onlineMIS.common.Common_util;

public class ChainVIPPrepaidFlowUI extends ChainVIPPrepaidFlow {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2449311953108767978L;
	
	public ChainVIPPrepaidFlowUI(){
		
	}
	
	public ChainVIPPrepaidFlowUI(ChainVIPPrepaidFlow c){
		BeanUtils.copyProperties(c, this);
		String amount = String.valueOf(Common_util.roundDouble(Math.abs(this.getAmount()), 1));
		if (this.getOperationType().equalsIgnoreCase(ChainVIPPrepaidFlow.OPERATION_TYPE_CONSUMP)){
			prepaidType = "消费-预存金";
			consump = amount;
		} else if (this.getDepositType().equalsIgnoreCase(ChainVIPPrepaidFlow.DEPOSIT_TYPE_CARD)){
			prepaidType = "充值";
			depositCard = amount;
		} else if (this.getDepositType().equalsIgnoreCase(ChainVIPPrepaidFlow.DEPOSIT_TYPE_CASH)){
			prepaidType = "充值";
			depositCash = amount;
		}
		dateUI = Common_util.dateFormat.format(c.getDateD());
		
		if (this.getStatus() == ChainVIPPrepaidFlow.STATUS_SUCCESS)
			statusS = "完成";
		else if  (this.getStatus() == ChainVIPPrepaidFlow.STATUS_CANCEL)
		    statusS = "红冲";

	}

	private String prepaidType = "";
	private String depositCash = "-";
	private String depositCard = "-";
	private String consump = "-";
	private String dateUI = "";
	private String statusS = "";
	
	
	public String getDateUI() {
		return dateUI;
	}
	public void setDateUI(String dateUI) {
		this.dateUI = dateUI;
	}
	public String getPrepaidType() {
		return prepaidType;
	}
	public void setPrepaidType(String prepaidType) {
		this.prepaidType = prepaidType;
	}
	public String getDepositCash() {
		return depositCash;
	}
	public void setDepositCash(String depositCash) {
		this.depositCash = depositCash;
	}
	public String getDepositCard() {
		return depositCard;
	}
	public void setDepositCard(String depositCard) {
		this.depositCard = depositCard;
	}
	public String getConsump() {
		return consump;
	}
	public void setConsump(String consump) {
		this.consump = consump;
	}

	public String getStatusS() {
		return statusS;
	}
	public void setStatusS(String statusS) {
		this.statusS = statusS;
	}
	
}
