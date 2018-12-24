package supplier;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.onlineMIS.ORM.entity.headQ.user.UserInfor;

public class HeadqPurchaseOrder implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -8628904820944690447L;
	private int id;
	private String comment;
	private Date lastUpdtTime;
	private int status;
	private int type;
	private int supplier;
	private String supplierName;
	private int quantity;
	private double cost;
	private double amount;
	private double discount;
	private double preAcctAmt;
	private double postAcctAmt;
	private UserInfor account;
	private Set<HeadqPurchaseOrderItem> productSet = new HashSet<HeadqPurchaseOrderItem>();
	private List<HeadqPurchaseOrderItem> productList= new ArrayList<HeadqPurchaseOrderItem>();
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public Date getLastUpdtTime() {
		return lastUpdtTime;
	}
	public void setLastUpdtTime(Date lastUpdtTime) {
		this.lastUpdtTime = lastUpdtTime;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public int getSupplier() {
		return supplier;
	}
	public void setSupplier(int supplier) {
		this.supplier = supplier;
	}
	public String getSupplierName() {
		return supplierName;
	}
	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public double getCost() {
		return cost;
	}
	public void setCost(double cost) {
		this.cost = cost;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public double getDiscount() {
		return discount;
	}
	public void setDiscount(double discount) {
		this.discount = discount;
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
	public UserInfor getAccount() {
		return account;
	}
	public void setAccount(UserInfor account) {
		this.account = account;
	}
	public Set<HeadqPurchaseOrderItem> getProductSet() {
		return productSet;
	}
	public void setProductSet(Set<HeadqPurchaseOrderItem> productSet) {
		this.productSet = productSet;
	}
	public List<HeadqPurchaseOrderItem> getProductList() {
		return productList;
	}
	public void setProductList(List<HeadqPurchaseOrderItem> productList) {
		this.productList = productList;
	}
	
	
}
