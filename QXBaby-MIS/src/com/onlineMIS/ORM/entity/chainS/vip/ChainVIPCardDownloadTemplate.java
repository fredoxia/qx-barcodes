package com.onlineMIS.ORM.entity.chainS.vip;


import java.io.IOException;
import java.sql.Date;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import com.onlineMIS.ORM.entity.headQ.barcodeGentor.Color;
import com.onlineMIS.ORM.entity.headQ.barcodeGentor.Product;
import com.onlineMIS.common.Common_util;
import com.onlineMIS.common.ExcelTemplate;


public class ChainVIPCardDownloadTemplate extends ExcelTemplate{
	private final int header_row = 0;
	private final int data_row = 1;
	
	private final int cardNo_column= 0;
	private final int cardTye_column = 1;
	private final int issueChainStore_column= 2;
	private final int issueDate_column = 3;
	private final int expireDate_column = 4;
	private final int status_column = 5;
	private final int custNm_column= 6;
	private final int gender_column= 7;
	private final int telephone_column= 8;
	private final int birthday_column= 9;
	private final int address_column = 11;
	private final int comment_column = 10;
	private final int vipScore_column = 12;
	private final int last_consump_date = 14;
	private final int prepaid_accumulated = 13;
	
	private List<ChainVIPCard> vipCards;
	private Map<Integer, List<Double>> vipScoreMap ;
	private Map<Integer, Date> vipLastConsumpMap;
	Map<Integer, Double> vipPrepaidAccumulated;
	
	public ChainVIPCardDownloadTemplate(List<ChainVIPCard> vipCards,Map<Integer, List<Double>> scoreMap,Map<Integer, Date> vipLastConsumpMap, Map<Integer, Double> vipPrepaidAccumulated,String templateWorkbookPath) throws IOException{
		super(templateWorkbookPath);		
		this.vipCards = vipCards;	
		this.vipScoreMap = scoreMap;
		this.vipLastConsumpMap = vipLastConsumpMap;
		this.vipPrepaidAccumulated = vipPrepaidAccumulated;
	}
	
	public HSSFWorkbook process(){
		HSSFSheet sheet = templateWorkbook.getSheetAt(0);
        
		//写vip信息
		if (vipCards != null && vipCards.size() > 0){
			for (int i = 0; i < vipCards.size(); i++){
				ChainVIPCard vipCard = vipCards.get(i);
				Row row = sheet.createRow(data_row + i);
				
				row.createCell(cardNo_column).setCellValue(vipCard.getVipCardNo());
				row.createCell(cardTye_column).setCellValue(vipCard.getVipType().getVipTypeName());
				row.createCell(issueChainStore_column).setCellValue(vipCard.getIssueChainStore().getChain_name());
				row.createCell(issueDate_column).setCellValue(Common_util.dateFormat.format(vipCard.getCardIssueDate()));
				row.createCell(expireDate_column).setCellValue(Common_util.dateFormat.format(vipCard.getCardExpireDate()));
				row.createCell(status_column).setCellValue(vipCard.getStatusS());
				row.createCell(custNm_column).setCellValue(vipCard.getCustomerName());
				row.createCell(gender_column).setCellValue(vipCard.getGenderS());
				row.createCell(telephone_column).setCellValue(vipCard.getTelephone());
				row.createCell(birthday_column).setCellValue(Common_util.dateFormat.format(vipCard.getCustomerBirthday()));
				row.createCell(comment_column).setCellValue(vipCard.getComment());
				row.createCell(address_column).setCellValue(vipCard.getProvince() + vipCard.getCity() + vipCard.getZone() + vipCard.getStreet());
				
				List<Double> vipScores = vipScoreMap.get(vipCard.getId());
				double scoreValue = vipCard.getInitialScore();
				if (vipScores != null){
					scoreValue += vipScores.get(0) - vipScores.get(1);
				}
				row.createCell(vipScore_column).setCellValue(scoreValue);
				
				Date lastDate = vipLastConsumpMap.get(vipCard.getId());
				if (lastDate != null){
					row.createCell(last_consump_date).setCellValue(Common_util.dateFormat.format(lastDate));
				}
				
				Double prepaidAccumulated = vipPrepaidAccumulated.get(vipCard.getId());
				if (prepaidAccumulated != null)
					row.createCell(prepaid_accumulated).setCellValue(prepaidAccumulated);
				else 
					row.createCell(prepaid_accumulated).setCellValue(0);
			}
		}
		return templateWorkbook;
	}

}
