package com.onlineMIS.ORM.entity.chainS.vip;


import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.web.bind.annotation.InitBinder;

import com.onlineMIS.ORM.entity.chainS.user.ChainStore;
import com.onlineMIS.ORM.entity.headQ.barcodeGentor.Color;
import com.onlineMIS.ORM.entity.headQ.barcodeGentor.Product;
import com.onlineMIS.common.Common_util;
import com.onlineMIS.common.ExcelTemplate;
import com.onlineMIS.common.ExcelUtil;
import com.onlineMIS.common.loggerLocal;

/**
 * 批量导入的数据
 * @author fredo
 *
 */
public class ChainVIPCardInforTemplate extends ExcelTemplate{
	private final int data_row = 13;
	private final int vipCardNo_column= 1;
	private final int vipType_column= 2;
	private final int status_column = 3;
	private final int issueDate_column = 4;
	private final int expireDate_column= 5;
	private final int holderName_column= 6;
	private final int accumulateScore_column= 7;
	private final int usedScore_column= 9;
	private final int holderBirthday_column = 10;
	private final int gender_column= 11;
	private final int consumptionValue_column= 13;
	private final int idNumber_column = 14;
	private final int phoneNum_column = 15;
	private final int address_column = 16;
	private final int comment_column = 17;
	private ChainStore chainStore;
	
	public ChainVIPCardInforTemplate(ChainStore chainStore, File templateWorkbook) throws IOException{
		super(templateWorkbook);		
		this.chainStore = chainStore;
	}
	
	public List<ChainVIPCard> process() throws Exception{
		List<ChainVIPCard> vipCards = new ArrayList<ChainVIPCard>();
		Map<String, Integer> genMap = Common_util.getGenderS();
		HSSFSheet sheet = templateWorkbook.getSheetAt(0);
		int row_start = data_row;

		while (true){
			HSSFRow row  = sheet.getRow(row_start);
			if (row == null)
				break;
			else {
				HSSFCell cell = row.getCell(vipCardNo_column);
				if (cell == null)
					break;
				else {
					System.out.println("row " + row_start);
					String vipCardNo = ExcelUtil.getPuzzleString(cell);
					if (vipCardNo == null){
						loggerLocal.error("Error to load the VIP Card Number.");
						throw new Exception("VIP Card number format error");
					}

					int vipCardType = (int)row.getCell(vipType_column).getNumericCellValue();
					int vipCardStatus = (int)row.getCell(status_column).getNumericCellValue();
					HSSFCell issueDateCell = row.getCell(issueDate_column);
					Date issueDate = getDateCellValue(issueDateCell);
					
					Date expireDate = getDateCellValue(row.getCell(expireDate_column));
					String holderName = row.getCell(holderName_column).getStringCellValue();
					double accumulateScore = row.getCell(accumulateScore_column).getNumericCellValue();
					Date holderBirthday = getDateCellValue(row.getCell(holderBirthday_column));
					String gender = row.getCell(gender_column).getStringCellValue();
					String idNumber = "";
					HSSFCell idCell = row.getCell(idNumber_column);
					if (idCell != null)
						idNumber = idCell.getStringCellValue();
		
					String phoneNumber = ExcelUtil.getPuzzleString(row.getCell(phoneNum_column));
					if (phoneNumber == null){
						phoneNumber = "";
					}
	
					String address = "";
					HSSFCell addressCell = row.getCell(address_column);
					if (addressCell != null)
						address = addressCell.getStringCellValue();
					String comment = "";
					HSSFCell commentCell = row.getCell(comment_column);
					if (commentCell != null)
						comment = commentCell.getStringCellValue();
					
					ChainVIPCard vipCard = new ChainVIPCard();
					vipCard.setCardExpireDate(expireDate);
					vipCard.setCardIssueDate(issueDate);
					vipCard.setCity("");
					vipCard.setComment(comment);
					
					vipCard.setCustomerBirthday(holderBirthday);
					vipCard.setCustomerName(holderName);
					vipCard.setGender(genMap.get(gender));
					vipCard.setIdNum(idNumber);
					vipCard.setInitialScore(accumulateScore);
					vipCard.setInitialValue(accumulateScore);
					vipCard.setIssueChainStore(chainStore);
					vipCard.setProvince("");
					vipCard.setStatus(vipCardStatus);
					vipCard.setStreet(address);
					vipCard.setTelephone(phoneNumber);
					vipCard.setVipCardNo(vipCardNo);
					ChainVIPType type = new ChainVIPType();
					type.setId(vipCardType);
					vipCard.setVipType(type);
					vipCard.setZone("");
					vipCard.setPinyin(Common_util.getPinyinCode(holderName, false));

					vipCards.add(vipCard);
					
					row_start++;
				}

			}
		}
		
		return vipCards;
	}

    private Date getDateCellValue(HSSFCell dateCell){
    	Date issueDate = null;
		if (HSSFCell.CELL_TYPE_STRING == dateCell.getCellType())
			try {
				issueDate = Common_util.dateFormat.parse(dateCell.getStringCellValue());
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		else 
		    issueDate = dateCell.getDateCellValue();
		
		return issueDate;
    }
}
