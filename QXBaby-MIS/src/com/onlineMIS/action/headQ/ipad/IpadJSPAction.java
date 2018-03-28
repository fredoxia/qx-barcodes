package com.onlineMIS.action.headQ.ipad;



import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Controller;

import com.onlineMIS.ORM.DAO.Response;
import com.onlineMIS.ORM.entity.headQ.inventory.InventoryOrder;
import com.onlineMIS.ORM.entity.headQ.inventory.InventoryOrderProduct;
import com.onlineMIS.ORM.entity.headQ.user.UserInfor;
import com.onlineMIS.common.Common_util;
import com.onlineMIS.common.loggerLocal;
import com.onlineMIS.sorter.InventoryOrderProductSort;
import com.opensymphony.xwork2.ActionContext;

@Controller
public class IpadJSPAction extends IpadAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5368542996850921741L;


	@SuppressWarnings("unchecked")
	/**
	 * for the admin to edit the user's information
	 * @return
	 */
	public String goToEditCust(){

	    loggerLocal.info("IpadJSPAction - goToEditCust");
		
		return "EditCust";
	}
	
	public String logoff(){
		loggerLocal.info("IpadJSPAction - logoff");
		
		ActionContext.getContext().getSession().clear();
		
		return "ipadIndex";
	}
	
	public String viewCurrentOrder(){
		Object orderIdObj =  ActionContext.getContext().getSession().get(IpadConf.HQ_SESSION_INFO_ORDER_ID);
		
		Response response = ipadService.getOrder(orderIdObj);
		
		if (response.isSuccess()){
			InventoryOrder order = (InventoryOrder)response.getReturnValue();
			
			int orderId = order.getOrder_ID();
			int clientId = order.getClient_id();
			String clientName = order.getClient_name();
			
			ActionContext.getContext().getSession().put(IpadConf.HQ_SESSION_INFO_ORDER_ID, orderId);
			ActionContext.getContext().getSession().put(IpadConf.HQ_SESSION_INFO_CLIENT_ID, clientId);
			ActionContext.getContext().getSession().put(IpadConf.HQ_SESSION_INFO_CUSTNAME, clientName);
			
			List<InventoryOrderProduct> orderProducts = order.getProduct_List();
			
			Collections.sort(orderProducts, new InventoryOrderProductSort());
			
			String productCode = formBean.getProductCode().toUpperCase();
			if (!productCode.equalsIgnoreCase("")){
				for (int i=orderProducts.size()-1; i>=0 ; i--){
					InventoryOrderProduct orderProduct = orderProducts.get(i);
					String theProductCode = orderProduct.getProductBarcode().getProduct().getProductCode().toUpperCase();
					if (theProductCode.indexOf(productCode) == -1){
						orderProducts.remove(i);
					}
				}
			}
			
			List<InventoryOrderProductVO> orderProductVOs = InventoryOrderProductVO.parse(orderProducts);
			uiBean.setOrderProducts(orderProductVOs);
			uiBean.setTotalQ(order.getTotalQuantity());
			uiBean.setTotalW((int)order.getTotalWholePrice());
		}
		
		return "viewCurrentOrder";
	}
	
	public String listDraftOrder(){
		UserInfor loginUser = (UserInfor)ActionContext.getContext().getSession().get(Common_util.LOGIN_USER);
		
		Response response = ipadService.getDraftOrders(loginUser);
		
		if (response.isSuccess()){
			List<InventoryOrder> orders = (List<InventoryOrder>)response.getReturnValue();
			
			List<InventoryOrderVO> orderVOs = InventoryOrderVO.parse(orders);
			uiBean.setOrders(orderVOs);

		}
		
		return "listDraftOrder";
	}

}
