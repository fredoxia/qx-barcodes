package com.onlineMIS.ORM.DAO.headQ.HR;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.onlineMIS.ORM.DAO.headQ.barCodeGentor.YearDaoImpl;
import com.onlineMIS.ORM.DAO.headQ.user.UserInforService;
import com.onlineMIS.ORM.entity.headQ.HR.Evaluation;
import com.onlineMIS.ORM.entity.headQ.HR.EvaluationCtl;
import com.onlineMIS.ORM.entity.headQ.HR.MagerEmployeeRelationship;
import com.onlineMIS.ORM.entity.headQ.HR.PeopleEvalItemMark;
import com.onlineMIS.ORM.entity.headQ.HR.PeopleEvalMark;
import com.onlineMIS.ORM.entity.headQ.HR.PeopleEvalUI;
import com.onlineMIS.ORM.entity.headQ.HR.PeopleEvaluation;
import com.onlineMIS.ORM.entity.headQ.barcodeGentor.Year;
import com.onlineMIS.ORM.entity.headQ.user.UserFunctionality;
import com.onlineMIS.ORM.entity.headQ.user.UserInfor;
import com.onlineMIS.action.headQ.HR.SearchEvalCriteria;
import com.onlineMIS.common.Common_util;
import com.onlineMIS.common.loggerLocal;

@Service
public class HRMgmtService {
	@Autowired
	private UserInforService userInforService;
	
	@Autowired 
	private YearDaoImpl yearDaoImpl;
	
//	@Autowired
//	private HRMgmtDaoImpl hrMgmtDaoImpl;
	
	@Autowired
	private HREvaluationCtrlDAOImpl hrEvaluationCtrlDAOImpl;
	
	@Autowired
	private HREvaluationDAOImpl hrEvaluationDAOImpl;
	
	@Autowired
	private HRPeoplEvaluationDAOImpl hrPeoplEvaluationDAOImpl;
	
	@Autowired
	private HRPeopleEvalMarkDAOImpl hrPeopleEvalMarkDAOImpl;
	
	@Autowired
	private HREvalRelationshipDAOImpl hrEvalRelationshipDAOImpl;

	public List<String> getYear(){
		List<Year> years = yearDaoImpl.getAll(true);
		List<String> yearList = new ArrayList<String>();
		for (Year year: years){
			String yearString = year.getYear();
			yearList.add(yearString);
		}
		return yearList;
	}
	
	public List<String> getMonth(){
		return Common_util.getMonth();
	}
	
	
	public EvaluationCtl getEvaluationCtlByPK(int year, int month){
		DetachedCriteria criteria = DetachedCriteria.forClass(EvaluationCtl.class,"evalCtrl");
		criteria.add(Restrictions.eq("evalCtrl.year", year));
		criteria.add(Restrictions.eq("evalCtrl.month", month));
		
		List<EvaluationCtl> evaluationCtls =  hrEvaluationCtrlDAOImpl.getByCritera(criteria, false);
		if (evaluationCtls == null || evaluationCtls.size()==0)
			return null;
		else 
            return evaluationCtls.get(0);
		
	}

	public boolean updateEvaluationCtl(EvaluationCtl evaluationCtl) {
		
		try{
			hrEvaluationCtrlDAOImpl.saveOrUpdate(evaluationCtl, false);
		} catch (Exception e) {
				loggerLocal.error(e);
				return false;
		}
		return true;   
	}
	
	@SuppressWarnings("unchecked")
	public int checkPeopleEvalExist(int year, int month, int user_id, int evaluater_id){
		String hql = "select count(*) from PeopleEvalMark as pem where pem.peopleEvaluation.evaluationYear="+year+" and pem.peopleEvaluation.evaluationMonth="+month+" and pem.evaluater.user_id = "+evaluater_id+" and pem.peopleEvaluation.evaluatee.user_id =" + user_id;
		List<Long> sum = hrPeopleEvalMarkDAOImpl.getHibernateTemplate().find(hql);
		
		return sum.get(0).intValue();
	}

