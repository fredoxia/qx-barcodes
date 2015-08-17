package com.onlineMIS.ORM.DAO.chainS.sales;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.onlineMIS.ORM.DAO.chainS.user.ChainStoreDaoImpl;
import com.onlineMIS.ORM.DAO.chainS.user.ChainUserInforService;
import com.onlineMIS.ORM.DAO.headQ.inventory.InventoryOrderDAOImpl;
import com.onlineMIS.ORM.entity.base.Pager;
import com.onlineMIS.ORM.entity.chainS.sales.ChainStoreSalesOrder;
import com.onlineMIS.ORM.entity.chainS.sales.PurchaseOrderTemplate;
import com.onlineMIS.ORM.entity.chainS.user.ChainRoleType;
import com.onlineMIS.ORM.entity.chainS.user.ChainStore;
import com.onlineMIS.ORM.entity.chainS.user.ChainUserInfor;
import com.onlineMIS.ORM.entity.headQ.inventory.InventoryOrder;
import com.onlineMIS.ORM.entity.headQ.inventory.InventoryOrderTemplate;
import com.onlineMIS.action.chainS.sales.PurchaseActionFormBean;
import com.onlineMIS.action.chainS.sales.PurchaseActionUIBean;
import com.onlineMIS.common.Common_util;
import com.onlineMIS.common.loggerLocal;

/**
 * the purchase service to support the clients operations on the purchase order
 * 
 * @author fredo
 *
 */

@Service
public class PurchaseService {
	
	@Autowired
	private InventoryOrderDAOImpl inventoryOrderDAOImpl;
	
	@Autowired
	private ChainStoreDaoImpl chainStoreDaoImpl;
	
	/**
	 * when user search through the search page。
	 * 1. Only those complete order can be searched
	 * 2. Only search the order belongs to the login user
	 * @param searchBean
	 * @return
	 */
	public List<InventoryOrder> searchPurchaseOrders(PurchaseActionFormBean searchBean){
		boolean cached = false;
		Pager pager = searchBean.getPager();

		if (pager.getTotalPage() == 0){
		    DetachedCriteria criteria = buildSearchPurchaseOrderCriteria(searchBean);
			criteria.setProjection(Projections.rowCount());
			int totalRecord = Common_util.getProjectionSingleValue(inventoryOrderDAOImpl.getByCriteriaProjection(criteria, false));
			pager.initialize(totalRecord);
		} else {
			pager.calFirstResult();
			cached = true;
		}
		
		DetachedCriteria criteria2 = buildSearchPurchaseOrderCriteria(searchBean);
		criteria2.addOrder(Order.asc("client_id"));

		return inventoryOrderDAOImpl.getByCritera(criteria2, pager.getFirstResult(), pager.getRecordPerPage(), cached);
	}
	
	private DetachedCriteria buildSearchPurchaseOrderCriteria(PurchaseActionFormBean searchBean){
		DetachedCriteria criteria = DetachedCriteria.forClass(InventoryOrder.class,"order");
		
		if (searchBean.getOrder().getOrder_type() != Common_util.ALL_RECORD)
		    criteria.add(Restrictions.eq("order_type", searchBean.getOrder().getOrder_type()));
		
		criteria.add(Restrictions.eq("client_id", searchBean.getOrder().getClient_id()));
		
		criteria.add(Restrictions.eq("order_Status", InventoryOrder.STATUS_ACCOUNT_COMPLETE));
		
		if (searchBean.getSearch_Start_Time() != null && searchBean.getSearch_End_Time() != null){
			Date end_date = Common_util.formEndDate(searchBean.getSearch_End_Time());
			criteria.add(Restrictions.between("order_EndTime",searchBean.getSearch_Start_Time(),end_date));
		}
		
		int productId = searchBean.getProductId();
		if (productId > 0){
			DetachedCriteria orderProductCriteria = criteria.createCriteria("product_Set");
			orderProductCriteria.add(Restrictions.eq("productBarcode.id", productId));
		}
		
		return criteria;
	}

	/**
	 * when user retrieve the order by order id
	 * 1. validate the access rights of the order
	 *    - the order belongs to the user
	 *    - the order is completed
	 * 2. if it is not passed, return null
	 * @param order_id
	 * @param userInfor
	 * @return
	 */
	@Transactional
	public InventoryOrder getPurchaseById(int order_id, ChainUserInfor userInfor) {
		
		InventoryOrder order = inventoryOrderDAOImpl.retrieveOrder(order_id);
		
		if (validatePurchase(order, userInfor)){
			order.putSetToList();
		   return order;
		}else 
		   return null;
	}
	
	/**
	 * to validate the access right of the inventory order and the chain user
	 * @param order
	 * @param userInfor
	 * @return
	 */
	private boolean validatePurchase(InventoryOrder order, ChainUserInfor userInfor){
		if (order.getOrder_Status() != InventoryOrder.STATUS_ACCOUNT_COMPLETE){
			return false;
		} else {
			if (ChainUserInforService.isMgmtFromHQ(userInfor))
				return true;
			else if (order.getClient_id() != userInfor.getMyChainStore().getClient_id())
				return false;
			else 
				return true;
		}
	}

	/**
	 * to prepare the search page UI bean
	 * @param uiBean
	 * @param userInfor
	 */
	public void prepareSearchUI(PurchaseActionUIBean uiBean,
			ChainUserInfor userInfor) {
		uiBean.setTypesMap(InventoryOrder.getTypesMap_retailer());
		List<ChainStore> chainStores = new ArrayList<ChainStore>();
		
		if (ChainUserInforService.isMgmtFromHQ(userInfor))
			chainStores =  chainStoreDaoImpl.getAll(ChainStoreDaoImpl.cached);
		else {
			chainStores.add(userInfor.getMyChainStore());
		}
		
		uiBean.setChainStores(chainStores);
	}

	@Transactional
	public Map<String, Object> generateExcelReport(InventoryOrder order,
			String templatePosition, boolean displayCost) {
		Map<String,Object> returnMap=new HashMap<String, Object>();   

        
		ByteArrayInputStream byteArrayInputStream;   
		try {     
			HSSFWorkbook wb = null;   
			
			//to get the order information from database
			order = inventoryOrderDAOImpl.retrieveOrder(order.getOrder_ID());
			PurchaseOrderTemplate orderTemplate = new PurchaseOrderTemplate(order, templatePosition);
			

			wb = orderTemplate.process(displayCost);

			ByteArrayOutputStream os = new ByteArrayOutputStream();   
			try {   
			    wb.write(os);   
			} catch (IOException e) {   
				loggerLocal.error(e);
		    }   
		    byte[] content = os.toByteArray();   
		    byteArrayInputStream = new ByteArrayInputStream(content);   
		    returnMap.put("stream", byteArrayInputStream);   
         
		    return returnMap;   
		 } catch (Exception ex) {   
			 loggerLocal.error(ex);
		 }   
		return null;   
	}
	
}
