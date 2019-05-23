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
		String amount = String.valueOf(Common_util.roundDouble(this.getAmount(), 1));
		String calculatedAmt = String.valueOf(Common_util.roundDouble(this.getCalculatedAmt(), 1));
		if (this.getOperationType().equalsIgnoreCase(ChainVIPPrepaidFlow.OPERATION_TYPE_CONSUMP)){
			prepaidType = "消费-预存金";
			consump = calculatedAmt;
		} else if (this.getDepositType().equalsIgnoreCase(ChainVIPPrepaidFlow.DEPOSIT_TYPE_CARD)){
			prepaidType = "充值";
			depositCard = amount;
		} else if (this.getDepositType().equalsIgnoreCase(ChainVIPPrepaidFlow.DEPOSIT_TYPE_CASH)){
			prepaidType = "充值";
			depositCash = amount;
		} else if (this.getDepositType().equalsIgnoreCase(ChainVIPPrepaidFlow.DEPOSIT_TYPE_WECHAT)){
			prepaidType = "充值";
			depositWechat = amount;
		} else if (this.getDepositType().equalsIgnoreCase(ChainVIPPrepaidFlow.DEPOSIT_TYPE_ALIPAY)){
			prepaidType = "充值";
			depositAlipay = amount;
		}
		dateUI = Common_util.dateFormat.format(c.getDateD());
		
		calculatedAmts = String.valueOf(Common_util.roundDouble(this.getCalculatedAmt(), 1));
		
		if (this.getStatus() == ChainVIPPrepaidFlow.STATUS_SUCCESS)
			statusS = "完成";
		else if  (this.getStatus() == ChainVIPPrepaidFlow.STATUS_CANCEL)
		    statusS = "红冲";

	}

	private String prepaidType = "";
	private String depositCash = "-";
	private String depositCard = "-";
	private String depositWechat = "-";
	private String depositAlipay = "-";
	private String consump = "-";
	private String dateUI = "";
	private String statusS = "";
	private String calculatedAmts = "";
	
	
	public String getDepositWechat() {
		return depositWechat;
	}

	public void setDepositWechat(String depositWechat) {
		this.depositWechat = depositWechat;
	}

	public String getDepositAlipay() {
		return depositAlipay;
	}

	public void setDepositAlipay(String depositAlipay) {
		this.depositAlipay = depositAlipay;
	}

	public String getCalculatedAmts() {
		return calculatedAmts;
	}

	public void setCalculatedAmt(String calculatedAmt) {
		this.calculatedAmts = calculatedAmt;
	}

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
