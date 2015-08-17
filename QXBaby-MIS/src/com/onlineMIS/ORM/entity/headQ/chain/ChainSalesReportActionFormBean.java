package com.onlineMIS.ORM.entity.headQ.chain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import net.sf.json.JSONObject;
import net.sf.json.util.JSONBuilder;

import com.onlineMIS.ORM.DAO.headQ.user.NewsService;
import com.onlineMIS.ORM.entity.headQ.inventory.InventoryOrder;
import com.onlineMIS.common.loggerLocal;

public class ChainSalesReportActionFormBean implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1887830780435635089L;
	private int year;
	private int quarter;
	private List<Integer> brands = new ArrayList<Integer>();
	private Date startDate = new Date();
	private Date endDate = new Date();
    private InventoryOrder order = new InventoryOrder();

	public InventoryOrder getOrder() {
		return order;
	}
	public void setOrder(InventoryOrder order) {
		this.order = order;
	}
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	public int getQuarter() {
		return quarter;
	}
	public void setQuarter(int quarter) {
		this.quarter = quarter;
	}
	public List<Integer> getBrands() {
		return brands;
	}
	public void setBrands(List<Integer> brands) {
		this.brands = brands;
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
	

}
