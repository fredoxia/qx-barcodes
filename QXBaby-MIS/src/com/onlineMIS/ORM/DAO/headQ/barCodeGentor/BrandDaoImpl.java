package com.onlineMIS.ORM.DAO.headQ.barCodeGentor;

import org.springframework.stereotype.Repository;
import com.onlineMIS.ORM.DAO.BaseDAO;
import com.onlineMIS.ORM.entity.headQ.barcodeGentor.Brand;

@Repository
public class BrandDaoImpl extends BaseDAO<Brand> {
	@Override
	public void saveOrUpdate(Brand brand, boolean cached) {
		if (cached)
			getHibernateTemplate().setCacheQueries(true);

		if (brand.getChainStore() != null  && brand.getChainStore().getChain_id() == 0 )
			brand.setChainStore(null);
		
		getHibernateTemplate().saveOrUpdate(brand);

	}

}
