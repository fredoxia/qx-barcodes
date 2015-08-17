package com.onlineMIS.ORM.entity.chainS.sales;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.onlineMIS.ORM.entity.chainS.user.ChainStore;

public class ChainDailySalesLineChart {
	/**
	 * 数据类型, 销售量，销售额
	 */
	private int type;
	private ChainStore chainStore;
	private String title = "";
	private List<String> baseLine = new ArrayList<String>();
	private Map<String, Double> chainDataSet = new HashMap<String, Double>();
	private Map<String, Double> avgDataSet = new HashMap<String, Double>();
	
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public ChainStore getChainStore() {
		return chainStore;
	}
	public void setChainStore(ChainStore chainStore) {
		this.chainStore = chainStore;
	}
	public List<String> getBaseLine() {
		return baseLine;
	}
	public void setBaseLine(List<String> baseLine) {
		this.baseLine = baseLine;
	}
	public Map<String, Double> getChainDataSet() {
		return chainDataSet;
	}
	public void setChainDataSet(Map<String, Double> chainDataSet) {
		this.chainDataSet = chainDataSet;
	}
	public Map<String, Double> getAvgDataSet() {
		return avgDataSet;
	}
	public void setAvgDataSet(Map<String, Double> avgDataSet) {
		this.avgDataSet = avgDataSet;
	}
	
	
	
}
