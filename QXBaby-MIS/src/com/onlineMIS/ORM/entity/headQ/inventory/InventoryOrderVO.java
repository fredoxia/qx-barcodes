package com.onlineMIS.ORM.entity.headQ.inventory;

import java.util.Date;

public class InventoryOrderVO {
	private int id;
	private String clientName = "";
	private Date startTime;
	private Date completeTime;
	private int importTimes;
	private String PDAUserName ="";
	private String keeperName = "";
	private int totalQ ;
	private double totalWholeSales;
	private String comment = "";
	private int status;
	private String process = "";
	private String orderType = "";
	private int orderTypeI ;
	private boolean isAuthorizedToEdit;
	
	public InventoryOrderVO(InventoryOrder i){
		this.setId(i.getOrder_ID());
		this.setClientName(i.getClient_name());
		this.setStartTime(i.getOrder_StartTime());
		this.setCompleteTime(i.getOrder_EndTime());
		this.setImportTimes(i.getImportTimes());
		if (i.getPdaScanner() != null)
		   this.setPDAUserName(i.getPdaScanner().getName());
		if (i.getOrder_Keeper() != null)
			this.setKeeperName(i.getOrder_Keeper().getName());
		this.setTotalQ(i.getTotalQuantity());
		this.setTotalWholeSales(i.getTotalWholePrice());
		this.setComment(i.getComment());
		this.setProcess(i.getOrder_Status_s());
		this.setOrderType(i.getOrder_type_ws());
		this.setStatus(i.getOrder_Status());
		this.setOrderTypeI(i.getOrder_type());
	}
	
	public int getOrderTypeI() {
		return orderTypeI;
	}

	public void setOrderTypeI(int orderTypeI) {
		this.orderTypeI = orderTypeI;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getClientName() {
		return clientName;
	}
	public void setClientName(String clientName) {
		this.clientName = clientName;
	}
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public Date getCompleteTime() {
		return completeTime;
	}
	public void setCompleteTime(Date completeTime) {
		this.completeTime = completeTime;
	}
	public int getImportTimes() {
		return importTimes;
	}
	public void setImportTimes(int importTimes) {
		this.importTimes = importTimes;
	}
	public String getPDAUserName() {
		return PDAUserName;
	}
	public void setPDAUserName(String pDAUserName) {
		PDAUserName = pDAUserName;
	}

	public String getKeeperName() {
		return keeperName;
	}

	public void setKeeperName(String keeperName) {
		this.keeperName = keeperName;
	}

	public int getTotalQ() {
		return totalQ;
	}
	public void setTotalQ(int totalQ) {
		this.totalQ = totalQ;
	}
	public double getTotalWholeSales() {
		return totalWholeSales;
	}
	public void setTotalWholeSales(double totalWholeSales) {
		this.totalWholeSales = totalWholeSales;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public String getProcess() {
		return process;
	}
	public void setProcess(String process) {
		this.process = process;
	}
	public String getOrderType() {
		return orderType;
	}
	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

	public boolean getIsAuthorizedToEdit() {
		return isAuthorizedToEdit;
	}

	public void setIsAuthorizedToEdit(boolean isAuthorizedToEdit) {
		this.isAuthorizedToEdit = isAuthorizedToEdit;
	}

	
	
}
