package com.onlineMIS.action.headQ.barCodeGentor;



import com.onlineMIS.ORM.entity.headQ.barcodeGentor.Brand;
import com.onlineMIS.ORM.entity.headQ.barcodeGentor.Category;
import com.onlineMIS.ORM.entity.headQ.barcodeGentor.Color;
import com.onlineMIS.ORM.entity.headQ.barcodeGentor.NumPerHand;
import com.onlineMIS.ORM.entity.headQ.barcodeGentor.ProductUnit;
import com.onlineMIS.ORM.entity.headQ.barcodeGentor.Quarter;
import com.onlineMIS.ORM.entity.headQ.barcodeGentor.Size;
import com.onlineMIS.ORM.entity.headQ.barcodeGentor.Year;

public class BasicDataActionFormBean {
	private Brand brand = new Brand();
	private Category category = new Category();
	private Size size = new Size();
	private Color color = new Color();
	private Year year = new Year();
	private Quarter quarter = new Quarter();
	private String basicData;
	private Integer basicDataId ;
	private ProductUnit productUnit = new ProductUnit();
	private NumPerHand numPerHand = new NumPerHand();

	
	public ProductUnit getProductUnit() {
		return productUnit;
	}
	public void setProductUnit(ProductUnit productUnit) {
		this.productUnit = productUnit;
	}
	public NumPerHand getNumPerHand() {
		return numPerHand;
	}
	public void setNumPerHand(NumPerHand numPerHand) {
		this.numPerHand = numPerHand;
	}
	public Integer getBasicDataId() {
		return basicDataId;
	}
	public void setBasicDataId(Integer basicDataId) {
		this.basicDataId = basicDataId;
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
	public String getBasicData() {
		return basicData;
	}
	public void setBasicData(String basicData) {
		this.basicData = basicData;
	}
	public Size getSize() {
		return size;
	}
	public void setSize(Size size) {
		this.size = size;
	}
	public Color getColor() {
		return color;
	}
	public void setColor(Color color) {
		this.color = color;
	}
	public Brand getBrand() {
		return brand;
	}
	public void setBrand(Brand brand) {
		this.brand = brand;
	}

	public Category getCategory() {
		return category;
	}
	public void setCategory(Category category) {
		this.category = category;
	}
	
}
