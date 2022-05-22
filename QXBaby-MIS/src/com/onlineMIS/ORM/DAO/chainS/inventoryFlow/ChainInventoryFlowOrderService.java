package com.onlineMIS.ORM.DAO.chainS.inventoryFlow;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.aspectj.internal.lang.annotation.ajcDeclareAnnotation;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.LogicalExpression;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.aop.ThrowsAdvice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.onlineMIS.ORM.DAO.Response;
import com.onlineMIS.ORM.DAO.chainS.chainMgmt.ChainInitialStockDaoImpl;
import com.onlineMIS.ORM.DAO.chainS.chainMgmt.ChainStoreGroupDaoImpl;
import com.onlineMIS.ORM.DAO.chainS.user.ChainStoreDaoImpl;
import com.onlineMIS.ORM.DAO.chainS.user.ChainStoreService;
import com.onlineMIS.ORM.DAO.chainS.user.ChainUserInforService;
import com.onlineMIS.ORM.DAO.headQ.barCodeGentor.BrandDaoImpl;
import com.onlineMIS.ORM.DAO.headQ.barCodeGentor.ColorDaoImpl;
import com.onlineMIS.ORM.DAO.headQ.barCodeGentor.ProductBarcodeDaoImpl;
import com.onlineMIS.ORM.DAO.headQ.barCodeGentor.ProductDaoImpl;
import com.onlineMIS.ORM.DAO.headQ.barCodeGentor.QuarterDaoImpl;
import com.onlineMIS.ORM.DAO.headQ.barCodeGentor.YearDaoImpl;
import com.onlineMIS.ORM.DAO.headQ.inventory.HeadQSalesHisDAOImpl;
import com.onlineMIS.ORM.entity.base.Pager;
import com.onlineMIS.ORM.entity.chainS.chainMgmt.ChainInitialStock;
import com.onlineMIS.ORM.entity.chainS.chainMgmt.ChainInitialStockId;
import com.onlineMIS.ORM.entity.chainS.chainMgmt.ChainStoreGroup;
import com.onlineMIS.ORM.entity.chainS.chainMgmt.ChainStoreGroupElement;
import com.onlineMIS.ORM.entity.chainS.inventoryFlow.ChainInOutStock;
import com.onlineMIS.ORM.entity.chainS.inventoryFlow.ChainInOutStockArchive;
import com.onlineMIS.ORM.entity.chainS.inventoryFlow.ChainInvenTraceInfoVO;
import com.onlineMIS.ORM.entity.chainS.inventoryFlow.ChainInventoryFlowOrder;
import com.onlineMIS.ORM.entity.chainS.inventoryFlow.ChainInventoryFlowOrderProduct;
import com.onlineMIS.ORM.entity.chainS.inventoryFlow.ChainInventoryFlowOrderTemplate;
import com.onlineMIS.ORM.entity.chainS.inventoryFlow.ChainInventoryItem;
import com.onlineMIS.ORM.entity.chainS.inventoryFlow.ChainInventoryItemVO;
import com.onlineMIS.ORM.entity.chainS.inventoryFlow.ChainInventoryReportTemplate;
import com.onlineMIS.ORM.entity.chainS.inventoryFlow.ChainInventoryRptItem;
import com.onlineMIS.ORM.entity.chainS.inventoryFlow.ChainInventoryVO;
import com.onlineMIS.ORM.entity.chainS.report.ChainWeeklySales;
import com.onlineMIS.ORM.entity.chainS.sales.ChainStoreSalesOrder;
import com.onlineMIS.ORM.entity.chainS.sales.ChainStoreSalesOrderProduct;
import com.onlineMIS.ORM.entity.chainS.user.ChainStore;
import com.onlineMIS.ORM.entity.chainS.user.ChainUserInfor;
import com.onlineMIS.ORM.entity.chainS.vip.ChainVIPCardDownloadTemplate;
import com.onlineMIS.ORM.entity.headQ.barcodeGentor.Brand;
import com.onlineMIS.ORM.entity.headQ.barcodeGentor.Category;
import com.onlineMIS.ORM.entity.headQ.barcodeGentor.Color;
import com.onlineMIS.ORM.entity.headQ.barcodeGentor.Product;
import com.onlineMIS.ORM.entity.headQ.barcodeGentor.ProductBarcode;
import com.onlineMIS.ORM.entity.headQ.barcodeGentor.Quarter;
import com.onlineMIS.ORM.entity.headQ.barcodeGentor.Year;
import com.onlineMIS.ORM.entity.headQ.inventory.HeadQSalesHistory;
import com.onlineMIS.ORM.entity.headQ.inventory.HeadQSalesHistoryId;
import com.onlineMIS.ORM.entity.headQ.inventory.InventoryFileTemplate;
import com.onlineMIS.ORM.entity.headQ.inventory.InventoryOrder;
import com.onlineMIS.ORM.entity.headQ.inventory.InventoryOrderProduct;
import com.onlineMIS.action.chainS.inventoryFlow.ChainInventoryFlowFormBean;
import com.onlineMIS.action.chainS.inventoryFlow.ChainInventoryFlowUIBean;
import com.onlineMIS.common.Common_util;
import com.onlineMIS.common.ERRORS;
import com.onlineMIS.common.ExcelUtil;
import com.onlineMIS.common.loggerLocal;
import com.onlineMIS.filter.SystemParm;
import com.onlineMIS.sorter.ChainInventoryOrderProductSorter;
import com.onlineMIS.sorter.ChainInventoryReportSort;
import com.onlineMIS.sorter.ChainStatisticReportItemVOSorter;


@Service
public class ChainInventoryFlowOrderService {
	@Autowired
	private ChainInventoryFlowOrderDaoImpl chainInventoryFlowOrderDaoImpl;
	
	@Autowired
	private ChainInventoryFlowOrderProductDaoImpl chainInventoryFlowOrderProductDaoImpl;
	
	@Autowired
	private ChainStoreService chainStoreService;
	
	@Autowired
	private ChainInOutStockDaoImpl chainInOutStockDaoImpl;
	
	@Autowired
	private HeadQSalesHisDAOImpl headQSalesHisDAOImpl;
	
	@Autowired
	private ProductBarcodeDaoImpl productBarcodeDaoImpl;
	
	@Autowired
	private ChainInitialStockDaoImpl chainInitialStockDaoImpl;
	
	@Autowired
	private YearDaoImpl yearDaoImpl;
	
	@Autowired
	private QuarterDaoImpl quarterDaoImpl;
	
	@Autowired
	private ColorDaoImpl colorDaoImpl;
	
	@Autowired
	private ChainStoreGroupDaoImpl chainStoreGroupDaoImpl;

	@Autowired
	private ChainStoreDaoImpl chainStoreDaoImpl;
	
	@Autowired
	private ChainInOutStockArchiveDaoImpl chainInOutStockArchiveDaoImpl;
	
	@Autowired
	private BrandDaoImpl brandDaoImpl;

	/**
	 * function to create the flow order ui bean for create/edit flow order
	 * @param loginUser
	 */
	public void prepareCreateFlowOrderFormUIBean(ChainUserInfor loginUser, ChainInventoryFlowUIBean uiBean, ChainInventoryFlowFormBean formBean, ChainInventoryFlowOrder order) {
 	    formBean.setFlowOrder(order);
 	   
 	    //1. set the store list
		List<ChainStore> stores = chainStoreService.getChainStoreList(loginUser);
		uiBean.setChainStores(stores);
		
		//2. set the creator
		formBean.getFlowOrder().setCreator(loginUser);
		
		//3. set the date
		formBean.getFlowOrder().setOrderDate(new Date());
	
	}
	
	/**
	 * 准备调货单的页面内容
	 * @param loginUser
	 * @param uiBean
	 * @param formBean
	 * @param order
	 */
	@Transactional
	public void prepareCreateInvenTransferOrderFormUIBean(
			ChainUserInfor loginUser, ChainInventoryFlowUIBean uiBean,
			ChainInventoryFlowFormBean formBean, ChainInventoryFlowOrder order) {
 	    formBean.setFlowOrder(order);
  	   
 	    //1. set the store list
		List<ChainStore> stores = chainStoreService.getChainStoreList(loginUser);
		stores.add(0, ChainStoreDaoImpl.getOutsideStore());
		uiBean.setChainStores(stores);
		
		//2. set the creator
		formBean.getFlowOrder().setCreator(loginUser);
		
		//3. set the date
		formBean.getFlowOrder().setOrderDate(new Date());
		
		//4.调货到的连锁店
		List<ChainStore> toChainStores = new ArrayList<ChainStore>();
		if (ChainUserInforService.isMgmtFromHQ(loginUser)){
			toChainStores =  chainStoreDaoImpl.getAll(true);
			toChainStores.add(0, ChainStoreDaoImpl.getOutsideStore());
	    }else if (stores != null && stores.size() >0){
			ChainStore fromChainStore = order.getFromChainStore();
			if (fromChainStore == null)
				fromChainStore = ChainStoreDaoImpl.getOutsideStore();
			getChainStores(fromChainStore.getChain_id(),loginUser.getMyChainStore().getChain_id(),toChainStores);
		}
		uiBean.setToChainStores(toChainStores);
	}
	
	public void prepareCheckInvenTraceUIBean(ChainUserInfor loginUserInfor,
			ChainInventoryFlowUIBean uiBean, ChainInventoryFlowFormBean formBean) {
 	    //1. set the store list
		List<ChainStore> stores = chainStoreService.getChainStoreList(loginUserInfor);
		uiBean.setChainStores(stores);
		
	}
	

	
	/**
	 * 当改变from chain store时，toChainStore跟着改变
	 * @param fromChainId
	 * @param loginChainId
	 * @return
	 */
	@Transactional
	public Response getToChainStoresByFrom(int fromChainId, ChainUserInfor loginUser){
		Response response = new Response();
		
		List<ChainStore> toChainStores = new ArrayList<ChainStore>();
		if (ChainUserInforService.isMgmtFromHQ(loginUser)){
			toChainStores =  chainStoreDaoImpl.getAll(true);
			toChainStores.add(0, ChainStoreDaoImpl.getOutsideStore());
		} else {
		    getChainStores(fromChainId, loginUser.getMyChainStore().getChain_id(), toChainStores);
		}
		
		response.setReturnCode(Response.SUCCESS);
		response.setReturnValue(toChainStores);
		
		return response;
	}
	
