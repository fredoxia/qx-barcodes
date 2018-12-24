package com.onlineMIS.action.chainS.sales;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.onlineMIS.ORM.entity.chainS.chainMgmt.ChainStoreConf;
import com.onlineMIS.ORM.entity.chainS.sales.ChainStoreSalesOrder;
import com.onlineMIS.ORM.entity.chainS.sales.ChainStoreSalesOrderProduct;
import com.onlineMIS.ORM.entity.chainS.user.ChainStore;
import com.onlineMIS.ORM.entity.chainS.user.ChainUserInfor;
import com.onlineMIS.ORM.entity.headQ.barcodeGentor.ProductBarcode;
import com.onlineMIS.action.chainS.vo.ChainProductBarcodeVO;

public class ChainSalesActionUIBean {
	public final static int CHAIN_ORDER_PAY_CASH = 1;
	public final static int CHAIN_ORDER_PAY_CARD = 2;
	public final static int CHAIN_ORDER_PAY_WECHAT = 3;
	public final static int CHAIN_ORDER_PAY_ALIPAY = 4;
	public final static int CHAIN_ORDER_PAY_COUPON = 5;
	public final static int CHAIN_ORDER_PAY_PREPAY = 6;
	public final static int CHAIN_ORDER_PAY_VIPSCORE = 7;

	/**
	 * those UI Bean is for the sales order search page's drop down
	 */
	private List<ChainStore> chainStores = new ArrayList<ChainStore>();
	private List<ChainUserInfor> chainSalers = new ArrayList<ChainUserInfor>();
    private ChainUserInfor orderCreator = new ChainUserInfor();
    private String createDate = "";
    private Map<Integer, String> chainOrderTypes = new HashMap<Integer, String>();
    private Map<Integer, String> chainOrderStatus = new HashMap<Integer, String>();
    private Map<Integer, String> chainOrderPay = new HashMap<Integer, String>();
    {
    	chainOrderPay.put(CHAIN_ORDER_PAY_CASH, "现金");
    	chainOrderPay.put(CHAIN_ORDER_PAY_CARD, "刷卡");
    	chainOrderPay.put(CHAIN_ORDER_PAY_WECHAT, "微信支付");
    	chainOrderPay.put(CHAIN_ORDER_PAY_ALIPAY, "支付宝支付");
    	chainOrderPay.put(CHAIN_ORDER_PAY_COUPON, "代金劵");
    	chainOrderPay.put(CHAIN_ORDER_PAY_PREPAY, "预付金");
    	chainOrderPay.put(CHAIN_ORDER_PAY_VIPSCORE, "积分抵现金");
    }
	private ChainStoreConf chainStoreConf = new ChainStoreConf();
    
    /**
     * when scan by barcode
     */
    private ChainStoreSalesOrderProduct chainOrderProduct = new ChainStoreSalesOrderProduct();
    private List<ProductBarcode> products = new ArrayList<ProductBarcode>();
    private List<ChainProductBarcodeVO> cpbVOs = new ArrayList<ChainProductBarcodeVO>();
    private int index;

    /**
     * when read one order
     */
    private ChainStoreSalesOrder chainSalesOrder = new ChainStoreSalesOrder();
    
    /**
     * view product infroamtion
     *
     */
    private ChainProductBarcodeVO cpbVO = new ChainProductBarcodeVO();
    private ProductBarcode product = new ProductBarcode();
    private boolean canViewRecCost = false;
    private boolean canViewMyRecCost = false;
    private ChainStore chainStore = null;

    
    public Map<Integer, String> getChainOrderPay() {
		return chainOrderPay;
	}
	public void setChainOrderPay(Map<Integer, String> chainOrderPay) {
		this.chainOrderPay = chainOrderPay;
	}
	public ChainStore getChainStore() {
		return chainStore;
	}
	public void setChainStore(ChainStore chainStore) {
		this.chainStore = chainStore;
	}
	private String msg;
    
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public ChainProductBarcodeVO getCpbVO() {
		return cpbVO;
	}
	public void setCpbVO(ChainProductBarcodeVO cpbVO) {
		this.cpbVO = cpbVO;
	}
	public List<ChainProductBarcodeVO> getCpbVOs() {
		return cpbVOs;
	}
	public void setCpbVOs(List<ChainProductBarcodeVO> cpbVOs) {
		this.cpbVOs = cpbVOs;
	}
	public boolean getCanViewMyRecCost() {
		return canViewMyRecCost;
	}
	public void setCanViewMyRecCost(boolean canViewMyRecCost) {
		this.canViewMyRecCost = canViewMyRecCost;
	}
	public ChainStoreConf getChainStoreConf() {
		return chainStoreConf;
	}
	public void setChainStoreConf(ChainStoreConf chainStoreConf) {
		this.chainStoreConf = chainStoreConf;
	}
	public boolean isCanViewRecCost() {
		return canViewRecCost;
	}
	public void setCanViewRecCost(boolean canViewRecCost) {
		this.canViewRecCost = canViewRecCost;
	}
	public ProductBarcode getProduct() {
		return product;
	}
	public void setProduct(ProductBarcode product) {
		this.product = product;
	}
	public ChainStoreSalesOrder getChainSalesOrder() {
		return chainSalesOrder;
	}
	public void setChainSalesOrder(ChainStoreSalesOrder chainSalesOrder) {
		this.chainSalesOrder = chainSalesOrder;
	}
	public Map<Integer, String> getChainOrderTypes() {
		return chainOrderTypes;
	}
	public void setChainOrderTypes(Map<Integer, String> chainOrderTypes) {
		this.chainOrderTypes = chainOrderTypes;
	}
	public Map<Integer, String> getChainOrderStatus() {
		return chainOrderStatus;
	}
	public void setChainOrderStatus(Map<Integer, String> chainOrderStatus) {
		this.chainOrderStatus = chainOrderStatus;
	}
	public int getIndex() {
		return index;
	}
	public void setIndex(int index) {
		this.index = index;
	}
	public List<ProductBarcode> getProducts() {
		return products;
	}
	public void setProducts(List<ProductBarcode> products) {
		this.products = products;
	}
	public ChainStoreSalesOrderProduct getChainOrderProduct() {
		return chainOrderProduct;
	}
	public void setChainOrderProduct(ChainStoreSalesOrderProduct chainOrderProduct) {
		this.chainOrderProduct = chainOrderProduct;
	}
	public List<ChainStore> getChainStores() {
		return chainStores;
	}
	public void setChainStores(List<ChainStore> chainStores) {
		this.chainStores = chainStores;
	}
	public List<ChainUserInfor> getChainSalers() {
		return chainSalers;
	}
	public void setChainSalers(List<ChainUserInfor> chainSalers) {
		this.chainSalers = chainSalers;
	}

	public ChainUserInfor getOrderCreator() {
		return orderCreator;
	}
	public void setOrderCreator(ChainUserInfor orderCreator) {
		this.orderCreator = orderCreator;
	}
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

}
