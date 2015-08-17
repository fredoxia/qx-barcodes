package com.onlineMIS.ORM.DAO.headQ.HR;

import org.springframework.stereotype.Repository;

import com.onlineMIS.ORM.DAO.BaseDAO;
import com.onlineMIS.ORM.entity.headQ.HR.PeopleEvaluation;

@Repository
public class HRPeoplEvaluationDAOImpl extends BaseDAO<PeopleEvaluation> {

	public void initialize(PeopleEvaluation peopleEvaluation){
		this.getHibernateTemplate().initialize(peopleEvaluation.getPeopleEvalMark_set());
	}

}