	/**
	 * 获取toChainStore下拉菜单
	 * @param fromChainId
	 * @param toChainStores
	 */
	private void getChainStores(int fromChainId, int loginChainId,List<ChainStore> toChainStores){
		ChainStore outSide = ChainStoreDaoImpl.getOutsideStore();

		if (fromChainId == outSide.getChain_id()){
			ChainStore loginChain = chainStoreService.getChainStoreByID(loginChainId);
			toChainStores.add(loginChain);
		} else {
			ChainStoreGroup chainGroup = chainStoreGroupDaoImpl.getChainStoreBelongGroup(fromChainId);
			if (chainGroup != null){
				chainStoreGroupDaoImpl.initialize(chainGroup.getChainStoreGroupElementSet());
				Set<ChainStoreGroupElement> chainGroupElements = chainGroup.getChainStoreGroupElementSet();
				if (chainGroupElements != null){
					for (ChainStoreGroupElement ele : chainGroupElements){
						int chainId = ele.getChainId();
						if (fromChainId != chainId)
	                         toChainStores.add(chainStoreService.getChainStoreByID(chainId));
					}
				}
				toChainStores.add(0, outSide);
			} else {
				toChainStores.add(outSide);
			}
		}
	}
	

	
	/**
	 * function to prepare the flow order ui bean for search
	 * @param loginUser
	 * @param uiBean
	 * @param formBean
	 */
	public void prepareSearchFlowOrderFormUIBean(ChainUserInfor loginUser,
			ChainInventoryFlowUIBean uiBean, ChainInventoryFlowFormBean formBean) {
		//1. set the store list
		if (!ChainUserInforService.isMgmtFromHQ(loginUser)){
			int chainId = loginUser.getMyChainStore().getChain_id();
			ChainStore chainStore = chainStoreService.getChainStoreByID(chainId);
			formBean.setChainStore(chainStore);
		} else {
			ChainStore allChainStore = ChainStoreDaoImpl.getAllChainStoreObject();
			formBean.setChainStore(allChainStore);
		}
		
		//2. set the status 
		ChainInventoryFlowOrder chainInventoryFlowOrder = new ChainInventoryFlowOrder();
		uiBean.setInvenOrderStatus(chainInventoryFlowOrder.getStatusMap());
		
		//3. set the type
		uiBean.setInvenOrderTypes(formBean.getFlowOrder().getTypeChainMap());

	}
	
	/**
	 * function to prepare the flow order ui bean for display
	 * @param loginUser
	 * @param uiBean
	 * @param formBean
	 */
	public void prepareDisplayFlowOrderFormUIBean(ChainUserInfor loginUser,
			ChainInventoryFlowUIBean uiBean, ChainInventoryFlowFormBean formBean, ChainInventoryFlowOrder order) {
        uiBean.setFlowOrder(order);
	}
	
	/**
	 * function to help search the flow order
	 * @param formBean
	 * @param loginUser
	 * @return
	 */
	@Transactional
	public List<ChainInventoryFlowOrder> searchInvenFlowOrders(
			ChainInventoryFlowFormBean formBean, ChainUserInfor loginUser) {
		boolean cache = false;
		Pager pager = formBean.getPager();

		if (pager.getTotalPage() == 0){
		    DetachedCriteria criteria = buildSearchInvenFlowCriter(formBean);
			criteria.setProjection(Projections.rowCount());
			int totalRecord = Common_util.getProjectionSingleValue(chainInventoryFlowOrderDaoImpl.getByCriteriaProjection(criteria, false));
			pager.initialize(totalRecord);
		} else {
			pager.calFirstResult();
			cache = true;
		}
		
		DetachedCriteria criteria2 = buildSearchInvenFlowCriter(formBean);
		criteria2.addOrder(Order.asc("orderDate")).addOrder(Order.asc("chainStore.chain_id"));
		
		List<ChainInventoryFlowOrder> chainOrders = chainInventoryFlowOrderDaoImpl.getByCritera(criteria2, pager.getFirstResult(), pager.getRecordPerPage(), cache);
		
		for (ChainInventoryFlowOrder order : chainOrders){
			if (order.getType() == ChainInventoryFlowOrder.INVENTORY_TRANSFER_ORDER){
				formatTransferOrderComment(order);
				chainInventoryFlowOrderDaoImpl.evict(order);
			}
		}
		
		return chainOrders;
	}
	
	private void formatTransferOrderComment(ChainInventoryFlowOrder order) {
		ChainStore fromChainStore = order.getFromChainStore();
		ChainStore toChainStore = order.getToChainStore();

		String fromChainName = "非连锁店";
		String toChainName = "非连锁店";
		
		if(fromChainStore != null && fromChainStore.getChain_id() != 0){
			fromChainStore = chainStoreDaoImpl.get(fromChainStore.getChain_id(), true);
			fromChainName = fromChainStore.getChain_name();
			if (fromChainName.length() > 4)
				fromChainName = fromChainName.substring(0,4);
		}
		if (toChainStore != null && toChainStore.getChain_id() != 0){
			toChainStore = chainStoreDaoImpl.get(toChainStore.getChain_id(), true);
			toChainName = toChainStore.getChain_name();
			if (toChainName.length() > 4)
				toChainName = toChainName.substring(0,4);
		}
		
		String transferComment = fromChainName + " -> " + toChainName;

		String orderComment = order.getComment();
		if (orderComment.length() > 0)
			order.setComment(transferComment + "<br/>" + orderComment);
		else 
			order.setComment(transferComment);
		
	}

	private DetachedCriteria buildSearchInvenFlowCriter(ChainInventoryFlowFormBean formBean){
        DetachedCriteria criteria = DetachedCriteria.forClass(ChainInventoryFlowOrder.class);
		
		int chainId = formBean.getChainStore().getChain_id();
		if (chainId != Common_util.ALL_RECORD){
			criteria.add(Restrictions.or(Restrictions.eq("chainStore.chain_id", chainId), Restrictions.and(Restrictions.eq("toChainStore.chain_id", chainId),Restrictions.ne("status", ChainInventoryFlowOrder.STATUS_DRAFT))));
		}
		
		int status = formBean.getFlowOrder().getStatus();
		if (status != Common_util.ALL_RECORD)
			criteria.add(Restrictions.eq("status", status));
		else 
			criteria.add(Restrictions.ne("status", ChainInventoryFlowOrder.STATUS_DELETED));
		
		int type = formBean.getFlowOrder().getType();
		if (type != Common_util.ALL_RECORD)
			criteria.add(Restrictions.eq("type", type));	
		
		criteria.add(Restrictions.between("orderDate", Common_util.formStartDate(formBean.getSearchStartTime()), Common_util.formEndDate(formBean.getSearchEndTime())));
		
		return criteria;
	}



	/**
	 * function to save the flow order to draft order 保存草稿
	 * @param flowOrder
	 * @throws Exception 
	 */
	@Transactional
	public Response saveToDraft(ChainInventoryFlowOrder flowOrder,File file, ChainUserInfor loginUser) throws Exception {
		int status_org = flowOrder.getStatus();
		Response response = new Response();
		if (status_org == ChainInventoryFlowOrder.STATUS_INITIAL || status_org == ChainInventoryFlowOrder.STATUS_DRAFT){
		    flowOrder.setStatus(ChainInventoryFlowOrder.STATUS_DRAFT);
		    
		    List<ChainInventoryFlowOrderProduct> products = null;
		    try {
		       products = parseInventoryFile(file);
		    } catch (Exception e) {
				response.setQuickValue(Response.FAIL, "库存文件格式错误");
				return response;
			}

		    List<ChainInventoryFlowOrderProduct> currentProducts = flowOrder.getProductList();
		    currentProducts.addAll(products);
		    
		    save(flowOrder, loginUser);
		    response.setReturnCode(Response.SUCCESS);
		    response.setMessage("已经成功保存");
		} else {
		    response.setReturnCode(Response.FAIL);
		    response.setMessage("保存草稿失败");
		}
		
		return response;
		
	}
	

	/**
	 * 保存 库存盘点单据
	 * @param flowOrder
	 * @param loginUser
	 * @throws Exception 
	 */
	@Transactional
	public Response saveInventoryToDraft(ChainInventoryFlowOrder flowOrder, File file,
			ChainUserInfor loginUser) throws Exception {
		Response response = new Response();
		
		int status_org = flowOrder.getStatus();
		if (status_org == ChainInventoryFlowOrder.STATUS_INITIAL || status_org == ChainInventoryFlowOrder.STATUS_DRAFT){
		    flowOrder.setStatus(ChainInventoryFlowOrder.STATUS_DRAFT);
		    
		    List<ChainInventoryFlowOrderProduct> products = null;
		    try {
		       products = parseInventoryFile(file, flowOrder.getChainStore().getChain_id());
		    } catch (Exception e) {
				response.setQuickValue(Response.FAIL, "库存文件格式错误");
				return response;
			}

		    List<ChainInventoryFlowOrderProduct> currentProducts = flowOrder.getProductList();
		    currentProducts.addAll(products);

		    save(flowOrder, loginUser);
		    response.setQuickValue(Response.SUCCESS,"已经成功保存");
		    response.setReturnValue(flowOrder.getId());
		} else {
		    response.setQuickValue(Response.FAIL,"保存草稿失败");
		}
		
		return response;
		
	}
	

	
	/**
	 * function to save the flow order to final 过账
	 * @param flowOrder
	 * @param loginUser
	 * @throws Exception 
	 */
	@Transactional
	public Response saveToFinal(ChainInventoryFlowOrder flowOrder,ChainUserInfor loginUser) throws Exception {
		int status_org = flowOrder.getStatus();
		Response response = new Response();
		if (status_org == ChainInventoryFlowOrder.STATUS_INITIAL || status_org == ChainInventoryFlowOrder.STATUS_DRAFT){
		    
		   flowOrder.setStatus(ChainInventoryFlowOrder.STATUS_COMPLETE);

		   //1. save the order to final 过账
		   save(flowOrder, loginUser);
		   
	       //2. 修改stock in/out table 
    	   //   只有报溢单,报损单，调货单可以
    	   if (flowOrder.getType() == ChainInventoryFlowOrder.FLOW_LOSS_ORDER || flowOrder.getType() == ChainInventoryFlowOrder.OVER_FLOW_ORDER){
    	      //ChainInventoryFlowOrder order = getOrderById(flowOrder.getId(), loginUser);
    		   flowOrder.setChainStore(chainStoreService.getChainStoreByID(flowOrder.getChainStore().getChain_id()));
    	       updateChainFlowOrderInOutStock(flowOrder, ChainStoreSalesOrder.STATUS_COMPLETE);
    	   } else if (flowOrder.getType() == ChainInventoryFlowOrder.INVENTORY_TRANSFER_ORDER){
    		   updateChainInvenTransferInOutStock(flowOrder, ChainStoreSalesOrder.STATUS_COMPLETE);
    	   }
		   
		   response.setReturnCode(Response.SUCCESS);
		   response.setMessage("已经成功保存");
		} else {
		    response.setReturnCode(Response.FAIL);
		    response.setMessage("保存草稿失败");
		}
		
		return response;
	}
	
