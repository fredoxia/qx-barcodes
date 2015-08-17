package com.onlineMIS.action.headQ.HR;

import java.util.ArrayList;
import java.util.List;

import javassist.expr.NewArray;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.onlineMIS.ORM.DAO.headQ.HR.HRMgmtService;
import com.onlineMIS.ORM.DAO.headQ.user.UserInforService;
import com.onlineMIS.ORM.entity.headQ.HR.Evaluation;
import com.onlineMIS.ORM.entity.headQ.HR.EvaluationCtl;
import com.onlineMIS.ORM.entity.headQ.HR.PeopleEvalMark;
import com.onlineMIS.ORM.entity.headQ.HR.PeopleEvalUI;
import com.onlineMIS.ORM.entity.headQ.HR.PeopleEvaluation;
import com.onlineMIS.ORM.entity.headQ.user.UserFunctionality;
import com.onlineMIS.ORM.entity.headQ.user.UserInfor;
import com.onlineMIS.common.Common_util;
import com.onlineMIS.common.loggerLocal;
import com.opensymphony.xwork2.ActionContext;

@Controller
public class HREvalJSPAction extends HREvalAction {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5886406876852955703L;
	/**
	 * the two lists are for the page view part list
	 */
	private List<String> year = new ArrayList<String>();
	private List<String> month = new ArrayList<String>();
	public List<String> getYear() {
		return year;
	}
	public void setYear(List<String> year) {
		this.year = year;
	}
	public List<String> getMonth() {
		return month;
	}
	public void setMonth(List<String> month) {
		this.month = month;
	}
	
	/**
	 * to prepare the user list for evaluation management (evaluatees)
	 * 1. by using the hrMgmtService to get the users
	 */
	private List<UserInfor> users = new ArrayList<UserInfor>();
	public List<UserInfor> getUsers() {
		return users;
	}
	public void setUsers(List<UserInfor> users) {
		this.users = users;
	}

	/**
	 * to prepare the user list for evaluation management (evaluaters)
	 */
	private List<UserInfor> evaluaters = new ArrayList<UserInfor>();
	public List<UserInfor> getEvaluaters() {
		return evaluaters;
	}
	public void setEvaluaters(List<UserInfor> evaluaters) {
		this.evaluaters = evaluaters;
	}
	
	/**
	 * the UI Bean for displaying the person's evaluation summary each month
	 */
	private PeopleEvalUI peopleEvalUI = new PeopleEvalUI();
	public PeopleEvalUI getPeopleEvalUI() {
		return peopleEvalUI;
	}
	public void setPeopleEvalUI(PeopleEvalUI peopleEvalUI) {
		this.peopleEvalUI = peopleEvalUI;
	}


	@Autowired
	private UserInforService userInforService;

	/**
	 * the prepare action for the HR Management
	 * to prepare the view
	 * @return
	 */
	public String preEvaluationConf(){
		year = hrMgmtService.getYear();
		month = hrMgmtService.getMonth();
		
		//those who have the evaluation management right
		evaluaters = userInforService.getAvailableEvaluaters();
		
		//all the user list
		users = userInforService.getAllNormalUsers();

		return "EvalConf";
	}
	
	/**
	 * to prepare the data for create evaluation
	 * @return
	 */
	public String preEvaluationMgmtNew(){
        prepareEvalNewData();
		return "EvalMgmtNew";
	}
	
	/**
	 * the common function
	 */
	private void prepareEvalNewData() {
		year = hrMgmtService.getYear();
		month = hrMgmtService.getMonth();
		//the evaluatees should under the manager
		UserInfor loginUserInfor = (UserInfor)ActionContext.getContext().getSession().get(Common_util.LOGIN_USER);
		
		//get those users under the manager
		users  = hrMgmtService.getEmployeesUnder(loginUserInfor.getUser_id()); 
	}
	
	/**
	 * prepare the data for search evaluation
	 * @return
	 */
	public String preEvaluationMgmtSearch(){
		year = hrMgmtService.getYear();
		month = hrMgmtService.getMonth();
		
		UserInfor loginUserInfor = (UserInfor)ActionContext.getContext().getSession().get(Common_util.LOGIN_USER);
		
		users  =  hrMgmtService.prepareEvalateesUI(loginUserInfor);

		evaluaters = hrMgmtService.prepareEvaluaterUI(loginUserInfor);
		
		return "EvalMgmtSearch";
	}

	/**
	 * prepare the data for search evaluation
	 * @return
	 */
	public String preEvaluationSummaryMgmtSearch(){
		year = hrMgmtService.getYear();
		month = hrMgmtService.getMonth();
		UserInfor loginUserInfor = (UserInfor)ActionContext.getContext().getSession().get(Common_util.LOGIN_USER);
		
		users  =  hrMgmtService.prepareEvalateesUI(loginUserInfor);
		
		return "EvalMgmtPersonalSearch";
	}
	
