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
		url : 'inventoryFlowJSONAction!getInventoryFlowEles',
		idField: 'id',
		queryParams: params,
		treeField : 'name',
		onBeforeExpand : function(node) {
			$("#parentId").attr("value", node.parentId);
			$("#chainId").attr("value", node.chainId);
		    $("#yearId").attr("value", node.yearId);
			$("#quarterId").attr("value", node.quarterId);
			$("#brandId").attr("value", node.brandId);
			var params = $('#preGenReportForm').serialize();
			$('#dataGrid').treegrid('options').url = 'inventoryFlowJSONAction!getInventoryFlowEles?' + params;
		},
		columns : [ [
					{field:'name', width:250,title:'库存列表'},
					{field:'inventory', width:170,title:'库存数量'},
					{field:'wholeSales', width:175,title:'库存成本金额',
						formatter: function (value, row, index){
							if (row.seeCost == true) 
								return (row.wholeSales).toFixed(2);
							else 
								return "-";
						}
					},
					{field:'retailSales', width:175,title:'估算销售金额',
						formatter: function (value, row, index){
							return (row.retailSales).toFixed(2);
						}
					}
			     ]],
		toolbar : '#toolbar',
	});
});

function refresh(){
	location.reload();
}
function back(){
    document.preGenReportForm.action="inventoryFlowJSPAction!preGetCurrentInventory";
    document.preGenReportForm.submit();
}
function downloadInventory(){
	var node = $('#dataGrid').treegrid('getSelected');

	if (node == null){
		$.messager.alert('错误', '请先选中一行再继续操作', 'error');
	} else {
		
		$("#chainId").attr("value", node.chainId);
	    $("#yearId").attr("value", node.yearId);
		$("#quarterId").attr("value", node.quarterId);
		$("#brandId").attr("value", node.brandId);
        document.preGenReportForm.action="inventoryFlowJSPAction!generateChainInventoryExcelReport";
        document.preGenReportForm.submit();
	}

}
function deleteInventory(){
	var node = $('#dataGrid').treegrid('getSelected');

	if (node == null){
		$.messager.alert('错误', '请先选中一行再继续操作', 'error');
	} else {
		$.messager.prompt("密码验证","一旦确认,选中库存将要清空:", function(password){
			if (password == "vj7683c688"){
				$.messager.progress({
					title : '提示',
					text : '数据处理中，请稍后....'
				});
				
				$("#chainId").attr("value", node.chainId);
			    $("#yearId").attr("value", node.yearId);
				$("#quarterId").attr("value", node.quarterId);
				$("#brandId").attr("value", node.brandId);
		        
				var params = $('#preGenReportForm').serialize();
				$.post('inventoryFlowJSONAction!deleteInventory', params, 
						function(result) {
					  
							if (result.returnCode == SUCCESS) {
								$.messager.progress('close'); 
								$.messager.alert('信息', result.message, 'info');
								setTimeout('refresh();', 2000);
								
							} else {
								$.messager.progress('close'); 
								$.messager.alert('失败警告', result.message, 'error');
							}
						}, 'JSON');
			} else {
				alert("密码错误");
			}	   
		});

	}
	
}
</script>
</head>
<body>
	<div class="easyui-layout" data-options="fit : true,border : false">
	    <div data-options="region:'north',border:false" style="height: fit">
        <s:form id="preGenReportForm" name="preGenReportForm" action="" theme="simple" method="POST">  
            <s:hidden name="formBean.parentId" id="parentId"/>
            <s:hidden name="formBean.chainId" id="chainId"/>
            <input type="hidden" name="formBean.yearId" id="yearId" value="0"/>
            <input type="hidden" name="formBean.quarterId" id="quarterId" value="0"/>
            <input type="hidden" name="formBean.brandId" id="brandId" value="0"/>
        </s:form>
        </div>
		<div data-options="region:'center',border:false">
			    <table id="dataGrid" style="width:780px;height:fit">			       
		        </table>
		        <div id="toolbar" style="display: none;">
		             <a onclick="back();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-back'">退回上页</a>
		             <a onclick="refresh();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-reload'">刷新库存</a>
		             <a onclick="downloadInventory();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-save'">下载库存</a>
		             <s:if test="#session.LOGIN_CHAIN_USER.containFunction('inventoryFlowJSONAction!deleteInventory')">
		                <a onclick="deleteInventory();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-remove'">删除库存</a>
		             </s:if>
	             </div>
		</div>
	</div>					  
</body>
</html>