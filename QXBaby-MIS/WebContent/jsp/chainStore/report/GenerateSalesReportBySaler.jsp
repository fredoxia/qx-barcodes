<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib prefix="s" uri="/struts-tags" %>
<%@ page import="com.onlineMIS.common.Common_util,java.util.Date,java.text.SimpleDateFormat" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>千禧宝贝连锁店管理信息系统</title>
<%@ include file="../../common/Style.jsp"%>
<SCRIPT src="<%=request.getContextPath()%>/conf_files/js/ChainReport.js" type=text/javascript></SCRIPT>
<script>
var baseurl = "<%=request.getContextPath()%>";
$(document).ready(function(){
	parent.$.messager.progress('close'); 
	
	var params= $.serializeObject($('#preGenReportForm'));
	
	$('#dataGrid').datagrid({
		url : 'chainReportJSON!generateSalesReportBySaler',
		queryParams: params,
		fit : true,
		fitColumns : true,
		pagination : true,
		pageSize : 15,
		pageList : [ 15, 30],
		border : false,
		checkOnSelect : false,
		selectOnCheck : false,
		sortName : 'receiveAmtSum',
		sortOrder : 'desc',
		singleSelect:true,
		showFooter:true,
		rownumbers:true,
		nowrap : false,
		columns : [ [
					{field:'salerName', width:50,title:'销售人员',fixed:true,
							formatter: function (value, row, index){
								return row.user.name;
					}},	
					{field:'chainName', width:100,title:'连锁店',fixed:true,
						formatter: function (value, row, index){
							return row.chainStore.chain_name;
						}},
					
					{field:'saleQuantitySum', width:50,title:'销售量',sortable:true,order:'desc'},
					{field:'returnQuantitySum', width:50,title:'退货量',sortable:true,order:'desc'},
					{field:'netQuantitySum', width:50,title:'净销售',sortable:true,order:'desc'},
					{field:'freeQantitySum', width:40,title:'赠品'},
					{field:'salesAmtSum', width:65,title:'销售额',sortable:true,order:'desc',
						formatter: function (value, row, index){
							return (row.salesAmtSum).toFixed(0);
						}
					},
					{field:'returnAmtSum', width:65,title:'退货额',sortable:true,order:'desc',
						formatter: function (value, row, index){
							return (row.returnAmtSum).toFixed(0);
						}},
					{field:'netAmtSum', width:65,title:'净销售',sortable:true,order:'desc',
						formatter: function (value, row, index){
							return (row.netAmtSum).toFixed(0);

						}},
					{field:'receiveAmtSum', width:55,title:'刷卡+现金等',sortable:true,order:'desc',
							formatter: function (value, row, index){
								return (row.receiveAmtSum).toFixed(0);
						}},		
						{field:'vipPrepaidAmt', width:57,title:'预存消费',
							formatter: function (value, row, index){
								return (row.vipPrepaidAmt).toFixed(0);
						}},							
					{field:'netSaleCostSum', width:65,title:'净售成本',
						formatter: function (value, row, index){
							return (row.netSaleCostSum).toFixed(0);
						}},
					{field:'freeCostSum', width:60,title:'赠品成本',
						formatter: function (value, row, index){
							return (row.freeCostSum).toFixed(0);
						}},
					{field:'discountSum', width:50,title:'优惠',
							formatter: function (value, row, index){
								return (row.discountSum).toFixed(1);
							}},						
					{field:'netProfit', width:65,title:'净利润',sortable:true,order:'desc',
						formatter: function (value, row, index){
							return (row.netProfit).toFixed(0);
						}},
					{field:'salesDiscountAmtSum', width:50,title:'销售折扣',
						formatter: function (value, row, index){
							return (row.salesDiscountAmtSum).toFixed(0);
						}},
					{field:'couponSum', width:40,title:'代金券',
						formatter: function (value, row, index){
							return (row.couponSum).toFixed(0);
						}}
			     ]]
	});
});
function changeChainStore(chainId){}
function genSalesReport(){
	var params = $.serializeObject($('#preGenReportForm')); 
	$('#dataGrid').datagrid('load',params); 
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
			      <td colspan="3"><%@ include file="../include/SearchChainStore.jsp"%><input type="hidden" id="indicator" name="formBean.indicator" value="-1"/></td>
			    </tr>
                <tr class="InnerTableContent">
			      <td height="25">&nbsp;</td>
			      <td>&nbsp;</td>
			      <td colspan="2"><input type="button" value="生成报表" onclick="genSalesReport();"/>&nbsp;
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