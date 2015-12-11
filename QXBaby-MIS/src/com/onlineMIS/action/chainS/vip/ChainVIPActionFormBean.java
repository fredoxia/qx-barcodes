package com.onlineMIS.action.chainS.vip;

import java.io.File;
import java.sql.Date;

import com.onlineMIS.ORM.entity.base.Pager;
import com.onlineMIS.ORM.entity.chainS.chainMgmt.ChainStoreConf;
import com.onlineMIS.ORM.entity.chainS.user.ChainStore;
import com.onlineMIS.ORM.entity.chainS.vip.ChainVIPCard;
import com.onlineMIS.ORM.entity.chainS.vip.ChainVIPPrepaidFlow;
import com.onlineMIS.ORM.entity.chainS.vip.ChainVIPType;

public class ChainVIPActionFormBean {
	private Pager pager = new Pager();
	private ChainVIPType vipType = new ChainVIPType();
	private ChainVIPCard vipCard = new ChainVIPCard();
	private ChainVIPPrepaidFlow vipPrepaid = new ChainVIPPrepaidFlow();
	private int selectedCardId;
	private ChainStore chainStore = new ChainStore();
	private ChainStoreConf chainStoreConf = new ChainStoreConf();
	private boolean canEditOrderDate = false;
	
    //file upload
    private File vips = null;
    private String vipsContentType;
    private String vipsFileName;
    
    private Date startDate = new Date(new java.util.Date().getTime());
    private Date endDate = new Date(new java.util.Date().getTime());
   
    /**
     * 搜索时的search type
     * 1： 当日生日VIP
     * 2： 当周生日VIP
     * 3: 当月生日VIP
     */
    private int searchType;
    private Date birthday = new Date(new java.util.Date().getTime());
    private boolean overWrite;
    
    /**
     * 积分
     */
    private int vipScore;
    private String comment;
    

	public ChainStoreConf getChainStoreConf() {
		return chainStoreConf;
	}

	public void setChainStoreConf(ChainStoreConf chainStoreConf) {
		this.chainStoreConf = chainStoreConf;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public boolean getCanEditOrderDate() {
		return canEditOrderDate;
	}

	public void setCanEditOrderDate(boolean canEditOrderDate) {
		this.canEditOrderDate = canEditOrderDate;
	}

	public ChainVIPPrepaidFlow getVipPrepaid() {
		return vipPrepaid;
	}

	public void setVipPrepaid(ChainVIPPrepaidFlow vipPrepaid) {
		this.vipPrepaid = vipPrepaid;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public int getVipScore() {
		return vipScore;
	}

	public void setVipScore(int vipScore) {
		this.vipScore = vipScore;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public int getSearchType() {
		return searchType;
	}

	public void setSearchType(int searchType) {
		this.searchType = searchType;
	}

	public boolean getOverWrite() {
		return overWrite;
	}

	public void setOverWrite(boolean overWrite) {
		this.overWrite = overWrite;
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



	public ChainVIPCard getVipCard() {
		return vipCard;
	}

	public void setVipCard(ChainVIPCard vipCard) {
		this.vipCard = vipCard;
	}

	public ChainVIPType getVipType() {
		return vipType;
	}

	public void setVipType(ChainVIPType vipType) {
		this.vipType = vipType;
	}

	public File getVips() {
		return vips;
	}

	public void setVips(File vips) {
		this.vips = vips;
	}

	public String getVipsContentType() {
		return vipsContentType;
	}

	public void setVipsContentType(String vipsContentType) {
		this.vipsContentType = vipsContentType;
	}

	public String getVipsFileName() {
		return vipsFileName;
	}

	public void setVipsFileName(String vipsFileName) {
		this.vipsFileName = vipsFileName;
	}

	public int getSelectedCardId() {
		return selectedCardId;
	}

	public void setSelectedCardId(int selectedCardId) {
		this.selectedCardId = selectedCardId;
	}




//	private int selectedVIPType;
//
//	public int getSelectedVIPType() {
//		return selectedVIPType;
//	}
//
//	public void setSelectedVIPType(int selectedVIPType) {
//		this.selectedVIPType = selectedVIPType;
//	}
	

}
