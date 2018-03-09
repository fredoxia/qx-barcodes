<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib prefix="s" uri="/struts-tags" %>
<%@ page import="com.onlineMIS.common.Common_util,java.util.Date,java.text.SimpleDateFormat" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>千禧宝贝连锁店管理信息系统</title>
<%@ include file="../../../common/Style.jsp"%>

<script>
$(document).ready(function(){
	parent.$.messager.progress('close'); 
	});
function generateCurrentSalesReport(){
	var rptId = $("#rptId").val();
	$("#id").attr("value",rptId);
    document.preGenReportForm.submit();
}
function generateAccumulatedSalesReport(){
	var rptId = $("#rptId2").val();
	$("#id").attr("value",rptId);
    document.preGenReportForm.submit();
}

function generateChainTransferAcctFlowReport(){
	var rptId = $("#rptId3").val();
	$("#id").attr("value",rptId);
    document.preGenReportForm.submit();
}
function triggerRptBkprocess(data){
	var returnCode = data.returnCode;
	var returnMsg = data.message;
	if (returnCode == SUCCESS){		   
		alert("运行报表成功");
		window.location.reload();
	} else {
		$.messager.progress('close'); 
    	alert("错误 : " + returnMsg);
 	}
}

function triggerBatchReport(rptType){
	var myDate = new Date();
	var hr = myDate.getHours();   
	
	if (hr >= 8 && hr <= 20){
    	alert("此功能只能在早上八点前或者晚上九点之后才能使用");
    	return false;
	} else {
		$.messager.prompt("输入","请输入运行密码，才能运行", function(passwords){
	        if (passwords == "vj7683c6"){
	          var params = "formBean.reportType=" + rptType;
	  		  $.messager.progress({
					title : '提示',
					text : '报表运行中,请勿关闭窗口.需要等待大约20分钟....'
				   });
			  $.post("<%=request.getContextPath()%>/actionChain/chainReportJSON!triggerCurrentAnalysis",params, triggerRptBkprocess,"json");
		   } else {
	        	alert("密码错误");
	        	return false;
	        }
		});
	}
}
</script>
</head>
<body>
   <s:form id="preGenReportForm" name="preGenReportForm" action="/actionChain/chainReportJSPAction!generateChainRptRepository" theme="simple" method="POST"> 
   		<s:hidden id="id" name="formBean.rptRepository.id"/>
   </s:form>
   <s:form id="tempForm" name="tempForm" action="" theme="simple" method="POST"> 
     <table width="90%" align="center"  class="OuterTable">
    	<tr><td>
	     	<table width="100%" border="1" cellpadding="0" cellspacing="0">
			    <tr class="InnerTableContent">
		          <td height="32" width="200"><strong>报表种类</strong></td>
			      <td width="180"><strong>报表时间段</strong></td>
			      <td></td>
		        </tr>
		
			    <tr class="InnerTableContent">
			      <td height="32" >每周当季货品分析报表</td>
			      <td ><s:select id="rptId" name="formBean.id"  list="uiBean.currentSalesDates" listKey="id" listValue="rptDes" />
			      </td>
			      <td><input type="button" value="下载报表" onclick="generateCurrentSalesReport();"/>
			          <input type="button" value="运行报表" onclick="triggerBatchReport(1);"/>
			      </td>
		        </tr>
			    <tr class="InnerTableContent">
			      <td height="32" >每周当季货品累计销售分析报表</td>
			      <td ><s:select id="rptId2" name="formBean.id"  list="uiBean.accumulatedSalesDates" listKey="id" listValue="rptDes" />
			      </td>
			      <td><input type="button" value="下载报表" onclick="generateAccumulatedSalesReport();"/>
			      	  <input type="button" value="运行报表" onclick="triggerBatchReport(2);"/>
			      </td>
		        </tr>	
			    <tr class="InnerTableContent">
			      <td height="32" >每两周调货账目流水</td>
			      <td ><s:select id="rptId3" name="formBean.id"  list="uiBean.chainTransferAcctFlowDates" listKey="id" listValue="rptDes" />
			      </td>
			      <td><input type="button" value="下载报表" onclick="generateChainTransferAcctFlowReport();"/>
			      	  
			      </td>
		        </tr>		        	        
			</table>
		</td></tr>
	</table>
    </s:form>
</body>
</html>