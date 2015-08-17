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
		url : 'chainReportJSON!generateVIPConsumpReport',
		queryParams: params,
		fit : true,
		fitColumns : true,
		pagination : true,
		pageSize : 15,
		pageList : [ 15, 30],
		border : false,
		checkOnSelect : false,
		selectOnCheck : false,
		singleSelect:true,
		showFooter:true,
		rownumbers:true,
		nowrap : false,
		sortName : '',
		sortOrder : 'desc',
		columns : [ [
					{field:'chainName', width:100,title:'所属连锁店',fixed:true,
						formatter: function (value, row, index){
							return row.vip.issueChainStore.chain_name;
						}},	
					{field:'vipNo', width:80,title:'VIP卡号',fixed:true,
						formatter: function (value, row, index){
							return row.vip.vipCardNo;
						}},						
					{field:'vipName', width:60,title:'VIP名字',fixed:true,
						formatter: function (value, row, index){
							return row.vip.customerName;
						}},	
						
					{field:'saleQ', width:50,title:'消费数量'},
					{field:'returnQ', width:50,title:'退货量'},
					{field:'netQ', width:50,title:'净消费量'},
					{field:'salesAmt', width:65,title:'消费额',
						formatter: function (value, row, index){
							return (row.salesAmt).toFixed(1);
						}
					},
					{field:'returnAmt', width:65,title:'退货额',
						formatter: function (value, row, index){
							return (row.returnAmt).toFixed(1);
						}},
	
					{field:'netAmt', width:70,title:'净消费额',
						formatter: function (value, row, index){
							return (row.netAmt).toFixed(1) ;
						}},	
					{field:'receiveAmt', width:58,title:'付刷卡+现金',
							formatter: function (value, row, index){
								return (row.receiveAmt).toFixed(1);
						}},	
					{field:'discountAmt', width:50,title:'优惠',
							formatter: function (value, row, index){
								return (row.discountAmt).toFixed(1);
							}},	
					{field:'couponSum', width:50,title:'代金券',
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
			      <td width="76"><strong>日期</strong></td>
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