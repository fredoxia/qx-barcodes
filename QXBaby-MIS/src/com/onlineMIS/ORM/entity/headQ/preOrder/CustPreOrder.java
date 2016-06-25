package com.onlineMIS.ORM.entity.headQ.preOrder;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Repository;

import com.onlineMIS.ORM.entity.chainS.user.ChainStore;
import com.onlineMIS.ORM.entity.headQ.inventory.InventoryOrderProduct;
import com.onlineMIS.common.Common_util;
import com.onlineMIS.sorter.ProductSortByIndex;


public class CustPreOrder implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1151511966760113877L;
	private int id;
	private int custId ;
	private String orderIdentity = "";
	private String custName = "";
    private ChainStore chainStore = new ChainStore();
    private String chainStoreName = "";
    private int totalQuantity = 0;
    private Date createDate = Common_util.getToday();
    private double sumCost = 0;
    private double sumWholePrice = 0;
    private double sumRetailPrice = 0;
    private Date exportDate = Common_util.getToday();
    private int status;
    private String comment = "";
    private Set<CustPreOrderProduct> productSet = new HashSet<CustPreOrderProduct>();
    private List<CustPreOrderProduct> productList = new ArrayList<CustPreOrderProduct>();
    
    
	public List<CustPreOrderProduct> getProductList() {
		return productList;
	}
	public void setProductList(List<CustPreOrderProduct> productList) {
		this.productList = productList;
	}
	public Set<CustPreOrderProduct> getProductSet() {
		return productSet;
	}
	public void setProductSet(Set<CustPreOrderProduct> productSet) {
		this.productSet = productSet;
	}
	public String getOrderIdentity() {
		return orderIdentity;
	}
	public void setOrderIdentity(String orderIdentity) {
		this.orderIdentity = orderIdentity;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public double getSumCost() {
		return sumCost;
	}
	public void setSumCost(double sumCost) {
		this.sumCost = sumCost;
	}
	public double getSumWholePrice() {
		return sumWholePrice;
	}
	public void setSumWholePrice(double sumWholePrice) {
		this.sumWholePrice = sumWholePrice;
	}
	public double getSumRetailPrice() {
		return sumRetailPrice;
	}
	public void setSumRetailPrice(double sumRetailPrice) {
		this.sumRetailPrice = sumRetailPrice;
	}
	public Date getExportDate() {
		return exportDate;
	}
	public void setExportDate(Date exportDate) {
		this.exportDate = exportDate;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}

	public int getCustId() {
		return custId;
	}

	public void setCustId(int custId) {
		this.custId = custId;
	}

	public String getCustName() {
		return custName;
	}


	public void setCustName(String custName) {
		this.custName = custName;
	}

	
	public ChainStore getChainStore() {
		return chainStore;
	}
	public void setChainStore(ChainStore chainStore) {
		this.chainStore = chainStore;
	}
	public String getChainStoreName() {
		return chainStoreName;
	}

	public void setChainStoreName(String chainStoreName) {
		this.chainStoreName = chainStoreName;
	}

	public int getTotalQuantity() {
		return totalQuantity;
	}

	public void setTotalQuantity(int totalQuantity) {
		this.totalQuantity = totalQuantity;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
//	
//	public void putListToSet(){
//		if (productList != null)
//			for (int i = 0; i < productList.size(); i++){
//				if (productList.get(i)!= null && productList.get(i).getProductBarcode() != null && productList.get(i).getProductBarcode().getBarcode() != null && !product_List.get(i).getProductBarcode().getBarcode().trim().equals("") && product_List.get(i).getQuantity()>0){
//					InventoryOrderProduct orderProduct = product_List.get(i);
//				    orderProduct.setOrder(this);
//				    product_Set.add(orderProduct);
//				}
//			}
//    }
    
    public void putSetToList(){
    	productList.clear();
		if (productSet != null){
			for (Iterator<CustPreOrderProduct> product_Iterator = productSet.iterator();product_Iterator.hasNext();){
				productList.add(product_Iterator.next());
			}
			Collections.sort(productList, new ProductSortByIndex());
		}
    }
    
	@Override
	public String toString() {
		return "CustPreOrder [id=" + id + ", custId=" + custId
				+ ", orderIdentity=" + orderIdentity + ", custName=" + custName
				+ ", chainId=" + chainStore + ", chainStoreName=" + chainStoreName
				+ ", totalQuantity=" + totalQuantity + ", sumCost=" + sumCost
				+ ", sumWholePrice=" + sumWholePrice + ", sumRetailPrice="
				+ sumRetailPrice + ", status=" + status + ", comment="
				+ comment + "]";
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
