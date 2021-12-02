package com.onlineMIS.ORM.DAO.chainS.sales;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.onlineMIS.ORM.DAO.Response;
import com.onlineMIS.ORM.DAO.chainS.ChainUtility;
import com.onlineMIS.ORM.DAO.chainS.chainMgmt.ChainInitialStockDaoImpl;
import com.onlineMIS.ORM.DAO.chainS.chainMgmt.ChainSalesPriceDaoImpl;
import com.onlineMIS.ORM.DAO.chainS.chainMgmt.ChainStoreConfDaoImpl;
import com.onlineMIS.ORM.DAO.chainS.chainMgmt.ChainStoreGroupDaoImpl;
import com.onlineMIS.ORM.DAO.chainS.inventoryFlow.ChainInOutStockDaoImpl;
import com.onlineMIS.ORM.DAO.chainS.inventoryFlow.ChainInventoryFlowOrderService;
import com.onlineMIS.ORM.DAO.chainS.user.ChainStoreDaoImpl;
import com.onlineMIS.ORM.DAO.chainS.user.ChainStoreService;
import com.onlineMIS.ORM.DAO.chainS.user.ChainUserInforDaoImpl;
import com.onlineMIS.ORM.DAO.chainS.user.ChainUserInforService;
import com.onlineMIS.ORM.DAO.chainS.vip.ChainVIPCardImpl;
import com.onlineMIS.ORM.DAO.chainS.vip.ChainVIPPrepaidImpl;
import com.onlineMIS.ORM.DAO.chainS.vip.ChainVIPScoreImpl;
import com.onlineMIS.ORM.DAO.chainS.vip.ChainVIPService;
import com.onlineMIS.ORM.DAO.headQ.SQLServer.SaleHistoryDAOImpl;
import com.onlineMIS.ORM.DAO.headQ.barCodeGentor.ProductBarcodeDaoImpl;
import com.onlineMIS.ORM.DAO.headQ.barCodeGentor.ProductBarcodeService;
import com.onlineMIS.ORM.DAO.headQ.barCodeGentor.ProductDaoImpl;
import com.onlineMIS.ORM.DAO.headQ.inventory.HeadQSalesHisDAOImpl;
import com.onlineMIS.ORM.DAO.headQ.inventory.InventoryOrderDAOImpl;
import com.onlineMIS.ORM.entity.base.Pager;
import com.onlineMIS.ORM.entity.chainS.chainMgmt.ChainInitialStock;
import com.onlineMIS.ORM.entity.chainS.chainMgmt.ChainInitialStockId;
import com.onlineMIS.ORM.entity.chainS.chainMgmt.ChainPriceIncrement;
import com.onlineMIS.ORM.entity.chainS.chainMgmt.ChainSalesPrice;
import com.onlineMIS.ORM.entity.chainS.chainMgmt.ChainSalesPriceId;
import com.onlineMIS.ORM.entity.chainS.chainMgmt.ChainStoreConf;
import com.onlineMIS.ORM.entity.chainS.inventoryFlow.ChainInOutStock;
import com.onlineMIS.ORM.entity.chainS.sales.ChainCouponConsumeHis;
import com.onlineMIS.ORM.entity.chainS.sales.ChainDailySalesImpact;
import com.onlineMIS.ORM.entity.chainS.sales.ChainStoreSalesOrder;
import com.onlineMIS.ORM.entity.chainS.sales.ChainStoreSalesOrderProduct;
import com.onlineMIS.ORM.entity.chainS.user.ChainRoleType;
import com.onlineMIS.ORM.entity.chainS.user.ChainStore;
import com.onlineMIS.ORM.entity.chainS.user.ChainUserInfor;
import com.onlineMIS.ORM.entity.chainS.vip.ChainVIPCard;
import com.onlineMIS.ORM.entity.chainS.vip.ChainVIPPrepaidFlow;
import com.onlineMIS.ORM.entity.chainS.vip.ChainVIPScore;
import com.onlineMIS.ORM.entity.chainS.vip.ChainVIPType;
import com.onlineMIS.ORM.entity.headQ.SQLServer.SaleHistory;
import com.onlineMIS.ORM.entity.headQ.barcodeGentor.Brand;
import com.onlineMIS.ORM.entity.headQ.barcodeGentor.Category;
import com.onlineMIS.ORM.entity.headQ.barcodeGentor.Product;
import com.onlineMIS.ORM.entity.headQ.barcodeGentor.ProductBarcode;
import com.onlineMIS.ORM.entity.headQ.barcodeGentor.Quarter;
import com.onlineMIS.ORM.entity.headQ.barcodeGentor.Year;
import com.onlineMIS.ORM.entity.headQ.inventory.HeadQSalesHistory;
import com.onlineMIS.ORM.entity.headQ.inventory.HeadQSalesHistoryId;
import com.onlineMIS.ORM.entity.headQ.inventory.InventoryOrder;
import com.onlineMIS.ORM.entity.headQ.inventory.InventoryOrderProduct;
import com.onlineMIS.ORM.entity.headQ.user.UserInfor;
import com.onlineMIS.action.chainS.sales.ChainSalesActionFormBean;
import com.onlineMIS.action.chainS.sales.ChainSalesActionUIBean;
import com.onlineMIS.action.chainS.vo.ChainProductBarcodeVO;
import com.onlineMIS.common.Common_util;
import com.onlineMIS.common.ERRORS;
import com.onlineMIS.common.loggerLocal;
import com.onlineMIS.filter.SystemParm;

@Service
public class ChainStoreSalesService {
	@Autowired
	private ChainUserInforDaoImpl chainUserInforDaoImpl;
	
	@Autowired
	private ChainStoreSalesOrderDaoImpl chainStoreSalesOrderDaoImpl;
	
	@Autowired
	private ChainStoreSalesOrderProductDaoImpl chainStoreSalesOrderProductDaoImpl;
	
	@Autowired
	private ChainStoreService chainStoreService;

	@Autowired
    private ChainInOutStockDaoImpl chainInOutStockDaoImpl;

	@Autowired
	private ProductBarcodeDaoImpl productBarcodeDaoImpl;
	
	@Autowired
	private ProductBarcodeService productBarcodeService;

	@Autowired
	private HeadQSalesHisDAOImpl headQSalesHisDAOImpl;
	
	@Autowired
	private ChainVIPScoreImpl chainVIPScoreImpl;
	
	@Autowired
	private ChainVIPService chainVIPService;
	
	@Autowired
	private ChainVIPCardImpl chainVIPCardImpl;
	
	@Autowired
	private ChainStoreConfDaoImpl chainStoreConfDaoImpl;
	
	@Autowired
	private ChainInventoryFlowOrderService chainInventoryFlowOrderService;

	@Autowired
	private ChainInitialStockDaoImpl chainInitialStockDaoImpl;
	
	@Autowired
	private ChainSalesPriceDaoImpl chainSalesPriceDaoImpl;
	
	@Autowired
	private ChainDailySalesImpactDaoImpl chainDailySalesImpactDaoImpl;
	
	@Autowired
	private ChainCouponConsumHisDaoImpl chainCouponConsumHisDaoImpl;
	
	@Autowired
	private ProductDaoImpl productDaoImpl;
	@Autowired
	private ChainVIPPrepaidImpl chainVIPPrepaidImpl;
	@Autowired
	private ChainStoreGroupDaoImpl chainStoreGroupDaoImpl;

	/**
	 * this function is to prepare the UI Bean of the Chain sales order
	 * @param userInfor
	 * @return
	 */
	@Transactional
	public void prepareNewSalesOrderUI(ChainSalesActionFormBean formBean, ChainSalesActionUIBean uiBean, ChainUserInfor userInfor, int orderType) {
		List<ChainStore> stores =  new ArrayList<ChainStore>();
		
		//1. set the store list
		stores = chainStoreService.getChainStoreList(userInfor);
		uiBean.setChainStores(stores);
		
		//2. set the sales person in the chain store
		List<ChainUserInfor> salers = new ArrayList<ChainUserInfor>();
		if (stores.size() > 0)
		    salers.addAll(chainUserInforDaoImpl.getActiveChainUsersByChainStore(stores.get(0).getChain_id()));
		uiBean.setChainSalers(salers);

		//3. set the create date
		java.sql.Date today = new java.sql.Date(new Date().getTime());
		formBean.getChainSalesOrder().setOrderDate(today);
		
		//4. set the order creator
		uiBean.setOrderCreator(userInfor);
		
		//5. set the sales order type
		uiBean.getChainSalesOrder().setType(orderType);
		
		//6. get the chain store configuration information
		ChainStoreConf chainStoreConf = null;
		if (stores.size() > 0)
		    chainStoreConf = chainStoreConfDaoImpl.getChainStoreConfByChainId(stores.get(0).getChain_id());
		if (chainStoreConf == null)
			chainStoreConf = new ChainStoreConf();
		uiBean.setChainStoreConf(chainStoreConf);
		
		//7.准备chain store的信息
		ChainStore chainStore = null;
		if (stores.size() > 0)
		    chainStore = chainStoreService.getChainStoreByID(stores.get(0).getChain_id());
		uiBean.setChainStore(chainStore);
		
		//7. 准备vip信息
		ChainVIPCard vipCard = formBean.getChainSalesOrder().getVipCard();
		if (vipCard != null && vipCard.getId() != 0){
			int vipId = vipCard.getId();
			ChainVIPCard validatedCard = chainVIPCardImpl.get(vipId, true);
			if (validatedCard != null){
				List<Double> result = chainVIPService.getVIPCardTotalScore(vipId);
                formBean.setVipCardNo(validatedCard.getVipCardNo());
                formBean.setMaxVipCash(result.get(1));
                formBean.setDiscount(validatedCard.getVipType().getDiscountRate());
                uiBean.setMsg(validatedCard.getCustomerName() + " " + validatedCard.getVipType().getVipTypeName() + "  积分可换现金 :" + Common_util.formatDouble(result.get(1), Common_util.df) + " 剩余预存金 :" + Common_util.formatDouble(result.get(2), Common_util.df));
			} 
		}
	}
	
