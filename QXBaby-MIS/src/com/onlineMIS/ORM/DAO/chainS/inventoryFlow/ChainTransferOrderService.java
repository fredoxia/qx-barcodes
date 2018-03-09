package com.onlineMIS.ORM.DAO.chainS.inventoryFlow;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.onlineMIS.ORM.DAO.Response;
import com.onlineMIS.ORM.DAO.chainS.chainMgmt.ChainSalesPriceDaoImpl;
import com.onlineMIS.ORM.DAO.chainS.user.ChainStoreDaoImpl;
import com.onlineMIS.ORM.DAO.chainS.user.ChainUserInforDaoImpl;
import com.onlineMIS.ORM.DAO.chainS.user.ChainUserInforService;
import com.onlineMIS.ORM.DAO.headQ.barCodeGentor.ProductBarcodeDaoImpl;
import com.onlineMIS.ORM.DAO.headQ.barCodeGentor.ProductBarcodeService;
import com.onlineMIS.ORM.DAO.headQ.barCodeGentor.ProductDaoImpl;
import com.onlineMIS.ORM.entity.base.Pager;
import com.onlineMIS.ORM.entity.chainS.inventoryFlow.ChainInOutStock;
import com.onlineMIS.ORM.entity.chainS.inventoryFlow.ChainInventoryFlowOrder;
import com.onlineMIS.ORM.entity.chainS.inventoryFlow.ChainInventoryFlowOrderProduct;
import com.onlineMIS.ORM.entity.chainS.inventoryFlow.ChainTransferLog;
import com.onlineMIS.ORM.entity.chainS.inventoryFlow.ChainTransferOrder;
import com.onlineMIS.ORM.entity.chainS.inventoryFlow.ChainTransferOrderFlowAcct;
import com.onlineMIS.ORM.entity.chainS.inventoryFlow.ChainTransferOrderProduct;
import com.onlineMIS.ORM.entity.chainS.inventoryFlow.ChainTransferOrderVO;
import com.onlineMIS.ORM.entity.chainS.user.ChainRoleType;
import com.onlineMIS.ORM.entity.chainS.user.ChainStore;
import com.onlineMIS.ORM.entity.chainS.user.ChainUserInfor;
import com.onlineMIS.ORM.entity.headQ.barcodeGentor.Product;
import com.onlineMIS.ORM.entity.headQ.barcodeGentor.ProductBarcode;
import com.onlineMIS.ORM.entity.headQ.user.UserInfor;
import com.onlineMIS.action.chainS.chainTransfer.ChainTransferFormBean;
import com.onlineMIS.action.chainS.chainTransfer.ChainTransferUIBean;
import com.onlineMIS.action.chainS.vo.ChainProductBarcodeVO;
import com.onlineMIS.common.Common_util;

@Service
public class ChainTransferOrderService {
	
	@Autowired
	private ChainTransferFlowAcctDaoImpl chainTransferFlowAcctDaoImpl;
	
	@Autowired
	private ChainTransferOrderDaoImpl chainTransferOrderDaoImpl;
	
	@Autowired
	private ChainTransferOrderProductDaoImpl chainTransferOrderProductDaoImpl;
	
	@Autowired
	private ChainTransferLogDaoImpl chainTransferLogDaoImpl;
	
	@Autowired
	private ChainStoreDaoImpl chainStoreDaoImpl;
	
	@Autowired
	private ProductBarcodeService productBarcodeService;
	
	@Autowired
	private ChainSalesPriceDaoImpl chainSalesPriceDaoImpl;
	
	@Autowired
	private ProductBarcodeDaoImpl productBarcodeDaoImpl;
	
	@Autowired
	private ChainInOutStockDaoImpl chainInOutStockDaoImpl;


	/**
	 * 准备 创建调货单的页面
	 * @param loginUser
	 * @param formBean
	 * @param uiBean
	 */
	public void prepareCreateTransferOrderPage(ChainUserInfor loginUser, ChainTransferFormBean formBean, ChainTransferUIBean uiBean){
		ChainTransferOrder transferOrder = formBean.getTransferOrder();
		
		transferOrder.setCreator(loginUser.getName());
		transferOrder.setOrderDate(Common_util.getToday());
		
		calculateOrderActionParm(formBean, transferOrder, loginUser);
	}

	/**
	 * 获取from chainStore list
	 * @param userInfor
	 * @param pager
	 * @return
	 */
	public Response getFromChainStoreList(ChainUserInfor userInfor, Pager pager) {
		Response response = new Response();
		List<ChainStore> chainStores = new ArrayList<ChainStore>(); 

		
		if (ChainUserInforService.isMgmtFromHQ(userInfor)){
			boolean cache = false;
			
			//1. check the pager
			if (pager.getTotalResult() == 0){
				DetachedCriteria criteria = buildChainStoreCriteria();
				criteria.setProjection(Projections.rowCount());
				int totalRecord = Common_util.getProjectionSingleValue(chainStoreDaoImpl.getByCriteriaProjection(criteria, false));
				pager.initialize(totalRecord);
			} else {
				pager.calFirstResult();
				cache = true;
			}
			
			//2. 获取连锁店列表
			DetachedCriteria searchCriteria = buildChainStoreCriteria();
			searchCriteria.addOrder(Order.asc("pinYin"));
			chainStores = chainStoreDaoImpl.getByCritera(searchCriteria, pager.getFirstResult(), pager.getRecordPerPage(), cache);
			
			//3. 添加非连连锁店选项
//			if (pager.getCurrentPage() == Pager.FIRST_PAGE){
//			    ChainStore dummyStore = ChainStoreDaoImpl.getOutsideStore2();
//			    chainStores.add(0, dummyStore);
//			}
			
			response.setReturnValue(chainStores);
			response.setReturnCode(Response.SUCCESS);
			
			return response;
		} else {
			int chainId = userInfor.getMyChainStore().getChain_id();
			ChainStore chainStore = chainStoreDaoImpl.get(chainId, true);
			
//		    ChainStore dummyStore = ChainStoreDaoImpl.getOutsideStore2();
//		    chainStores.add(dummyStore);
		    
		    chainStores.add(chainStore);
			
			response.setReturnValue(chainStores);
			response.setReturnCode(Response.WARNING);
		    return response;
		}
	}
	
