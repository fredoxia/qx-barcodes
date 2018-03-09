package com.onlineMIS.action.chainS.chainMgmt;

import java.util.List;

import net.sf.json.JSONObject;

import com.onlineMIS.ORM.DAO.Response;
import com.onlineMIS.ORM.entity.chainS.chainMgmt.ChainInitialStock;
import com.onlineMIS.ORM.entity.chainS.chainMgmt.ChainPriceIncrement;
import com.onlineMIS.ORM.entity.chainS.user.ChainRoleType;
import com.onlineMIS.ORM.entity.chainS.user.ChainStore;
import com.onlineMIS.ORM.entity.chainS.user.ChainStore;
import com.onlineMIS.ORM.entity.chainS.user.ChainUserInfor;
import com.onlineMIS.ORM.entity.headQ.barcodeGentor.Brand;
import com.onlineMIS.ORM.entity.headQ.user.UserInfor;
import com.onlineMIS.action.chainS.vo.ChainProductBarcodeVO;
import com.onlineMIS.common.Common_util;
import com.onlineMIS.common.loggerLocal;
import com.opensymphony.xwork2.ActionContext;


public class ChainMgmtJSPAction extends ChainMgmtAction {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1796697393416073505L;

	/**
	 * function to pre-edit the chain store information from the headq
	 * @return
	 */
	public String preEditChainInfor(){
    	ChainUserInfor userInfor = (ChainUserInfor)ActionContext.getContext().getSession().get(Common_util.LOGIN_CHAIN_USER);
//    	loggerLocal.chainActionInfo(userInfor,this.getClass().getName()+ "."+"");
    		
		chainMgmtService.prepareEditChainInforUI(formBean,uiBean,userInfor);
		
		formBean.getChainUserInfor().getRoleType().setChainRoleTypeId(ChainRoleType.CHAIN_STAFF);

		return "editChainStoreInfor";
	}
	
	/**
	 * function to pre-edit chain store user information and configuration information
	 * @return
	 */
	public String preEditChainConf(){
    	ChainUserInfor userInfor = (ChainUserInfor)ActionContext.getContext().getSession().get(Common_util.LOGIN_CHAIN_USER);
    	loggerLocal.chainActionInfo(userInfor,this.getClass().getName()+ "."+"preEditChainConf");
    	
		chainMgmtService.prepareEditChainConfUI(uiBean, formBean, userInfor);

		return "editChainStoreConf";
	}
	
	/**
	 * function to prepare to create the initial stock of the chain
	 * @return
	 */
	public String preCreateInitialStock(){
//    	ChainUserInfor userInfor = (ChainUserInfor)ActionContext.getContext().getSession().get(Common_util.LOGIN_CHAIN_USER);
//    	loggerLocal.chainActionInfo(userInfor,this.getClass().getName()+ "."+"preCreateInitialStock");
//    	
		chainMgmtService.prepareEditInitialStockUI(uiBean);

		return "editChainInitialStock";
	}
	
	/**
	 * function to prepare upload the initial stock
	 * @return
	 */
	public String preUploadInitialStock(){
//    	ChainUserInfor userInfor = (ChainUserInfor)ActionContext.getContext().getSession().get(Common_util.LOGIN_CHAIN_USER);
//    	loggerLocal.chainActionInfo(userInfor,this.getClass().getName()+ "."+"preUploadInitialStock");
//    	
		return "uploadInitialStock";
	}
	
	/**
	 * 获取连锁店列表
	 * @return
	 */
	public String listChainStore(){
    	ChainUserInfor userInfor = (ChainUserInfor)ActionContext.getContext().getSession().get(Common_util.LOGIN_CHAIN_USER);
    	loggerLocal.chainActionInfo(userInfor,this.getClass().getName()+ "."+"listChainStore");
    	
		Response response = new Response();
		
		try {

		     response = chainStoreService.getChainStoreList(userInfor, formBean.getPager(), formBean.getAccessLevel(),formBean.getIndicator());
		} catch (Exception e) {
			loggerLocal.chainActionError(userInfor,this.getClass().getName()+ "."+"listChainStore");
			loggerLocal.error(e);
			response.setQuickValue(Response.ERROR, response.getMessage());
		}
		
		if (response.getReturnCode() == response.SUCCESS || response.getReturnCode() == response.WARNING){
			List<ChainStore> chainStores = (List<ChainStore>)response.getReturnValue();
			uiBean.setChainStores(chainStores);
		} else {
			addActionError("发生错误 : " + response.getMessage());
		}
		
		return "listChainStores";
	}
	
