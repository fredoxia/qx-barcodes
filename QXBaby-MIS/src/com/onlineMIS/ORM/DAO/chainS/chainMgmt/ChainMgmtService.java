package com.onlineMIS.ORM.DAO.chainS.chainMgmt;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.beanutils.BeanUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.onlineMIS.ORM.DAO.Response;
import com.onlineMIS.ORM.DAO.chainS.inventoryFlow.ChainInOutStockDaoImpl;
import com.onlineMIS.ORM.DAO.chainS.user.ChainRoleTypeDaoImpl;
import com.onlineMIS.ORM.DAO.chainS.user.ChainStoreDaoImpl;
import com.onlineMIS.ORM.DAO.chainS.user.ChainStoreService;
import com.onlineMIS.ORM.DAO.chainS.user.ChainUserInforService;
import com.onlineMIS.ORM.DAO.headQ.barCodeGentor.BrandDaoImpl;
import com.onlineMIS.ORM.DAO.headQ.barCodeGentor.ProductBarcodeDaoImpl;
import com.onlineMIS.ORM.DAO.headQ.barCodeGentor.ProductDaoImpl;
import com.onlineMIS.ORM.DAO.headQ.barCodeGentor.QuarterDaoImpl;
import com.onlineMIS.ORM.DAO.headQ.barCodeGentor.YearDaoImpl;
import com.onlineMIS.ORM.DAO.headQ.inventory.HeadQSalesHisDAOImpl;
import com.onlineMIS.ORM.entity.base.Pager;
import com.onlineMIS.ORM.entity.chainS.chainMgmt.ChainInitialStock;
import com.onlineMIS.ORM.entity.chainS.chainMgmt.ChainPriceIncrement;
import com.onlineMIS.ORM.entity.chainS.chainMgmt.ChainSalesPrice;
import com.onlineMIS.ORM.entity.chainS.chainMgmt.ChainStoreConf;
import com.onlineMIS.ORM.entity.chainS.chainMgmt.ChainStoreGroup;
import com.onlineMIS.ORM.entity.chainS.chainMgmt.ChainStoreGroupElement;
import com.onlineMIS.ORM.entity.chainS.chainMgmt.InitialInventoryFileTemplate;
import com.onlineMIS.ORM.entity.chainS.chainMgmt.QxbabyConf;
import com.onlineMIS.ORM.entity.chainS.inventoryFlow.ChainInOutStock;
import com.onlineMIS.ORM.entity.chainS.inventoryFlow.ChainInventoryItem;
import com.onlineMIS.ORM.entity.chainS.inventoryFlow.ChainLevelTwoInventoryItem;
import com.onlineMIS.ORM.entity.chainS.user.ChainRoleType;
import com.onlineMIS.ORM.entity.chainS.user.ChainStore;
import com.onlineMIS.ORM.entity.chainS.user.ChainUserInfor;
import com.onlineMIS.ORM.entity.chainS.vip.ChainVIPCard;
import com.onlineMIS.ORM.entity.headQ.barcodeGentor.Brand;
import com.onlineMIS.ORM.entity.headQ.barcodeGentor.Category;
import com.onlineMIS.ORM.entity.headQ.barcodeGentor.Color;
import com.onlineMIS.ORM.entity.headQ.barcodeGentor.ProductBarcode;
import com.onlineMIS.ORM.entity.headQ.barcodeGentor.Quarter;
import com.onlineMIS.ORM.entity.headQ.barcodeGentor.Size;
import com.onlineMIS.ORM.entity.headQ.barcodeGentor.Year;
import com.onlineMIS.ORM.entity.headQ.inventory.HeadQSalesHistory;
import com.onlineMIS.ORM.entity.headQ.inventory.InventoryFileTemplate;
import com.onlineMIS.action.chainS.chainMgmt.ChainMgmtActionFormBean;
import com.onlineMIS.action.chainS.chainMgmt.ChainMgmtActionUIBean;
import com.onlineMIS.action.chainS.manualRpt.ChainDailyManualRptActionFormBean;
import com.onlineMIS.action.chainS.manualRpt.ChainDailyManualRptActionUIBean;
import com.onlineMIS.action.chainS.vo.ChainProductBarcodeVO;
import com.onlineMIS.action.headQ.barCodeGentor.BarcodeGenBasicData;
import com.onlineMIS.common.Common_util;
import com.onlineMIS.common.loggerLocal;
import com.onlineMIS.sorter.SortYear;