	/**
	 * to get the evaluation for the year and month, if there is no, use 9999, 99
	 * @param year
	 * @param month
	 * @return
	 * @throws Exception 
	 */
	@Transactional
	public Evaluation getEvaluation(int year, int month, boolean isInitialized) {
		DetachedCriteria criteria = DetachedCriteria.forClass(Evaluation.class,"eval");
		criteria.add(Restrictions.eq("eval.year", year));
		criteria.add(Restrictions.eq("eval.month", month));
		
		List<Evaluation> evaluations = hrEvaluationDAOImpl.getByCritera(criteria, false);
		
		if (evaluations==null || evaluations.size()==0){
			DetachedCriteria criteria2 = DetachedCriteria.forClass(Evaluation.class,"eval");
			criteria2.add(Restrictions.eq("eval.year", Evaluation.YEAR_DEFAULT));
			criteria2.add(Restrictions.eq("eval.month", Evaluation.MONTH_DEFAULT));
			evaluations = hrEvaluationDAOImpl.getByCritera(criteria2, false);
			if (evaluations==null || evaluations.size()==0){
				loggerLocal.error("无法找到对应的评估表格模板，" + year +"-" +month);
                return null;
			}
		}
		
		//to initialize the item set or not
		if (isInitialized)
			hrEvaluationDAOImpl.initialize(evaluations.get(0));

		return evaluations.get(0);
	}

	/**
	 * to save the people evaluation from one person
	 * @param peopleEvaluation
	 * @param peopleEvalMark
	 */
	public void savePeopEvalMark(PeopleEvaluation peopleEvaluation,
			PeopleEvalMark peopleEvalMark) {
		DetachedCriteria criteria = DetachedCriteria.forClass(PeopleEvaluation.class);

		criteria.add(Restrictions.eq("evaluatee.user_id", peopleEvaluation.getEvaluatee().getUser_id()));
		criteria.add(Restrictions.eq("evaluationYear", peopleEvaluation.getEvaluationYear()));
		criteria.add(Restrictions.eq("evaluationMonth", peopleEvaluation.getEvaluationMonth()));
		
		List<PeopleEvaluation> peopleEvaluationCopy = hrPeoplEvaluationDAOImpl.getByCritera(criteria, false);
		if (peopleEvaluationCopy != null && peopleEvaluationCopy.size() !=0){
			peopleEvaluation = peopleEvaluationCopy.get(0);
		}
		peopleEvalMark.setCreateDate(new Date());
		peopleEvalMark.setPeopleEvaluation(peopleEvaluation);
		peopleEvaluation.addPeopleEvalMark(peopleEvalMark);
		
		hrPeoplEvaluationDAOImpl.saveOrUpdate(peopleEvaluation, false);

	}

	/**
	 * to search by the search bean
	 * @param searchEval
	 * @return
	 */
	public List<PeopleEvalMark> searchPeopleEvalMark(SearchEvalCriteria searchEval) {
		DetachedCriteria criteria = DetachedCriteria.forClass(PeopleEvalMark.class,"eval");
		DetachedCriteria criteria_peopleEval = criteria.createCriteria("peopleEvaluation");
		criteria_peopleEval.add(Restrictions.eq("evaluationYear", searchEval.getYear()));
		criteria_peopleEval.add(Restrictions.eq("evaluationMonth", searchEval.getMonth()));
		if (searchEval.getEvaluatee() != Common_util.ALL_RECORD)
		    criteria_peopleEval.add(Restrictions.eq("evaluatee.user_id", searchEval.getEvaluatee()));
		if (searchEval.getEvaluater() != Common_util.ALL_RECORD)
		    criteria.add(Restrictions.eq("eval.evaluater.user_id", searchEval.getEvaluater()));
		return hrPeopleEvalMarkDAOImpl.getByCritera(criteria, false);
	}

