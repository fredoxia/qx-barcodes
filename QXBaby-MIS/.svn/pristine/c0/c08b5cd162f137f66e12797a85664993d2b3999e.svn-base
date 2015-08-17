package com.onlineMIS.ORM.entity.headQ.HR;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javassist.expr.NewArray;

import com.onlineMIS.ORM.entity.headQ.user.UserInfor;

public class PeopleEvalMark implements Serializable {
	 /**
	 * 
	 */
	private static final long serialVersionUID = -3811088994508972577L;
	private int id;
	 private PeopleEvaluation peopleEvaluation = new PeopleEvaluation();
	 private UserInfor evaluater = new UserInfor();
	 private String comment;
	 private double mark;
	 private Date createDate = new Date();
	 private Set<PeopleEvalItemMark> peopleEvalItemMark_set = new HashSet<PeopleEvalItemMark>();
	 private List<PeopleEvalItemMark> peopleEvalItemMark_list = new ArrayList<PeopleEvalItemMark>();
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public PeopleEvaluation getPeopleEvaluation() {
		return peopleEvaluation;
	}
	public void setPeopleEvaluation(PeopleEvaluation peopleEvaluation) {
		this.peopleEvaluation = peopleEvaluation;
	}
	public UserInfor getEvaluater() {
		return evaluater;
	}
	public void setEvaluater(UserInfor evaluater) {
		this.evaluater = evaluater;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}

	public double getMark() {
		return mark;
	}
	public void setMark(double mark) {
		this.mark = mark;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public Set<PeopleEvalItemMark> getPeopleEvalItemMark_set() {
		return peopleEvalItemMark_set;
	}
	public void setPeopleEvalItemMark_set(
			Set<PeopleEvalItemMark> peopleEvalItemMark_set) {
		this.peopleEvalItemMark_set = peopleEvalItemMark_set;
	}
	public List<PeopleEvalItemMark> getPeopleEvalItemMark_list() {
		return peopleEvalItemMark_list;
	}
	public void setPeopleEvalItemMark_list(
			List<PeopleEvalItemMark> peopleEvalItemMark_list) {
		this.peopleEvalItemMark_list = peopleEvalItemMark_list;
	}
	
	public void putListToSet(){
		int itemMark = 0;
		
		//to put the list to set
		for (PeopleEvalItemMark evalItemMark: peopleEvalItemMark_list){
			if (evalItemMark.getEvaluationItem().getId() != 0)
				evalItemMark.setPeopleEvalMark(this);
				peopleEvalItemMark_set.add(evalItemMark);
			    itemMark += evalItemMark.getItem_mark();
		}
		
		this.setMark(itemMark);	
	}
	
	@Override
	public String toString() {
		return "PeopleEvalMark [id=" + id + ", peopleEvaluation="
				+ peopleEvaluation + ", evaluater=" + evaluater + ", comment="
				+ comment + ", mark=" + mark + ", createDate=" + createDate
				+ "]";
	}
	 
	 
}
