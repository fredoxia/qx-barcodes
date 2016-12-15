<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib prefix="s" uri="/struts-tags" %>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>千禧宝贝连锁店管理信息系统</title>
<%@ include file="../../common/Style.jsp"%>
<script type="text/javascript" src="<%=request.getContextPath()%>/conf_files/js/print/printPrepaid.js"></script>
<script>
$(document).ready(function(){
	parent.$.messager.progress('close'); 
});

function deposit(){
    var chainId = $("#chainId").val();
    if (chainId == "" || chainId == 0){
		alert("连锁店是必选项");
		return ;
	}
    
    var vipId = $("#vipCardIdHidden").val();
    if (vipId == "" || vipId == 0){
		alert("VIP卡是必选项");
		$("#vipCardNo").focus();
		return ;
	}
	
    var depositType = $("#depositType").val();
	if (depositType == ""){
		alert("现金/刷卡必须正确选择，否则帐目将无法正确");
		return ;
	}
	
    var amount = $("#amount").val();
	if (amount == "" || amount == 0){
		alert("充值金额 必须大于0");
		return ;
	}
		
	var params = $("#vipPrepaidDepositForm").serialize();
	$.post("<%=request.getContextPath()%>/actionChain/chainVIPJSONAction!saveVIPPrepaidDeposit",params, backProcessDepositPrepaid,"json");
	$.messager.progress({
		title : '提示',
		text : '数据处理中，请稍后....'
	});
}

function backProcessDepositPrepaid(data){
	$.messager.progress('close'); 
    var response = data.response;
    if (response.returnCode == SUCCESS){
       alert(response.message);
       
       var prepaid = response.returnValue;
       try {
    	   //alert((prepaid.accumulateVipPrepaid).toFixed(0));
	        printSalesOrder(prepaid);
       } catch (e){
			alert("小票打印出现问题 ,请联系总部管理员");
       }

       document.vipPrepaidDepositForm.action = "actionChain/chainVIPJSPAction!preDepositVIPPrepaid";
	   document.vipPrepaidDepositForm.submit();
    } else 
       alert("操作失败:" + response.message);
}

function changeChainStore(chainId){
}
</script>
</head>
<body>
   <s:hidden name="uiBean.chainStoreConf.printCopy" id="printCopy"/>
   <s:hidden name="uiBean.chainStoreConf.address" id="address"/>
   <s:hidden name="uiBean.chainStoreConf.prepaidCalculationType" id="prepaidCalculationType"/>
    
   <s:form action="" method="POST"  name="vipPrepaidDepositForm" id="vipPrepaidDepositForm" theme="simple"> 
	<table width="95%" align="center"  class="OuterTable">
	    <tr><td>
	
		 <table width="100%" border="0">
		    <tr class="PBAOuterTableTitale">
		       <td height="50" colspan="3">VIP 预存金充值<br />
	           	- 充值前请确认vip信息,确保预存金充值给正确的vip卡号</td>
	        </tr>
		    <tr class="InnerTableContent">
		      <td>连锁店 *</td>
		      <td colspan="2">
		      	<%@ include file="../include/SearchChainStore.jsp"%>
		      	<s:hidden name="formBean.accessLevel" id="accessLevel" value="1"/>
		      </td>
		    </tr>
		    <tr class="InnerTableContent">
		      <td>单据日期*</td>
		      <td colspan="2">
		      	<s:if test="formBean.canEditOrderDate">
						<s:textfield id="orderDate" name="formBean.vipPrepaid.dateD" cssClass="easyui-datebox"  data-options="width:100,editable:false"/>
				</s:if><s:else>
						<s:textfield id="orderDate" readonly="true" name="formBean.vipPrepaid.dateD" size="8"/>
				</s:else>
		      </td>
		    </tr>		    				                         
		    <tr class="InnerTableContent">
		      <td>VIP卡号 *</td>
		      <td colspan="2">
		      	<%@include file="SearchVIPCard.jsp"%>
		      </td>
		    </tr>
		    <tr class="InnerTableContent">
		      <td>现金/刷卡 *</td>
		      <td colspan="2">
		      	<s:select id="depositType" name="formBean.vipPrepaid.depositType"  list="#{'C':'现金','D':'刷卡'}" listKey="key" listValue="value" headerKey="" headerValue="------" />
		      </td>
		    </tr>		    
		    <tr class="InnerTableContent">
		      <td>充值金额 *</td>
		      <td colspan="2">
		      	<s:textfield name="formBean.vipPrepaid.amount" id="amount" cssClass="easyui-numberspinner" style="width:80px;" required="required" data-options=" increment:1,min:50,max:4000"/>
		      </td>
		    </tr>		
		    <tr class="InnerTableContent">
		      <td>备注</td>
		      <td colspan="2">
		      	<s:textfield name="formBean.vipPrepaid.comment" size="30" maxlength="20"/>
		      </td>
		    </tr>			        
		    <tr class="InnerTableContent">
		      <td width="7%" height="30">
		      </td>
		      <td width="79%">
		        <input type="button" value="预存金充值" onclick="deposit();" />
		      </td>
		      <td>&nbsp;</td>
		    </tr>
		  </table>
	   </td></tr>
	 </table>
	 </s:form>
	 <object  classid="clsid:AF33188F-6656-4549-99A6-E394F0CE4EA4"       
         codebase="<%=request.getContextPath()%>/conf_files/sc_setup.exe"     
         id="pazu"       
         name="pazu" >       
    <param  name="License"  value="8F34B771723DCC171F931EA900F9967E"/>     
</object>
</body>
</html>