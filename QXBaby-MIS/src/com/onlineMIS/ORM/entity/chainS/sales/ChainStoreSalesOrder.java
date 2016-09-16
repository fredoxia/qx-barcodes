package com.onlineMIS.ORM.entity.chainS.sales;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import com.onlineMIS.ORM.entity.base.BaseOrder;
import com.onlineMIS.ORM.entity.chainS.user.ChainStore;
import com.onlineMIS.ORM.entity.chainS.user.ChainUserInfor;
import com.onlineMIS.ORM.entity.chainS.vip.ChainVIPCard;
import com.onlineMIS.sorter.ProductSortByIndex;

public class ChainStoreSalesOrder extends BaseOrder {
	/**
	 * 
	 */
	private static final long serialVersionUID = 526914422972089273L;
	{
		typeChainMap.put(SALES, "零售单");
	}
	
	/**
	 * the sales order type
	 */
	public static final int SALES = 1;
	
    private int id;
	private ChainStore chainStore = new ChainStore();
	private ChainUserInfor saler;
	private ChainUserInfor creator;
	private int accountTypeId;
	private java.sql.Date orderDate;
	private Date orderCreateDate;
	private String memo;
	/**
	 * 实际收到现金
	 */
	private double cashAmount;
	/**
	 * 实际刷卡金额
	 */
	private double cardAmount;
	/**
	 * 微信金额
	 */
	private double wechatAmount;
	/**
	 * 支付宝金额
	 */
	private double alipayAmount;
	/**
	 * 使用vip预付现金
	 */
	private double chainPrepaidAmt;
	/**
	 * 使用预付金的那张单子
	 */
	private int vipPrepaidOrderId;
	/**
	 * 优惠金额
	 */
	private double discountAmount;
	/**
	 * 代金券
	 */
	private double coupon;
	/**
	 * VIP 代扣
	 */
	private double vipScore;
	/**
	 * vip 购买一些未入条码系统产品时，录入
	 */
	private double extralVipScore;
	
	/**
	 * 销售总金额
	 */
	private double totalAmount;
	/**
	 * 销售折后金额
	 */
	private double netAmount;
	/**
	 * 找零/退钱
	 */
	private double returnAmount;
	
	/**
	 * 在换货单中的，退货物品的折后金额
	 */
	private double netAmountR;
	
	/**
	 * 在换货单中的，退货物品的总金额
	 */
	private double totalAmountR;
	
	/**
	 * 在换货单中的，退货物品的总数量
	 */
	private int totalQuantityR;
	
	/**
	 * 数量总数
	 */
	private int totalQuantity;
	
	/**
	 * 赠送数量
	 */
	private int totalQuantityF;
	
	/**
	 * 成本总数 (销售 - 退货)
	 */
	private double totalCost;
	
	/**
	 * 赠品成本
	 */
	private double totalCostF;
	
	/**
	 * 除开内衣，饰品的数量
	 */
	private int totalQuantityA;
	
	/**
	 * 除开内衣，饰品的金额
	 */
	private double netAmountA;
	
	/**
	 * 千禧货品数量
	 */
	private int qxQuantity;
	
	/**
	 * 千禧货品净销售
	 */
	private double qxAmount;
	
	/**
	 * 千禧货品成本
	 */
	private double qxCost;
	
	/**
	 * 自己货品的数量
	 */
	private int myQuantity;
	
	/**
	 * 自己货品净销售
	 */
	private double myAmount;
	
	/**
	 * 自己货品成本
	 */
	private double myCost;
	
	private ChainVIPCard vipCard;
	/**
	 * This productlist is for the sales_out, sales_return, and sale_exchange-type sales_out
	 */
	private List<ChainStoreSalesOrderProduct> productList = new ArrayList<ChainStoreSalesOrderProduct>();
	
	/**
	 * This productListR is for the sale_exchange-type return_back
	 */
	private List<ChainStoreSalesOrderProduct> productListR = new ArrayList<ChainStoreSalesOrderProduct>();
	
	/**
	 * This productListF is for the 赠送 product list
	 */
	private List<ChainStoreSalesOrderProduct> productListF= new ArrayList<ChainStoreSalesOrderProduct>();
	
	private Set<ChainStoreSalesOrderProduct> productSet = new HashSet<ChainStoreSalesOrderProduct>();

	
	public int getTotalQuantityA() {
		return totalQuantityA;
	}
	public void setTotalQuantityA(int totalQuantityA) {
		this.totalQuantityA = totalQuantityA;
	}