	/**
	 * 获取to chainStore list
	 * @param userInfor
	 * @param pager
	 * @return
	 */
	public Response getToChainStoreList(ChainUserInfor userInfor, int fromChainId,
			Pager pager) {
		Response response = new Response();
		List<ChainStore> chainStores = new ArrayList<ChainStore>(); 

		if (fromChainId == 0){
			response.setReturnValue(chainStores);
			response.setReturnCode(Response.SUCCESS);
			return response;
		} else {
//			if (fromChainId == ChainStoreDaoImpl.OUTSIDE_STORE_ID){
//				if (ChainUserInforService.isMgmtFromHQ(userInfor)){
//					boolean cache = false;
//					
//					//1. check the pager
//					if (pager.getTotalResult() == 0){
//						DetachedCriteria criteria = buildChainStoreCriteria();
//						criteria.setProjection(Projections.rowCount());
//						int totalRecord = Common_util.getProjectionSingleValue(chainStoreDaoImpl.getByCriteriaProjection(criteria, false));
//						pager.initialize(totalRecord);
//					} else {
//						pager.calFirstResult();
//						cache = true;
//					}
//					
//					//2. 获取连锁店列表
//					DetachedCriteria searchCriteria = buildChainStoreCriteria();
//					searchCriteria.addOrder(Order.asc("pinYin"));
//					chainStores = chainStoreDaoImpl.getByCritera(searchCriteria, pager.getFirstResult(), pager.getRecordPerPage(), cache);
//										
//					response.setReturnValue(chainStores);
//					response.setReturnCode(Response.SUCCESS);
//					
//					return response;
//				} else {
//					int chainId = userInfor.getMyChainStore().getChain_id();
//					ChainStore chainStore = chainStoreDaoImpl.get(chainId, true);
//				    chainStores.add(chainStore);
//				
//					response.setReturnValue(chainStores);
//					response.setReturnCode(Response.WARNING);
//				    return response;
//				}
//			} else {
				boolean cache = false;
				
				//1. check the pager
				if (pager.getTotalResult() == 0){
					DetachedCriteria criteria = buildOtherChainStoreCriteria(fromChainId, ChainUserInforService.isMgmtFromHQ(userInfor));
					criteria.setProjection(Projections.rowCount());
					int totalRecord = Common_util.getProjectionSingleValue(chainStoreDaoImpl.getByCriteriaProjection(criteria, false));
					pager.initialize(totalRecord);
				} else {
					pager.calFirstResult();
					cache = true;
				}
				
				//2. 获取连锁店列表
				DetachedCriteria searchCriteria = buildOtherChainStoreCriteria(fromChainId, ChainUserInforService.isMgmtFromHQ(userInfor));
				searchCriteria.addOrder(Order.asc("pinYin"));
				chainStores = chainStoreDaoImpl.getByCritera(searchCriteria, pager.getFirstResult(), pager.getRecordPerPage(), cache);
				
				//3. 添加非连连锁店选项
//				if (pager.getCurrentPage() == Pager.FIRST_PAGE){
//				    ChainStore dummyStore = ChainStoreDaoImpl.getOutsideStore2();
//				    chainStores.add(0, dummyStore);
//				}
				
				response.setReturnValue(chainStores);
				response.setReturnCode(Response.SUCCESS);
				
				return response;
//			}
		}
		

	}
	
	/**
	 * 查找其他连锁店
	 * @param fromChainId
	 * @return
	 */
	private DetachedCriteria buildOtherChainStoreCriteria(int fromChainId, boolean isMgmt) {
		DetachedCriteria criteria = DetachedCriteria.forClass(ChainStore.class);
		criteria.add(Restrictions.ne("status", ChainStore.STATUS_DELETE));
		
		criteria.add(Restrictions.isNull("parentStore"));
		if (isMgmt){
			criteria.add(Restrictions.ne("chain_id", fromChainId));
		} else {
			criteria.add(Restrictions.and(Restrictions.ne("chain_id", fromChainId), Restrictions.ne("chain_id", ChainStore.CHAIN_ID_TEST_ID)));
		}
		
		return criteria;
	}

	/**
	 * this criteria 用来搜索连锁店
	 * @return
	 */
	private DetachedCriteria buildChainStoreCriteria() {
		DetachedCriteria criteria = DetachedCriteria.forClass(ChainStore.class);
		criteria.add(Restrictions.ne("status", ChainStore.STATUS_DELETE));
		criteria.add(Restrictions.isNull("parentStore"));
		
		return criteria;
	}

