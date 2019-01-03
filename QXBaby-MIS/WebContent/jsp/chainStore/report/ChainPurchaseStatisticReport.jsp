<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib prefix="s" uri="/struts-tags" %>
<%@ page import="com.onlineMIS.common.Common_util,java.util.Date,java.text.SimpleDateFormat" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>朴与素连锁店管理信息系统</title>
<%@ include file="../../common/Style.jsp"%>
<script>
var baseurl = "<%=request.getContextPath()%>";
$(document).ready(function(){
	var params= $.serializeObject($('#preGenReportForm'));
	$.messager.progress({
		title : '提示',
		text : '数据处理中，请稍后....'
	});
	
	$('#dataGrid').treegrid({
		url : 'chainReportJSON!getPurchaseStatisticReptEles',
		idField: 'id',
		queryParams: params,
		treeField : 'name',
		rownumbers: true,
		lines : true,
		onLoadSuccess : function(row, param){
			$.messager.progress('close'); 
		},
		onBeforeExpand : function(node) {
			$("#parentId").attr("value", node.parentId);
		    $("#yearId").attr("value", node.yearId);
			$("#quarterId").attr("value", node.quarterId);
			$("#brandId").attr("value", node.brandId);
			var params = $('#preGenReportForm').serialize();
			$('#dataGrid').treegrid('options').url = 'chainReportJSON!getPurchaseStatisticReptEles?' + params;
		},	
		frozenColumns :[[					
						{field:'name', width:220,title:'<s:property value="formBean.startDate"/> 到 <s:property value="formBean.endDate"/>'}
						]],		
		columns : [ [
					{field:'purchaseQuantity', width:80,title:'采购数量'},
					{field:'returnQuantity', width:80,title:'退货数量'},
					{field:'netQuantity', width:100,title:'净采购量'},
					{field:'avgPrice', width:100,title:'采购单价',
						formatter: function (value, row, index){
							if (row.seeCost == true) 
								return (row.avgPrice).toFixed(2);
							else 
								return "-";
						}},
					{field:'purchaseTotalAmt', width:100,title:'净采购金额',
						formatter: function (value, row, index){
							if (row.seeCost == true) 
								return (row.purchaseTotalAmt).toFixed(2);
							else 
								return "-";
						}
					}
			     ]],
		toolbar : '#toolbar',
	});
	
	parent.$.messager.progress('close'); 
	
});

function refresh(){
	$("#parentId").attr("value", 0);
    $("#yearId").attr("value", 0);
	$("#quarterId").attr("value", 0);
	$("#brandId").attr("value", 0);
    document.preGenReportForm.action="chainReportJSPAction!generatePurchaseStatisticReport";
    document.preGenReportForm.submit();
}
function back(){
    document.preGenReportForm.action="chainReportJSPAction!prePurchaseStatisticReport";
    document.preGenReportForm.submit();
}

</script>
</head>
<body>
	<div class="easyui-layout" data-options="fit : true,border : false">
	    <div data-options="region:'north',border:false" style="height: fit">
        <s:form id="preGenReportForm" name="preGenReportForm" action="" theme="simple" method="POST">  
            <s:hidden name="formBean.parentId" id="parentId"/>
            <s:hidden name="formBean.chainStore.chain_id" id="chainId"/>
            <s:hidden name="formBean.startDate" id="startDate"/>
            <s:hidden name="formBean.endDate" id="endDate"/>
            <input type="hidden" name="formBean.year.year_ID" id="yearId" value="0"/>
            <input type="hidden" name="formBean.quarter.quarter_ID" id="quarterId" value="0"/>
            <input type="hidden" name="formBean.brand.brand_ID" id="brandId" value="0"/>
        </s:form>
        </div>
		<div data-options="region:'center',border:false">
			    <table id="dataGrid">			       
		        </table>
		        <div id="toolbar" style="display: none;">
		             <a onclick="back();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-back'">退回上页</a>
		             <a onclick="refresh();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-reload'">刷新采购信息</a>
					
	             </div>
		</div>
	</div>					  
</body>
</html>