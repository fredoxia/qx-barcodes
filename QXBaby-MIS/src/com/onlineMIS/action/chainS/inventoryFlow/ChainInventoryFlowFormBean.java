package com.onlineMIS.action.chainS.inventoryFlow;

import java.io.File;
import java.io.InputStream;
import java.sql.Date;

import com.onlineMIS.ORM.entity.base.Pager;
import com.onlineMIS.ORM.entity.chainS.inventoryFlow.ChainInventoryFlowOrder;
import com.onlineMIS.ORM.entity.chainS.user.ChainStore;
import com.onlineMIS.action.chainS.ChainActionFormBaseBean;
import com.onlineMIS.common.Common_util;

public class ChainInventoryFlowFormBean extends ChainActionFormBaseBean{
	
	private ChainInventoryFlowOrder flowOrder = new ChainInventoryFlowOrder();
	private int chainId ;
	
	private ChainStore chainStore;


	private String barcode;
	/**
	 * 
	 * the date are used to do the search order
	 */
	private Date searchStartTime = new Date(new java.util.Date().getTime());
    private Date searchEndTime = new Date(new java.util.Date().getTime());
    
    //file upload
    private File inventory = null;
    private String inventoryContentType;
    private String inventoryFileName;
    
    private Pager pager = new Pager();
    private String url = "";
    
    //file download
	private InputStream fileStream;
	private String fileName;
	
	//for the current inventory
	private int yearId;
	private int quarterId;
	private int brandId;
	
	//for the chain inventory excle report
	private int reportType;
	
	public ChainStore getChainStore() {
		return chainStore;
	}

	public void setChainStore(ChainStore chainStore) {
		this.chainStore = chainStore;
	}
	public String getBarcode() {
		return barcode;
	}

	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}

	public int getReportType() {
		return reportType;
	}

	public void setReportType(int reportType) {
		this.reportType = reportType;
	}

	public int getYearId() {
		return yearId;
	}

	public void setYearId(int yearId) {
		this.yearId = yearId;
	}

	public int getQuarterId() {
		return quarterId;
	}

	public void setQuarterId(int quarterId) {
		this.quarterId = quarterId;
	}

	public int getBrandId() {
		return brandId;
	}

	public void setBrandId(int brandId) {
		this.brandId = brandId;
	}

	public InputStream getFileStream() {
		return fileStream;
	}

	public void setFileStream(InputStream fileStream) {
		this.fileStream = fileStream;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName =Common_util.encodeAttachment(fileName);
//		this.fileName = fileName;
	}

	public int getChainId() {
		return chainId;
	}

	public void setChainId(int chainId) {
		this.chainId = chainId;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
    
	public Pager getPager() {
		return pager;
	}
	public void setPager(Pager pager) {
		this.pager = pager;
	}
	public Date getSearchStartTime() {
		return searchStartTime;
	}

	public void setSearchStartTime(Date searchStartTime) {
		this.searchStartTime = searchStartTime;
	}

	public Date getSearchEndTime() {
		return searchEndTime;
	}

	public void setSearchEndTime(Date searchEndTime) {
		this.searchEndTime = searchEndTime;
	}

	public ChainInventoryFlowOrder getFlowOrder() {
		return flowOrder;
	}

	public void setFlowOrder(ChainInventoryFlowOrder flowOrder) {
		this.flowOrder = flowOrder;
	}
	
	
	public File getInventory() {
		return inventory;
	}

	public void setInventory(File inventory) {
		this.inventory = inventory;
	}

	public String getInventoryContentType() {
		return inventoryContentType;
	}

	public void setInventoryContentType(String inventoryContentType) {
		this.inventoryContentType = inventoryContentType;
	}

	public String getInventoryFileName() {
		return inventoryFileName;
	}

	public void setInventoryFileName(String inventoryFileName) {
		this.inventoryFileName = inventoryFileName;
	}

	/**
	 * to clear the form bean
	 */
	public void finalize(){
		flowOrder = new ChainInventoryFlowOrder();
	}

	@Override
	public String toString() {
		return "ChainInventoryFlowFormBean [chainId=" + chainId
				+ ", searchStartTime=" + searchStartTime + ", searchEndTime="
				+ searchEndTime + ", pager=" + pager + ", yearId=" + yearId
				+ ", quarterId=" + quarterId + ", brandId=" + brandId + "]";
	}
	
	
	
}