	/**
	 * 总部获取所有可能成为父亲连锁店
	 * @return
	 */
	public String listParentStore(){
    	UserInfor userInfor = (UserInfor)ActionContext.getContext().getSession().get(Common_util.LOGIN_USER);
    	loggerLocal.info(userInfor.getUser_name() + "," + this.getClass().getName()+ "."+"listChainStoreHQ");
    	
		Response response = new Response();
		
		try {
		     response = chainStoreService.getChainStoreListHQ(userInfor, formBean.getPager(), formBean.getIsAll(),formBean.getIndicator());
		} catch (Exception e) {
			loggerLocal.error(e);
			response.setQuickValue(Response.ERROR, response.getMessage());
		}
		
		if (response.getReturnCode() == response.SUCCESS || response.getReturnCode() == response.WARNING){
			List<ChainStore> chainStores = (List<ChainStore>)response.getReturnValue();
			uiBean.setChainStores(chainStores);
		} else {
			addActionError("发生错误 : " + response.getMessage());
		}
		
		return "listParentSotres";
	}
	
	/**
	 * 总部获取连锁店列表
	 * @return
	 */
	public String listChainStoreHQ(){
    	UserInfor userInfor = (UserInfor)ActionContext.getContext().getSession().get(Common_util.LOGIN_USER);
    	loggerLocal.info(userInfor.getUser_name() + "," + this.getClass().getName()+ "."+"listChainStoreHQ");
    	
		Response response = new Response();
		
		try {
		     response = chainStoreService.getChainStoreListHQ(userInfor, formBean.getPager(), formBean.getIsAll(),formBean.getIndicator());
		} catch (Exception e) {
			loggerLocal.error(e);
			response.setQuickValue(Response.ERROR, response.getMessage());
		}
		
		if (response.getReturnCode() == response.SUCCESS || response.getReturnCode() == response.WARNING){
			List<ChainStore> chainStores = (List<ChainStore>)response.getReturnValue();
			uiBean.setChainStores(chainStores);
		} else {
			addActionError("发生错误 : " + response.getMessage());
		}
		
		return "listChainStoresHQ";
	}
	
	/**
	 * 获取品牌列表
	 * @return
	 */
	public String listBrands(){
    	ChainUserInfor userInfor = (ChainUserInfor)ActionContext.getContext().getSession().get(Common_util.LOGIN_CHAIN_USER);
    	loggerLocal.chainActionInfo(userInfor,this.getClass().getName()+ "."+"listBrands");
    	
		Response response = new Response();
		
		String brandName = Common_util.decode(formBean.getBrand().getBrand_Name());
		
		try {
		     response = chainMgmtService.getBrandList(formBean.getYear().getYear_ID(), formBean.getQuarter().getQuarter_ID(),brandName, formBean.getPager());
		} catch (Exception e) {
			loggerLocal.chainActionError(userInfor,this.getClass().getName()+ "."+"listBrands");
			loggerLocal.error(e);
			response.setQuickValue(Response.ERROR, response.getMessage());
		}
		
		if (response.getReturnCode() == response.SUCCESS || response.getReturnCode() == response.WARNING){
			List<Brand> brands = (List<Brand>)response.getReturnValue();
			uiBean.setBrands(brands);
		} else {
			addActionError("发生错误 : " + response.getMessage());
		}
		
		return "listBrands";
	}	

	/**
	 * 准备修改连锁店关联信息
	 * @return
	 */
	public String preEditChainGroup(){
	
		chainMgmtService.prepareEditChainGroupUI(uiBean);
		
		formBean.getChainUserInfor().getRoleType().setChainRoleTypeId(ChainRoleType.CHAIN_STAFF);

		return "editChainStoreGroup";
	}
	
	/**
	 * 准备修改连锁店零售价格
	 * @return
	 */
	public String preEditChainProductPrice(){
    	ChainUserInfor userInfor = (ChainUserInfor)ActionContext.getContext().getSession().get(Common_util.LOGIN_CHAIN_USER);
    	loggerLocal.chainActionInfo(userInfor,this.getClass().getName()+ "."+"preEditChainProductPrice");
    	
		chainMgmtService.prepareEditChainProductPriceUI(uiBean,userInfor);
		
		return "searchChainSalesPrice";
	}
	
