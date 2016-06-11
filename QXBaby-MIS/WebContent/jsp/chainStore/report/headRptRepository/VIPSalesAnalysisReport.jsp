<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib prefix="s" uri="/struts-tags" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>千禧宝贝连锁店管理信息系统</title>
<%@ include file="../../../common/Style.jsp"%>

<script>
$(document).ready(function(){
	parent.$.messager.progress('close'); 
});
function getReport(){
    document.preGenReportForm.action="chainReportJSPAction!getVIPSalesAnalysisRpt";
    document.preGenReportForm.submit();
}
</script>
</head>
<body>
	<s:form id="preGenReportForm" name="preGenReportForm" action="" theme="simple" method="POST">  
			<table width="100%" border="0">
				<tr class="InnerTableContent">
				  <td height="25">&nbsp;</td>
			      <td colspan="4">VIP销售占比报表的最早日期是2016-6-6</td>
			    </tr>			
				<tr class="InnerTableContent">
				  <td height="15">&nbsp;</td>
			      <td colspan="4"><div class="errorAndmes"><s:actionerror cssStyle="color:red"/><s:actionmessage cssStyle="color:blue"/></div></td>
			    </tr>
			    <tr class="InnerTableContent">
			      <td width="45" height="35">&nbsp;</td>
			      <td width="76"><strong>报表日期</strong></td>
			      <td width="284" colspan="3">
			        <s:textfield id="rptDate" name="formBean.startDate" cssClass="easyui-datebox"  data-options="width:100,editable:false"/>
			      </td>
			    </tr>
				<tr class="InnerTableContent">
			      <td height="35">&nbsp;</td>
			      <td>&nbsp;</td>
			      <td colspan="2"><input type="button" value="获取报表" onclick="getReport();"/>&nbsp;
			      </td>
			      <td>&nbsp;</td>
			    </tr>
			</table>
			</s:form>
				  

</body>
</html>