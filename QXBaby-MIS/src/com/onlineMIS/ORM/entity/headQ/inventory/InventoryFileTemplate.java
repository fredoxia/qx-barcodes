package com.onlineMIS.ORM.entity.headQ.inventory;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.onlineMIS.ORM.DAO.Response;
import com.onlineMIS.common.Common_util;
import com.onlineMIS.common.FileOperation;
import com.onlineMIS.common.loggerLocal;

public class InventoryFileTemplate {
	//public static final String DELIMETER = ";";
	public static final String DECLARE = ",";
	
	private File inventoryFile;

	public File getInventoryFile() {
		return inventoryFile;
	}
	public void setInventoryFile(File inventoryFile) {
		this.inventoryFile = inventoryFile;
	}

	
	public InventoryFileTemplate(File invenFile){
		this.inventoryFile = invenFile;
	}
	
	public Response process(){
		Response response = new Response();
		Set<String> barcodes = new HashSet<String>();
		Map<String, Integer> barcodeMap = new HashMap<String, Integer>();

		String error = "";
		
		if (inventoryFile != null){
			
			List<String> files = FileOperation.readFile(inventoryFile);

			//1. format the original data
			String[] eles = null;
			String barcode = "";
			String quantityS = "";
			for (String fileEle: files){
                if (fileEle == null || fileEle.trim().equals(""))
                	continue;
				eles = fileEle.split(DECLARE);
				barcode = eles[0].trim();
				quantityS = eles[1].trim();

				if (barcode.equals("") || quantityS.equals("")){
					error += barcode + ":" + quantityS;
					continue;
				} else {
					int quantity = 0;
					
					try{
					    quantity = Integer.parseInt(quantityS);
					} catch (Exception e) {
						error += barcode + ":" + quantityS;
						continue;
					}
					
					if (barcodeMap.containsKey(barcode)){
						int quantityOriginal = barcodeMap.get(barcode);
						barcodeMap.put(barcode, quantityOriginal + quantity);
					} else {
						barcodeMap.put(barcode, quantity);
					}
					
					barcodes.add(barcode);
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
		
//		barcodes = new ArrayList<String>();
//		if (!barcodes.contains("000000045537"))
//		   barcodes.add("000000045537");
//		if (!barcodes.contains("166000109324"))
//		   barcodes.add("166000109324");
		
		returns.add(barcodes);
		
		response.setReturnValue(returns);
		return response;
	}

}
