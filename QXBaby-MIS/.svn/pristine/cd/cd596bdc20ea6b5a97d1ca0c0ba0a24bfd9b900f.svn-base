package com.onlineMIS.action.headQ.barCodeGentor;

import java.util.ArrayList;
import java.sql.Date;
import java.util.List;
import com.onlineMIS.ORM.entity.base.Pager;
import com.onlineMIS.ORM.entity.headQ.barcodeGentor.ProductBarcode;

public class ProductActionFormBean {
	private ProductBarcode productBarcode = new ProductBarcode();
	private List<Integer> colorIds = new ArrayList<Integer>();
	private List<Integer> brandIds = new ArrayList<Integer>();
	private List<Integer> sizeIds = new ArrayList<Integer>();
	private Date startDate = new Date(new java.util.Date().getTime());
	private Date endDate = new Date(new java.util.Date().getTime());
	private String needCreateDate = "";
	protected int indexPage;
    protected int client_id;
    protected int orderType;
    private String colorNames;
    private Pager pager = new Pager();

    
    /**
     * 来至哪里
     * 0: barcode
     * 1: productCode
     */
    private int fromSrc = 0;

	public int getFromSrc() {
		return fromSrc;
	}

	public void setFromSrc(int fromSrc) {
		this.fromSrc = fromSrc;
	}

	public List<Integer> getBrandIds() {
		return brandIds;
	}

	public void setBrandIds(List<Integer> brandIds) {
		this.brandIds = brandIds;
	}

	public String getColorNames() {
		return colorNames;
	}

	public void setColorNames(String colorNames) {
		this.colorNames = colorNames;
	}

	public Pager getPager() {
		return pager;
	}

	public void setPager(Pager pager) {
		this.pager = pager;
	}

	public int getIndexPage() {
		return indexPage;
	}

	public void setIndexPage(int indexPage) {
		this.indexPage = indexPage;
	}
	public int getOrderType() {
		return orderType;
	}

	public void setOrderType(int orderType) {
		this.orderType = orderType;
	}


	public int getClient_id() {
		return client_id;
	}

	public void setClient_id(int client_id) {
		this.client_id = client_id;
	}
	
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getNeedCreateDate() {
		return needCreateDate;
	}
	public void setNeedCreateDate(String needCreateDate) {
		this.needCreateDate = needCreateDate;
	}
	public ProductBarcode getProductBarcode() {
		return productBarcode;
	}
	public void setProductBarcode(ProductBarcode productBarcode) {
		this.productBarcode = productBarcode;
	}
	public List<Integer> getColorIds() {
		return colorIds;
	}
	public void setColorIds(List<Integer> colorIds) {
		this.colorIds = colorIds;
	}
	public List<Integer> getSizeIds() {
		return sizeIds;
	}
	public void setSizeIds(List<Integer> sizeIds) {
		this.sizeIds = sizeIds;
	}

}