	/**
	 * to search people evaluatio mark by id
	 * @param id
	 * @param initialize
	 * @return
	 */
	@Transactional
	public PeopleEvalMark getPeopleEvalMark(int id, boolean initialize) {
		PeopleEvalMark  peopleEvalMark = hrPeopleEvalMarkDAOImpl.get(id, false);
		
		if (peopleEvalMark!= null && initialize){
			hrPeopleEvalMarkDAOImpl.initialize(peopleEvalMark);
		}

		return peopleEvalMark;
	}

	/**
	 * to check the user can view the people evaluation mark or not
	 * 1. the person is the evaluater of this evaluation
	 * 2. the person is the evaluatee of this evaluation
	 * 3. the person is the admin || authorized to view all people's evaluation
	 * @param loginUserInfor
	 * @param id
	 * @return
	 */
	public boolean isAuthorizedToView(UserInfor loginUserInfor, PeopleEvalMark peopleEvalMark) {
		if (loginUserInfor.getRoleType() == UserInfor.SUPER_ADMIN || loginUserInfor.containFunction(UserFunctionality.EVALUATION_MANAGE_ALL_EVALUATER))
		    return true;
		else {
			int evaluater_id = peopleEvalMark.getEvaluater().getUser_id();
			int loginUser_id = loginUserInfor.getUser_id();
			int evaluatee_id = peopleEvalMark.getPeopleEvaluation().getEvaluatee().getUser_id();
			if (evaluater_id == loginUser_id || evaluatee_id==loginUser_id)
				return true;
			else
				return false;
		}
	}

	/**
	 * to check the user can view the people evaluation or not
	 * 1. the person is the evaluater of this evaluation
	 * 2. the person is the evaluatee of this evaluation
	 * 3. the person is the admin
	 * @param loginUserInfor
	 * @param id
	 * @return
	 */
	public boolean isAuthorizedToView(UserInfor loginUserInfor, PeopleEvaluation peopleEvaluation) {
		if (loginUserInfor.getRoleType() == UserInfor.SUPER_ADMIN || loginUserInfor.containFunction(UserFunctionality.EVALUATION_VIEW_SUMMARY))
		    return true;
		else {
			int loginUser_id = loginUserInfor.getUser_id();
			int evaluatee_id = peopleEvaluation.getEvaluatee().getUser_id();
			if (evaluatee_id==loginUser_id)
				return true;
			else
				return false;
		}
	}
	
	/**
	 * to check whether this evaluation mark is valid to be deleted
	 * 1. if the login user is super admin||assigned the right to manager all users and if the evaluation period is open 
	 * 2. if the evaluation period is open and the login user is the evaluator of this evaluation
	 * @param loginUserInfor
	 * @param peopleEvalMark
	 * @return
	 */
	public boolean isValidToDelete(UserInfor loginUserInfor,
			PeopleEvalMark peopleEvalMark) {
		PeopleEvaluation peopleEvaluation = peopleEvalMark.getPeopleEvaluation();
		EvaluationCtl evaluationCtl = getEvaluationCtlByPK(peopleEvaluation.getEvaluationYear(), peopleEvaluation.getEvaluationMonth());
		
		if (evaluationCtl.getStatus() == EvaluationCtl.CLOSED)
			return false;
		else if (loginUserInfor.getRoleType() == UserInfor.SUPER_ADMIN || loginUserInfor.containFunction(UserFunctionality.EVALUATION_MANAGE_ALL_EVALUATER))
		    return true;
		else {
			int evaluater_id = peopleEvalMark.getEvaluater().getUser_id();
			int loginUser_id = loginUserInfor.getUser_id();

			if (loginUser_id == evaluater_id)
				return true;
			else 
				return false;
		}
	}

	/**
	 * to delte the people evaluation mark
	 * @param peopleEvalMark
	 */
	public void deletePeopleEvalMark(PeopleEvalMark peopleEvalMark) {
		hrPeopleEvalMarkDAOImpl.delete(peopleEvalMark, false);
	}

