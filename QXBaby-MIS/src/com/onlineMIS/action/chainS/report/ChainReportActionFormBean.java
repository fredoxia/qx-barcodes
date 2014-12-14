package com.onlineMIS.action.chainS.report;

import java.sql.Date;

import com.onlineMIS.ORM.entity.base.Pager;
import com.onlineMIS.ORM.entity.chainS.report.ChainBatchRptRepositoty;
import com.onlineMIS.ORM.entity.chainS.user.ChainStore;
import com.onlineMIS.ORM.entity.chainS.user.ChainUserInfor;
import com.onlineMIS.ORM.entity.headQ.barcodeGentor.Brand;
import com.onlineMIS.ORM.entity.headQ.barcodeGentor.Quarter;
import com.onlineMIS.ORM.entity.headQ.barcodeGentor.Year;

public class ChainReportActionFormBean {
	private int reportType;
	private Date startDate = new Date(new java.util.Date().getTime());
	private Date endDate = new Date(new java.util.Date().getTime());;
	private ChainStore chainStore = new ChainStore();

    //查找品牌
    private Brand brand = new Brand();
    private Year year = new Year();
    private Quarter quarter = new Quarter();
    private ChainUserInfor saler = new ChainUserInfor();
    private Pager pager = new Pager();
    
    /**
     * report repository
     */
    private ChainBatchRptRepositoty rptRepository = new ChainBatchRptRepositoty();
    
    
	public ChainBatchRptRepositoty getRptRepository() {
		return rptRepository;
	}
	public void setRptRepository(ChainBatchRptRepositoty rptRepository) {
		this.rptRepository = rptRepository;
	}
	public ChainUserInfor getSaler() {
		return saler;
	}
	public void setSaler(ChainUserInfor saler) {
		this.saler = saler;
	}
	public Brand getBrand() {
		return brand;
	}
	public void setBrand(Brand brand) {
		this.brand = brand;
	}
	public Year getYear() {
		return year;
	}
	public void setYear(Year year) {
		this.year = year;
	}
	public Quarter getQuarter() {
		return quarter;
	}
	public void setQuarter(Quarter quarter) {
		this.quarter = quarter;
	}
	public int getReportType() {
		return reportType;
	}
	public void setReportType(int reportType) {
		this.reportType = reportType;
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
	
	@Override
	public String toString() {
		return "ChainReportActionFormBean [reportType=" + reportType
				+ ", startDate=" + startDate + ", endDate=" + endDate
				+ ", chainStore=" + chainStore + ", brand=" + brand + ", year="
				+ year + ", quarter=" + quarter + ", saler=" + saler
				+ ", pager=" + pager + "]";
	}
	

	
}
