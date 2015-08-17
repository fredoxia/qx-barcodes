<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script>
function confirmDelete(){
	if (validateLoginForm()){
	    var params=$("#authorizeDeleteForm").serialize(); 
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
<div id="confirmDeleteDialog" class="easyui-dialog" title="授权删除单据" data-options="iconCls:'icon-status_online',resizable:false,modal:true,draggable:false,closable:true,closed:true,inline:true,buttons:[{text:'授权删除',handler:function(){ confirmDelete(); }}]" style="width:330px;height:180px;padding:10px">
	  <s:form id="authorizeDeleteForm" name="authorizeDeleteForm" method="post" action="" theme="simple">
	      <s:hidden name="formBean.order.order_ID"  id="orderId"/>
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