	/**
	 * to search the persons' own evaluation
	 * @param searchEval
	 * @param loginUserInfor
	 * @return
	 */
	public List<PeopleEvaluation> searchEvalSummary(SearchEvalCriteria searchEval) {
		DetachedCriteria criteria = DetachedCriteria.forClass(PeopleEvaluation.class);

		if (searchEval.getEvaluatee() != Common_util.ALL_RECORD)
		     criteria.add(Restrictions.eq("evaluatee.user_id", searchEval.getEvaluatee()));
		criteria.add(Restrictions.eq("evaluationYear", searchEval.getYear()));
		if (searchEval.getMonth() != Common_util.ALL_RECORD)
			criteria.add(Restrictions.eq("evaluationMonth", searchEval.getMonth()));
		
		return hrPeoplEvaluationDAOImpl.getByCritera(criteria, false);
	}

	/**
	 * to get the people evaluation which is a summary
	 * @param id
	 * @param b
	 * @return
	 */
	@Transactional
	public PeopleEvalUI getPeopleEvalSummary(int id, boolean initialize) {
		DetachedCriteria criteria = DetachedCriteria.forClass(PeopleEvaluation.class);
		criteria.add(Restrictions.eq("id", id));
		
		List<PeopleEvaluation> peopleEvaluations = hrPeoplEvaluationDAOImpl.getByCritera(criteria, false);
		
		
		if (peopleEvaluations == null || peopleEvaluations.size()==0)
			return null;
		else {
			
			PeopleEvaluation peopleEvaluation = peopleEvaluations.get(0);
			
			PeopleEvalUI peopleEvalUI = new PeopleEvalUI(peopleEvaluation);
			String comment = "";
			Map<Integer, PeopleEvalItemMark> evaluationMap = new HashMap<Integer, PeopleEvalItemMark>();
			
			if (initialize){
				hrPeoplEvaluationDAOImpl.initialize(peopleEvalUI);
				Iterator<PeopleEvalMark> peopleEvalMark_it = peopleEvalUI.getPeopleEvalMark_set().iterator();

				while (peopleEvalMark_it.hasNext()){
					
					PeopleEvalMark evalMark = peopleEvalMark_it.next();
					if (evalMark.getComment().trim().length() != 0)
						comment += " - " + evalMark.getComment() + "   ";
					
					
					Iterator<PeopleEvalItemMark> peopleEvalItemMark_it = evalMark.getPeopleEvalItemMark_set().iterator();
					while (peopleEvalItemMark_it.hasNext()){
						PeopleEvalItemMark itemMark = peopleEvalItemMark_it.next();
						int itemId = itemMark.getEvaluationItem().getId();
						PeopleEvalItemMark markInMap = evaluationMap.get(itemId);
						
						if (markInMap == null){
								evaluationMap.put(itemId, itemMark.clone());
						} else {
							String comment_item = markInMap.getComment();
							if (itemMark.getComment().trim().length() != 0){
								comment_item += " - " + itemMark.getComment();
							}
							
							double mark = markInMap.getItem_mark();
							mark += itemMark.getItem_mark();
							
							markInMap.setComment(comment_item);
							markInMap.setItem_mark(mark);
							markInMap.setEvaluationItem(itemMark.getEvaluationItem());
							evaluationMap.put(itemId, markInMap);
						}
					}
					
					peopleEvalUI.setComment(comment);
				}
			}
			
			Set<PeopleEvalItemMark> vals = new HashSet<PeopleEvalItemMark>();
			vals.addAll(evaluationMap.values());

			peopleEvalUI.setPeopleEvalItemMark_set(vals);
			
			constructPeopleEvalUI(peopleEvalUI);
			
			return peopleEvalUI;
		}

	}

