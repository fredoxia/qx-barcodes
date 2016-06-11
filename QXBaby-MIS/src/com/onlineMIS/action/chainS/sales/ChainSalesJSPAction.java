package com.onlineMIS.action.chainS.sales;

import java.util.ArrayList;
import java.util.List;
import com.onlineMIS.ORM.DAO.Response;
import com.onlineMIS.ORM.DAO.chainS.ChainUtility;
import com.onlineMIS.ORM.entity.chainS.chainMgmt.ChainStoreConf;
import com.onlineMIS.ORM.entity.chainS.sales.ChainStoreSalesOrder;
import com.onlineMIS.ORM.entity.chainS.user.ChainStore;
import com.onlineMIS.ORM.entity.chainS.user.ChainUserInfor;
import com.onlineMIS.ORM.entity.chainS.vip.ChainVIPCard;
import com.onlineMIS.action.chainS.vo.ChainProductBarcodeVO;
import com.onlineMIS.common.Common_util;
import com.onlineMIS.common.loggerLocal;
import com.opensymphony.xwork2.ActionContext;

public class ChainSalesJSPAction extends ChainSalesAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5320369291528971495L;

	
	/**
	 * the action to prepare the data for the new chain sales exchange order
	 * @return
	 */
	public String preNewSalesOrder(){
		ChainUserInfor userInfor = (ChainUserInfor)ActionContext.getContext().getSession().get(Common_util.LOGIN_CHAIN_USER);
    	loggerLocal.chainActionInfo(userInfor,this.getClass().getName()+ "."+"preNewSalesOrder");
    	
		chainStoreSalesService.prepareNewSalesOrderUI(formBean, uiBean,userInfor, ChainStoreSalesOrder.SALES);
		
		//2. parepare the parameters in the ui
        chainStoreSalesService.calculateSalesParm(formBean, userInfor, new ChainStoreSalesOrder());
		
        //3. 当前功能需要防止多次提交
        formBean.setToken(createToken(null));
        
		return "EditChainSalesOrder";	
	}
	

	
	/**
	 * this action is triggered by sacanning the product by product code
	 * @return
	 */
	public String scanByProductCode(){
		ChainUserInfor userInfor = (ChainUserInfor)ActionContext.getContext().getSession().get(Common_util.LOGIN_CHAIN_USER);

    	String productCode = "";
    	try{
    		productCode = Common_util.decode(formBean.getProductCode());
    	} catch (Exception e) {
			e.printStackTrace();
		}
    	
    	loggerLocal.chainActionInfo(userInfor,this.getClass().getName()+ "."+"scanByProductCode : " + productCode);
    	
		List<ChainProductBarcodeVO> products = chainStoreSalesService.getProductsForSimiliarProductCode(productCode, formBean.getPager(), formBean.getChainId(),userInfor);
		uiBean.setCpbVOs(products);

		ChainStore chainStore = chainStoreService.getChainStoreByID(formBean.getChainId());
		
		Response response = chainStoreSalesService.getReturnPage(chainStore, userInfor);
		int returnCode = response.getReturnCode();
		switch (returnCode) {
		case 1:
			return "SalesSearchProducts";
		case 2:
			return "SalesSearchProducts2";
		case 3:
			return "SalesSearchProducts3";
		default:
			return "SalesSearchProducts";
		}
	}
	
	
	
		
	/**
	 * triggered by click the button to delete the order "删除单据"
	 * @return
	 */
	public String deleteOrder(){
		ChainUserInfor userInfor = (ChainUserInfor)ActionContext.getContext().getSession().get(Common_util.LOGIN_CHAIN_USER);

		int orderId = formBean.getChainSalesOrder().getId();
		
		loggerLocal.chainActionInfo(userInfor,this.getClass().getName()+ "."+"deleteOrder : " + orderId);

		Response response = chainStoreSalesService.deleteOrder(userInfor, orderId);
		
		String msg = response.getMessage();
		int returnCode = response.getReturnCode();

		if (returnCode == Response.SUCCESS){
			addActionMessage(msg);
		    return preSearchSalesOrder();
	    } else if (returnCode ==  Response.FAIL){
			addActionError(msg);
			return getSalesOrderById();
		} else {
			addActionError("删除单据发生错误 ");
			return "operationError";
		}
	}
	
	/**
	 * triggered by click the button of cancel 红冲
	 * @return
	 */
	public String cancelOrder(){
		ChainUserInfor userInfor = (ChainUserInfor)ActionContext.getContext().getSession().get(Common_util.LOGIN_CHAIN_USER);
		int orderId = formBean.getChainSalesOrder().getId();
		
		loggerLocal.chainActionInfo(userInfor,this.getClass().getName()+ "."+"cancelOrder : " + orderId);		
		
		Response response = chainStoreSalesService.cancelOrder(userInfor, orderId);
		
		String msg = response.getMessage();
		int returnCode = response.getReturnCode();

		if (returnCode == Response.SUCCESS){
			addActionMessage(msg);
			return getSalesOrderById();
	    } else if (returnCode ==  Response.FAIL || returnCode ==  Response.ERROR){
			addActionError(msg);
			return getSalesOrderById();
		} else {
			addActionError("红冲单据发生错误 ");
			return "operationError";
		}
	}
	
	/**
	 * function to copy order
	 * @return
	 */
	public String copyOrder(){
		ChainUserInfor userInfor = (ChainUserInfor)ActionContext.getContext().getSession().get(Common_util.LOGIN_CHAIN_USER);
    	
		int orderId  = formBean.getChainSalesOrder().getId();
		
		loggerLocal.chainActionInfo(userInfor,this.getClass().getName()+ "."+"copyOrder : " + orderId);
    	
		Response response = chainStoreSalesService.copyOrder(orderId,userInfor);
		
		if (response.getReturnCode() != Response.SUCCESS){
			addActionError(response.getMessage());
			return "operationError";
		} else {

			ChainStoreSalesOrder salesOrder = (ChainStoreSalesOrder)response.getReturnValue();
			
			int order_type = salesOrder.getType();
	
			//1. prepare the ui bean
			chainStoreSalesService.prepareNewSalesOrderUI(formBean, uiBean,userInfor, order_type);
			
			//2. set the sales order
			formBean.setChainSalesOrder(salesOrder);
			
			//3.1set the vip information
			ChainVIPCard vipCard = salesOrder.getVipCard();
			if (vipCard != null && vipCard.getId() != 0){
				double discount = vipCard.getVipType().getDiscountRate();
				
				formBean.setDiscount(discount);
//				formBean.setVipCardNo(cardNo);
			}
			
			//4. set the parameter
	        chainStoreSalesService.calculateSalesParm(formBean, userInfor, new ChainStoreSalesOrder());
			
			addActionMessage(response.getMessage());
			
			if (order_type == ChainStoreSalesOrder.SALES){
		        //3. 当前功能需要防止多次提交
		        formBean.setToken(createToken(null));
		        return "EditChainSalesOrder";
			} else
				return ERROR;
		}
	}
	
	/**
	 * triggered by click the "Search Draft Sales Order"
	 * @return
	 */
	public String preSearchDraftSalesOrder(){
		ChainUserInfor userInfor = (ChainUserInfor)ActionContext.getContext().getSession().get(Common_util.LOGIN_CHAIN_USER);
    	loggerLocal.chainActionInfo(userInfor,this.getClass().getName()+ "."+"preSearchDraftSalesOrder");
    	
		chainStoreSalesService.prepareSearchSalesOrderUI(userInfor, uiBean, formBean);	 
		
		formBean.getChainSalesOrder().setStatus(ChainStoreSalesOrder.STATUS_DRAFT);
		formBean.setInitialSearch(true);
		
		return "SalesOrderSearch";
	}
	
	/**
	 * triggered by click the "search Sales Order"
	 * @return
	 */
	public String preSearchSalesOrder(){
		ChainUserInfor userInfor = (ChainUserInfor)ActionContext.getContext().getSession().get(Common_util.LOGIN_CHAIN_USER);
    	loggerLocal.chainActionInfo(userInfor,this.getClass().getName()+ "."+"");
    	
		chainStoreSalesService.prepareSearchSalesOrderUI(userInfor, uiBean, formBean);	 

		return "SalesOrderSearch";
	}
	
	/**
	 * triggered by click the retrieve button
	 * @return
	 */
	public String getSalesOrderById(){
		ChainUserInfor userInfor = (ChainUserInfor)ActionContext.getContext().getSession().get(Common_util.LOGIN_CHAIN_USER);
    	
		int orderId = formBean.getChainSalesOrder().getId();
		
		loggerLocal.chainActionInfo(userInfor,this.getClass().getName()+ "."+"getSalesOrderById : " + orderId);
		
		
		ChainStoreSalesOrder salesOrder = chainStoreSalesService.getSalesOrderById(orderId, userInfor);
		chainStoreSalesService.prepareEditSalesOrderUI(formBean, uiBean,userInfor, salesOrder);
		
		//1. check the order type, it is sales-out, exchange or return order
		if (salesOrder != null && salesOrder.getId() != 0 ){
			
			//to calculate the parameters on the UI
			chainStoreSalesService.calculateSalesParm(formBean, userInfor,salesOrder);

			int order_type = salesOrder.getType();
			int order_status = salesOrder.getStatus();

				//2.1 check the sales order status
			    //2.1.1 draft order
				if (order_status == ChainStoreSalesOrder.STATUS_DRAFT){
					salesOrder.setOrderDate(Common_util.getToday());
					formBean.setChainSalesOrder(salesOrder);
		
					//2.1.1 set the vip information
//					ChainVIPCard vipCard = salesOrder.getVipCard();
//					if (vipCard != null && vipCard.getId() != 0){
//						double discount = vipCard.getVipType().getDiscountRate();
//						
//						formBean.setDiscount(discount);
//						formBean.setVipCardNo(vipCard.getVipCardNo());
//					}

			        
					if (order_type == ChainStoreSalesOrder.SALES){
						
				        //3. 当前功能需要防止多次提交
				        formBean.setToken(createToken(null));
				        
				       return "EditChainSalesOrder";
					} else
						return ERROR;
				//2.1.2 complete 或者 cancel的order
				}else if (order_status == ChainStoreSalesOrder.STATUS_COMPLETE || order_status == ChainStoreSalesOrder.STATUS_CANCEL){
					uiBean.setChainSalesOrder(salesOrder);
					if (order_type == ChainStoreSalesOrder.SALES){
						ChainStoreConf conf = chainStoreSalesService.getChainStoreConf(salesOrder.getChainStore().getChain_id());
						uiBean.setChainStoreConf(conf);
				        return "ViewSales";
					} else
						return ERROR;
				}else 
					return ERROR;
		    
		} else {
			addActionError("无法获取相对应的订单");
			return ERROR;
		}
	}
	
	/**
	 * view the product infromation
	 * @return
	 */
	public String viewProduct(){
		ChainProductBarcodeVO product = chainStoreSalesService.getChainProductInfo(formBean.getBarcode(), formBean.getChainId());

		if (product == null){
			addActionError("无法找到相应信息");
		} else {
		   uiBean.setCpbVO(product);
		}
		
		return "ViewProduct";		
	}
	
	/**
	 * pre get the product information
	 * @return
	 */
	public String preGetProduct(){
		ChainUserInfor userInfor = (ChainUserInfor)ActionContext.getContext().getSession().get(Common_util.LOGIN_CHAIN_USER);
    	
		List<ChainStore> stores =  new ArrayList<ChainStore>();
		
		//1. set the store list
		stores = chainStoreService.getChainStoreList(userInfor);
		uiBean.setChainStores(stores);
		
		//2. set the boss id
		List<ChainUserInfor> boss = chainUserInforService.getBossInChain(stores.get(0).getChain_id());
		if (boss != null && boss.size()>0)
			uiBean.setOrderCreator(boss.get(0));
		
		return "checkProductInfor";
	}

}
