package com.onlineMIS.action.headQ.barCodeGentor;

import java.util.ArrayList;
import java.util.List;

import org.aspectj.weaver.NewConstructorTypeMunger;

import com.onlineMIS.ORM.entity.headQ.barcodeGentor.Brand;
import com.onlineMIS.ORM.entity.headQ.barcodeGentor.Color;
import com.onlineMIS.ORM.entity.headQ.barcodeGentor.Product;
import com.onlineMIS.ORM.entity.headQ.barcodeGentor.ProductBarcode;
import com.onlineMIS.ORM.entity.headQ.barcodeGentor.Quarter;
import com.onlineMIS.ORM.entity.headQ.barcodeGentor.Year;

public class ProductActionUIBean {
	private ProductBarcode product = new ProductBarcode();
	private List<ProductBarcode> products = new ArrayList<ProductBarcode>();
	
	private int index;
	private boolean canViewRecCost = false;
	private BarcodeGenBasicData basicData = new BarcodeGenBasicData();
	private List<Color> colors = new ArrayList<Color>();
	
	

	public List<Color> getColors() {
		return colors;
	}

	public void setColors(List<Color> colors) {
		this.colors = colors;
	}

	/**
	 * the brands list for the
	 */
	private List<Brand> brands = new ArrayList<Brand>();
	

	public List<Brand> getBrands() {
		return brands;
	}

	public void setBrands(List<Brand> brands) {
		this.brands = brands;
	}

	public BarcodeGenBasicData getBasicData() {
		return basicData;
	}

	public void setBasicData(BarcodeGenBasicData basicData) {
		this.basicData = basicData;
	}

	public boolean isCanViewRecCost() {
		return canViewRecCost;
	}

	public void setCanViewRecCost(boolean canViewRecCost) {
		this.canViewRecCost = canViewRecCost;
	}

	public ProductBarcode getProduct() {
		return product;
	}

	public void setProduct(ProductBarcode product) {
		this.product = product;
	}

	public List<ProductBarcode> getProducts() {
		return products;
	}

	public void setProducts(List<ProductBarcode> products) {
		this.products = products;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}
	
}
