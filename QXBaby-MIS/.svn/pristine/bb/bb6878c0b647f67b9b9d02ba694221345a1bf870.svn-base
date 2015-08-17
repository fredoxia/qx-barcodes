package com.onlineMIS.ORM.entity.chainS.inventoryFlow;

import java.io.Serializable;
import java.util.Date;

import com.onlineMIS.ORM.entity.chainS.user.ChainStore;
import com.onlineMIS.ORM.entity.headQ.barcodeGentor.ProductBarcode;
import com.onlineMIS.common.Common_util;
import com.onlineMIS.common.loggerLocal;


public class ChainInvenTraceInfoVO implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 3440313540411506629L;
	/**
     * 货品信息
     */
    private String barcode;
    /**
     * 日期
     */
    private String date;
    private int quantity;
    /**
     * 比如cs, hs, 
     */
    private String actionCode;
    /**
     * 描述,比如 总部进货，总部退货，零售销售，零售退货，调货，
     */
    private String descp;
    /**
     * 单据号
     */
    private String orderId;
    private ChainStore chainStore;
    
    public ChainInvenTraceInfoVO(){
    	
    }
    
    public ChainInvenTraceInfoVO(ChainInOutStockArchive stock){
    	this.date = Common_util.dateFormat_f.format(stock.getDate());
    	this.quantity = stock.getQuantity();
    	
    	String actionAndId = stock.getOrderId();
    	
    	String[] actionAndIdArray = sliptActionAndId(actionAndId);
    	
    	this.actionCode = actionAndIdArray[0];
    	this.descp = actionAndIdArray[1];
    	this.orderId = actionAndIdArray[2];
    	this.barcode = stock.getBarcode();
    }
    
    public ChainInvenTraceInfoVO(ChainInOutStock stock){
    	this.date = Common_util.dateFormat_f.format(stock.getDate());
    	this.quantity = stock.getQuantity();
    	
    	String actionAndId = stock.getOrderId();
    	
    	String[] actionAndIdArray = sliptActionAndId(actionAndId);
    	
    	this.actionCode = actionAndIdArray[0];
    	this.descp = actionAndIdArray[1];
    	this.orderId = actionAndIdArray[2];
    	this.barcode = stock.getBarcode();
    }
    
	private String[] sliptActionAndId(String actionAndId) {
		String[] actionArray = {"","",""};
		String[] errorArray = {"错误","错误","错误"};
		
		actionAndId = actionAndId.trim();
		
		if (actionAndId.equals("")){
			actionArray[0] = "";
			actionArray[1] = "期初库存";
			actionArray[2] = "";
		} else {
			String actionCode ="" ;
			String orderId = "";
			try {
				if (actionAndId.startsWith("HSC") || actionAndId.startsWith("HRC") ||actionAndId.startsWith("CSC")  ||actionAndId.startsWith("CTC") ||actionAndId.startsWith("COC") ||actionAndId.startsWith("CLC") ||actionAndId.startsWith(ChainInOutStock.AUTO_BAR_ACCT)){
					actionCode = actionAndId.substring(0,3);
					orderId = actionAndId.substring(3);
				} else if (actionAndId.startsWith("HS") || actionAndId.startsWith("HR") ||actionAndId.startsWith("CS")  ||actionAndId.startsWith("CT") ||actionAndId.startsWith("CO") ||actionAndId.startsWith("CL")){
					actionCode = actionAndId.substring(0,2);
					orderId = actionAndId.substring(2);
				}
				
				actionArray[0] = actionCode;
				actionArray[1] = Common_util.getTraceActionDesp(actionCode);
				actionArray[2] = orderId;
			} catch (Exception e) {
				loggerLocal.error(e.getMessage());
				loggerLocal.error(e);
				actionArray = errorArray;
			}
		}
		
		return actionArray;
	}


	public String getBarcode() {
		return barcode;
	}

	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}


	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public String getActionCode() {
		return actionCode;
	}
	public void setActionCode(String actionCode) {
		this.actionCode = actionCode;
	}
	public String getDescp() {
		return descp;
	}
	public void setDescp(String descp) {
		this.descp = descp;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public ChainStore getChainStore() {
		return chainStore;
	}
	public void setChainStore(ChainStore chainStore) {
		this.chainStore = chainStore;
	}
    
}
