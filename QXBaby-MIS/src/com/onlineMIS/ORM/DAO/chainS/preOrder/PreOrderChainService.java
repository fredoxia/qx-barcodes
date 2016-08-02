package com.onlineMIS.ORM.DAO.chainS.preOrder;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.hibernate.Criteria;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.onlineMIS.ORM.DAO.Response;
import com.onlineMIS.ORM.DAO.chainS.user.ChainStoreDaoImpl;
import com.onlineMIS.ORM.DAO.chainS.user.ChainUserInforService;
import com.onlineMIS.ORM.DAO.headQ.SQLServer.ClientDAOImpl;
import com.onlineMIS.ORM.DAO.headQ.barCodeGentor.BrandDaoImpl;
import com.onlineMIS.ORM.DAO.headQ.preOrder.PreOrderDaoImpl;
import com.onlineMIS.ORM.DAO.headQ.preOrder.PreOrderIdentityDaoImpl;
import com.onlineMIS.ORM.DAO.headQ.preOrder.PreOrderProductDaoImpl;
import com.onlineMIS.ORM.DAO.headQ.user.UserInforDaoImpl;
import com.onlineMIS.ORM.DAO.headQ.user.UserInforService;
import com.onlineMIS.ORM.entity.base.Pager;
import com.onlineMIS.ORM.entity.chainS.inventoryFlow.ChainInventoryFlowOrder;
import com.onlineMIS.ORM.entity.chainS.inventoryFlow.ChainInventoryFlowOrderTemplate;
import com.onlineMIS.ORM.entity.chainS.user.ChainUserInfor;
import com.onlineMIS.ORM.entity.headQ.SQLServer.ClientsMS;
import com.onlineMIS.ORM.entity.headQ.barcodeGentor.Brand;
import com.onlineMIS.ORM.entity.headQ.preOrder.CustPreOrder;
import com.onlineMIS.ORM.entity.headQ.preOrder.CustPreOrderProduct;
import com.onlineMIS.ORM.entity.headQ.preOrder.CustPreOrderSummaryData;
import com.onlineMIS.ORM.entity.headQ.preOrder.CustPreOrderSummaryTemplate;
import com.onlineMIS.ORM.entity.headQ.preOrder.CustPreOrderTemplate;
import com.onlineMIS.ORM.entity.headQ.preOrder.CustPreorderIdentity;
import com.onlineMIS.action.chainS.preOrder.PreOrderActionFormBean;
import com.onlineMIS.action.chainS.preOrder.PreOrderActionUIBean;
import com.onlineMIS.common.Common_util;

@Service
public class PreOrderChainService {

	@Autowired
	private PreOrderDaoImpl preOrderDaoImpl;
	
	@Autowired
	private PreOrderProductDaoImpl preOrderProductDaoImpl;
	
	@Autowired
	private PreOrderIdentityDaoImpl preOrderIdentityDaoImpl;
	
	@Autowired
	private BrandDaoImpl brandDaoImpl;
	
	@Autowired
	private ClientDAOImpl clientDAOImpl;
	
	@Autowired
	private ChainStoreDaoImpl chainStoreDaoImpl;

	
	@Transactional
	public Response searchOrders(int chainId, String preOrderIdentity, Integer page, Integer rowPerPage, String sortName, String sortOrder){
		Response response = new Response();
		Map data = new HashMap<String, Object>();
		
		int clientId = chainId;
		if (chainId !=  Common_util.ALL_RECORD)
			clientId = chainStoreDaoImpl.get(chainId, true).getClient_id();
		
		//1.计算总条数
		DetachedCriteria criteria = buildSearchOrdersCriteria(clientId, preOrderIdentity);
		criteria.setProjection(Projections.rowCount());
		int totalRecord = Common_util.getProjectionSingleValue(preOrderDaoImpl.getByCriteriaProjection(criteria, false));
		
		DetachedCriteria criteria2 = buildSearchOrdersCriteria(clientId, preOrderIdentity);
		criteria2.addOrder(Order.asc("custId"));
		
		Pager pager = new Pager();
		pager.setTotalPage(totalRecord);
		pager.setCurrentPage(page);
		pager.setRecordPerPage(rowPerPage);
		pager.calFirstResult();
		
		List<CustPreOrder> orders = preOrderDaoImpl.getByCritera(criteria2, pager.getFirstResult(), pager.getRecordPerPage(), true);

		data.put("rows", orders);
		data.put("total", totalRecord);
		
		response.setReturnValue(data);
		
		return response;
	}
	
