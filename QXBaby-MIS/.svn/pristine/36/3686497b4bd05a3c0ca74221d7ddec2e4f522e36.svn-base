package com.onlineMIS.ORM.entity.headQ.HR;

import java.io.Serializable;

public class PeopleEvalItemMark implements Serializable {
		/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
		private int id;
		private PeopleEvalMark peopleEvalMark = new PeopleEvalMark();
		private EvaluationItem evaluationItem = new EvaluationItem();
		private double item_mark;
		private String comment;
		
		public int getId() {
			return id;
		}
		public void setId(int id) {
			this.id = id;
		}
		public PeopleEvalMark getPeopleEvalMark() {
			return peopleEvalMark;
		}
		public void setPeopleEvalMark(PeopleEvalMark peopleEvalMark) {
			this.peopleEvalMark = peopleEvalMark;
		}
		public EvaluationItem getEvaluationItem() {
			return evaluationItem;
		}
		public void setEvaluationItem(EvaluationItem evaluationItem) {
			this.evaluationItem = evaluationItem;
		}

		public double getItem_mark() {
			return item_mark;
		}
		public void setItem_mark(double item_mark) {
			this.item_mark = item_mark;
		}
		public String getComment() {
			return comment;
		}
		public void setComment(String comment) {
			this.comment = comment;
		}


		public PeopleEvalItemMark clone(){
			PeopleEvalItemMark peopleEvalItemMark = new PeopleEvalItemMark();
			peopleEvalItemMark.setComment(comment);
			peopleEvalItemMark.setEvaluationItem(evaluationItem);
			peopleEvalItemMark.setId(id);
			peopleEvalItemMark.setItem_mark(item_mark);
			return peopleEvalItemMark;
		}
}