@Service
public class ChainMgmtService {
	@Autowired
	private YearDaoImpl yearDaoImpl;
	@Autowired
	private QuarterDaoImpl quarterDaoImpl;
	
	@Autowired
	private ChainRoleTypeDaoImpl chainRoleTypeDaoImpl;
	
	@Autowired
	private ChainStoreService chainStoreService;
	
	@Autowired
	private ProductBarcodeDaoImpl productBarcodeDaoImpl;
	
	@Autowired
	private ChainInitialStockDaoImpl chainInitialStockDaoImpl;
	
	@Autowired
	private HeadQSalesHisDAOImpl headQSalesHisDAOImpl;
	
	@Autowired
	private ChainStoreConfDaoImpl chainConfDaoImpl;
	
	@Autowired
	private ProductDaoImpl productDaoImpl;
	
	@Autowired
	private BrandDaoImpl  brandDaoImpl;
	
	@Autowired
	private ChainInOutStockDaoImpl chainInOutStockDaoImpl;

	@Autowired
	private ChainStoreGroupDaoImpl chainStoreGroupDaoImpl;
	
	@Autowired
	private ChainStoreGroupElementDaoImpl chainStoreGroupElementDaoImpl;
	
	@Autowired
	private ChainSalesPriceDaoImpl chainSalesPriceDaoImpl;
	
	@Autowired
	private ChainPriceIncrementDaoImpl chainPriceIncrementDaoImpl;
	
	@Autowired
	private ChainStoreDaoImpl chainStoreDaoImpl;
	
	@Autowired
	private QxbabyConfDaoImpl qxbabyConfDaoImpl;

	
	/**
	 * to prepare the ui bean for edit chain inforamtion ui
	 * @param uiBean
	 */
	public void prepareEditChainInforUI(ChainMgmtActionUIBean uiBean) {
		
		//1. chain store list
		List<ChainStore> chainStores =  new ArrayList<ChainStore>();

		chainStores = chainStoreService.getChainStoreList();
		uiBean.setChainStores(chainStores);
			
		//2. status map
		uiBean.setStatusMap(ChainStore.getStatusMap());
		
		//3. role types
		List<ChainRoleType> chainRoleTypes = chainRoleTypeDaoImpl.getChainUserTypes();
		uiBean.setChainRoleTypes(chainRoleTypes);
		
		//4. price increment 下拉
		List<ChainPriceIncrement> chainPriceIncrements = chainPriceIncrementDaoImpl.getAll(true);
		uiBean.setPriceIncrements(chainPriceIncrements);
	}
	
	/**
	 * to prepare the UI bean for the edit initial stock ui
	 */
	public void prepareEditInitialStockUI(ChainMgmtActionUIBean uiBean){
		List<ChainStore> chainStores = chainStoreService.getChainStoreList();
		uiBean.setChainStores(chainStores);
	}
	

	/**
	 * to prepare the UI Bean for the edit
	 * @param uiBean
	 * @param userInfor
	 */
	public void prepareEditChainConfUI(ChainMgmtActionUIBean uiBean, ChainMgmtActionFormBean formBean,
			ChainUserInfor userInfor) {
		List<ChainStore> chainStores =  new ArrayList<ChainStore>();
		ChainStoreConf chainStoreConf = null;

		//1. set the chain store informations
		chainStores = chainStoreService.getChainStoreList(userInfor);
		uiBean.setChainStores(chainStores);
		
		//2. set the first chain store
		if (chainStores != null && chainStores.size() > 0){
			int chainId = chainStores.get(0).getChain_id();
			formBean.getChainStoreConf().setChainId(chainId);
			chainStoreConf = chainConfDaoImpl.getChainStoreConfByChainId(chainId);
		}
		
		//3. set the chain store configuration
		if (chainStoreConf == null)
			chainStoreConf = new ChainStoreConf();
		formBean.setChainStoreConf(chainStoreConf);
			
		//2. set the amount type map
		Map<Integer, String> amtTypeMap = new LinkedHashMap<Integer, String>();
		for (ChainStoreConf.amtTypes amtType : ChainStoreConf.amtTypes.values()) {
			amtTypeMap.put(amtType.getTypeId(), amtType.getTypeS());
		}
		uiBean.setAmtTypeMap(amtTypeMap);
		
	}