	/**
	 * 获取chain product barcode by id
	 * @return
	 */
	public String getProductBarcode(){
    	ChainUserInfor userInfor = (ChainUserInfor)ActionContext.getContext().getSession().get(Common_util.LOGIN_CHAIN_USER);
    	loggerLocal.chainActionInfo(userInfor,this.getClass().getName()+ "."+"getProductBarcode");
    	
    	List<ChainProductBarcodeVO> chainProductBarcodeVOs = chainMgmtService.searchProductBarcode(formBean.getNeedUpdtDate(),formBean.getProductBarcode(), formBean.getChainId(), formBean.getStartDate(), formBean.getEndDate());
    	if (chainProductBarcodeVOs != null && chainProductBarcodeVOs.size() == 1)
    		formBean.setChainProductBarcodeVO(chainProductBarcodeVOs.get(0));
    	
    	return "editChainSalesPrice";	
	}
	
	/**
	 * 保存初始库存
	 * @return
	 */
	public String saveChainInitialStocks(){

		Response response = new Response();
		try{
		     response = chainMgmtService.saveChainInitialStock(formBean.getStocks(), formBean.getChainStore().getClient_id(),formBean.getInventory());
		} catch (Exception e) {
			loggerLocal.error(e);
			response.setQuickValue(Response.ERROR, e.getMessage());
		}
		
		if (response.getReturnCode() == Response.SUCCESS){
			addActionMessage("成功保存连锁店初始库存");
			return getChainInitialStocks();
		} else {
			addActionError("保存连锁店初始库存失败");
			preCreateInitialStock();
			return  "editChainInitialStock";
		}
	}
	
	/**
	 * 获取连锁店的初始库存
	 * @return
	 */
	public String getChainInitialStocks(){
		
		Response response = new Response();

		try{
			response = chainMgmtService.getChainInitialStocks(formBean.getChainStore().getClient_id());
			} catch (Exception e){
				response.setQuickValue(Response.FAIL, "失败 : " + e.getMessage());
				loggerLocal.error(e);
			}	
		
		if (response.getReturnCode() == Response.SUCCESS){
		   formBean.setStocks((List<ChainInitialStock>)response.getReturnValue());
		} else if (response.getReturnCode() == Response.WARNING){
			addActionError(response.getMessage());
		} else {
		   addActionError("获取连锁店初始库存失败");
		}

		preCreateInitialStock();
		return "editChainInitialStock";
	}
	
	/**
	 * 准备修改chain的价格涨幅
	 * @return
	 */
	public String preEditChainPriceIncre(){
		return "listChainPriceIncre";
	}
	
	/**
	 * 准备修改chain price increment
	 * @return
	 */
	public String preAddChainPriceIncre(){
		Response response = new Response();
		response = chainMgmtService.getChainPriceIncre(formBean.getPriceIncrement());
		
		if (response.getReturnCode() == Response.SUCCESS){
			formBean.setPriceIncrement((ChainPriceIncrement)response.getReturnValue());
		}
			
		return "editChainPriceIncre";
	}
	
	/**
	 * 准备 千禧宝贝系统当前 主要服饰的年份和季度
	 * @return
	 */
	public String preCreateConf(){
		ChainUserInfor userInfor = (ChainUserInfor)ActionContext.getContext().getSession().get(Common_util.LOGIN_CHAIN_USER);
    	
		Response response = chainMgmtService.preparePreCreateConfUI(formBean, uiBean, userInfor);
		if (!response.isSuccess())
			addActionError(response.getMessage());
		
		return "preCreateConf";
	}
	
	/**
	 * 批量上传价格
	 * @return
	 */
	public String updateBatchPrice(){
		
		try {
			Response response = chainMgmtService.updateBatchPrice(formBean.getChainId(),formBean.getInventory());
			
			if (response.isSuccess())
			    addActionMessage("已经全部成功更新价格, 共 " + response.getReturnValue() + " 个条码");
			else 
				addActionError(response.getMessage());
		} catch (Exception e) {
			loggerLocal.error(e);
			addActionError("批量修改价格错误，请联系系统管理员 ： " + e.getMessage());
		}
		
		return preEditChainProductPrice();
	}
}
