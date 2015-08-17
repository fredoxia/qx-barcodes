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
		url : 'chainReportJSON!generateSalesAnalysisReport',
		queryParams: params,
		fit : true,
		fitColumns : true,
		pagination : false,
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
					
					{field:'chainName', width:70,title:'连锁店',fixed:true,
						formatter: function (value, row, index){
							return row.chainStore.chain_name;
						}},
					
					{field:'lianDaiRatio', width:40,title:'连带率',
						formatter: function (value, row, index){
							return (row.lianDaiRatio).toFixed(3);
						}},
					{field:'largestSalesQ', width:40,title:'最大单'},
					{field:'keChiOrderRatio', width:50,title:'可耻单占比',
						formatter: function (value, row, index){
							return (row.keChiOrderRatio).toFixed(3);
						}},
					{field:'keDanAvg', width:40,title:'客单价',
						formatter: function (value, row, index){
							return (row.keDanAvg).toFixed(0);
						}}
			     ]],
		onLoadError: function(){
			alert("因为数据收集和计算等原因，普通用户只能在晚上九点以后使用此功能");
		}
	});
});

function changeChainStore(chainId){}
function genSalesAnalysisReport(){
	var params = $.serializeObject($('#preGenReportForm')); 
	$('#dataGrid').datagrid('load',params); 
}

function downloadSalesReportToExcel(){
	
    document.preGenReportForm.action="chainReportJSPAction!generateSalesReportToExcel";
    document.preGenReportForm.submit();
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
			      <td width="284" colspan="3">
			        <s:textfield id="startDate" name="formBean.startDate" cssClass="easyui-datebox"  data-options="width:100,editable:false"/>
			        &nbsp;至&nbsp;
			        <s:textfield id="endDate" name="formBean.endDate" cssClass="easyui-datebox"  data-options="width:100,editable:false"/>
			      </td>
			    </tr>
				<tr class="InnerTableContent">
			      <td height="30">&nbsp;</td>
			      <td><strong>连锁店</strong></td>
			      <td colspan="3"><%@ include file="../include/SearchChainStore.jsp"%></td>
			    </tr>
                <tr class="InnerTableContent">
			      <td height="25">&nbsp;</td>
			      <td>&nbsp;</td>
			      <td colspan="2"><input type="button" value="生成销售分析报表" onclick="genSalesAnalysisReport();"/>&nbsp;
			      <!--  <input type="button" value="生成Excel报表" onclick="downloadSalesReportToExcel();"/>-->
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

</body>
</html>