	/**
	 * this function is to prepare the UI Bean of the Chain sales order
	 * @param userInfor
	 * @return
	 */
	public void prepareEditSalesOrderUI(ChainSalesActionFormBean formBean, ChainSalesActionUIBean uiBean, ChainUserInfor userInfor, ChainStoreSalesOrder salesOrder) {
		int orderType =  salesOrder.getType();
		ChainStore chainStore = salesOrder.getChainStore();
		int chainId = 0;
		if (chainStore != null && chainStore.getChain_id() > 0)
			chainId = chainStore.getChain_id();
			
		List<ChainStore> stores =  new ArrayList<ChainStore>();
		
		//1. set the store list
		stores = chainStoreService.getChainStoreList(userInfor);
		uiBean.setChainStores(stores);
		
		//2. set the sales person in the chain store
		List<ChainUserInfor> salers = new ArrayList<ChainUserInfor>();
		
		if (chainId > 0){
			salers.addAll(chainUserInforDaoImpl.getActiveChainUsersByChainStore(chainId));
		} else if (stores.size() > 0)
		    salers.addAll(chainUserInforDaoImpl.getActiveChainUsersByChainStore(stores.get(0).getChain_id()));
		uiBean.setChainSalers(salers);

		//3. set the order creator
		uiBean.setOrderCreator(userInfor);
		
		//4. set the sales order type
		uiBean.getChainSalesOrder().setType(orderType);
		
		//5. get the chain store configuration information
		ChainStoreConf chainStoreConf = chainStoreConfDaoImpl.get(chainId, true);
		
		if (chainStoreConf == null)
			chainStoreConf = new ChainStoreConf();
		uiBean.setChainStoreConf(chainStoreConf);
		
		//7. 准备vip信息
		ChainVIPCard vipCard = salesOrder.getVipCard();
		if (salesOrder.getStatus() == ChainStoreSalesOrder.STATUS_DRAFT && vipCard != null && vipCard.getId() != 0){
			int vipId = vipCard.getId();
			ChainVIPCard validatedCard = chainVIPCardImpl.get(vipId, true);
			if (validatedCard != null){
				List<Double> result = chainVIPService.getVIPCardTotalScore(vipId);
                formBean.setVipCardNo(validatedCard.getVipCardNo());
//                formBean.setMaxVipCash();
                formBean.setDiscount(validatedCard.getVipType().getDiscountRate());
                uiBean.setMsg(validatedCard.getCustomerName() + " " + validatedCard.getVipType().getVipTypeName() + "  积分可换现金 :" + Common_util.formatDouble(result.get(1), Common_util.df) + " 剩余预存金 :" + Common_util.formatDouble(result.get(2), Common_util.df));
			} 
		}
	}

