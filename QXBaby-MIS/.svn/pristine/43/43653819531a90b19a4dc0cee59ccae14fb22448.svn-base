package com.onlineMIS.ORM.DAO.headQ.barCodeGentor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.onlineMIS.ORM.DAO.BaseDAO;
import com.onlineMIS.ORM.entity.headQ.barcodeGentor.Quarter;
import com.onlineMIS.ORM.entity.headQ.barcodeGentor.Year;

@Repository
public class QuarterDaoImpl extends BaseDAO<Quarter> {
//	@SuppressWarnings("unchecked")
//	public Quarter load(final int id){
//		return (Quarter)this.getHibernateTemplate().execute(new HibernateCallback(){
//            public Object doInHibernate(Session session) throws HibernateException,SQLException{
//            	Quarter quarter=(Quarter)session.load(Quarter.class,id);
//                Hibernate.initialize(quarter);
//                return quarter;
//           }
//     });
//	}
	
	public Map<String, Quarter> getQuarterNameMap(){
		List<Quarter> quarters = getAll(true);
		Map<String, Quarter> quarterMap = new HashMap<String, Quarter>();
		for (Quarter quarter: quarters){
			quarterMap.put(quarter.getQuarter_Name().trim(), quarter);
		}
		
		return quarterMap;
	}
}
