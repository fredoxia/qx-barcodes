package com.onlineMIS.action.chainS.chainMgmt;

import java.io.File;
import java.util.ArrayList;
import java.sql.Date;
import java.util.List;

import com.onlineMIS.ORM.entity.base.Pager;
import com.onlineMIS.ORM.entity.chainS.chainMgmt.ChainInitialStock;
import com.onlineMIS.ORM.entity.chainS.chainMgmt.ChainPriceIncrement;
import com.onlineMIS.ORM.entity.chainS.chainMgmt.ChainStoreConf;
import com.onlineMIS.ORM.entity.chainS.chainMgmt.ChainStoreGroup;
import com.onlineMIS.ORM.entity.chainS.chainMgmt.QxbabyConf;
import com.onlineMIS.ORM.entity.chainS.user.ChainStore;
import com.onlineMIS.ORM.entity.chainS.user.ChainUserInfor;
import com.onlineMIS.ORM.entity.headQ.barcodeGentor.Brand;
import com.onlineMIS.ORM.entity.headQ.barcodeGentor.ProductBarcode;
import com.onlineMIS.ORM.entity.headQ.barcodeGentor.Quarter;
import com.onlineMIS.ORM.entity.headQ.barcodeGentor.Year;
import com.onlineMIS.action.chainS.vo.ChainProductBarcodeVO;
import com.onlineMIS.common.Common_util;

public class ChainMgmtActionFormBean {
	private ChainStore chainStore = new ChainStore();
	private ChainStoreGroup chainGroup = new ChainStoreGroup();
	private List<Integer> chainStoreIds = new ArrayList<Integer>();
	private ChainStoreConf chainStoreConf = new ChainStoreConf();
	private ChainUserInfor chainUserInfor = new ChainUserInfor();
	private List<ChainInitialStock> stocks = new ArrayList<ChainInitialStock>();
	private String url = "";
	private String barcode = "";
	private String index = "";
	private int chainId ;
	
	private ProductBarcode productBarcode = new ProductBarcode();
	private ChainProductBarcodeVO chainProductBarcodeVO = new ChainProductBarcodeVO();
    
	//file upload
    private File inventory = null;
    private String inventoryContentType;
    private String inventoryFileName;
    
    //查找品牌
    private Brand brand = new Brand();
    private Year year = new Year();
    private Quarter quarter = new Quarter();
    
    //分页的参数
    private Pager pager = new Pager();
    private int accessLevel = Common_util.CHAIN_ACCESS_LEVEL_2;
    
    //搜索
    private String needUpdtDate = "";
	private Date startDate = new Date(new java.util.Date().getTime());
	private Date endDate = new Date(new java.util.Date().getTime());
	
	//isAll : 1 是否列举所有关系的连锁店，0 或者只列举 父亲连锁店， -1 只列举儿子连锁店
	private int isAll = 0;
	
	//includeAllChain : 是否包含 所有连锁店 选项在第一页
	private int indicator = 0;
	
	private ChainPriceIncrement priceIncrement;

	private QxbabyConf qxbabyConf = new QxbabyConf();
	
	
	public int getIsAll() {
		return isAll;
	}
	public void setIsAll(int isAll) {
		this.isAll = isAll;
	}
	public QxbabyConf getQxbabyConf() {
		return qxbabyConf;
	}
	public void setQxbabyConf(QxbabyConf qxbabyConf) {
		this.qxbabyConf = qxbabyConf;
	}
	public int getAccessLevel() {
		return accessLevel;
	}
	public void setAccessLevel(int accessLevel) {
		this.accessLevel = accessLevel;
	}
	public int getIndicator() {
		return indicator;
	}
	public void setIndicator(int indicator) {
		this.indicator = indicator;
	}
	public ChainPriceIncrement getPriceIncrement() {
		return priceIncrement;
	}
	public void setPriceIncrement(ChainPriceIncrement priceIncrement) {
		this.priceIncrement = priceIncrement;
	}
	public String getNeedUpdtDate() {
		return needUpdtDate;
	}
	public void setNeedUpdtDate(String needUpdtDate) {
		this.needUpdtDate = needUpdtDate;
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
	public ChainProductBarcodeVO getChainProductBarcodeVO() {
		return chainProductBarcodeVO;
	}
	public void setChainProductBarcodeVO(ChainProductBarcodeVO chainProductBarcodeVO) {
		this.chainProductBarcodeVO = chainProductBarcodeVO;
	}
	public ProductBarcode getProductBarcode() {
		return productBarcode;
	}
	public void setProductBarcode(ProductBarcode productBarcode) {
		this.productBarcode = productBarcode;
	}
	public List<Integer> getChainStoreIds() {
		return chainStoreIds;
	}
	public void setChainStoreIds(List<Integer> chainStores) {
		this.chainStoreIds = chainStores;
	}
	public ChainStoreGroup getChainGroup() {
		return chainGroup;
	}
	public void setChainGroup(ChainStoreGroup chainGroup) {
		this.chainGroup = chainGroup;
	}
	public Year getYear() {
		return year;
	}
	public void setYear(Year year) {
		this.year = year;
	}
	public Quarter getQuarter() {
		return quarter;
	}
	public void setQuarter(Quarter quarter) {
		this.quarter = quarter;
	}
	public Brand getBrand() {
		return brand;
	}
	public void setBrand(Brand brand) {
		this.brand = brand;
	}
	public Pager getPager() {
		return pager;
	}
	public void setPager(Pager pager) {
		this.pager = pager;
	}
	public ChainStoreConf getChainStoreConf() {
		return chainStoreConf;
	}
	public void setChainStoreConf(ChainStoreConf chainStoreConf) {
		this.chainStoreConf = chainStoreConf;
	}
	public int getChainId() {
		return chainId;
	}
	public void setChainId(int chainId) {
		this.chainId = chainId;
	}
	public String getIndex() {
		return index;
	}
	public void setIndex(String index) {
		this.index = index;
	}
	public String getBarcode() {
		return barcode;
	}
	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}
	public List<ChainInitialStock> getStocks() {
		return stocks;
	}
	public void setStocks(List<ChainInitialStock> stocks) {
		this.stocks = stocks;
	}
	public File getInventory() {
		return inventory;
	}
	public void setInventory(File inventory) {
		this.inventory = inventory;
	}
	public String getInventoryContentType() {
		return inventoryContentType;
	}
	public void setInventoryContentType(String inventoryContentType) {
		this.inventoryContentType = inventoryContentType;
	}
	public String getInventoryFileName() {
		return inventoryFileName;
	}
	public void setInventoryFileName(String inventoryFileName) {
		this.inventoryFileName = inventoryFileName;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public ChainStore getChainStore() {
		return chainStore;
	}
	public void setChainStore(ChainStore chainStore) {
		this.chainStore = chainStore;
	}
	public ChainUserInfor getChainUserInfor() {
		return chainUserInfor;
	}
	public void setChainUserInfor(ChainUserInfor chainUserInfor) {
		this.chainUserInfor = chainUserInfor;
	}
	
}
