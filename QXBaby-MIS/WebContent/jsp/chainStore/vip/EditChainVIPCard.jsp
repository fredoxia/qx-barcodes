<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib prefix="s" uri="/struts-tags" %>
<%@ page import="com.onlineMIS.ORM.entity.headQ.barcodeGentor.*" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@ include file="../../common/Style.jsp"%>

<title>管理VIP卡</title>
<script>
$(document).ready(function(){

	});
	
var refreshURL = "chainVIPJSPAction";
function validateUpdate(){
	var error ="";
 	var vipCardNo = $.trim($("#vipCardNo").val());
 	var vipCardType = $.trim($("#vipCardType").val());
 	var initialScore = $.trim($("#initialScore").val());
 	var vipCardStatus = $.trim($("#vipCardStatus").val());
 	var issueChainStore = $.trim($("#issueChainStore").val());
 	var issueDate = $.trim($("#issueDate").val());
 	var expireDate = $.trim($("#expireDate").val());
 	var birthday = $.trim($("#birthday").combo("getValue"));
 	var customerName = $.trim($("#customerName").val());
 	var telephone = $.trim($("#telephone").val());

 	if (vipCardNo == "")
 	 	error += "VIP卡号 - 不能为空\n";
 	if (vipCardType == 0)
 	 	error += "VIP卡种类 - 不能为空\n";
 	if (vipCardStatus == 0)
 	 	error += "VIP卡状态 - 不能为空\n";
 	if (issueChainStore == 0)
 	 	error += "VIP卡发行连锁店 - 不能为空\n";
 	if (issueDate == "")
 	 	error += "VIP卡发行日期 - 不能为空\n";
 	if (expireDate == "")
 	 	error += "VIP卡有效日期 - 不能为空\n";
 	if (birthday == "")
 	 	error += "VIP卡持有人生日 - 不能为空\n";
 	else if (!isValidDate(birthday))
		error += "VIP卡持有人生日 - 格式必须为YYYY-MM-DD\n";
	
 	if (customerName == "")
 	 	error += "VIP卡客户名字 - 不能为空\n";
 	if (telephone == "")
 	 	error += "VIP卡客户电话 - 不能为空\n";
	
	if (error != ""){
		alert(error);
		return false;
	} else {
		return true
	}
}

