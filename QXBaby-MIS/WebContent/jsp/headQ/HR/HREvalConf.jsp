<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib prefix="s" uri="/struts-tags" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>人力资源绩效评估管理</title>
<%@ include file="../../common/Style.jsp"%>
<script>
$(document).ready(function(){
	parent.$.messager.progress('close'); 
});
var employeePrefix = "employee";
 function getStatus(){
	 var year = $("#year").val();
	 var month = $("#month").val();
	 if (year != 0 && month != 0){
		    clearTipError();
		    var params=$("#hrEvalConfForm").serialize();  
		    $.post("hrEvalJSON!getStatus",params, getStatusProcess,"json");	
     } 
 }

function getStatusProcess(data){
	var error = data.error;
	if (error == ""){
		var evaluationCtrl = data.result;
		if (evaluationCtrl.status == undefined){
			$("#status0").attr("checked",true); 
			$("#tip").html("提示:此月份还未创建");
		}else if(evaluationCtrl.status == 0 ){
			$("#status0").attr("checked",true);
			$("#tip").html("提示:此月份已经关闭操作"); 
		} else {
			$("#status1").attr("checked",true);
			$("#tip").html("提示:此月份操作正常");
		}	
	} else {
		$("#error").html(error);
	}
}
function validateStatus(){
	 var year = $("#year").val();
	 var month = $("#month").val();
	 if (year == 0){
		 alert("请选择<年份>");
		 return false;
	 }else if (month == 0){
		 alert("请选择<月份>");
		 return false;
	 }

	 return true;
}

function updateStatus(){
  	if (validateStatus()){
		clearTipError();
	    var params=$("#hrEvalConfForm").serialize();  
	    $.post("hrEvalJSON!updateStatus",params, updateStatusProcess,"json");	 
 	}
}

function updateStatusProcess(data){
	var error = data.error;
	if (error == ""){
		var success = data.success;
		if (success == true){
			alert("操作成功!");
		}else if(success == true){
			alert("操作失败，请联系系统管理员!");
		} 
	} else {
		$("#error").html(error);
	}
}

function getManagerDP(){
	var userID = $("#evaluater").val();
	if (userID == 0){
		clearAllRelationshipData();
	} else {
	    var params = "formBean.userInfor.user_id=" + userID;  
	    $.post("userJSON!getUser",params, getManagerDPBKProcess,"json");	
	}
}
function getManagerDPBKProcess(data){
    var user = data.user;

    if (user != ""){
    	var employees = user.employeeUnder_Set;
    	clearAllRelationshipData();
    	
	    if (employees.length != 0)
		    for (var i = 0; i < employees.length; i++){
		        var employeeid = employees[i].employee_id;
		        $("#"+employeePrefix+employeeid).attr("checked",true);
		    }
    }else {
    	alert("获取信息发生错误!");
    }
}
function clearAllRelationshipData(){
	$("input[name='employees']").attr("checked",false);
}


function updateEvalRelationship(){
    var params=$("#hrEvalRelationshipForm").serialize();  
    $.post("hrEvalJSON!updateEvalRelationship",params, updateRelationshipBKProcess,"json");	
}

function updateRelationshipBKProcess(data){
	var success = data.isSuccess;
	if (success == true)
		alert("更新成功");
	else
		alert("更新失败");	
}

function clearTipError(){
	$("#tip").html("");
    $("#error").html("");
}
</script>
</head>
<body>

   <s:form id="hrEvalConfForm" action="/action/hrEvalConfJSP.action" method="POST" theme="simple" >
   <table width="90%" align="center"  class="OuterTable">
    <tr><td>

	 <table width="100%" border="0">
	    <tr class="PBAOuterTableTitale">
	       <td height="78" colspan="6">1. 绩效评估管理配置 - 开通/关闭月份评估<br />
	         <br />
           - 通过这个配置管理管理员可以开通或者关闭某个月的评估<br/>
           - 当选中一个月之后，系统会给出提示（未开通，操作正常，操作关闭）</td>
	    </tr>
	    <tr class="InnerTableContent">
	      <td width="57" height="32">
	         <strong>年</strong></td>
	      <td width="122"><s:select id="year" name="evaluationCtl.year"  list="year" headerKey="0" headerValue="-------" onchange="getStatus();"/></td>
	      <td width="19"><strong>月</strong></td>
	      <td width="186"><s:select id="month" name="evaluationCtl.month"  list="month" headerKey="0" headerValue="-------"  onchange="getStatus();"/></td>
	      <td width="495">&nbsp;</td>
	      <td width="195">&nbsp;</td>
	    </tr>
	    <tr class="InnerTableContent">
	      <td height="4" colspan="6"><hr width="100%" color="#FFCC00"/></td>
	    </tr>
	    <tr class="InnerTableContent">
	      <td width="57" height="32">
	         <strong>状态</strong>	      </td>
	      <td>开通<input id="status1" name="evaluationCtl.status" type="radio" value="1"/>关闭<input id="status0" name="evaluationCtl.status" type="radio" value="0"/></td>
	      <td colspan="3"><div id="tip"></div><div id="error" style="color:red;font-size:13px"></td>
	      <td>&nbsp;</td>
	    </tr>
	    <tr class="InnerTableContent">
	      <td height="4" colspan="6"><hr width="100%" color="#FFCC00"/></td>
	    </tr>
	    <tr class="InnerTableContent">
	      <td height="30" colspan="6"><input type="button" value="提交修改" onclick="updateStatus();"/></td>
	    </tr>
	  </table>
   </td></tr>
 </table>
</s:form>
<br/>
   <s:form id="hrEvalRelationshipForm" action="/action/hrEvalConfJSP.action" method="POST" theme="simple" >
   <table width="90%" align="center"  class="OuterTable">
    <tr><td>

	 <table width="100%" border="0">
	    <tr class="PBAOuterTableTitale">
	       <td height="78" colspan="2">2. 绩效评估管理配置 - 人员评估对应关系<br />
	         <br />
           - 通过这个配置管理管理员可以配置某位评估人员所应该评估的员工<br/>           </td>
	    </tr>
	    <tr class="InnerTableContent">
	      <td width="77" height="32">
	         <strong>评估人员</strong></td>
	      <td width="826"><s:select id="evaluater" name="searchEval.evaluater"  list="evaluaters" listKey="user_id" listValue="user_name" headerKey="0" headerValue="-------" onChange="getManagerDP();"/></td>
        </tr>
		<tr class="InnerTableContent">
	      <td height="32" colspan="2">
	         <strong>选择所属员工</strong>
	         <table width="100%" border="0" cellspacing="0" cellpadding="0">
               <s:iterator value="users" status="st" id="user" >
                  <s:if test="(#st.index % 5) == 0"><tr></s:if>
                         <td><input type="checkbox" id="employee<s:property value="#user.user_id"/>" name="employees" value="<s:property value="#user.user_id"/>"/><s:property value="#user.user_name"/></td>
                  <s:if test="((#st.index+1) % 5) == 0 && (#st.index != 0)"></tr></s:if>
               </s:iterator>
             </table>
           </td>
        </tr>
	    <tr class="InnerTableContent">
	      <td height="30" colspan="2"><input type="button" value="提交" onclick="updateEvalRelationship();"/></td>
	    </tr>
	  </table>
   </td></tr>
 </table>
</s:form>
</body>
</html>