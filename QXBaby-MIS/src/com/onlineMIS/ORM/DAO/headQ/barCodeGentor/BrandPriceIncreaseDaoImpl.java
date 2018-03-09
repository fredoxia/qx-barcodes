package com.onlineMIS.ORM.DAO.headQ.barCodeGentor;


import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.onlineMIS.ORM.DAO.BaseDAO;
import com.onlineMIS.ORM.entity.headQ.barcodeGentor.Brand;
import com.onlineMIS.ORM.entity.headQ.barcodeGentor.BrandPriceIncrease;
import com.onlineMIS.ORM.entity.headQ.barcodeGentor.Quarter;
import com.onlineMIS.ORM.entity.headQ.barcodeGentor.Year;

@Repository
public class BrandPriceIncreaseDaoImpl extends BaseDAO<BrandPriceIncrease>{

	public BrandPriceIncrease getByPK(Year year, Quarter quarter, Brand brand){
		return getByPK(year.getYear_ID(), quarter.getQuarter_ID(), brand.getBrand_ID());
	}
	
	public BrandPriceIncrease getByPK(int yearId, int quarterId, int brandId){
		DetachedCriteria criteria = DetachedCriteria.forClass(BrandPriceIncrease.class);
		criteria.add(Restrictions.eq("year.year_ID", yearId));
		criteria.add(Restrictions.eq("quarter.quarter_ID", quarterId));
		criteria.add(Restrictions.eq("brand.brand_ID", brandId));
		
		List<BrandPriceIncrease> brandPriceIncreases = this.getByCritera(criteria, true);
		if (brandPriceIncreases == null || brandPriceIncreases.size() == 0)
			return null;
		else {
			return brandPriceIncreases.get(0);
		}
	}
}
