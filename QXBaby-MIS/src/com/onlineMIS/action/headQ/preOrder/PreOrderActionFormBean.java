package com.onlineMIS.action.headQ.preOrder;

import java.io.InputStream;

import com.onlineMIS.ORM.entity.base.Pager;
import com.onlineMIS.ORM.entity.chainS.user.ChainStore;
import com.onlineMIS.action.chainS.ChainActionFormBaseBean;
import com.onlineMIS.common.Common_util;

public class PreOrderActionFormBean extends ChainActionFormBaseBean{
	private ChainStore chainStore = new ChainStore();
    private Pager pager = new Pager(); 
    private PreOrderUIBean order = new PreOrderUIBean();
    //file download
	private InputStream fileStream;
	private String fileName;
	
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
	}
	public PreOrderUIBean getOrder() {
		return order;
	}
	public void setOrder(PreOrderUIBean order) {
		this.order = order;
	}

	public ChainStore getChainStore() {
		return chainStore;
	}
	public void setChainStore(ChainStore chainStore) {
		this.chainStore = chainStore;
	}
	public Pager getPager() {
		return pager;
	}
	public void setPager(Pager pager) {
		this.pager = pager;
	}
	
}