	/**
	 * 
	 * @param barcode
	 * @param chain_id
	 * @return
	 */
	public Response getChainInitialStock(String barcode, int clientId) {
		Response response = new Response();
		
		ProductBarcode product =  productBarcodeDaoImpl.getByBarcode(barcode);
		if (product != null){
			ChainInitialStock chainInitialStock = new ChainInitialStock();
			chainInitialStock.getId().setBarcode(barcode);
			chainInitialStock.getId().setClientId(clientId);
			chainInitialStock.setProductBarcode(product);
			double wholePrice = productBarcodeDaoImpl.getWholeSalePrice(product);
			chainInitialStock.setQuantity(1);
			chainInitialStock.setCost(wholePrice);
			chainInitialStock.setCostTotal(wholePrice);
			response.setReturnCode(Response.SUCCESS);
			response.setReturnValue(chainInitialStock);
		} else 
			response.setQuickValue(Response.FAIL,"无法找到货款");
		
		return response;
	}
	
	/**
	 * to save the chain initial stocks to table
	 * @param inputStocks
	 * @return
	 */
	@Transactional
	public Response saveChainInitialStock(List<ChainInitialStock> inputStocks, int clientId,File inventoryFile){
		Response response = new Response();
		
		List<ChainInitialStock> stocksInFile = parseInitialStockFile(inventoryFile, clientId);
		inputStocks.addAll(stocksInFile);
		
		/**
		 * validate the input stocks
		 */
		Collection<ChainInitialStock> groupedStocks = groupInitialStockQ(inputStocks);
		
//		for (ChainInitialStock chainInitialStock : groupedStocks){
//
//			if (!chainInitialStock.getId().getBarcode().equals("") && chainInitialStock.getQuantity() <= 0){
//				response.setQuickValue(Response.FAIL, "数量必须为整数");
//				return response;
//			}
//		}
		if (clientId == 0){
			response.setQuickValue(Response.FAIL, "连锁店为必选项");
			return response;
		}

		/**
		 * 1. prepare the data
		 */
		for (ChainInitialStock chainInitialStock : groupedStocks){
			chainInitialStock.getId().setClientId(clientId);
		}
		
		/**
		 * 2. to delete the prior initial stock and in-out stock flow
		 */
		chainInitialStockDaoImpl.deleteChainStockByClientId(clientId);
		chainInOutStockDaoImpl.deleteInitialStockFlow(clientId);
		
		/**
		 * 3. update the existing stock and update the price history
		 */
		for (ChainInitialStock chainInitialStock : groupedStocks){
			//1. 保存initial stock
			chainInitialStock.setDate(new Date());
			chainInitialStockDaoImpl.save(chainInitialStock, true);
			
			//2. 展示不能用，放到headeq才能用
			ProductBarcode productBarcode = chainInitialStock.getProductBarcode();
			HeadQSalesHistory salesHistory = new HeadQSalesHistory(productBarcode.getId(), clientId, 0 , chainInitialStock.getCost(), 0, chainInitialStock.getQuantity(), 0, 1);
			headQSalesHisDAOImpl.saveOrUpdate(salesHistory, false);
			
			//3. 保存in-out stock flow
			String barcode = chainInitialStock.getId().getBarcode();
			
			int productBarcodeId = chainInitialStock.getProductBarcode().getId();
			double chainSalePrice = productBarcodeDaoImpl.get(productBarcodeId, true).getProduct().getSalesPrice();
			 
			ChainInOutStock inOutStock = new ChainInOutStock(barcode, clientId, ChainInOutStock.DEFAULT_INITIAL_STOCK_ORDER_ID, ChainInOutStock.TYPE_INITIAL_STOCK, chainInitialStock.getCost(), chainInitialStock.getCostTotal(), chainInitialStock.getSalePrice(), chainInitialStock.getSalePriceTotal(),chainSalePrice*chainInitialStock.getQuantity(), chainInitialStock.getQuantity(), productBarcode);
			chainInOutStockDaoImpl.save(inOutStock, false);
		}

		
		response.setReturnCode(Response.SUCCESS);
		
		return response;
	}
	
