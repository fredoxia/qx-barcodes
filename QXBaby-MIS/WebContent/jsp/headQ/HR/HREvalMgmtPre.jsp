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
	 var user = $("#userid").val();
	 if (year == 0){
		 alert("请选择<年份>");
		 return false;
	 }else if (month == 0){
		 alert("请选择<月份>");
		 return false;
	 }else if (user ==0){
		 alert("请选择<用户>");
		 return false;
	 }

	 return true;
}
$(document).ready(function(){
	parent.$.messager.progress('close'); 
});

</script>
</head>
<body>

   <s:form id="hrEvalForm" action="/action/hrEvalJSP!preCreatePeopleEval" method="POST" theme="simple" onsubmit="return validateForm();" >
   <table width="90%" align="center"  class="OuterTable">
    <tr><td>

	 <table width="100%" border="0">
	    <tr class="PBAOuterTableTitale">
	       <td height="78" colspan="7">人力资源 - 新建绩效评估<br />
	         <br />
           - 管理人员通过此页面创建对应员工的当月评估</td>
	    </tr>
	    <tr class="InnerTableContent">
	      <td width="34" height="32">
	         <strong>年</strong></td>
	      <td width="121"><s:select id="year" name="peopleEvaluation.evaluationYear"  list="year" headerKey="0" headerValue="-------" /></td>
	      <td width="23"><strong>&#26376;</strong></td>
	      <td width="140"><s:select id="month" name="peopleEvaluation.evaluationMonth"  list="month" headerKey="0" headerValue="-------" /></td>
	      <td width="24">&nbsp;</td>
	      <td width="137">&nbsp;</td>
	      <td width="591">&nbsp;</td>
	    </tr>
	    <tr class="InnerTableContent">
	      <td height="4" colspan="7"><hr width="100%" color="#FFCC00"/></td>
	    </tr>
	    <tr class="InnerTableContent">
	      <td width="34" height="32"><strong>员工</strong></td>
	      <td><s:select id="userid" name="peopleEvaluation.evaluatee.user_id"  list="users" listKey="user_id" listValue="user_name" headerKey="0" headerValue="-------" /></td>
	      <td colspan="5"><s:actionerror/></td>
	    </tr>
	    <tr class="InnerTableContent">
	      <td height="4" colspan="7"><hr width="100%" color="#FFCC00"/></td>
	    </tr>
	    <tr class="InnerTableContent">
	      <td height="30" colspan="7"><input type="submit" value="创建"/></td>
	    </tr>
	  </table>
   </td></tr>
 </table>
</s:form>
</body>
</html>