	/**
	 * to calculate the average mark for each item
	 * @param vals
	 */
	private void constructPeopleEvalUI(PeopleEvalUI peopleEvalUI) {
		//to calculate the average mark
		Set<PeopleEvalItemMark> vals = peopleEvalUI.getPeopleEvalItemMark_set();
		int size = peopleEvalUI.getPeopleEvalMark_set().size();
		Iterator<PeopleEvalItemMark> peIterator = vals.iterator();
		double mark_avg = 0;
		while (peIterator.hasNext()){
			PeopleEvalItemMark peopleEvalItemMark = peIterator.next();
			double mark = peopleEvalItemMark.getItem_mark()/size;
			peopleEvalItemMark.setItem_mark(mark);
			mark_avg += mark;
		}
		peopleEvalUI.setMark_avg(mark_avg);
		
		//to set the number of evaluaters
		Set<PeopleEvalMark> peopleEvalMarks = peopleEvalUI.getPeopleEvalMark_set();
		peopleEvalUI.setNumOfEvaluater(peopleEvalMarks.size());
		
		//to set the evaluators
		Iterator<PeopleEvalMark> peoIterator = peopleEvalMarks.iterator();
		String evaluaters = "";
		while (peoIterator.hasNext()){
			evaluaters += peoIterator.next().getEvaluater().getName() +" ";
		}
		peopleEvalUI.setEvaluaters(evaluaters);
		
	}

	/**
	 * to prepare the evaluater list for UI when search
	 * 1. if the login user is super admin or assigned the manage all user evaluation mark, then add all users
	 * 2. if not assigned himself
	 * @param loginUserInfor
	 * @return
	 */
	public List<UserInfor> prepareEvaluaterUI(UserInfor loginUserInfor) {
		List<UserInfor> evaluaters = new ArrayList<UserInfor>();
		if (loginUserInfor.getRoleType()==UserInfor.SUPER_ADMIN || loginUserInfor.containFunction(UserFunctionality.EVALUATION_MANAGE_ALL_EVALUATER)){
			UserInfor allUserInfor = new UserInfor();
			allUserInfor.setUser_id(Common_util.ALL_RECORD);
			allUserInfor.setUser_name("所有员工");
			evaluaters.add(allUserInfor);
			evaluaters.addAll(userInforService.getAllNormalUsers());
		}else 
			evaluaters.add(loginUserInfor);
		return evaluaters;
	}
	
	/**
	 * to prepare the evaluatees list for UI when search the people evaluation or evaluation summary
	 * 1. if the login user is admin or assigned the manage all user evaluation mark, then add all
	 * 2. if not, assign the employees under himself
	 * @param loginUserInfor
	 * @return
	 */
	public List<UserInfor> prepareEvalateesUI(UserInfor loginUserInfor) {
		List<UserInfor> evaluatees = new ArrayList<UserInfor>();
		if (loginUserInfor.getRoleType()==UserInfor.SUPER_ADMIN || loginUserInfor.containFunction(UserFunctionality.EVALUATION_MANAGE_ALL_EVALUATER)){
			UserInfor allUserInfor = new UserInfor();
			allUserInfor.setUser_id(Common_util.ALL_RECORD);
			allUserInfor.setUser_name("所有员工");
			evaluatees.add(allUserInfor);
			evaluatees.addAll(userInforService.getAllNormalUsers());
		}else{ 
			evaluatees = getEmployeesUnder(loginUserInfor.getUser_id());
		}
		return evaluatees;
	}

	/*
	 * to update the employees to the assigned manager
	 */
	public void updateEvalRelationship(int manager_id, List<Integer> employees) {
		hrEvalRelationshipDAOImpl.removeEmployeeUnder(manager_id);
		hrEvalRelationshipDAOImpl.addEmployeeUnder(manager_id,employees);
	}

	/**
	 * to get the employees under
	 * @param employeeIDUnder
	 * @return
	 */
	public List<UserInfor> getEmployeesUnder(int userId) {
		List<Integer> ids = hrEvalRelationshipDAOImpl.getEmployeeUnder(userId);
		
		if (ids.size() == 0)
			return new ArrayList<UserInfor>();
		else
		    return userInforService.getUsers(ids);
	}



}
