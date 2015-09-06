package com.onlineMIS.ORM.DAO.headQ.finance;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.onlineMIS.ORM.DAO.BaseDAO;
import com.onlineMIS.ORM.entity.headQ.finance.ChainFinanceTrace;

@Repository
public class ChainFinanceTraceImpl extends BaseDAO<ChainFinanceTrace> {


	public double getSumOfFinanceCategory(int categoryId, int chainId){
		Object[] values = new Object[]{chainId, categoryId};
		
		String hql = "SELECT SUM(amount) from ChainFinanceTrace WHERE chainId =? AND categoryId=?";
		
		List<Object> results = this.executeHQLSelect(hql, values,null, true);
		
		if (results != null && results.size() > 0){
			double sum = (Double)results.get(0);
			return sum;
		} else 
			return 0;
	}


}
