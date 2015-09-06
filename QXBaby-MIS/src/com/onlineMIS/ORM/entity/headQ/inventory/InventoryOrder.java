package com.onlineMIS.ORM.entity.headQ.inventory;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import com.onlineMIS.ORM.entity.headQ.user.UserInfor;
import com.onlineMIS.sorter.ProductSortByIndex;

public class InventoryOrder implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3040767356713945032L;
	//pda扫描草稿
	public static final int STATUS_PDA_DRAFT = 7;
	//pda扫描完成
	public static final int STATUS_PDA_COMPLETE = 8;
	//仓库草稿
	public static final int STATUS_DRAFT = 9;
	public static final int STATUS_INITIAL = 0;
	//仓库完成
	public static final int STATUS_COMPLETE = 1;
	//会计进行中
	public static final int STATUS_ACCOUNT_PROCESS = 2;
	//会计完成
	public static final int STATUS_ACCOUNT_COMPLETE = 3;
	public static final int STATUS_DELETED = 4;
	public static final int STATUS_CANCELLED = 5;
	//单据待审核 (审核完成才能导入到连锁店)
	public static final int STATUS_WAITING_AUDIT = 6;
	
	//for the order type
	public static final int TYPE_SALES_ORDER_W = 0;
	public static final int TYPE_SALES_RETURN_ORDER_W = 1;
	
	/**
	 * maps to store the types of order
	 */
	private static Map<Integer, String> typesMap_wholeSaler = new LinkedHashMap<Integer, String>();
	private static Map<Integer, String> typesMap_retailer = new LinkedHashMap<Integer, String>();
	private static Map<Integer, String> orderStatusMap = new LinkedHashMap<Integer, String>();
	
	static {
		typesMap_wholeSaler.put(TYPE_SALES_ORDER_W, "销售出库单");
		typesMap_wholeSaler.put(TYPE_SALES_RETURN_ORDER_W, "销售退货单");
		
		typesMap_retailer.put(TYPE_SALES_ORDER_W, "采购进库单");
		typesMap_retailer.put(TYPE_SALES_RETURN_ORDER_W, "采购退货单");
		
		orderStatusMap.put(STATUS_PDA_DRAFT, "PDA草稿");
		orderStatusMap.put(STATUS_PDA_COMPLETE, "PDA完成");
		orderStatusMap.put(STATUS_DRAFT, "仓库草稿");
		orderStatusMap.put(STATUS_COMPLETE, "仓库完成");
		orderStatusMap.put(STATUS_ACCOUNT_PROCESS, "会计录入中");
		orderStatusMap.put(STATUS_ACCOUNT_COMPLETE, "审核完成");
		orderStatusMap.put(STATUS_CANCELLED, "单据红冲");
		orderStatusMap.put(STATUS_WAITING_AUDIT, "待审核单据");
	}

    public int order_ID;
    /*8
     *the two are from SQLserver db, so can only store the primitive data
     */
    private int client_id = 0;
    private String client_name ="";

    private Date order_StartTime = new Date();
    /**
     * warehouse complete time
     */
    private Date order_ComplTime;
    private int order_Status;
    private String order_Status_s;
    /**
     * pda扫描人员
     */
    private UserInfor pdaScanner;
    /**
     * 仓库单据扫描人员
     */
    private UserInfor order_scanner;
    /**
     * 仓库单据创建人员
     */
    private UserInfor order_Keeper;
    /**
     * 点数人员
     */
    private UserInfor order_Counter;
    /**
     * 会计处理单据人员
     */
    private UserInfor order_Auditor;
    /**
     * account complete order
     */
    private Date order_EndTime;
    private String comment;
    private double preAcctAmt;
    private double postAcctAmt;
    
    private List<InventoryOrderProduct> product_List = new ArrayList<InventoryOrderProduct>();
    private Set<InventoryOrderProduct> product_Set = new HashSet<InventoryOrderProduct>();
	


    /**
     * 0: sales order (for clients: purchase order)
     * 1: returned order (for clients: purchase return order)
     */
    private int order_type = -1;
    private String order_type_ws;
    private String order_type_chain;
    
    private int totalQuantity;
    private double totalRetailPrice;
    private double totalRecCost;
    private double totalWholePrice;
    private double totalPaid;
    private double totalDiscount;
    private int importTimes;
    private int totalQuantityA;
    private double totalWholePriceA;

	public UserInfor getPdaScanner() {
		return pdaScanner;
	}
	public void setPdaScanner(UserInfor pdaScanner) {
		this.pdaScanner = pdaScanner;
	}
	public double getPreAcctAmt() {
		return preAcctAmt;
	}
	public void setPreAcctAmt(double preAcctAmt) {
		this.preAcctAmt = preAcctAmt;
	}
	public double getPostAcctAmt() {
		return postAcctAmt;
	}
	public void setPostAcctAmt(double postAcctAmt) {
		this.postAcctAmt = postAcctAmt;
	}
	public int getImportTimes() {
		return importTimes;
	}
	public void setImportTimes(int importTimes) {
		this.importTimes = importTimes;
	}


	public String getOrder_type_chain() {
		return getTypeRetailer(order_type);
	}

	public String getOrder_type_ws() {
		return getTypeWholeSaler(order_type);
	}

	public int getTotalQuantity() {
		return totalQuantity;
	}

	public void setTotalQuantity(int totalQuantity) {
		this.totalQuantity = totalQuantity;
	}

	public double getTotalRetailPrice() {
		return totalRetailPrice;
	}

	public void setTotalRetailPrice(double totalRetailPrice) {
		this.totalRetailPrice = totalRetailPrice;
	}

	public double getTotalRecCost() {
		return totalRecCost;
	}

	public void setTotalRecCost(double totalRecCost) {
		this.totalRecCost = totalRecCost;
	}

	public double getTotalWholePrice() {
		return totalWholePrice;
	}

	public void setTotalWholePrice(double totalWholePrice) {
		this.totalWholePrice = totalWholePrice;
	}

	public UserInfor getOrder_scanner() {
		return order_scanner;
	}

	public void setOrder_scanner(UserInfor order_scanner) {
		this.order_scanner = order_scanner;
	}



	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public UserInfor getOrder_Counter() {
		return order_Counter;
	}

	public void setOrder_Counter(UserInfor order_Counter) {
		this.order_Counter = order_Counter;
	}
   
    public UserInfor getOrder_Keeper() {
		return order_Keeper;
	}

	public void setOrder_Keeper(UserInfor order_Keeper) {
		this.order_Keeper = order_Keeper;
	}

	public int getOrder_ID() {
		return order_ID;
	}
     
	public void setOrder_ID(int order_ID) {
		this.order_ID = order_ID;
	}

	public Date getOrder_StartTime() {
		return order_StartTime;
	}
	public void setOrder_StartTime(Date order_StartTime) {
		this.order_StartTime = order_StartTime;
	}

	public Date getOrder_ComplTime() {
		return order_ComplTime;
	}

	public void setOrder_ComplTime(Date order_ComplTime) {
		this.order_ComplTime = order_ComplTime;
	}

	public Set<InventoryOrderProduct> getProduct_Set() {
		return product_Set;
	}

	public void setProduct_Set(Set<InventoryOrderProduct> product_Set) {
		this.product_Set = product_Set;
	}


	public int getOrder_Status() {
		return order_Status;
	}
	public void setOrder_Status(int order_Status) {
		this.order_Status = order_Status;
	}

	public UserInfor getOrder_Auditor() {
		return order_Auditor;
	}

	public void setOrder_Auditor(UserInfor order_Auditor) {
		this.order_Auditor = order_Auditor;
	}

	public Date getOrder_EndTime() {
		return order_EndTime;
	}
	public void setOrder_EndTime(Date order_EndTime) {
		this.order_EndTime = order_EndTime;
	}
	public List<InventoryOrderProduct> getProduct_List() {
		return product_List;
	}
	public void setProduct_List(List<InventoryOrderProduct> product_List) {
		this.product_List = product_List;

	}

	public int getClient_id() {
		return client_id;
	}

	public void setClient_id(int client_id) {
		this.client_id = client_id;
	}

	public String getClient_name() {
		return client_name;
	}

	public void setClient_name(String client_name) {
		this.client_name = client_name;
	}
	public int getOrder_type() {
		return order_type;
	}

	public void setOrder_type(int order_type) {
		this.order_type = order_type;
	}
	

	public static Map<Integer, String> getTypesMap_wholeSaler() {
		return typesMap_wholeSaler;
	}


	public static Map<Integer, String> getTypesMap_retailer() {
		return typesMap_retailer;
	}


	public static Map<Integer, String> getOrderStatusMap() {
		return orderStatusMap;
	}
	
	public static String getTypeWholeSaler(int typeid){
		return typesMap_wholeSaler.get(typeid);
	}
	
	public static String getTypeRetailer(int typeid){
		return typesMap_retailer.get(typeid);
	}
	
	public String getOrder_Status_s() {
		return orderStatusMap.get(order_Status);
	}

	public double getTotalPaid() {
		return totalPaid;
	}
	public void setTotalPaid(double totalPaid) {
		this.totalPaid = totalPaid;
	}
	public double getTotalDiscount() {
		return totalDiscount;
	}
	public void setTotalDiscount(double totalDiscount) {
		this.totalDiscount = totalDiscount;
	}
	
	public int getTotalQuantityA() {
		return totalQuantityA;
	}
	public void setTotalQuantityA(int totalQuantityA) {
		this.totalQuantityA = totalQuantityA;
	}
	public double getTotalWholePriceA() {
		return totalWholePriceA;
	}
	public void setTotalWholePriceA(double totalWholePriceA) {
		this.totalWholePriceA = totalWholePriceA;
	}
	public void putListToSet(){
		if (product_List != null)
			for (int i = 0; i < product_List.size(); i++){
				if (product_List.get(i)!= null && product_List.get(i).getProductBarcode() != null && product_List.get(i).getProductBarcode().getBarcode() != null && !product_List.get(i).getProductBarcode().getBarcode().trim().equals("") && product_List.get(i).getQuantity()>0){
					InventoryOrderProduct orderProduct = product_List.get(i);
				    orderProduct.setOrder(this);
				    product_Set.add(orderProduct);
				}
			}
    }
    
    public void putSetToList(){
		if (product_Set != null){
			for (Iterator<InventoryOrderProduct> product_Iterator = product_Set.iterator();product_Iterator.hasNext();){
				product_List.add(product_Iterator.next());
			}
			Collections.sort(product_List, new ProductSortByIndex());
		}
    }
    
    /**
     * TO calculate the total quantity, total price and check the quantity >= 2 number of hand
     */
