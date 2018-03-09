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
	
	var params = $.serializeObject($('#basicDataForm'));

	dataGrid = $('#dataGrid').datagrid({
		url : 'productJSONAction!getAllBrandPriceIncrease',
		queryParams: params,
		fit : true,
		border : false,
		pagination : false,	
		checkOnSelect : false,
		selectOnCheck : false,
		singleSelect:true,
		nowrap : false,
		rownumbers : true,

		columns : [ [ 
		      {
			field : 'year',
			title : '年份',
			width : 60
		    },{
				field : 'quarter',
				title : '季度',
				width : 60
			},{			    
			field : 'brand',
			title : '品牌',
			width : 150
		    }, {
			field : 'increaseString',
			title : '价格变化',
			width : 200
		    }, {			    
			field : 'action',
			title : '删除记录',
			width : 50,
			formatter : function(value, row, index) {
				var str = '';
				str += $.formatString('<a href="#" onclick="deleteBPI(\'{0}\',\'{1}\',\'{2}\');"><img border="0" src="{3}" title="编辑品牌"/></a>', row.yearId, row.quarterId,row.brandId,'<%=request.getContextPath()%>/conf_files/easyUI/themes/icons/text_1.png');
				return str;
			}
		}]],
		toolbar : '#toolbar',
	});

});

function deleteBPI(yearId, quarterId, brandId){
	var params = "formBean.bpi.year.year_ID=" +yearId;
	    params += "&formBean.bpi.quarter.quarter_ID=" + quarterId;
	    params += "&formBean.bpi.brand.brand_ID=" + brandId;
	    $.post("productJSONAction!deleteBrandPriceIncrease",params, deleteBrandPriceIncrease,"json");	
}
function deleteBrandPriceIncrease(response){

	var returnCode = response.returnCode;
	if (returnCode != SUCCESS)
		alert(response.message);
	else {
		$("#dataGrid").datagrid('reload');
	}		
}

function addBPI(){

	//$.modalDialog.opener_dataGrid = dataGrid;
	
	$.modalDialog({
		title : "添加品牌提价",
		width : 540,
		height : 380,
		modal : false,
		draggable:false,
		href : 'productJSPAction!preEditBrandPriceIncrease',
		buttons : [ {
			text : '保存信息',
			handler : function() {

				if ($("#yearId").val() == "" || $("#yearId").val() == "0")
					alert("年份不能为空");
				else if ($("#quarterId").val() == "" || $("#quarterId").val() == "0")
					alert("季度不能为空");
				else if ($("#brand_ID").val() == "" || $("#brand_ID").val() == "0")
					alert("品牌不能为空");
				else {
				    var params=$("#BPIGenForm").serialize();

				   $.post('productJSONAction!saveBrandPriceIncrease',
						    params,
						    function(data) {
								$.messager.progress('close');
								if (data.returnCode == SUCCESS) {
									$("#dataGrid").datagrid('reload');
									$.modalDialog.handler.dialog('close');
							    } else 
								    alert(data.message);
					        },"json");
				}
			}
		} , {
			text : '放弃修改',
			handler : function() {
					$.modalDialog.handler.dialog('close');
			}
		}]
	});
}
function refresh(){
	$('#dataGrid').datagrid('load', $.serializeObject($('#basicDataForm')));
}
function clearAllData(){
	
}
</script>
</head>
<body>
	<div class="easyui-layout" border="false" style="width:600px;height:560px">
		<div data-options="region:'center',border:false">
				<table id="dataGrid">			       
		        </table>
	
			<div id="toolbar" style="display: none;">
		             <a onclick="addBPI();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-add'">添加调价</a>
                     
	        </div>
		</div>
	</div>
</body>
</html>