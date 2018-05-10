<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content ="width=device-width, initial-scale=1">
<%@ include file="jsp/headQ/ipad/JQMStyle.jsp"%>
</head>
<body>
	<div id="loginPage" data-role="page">

		<header data-role="header" data-theme="b">
			<h1>PDA在线选货</h1>
		</header>

		<div data-role="content" class="content">

			<p style="">
				员工登录系统
			</p>
			<s:form id="loginForm" name="loginForm" method="post" theme="simple">
				<div data-role="fieldcontainer">
					<label for="userName">员工ID号 : </label> <input type="number" name="user.user_id"
						id="id"  placeholder="你的员ID登录账号" />
				</div>
				<div data-role="fieldcontainer">
					<label for="password">密码 : </label> <input name="user.password"
						id="password" placeholder="系统数字密码" />
				</div>
				<div data-role="fieldcontainer">
					<input type="button" id="submitBt" data-theme="b" onclick ="login();" value="登录"/>
				</div>
			</s:form>
		</div>

		<footer data-role="footer" data-theme="a">
			<h1>所有选货自动保存草稿 </h1>
		</footer>

		<jsp:include  page="jsp/headQ/ipad/Popup.jsp"/>
		
		<script>
			function login(){
	
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
					renderPopup("验证错误  " , response.message);
				else{
					window.location.href = '<%=request.getContextPath()%>/action/ipadJSP!goToEditCustPDA';  
				}
			}
			function validateLoginForm(){
				var userName = $("#id").val();
				var password = $("#password").val();
				if (userName == "" || password ==""){
					renderPopup("验证错误","登录名和密码不能为空");
					return false;
				}
				return true;
			}
		</script>
	</div>

</body>
</html>