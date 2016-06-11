<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib prefix="s" uri="/struts-tags" %>

<%@ page import="java.util.Date,java.text.SimpleDateFormat" %>
<html>
<head>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>连锁店信息管理</title>
<%@ include file="../../common/Style.jsp"%>
<script>
$(document).ready(function(){
	parent.$.messager.progress('close'); 
});
/**
 * to check the duplication of the user name
 */
function checkUserName(){
	var userName = $("#user_name").val();
	var userId = $("#chainUser").val();
	if (userName != ""){
		var params="formBean.chainUserInfor.user_name=" + userName +"&formBean.chainUserInfor.user_id=" + userId;
		$.post("<%=request.getContextPath()%>/actionChain/chainMgmtJSON!checkUserName",params, backProcessCheckUserName,"json");
	}
}
function backProcessCheckUserName(data){
	var error =  data.error;

	if (error != undefined && error == true){
		var userName = data.userName;
		alert("用户名: " + userName + " 已经使用.请另外使用");
	}
}

function changeChainStore(chainId){
	var chainStoreId = chainId;
	if (chainStoreId == 0){
		clearChainUserDrop();
		clearChainUser();
	} else {
		var params="formBean.chainStore.chain_id=" + chainStoreId;
		$.post("<%=request.getContextPath()%>/actionChain/chainMgmtJSON!getChainStoreUsers",params, backProcessGetChainStore,"json");
	}
}
function backProcessGetChainStore(data){
	var chainUsers = data.chainUsers;

	if ($("#chainUser").val() != 0 )
	    clearChainUser();

	clearChainUserDrop();
	
	if (chainUsers != null && chainUsers.length != 0){
		 for (var i = 0; i < chainUsers.length; i++)
		   $("#chainUser").append("<option value='"+chainUsers[i].user_id+"'>"+chainUsers[i].name+"</option>"); 
	}
}


function clearChainUserDrop(){
	$("#chainUser").empty();
	$("#chainUser").prepend("<option value='0'>----- 新增 -----</option>");
}

function clearChainUser(){
	$("#name").attr("value", "");
	$("#mobilePhone").attr("value", "");
	$("#user_name").attr("value", "");
	$("#passwordU").attr("value", "");
	$("#roleType").attr("checked",false);
	$("#resign").attr("checked",false);
}

function editChainUser(){
	var params=$("#EditChainInforForm").serialize(); 
	if (validateChainUser()) 
	    $.post("<%=request.getContextPath()%>/actionChain/chainMgmtJSON!saveChainUser",params, backProcessEditChainUser,"json");
}
function backProcessEditChainUser(data){
	var error =  data.error;

	if (error != undefined && error == true){
		var userName = data.userName;
		alert("用户名: " + userName + " 已经使用.请另外使用");
	} else {
	 	var chainUser =  data.chainUserInfor;
	
		if (chainUser != undefined){
			if ($("#chainUser").val() != 0)
			    $("#chainUser").find("option:selected").text(chainUser.name);
			else {
				$("#chainUser").append("<option value='"+chainUser.user_id+"'>"+chainUser.name+"</option>"); 
				$("#chainUser").attr("value", chainUser.user_id);
			}
			
			alert("成功更新");
		}
	}

}

function getChainUser(){
	var chainUserId = $("#chainUser").val();
	if (chainUserId == 0){
		clearChainUser();
	} else {
		var params="formBean.chainUserInfor.user_id=" + chainUserId;
		$.post("<%=request.getContextPath()%>/actionChain/chainMgmtJSON!getChainUser",params, backProcessGetChainUser,"json");
	}
}
function backProcessGetChainUser(data){
	var chainUser = data.chainUser;

	if (chainUser != undefined){
		$("#name").attr("value", chainUser.name);
		$("#mobilePhone").attr("value", chainUser.mobilePhone);
		$("#user_name").attr("value",chainUser.user_name);
		$("#passwordU").attr("value",chainUser.password);
		
		var roleType = chainUser.roleType.chainRoleTypeId;
		$("#roleType").val(roleType).attr("selected", true);

		if (chainUser.resign == 1)
		    $("#resign").attr("checked",true);
		else
			$("#resign").attr("checked",false);
	}
}

function checkRoleType(){
	var roleType = $("#roleType").val();
	var chainStoreId = $("#chainStoreId").val();
	if (chainStoreId != 0 ){
		var params = "formBean.chainStore.chain_id=" +  chainStoreId + "&formBean.chainUserInfor.roleType.chainRoleTypeId=" +roleType ;
		$.post("<%=request.getContextPath()%>/actionChain/chainMgmtJSON!getChainStoreAdmin",params, backProcessCheckRoleType,"json");
	}
}
function backProcessCheckRoleType(data){
	var users = data.chainUsers;

	if (users != undefined && users.length != 0){
		var users_s = "";
		for (var i = 0; i < users.length; i++)
			users_s += users[i].name + ",";
		alert("此连锁店已经有以下相同权限人员 : " + users_s);
	}
}