	/**
	 * 保存单据到 草稿状态
	 * @param transferOrder
	 * @param userInfor
	 * @return
	 */
	@Transactional
	public Response saveOrderToDraft(ChainTransferOrder transferOrder,
			ChainUserInfor userInfor) {
		Response response = new Response();
		//1. 验证单据可提交不
		int newStatus = ChainTransferOrder.STATUS_DRAFT;
		validateTransferOrder(transferOrder, userInfor, newStatus, response);
		if (!response.isSuccess())
			return response;
		
		//2.获取以前单据，复制以前一些信息
		int orderId = transferOrder.getId();
		initializeTransferOrderInformation(orderId, transferOrder, userInfor);
		
		//3. 生成audit log
		ChainTransferLog auditLog = new ChainTransferLog();
		auditLog.setOrderId(transferOrder.getId());
		auditLog.setLogTime(Common_util.getToday());
		auditLog.setLog(userInfor.getName() + " 保存草稿");
		
		//4. 保存单据
		transferOrder.setStatus(newStatus);
		this.reCalculate(transferOrder);
		saveTransferOrder(transferOrder,auditLog, null, response);
		
		response.setReturnValue(transferOrder.getId());
		
		return response;
	}
	
	/**
	 * 判断是否需要初始化一些单据信息
	 * @param orderId
	 * @param order
	 * @param userInfor
	 */
	private void initializeTransferOrderInformation(int orderId, ChainTransferOrder order, ChainUserInfor userInfor){
		if (orderId > 0){
			ChainTransferOrder originalOrder = chainTransferOrderDaoImpl.get(orderId, true);
			copyOrderInformation(originalOrder, order);
		} else {
			String creator = userInfor.getName();

			order.setCreator(creator);
			order.setOrderDate(Common_util.getToday());
		}
	}
	
	/**
	 * 持久化单据
	 * @param transferOrder
	 */
	private void saveTransferOrder(ChainTransferOrder transferOrder, ChainTransferLog auditLog, ChainTransferOrderFlowAcct flowAcct, Response response){
		//1. 处理productList 和 productSet
		transferOrder.putListToSet();
		transferOrder.setProductList(null);
		
		int orderId = transferOrder.getId();
		
		//2. 检查是否toChainStore
		ChainStore toChainStore = transferOrder.getToChainStore();
		if (toChainStore != null && toChainStore.getChain_id() == ChainStoreDaoImpl.OUTSIDE_STORE_ID)
			transferOrder.setToChainStore(null);
		
		//3. 检查是否fromChainStore
		ChainStore fromChainStore = transferOrder.getFromChainStore();
		if (fromChainStore != null && fromChainStore.getChain_id() == ChainStoreDaoImpl.OUTSIDE_STORE_ID)
			transferOrder.setFromChainStore(null);

		if (orderId != 0){	
			ChainTransferOrder originalOrder = getOrderById(orderId);
			 
			 List<Integer> newProducts =  getProductIds(transferOrder);
			 List<Integer> originalProducts = getProductIds(originalOrder);
			 List<Integer> deletedProducts = new ArrayList<Integer>();
			 for (int id: originalProducts){
				 if (!newProducts.contains(id))
					 deletedProducts.add(id);
			 }
			 chainTransferOrderProductDaoImpl.deleteProducts(orderId, deletedProducts);

			 chainTransferOrderDaoImpl.evict(originalOrder);
			 
			 chainTransferOrderDaoImpl.saveOrUpdate(transferOrder,false);
		} else {
			 chainTransferOrderDaoImpl.save(transferOrder, false);
		}
		
		//2. audit log
		if (auditLog != null){
			if (auditLog.getOrderId() == 0)
				auditLog.setOrderId(transferOrder.getId());
			chainTransferLogDaoImpl.save(auditLog, true);
		}
		
		//3. acct flow
		if (flowAcct != null)
			chainTransferFlowAcctDaoImpl.save(flowAcct, true);
	}
	
	/**
	 * to get the product ids
	 * @param order
	 * @return
	 */
	private List<Integer> getProductIds(ChainTransferOrder order){
		List<Integer> originalProducts =  new ArrayList<Integer>();
		if (order != null && order.getProductSet() != null)
		  for (ChainTransferOrderProduct orderProduct: order.getProductSet()){
			  if (orderProduct != null){
			     int id = orderProduct.getProductBarcode().getId();
			     if (id != 0)
			         originalProducts.add(id);
			  }
		  }
		
		return originalProducts;
	}


	/**
	 * 验证单据
	 * 1. 验证新的status能不能通过旧的status更新过来
	 * 2. 验证货品有没有问题
	 * @param transferOrder
	 * @param userInfor
	 * @param newStatus
	 * @return
	 */
	private void validateTransferOrder(ChainTransferOrder transferOrder,
			ChainUserInfor userInfor, int newStatus, Response response){
		int orderStatus = transferOrder.getStatus();
		
		//1. 验证状态
		switch (newStatus) {
			case ChainTransferOrder.STATUS_DRAFT:
				if (orderStatus == ChainTransferOrder.STATUS_COMPLETE){
					response.setFail("当前状态下,单据无法保存到草稿状态.请先让对方退回单据，再次操作");
				} else if (orderStatus == ChainTransferOrder.STATUS_CONFIRMED){
					response.setFail("完成的单据无法保存到草稿状态");
				}
				break;
			case ChainTransferOrder.STATUS_COMPLETE:
				if (orderStatus == ChainTransferOrder.STATUS_COMPLETE){
					response.setFail("当前状态下,单据无法再次提交.请先让对方退回单据，再次操作");
				} else if (orderStatus == ChainTransferOrder.STATUS_CONFIRMED){
					response.setFail("完成的单据无法重复提交");
				}
				break;
			case ChainTransferOrder.STATUS_REJECTED:
				if (orderStatus != ChainTransferOrder.STATUS_COMPLETE){
					response.setFail("当前状态下,单据无法退回");
				}
				break;
			case ChainTransferOrder.STATUS_CONFIRMED:
				if (orderStatus != ChainTransferOrder.STATUS_COMPLETE){
					response.setFail("当前状态下,单据无法完成确认操作");
				}
				break;
			case ChainTransferOrder.STATUS_DELETED:
				if (orderStatus == ChainTransferOrder.STATUS_CONFIRMED){
					response.setFail("当前状态下,单据无法删除");
				}
				break;				
			default:
				break;
		}
		
		if (!response.isSuccess())
			return;
		
		//2. 验证单据产品
		List<ChainTransferOrderProduct> products = transferOrder.getProductList();
		if (products.size() == 0){
			response.setFail("请输入货品后再提交单据");
			return;
		}

		for (ChainTransferOrderProduct product : products){
			if (product == null || product.getProductBarcode() == null || product.getProductBarcode().getId() == 0)
				continue;
			if (product.getQuantity() <= 0){
				response.setFail("货号" + product.getProductBarcode().getProduct().getProductCode() + " 的数量必须为>0的数字");
				return;
			}
			
			ProductBarcode pb = productBarcodeDaoImpl.get(product.getProductBarcode().getId(), true);
			if (pb.getChainStore() != null){
				response.setFail("货号" + product.getProductBarcode().getProduct().getProductCode() + " 不是总部货品,不能使用调货单");
				return;
			}
		}
	}