	/**
	 * prepare the data for search evaluation
	 * @return
	 */
	public String preEvaluationSummaryPersonalSearch(){
		year = hrMgmtService.getYear();
		month = hrMgmtService.getMonth();

		return "EvalSummaryPersonalSearch";
	}

	
	/**
	 * before create the people evaluation, need run this actoin
	 * @return
	 */
	public String preCreatePeopleEval(){
		boolean validated = true;
		int year = peopleEvaluation.getEvaluationYear();
		int month = peopleEvaluation.getEvaluationMonth();

		//the the integrity of the form data
		if ( year == 0 ||  month==0 || peopleEvaluation.getEvaluatee() == null || peopleEvaluation.getEvaluatee().getUser_id()==0){
			addActionError("年，月，和被评估员工都是必选项");

			validated = false;
		} else{ 
		  //check it is evaluate herself/himself
		  UserInfor loginUserInfor = (UserInfor)ActionContext.getContext().getSession().get(Common_util.LOGIN_USER);
		  int evaluater_id = loginUserInfor.getUser_id();
		  int evaluatee_id = peopleEvaluation.getEvaluatee().getUser_id();
		  
		  if (loginUserInfor.getUser_id() == peopleEvaluation.getEvaluatee().getUser_id()){
			  addActionError("不能自己评估自己");
			  validated = false;
		  } else {
			  //to check this evaluater has evaluated this user or not
			  int sumOfEvaluation = hrMgmtService.checkPeopleEvalExist(year, month, evaluatee_id, evaluater_id);
			  if (sumOfEvaluation>0){
				  addActionError("你已经创建过该员工此月份评估，请检查"); 
			      validated = false;
			  } else {
				  //to check whether this month is open for evaluation
				  EvaluationCtl evaluationCtl = hrMgmtService.getEvaluationCtlByPK(year, month);
				  if (evaluationCtl == null){
					  addActionError("管理员还未开通该月份的评估，请联系管理员"); 
				      validated = false;
				  } else if (evaluationCtl.getStatus() == 0){
					  addActionError("管理员已经关闭该月份的评估，请联系管理员"); 
				      validated = false;
				  }
			  }
		  }
		}
		
		if (!validated){
		   prepareEvalNewData();
		   return "EvalMgmtNew";
		}
		
		//to prepare the UI bean for creating evaluation
		Evaluation evaluation = hrMgmtService.getEvaluation(year, month,true);
		if (evaluation == null){
			addActionError("无法找到对应月份的评估模板，请联系管理员"); 
			prepareEvalNewData();
			return "EvalMgmtNew";
		} else {
			peopleEvaluation.setEvaluation(evaluation);
			peopleEvaluation.setEvaluatee(userInforService.getUser(peopleEvaluation.getEvaluatee().getUser_id(),false));
			//to initialize the people evaluation
			loggerLocal.info("The size of people evaluation : " +peopleEvaluation.getEvaluation().getYear() + "," + peopleEvaluation.getEvaluation().getMonth() + "," + peopleEvaluation.getEvaluation().getEvaluationItem_set().size());
		}
		return "EvalMgmtCreate";
	}

	/**
	 * create the people evaluation and get the content
	 * @return
	 */
	public String CreatePeopleEval(){
		UserInfor loginUserInfor = (UserInfor)ActionContext.getContext().getSession().get(Common_util.LOGIN_USER);
		peopleEvalMark.setEvaluater(loginUserInfor);
		peopleEvalMark.putListToSet();
        
		hrMgmtService.savePeopEvalMark(peopleEvaluation,peopleEvalMark);
		
		UserInfor evaluatee = userInforService.getUser(peopleEvaluation.getEvaluatee().getUser_id(), false);

		addActionMessage(evaluatee.getName()+" " + peopleEvaluation.getEvaluationYear() +"-"+ peopleEvaluation.getEvaluationMonth()+"评估");
		return "EvalMgmtCreateSuccess";
	}
	
	/**
	 * view the people evaluation mark content
	 * @return
	 */
	public String ViewPeopleEvalMark(){
		UserInfor loginUserInfor = (UserInfor)ActionContext.getContext().getSession().get(Common_util.LOGIN_USER);
		peopleEvalMark = hrMgmtService.getPeopleEvalMark(peopleEvalMark.getId(), true);
		
		//to check the view mark is in the person view's scope or not
		if (peopleEvalMark == null ){
			addActionMessage("系统无法找到对应的评估表");
			return "manipulationError";			
	    }else if (hrMgmtService.isAuthorizedToView(loginUserInfor,peopleEvalMark)){
			return "PeopleEvalMarkView";
		} else {
			addActionMessage("如果你不是所选评估的评估人，被评估人或者系统管理员，你没有权限浏览此评估。");
			return "manipulationError";
		}
	}
	
	/**
	 * view the people evaluation content which is a summary
	 * @return
	 */
	public String ViewPeopleEval(){
		UserInfor loginUserInfor = (UserInfor)ActionContext.getContext().getSession().get(Common_util.LOGIN_USER);
		peopleEvalUI = hrMgmtService.getPeopleEvalSummary(peopleEvaluation.getId(), true);
		
		//to check the view mark is in the person view's scope or not
		if (peopleEvalUI == null ){
			addActionMessage("系统无法找到对应的评估表");
			return "manipulationError";			
	    }else if (hrMgmtService.isAuthorizedToView(loginUserInfor,peopleEvalUI)){
			return "PeopleEvalView";
		} else {
			addActionMessage("如果你不是所选评估的评估人，被评估人或者系统管理员，你没有权限浏览此评估。");
			return "manipulationError";
		}
	}

}
