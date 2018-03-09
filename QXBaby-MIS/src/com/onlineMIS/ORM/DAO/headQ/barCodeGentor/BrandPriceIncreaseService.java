package com.onlineMIS.ORM.DAO.headQ.barCodeGentor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.onlineMIS.ORM.DAO.Response;
import com.onlineMIS.ORM.entity.chainS.report.ChainSalesReport;
import com.onlineMIS.ORM.entity.headQ.barcodeGentor.Brand;
import com.onlineMIS.ORM.entity.headQ.barcodeGentor.BrandPriceIncrease;
import com.onlineMIS.ORM.entity.headQ.barcodeGentor.BrandPriceIncreaseVO;
import com.onlineMIS.ORM.entity.headQ.barcodeGentor.Quarter;
import com.onlineMIS.ORM.entity.headQ.barcodeGentor.Year;
import com.onlineMIS.action.headQ.barCodeGentor.ProductActionFormBean;
import com.onlineMIS.action.headQ.barCodeGentor.ProductActionUIBean;

@Service
public class BrandPriceIncreaseService {
	@Autowired
	private BrandDaoImpl brandDaoImpl;
	@Autowired
	private YearDaoImpl yearDaoImpl;
	@Autowired
	private QuarterDaoImpl quarterDaoImpl;
	@Autowired
	private BrandPriceIncreaseDaoImpl brandPriceIncreaseDaoImpl;
	
	/**
	 * 获取所有的brand price increase在列表里面
	 * @return
	 */
	public Response getAllBrandPriceIncrease(){
		Response response = new Response();
		Map data = new HashMap<String, Object>();
		
		try {
			List<BrandPriceIncrease> allRecords = brandPriceIncreaseDaoImpl.getAll(true);
			
			List<BrandPriceIncreaseVO> allRecordVos = new ArrayList<BrandPriceIncreaseVO>();
			for (BrandPriceIncrease bpi : allRecords){
				BrandPriceIncreaseVO bpiVO = new BrandPriceIncreaseVO(bpi);
				
				allRecordVos.add(bpiVO);
			}
			
			List<BrandPriceIncreaseVO> footer = new ArrayList<BrandPriceIncreaseVO>();
	
			data.put("footer", footer);
			data.put("rows", allRecordVos);
			data.put("total", allRecordVos.size());
			
			response.setReturnValue(data);
		} catch (Exception e){
			e.printStackTrace();
			response.setFail(e.getMessage());
		}
		
		return response;
		
	}

	/**
	 * 通过 pk获取brandPriceIncrease
	 * @param yearId
	 * @param quarterId
	 * @param brandId
	 * @return
	 */
	public Response getBPI(int yearId, int quarterId, int brandId) {
		Response response = new Response();
		
		BrandPriceIncrease bpi = brandPriceIncreaseDaoImpl.getByPK(yearId, quarterId, brandId);
		
		if (bpi == null){
			response.setFail("错误 : 无法找到记录");
		} else 
			response.setReturnValue(bpi);
		
		return response;
	}

	/**
	 * 准备 修改 brand price increase ui的界面
	 * @param formBean
	 * @param uiBean
	 */
	public void prepareEditBrandPriceIncreaseUI(ProductActionFormBean formBean,
			ProductActionUIBean uiBean) {
		
		List<Year> years = yearDaoImpl.getLatestYears();
		List<Quarter> quarters = quarterDaoImpl.getAll(true);
		
		uiBean.getBasicData().setQuarterList(quarters);
		uiBean.getBasicData().setYearList(years);
		
	}

	/**
	 * 保存 brand price increase 对象
	 * @param bpi
	 * @param brand_ID
	 * @return
	 */
	public Response saveBrandPriceIncrease(BrandPriceIncrease bpi, Brand brand) {
		Response response = new Response();
		
        //1. 验证不是客户的品牌
		brand = brandDaoImpl.get(brand.getBrand_ID(), true);
		if (brand == null){
			response.setFail("无法找到该品牌");
		} else if (brand.getChainStore() != null) {
			response.setFail("不能为客户品牌调整价格");
		}
		
		if (!response.isSuccess())
			return response;
		
		bpi.setBrand(brand);
		BrandPriceIncrease bpiOriginal = brandPriceIncreaseDaoImpl.getByPK(bpi.getYear(), bpi.getQuarter(), bpi.getBrand());
		
		if (bpiOriginal != null){
			response.setFail("当年年份和季度下已经存在相同品牌的调价,请删除以后再操作");
		} else {
			try {
				brandPriceIncreaseDaoImpl.save(bpi, true);
				response.setSuccess("添加记录成功");
			} catch (Exception e){
				response.setFail("添加记录失败 : " + e.getMessage());
				e.printStackTrace();
			}
		}
		
		return response;
	}
	
	/**
	 * 保存 brand price increase 对象
	 * @param bpi
	 * @param brand_ID
	 * @return
	 */
	public Response deleteBrandPriceIncrease(BrandPriceIncrease bpi) {
		Response response = new Response();
		

		BrandPriceIncrease bpiOriginal = brandPriceIncreaseDaoImpl.getByPK(bpi.getYear(), bpi.getQuarter(), bpi.getBrand());
		
		if (bpiOriginal == null){
			response.setFail("无法找到当前记录");
		} else {
			try {
				brandPriceIncreaseDaoImpl.delete(bpiOriginal, true);
				response.setSuccess("删除记录成功");
			} catch (Exception e){
				response.setFail("删除记录失败 : " + e.getMessage());
				e.printStackTrace();
			}
		}
		
		return response;
	}
}
