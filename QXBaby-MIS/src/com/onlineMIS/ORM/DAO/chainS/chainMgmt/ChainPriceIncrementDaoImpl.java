package com.onlineMIS.ORM.DAO.chainS.chainMgmt;

import java.io.Serializable;

import org.springframework.stereotype.Repository;

import com.onlineMIS.ORM.DAO.BaseDAO;
import com.onlineMIS.ORM.entity.chainS.chainMgmt.ChainPriceIncrement;
import com.onlineMIS.common.loggerLocal;

@Repository
public class ChainPriceIncrementDaoImpl  extends BaseDAO<ChainPriceIncrement> {
	@Override
	public void saveOrUpdate(ChainPriceIncrement entity, boolean cached) {
		if (cached)
			getHibernateTemplate().setCacheQueries(true);

		entity.formatDescription();
		getHibernateTemplate().saveOrUpdate(entity);

	}

	@Override
	public void save(ChainPriceIncrement entity, boolean cached) {
		if (cached)
			getHibernateTemplate().setCacheQueries(true);
		
		entity.formatDescription();
		getHibernateTemplate().save(entity);

	}

	@Override
	public void update(ChainPriceIncrement entity, boolean cached) {
		if (cached)
			getHibernateTemplate().setCacheQueries(true);
		
		entity.formatDescription();
		getHibernateTemplate().update(entity);
	}

}
