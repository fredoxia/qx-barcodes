<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib prefix="s" uri="/struts-tags" %>
<%@ page import="com.onlineMIS.common.Common_util,java.util.Date,java.text.SimpleDateFormat" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>千禧宝贝连锁店管理信息系统</title>
<%@ include file="../../common/Style.jsp"%>

<script src="<%=request.getContextPath()%>/conf_files/js/highChart/highcharts.js"></script>
<script src="<%=request.getContextPath()%>/conf_files/js/highChart/modules/exporting.js"></script>

<script>
$(document).ready(function(){
	parent.$.messager.progress('close'); 
});


</script>
</head>
<body>
   <s:form id="preGenReportForm" action="" theme="simple" method="POST"> 
     <table width="90%" align="center"  class="OuterTable">
	    <tr><td>
			 <table width="100%" border="0">
			    <tr>
			       <td height="50" colspan="7">
					   	<table width="100%" border="0">
					    <tr class="InnerTableContent">
					      <td width="45" height="35">&nbsp;</td>
					      <td width="76"><strong>开始截止日期</strong></td>
					      <td width="284" colspan="2">
					        <s:textfield id="startDate" name="formBean.startDate" cssClass="easyui-datebox"  data-options="width:100,editable:false"/>
					        &nbsp;至&nbsp;
					        <s:textfield id="endDate" name="formBean.endDate" cssClass="easyui-datebox"  data-options="width:100,editable:false"/>
					      </td>
					      <td></td>
					    </tr>
						<tr class="InnerTableContent">
					      <td height="35">&nbsp;</td>
					      <td><strong>连锁店</strong></td>
					      <td><s:select id="chainStore" name="formBean.chainStore.chain_id"  list="uiBean.chainStores" listKey="chain_id" listValue="chain_name"/>	      </td>
					      <td></td>
					      <td></td>
					    </tr>
	                    <tr class="InnerTableContent">
					      <td height="35">&nbsp;</td>
					      <td>&nbsp;</td>
					      <td colspan="2"><input type="button" value="生成报表" onclick="generateReport();"/></td>
					      <td>&nbsp;</td>
					    </tr>
					  </table></td>
			    </tr>
	   			<tr>
                    <td>
            	      <div id="report" style="display:none">
            	         <table width="70%" border="0" id="reportTable">

					     </table>
            	      </div>
                    </td>
                </tr>
			  </table>
	   </td></tr>

	 </table>
	 </s:form>
</body>
</html>