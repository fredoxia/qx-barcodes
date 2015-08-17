package com.onlineMIS.ORM.entity.headQ.HR;

import java.io.Serializable;



public class EvaluationItem implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	private String item_name;
	private String item_desp_1; //no pass
	private String item_desp_2; //lower
	private String item_desp_3; //medium
	private String item_desp_4; //high

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getItem_name() {
		return item_name;
	}
	public void setItem_name(String item_name) {
		this.item_name = item_name;
	}
	public String getItem_desp_1() {
		return item_desp_1;
	}
	public void setItem_desp_1(String item_desp_1) {
		this.item_desp_1 = item_desp_1;
	}
	public String getItem_desp_2() {
		return item_desp_2;
	}
	public void setItem_desp_2(String item_desp_2) {
		this.item_desp_2 = item_desp_2;
	}
	public String getItem_desp_3() {
		return item_desp_3;
	}
	public void setItem_desp_3(String item_desp_3) {
		this.item_desp_3 = item_desp_3;
	}
	public String getItem_desp_4() {
		return item_desp_4;
	}
	public void setItem_desp_4(String item_desp_4) {
		this.item_desp_4 = item_desp_4;
	}
	
	

}
