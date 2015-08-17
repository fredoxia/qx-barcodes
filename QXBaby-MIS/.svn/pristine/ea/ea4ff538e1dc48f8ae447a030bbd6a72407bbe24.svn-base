package com.onlineMIS.ORM.entity.headQ.HR;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class Evaluation implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7349077462654510233L;
	public static final int YEAR_DEFAULT = 9999;
	public static final int MONTH_DEFAULT = 99;
	private int id;
	private int year;
	private int month;
	private String description;
	private String evaluation_type;//in the future different group may use differnet type of evalution
	private Set<EvaluationItem> evaluationItem_set = new HashSet<EvaluationItem>();
     
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	public int getMonth() {
		return month;
	}
	public void setMonth(int month) {
		this.month = month;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getEvaluation_type() {
		return evaluation_type;
	}
	public void setEvaluation_type(String evaluation_type) {
		this.evaluation_type = evaluation_type;
	}
	public Set<EvaluationItem> getEvaluationItem_set() {
		return evaluationItem_set;
	}
	public void setEvaluationItem_set(Set<EvaluationItem> evaluationItem_set) {
		this.evaluationItem_set = evaluationItem_set;
	}
     
}
