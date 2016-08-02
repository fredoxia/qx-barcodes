<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib prefix="s" uri="/struts-tags" %>
<%@ page import="com.onlineMIS.common.Common_util,java.util.Date,java.text.SimpleDateFormat,com.onlineMIS.ORM.entity.headQ.finance.ChainAcctFlowReportItem" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>千禧宝贝连锁店管理信息系统</title>
<%@ include file="../../common/Style.jsp"%>

<script>
var ITEM_TYPE_P = "<%=ChainAcctFlowReportItem.ITEM_TYPE_PURCHASE%>";
var ITEM_TYPE_F = "<%=ChainAcctFlowReportItem.ITEM_TYPE_FINANCE%>";

$(document).ready(function(){
	parent.$.messager.progress('close'); 

	var params= $.serializeObject($('#SalesOrderSearchForm'));
	
	$('#dataGrid').datagrid({
		url : 'financeChainJSON!searchAcctFlow',
		queryParams: params,
		fit : true,
		fitColumns : false,
		border : false,
		checkOnSelect : false,
		selectOnCheck : false,
		singleSelect:true,
		rownumbers:true,
		nowrap : false,
		columns : [ [
					{field:'date', width:120,title:'单据日期'},
					{field:'itemTypeName', width:90,title:'单据种类'},
					{field:'comment', width:240,title:'单据摘要'},
					{field:'quantity', width:60,title:'单据数量',
						formatter: function (value, row, index){
							return parseQuantity(row.quantity);
						}},
					{field:'amount', width:65,title:'单据金额'},
					
					{field:'acctIncrease', width:65,title:'应付增加',
						formatter: function (value, row, index){
							return parseNumberValue(row.acctIncrease);
						}},
					{field:'acctDecrease', width:65,title:'应付减少',
						formatter: function (value, row, index){
							return parseNumberValue(row.acctDecrease);
						}},
					{field:'postAcct', width:110,title:'+应付/-应收金额',
						formatter: function (value, row, index){
							return (row.postAcct).toFixed(0);
						}},
					{field:'action', width:65,title:'',
						formatter: function (value, row, index){
							var url = "";
							var id = row.id;
							if (row.itemType == ITEM_TYPE_P)
								url = "purchaseAction!getPurchase?formBean.order.order_ID=" + id ;
							else if (row.itemType == ITEM_TYPE_F)
							    url = "financeChainJSP!getFHQ?formBean.financeBill.id="+id;
							
							var str = '';
							if (row.id != 0)
							    str += $.formatString('<a href="#" onclick="openWin(\'{0}\',\'{1}\');"><img border="0" src="{2}" title="查看"/></a>', url, row.itemTypeName, '<%=request.getContextPath()%>/conf_files/easyUI/themes/icons/text_1.png' );
							return str;
						}}
			     ]]
	});
});


function searchAcctFlow(){
	var params = $.serializeObject($('#SalesOrderSearchForm')); 
	$('#dataGrid').datagrid('load',params); 
}

function openWin(url, typeName){
	window.open (url,typeName,'height=800, width=1000, toolbar=no,scrollbars=yes, resizable=yes,  menubar=no, location=no, status=no');
}

function changeChainStore(chainId){

}
</script>
</head>
<body>
	<div class="easyui-layout" data-options="fit : true,border : false">
		<div data-options="region:'north',border:false" style="height: 145px;">
               <s:form id="SalesOrderSearchForm" action="/actionChain/salesAction!searchOrders" theme="simple" method="POST"> 
                 <input type="hidden" id="accessLevel" name="formBean.accessLevel" value="4"/>
                <table width="100%" border="0">
			    <tr class="PBAOuterTableTitale">
		          <td height="30" colspan="7">
                           - 查询与总部之间的往来账目,包括(货品采购款项,财务款项,等)</td>
			    </tr>
			    <tr class="InnerTableContent">
			      <td width="45" height="25">&nbsp;</td>
			      <td width="76"><strong>连锁店</strong></td>
			      <td width="284"><%@ include file="../include/SearchChainStore.jsp"%></td>
			      <td width="71"></td>
			      <td width="200"></td>
			      <td></td>
			    </tr>				
			    <tr class="InnerTableContent">
			      <td height="25">&nbsp;</td>
			      <td><strong>日期</strong></td>
			      <td>
			      	 <s:textfield id="startDate" name="formBean.searchStartTime" cssClass="easyui-datebox"  data-options="width:100,editable:false"/>			      
			      			&nbsp; 至&nbsp;
			         <s:textfield id="endDate" name="formBean.searchEndTime" cssClass="easyui-datebox"  data-options="width:100,editable:false"/>	
				  </td>
			      <td></td>
			      <td></td>
			      <td></td>
			    </tr>
                   <tr class="InnerTableContent">
			      <td height="25">&nbsp;</td>
			      <td>&nbsp;</td>
			      <td colspan="2"><s:if test="#session.LOGIN_CHAIN_USER.containFunction('financeChainJSON!searchAcctFlow')"><input type="button" value="查询往来账目" onclick="searchAcctFlow();"/></s:if></td>
			      <td>&nbsp;</td>
			      <td>&nbsp;</td>
			    </tr>
			  </table>
             </s:form>
		  </div>
		  <div data-options="region:'center',border:false">
			    <table id="dataGrid">			       
		        </table>
		  </div>
	</div>		                     
	 
<script>
$(document).ready(function(){
	<s:if test="formBean.initialSearch == true">
	      searchOrders();
	</s:if>
});
</script>
</body>
</html>