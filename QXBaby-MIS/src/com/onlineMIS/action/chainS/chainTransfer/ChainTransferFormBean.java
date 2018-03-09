package com.onlineMIS.action.chainS.chainTransfer;

import java.io.File;
import java.io.InputStream;
import java.sql.Date;

import com.onlineMIS.ORM.entity.base.Pager;
import com.onlineMIS.ORM.entity.chainS.inventoryFlow.ChainInventoryFlowOrder;
import com.onlineMIS.ORM.entity.chainS.inventoryFlow.ChainTransferOrder;
import com.onlineMIS.ORM.entity.chainS.user.ChainStore;
import com.onlineMIS.action.chainS.ChainActionFormBaseBean;
import com.onlineMIS.common.Common_util;

public class ChainTransferFormBean extends ChainActionFormBaseBean{
	
	private ChainTransferOrder transferOrder = new ChainTransferOrder();
	private ChainStore chainStore;
	/**
	 * 
	 * the date are used to do the search order
	 */
	private Date searchStartTime = new Date(new java.util.Date().getTime());
    private Date searchEndTime = new Date(new java.util.Date().getTime());
    private Pager pager = new Pager();
    
    //file download
	private InputStream fileStream;
	private String fileName;

	private String productCode;
	
	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public ChainTransferOrder getTransferOrder() {
		return transferOrder;
	}

	public void setTransferOrder(ChainTransferOrder transferOrder) {
		this.transferOrder = transferOrder;
	}

	public ChainStore getChainStore() {
		return chainStore;
	}

	public void setChainStore(ChainStore chainStore) {
		this.chainStore = chainStore;
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

	
	
	
}
