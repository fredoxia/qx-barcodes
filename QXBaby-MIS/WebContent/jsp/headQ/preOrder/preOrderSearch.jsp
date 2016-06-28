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
var dataGrid;
$(document).ready(function(){
	parent.$.messager.progress('close'); 
	
	var params= $.serializeObject($('#preOrderForm'));
	
	dataGrid = $('#dataGrid').datagrid({
		url : 'preOrderActionJSON!searchPreOrders',
		queryParams: params,
		fit : true,
		fitColumns : true,
		pagination : true,
		pageSize : 15,
		pageList : [15, 30],
		border : false,
		checkOnSelect : false,
		selectOnCheck : false,
		sortName : 'receiveAmtSum',
		sortOrder : 'desc',
		singleSelect:true,
		showFooter:true,
		rownumbers:true,
		nowrap : false,
		columns : [ [
					{field:'custName', width:80,title:'客户名字',fixed:true},
					{field:'chainName', width:80,title:'连锁店',fixed:true,
						formatter: function (value, row, index){
							return row.chainStore.chain_name;
						}},
					{field:'orderIdentity', width:50,title:'订货会',sortable:true,order:'desc'},
					{field:'totalQuantity', width:50,title:'总数量(手)',sortable:true,order:'desc'},
					{field:'sumWholePrice', width:50,title:'总批发价',sortable:true,order:'desc'},
					{field:'sumRetailPrice', width:50,title:'总零售价',sortable:true,order:'desc'},
					{field:'createDate', width:50,title:'订货日期',sortable:true},
					{field:'exportDate', width:50,title:'导出日期',sortable:true}
					]],
		toolbar : '#toolbar',
		onRowContextMenu : function(e, rowIndex, rowData) {
			e.preventDefault();
			$(this).datagrid('unselectAll').datagrid('uncheckAll');
			$(this).datagrid('selectRow', rowIndex);
		}
	});
});
function searchOrders(){

	var params = $.serializeObject($('#preOrderForm')); 
	$('#dataGrid').datagrid('load',params); 
}
function downloadOrder(){
	var rows = dataGrid.datagrid('getSelections');
	if (rows.length == 0){
		parent.$.messager.alert('错误', '请选中一张订单再继续操作', 'error');
		return;
	}
	var	id = rows[0].id;
	var param = "formBean.order.id="+ id;
	
    document.preOrderForm.action="preorderHQJSP!downloadOrderHQById?" + param;
    document.preOrderForm.submit();
}
function openOrder(){
	var rows = dataGrid.datagrid('getSelections');
	if (rows.length == 0){
		parent.$.messager.alert('错误', '请选中一张订单再继续操作', 'error');
		return;
	}
	
	var	id = rows[0].id;
	
	var param = "formBean.order.id="+ id;

	var custName = rows[0].custName;
	parent.$.modalDialog({
		title : "客户订单 " + custName,
		width : 900,
		height : 550,
		modal : false,
		draggable:true,
		href : 'preorderHQJSP!getOrderById?' + param
	});
}
</script>
</head>
<body>
	<div class="easyui-layout" data-options="fit : true,border : false">
		<div data-options="region:'north',border:false" style="height: 105px;">
		   <s:form id="preOrderForm" name="preOrderForm" action="" theme="simple" method="POST">  
			<table width="100%" border="0">
			    <tr class="InnerTableContent">
			      <td width="45" height="25">&nbsp;</td>
			      <td width="76"><strong>客户名字</strong></td>
			      <td width="284" colspan="3">
			        <%@ include file="../include/ClientInput.jsp"%>
			      </td>
			    </tr>
				<tr class="InnerTableContent">
			      <td height="30">&nbsp;</td>
			      <td><strong>订货会代码</strong></td>
			      <td colspan="3"><s:select name="formBean.order.orderIdentity"  list="uiBean.identities" listKey="orderIdentity" listValue="orderIdentity" headerKey="" headerValue="---全部---" /></td>
			    </tr>
                <tr class="InnerTableContent">
			      <td height="25">&nbsp;</td>
			      <td>&nbsp;</td>
			      <td colspan="2"><input type="button" value="搜索单据" onclick="searchOrders();"/>
			      </td>
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
	<div id="toolbar" style="display: none;">
			<a onclick="openOrder();" href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-redo',plain:true">打开选中客户订单</a>
			<a onclick="downloadOrder();" href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-save',plain:true">下载客户订单</a>
	</div>
</body>
</html>