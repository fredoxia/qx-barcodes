package com.onlineMIS.ORM.DAO.headQ.preOrder;

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
import com.onlineMIS.ORM.DAO.headQ.barCodeGentor.BrandDaoImpl;
import com.onlineMIS.ORM.entity.base.Pager;
import com.onlineMIS.ORM.entity.chainS.inventoryFlow.ChainInventoryFlowOrder;
import com.onlineMIS.ORM.entity.chainS.inventoryFlow.ChainInventoryFlowOrderTemplate;
import com.onlineMIS.ORM.entity.headQ.SQLServer.ClientsMS;
import com.onlineMIS.ORM.entity.headQ.barcodeGentor.Brand;
import com.onlineMIS.ORM.entity.headQ.preOrder.CustPreOrder;
import com.onlineMIS.ORM.entity.headQ.preOrder.CustPreOrderProduct;
import com.onlineMIS.ORM.entity.headQ.preOrder.CustPreOrderSummaryData;
import com.onlineMIS.ORM.entity.headQ.preOrder.CustPreOrderSummaryTemplate;
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
	
	@Autowired
	private BrandDaoImpl brandDaoImpl;

	
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
	public Response downloadPreOrder(int id, boolean showCost) throws IOException {
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

	@Transactional
	public Response downloadPreOrderSummary(String orderIdentity) throws IOException {
		Response response = new Response();
		List<Object> values = new ArrayList<Object>();
		
		String webInf = this.getClass().getClassLoader().getResource("").getPath();
		String contextPath = webInf.substring(1, webInf.indexOf("classes")).replaceAll("%20", " ");  

		//1.获取牌子
		CustPreorderIdentity orderIdentity2 = preOrderIdentityDaoImpl.get(orderIdentity, true);
		if (orderIdentity2 == null){
			response.setFail("无法找到 订货会代码");
			return response;
		}
		String[] brandArray = orderIdentity2.getBrands().split(",");
		List<Brand> brands = new ArrayList<Brand>();
		for (String brandString : brandArray){
			int brandId = Integer.parseInt(brandString);
			brands.add(brandDaoImpl.get(brandId, true));
		}	

		//2. 获取所有订单
		DetachedCriteria preOrderCriteria = DetachedCriteria.forClass(CustPreOrder.class);
		preOrderCriteria.add(Restrictions.eq("orderIdentity", orderIdentity));
		preOrderCriteria.addOrder(Order.desc("totalQuantity"));
		List<CustPreOrder> orders = preOrderDaoImpl.getByCritera(preOrderCriteria, true);
		
		//2. 分析订单数据
		//key : CustId@brandId
		//value : quantity
		//full key : CustId@-1
		Map<String, Integer> summaryMapData = new HashMap<String, Integer>();
		String mapKey = "";

		List<ClientsMS> clients = new ArrayList<ClientsMS>();
		for (CustPreOrder order : orders){
			int totalQ = 0;
			int custId = order.getCustId();
			
			ClientsMS clientsMS = new ClientsMS();
			clientsMS.setClient_id(custId);
			clientsMS.setName(order.getCustName());
			clients.add(clientsMS);
			
			Set<CustPreOrderProduct> products = order.getProductSet();
			for (CustPreOrderProduct p : products){
				int brandId = p.getProductBarcode().getProduct().getBrand().getBrand_ID();
				int q = p.getTotalQuantity();
				totalQ += q;
				
				mapKey = custId + "@" + brandId;
				Integer qInMap = summaryMapData.get(mapKey);
				if (qInMap != null){
					q += qInMap;
				} 
				
				summaryMapData.put(mapKey, q);

			}
			
			String fullKey = custId + "@" + "-1";
			summaryMapData.put(fullKey, totalQ);
		}
		
		List<CustPreOrderSummaryData> summaryDatas = new ArrayList<CustPreOrderSummaryData>();
		for (ClientsMS client : clients){
			CustPreOrderSummaryData summaryData = new CustPreOrderSummaryData();
			List<Integer> quantities = new ArrayList<Integer>();
			
			int clientId = client.getClient_id();
			String clientName = client.getName();
			
			summaryData.setCustName(clientName);
			for (Brand brand : brands){
				int brandId = brand.getBrand_ID();
				
				String key = clientId + "@" + brandId;
								
				Integer q = summaryMapData.get(key);
				if (q == null)
					q = 0;
				
				quantities.add(q);
			}
			String fullKey = clientId + "@" + "-1";
			Integer q = summaryMapData.get(fullKey);
			if (q == null)
				q = 0;
			summaryData.setSum(q);
			
			summaryData.setQ(quantities);
			
			summaryDatas.add(summaryData);
		}

		ByteArrayInputStream byteArrayInputStream;   
		HSSFWorkbook wb = null;   
		CustPreOrderSummaryTemplate custPreOrderSummaryTemplate = new CustPreOrderSummaryTemplate(orderIdentity, brands, summaryDatas, contextPath + "template\\");
		wb = custPreOrderSummaryTemplate.process();
	
		ByteArrayOutputStream os = new ByteArrayOutputStream();   
		wb.write(os);   
  
		byte[] content = os.toByteArray();   
		byteArrayInputStream = new ByteArrayInputStream(content);  
		
		values.add(byteArrayInputStream);
		values.add(Common_util.correctFileName("订货会" + orderIdentity + "汇总信息"));
		response.setReturnValue(values);   

		return response;
	}
}
