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
	
	loadTreeGrid();
});
function changeChainStore(chainId){}
function loadTreeGrid(){
	var params= $.serializeObject($('#preGenReportForm'));
	$('#dataGrid').treegrid({
		url : 'expenseChainJSON!expensReportChain',
		queryParams: params,
		border : false,
		checkOnSelect : false,
		selectOnCheck : false,
		singleSelect:true,
		nowrap : false,
		treeField : 'name',
		idField:'id',
		onBeforeExpand : function(node) {
			$("#expenseRptLevel").attr("value", node.expenseRptLevel);
			var params = $('#preGenReportForm').serialize();
			$('#dataGrid').treegrid('options').url = 'expenseChainJSON!expensReportChain?' + params;
		},	
		columns : [ [
					{field:'name', width:180,title:'费用类别'},
					{field:'amount', width:80,title:'汇总金额'}

			     ]]
	});
}
function genSalesReport(){
	$("#expenseRptLevel").attr("value", 0);
	
	loadTreeGrid();
}

</script>
</head>
<body>
	<div class="easyui-layout" data-options="fit : true,border : false">
		<div data-options="region:'north',border:false" style="height: 115px;">
		   <s:form id="preGenReportForm" name="preGenReportForm" action="" theme="simple" method="POST">  
		    <s:hidden name="formBean.expenseRptLevel" id="expenseRptLevel" value="0"/>
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
			           <a href="javascript:void(0)" class="easyui-linkbutton" onclick="genSalesReport();">费用汇总</a>
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