	/**
	 * to parse the initial stock files
	 * @param inventory
	 * @param chain_id
	 * @return
	 */
	private List<ChainInitialStock> parseInitialStockFile(File inventoryFile,
			int clientId) {
		List<ChainInitialStock> chainInitialStocks = new ArrayList<ChainInitialStock>();
		
		if (inventoryFile != null){
			 InitialInventoryFileTemplate fileTemplate = new InitialInventoryFileTemplate(inventoryFile);
			
			Response response = fileTemplate.process();
			List<Object> result = (List<Object>)response.getReturnValue();
			Map<String, ChainInitialStock> stockMap = (Map<String, ChainInitialStock>)result.get(0);
			List<String> barcodes = (List<String>)result.get(1);
			
			//2. prepare the data
			List<ProductBarcode> products = productBarcodeDaoImpl.getProductBarcodes(new HashSet<String>(barcodes));

			for (ProductBarcode productBarcode : products){
				String barcode = productBarcode.getBarcode();
				
				ChainInitialStock chainInitialStock = stockMap.get(barcode);
				chainInitialStock.getId().setBarcode(barcode);
				chainInitialStock.getId().setClientId(clientId);
				chainInitialStock.setProductBarcode(productBarcode);
				chainInitialStock.setDate(new Date());
				
				chainInitialStocks.add(chainInitialStock);
			}
		}
		return chainInitialStocks;
	}

	private Collection<ChainInitialStock> groupInitialStockQ(List<ChainInitialStock> chainInitialStocks){
		Map<String, ChainInitialStock> stockMap = new HashMap<String, ChainInitialStock>();
		
		Set<String> barcodes = new HashSet<String>();
		for (ChainInitialStock initialStock : chainInitialStocks){
			if (initialStock != null && initialStock.getId() != null)
			   barcodes.add(initialStock.getId().getBarcode());
		}
		Map<String, ProductBarcode> productMap = productBarcodeDaoImpl.getProductMapByBarcode(barcodes);
		
		for (ChainInitialStock stock : chainInitialStocks){
			if (stock == null ||stock.getId().getBarcode().equals(""))
				continue;
			else {
				String barcode = stock.getId().getBarcode();
				ChainInitialStock stock2 = stockMap.get(barcode);
				if (stock2 == null){
					ProductBarcode productBarcode = productMap.get(barcode);
					if (productBarcode != null){
						double salePrice = productBarcode.getProduct().getSalesPrice();
						stock.setSalePrice(salePrice);
						stock.setSalePriceTotal(salePrice * stock.getQuantity());
					}
					
					stockMap.put(barcode, stock);
				} else {
					stock2.setQuantity(stock2.getQuantity() + stock.getQuantity());
					stock2.setCostTotal(stock.getCostTotal() + stock2.getCostTotal());
					stock2.setSalePriceTotal(stock2.getSalePrice() * stock.getQuantity() + stock2.getSalePriceTotal());
				}
			}
		}
		
		Collection<ChainInitialStock> chainInitialStocks2 = stockMap.values();
		
		return chainInitialStocks2;
	}

