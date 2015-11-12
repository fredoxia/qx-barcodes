package com.onlineMIS.ORM.DAO.chainS.sales;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.onlineMIS.ORM.DAO.Response;
import com.onlineMIS.ORM.DAO.chainS.user.ChainStoreDaoImpl;
import com.onlineMIS.ORM.DAO.chainS.user.ChainUserInforService;
import com.onlineMIS.ORM.DAO.headQ.inventory.InventoryOrderDAOImpl;
import com.onlineMIS.ORM.DAO.headQ.inventory.InventoryService;
import com.onlineMIS.ORM.entity.base.Pager;
import com.onlineMIS.ORM.entity.chainS.sales.ChainStoreSalesOrder;
import com.onlineMIS.ORM.entity.chainS.sales.PurchaseOrderTemplate;
import com.onlineMIS.ORM.entity.chainS.user.ChainRoleType;
import com.onlineMIS.ORM.entity.chainS.user.ChainStore;
import com.onlineMIS.ORM.entity.chainS.user.ChainUserInfor;
import com.onlineMIS.ORM.entity.headQ.inventory.InventoryOrder;
import com.onlineMIS.ORM.entity.headQ.inventory.InventoryOrderTemplate;
import com.onlineMIS.ORM.entity.headQ.inventory.InventoryOrderVO;
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
	public Response searchPurchaseOrders(PurchaseActionFormBean searchBean){
		boolean cached = false;
		Response response = new Response();
		Pager pager = searchBean.getPager();

		try {
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
			criteria2.addOrder(Order.asc("order_ID"));
	
			List<InventoryOrder> InventoryOrders = inventoryOrderDAOImpl.getByCritera(criteria2, pager.getFirstResult(), pager.getRecordPerPage(), cached);
			List<InventoryOrderVO> inventoryOrderVOs = constructInventoryVOs(InventoryOrders);
			
			response.setReturnValue(inventoryOrderVOs);
		} catch (Exception e){
			response.setFail(e.getMessage());
		}
		
		return response;
	}
	
	private List<InventoryOrderVO> constructInventoryVOs(
			List<InventoryOrder> orderList) {
		List<InventoryOrderVO> inventoryOrderVOs = new ArrayList<InventoryOrderVO>();
		for (InventoryOrder order : orderList){
			InventoryOrderVO vo = new InventoryOrderVO(order);
			inventoryOrderVOs.add(vo);
		}
		return inventoryOrderVOs;
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
	
	public Response getChainConfirmInfor(int orderId){
		Response response = new Response();
		if (orderId <= 0){
			response.setQuickValue(Response.FAIL, "无效的单据号");
			return response;
		}
			
		
		String hql = "SELECT i.chainConfirmStatus,i.chainConfirmComment FROM InventoryOrder i WHERE i.order_ID = ?";
		Object[] values = new Object[]{orderId};
		List<Object> objects = inventoryOrderDAOImpl.executeHQLSelect(hql, values, null, true);
		Object[] objects2 = (Object[])objects.get(0);
		if (objects == null || objects.size() == 0){
			response.setQuickValue(Response.FAIL, "无法找到对应单据号，请联系系统管理员，夏林。");
			return response;
		}else {
			int status = Common_util.getInt(objects2[0]);
			String comment = Common_util.getString(objects2[1]);
 
			InventoryOrderVO vo = new InventoryOrderVO();
			vo.setStatus(status);
			vo.setComment(comment);
			
			response.setReturnValue(vo);
			return response;
		}
	}

	public void prepareFormUIBean(PurchaseActionFormBean formBean,
			PurchaseActionUIBean uiBean, InventoryOrder order)  {
		//1. 确认收货状态下拉清单
		//   - 如果是收货状态，清楚其他下拉清单
		//   - 如果有值状态，清除未确认收货值
		Map<Integer, String> chainConfirmMap = new HashMap<Integer, String>();
		chainConfirmMap.putAll(InventoryOrderVO.getChainConfirmMap());
		int chainConfirmStatus = order.getChainConfirmStatus();
		formBean.setCanEdit(true);
		if (chainConfirmStatus == InventoryOrder.STATUS_CHAIN_CONFIRM){
			chainConfirmMap.remove(InventoryOrder.STATUS_CHAIN_NOT_CONFIRM);
			chainConfirmMap.remove(InventoryOrder.STATUS_CHAIN_PRODUCT_INCORRECT);
			formBean.setCanEdit(false);
		} else if (chainConfirmStatus == InventoryOrder.STATUS_CHAIN_PRODUCT_INCORRECT){
			chainConfirmMap.remove(InventoryOrder.STATUS_CHAIN_NOT_CONFIRM);
		} 
			
		uiBean.setChainConfirmList(chainConfirmMap);
		
		uiBean.setOrder(order);
		formBean.setOrder(order);
		uiBean.setOrder_type(Common_util.getOrderTypeClient(order.getOrder_type()));
		
	}

	/**
	 * 更新订单的状态
	 * 1. 如果订单以前的总部状态不是会计完成就要报错， 或者以前连锁店状态是确认收货也要报错
	 * 2. 如果登录用户跟当前订单不是同一个连锁店，报错
	 * @param order
	 * @param loginUser
	 * @return
	 */
	public void updatePurchaseOrderStatus(InventoryOrder order,
			ChainUserInfor loginUser, Response response) {
		InventoryOrder oldOrder = inventoryOrderDAOImpl.get(order.getOrder_ID(), true);
		
		if (oldOrder == null){
			response.setFail("无法找到单据");
		} else if (oldOrder.getOrder_Status() != InventoryOrder.STATUS_ACCOUNT_COMPLETE || oldOrder.getChainConfirmStatus() == InventoryOrder.STATUS_CHAIN_CONFIRM){
			response.setFail("单据更新状态出现错误，请联系管理员");
		} else if (oldOrder.getClient_id() != loginUser.getMyChainStore().getClient_id()){
			response.setFail("没有权限更新其他连锁店单据状态");
		} else {
			//1. 修改连锁店确认信息
			oldOrder.setChainConfirmStatus(order.getChainConfirmStatus());
			oldOrder.setChainConfirmComment(order.getChainConfirmComment());
			oldOrder.setChainConfirmDate(new Date());
			
			//2. 确认单据的库存
			
			inventoryOrderDAOImpl.update(oldOrder, true);
		}
		    
	}
	
}