	/**
	 * 销售时候通过条码获取数据
	 * @param barcode
	 * @return
	 */
	public ChainStoreSalesOrderProduct scanProductsByBarcode(String barcode, int chainId, int vipId) {
		ProductBarcode product =  productBarcodeDaoImpl.getByBarcode(barcode);
			
		ChainStoreSalesOrderProduct chainOrderProduct = new ChainStoreSalesOrderProduct();
		
		if (product!= null){
		   ChainStore chainStore = chainStoreService.getChainStoreByID(chainId);
		   
		   //2. 获取涨价
		   ChainProductBarcodeVO vo = chainSalesPriceDaoImpl.convertProductBarcodeVO(product, chainStore, vipId);
		   if (vo.getMySalePrice() != 0)
			   product.getProduct().setSalesPrice(vo.getMySalePrice());
		   if (vo.getDiscount() != 1)
			   chainOrderProduct.setDiscountRate(vo.getDiscount());
		   
			
		   productBarcodeDaoImpl.evict(product);
			
		   chainOrderProduct.setProductBarcode(product);
		   int inventoryLevel = 0;
		   
		   try {
		       inventoryLevel = chainInventoryFlowOrderService.getInventoryLevel(product.getBarcode(), chainStore.getClient_id());
		   } catch (Exception e) {
			   loggerLocal.info("获取inventory出错," + chainId + "," + product.getBarcode());
		   }
		   
		   chainOrderProduct.setInventoryLevel(inventoryLevel);
		   return chainOrderProduct;
		}else {
		   return null;
		}

	}


	
	/**
	 * function to delete the sales order
	 * @param userInfor
	 * @param orderId
	 * @return
	 */
	public Response deleteOrder(ChainUserInfor userInfor, int orderId) {
		Response response = new Response();
		ChainStoreSalesOrder salesOrder = chainStoreSalesOrderDaoImpl.get(orderId, false);
		if (salesOrder.getStatus() == ChainStoreSalesOrder.STATUS_DRAFT){
	        String sql = "UPDATE ChainStoreSalesOrder SET status = ?, orderCreateDate =? WHERE id =?";
	        Object[] values = null;
	        
	        if (!ChainUserInforService.isMgmtFromHQ(userInfor)){
	    		ChainStore myChainStore = userInfor.getMyChainStore();
	    		
	        	sql += " and chainStore.chain_id =?";
	        	values = new Object[]{ChainStoreSalesOrder.STATUS_DELETED,new Date(), orderId, myChainStore.getChain_id()};
	        } else {
	        	values = new Object[]{ChainStoreSalesOrder.STATUS_DELETED,new Date(), orderId};
	        }
	 
	        int count = 0;
	        
	        try {
	             count = chainStoreSalesOrderDaoImpl.executeHQLUpdateDelete(sql, values, false);
	        } catch (Exception e) {
				response.setReturnCode(Response.ERROR);
				response.setMessage(e.getMessage());
				return response;
			}
	        
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
	 * to cancel 红冲单据
	 * @param userInfor
	 * @param orderId
	 * @return
	 */
	@Transactional
	public Response cancelOrder(ChainUserInfor userInfor, int orderId) {
		ChainStoreSalesOrder salesOrder = getSalesOrderById(orderId, userInfor);
		Response response = new Response();
		
    	response = validateOverDay(salesOrder, userInfor, ChainStoreSalesOrder.STATUS_CANCEL);	
    	if (response.getReturnCode() == Response.ERROR)
    		return response;
    	
		if (salesOrder != null){
			//1. Cancel the order
			String sql = "UPDATE ChainStoreSalesOrder SET status = ?, orderCreateDate =? WHERE id =?";
	        Object[] values = new Object[]{ChainStoreSalesOrder.STATUS_CANCEL, new Date(), orderId};

	        int count = 0;
	        
	        try {
	             count = chainStoreSalesOrderDaoImpl.executeHQLUpdateDelete(sql, values, false);
	        } catch (Exception e) {
				response.setReturnCode(Response.ERROR);
				response.setMessage(e.getMessage());
				return response;
			}
	        
	        if (count == 0){
	        	response.setReturnCode(Response.FAIL);
	        	response.setMessage("红冲单据失败");
	        } else {
	        	//2.0 update the stock table 
	        	updateChainInOutStock(salesOrder, ChainStoreSalesOrder.STATUS_CANCEL);
	        	
	        	//3.0 update the vip积分和预付款
	        	updateVipScorePrepaid(salesOrder, ChainStoreSalesOrder.STATUS_CANCEL);
	        	
	        	//4.0 查看是否会印象batch
	        	checkDailySalesImpact(salesOrder);
	        	
	        	response.setReturnCode(Response.SUCCESS);
	        	response.setMessage("成功红冲单据");
	        }
		} else {
			response.setReturnCode(Response.NO_AUTHORITY);
			response.setMessage("无权限红冲此单据");
		}
		return response;
	}
	
	/**
	 * service to copy one order
	 * @param id
	 * @return
	 */
	@Transactional
	public Response copyOrder(int orderId, ChainUserInfor user) {
		ChainStoreSalesOrder salesOrder = getSalesOrderById(orderId, user);

		Response response = new Response();
		if (salesOrder == null){
			response.setReturnCode(Response.FAIL);
			response.setMessage("无法复制相应单据");
		} else {
			initializeInventoryLevel(salesOrder.getProductSet(), salesOrder.getChainStore().getClient_id());
			
			chainStoreSalesOrderDaoImpl.evict(salesOrder);
			salesOrder.setId(0);
			salesOrder.setStatus(0);
			
			response.setReturnCode(Response.SUCCESS);
			salesOrder.setOrderDate(Common_util.getToday());
			response.setReturnValue(salesOrder);
			response.setMessage("成功复制单据");
		}
		return response;
	}
	
	/**
	 * function to save the sales orders to one status
	 * @param salesOrder
	 * @param userInfor
	 * @param orderType
	 * @param orderStatus
	 */
	@Transactional
	public Response saveSaleOrders(ChainStoreSalesOrder salesOrder,
			ChainUserInfor userInfor, int orderType, int statusNew){
		Response response = new Response();

		if (salesOrder != null){
			ChainStoreSalesOrder origOrder = chainStoreSalesOrderDaoImpl.get(salesOrder.getId(), true);
			//to get the client id
			int statusCurrent = 0;
			if (origOrder != null){
				salesOrder.setChainStore(origOrder.getChainStore());
				statusCurrent = origOrder.getStatus();
				chainStoreSalesOrderDaoImpl.evict(origOrder);
			} else {
				salesOrder.setChainStore(chainStoreService.getChainStoreByID(salesOrder.getChainStore().getChain_id()));
			}
			
			if (statusNew == ChainStoreSalesOrder.STATUS_DELETED && statusCurrent != ChainStoreSalesOrder.STATUS_DRAFT){
				response.setReturnCode(Response.FAIL);
				response.setMessage("过账单据无法删除，只能红冲");
			} else if (statusNew == ChainStoreSalesOrder.STATUS_CANCEL && statusCurrent != ChainStoreSalesOrder.STATUS_COMPLETE){
				response.setReturnCode(Response.FAIL);
				response.setMessage("未过账单据无法红冲");
			} else if (statusNew <= statusCurrent && statusCurrent != ChainStoreSalesOrder.STATUS_DRAFT){
				response.setReturnCode(Response.FAIL);
				response.setMessage("操作无法完成,错误包括(1. 重复过账 2.重复红冲单据)");
		    }else {
		    	
		    	//过账或者红冲前要验证是否夸时间，和权限是否够
		    	if (statusNew == ChainStoreSalesOrder.STATUS_COMPLETE || statusNew == ChainStoreSalesOrder.STATUS_CANCEL){
		    		response = validateOverDay(salesOrder, userInfor, statusNew);		    		
		    	}
		    	
		    	//过账前要验证validate
		    	if (response.getReturnCode() != Response.ERROR && statusNew == ChainStoreSalesOrder.STATUS_COMPLETE)
		            response = validateSalesOrder(salesOrder, userInfor);	
		    	
		        if (response.getReturnCode() != Response.ERROR){
		    		//1. 计算单据的totalQuantityA, totalAmountA
		    		List<ChainStoreSalesOrderProduct> productSales = salesOrder.getProductList();
		    		int totalQuantityA = salesOrder.getTotalQuantity();
		    		double netAmountA = salesOrder.getNetAmount();
		    		if (totalQuantityA != 0){
		    	      for (ChainStoreSalesOrderProduct product : productSales){
		    	    	  if (product == null || product.getProductBarcode() == null || product.getProductBarcode().getBarcode().equals(""))
		    	    		  continue;
		    	    	  
		    	    	  if (isExclucded(product.getProductBarcode().getId())){
		    	    		  totalQuantityA -= product.getQuantity();
		    	    		  netAmountA -= product.getRetailPrice() * product.getDiscountRate();
		    	    	  }
		    	      }
		    	      
		    	      salesOrder.setNetAmountA(netAmountA);
		    	      salesOrder.setTotalQuantityA(totalQuantityA);
		    		}
		    		
		    		
					//1. update the nessary information
					salesOrder.setOrderCreateDate(new Date());
					//有可能是总部用户过账
					if (ChainUserInforService.isMgmtFromHQ(userInfor))
					    salesOrder.setCreator(salesOrder.getSaler());
					else 
					    salesOrder.setCreator(userInfor);
					salesOrder.setType(orderType);
					salesOrder.setStatus(statusNew);
			
					//2. check the availability of vip card
					ChainVIPCard vipCard = salesOrder.getVipCard();
					if (vipCard == null || vipCard.getId() == 0)
						salesOrder.setVipCard(null);
					
					//3. validate the sales order like the total vip score
					validateChainSalesOrder(salesOrder, response);
					if (response.getReturnCode() == Response.ERROR)
						return response;
	
					//4. save the sales order information
					if (statusNew== ChainStoreSalesOrder.STATUS_DRAFT || statusNew == ChainStoreSalesOrder.STATUS_COMPLETE){
						if (salesOrder.getId() != 0)
							chainStoreSalesOrderProductDaoImpl.deleteOrderProduct(salesOrder.getId());
	
						salesOrder.buildIndex();
						salesOrder.putListToSet();
					}
					
					//5. inject the cost price to the order product if it is complete
					if (statusNew == ChainStoreSalesOrder.STATUS_COMPLETE){
						calculateCostPrice(salesOrder);
					}
					
					chainStoreSalesOrderDaoImpl.saveOrUpdate(salesOrder, false);
					
					//6. - update the stock table
					//   - update the vip score
					//   - 以及预付款
					if (statusNew== ChainStoreSalesOrder.STATUS_COMPLETE || statusNew == ChainStoreSalesOrder.STATUS_CANCEL){
					    updateChainInOutStock(salesOrder, statusNew);
					    
					    updateVipScorePrepaid(salesOrder, statusNew);
					}
					
					//7. 检查是否对日销售数据有印象
					//   每晚有一个batch汇总，今天之前的单子就会对以前的数据造成印象
					checkDailySalesImpact(salesOrder);
					
					//8. 如果是过账还需要检查是否需要返回vip的积分

					checkVIPCoupon(salesOrder, response, statusNew);

					
					response.setReturnCode(Response.SUCCESS);
		        }
			}
		} else {
			response.setReturnCode(Response.NO_AUTHORITY);
			response.setMessage("无权限修改单据");
		}
		
		return response;	
	}

    /**
     * 检查这张单子是否
     * @param salesOrder
     * @return
     */
	private void checkVIPCoupon(ChainStoreSalesOrder salesOrder, Response response, int statusNew) {
		if (statusNew == ChainStoreSalesOrder.STATUS_COMPLETE){
		      ChainVIPCard vipCard = salesOrder.getVipCard();
		      if (vipCard != null && vipCard.getId() != 0){
		    	  ChainVIPCard vipCard2 = chainVIPCardImpl.get(vipCard.getId(), true);
		    	  
		    	  double couponSum =  chainVIPScoreImpl.getVIPScoreSum(vipCard.getId());
		    	  
		    	  double accumulateVipPrepaid = chainVIPService.getAcumulateVipPrepaid(vipCard);
		    	  vipCard.setCustomerName(vipCard2.getCustomerName());
		    	  vipCard.setAccumulatedScore(couponSum + vipCard2.getInitialScore());
		    	  vipCard.setAccumulateVipPrepaid(accumulateVipPrepaid);
		    	  response.setReturnValue(vipCard);
		      }
	    }
	}

	/**
	 * 验证是否跨日期和有权限跨日期
	 * @param salesOrder
	 * @param userInfor
	 * @return
	 */
	private Response validateOverDay(ChainStoreSalesOrder salesOrder,
			ChainUserInfor userInfor, int statusNew) {
		Response response = new Response(Response.SUCCESS);
		Date  today = Common_util.getToday();
		
		if (statusNew == ChainStoreSalesOrder.STATUS_COMPLETE || statusNew == ChainStoreSalesOrder.STATUS_CANCEL){
			java.sql.Date orderDate = salesOrder.getOrderDate();
			if (Common_util.isBefore(today, orderDate)){
				if (!ChainUserInforService.isMgmtFromHQ(userInfor) && userInfor.getRoleType().getChainRoleTypeId() != ChainRoleType.CHAIN_OWNER){
					response.setQuickValue(Response.ERROR, "你的账号无权限修改历史单据");
				}	
			}
		}
		return response;
	}

	/**
	 * 检查是否对以前batch运行过的数据有影响
	 * 1. 红冲今天以前的单子
	 * 2. 过账到今天以前
	 * @param origOrder
	 * @param salesOrder
	 */
	private void checkDailySalesImpact(ChainStoreSalesOrder salesOrder) {
		int statusNew = salesOrder.getStatus();
		Date  today = Common_util.getToday();
		
		if (statusNew == ChainStoreSalesOrder.STATUS_COMPLETE || statusNew == ChainStoreSalesOrder.STATUS_CANCEL){
			java.sql.Date orderDate = salesOrder.getOrderDate();
			if (Common_util.isBefore(today, orderDate)){
				ChainDailySalesImpact chainDailySalesImpact = new ChainDailySalesImpact(salesOrder.getChainStore(),orderDate, salesOrder.getId());
				chainDailySalesImpactDaoImpl.saveOrUpdate(chainDailySalesImpact, true);
			}
		}		
	}

	/**
	 * 验证销售单据
	 * 1. 准备过账
	 *    - discount 不能大于 minDiscountRate
	 *    - 计算单据的totalQuantityA, totalAmountA， qxQuantity, qxAmount, myQuantity, myAmount
	 *    - 如果不是总部管理员工和老板账户，不能跨日期过账
	 *    - 非总部管理人员不能跨连锁店过账
	 *    - 使用积分策略验证
	 *    - 所有数量必须是大于零的 数字
	 *    - 是否有2020年受约束 货品
	 * @param salesOrder
	 * @return
	 */
	@Transactional
	private Response validateSalesOrder(ChainStoreSalesOrder salesOrder, ChainUserInfor loginUser) {
		int chainId = salesOrder.getChainStore().getChain_id();
		Response response = new Response();
		response.setReturnCode(Response.SUCCESS);
		ChainStoreConf conf = chainStoreConfDaoImpl.getChainStoreConfByChainId(chainId);
		
		//1. 查看登录人员的连锁店和当前连锁店是否匹配
		int myChainId = loginUser.getMyChainStore().getChain_id();
		if (myChainId != Common_util.ALL_RECORD && myChainId != salesOrder.getChainStore().getChain_id()){
			String errorMsg = "跨连锁店过账有安全隐患，此操作被禁止.你可以关闭当前销售开单页面，重新再次打开";
			response.setQuickValue(Response.ERROR, errorMsg);
			return response;
  	  	}
		
		//2. 使用积分策略验证
		if (salesOrder.getVipCard() != null && salesOrder.getVipScore() != 0){
			int vipCardId = salesOrder.getVipCard().getId();
			ChainVIPCard vipCard = chainVIPCardImpl.get(vipCardId, true);
			
			if (vipCard != null){
		        int vipIssuedChainStore = vipCard.getIssueChainStore().getChain_id();
		        int salesOrderChainStore = salesOrder.getChainStore().getChain_id();
		        
		        if (vipIssuedChainStore != salesOrderChainStore){
		        	int VIPSCORE_STRATEGY = conf.getAllowOtherVIPUseVIPScore();
		        	
		        	if (VIPSCORE_STRATEGY == ChainStoreConf.VIPSCORE_USAGE_RESTRICTED){
						String errorMsg = "当前vip的开户连锁店是 : " + vipCard.getIssueChainStore().getChain_name() + "\n";
						       errorMsg += "当前连锁店设置 : 只能自己连锁店vip兑换积分";
						response.setQuickValue(Response.ERROR, errorMsg);
						return response;
		        	} else if (VIPSCORE_STRATEGY == ChainStoreConf.VIPSCORE_USAGE_GROUPCHAIN){
		        		Set<Integer> groupChainIdSet = chainStoreGroupDaoImpl.getChainGroupStoreIdList(salesOrder.getChainStore().getChain_id(),loginUser,Common_util.CHAIN_ACCESS_LEVEL_3);
		        		if (!groupChainIdSet.contains(vipIssuedChainStore)){
							String errorMsg = "当前vip的开户连锁店是 : " + vipCard.getIssueChainStore().getChain_name() + "\n";
						       errorMsg += "当前连锁店设置 : 只能关联连锁店vip兑换积分";
						response.setQuickValue(Response.ERROR, errorMsg);
						return response;
		        		}
		        	}
					
		        }
			} else {
				String errorMsg = "无法找到vip 卡信息";
				response.setQuickValue(Response.ERROR, errorMsg);
				return response;
			}
		}
		
		//3. 验证所有的quantity 都是大于0
	      List<ChainStoreSalesOrderProduct> products2 = salesOrder.getProductList();
	      List<ChainStoreSalesOrderProduct> productsR2 = salesOrder.getProductListR();
	      int salesTotalQ = 0;
	      boolean quantityError = false;
	      for (ChainStoreSalesOrderProduct product : products2){
	    	  if (product == null || product.getProductBarcode() == null|| product.getProductBarcode().getBarcode() == null || product.getProductBarcode().getBarcode().equals(""))
	    		  continue;
	    	  
	    	  if (product.getQuantity() <= 0 && quantityError == false)
	    	      quantityError = true;
	    	  else 
	    		  salesTotalQ += product.getQuantity();	    	  
	      }
	      
	      int returnTotalQ = 0;
	      for (ChainStoreSalesOrderProduct product : productsR2){
	    	  if (product == null || product.getProductBarcode() == null|| product.getProductBarcode().getBarcode() == null || product.getProductBarcode().getBarcode().equals(""))
	    		  continue;
	    	  
	    	  if (product.getQuantity() <= 0 && quantityError == false)
	    	      quantityError = true;
	    	  else 
	    		  returnTotalQ += product.getQuantity();
	      }
	      if (quantityError){
				String errorMsg2 = "所有数量必须是大于零的数字";
				response.setQuickValue(Response.ERROR, errorMsg2);
				return response;
	      } else {
	    	  if (salesTotalQ != salesOrder.getTotalQuantity()){
					response.setQuickValue(Response.ERROR, "销售货品总数和明细数量无法匹配，请检查再保存单据");
					return response;  
	    	  }
	    	  
	    	  if (returnTotalQ != salesOrder.getTotalQuantityR()){
					response.setQuickValue(Response.ERROR, "退货货品总数和明细数量无法匹配，请检查再保存单据");
					return response;  
	    	  }
	      }
	      
	    
		
		if (conf != null ){
		   double minDiscountRate = conf.getMinDiscountRate();
		   if (minDiscountRate > 0){
			  String errorMsg = "";
		      List<ChainStoreSalesOrderProduct> products = salesOrder.getProductList();
		      List<ChainStoreSalesOrderProduct> productsR = salesOrder.getProductListR();
		      for (ChainStoreSalesOrderProduct product : products){
		    	  if (product == null || product.getProductBarcode() == null|| product.getProductBarcode().getBarcode() == null || product.getProductBarcode().getBarcode().equals(""))
		    		  continue;
		    	  double discount = product.getDiscountRate();
		    	  double productId = product.getProductBarcode().getId();
		    	  if (productId != 0 && discount < minDiscountRate){
		    		  String barcode = product.getProductBarcode().getBarcode();
		    		  String brand = product.getProductBarcode().getProduct().getBrand().getBrand_Name();
		    		  String productCode = product.getProductBarcode().getProduct().getProductCode();
		    		  errorMsg += barcode + " " + brand +  " " + productCode + " 低于本连锁店最低折扣" +minDiscountRate +"\n";
		    	  }
		      }
		      
		      for (ChainStoreSalesOrderProduct product : productsR){
		    	  if (product == null || product.getProductBarcode() == null|| product.getProductBarcode().getBarcode() == null || product.getProductBarcode().getBarcode().equals(""))
		    		  continue;
		    	  double discount = product.getDiscountRate();
		    	  double productId = product.getProductBarcode().getId();
		    	  if (productId != 0 && discount < minDiscountRate){
		    		  String barcode = product.getProductBarcode().getBarcode();
		    		  String brand = product.getProductBarcode().getProduct().getBrand().getBrand_Name();
		    		  String productCode = product.getProductBarcode().getProduct().getProductCode();
		    		  errorMsg += barcode + " " + brand + " " + productCode + " 低于本连锁店最低折扣" +minDiscountRate +"\n";
		    	  }
		      }
		      if (errorMsg.length() > 0)
		    	  response.setQuickValue(Response.ERROR, errorMsg);
		   }
		}
		
		//更新2020 春 的产品
		for (ChainStoreSalesOrderProduct product : products2){
	    	  if (product == null || product.getProductBarcode() == null|| product.getProductBarcode().getBarcode() == null || product.getProductBarcode().getBarcode().equals(""))
	    		  continue;
	    	  
	    	ProductBarcode pBarcode = productBarcodeDaoImpl.get(product.getProductBarcode().getId(), true);
	    	  
			Product p = pBarcode.getProduct();
			Year year = p.getYear();
			Quarter quarter = p.getQuarter();
			Brand brand = p.getBrand();
			
			int specialYear = Integer.parseInt(SystemParm.getParm("SPEICAL_YEAR_2020"));
			int specialQuarterSpring = Integer.parseInt(SystemParm.getParm("SPECIAL_QUARTER_SPRING"));
			int specialQuarterSummer = Integer.parseInt(SystemParm.getParm("SPECIAL_QUARTER_SUMMER"));
			int specialQuarterAut = Integer.parseInt(SystemParm.getParm("SPECIAL_QUARTER_AUTUMN"));
			int specialQuarterWinter = Integer.parseInt(SystemParm.getParm("SPECIAL_QUARTER_WINTER"));
			Set<Integer> specialBrandSpringSet = SystemParm.getParmSet("SPECIAL_BRAND_2020_SPRING");
			Set<Integer> specialBrandSummerSet = SystemParm.getParmSet("SPECIAL_BRAND_2020_SUMMER");
			Set<Integer> specialBrandAutoSet = SystemParm.getParmSet("SPECIAL_BRAND_2020_AUTUMN");
			Set<Integer> specialBrandWinterSet = SystemParm.getParmSet("SPECIAL_BRAND_2020_WINTER");
			if (!specialBrandSpringSet.contains(brand.getBrand_ID()) && (year.getYear_ID() == specialYear && quarter.getQuarter_ID() == specialQuarterSpring) && (conf == null || (conf != null && conf.getDiscount2020Spring() == ChainStoreConf.DISCOUNT_2020_ENABLE))){
				if (isHighVip(salesOrder.getVipCard())){
					if (product.getDiscountRate() > ChainStoreConf.VIP_DISCOUNT_2020_SPRING){
						response.setQuickValue(Response.ERROR, "金卡和家人卡会员购买2021年春季的产品,折扣不能高于 " + ChainStoreConf.VIP_DISCOUNT_2020_SPRING);
						break;
					}
				} else 
					if (product.getDiscountRate() > ChainStoreConf.NORMAL_DISCOUNT_2020_SPRING){
						response.setQuickValue(Response.ERROR, "客户购买2021年春季的产品,折扣不能高于 " + ChainStoreConf.NORMAL_DISCOUNT_2020_SPRING);
						break;
					}
			} 
			
			if (!specialBrandSummerSet.contains(brand.getBrand_ID()) && (year.getYear_ID() == specialYear && quarter.getQuarter_ID() == specialQuarterSummer) && (conf == null || (conf != null && conf.getDiscount2020Summer() == ChainStoreConf.DISCOUNT_2020_ENABLE))){
				if (isHighVip(salesOrder.getVipCard())){
					if (product.getDiscountRate() > ChainStoreConf.VIP_DISCOUNT_2020_SPRING){
						response.setQuickValue(Response.ERROR, "金卡和家人卡会员购买2021夏季 的产品,折扣不能高于 " + ChainStoreConf.VIP_DISCOUNT_2020_SPRING);
						break;
					}
				} else 
					if (product.getDiscountRate() > ChainStoreConf.NORMAL_DISCOUNT_2020_SPRING){
						response.setQuickValue(Response.ERROR, "客户购买2021年夏季的产品,折扣不能高于 " + ChainStoreConf.NORMAL_DISCOUNT_2020_SPRING);
						break;
					}
			} 
			
			if (!specialBrandAutoSet.contains(brand.getBrand_ID()) && (year.getYear_ID() == specialYear && quarter.getQuarter_ID() == specialQuarterAut) && (conf == null || (conf != null && conf.getDiscount2020Auto() == ChainStoreConf.DISCOUNT_2020_ENABLE))){
				if (isHighVip(salesOrder.getVipCard())){
					if (product.getDiscountRate() > ChainStoreConf.VIP_DISCOUNT_2020_SPRING){
						response.setQuickValue(Response.ERROR, "金卡和家人卡会员购买2021秋季 的产品,折扣不能高于 " + ChainStoreConf.VIP_DISCOUNT_2020_SPRING);
						break;
					}
				} else 
					if (product.getDiscountRate() > ChainStoreConf.NORMAL_DISCOUNT_2020_SPRING){
						response.setQuickValue(Response.ERROR, "客户购买2021年秋季的产品,折扣不能高于 " + ChainStoreConf.NORMAL_DISCOUNT_2020_SPRING);
						break;
					}
			} 
			
			if (!specialBrandWinterSet.contains(brand.getBrand_ID()) && (year.getYear_ID() == specialYear && quarter.getQuarter_ID() == specialQuarterWinter) && (conf == null || (conf != null && conf.getDiscount2020Winter() == ChainStoreConf.DISCOUNT_2020_ENABLE))){
				if (isHighVip(salesOrder.getVipCard())){
					if (product.getDiscountRate() > ChainStoreConf.VIP_DISCOUNT_2020_SPRING){
						response.setQuickValue(Response.ERROR, "金卡和家人卡会员购买2021冬季 的产品,折扣不能高于 " + ChainStoreConf.VIP_DISCOUNT_2020_SPRING);
						break;
					}
				} else 
					if (product.getDiscountRate() > ChainStoreConf.NORMAL_DISCOUNT_2020_SPRING){
						response.setQuickValue(Response.ERROR, "客户购买2021年冬季的产品,折扣不能高于 " + ChainStoreConf.NORMAL_DISCOUNT_2020_SPRING);
						break;
					}
			} 
		}

		return response;
	}
	
	
	
	private boolean isHighVip(ChainVIPCard vipCard) {
		if (vipCard != null){
			int vipId = vipCard.getId();
			vipCard = chainVIPCardImpl.get(vipId, true);
			if (vipCard == null || vipCard.getVipType().getId() == ChainVIPType.VIP2_ID)
				return false;
			else {
				return true;
			}
		} else 
			return false;
	}

	@Transactional
	private boolean isExclucded(int id) {
		ProductBarcode pb = productBarcodeDaoImpl.get(id, true);
		if (pb != null) {
			productDaoImpl.initialize(pb.getProduct());
			Quarter quarter = pb.getProduct().getQuarter();
			
			if (quarter.getQuarter_ID() == Quarter.QUARTER_FOUR_SEARSON)
				return true;
		}
		return false;
	}


	


	/**
	 * to validate the chain sales order
	 * 1. 如果没有配置可以跨连锁店过账，不能跨连锁店过账
	 * @param salesOrder
	 * @return
	 */
	private void validateChainSalesOrder(ChainStoreSalesOrder salesOrder, Response response) {
		ChainVIPCard vipCard = salesOrder.getVipCard();
		//1. check the vip card
		if (vipCard != null){
			vipCard = chainVIPCardImpl.get(vipCard.getId(), true);
			if (vipCard.getStatus() != ChainVIPCard.STATUS_GOOD){
				response.setQuickValue(Response.ERROR, "此VIP卡已经处于非正常状态，无法使用");
				return ;
			}
			
			List<Double> results = chainVIPService.getVIPCardTotalScore(vipCard.getId()) ;
			double totalScore = results.get(0);
			double totalVipPrepaid = results.get(2);
			
			//计算 账面的金额需要多少积分才能处理
			double requiredScore = chainVIPService.getVIPCardRequiredScore(vipCard.getId(), salesOrder.getVipScore());
			
			
			//检查 vip卡的开卡 连锁店 是否和当前过账连锁店一致
			//如果不一致，检查是否已经设置了可以在关联连锁店过账。如果没有设置，报错
			//如果设置了，检查是否是关联连锁店，如果不是关联连锁店，报错
			if (salesOrder.getChainPrepaidAmt() > 0) {
				int chainId_sale = salesOrder.getChainStore().getChain_id();
				int chainId_vip = vipCard.getIssueChainStore().getChain_id();
				ChainStoreConf conf = chainStoreConfDaoImpl.getChainStoreConfByChainId(chainId_sale);
				if (chainId_sale != chainId_vip){
					if (conf != null && conf.getAllowMyPrepaidCrossStore() == ChainStoreConf.ALLOW_MY_PREPAID_CROSS){
						Set<Integer> chainStoreAssociated = chainStoreGroupDaoImpl.getChainGroupStoreIdList(chainId_vip, null, Common_util.CHAIN_ACCESS_LEVEL_3);
						if (!chainStoreAssociated.contains(chainId_sale)){
							response.setQuickValue(Response.ERROR, "预存金  只能在VIP卡的开户连锁店/关联连锁店中使用");
							return ;
						}	
					} else {
						response.setQuickValue(Response.ERROR, "预存金  只能在VIP卡的开户连锁店/关联连锁店中使用");
						return ;
					}
				}
			} else if (salesOrder.getChainPrepaidAmt() < 0){
				response.setQuickValue(Response.ERROR, "预存金  必须为大于或者等于0");
				return ;
			}
			if (salesOrder.getChainPrepaidAmt()!= 0 && salesOrder.getChainPrepaidAmt() > totalVipPrepaid){
				response.setQuickValue(Response.ERROR, "超过此VIP卡剩余预存金: " + totalVipPrepaid);
				return ;
			}
			
			if (salesOrder.getVipScore()!= 0 && requiredScore > totalScore){
				response.setQuickValue(Response.ERROR, "当前vip的积分不够抵扣 : " + totalScore + " , " + requiredScore);
				return ;
			}else if (salesOrder.getVipScore() < 0) {
				response.setQuickValue(Response.ERROR, "积分换现金  必须为大于或者等于0");
				return ;
			}
		} else {
			if (salesOrder.getVipScore() != 0)
				response.setQuickValue(Response.ERROR, "没有VIP卡，请清空 积分换现金 项目");
		}
	}

	/**
	 * to update the vip score 
	 * @param salesOrder
	 * @param statusNew
	 */
    private void updateVipScorePrepaid(ChainStoreSalesOrder salesOrder, int statusNew) {
    	ChainStore chainStore = salesOrder.getChainStore();
    	
		ChainVIPCard vipCard = salesOrder.getVipCard();
		if (vipCard != null && vipCard.getId() != 0){
			vipCard = chainVIPCardImpl.get(vipCard.getId(), true);
			ChainVIPType vipType = vipCard.getVipType();
			int chainId = salesOrder.getChainStore().getChain_id();
			double couponRatio = vipType.getCouponRatio();
			
			/**
			 * 1.  to calculate the revenue to accumulate the vip
			 * 1.1 to calculate the offset
			 */
			boolean isCancel = false;
			if (statusNew == ChainStoreSalesOrder.STATUS_CANCEL)
				isCancel = true;

			int offset = isCancel ? -1 : 1;
			int offsetPrepaid = isCancel ? 1 : -1;
			
			/**
			 * 1.2. to calculate the coupon
			 */
			double salesValue = salesOrder.getNetAmount() - salesOrder.getNetAmountR();
//			double coupon = (salesValue - salesOrder.getDiscountAmount() - salesOrder.getCoupon()) * offset * couponRatio;
			double coupon = (salesOrder.getAlipayAmount() + salesOrder.getWechatAmount() + salesOrder.getCardAmount() + salesOrder.getCashAmount() + salesOrder.getChainPrepaidAmt() - salesOrder.getReturnAmount()) * offset * couponRatio;
			/**
			 * 1.3. calcualte the order vip score
			 */
			/**
			 * 1.3.1 计算是否需要积分加倍，
			 * - 会员生日
			 * - 会员日 每月8号
			 * - 新店开业
			 */
			int multiple = 1;
			String comment = "";
			Date orderDate = salesOrder.getOrderDate();
//			if (orderDate.getDate() == Common_util.VIP_DATE){
//				multiple = 2;
//				comment = "会员日双倍积分";
//			} else {
				Date vipBirthDate = vipCard.getCustomerBirthday();
				if (orderDate.getMonth() == vipBirthDate.getMonth() && orderDate.getDate() == vipBirthDate.getDate()){
					multiple = 2;
					comment = "VIP生日双倍积分";
				} else {
					ChainStoreConf conf = chainStoreConfDaoImpl.getChainStoreConfByChainId(chainStore.getChain_id());
					if (conf != null)
						if (conf.getDefaultVipScoreMultiple() > 1){
							multiple = conf.getDefaultVipScoreMultiple();
							comment = "连锁店活动,加倍积分";
						}
				}
//			}
			
			int orderId = salesOrder.getId();

			if (isCancel)
				comment = "红冲 ";
			if (salesOrder.getChainPrepaidAmt() != 0)
				comment += "预付款消费" + Common_util.roundDouble(salesOrder.getChainPrepaidAmt(),0);
			ChainVIPScore vipScoreObj = new ChainVIPScore(chainId,vipCard.getId(), ChainVIPScore.TYPE_SALE, orderId, Common_util.getDecimalDouble(salesValue), Common_util.getDecimalDouble(coupon * multiple), comment);
			chainVIPScoreImpl.save(vipScoreObj, false);
			
			/**
			 * 1.4 calculate the extra vip score
			 */
			double extralVip = salesOrder.getExtralVipScore();
			if (extralVip != 0){
				ChainVIPScore vipScoreObj3 = new ChainVIPScore(chainId,vipCard.getId(),ChainVIPScore.TYPE_SALE, orderId, Common_util.getDecimalDouble(salesValue), extralVip * offset);
				chainVIPScoreImpl.save(vipScoreObj3, false);
			}
			
			/**
			 * 2. to reduce the vip score if used
			 */
			double vipScore = salesOrder.getVipScore();
			if (vipScore != 0){
				double vipCashRatio =  Common_util.VIP_CASH_RATIO;
				ChainStoreConf chainStoreConf = chainStoreConfDaoImpl.get(vipCard.getIssueChainStore().getChain_id(), true);
				if (chainStoreConf != null)
					vipCashRatio = chainStoreConf.getVipScoreCashRatio();
				
				double vipCoupon = ChainVIPService.calculateVIPScore(vipScore, vipCashRatio) * offset * (-1);
				double salesValue2 = salesValue;
				ChainVIPScore vipScoreObj2 = new ChainVIPScore(chainId, vipCard.getId(),ChainVIPScore.TYPE_SALE, orderId, Common_util.getDecimalDouble(salesValue2), Common_util.getDecimalDouble(vipCoupon));
				
				chainVIPScoreImpl.save(vipScoreObj2, false);
			}
			
			/**
			 * 3.0 计算vip的预存款
			 */
			double vipPrepaidAmt = salesOrder.getChainPrepaidAmt();
			if (vipPrepaidAmt != 0){
//				int chainId_sale = salesOrder.getChainStore().getChain_id();
//				int chainId_vip = vipCard.getIssueChainStore().getChain_id();
				
				if (isCancel){
					int vipPrepaidOrderId = salesOrder.getVipPrepaidOrderId();
					if (vipPrepaidOrderId > 0){
						ChainVIPPrepaidFlow vipPrepaid = chainVIPPrepaidImpl.get(vipPrepaidOrderId, true);
						if (vipPrepaid != null){
							vipPrepaid.setStatus(ChainVIPPrepaidFlow.STATUS_CANCEL);
							chainVIPPrepaidImpl.update(vipPrepaid, true);
						} else 
							loggerLocal.error("错误 : 单据" + salesOrder.getId() + " 的预付款无法找到对应单子，" + vipPrepaidAmt + "," + vipPrepaidOrderId);
					} else 
						loggerLocal.error("错误 : 单据" + salesOrder.getId() + " 的预付款无法找到对应单子，" + vipPrepaidAmt + "," + vipPrepaidOrderId);
				} else {
					ChainVIPPrepaidFlow vipPrepaid = new ChainVIPPrepaidFlow();
					String commentHeader = "";
					if (chainStore.getChain_id() != vipCard.getIssueChainStore().getChain_id())
						commentHeader = chainStore.getChain_name();

					vipPrepaid.setComment(commentHeader + " 单据 :" + salesOrder.getId());
	                vipPrepaid.setOperationType(ChainVIPPrepaidFlow.OPERATION_TYPE_CONSUMP);
					vipPrepaid.setAmount(vipPrepaidAmt * offsetPrepaid);
					vipPrepaid.setCalculatedAmt(vipPrepaidAmt * offsetPrepaid);
					vipPrepaid.setChainStore(salesOrder.getChainStore());
					vipPrepaid.setDateD(salesOrder.getOrderDate());
					vipPrepaid.setCreateDate(Common_util.getToday());
					ChainUserInfor operator = salesOrder.getSaler();
					vipPrepaid.setOperator(operator);
					vipPrepaid.setVipCard(vipCard);
					chainVIPPrepaidImpl.save(vipPrepaid, true);
					
					salesOrder.setVipPrepaidOrderId(vipPrepaid.getId());
					chainStoreSalesOrderDaoImpl.update(salesOrder, true);
				}

			}
			
		}
	}

	/**
     * 1. to get the product's cost price from the sales history
     * 2. if doesn't exist, get it from the product table
     * 3. calculate this order's cost
     * 4. calculate the free order's current sales price
     * @param salesOrder
     */
	private void calculateCostPrice(ChainStoreSalesOrder salesOrder) {
		
		int clientId = salesOrder.getChainStore().getClient_id();
		Set<ChainStoreSalesOrderProduct> chainStoreSalesOrderProducts = salesOrder.getProductSet();
		double totalCost = 0;
		double totalCostF = 0;
		for (ChainStoreSalesOrderProduct orderProduct : chainStoreSalesOrderProducts){
			int productId = orderProduct.getProductBarcode().getId();
			
			HeadQSalesHistoryId id = new HeadQSalesHistoryId(productId, clientId);
			HeadQSalesHistory history = headQSalesHisDAOImpl.get(id, true);
			
			if (history != null){
				double costPrice = history.getWholePrice();
				orderProduct.setCostPrice(costPrice);
				
//				double salePrice = history.getSalesPrice();
//				orderProduct.setRetailPrice(salePrice);
			} else {
				 ChainStore childStore = chainStoreService.getChildChainStore(salesOrder.getChainStore().getChain_id());
				 if (childStore != null){
						HeadQSalesHistoryId idChild = new HeadQSalesHistoryId(productId, childStore.getClient_id());
						HeadQSalesHistory historyChild = headQSalesHisDAOImpl.get(idChild, true);
						
						if (historyChild != null){
							loggerLocal.error("INOF  : 从子账户获取价格信息 : " + productId + " , " + clientId);
							double cost = historyChild.getWholePrice();
							orderProduct.setCostPrice(cost);
					    } else {
							loggerLocal.error("Error " + ERRORS.ERROR_NO_HISTORY + " : 无法获取销售货品历史价格 : " + productId + " , " + clientId);
							
							ProductBarcode productBarcode2 = productBarcodeDaoImpl.get(productId, true);
							orderProduct.setCostPrice(ProductBarcodeDaoImpl.getWholeSalePrice(productBarcode2));
							
//							double salePrice = productBarcode2.getProduct().getSalesPrice();
//							orderProduct.setRetailPrice(salePrice);
						}
			    } else {
					loggerLocal.error("Error " + ERRORS.ERROR_NO_HISTORY + " : 无法获取销售货品历史价格 : " + productId + " , " + clientId);
					
					ProductBarcode productBarcode2 = productBarcodeDaoImpl.get(productId, true);
					orderProduct.setCostPrice(ProductBarcodeDaoImpl.getWholeSalePrice(productBarcode2));
					
//					double salePrice = productBarcode2.getProduct().getSalesPrice();
//					orderProduct.setRetailPrice(salePrice);
			    }
			}
			
			
			if (orderProduct.getType() == ChainStoreSalesOrderProduct.SALES_OUT)
			   totalCost += orderProduct.getQuantity() * orderProduct.getCostPrice();
			else if (orderProduct.getType() == ChainStoreSalesOrderProduct.RETURN_BACK)
			   totalCost += orderProduct.getQuantity() * orderProduct.getCostPrice() * -1;
			else if (orderProduct.getType() == ChainStoreSalesOrderProduct.FREE){
			   totalCostF += orderProduct.getQuantity() * orderProduct.getCostPrice(); 
			}
			
		}
		
		salesOrder.setTotalCost(Common_util.roundDouble(totalCost,2));
		salesOrder.setTotalCostF(Common_util.roundDouble(totalCostF,2));
		
		//2. 计算qxquantity, qxamount, myquantity, myamount, etc
		int qxQuantity = 0;
		double qxAmount = 0;
		double qxCost = 0;
		int myQuantity = 0;
		double myAmount = 0;
		double myCost = 0;

		for (ChainStoreSalesOrderProduct orderProduct : chainStoreSalesOrderProducts){
			int productId = orderProduct.getProductBarcode().getId();
			ProductBarcode pb = productBarcodeDaoImpl.get(productId, true);
			
			int factor = 0;
			if (orderProduct.getType() == ChainStoreSalesOrderProduct.SALES_OUT)
				factor = 1;
			else if (orderProduct.getType() == ChainStoreSalesOrderProduct.RETURN_BACK)
				factor = -1;
			
			if (pb.getChainStore() == null){
				qxQuantity += orderProduct.getQuantity() * factor;
				qxCost += orderProduct.getQuantity() * orderProduct.getCostPrice() * factor;
				qxAmount += orderProduct.getQuantity() * orderProduct.getRetailPrice() * orderProduct.getDiscountRate() * factor;
			} else {
				myQuantity += orderProduct.getQuantity() * factor;
				myCost += orderProduct.getQuantity() * orderProduct.getCostPrice() * factor;
				myAmount += orderProduct.getQuantity() * orderProduct.getRetailPrice() * orderProduct.getDiscountRate() * factor;
			}
		}
		
		salesOrder.setQxQuantity(qxQuantity);
		salesOrder.setQxAmount(qxAmount);
		salesOrder.setQxCost(qxCost);
		salesOrder.setMyQuantity(myQuantity);
		salesOrder.setMyAmount(myAmount);
		salesOrder.setMyCost(myCost);
	}

	/**
	 * to prepare the UI components for the drop down list and so on
	 * @param userInfor
	 * @return
	 */
	public void prepareSearchSalesOrderUI(
			ChainUserInfor userInfor, ChainSalesActionUIBean uiBean, ChainSalesActionFormBean formBean) {
		//1. set the store list
		int chainId = 0;
		ChainStore chainStore = null;
		if (ChainUserInforService.isMgmtFromHQ(userInfor)){
			chainStore = ChainStoreDaoImpl.getAllChainStoreObject();
		} else {
			chainId = userInfor.getMyChainStore().getChain_id();
			chainStore = chainStoreService.getChainStoreByID(chainId);
		}
		formBean.setChainStore(chainStore);

		
		//2. set the sales person in the chain store
		List<ChainUserInfor> salers = new ArrayList<ChainUserInfor>();
		salers.addAll(chainUserInforDaoImpl.getActiveChainUsersByChainStore(chainId));
		uiBean.setChainSalers(salers);
		
		//3. set the sales order type list
		ChainStoreSalesOrder chainStoreSalesOrder = new ChainStoreSalesOrder();
		Map<Integer, String> types = chainStoreSalesOrder.getTypeChainMap();
		uiBean.setChainOrderTypes(types);
		
		//4. set the sales order status list
		Map<Integer, String> status = chainStoreSalesOrder.getStatusMap();
		uiBean.setChainOrderStatus(status);
	}



	/**
	 * function to search the draft sales order list for the header information
	 * no need to initialize all information
	 * @param formBean
	 * @return
	 */
	public Response searchSalesOrders(
			ChainSalesActionFormBean formBean, ChainUserInfor loginUser) {
		Response response = new Response();
		
		boolean cache = false;
		Pager pager = formBean.getPager();
		List<ChainStoreSalesOrder> chainStoreSalesOrders = new ArrayList<ChainStoreSalesOrder>();
		
		if (pager.getTotalPage() == 0){
		    DetachedCriteria criteria = buildSearchSalesCriteria(formBean, loginUser);
			criteria.setProjection(Projections.rowCount());
			int totalRecord = Common_util.getProjectionSingleValue(chainStoreSalesOrderDaoImpl.getByCriteriaProjection(criteria, false));
			pager.initialize(totalRecord);
		} else {
			pager.calFirstResult();
		}
		
		DetachedCriteria criteria2 = buildSearchSalesCriteria(formBean, loginUser);
		
	    //1. 搜索单据
	    criteria2.addOrder(Order.asc("chainStore.chain_id"));
	    criteria2.addOrder(Order.asc("orderDate"));
	    criteria2.addOrder(Order.asc("orderCreateDate"));
	    chainStoreSalesOrders = chainStoreSalesOrderDaoImpl.getByCritera(criteria2,pager.getFirstResult(), pager.getRecordPerPage(), cache);

	    //2. 汇总单据信息
	    ChainStoreSalesOrder totalRecord = new ChainStoreSalesOrder();
	    if (chainStoreSalesOrders != null && chainStoreSalesOrders.size() >0){
			DetachedCriteria criteriaTotal = buildSearchSalesCriteria(formBean, loginUser);
			ProjectionList projectionList = Projections.projectionList();
			projectionList.add(Projections.sum("totalQuantity"));			
			projectionList.add(Projections.sum("netAmount"));
			projectionList.add(Projections.sum("totalQuantityR"));
			projectionList.add(Projections.sum("netAmountR"));
			projectionList.add(Projections.sum("totalQuantityF"));
			projectionList.add(Projections.sum("totalAmount"));
			criteriaTotal.setProjection(projectionList);
			List<Object> totalObjects = chainStoreSalesOrderDaoImpl.getByCriteriaProjection(criteriaTotal, true);
			Object[] resultObjects = (Object[])totalObjects.get(0);

			totalRecord.setTotalQuantity(Common_util.getInt(resultObjects[0]));
			totalRecord.setNetAmount(Common_util.getDouble(resultObjects[1]));
			totalRecord.setTotalQuantityR(Common_util.getInt(resultObjects[2]));
			totalRecord.setNetAmountR(Common_util.getDouble(resultObjects[3]));
			totalRecord.setTotalQuantityF(Common_util.getInt(resultObjects[4]));
			totalRecord.setTotalAmount(Common_util.getDouble(resultObjects[5]));
	    }
		
		List<Object> result = new ArrayList<Object>();
		result.add(chainStoreSalesOrders);
		result.add(totalRecord);
		
		response.setReturnCode(Response.SUCCESS);
		response.setReturnValue(result);

		return response;
	}
	
	private DetachedCriteria buildSearchSalesCriteria(ChainSalesActionFormBean formBean, ChainUserInfor loginUser){
		DetachedCriteria criteria = DetachedCriteria.forClass(ChainStoreSalesOrder.class);
		
		int orderId = formBean.getChainSalesOrder().getId();
		int chainStoreId = formBean.getChainStore().getChain_id();
		
		//在Orderid > 0, 情况下， 只需要看连锁店这一个关键条件
		if (orderId > 0){
			criteria.add(Restrictions.eq("id", orderId));
			
			if (!ChainUserInforService.isMgmtFromHQ(loginUser)){
			    criteria.add(Restrictions.eq("chainStore.chain_id", chainStoreId));
			}
			
			return criteria;
		}
		
		
		ChainUserInfor saler = formBean.getChainSalesOrder().getSaler();
		if (chainStoreId != Common_util.ALL_RECORD)
		   criteria.add(Restrictions.eq("chainStore.chain_id", chainStoreId));
		
		if (saler != null) {
			int salerId = saler.getUser_id();
			if (salerId != Common_util.ALL_RECORD)
				criteria.add(Restrictions.eq("saler.user_id", salerId));
		}
		
		int status = formBean.getChainSalesOrder().getStatus();
		if (status != Common_util.ALL_RECORD)
		    criteria.add(Restrictions.eq("status", status));
		else 
			criteria.add(Restrictions.ne("status", ChainStoreSalesOrder.STATUS_DELETED));
		
//		int type = formBean.getChainSalesOrder().getType();
//		if (type != Common_util.ALL_RECORD)
//		    criteria.add(Restrictions.eq("type", type));
		if (formBean.getSearch_Start_Time() != null && formBean.getSearch_End_Time() != null){
			Date end_date = Common_util.formEndDate(formBean.getSearch_End_Time());
			criteria.add(Restrictions.between("orderDate",formBean.getSearch_Start_Time(),end_date));
		}
		
		int productId = formBean.getProductId();
		if (productId > 0){
			DetachedCriteria orderProductCriteria = criteria.createCriteria("productSet");
			orderProductCriteria.add(Restrictions.eq("productBarcode.id", productId));
		}
		
		int chainPayType = formBean.getChainOrderPay();
		if (chainPayType != Common_util.ALL_RECORD){
			switch (chainPayType) {
			case ChainSalesActionUIBean.CHAIN_ORDER_PAY_CASH:
				criteria.add(Restrictions.ne("cashAmount", 0.0));
				break;
			case ChainSalesActionUIBean.CHAIN_ORDER_PAY_CARD:
				criteria.add(Restrictions.ne("cardAmount", 0.0));
				break;
			case ChainSalesActionUIBean.CHAIN_ORDER_PAY_WECHAT:
				criteria.add(Restrictions.ne("wechatAmount", 0.0));
				break;
			case ChainSalesActionUIBean.CHAIN_ORDER_PAY_ALIPAY:
				criteria.add(Restrictions.ne("alipayAmount", 0.0));
				break;
			case ChainSalesActionUIBean.CHAIN_ORDER_PAY_COUPON:
				criteria.add(Restrictions.ne("coupon", 0.0));
				break;
			case ChainSalesActionUIBean.CHAIN_ORDER_PAY_PREPAY:
				criteria.add(Restrictions.ne("chainPrepaidAmt", 0.0));
				break;
			case ChainSalesActionUIBean.CHAIN_ORDER_PAY_VIPSCORE:
				criteria.add(Restrictions.ne("vipScore", 0.0));
				break;			
			default:
				break;
			}
		}
		
		return criteria;
	}
	
	public ChainStoreConf getChainStoreConf(int chainStoreId){
		ChainStoreConf conf = chainStoreConfDaoImpl.get(chainStoreId, true);
		
		return conf;
	}

	/**
	 * function to get the sales order by id
	 * @param orderId
	 * @param userInfor
	 * @return
	 */
	@Transactional
	public ChainStoreSalesOrder getSalesOrderById(int orderId,
			ChainUserInfor user) {
		ChainStoreSalesOrder salesOrder = chainStoreSalesOrderDaoImpl.get(orderId, false);
		if (validateChainSalesOrderAccess(salesOrder, user)){
			//1. initialize the order products
			chainStoreSalesOrderDaoImpl.initialize(salesOrder);

			//2. if the order is draft order, we need update the product inventory level
			if (salesOrder.getStatus() == ChainStoreSalesOrder.STATUS_DRAFT){
				initializeInventoryLevel(salesOrder.getProductSet(),salesOrder.getChainStore().getClient_id());
			}

			salesOrder.putSetToList();
			
			return salesOrder;
		}
		return null;
	}
	
	/**
	 * function to initialize the inventory level
	 * @param productSet
	 */
	private void initializeInventoryLevel(
			Set<ChainStoreSalesOrderProduct> productSet, int clientId) {
		Iterator<ChainStoreSalesOrderProduct> chainProductIterator = productSet.iterator();
		Set<String> barcodes = new HashSet<String>();
		while (chainProductIterator.hasNext())
			barcodes.add(chainProductIterator.next().getProductBarcode().getBarcode());
		Map<String, Integer> inventoryLevelMap = chainInventoryFlowOrderService.getProductsInventoryLevel(barcodes, clientId);
		
		chainProductIterator  = productSet.iterator();
		while (chainProductIterator.hasNext()){
			ChainStoreSalesOrderProduct chainProduct = chainProductIterator.next();
			String productKey = chainProduct.getProductBarcode().getBarcode();
		    Integer inventoryLevel = inventoryLevelMap.get(productKey);
		    
		    if (inventoryLevel != null)
		    	chainProduct.setInventoryLevel(inventoryLevel);
		}
		
	}

	/**
	 * to update the chain in/out stock table information
	 * @param salesOrder
	 * @param orderStatus
	 */
	@Transactional
	private void updateChainInOutStock(ChainStoreSalesOrder salesOrder,
			int orderStatus) {
		boolean isCancel = false;
		int orderType = salesOrder.getType();
		
		int clientId = salesOrder.getChainStore().getClient_id();
		int chainId = salesOrder.getChainStore().getChain_id();
		if (orderStatus == ChainStoreSalesOrder.STATUS_CANCEL)
			isCancel = true;
		
		String orderId = String.valueOf(salesOrder.getId());
		int offset = isCancel ? -1 : 1;
		String orderIdHead = isCancel ? "C" : "";

		if (orderType == ChainStoreSalesOrder.SALES){
			orderId = ChainInOutStock.CHAIN_SALES + orderIdHead + orderId;
		} else if (orderType == ChainStoreSalesOrder.SALES){
			orderId = ChainInOutStock.CHAIN_RETURN + orderIdHead + orderId;	
		} else 
			orderId = ChainInOutStock.CHAIN_EXCHANGE + orderIdHead + orderId;	
		
		 Iterator<ChainStoreSalesOrderProduct> orderProducts = salesOrder.getProductSet().iterator();
		 while (orderProducts.hasNext()){
			 ChainStoreSalesOrderProduct orderProduct = orderProducts.next();
			 int offsetR = offset;

			 int recordType = orderProduct.getType();
			 if (recordType == ChainStoreSalesOrderProduct.SALES_OUT || recordType == ChainStoreSalesOrderProduct.FREE)
				 offsetR *= -1;
			 else 
				 offsetR *= 1; 
		 
			 int productBarcodeId = orderProduct.getProductBarcode().getId();
			 String barcode = orderProduct.getProductBarcode().getBarcode();
			 double salePrice = orderProduct.getRetailPrice();
			 double discount = orderProduct.getDiscountRate();
			 int quantity = orderProduct.getQuantity() * offsetR;
			 
			 //get the sales price
			 HeadQSalesHistoryId historyId = new HeadQSalesHistoryId(productBarcodeId, clientId);
			 HeadQSalesHistory priorHistory = headQSalesHisDAOImpl.get(historyId, true);
			 double cost = 0;
			 if (priorHistory != null)
				 cost = priorHistory.getWholePrice();
			 else {
				 ChainStore childStore = chainStoreService.getChildChainStore(chainId);
					if (childStore != null){
						HeadQSalesHistoryId idChild = new HeadQSalesHistoryId(productBarcodeId, childStore.getClient_id());
						HeadQSalesHistory historyChild = headQSalesHisDAOImpl.get(idChild, true);
						
						if (historyChild != null){
						   cost = historyChild.getWholePrice();
					    } else {
				 
							 ChainInitialStockId initialStockId = new ChainInitialStockId(barcode, clientId);
							 ChainInitialStock initialStock = chainInitialStockDaoImpl.get(initialStockId, true);
							 if (initialStock != null)
								 cost = initialStock.getCost();
							 else {
								 loggerLocal.error(ERRORS.ERROR_NO_COST + " 找不到货品进价: " + clientId + "," + barcode + "," + productBarcodeId + "," + orderProduct.getProductBarcode().getProduct().getProductCode());
								 ProductBarcode pBarcode = productBarcodeDaoImpl.get(productBarcodeId, true);
								 cost = productBarcodeDaoImpl.getWholeSalePrice(pBarcode);
							 }
						 }
					} else {
						 ChainInitialStockId initialStockId = new ChainInitialStockId(barcode, clientId);
						 ChainInitialStock initialStock = chainInitialStockDaoImpl.get(initialStockId, true);
						 if (initialStock != null)
							 cost = initialStock.getCost();
						 else {
							 loggerLocal.error(ERRORS.ERROR_NO_COST + " 找不到货品进价: " + clientId + "," + barcode + "," + productBarcodeId + "," + orderProduct.getProductBarcode().getProduct().getProductCode());
							 ProductBarcode pBarcode = productBarcodeDaoImpl.get(productBarcodeId, true);
							 cost = productBarcodeDaoImpl.getWholeSalePrice(pBarcode);
						 }
					}
			 }
			 
			 double chainSalePrice = productBarcodeDaoImpl.get(productBarcodeId, true).getProduct().getSalesPrice();
			 
			 ChainInOutStock inOutStock = new ChainInOutStock(barcode, clientId, orderId, orderProduct.getType() , cost, cost * quantity, salePrice * discount, salePrice * quantity * discount,chainSalePrice*quantity, quantity,orderProduct.getProductBarcode());
//			 System.out.println(barcode+","+ clientId+","+ orderId+","+  orderProduct.getType());
			 
			 chainInOutStockDaoImpl.save(inOutStock, false);
		 }
		
	}
	
	/**
	 * to validate whether the user could get the order information
	 * @param salesOrder
	 * @param user
	 * @return
	 */
	private boolean validateChainSalesOrderAccess(ChainStoreSalesOrder salesOrder, ChainUserInfor user){
		if (ChainUserInforService.isMgmtFromHQ(user))
			return true;
		else if (user.getMyChainStore()== null || (salesOrder.getChainStore().getChain_id() != user.getMyChainStore().getChain_id()))
			return false;
		else {
			return true;
		}
	}

	/**
	 * when the chain store is changed
	 * @param chainStoreId
	 * @return
	 */
	public List<Object> changeChainStore(int chainStoreId) {
		//1. get the user list
		List<ChainUserInfor> users = chainUserInforDaoImpl.getActiveChainUsersByChainStore(chainStoreId);
		
		//2. get the chainStoreConf
		ChainStoreConf chainStoreConf = chainStoreConfDaoImpl.getChainStoreConfByChainId(chainStoreId);
		if (chainStoreConf == null)
			chainStoreConf = new ChainStoreConf();
		
		//3. get the chainStore
		ChainStore chainStore = chainStoreService.getChainStoreByID(chainStoreId);
		
		List<Object> uiList = new ArrayList<Object>();
		uiList.add(users);
		uiList.add(chainStoreConf);
		uiList.add(chainStore);

		return uiList;
	}

	public void clearOrderUnusedRecord(ChainSalesActionFormBean formBean) {
		ChainStoreSalesOrder salesOrder = formBean.getChainSalesOrder();
		deleteUnusedRecord(salesOrder.getProductListR());
		deleteUnusedRecord(salesOrder.getProductListF());
		deleteUnusedRecord(salesOrder.getProductList());
	}
	
	private void deleteUnusedRecord(List<ChainStoreSalesOrderProduct> saleProducts){
		for (int i = 0; i < saleProducts.size(); i++ ){
			ChainStoreSalesOrderProduct orderProduct = saleProducts.get(i);
			if (orderProduct == null || orderProduct.getProductBarcode().getBarcode().equals("") || orderProduct.getQuantity() == 0)
				saleProducts.remove(i);
		}
	}
	
	/**
	 * to set the parm based for sales module
	 * @param formBean
	 * @param userInfor
	 */
	public void calculateSalesParm(ChainSalesActionFormBean formBean, ChainUserInfor loginUser, ChainStoreSalesOrder saleOrder){
		ChainRoleType roleType = loginUser.getRoleType();
		
		//to calculate the common parameters on the UI
		ChainUtility.calculateParam(formBean, saleOrder);
		
		//the particular parameter
		if (ChainUserInforService.isMgmtFromHQ(loginUser) || roleType.isOwner())
			formBean.setCanEditOrderDate(true);
		else {
			formBean.setCanEditOrderDate(false);
		}
	}

	/**
	 * to get the product information
	 * @param barcode
	 * @return
	 */
	public ProductBarcode getProductInfo(String barcode, int chainId) {
		ProductBarcode productBarcode =  productBarcodeDaoImpl.getByBarcode(barcode);
		
		if (productBarcode != null ){
			double wholePrice = productBarcodeDaoImpl.getWholeSalePrice(productBarcode);
			
			//批发价
			productBarcode.getProduct().setLastChoosePrice(wholePrice);
			
			if (chainId > 0){
				ChainStore chainStore = chainStoreService.getChainStoreByID(chainId);
				
				//1. 先尝试获取当前店铺的采购价，如果没有再获取子连锁店采购价
				HeadQSalesHistoryId id = new HeadQSalesHistoryId(productBarcode.getId(), chainStore.getClient_id());
				HeadQSalesHistory history = headQSalesHisDAOImpl.get(id, true);
				if (history != null){
					//我的采购价
					productBarcode.getProduct().setLastInputPrice(history.getWholePrice());
				} else {
					ChainStore childStore = chainStoreService.getChildChainStore(chainStore.getChain_id());
					if (childStore != null){
						HeadQSalesHistoryId idChild = new HeadQSalesHistoryId(productBarcode.getId(), childStore.getClient_id());
						HeadQSalesHistory historyChild = headQSalesHisDAOImpl.get(idChild, true);
						if (historyChild != null){
							//我的采购价
							productBarcode.getProduct().setLastInputPrice(historyChild.getWholePrice());
						}
					}
				}
				
				int inventory = chainInOutStockDaoImpl.getProductStock(barcode, chainStore.getClient_id(), false);
				productBarcode.setBoughtBefore(inventory);
				
				//设置零售价
				if (chainStore.getPriceIncrement() != null){
					ChainPriceIncrement priceIncrement = chainStore.getPriceIncrement();
					double salePrice = ChainSalesPriceDaoImpl.calPriceIncre(productBarcode.getProduct().getSalesPrice(), priceIncrement);
					productBarcode.getProduct().setSalesPrice(salePrice);
				}
				
			}
			
		}
		
		return productBarcode;
	}

	/**
	 * 为chain 定制，要求封装自己的卖价，以及库存
	 * @param productCode
	 * @param pager
	 * @return
	 */
	public List<ChainProductBarcodeVO> getProductsForSimiliarProductCode(
			String productCode, Pager pager, int chainId, ChainUserInfor loginUser) {
		ChainStore chainStore = chainStoreService.getChainStoreByID(chainId);
		
		//1. 封装chain product vo
		int scope = chainId;
		if (ChainUserInforService.isMgmtFromHQ(loginUser))
			scope = Common_util.ALL_RECORD;
		
        List<ProductBarcode> productBarcodes = productBarcodeService.getProductsForSimiliarProductCode(productCode, scope, pager);
        List<ChainProductBarcodeVO> chainProductVOs = chainSalesPriceDaoImpl.convertProductBarcodeVO(productBarcodes, chainStore);

        //2. 获取库存

		if (chainStore != null){
	        Set<String> barcodes = new HashSet<String>();
	        for (ChainProductBarcodeVO vo: chainProductVOs)
	        	barcodes.add(vo.getBarcode().trim());
	        Map<String, Integer> inventoryMap = chainInventoryFlowOrderService.getProductsInventoryLevel(barcodes, chainStore.getClient_id());
	        Integer inventory = null;
	        for (ChainProductBarcodeVO vo: chainProductVOs){
	        	inventory = inventoryMap.get(vo.getBarcode());
	        	if (inventory != null)
	        		vo.setInventoryLevel(inventory);
	        }
		}

		return chainProductVOs;
	}

	/**
	 * 获取chain 封装的product information
	 * @param barcode
	 * @param chainId
	 * @return
	 */
	public ChainProductBarcodeVO getChainProductInfo(String barcode, int chainId) {
		ProductBarcode productBarcode =  productBarcodeDaoImpl.getByBarcode(barcode);
		ChainProductBarcodeVO vo = new ChainProductBarcodeVO();
		if (productBarcode != null ){
			ChainStore chainStore = chainStoreService.getChainStoreByID(chainId);
			vo = chainSalesPriceDaoImpl.convertProductBarcodeVO(productBarcode, chainStore, 0);
					
//			if (chainId > 0){
//				int inventory = chainInOutStockDaoImpl.getProductStock(barcode, chainStore.getClient_id(), false);
//				productBarcode.setBoughtBefore(inventory);
//			}
		}
		
		return vo;
	}

	/**
	 * returnCode 
	 * 1: 只有统一零售价
	 * 2：  统一零售价 + 我的零售价
	 * 3: 我的零售价
	 * @param chainStore
	 * @param userInfor
	 * @return
	 */
	public Response getReturnPage(ChainStore chainStore,
			ChainUserInfor userInfor) {
		Response response = new Response();
		
		if (chainStore == null || (chainStore.getAllowChangeSalesPrice() == ChainStore.DISALLOW_CHANGE_PRICE && chainStore.getPriceIncrement() == null))
			   response.setReturnCode(1);
		else {
			ChainRoleType userRoleType = userInfor.getRoleType();
			
			if (chainStore.getAllowChangeSalesPrice() == ChainStore.ALLOW_CHANGE_PRICE || ((chainStore.getAllowChangeSalesPrice() == ChainStore.DISALLOW_CHANGE_PRICE && chainStore.getPriceIncrement() != null)) && (userRoleType.isOwner() || userRoleType.isAdmin() || userRoleType.isMgmt()))
				response.setReturnCode(2);
			else 
				response.setReturnCode(3);
		}
		return response;
	}


}