	/**
	 * 
	 * @param chain_id
	 * @return
	 */
	public Response getChainInitialStocks(int clientId) {
		Response response = new Response();
		
		/**
		 * 1. to get the initial stocks
		 */
		List<ChainInitialStock> chainStocks = chainInitialStockDaoImpl.getChainStockByClientId(clientId);
		
		if (chainStocks != null && chainStocks.size() >0){
			/**
			 * 2. need set the productBarcode information
			 */
			List<String> barcodes = new ArrayList<String>();
			for (ChainInitialStock chainInitialStock : chainStocks){
				barcodes.add(chainInitialStock.getId().getBarcode());
			}
			Map<String, ProductBarcode> productMap = productBarcodeDaoImpl.getProductMapByBarcode(new HashSet(barcodes));
			
			
			/**
			 * 3. set to the product list
			 */
			for (ChainInitialStock chainInitialStock : chainStocks){
				ProductBarcode productBarcode = productMap.get(chainInitialStock.getId().getBarcode());
				chainInitialStock.setProductBarcode(productBarcode);
			}
			
			response.setReturnCode(Response.SUCCESS);
			response.setReturnValue(chainStocks);
		} else {
			response.setReturnCode(Response.WARNING);
			response.setMessage("此连锁店还未录入资料");
		}

		return response;
	}

	/**
	 * 
	 * @param client_id
	 * @return
	 */
	public Response getChainStoreConf(int chainId) {
		Response response = new Response();
		
		ChainStoreConf conf = chainConfDaoImpl.getChainStoreConfByChainId(chainId);
		if (conf == null)
			response.setQuickValue(Response.ERROR, "连锁店还未添加配置信息");
		else {
			response.setReturnCode(Response.SUCCESS);
			response.setReturnValue(conf);
		}
		return response;
	}

	public Response saveChainStoreConf(ChainStoreConf chainStoreConf) {
		int chainId = chainStoreConf.getChainId();
		Response response = new Response();
		
		if (chainId == 0)
			response.setQuickValue(Response.ERROR, "连锁店不能为空");
		else {
//			ChainStoreConf chainStoreConf2 = chainConfDaoImpl.getChainStoreConfByChainId(chainId);
//			if (chainStoreConf2 == null){
//				chainStoreConf2 = new ChainStoreConf();
//				chainStoreConf2.setChainId(chainId);
//			}
//			
//			try {
//				chainStoreConf2 = (ChainStoreConf)BeanUtils.cloneBean(chainStoreConf);
//			} catch (IllegalAccessException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			} catch (InstantiationException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			} catch (InvocationTargetException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			} catch (NoSuchMethodException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			
			chainConfDaoImpl.saveOrUpdate(chainStoreConf, true);
			response.setReturnCode(Response.SUCCESS);
		}
		
		return response;
	}

	/**
	 * 查询品牌名称
	 * @param brand
	 * @param pager
	 * @return
	 */
	public Response getBrandList(int yearId, int quarterId, String brandName, Pager pager) {
		Response response = new Response();
		List<Brand> brands = null; 
		
		//1. check the pager
		if (pager.getTotalResult() == 0){
			int totalRecord = productDaoImpl.getNumOfBrandsUnderYQName(yearId, quarterId, brandName);
			pager.initialize(totalRecord);
		} else {
			pager.calFirstResult();
		}
		
		//2. 获取品牌列表
		List<Object> brandsId = productDaoImpl.getBrandIdsUnderYQName(yearId, quarterId, brandName, pager.getFirstResult(), pager.getRecordPerPage());
		
		DetachedCriteria criteria = DetachedCriteria.forClass(Brand.class);
		criteria.add(Restrictions.in("brand_ID", brandsId));
		brands = brandDaoImpl.getByCritera(criteria, true);
		
		if (brands == null)
			brands = new ArrayList<Brand>();
		
		response.setReturnValue(brands);
		response.setReturnCode(Response.SUCCESS);
		
		return response;

	}

	/**
	 * 准备chainGroup的页面内容
	 * @param uiBean
	 */
	public void prepareEditChainGroupUI(ChainMgmtActionUIBean uiBean) {
		//1. 获取所有chaingroup
		List<ChainStoreGroup> chainStoreGroups = chainStoreGroupDaoImpl.getAll(true);
		uiBean.setChainGroups(chainStoreGroups);
		
		//2.获取所有活跃连锁店
		List<ChainStore> chainStores = chainStoreService.getAvailableClientChainstores();
		uiBean.setChainStores(chainStores);
	}

