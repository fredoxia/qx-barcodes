<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib prefix="s" uri="/struts-tags" %>
<%@ page import="com.onlineMIS.ORM.entity.headQ.barcodeGentor.*" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@ include file="../../common/Style.jsp"%>
<title>管理VIP卡类型</title>
<script>
var refreshURL = "chainVIPJSPAction";
function update(){
	var error ="";
	var vipTypeName = $("#vipTypeName").val();
	if (vipTypeName == ""){
		error +="VIP卡类型信息 - 不能为空\n";
		$("#vipTypeName").focus();
	}
	
	var discountRate = $("#discountRate").val();
	if (discountRate != "" && isNaN(discountRate)){
        error += "折扣率 - 必须是小于或者等于1的数字\n";
        $("#discountRate").focus();
	} else if (discountRate <= 0 || discountRate > 1){
        error += "折扣率 - 必须是小于或者等于1的数字\n";
        $("#discountRate").focus();
	} 

	var couponRatio = $("#couponRatio").val();
	if (couponRatio != "" && isNaN(couponRatio)){
        error += "积分系数 - 必须是数字\n";
        $("#couponRatio").focus();
	}
	
	if (error != "")
		alert(error);
	else {
	    var params=$("#editVIPTypeForm").serialize();  
	    $.post("actionChain/chainVIPJSONAction!saveUpdateVIPType",params, editBKProcess,"json");
	}
}

function editBKProcess(data){
	var isSuccess = data.isSuccess;
	if (isSuccess == 1){
        alert("成功更新");
        window.close();
        var url  = window.opener.location.toString();
        if (url.indexOf(refreshURL) >=0)  
        	window.opener.location = "chainVIPJSPAction!viewVIPTypes";
	} else{
		var error = data.error;
		$("#errorDiv").html(error);
	}
}
</script>
</head>
<body>
    <table width="90%" align="center"  class="OuterTable">
    <tr><td>
        <s:form id="editVIPTypeForm" name="editVIPTypeForm" method="post" action="" theme="simple">
        
	    <table width="100%" border="0">
	       <tr class="PBAOuterTableTitale">
	          <td colspan="2">VIP卡类型信息</td>
	       </tr>
	       <tr>
	          <td colspan="2" class="InnerTableContent">* 修改 折扣率和积分系数 可能会影响到以前绑定到当前卡类型的客户</td>
	       </tr>
	       <tr>
	          <td colspan="2" class="InnerTableContent"><div id="messageDiv"></div><div id="errorDiv" style="color: red"></div></td>
	       </tr>
	       <tr class="InnerTableContent">
	          <td><strong>卡类型名称 </strong>  : </td>
	          <td><s:textfield name="formBean.vipType.vipTypeName" id="vipTypeName" maxlength="15"/><s:hidden name="formBean.vipType.id"/></td>
	       </tr>
	       <tr class="InnerTableContent">
	          <td><strong>折扣率 </strong>:</td><td><s:textfield name="formBean.vipType.discountRate" id="discountRate"/></td>
	       </tr>

	       <tr class="InnerTableContent">
	          <td><strong>积分系数</strong>:</td><td><s:textfield name="formBean.vipType.couponRatio" id="couponRatio"/></td>
	       </tr>
	       <tr class="InnerTableContent">
	          <td><strong>备注</strong>:</td><td><s:textarea name="formBean.vipType.comment" rows="4"/></td>
	       </tr>   
	       <tr class="InnerTableContent">
	          <td colspan="2"> <s:if test="#session.LOGIN_CHAIN_USER.containFunction('chainVIPJSONAction!saveUpdateVIPType')"><input type="button" value="添加/更新" onclick="update();"/>&nbsp;&nbsp;</s:if>
	                           <input type="button" value="取消" onclick="window.close();"/>
	          </td>
	       </tr>
	    </table>
	    </s:form>
	    </td>
	</tr>
	</table>   
</body>
</html>