package com.onlineMIS.ORM.DAO.headQ.barCodeGentor;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;

import com.onlineMIS.ORM.DAO.BaseDAO;
import com.onlineMIS.ORM.entity.headQ.barcodeGentor.Category;
import com.onlineMIS.common.loggerLocal;

@Repository
public class CategoryDaoImpl extends BaseDAO<Category> {
	
	public List<Category> getHeadCategry(boolean cached){
		DetachedCriteria criteria = DetachedCriteria.forClass(Category.class);
		criteria.add(Restrictions.eq("chainId", Category.TYPE_HEAD));
		return this.getByCritera(criteria, cached);
	}
	
	public List<Category> getChainCategry(boolean cached){
		DetachedCriteria criteria = DetachedCriteria.forClass(Category.class);
		criteria.add(Restrictions.eq("chainId", Category.TYPE_CHAIN));
		return this.getByCritera(criteria, cached);
	}	
	
	public boolean checkCategoryExist(String category){
		DetachedCriteria criteria = DetachedCriteria.forClass(Category.class);
		criteria.add(Restrictions.eq("category_Name", category));
		criteria.add(Restrictions.eq("chainId", Category.TYPE_HEAD));
		List<Category> categories = this.getByCritera(criteria, true);
		
		if (categories == null || categories.size() != 1)
			return false;
		else 
			return true;
	}

	public Category getCategoryByName(String categoryString) {
		DetachedCriteria criteria = DetachedCriteria.forClass(Category.class);
		criteria.add(Restrictions.eq("category_Name", categoryString));
		criteria.add(Restrictions.eq("chainId", Category.TYPE_HEAD));
		List<Category> categories = this.getByCritera(criteria, true);
		
		return categories.get(0);
	}
	
	
//	@SuppressWarnings("unchecked")
//	public List<Category> findCategories(DetachedCriteria criteria,int start, int end){
//			return getHibernateTemplate().findByCriteria(criteria, start, end);
//	}
//	
//	public void save(Category category){
//		this.getHibernateTemplate().save(category);
//	}
//	
//	@SuppressWarnings("unchecked")
//	public Category load(final int id){
//		return (Category)this.getHibernateTemplate().execute(new HibernateCallback(){
//            public Object doInHibernate(Session session) throws HibernateException,SQLException{
//            	Category category=(Category)session.load(Category.class,id);
//                Hibernate.initialize(category);
//                return category;
//           }
//     });
//	}
//
//	public boolean update(Category category) {
//		try{
//		    this.getHibernateTemplate().update(category);
//		
//		} catch (Exception e) {
//			loggerLocal.error(e);
//		    return false;
//		}
//	return true;
//		
//	}
}
