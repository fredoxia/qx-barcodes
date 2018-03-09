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
var dataGrid ;
$(document).ready(function(){
	parent.$.messager.progress('close'); 
	var params= $.serializeObject($('#preGenReportForm'));
	
	dataGrid =  $('#dataGrid').datagrid({
		url : 'chainTransferActionJSON!searchTransferOrders',
		queryParams: params,
		fit : true,
		fitColumns : true,
		pagination : true,
		pageSize : 15,
		pageList : [ 15, 30],
		border : false,
		checkOnSelect : false,
		selectOnCheck : false,
		singleSelect:true,
		showFooter:true,
		rownumbers:true,
		nowrap : false,
		columns : [ [
					{field:'fromChain', width:50,title:'调出连锁店'},
					{field:'toChain', width:50,title:'调入连锁店'},
					{field:'creator', width:30,title:'创建人'},
					{field:'orderDate', width:50,title:'单据创建日期'},
					{field:'status', width:40,title:'状态'},
					{field:'quantity', width:40,title:'调货数量'},
					{field:'wholePrice', width:40,title:'总批发价',
						formatter: function (value, row, index){
							if (row.wholePrice == 0)
								return "";
							else 
								return row.wholePrice;
						}},
					{field:'salesPrice', width:40,title:'总零售价'},
					{field:'transportationFee', width:40,title:'运费'},
					{field:'userComment', width:65,title:'备注'}
			     ]],
		toolbar : '#toolbar',
		onRowContextMenu : function(e, rowIndex, rowData) {
			e.preventDefault();
			$(this).datagrid('unselectAll').datagrid('uncheckAll');
			$(this).datagrid('selectRow', rowIndex);
		}
	});
});

function searchTransferOrder(){
	var params = $.serializeObject($('#preGenReportForm')); 
	$('#dataGrid').datagrid('load',params); 
}
function loadTransferOrder(){
	var rows = dataGrid.datagrid('getSelections');
	if (rows.length == 0){
		parent.$.messager.alert('错误', '请选中一条记录再继续操作', 'error');
		return;
	}
	
	var	id = rows[0].id;
	
	var url = "chainTransferJSPAction!loadTransferOrder?formBean.transferOrder.id=" +id;
	var nodeTitle = "调货单" + id;
	addTab3(url,nodeTitle);
}
function changeChainStore(){
	
}
</script>
</head>
<body>
	<div class="easyui-layout" data-options="fit : true,border : false">
		<div data-options="region:'north',border:false" style="height: 115px;">
		   <s:form id="preGenReportForm" name="preGenReportForm" action="" theme="simple" method="POST">  
			<table width="100%" border="0">
			    <tr class="InnerTableContent">
			      <td width="45" height="25">&nbsp;</td>
			      <td width="76"><strong>单据日期</strong></td>
			      <td width="250">
			        <s:textfield id="startDate" name="formBean.searchStartTime" cssClass="easyui-datebox"  data-options="width:100,editable:false"/>
			        &nbsp;至&nbsp;
			        <s:textfield id="endDate" name="formBean.searchEndTime" cssClass="easyui-datebox"  data-options="width:100,editable:false"/>
			      </td>
			      <td width="76"><strong>单据状态</strong></td>
			      <td><s:select id="orderStatus" name="formBean.transferOrder.status"  list="uiBean.orderStatus" listKey="key" listValue="value" headerKey="-1" headerValue="--所有状态--" /></td>
			    </tr>
				<tr class="InnerTableContent">
			      <td height="30">&nbsp;</td>
			      <td><strong>连锁店</strong></td>
			      <td><%@ include file="../include/SearchChainStore.jsp"%><input type="hidden" id="indicator" name="formBean.indicator" value="-1"/></td>
			    </tr>
                   <tr class="InnerTableContent">
			      <td height="25">&nbsp;</td>
			      <td>&nbsp;</td>
			      <td colspan="4"><input type="button" value="查询单据" onclick="searchTransferOrder();"/>
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
			<a onclick="loadTransferOrder();" href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true">打开单据</a>
	</div>

</body>
</html>