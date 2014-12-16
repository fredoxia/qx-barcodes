package com.onlineMIS.ORM.entity.chainS.chainMgmt;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.onlineMIS.ORM.DAO.Response;
import com.onlineMIS.ORM.entity.chainS.chainMgmt.ChainInitialStock;
import com.onlineMIS.common.Common_util;
import com.onlineMIS.common.FileOperation;
import com.onlineMIS.common.loggerLocal;

public class InitialInventoryFileTemplate {
	//public static final String DELIMETER = ";";
	public static final String DECLARE = ",";
	
	private File inventoryFile;
	private List<String> barcodes = new ArrayList<String>();
	private Map<String, ChainInitialStock> barcodeMap = new HashMap<String, ChainInitialStock>();

	public List<String> getBarcodes() {
		return barcodes;
	}
	public void setBarcodes(List<String> barcodes) {
		this.barcodes = barcodes;
	}
	public File getInventoryFile() {
		return inventoryFile;
	}
	public void setInventoryFile(File inventoryFile) {
		this.inventoryFile = inventoryFile;
	}

	public Map<String, ChainInitialStock> getBarcodeMap() {
		return barcodeMap;
	}
	public void setBarcodeMap(Map<String, ChainInitialStock> barcodeMap) {
		this.barcodeMap = barcodeMap;
	}
	
	public InitialInventoryFileTemplate(File invenFile){
		this.inventoryFile = invenFile;
	}
	
	public Response process(){
		Response response = new Response();
		String error = "";
		
		if (inventoryFile != null){
			
			List<String> files = FileOperation.readFile(inventoryFile);

			//1. format the original data
			for (String fileEle: files){
                if (fileEle == null || fileEle.trim().equals(""))
                	continue;
				String[] eles = fileEle.split(DECLARE);
				String barcode = eles[0].trim();
				String quantityS = eles[1].trim();
				String costS = eles[2].trim();

				if (barcode.equals("") || quantityS.equals("")){
					error += barcode + ":" + quantityS;
					continue;
				} else {
					int quantity = 0;
					double cost = 0;
					try{
					    quantity = Integer.parseInt(quantityS);
					    cost = Double.parseDouble(costS);
					} catch (Exception e) {
						error += barcode + ":" + quantityS + "," + costS;
						continue;
					}
					
					if (barcodeMap.containsKey(barcode)){
						ChainInitialStock initialStock = barcodeMap.get(barcode);
						int newQuantity = initialStock.getQuantity() + quantity;
						double newCostTotal = newQuantity * cost;
						initialStock.setCost(cost);
						initialStock.setQuantity(newQuantity);
						initialStock.setCostTotal(newCostTotal);
						barcodeMap.put(barcode, initialStock);
					} else {
						ChainInitialStock initialStock  = new ChainInitialStock();
						double costTotal = quantity * cost;
						initialStock.setCost(cost);
						initialStock.setQuantity(quantity);
						initialStock.setCostTotal(costTotal);
						barcodeMap.put(barcode, initialStock);
						barcodes.add(barcode);
					}
				}
			}
		} else {
			error += "File is empty";
		}
		
		if (error.length() == 0)
			response.setReturnCode(Response.SUCCESS);
		else
			response.setReturnCode(Response.WARNING);
		List<Object> returns = new ArrayList<Object>();
		returns.add(barcodeMap);
		returns.add(barcodes);
		
		response.setReturnValue(returns);
		return response;
	}

}
