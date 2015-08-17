package com.onlineMIS.ORM.entity.chainS.report;

public class ChainPurchaseReport extends ChainReport {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1593534836637677380L;
	private int purchaseQuantitySum;
	private int returnQuantitySum;
	private double purchaseSum;
	private double returnSum;
	
	public ChainPurchaseReport(){
		
	}
	
	public ChainPurchaseReport(int type, int purchaseQSum, int returnQSum, double purchaseSum, double returnSum){
		this.type = type;
		this.purchaseQuantitySum = purchaseQSum;
		this.purchaseSum = purchaseSum;
		this.returnQuantitySum = returnQSum;
		this.returnSum = returnSum;
	}
	
	public int getPurchaseQuantitySum() {
		return purchaseQuantitySum;
	}
	public void setPurchaseQuantitySum(int purchaseQuantitySum) {
		this.purchaseQuantitySum = purchaseQuantitySum;
	}
	public int getReturnQuantitySum() {
		return returnQuantitySum;
	}
	public void setReturnQuantitySum(int returnQuantitySum) {
		this.returnQuantitySum = returnQuantitySum;
	}
	public double getPurchaseSum() {
		return purchaseSum;
	}
	public void setPurchaseSum(double purchaseSum) {
		this.purchaseSum = purchaseSum;
	}
	public double getReturnSum() {
		return returnSum;
	}
	public void setReturnSum(double returnSum) {
		this.returnSum = returnSum;
	}
	
	

}
