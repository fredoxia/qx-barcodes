<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib prefix="s" uri="/struts-tags" %>
<html>
<head>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>员工信息管理</title>
<%@ include file="../../common/Style.jsp"%>
<script src="<%=request.getContextPath()%>/conf_files/js/datetimepicker_css.js" type=text/javascript></script>
<script>
$(document).ready(function(){
	parent.$.messager.progress('close'); 
});
function checkUserName(){
	
    var params=$("#userInforForm").serialize();  

    $.post("userJSON!checkUserName",params, checkUserNameBackProcess,"json");	
}

function checkUserNameBackProcess(data){
	var result = data.result;
	var userName = $("#user_name").val();
	if (result == true){
		$("#userNameDiv").html(userName + " 已经在使用");
	    return false;
	}else{
		$("#userNameDiv").html(userName + " 可以使用");
		return true;
	}
}

function submitUser(){
    clearAllData();
    var userID = $("#user_id").val();
    if (userID != 0){
	    var params=$("#userInforForm").serialize();  
	    $.post("userJSON!getUser",params, getUserBackProcess,"json");	
    }
}
function getUserBackProcess(data){
    var user = data.user;
	
    if (user != ""){
    	$("#name").attr("value",user.name);
    	$("#passwordU").attr("value",user.password);
    	$("#user_name").attr("value",user.user_name);
    	$("#roleType").attr("value",user.roleType);
    	$("#jinsuanID").attr("value",user.jinsuanID);
    	var departmentCode = user.department;
    	var departSlect = $("#department");

    	var count=$("#department option").length;

    	for(var i=0;i<count;i++){     
    	    if(departSlect.get(0).options[i].value == departmentCode)  {  
    	    	    departSlect.get(0).options[i].selected = true;  
    	            break;  
    	    }  
    	}
        if (user.onBoardDate.year == undefined)
        	 $("#onBoardDate").attr("value","");
        else
    	    $("#onBoardDate").attr("value",formatDay(user.onBoardDate.year,user.onBoardDate.month,user.onBoardDate.date));
    	$("#homePhone").attr("value",user.homePhone);
    	$("#mobilePhone").attr("value",user.mobilePhone);
    	if (user.birthday.year == undefined)
    	    $("#birthday").attr("value","");
    	else
    	   $("#birthday").attr("value",formatDay(user.birthday.year,user.birthday.month,user.birthday.date));
    	$("#jobTitle").attr("value",user.jobTitle);
    	$("#baseSalary").attr("value",user.baseSalary);
    	$("#baseVacation").attr("value",user.baseVacation);
    	$("#idNumber").attr("value",user.idNumber);

    	if (user.resign ==0)
    		$("#resign").attr("checked",false);
    	else
    		$("#resign").attr("checked",true);
    }else {
    	alert("获取员工信息发生错误!");
    }
}

function formatDay(year, month, day){
	year = 1900 + year;
	month = month +1;
	if (month <10)
		month = "0" + month;
	if (day <10)
		day = "0" + day;
	return year +"-" + month + "-" +day;
}

function clearAllData(){
	$("#name").attr("value","");
	$("#passwordU").attr("value","");
	$("#user_name").attr("value","");
	$("#department").attr("value","");
	$("#onBoardDate").attr("value","");
	$("#homePhone").attr("value","");
	$("#mobilePhone").attr("value","");
	$("#birthday").attr("value","");
	$("#jobTitle").attr("value","");
	$("#baseSalary").attr("value","");
	$("#baseVacation").attr("value","");
	$("#idNumber").attr("value","");
	$("#roleType").attr("value",0);
	$("#jinsuanID").attr("value",0);
	$("#userNameDiv").html("");
	$("#resign").attr("checked",false);
}

function saveOrUpdate(){
	if (validate()){
		var params=$("#userInforForm").serialize();  
		$.post("userJSON!checkUserName",params, submitBackProcess,"json");	
	}
}


function submitBackProcess(data){
	var result = data.result;
	var userName = $("#user_name").val();
	if (result == true){
		$("#userNameDiv").html(userName + " 已经在使用");
	}else{
		document.userInforForm.action="userJSP!saveOrUpdate";
		document.userInforForm.submit();
	}
}

