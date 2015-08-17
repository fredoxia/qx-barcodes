package com.onlineMIS.ORM.entity.headQ.HR;

import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

public class EvaluationCtl implements Serializable {
	public static final int CLOSED = 0;
	public static final int OPEN = 1;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int year;
	private int month;
	private int status;
	 
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
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	 
	public boolean equals(Object obj){
		if(!(obj instanceof EvaluationCtl)){
		    return false;
		}else{
			EvaluationCtl user = (EvaluationCtl)obj;
		    return new EqualsBuilder().appendSuper(super.equals(obj))
		       .append(this.year, user.year)
		       .append(this.month, user.month)
		       .isEquals();
		     }
	}

	public int hashCode(){
		return new HashCodeBuilder(-528253723, -475504089)
		.appendSuper(super.hashCode())
		.append(this.year)
		.append(this.month)
		.toHashCode();
	}

}
