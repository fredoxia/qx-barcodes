package com.onlineMIS.ORM.entity.headQ.chain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.onlineMIS.ORM.entity.headQ.barcodeGentor.Brand;
/**
 * this class is for chain sales report of each brand
 * @author fredo
 *
 */
public class ChainSalesReportBrandBean implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = -7499285330478001722L;
	private String brand ;
    private List<ChainSalesReportResultItem> items= new ArrayList<ChainSalesReportResultItem>();
    private int totalQ;
    private double totalRevenue;
    private double totalWholeSaleRevenue;

	public double getTotalWholeSaleRevenue() {
		return totalWholeSaleRevenue;
	}
	public void setTotalWholeSaleRevenue(double totalWholeSaleRevenue) {
		this.totalWholeSaleRevenue = totalWholeSaleRevenue;
	}
	public double getTotalRevenue() {
		return totalRevenue;
	}
	public void setTotalRevenue(double totalRevenue) {
		this.totalRevenue = totalRevenue;
	}
	public String getBrand() {
		return brand;
	}
	public void setBrand(String brand) {
		this.brand = brand;
	}
	public List<ChainSalesReportResultItem> getItems() {
		return items;
	}
	public void setItems(List<ChainSalesReportResultItem> items) {
		this.items = items;
	}
	public int getTotalQ() {
		return totalQ;
	}
	public void setTotalQ(int totalQ) {
		this.totalQ = totalQ;
	}
       
       
}
