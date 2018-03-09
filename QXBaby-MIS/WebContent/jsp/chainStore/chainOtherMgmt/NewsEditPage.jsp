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
var editRow = undefined;
var newsDataGrid = undefined;
$(document).ready(function(){
	parent.$.messager.progress('close'); 
	
	newsDataGrid = $('#dataGrid').datagrid({
		url : 'newsActionJSON!getAllNews',
		queryParams: "",
		fit : true,
		fitColumns : true,
		pagination : false,
		border : false,
		checkOnSelect : false,
		selectOnCheck : false,
		singleSelect:true,
		showFooter:false,
		rownumbers:true,
		nowrap : false,
		columns : [ [
						{
							field:'id', 
							width:20,
							title:'数据库标识符'
						},	
					{
						field:'title', 
						width:50,
						title:'新闻标题',
						editor : {
							 type : 'validatebox',
							 options : {
								 required : true,
								 validType:['length[0,20]']
							 }
						}
					},										
					{
						field:'content', 
						width:200,
						title:'新闻内容',
						editor : {
							 type : 'validatebox',
							 options : {
								 required : true,
								 validType:['length[0,90]']
							 }
						}
					}
			     ]],
		onAfterEdit : function(rowIndex, rowData, changes) {
		
			submitEdit(rowIndex, rowData);
		},
		toolbar : '#toolbar'
	});
});
function addNews(){
	if (editRow == undefined){
		newsDataGrid.datagrid('insertRow',{
			index: 0,
			row: {
				id : '',
				title : '',
				content : ''
			}
		});
		newsDataGrid.datagrid('beginEdit',0);
		editRow = 0;
	} else {
		
	}
}

function editNews(){
	var rows = newsDataGrid.datagrid('getSelections');
	if (rows.length == 0){
		parent.$.messager.alert('错误', '请选中一条记录再继续操作', 'error');
		return;
	}
	
    var row = rows[0];
	var rowIndex = newsDataGrid.datagrid('getRowIndex',row);
	newsDataGrid.datagrid('beginEdit',rowIndex);
	editRow = rowIndex;
}

function deleteNews(){
	var rows = newsDataGrid.datagrid('getSelections');
	if (rows.length == 0){
		parent.$.messager.alert('错误', '请选中一条记录再继续操作', 'error');
		return;
	}
	
    var row = rows[0];
    $.post("newsActionJSON!deleteNews",row, deleteNewsBKProcess,"json");
}

function deleteNewsBKProcess(data){
	var returnCode = data.returnCode;
	if (returnCode == SUCCESS){
		newsDataGrid.datagrid('load', '');
		editRow = undefined;
		newsDataGrid.datagrid('acceptChanges');
	} else {
		$.messager.alert('失败警告', "删除消息失败", 'error');
	}
}

function endEdit(){
	if (editRow != undefined){
	  newsDataGrid.datagrid('endEdit', editRow);
	}
}
function submitEdit(rowIndex, rowData){

	 $.post("newsActionJSON!updateNews",rowData, saveEditNewsBKProcess,"json");
}
function saveEditNewsBKProcess(data){
	var returnCode = data.returnCode;
	if (returnCode == SUCCESS){
		newsDataGrid.datagrid('load', '');
		editRow = undefined;
		newsDataGrid.datagrid('acceptChanges');
	} else {
		$.messager.alert('失败警告', "保存消息失败 : " + data.message, 'error');
		newsDataGrid.datagrid('beginEdit', editRow);
	}
}
function cancelEdit(){
	newsDataGrid.datagrid('rejectChanges');
	editRow = undefined;
}
</script>
</head>
<body>
	<div class="easyui-layout" data-options="fit : true,border : false">
		<div data-options="region:'center',border:false">
			    <table id="dataGrid">			       
		        </table>
		</div>
	</div>	
		<div id="toolbar" style="display: none;">
			<a onclick="addNews();" href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true">新增</a>
			<a onclick="editNews();" href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-edit',plain:true">修改</a>
			<a onclick="endEdit();" href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-save',plain:true">提交修改</a>
			<a onclick="cancelEdit();" href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-redo',plain:true">撤销修改</a>
			<a onclick="deleteNews();" href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true">删除</a>
	</div>				  
</body>
</html>