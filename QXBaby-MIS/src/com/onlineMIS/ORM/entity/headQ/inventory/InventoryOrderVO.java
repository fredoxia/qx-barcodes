package com.onlineMIS.ORM.entity.headQ.inventory;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

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
	private double totalRetailSales;
	private String comment = "";
	private int status;
	private String process = "";
	private String orderType = "";
	private int orderTypeI ;
	private boolean isAuthorizedToEdit;
	
	
	private int chainStatusIndicator = 0;
	private String chainStatusS = "";
	private String chainConfirmComment = "";
	private Date chainConfirmDate;
	private String orderTypeC ="";
	private static Map<Integer, String> chainConfirmMap = new HashMap<Integer, String>();
	static {
		chainConfirmMap.put(InventoryOrder.STATUS_CHAIN_CONFIRM, "清点确认");
		chainConfirmMap.put(InventoryOrder.STATUS_CHAIN_NOT_CONFIRM, "未确认");
		chainConfirmMap.put(InventoryOrder.STATUS_CHAIN_PRODUCT_INCORRECT, "订单错误");
		chainConfirmMap.put(InventoryOrder.STATUS_SYSTEM_CONFIRM, "系统自动确认");
	}
	
	public InventoryOrderVO(){
		
	}
	
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
		this.setOrderTypeC(i.getOrder_type_chain());
		this.setStatus(i.getOrder_Status());
		this.setOrderTypeI(i.getOrder_type());
		this.setChainStatusIndicator(i.getChainConfirmStatus());
		this.setChainConfirmComment(i.getChainConfirmComment());
		this.calculateChainStatusS(i.getChainConfirmStatus());
		this.setTotalRetailSales(i.getTotalRetailPrice());
		this.setChainConfirmDate(i.getChainConfirmDate());
	}
	
	private void calculateChainStatusS(int chainConfirmStatus) {
		String confirmStatusS = chainConfirmMap.get(chainConfirmStatus);
		if (confirmStatusS == null)
		    setChainStatusS("系统错误");
		else 
			setChainStatusS(confirmStatusS);
	}

	public Date getChainConfirmDate() {
		return chainConfirmDate;
	}

	public void setChainConfirmDate(Date chainConfirmDate) {
		this.chainConfirmDate = chainConfirmDate;
	}

	public double getTotalRetailSales() {
		return totalRetailSales;
	}

	public void setTotalRetailSales(double totalRetailSales) {
		this.totalRetailSales = totalRetailSales;
	}

	public String getOrderTypeC() {
		return orderTypeC;
	}

	public void setOrderTypeC(String orderTypeC) {
		this.orderTypeC = orderTypeC;
	}

	public String getChainConfirmComment() {
		return chainConfirmComment;
	}

	public void setChainConfirmComment(String chainConfirmComment) {
		this.chainConfirmComment = chainConfirmComment;
	}

	public int getChainStatusIndicator() {
		return chainStatusIndicator;
	}

	public void setChainStatusIndicator(int chainStatusIndicator) {
		this.chainStatusIndicator = chainStatusIndicator;
	}

	public String getChainStatusS() {
		return chainStatusS;
	}

	public void setChainStatusS(String chainStatusS) {
		this.chainStatusS = chainStatusS;
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

	public static Map<Integer, String> getChainConfirmMap() {
		return chainConfirmMap;
	}

	
	
}
