package com.onlineMIS.ORM.DAO.chainS.chainMgmt;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.onlineMIS.ORM.DAO.BaseDAO;
import com.onlineMIS.ORM.DAO.headQ.barCodeGentor.BrandPriceIncreaseDaoImpl;
import com.onlineMIS.ORM.entity.chainS.chainMgmt.ChainPriceIncrement;
import com.onlineMIS.ORM.entity.chainS.chainMgmt.ChainSalesPrice;
import com.onlineMIS.ORM.entity.chainS.chainMgmt.ChainSalesPriceId;
import com.onlineMIS.ORM.entity.chainS.chainMgmt.ChainStoreConf;
import com.onlineMIS.ORM.entity.chainS.user.ChainStore;
import com.onlineMIS.ORM.entity.headQ.barcodeGentor.Brand;
import com.onlineMIS.ORM.entity.headQ.barcodeGentor.BrandPriceIncrease;
import com.onlineMIS.ORM.entity.headQ.barcodeGentor.Product;
import com.onlineMIS.ORM.entity.headQ.barcodeGentor.ProductBarcode;
import com.onlineMIS.ORM.entity.headQ.barcodeGentor.Quarter;
import com.onlineMIS.ORM.entity.headQ.barcodeGentor.Year;
import com.onlineMIS.action.chainS.vo.ChainProductBarcodeVO;
import com.onlineMIS.common.Common_util;


@Repository
public class ChainSalesPriceDaoImpl extends BaseDAO<ChainSalesPrice> {
	
	@Autowired
	BrandPriceIncreaseDaoImpl brandPriceIncreaseDaoImpl;
	
