package com.onlineMIS.ORM.DAO.chainS.sales;

import java.util.ArrayList;
import java.util.List;

import javassist.runtime.Inner;

public class ChartReportVO {

	private int ticketInterval = 1;
	private String chartName = "";
	private List<String> xcategory = new ArrayList<String>();
	private String yserisName = "";
	private List<ChartReportSeries> seriesList= new ArrayList<ChartReportSeries>();

	public int getTicketInterval() {
		return ticketInterval;
	}
	public void setTicketInterval(int ticketInterval) {
		this.ticketInterval = ticketInterval;
	}
	public String getChartName() {
		return chartName;
	}
	public void setChartName(String chartName) {
		this.chartName = chartName;
	}

	public List<String> getXcategory() {
		return xcategory;
	}
	public void setXcategory(List<String> xcategory) {
		this.xcategory = xcategory;
	}
	public String getYserisName() {
		return yserisName;
	}
	public void setYserisName(String yserisName) {
		this.yserisName = yserisName;
	}
	public List<ChartReportSeries> getSeriesList() {
		return seriesList;
	}
	public void setSeriesList(List<ChartReportSeries> seriesList) {
		this.seriesList = seriesList;
	}
}