	/**
	 * function to cancel the flow order 红冲
	 */
	@Transactional
	public Response cancelOrder(int orderId ,ChainUserInfor loginUser){
		ChainInventoryFlowOrder flowOrder = getOrderById(orderId, loginUser);
		int status_org = flowOrder.getStatus();
		Response response = new Response();
		if (status_org == ChainInventoryFlowOrder.STATUS_COMPLETE){
			   //1. 红冲单据，修改状态
			   String hql ="UPDATE ChainInventoryFlowOrder c SET c.status=? WHERE c.id=?";
			   Object[] values = new Object[]{ChainInventoryFlowOrder.STATUS_CANCEL, orderId};
			
			   int count = chainInventoryFlowOrderDaoImpl.executeHQLUpdateDelete(hql, values, true);
	
		       if (count == 0){
		        	response.setReturnCode(Response.FAIL);
		        	response.setMessage("红冲单据失败");
		       } else {
			       //2. 修改stock in/out table 
		    	   //   只有报溢单,报损单，调货单可以
		    	   if (flowOrder.getType() == ChainInventoryFlowOrder.FLOW_LOSS_ORDER || flowOrder.getType() == ChainInventoryFlowOrder.OVER_FLOW_ORDER){
		    	       updateChainFlowOrderInOutStock(flowOrder, ChainStoreSalesOrder.STATUS_CANCEL);
		    	   } else if (flowOrder.getType() == ChainInventoryFlowOrder.INVENTORY_TRANSFER_ORDER){
		    		   updateChainInvenTransferInOutStock(flowOrder, ChainStoreSalesOrder.STATUS_CANCEL);
		    	   }
		        	
		           response.setReturnCode(Response.SUCCESS);
		           response.setMessage("成功红冲单据");
		       }
			} else {
			    response.setReturnCode(Response.FAIL);
			    response.setMessage("只有过账的单据可以红冲");
			}
			
			return response;
	}
	
	/**
	 * function to copy the order
	 * 1. if 
	 */
	@Transactional
	public Response copyOrder(ChainInventoryFlowOrder order ,ChainUserInfor loginUser){
		int orderId = order.getId();
		ChainInventoryFlowOrder flowOrder = getOrderById(orderId, loginUser);
		Response response = new Response();
		if (flowOrder == null){
			response.setReturnCode(Response.FAIL);
			response.setMessage("无法复制相应单据");
		} else {
			order = chainInventoryFlowOrderDaoImpl.copy(flowOrder);
			order.setStatus(ChainInventoryFlowOrder.STATUS_DRAFT);
			order.setComment("复制于单据:" + orderId);

			response.setReturnCode(Response.SUCCESS);
			response.setReturnValue(order);
			response.setMessage("成功复制单据");
		}
		return response;
	}
	
	/**
	 * function to updat the chain stock in/out level
	 * @param order
	 * @param statusCancel
	 * @throws Exception 
	 */
	private void updateChainFlowOrderInOutStock(ChainInventoryFlowOrder order,
			int status)  {
		boolean isCancel = false;
		int orderType = order.getType();
		int clientId = order.getChainStore().getClient_id();
		
		if (clientId == 0)
			loggerLocal.error("单据的客户ID不能为零. 单据号" + order.getId());

		if (status == ChainInventoryFlowOrder.STATUS_CANCEL)
			isCancel = true;
		
		String orderId = String.valueOf(order.getId());
		int offset = isCancel ? -1 : 1;
		String orderIdHead = isCancel ? "C" : "";

		if (orderType == ChainInventoryFlowOrder.FLOW_LOSS_ORDER){
			orderId = ChainInOutStock.CHAIN_FLOWLOSS + orderIdHead + orderId;
			offset *= -1;
		} else if (orderType == ChainInventoryFlowOrder.OVER_FLOW_ORDER){
			orderId = ChainInOutStock.CHAIN_OVERFLOW + orderIdHead + orderId;	
		} else 
			return ;
		
		 Iterator<ChainInventoryFlowOrderProduct> orderProducts = order.getProductSet().iterator();
		 while (orderProducts.hasNext()){
			 ChainInventoryFlowOrderProduct orderProduct = orderProducts.next();
		 
			 int productId = orderProduct.getProductBarcode().getId();
			 String barcode = orderProduct.getProductBarcode().getBarcode();
			 int quantity = orderProduct.getQuantity() * offset;
			 
			 //to get the latest cost for wholesaler
			 HeadQSalesHistoryId historyId = new HeadQSalesHistoryId(productId, clientId);
			 HeadQSalesHistory priorHistory = headQSalesHisDAOImpl.get(historyId, true);
			 double cost = 0;
			 
			 ProductBarcode pBarcode = productBarcodeDaoImpl.get(productId, true);
			 double salePrice = pBarcode.getProduct().getSalesPrice();
			 
			 if (priorHistory != null)
				 cost = priorHistory.getWholePrice();
			 else {
				 ChainInitialStockId initialStockId = new ChainInitialStockId(barcode, clientId);
				 ChainInitialStock initialStock = chainInitialStockDaoImpl.get(initialStockId, true);
				 if (initialStock != null)
					 cost = initialStock.getCost();
				 else {
					 loggerLocal.error(ERRORS.ERROR_NO_COST + " 找不到货品进价: " + clientId + "," + barcode + "," + productId + "," + orderProduct.getProductBarcode().getProduct().getProductCode());
					 
					 cost = productBarcodeDaoImpl.getWholeSalePrice(pBarcode);
				 }
			 }
			 
			 double chainSalePrice = pBarcode.getProduct().getSalesPrice();
			 
			 ChainInOutStock inOutStock = new ChainInOutStock(barcode, clientId, orderId, order.getType() , cost, cost * quantity,salePrice, salePrice * quantity,chainSalePrice * quantity, quantity, orderProduct.getProductBarcode());
			 chainInOutStockDaoImpl.save(inOutStock, false);
		 }
	}
	
	/**
	 * 调库产生的库存需要修改
	 * @param order
	 * @param status
	 */
	private void updateChainInvenTransferInOutStock(ChainInventoryFlowOrder order,
			int status)  {
		boolean isCancel = false;
		ChainStore fromChainStore = order.getFromChainStore();
		ChainStore toChainStore = order.getToChainStore();
		
		int fromClientId = 0;
		int toClientId = 0;
		
		if (fromChainStore != null && fromChainStore.getChain_id() !=0){
			fromClientId = chainStoreService.getChainStoreByID(fromChainStore.getChain_id()).getClient_id();
		}
		if (toChainStore != null && toChainStore.getChain_id() !=0){
			toClientId = chainStoreService.getChainStoreByID(toChainStore.getChain_id()).getClient_id();
		}

		if (fromClientId == 0 && toClientId == 0)
			loggerLocal.error("调货单据的调出客户和调入客户ID不能同时为零. 单据号" + order.getId());

		if (status == ChainInventoryFlowOrder.STATUS_CANCEL)
			isCancel = true;
		
		String orderId = String.valueOf(order.getId());
		int offset = isCancel ? -1 : 1;
		String orderIdHead = isCancel ? "C" : "";

		orderId = ChainInOutStock.CHAIN_TRANSFER + orderIdHead + orderId;	

		 Iterator<ChainInventoryFlowOrderProduct> orderProducts = order.getProductSet().iterator();
		 while (orderProducts.hasNext()){
			 ChainInventoryFlowOrderProduct orderProduct = orderProducts.next();
		 
			 int productId = orderProduct.getProductBarcode().getId();
			 String barcode = orderProduct.getProductBarcode().getBarcode();
			 int quantity = orderProduct.getQuantity() * offset;
			 
			 /**
			  * 1. 为from chain准备库存调出
			  */
			 double cost = 0;

			 ProductBarcode pBarcode = productBarcodeDaoImpl.get(productId, true);
			 double salePrice = pBarcode.getProduct().getSalesPrice();
			 
			 double chainSalePrice = pBarcode.getProduct().getSalesPrice();
			 
			 if (fromClientId > 0) {
				 //to get the latest cost for from wholesaler
				 int fromQuantity = -1 * quantity;
				 HeadQSalesHistoryId historyId = new HeadQSalesHistoryId(productId, fromClientId);
				 HeadQSalesHistory priorHistory = headQSalesHisDAOImpl.get(historyId, true);

				 if (priorHistory != null)
					 cost = priorHistory.getWholePrice();
				 else {
					 loggerLocal.error(ERRORS.ERROR_NO_COST + " 找不到货品进价: " + fromClientId + "," + barcode + "," + productId + "," + orderProduct.getProductBarcode().getProduct().getProductCode());
					 cost = productBarcodeDaoImpl.getWholeSalePrice(pBarcode);
				 }

				 ChainInOutStock inOutStock = new ChainInOutStock(barcode, fromClientId, orderId, order.getType() , cost, cost * fromQuantity,salePrice, salePrice * fromQuantity, chainSalePrice* fromQuantity, fromQuantity, orderProduct.getProductBarcode());
				 chainInOutStockDaoImpl.save(inOutStock, false);
			 } else 
				 cost = productBarcodeDaoImpl.getWholeSalePrice(pBarcode);
			 
			 /**
			  * 为to chain store准备库存调入单, 和saleshistory
			  */
			 if (toClientId > 0){
				 //1. 更新in-out
				 int toQuantity = quantity;	 
				 ChainInOutStock inOutStockTo = new ChainInOutStock(barcode, toClientId, orderId, order.getType() , cost, cost * toQuantity,salePrice, salePrice * toQuantity, chainSalePrice*toQuantity, toQuantity, orderProduct.getProductBarcode());
				 chainInOutStockDaoImpl.save(inOutStockTo, false);
				 
//				 //2. 更新history以后合并系统可以
//				 HeadQSalesHistory salesHistory = new HeadQSalesHistory(pBarcode.getId(), toClientId, 0 , cost, 0, toQuantity, 0, 1);
//				 headQSalesHisDAOImpl.saveOrUpdate(salesHistory, false);
				 
			 }
		 }
	}


	/**
	 * to get the chain flow order by id
	 * @param orderId
	 * @return
	 */
	@Transactional
	public ChainInventoryFlowOrder getOrderById(int orderId,ChainUserInfor loginUser) {
		ChainInventoryFlowOrder order = chainInventoryFlowOrderDaoImpl.get(orderId, false);
		chainInventoryFlowOrderDaoImpl.initialize(order);
		
		//check the toChainStore
		int orderType = order.getType();
		if (orderType == ChainInventoryFlowOrder.INVENTORY_TRANSFER_ORDER){
			ChainStore fromChainStore= order.getFromChainStore();
			if (fromChainStore == null){
			    order.setFromChainStore(ChainStoreDaoImpl.getOutsideStore());
			    chainInventoryFlowOrderDaoImpl.evict(order);
			}
			ChainStore toChainStore = order.getToChainStore();
			if (toChainStore == null){
			    order.setToChainStore(ChainStoreDaoImpl.getOutsideStore());
			    chainInventoryFlowOrderDaoImpl.evict(order);
			}
			
			order.putSetToList();
		} else if (orderType ==  ChainInventoryFlowOrder.INVENTORY_ORDER){
			order.putSetToList(new ChainInventoryOrderProductSorter());
		} else 
		    order.putSetToList();
		 
		return order;
	}
	