	private DetachedCriteria buildSearchOrdersCriteria(int clientId, String preOrderIdentity){
		DetachedCriteria criteria = DetachedCriteria.forClass(CustPreOrder.class);
		if (clientId != Common_util.ALL_RECORD)
			criteria.add(Restrictions.eq("custId", clientId)); 
		else 
			criteria.add(Restrictions.isNotNull("chainStore.chain_id")); 
		
		if (preOrderIdentity != null && !preOrderIdentity.trim().equals(""))
			criteria.add(Restrictions.eq("orderIdentity", preOrderIdentity));

		return criteria;
	}

	/**
	 * 搜索单据前，准备搜索页面
	 * @param formBean
	 */
	public void prepareSearchUI(PreOrderActionFormBean formBean, PreOrderActionUIBean uiBean, ChainUserInfor loginUser) {
		List<CustPreorderIdentity> identityList = preOrderIdentityDaoImpl.getTop(20);
		uiBean.setIdentities(identityList);
		
		if (identityList.size() > 0){
			formBean.getOrder().setOrderIdentity(identityList.get(0).getOrderIdentity());
		}
		
		if (ChainUserInforService.isMgmtFromHQ(loginUser)){
			formBean.setChainStore(chainStoreDaoImpl.getAllChainStoreObject());
		} else {
			int clientId = loginUser.getMyChainStore().getClient_id();
			String clientName = loginUser.getMyChainStore().getChain_name();
			formBean.getOrder().setClient_id(clientId);
			formBean.getOrder().setClient_name(clientName);
		}
	}

	/**
	 * 获取订单详情
	 * @param id
	 * @return
	 */
	@Transactional
	public Response getOrderById(int id, ChainUserInfor userInfor) {
		Response response = new Response();
		boolean showCost = false;
		if (ChainUserInforService.isMgmtFromHQ(userInfor))
			showCost = true;
		else if (userInfor.getRoleType().isOwner())
			showCost = true;
		
		CustPreOrder order =preOrderDaoImpl.getById(id, true);
		
		order.putSetToList();
		
		if (showCost == false){
			for (CustPreOrderProduct orderProduct : order.getProductList()){
				orderProduct.setSumWholePrice(0);
				preOrderProductDaoImpl.evict(orderProduct);
			}
			order.setSumWholePrice(0);
			preOrderDaoImpl.evict(order);
		}
		order.setProductSet(null);
		response.setReturnValue(order);
		return response;
	}

	@Transactional
	public Response downloadPreOrder(int id, ChainUserInfor userInfor) throws IOException {
		Response response = new Response();
		List<Object> values = new ArrayList<Object>();
		
		String webInf = this.getClass().getClassLoader().getResource("").getPath();
		String contextPath = webInf.substring(1, webInf.indexOf("classes")).replaceAll("%20", " ");  

		boolean showCost = false;
		if (ChainUserInforService.isMgmtFromHQ(userInfor))
			showCost = true;
		else if (userInfor.getRoleType().isOwner())
			showCost = true;
		
		CustPreOrder preOrder = preOrderDaoImpl.getById(id, true);
		preOrder.putSetToList();

		ByteArrayInputStream byteArrayInputStream;   
		HSSFWorkbook wb = null;   
		CustPreOrderTemplate orderTemplate = new CustPreOrderTemplate(preOrder, contextPath + "template\\", showCost);
		wb = orderTemplate.process();
	
		ByteArrayOutputStream os = new ByteArrayOutputStream();   
		wb.write(os);   
  
		byte[] content = os.toByteArray();   
		byteArrayInputStream = new ByteArrayInputStream(content);  
		
		values.add(byteArrayInputStream);
		values.add(Common_util.correctFileName(preOrder.getCustName()));
		response.setReturnValue(values);   

		return response;
	}

	
}
