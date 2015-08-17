package com.onlineMIS.ORM.entity.headQ.HR;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;


import com.onlineMIS.ORM.entity.headQ.user.UserInfor;

public class PeopleEvaluation implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = -1774331787835264758L;
	private int id;
    private UserInfor evaluatee = new UserInfor();
   
    private Evaluation evaluation = new Evaluation();
    private int evaluationYear;
    private int evaluationMonth;
    private int status = 0;
    private double mark;
    private Set<PeopleEvalMark> peopleEvalMark_set = new HashSet<PeopleEvalMark>();
    
    public PeopleEvaluation(){
    	
    }
    
    public PeopleEvaluation(PeopleEvaluation peopleEvaluation){
    	id = peopleEvaluation.getId();
    	evaluatee = peopleEvaluation.getEvaluatee();
    	evaluation = peopleEvaluation.getEvaluation();
    	evaluationYear = peopleEvaluation.getEvaluationYear();
    	evaluationMonth = peopleEvaluation.getEvaluationMonth();
    	status = peopleEvaluation.getStatus();
    	mark = peopleEvaluation.getMark();
    	peopleEvalMark_set = peopleEvaluation.getPeopleEvalMark_set();
    }
    

	public double getMark() {
		return mark;
	}

	public void setMark(double mark) {
		this.mark = mark;
	}

	public Evaluation getEvaluation() {
		return evaluation;
	}
	public void setEvaluation(Evaluation evaluation) {
		this.evaluation = evaluation;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public UserInfor getEvaluatee() {
		return evaluatee;
	}
	public void setEvaluatee(UserInfor evaluatee) {
		this.evaluatee = evaluatee;
	}


	public Set<PeopleEvalMark> getPeopleEvalMark_set() {
		
		Iterator<PeopleEvalMark> evalItemMark_it =  peopleEvalMark_set.iterator();
		int thisMark = 0;
		while (evalItemMark_it.hasNext()){
			thisMark += evalItemMark_it.next().getMark();
		}
		mark = thisMark;
		return peopleEvalMark_set;
	}
	public void setPeopleEvalMark_set(Set<PeopleEvalMark> peopleEvalMark_set) {
		this.peopleEvalMark_set = peopleEvalMark_set;

	}
	public int getEvaluationYear() {
		return evaluationYear;
	}
	public void setEvaluationYear(int evaluationYear) {
		this.evaluationYear = evaluationYear;
	}
	public int getEvaluationMonth() {
		return evaluationMonth;
	}
	public void setEvaluationMonth(int evaluationMonth) {
		this.evaluationMonth = evaluationMonth;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	   
	public void addPeopleEvalMark(PeopleEvalMark evalMark){
		getPeopleEvalMark_set().add(evalMark);
		mark += evalMark.getMark();
	}
	
	public void removePeopleEvalMark(PeopleEvalMark evalMark){
		getPeopleEvalMark_set().remove(evalMark);
		mark -= evalMark.getMark();
	}
}