	/**
	 * 计算单据的总数量，总的批发价和总零售价
	 * @param transferOrder
	 */
	private void reCalculate(ChainTransferOrder transferOrder){
		List<ChainTransferOrderProduct> products = transferOrder.getProductList();
		int totalQ = 0;
		double totalWholePrice = 0;
		double totalSalesPrice = 0;
		double subWholePrice = 0;
		double subSalesPrice = 0;

		for (ChainTransferOrderProduct product : products){
			if (product == null || product.getProductBarcode() == null || product.getProductBarcode().getId() == 0)
				continue;
			int q = product.getQuantity();
			ProductBarcode pb = productBarcodeDaoImpl.get(product.getProductBarcode().getId(), true);
			
			double wholePrice = ProductBarcodeDaoImpl.getWholeSalePrice(pb);
			double salesPrice = pb.getProduct().getSalesPrice();
			
			product.setProductBarcode(pb);
			
			subWholePrice = wholePrice * q;
			subSalesPrice = salesPrice * q;
			
			totalWholePrice += subWholePrice;
			totalSalesPrice += subSalesPrice;
			totalQ += q;
			product.setTotalSalesPrice(subSalesPrice);
			product.setTotalWholeSalesPrice(subWholePrice);
			product.setWholeSalesPrice(wholePrice);
			product.setSalesPrice(salesPrice);
		}
		
		transferOrder.setTotalQuantity(totalQ);
		transferOrder.setTotalSalesPrice(totalSalesPrice);
		transferOrder.setTotalWholeSalesPrice(totalWholePrice);
	}
	
	private void copyOrderInformation(ChainTransferOrder oldOrder, ChainTransferOrder newOrder){
		newOrder.setConfirmedBy(oldOrder.getConfirmedBy());
		newOrder.setCreator(oldOrder.getCreator());
		newOrder.setOrderDate(oldOrder.getOrderDate());
	}

	/**
	 * 获取tranfer order detail
	 * 1. 获取订单信息
	 * 2. 获取订单修改记录
	 * @param userInfor
	 * @param id
	 * @return
	 */
	@Transactional
	public Response loadTransferOrder(ChainUserInfor userInfor, int orderId, ChainTransferFormBean formBean) {
		Response response = new Response();
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		ChainTransferOrder orderDetail = getOrderById(orderId);
		if (orderDetail == null){
			response.setFail("无法找到订单，请重新刷新再试");
			return response;
		} else {
			chainTransferOrderDaoImpl.evict(orderDetail);
			orderDetail.putSetToList();
			maskWholePrice(orderDetail, userInfor);
			
			List<ChainTransferLog> orderLogs = chainTransferLogDaoImpl.getOrderLogs(orderId);
			resultMap.put("order", orderDetail);
			resultMap.put("logs", orderLogs);
			
			formBean.setTransferOrder(orderDetail);
			calculateOrderActionParm(formBean, orderDetail, userInfor);
			
			response.setReturnValue(resultMap);
			response.setSuccess("");
			return response;
		}
	}
	
	/**
	 * 看看是否需要mask whole price
	 * @param transferOrder
	 * @param user
	 */
	private void maskWholePrice(ChainTransferOrder transferOrder, ChainUserInfor userInfor){
		if (!ChainUserInforService.isMgmtFromHQ(userInfor) && userInfor.getRoleType().getChainRoleTypeId() != ChainRoleType.CHAIN_OWNER){
			List<ChainTransferOrderProduct> products = transferOrder.getProductList();
			for (ChainTransferOrderProduct product : products){
				product.setWholeSalesPrice(0);
				product.setTotalWholeSalesPrice(0);
			}
			transferOrder.setTotalWholeSalesPrice(0);
		}
			
	}
	
	
	/**
	 * 获取某个单据信息
	 * @param orderId
	 * @return
	 */
	private ChainTransferOrder getOrderById(int orderId) {
		ChainTransferOrder transferOrder = chainTransferOrderDaoImpl.get(orderId, true);
		chainTransferOrderDaoImpl.initialize(transferOrder);
		return transferOrder;
	}

