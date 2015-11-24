<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags" %>
<script>
function upgradeVipCard(){
    var params = $("#vipUpgradeForm").serialize(); 
    //var params += "&formBean.chainUserInfor.myChainStore.chain_id =" + chainId;
    $.post("<%=request.getContextPath()%>/actionChain/chainVIPJSONAction!upgradeVIP",params, upgradeVipCardBk,"json");
}

function upgradeVipCardBk(data){
	var response = data.response;
	var returnCode = response.returnCode;
	
	if (returnCode == SUCCESS){
		flag = true;
		var dialogA = $.modalDialog.handler;
		dialogA.dialog('close');
		alert("成功升级VIP信息");
	    document.vipCardListForm.action="chainVIPJSPAction!searchVIPCards";
	    document.vipCardListForm.submit();
	} else 
		alert(response.message);
}
</script>
   <s:form id="vipUpgradeForm" action="" theme="simple" method="POST"> 
	<table>
		<tbody>
		    <tr>
			      <td height="40">VIP卡号</td>
			      <td>
			            <s:hidden name="formBean.vipCard.id"/>
			      		<s:textfield name="formBean.vipCard.vipCardNo" readonly="true"/>
			            
			      </td>
			</tr>		
		    <tr>
			      <td height="40">VIP种类</td>
			      <td>
			            <s:select name="formBean.vipCard.vipType.id" id="vipType" list="uiBean.chainVIPTypes" listKey="id" listValue="vipTypeName"/>
			      </td>
			</tr>
		    <tr>
			      <td>扣除VIP积分</td>
			      <td><s:textfield name="formBean.vipScore" id="vipScore" cssClass="easyui-numberspinner" style="width:80px;" required="required" data-options=" increment:100,min:0,max:1000"/></td>
			</tr>
	    </tbody>
	</table>
	</s:form>