function updateContinue(){
	if (validateUpdate() == true){
		var vipCardNo = $.trim($("#vipCardNo").val());
		var confirmMsg = "你确认VIP卡号是: " + vipCardNo;
		if (confirm(confirmMsg)){
	       var params=$("#editVIPCardForm").serialize();  
	       $.post("chainVIPJSONAction!saveUpdateVIPCard",params, editContinueBKProcess,"json");
		}
	}
}
function update(){
	if (validateUpdate() == true){
		var vipCardNo = $.trim($("#vipCardNo").val());
		var confirmMsg = "你确认VIP卡号是: " + vipCardNo;
		if (confirm(confirmMsg)){
	       var params=$("#editVIPCardForm").serialize();  
	       $.post("chainVIPJSONAction!saveUpdateVIPCard",params, editBKProcess,"json");
		}
	}
}
function editContinueBKProcess(data){
	var isSuccess = data.isSuccess;
	if (isSuccess == 1){
        alert("成功更新");
        
        window.location.href = "chainVIPJSPAction!preAddVIPCard";
        var url  = window.opener.location.toString();
        if (url.indexOf(refreshURL) >=0) {
        	window.opener.vipCardListForm.action="chainVIPJSPAction!searchVIPCards";
            window.opener.vipCardListForm.submit();
        }
	} else{
		var error = data.error;
		$("#errorDiv").html(error);
	}
}
function editBKProcess(data){
	var isSuccess = data.isSuccess;
	if (isSuccess == 1){
        alert("成功更新");
        window.close();
        var url  = window.opener.location.toString();
        if (url.indexOf(refreshURL) >=0) {
        	window.opener.vipCardListForm.action="chainVIPJSPAction!searchVIPCards";
            window.opener.vipCardListForm.submit();
        }
        	//window.opener.location = "chainVIPJSPAction!viewAllVIPCards";
	} else{
		var error = data.error;
		$("#errorDiv").html(error);
	}
}
function useTelAsVIPNum(){
	var telephone = $.trim($("#telephone").val());
	if (telephone != ""){
		$("#vipCardNo").attr("value", telephone);
	} else 
		alert("电话号码为空");
}
</script>
</head>
<body>
    <table width="90%" align="left"  class="OuterTable">
    <tr><td>
        <s:form id="editVIPCardForm" name="editVIPCardForm" method="post" action="" theme="simple">
	    <table width="100%" border="0">
	       <tr class="PBAOuterTableTitale">
	          <td colspan="2">VIP卡信息</td>
	       </tr>
	       <tr>
	          <td colspan="2" class="InnerTableContent"></td>
	       </tr>
	       <tr>
	          <td colspan="2" class="InnerTableContent"><div id="messageDiv"></div><div id="errorDiv" style="color: red"></div></td>
	       </tr>
	       <tr class="InnerTableContent">
	          <td width="70"><strong>卡号 </strong>  : </td>
	          <td>
	          <s:hidden name="formBean.vipCard.id" id="cardId"/>
	          <s:textfield name="formBean.vipCard.vipCardNo" id="vipCardNo" maxlength="20" cssClass="easyui-validatebox" data-options="required:true"/></td>
	       </tr>
	       <tr class="InnerTableContent">
	          <td><strong>类型 </strong>:</td>
	          <td><s:select id="vipCardType" name="formBean.vipCard.vipType.id"  list="uiBean.chainVIPTypes" listKey="id" listValue="vipTypeName"/></td>
	       </tr>
	       <tr class="InnerTableContent">
	          <td><strong>状态</strong>:</td>
	          <td><s:select id="vipCardStatus" name="formBean.vipCard.status"  list="uiBean.cardStatus"  listKey="key" listValue="value"/></td>
	       </tr>
	       <tr class="InnerTableContent">
	          <td><strong>初始积分</strong>:</td><td><s:textfield name="formBean.vipCard.initialScore"/></td>
	       </tr> 
	       <tr class="InnerTableContent">
	          <td><strong>发卡连锁店</strong>:</td>
	          <td><s:select id="issueChainStore" name="formBean.vipCard.issueChainStore.chain_id"  list="uiBean.chainStores" listKey="chain_id" listValue="chain_name"/></td>
	       </tr>   
	       <tr class="InnerTableContent">
	          <td><strong>发卡日期</strong>:</td>
	          <td><s:textfield id="issueDate"  name="formBean.vipCard.cardIssueDate" cssClass="easyui-datebox" data-options="width:100,editable:false">
	                   <s:param name="value"><s:date name="formBean.vipCard.cardIssueDate" format="yyyy-MM-dd" /></s:param> 
	              </s:textfield>
	           </td>
	       </tr> 
	       <tr class="InnerTableContent">
	          <td><strong>有效日期</strong>:</td>
	          <td><s:textfield id="expireDate" name="formBean.vipCard.cardExpireDate" cssClass="easyui-datebox" data-options="width:100,editable:false">
	                   <s:param name="value"><s:date name="formBean.vipCard.cardExpireDate" format="yyyy-MM-dd" /></s:param> 
	              </s:textfield>
	       </tr> 
	       <tr class="InnerTableContent">
	          <td><strong>卡持有者</strong>:</td><td><s:textfield id="customerName" name="formBean.vipCard.customerName" cssClass="easyui-validatebox" data-options="required:true" maxlength="10"/></td>
	       </tr> 
	       <tr class="InnerTableContent">
	          <td><strong>卡主生日</strong>:</td>
	          <td><s:textfield id="birthday" name="formBean.vipCard.customerBirthday" cssClass="easyui-datebox" data-options="required:true,width:100,editable:true">
	                   <s:param name="value"><s:date name="formBean.vipCard.customerBirthday" format="yyyy-MM-dd" /></s:param> 
	              </s:textfield>
	          </td>
	       </tr> 
	       <tr class="InnerTableContent">
	          <td><strong>卡主性别</strong>:</td>
	          <td><s:select id="cardHolderGender" name="formBean.vipCard.gender"  list="uiBean.genders"  listKey="key" listValue="value"/></td>
	       </tr> 
	       <tr class="InnerTableContent">
	          <td><strong>证件号码</strong>:</td><td><s:textfield name="formBean.vipCard.idNum"  maxlength="18"/></td>
	       </tr> 
	       <tr class="InnerTableContent">
	          <td><strong>电话</strong>:</td><td><s:textfield id="telephone" name="formBean.vipCard.telephone"  maxlength="11" cssClass="easyui-validatebox" data-options="required:true"/><br/></td>
	       </tr> 
	       <tr class="InnerTableContent">
	          <td></td><td><input type="button" value="自动使用电话作为VIP号" onclick="useTelAsVIPNum();"/></td>
	       </tr> 	       
	       <tr class="InnerTableContent">
	          <td><strong>省</strong>:</td><td><s:textfield name="formBean.vipCard.province"  maxlength="5"/></td>
	       </tr>
	       <tr class="InnerTableContent"> 
	          <td><strong>市</strong>:</td><td><s:textfield name="formBean.vipCard.city"  maxlength="5"/></td>
	       </tr> 
	       <tr class="InnerTableContent">
	          <td><strong>区</strong>:</td><td><s:textfield name="formBean.vipCard.zone" maxlength="5"/></td>
	       </tr>
	       <tr class="InnerTableContent"> 
	          <td><strong>街道</strong>:</td><td><s:textfield name="formBean.vipCard.street" maxlength="50"/></td>
	       </tr> 
	       <tr class="InnerTableContent">
	          <td><strong>备注</strong>:</td><td><s:textarea name="formBean.vipCard.comment" rows="4"/></td>
	       </tr> 	      
	       <tr class="InnerTableContent">
	          <td colspan="2"> <s:if test="#session.LOGIN_CHAIN_USER.containFunction('chainVIPJSONAction!saveUpdateVIPCard')"><input type="button" value="添加/更新" onclick="update();"/>&nbsp;&nbsp;</s:if>
	                           <s:if test="#session.LOGIN_CHAIN_USER.containFunction('chainVIPJSONAction!saveUpdateVIPCard')"><input type="button" value="添加/更新后,继续添加" onclick="updateContinue();"/>&nbsp;&nbsp;</s:if>
	                           <input type="button" value="取消" onclick="window.close();"/></td>
	       </tr>
	    </table>
	    </s:form>
	    </td>
	</tr>
	</table>   
</body>
</html>