	/**
	 * 准备搜索的界面
	 * @param userInfor
	 * @param formBean
	 * @return
	 */
	public void preSearchTransferOrder(ChainUserInfor userInfor,
			ChainTransferFormBean formBean, ChainTransferUIBean uiBean) {
		if (!ChainUserInforService.isMgmtFromHQ(userInfor)){
			int myChainStoreID = userInfor.getMyChainStore().getChain_id();
			ChainStore myChainStore = chainStoreDaoImpl.get(myChainStoreID, true);
			formBean.setChainStore(myChainStore);
		} else {
			ChainStore allChainStore = chainStoreDaoImpl.getAllChainStoreObject();
			formBean.setChainStore(allChainStore);
		}
		
		formBean.getTransferOrder().setStatus(Common_util.ALL_RECORD);
		
		ChainTransferOrder transferOrder = new ChainTransferOrder();
		uiBean.setOrderStatus(transferOrder.getStatusMap());
	}

	/**
	 * 查找调货单
	 * @param formBean
	 * @param page
	 * @param rows
	 * @param sort
	 * @param order
	 * @return
	 */
	@Transactional
	public Response searchTransferOrders(ChainTransferFormBean formBean,
			int page, int rowPerPage, String sort, String order, ChainUserInfor userInfor) {
		Response response = new Response();
		
		Date fromDate = Common_util.formStartDate(formBean.getSearchStartTime());
		Date toDate = Common_util.formEndDate(formBean.getSearchEndTime());
		ChainStore chainStore = formBean.getChainStore();
		int status = formBean.getTransferOrder().getStatus();
		Map data = new HashMap<String, Object>();
		
		//1. 获取计算总行数的critieria
		DetachedCriteria pageCriteria = buildSearchTransferOrderCriteria(fromDate, toDate, chainStore, status);
		pageCriteria.setProjection(Projections.rowCount());
		int totalRecord = Common_util.getProjectionSingleValue(chainTransferOrderDaoImpl.getByCriteriaProjection(pageCriteria, true));
		
		//2. 获取数据
		int startRow = Common_util.getFirstRecord(page, rowPerPage);
		DetachedCriteria searchCriteria = buildSearchTransferOrderCriteria(fromDate, toDate, chainStore, status);
		List<ChainTransferOrder> rows = chainTransferOrderDaoImpl.getByCritera(searchCriteria, startRow, rowPerPage, true);

		boolean displayWholePrice = false;
		if (ChainUserInforService.isMgmtFromHQ(userInfor) || userInfor.getRoleType().getChainRoleTypeId() == ChainRoleType.CHAIN_OWNER)
			displayWholePrice = true;
		
		List<ChainTransferOrderVO> orderVOs = new ArrayList<ChainTransferOrderVO>();
		for (ChainTransferOrder orderObj : rows){
			chainTransferOrderDaoImpl.initialize(orderObj);
			ChainTransferOrderVO orderVO = new ChainTransferOrderVO(orderObj, displayWholePrice);
			orderVOs.add(orderVO);
		}
		
		
		data.put("rows", orderVOs);
		data.put("total", totalRecord);
		
		response.setReturnValue(data);
		
		return response;
	}

	private DetachedCriteria buildSearchTransferOrderCriteria(Date fromDate, Date toDate,
			ChainStore chainStore, int status) {
		DetachedCriteria criteria = DetachedCriteria.forClass(ChainTransferOrder.class);
		
		criteria.add(Restrictions.between("orderDate", fromDate, toDate));
		
		if (chainStore.getChain_id() != Common_util.ALL_RECORD){
			criteria.add(Restrictions.or(Restrictions.eq("fromChainStore.chain_id", chainStore.getChain_id()), Restrictions.eq("toChainStore.chain_id", chainStore.getChain_id())));
		}
		
		if (status != Common_util.ALL_RECORD){
			criteria.add(Restrictions.eq("status", status));
		}
		
		return criteria;
	}
	
	/**
	 * 计算订单的action parameters在当前订单状态下什么样的action可以操作
	 * 比如 : 
	 * 1. edit
	 * 2. 提交出去
	 * 3. 退回单据
	 * 4. 确认单据
	 * 5. 当前用户能否红冲单据
	 * 6. 删除单据
	 * 
	//连锁店刚初始化订单  STATUS_INITIAL = 0;
	//连锁店提交的草稿单据 STATUS_DRAFT = 1;
	//连锁店提交了订单，但是对方还没有确认 STATUS_COMPLETE = 2;
	//对方退回订单 STATUS_REJECTED = 5;
	//连锁店确认了订单，完成  STATUS_CONFIRMED = 6;
	 * 
	 * @param formBean
	 */
	private void calculateOrderActionParm(ChainTransferFormBean formBean, ChainTransferOrder order, ChainUserInfor user){
		//1. 能否edit
		boolean canEdit = calculateOrderActionParm_canEdit(order, user);
		
		//2. 当前用户能否提交给对方
		boolean canPost = calculateOrderActionParm_canPost(order, user);
		
		//3. 当前用户能否退回单据
		boolean canReject = calculateOrderActionParm_canReject(order, user);
		
		//4. 当前用户能否确认单据
		boolean canConfirm = calculateOrderActionParm_canConfirm(order, user);
		
		//5. 当前用户能否红冲单据
		boolean canCancel = calculateOrderActionParm_canCancel(order, user);
		
		//6. 能否save draft
		boolean canDraft = calculateOrderActionParm_canDraft(order, user);
		
		//7. 当前用户能否delete 单据
		boolean canDelete = calculateOrderActionParm_canDelete(order, user);
		
		formBean.setCanCancel(canCancel);
		formBean.setCanConfirm(canConfirm);
		formBean.setCanDelete(canDelete);
		formBean.setCanEdit(canEdit);
		formBean.setCanReject(canReject);
		formBean.setCanSaveDraft(canDraft);
		formBean.setCanPost(canPost);
	}

