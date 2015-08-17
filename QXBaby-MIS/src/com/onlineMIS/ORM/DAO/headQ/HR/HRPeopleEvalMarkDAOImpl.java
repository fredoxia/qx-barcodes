package com.onlineMIS.ORM.DAO.headQ.HR;

import org.springframework.stereotype.Repository;

import com.onlineMIS.ORM.DAO.BaseDAO;
import com.onlineMIS.ORM.entity.headQ.HR.PeopleEvalMark;

@Repository
public class HRPeopleEvalMarkDAOImpl extends BaseDAO<PeopleEvalMark> {

	public void initialize(PeopleEvalMark peopleEvalMark){
		this.getHibernateTemplate().initialize(peopleEvalMark.getPeopleEvalItemMark_set());
	}
}
