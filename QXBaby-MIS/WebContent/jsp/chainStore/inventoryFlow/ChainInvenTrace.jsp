<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib prefix="s" uri="/struts-tags" %>
<%@ page import="com.onlineMIS.common.Common_util,java.util.Date,java.text.SimpleDateFormat" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>千禧宝贝连锁店管理信息系统</title>
<%@ include file="../../common/Style.jsp"%>

<script>
$(document).ready(function(){
	parent.$.messager.progress('close'); 

	var params= $.serializeObject($('#CheckProductForm'));
	
	$('#dataGrid').datagrid({
		url : 'inventoryFlowJSONAction!getInventoryTraceInfor',
		queryParams: params,
		fit : true,
		fitColumns : true,
		border : false,
		checkOnSelect : false,
		selectOnCheck : false,
		singleSelect:true,
		showFooter:true,
		rownumbers:true,
		nowrap : false,
		columns : [ [
					{field:'date', width:50,title:'发生日期'},
					{field:'descp', width:50,title:'描述'},
					{field:'quantity', width:50,title:'数量'},
					{field:'actionCode', width:50,title:'编码'},
					{field:'orderId', width:40,title:'单据号'}
			     ]]
	});
});
/**
 * user input the product code and press enter
 */
function searchProductsProductCode(){
	var productCode = $("#productCode").val();
	var chainStore = $("#chainStore").val();

	 if (validateProductCodeInput(productCode)){
	    var url = encodeURI(encodeURI("chainSalesJSPAction!scanByProductCode?formBean.productCode=" + productCode+"&formBean.chainId=" + chainStore))  ; 
	
	    window.open(url,'新窗口','height=530, width=600, toolbar=no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=no');    
	}
}

/**
 * to validate the product code input
 */
function validateProductCodeInput(productCode){
	if (productCode.length <= 1){
       alert("输入的货号太短");
       return false;
	}
	return true;
}
/**
 * retrieve the product by input barcode
 */
function retrieveProductByBarcode(index_trigger, suffix, currentBarcode){
	$("#barcode").attr("value",currentBarcode);
}

function validateRowInputFromChild(index_trigger,suffix, currentBarcode){
	return true;
}
function clearSearch(){
	$("#barcode").attr("value","");
	$("#productCode").attr("value","");
}
function genChainInvenTrace(){
	var barcode = $("#barcode").val();
	if (!isValidBarcode(barcode)){
		alert("请输入一个正确的条码,或者通过货号查找");
		return ;
	}
	var params = $.serializeObject($('#CheckProductForm')); 
	$('#dataGrid').datagrid('load',params); 
}
</script>
</head>
<body>
	<div class="easyui-layout" data-options="fit : true,border : false">
		<div data-options="region:'north',border:false" style="height: 175px;">
			       <s:form id="CheckProductForm" action="/actionChain/salesAction!searchOrders" theme="simple" method="POST">
					   <table width="95%" border="0">
					    <tr class="PBAOuterTableTitale">
				          <td height="30" colspan="3">
				          - 此功能可以帮助用户查询某个商品的库存增减情况<br/>
				          </td>
					    </tr>
					    <tr class="InnerTableContent">
					      <td height="25">&nbsp;</td>
					      <td width="50"><strong>连锁店</strong></td>
					      <td>		
					      	<s:select id="chainStore" name="formBean.chainId"  list="uiBean.chainStores" listKey="chain_id" listValue="chain_name"/>
						  </td>
					    </tr>
					    <tr class="InnerTableContent">
					      <td height="25">&nbsp;</td>
					      <td><strong>条码</strong></td>
					      <td>		
					      	<s:textfield name="formBean.barcode" id="barcode"/>
						  </td>
					    </tr>
						<tr class="InnerTableContent">
					      <td height="25">&nbsp;</td>
					      <td><strong>货号</strong></td>
					      <td>		
						  	<s:textfield name="formBean.productCode" id="productCode" size="18"/><input type="button" id="checkBrandBt" value="查询" onclick="searchProductsProductCode();"/><input type="button" id="clearBtn" value="清除" onclick="clearSearch();"/>
						  	<div id="productInfo"></div>
						  </td>
					    </tr>					    
	                    <tr class="InnerTableContent">
					      <td height="25" colspan="2">&nbsp;</td>
					      <td ><input type="button" value="跟踪产品库存信息" onclick="genChainInvenTrace()"/></td>
					    </tr>
					  </table>
				   </s:form>
		</div>
		<div data-options="region:'center',border:false">
			    <table id="dataGrid">			       
		        </table>
		</div>
	</div>	
</body>
</html>