	/**
	 * 获取chain group信息
	 * @param id
	 * @return
	 */
	@Transactional
	public Response getChainGroup(int id) {
		Response response = new Response();
		
		if (id == 0){
			response.setQuickValue(Response.FAIL, "无法获取id为空的连锁店关联信息");
		} else {
			ChainStoreGroup chainStoreGroup = chainStoreGroupDaoImpl.get(id, true);
			if (chainStoreGroup == null)
				response.setQuickValue(Response.FAIL, "无法获取相关的连锁店关联信息");
			else{
				chainStoreGroupDaoImpl.initialize(chainStoreGroup.getChainStoreGroupElementSet());
				response.setReturnCode(Response.SUCCESS);
				response.setReturnValue(chainStoreGroup);
			}
		}
		return response;
	}

	/**
	 * 增加或者更新chainGroup
	 * @param chainGroup
	 * @return
	 */
	@Transactional
	public Response updateChainGroup(ChainStoreGroup chainGroup, List<Integer> chainIds) {
		Response response = new Response();
		//1. 检查是修改还是增加
		int chainGroupId = chainGroup.getId();
		if (chainGroupId != 0){
			chainStoreGroupElementDaoImpl.deleteGroupEleByGroupId(chainGroupId);
		}
		
		for (int chainId: chainIds){
			ChainStoreGroupElement element = new ChainStoreGroupElement();
			element.setChainId(chainId);
			element.setChainGroup(chainGroup);
			chainGroup.getChainStoreGroupElementSet().add(element);
		}
		
		response = validateChainGroup(chainIds, chainGroupId);
		
		if (response.getReturnCode() == Response.SUCCESS){
			if (chainGroupId != 0){
				response.setAction(Response.ACTION_UPDATE);
			    chainStoreGroupDaoImpl.update(chainGroup, true);
			} else {
				response.setAction(Response.ACTION_ADD);
				chainStoreGroupDaoImpl.save(chainGroup, true);
			}
	
			response.setReturnCode(Response.SUCCESS);
		}
		return response;
	}
	
	/**
	 * 验证chain group里面的chain store不能同时被两个关联，关联住
	 * @return
	 */
	private Response validateChainGroup(List<Integer> chainIds, int chainGroupId){
		Response response = new Response();
		
		String errorMsg = "";
		boolean isError = false;
		for (Integer chainId: chainIds){
			int count = chainStoreGroupElementDaoImpl.checkExistOfChain(chainId, chainGroupId);
			if (count > 0){
				ChainStore chainStore = chainStoreService.getChainStoreByID(chainId);
				isError = true;
				errorMsg += chainStore.getChain_name() + " 已经存在于其他关联中.\n";
			}
		}
		
		if (isError){
			response.setQuickValue(Response.FAIL, errorMsg);
		} else 
			response.setReturnCode(Response.SUCCESS);
		
		return response;
	}

	/**
	 * 删除chain group
	 * @param id
	 * @return
	 */
	@Transactional
	public Response deleteChainGroup(int id) {
		Response response = new Response();
		if (id < 1)
			response.setQuickValue(Response.ERROR, "未保存的新连锁店关联无法删除");
		else {
			ChainStoreGroup chainGroup = chainStoreGroupDaoImpl.get(id, true);
			if (chainGroup == null){
				response.setQuickValue(Response.ERROR, "无法找到对应的连锁店关联");
			} else {
				chainStoreGroupDaoImpl.delete(chainGroup, true);
				response.setReturnCode(Response.SUCCESS);
			}
		}
		return response;
	}

	/**
	 * 准备修改连锁店销售价格的界面
	 * @param uiBean
	 */
	public void prepareEditChainProductPriceUI(ChainMgmtActionUIBean uiBean, ChainUserInfor loginUser) {
		List<Year> yearList =  yearDaoImpl.getAll(true); 
		Collections.sort(yearList, new SortYear());

		List<Quarter> quarterList =  quarterDaoImpl.getAll(true);  

		uiBean.setQuarterList(quarterList);
		uiBean.setYearList(yearList);
		
		//获取有权限修改价格的连锁店
		List<ChainStore> chainStores = new ArrayList<ChainStore>();
		if (ChainUserInforService.isMgmtFromHQ(loginUser)){
			chainStores = chainStoreService.getChainStoreCouldChangePrice();
		} else {
			chainStores.add(loginUser.getMyChainStore());
		}
		uiBean.setChainStores(chainStores);
		
	}