	/**
	 * the function to delete the order by mark the order status to deleted
	 * @param orderId
	 * @param loginUser
	 */
	public Response deleteOrderById(int orderId, ChainUserInfor loginUser){
		Response response = new Response();
		ChainInventoryFlowOrder flowOrder = chainInventoryFlowOrderDaoImpl.get(orderId, true);
		
		if (flowOrder.getStatus() == ChainInventoryFlowOrder.STATUS_DRAFT){
		    String hql ="UPDATE ChainInventoryFlowOrder c SET c.status=? WHERE c.id=?";
		    Object[] values = new Object[]{ChainInventoryFlowOrder.STATUS_DELETED, orderId};
		
		    int count = chainInventoryFlowOrderDaoImpl.executeHQLUpdateDelete(hql, values, true);

	        if (count == 0){
	        	response.setReturnCode(Response.FAIL);
	        	response.setMessage("删除单据失败");
	        } else {
	        	response.setReturnCode(Response.SUCCESS);
	        	response.setMessage("成功删除单据");
	        }
		} else {
        	response.setReturnCode(Response.FAIL);
        	response.setMessage("过账的单据无法删除");
		}
		
		return response;
	}
	
	
	/**
	 * add the general save information
	 * @param flowOrder
	 * @param loginUser
	 * @throws Exception 
	 */
	@Transactional
	public ChainInventoryFlowOrder save(ChainInventoryFlowOrder flowOrder,
			ChainUserInfor loginUser) throws Exception{
		flowOrder.setOrderDate(new Date());
		flowOrder.setCreator(loginUser);
		
		//1. to group the product list
		groupProductList(flowOrder);
		
		//2. 检查是否toChainStore
		ChainStore toChainStore = flowOrder.getToChainStore();
		if (toChainStore != null && toChainStore.getChain_id() == 0)
			flowOrder.setToChainStore(null);
		
		//3. 检查是否fromChainStore
		ChainStore fromChainStore = flowOrder.getFromChainStore();
		if (fromChainStore != null && fromChainStore.getChain_id() == 0)
			flowOrder.setFromChainStore(null);
		
		//4. 注入 chainStore
		//   如果是调库存单，需要注入chainStore.首先fromChainStore,然后toChainStore
		if (flowOrder.getType() == ChainInventoryFlowOrder.INVENTORY_TRANSFER_ORDER){		
			if (fromChainStore != null && fromChainStore.getChain_id() != 0)
				flowOrder.setChainStore(fromChainStore);
			else if (toChainStore != null && toChainStore.getChain_id() != 0)
				flowOrder.setChainStore(toChainStore);
			else 
				throw new Exception("无法找到调货单的发起人");
		}
				
		
		flowOrder.buildIndex();
		flowOrder.putListToSet();
		
		flowOrder.setProductList(null);
		
		int orderId = flowOrder.getId();

		if (orderId != 0){	
			 ChainInventoryFlowOrder originalOrder = getOrderById(orderId, loginUser);
			 
			 List<Integer> newProducts =  getProductIds(flowOrder);
			 List<Integer> originalProducts = getProductIds(originalOrder);
			 List<Integer> deletedProducts = new ArrayList<Integer>();
			 for (int id: originalProducts){
				 if (!newProducts.contains(id))
					 deletedProducts.add(id);
			 }
			 chainInventoryFlowOrderProductDaoImpl.deleteProducts(deletedProducts);
			 
			 chainInventoryFlowOrderDaoImpl.evict(originalOrder);

			 chainInventoryFlowOrderDaoImpl.saveOrUpdate(flowOrder,false);
		} else {
			 chainInventoryFlowOrderDaoImpl.save(flowOrder, false);
		}
		
		return flowOrder;

	}
	
    /**
     * the function to calculate the inventory order
     * @param flowOrder
     * @return
     */
	public Response calculateInventoryOrder(ChainInventoryFlowOrder flowOrder) {
		Response response = new Response();
		
		int orderId = flowOrder.getId();
		
		groupProductList(flowOrder);
		
		if (orderId != 0){
			ChainInventoryFlowOrder inventoryFlowOrder = chainInventoryFlowOrderDaoImpl.get(orderId, true);
			if (inventoryFlowOrder != null){
				inventoryFlowOrder.setProductList(flowOrder.getProductList());
				inventoryFlowOrder.setTotalInventoryQ(flowOrder.getTotalInventoryQ());
				inventoryFlowOrder.setTotalQuantity(flowOrder.getTotalQuantity());
				inventoryFlowOrder.setTotalQuantityDiff(flowOrder.getTotalQuantityDiff());
				
				response.setReturnCode(Response.SUCCESS);
				response.setReturnValue(inventoryFlowOrder);
				
				return response;
			} else {
				response.setReturnCode(Response.FAIL);
				response.setMessage("重新计算失败:原始单据无法找到");
				return response;
			}
		} else {
			response.setReturnCode(Response.SUCCESS);
			response.setReturnValue(flowOrder);
            return response;
		}
	}
	
	/**
	 * function to generatet the inventory report
	 * @param flowOrder
	 * @return
	 * @throws Exception 
	 */
	@Transactional
	public Response genInventoryReport(ChainInventoryFlowOrder flowOrder, File file,
			ChainUserInfor loginUser) throws Exception {
		int status_org = flowOrder.getStatus();
		Response response = new Response();
		if (status_org == ChainInventoryFlowOrder.STATUS_INITIAL || status_org == ChainInventoryFlowOrder.STATUS_DRAFT){
		    flowOrder.setStatus(ChainInventoryFlowOrder.STATUS_COMPLETE);

		    List<ChainInventoryFlowOrderProduct> products = null;
		    try {
		       products = parseInventoryFile(file, flowOrder.getChainStore().getChain_id());
		    } catch (Exception e) {
				response.setQuickValue(Response.FAIL, "库存文件格式错误");
				return response;
			}

		    List<ChainInventoryFlowOrderProduct> currentProducts = flowOrder.getProductList();
		    currentProducts.addAll(products);
		    
		    //1. order the products by Year, Quarter, Brand
		    chainInventoryFlowOrderDaoImpl.initialize(flowOrder.getProductList());
		    
		    //1. save the order to final 过账
		    save(flowOrder, loginUser);
		   
		    response.setReturnCode(Response.SUCCESS);
		    response.setReturnValue(flowOrder.getId());
		    response.setMessage("已经成功保存");
		} else {
		    response.setReturnCode(Response.FAIL);
		    response.setMessage("保存单据失败");
		}
		
		return response;
	}
	
	/**
	 * 自动纠正库存
	 * @param flowOrder
	 * @param loginUser
	 * @return
	 * @throws Exception 
	 */
	@Transactional
	public Response adjustInventory(ChainInventoryFlowOrder inventoryOrder,
			ChainUserInfor loginUser) throws Exception {
		int status_org = inventoryOrder.getStatus();
		Response response = new Response();
		if (status_org == ChainInventoryFlowOrder.STATUS_INITIAL || status_org == ChainInventoryFlowOrder.STATUS_DRAFT){
			//inventoryOrder.setStatus(ChainInventoryFlowOrder.STATUS_COMPLETE);

		    //1.整理order product
		    groupProductList(inventoryOrder);
		    
		    //2. 自动创建报损单，报溢单
		    ChainInventoryFlowOrder overFlowOrder = new ChainInventoryFlowOrder();
		    ChainInventoryFlowOrder flowLossOrder = new ChainInventoryFlowOrder();
		    List<ChainInventoryFlowOrderProduct> overFlowOrderProducts = new ArrayList<ChainInventoryFlowOrderProduct>();
		    List<ChainInventoryFlowOrderProduct> flowLossOrderProducts = new ArrayList<ChainInventoryFlowOrderProduct>();
		    List<ChainInventoryFlowOrderProduct> inventoryOrderProducts = inventoryOrder.getProductList();
		    int totalInventoryQ_overFlow = 0;
		    int totalQuantity_overFlow = 0;
		    int totalInventoryQ_flowLoss = 0;
		    int totalQuantity_flowLoss = 0;		    
		    for (ChainInventoryFlowOrderProduct inventoryOrderProduct : inventoryOrderProducts){
		    	int quantityDiff = inventoryOrderProduct.getQuantityDiff();
		    	int inventoryQ = inventoryOrderProduct.getInventoryQ();
		    	ProductBarcode productBarcode = inventoryOrderProduct.getProductBarcode();
		    	
		    	//实际库存 > 电脑库存
		    	if (quantityDiff > 0) {
		    		ChainInventoryFlowOrderProduct overFlowOrderProduct = new ChainInventoryFlowOrderProduct();
		    		overFlowOrderProduct.setInventoryQ(inventoryQ);
		    		overFlowOrderProduct.setQuantity(quantityDiff);
		    		overFlowOrderProduct.setProductBarcode(productBarcode);
		    		overFlowOrderProduct.setComment("一键纠正");
		    		overFlowOrderProducts.add(overFlowOrderProduct);
		    		totalInventoryQ_overFlow += inventoryQ;
		    		totalQuantity_overFlow += quantityDiff;
		    	//实际库存 < 电脑库存
		    	} else if (quantityDiff < 0){
		    		ChainInventoryFlowOrderProduct flowLossOrderProduct = new ChainInventoryFlowOrderProduct();
		    		flowLossOrderProduct.setInventoryQ(inventoryQ);
		    		flowLossOrderProduct.setQuantity(Math.abs(quantityDiff));
		    		flowLossOrderProduct.setProductBarcode(productBarcode);
		    		flowLossOrderProduct.setComment("一键纠正");
		    		flowLossOrderProducts.add(flowLossOrderProduct);
		    		totalInventoryQ_flowLoss += inventoryQ;
		    		totalQuantity_flowLoss += Math.abs(quantityDiff);
		    	}
		    }
		    String msg = "";
		    if (overFlowOrderProducts.size() > 0){
		    	overFlowOrder.setChainStore(inventoryOrder.getChainStore());
		    	overFlowOrder.setCreator(loginUser);
		    	overFlowOrder.setOrderDate(new Date());
		    	overFlowOrder.setStatus(ChainInventoryFlowOrder.STATUS_INITIAL);
		    	overFlowOrder.setType(ChainInventoryFlowOrder.OVER_FLOW_ORDER);
		    	overFlowOrder.setTotalInventoryQ(totalInventoryQ_overFlow);
		    	overFlowOrder.setTotalQuantity(totalQuantity_overFlow);
		    	overFlowOrder.setComment("一键纠正" + inventoryOrder.getId());
		    	overFlowOrder.setProductList(overFlowOrderProducts);
		    	overFlowOrder.buildIndex();
		    	overFlowOrder.putListToSet();
		    	saveToFinal(overFlowOrder, loginUser);
		    	msg += "报溢单据号:" + overFlowOrder.getId() +"\n";
		    }
		    if (flowLossOrderProducts.size() > 0){
		    	flowLossOrder.setChainStore(inventoryOrder.getChainStore());
		    	flowLossOrder.setCreator(loginUser);
		    	flowLossOrder.setOrderDate(new Date());
		    	flowLossOrder.setStatus(ChainInventoryFlowOrder.STATUS_INITIAL);
		    	flowLossOrder.setType(ChainInventoryFlowOrder.FLOW_LOSS_ORDER);
		    	flowLossOrder.setTotalInventoryQ(totalInventoryQ_flowLoss);
		    	flowLossOrder.setTotalQuantity(totalQuantity_flowLoss);
		    	flowLossOrder.setComment("一键纠正" + inventoryOrder.getId());
		    	flowLossOrder.setProductList(flowLossOrderProducts);
		    	flowLossOrder.buildIndex();
		    	flowLossOrder.putListToSet();
		    	saveToFinal(flowLossOrder, loginUser);
		    	msg += "报损单据号:" + flowLossOrder.getId() +"\n";
		    }
		   
		    response.setReturnCode(Response.SUCCESS);
		    if (msg.length() > 0)
		        response.setMessage("已经成功自动生成." + msg);
		    else
		    	response.setMessage("所有实际库存和电脑库存都匹配.无需纠正库存");
		} else {
		    response.setReturnCode(Response.FAIL);
		    response.setMessage("生成报表或者红冲的单据不能再做一键纠正");
		}
		
		return response;
	}
	
