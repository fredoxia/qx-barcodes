<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags" %>
<script>
function updateVipScore(){
    var params = $("#vipUpdateForm").serialize(); 
    //var params += "&formBean.chainUserInfor.myChainStore.chain_id =" + chainId;
    $.post("<%=request.getContextPath()%>/actionChain/chainVIPJSONAction!updateVipScore",params, updateVipScoreBk,"json");
}

function updateVipScoreBk(data){
	var response = data.response;
	var returnCode = response.returnCode;
	
	if (returnCode == SUCCESS){
		flag = true;
		var dialogA = $.modalDialog.handler;
		dialogA.dialog('close');
		alert("成功调整VIP积分");
		location.reload();
	} else 
		alert(response.message);
}
</script>
   <s:form id="vipUpdateForm" action="" theme="simple" method="POST"> 
	<table>
		<tbody>
		    <tr>
			      <td height="40">VIP 卡号</td>
			      <td>
			            <s:hidden name="formBean.vipCard.id"/>
			      		<s:textfield name="formBean.vipCard.vipCardNo" readonly="true"/>
			      </td>
			</tr>
		    <tr>
			      <td height="40">调整积分</td>
			      <td><s:textfield name="formBean.vipScore" id="vipScore" cssClass="easyui-numberspinner" style="width:80px;" required="required" data-options=" increment:10,min:-5000,max:5000"/></td>
			</tr>
			<tr>
			      <td height="40">备注</td>
			      <td><s:textfield name="formBean.comment" id="comment" maxlength="15"/></td>
			</tr>
	    </tbody>
	</table>
	</s:form>