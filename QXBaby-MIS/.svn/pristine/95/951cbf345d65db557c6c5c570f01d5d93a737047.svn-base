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
$(document).ready(function(){
	parent.$.messager.progress('close'); 
});
function saveUpdate(){
	var error ="";
	if ($("#name").val() == ""){
		error +="姓名 - 不能为空\n";
	} 	
	if ($("#user_name").val() == ""){
		error +="系统用户名 - 不能为空\n";
	} 	
	if ($("#mobilePhone").val() == ""){
		error +="移动电话 - 不能为空\n";
	} 	
	var password1 = $("#password1").val();
	var password2 = $("#password2").val();
	var password = $("#password").val();
	
	if ((password != "" || password1 != "" || password2 != "")){
		if  (password1 != password2 || password1 =="" || password2 == "") 
		    error +="系统密码 - 请输入两个一致的非空新密码\n";
		else if (password == "")
			error +="系统密码 - 请输入原始密码\n";
	} 	
	
	if (error != "")
		alert(error);
	else {
		document.updateChainUserForm.action = "chainUserJSP!editMyAcct";
		document.updateChainUserForm.submit();
	}
}

</script>
</head>
<body>
    <table width="60%" align="center"  class="OuterTable">
    <tr><td>
        <s:form id="updateChainUserForm" name="updateChainUserForm" method="post" action="chainUserJSP!saveUpdateUser" theme="simple" >
	    <table width="100%" border="0">
	       <tr class="PBAOuterTableTitale">
	          <td colspan="2">修改员工信息</td>
	       </tr>
	       <tr class="InnerTableContent">
	          <td colspan="2"><div class="errorAndmes"><s:actionerror cssStyle="color:red"/><s:actionmessage cssStyle="color:blue"/></div><hr width="100%" color="#FFCC00"/>
	          </td>
	       </tr>
	       <tr class="InnerTableContent">
	          <td height="18" align="right"><strong>系统用户名 </strong>        :</td>
	          <td><s:property value="formBean.chainUserInfor.user_name"/></td>
	       </tr>
	       <tr class="InnerTableContent">
	          <td height="18" align="right"><strong>所属连锁店 </strong>       :</td>
	          <td><s:property value="formBean.chainUserInfor.myChainStore.chain_name"/></td>
	       </tr>
	       <tr class="InnerTableContent">
	          <td height="18" align="right"><strong>职位</strong>         :</td>
	          <td><s:property value="formBean.chainUserInfor.roleType.chainRoleTypeName"/></td>
	       </tr>
	       <tr class="InnerTableContent">
	          <td height="18" align="right"><strong>姓名</strong>  : </td>
	          <td><s:textfield name="formBean.chainUserInfor.name" id="name"/></td>
	       </tr>

	       <tr class="InnerTableContent">
	          <td height="18" align="right"><strong>手机号</strong>   :</td>
	          <td><s:textfield name="formBean.chainUserInfor.mobilePhone" id="mobilePhone" maxlength="11"/></td>
	       </tr>

	       <tr class="InnerTableContent">
	          <td height="18" align="right"><strong>修改密码 </strong>        </td>
	          <td></td>
	       </tr>
	       <tr class="InnerTableContent">
	          <td height="18" align="right">原始密码</td>
	          <td><s:password name="formBean.password" id="password"/></td>
	       </tr>
	       <tr class="InnerTableContent">
	          <td height="18" align="right">新密码</td>
	          <td><input type="password" name="formBean.chainUserInfor.password" id="password1"/></td>
	       </tr>
	       <tr class="InnerTableContent">
	          <td height="18" align="right">新密码</td>
	          <td><input type="password" id="password2"/></td>
	       </tr>
	       <tr class="InnerTableContent">
	          <td height="18"></td>
	          <td><input id="saveButton" type="button" value="保存/修改" onclick="saveUpdate();"/></td>
	       </tr>
	    </table>
	    </s:form>
	    </td>
	</tr>
	</table>   
</body>
</html>