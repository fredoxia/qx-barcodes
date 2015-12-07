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
	alert("go");
}
</script>
</head>
<body>
   <s:form id="preGenReportForm" name="preGenReportForm" action="/actionChain/chainReportJSPAction!generateAllInOneReport" theme="simple" method="POST"> 
   </s:form>
   <s:form id="tempForm" name="tempForm" action="" theme="simple" method="POST"> 
     <table width="90%" align="center"  class="OuterTable">
    	<tr><td>
	     	<table width="100%" border="1" cellpadding="0" cellspacing="0">
			    <tr class="InnerTableContent">
		          <td height="32" width="200"><strong>报表种类</strong></td>
			      <td width="150"><strong>查询时间段</strong></td>
			      <td></td>
		        </tr>
		
			    <tr class="InnerTableContent">
			      <td height="32" >每周当季货品销售分析报表</td>
			      <td ><s:select id="currentSalesDate" name="formBean.chainStore.chain_id"  list="uiBean.currentSalesDates" listKey="rptDate" listValue="rptDes" />
			      </td>
			      <td><input type="button" value="下载报表" onclick="generateCurrentSalesReport();"/></td>
		        </tr>
			</table>
		</td></tr>
	</table>
    </s:form>
</body>
</html>