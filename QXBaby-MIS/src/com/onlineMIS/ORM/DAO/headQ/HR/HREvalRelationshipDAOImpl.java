package com.onlineMIS.ORM.DAO.headQ.HR;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.onlineMIS.ORM.DAO.BaseDAO;
import com.onlineMIS.ORM.entity.headQ.HR.Evaluation;
import com.onlineMIS.ORM.entity.headQ.HR.MagerEmployeeRelationship;

@Repository
public class HREvalRelationshipDAOImpl extends BaseDAO<MagerEmployeeRelationship> {
	/**
	 * to remove
	 * @param manager_id
	 */
	public void removeEmployeeUnder(int manager_id){
		this.getHibernateTemplate().setCacheQueries(true);
		
		String hql = "delete from MagerEmployeeRelationship where manager_id=?";
		this.getHibernateTemplate().bulkUpdate(hql, manager_id);

	}

	/**
	 * to add the employees under the manager
	 * @param employees
	 */
	public void addEmployeeUnder(int manager_id,List<Integer> employees) {
		for (Integer id: employees){
		    MagerEmployeeRelationship relationship = new MagerEmployeeRelationship();
		    relationship.setEmployee_id(id);
		    relationship.setManager_id(manager_id);
		    
		    save(relationship, true);
		}
		
	}

	public List<Integer> getEmployeeUnder(int userId) {
		DetachedCriteria criteria = DetachedCriteria.forClass(MagerEmployeeRelationship.class);
		criteria.add(Restrictions.eq("manager_id", userId));
		
		List<MagerEmployeeRelationship> relationships = this.getByCritera(criteria, true);
		
		List<Integer> ids = new ArrayList<Integer>();
		for (MagerEmployeeRelationship relationship : relationships)
			ids.add(relationship.getEmployee_id());
		
		return ids;
	}
}
