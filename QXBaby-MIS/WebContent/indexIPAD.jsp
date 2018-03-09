<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content ="width=device-width, initial-scale=1">
<%@ include file="/jsp/commonJQM/JQMStyle.jsp"%>
</head>
<body>
	<div id="loginPage" data-role="page">

		<header data-role="header" data-theme="b">
			<h1>千禧在线选货</h1>
		</header>

		<div data-role="content" class="content">

			<p style="">
				员工登录系统
			</p>
			<form method="post" id="loginform">
				<div data-role="fieldcontainer">
					<label for="userName">员工号 : </label> <input type="number"
						id="id" name="id" placeholder="员工PDA登录账号" />
				</div>
				<div data-role="fieldcontainer">
					<label for="password">密码 : </label> <input type="number"
						id="password" name="password" required placeholder="数字密码" />
				</div>
				<div data-role="fieldcontainer">
					<input type="button" id="submitBt" data-theme="b" onclick ="login();" value="登录"/>
				</div>
			</form>
		</div>

		<footer data-role="footer" data-theme="a">
			<h1>©2017 千禧宝贝科技</h1>
		</footer>

		<jsp:include  page="/jsp/commonJQM/Popup.jsp"/>
		
		<script>
			function login() {
				if (validateLoginForm()){
					$.mobile.loading("show",{ theme: "b", text: "正在加载数据", textonly: false});
					var params=$("#loginform").serialize();

					$.post('<%=request.getContextPath()%>/userController/login/mobile', params, 
					function(result) {
						$.mobile.loading("hide");
						if (result.success) {
							$("#password").attr("value","");
							$("#id").attr("value","");
							window.location.href = "<%=request.getContextPath()%>/orderController/StartOrder/mobile"

						} else {
							$("#password").attr("value","");
							renderPopup("登录错误",result.msg)
						}
					}, 'JSON');
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