package com.onlineMIS.ORM.entity.chainS.inventoryFlow;
import com.onlineMIS.ORM.entity.headQ.barcodeGentor.ProductBarcode;

public class ChainLevelFourInventoryItem  extends ChainInventoryItem {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8461400186045805703L;

	private ProductBarcode productBarcode;
	
	public ChainLevelFourInventoryItem(){
	}
	
	public ChainLevelFourInventoryItem(ChainInventoryItem superItem){
		this.setChainStore(superItem.getChainStore());
		this.setTotalQuantity(superItem.getTotalQuantity());
		this.setTotalCostAmt(superItem.getTotalCostAmt());
		this.setTotalSalesAmt(superItem.getTotalSalesAmt());
	}

	public ProductBarcode getProductBarcode() {
		return productBarcode;
	}
	public void setProductBarcode(ProductBarcode productBarcode) {
		this.productBarcode = productBarcode;
	}
	
	public int getKey(){
		return productBarcode.getId();
	}
	
}