	/**
	 * 获取这个盘点单中所有的年份和季度
	 * @param inventoryOrder
	 * @return
	 */
	public Response getYearQuarterInOrder(ChainInventoryFlowOrder inventoryOrder, List<String> yearQuarterKey){
		List<ChainInventoryFlowOrderProduct> productList = inventoryOrder.getProductList();
		Set<String> yearQuarter = new HashSet<String>();
		Product product = null ;
		for (ChainInventoryFlowOrderProduct orderProduct : productList){
			if (orderProduct == null || orderProduct.getProductBarcode() == null || orderProduct.getProductBarcode().getId() == 0 || orderProduct.getProductBarcode().getBarcode()=="")
				continue;
			product = orderProduct.getProductBarcode().getProduct();
			yearQuarter.add(product.getYear().getYear() + "-" + product.getQuarter().getQuarter_Name());
			String key = product.getYear().getYear() + "-" + product.getQuarter().getQuarter_Name();
			if (yearQuarterKey!= null && !yearQuarterKey.contains(key))
				yearQuarterKey.add(key);
		}
		
		List<String> yearQuarterList = new ArrayList<String>(yearQuarter);
		Collections.sort(yearQuarterList);
		
		Response response = new Response();
		response.setReturnCode(Response.SUCCESS);
		response.setReturnValue(yearQuarterList);
		return response;
	}
	
	/**
	 * 将会按照这张单据成为实际库存
	 * @param flowOrder
	 * @param loginUser
	 * @return
	 * @throws Exception 
	 */
	@Transactional
	public Response clearAndadjustInventory(ChainInventoryFlowOrder inventoryOrder,
			ChainUserInfor loginUser) throws Exception {
		int status_org = inventoryOrder.getStatus();
		Response response = new Response();
		if (status_org == ChainInventoryFlowOrder.STATUS_INITIAL || status_org == ChainInventoryFlowOrder.STATUS_DRAFT){
		    //1.整理这张单据中order product
			List<ChainInventoryFlowOrderProduct> productList = inventoryOrder.getProductList();
			//to store the sequence product id list
			Set<String> barcodeInOrder = new LinkedHashSet<String>();
			Map<String, Integer> inventoryInOrderMap = new HashMap<String,Integer>();
			for (ChainInventoryFlowOrderProduct orderProduct : productList){
				if (orderProduct != null){
				    ProductBarcode productBarcode = orderProduct.getProductBarcode();
					if (productBarcode != null){
						String barcode = productBarcode.getBarcode();
						int productId = productBarcode.getId();
						if (barcode != null && !barcode.equals("") && productId != 0){
							//1.1 to make the order product ids
							barcodeInOrder.add(barcode);
							
							//1.2 to group the product quantity
							int productQ = orderProduct.getQuantity();
							Integer inventoryObj = inventoryInOrderMap.get(barcode);
							
							if (inventoryObj != null){
								inventoryInOrderMap.put(barcode, productQ + inventoryObj);
							} else {
								inventoryInOrderMap.put(barcode, productQ);
							}
						}
					}
				}
			}	
			
			//1.2 把这次年份和季度整理出来
			List<String> yearQuarterNames = new ArrayList<String>();
			getYearQuarterInOrder(inventoryOrder, yearQuarterNames);
			Map<String, Year> yearMap = yearDaoImpl.getYearNameMap();
			Map<String, Quarter> quarterMap = quarterDaoImpl.getQuarterNameMap();

		    //2. 获取出连锁店目前电脑存在的库存,只包含在year quarterkey里面
			Map<String, Integer> inventoryPCMap = new HashMap<String,Integer>();
			ChainStore chainStore = chainStoreService.getChainStoreByID(inventoryOrder.getChainStore().getChain_id());
			int clientId = chainStore.getClient_id();
			
			DetachedCriteria criteria = DetachedCriteria.forClass(ChainInOutStock.class);
			criteria.add(Restrictions.eq("clientId", clientId));
			
			if (yearQuarterNames != null && !yearQuarterNames.isEmpty()){
				DetachedCriteria productBarcodeCriteria = criteria.createCriteria("productBarcode");
				DetachedCriteria productCriteria = productBarcodeCriteria.createCriteria("product");
				
				String[] keyArray = yearQuarterNames.get(0).split("-");

				int yearKey = yearMap.get(keyArray[0]).getYear_ID();
				int quarterKey = quarterMap.get(keyArray[1]).getQuarter_ID();
				LogicalExpression restrictions = Restrictions.and(Restrictions.eq("year.year_ID", yearKey), Restrictions.eq("quarter.quarter_ID", quarterKey));

				for (int i =1; i < yearQuarterNames.size(); i++){
					keyArray = yearQuarterNames.get(i).split("-");
					yearKey = yearMap.get(keyArray[0]).getYear_ID();
					quarterKey = quarterMap.get(keyArray[1]).getQuarter_ID();
					LogicalExpression restrictions2 = Restrictions.and(Restrictions.eq("year.year_ID", yearKey), Restrictions.eq("quarter.quarter_ID", quarterKey));
					restrictions = Restrictions.or(restrictions, restrictions2);
				}
				
				productCriteria.add(restrictions);
			}
			

			ProjectionList projList = Projections.projectionList();
			projList.add(Projections.groupProperty("barcode"));
			projList.add(Projections.sum("quantity"));
			criteria.setProjection(projList);
			
			List<Object> result = chainInOutStockDaoImpl.getByCriteriaProjection(criteria,  false);
			for (int i = 0; i < result.size(); i++){
				  Object object = result.get(i);
				  if (object != null){
					 Object[] recordResult = (Object[])object;
					 String barcode = String.valueOf(recordResult[0]);
					 int quantity = Common_util.getInt(recordResult[1]);
					 inventoryPCMap.put(barcode, quantity);
				  }
			}
			Set<String> barcodeInPC = new HashSet<String>();
			barcodeInPC = inventoryPCMap.keySet();
			
			Set<String> barcodeAll = new HashSet<String>();
			barcodeAll.addAll(barcodeInOrder);
			barcodeAll.addAll(barcodeInPC);
			
			//3. 获取productbarcode 对象
			Map<String, ProductBarcode> barcodeMap = productBarcodeDaoImpl.getProductMapByBarcode(barcodeAll);

		    //3. 自动创建报损单，报溢单
		    ChainInventoryFlowOrder overFlowOrder = new ChainInventoryFlowOrder();
		    ChainInventoryFlowOrder flowLossOrder = new ChainInventoryFlowOrder();
		    List<ChainInventoryFlowOrderProduct> overFlowOrderProducts = new ArrayList<ChainInventoryFlowOrderProduct>();
		    List<ChainInventoryFlowOrderProduct> flowLossOrderProducts = new ArrayList<ChainInventoryFlowOrderProduct>();

		    int totalInventoryQ_overFlow = 0;
		    int totalQuantity_overFlow = 0;
		    int totalInventoryQ_flowLoss = 0;
		    int totalQuantity_flowLoss = 0;		    
		    for (String barcode : barcodeAll){
		    	Integer quantityInOrderObj = inventoryInOrderMap.get(barcode);
		    	Integer quantityInPCObj = inventoryPCMap.get(barcode);
		    	
		    	if (quantityInOrderObj == null && quantityInPCObj==null)
		    		continue;
		    	
		    	int quantityInOrder = quantityInOrderObj==null?0:quantityInOrderObj.intValue();
		    	int quantityInPC = quantityInPCObj==null?0:quantityInPCObj.intValue();
		    	
		    	int quantityDiff = quantityInOrder - quantityInPC;
		    	ProductBarcode productBarcode = barcodeMap.get(barcode);
		    	if (productBarcode == null){
		    		loggerLocal.error("保存库存单据错误 : " + barcode);
		    		continue;
		    	}
		    	
		    	//实际库存 > 电脑库存
		    	if (quantityDiff > 0) {
		    		ChainInventoryFlowOrderProduct overFlowOrderProduct = new ChainInventoryFlowOrderProduct();
		    		overFlowOrderProduct.setInventoryQ(quantityInPC);
		    		overFlowOrderProduct.setQuantity(quantityDiff);
		    		overFlowOrderProduct.setProductBarcode(productBarcode);
		    		overFlowOrderProduct.setComment("库存扎帐");
		    		overFlowOrderProducts.add(overFlowOrderProduct);
		    		totalInventoryQ_overFlow += quantityInPC;
		    		totalQuantity_overFlow += quantityDiff;
		    	//实际库存 < 电脑库存
		    	} else if (quantityDiff < 0){
		    		ChainInventoryFlowOrderProduct flowLossOrderProduct = new ChainInventoryFlowOrderProduct();
		    		flowLossOrderProduct.setInventoryQ(quantityInPC);
		    		flowLossOrderProduct.setQuantity(Math.abs(quantityDiff));
		    		flowLossOrderProduct.setProductBarcode(productBarcode);
		    		flowLossOrderProduct.setComment("库存扎帐");
		    		flowLossOrderProducts.add(flowLossOrderProduct);
		    		totalInventoryQ_flowLoss += quantityInPC;
		    		totalQuantity_flowLoss += Math.abs(quantityDiff);
		    	}
		    }
		    
		    String msg = "";
		    if (overFlowOrderProducts.size() > 0){
		    	overFlowOrder.setChainStore(inventoryOrder.getChainStore());
		    	overFlowOrder.setCreator(loginUser);
		    	overFlowOrder.setOrderDate(new Date());
		    	overFlowOrder.setStatus(ChainInventoryFlowOrder.STATUS_INITIAL);
		    	overFlowOrder.setType(ChainInventoryFlowOrder.OVER_FLOW_ORDER);
		    	overFlowOrder.setTotalInventoryQ(totalInventoryQ_overFlow);
		    	overFlowOrder.setTotalQuantity(totalQuantity_overFlow);
		    	overFlowOrder.setComment("库存扎帐" + inventoryOrder.getId());
		    	overFlowOrder.setProductList(overFlowOrderProducts);
		    	overFlowOrder.buildIndex();
		    	overFlowOrder.putListToSet();
		    	saveToFinal(overFlowOrder, loginUser);
		    	msg += "报溢单据号:" + overFlowOrder.getId() +"\n";
		    }
		    if (flowLossOrderProducts.size() > 0){
		    	flowLossOrder.setChainStore(inventoryOrder.getChainStore());
		    	flowLossOrder.setCreator(loginUser);
		    	flowLossOrder.setOrderDate(new Date());
		    	flowLossOrder.setStatus(ChainInventoryFlowOrder.STATUS_INITIAL);
		    	flowLossOrder.setType(ChainInventoryFlowOrder.FLOW_LOSS_ORDER);
		    	flowLossOrder.setTotalInventoryQ(totalInventoryQ_flowLoss);
		    	flowLossOrder.setTotalQuantity(totalQuantity_flowLoss);
		    	flowLossOrder.setComment("库存扎帐" + inventoryOrder.getId());
		    	flowLossOrder.setProductList(flowLossOrderProducts);
		    	flowLossOrder.buildIndex();
		    	flowLossOrder.putListToSet();
		    	saveToFinal(flowLossOrder, loginUser);
		    	msg += "报损单据号:" + flowLossOrder.getId() +"\n";
		    }
		   
		    response.setReturnCode(Response.SUCCESS);
		    if (msg.length() > 0)
		        response.setMessage("已经将电脑库存清空并修正成实际库存。成功自动生成." + msg);
		    else
		    	response.setMessage("所有实际库存和电脑库存都匹配.无需纠正库存");
		} else {
		    response.setReturnCode(Response.FAIL);
		    response.setMessage("生成报表或者红冲的单据不能再做一键纠正");
		}
		
		return response;
	}

