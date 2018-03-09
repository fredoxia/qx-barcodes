package com.onlineMIS.ORM.entity.chainS.inventoryFlow;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.onlineMIS.ORM.entity.base.BaseOrder;
import com.onlineMIS.ORM.entity.base.BaseProduct;
import com.onlineMIS.ORM.entity.chainS.user.ChainStore;
import com.onlineMIS.ORM.entity.chainS.user.ChainUserInfor;
import com.onlineMIS.sorter.ChainInventoryOrderProductSorter;
import com.onlineMIS.sorter.ProductSortByIndex;
/**
 * This class is 
 * @author fredo
 *
 */
public class ChainTransferOrder extends BaseOrder {
	//连锁店刚初始化订单  STATUS_INITIAL = 0;
	//连锁店提交的草稿单据 STATUS_DRAFT = 1;
	//连锁店提交了订单，但是对方还没有确认 STATUS_COMPLETE = 2;
	//对方退回订单 STATUS_REJECTED = 5;
	//连锁店确认了订单，完成  STATUS_CONFIRMED = 6;
	
	
	public static final int STATUS_REJECTED = 5;
	public static final int STATUS_CONFIRMED = 6;
	{
		statusMap.put(STATUS_DRAFT, "草稿");
		statusMap.put(STATUS_COMPLETE, "接收方未确认");
		statusMap.put(STATUS_REJECTED, "接收方退回");
		statusMap.put(STATUS_CONFIRMED, "单据完成");
		statusMap.remove(STATUS_CANCEL);
	}
	/**
	 * 
	 */
	private static final long serialVersionUID = 4659566872235652808L;
	private int id;
	private int totalQuantity;
	private double totalWholeSalesPrice = 0;
	private double totalSalesPrice = 0;
	private Date orderDate = new Date();
	private String comment = "";
	private String userComment ="";
	private String creator = "";
	private String confirmedBy = "";
	private ChainStore fromChainStore;
	private ChainStore toChainStore;
	private double transportationFee;

	private Set<ChainTransferOrderProduct> productSet = new HashSet<ChainTransferOrderProduct>();
	private List<ChainTransferOrderProduct> productList = new ArrayList<ChainTransferOrderProduct>();
	
	
	public double getTransportationFee() {
		return transportationFee;
	}
	public void setTransportationFee(double transportationFee) {
		this.transportationFee = transportationFee;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getTotalQuantity() {
		return totalQuantity;
	}
	public void setTotalQuantity(int totalQuantity) {
		this.totalQuantity = totalQuantity;
	}
	public double getTotalWholeSalesPrice() {
		return totalWholeSalesPrice;
	}
	public void setTotalWholeSalesPrice(double totalWholeSalesPrice) {
		this.totalWholeSalesPrice = totalWholeSalesPrice;
	}
	public double getTotalSalesPrice() {
		return totalSalesPrice;
	}
	public void setTotalSalesPrice(double totalSalesPrice) {
		this.totalSalesPrice = totalSalesPrice;
	}
	public Date getOrderDate() {
		return orderDate;
	}
	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public String getUserComment() {
		return userComment;
	}
	public void setUserComment(String userComment) {
		this.userComment = userComment;
	}
	public String getCreator() {
		return creator;
	}
	public void setCreator(String creator) {
		this.creator = creator;
	}
	public String getConfirmedBy() {
		return confirmedBy;
	}
	public void setConfirmedBy(String confirmedBy) {
		this.confirmedBy = confirmedBy;
	}
	public ChainStore getFromChainStore() {
		return fromChainStore;
	}
	public void setFromChainStore(ChainStore fromChainStore) {
		this.fromChainStore = fromChainStore;
	}
	public ChainStore getToChainStore() {
		return toChainStore;
	}
	public void setToChainStore(ChainStore toChainStore) {
		this.toChainStore = toChainStore;
	}

	public Set<ChainTransferOrderProduct> getProductSet() {
		return productSet;
	}
	public void setProductSet(Set<ChainTransferOrderProduct> productSet) {
		this.productSet = productSet;
	}
	public List<ChainTransferOrderProduct> getProductList() {
		return productList;
	}
	public void setProductList(List<ChainTransferOrderProduct> productList) {
		this.productList = productList;
	}
	public void buildIndex() {
		for(int i =0; i< productList.size();i++){
			ChainTransferOrderProduct product = productList.get(i);
			if (product!= null && product.getProductBarcode() != null && product.getProductBarcode().getBarcode() != null && !product.getProductBarcode().getBarcode().trim().equals("") && product.getProductBarcode().getId() != 0){
				product.setIndex(i);		
			}
		}
		
	}

	/**
	 * function to put the list to set
	 * from web to database
	 */
	public void putListToSet(){
		
		if (productList != null)
			for (int i = 0; i < productList.size(); i++){
				if (productList.get(i)!= null && productList.get(i).getProductBarcode() != null && productList.get(i).getProductBarcode().getBarcode() != null && !productList.get(i).getProductBarcode().getBarcode().trim().equals("")&& productList.get(i).getProductBarcode().getId() != 0){
					ChainTransferOrderProduct orderProduct = productList.get(i);
					
					orderProduct.setIndex(i);
				    orderProduct.setTransferOrder(this);
				    productSet.add(orderProduct);
				}
			}
	}

	/**
	 * function to put the 报损，报溢，调货单 product list to set
	 * from web to database
	 */
	public void putSetToList(){
		if (productSet != null && !productSet.isEmpty()){
			for (Iterator<ChainTransferOrderProduct> transferOrderProducts = productSet.iterator();transferOrderProducts.hasNext();){
				productList.add(transferOrderProducts.next());
			}
			
			Collections.sort(productList, new ProductSortByIndex());
		}
	}

}
