package com.onlineMIS.unit_testing;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.onlineMIS.ORM.DAO.Response;
import com.onlineMIS.ORM.entity.chainS.chainMgmt.ChainInitialStock;
import com.onlineMIS.ORM.entity.chainS.chainMgmt.ChainInitialStockId;
import com.onlineMIS.ORM.entity.chainS.chainMgmt.InitialInventoryFileTemplate;
import com.onlineMIS.ORM.entity.chainS.inventoryFlow.ChainInOutStock;
import com.onlineMIS.ORM.entity.headQ.barcodeGentor.ProductBarcode;
import com.onlineMIS.ORM.entity.headQ.inventory.HeadQSalesHistory;

public class bulkInsertInitalStock {
	private Session session;
	private int clientId = 0;
	
	public bulkInsertInitalStock(Session session,int clientId){
		this.session = session;
		this.clientId = clientId;
	}
	
	private Map<String, ProductBarcode> getProductMapByBarcode(Set<String> barcodes){
		Criteria criteria = session.createCriteria(ProductBarcode.class);
		criteria.add(Restrictions.in("barcode",barcodes));
		
		List<ProductBarcode> productBarcodes = criteria.list();

		Map<String, ProductBarcode> productMap = new HashMap<String, ProductBarcode>();
		
		if (productBarcodes != null){
			for (ProductBarcode productBarcode : productBarcodes){
				productMap.put(productBarcode.getBarcode(), productBarcode);
			}
		}
		
		return productMap;
	}
	
	
	private Collection<ChainInitialStock> groupInitialStockQ(List<ChainInitialStock> chainInitialStocks){
		Map<String, ChainInitialStock> stockMap = new HashMap<String, ChainInitialStock>();
		
		Set<String> barcodes = new HashSet<String>();
		for (ChainInitialStock initialStock : chainInitialStocks){
			if (initialStock != null && initialStock.getId() != null)
			   barcodes.add(initialStock.getId().getBarcode());
		}

		Map<String, ProductBarcode> productMap = getProductMapByBarcode(barcodes);
		
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
						stock.setProductBarcode(productBarcode);
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
	
	public int process(){
		String filePath = "F:\\qxbaby\\宜宾库存\\2013.csv";
		File initialStockFile = new File(filePath);
		
		InitialInventoryFileTemplate initialInventoryFileTemplate = new InitialInventoryFileTemplate(initialStockFile);
		Response response = initialInventoryFileTemplate.process();
		Map<String, ChainInitialStock> barcodes = (Map<String, ChainInitialStock>)((List<Object>)response.getReturnValue()).get(0);
		Set<String> keys = barcodes.keySet();
		
		List<ChainInitialStock> inputStocks = new ArrayList<ChainInitialStock>();
		for (String barcode: keys){
			ChainInitialStock initialStock = barcodes.get(barcode);
			ChainInitialStockId id = new ChainInitialStockId(barcode, clientId);
			initialStock.setId(id);
			
			inputStocks.add(initialStock);
		}
		
		/**
		 * validate the input stocks
		 */
		Collection<ChainInitialStock> groupedStocks = groupInitialStockQ(inputStocks);
		
//		for (ChainInitialStock chainInitialStock : groupedStocks){
//
////			if (!chainInitialStock.getId().getBarcode().equals("") &&){
////				System.out.println("数量必须为整数");
////				return 0;
////			}
//		}
		if (clientId == 0){
			System.out.println("连锁店为必选项");
			return 0;
		}
			
		

		/**
		 * 1. prepare the data
		 */
		for (ChainInitialStock chainInitialStock : groupedStocks){
			chainInitialStock.getId().setClientId(clientId);
		}
		
		/**
		 * 3. update the existing stock and update the price history
		 */
		for (ChainInitialStock chainInitialStock : groupedStocks){
			System.out.println(chainInitialStock);
			
			//1. 保存initial stock
			chainInitialStock.setDate(new Date());
			session.save(chainInitialStock);
			
			//2. 展示不能用，放到headeq才能用
			ProductBarcode productBarcode = chainInitialStock.getProductBarcode();
			HeadQSalesHistory salesHistory = new HeadQSalesHistory(productBarcode.getId(), clientId, 0 , chainInitialStock.getCost(), 0, chainInitialStock.getQuantity(), 0, 1);
			session.saveOrUpdate(salesHistory);
			
			//3. 保存in-out stock flow
			String barcode = chainInitialStock.getId().getBarcode();
			ChainInOutStock inOutStock = new ChainInOutStock(barcode, clientId, ChainInOutStock.DEFAULT_INITIAL_STOCK_ORDER_ID, ChainInOutStock.TYPE_INITIAL_STOCK, chainInitialStock.getCost(), chainInitialStock.getCostTotal(), chainInitialStock.getSalePrice(), chainInitialStock.getSalePriceTotal(), chainInitialStock.getSalePriceTotal(), chainInitialStock.getQuantity(), productBarcode);
			session.save(inOutStock);
		}
		
		return 1;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		//宜宾
		int chainId = 15;
		int clientId = 1621;
		
		Configuration configuration = new Configuration().configure();
		SessionFactory sFactory = configuration.buildSessionFactory();
		Session session = sFactory.openSession();
		Transaction transaction = session.beginTransaction();

		bulkInsertInitalStock bulkInsertInitalStock = new bulkInsertInitalStock(session, 1621);
		bulkInsertInitalStock.process();

		transaction.commit();
	    session.close();
	}

}
