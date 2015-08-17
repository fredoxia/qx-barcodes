package com.onlineMIS.ORM.DAO.headQ.HR;

import org.springframework.stereotype.Repository;

import com.onlineMIS.ORM.DAO.BaseDAO;
import com.onlineMIS.ORM.entity.headQ.HR.Evaluation;

@Repository
public class HREvaluationDAOImpl extends BaseDAO<Evaluation> {

    public void initialize(Evaluation evaluation){
    	   this.getHibernateTemplate().initialize(evaluation.getEvaluationItem_set());
    }
}
