package com.onlineMIS.common;

import java.awt.image.RescaleOp;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.onlineMIS.ORM.DAO.Response;

public class FileOperation {
	public static boolean deleteFiles(String path){
		boolean success = true;
		File directory = new File(path);
		if (directory.isDirectory()){
			File[] files = directory.listFiles();
			for(File file : files){
				try {
				   file.delete();
				} catch (Exception e){
					success = false;
					loggerLocal.error(e);
				}
			}
		} else 
			try {
			    directory.delete();
			} catch (Exception e){
				success = false;
				loggerLocal.error(e);
			}
		
		return success;
	}
	
	public static List<String> readFile(File file ) {
		List<String> outputList = new ArrayList<String>();
		
		BufferedReader br;
		try {
			br = new BufferedReader(new FileReader(file));
			String line="";
			
			while((line=br.readLine())!=null){
				outputList.add(line);
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return outputList;

	}

	public static void saveWorkbook(HSSFWorkbook wholeChainWorkbook, String filePath, Response response) {
		try {   
			FileOutputStream os = new FileOutputStream(new File(filePath)); 
			wholeChainWorkbook.write(os);   
		} catch (IOException e) {   
			loggerLocal.error(e);
			response.setFail(e.getMessage());
	    }  
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
