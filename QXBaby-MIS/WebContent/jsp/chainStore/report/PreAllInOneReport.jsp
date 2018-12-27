<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib prefix="s" uri="/struts-tags" %>
<%@ page import="com.onlineMIS.common.Common_util,java.util.Date,java.text.SimpleDateFormat" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>朴与素连锁店管理信息系统</title>
<%@ include file="../../common/Style.jsp"%>

<SCRIPT src="<%=request.getContextPath()%>/conf_files/js/ChainReport.js" type=text/javascript></SCRIPT>
<script>
$(document).ready(function(){
	parent.$.messager.progress('close'); 
	});

function changeChainStore(chainId){
}


function generateReport(){
	if (validateReportForm()){
		$("#quarter").attr("disabled",false);
	    document.preGenReportForm.action="chainReportJSPAction!generateAllInOneReport";
	    document.preGenReportForm.submit();
	}
}
function validateReportForm(){
	return true;
}
</script>
</head>
<body>
   <s:form id="preGenReportForm" name="preGenReportForm" action="/actionChain/chainReportJSPAction!generateAllInOneReport" theme="simple" method="POST"> 
     <s:hidden name="formBean.reportType" id="reportType"/>
     <table width="60%" align="center"  class="OuterTable">
	    <tr><td>
			 <table width="100%" border="0">
			    <tr>
			       <td height="50" colspan="7">
					   	<table width="100%" border="0" cellpadding="0" cellspacing="0">
					    <tr class="PBAOuterTableTitale">
				          <td height="43" colspan="3">连锁店货品综合报表</td>
				        </tr>
				        <tr class="InnerTableContent">
				          <td height="32" colspan="3"><s:actionerror cssStyle="color:red"/><s:actionmessage cssStyle="color:blue"/></td>
				        </tr>
					    <tr class="InnerTableContent">
					      <td width="134" height="32"><strong>连锁店</strong></td>
					      <td width="302"><%@ include file="../include/SearchChainStore.jsp"%><input type="hidden" name="formBean.indicator" id="indicator" value="1"/></td>
					      <td width="579"></td>
				        </tr>
					    <tr class="InnerTableContent">
					      <td height="32"><strong>查询日期</strong></td>
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