package com.onlineMIS.unit_testing;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.FileHandler;
import java.util.logging.Formatter;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Restrictions;

import com.onlineMIS.ORM.entity.chainS.inventoryFlow.ChainInOutStock;
import com.onlineMIS.ORM.entity.chainS.inventoryFlow.ChainInOutStockArchive;
import com.onlineMIS.ORM.entity.chainS.inventoryFlow.ChainInOutStockCopy;
import com.onlineMIS.ORM.entity.chainS.user.ChainStore;
import com.onlineMIS.ORM.entity.headQ.barcodeGentor.ProductBarcode;
import com.onlineMIS.ORM.entity.headQ.barcodeGentor.Year;
import com.onlineMIS.common.Common_util;

public class AnnualBarAcct {
	Logger logger = null;
	final int YEAR_START = 2011;
	final int YEAR_INTERVAL = 1;
	final String AUTO_BAR_ACCT = "AB";
	
	public AnnualBarAcct() throws SecurityException, IOException{
		logger = Logger.getLogger("lavasoft"); 
		logger.setLevel(Level.INFO); 
        FileHandler fileHandler = new FileHandler("d:/annualBarAcctlog%g.log"); 
        fileHandler.setLevel(Level.INFO); 
        fileHandler.setFormatter(new MyLogHander());
        logger.addHandler(fileHandler); 
	}
	
	public void process(){

		
		Configuration configuration = new Configuration().configure();
		SessionFactory sFactory = configuration.buildSessionFactory();
		Session session2 = sFactory.openSession();

		
		//2. 获取chain store
		Criteria chainCriteria = session2.createCriteria(ChainStore.class);
		Integer[] chainIds = {4,5};
		chainCriteria.add(Restrictions.in("chain_id", chainIds));
		List<ChainStore> chains = chainCriteria.list();
//		for (ChainStore chain: chains){
//			logger.info(chain.getChain_id() + " , " + chain.getChain_name());
//		}
		
		//3. 获取条码
//		Criteria pbCriteria = session.createCriteria(ProductBarcode.class);
//		Criteria pCriteria = pbCriteria.createCriteria("product");
//		pCriteria.add(Restrictions.in("year.year_ID", yearIds));
//		List<ProductBarcode> productBarcodes = pbCriteria.list();
//		logger.info("总数 ： " + productBarcodes.size() );
		
		
		//4. 开始chain stores

		String barcode = "";

		for (ChainStore chain: chains){
			Session session = sFactory.openSession();
			Transaction transaction = session.beginTransaction();
			
			logger.info("开始 ： " + chain.getChain_name() + "," + chain.getClient_id());

			int clientId = chain.getClient_id();
			
            Query query = session.createQuery("SELECT DISTINCT barcode FROM ChainInOutStock WHERE clientId = " + clientId);
			List<Object> barcodes  = query.list();
			logger.info(barcodes.toString());
			
			for (Object barcodeObj : barcodes){
				barcode = barcodeObj.toString();
				
				Criteria pbCriteria = session.createCriteria(ProductBarcode.class);
				pbCriteria.add(Restrictions.eq("barcode", barcode));
				List<ProductBarcode> productBarcodes = pbCriteria.list();
				if (productBarcodes == null || productBarcodes.size() == 0){
					logger.info("ERROR : " + clientId + " , " + barcode);
					continue;
				}
				
				ProductBarcode pb = productBarcodes.get(0);
	
				Criteria inOutSumCriteria = session.createCriteria(ChainInOutStock.class);
				inOutSumCriteria.add(Restrictions.eq("clientId", clientId));
				inOutSumCriteria.add(Restrictions.eq("barcode", barcode));
				List<ChainInOutStock> chainInOutStocks = inOutSumCriteria.list();
				
				if (chainInOutStocks == null || chainInOutStocks.size() == 0){
					continue;
				} else {
					logger.info(chain.getClient_id() + "," + pb.getBarcode() + "," + chainInOutStocks.size());
					
					int totalQ = 0;
					double costTotal = 0;
					double salePriceTotal = 0;
					double chainSalePriceTotal = 0;
					for (ChainInOutStock stock : chainInOutStocks){
						totalQ += stock.getQuantity();
						costTotal += stock.getCostTotal();
						salePriceTotal += stock.getSalePriceTotal();
						chainSalePriceTotal += stock.getChainSalePriceTotal();
						
						if (stock.getOrderId().indexOf(ChainInOutStock.AUTO_BAR_ACCT) != 0){
								ChainInOutStockArchive stockArchive = new ChainInOutStockArchive(stock);
								session.save(stockArchive);
						}
						
						session.delete(stock);
					}

					if (totalQ != 0){
					    ChainInOutStock totalStock = new ChainInOutStock(barcode, clientId, ChainInOutStock.AUTO_BAR_ACCT + Common_util.AUTO_BAR_ACCT_ROUND, Common_util.ALL_RECORD, costTotal / totalQ, costTotal, pb.getProduct().getSalesPrice(), salePriceTotal, chainSalePriceTotal, totalQ, pb);
					    session.saveOrUpdate(totalStock);
					}
					logger.info(pb.getBarcode() + " : " + totalQ + "," + chainInOutStocks.size());
				}
   
			}
			session.flush();
			transaction.commit();
		    session.close();
		    
		}


	}
	
