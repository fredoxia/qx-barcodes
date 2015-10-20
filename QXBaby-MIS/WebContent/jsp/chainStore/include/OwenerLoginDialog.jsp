<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags" %>
<script>
$(document).ready(function(){
	parent.$.messager.progress('close'); 
	var ownerInput = $("#owner");
	if (ownerInput != undefined){
		$("#userName").attr("value", ownerInput.val());
	}
});
function confirmLogin(){
	var chainId = $("#chainId").val();
	if (validateLoginForm()){
	    var params = $("#authorizeForm").serialize(); 
	    params += "&formBean.chainUserInfor.myChainStore.chain_id =" + chainId;
	    $.post("<%=request.getContextPath()%>/actionChain/chainUserJSON!ownerLogin",params, confirmLoginProcess,"json");
	}
}
function confirmLoginProcess(data){
	var response = data.response;
	var returnCode = response.returnCode;
	
	if (returnCode == SUCCESS){
		flag = true;
		var dialogA = $.modalDialog.handler;
		dialogA.dialog('close');
		ownerLoginSuccess();
	} else {
		alert(response.message);
	}		
}
</script>
<s:form id="authorizeForm" name="authorizeForm" method="post" action="" theme="simple">
		  <table width="100%">
		    <tr>
		        <td colspan="2">请输入店主账号查看全部信息.如果不需要请关闭窗口.</td>
		    </tr>
		    <tr>
		      <td width="85" height="30">用户：</td>
		      <td width="180"><s:textfield name="formBean.chainUserInfor.user_name" id="userName" value=""  cssClass="easyui-validatebox" data-options="required:true"/></td>
		    </tr>
		    <tr>
		      <td height="30">密码：</td>
		      <td><s:password name="formBean.chainUserInfor.password" id="password" cssClass="easyui-validatebox" data-options="required:true"/></td>
		    </tr>
		  </table>
</s:form>
