package com.onlineMIS.action.chainS.vip;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.onlineMIS.ORM.DAO.chainS.vip.ChainVIPCardImpl;
import com.onlineMIS.ORM.entity.chainS.chainMgmt.ChainStoreConf;
import com.onlineMIS.ORM.entity.chainS.user.ChainStore;
import com.onlineMIS.ORM.entity.chainS.vip.ChainVIPCard;
import com.onlineMIS.ORM.entity.chainS.vip.ChainVIPScore;
import com.onlineMIS.ORM.entity.chainS.vip.ChainVIPType;
import com.onlineMIS.common.Common_util;

public class ChainVIPActionUIBean {
	public static int SEARCH_TYPE_BIR_TODAY = 1;
	public static int SEARCH_TYPE_BIR_WEEK = 2;
	public static int SEARCH_TYPE_BIR_MONTH = 3;
	public static int SEARCH_TYPE_PART_DATE = 0;
	
	private List<ChainVIPType> chainVIPTypes = new ArrayList<ChainVIPType>();
    private List<ChainVIPCard> chainVIPCards = new ArrayList<ChainVIPCard>();
    
    private List<ChainStore> chainStores = new ArrayList<ChainStore>();
    private Map<Integer, String> genders= new HashMap<Integer, String>();
    private Map<Integer, String> cardStatus= new HashMap<Integer, String>();
    
	private Map<Integer, String> searchTypes = new HashMap<Integer, String>();
    
    private List<ChainVIPScore> vipConumps = new ArrayList<ChainVIPScore>();
    private ChainVIPScore vipConsumpFooter = new ChainVIPScore();
    private ChainStoreConf chainStoreConf = new ChainStoreConf();
    public int good;

    
	public ChainStoreConf getChainStoreConf() {
		return chainStoreConf;
	}

	public void setChainStoreConf(ChainStoreConf chainStoreConf) {
		this.chainStoreConf = chainStoreConf;
	}

	public List<ChainVIPScore> getVipConumps() {
		return vipConumps;
	}

	public void setVipConumps(List<ChainVIPScore> vipConumps) {
		this.vipConumps = vipConumps;
	}

	public ChainVIPScore getVipConsumpFooter() {
		return vipConsumpFooter;
	}

	public void setVipConsumpFooter(ChainVIPScore vipConsumpFooter) {
		this.vipConsumpFooter = vipConsumpFooter;
	}


	public List<ChainStore> getChainStores() {
		return chainStores;
	}
	public void setChainStores(List<ChainStore> chainStores) {
		this.chainStores = chainStores;
	}
	public Map<Integer, String> getGenders() {
		return Common_util.getGender();
	}
	

	public Map<Integer, String> getSearchTypes() {
		searchTypes.put(SEARCH_TYPE_PART_DATE, "特定日期");
		searchTypes.put(SEARCH_TYPE_BIR_TODAY, "当天生日");
		searchTypes.put(SEARCH_TYPE_BIR_WEEK, "当周生日");
		searchTypes.put(SEARCH_TYPE_BIR_MONTH, "当月生日");
		
		return searchTypes;
	}
	
    public  Map<Integer, String> getCardStatus() {
		cardStatus.put(ChainVIPCard.STATUS_GOOD, "正常");
		cardStatus.put(ChainVIPCard.STATUS_LOST, "挂失");
		cardStatus.put(ChainVIPCard.STATUS_STOP, "停用");
		return cardStatus;
	}

	public void setSearchTypes(Map<Integer, String> searchTypes) {
		this.searchTypes = searchTypes;
	}

	public List<ChainVIPCard> getChainVIPCards() {
		return chainVIPCards;
	}
	public void setChainVIPCards(List<ChainVIPCard> chainVIPCards) {
		this.chainVIPCards = chainVIPCards;
	}
	public List<ChainVIPType> getChainVIPTypes() {
		return chainVIPTypes;
	}
	public void setChainVIPTypes(List<ChainVIPType> chainVIPTypes) {
		this.chainVIPTypes = chainVIPTypes;
	}
	
}
