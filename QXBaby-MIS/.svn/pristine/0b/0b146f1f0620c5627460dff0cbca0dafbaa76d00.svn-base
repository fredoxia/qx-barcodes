package com.onlineMIS.action.headQ.HR;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.onlineMIS.ORM.DAO.headQ.HR.HRMgmtService;
import com.onlineMIS.ORM.entity.headQ.HR.EvaluationCtl;
import com.onlineMIS.ORM.entity.headQ.HR.PeopleEvalMark;
import com.onlineMIS.ORM.entity.headQ.HR.PeopleEvaluation;
import com.onlineMIS.action.BaseAction;

@Controller
public class HREvalAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2650185972400475718L;


	@Autowired
	protected HRMgmtService hrMgmtService;
	
	/**
	 * the object to set the evaluation control
	 */
	protected EvaluationCtl evaluationCtl = new EvaluationCtl();
	public EvaluationCtl getEvaluationCtl() {
		return evaluationCtl;
	}
	public void setEvaluationCtl(EvaluationCtl evaluationCtl) {
		this.evaluationCtl = evaluationCtl;
	}
	
	protected PeopleEvaluation peopleEvaluation = new PeopleEvaluation();
	public PeopleEvaluation getPeopleEvaluation() {
		return peopleEvaluation;
	}
	public void setPeopleEvaluation(PeopleEvaluation peopleEvaluation) {
		this.peopleEvaluation = peopleEvaluation;
	}
	
	/**
	 * the evaluation mark
	 */
	protected PeopleEvalMark peopleEvalMark = new PeopleEvalMark();
	public PeopleEvalMark getPeopleEvalMark() {
		return peopleEvalMark;
	}
	public void setPeopleEvalMark(PeopleEvalMark peopleEvalMark) {
		this.peopleEvalMark = peopleEvalMark;
	}
	
}
