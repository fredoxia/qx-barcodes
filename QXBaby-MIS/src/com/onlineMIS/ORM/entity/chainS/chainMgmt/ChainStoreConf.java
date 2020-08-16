package com.onlineMIS.ORM.entity.chainS.chainMgmt;

import java.io.Serializable;

import com.hp.hpl.sparta.xpath.ThisNodeTest;

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
	//0: 严格使用预存款,只能在当前连锁店使用当前vip
	//1. 我的vip卡可以在关联连锁店消费/充值，
	private int allowMyPrepaidCrossStore = 0;
	
	//允许我的连锁店的预存款跨自己连锁店过账
	public static final int ALLOW_MY_PREPAID_CROSS = 1;
	public static final int NOT_ALLOW_PREPAID_CROSS = 0;	
	
	private String shippingAddress = "";
	private int prepaidPasswordRequired = 0;
	public static final int PREPAID_PASSWORD_REQUIRED = 1;
	public static final int PREPAID_PASSWORD_NOT_REQUIRED = 0;
	
	public static final int PREPAID_ALL_PREPAID_CROSS_STORE = 1;
	
	/**
	 * type=1 : amount * 1.1 = calculated amount
	 */
	private int prepaidCalculationType = 0;
	public static final int PREPAID_CALCULATION_TYPE_1 = 1;
	public static final int PREPAID_CALCULATION_TYPE_NORMAL = 0;
	
	/**
	 * 0:'只能自己连锁店vip兑换积分',
	 * 1:'关联连锁店的vip也可以在本店铺消费积分',
	 * 2:'所有其他连锁店的vip都可以在本店兑换积分'
	 */
	private int allowOtherVIPUseVIPScore = 0;
	public static final int VIPSCORE_USAGE_RESTRICTED= 0;
	public static final int VIPSCORE_USAGE_GROUPCHAIN = 1;
	public static final int VIPSCORE_USAGE_ALL = 2;
	
	/**
	 * 2020春货夏季 品默认8.8折,vip默认8折,高于8折不能过帐
	 * 1 : 开启
	 * 2  : 关闭
	 */
	private int discount2020Spring = 1;
	private int discount2020Summer = 1;
	private int discount2020Auto = 1;
	private int discount2020Winter = 1;	
	public static final int DISCOUNT_2020_ENABLE = 1;
	public static final int DISCOUNT_2020_DISENABLE = 2;
	public static final double NORMAL_DISCOUNT_2020_SPRING = 0.88;
	public static final double VIP_DISCOUNT_2020_SPRING = 0.8;
	public ChainStoreConf(){
		
	}

	public int getDiscount2020Auto() {
		return discount2020Auto;
	}

	public void setDiscount2020Auto(int discount2020Auto) {
		this.discount2020Auto = discount2020Auto;
	}

	public int getDiscount2020Winter() {
		return discount2020Winter;
	}

	public void setDiscount2020Winter(int discount2020Winter) {
		this.discount2020Winter = discount2020Winter;
	}

	public int getDiscount2020Summer() {
		return discount2020Summer;
	}

	public void setDiscount2020Summer(int discount2020Summer) {
		this.discount2020Summer = discount2020Summer;
	}

	public int getDiscount2020Spring() {
		return discount2020Spring;
	}

	public void setDiscount2020Spring(int discount2020Spring) {
		this.discount2020Spring = discount2020Spring;
	}

	public int getPrepaidPasswordRequired() {
		return prepaidPasswordRequired;
	}

	public void setPrepaidPasswordRequired(int prepaidPasswordRequired) {
		this.prepaidPasswordRequired = prepaidPasswordRequired;
	}

	public int getAllowOtherVIPUseVIPScore() {
		return allowOtherVIPUseVIPScore;
	}


	public void setAllowOtherVIPUseVIPScore(int allowOtherVIPUseVIPScore) {
		this.allowOtherVIPUseVIPScore = allowOtherVIPUseVIPScore;
	}
	public String getShippingAddress() {
		return shippingAddress;
	}

	public void setShippingAddress(String shippingAddress) {
		this.shippingAddress = shippingAddress;
	}

	public int getPrepaidCalculationType() {
		return prepaidCalculationType;
	}

	public void setPrepaidCalculationType(int prepaidCalculationType) {
		this.prepaidCalculationType = prepaidCalculationType;
	}


	public int getAllowMyPrepaidCrossStore() {
		return allowMyPrepaidCrossStore;
	}

	public void setAllowMyPrepaidCrossStore(int allow_my_prepaid_cross_store) {
		this.allowMyPrepaidCrossStore = allow_my_prepaid_cross_store;
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
	
	public double getRatioByPrepaidType(){
		switch (this.prepaidCalculationType) {
			case 1:
				return 1.1;
			default:
				return 1;
		}
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
