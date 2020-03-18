package com.onlineMIS.ORM.entity.chainS.sales;


import java.util.Date;

import com.onlineMIS.ORM.entity.chainS.user.ChainStore;
import com.onlineMIS.ORM.entity.chainS.user.ChainUserInfor;
import com.onlineMIS.ORM.entity.headQ.barcodeGentor.ProductBarcode;


public class ChainErrorProduct  {
	/**
	 * 
	 */
	private static final long serialVersionUID = 526914422972089273L;

	
    private int id;
	private ChainStore chainStore = new ChainStore();
	private ChainUserInfor creator;
	private Date createDate;
	private Date lastUpdateTime;
	private String commentHistory;
	private int status;
	private int errorType;
	private ProductBarcode pb = new ProductBarcode();
	
	
	public ProductBarcode getPb() {
		return pb;
	}
	public void setPb(ProductBarcode pb) {
		this.pb = pb;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public ChainStore getChainStore() {
		return chainStore;
	}
	public void setChainStore(ChainStore chainStore) {
		this.chainStore = chainStore;
	}
	public ChainUserInfor getCreator() {
		return creator;
	}
	public void setCreator(ChainUserInfor creator) {
		this.creator = creator;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public Date getLastUpdateTime() {
		return lastUpdateTime;
	}
	public void setLastUpdateTime(Date lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}
	public String getCommentHistory() {
		return commentHistory;
	}
	public void setCommentHistory(String commentHistory) {
		this.commentHistory = commentHistory;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public int getErrorType() {
		return errorType;
	}
	public void setErrorType(int errorType) {
		this.errorType = errorType;
	}
}
