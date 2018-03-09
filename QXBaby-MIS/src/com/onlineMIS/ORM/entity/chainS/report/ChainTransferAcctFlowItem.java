package com.onlineMIS.ORM.entity.chainS.report;

public class ChainTransferAcctFlowItem {
	private String chainStoreName = "";
	private double transferAcctFlow = 0;
	
	public ChainTransferAcctFlowItem(){
		
	}
	
	public ChainTransferAcctFlowItem(String chainStoreName, double acctFlow){
		this.chainStoreName = chainStoreName;
		this.transferAcctFlow = acctFlow;
	}
	
	public String getChainStoreName() {
		return chainStoreName;
	}
	public void setChainStoreName(String chainStoreName) {
		this.chainStoreName = chainStoreName;
	}
	public double getTransferAcctFlow() {
		return transferAcctFlow;
	}
	public void setTransferAcctFlow(double transferAcctFlow) {
		this.transferAcctFlow = transferAcctFlow;
	}
	
	
}