	public void validate(int clientId){
		Configuration configuration = new Configuration().configure();
		SessionFactory sFactory = configuration.buildSessionFactory();
		Session session = sFactory.openSession();
		Transaction transaction = session.beginTransaction();
		
        Query query = session.createQuery("SELECT DISTINCT barcode FROM ChainInOutStockCopy WHERE clientId = " + clientId);
		List<Object> barcodes  = query.list();
		logger.info(barcodes.toString());
		

		for (Object barcodeObj : barcodes){
			int total  = 0;
			int total2 = 0;
			double totalCost = 0;
			double totalCost2 = 0;
			
			String barcode = barcodeObj.toString();
			Criteria inOutSumCriteria = session.createCriteria(ChainInOutStock.class);
			inOutSumCriteria.add(Restrictions.eq("clientId", clientId));
			inOutSumCriteria.add(Restrictions.eq("barcode", barcode));
			List<ChainInOutStock> chainInOutStocks = inOutSumCriteria.list();
			
			if (chainInOutStocks != null && chainInOutStocks.size() != 0){
				for (ChainInOutStock stock : chainInOutStocks){
					total += stock.getQuantity();
					totalCost += stock.getCostTotal();
				}
			}
			
			Criteria inOutSumCriteria2 = session.createCriteria(ChainInOutStockCopy.class);
			inOutSumCriteria2.add(Restrictions.eq("clientId", clientId));
			inOutSumCriteria2.add(Restrictions.eq("barcode", barcode));
			List<ChainInOutStockCopy> chainInOutStocks2 = inOutSumCriteria2.list();
			if (chainInOutStocks2 != null && chainInOutStocks2.size() != 0){
				for (ChainInOutStockCopy stock : chainInOutStocks2){
					total2 += stock.getQuantity();
					totalCost2 += stock.getCostTotal();
				}
			}
			
			
			if (total != total2 || (Math.abs(totalCost2 - totalCost) > 5 && totalCost2 != 0)){
				logger.info("Error : " + barcode + "," + total + "," + total2 + "," + totalCost + "," + totalCost2);
			} else 
				logger.info(barcode + "," + total + "," + total2+ "," + totalCost + "," + totalCost2);
		}
		
		
		transaction.commit();
	    session.close();
	}

	/**
	 * @param args
	 * @throws IOException 
	 * @throws SecurityException 
	 */
	public static void main(String[] args) throws SecurityException, IOException {
		AnnualBarAcct barAcct = new AnnualBarAcct();
//		barAcct.process();
		barAcct.validate(1989);

	}

}
class MyLogHander extends Formatter { 

	@Override 
	public String format(LogRecord record) { 
	      return record.getMessage()+"\n"; 
	} 
}
