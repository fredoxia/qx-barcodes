package com.onlineMIS.action.headQ.HR;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javassist.expr.NewArray;

import org.springframework.stereotype.Controller;

import com.onlineMIS.ORM.entity.headQ.HR.EvaluationCtl;
import com.onlineMIS.ORM.entity.headQ.HR.PeopleEvalMark;
import com.onlineMIS.ORM.entity.headQ.HR.PeopleEvaluation;
import com.onlineMIS.ORM.entity.headQ.user.UserInfor;
import com.onlineMIS.common.Common_util;
import com.onlineMIS.converter.JSONUtilDateConverter;
import com.opensymphony.xwork2.ActionContext;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

@Controller
public class HREvalJSONAction extends HREvalAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4990758390973088073L;

	private JSONObject jsonObject;
	public JSONObject getJsonObject() {
		return jsonObject;
	}
	public void setJsonObject(JSONObject jsonObject) {
		this.jsonObject = jsonObject;
	}
	
	/**
	 * in evaluation conf page, this is used for selecting the employees who are under one management
	 */
	private List<Integer> employees = new ArrayList<Integer>();
	public List<Integer> getEmployees() {
		return employees;
	}
	public void setEmployees(List<Integer> employees) {
		this.employees = employees;
	}

	/**
	 * the search evaluation criteria evaluation
	 */
	private SearchEvalCriteria searchEval = new SearchEvalCriteria();
	public SearchEvalCriteria getSearchEval() {
		return searchEval;
	}
	public void setSearchEval(SearchEvalCriteria searchEval) {
		this.searchEval = searchEval;
	}
	
	/**
	 * the people evaluation marks list from the evaluater
	 */
	private List<PeopleEvalMark> peopleEvalMarks = null;
	public List<PeopleEvalMark> getPeopleEvalMarks() {
		return peopleEvalMarks;
	}
	public void setPeopleEvalMarks(List<PeopleEvalMark> peopleEvalMarks) {
		this.peopleEvalMarks = peopleEvalMarks;
	}
	
	/**
	 * the people evaluation marks list from the evaluater
	 */
	private List<PeopleEvaluation> peopleEvaluations = null;
	public List<PeopleEvaluation> getPeopleEvaluations() {
		return peopleEvaluations;
	}
	public void setPeopleEvaluations(List<PeopleEvaluation> peopleEvaluations) {
		this.peopleEvaluations = peopleEvaluations;
	}
	/**
	 * for the evaluation conf, to get the year-month's status
	 * @return
	 */
	public String getStatus(){
		Map<String,Object> jsonMap = new HashMap<String, Object>();
		
		String error = validateGUStatus();
		jsonMap.put("error", error);
		if (error.length()==0){
		    EvaluationCtl evaluationCtlDB = hrMgmtService.getEvaluationCtlByPK(evaluationCtl.getYear(), evaluationCtl.getMonth());
			jsonMap.put("result", evaluationCtlDB);
		}
		
		try{
			   jsonObject = JSONObject.fromObject(jsonMap);
			} catch (Exception e){
				e.printStackTrace();
			}	
		
		System.out.println(jsonObject);
		
		return SUCCESS;
	}
	
	/**
	 * for the eval conf to update the year-month status
	 * @return
	 */
	public String updateStatus(){
		Map<String,Object> jsonMap = new HashMap<String, Object>();
		
		String error = validateGUStatus();
		jsonMap.put("error", error);
		if (error.length() == 0){
		    boolean success = hrMgmtService.updateEvaluationCtl(evaluationCtl);
		    if (success)
			    jsonMap.put("success", true);
		    else
		    	jsonMap.put("success", false);
		}
		
		try{
			   jsonObject = JSONObject.fromObject(jsonMap);
			} catch (Exception e){
				e.printStackTrace();
			}	
		
		return SUCCESS;		
	}
	
	
	/**
	 * to return the result of searching people evaluation mark
	 * @return
	 */
	public String searchPeopleEval(){
		peopleEvalMarks = hrMgmtService.searchPeopleEvalMark(searchEval);
		Map<String,Object> jsonMap = new HashMap<String, Object>();
		
		jsonMap.put("result", peopleEvalMarks);

		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.setExcludes( new String[]{"peopleEvalItemMark_set","evaluationItem_set","peopleEvalMark_set","employeeUnder_Set","userFunction_Set"} );
		jsonConfig.registerJsonValueProcessor(java.util.Date.class, new JSONUtilDateConverter());  
		try{
			   jsonObject = JSONObject.fromObject(jsonMap,jsonConfig);
			} catch (Exception e){
				e.printStackTrace();
			}	
			
		return SUCCESS;
	}

	/**
	 * to delete the people evaluation mark
	 * @return
	 */
	public String deletePeopleEvalMark(){
		Map<String,Object> jsonMap = new HashMap<String, Object>();
		UserInfor loginUserInfor = (UserInfor)ActionContext.getContext().getSession().get(Common_util.LOGIN_USER);
		peopleEvalMark = hrMgmtService.getPeopleEvalMark(peopleEvalMark.getId(), true);
		
		if (hrMgmtService.isValidToDelete(loginUserInfor,peopleEvalMark)){
			hrMgmtService.deletePeopleEvalMark(peopleEvalMark);
			jsonMap.put("result", "SUCCESS");
		} else {
			jsonMap.put("result", "FAIL");
		}
		
		try{
			   jsonObject = JSONObject.fromObject(jsonMap);
			} catch (Exception e){
				e.printStackTrace();
			}
	    return SUCCESS;
	}
	
	/**
	 * to search the person's evaluation 
	 * @return
	 */
	public String searchOwnEval(){
		UserInfor loginUserInfor = (UserInfor)ActionContext.getContext().getSession().get(Common_util.LOGIN_USER);
		searchEval.setEvaluatee(loginUserInfor.getUser_id());
		peopleEvaluations = hrMgmtService.searchEvalSummary(searchEval);
		Map<String,Object> jsonMap = new HashMap<String, Object>();
		
		jsonMap.put("result", peopleEvaluations);

		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.setExcludes( new String[]{"peopleEvalItemMark_set","evaluationItem_set","peopleEvalMark_set","employeeUnder_Set","userFunction_Set"} );
		jsonConfig.registerJsonValueProcessor(java.util.Date.class, new JSONUtilDateConverter());  
		try{
			   jsonObject = JSONObject.fromObject(jsonMap,jsonConfig);

			} catch (Exception e){
				e.printStackTrace();
			}	
			
		return SUCCESS;		
	}

	/**
	 * to search the person's evaluation 
	 * @return
	 */
	public String searchPeopleEvalSummary(){
		peopleEvaluations = hrMgmtService.searchEvalSummary(searchEval);
		Map<String,Object> jsonMap = new HashMap<String, Object>();

		jsonMap.put("result", peopleEvaluations);

		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.setExcludes( new String[]{"peopleEvalItemMark_set","evaluationItem_set","peopleEvalMark_set","employeeUnder_Set","userFunction_Set"} );
		jsonConfig.registerJsonValueProcessor(java.util.Date.class, new JSONUtilDateConverter());  
		try{
			   jsonObject = JSONObject.fromObject(jsonMap,jsonConfig);
			} catch (Exception e){
				e.printStackTrace();
			}	
			
		return SUCCESS;		
	}
	
	/**
	 * for the evaluation conf to update the evaluaters and evaluatees relationship
	 * @return
	 */
	public String updateEvalRelationship(){
		int evaluator_id = searchEval.getEvaluater();
		
		Map<String,Object> jsonMap = new HashMap<String, Object>();

		try{
			hrMgmtService.updateEvalRelationship(evaluator_id, employees);
			jsonMap.put("isSuccess", true);
		} catch (Exception e) {
			jsonMap.put("isSuccess", false);
		}
		try{
			   jsonObject = JSONObject.fromObject(jsonMap);
			} catch (Exception e){
				e.printStackTrace();
			}
			
		
		return SUCCESS;
	}
	
	/**
	 * mainly to validate whether the year & month have value or not get&update
	 * @return
	 */
	private String validateGUStatus(){
		String errorString="";
		if (evaluationCtl.getYear() == 0){
			errorString += "年份必选\n";
		}
		if (evaluationCtl.getMonth() == 0)
			errorString += "月份必选\n";
		return errorString;
	}
}
