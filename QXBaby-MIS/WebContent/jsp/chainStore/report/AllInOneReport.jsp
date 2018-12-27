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
	
	$('#dataGrid').treegrid({
		url : 'chainReportJSON!getAllInOneReptEles',
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
			$('#dataGrid').treegrid('options').url = 'chainReportJSON!getAllInOneReptEles?' + params;
		},		
		columns : [ [
					{field:'name', width:280,title:'统计日期 <s:property value="formBean.startDate"/> 到 <s:property value="formBean.endDate"/>'},
					{field:'purchaseQ', width:80,title:'采购数量'},
					{field:'purchaseR', width:80,title:'采购退货量'},
					{field:'salesQ', width:80,title:'零售量'},
					{field:'salesR', width:80,title:'零售退货'},
					{field:'salesF', width:80,title:'赠品量'},
					{field:'currentInventory', width:80,title:'当前库存'},
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
    document.preGenReportForm.action="chainReportJSPAction!generateAllInOneReport";
    document.preGenReportForm.submit();
}
function back(){
    document.preGenReportForm.action="chainReportJSPAction!preAllInOneReport";
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
			    <table id="dataGrid" style="width:fit;height:650px">			       
		        </table>
		        <div id="toolbar" style="display: none;">
		             <a onclick="back();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-back'">退回上页</a>
		             <a onclick="refresh();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-reload'">刷新报表</a>
					
	             </div>
		</div>
	</div>					  
</body>
</html>