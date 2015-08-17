package com.onlineMIS.ORM.entity.headQ.SQLServer;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class BillidxMS implements Serializable {
    private int billid;
	private Date billdate = new Date();
	private String billnumber;
	private int billtype;
	private int a_id;
	private int c_id;
	private double ysmoney;
	private double ssmoney;
	private int quantity;
	private double jsye;
	private String jsflag;
	private String note;
	private double c_araptotal;
    private Set<FinanceBillMS> financeBill_Set = new HashSet<FinanceBillMS>();
	
	public Set<FinanceBillMS> getFinanceBill_Set() {
		return financeBill_Set;
	}
	public void setFinanceBill_Set(Set<FinanceBillMS> financeBill_Set) {
		this.financeBill_Set = financeBill_Set;
	}
	public int getBillid() {
		return billid;
	}
	public void setBillid(int billid) {
		this.billid = billid;
	}
	public Date getBilldate() {
		return billdate;
	}
	public void setBilldate(Date billdate) {
		this.billdate = billdate;
	}
	public String getBillnumber() {
		return billnumber;
	}
	public void setBillnumber(String billnumber) {
		this.billnumber = billnumber;
	}
	public int getBilltype() {
		return billtype;
	}
	public void setBilltype(int billtype) {
		this.billtype = billtype;
	}
	public int getA_id() {
		return a_id;
	}
	public void setA_id(int a_id) {
		this.a_id = a_id;
	}
	public int getC_id() {
		return c_id;
	}
	public void setC_id(int c_id) {
		this.c_id = c_id;
	}
	public double getYsmoney() {
		return ysmoney;
	}
	public void setYsmoney(double ysmoney) {
		this.ysmoney = ysmoney;
	}
	public double getSsmoney() {
		return ssmoney;
	}
	public void setSsmoney(double ssmoney) {
		this.ssmoney = ssmoney;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public double getJsye() {
		return jsye;
	}
	public void setJsye(double jsye) {
		this.jsye = jsye;
	}
	public String getJsflag() {
		return jsflag;
	}
	public void setJsflag(String jsflag) {
		this.jsflag = jsflag;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public double getC_araptotal() {
		return c_araptotal;
	}
	public void setC_araptotal(double c_araptotal) {
		this.c_araptotal = c_araptotal;
	}

	
}
