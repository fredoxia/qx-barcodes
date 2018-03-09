package com.onlineMIS.ORM.entity.chainS.chainMgmt;

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

public class BatchPriceTemplate {
	//public static final String DELIMETER = ";";
	public static final String DECLARE = ",";
	
	private File batchPriceFile;

	public File getBatchPriceFile() {
		return batchPriceFile;
	}

	public void setBatchPriceFile(File batchPriceFile) {
		this.batchPriceFile = batchPriceFile;
	}

	public BatchPriceTemplate(File batchPriceFile){
		this.batchPriceFile = batchPriceFile;
	}
	
	public Response process(){
		Response response = new Response();
		Set<String> barcodes = new HashSet<String>();
		Map<String, Double> barcodeMap = new HashMap<String, Double>();

		String error = "";
		
		if (batchPriceFile != null){
			
			List<String> files = FileOperation.readFile(batchPriceFile);

			//1. format the original data
			String[] eles = null;
			String barcode = "";
			String priceS = "";
			for (String fileEle: files){
                if (fileEle == null || fileEle.trim().equals(""))
                	continue;
				eles = fileEle.split(DECLARE);
				barcode = eles[0].trim();
				priceS = eles[1].trim();

				if (barcode.equals("") || priceS.equals("")){
					error += barcode + ":" + priceS;
					continue;
				} else {
					double price = 0;
					
					try{
						price = Double.parseDouble(priceS);
					} catch (Exception e) {
						error += barcode + ":" + price;
						continue;
					}
					
					if (barcodeMap.containsKey(barcode)){
						double priceOriginal = barcodeMap.get(barcode);
						barcodeMap.put(barcode, priceOriginal + price);
					} else {
						barcodeMap.put(barcode, price);
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
