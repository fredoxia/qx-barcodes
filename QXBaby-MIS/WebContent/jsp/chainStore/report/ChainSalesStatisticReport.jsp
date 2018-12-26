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
	parent.$.messager.progress('close'); 
	
	var params= $.serializeObject($('#preGenReportForm'));
	
	$('#dataGrid').treegrid({
		url : 'chainReportJSON!getSalesStatisticReptEles',
		idField: 'id',
		queryParams: params,
		treeField : 'name',
		rownumbers: true,
		lines : true,
		onBeforeExpand : function(node) {
			$("#parentId").attr("value", node.parentId);
		    $("#yearId").attr("value", node.yearId);
			$("#quarterId").attr("value", node.quarterId);
			$("#brandId").attr("value", node.brandId);
			var params = $('#preGenReportForm').serialize();
			$('#dataGrid').treegrid('options').url = 'chainReportJSON!getSalesStatisticReptEles?' + params;
		},		
		columns : [ [
					{field:'name', width:250,title:'销售列表'},
					{field:'salesQ', width:80,title:'销售数量 A'},
					{field:'returnQ', width:80,title:'退货数量 B'},
					{field:'netQ', width:100,title:'净销售数量 A-B'},
					{field:'freeQ', width:80,title:'赠品数量'},
					{field:'salesPrice', width:80,title:'销售额 C'},
					{field:'returnPrice', width:80,title:'退货额 D'},
					{field:'netPrice', width:100,title:'净销售额 C-D'},
					{field:'salesDiscount', width:70,title:'销售折扣'},
					{field:'netCost', width:100,title:'净销售成本 E',
						formatter: function (value, row, index){
							if (row.seeCost == true) 
								return (row.netCost).toFixed(2);
							else 
								return "-";
						}},
					{field:'freeCost', width:100,title:'赠品成本 F',
						formatter: function (value, row, index){
							if (row.seeCost == true) 
								return (row.freeCost).toFixed(2);
							else 
								return "-";
						}
					},
					{field:'netProfit', width:110,title:'商品利润 C-D-E-F',
						formatter: function (value, row, index){
							if (row.seeCost == true) 
								return (row.netProfit).toFixed(2);
							else 
								return "-";
						}
					}
			     ]],
		toolbar : '#toolbar',
	});
});

function refresh(){
	$("#parentId").attr("value", 0);
    $("#yearId").attr("value", 0);
	$("#quarterId").attr("value", 0);
	$("#brandId").attr("value", 0);
    document.preGenReportForm.action="chainReportJSPAction!generateSalesStatisticReport";
    document.preGenReportForm.submit();
}
function back(){
    document.preGenReportForm.action="chainReportJSPAction!preSalesStatisticReport";
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
            <s:hidden name="formBean.saler.user_id" id="salerId"/>
            <input type="hidden" name="formBean.year.year_ID" id="yearId" value="0"/>
            <input type="hidden" name="formBean.quarter.quarter_ID" id="quarterId" value="0"/>
            <input type="hidden" name="formBean.brand.brand_ID" id="brandId" value="0"/>
        </s:form>
        </div>
		<div data-options="region:'center',border:false">
			    <table id="dataGrid" style="width:fit;height:fit">			       
		        </table>
		        <div id="toolbar" style="display: none;">
		             <a onclick="back();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-back'">退回上页</a>
		             <a onclick="refresh();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-reload'">刷新库存</a>
					
	             </div>
		</div>
	</div>					  
</body>
</html>