	/**
	 * 1. 当前用户是来至fromStore 或者 admin
	 * 2. 订单状态是 draft, complete, reject 状态
	 * @param order
	 * @param user
	 * @return
	 */
	private boolean calculateOrderActionParm_canDelete(
			ChainTransferOrder order, ChainUserInfor user) {
		ChainStore fromStore = order.getFromChainStore();
		ChainStore toStore = order.getToChainStore();
		ChainStore myStore = user.getMyChainStore();
		int orderStatus = order.getStatus();
		
		if (fromStore == null || toStore == null)
			return false;
		
		if ((ChainUserInforService.isMgmtFromHQ(user) || fromStore.getChain_id() == myStore.getChain_id()) && (orderStatus == ChainTransferOrder.STATUS_DRAFT|| orderStatus == ChainTransferOrder.STATUS_COMPLETE || orderStatus == ChainTransferOrder.STATUS_REJECTED)){
			return true;
		} else 
		   return false;
	}

	/**
	 * 1. 当前用户是来至fromStore 或者 admin
	 * 2. 订单状态是 initiali, draft, complete, reject 状态
	 * @param order
	 * @param user
	 * @return
	 */
	private boolean calculateOrderActionParm_canDraft(ChainTransferOrder order,
			ChainUserInfor user) {
		ChainStore fromStore = order.getFromChainStore();
		ChainStore toStore = order.getToChainStore();
		ChainStore myStore = user.getMyChainStore();
		int orderStatus = order.getStatus();
		
		if (fromStore == null || toStore == null)
			return true;
		
		if ((ChainUserInforService.isMgmtFromHQ(user) || fromStore.getChain_id() == myStore.getChain_id()) && (orderStatus == ChainTransferOrder.STATUS_INITIAL || orderStatus == ChainTransferOrder.STATUS_DRAFT|| orderStatus == ChainTransferOrder.STATUS_COMPLETE || orderStatus == ChainTransferOrder.STATUS_REJECTED)){
			return true;
		} else 
		   return false;
	}

