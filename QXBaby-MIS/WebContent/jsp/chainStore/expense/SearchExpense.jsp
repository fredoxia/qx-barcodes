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
var baseurl = "<%=request.getContextPath()%>";
$(document).ready(function(){
	parent.$.messager.progress('close'); 
	
	var params= $.serializeObject($('#preGenReportForm'));

	$('#dataGrid').datagrid({
		url : 'expenseChainJSON!searchExpenseChain',
		queryParams: params,
		fit : true,
		fitColumns : false,
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
		rowStyler: function(index,row){
			var style = "";
			if (row.statusCode == -1)
				style += 'color:red;';
			return style;
		},
		columns : [ [
					{field:'entity', width:120,title:'店铺'},	
					{field:'expenseDate', width:100,title:'费用日期'},	
					{field:'expenseType', width:100,title:'费用类别'},
					{field:'feeType', width:100,title:'支付方式'},
					{field:'amount', width:60,title:'金额'},
					{field:'userName', width:70,title:'修改人员'}	,
					{field:'comment', width:200,title:'备注'}	,
					{field:'status', width:200,title:'状态'}	

			     ]],
		toolbar : '#toolbar'
	});
});
function changeChainStore(chainId){}
function genSalesReport(){

	var params = $.serializeObject($('#preGenReportForm')); 
	$('#dataGrid').datagrid('load',params); 
}
function deleteExpense(){
	var rows = $('#dataGrid').datagrid('getSelections');
	if (rows.length == 0){
		$.messager.alert('错误', '请选中一条费用记录再继续操作', 'error');
		return;
	}
	
	var	id = rows[0].id;
	var params = "formBean.expense.id="+ id;

	$.post("<%=request.getContextPath()%>/actionChain/expenseChainJSON!deleteExpenseChain",params, backProcessDeleteExpense,"json");
}
function backProcessDeleteExpense(data){
    if (data.returnCode == SUCCESS){       
        $('#dataGrid').datagrid('reload'); 
        $.messager.alert('成功信息', '成功删除费用记录', 'info');
     } else 
        $.messager.alert('操作失败', data.message, 'error');
}
function loadExpenseForUpdate(){
	var rows = $('#dataGrid').datagrid('getSelections');
	if (rows.length == 0){
		$.messager.alert('错误', '请选中一条费用再继续操作', 'error');
		return;
	}
	
	var	id = rows[0].id;
	
	var param = "formBean.expense.id="+ id;

	var windowTitle =rows[0].entity + " 费用 " + rows[0].comment;
	$.modalDialog({
		title : windowTitle,
		width : 600,
		height : 350,
		modal : false,
		draggable:true,
		href : 'expenseChainJSP!getExpenseById?' + param
	});
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
			      <td width="76"><strong>费用日期</strong></td>
			      <td width="284" colspan="3">
			        <s:textfield id="startDate" name="formBean.startDate" cssClass="easyui-datebox"  data-options="width:100,editable:false"/>
			        &nbsp;至&nbsp;
			        <s:textfield id="endDate" name="formBean.endDate" cssClass="easyui-datebox"  data-options="width:100,editable:false"/>
			      </td>
			    </tr>
				<tr class="InnerTableContent">
			      <td height="30">&nbsp;</td>
			      <td><strong>连锁店</strong></td>
			      <td colspan="3"><%@ include file="../include/SearchChainStore.jsp"%>
			                      <s:hidden name="formBean.accessLevel" id="accessLevel" value="1"/></td>
			    </tr>
                   <tr class="InnerTableContent">
			      <td height="25">&nbsp;</td>
			      <td>&nbsp;</td>
			      <td colspan="2">
			           <a href="javascript:void(0)" class="easyui-linkbutton" onclick="genSalesReport();">查找费用</a>
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
			<a onclick="deleteExpense();" href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true">删除费用</a>
			<a onclick="loadExpenseForUpdate();" href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-edit',plain:true">修改费用</a>
	</div>
</body>
</html>