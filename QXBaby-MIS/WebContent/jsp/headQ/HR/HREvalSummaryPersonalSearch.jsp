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
function validateForm(){
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

function searchPeopleEval(){
	if (validateForm()){
	    var params=$("#peopleEvalSearchForm").serialize();  
	    $.post("hrEvalJSON!searchOwnEval",params, searchPeopleEvalBackProcess,"json");	
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
		          $("<tr align='center' class='InnerTableContent'" + bg +"><td>"+evals[i].evaluationYear +"-" + evals[i].evaluationMonth +"</td><td>"+evals[i].evaluatee.user_name+"</td><td><a href='#' onclick=\"window.open ('hrEvalJSP!ViewPeopleEval?peopleEvaluation.id="+ evals[i].id +"','新窗口','height=700, width=700, toolbar=no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=no');\"><img src='<%=request.getContextPath()%>/conf_files/web-image/editor.gif' border='0'/></a><td></td></td></tr>").appendTo("#orgTablebody");
	    }
    }else {
    	$("<tr class='InnerTableContent' style='background-color: rgb(255, 250, 208);' align='center'><td colspan=4><font color='red'>对应条件没有查询信息</font> </td></tr>").appendTo("#orgTablebody");
    }
}
</script>
</head>
<body>

	<s:form id="peopleEvalSearchForm" action="/action/hrEvalJSON!searchOwnEval" method="POST" theme="simple" onsubmit="return validateForm();">
	   <table width="90%" align="center"  class="OuterTable">
	    <tr><td>
	
		 <table width="100%" border="0">
		    <tr class="PBAOuterTableTitale">
		       <td height="78" colspan="7">人力资源 - 搜索绩效评估<br />
		         <br />
	           - 员工通过此页面搜索自己的评估报告</td>
		    </tr>
		    <tr class="InnerTableContent">
		      <td height="4" colspan="7"><s:actionmessage/> <hr width="100%" color="#FFCC00"/></td>
		    </tr>
		    <tr class="InnerTableContent">
		      <td width="71" height="32">
		         <strong>年</strong></td>
		      <td width="122"><s:select id="year" name="searchEval.year"  list="year" headerKey="0" headerValue="-------" /></td>
		      <td width="106"><strong>&#26376;</strong></td>
		      <td width="161"><s:select id="month" name="searchEval.month"  list="month" headerKey="-1" headerValue="--所有月份--" /></td>
		      <td width="64">&nbsp;</td>
		      <td width="15">&nbsp;</td>
		      <td width="344">&nbsp;</td>
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
		       <th height="28" colspan="4" align="left">搜索结果</th>
		    </tr>
		    <tr class="PBAOuterTableTitale">
		      <th width="90" height="32">评估月份</th>
		      <th width="90">被评估员工</th>
		      <th width="85">查看</th>
		      <th width="400">&nbsp;</th>
	        </tr>
	        <tbody id="orgTablebody">
            </tbody>
		  </table>
	   </td></tr>
	 </table>
</body>
</html>