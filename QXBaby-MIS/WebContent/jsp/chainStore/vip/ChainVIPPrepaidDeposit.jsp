<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib prefix="s" uri="/struts-tags" %>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>千禧宝贝连锁店管理信息系统</title>
<%@ include file="../../common/Style.jsp"%>

<script>
$(document).ready(function(){
	parent.$.messager.progress('close'); 
});

function deposit(){
	
}

function changeChainStore(chainId){
}
</script>
</head>
<body>
   <s:form action="" method="POST"  name="vipPrepaidDepositForm" id="vipPrepaidDepositForm" theme="simple"> 
	<table width="95%" align="center"  class="OuterTable">
	    <tr><td>
	
		 <table width="100%" border="0">
		    <tr class="PBAOuterTableTitale">
		       <td height="50" colspan="3">VIP 预存金充值<br />
	           	- 充值前请确认vip信息,确保预存金充值给正确的vip卡号</td>
	        </tr>
		    <tr class="InnerTableContent">
		      <td>连锁店</td>
		      <td colspan="2">
		      	<%@ include file="../include/SearchChainStore.jsp"%>
		      </td>
		    </tr>
		    <tr class="InnerTableContent">
		      <td>VIP卡号</td>
		      <td colspan="2">
		      	<%@include file="SearchVIPCard.jsp"%>
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
</body>
</html>