function validate(){
	var error = "";
	if ($("#name").val() == ""){
		error +="姓名 - 不能为空\n";
		$("#name").focus();
	}
	if ($("#department").val() == ""){
		error +="部门 - 不能为空\n";
		$("#department").focus();
	}	

	
	var homephone = $("#homePhone").val();
	if (homephone != "" && isNaN(homephone)){
		error +="住宅电话 - 包含了非数字信息\n";
	    $("#homePhone").focus();
    }else if ($("#homePhone").val().length > 14){
    	error +="住宅电话 - 超过最长14位\n";
    }		

	var mobilePhone = $("#mobilePhone").val();
    if (mobilePhone == ""){
		error +="手机 - 不能为空\n";
		$("#mobilePhone").focus();
    } else if (isNaN(mobilePhone)){
		error +="手机 - 包含了非数字信息\n";
	    $("#mobilePhone").focus();
    } else if ($("#mobilePhone").val().length > 11){
    	error +="手机 - 超过最长11位\n";
    }		 

	
	var baseSalary = $("#baseSalary").val();
	if (baseSalary != "" && isNaN(baseSalary)){
		error +="基本工资 - 包含了非数字信息\n";
	    $("#baseSalary").focus();
    } else if (parseInt(baseSalary) == 0)
    	$("#baseSalary").attr("value","");
	
	var baseVacation = $("#baseVacation").val();
	if (baseVacation != "" && isNaN(baseVacation)){
		error +="基本假期 - 包含了非数字信息\n";
	    $("#baseVacation").focus();
    }else if (parseInt(baseVacation) == 0)
    	$("#baseVacation").attr("value","");	
	
	var idNumber = $("#idNumber").val();
	if ($("#idNumber").val() != "" && isNaN(idNumber)){
		error +="身份证 - 包含了非数字信息\n";
	    $("#idNumber").focus();
    } else if ($("#idNumber").val().length > 18){
    	error +="身份证 - 超过最长18位\n";
    }
    
	if ($("#user_name").val() == ""){
		error +="系统用户名 - 不能为空\n";
		$("#user_name").focus();
	}	
	if ($("#passwordU").val() == ""){
		error +="系统密码 - 不能为空\n";
		$("#passwordU").focus();
	} else if ($("#passwordU").val().length > 8){
    	error +="系统密码 - 超过最长8位\n";
    }
    
	if (error == "")
		return true;
	else{
		alert(error);
		return false;
	}
		
}
</script>

</head>
<body>