    /**
     * function to group the flow order's product list
     * @param flowOrder
     * @throws Exception 
     */
	private void groupProductList(ChainInventoryFlowOrder flowOrder){
		List<ChainInventoryFlowOrderProduct> productList = flowOrder.getProductList();
		
		//to store the sequence product id list
		Set<String> barcodes = new LinkedHashSet<String>();
		
		//list of the post-group
		List<ChainInventoryFlowOrderProduct> productList2 = new ArrayList<ChainInventoryFlowOrderProduct>();
		
		//1. to group the quantity
		Map<String, ChainInventoryFlowOrderProduct> productMap = new HashMap<String,ChainInventoryFlowOrderProduct>();
		int totalProductQ = 0;
		int totalInventoryLevel = 0;
		for (ChainInventoryFlowOrderProduct orderProduct : productList){
			if (orderProduct != null){
			    ProductBarcode productBarcode = orderProduct.getProductBarcode();
				if (productBarcode != null){
					String barcode = productBarcode.getBarcode();
					int productId = productBarcode.getId();
					if (barcode != null && !barcode.equals("") && productId != 0){
						//1.1 to make the order product ids
						barcodes.add(barcode);
						
						//1.2 to group the product quantity
						int productQ = orderProduct.getQuantity();
						totalProductQ += productQ;
						ChainInventoryFlowOrderProduct orderProduct2 = productMap.get(barcode);
						
						if (orderProduct2 != null){
							int originalQ = orderProduct2.getQuantity();
							orderProduct2.setQuantity(productQ + originalQ);
						} else {
							productMap.put(barcode, orderProduct);
						}
					}
				}
			}
		}
		flowOrder.setTotalQuantity(totalProductQ);
		
		//2. calculate the inventory if it is inventory order
		if (flowOrder.getType() == ChainInventoryFlowOrder.INVENTORY_ORDER){
			ChainStore chainStore = chainStoreService.getChainStoreByID(flowOrder.getChainStore().getChain_id());
	        Map<String, Integer> inventoryLevelMap = this.getProductsInventoryLevel(barcodes, chainStore.getClient_id());	
	        
			//2.2 to make the new list with inventory level map
			for (String barcode : barcodes){
				ChainInventoryFlowOrderProduct orderProduct = productMap.get(barcode);
				Integer inventoryLevel = inventoryLevelMap.get(barcode);
				int inventoryLevel_int = 0;
				
				if (inventoryLevel != null)
					inventoryLevel_int = inventoryLevel.intValue();
				
				totalInventoryLevel += inventoryLevel_int;
				
				if (orderProduct == null){
					loggerLocal.error("在汇总数据时发生错误");
				} else {
					orderProduct.setInventoryQ(inventoryLevel_int);
					orderProduct.setQuantityDiff(orderProduct.getQuantity() - inventoryLevel_int);
					productList2.add(orderProduct);
				}
				
				
			}	 
			flowOrder.setTotalQuantityDiff(totalProductQ - totalInventoryLevel);
			flowOrder.setTotalInventoryQ(totalInventoryLevel);
		} else {
			//2.2 to make the new list
			for (String barcode : barcodes){
				ChainInventoryFlowOrderProduct orderProduct = productMap.get(barcode);
				if (orderProduct == null){
					loggerLocal.error("在汇总数据时发生错误");
				} else {
					productList2.add(orderProduct);
				}
			}
		}
		
		

		//3. set the new list
		flowOrder.setProductList(productList2);
		
	}



	/**
	 * to get the product ids
	 * @param order
	 * @return
	 */
	private List<Integer> getProductIds(ChainInventoryFlowOrder order){
		List<Integer> originalProducts =  new ArrayList<Integer>();
		if (order != null && order.getProductSet() != null)
		  for (ChainInventoryFlowOrderProduct orderProduct: order.getProductSet()){
			  if (orderProduct != null){
			     int id = orderProduct.getId();
			     if (id != 0)
			         originalProducts.add(id);
			  }
		  }
		
		return originalProducts;
	}

    /**
     * to parse the file from the inventory machine
     * @param inventory
     * @return
     */
	@SuppressWarnings("unchecked")
	private List<ChainInventoryFlowOrderProduct> parseInventoryFile(File inventoryFile) {
		List<ChainInventoryFlowOrderProduct> orderProducts = new ArrayList<ChainInventoryFlowOrderProduct>();

		if (inventoryFile != null){
			InventoryFileTemplate inventoryFileTemplate = new InventoryFileTemplate(inventoryFile);
			
			Response response = inventoryFileTemplate.process();
			List<Object> returnValues = (List<Object>)response.getReturnValue();
			Map<String, Integer> barcodeMap = (Map<String, Integer>)returnValues.get(0);
			Set<String> barcodes = (Set<String>)returnValues.get(1);

			//2. prepare the data
			Map<String,ProductBarcode> products = productBarcodeDaoImpl.getProductMapByBarcode(barcodes);


			for (String barcode: barcodes){
				ProductBarcode product = products.get(barcode);
				
				if (product == null){
					loggerLocal.error("Could not find the " + barcode + " in product table");
					continue;
				}
				
				ChainInventoryFlowOrderProduct chainInventoryFlowOrderProduct = new ChainInventoryFlowOrderProduct();
				chainInventoryFlowOrderProduct.setProductBarcode(product);
				
				Integer quantityI = barcodeMap.get(product.getBarcode());
				int quantity = 0;
				if (quantityI != null)
					quantity = quantityI.intValue();

				chainInventoryFlowOrderProduct.setQuantity(quantity);
				
				orderProducts.add(chainInventoryFlowOrderProduct);
			}
		
		}
		
		return orderProducts;
	}

    /**
     * to parse the file from the inventory machine
     * @param inventory
     * @return
     */
	@SuppressWarnings("unchecked")
	private List<ChainInventoryFlowOrderProduct> parseInventoryFile(File inventoryFile, int chainId) {
		List<ChainInventoryFlowOrderProduct> orderProducts = new ArrayList<ChainInventoryFlowOrderProduct>();

		if (inventoryFile != null){
			InventoryFileTemplate inventoryFileTemplate = new InventoryFileTemplate(inventoryFile);
			
			Response response = inventoryFileTemplate.process();
			List<Object> returnValues = (List<Object>)response.getReturnValue();
			Map<String, Integer> barcodeMap = (Map<String, Integer>)returnValues.get(0);
			Set<String> barcodes = (Set<String>)returnValues.get(1);
			
			ChainStore chain = chainStoreService.getChainStoreByID(chainId);
			
			//2. prepare the data
			Map<String,ProductBarcode> products = productBarcodeDaoImpl.getProductMapByBarcode(barcodes);

			//3. to get the inventory 
			Map<String, Integer> inventoryMap = this.getProductsInventoryLevel(barcodes, chain.getClient_id());

			for (String barcode: barcodes){
				ProductBarcode product = products.get(barcode);
				
				if (product == null){
					loggerLocal.error("Could not find the " + barcode + " in product table");
					continue;
				}
				
				ChainInventoryFlowOrderProduct chainInventoryFlowOrderProduct = new ChainInventoryFlowOrderProduct();
				chainInventoryFlowOrderProduct.setProductBarcode(product);
				
				Integer quantityI = barcodeMap.get(product.getBarcode());
				int quantity = 0;
				if (quantityI != null)
					quantity = quantityI.intValue();
				
				Integer inventoryI = inventoryMap.get(product.getBarcode());
				int inventory = 0;
				if (inventoryI != null)
					inventory = inventoryI.intValue();
				
				chainInventoryFlowOrderProduct.setQuantity(quantity);
				chainInventoryFlowOrderProduct.setInventoryQ(inventory);
				chainInventoryFlowOrderProduct.setTotalSalesPrice(quantity * product.getProduct().getSalesPrice());
				chainInventoryFlowOrderProduct.setQuantityDiff(quantity - inventory);
				
				orderProducts.add(chainInventoryFlowOrderProduct);
			}
		
		}
		
		return orderProducts;
	}
	
	/**
	 * get the inventory level
	 * the initial stock is put in 
	 * @return
	 */
	public int getInventoryLevel(String barcode, int clientId){
		int inventoryFlowLevel = chainInOutStockDaoImpl.getProductStock(barcode, clientId, true);

		return inventoryFlowLevel;
	}
	
	public Map<String, Integer> getProductsInventoryLevel(Set<String> barcodes, int clientId) {
		Map<String, Integer> inventoryFlowMap = new HashMap<String, Integer>();
		
		if (barcodes != null && barcodes.size() >0)
			inventoryFlowMap = chainInOutStockDaoImpl.getProductsInventoryLevel(barcodes, clientId);
		
		//Map<String, Integer> initialInventoryMap = chainInitialStockDaoImpl.getProductsInventoryLevel(barcodes, clientId);
		
//		Map<String, Integer> stockMap = new HashMap<String, Integer>();
//		for (String barcode : barcodes){
//			int inventoryFlowLevel = 0;
//			int initialInventory = 0;
//			Integer inventoryFlowLevel_obj = inventoryFlowMap.get(barcode);
//			Integer initialInventory_obj = initialInventoryMap.get(barcode);
//			
//			if (inventoryFlowLevel_obj != null)
//				inventoryFlowLevel = inventoryFlowLevel_obj.intValue();
//			
//			if (initialInventory_obj != null)
//				initialInventory = initialInventory_obj.intValue();
//			
//			stockMap.put(barcode, inventoryFlowLevel + initialInventory);
//		
//	     }
		
		return inventoryFlowMap;
	}