	/**
	 * 红冲
	 * 1. 当前用户是总部用户
	 * 2. 订单状态是 confirm 状态
	 * @param order
	 * @param user
	 * @return
	 */
	private boolean calculateOrderActionParm_canCancel(
			ChainTransferOrder order, ChainUserInfor user) {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * 1. 当前用户是来至toStore 或者 admin
	 * 2. 订单状态是 submit 状态
	 * @param order
	 * @param user
	 * @return
	 */
	private boolean calculateOrderActionParm_canConfirm(
			ChainTransferOrder order, ChainUserInfor user) {
		ChainStore fromStore = order.getFromChainStore();
		ChainStore toStore = order.getToChainStore();
		ChainStore myStore = user.getMyChainStore();
		int orderStatus = order.getStatus();
		
		if (fromStore == null || toStore == null)
			return false;
		
		if ((ChainUserInforService.isMgmtFromHQ(user) || toStore.getChain_id() == myStore.getChain_id()) && (orderStatus == ChainTransferOrder.STATUS_COMPLETE)){
			return true;
		} else 
		   return false;
	}

	/**
	 * 1. 当前用户是来至toStore 或者 admin
	 * 2. 订单状态是 submit 状态
	 * @param order
	 * @param user
	 * @return
	 * @param order
	 * @param user
	 * @return
	 */
	private boolean calculateOrderActionParm_canReject(
			ChainTransferOrder order, ChainUserInfor user) {
		ChainStore fromStore = order.getFromChainStore();
		ChainStore toStore = order.getToChainStore();
		ChainStore myStore = user.getMyChainStore();
		int orderStatus = order.getStatus();
		
		if (fromStore == null || toStore == null)
			return false;
		
		if ((ChainUserInforService.isMgmtFromHQ(user) || toStore.getChain_id() == myStore.getChain_id()) && (orderStatus == ChainTransferOrder.STATUS_COMPLETE)){
			return true;
		} else 
		   return false;
	}

	/**
	 * 1. 当前用户是来至fromStore 或者 admin
	 * 2. 订单状态是 initiali, draft, reject 状态
	 * @param order
	 * @param user
	 * @return
	 */
	private boolean calculateOrderActionParm_canPost(
			ChainTransferOrder order, ChainUserInfor user) {
		ChainStore fromStore = order.getFromChainStore();
		ChainStore toStore = order.getToChainStore();
		ChainStore myStore = user.getMyChainStore();
		int orderStatus = order.getStatus();
		
		if (fromStore == null || toStore == null)
			return true;
		
		if ((ChainUserInforService.isMgmtFromHQ(user) || fromStore.getChain_id() == myStore.getChain_id()) && (orderStatus == ChainTransferOrder.STATUS_INITIAL || orderStatus == ChainTransferOrder.STATUS_DRAFT || orderStatus == ChainTransferOrder.STATUS_REJECTED)){
			return true;
		} else 
		   return false;
	}

	/**
	 * 1. 当前用户是来至fromStore或者admin
	 * 2. 订单状态是 initiali, draft, reject 状态
	 * 
	 * 1. 如果包含非连锁店，
	 * 2. 订单状态是 initiali, draft, reject 状态
	 * @param order
	 * @param user
	 * @return
	 */
	private boolean calculateOrderActionParm_canEdit(ChainTransferOrder order, ChainUserInfor user) {
		ChainStore fromStore = order.getFromChainStore();
		ChainStore toStore = order.getToChainStore();
		int orderStatus = order.getStatus();

		ChainStore myStore = user.getMyChainStore();
		if (fromStore == null || toStore == null)
			return false;
		
		if ((ChainUserInforService.isMgmtFromHQ(user) || fromStore.getChain_id() == myStore.getChain_id()) && (orderStatus == ChainTransferOrder.STATUS_INITIAL || orderStatus == ChainTransferOrder.STATUS_DRAFT || orderStatus == ChainTransferOrder.STATUS_REJECTED)){
			return true;
		} else 
		   return false;
	}

	/**
	 * edit transfer order
	 * @param formBean
	 * @param userInfor
	 * @return
	 */
	@Transactional
	public Response editTransferOrder(ChainTransferFormBean formBean,
			ChainUserInfor userInfor) {
		Response response = new Response();
		int orderId = formBean.getTransferOrder().getId();
		ChainTransferOrder order = getOrderById(orderId);
		
		if (order == null)
			response.setFail("无法找到单据号为 " + orderId + " 的调货单");
		else if (!calculateOrderActionParm_canEdit(order, userInfor)){
			response.setFail("单据号为 " + orderId + " 的调货单 在当前状态下不能被修改");
		} else {
			order.putSetToList();
			maskWholePrice(order, userInfor);
			formBean.setTransferOrder(order);
			
			calculateOrderActionParm(formBean, order, userInfor);
		}
		
		return response;
	}

	/**
	 * 删除transfer order
	 * @param id
	 * @param userInfor
	 * @return
	 */
	@Transactional
	public Response deleteTransferOrder(int id, ChainUserInfor userInfor) {
		Response response = new Response();
		ChainTransferOrder order = getOrderById(id);
		
		if (order == null)
			response.setFail("无法找到单据号为 " + id + " 的调货单,无法完成删除");
		else if (!calculateOrderActionParm_canDelete(order, userInfor)){
			response.setFail("单据号为 " + id + " 的调货单 在当前状态下不能被删除");
		} else {
			chainTransferOrderDaoImpl.delete(order, true);
			ChainTransferLog log = new ChainTransferLog(id, userInfor.getName() + " 删除单据");
			chainTransferLogDaoImpl.save(log, true);
		}
		
		return response;
	}

	/**
	 * 提交单据给对方连锁店
	 * @param transferOrder
	 * @param userInfor
	 * @return
	 */
	@Transactional
	public Response postTransferOrder(ChainTransferOrder transferOrder,
			ChainUserInfor userInfor) {
		Response response = new Response();
		//1. 验证单据可提交不
		int newStatus = ChainTransferOrder.STATUS_COMPLETE;
		validateTransferOrder(transferOrder, userInfor, newStatus, response);
		if (!response.isSuccess())
			return response;
		
		//2.获取以前单据，复制以前一些信息
		int orderId = transferOrder.getId();
		initializeTransferOrderInformation(orderId, transferOrder, userInfor);
		
		//3. 生成audit log
		ChainTransferLog auditLog = new ChainTransferLog();
		auditLog.setOrderId(transferOrder.getId());
		auditLog.setLogTime(Common_util.getToday());
		auditLog.setLog(userInfor.getName() + " 过账单据");
		
		//4. 保存单据
		transferOrder.setStatus(newStatus);
		this.reCalculate(transferOrder);
		saveTransferOrder(transferOrder, auditLog, null, response);
		
		response.setReturnValue(transferOrder.getId());
		
		return response;
	}

	/**
	 * 对方连锁店 退回单据
	 * @param id
	 * @param userInfor
	 * @return
	 */
	@Transactional
	public Response rejectTransferOrder(int id, ChainUserInfor userInfor) {
		Response response = new Response();
		ChainTransferOrder order = getOrderById(id);
		
		if (order == null)
			response.setFail("无法找到单据号为 " + id + " 的调货单,无法完成删除");
		else if (!calculateOrderActionParm_canReject(order, userInfor)){
			response.setFail("单据号为 " + id + " 的调货单 在当前状态下不能被退回");
		} else {
			order.setStatus(ChainTransferOrder.STATUS_REJECTED);
			
			chainTransferOrderDaoImpl.update(order, true);
			
			ChainTransferLog log = new ChainTransferLog(id, userInfor.getName() + " 退回单据");
			chainTransferLogDaoImpl.save(log, true);
			
			response.setReturnValue(id);
		}
		
		return response;
	}

	/**
	 * 确认单据
	 * 1. 两边连锁店下账
	 * 2. 两边下库存
	 * @param id
	 * @param userInfor
	 * @return
	 */
	@Transactional
	public Response confirmTransferOrder(int id, ChainUserInfor userInfor) {
		Response response = new Response();
		ChainTransferOrder order = getOrderById(id);
		
		if (order == null)
			response.setFail("无法找到单据号为 " + id + " 的调货单,无法完成确认单据");
		else if (!calculateOrderActionParm_canConfirm(order, userInfor)){
			response.setFail("单据号为 " + id + " 的调货单 在当前状态下不能被确认");
		} else {
			//更新单据状态
			order.setStatus(ChainTransferOrder.STATUS_CONFIRMED);
			chainTransferOrderDaoImpl.update(order, true);
			
			//记录Log
			ChainTransferLog log = new ChainTransferLog(id, userInfor.getName() + " 确认单据");
			chainTransferLogDaoImpl.save(log, true);
			
			//账目下账
			double transportationFee = order.getTransportationFee();
			double wholePrice = order.getTotalWholeSalesPrice();
			double acctFlow = wholePrice + transportationFee;
			int fromStoreId = order.getFromChainStore().getChain_id();
			int toStoreId = order.getToChainStore().getChain_id();
			
			ChainTransferOrderFlowAcct chainTransferOrderFlowAcctFromStore = new ChainTransferOrderFlowAcct(order, acctFlow * -1, fromStoreId);
			ChainTransferOrderFlowAcct chainTransferOrderFlowAcctToStore = new ChainTransferOrderFlowAcct(order, acctFlow, toStoreId);
			chainTransferFlowAcctDaoImpl.save(chainTransferOrderFlowAcctFromStore, true);
			chainTransferFlowAcctDaoImpl.save(chainTransferOrderFlowAcctToStore, true);
			
			//库存下账
			int fromClientId = order.getFromChainStore().getClient_id();
			int toClientId = order.getToChainStore().getClient_id();
			String orderId = ChainInOutStock.CHAIN_TRASFER2 + id;
			int type = ChainInOutStock.TYPE_TRANSFER2;
			
			Iterator<ChainTransferOrderProduct> orderProducts = order.getProductSet().iterator();
			while (orderProducts.hasNext()){
				ChainTransferOrderProduct orderProduct = orderProducts.next();
				ProductBarcode pb = orderProduct.getProductBarcode();
				int quantity = orderProduct.getQuantity();
				double wholePriceP = orderProduct.getWholeSalesPrice();
				double wholePriceTotal = orderProduct.getTotalWholeSalesPrice();
				double salesPriceP = orderProduct.getSalesPrice();
				double salesPriceTotal = orderProduct.getTotalSalesPrice();
				
				ChainInOutStock chainInOutStockFrom = new ChainInOutStock(pb.getBarcode(), fromClientId, orderId, type, wholePriceP, wholePriceTotal * -1, salesPriceP, salesPriceTotal * -1, salesPriceTotal * -1, quantity * -1, pb);
				chainInOutStockDaoImpl.saveOrUpdate(chainInOutStockFrom, true);
				
				ChainInOutStock chainInOutStockTo = new ChainInOutStock(pb.getBarcode(), toClientId, orderId, type, wholePriceP, wholePriceTotal, salesPriceP, salesPriceTotal, salesPriceTotal, quantity, pb);
				chainInOutStockDaoImpl.saveOrUpdate(chainInOutStockTo, true);
			}
			
			response.setReturnValue(id);
		}
		
		return response;
	}

	/**
	 * 准备search acct flow的页面
	 * @param userInfor
	 * @param formBean
	 * @param uiBean
	 */
	public void preSearchTransferAcctFlow(ChainUserInfor userInfor,
			ChainTransferFormBean formBean, ChainTransferUIBean uiBean) {
		if (!ChainUserInforService.isMgmtFromHQ(userInfor)){
			int myChainStoreID = userInfor.getMyChainStore().getChain_id();
			ChainStore myChainStore = chainStoreDaoImpl.get(myChainStoreID, true);
			formBean.setChainStore(myChainStore);
		} 
	}

	public Response searchTransferAcctFlow(ChainTransferFormBean formBean,
			int page, int rowPerPage, String sort, String order,
			ChainUserInfor userInfor) {
		Response response = new Response();
		
		Date fromDate = Common_util.formStartDate(formBean.getSearchStartTime());
		Date toDate = Common_util.formEndDate(formBean.getSearchEndTime());
		ChainStore chainStore = formBean.getChainStore();
		Map data = new HashMap<String, Object>();
		
		//1. 获取计算总行数的critieria
		DetachedCriteria pageCriteria = buildSearchTransferAcctFlowCriteria(fromDate, toDate, chainStore);
		pageCriteria.setProjection(Projections.rowCount());
		int totalRecord = Common_util.getProjectionSingleValue(chainTransferFlowAcctDaoImpl.getByCriteriaProjection(pageCriteria, true));
		
		//2. 获取数据
		int startRow = Common_util.getFirstRecord(page, rowPerPage);
		DetachedCriteria searchCriteria = buildSearchTransferAcctFlowCriteria(fromDate, toDate, chainStore);
		searchCriteria.addOrder(Order.asc("acctFlowDate"));
		List<ChainTransferOrderFlowAcct> rows = chainTransferFlowAcctDaoImpl.getByCritera(searchCriteria, startRow, rowPerPage, true);

		boolean displayWholePrice = false;
		if (ChainUserInforService.isMgmtFromHQ(userInfor) || userInfor.getRoleType().getChainRoleTypeId() == ChainRoleType.CHAIN_OWNER)
			displayWholePrice = true;

		for (ChainTransferOrderFlowAcct orderObj : rows){
             if (displayWholePrice == false){
            	 orderObj.setTotalWholeSalesPrice(0);
            	 orderObj.setFlowAcctAmt(0);
            	 chainTransferFlowAcctDaoImpl.evict(orderObj);
             }
		}
		
		
		data.put("rows", rows);
		data.put("total", totalRecord);
		
		response.setReturnValue(data);
		
		return response;
	}

	private DetachedCriteria buildSearchTransferAcctFlowCriteria(Date fromDate,
			Date toDate, ChainStore chainStore) {
		DetachedCriteria criteria = DetachedCriteria.forClass(ChainTransferOrderFlowAcct.class);
		
		criteria.add(Restrictions.between("acctFlowDate", fromDate, toDate));
		
		if (chainStore.getChain_id() != Common_util.ALL_RECORD){
			criteria.add(Restrictions.eq("acctChainStoreId", chainStore.getChain_id()));
		}

		return criteria;
	}
	
	
}
