package com.onlineMIS.ORM.entity.chainS.vip;

import org.springframework.beans.BeanUtils;

import com.onlineMIS.common.Common_util;

public class ChainVIPPrepaidFlowUI extends ChainVIPPrepaidFlow {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2449311953108767978L;
	
	public ChainVIPPrepaidFlowUI(ChainVIPPrepaidFlow c){
		BeanUtils.copyProperties(c, this);
		String amount = String.valueOf(Common_util.roundDouble(this.getAmount(), 1));
		if (this.getOperationType().equalsIgnoreCase(ChainVIPPrepaidFlow.OPERATION_TYPE_CONSUMP)){
			prepaidType = "预存金消费";
			consump = amount;
		} else if (this.getDepositType().equalsIgnoreCase(ChainVIPPrepaidFlow.DEPOSIT_TYPE_CARD)){
			depositCard = "充值-刷卡";
			consump = amount;
		} else if (this.getDepositType().equalsIgnoreCase(ChainVIPPrepaidFlow.DEPOSIT_TYPE_CASH)){
			prepaidType = "充值-现金";
			depositCash = amount;
		}
	}

	private String prepaidType = "";
	private String depositCash = "-";
	private String depositCard = "-";
	private String consump = "-";
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
	
	
}
