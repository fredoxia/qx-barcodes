<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags" %>
<script>
$(document).ready(function(){
	parent.$.messager.progress('close'); 
});
function confirmDelete(){
	var orderId = $("#orderId").val();
	if (validateLoginForm()){
	    var params = $("#authorizeDeleteForm").serialize(); 
	    params += "&formBean.order.order_ID =" + orderId;
	    $.post("<%=request.getContextPath()%>/action/inventoryOrderJSON!deleteOrder",params, confirmDeleteProcess,"json");
	}
}
function confirmDeleteProcess(data){
	$("#password").attr("value","");
	var response = data.response;
	var returnCode = response.returnCode;
	if (returnCode != SUCCESS)
		alert(response.message);
	else 
		window.location.href = "<%=request.getContextPath()%>/jsp/headQ/common/updateSuccess.jsp?location=/action/inventoryOrder!createReturnOrder";
	
}
</script>
<s:form id="authorizeDeleteForm" name="authorizeDeleteForm" method="post" action="" theme="simple">
		  <table width="100%">
		    <tr>
		      <td width="85" height="30">授权用户：</td>
		      <td width="180"><s:textfield name="formBean.user.user_name" id="userName" value="周洁"  cssClass="easyui-validatebox" data-options="required:true"/></td>
		    </tr>
		    <tr>
		      <td height="30">密码：</td>
		      <td><s:password name="formBean.user.password" id="password" cssClass="easyui-validatebox" data-options="required:true"/></td>
		    </tr>
		  </table>
</s:form>
