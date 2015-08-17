<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.onlineMIS.ORM.entity.headQ.user.UserInfor" %>
<%@taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>PDA草稿</title>
<script>
var total = parseInt("<s:property value='uiBean.orders.size() -1'/>");
var linkReg = /link+\d/;
document.onkeyup = BSkeyUp; 
function BSkeyUp(e){
	 var ieKey = event.keyCode;
	 if (ieKey==112){
		 backMenu();
	 } else if (ieKey == 40){
		 var sourceId = event.srcElement.id;
		 if (linkReg.test(sourceId)){
	           var rowIndex = parseInt(sourceId.substring(4));
		       if (rowIndex < total)
		    	  rowIndex = rowIndex + 1;
		       else 
			      rowIndex = 0;
		       $("#link"+rowIndex).focus();
		 } else 
			 $("#link0").focus();
	 }
} 

function backMenu(){
	window.location.href='<%=request.getContextPath()%>/jsp/headQ/PDA/PDAMainMenu.jsp';
}

</script>
</head>
<body>
<jsp:include page="../../common/PDAStyle.jsp"/>
<br/>
<table style="width: 100%" align="left">
	<tr>
		<td colspan="3">欢迎 : <s:property value="#session.LOGIN_USER.name"/></td>
	<tr>
	<tr>
		<td colspan="3"><hr/></td>
	</tr>
	<tr>
		<td>客户名字</td><td>数量</td><td>金额</td>
	</tr>
	<s:iterator value="uiBean.orders" status = "st" id="order">
	   <tr>
		   <td><a id="link<s:property value="#st.index"/>" href="<%=request.getContextPath()%>/action/inventoryOrder!getPDADraft?formBean.order.order_ID=<s:property value="#order.order_ID"/>"><s:property value="#order.client_name"/></a></td>
		   <td><s:property value="#order.totalQuantity"/></td>
		   <td><s:property value="#order.totalWholePrice"/></td>
	   </tr>
	</s:iterator>
	<s:if test="uiBean.orders.size==0">
	   <tr>
		   <td colspan="3">无草稿单据</td>
	   </tr>	
	</s:if>
	<tr>
		<td colspan="3">
			<input type="button" value="主菜单 F1" onclick="backMenu();" class="inputButton"/>
		</td>
	<tr>
</table>


</body>
</html>