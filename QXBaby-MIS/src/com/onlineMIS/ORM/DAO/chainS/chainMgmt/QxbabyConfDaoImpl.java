package com.onlineMIS.ORM.DAO.chainS.chainMgmt;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.onlineMIS.ORM.DAO.BaseDAO;
import com.onlineMIS.ORM.entity.chainS.chainMgmt.ChainStoreConf;
import com.onlineMIS.ORM.entity.chainS.chainMgmt.QxbabyConf;

import freemarker.core.ArithmeticEngine.ConservativeEngine;


@Repository
public class QxbabyConfDaoImpl extends BaseDAO<QxbabyConf> {
		public QxbabyConf getConf(){
			List<QxbabyConf> confs = this.getAll(true);
			if (confs == null || confs.size() == 0)
				return null;
			else 
				return confs.get(0);
		}
}