	/**
	 * 连锁店查找product barcode，添加的价格
	 * 1. 这里不分页
	 * @param productBarcode
	 * @return
	 */
	@Transactional
	public List<ChainProductBarcodeVO> searchProductBarcode(String needUpdtDate,
			ProductBarcode productBarcode, int chainId, Date startDate, Date endDate) {

		List<ChainProductBarcodeVO> chainProductBarcodeVOs = new ArrayList<ChainProductBarcodeVO>();
		if (needUpdtDate.equals("")){
			DetachedCriteria productBarcodeCriteria = DetachedCriteria.forClass(ProductBarcode.class);
			
			DetachedCriteria productCriteria = productBarcodeCriteria.createCriteria("product");
			
			boolean hasUserCriteria = false;
			if (productBarcode.getProduct().getProductCode() != null && !productBarcode.getProduct().getProductCode().trim().equals("")){
			    productCriteria.add(Restrictions.eq("productCode", productBarcode.getProduct().getProductCode()));
			    hasUserCriteria = true;
			}
	        
			if (productBarcode.getBarcode() != null && !productBarcode.getBarcode().trim().equals("")){
	        	productBarcodeCriteria.add(Restrictions.eq("barcode", productBarcode.getBarcode()));
	        	hasUserCriteria = true;
			}
	        
			if (!hasUserCriteria){
				if (productBarcode.getProduct().getYear() != null && productBarcode.getProduct().getYear().getYear_ID() != 0)
					productCriteria.add(Restrictions.eq("year.year_ID", productBarcode.getProduct().getYear().getYear_ID()));
				if (productBarcode.getProduct().getQuarter() != null && productBarcode.getProduct().getQuarter().getQuarter_ID() != 0)
					productCriteria.add(Restrictions.eq("quarter.quarter_ID", productBarcode.getProduct().getQuarter().getQuarter_ID()));
				if (productBarcode.getProduct().getBrand() != null && productBarcode.getProduct().getBrand().getBrand_ID() != 0)
					productCriteria.add(Restrictions.eq("brand.brand_ID", productBarcode.getProduct().getBrand().getBrand_ID()));
			}
	
			List<ProductBarcode> barcodes =  productBarcodeDaoImpl.getByCritera(productCriteria,true);
			
			ChainStore chainStore = chainStoreService.getChainStoreByID(chainId);
			chainProductBarcodeVOs = chainSalesPriceDaoImpl.convertProductBarcodeVO(barcodes, chainStore);
		} else {

			DetachedCriteria chainSaleCriteria = DetachedCriteria.forClass(ChainSalesPrice.class);
			chainSaleCriteria.add(Restrictions.between("lastUpdtDate",Common_util.formStartDate(new java.util.Date(startDate.getTime())), Common_util.formEndDate(new java.util.Date(endDate.getTime()))));
			chainSaleCriteria.add(Restrictions.eq("id.chainId",chainId));
			DetachedCriteria pbCriteria = chainSaleCriteria.createCriteria("pb");
			DetachedCriteria productCriteria = pbCriteria.createCriteria("product");
			DetachedCriteria yearCriteria = productCriteria.createCriteria("year");
			DetachedCriteria quarterCriteria = productCriteria.createCriteria("quarter");
			DetachedCriteria brandCriteria = productCriteria.createCriteria("brand");
			yearCriteria.addOrder(Order.desc("year_ID"));
			quarterCriteria.addOrder(Order.desc("quarter_ID"));
			brandCriteria.addOrder(Order.desc("brand_ID"));
			List<ChainSalesPrice> chainSalesPrices = chainSalesPriceDaoImpl.getByCritera(chainSaleCriteria, true);
			
			chainProductBarcodeVOs = chainSalesPriceDaoImpl.convertChainSalePriceToVO(chainSalesPrices);

		}
		
		return chainProductBarcodeVOs;
	}


