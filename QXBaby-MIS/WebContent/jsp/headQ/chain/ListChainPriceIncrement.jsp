<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-Strict.dtd">
<%@taglib prefix="s" uri="/struts-tags" %>
<%@ page import="com.onlineMIS.common.Common_util,java.util.Date,java.text.SimpleDateFormat" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<meta http-equiv="X-UA-Compatible" content="IE=8" />
<title>千禧宝贝连锁店管理信息系统</title>
<%@ include file="../../common/Style.jsp"%>
<script>
var dataGrid ;
$(document).ready(function(){
	parent.$.messager.progress('close'); 

	dataGrid = $('#dataGrid').datagrid({
		url : 'chainSMgmtJSON!getAllChainPriceIncre',
		fit : true,
		border : false,
		pagination : true,
		pageSize : 15,
		pageList : [ 15, 30],		
		checkOnSelect : false,
		selectOnCheck : false,
		singleSelect:true,
		nowrap : false,
		rownumbers : true,
		columns : [ [ {
			field : 'increment',
			title : '零售价涨幅',
			width : 100
		    },{
				field : 'description',
				title : '描述',
				width : 150
			},{			    
			field : 'action',
			title : '编辑',
			width : 50,
			formatter : function(value, row, index) {
				var str = '';
				str += $.formatString('<a href="#" onclick="EditChainPriceIncre(\'{0}\');"><img border="0" src="{1}" title="编辑连锁店涨价"/></a>', row.id, '<%=request.getContextPath()%>/conf_files/easyUI/themes/icons/text_1.png');
				return str;
			}
		}]],
		toolbar : '#toolbar',
	});

});

function EditChainPriceIncre(id){
	var params = "";
	if (id != 0)
	   params += "&formBean.priceIncrement.id =" + id;

	$.modalDialog.opener_dataGrid = dataGrid;
	
	$.modalDialog({
		title : "添加/更新连锁店价格涨幅",
		width : 540,
		height : 380,
		modal : false,
		draggable:false,
		href : 'chainSMgmtJSP!preAddChainPriceIncre?' + params,
	});

	//window.open ('chainSMgmtJSP!preAddChainPriceIncre?' + params,"",'height=800, width=1000, toolbar=no,scrollbars=yes, resizable=yes,  menubar=no, location=no, status=no');
}
function refresh(){
	$('#dataGrid').datagrid('load');
}
</script>
</head>
<body>
	<div class="easyui-layout" border="false" style="width:500px;height:560px">
		<div data-options="region:'center',border:false">
				<table id="dataGrid">			       
		        </table>
	
			<div id="toolbar" style="display: none;">
		             <a onclick="EditChainPriceIncre(0);" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-add'">添加</a>
                     <a onclick="refresh();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-reload'">刷新</a>
	        </div>
		</div>
	</div>
</body>
</html>