    /**
     * 获取条形码，然后生成一个inputsteam
     * @return
     */
	public InputStream genBarcodeFile() {
		//1. 准备前四年的年份
		Date today = new Date();
		int thisYear = 1900 + today.getYear();
		
		//2. 新增一年
		thisYear++;
		
		List<String> years = new ArrayList<String>();
		for (int i = 0; i <5; i++)
			years.add(String.valueOf(thisYear - i));
		
		//2. 获取前四年的year
		List<Year> years2 = yearDaoImpl.getYear(years);
		
		List<ProductBarcode> productBarcodes = productBarcodeDaoImpl.getBarcodeByYear(years2);
		StringBuffer barcodeBuffer = new StringBuffer();
		
		String barcode = "";
		String productCode = "";
		String brandName = "";
		Color color = null;
		String colorName = "";
		for (ProductBarcode productBarcode: productBarcodes){
			barcode = productBarcode.getBarcode();
			productCode = productBarcode.getProduct().getProductCode();
			brandName = productBarcode.getProduct().getBrand().getBrand_Name();
			color = productBarcode.getColor();
			if (color != null)
				colorName = color.getName();
			
			barcodeBuffer.append(barcode + "," + productCode + "," + brandName + "," + colorName + "," + "\n" );
			//barcodeBuffer.append(productBarcode.getId() + ",1"+ "\n" );
		}
		
		ByteArrayInputStream is = new ByteArrayInputStream(barcodeBuffer.toString().getBytes());  
		return is;  
	}
	

	/**
	 * 获取连锁店某个货品的库存跟踪
	 * @param chainId
	 * @param barcode
	 * @return
	 */
	public Response getInventoryTraceInfor(int chainId, String barcode) {
		Response response = new Response();
		Map data = new HashMap<String, List>();
		List<ChainInvenTraceInfoVO> traceVOs = new ArrayList<ChainInvenTraceInfoVO>();
		List<ChainInvenTraceInfoVO> footers = new ArrayList<ChainInvenTraceInfoVO>();
		
		if (barcode != null && !barcode.equals("")){
            ChainStore chainStore = chainStoreService.getChainStoreByID(chainId);
            if (chainStore != null) {
            	int clientId = chainStore.getClient_id();
            	DetachedCriteria criteria1 = DetachedCriteria.forClass(ChainInOutStockArchive.class);		
            	criteria1.add(Restrictions.eq("clientId", clientId));
        		criteria1.add(Restrictions.eq("barcode", barcode));
        		criteria1.addOrder(Order.asc("date"));	
				List<ChainInOutStockArchive> stocks2 = chainInOutStockArchiveDaoImpl.getByCritera(criteria1, true);
            	
				DetachedCriteria criteria = DetachedCriteria.forClass(ChainInOutStock.class);
				criteria.add(Restrictions.eq("clientId", clientId));
				criteria.add(Restrictions.eq("barcode", barcode));
				criteria.addOrder(Order.asc("date"));	
				List<ChainInOutStock> stocks = chainInOutStockDaoImpl.getByCritera(criteria, true);
				
				for (ChainInOutStockArchive stock : stocks2){
					ChainInvenTraceInfoVO traceVO = new ChainInvenTraceInfoVO(stock);
					traceVOs.add(traceVO);
				}
				
				boolean skip = false;
				if (stocks2.size() > 0)
					skip = true;
				
				for (ChainInOutStock stock : stocks){
					if ((stock.getType() == ChainInOutStock.TYPE_ARCHIVING || stock.getOrderId().startsWith(ChainInOutStock.AUTO_BAR_ACCT)) && skip)
						continue;
					ChainInvenTraceInfoVO traceVO = new ChainInvenTraceInfoVO(stock);
					traceVOs.add(traceVO);
				}
				
				//建设foot
				ChainInvenTraceInfoVO foot = new ChainInvenTraceInfoVO();
				int stockInventory = 0;
				for (ChainInvenTraceInfoVO ele : traceVOs){
					stockInventory += ele.getQuantity();
				}
				foot.setDescp("当前库存");
				foot.setQuantity(stockInventory);
				footers.add(foot);
	
				response.setReturnCode(Response.SUCCESS);
            }
		} else {
			response.setReturnCode(Response.FAIL);
		}

		data.put("rows", traceVOs);
		data.put("footer", footers);
		response.setReturnValue(data);
		
		return response;
	}

	/**
	 * 获取某个货品的各个连锁店库存
	 * @param barcode
	 * @return
	 */
	public Response getChainInventory(String barcode) {
		Response response = new Response();
		Map data = new HashMap<String, List>();
		List<ChainInventoryVO> chainInventoryVOs = new ArrayList<ChainInventoryVO>();
		int total = 0;
		try {
			if (barcode != null && !barcode.trim().equals("")){
				DetachedCriteria criteria = DetachedCriteria.forClass(ChainInOutStock.class);
				criteria.add(Restrictions.eq("barcode", barcode));
				
				ProjectionList projList = Projections.projectionList();
				projList.add(Projections.groupProperty("clientId"));
				projList.add(Projections.sum("quantity"));
				criteria.setProjection(projList);
				
				Map<Integer, Integer> inventoryMap = new HashMap<Integer, Integer>();
				
				List<Object> result = chainInOutStockDaoImpl.getByCriteriaProjection(criteria,  false);
				for (int i = 0; i < result.size(); i++){
					  Object object = result.get(i);
					  if (object != null){
						 Object[] recordResult = (Object[])object;
						 int clientId = Common_util.getInt(recordResult[0]);
						 int quantity = Common_util.getInt(recordResult[1]);
						 inventoryMap.put(clientId, quantity);
					  }
				}
				
				for (Integer clientId : inventoryMap.keySet()){
					ChainStore chainStore = chainStoreDaoImpl.getByClientId(clientId);
					if (chainStore != null){
						Integer inventory = inventoryMap.get(clientId);
						if (inventory == null || inventory == 0)
							continue;
						total += inventory;
						ChainInventoryVO chainInventoryVO = new ChainInventoryVO(chainStore.getChain_name(), inventory);
						
						chainInventoryVOs.add(chainInventoryVO);
					}
				}
			}
	
		} catch (Exception e) {
			response.setFail(e.getMessage());
		}
		
		data.put("rows", chainInventoryVOs);
		List<ChainInventoryVO> footers = new ArrayList<ChainInventoryVO>();
		ChainInventoryVO footer = new ChainInventoryVO("总计", total);
		footers.add(footer);
		data.put("footer", footers);
	
		response.setReturnValue(data);
		return response;
	}


	@Transactional
	public Response downloadFlowOrder(int id, ChainUserInfor loginUser) throws Exception {
		Response response = new Response();
		List<Object> values = new ArrayList<Object>();
		
		String webInf = this.getClass().getClassLoader().getResource("").getPath();
		String contextPath = webInf.substring(1, webInf.indexOf("classes")).replaceAll("%20", " ");  

		ChainInventoryFlowOrder order = getOrderById(id, loginUser);
		if (order.getType() ==  ChainInventoryFlowOrder.INVENTORY_TRANSFER_ORDER){
			formatTransferOrderComment(order);
			chainInventoryFlowOrderDaoImpl.evict(order);
		}
		
		boolean showCost = loginUser.containFunction("purchaseAction!seeCost");

		ByteArrayInputStream byteArrayInputStream;   
		HSSFWorkbook wb = null;   
		ChainInventoryFlowOrderTemplate flowOrderTemplate = new ChainInventoryFlowOrderTemplate(order, contextPath + "template\\", showCost);
		wb = flowOrderTemplate.process();
	
		ByteArrayOutputStream os = new ByteArrayOutputStream();   
		wb.write(os);   
  
		byte[] content = os.toByteArray();   
		byteArrayInputStream = new ByteArrayInputStream(content);  
		
		values.add(byteArrayInputStream);
		values.add(order.getTypeChainS());
		response.setReturnValue(values);   

		return response;
	}

	/**
	 * 清空某个连锁店的库存
	 * @param chainId
	 * @return
	 */

	public Response deleteInventory(ChainUserInfor userInfor, int chainId, int yearId, int quarterId, int brandId) {
		Response response = new Response();
		if (ChainUserInforService.isMgmtFromHQ(userInfor)){
			ChainStore chainStore = chainStoreDaoImpl.get(chainId, true);
			if (chainStore != null) {
			  int clientId = chainStore.getClient_id();
			  StringBuffer deleteInventory = new StringBuffer("DELETE FROM ChainInOutStock AS his WHERE his.clientId = " + clientId);
			  
			  if (yearId != 0 || quarterId != 0 || brandId != 0){
				  if (yearId != 0){
					  deleteInventory.append(" AND his.productBarcode.id IN (SELECT pb.id FROM ProductBarcode AS pb WHERE pb.product.year.year_ID=" + yearId);
				  }
				  if (quarterId != 0){
					  deleteInventory.append(" AND pb.product.quarter.quarter_ID=" + quarterId);
				  }
				  if (brandId != 0){
					  deleteInventory.append(" AND pb.product.brand.brand_ID=" + brandId);
				  }
				  
				  deleteInventory.append(")");
			  }
			  
			  int rowCount = chainInOutStockDaoImpl.executeHQLUpdateDelete(deleteInventory.toString(), null, true);

			  response.setSuccess(rowCount + " 行数据已经删除");
			} else {
				response.setFail("无法找到连锁店");
			}
		} else {
			response.setFail("非总部管理人员无法清空库存");
		}
		return response;
	}


