<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib prefix="s" uri="/struts-tags" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>人力资源绩效评估管理</title>
<%@ include file="../../common/Style.jsp"%>
<script>

function validateForm(){
	 var year = $("#year").val();
	 var month = $("#month").val();
	 var user = $("#evaluater").val();
	 var evaluatee = $("#evaluatee").val();
	 if (year == 0){
		 alert("请选择<年份>");
		 return false;
	 }else if (month == 0){
		 alert("请选择<月份>");
		 return false;
	 }else if (user ==0){
		 alert("请选择<评估管理人员>");
		 return false;
	 }else if (evaluatee ==0){
		 alert("请选择<被评估人员>");
		 return false;
	 }

	 return true;
}
function searchPeopleEval(){
	if (validateForm()){
	    var params=$("#peopleEvalSearchForm").serialize();  
	    $.post("hrEvalJSON!searchPeopleEval",params, searchPeopleEvalBackProcess,"json");	
	}
}
function searchPeopleEvalBackProcess(data){
    var evals = data.result;

    $('#orgTablebody tr').each(function () {                
        $(this).remove();
    });
    
    if (evals != ""){
    	for (var i = 0; i < evals.length; i++){
	    	var j = i+1;
	    	var bg = "";
	    	if ((i % 2) == 0)
	    		bg = " style='background-color: rgb(255, 250, 208);'";
	        if (evals[i] != "")  
		          $("<tr align='center' class='InnerTableContent'" + bg +"><td>"+evals[i].peopleEvaluation.evaluationYear +"-" + evals[i].peopleEvaluation.evaluationMonth +"</td><td>"+evals[i].evaluater.user_name+"</td><td>"+evals[i].peopleEvaluation.evaluatee.user_name+"</td><td>"+evals[i].createDate.toString()+"</td><td>"+evals[i].mark+"</td><td><a href='#' onclick=\"window.open ('hrEvalJSP!ViewPeopleEvalMark?peopleEvalMark.id="+ evals[i].id +"','新窗口','height=700, width=700, toolbar=no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=no');\"><img src='<%=request.getContextPath()%>/conf_files/web-image/editor.gif' border='0'/></a><td></td></td></tr>").appendTo("#orgTablebody");
	    }
    }else {
    	$("<tr class='InnerTableContent' style='background-color: rgb(255, 250, 208);' align='center'><td colspan=7><font color='red'>对应条件没有查询信息</font> </td></tr>").appendTo("#orgTablebody");
    }
}
$(document).ready(function(){
	parent.$.messager.progress('close'); 
});
</script>
</head>
<body>

	<s:form id="peopleEvalSearchForm" action="/action/hrEvalJSON!searchPeopleEval" method="POST" theme="simple" onsubmit="return validateForm();">
	   <table width="90%" align="center"  class="OuterTable">
	    <tr><td>
	
		 <table width="100%" border="0">
		    <tr class="PBAOuterTableTitale">
		       <td height="78" colspan="7">人力资源 - 搜索绩效评估<br />
		         <br />
	           - 管理层人员通过此页面搜索对应员工的评估</td>
		    </tr>
		    <tr class="InnerTableContent">
		      <td height="4" colspan="7"><s:actionmessage/> <hr width="100%" color="#FFCC00"/></td>
		    </tr>
		    <tr class="InnerTableContent">
		      <td width="71" height="32">
		         <strong>年</strong></td>
		      <td width="122"><s:select id="year" name="searchEval.year"  list="year" headerKey="0" headerValue="-------" /></td>
		      <td width="106"><strong>&#26376;</strong></td>
		      <td width="161"><s:select id="month" name="searchEval.month"  list="month" headerKey="0" headerValue="-------" /></td>
		      <td width="64">&nbsp;</td>
		      <td width="15">&nbsp;</td>
		      <td width="344">&nbsp;</td>
		    </tr>

		    <tr class="InnerTableContent">
		      <td width="71" height="32"><strong>评估管理员</strong></td>
		      <td><s:select id="evaluater" name="searchEval.evaluater"  list="evaluaters" listKey="user_id" listValue="user_name" headerKey="0" headerValue="-------" /></td>
		      <td><strong>被评估员工</strong></td>
		      <td><s:select id="evaluatee" name="searchEval.evaluatee"  list="users" listKey="user_id" listValue="user_name" headerKey="0" headerValue="-------" /></td>
		      <td>&nbsp;</td>
		      <td>&nbsp;</td>
		      <td>&nbsp;</td>
		    </tr>
		    <tr class="InnerTableContent">
		      <td height="4" colspan="7"><hr width="100%" color="#FFCC00"/></td>
		    </tr>
		    <tr class="InnerTableContent">
		      <td height="30" colspan="7"><input type="button" value="搜索评估" onClick="searchPeopleEval();"/></td>
		    </tr>
		  </table>
	   </td></tr>
	 </table>
	</s:form>
	<br/>
	
	<table width="90%" align="center"  class="OuterTable">
	    <tr><td>
	
		 <table width="100%" border="0">
		    <tr class="PBAOuterTableTitale">
		       <th height="28" colspan="7" align="left">搜索结果</th>
		    </tr>
		    <tr class="PBAOuterTableTitale">
		      <th width="90" height="32">评估月份</th>
		      <th width="90">评估管理员</th>
		      <th width="90">被评估员工</th>
		      <th width="160">评估时间</th>
		      <th width="80">评估分数</th>
		      <th width="80">查看</th>
		      <th>&nbsp;</th>
	        </tr>
	        <tbody id="orgTablebody">
            </tbody>
		  </table>
	   </td></tr>
	 </table>
</body>
</html>