package com.onlineMIS.ORM.entity.chainS.vip;

import java.io.Serializable;
import java.util.Date;

public class ChainVIPScore implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1390832242438935186L;
	private int id;
	private String type;
	private int chainId;
	private int vipCardId;
	private int orderId;
	private double salesValue;
	private double coupon;
	private Date date;
	private String comment = "";
	
	/**
	 * 为显示工作
	 */
	private String typeS;
	private String vipCardNo;
	
	
	public final static String TYPE_SALE = "s";
	public final static String TYPE_UPGRADE = "u";
	public final static String TYPE_MANUAL_ADJUST = "m";
	public final static String TYPE_DEPOSIT = "d";
	
	public ChainVIPScore(){
		
	}
	
	public ChainVIPScore(int chainId, int vipCardId, String type, int orderId, double salesValue,
			double coupon) {
		super();
		this.type = type;
		this.chainId = chainId;
		this.vipCardId = vipCardId;
		this.orderId = orderId;
		this.salesValue = salesValue;
		this.coupon = coupon;
		this.date = new Date();
	}
	
	public ChainVIPScore(int chainId, int vipCardId, String type, int orderId, double salesValue,
			double coupon, String comment ) {
		super();
		this.type = type;
		this.chainId = chainId;
		this.vipCardId = vipCardId;
		this.orderId = orderId;
		this.salesValue = salesValue;
		this.coupon = coupon;
		this.date = new Date();
		this.comment = comment;
	}


	public String getTypeS() {
		if (type.equalsIgnoreCase(TYPE_SALE))
			return "零售";
		else if (type.equalsIgnoreCase(TYPE_UPGRADE))
			return "VIP升级";
		else if (type.equalsIgnoreCase(TYPE_MANUAL_ADJUST))
			return "手动积分调整";	
		else if (type.equalsIgnoreCase(TYPE_DEPOSIT))
			return "预存金";
		else
			return "";
	}

	
	public String getVipCardNo() {
		return vipCardNo;
	}

	public void setVipCardNo(String vipCardNo) {
		this.vipCardNo = vipCardNo;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public void setTypeS(String typeS) {
		this.typeS = typeS;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getChainId() {
		return chainId;
	}

	public void setChainId(int chainId) {
		this.chainId = chainId;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

	public int getVipCardId() {
		return vipCardId;
	}

	public void setVipCardId(int vipCardId) {
		this.vipCardId = vipCardId;
	}

	public int getOrderId() {
		return orderId;
	}
	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}
	public double getSalesValue() {
		return salesValue;
	}
	public void setSalesValue(double salesValue) {
		this.salesValue = salesValue;
	}
	public double getCoupon() {
		return coupon;
	}
	public void setCoupon(double coupon) {
		this.coupon = coupon;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	
	
}
