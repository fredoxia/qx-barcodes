package com.onlineMIS.ORM.entity.headQ.SQLServer;

import java.io.Serializable;

public class FinanceBillMS implements Serializable {
    private int smb_id;
    private BillidxMS billidxMS;
    private int a_id;
    private int c_id;
    private double jftotal;
    
	public int getSmb_id() {
		return smb_id;
	}
	public void setSmb_id(int smb_id) {
		this.smb_id = smb_id;
	}

	public BillidxMS getBillidxMS() {
		return billidxMS;
	}
	public void setBillidxMS(BillidxMS billidxMS) {
		this.billidxMS = billidxMS;
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
	public double getJftotal() {
		return jftotal;
	}
	public void setJftotal(double jftotal) {
		this.jftotal = jftotal;
	}
    
    
}
