package com.onlineMIS.ORM.entity.headQ.chain;

import com.onlineMIS.ORM.entity.headQ.barcodeGentor.Product;
import com.onlineMIS.ORM.entity.headQ.inventory.InventoryOrderProduct;
import com.onlineMIS.common.Common_util;

/**
 * this item is for chain sales report for each item in brand
 * @author fredo
 *
 */
public class ChainSalesReportResultItem  extends InventoryOrderProduct{
	/**
	 * 
	 */
	private static final long serialVersionUID = -6783461100656868482L;
	private double salesRevenue = 0;
	private double wholeRevenue=0;

	public double getWholeRevenue() {
		return wholeRevenue;
	}

	public void setWholeRevenue(double wholeRevenue) {
		this.wholeRevenue = wholeRevenue;
	}

	public double getSalesRevenue() {
		return salesRevenue;
	}

	public void setSalesRevenue(double salesRevenue) {
		this.salesRevenue = salesRevenue;
	}

	public ChainSalesReportResultItem(){
		
	}
	
	public ChainSalesReportResultItem(InventoryOrderProduct orderProduct){
		this.setID(orderProduct.getID());
		this.setIndex(orderProduct.getIndex());
		this.setMoreThanTwoHan(orderProduct.getMoreThanTwoHan());
		this.setOrder(orderProduct.getOrder());
		Product product = orderProduct.getProductBarcode().getProduct();
		
		//need set the current whole sale price 
		double wholePrice = 0;
		if (product.getWholeSalePrice3() != 0)
			wholePrice = product.getWholeSalePrice3();
		else if (product.getWholeSalePrice2() != 0)
			wholePrice = product.getWholeSalePrice2();
		else if (product.getWholeSalePrice() != 0)
			wholePrice = product.getWholeSalePrice();
		else if (product.getSalesPriceFactory() != 0 && product.getDiscount() != 0)
			wholePrice = Common_util.roundDouble(product.getSalesPriceFactory() * product.getDiscount(),2);
		
		product.setWholeSalePrice(wholePrice);
		this.setProductBarcode(orderProduct.getProductBarcode());
		this.setQuantity(orderProduct.getQuantity());
	}


}
