package com.onlineMIS.ORM.DAO.chainS.chainMgmt;

import org.springframework.stereotype.Repository;
import com.onlineMIS.ORM.DAO.BaseDAO;
import com.onlineMIS.ORM.entity.chainS.chainMgmt.ChainStoreConf;


@Repository
public class ChainStoreConfDaoImpl extends BaseDAO<ChainStoreConf> {
	/**
	 * 
	 * @param chainId
	 * @return
	 */
	public ChainStoreConf getChainStoreConfByChainId(int chainId){
		return this.get(chainId, true);
	}

}
