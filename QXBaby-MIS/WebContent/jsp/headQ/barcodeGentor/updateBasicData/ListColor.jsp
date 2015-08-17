<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-Strict.dtd">
<%@taglib prefix="s" uri="/struts-tags" %>
<%@ page import="com.onlineMIS.common.Common_util,java.util.Date,java.text.SimpleDateFormat" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<meta http-equiv="X-UA-Compatible" content="IE=8" />
<title>千禧宝贝连锁店管理信息系统</title>
<%@ include file="../../../common/Style.jsp"%>
<script>
var dataGrid ;
$(document).ready(function(){
	parent.$.messager.progress('close'); 
	
	var params = $.serializeObject($('#basicDataForm'));

	dataGrid = $('#dataGrid').datagrid({
		url : 'basicDataJSON!getBasicData',
		queryParams: params,
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
		sortName : 'code',
		sortOrder : 'asc',
		columns : [ [ {
			field : 'code',
			title : '颜色拼音',
			width : 100,
			sortable:true,
			order:'desc',
		    },{
				field : 'name',
				title : '品牌名字',
				width : 150
			},{			    
			field : 'action',
			title : '编辑',
			width : 50,
			formatter : function(value, row, index) {
				var str = '';
				str += $.formatString('<a href="#" onclick=""><img border="0" src="{1}" title="编辑品牌"/></a>', row.colorId, '<%=request.getContextPath()%>/conf_files/easyUI/themes/icons/text_1.png');
				return str;
			}
		}]],
		toolbar : '#toolbar',
	});

});

function EditColor(colorId){
	var params = "formBean.basicData=color";
	if (colorId != 0)
	   params += "&formBean.basicDataId =" + colorId;

	$.modalDialog.opener_dataGrid = dataGrid;
	
	$.modalDialog({
		title : "添加/更新品牌",
		width : 540,
		height : 380,
		modal : false,
		draggable:false,
		href : 'basicData!preAddBasicData?' + params,
		
	});
}
function refresh(){
	$('#dataGrid').datagrid('load', $.serializeObject($('#basicDataForm')));
}
function synColor(){
    $.post("basicDataJSON!synchnizeColor","", backProcessS,"json");
}
function backProcessS(data){
	var response = data.response;
	var returnCode = response.returnCode;
	if (returnCode == SUCCESS){
        alert("同步颜色成功");
        refresh();
	} else 
		alert(response.message);
}
</script>
</head>
<body>
	<div class="easyui-layout" border="false" style="width:500px;height:560px">
		<div data-options="region:'north',border:false" style="height: 40px;">
			<%@ include file="ChooseBasicData.jsp"%>
		</div>
		<div data-options="region:'center',border:false">
				<table id="dataGrid">			       
		        </table>
	
			<div id="toolbar" style="display: none;">
		             <a onclick="synColor();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-syn_data'">同步精算颜色</a>
                     <a onclick="refresh();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-reload'">刷新</a>
	        </div>
		</div>
	</div>
</body>
</html>