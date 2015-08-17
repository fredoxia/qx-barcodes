<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib prefix="s" uri="/struts-tags" %>
<%@ page import="com.onlineMIS.common.Common_util,java.util.Date,java.text.SimpleDateFormat" %>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>千禧宝贝连锁店管理信息系统</title>
<%@ include file="../../common/Style.jsp"%>
<script src="<%=request.getContextPath()%>/conf_files/js/highChart/js/highcharts.js"></script>
<script src="<%=request.getContextPath()%>/conf_files/js/highChart/js/modules/exporting.js"></script>
<script src="<%=request.getContextPath()%>/conf_files/js/drawHighChartSales.js"></script>
<script>
$(document).ready(function(){
	parent.$.messager.progress('close'); 
});
function validateForm(){
	var earliestDate = new Date("2013/7/28");
	var today = new Date();
    var fromDate = $("#startDate").val();
    var toDate = $("#endDate").val();

    var start=new Date(fromDate.replace("-", "/").replace("-", "/"));  
    var end=new Date(toDate.replace("-", "/").replace("-", "/"));  

    if(end<start){  
        alert("日期错误: 前面的日期>后面日期");
        return false;  
    } else if (start < earliestDate){
        alert("日期错误: 系统最早的数据到2013-7-28");
        $("#startDate").attr("value","2013-7-28");
        return false;  
    } else if (end > today)
    	$("#endDate").attr("value",formatDate(today, "YYYY-MM-DD"));
    return true;  
}
function genDailySalesReport(){
	var chainId = $("#chainStore").val();
	if (validateForm()) {
		$.messager.progress({
			text : '数据处理中，请稍后....'
		});
		var startDate = $("#startDate").combo("getValue");
		var endDate = $("#endDate").combo("getValue");
		var param ="formBean.chainStore.chain_id=" +chainId + "&formBean.startDate="+ startDate +"&formBean.endDate=" + endDate;
	
		$.post("<%=request.getContextPath()%>/actionChain/chainDailySalesJSON!genDailySalesReport",param, backProcessGenDailySalesReport,"json");
	}
}
function backProcessGenDailySalesReport(data){
	var response = data.response;
	var returnCode = response.returnCode;
	if (returnCode != SUCCESS){
		alert("获取信息失败 : " + response.message);
	} else {
		var reportVO = response.returnValue;
	       
		drawHighChartDailySales("container", reportVO);
		
		$('#win').window('open');
	}
	$.messager.progress('close'); 
}
</script>
</head>
<body>
    <s:form id="invenOrderSearchForm" name="invenOrderSearchForm" action="/actionChain/chainDailySalesAction!genDailySalesLineChart" theme="simple" method="POST" target="连锁店销售折线图" onsubmit="return validateForm();"> 
      <table width="90%" align="center"  class="OuterTable">
	    <tr><td>
			    <div class="errorAndmes"><s:actionerror cssStyle="color:red"/><s:actionmessage cssStyle="color:blue"/></div>
			   		<table width="100%" border="0">
					    <tr class="PBAOuterTableTitale">
				          <td height="35" colspan="3">生成连锁店日销售</td>
					    </tr>
					   	<tr class="InnerTableContent">
				          <td height="35" colspan="3">通过折线图你可以比较你店铺和连锁店平均销售情况。找到你的差距。</td>
					    </tr>
					    <tr class="InnerTableContent">
					      <td height="30" width="80">&nbsp;</td>
					      <td width="60"><strong>销售日期</strong></td>
					      <td >	
					      	<s:textfield id="startDate" name="formBean.startDate" cssClass="easyui-datebox" data-options="width:100,editable:false"/>			      
					      	&nbsp; 至&nbsp;
					      	<s:textfield id="endDate" name="formBean.endDate" cssClass="easyui-datebox" data-options="width:100,editable:false"/>
	                      </td>
					    </tr>
					    <tr class="InnerTableContent">
					      <td height="30">&nbsp;</td>
					      <td><strong>连锁店</strong></td>
					      <td><s:select id="chainStore" name="formBean.chainStore.chain_id"  list="uiBean.chainStores" listKey="chain_id" listValue="chain_name"/></td>
					    </tr>	
	                    <tr class="InnerTableContent">
					      <td height="35">&nbsp;</td>
					      <td>&nbsp;</td>
					      <td><input type="button" value="生成报表" onclick="genDailySalesReport();"/></td>
					    </tr>
					  </table>
					</td>
				</tr>
			</table>
		</s:form>
	    <div id="win" class="easyui-window" title="连锁店日销售趋势图"  style="width:840px;height:540px"  data-options="modal:true,closed:true,minimizable:false">  
	      <div id="container" style="min-width: 800px; height: 500px; margin: 0 auto;"></div>
	    </div>
</body>
</html>