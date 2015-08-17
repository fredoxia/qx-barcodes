package com.onlineMIS.unit_testing;

import java.io.File;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import com.onlineMIS.ORM.DAO.Response;
import com.onlineMIS.ORM.entity.chainS.inventoryFlow.ChainInOutStock;
import com.onlineMIS.ORM.entity.chainS.inventoryFlow.ChainInventoryFlowOrder;
import com.onlineMIS.ORM.entity.chainS.inventoryFlow.ChainInventoryFlowOrderProduct;
import com.onlineMIS.ORM.entity.chainS.sales.ChainStoreSalesOrder;
import com.onlineMIS.ORM.entity.chainS.sales.ChainStoreSalesOrderProduct;
import com.onlineMIS.ORM.entity.chainS.user.ChainStore;
import com.onlineMIS.ORM.entity.headQ.barcodeGentor.ProductBarcode;
import com.onlineMIS.ORM.entity.headQ.inventory.InventoryFileTemplate;
import com.onlineMIS.ORM.entity.headQ.inventory.InventoryOrder;
import com.onlineMIS.ORM.entity.headQ.inventory.InventoryOrderProduct;

public class testBulkInsert {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
Configuration configuration = new Configuration().configure();
		Date startTime = new Date();
		SessionFactory sFactory = configuration.buildSessionFactory();
		
		Session session = sFactory.openSession();
		Transaction transaction = session.beginTransaction();

        ChainInventoryFlowOrder order = (ChainInventoryFlowOrder)session.get(ChainInventoryFlowOrder.class, 5);
		Set<ChainInventoryFlowOrderProduct> products = order.getProductSet();
        for (ChainInventoryFlowOrderProduct product: products)
        	System.out.println(product.getId());
        
        File inventoryFile = new File("d:\\inventory.txt");
        InventoryFileTemplate inventoryFileTemplate = new InventoryFileTemplate(inventoryFile);
		
		Response response = inventoryFileTemplate.process();
		Map<String, Integer> barcodeMap = (Map<String, Integer>)response.getReturnValue();
		Set<String> barcodes = barcodeMap.keySet();

		for (String productId: barcodeMap.keySet()){
			ChainInventoryFlowOrderProduct chainInventoryFlowOrderProduct = new ChainInventoryFlowOrderProduct();
			ProductBarcode barcode = new ProductBarcode();
			barcode.setId(Integer.parseInt(productId));
			
			chainInventoryFlowOrderProduct.setProductBarcode(barcode);

			int quantity = 0;

	
			int inventory = 0;

			chainInventoryFlowOrderProduct.setComment("");
			chainInventoryFlowOrderProduct.setQuantity(quantity);
			chainInventoryFlowOrderProduct.setInventoryQ(inventory);
			chainInventoryFlowOrderProduct.setQuantityDiff(quantity - inventory);
			
			chainInventoryFlowOrderProduct.setFlowOrder(order);
			
			products.add(chainInventoryFlowOrderProduct);
		}
        
		session.saveOrUpdate(order);

		transaction.commit();
	    session.close();


	}

}
