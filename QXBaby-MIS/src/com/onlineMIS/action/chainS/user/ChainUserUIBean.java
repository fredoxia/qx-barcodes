package com.onlineMIS.action.chainS.user;

import java.util.ArrayList;
import java.util.List;

import com.onlineMIS.ORM.entity.chainS.report.ChainWMRank;
import com.onlineMIS.ORM.entity.chainS.user.ChainStore;
import com.onlineMIS.ORM.entity.chainS.user.ChainUserInfor;
import com.onlineMIS.ORM.entity.chainS.user.ChainRoleType;
import com.onlineMIS.ORM.entity.headQ.user.News;

/**
 * this bean is for the chain user action's UI
 * @author fredo
 *
 */
public class ChainUserUIBean {
	private ChainUserInfor chainUserInfor;
	private ChainWMRank chainWMRank;
	private List<ChainWMRank> myDailyRank = new ArrayList<ChainWMRank>();
	private List<ChainUserInfor> chainUserInfors = new ArrayList<ChainUserInfor>();
	private List<ChainStore> chainStores = new ArrayList<ChainStore>();
	private List<ChainRoleType> chainRoleTypes = new ArrayList<ChainRoleType>();
    private List<News> news = new ArrayList<News>();
    private String specialMsg = "";


	public List<ChainWMRank> getMyDailyRank() {
		return myDailyRank;
	}
	public void setMyDailyRank(List<ChainWMRank> myDailyRank) {
		this.myDailyRank = myDailyRank;
	}
	public ChainWMRank getChainWMRank() {
		return chainWMRank;
	}
	public void setChainWMRank(ChainWMRank chainWMRank) {
		this.chainWMRank = chainWMRank;
	}
	public List<ChainRoleType> getChainRoleTypes() {
		return chainRoleTypes;
	}
	public void setChainRoleTypes(List<ChainRoleType> chainRoleTypes) {
		this.chainRoleTypes = chainRoleTypes;
	}
	public List<News> getNews() {
		return news;
	}
	public void setNews(List<News> news) {
		this.news = news;
	}   	
	
	public ChainUserInfor getChainUserInfor() {
		return chainUserInfor;
	}
	public void setChainUserInfor(ChainUserInfor chainUserInfor) {
		this.chainUserInfor = chainUserInfor;
	}
	public List<ChainUserInfor> getChainUserInfors() {
		return chainUserInfors;
	}
	public void setChainUserInfors(List<ChainUserInfor> chainUserInfors) {
		this.chainUserInfors = chainUserInfors;
	}
	public List<ChainStore> getChainStores() {
		return chainStores;
	}

	public void setChainStores(List<ChainStore> chainStores) {
		this.chainStores = chainStores;
	}
	public String getSpecialMsg() {
		return specialMsg;
	}
	public void setSpecialMsg(String specialMsg) {
		this.specialMsg = specialMsg;
	}
	
}