	public Response getChainInventory(int parentId, int chainId, int yearId, int quarterId, int brandId,ChainUserInfor userInfor, boolean skipZero) {
		Response response = new Response();
		List<ChainInventoryItemVO> chainInventoryVOs = new ArrayList<ChainInventoryItemVO>();
		
		String whereClause = " his.productBarcode.product.category.category_ID!=" + SystemParm.getQXCategory();
		ChainStore store = null;
		
		if (chainId == Common_util.ALL_RECORD){
			store = new ChainStore();
			store.setChain_name("所有连锁店");
		} else {
			store = chainStoreDaoImpl.get(chainId, true);
			if (store == null){
				response.setFail("无法找到连锁店");
				return response;
			}
			
			int clientId = store.getClient_id();
			
			whereClause += " AND his.clientId ="+clientId;
		}
		
		boolean showCost = userInfor.containFunction("purchaseAction!seeCost");

		if (parentId == 0){
			//@2. 展开所有年份的库存信息
			String hql = "SELECT SUM(costTotal),SUM(chainSalePriceTotal), SUM(quantity) FROM ChainInOutStock AS his WHERE " +  whereClause;
			
			List<Object> inventoryData = chainInOutStockDaoImpl.executeHQLSelect(hql, null, null, true);
			
		    if (inventoryData != null){
				for (Object object: inventoryData){
						Object[] object2 = (Object[])object;
						double costTotal = Common_util.getDouble(object2[0]);
						double retailTotal = Common_util.getDouble(object2[1]);
						int quantity = Common_util.getInt(object2[2]);
						
						ChainInventoryItemVO headqInventoryVO = new ChainInventoryItemVO(store.getChain_name(), quantity, costTotal, retailTotal, ChainInventoryItemVO.STATE_CLOSED, 1,chainId, yearId, quarterId, brandId, 0,showCost);
						chainInventoryVOs.add(headqInventoryVO);
				}
		    }
		} else if (yearId == 0){
			//@2. 展开所有年份的库存信息
			String hql = "SELECT his.productBarcode.product.year.year_ID, SUM(costTotal),SUM(chainSalePriceTotal), SUM(quantity) FROM ChainInOutStock AS his WHERE " +  whereClause +" GROUP BY his.productBarcode.product.year.year_ID ";
			
			List<Object> inventoryData = chainInOutStockDaoImpl.executeHQLSelect(hql, null, null, true);
			
		    if (inventoryData != null){
				for (Object object: inventoryData){
						Object[] object2 = (Object[])object;
						int yearIdDB = Common_util.getInt(object2[0]);
						double costTotal = Common_util.getDouble(object2[1]);
						double retailTotal = Common_util.getDouble(object2[2]);
						int quantity = Common_util.getInt(object2[3]);
						
						Year year = yearDaoImpl.get(yearIdDB, true);
						
						ChainInventoryItemVO headqInventoryVO = new ChainInventoryItemVO(year.getYear() + "年", quantity, costTotal, retailTotal, ChainInventoryItemVO.STATE_CLOSED, parentId,chainId, yearIdDB, quarterId, brandId,0, showCost);
						chainInventoryVOs.add(headqInventoryVO);
				}
		    }
		} else if (quarterId == 0){
			//@2. 展开所有季的库存信息
			String hql = "SELECT his.productBarcode.product.quarter.quarter_ID, SUM(costTotal), SUM(chainSalePriceTotal), SUM(quantity) FROM ChainInOutStock AS his WHERE " +  whereClause +" AND his.productBarcode.product.year.year_ID=? GROUP BY his.productBarcode.product.quarter.quarter_ID";
			Object[] values = { yearId};
			
			List<Object> inventoryData = chainInOutStockDaoImpl.executeHQLSelect(hql, values, null, true);
			
		    if (inventoryData != null){
				for (Object object: inventoryData){
						Object[] object2 = (Object[])object;
						int quarterIdDB = Common_util.getInt(object2[0]);
						double costTotal = Common_util.getDouble(object2[1]);
						double retailTotal = Common_util.getDouble(object2[2]);
						int quantity = Common_util.getInt(object2[3]);
						
						Year year = yearDaoImpl.get(yearId, true);
						Quarter quarter = quarterDaoImpl.get(quarterIdDB, true);
						
						String name = year.getYear() + "年" + quarter.getQuarter_Name();
						
						ChainInventoryItemVO headqInventoryVO = new ChainInventoryItemVO(name, quantity, costTotal, retailTotal, ChainInventoryItemVO.STATE_CLOSED, parentId, chainId, yearId, quarterIdDB, brandId,0, showCost);
						chainInventoryVOs.add(headqInventoryVO);
				}
		    }
		} else if (brandId == 0){
			//@2. 展开所有品霞的库存信息
			String hql = "SELECT his.productBarcode.product.brand.brand_ID, SUM(costTotal), SUM(chainSalePriceTotal), SUM(quantity) FROM ChainInOutStock AS his WHERE " +  whereClause +" AND his.productBarcode.product.year.year_ID=? AND his.productBarcode.product.quarter.quarter_ID=? GROUP BY his.productBarcode.product.brand.brand_ID";
			Object[] values = {yearId, quarterId};
			
			List<Object> inventoryData = chainInOutStockDaoImpl.executeHQLSelect(hql, values, null, true);
			
		    if (inventoryData != null){
				for (Object object: inventoryData){
						Object[] object2 = (Object[])object;
						int brandIdDB = Common_util.getInt(object2[0]);
						double costTotal = Common_util.getDouble(object2[1]);
						double retailTotal = Common_util.getDouble(object2[2]);
						int quantity = Common_util.getInt(object2[3]);
					
						Brand brand = brandDaoImpl.get(brandIdDB, true);
						boolean isChain = false;
						if (brand.getChainStore() != null && brand.getChainStore().getChain_id() !=0)
							isChain = true;
						
						String name = "";
						String pinyin = brand.getPinyin();
						if (!StringUtils.isEmpty(pinyin)){
							name = pinyin.substring(0, 1) + " ";
						}

						 name += brand.getBrand_Name();
						 
						 if (ChainUserInforService.isMgmtFromHQ(userInfor))
							 name +=  " "+ brand.getComment();
						
						ChainInventoryItemVO headqInventoryVO = new ChainInventoryItemVO(name, quantity, costTotal, retailTotal, ChainInventoryItemVO.STATE_CLOSED,parentId,  chainId, yearId, quarterId, brandIdDB,0, showCost);
						headqInventoryVO.setIsChain(isChain);
						chainInventoryVOs.add(headqInventoryVO);
				}
		    }
		} else if (brandId != 0) {
			//@2. 展开当前品霞的库存信息
			String hql = "SELECT his.productBarcode.id, SUM(costTotal), SUM(chainSalePriceTotal), SUM(quantity) FROM ChainInOutStock AS his WHERE " +  whereClause +" AND his.productBarcode.product.year.year_ID=? AND his.productBarcode.product.quarter.quarter_ID=? AND his.productBarcode.product.brand.brand_ID=? GROUP BY his.productBarcode.id";
			Object[] values = { yearId, quarterId, brandId};
			
			List<Object> inventoryData = chainInOutStockDaoImpl.executeHQLSelect(hql, values, null, true);
			
		    if (inventoryData != null){
				for (Object object: inventoryData){
						Object[] object2 = (Object[])object;
						int pbId = Common_util.getInt(object2[0]);
						double costTotal = Common_util.getDouble(object2[1]);
						double retailTotal = Common_util.getDouble(object2[2]);
						int quantity = Common_util.getInt(object2[3]);
						
						if (quantity == 0 && skipZero)
							continue;
						
						ProductBarcode pb = productBarcodeDaoImpl.get(pbId, true);
						Color color = pb.getColor();
						String colorName = "";
						if (color != null)
							colorName = color.getName();
						
						String barcode = pb.getBarcode();
						Category category = pb.getProduct().getCategory();
						
						String name = Common_util.cutProductCode(pb.getProduct().getProductCode()) + colorName  + " " +  category.getCategory_Name();
						
						
						boolean isChain = false;
						if (pb.getChainStore() != null && pb.getChainStore().getChain_id() !=0)
							isChain = true;
						
						ChainInventoryItemVO headqInventoryVO = new ChainInventoryItemVO(name, quantity, costTotal, retailTotal, ChainInventoryItemVO.STATE_OPEN, parentId,chainId, yearId, quarterId, brandId,pbId, showCost);
						headqInventoryVO.setBarcode(barcode);
						headqInventoryVO.setIsChain(isChain);
						chainInventoryVOs.add(headqInventoryVO);
				}
		    }
		}
		Collections.sort(chainInventoryVOs, new ChainStatisticReportItemVOSorter());
		response.setReturnValue(chainInventoryVOs);
		return response;
	}

	/**
	 * 获取连锁店库存excel报表的java对象
	 * 1. 某年的库存
	 * 2. 某个季度库存
	 * 3. 某个品牌
	 * @return
	 * @throws IOException 
	 */
	@Transactional
	public Response generateChainInventoryExcelReport(int chainId, int year, int quarter, int brandId, ChainUserInfor userInfor, String templateFilePath) throws IOException{
		Response response = new Response();
		
		/**
		 * @1. 准备数据库数据
		 */
		List<ChainInventoryRptItem> reportItems = new ArrayList<ChainInventoryRptItem>();
		
		DetachedCriteria criteria = DetachedCriteria.forClass(ChainInOutStock.class);
		ChainStore chain = null;
		if (chainId != Common_util.ALL_RECORD){
			chain = chainStoreDaoImpl.get(chainId, true);
			int clientId = chain.getClient_id();
			criteria.add(Restrictions.eq("clientId", clientId));
		} else {
			chain = chainStoreDaoImpl.getAllChainStoreObject();
		}
			
		
		ProjectionList projList = Projections.projectionList();
		projList.add(Projections.groupProperty("productBarcode.id"));
		projList.add(Projections.sum("quantity"));
		projList.add(Projections.sum("costTotal"));
		projList.add(Projections.sum("chainSalePriceTotal"));
		criteria.setProjection(projList);
		
		DetachedCriteria productBarcodeCriteria = criteria.createCriteria("productBarcode");
		DetachedCriteria productCriteria = productBarcodeCriteria.createCriteria("product");		
		
		if (brandId != 0) { 
				DetachedCriteria brandCriteria = productCriteria.createCriteria("brand");		
				brandCriteria.add(Restrictions.eq("brand_ID", brandId));
		}
		if (quarter != 0){
				DetachedCriteria quarterCriteria = productCriteria.createCriteria("quarter");	
				quarterCriteria.add(Restrictions.eq("quarter_ID", quarter));
		}
		if (year != 0){
				DetachedCriteria yearCriteria = productCriteria.createCriteria("year");
				yearCriteria.add(Restrictions.eq("year_ID", year));
		}

		List<Object> result = chainInOutStockDaoImpl.getByCriteriaProjection(criteria,  false);
		for (int i = 0; i < result.size(); i++){
			  Object object = result.get(i);
			  if (object != null){
				 ChainInventoryRptItem item = new ChainInventoryRptItem();
				  
				 Object[] recordResult = (Object[])object;
				 int productId = Common_util.getInt(recordResult[0]);
				 int quantity = Common_util.getInt(recordResult[1]);
				 double cost = Common_util.getDouble(recordResult[2]);
				 double salesRevenue = Common_util.getDouble(recordResult[3]);
				 
				 if (quantity == 0)
					 continue;
				 item.setTotalQuantity(quantity);
				 item.setTotalWholeSalesAmt(cost);
				 item.setTotalRetailSalesAmt(salesRevenue);
				 
				 ProductBarcode productBarcode = productBarcodeDaoImpl.get(productId, true);

				 item.setProductBarcode(productBarcode);
				 
				 reportItems.add(item);
			  }
		}
		
		Collections.sort(reportItems, new ChainInventoryReportSort());
		/**
		 * @2. 准备excel报表
		 */
		boolean showCost = false;
		if (ChainUserInforService.isMgmtFromHQ(userInfor) || userInfor.containFunction("purchaseAction!seeCost"))
			showCost = true;
		
		ChainInventoryReportTemplate chainInventoryReportTemplate = new ChainInventoryReportTemplate(reportItems, chain, templateFilePath, showCost);
		HSSFWorkbook wb = chainInventoryReportTemplate.process();
		
		ByteArrayInputStream byteArrayInputStream = ExcelUtil.convertExcelToInputStream(wb);
		
		response.setReturnValue(byteArrayInputStream);
		response.setReturnCode(Response.SUCCESS);
		return response;
	}

}
