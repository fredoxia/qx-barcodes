package com.onlineMIS.ORM.DAO.headQ.preOrder;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
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

import com.onlineMIS.ORM.DAO.Response;
import com.onlineMIS.ORM.entity.base.Pager;
import com.onlineMIS.ORM.entity.chainS.inventoryFlow.ChainInventoryFlowOrder;
import com.onlineMIS.ORM.entity.chainS.inventoryFlow.ChainInventoryFlowOrderTemplate;
import com.onlineMIS.ORM.entity.headQ.preOrder.CustPreOrder;
import com.onlineMIS.ORM.entity.headQ.preOrder.CustPreOrderProduct;
import com.onlineMIS.ORM.entity.headQ.preOrder.CustPreOrderTemplate;
import com.onlineMIS.ORM.entity.headQ.preOrder.CustPreorderIdentity;
import com.onlineMIS.action.headQ.preOrder.PreOrderActionFormBean;
import com.onlineMIS.action.headQ.preOrder.PreOrderActionUIBean;
import com.onlineMIS.common.Common_util;

@Service
public class PreOrderHQService {

	@Autowired
	private PreOrderDaoImpl preOrderDaoImpl;
	
	@Autowired
	private PreOrderIdentityDaoImpl preOrderIdentityDaoImpl;
	
	@Transactional
	public Response searchOrders(int clientId, String preOrderIdentity, Integer page, Integer rowPerPage, String sortName, String sortOrder){
		Response response = new Response();
		Map data = new HashMap<String, Object>();
		
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
		if (clientId != 0)
			criteria.add(Restrictions.eq("custId", clientId));
		
		if (preOrderIdentity != null && !preOrderIdentity.trim().equals(""))
			criteria.add(Restrictions.eq("orderIdentity", preOrderIdentity));

		return criteria;
	}

	/**
	 * 搜索单据前，准备搜索页面
	 * @param formBean
	 */
	public void prepareSearchUI(PreOrderActionFormBean formBean, PreOrderActionUIBean uiBean) {
		List<CustPreorderIdentity> identityList = preOrderIdentityDaoImpl.getTop(10);
		
		uiBean.setIdentities(identityList);
		
	}

	/**
	 * 获取订单详情
	 * @param id
	 * @return
	 */
	@Transactional
	public Response getOrderById(int id) {
		Response response = new Response();
		
		CustPreOrder order =preOrderDaoImpl.getById(id, true);
		
		order.putSetToList();
		order.setProductSet(null);
		response.setReturnValue(order);
		return response;
	}

	@Transactional
	public Response downloadFlowOrder(int id, boolean showCost) throws IOException {
		Response response = new Response();
		List<Object> values = new ArrayList<Object>();
		
		String webInf = this.getClass().getClassLoader().getResource("").getPath();
		String contextPath = webInf.substring(1, webInf.indexOf("classes")).replaceAll("%20", " ");  

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