<s:form id="userInforForm" name="userInforForm" action="" method="POST" theme="simple">
 <table width="90%" align="center"  class="OuterTable">
 <tr><td>

 <table width="100%" border="0">
    <tr class="PBAOuterTableTitale">
       <td colspan="6">员工信息管理</td>
    </tr>
    <tr>
       <td colspan="6" height="6"><s:fielderror/></td>
    </tr>
    <tr class="InnerTableContent">
      <td width="87" height="19"><strong>现有员工：</strong></td>
      <td width="100">
       <select name="formBean.userInfor.user_id" size="1" id="user_id" onchange="submitUser();">
           <option value="0">---新增---</option>
           <s:iterator value="#request.ALL_USER" id = "user">
             <option value="<s:property value="#user.user_id"/>"><s:property value="#user.user_name"/></option>
           </s:iterator>
       </select>
      </td>
      <td width="88">&nbsp;</td>
      <td width="125"></td>
      <td width="90">&nbsp;</td>
      <td width="392"></td>
    </tr>
    <tr class="InnerTableContent">
      <td height="4" colspan="6"><hr width="100%" color="#FFCC00"/></td>
    </tr>
    <tr class="InnerTableContent">
      <td height="19"><strong>员工姓名：</strong></td>
      <td>
         <input type="text" name="formBean.userInfor.name" id="name" />
         <input type="hidden" name="formBean.userInfor.roleType" id="roleType" value="0"/>
         
      </td>
      <td><strong>部门：</strong></td>
      <td>
         <select name="formBean.userInfor.department"  size="1" id="department">
             <option value="0">---------</option>
             <option value="01">会计部</option>
             <option value="02">销售部</option>
             <option value="03">物流部</option>
         </select>
      </td>
      <td><strong>入职时间：</strong></td>
      <td>
        <s:textfield id="onBoardDate" readonly="true" name="formBean.userInfor.onBoardDate"> 
             <s:param name="value"><s:date name="formBean.userInfor.onBoardDate" format="yyyy-MM-dd" /></s:param>
        </s:textfield>
		<a href="javascript:NewCssCal('onBoardDate','yyyymmdd','arrow')">
			<img src="<%=request.getContextPath()%>/conf_files/web-image/cal.gif" width="16" height="16" alt="Pick a date" border="0">
		</a>
      </td>
    </tr>
   <tr class="InnerTableContent">
      <td height="4" colspan="6"><hr width="100%" color="#FFCC00"/></td>
    </tr>
    <tr class="InnerTableContent">
      <td height="19"><strong>住宅电话：</strong></td>
      <td>
      <input type="text" name="formBean.userInfor.homePhone" id="homePhone" />

      </td>
      <td><strong>手机号：</strong></td>
      <td> 
         <input type="text" name="formBean.userInfor.mobilePhone" id="mobilePhone" />
      </td>
      <td><strong>生日：</strong></td>
      <td>
        <s:textfield id="birthday" readonly="true" name="formBean.userInfor.birthday"> 
                <s:param name="value"><s:date name="formBean.userInfor.birthday" format="yyyy-MM-dd" /></s:param>
        </s:textfield>
		<a href="javascript:NewCssCal('birthday','yyyymmdd','arrow')">
			<img src="<%=request.getContextPath()%>/conf_files/web-image/cal.gif" width="16" height="16" alt="Pick a date" border="0">
		</a>       
      </td>
    </tr>

    <tr class="InnerTableContent">
      <td height="4" colspan="6"><hr width="100%" color="#FFCC00"/></td>
    </tr>
        <tr class="InnerTableContent">
      <td height="19"><strong>职位：</strong></td>
      <td>
        <input type="text" name="formBean.userInfor.jobTitle" id="jobTitle" />
      </td>
      <td><strong>基本工资：</strong></td>
      <td>
        <input type="text" name="formBean.userInfor.baseSalary" id="baseSalary" /> 
      </td>
      <td><strong>基本假期：</strong></td>
      <td>
        <input type="text" name="formBean.userInfor.baseVacation" id="baseVacation" />
      </td>
    </tr>

    <tr class="InnerTableContent">
      <td height="4" colspan="6"><hr width="100%" color="#FFCC00"/></td>
    </tr>
        <tr class="InnerTableContent">
      <td height="19"><strong>身份证：</strong></td>
      <td>
        <input type="text" name="formBean.userInfor.idNumber" id="idNumber" />
      </td>
      <td><strong>用户名：</strong> 
      </td>
      <td>
         <input type="text" name="formBean.userInfor.user_name" id="user_name"  onchange="checkUserName();"/><div id="userNameDiv"></div> 
      </td>
      <td><strong>系统密码：</strong> </td>
      <td>
         <input type="text" name="formBean.userInfor.password" id="passwordU" /> 
      </td>
    </tr>
    <tr class="InnerTableContent">
      <td height="4" colspan="6"><hr width="100%" color="#FFCC00"/></td>
    </tr>
        <tr class="InnerTableContent">
      <td height="19"><strong>离职：</strong></td>
      <td>
        <input type="checkbox" name="formBean.userInfor.resign" id="resign" value="1" />
      </td>
      <td><strong>精算账号：</strong> </td>
      <td colspan="3"><input type="text" name="formBean.userInfor.jinsuanID" id="jinsuanID" value="0"   onkeypress="return is_number(event);"/> *精算账号请查找精算数据库</td>
    </tr>
    <tr class="InnerTableContent">
      <td height="4" colspan="6"><hr width="100%" color="#FFCC00"/></td>
    </tr>
    <tr class="InnerTableContent">
      <td height="30">&nbsp;</td>
      <td><input type="button" value="更新" onclick="saveOrUpdate();"/></td>
      <td>&nbsp;</td>
      <td>&nbsp;</td>
      <td>&nbsp;</td>
      <td>&nbsp;</td>
    </tr>
    <tr class="InnerTableContent">
      <td height="2" colspan="6"></td>
    </tr>
  </table>
  </td></tr>
 </table>
</s:form>

</body>
</html>