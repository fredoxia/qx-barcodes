<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib prefix="s" uri="/struts-tags" %>

<script>
function validateVIPPassword(){
	if ($('#vipPasswordForm').form('validate')){
		var vipId = $("#vipCardId").val();
		var password = $("#password").val();
		var params = "formBean.vipCard.id=" + vipId + "&formBean.vipCard.password=" + password
	
		$.post("<%=request.getContextPath()%>/actionChain/chainVIPJSONAction!validateVIPPassword",params, postValidateVIPProcess,"json"); 
	}
}
</script>

   <s:form id="vipPasswordForm" action="" theme="simple" method="POST" cssClass="easyui-form"> 
	<table>
		    <tr>
			      <td height="40">VIP 卡号</td>
			      <td>
			            <s:hidden name="formBean.vipCard.id" id="vipCardId"/>
			      		<s:textfield name="formBean.vipCard.vipCardNo" readonly="true"/>
			      </td>
			</tr>
		    <tr>
			      <td height="40">密码</td>
			      <td><s:password name="formBean.vipCard.password" id="password" cssClass="easyui-validatebox"  style="width:80px;" data-options="required:true" /></td>
			</tr>

	</table>
	</s:form>
