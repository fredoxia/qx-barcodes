<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib prefix="s" uri="/struts-tags" %>
<%@ page import="com.onlineMIS.ORM.entity.chainS.user.*" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@ include file="../../common/Style.jsp"%>
<title>修改连锁店用户资料</title>
<script>

function saveUpdate(){
	var error ="";
	if ($("#name").val() == ""){
		error +="姓名 - 不能为空\n";
	} 	
	if ($("#user_name").val() == ""){
		error +="系统用户名 - 不能为空\n";
	} 	
	if ($("#password").val() == ""){
		error +="系统密码 - 不能为空\n";
	} 		
	if ($("#mobilePhone").val() == ""){
		error +="移动电话 - 不能为空\n";
	} 	
	if ($("#chainStore").val() == 0){
		error +="所属连锁店 - 不能为空\n";
	} 		
	
	if (error != "")
		alert(error);
	else {
		document.updateChainUserForm.action = "chainUserJSP!saveUpdateUser";
		document.updateChainUserForm.submit();
	}
}

function checkUserName(){
	
    var params=$("#updateChainUserForm").serialize();  

    $.post("chainUserJSON!checkChainUsername",params, checkUserNameBackProcess,"json");	
}

function checkUserNameBackProcess(data){
	var result = data.result;
	var userName = $("#user_name").val();
	if (result == false){
		$("#userNameDiv").html(userName + " 已经在使用");
		$("#saveButton").attr('disabled',true);
	    return false;
	}else{
		$("#userNameDiv").html(userName + " 可以使用");
		$("#saveButton").attr('disabled',false);
		return true;
	}
}
</script>
</head>
<body>
    <table width="90%" align="center"  class="OuterTable">
    <tr><td>
        <s:form id="updateChainUserForm" name="updateChainUserForm" method="post" action="chainUserJSP!saveUpdateUser" theme="simple" >
	    <table width="100%" border="0">
	       <tr class="PBAOuterTableTitale">
	          <td colspan="2">修改员工信息</td>
	       </tr>
	       <tr>
	          <td colspan="2"><s:actionerror cssStyle="color:red"/><s:actionmessage/>
	          </td>
	       </tr>
	       <tr class="InnerTableContent" style="background-color: rgb(255, 250, 208);">
	          <td height="18"><strong>姓名</strong>  : </td>
	          <td><input type="hidden" name="formBean.chainUserInfor.user_id" id="user_id" value="<s:property value="uiBean.chainUserInfor.user_id"/>"/>
	              <input type="text" name="formBean.chainUserInfor.name" id="name" value="<s:property value="uiBean.chainUserInfor.name"/>"/></td>
	       </tr>
	       <tr class="InnerTableContent">
	          <td height="18"><strong>系统用户名 </strong>        :</td>
	          <td><input type="text" name="formBean.chainUserInfor.user_name" id="user_name" value="<s:property value="uiBean.chainUserInfor.user_name"/>" onchange="checkUserName();"/><div id="userNameDiv"></div></td>
	       </tr>
	       <tr class="InnerTableContent" style="background-color: rgb(255, 250, 208);">
	          <td height="18"><strong>系统密码 </strong>        :</td>
	          <td><input type="text" name="formBean.chainUserInfor.password" id="password" value="<s:property value="uiBean.chainUserInfor.password"/>"  maxlength="6"/></td>
	       </tr>
	       <tr class="InnerTableContent">
	          <td height="18"><strong>手机号</strong>   :</td>
	          <td><input type="text" name="formBean.chainUserInfor.mobilePhone" id="mobilePhone" value="<s:property value="uiBean.chainUserInfor.mobilePhone"/>" maxlength="11"/></td>
	       </tr>
	       <tr class="InnerTableContent" style="background-color: rgb(255, 250, 208);">
	          <td height="18"><strong>所属连锁店 </strong>       :</td>
	          <td><s:select id="chainStore" name="formBean.chainUserInfor.myChainStore.chain_id"  list="uiBean.chainStores" listKey="chain_id" listValue="chain_name"  headerKey="0" headerValue="-------" /></td>
	       </tr>
	       <tr class="InnerTableContent">
	          <td height="18"><strong>职位</strong>         :</td>
	          <td><s:select name="formBean.chainUserInfor.roleType.chainRoleTypeId" id="roleType" list="uiBean.chainRoleTypes" listKey="chainRoleTypeId" listValue="chainRoleTypeName"/></td>
	       </tr>
	       <tr class="InnerTableContent" style="background-color: rgb(255, 250, 208);">
	          <td height="18"><strong>离职</strong>       :</td>
	          <td><input type="checkbox" name="formBean.chainUserInfor.resign" value="1" <s:if test="uiBean.chainUserInfor.resign == 1">checked=true</s:if>/></td>
	       </tr>
	       <tr class="InnerTableContent">
	          <td height="18"></td>
	          <td><s:if test="#session.LOGIN_CHAIN_USER.containFunction('chainUserJSP!saveUpdateUser')"><input id="saveButton" type="button" value="保存/修改" onclick="saveUpdate();"/></s:if></td>
	       </tr>
	    </table>
	    </s:form>
	    </td>
	</tr>
	</table>   
</body>
</html>