	/**
	 * 更新连锁带您自己的零售价
	 * @param productBarcode
	 * @param chainId
	 * @return
	 */
	public Response updateChainBarcode(ChainProductBarcodeVO pb) {
		Response response = new Response();
		

		ChainSalesPrice chainSalesPrice = new ChainSalesPrice(pb.getBarcode(), pb.getChainId(), new ProductBarcode(pb.getProductBarcodeId()), pb.getMySalePrice());
		
		try {
			chainSalesPriceDaoImpl.saveOrUpdate(chainSalesPrice, true);
		} catch (Exception e) {
			loggerLocal.error(e);
			response.setQuickValue(Response.FAIL, "更新失败");
			return response;
		}
		
		response.setReturnCode(Response.SUCCESS);
		
		return response;
	}

	public Response getAllChainPriceIncre(int page, int rowPerPage) {
		Response response = new Response();
		Map dataMap = new HashMap();
		
		//1. 获取总条数
		DetachedCriteria priceIncreTotalCriteria = DetachedCriteria.forClass(ChainPriceIncrement.class);
		priceIncreTotalCriteria.setProjection(Projections.rowCount());
		int total = Common_util.getProjectionSingleValue(chainPriceIncrementDaoImpl.getByCriteriaProjection(priceIncreTotalCriteria, true));
		
		//2. 获取当页数据
		DetachedCriteria priceIncreCriteria = DetachedCriteria.forClass(ChainPriceIncrement.class);
		List<ChainPriceIncrement> increments = chainPriceIncrementDaoImpl.getByCritera(priceIncreCriteria, Common_util.getFirstRecord(page, rowPerPage), rowPerPage, true);
		
		dataMap.put("rows", increments);
		dataMap.put("total", total);
		
		response.setReturnValue(dataMap);
		response.setReturnCode(Response.SUCCESS);
		return response;
	}

	public Response getChainPriceIncre(ChainPriceIncrement priceIncrement) {
		Response response = new Response();
		if (priceIncrement != null && priceIncrement.getId() != 0)
			priceIncrement = chainPriceIncrementDaoImpl.get(priceIncrement.getId(), true);
		
		response.setReturnCode(Response.SUCCESS);
		response.setReturnValue(priceIncrement);
		return response;
	}

	public Response updatePriceIncrement(ChainPriceIncrement priceIncrement) {
		Response response = new Response();
		int id = priceIncrement.getId();
		if (id != 0){
			//1.验证是否有其他连锁店正在使用
			DetachedCriteria chainStoreCriteria = DetachedCriteria.forClass(ChainStore.class);
			chainStoreCriteria.add(Restrictions.eq("priceIncrement.id", id));
			chainStoreCriteria.setProjection(Projections.rowCount());
			int total = Common_util.getProjectionSingleValue(chainStoreDaoImpl.getByCriteriaProjection(chainStoreCriteria, true));
			if (total >0){
				response.setReturnCode(Response.FAIL);
				response.setMessage("有连锁店正在使用此数据，不能修改");
				return response;
			}
			
			chainPriceIncrementDaoImpl.update(priceIncrement, true);
		} else 
			chainPriceIncrementDaoImpl.save(priceIncrement, true);
		
		response.setReturnCode(Response.SUCCESS);
		return response;
	}

	public Response preparePreCreateConfUI(ChainMgmtActionFormBean formBean,
			ChainMgmtActionUIBean uiBean, ChainUserInfor userInfor) {
		Response response = new Response();
		QxbabyConf qxbabyConf = qxbabyConfDaoImpl.getConf();
		if (qxbabyConf == null)
			response.setFail("尚未设置配置信息"); 
		else 
			formBean.setQxbabyConf(qxbabyConf);
		
		uiBean.setYearList(yearDaoImpl.getAll(true));
		
		uiBean.setQuarterList(quarterDaoImpl.getAll(true));
		
		return response;
	}
	
	public void updateQxbabyConf(QxbabyConf conf){
		qxbabyConfDaoImpl.saveOrUpdate(conf, true);
	}


}