	public ChainProductBarcodeVO convertProductBarcodeVO(ProductBarcode barcode, ChainStore chainStore){
		ChainProductBarcodeVO chainProductBarcodeVO = new ChainProductBarcodeVO();
		int chainId = 0;
		
		if (barcode != null){
			Double myPrice = null;

			if (chainStore != null){
				chainId = chainStore.getChain_id();
				
				//1. 如果是总部设定的涨价的货品
				Product product = barcode.getProduct();
				Year year = product.getYear();
				Quarter quarter = product.getQuarter();
				Brand brand = product.getBrand();
				chainProductBarcodeVO = new ChainProductBarcodeVO(barcode, null);
				
				BrandPriceIncrease brandPriceIncrease = brandPriceIncreaseDaoImpl.getByPK(year, quarter, brand);
				if (brandPriceIncrease != null){
					double increase = brandPriceIncrease.getIncrease();
					
					chainProductBarcodeVO.setDiscount(increase/100);
				} else {
					//2. 如果连锁店自己设定过价格,优先使用连锁店设定的价格
					ChainSalesPriceId id = new ChainSalesPriceId(chainId, barcode.getBarcode());
					ChainSalesPrice chainSalesPrice = this.get(id, true);
					if (chainSalesPrice != null) {
						myPrice = chainSalesPrice.getChainSalesPrice();
//						chainProductBarcodeVO = new ChainProductBarcodeVO(barcode, myPrice);
						chainProductBarcodeVO.setMySalePrice(myPrice);
					} else {
						//1. 获取连锁店涨价
						ChainPriceIncrement priceIncre = chainStore.getPriceIncrement();
						if (priceIncre != null && (barcode.getChainStore() == null || barcode.getChainStore().getChain_id() == 0)){
							myPrice = ChainSalesPriceDaoImpl.calPriceIncre(barcode.getProduct().getSalesPrice(),priceIncre);
							//chainProductBarcodeVO = new ChainProductBarcodeVO(barcode, myPrice);
							chainProductBarcodeVO.setMySalePrice(myPrice);
						}
					}
				}
			}
			
			
		}
		
		chainProductBarcodeVO.setChainId(chainId);
		
		return chainProductBarcodeVO;
	}
	public List<ChainProductBarcodeVO> convertProductBarcodeVO(List<ProductBarcode> barcodes, ChainStore chainStore){
		List<ChainProductBarcodeVO> chainProductBarcodeVOs = new ArrayList<ChainProductBarcodeVO>();
		
		List<String> barcodeStrs = new ArrayList<String>();
		
		if (barcodes != null && barcodes.size() >0){
			for (ProductBarcode barcode : barcodes)
				barcodeStrs.add(barcode.getBarcode());
			
			//连锁店提升的价格
			Map<String, Double> chainIncrePriceMap = new HashMap<String, Double>();
			//连锁店自己设置的价格
			Map<String, Double> chainBarcodeMap = new HashMap<String, Double>();
			
			int chainId = 0;
			if (chainStore != null) {
				chainId = chainStore.getChain_id();
				
				//1.检查连锁店是否会提升价格
				ChainPriceIncrement priceIncre = chainStore.getPriceIncrement();
				
				if (priceIncre != null){
					for (ProductBarcode barcode : barcodes){
						//如果连锁店的条码，就不去做升价格处理
					    if (barcode.getChainStore() == null || barcode.getChainStore().getChain_id() == 0)
					        chainIncrePriceMap.put(barcode.getBarcode(), this.calPriceIncre(barcode.getProduct().getSalesPrice(),priceIncre));
					}
				}
				
				//2. 获取连锁店自己设置的价格
				DetachedCriteria chainSalesPriceCriteria = DetachedCriteria.forClass(ChainSalesPrice.class);
				chainSalesPriceCriteria.add(Restrictions.eq("id.chainId", chainId));
				chainSalesPriceCriteria.add(Restrictions.in("id.barcode", barcodeStrs));
				
				List<ChainSalesPrice> chainSalesPrices = this.getByCritera(chainSalesPriceCriteria, true);
				if (chainSalesPrices != null && chainSalesPrices.size()>0){
					for (ChainSalesPrice chainSalesPrice : chainSalesPrices )
						chainBarcodeMap.put(chainSalesPrice.getId().getBarcode(), chainSalesPrice.getChainSalesPrice());
				}
			}
			
			for (ProductBarcode barcode : barcodes){
				 Double mySalePrice = chainBarcodeMap.get(barcode.getBarcode());
				 if (mySalePrice == null)
					 mySalePrice = chainIncrePriceMap.get(barcode.getBarcode());
			     ChainProductBarcodeVO chainProductBarcodeVO = new ChainProductBarcodeVO(barcode, mySalePrice);
			     chainProductBarcodeVO.setChainId(chainId);
			     chainProductBarcodeVOs.add(chainProductBarcodeVO);
			}
		}
		
		return chainProductBarcodeVOs;
	}
	
	public static Double calPriceIncre(double salesPrice, ChainPriceIncrement priceIncre) {
		if (priceIncre.getIncrementType() == ChainPriceIncrement.TYPE_MULTIPLE){
			return Common_util.roundDouble(salesPrice * (1 + priceIncre.getIncrement()/100), 0);
		} else if (priceIncre.getIncrementType() == ChainPriceIncrement.TYPE_ADD){
			return salesPrice + priceIncre.getIncrement();
		} else 
			return salesPrice;
	}
	
	public List<ChainProductBarcodeVO> convertChainSalePriceToVO(
			List<ChainSalesPrice> chainSalesPrices) {
		List<ChainProductBarcodeVO> chainProductBarcodeVOs = new ArrayList<ChainProductBarcodeVO>();

		if (chainSalesPrices != null && chainSalesPrices.size() >0){
			for (ChainSalesPrice salePrice : chainSalesPrices){
			     ChainProductBarcodeVO chainProductBarcodeVO = new ChainProductBarcodeVO(salePrice.getPb(), salePrice.getChainSalesPrice());
			     chainProductBarcodeVOs.add(chainProductBarcodeVO);
			}
		}
		
		return chainProductBarcodeVOs;
	}
}
