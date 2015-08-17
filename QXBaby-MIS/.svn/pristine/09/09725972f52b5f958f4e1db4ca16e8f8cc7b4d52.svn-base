<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.onlineMIS.ORM.entity.headQ.user.UserInfor" %>
<%@taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>我的任务</title>
<script>
document.onkeyup = BSkeyUp; 
function BSkeyUp(e){
	 var ieKey = event.keyCode;
	 if (ieKey==112){
		 window.location.href = "<%=request.getContextPath()%>/action/inventoryOrder!createPDAOrder";
	 } else if (ieKey == 114){
		 window.location.href = "<%=request.getContextPath()%>/action/inventoryOrder!getPDADrafts";
     } else if (ieKey == 114){
    	 window.location.href = "<%=request.getContextPath()%>/action/userJSP!pdaLogoff";
     }
} 
</script>
</head>
<body>
<jsp:include page="../../common/PDAStyle.jsp"/>
<br/>
<table style="width: 100%" align="left">
	<tr>
		<td>欢迎 : <s:property value="#session.LOGIN_USER.name"/></td>
	<tr>
	<tr>
		<td><hr/></td>
	</tr>
	<tr>
		<td><a href="<%=request.getContextPath()%>/action/inventoryOrder!createPDAOrder">开新单 F1</a></td>
	<tr>	
	</tr>
	<tr>
		<td><a href="<%=request.getContextPath()%>/action/inventoryOrder!getPDADrafts">我的草稿单据 F3</a></td>
	<tr>	
	<tr>	
	</tr>
	<tr>
		<td><a href ="<%=request.getContextPath()%>/action/userJSP!pdaLogoff" >退出 F2</a></td>
	<tr>
</table>


</body>
</html>