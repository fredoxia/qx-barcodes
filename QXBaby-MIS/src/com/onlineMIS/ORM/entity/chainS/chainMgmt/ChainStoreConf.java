package com.onlineMIS.ORM.entity.chainS.chainMgmt;

import java.io.Serializable;

public class ChainStoreConf implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8190919880095952097L;
	//正常收款计算
	public static final int AMT_TYPE_NORMAL = 1;
	//只舍不入
	public static final int AMT_TYPE_DOWN = 2; 
	//只入不舍
	public static final int AMT_TYPE_UP = 4; 
	//四舍五入
	public static final int AMT_TYPE_ROUND = 3; 
	
	//低于成本报警
	public static final int LOW_COST_ALERT = 1;
	//低于成本不报警
	public static final int LOW_COST_NO_ALERT = 2;

	private int chainId;
	private int printCopy = 1;
	private double minDiscountRate = 0;
	private int discountAmtType = 1;
	private int lowThanCostAlert = 1;
	private double defaultDiscount = 1;
	private double vipScoreCashRatio = 0.01;
	private int printTemplate = 1;
	private String address = "";
	private int defaultVipScoreMultiple = 1;
	private int hideDiscountPrint = 0;
	private int allow_my_prepaid_cross_store = 0;
	
	public ChainStoreConf(){
		
	}

	
	public int getAllow_my_prepaid_cross_store() {
		return allow_my_prepaid_cross_store;
	}


	public void setAllow_my_prepaid_cross_store(int allow_my_prepaid_cross_store) {
		this.allow_my_prepaid_cross_store = allow_my_prepaid_cross_store;
	}


	public int getHideDiscountPrint() {
		return hideDiscountPrint;
	}


	public void setHideDiscountPrint(int hideDiscountPrint) {
		this.hideDiscountPrint = hideDiscountPrint;
	}


	public int getDefaultVipScoreMultiple() {
		return defaultVipScoreMultiple;
	}

	public void setDefaultVipScoreMultiple(int defaultVipScoreMultiple) {
		this.defaultVipScoreMultiple = defaultVipScoreMultiple;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public int getPrintTemplate() {
		return printTemplate;
	}

	public void setPrintTemplate(int print_template) {
		this.printTemplate = print_template;
	}

	public double getDefaultDiscount() {
		return defaultDiscount;
	}

	public void setDefaultDiscount(double defaultDiscount) {
		this.defaultDiscount = defaultDiscount;
	}

	public int getChainId() {
		return chainId;
	}
	public void setChainId(int chainId) {
		this.chainId = chainId;
	}
	public int getPrintCopy() {
		return printCopy;
	}
	public void setPrintCopy(int printCopy) {
		this.printCopy = printCopy;
	}
	public double getMinDiscountRate() {
		return minDiscountRate;
	}
	public void setMinDiscountRate(double minDiscountRate) {
		this.minDiscountRate = minDiscountRate;
	}
	public int getDiscountAmtType() {
		return discountAmtType;
	}
	public void setDiscountAmtType(int discountAmtType) {
		this.discountAmtType = discountAmtType;
	}
	public int getLowThanCostAlert() {
		return lowThanCostAlert;
	}
	public void setLowThanCostAlert(int lowThanCostAlert) {
		this.lowThanCostAlert = lowThanCostAlert;
	}
	
	public double getVipScoreCashRatio() {
		return vipScoreCashRatio;
	}

	public void setVipScoreCashRatio(double vipScoreCashRatio) {
		this.vipScoreCashRatio = vipScoreCashRatio;
	}

	public enum lowCostAlert {
    	ALERT (LOW_COST_ALERT, "低于成本不能过账"),
    	NOT_ALERT (LOW_COST_NO_ALERT, "低于成本可以过账");
    	
    	private int toAlert;
    	private String toAlertS;

    	public int getToAlert() {
			return toAlert;
		}

		public void setToAlert(int toAlert) {
			this.toAlert = toAlert;
		}

		public String getToAlertS() {
			return toAlertS;
		}

		public void setToAlertS(String toAlertS) {
			this.toAlertS = toAlertS;
		}

		lowCostAlert(int toAlert, String toAlertS){
    		this.toAlert = toAlert;
    		this.toAlertS = toAlertS;
    	}	
    }
	
	
	public enum amtTypes {
	    	ATM_TYPES_NORMAL (AMT_TYPE_NORMAL, "正常"),
	    	AMT_TYPES_DOWN (AMT_TYPE_DOWN, "只舍不入"),
	    	AMT_TYPES_ROUND (AMT_TYPE_ROUND, "四舍五入"),
	    	AMT_TYPES_UP (AMT_TYPE_UP,"只入不舍");
	    	
	    	private int typeId;
	    	private String typeS;
	    	
	    	public int getTypeId() {
				return typeId;
			}

			public void setTypeId(int typeId) {
				this.typeId = typeId;
			}

			public String getTypeS() {
				return typeS;
			}

			public void setTypeS(String typeS) {
				this.typeS = typeS;
			}

			amtTypes(int typeId, String typeS){
	    		this.typeId = typeId;
	    		this.typeS = typeS;
	    	}	
	 }

}