	public double getNetAmountA() {
		return netAmountA;
	}
	public void setNetAmountA(double netAmountA) {
		this.netAmountA = netAmountA;
	}
	public double getExtralVipScore() {
		return extralVipScore;
	}
	public void setExtralVipScore(double extralVipScore) {
		this.extralVipScore = extralVipScore;
	}
	public double getTotalCostF() {
		return totalCostF;
	}
	public void setTotalCostF(double totalCostF) {
		this.totalCostF = totalCostF;
	}
	public double getVipScore() {
		return vipScore;
	}
	public void setVipScore(double vipScore) {
		this.vipScore = vipScore;
	}
	public int getTotalQuantityF() {
		return totalQuantityF;
	}
	public void setTotalQuantityF(int totalQuantityF) {
		this.totalQuantityF = totalQuantityF;
	}
	public List<ChainStoreSalesOrderProduct> getProductListF() {
		return productListF;
	}
	public void setProductListF(List<ChainStoreSalesOrderProduct> productListF) {
		this.productListF = productListF;
	}
	public Date getOrderCreateDate() {
		return orderCreateDate;
	}
	public void setOrderCreateDate(Date orderCreateDate) {
		this.orderCreateDate = orderCreateDate;
	}
	public List<ChainStoreSalesOrderProduct> getProductListR() {
		return productListR;
	}
	public void setProductListR(List<ChainStoreSalesOrderProduct> productListR) {
		this.productListR = productListR;
	}
	public ChainVIPCard getVipCard() {
		return vipCard;
	}
	public void setVipCard(ChainVIPCard vipCard) {
		this.vipCard = vipCard;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public ChainStore getChainStore() {
		return chainStore;
	}
	public void setChainStore(ChainStore chainStore) {
		this.chainStore = chainStore;
	}
	public ChainUserInfor getSaler() {
		return saler;
	}
	public void setSaler(ChainUserInfor saler) {
		this.saler = saler;
	}

	public int getAccountTypeId() {
		return accountTypeId;
	}
	public void setAccountTypeId(int accountTypeId) {
		this.accountTypeId = accountTypeId;
	}
	public java.sql.Date getOrderDate() {
		return orderDate;
	}
	public void setOrderDate(java.sql.Date orderDate) {
		this.orderDate = orderDate;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}

	public double getCashAmount() {
		return cashAmount;
	}
	public void setCashAmount(double cashAmount) {
		this.cashAmount = cashAmount;
	}
	public double getCardAmount() {
		return cardAmount;
	}
	public void setCardAmount(double cardAmount) {
		this.cardAmount = cardAmount;
	}
	public double getCoupon() {
		return coupon;
	}
	public void setCoupon(double coupon) {
		this.coupon = coupon;
	}
	public double getDiscountAmount() {
		return discountAmount;
	}
	public void setDiscountAmount(double discountAmount) {
		this.discountAmount = discountAmount;
	}

	public List<ChainStoreSalesOrderProduct> getProductList() {
		return productList;
	}
	public void setProductList(List<ChainStoreSalesOrderProduct> productList) {
		this.productList = productList;
	}
	public Set<ChainStoreSalesOrderProduct> getProductSet() {
		return productSet;
	}
	public void setProductSet(Set<ChainStoreSalesOrderProduct> productSet) {
		this.productSet = productSet;
	}
	public double getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(double totalAmount) {
		this.totalAmount = totalAmount;
	}
	
	public double getReturnAmount() {
		return returnAmount;
	}
	public void setReturnAmount(double returnAmount) {
		this.returnAmount = returnAmount;
	}
	public int getTotalQuantity() {
		return totalQuantity;
	}
	public void setTotalQuantity(int totalQuantity) {
		this.totalQuantity = totalQuantity;
	}
	public double getNetAmount() {
		return netAmount;
	}
	public void setNetAmount(double netAmount) {
		this.netAmount = netAmount;
	}
	public ChainUserInfor getCreator() {
		return creator;
	}
	public void setCreator(ChainUserInfor creator) {
		this.creator = creator;
	}
	public double getNetAmountR() {
		return netAmountR;
	}
	public void setNetAmountR(double netAmountR) {
		this.netAmountR = netAmountR;
	}
	public double getTotalAmountR() {
		return totalAmountR;
	}
	public void setTotalAmountR(double totalAmountR) {
		this.totalAmountR = totalAmountR;
	}
	public int getTotalQuantityR() {
		return totalQuantityR;
	}
	public void setTotalQuantityR(int totalQuantityR) {
		this.totalQuantityR = totalQuantityR;
	}
	
	public double getTotalCost() {
		return totalCost;
	}
	public void setTotalCost(double totalCost) {
		this.totalCost = totalCost;
	}

	public int getQxQuantity() {
		return qxQuantity;
	}
	public void setQxQuantity(int qxQuantity) {
		this.qxQuantity = qxQuantity;
	}
	public double getQxAmount() {
		return qxAmount;
	}
	public void setQxAmount(double qxAmount) {
		this.qxAmount = qxAmount;
	}
	public double getQxCost() {
		return qxCost;
	}
	public void setQxCost(double qxCost) {
		this.qxCost = qxCost;
	}
	public int getMyQuantity() {
		return myQuantity;
	}
	public void setMyQuantity(int myQuantity) {
		this.myQuantity = myQuantity;
	}
	public double getMyAmount() {
		return myAmount;
	}
	public void setMyAmount(double myAmount) {
		this.myAmount = myAmount;
	}
	public double getMyCost() {
		return myCost;
	}
	public void setMyCost(double myCost) {
		this.myCost = myCost;
	}
	
	public double getChainPrepaidAmt() {
		return chainPrepaidAmt;
	}
	public void setChainPrepaidAmt(double chainPrepaidAmt) {
		this.chainPrepaidAmt = chainPrepaidAmt;
	}
	
	public int getVipPrepaidOrderId() {
		return vipPrepaidOrderId;
	}
	public void setVipPrepaidOrderId(int vipPrepaidOrderId) {
		this.vipPrepaidOrderId = vipPrepaidOrderId;
	}
	
	public double getWechatAmount() {
		return wechatAmount;
	}
	public void setWechatAmount(double wechatAmount) {
		this.wechatAmount = wechatAmount;
	}
	public double getAlipayAmount() {
		return alipayAmount;
	}
	public void setAlipayAmount(double alipayAmount) {
		this.alipayAmount = alipayAmount;
	}
	/**
     * 1. to build the index for each order product
     * 2. to set the order product type
     * index is to tell the sequence of the products scanned
     */
	public void buildIndex() {
		for(int i =0; i< productList.size();i++){
			ChainStoreSalesOrderProduct product = productList.get(i);
			if (product!= null && product.getProductBarcode() != null && product.getProductBarcode().getBarcode() != null && !product.getProductBarcode().getBarcode().trim().equals("") && product.getProductBarcode().getId()!=0){
			   product.setIndex(i);		
			   product.setType(ChainStoreSalesOrderProduct.SALES_OUT);
			}
		}
		
		for(int i =0; i< productListR.size();i++){
			ChainStoreSalesOrderProduct product = productListR.get(i);
			if (product!= null && product.getProductBarcode() != null && product.getProductBarcode().getBarcode() != null && !product.getProductBarcode().getBarcode().trim().equals("") && product.getProductBarcode().getId()!=0){
			   product.setIndex(i);		
			   product.setType(ChainStoreSalesOrderProduct.RETURN_BACK); 
			}
		}
		
		for(int i =0; i< productListF.size();i++){
			ChainStoreSalesOrderProduct product = productListF.get(i);
			if (product!= null && product.getProductBarcode() != null && product.getProductBarcode().getBarcode() != null && !product.getProductBarcode().getBarcode().trim().equals("") && product.getProductBarcode().getId()!=0){
			   product.setIndex(i);		
			   product.setType(ChainStoreSalesOrderProduct.FREE); 
			}
		}
	}

	public void putListToSet(){
		if (productList != null)
			for (int i = 0; i < productList.size(); i++){
				if (productList.get(i)!= null && productList.get(i).getProductBarcode() != null && productList.get(i).getProductBarcode().getBarcode() != null && !productList.get(i).getProductBarcode().getBarcode().trim().equals("") && productList.get(i).getProductBarcode().getId()!=0){
					ChainStoreSalesOrderProduct orderProduct = productList.get(i);
				    orderProduct.setChainSalesOrder(this);
				    productSet.add(orderProduct);
				}
			}
		
		if (productListR != null)
			for (int i = 0; i < productListR.size(); i++){
				if (productListR.get(i)!= null && productListR.get(i).getProductBarcode() != null && productListR.get(i).getProductBarcode().getBarcode() != null && !productListR.get(i).getProductBarcode().getBarcode().trim().equals("") && productListR.get(i).getProductBarcode().getId()!=0){
					ChainStoreSalesOrderProduct orderProduct = productListR.get(i);
				    orderProduct.setChainSalesOrder(this);
				    productSet.add(orderProduct);
				}
			}
		
		if (productListF != null)
			for (int i = 0; i < productListF.size(); i++){
				if (productListF.get(i)!= null && productListF.get(i).getProductBarcode() != null && productListF.get(i).getProductBarcode().getBarcode() != null && !productListF.get(i).getProductBarcode().getBarcode().trim().equals("") && productListF.get(i).getProductBarcode().getId()!=0){
					ChainStoreSalesOrderProduct orderProduct = productListF.get(i);
				    orderProduct.setChainSalesOrder(this);
				    productSet.add(orderProduct);
				}
			}
	}
	
    public void putSetToList(){
		if (productSet != null){
			for (Iterator<ChainStoreSalesOrderProduct> product_Iterator = productSet.iterator();product_Iterator.hasNext();){
				ChainStoreSalesOrderProduct orderProduct = product_Iterator.next();
				if (orderProduct.getType() == ChainStoreSalesOrderProduct.SALES_OUT)
				    productList.add(orderProduct);
				else if (orderProduct.getType() == ChainStoreSalesOrderProduct.RETURN_BACK)
					productListR.add(orderProduct);
				else 
					productListF.add(orderProduct);
			}
			Collections.sort(productList, new ProductSortByIndex());
			Collections.sort(productListR, new ProductSortByIndex());
			Collections.sort(productListF, new ProductSortByIndex());
		}
    }


}
