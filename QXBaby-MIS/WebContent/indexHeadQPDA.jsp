<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>千禧宝贝管理信息系统  abc </title>
<script type="text/javascript" src="<%=request.getContextPath()%>/conf_files/js/qxbaby-common.js"></script>
<script>
function pdaLogin(){

	if (validateLoginForm()){
	    var params=$("#loginForm").serialize(); 
	    var url = "<%=request.getContextPath()%>/action/loginJSON!PDALogin";
	    $.post(url,params, loginBackProcess,"json");
	}
}
function loginBackProcess(data){
	var response = data.response;
	var returnCode = response.returnCode;
	if (returnCode != SUCCESS)
		alert("错误 : " + response.message);
	else{
		window.location.href = '<%=request.getContextPath()%>/action/inventoryOrder!createPDAOrder';  
	}
}
document.onkeyup = BSkeyUp; 
function BSkeyUp(e){
	 var ieKey = event.keyCode;
	 //alert(ieKey);
	 if (ieKey==113){
		 pdaLogin();
		 event.returnValue=false;
	 } else if (ieKey == 40){
	   if (event.srcElement.id=="userName"){
		   $("#password").select();
	   } else
		   $("#userName").select();

	   event.returnValue=false;
     }
} 
</script>
</head>
<body>
       <jsp:include page="jsp/common/PDAStyle.jsp"/>
		<s:form id="loginForm" name="loginForm" method="post" action="/action/login!PDALogin" theme="simple">
		  <table width="165">
		    <tr>
		      <td colspan="2" align="center"><strong>总部PDA用户登录</strong></td>
		    </tr>
		    <tr>
		      <td colspan="2" align="left"><div class="errorAndmes"><s:fielderror cssStyle="color:red"/><s:actionerror cssStyle="color:red"/></div></td>
		    </tr>
		    <tr>
		      <td width="50" height="30">用户ID</td>
		      <td>
		      <s:textfield name="user.user_id" id="userName" cssClass="input"/></td>
		    </tr>
		    <tr>
		      <td height="30">密码</td>
		      <td>
		      <s:password name="user.password" id="password" cssClass="input"/></td>
		    </tr>
		    <tr>
		      <td colspan="2" align="center"><input type="button" value="登录 (F2)" onclick="pdaLogin();" class="inputButton"/></td>
		    </tr>
		  </table>
		</s:form>
</body>
</html>