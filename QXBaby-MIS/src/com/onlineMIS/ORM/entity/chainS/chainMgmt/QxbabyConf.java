package com.onlineMIS.ORM.entity.chainS.chainMgmt;

import java.io.Serializable;

import org.springframework.stereotype.Repository;

public class QxbabyConf implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4356018618083698291L;
	private int id;
	private int yearId ;
	private int quarterId ;

	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public int getYearId() {
		return yearId;
	}


	public void setYearId(int yearId) {
		this.yearId = yearId;
	}


	public int getQuarterId() {
		return quarterId;
	}


	public void setQuarterId(int quarterId) {
		this.quarterId = quarterId;
	}


	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
