package com.onlineMIS.ORM.entity.base;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * the utility class for paging
 * 
 * @author fredo
 *
 */
public class Pager implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int recordPerPage = 15;

	public static final int FIRST_PAGE = 1;
	
	/**
	 * the total page of the whole records
	 * totalPage = totalRecord / recordPerPage
	 */
	private int totalPage = 0;


	private int totalResult = 0;
	private int currentPage = 0;

	/**
	 * the first record number in one page
	 */
	private int firstResult = 0;

	
	public Pager(){
	}
	
	public void initialize(int totalResult){
		this.totalResult = totalResult;
		currentPage = 1;
		firstResult = 0;
		totalPage = (totalResult + recordPerPage -1)/recordPerPage;
	}
	
	/**
	 * 当total result改变，需要重新刷新数值
	 * @param totalResult
	 */
	public void refresh(int totalResult){
		this.totalResult = totalResult;
		totalPage = (totalResult + recordPerPage -1)/recordPerPage;
		if (currentPage > totalPage)
			currentPage = totalPage;
			
		firstResult = recordPerPage * (currentPage -1);
	}


	public int getTotalResult() {
		return totalResult;
	}
	public void setTotalResult(int totalResult) {
		this.totalResult = totalResult;
	}
	public int getTotalPage() {
		return totalPage;
	}
	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}
	public int getCurrentPage() {
		return currentPage;
	}
	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public int getFirstResult() {
		return firstResult;
	}
	public void setFirstResult(int firstResult) {
		this.firstResult = firstResult;
	}
	public int getRecordPerPage() {
		return recordPerPage;
	}
	public void setRecordPerPage(int recordPerPage){
		this.recordPerPage = recordPerPage;
	}


	/**
	 * to calcualte the first result of the page
	 * and calcualte the current page
	 */
	public void calFirstResult(){
		 firstResult = recordPerPage * (currentPage -1);
	}

	
	public int getLastPage(){
		return totalPage;
	}
	
	public String toString(){
		return "totalPage " + totalPage  + "," +
			   "totalResult " + totalResult  + "," +
		       "currentPage " + currentPage  + "," +
		       "firstResult " + firstResult;
	}
	
	public void reset(){
		totalPage = 0;
		totalResult = 0;
		currentPage = 0;
		firstResult = 0;
	}
	
	public static void main(String args[]){
//		Pager pager = new Pager(65);
//		pager.setCurrentPage(7);
//		pager.calFirstResult();
////		pager.calLastResult(5);
//		System.out.println("---" + pager);
	}
}
