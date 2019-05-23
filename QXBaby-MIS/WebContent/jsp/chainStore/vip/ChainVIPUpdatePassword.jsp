<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib prefix="s" uri="/struts-tags" %>
<script>
function validateVIPPassword(){
		 if ($('#vipPasswordUpdateForm').form('validate')){
			 var password1= $("#password1").val();
			 var password2= $("#password2").val();
			 if (password1.length >6 || password2.length >6){
				 $.messager.alert('失败警告', "密码不能超过六位字符", 'error');
				 return ;
			 } else if (password1 != password2){
				 $.messager.alert('失败警告', "两次输入的密码不一致", 'error');
				 return ;
			 }
			 
			    var params = $("#vipPasswordUpdateForm").serialize(); 
			    //var params += "&formBean.chainUserInfor.myChainStore.chain_id =" + chainId;
			    $.post("<%=request.getContextPath()%>/actionChain/chainVIPJSONAction!updateVIPPassword",params, postValidateVIPProcess,"json");
		 }
}
</script>
   <s:form id="vipPasswordUpdateForm" action="" theme="simple" method="POST" cssClass="easyui-form"> 
	<div class="errorAndmes"><s:actionmessage cssStyle="color:blue"/></div>
	<table>
		    <tr>
			      <td height="40">VIP 卡号</td>
			      <td>
			            <s:hidden name="formBean.vipCard.id"/>
			      		<s:textfield name="formBean.vipCard.vipCardNo" readonly="true"/>
			      </td>
			</tr>
		    <tr>
			      <td height="40">密码</td>
			      <td><s:password name="formBean.vipCard.password" id="password1" cssClass="easyui-validatebox"  style="width:80px;" data-options="required:true" /></td>
			</tr>
			<tr>
			      <td height="40">再次密码</td>
			      <td><s:password name="formBean.vipCard.name" id="password2" cssClass="easyui-validatebox" style="width:80px;" data-options="required:true"/></td>
			</tr>

	</table>
	</s:form>