function validateChainUser(){
	var error = "";
	var chainId = $("#chainId").val();
	var clientId = $("#clientId").val();
	var name = $("#name").val();
	var mobilePhone = $("#mobilePhone").val();
	var user_name = $("#user_name").val();
	var password = $("#passwordU").val();
	
	if (chainId == 0)
		error += "请选择用户所属的连锁店.\n";
	
	if (user_name == "" || user_name.length >10)
		error += "用户名 - 为长度小于10的非空字符.\n";

	if (name == "" || name.length >10)
		error += "名字 - 为长度小于10的非空字符.\n";

	if (clientId == "" || clientId == 0)
		error += "精算账号 - 不能为空.\n";
	
    if (mobilePhone == ""){
		error +="手机 - 不能为空.\n";
    } else if (isNaN(mobilePhone)){
		error +="手机 - 包含了非数字信息.\n";
    } else if (mobilePhone.length > 11){
    	error +="手机 - 超过最长11位.\n";
    }	

	if (password == "" || password.length >8)
		error += "密码 - 为长度小于8的非空字符.\n";

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
<s:form id="EditChainInforForm" action="/actionChain/chainMgmtJSP!save" method="POST" theme="simple" target="_blank" onsubmit="" >
  <table width="90%" align="center"  class="OuterTable">
    <tr><td>
		 <table width="100%" border="0">
	    <tr class="PBAOuterTableTitale">
	       <td height="78" colspan="8">连锁店人员信息管理<br />
	         <br />
           - 通过这个界面管理员可以添加/修改/删除,连锁店信息以及连锁店员工信息<br/>           </td>
	    </tr>
	    <tr class="InnerTableContent">
	      <td width="100" height="25">
	         <strong>连锁店</strong></td>
	      <td colspan="3"><%@ include file="../include/SearchChainStore.jsp"%></td>
	      <td width="68">&nbsp;</td>
	      <td width="314">&nbsp;</td>
	      <td width="182">&nbsp;</td>
	      <td width="137">&nbsp;</td>
	    </tr>
   	    <tr class="InnerTableContent">
	      <td height="25"><strong>连锁店员工</strong></td>
	      <td><s:select id="chainUser" name="formBean.chainUserInfor.user_id"  list="uiBean.chainUserInfors" headerKey="0" headerValue="----- 新增 -----" listKey="user_id" listValue="name" onchange="getChainUser();" /></td>
	      <td></td>
	      <td></td>
	      <td>&nbsp;</td>
	      <td>&nbsp;</td>
	      <td>&nbsp;</td>
	      <td>&nbsp;</td>
   	    </tr>
   	    <tr class="InnerTableContent">
   	      <td height="25"><strong>姓名</strong></td>
   	      <td width="165"><s:textfield id="name" name="formBean.chainUserInfor.name" size="20"/></td>
   	      <td width="70"><strong>手机</strong></td>
   	      <td width="150"><s:textfield id="mobilePhone" name="formBean.chainUserInfor.mobilePhone" size="20"/></td>
   	      <td><strong>用户名</strong></td>
   	      <td><s:textfield id="user_name" name="formBean.chainUserInfor.user_name" size="25" onchange="checkUserName();"/></td>
          <td>&nbsp;</td>
          <td>&nbsp;</td>
       </tr>
   	    <tr class="InnerTableContent">
   	      <td><strong>密码</strong></td>
   	      <td><s:textfield id="passwordU" name="formBean.chainUserInfor.password" size="20"/></td>
   	      <td><strong>店铺权限</strong></td>
   	      <td><s:select name="formBean.chainUserInfor.roleType.chainRoleTypeId" id="roleType" list="uiBean.chainRoleTypes" listKey="chainRoleTypeId" listValue="chainRoleTypeName" onchange="checkRoleType();"/></td>
   	      <td height="25"><strong>离职</strong></td>
   	      <td><input type="checkbox" name="formBean.chainUserInfor.resign" id="resign" value="1" /></td>
   	      <td>&nbsp;</td>
          <td>&nbsp;</td>
       </tr>
       <tr class="InnerTableContent">
   	      <td height="25"></td>
   	      <td></td>
   	      <td></td>
   	      <td></td>
   	      <td></td>
   	      <td></td>
          <td>&nbsp;</td>
          <td>&nbsp;</td>
       </tr>

   	    <tr class="InnerTableContent">
   	      <td height="25">&nbsp;</td>
   	      <td><s:if test="#session.LOGIN_CHAIN_USER.containFunction('chainMgmtJSON!saveChainUser')"><input type="button" onclick="editChainUser();" value="添加/更新员工"/></s:if></td>
   	      <td></td>
   	      <td></td>
   	      <td>&nbsp;</td>
   	      <td>&nbsp;</td>
          <td>&nbsp;</td>
          <td>&nbsp;</td>
       </tr>
	  </table>
   </td>
   </tr>

 </table>
</s:form>

</body>
</html>