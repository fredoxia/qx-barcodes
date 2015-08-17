<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib prefix="s" uri="/struts-tags" %>

<%@ page import="java.util.Date,java.text.SimpleDateFormat" %>
<html>
<head>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>连锁店销售报表</title>
<%@ include file="../../common/Style.jsp"%>
<script>
function BSkeyDown(e){

	 var ieKey = event.keyCode;

	 if (ieKey==13){
	   if (event.srcElement.id == "clientName"){
		   searchCustomer(); 
		   event.returnValue=false;
	   }
	 }  
} 

document.onkeydown = BSkeyDown; 

/**
 * to ensure user has select all criteria
 */

function validateSearch(){

	var year = $("#year").val();
	var quarter = $("#quarter").val();
	var brand = $("#brand").val();

	var clientID = $("#clientID").val();

	if (year==0 || quarter==0 || brand== null || clientID ==0 ){
		alert("请在选项（年份，季度，品牌，客户地区，客户名字，录入时间）中选出你需要的!");
		return false;
	} else
		return true;
}
$(document).ready(function(){
	parent.$.messager.progress('close'); 
});
</script>

</head>
<body>
<s:form id="preChainSalesReportForm" action="/action/chainSales!genChainSalesReport" method="POST" theme="simple" target="_blank" onsubmit="return validateSearch();" >
 <table width="90%" align="center"  class="OuterTable">
 <tr>
   <td>

 <table width="100%" border="0">
    <tr class="PBAOuterTableTitale">
       <td colspan="6">选择货品</td>
    </tr>
    <tr class="InnerTableContent">
      <td width="92" height="19"><strong>年份：</strong></td>
      <td width="152">
         <s:select name="formBean.year" id="year"  list="uiBean.basicData.yearList" listKey="year_ID" listValue="year" headerKey="0" headerValue="-------" />
      </td>
      <td width="94"><strong>季度：</strong></td>
      <td width="215">
        <s:select name="formBean.quarter" id="quarter"  list="uiBean.basicData.quarterList" listKey="quarter_ID" listValue="quarter_Name" headerKey="0" headerValue="-------" />
       </td>
      <td width="67">&nbsp;</td>
      <td width="267">&nbsp;</td>
    </tr>
    <tr class="InnerTableContent">
      <td height="4" colspan="6"><hr width="100%" color="#FFCC00"/></td>
    </tr>
    <tr class="InnerTableContent">
      <td height="19"><strong>品牌：</strong></td>
      <td>
          <s:select name="formBean.brands" id="brand"  list="uiBean.basicData.brandList" listKey="brand_ID" listValue="brand_Name" multiple="true" size="10" />
      </td>
      <td>&nbsp;</td>
      <td>&nbsp;</td>
      <td>&nbsp;</td>
      <td>&nbsp;</td>
    </tr>
    <tr class="InnerTableContent">
      <td height="5" colspan="6"><hr width="100%" color="#FFCC00"/></td>
    </tr>
            <tr class="InnerTableContent">
      <td height="19"><strong>客户名字：</strong></td>
      <td colspan="3"><%@ include file="../include/ClientInput.jsp"%></td>
      <td></td>
      <td>      </td>
    </tr>
   
    <tr class="InnerTableContent">
      <td height="4" colspan="6"><hr width="100%" color="#FFCC00"/></td>
    </tr>
    <tr class="InnerTableContent">
      <td height="19"><strong>过账时间：</strong></td>
      <td height="19" colspan="5">
                      开始日期 ：<input type="Text" id="startDate" readonly="readonly" name="formBean.startDate" value="<%=new SimpleDateFormat("yyyy-MM-dd").format(new Date())%>"> 
		<a href="javascript:NewCssCal('startDate','yyyymmdd','arrow')">
			<img src="<%=request.getContextPath()%>/conf_files/web-image/cal.gif" width="16" height="16" alt="Pick a date" border="0"></a>&nbsp;&nbsp;
	           截止日期 ：
	           <input type="Text" id="endDate" readonly="readonly" name="formBean.endDate" value="<%=new SimpleDateFormat("yyyy-MM-dd").format(new Date())%>"> 
		<a href="javascript:NewCssCal('endDate','yyyymmdd','arrow')">
			<img src="<%=request.getContextPath()%>/conf_files/web-image/cal.gif" width="16" height="16" alt="Pick a date" border="0">		</a>          </td>
      </tr>
    <tr class="InnerTableContent">
      <td height="5" colspan="6"><hr width="100%" color="#FFCC00"/></td>
    <tr class="InnerTableContent">
      <td height="30">&nbsp;</td>
      <td><input type="submit" name="generateReport" value="生成报表"/> </td>
      <td>&nbsp;</td>
      <td>&nbsp;</td>
      <td>&nbsp;</td>
      <td>&nbsp;</td>
    </tr>

  </table>
   </td></tr>
 </table>
</s:form>

</body>
</html>