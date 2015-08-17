<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib prefix="s" uri="/struts-tags" %>
<%@ page import="com.onlineMIS.common.Common_util" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>千禧宝贝连锁店管理信息系统</title>
<script>
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
    <%@ include file="Header.jsp"%>
   
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
             </table></td>
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