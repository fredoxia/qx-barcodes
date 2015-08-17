package com.onlineMIS.ORM.DAO.headQ.finance;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import com.onlineMIS.ORM.DAO.BaseDAO;
import com.onlineMIS.ORM.entity.headQ.finance.FinanceCategory;

@Repository
public class FinanceCategoryImpl extends BaseDAO<FinanceCategory> {
	
	    @Override
	    public List<FinanceCategory> getAll(boolean cached){
	    	DetachedCriteria criteria = DetachedCriteria.forClass(FinanceCategory.class);
	    	criteria.addOrder(Order.asc("type"));
	    	
	    	return this.getByCritera(criteria, cached);
	    }
	
	    public FinanceCategory getByTypeId(int typeId){
			DetachedCriteria criteria = DetachedCriteria.forClass(FinanceCategory.class);
			criteria.add(Restrictions.eq("type", typeId));
			List<FinanceCategory> categories =  getByCritera(criteria, true);
			if (categories == null || categories.size() == 0)
				return null;
			else 
				return categories.get(0);
	    }
	    
	    /**
	     * id: typeId;
	     * value: financeCategory
	     * @return
	     */
	    public Map<Integer, FinanceCategory> getFinanceCategoryMap(){
	    	List<FinanceCategory> categories = getAll(true);
	    	
	    	Map<Integer, FinanceCategory> categoryMap = new HashMap<Integer, FinanceCategory>();
	    	
	    	for (FinanceCategory financeCategory: categories){
	    		int typeId = financeCategory.getType();
	    		categoryMap.put(typeId, financeCategory);
	    	}
	    	return categoryMap;
	    }
	
//	    /**
//	     * get the preincomeItem
//	     * @return
//	     */
//		public List<FinanceCategory> getPreincomeItem(){
//			DetachedCriteria criteria = DetachedCriteria.forClass(FinanceCategory.class);
//			criteria.add(Restrictions.eq("type", FinanceCategory.PREINCOME));
//			return getByCritera(criteria, true);
//		}
//		
//		/**
//		 * to get the cash and bank items
//		 * @return
//		 */
//		public List<FinanceCategory> getCashBankItem(){
//			List<Integer> ids = new ArrayList<Integer>();
//			ids.add(FinanceCategory.BANK);
//			ids.add(FinanceCategory.CASH);
//			
//			DetachedCriteria criteria = DetachedCriteria.forClass(FinanceCategory.class);
//			criteria.add(Restrictions.in("type", ids));
//			return getByCritera(criteria, true);
//		}
}
