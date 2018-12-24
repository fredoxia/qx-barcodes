package supplier;

import java.io.Serializable;

import com.onlineMIS.ORM.entity.headQ.barcodeGentor.ProductBarcode;

public class HeadqPurchaseOrderItem implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -8270582612753088986L;
	private HeadqPurchaseOrder order;
	private ProductBarcode productBarcode;
	private int quantity;
	private int index;
	private double cost;
	private double totalCost;
	
	public HeadqPurchaseOrder getOrder() {
		return order;
	}
	public void setOrder(HeadqPurchaseOrder order) {
		this.order = order;
	}
	public ProductBarcode getProductBarcode() {
		return productBarcode;
	}
	public void setProductBarcode(ProductBarcode productBarcode) {
		this.productBarcode = productBarcode;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public int getIndex() {
		return index;
	}
	public void setIndex(int index) {
		this.index = index;
	}
	public double getCost() {
		return cost;
	}
	public void setCost(double cost) {
		this.cost = cost;
	}
	public double getTotalCost() {
		return totalCost;
	}
	public void setTotalCost(double totalCost) {
		this.totalCost = totalCost;
	}
	
	
}
