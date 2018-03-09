package com.onlineMIS.ORM.entity.chainS.inventoryFlow;

import java.io.Serializable;
import java.util.Date;

import com.onlineMIS.ORM.entity.headQ.barcodeGentor.ProductBarcode;
import com.onlineMIS.common.Common_util;


public class ChainInOutStock implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 3535346499179201903L;
	public static final String HEADQ_SALES = "HS";
	public static final String HEADQ_RETURN = "HR";
	public static final String CHAIN_SALES = "CS";
	public static final String CHAIN_RETURN = "CR";
	//换货单
	public static final String CHAIN_EXCHANGE = "CX";
	//调货单
	public static final String CHAIN_TRANSFER = "CT";
	public static final String CHAIN_TRASFER2 = "CT2";
	public static final String CHAIN_OVERFLOW = "CO";
	public static final String CHAIN_FLOWLOSS= "CL";
	public static final String AUTO_BAR_ACCT = "ABA";
	//public static final String CHAIN_INITIAL = "IN";
	public static final String DEFAULT_INITIAL_STOCK_ORDER_ID = "";
	
	public static final int TYPE_PURCHASE = 9;
	public static final int TYPE_INITIAL_STOCK = 10;
	public static final int TYPE_TRANSFER2 = 11;
	
	private String barcode;
    private int clientId;
    private String orderId;
    private int type;
    private double cost;
    private double costTotal;
    private double salePrice;
    private double salePriceTotal;
    private double chainSalePriceTotal;
    private int quantity;
    private Date date;
    private ProductBarcode productBarcode;
    
    public ChainInOutStock(){
    	
    }
    
    public ChainInOutStock(String barcode, int clientId, String orderId, int type, double cost, double costTotal, double salePrice, double salePriceTotal, double chainSalePriceTotal, int quantity, ProductBarcode productBarcode){
    	this.barcode = barcode;
    	this.clientId = clientId;
    	this.orderId = orderId;
    	this.type = type;
    	this.cost = cost;
    	this.costTotal = Common_util.roundDouble(costTotal,2);
    	this.salePrice = Common_util.roundDouble(salePrice,2);
    	this.salePriceTotal = Common_util.roundDouble(salePriceTotal,2);
    	this.quantity = quantity;
    	this.chainSalePriceTotal = Common_util.roundDouble(chainSalePriceTotal, 2);
    	date = new Date();
    	this.productBarcode = productBarcode;
    }
    
    public String getKey(){
    	return barcode +"#" + clientId + "#" + orderId + "#" + type;
    }
    
	public double getChainSalePriceTotal() {
		return chainSalePriceTotal;
	}

	public void setChainSalePriceTotal(double chainSalePriceTotal) {
		this.chainSalePriceTotal = chainSalePriceTotal;
	}

	public ProductBarcode getProductBarcode() {
		return productBarcode;
	}

	public void setProductBarcode(ProductBarcode productBarcode) {
		this.productBarcode = productBarcode;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getBarcode() {
		return barcode;
	}

	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}

	public int getClientId() {
		return clientId;
	}
	public void setClientId(int clientId) {
		this.clientId = clientId;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public double getCost() {
		return cost;
	}
	public void setCost(double cost) {
		this.cost = cost;
	}
	public double getCostTotal() {
		return costTotal;
	}
	public void setCostTotal(double costTotal) {
		this.costTotal = costTotal;
	}
	public double getSalePrice() {
		return salePrice;
	}
	public void setSalePrice(double salePrice) {
		this.salePrice = salePrice;
	}
	public double getSalePriceTotal() {
		return salePriceTotal;
	}
	public void setSalePriceTotal(double salePriceTotal) {
		this.salePriceTotal = salePriceTotal;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
	public void add(ChainInOutStock stockInMap) {
		this.quantity += stockInMap.getQuantity();
		this.salePriceTotal += stockInMap.getSalePriceTotal();
		this.costTotal += stockInMap.getCostTotal();
		
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((barcode == null) ? 0 : barcode.hashCode());
		result = prime * result + clientId;
		result = prime * result + ((orderId == null) ? 0 : orderId.hashCode());
		result = prime * result + type;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ChainInOutStock other = (ChainInOutStock) obj;
		if (barcode == null) {
			if (other.barcode != null)
				return false;
		} else if (!barcode.equals(other.barcode))
			return false;
		if (clientId != other.clientId)
			return false;
		if (orderId == null) {
			if (other.orderId != null)
				return false;
		} else if (!orderId.equals(other.orderId))
			return false;
		if (type != other.type)
			return false;
		return true;
	}




    
}