//    public void calTotalCheckQ(){
//    	int total = 0;
//    	double total_salesprice = 0;
//    	double total_wholeSalePrice = 0;
//		if (product_Set != null)
//			for (Iterator<InventoryOrderProduct> product_Iterator = product_Set.iterator();product_Iterator.hasNext();){
//				InventoryOrderProduct product = product_Iterator.next();
//				int q = product.getQuantity();
//				int numPerHand = product.getProduct().getNumPerHand();
//				total += q;
//				total_salesprice += q * product.getSalesPrice();
//				total_wholeSalePrice += q * product.getWholeSalePrice();
//				if (q >= numPerHand*2)
//					product.setMoreThanTwoHan(true);
//				else 
//					product.setMoreThanTwoHan(false);
//			}
//    	setTotal_Quantity(total);
//    	setTotal_price_salesPrice(total_salesprice);
//    	setTotal_price_wholeSalePrice(total_wholeSalePrice);
//    }

    /**
     * to build the index for each order product
     * index is to tell the sequence of the products scanned
     */
	public void buildIndex() {
		for(int i =0; i< product_List.size();i++){
			InventoryOrderProduct product = product_List.get(i);
			if (product!= null && product.getProductBarcode() != null && product.getProductBarcode().getBarcode() != null && !product.getProductBarcode().getBarcode().trim().equals(""))
			   product.setIndex(i);
		}
		
	}

	
	public String toString(){
		String objString = "";
			objString += "clientid =" + this.getClient_id() + ",";
			objString += "clientname =" + this.getClient_name() + ",";
			objString += "order status =" + this.getOrder_Status() + ",";
			objString += "account =" + this.getOrder_Auditor() + ",";
			objString += "start time =" + this.getOrder_StartTime() + ",";
			objString += "end time =" + this.getOrder_EndTime();
		return objString;
	}
    		 
}
