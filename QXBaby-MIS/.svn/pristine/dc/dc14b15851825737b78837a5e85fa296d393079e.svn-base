package com.onlineMIS.ORM.entity.headQ.HR;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class PeopleEvalUI extends PeopleEvaluation {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7830029369955443260L;
	/**
	 * this item is the sum of the peopleEvalMark_set
	 */
	private Set<PeopleEvalItemMark> peopleEvalItemMark_set = new HashSet<PeopleEvalItemMark>();
	
	public PeopleEvalUI(){
		
	}
	
	public PeopleEvalUI(PeopleEvaluation peopleEvaluation) {
		super(peopleEvaluation);
	}
	public Set<PeopleEvalItemMark> getPeopleEvalItemMark_set() {
		return peopleEvalItemMark_set;
	}
	public void setPeopleEvalItemMark_set(
			Set<PeopleEvalItemMark> peopleEvalItemMark_set) {
		
		this.peopleEvalItemMark_set = peopleEvalItemMark_set;
	}
	
	/**
	 * comment for the people evaluation
	 */
	private String comment;
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	
	private int numOfEvaluater;
	public int getNumOfEvaluater() {
		return numOfEvaluater;
	}
	public void setNumOfEvaluater(int numOfEvaluater) {
		this.numOfEvaluater = numOfEvaluater;
	}
	
	/**
	 * this mark is the arge of the people's evaluation for each month
	 */
	private double mark_avg;
	public double getMark_avg() {
		return mark_avg;
	}

	public void setMark_avg(double mark_avg) {
		this.mark_avg = mark_avg;
	}
	
	/**
	 * this is to show evaluaters that has approve
	 */
	private String evaluaters;
	public String getEvaluaters() {
		return evaluaters;
	}
	public void setEvaluaters(String evaluaters) {
		this.evaluaters = evaluaters;
	}
	
}
