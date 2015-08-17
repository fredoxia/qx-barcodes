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
$(document).ready(function(){
	var yearId = $("#year").val();
	if (yearId <= 0)
	   $("#quarter").attr("disabled","true");
	$("#checkBrandBt").attr("disabled","true");
	parent.$.messager.progress('close'); 
	});
	
function changeYear(){
	var yearId = $("#year").val();
	var quarterDis = $("#quarter").attr("disabled");

	if (yearId > 0){
		if (quarterDis == undefined){
			$("#brandId").attr("value", -1);
			$("#brandName").attr("value","");
		} else {
			$("#quarter").attr("disabled", false);
		}
	} else {
		$("#brandId").attr("value", -1);
		$("#brandName").attr("value","");
		$("#quarter").attr("value", -1);
		$("#quarter").attr("disabled", true);
		$("#checkBrandBt").attr("disabled", true);
	}
}
function changeQuarter(){
	var quarterId = $("#quarter").val();
	var checkBrandBtDis = $("#checkBrandBt").attr("disabled");

	if (quarterId > 0){
		if (checkBrandBtDis == undefined){
			$("#brandId").attr("value", -1);
			$("#brandName").attr("value","");
		} else {
			$("#checkBrandBt").attr("disabled", false);
		}
	} else {
		$("#brandId").attr("value", -1);
		$("#brandName").attr("value","");
		$("#checkBrandBt").attr("disabled", true);
	}
}
function changeChainStore(chainId){
	var params = "formBean.chainStore.chain_id=" + chainId;
	$.post("<%=request.getContextPath()%>/actionChain/chainMgmtJSON!changeChainStore",params, backProcessChangeChainStore,"json");
}
function backProcessChangeChainStore(data){
	var users =  data.chainUsers;
	if (users != undefined){
		$("#chainSaler").empty();
		
		$("#chainSaler").append("<option value='<%=Common_util.ALL_RECORD%>'>---全部---</option>"); 
		
		for (var i = 0; i < users.length; i++)
			   $("#chainSaler").append("<option value='"+users[i].user_id+"'>"+users[i].name+"</option>"); 
	}
}

function generateReport(){
	if (validateReportForm()){
		$("#quarter").attr("disabled",false);
	    document.preGenReportForm.action="chainReportJSPAction!generateSalesStatisticReport";
	    document.preGenReportForm.submit();
	}
}
function validateReportForm(){
	return true;
}
</script>
</head>
<body>
   <s:form id="preGenReportForm" name="preGenReportForm" action="/actionChain/chainReportJSPAction!generateSalesStatisticReport" theme="simple" method="POST"> 
     <s:hidden name="formBean.reportType" id="reportType"/>
     <table width="60%" align="center"  class="OuterTable">
	    <tr><td>
			 <table width="100%" border="0">
			    <tr>
			       <td height="50" colspan="7">
					   	<table width="100%" border="0" cellpadding="0" cellspacing="0">
					    <tr class="PBAOuterTableTitale">
				          <td height="43" colspan="3">连锁店销售统计报表</td>
				        </tr>
				        <tr class="InnerTableContent">
				          <td height="32" colspan="3"><s:actionerror cssStyle="color:red"/><s:actionmessage cssStyle="color:blue"/></td>
				        </tr>
					    <tr class="InnerTableContent">
					      <td width="134" height="32"><strong>连锁店</strong></td>
					      <td width="302"><%@ include file="../include/SearchChainStore.jsp"%></td>
					      <td width="579"></td>
				        </tr>
					    <tr class="InnerTableContent">
					      <td width="134" height="32"><strong>经手人</strong></td>
					      <td width="302"><s:select id="chainSaler" name="formBean.saler.user_id"  list="uiBean.chainSalers" listKey="user_id" listValue="user_name" headerKey="-1" headerValue="---全选---"/> </td>
					      <td width="579"></td>
				        </tr>
					    <tr class="InnerTableContent">
					      <td height="32"><strong>销售查询日期</strong></td>
					      <td colspan="2">
					        <s:textfield id="startDate" name="formBean.startDate" cssClass="easyui-datebox"  data-options="width:100,editable:false"/>
					        &nbsp;至&nbsp;
					        <s:textfield id="endDate" name="formBean.endDate" cssClass="easyui-datebox"  data-options="width:100,editable:false"/>
	                      </td>
				        </tr>
						<tr class="InnerTableContent">
					      <td height="25"></td>
				        </tr>				        
						<tr class="InnerTableContent">
					      <td height="31"><strong>商品年份</strong></td>
					      <td><s:select id="year" name="formBean.year.year_ID"  list="uiBean.years" listKey="year_ID" listValue="year" headerKey="-1" headerValue="---全选---" onchange = "changeYear();"/> </td>
					      <td>统计所属某个年份的货品销售情况</td>
				        </tr>
					    <tr class="InnerTableContent">
					      <td height="31"><strong>商品季度</strong></td>
					      <td><s:select id="quarter" name="formBean.quarter.quarter_ID"  list="uiBean.quarters" listKey="quarter_ID" listValue="quarter_Name" headerKey="-1" headerValue="---全选---" onchange="changeQuarter();"/> </td>
					      <td>统计所属某个季度的货品销售情况</td>
				        </tr>
				        <tr class="InnerTableContent">
					      <td width="134" height="32"><strong>商品品牌</strong></td>
					      <td width="302"><%@ include file="../include/SearchBrand.jsp"%></td>
					      <td width="579">统计所属某个品牌的货品销售情况</td>
				        </tr>
	                    <tr class="InnerTableContent">
					      <td height="34">&nbsp;</td>
					      <td colspan="2"><input type="button" value="查询报表" onclick="generateReport();"/></td>
				        </tr>
					</table></td>
			    </tr>
			  </table>
	   </td></tr>

	 </table>
	 </s:form>
</body>
</html>