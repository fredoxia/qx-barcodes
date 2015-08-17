<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib prefix="s" uri="/struts-tags" %>

<%@ page import="java.util.Date,java.text.SimpleDateFormat,com.onlineMIS.ORM.entity.headQ.chain.*" %>
<html>
<head>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>连锁店销售报表</title>
<%@ include file="../../common/Style.jsp"%>
<script type="text/javascript">
$(document).ready(function(){
	$("#loading").hide();
	$("#contentDiv").show();
	parent.$.messager.progress('close'); 
})


</script>
</head>
<body>
<script type="text/javascript">$("#loading").show();</script>

<div id="contentDiv" style="display:none;">
<table width="90%" align="center"  class="OuterTable">
 <tr>
   <td></td>
 </tr>
 <tr>
   <td>
	 <table width="100%" border="0">
	 	<tr class="PBAOuterTableTitale">
	       <td width="149"><s:property value="#session.CHAIN_SALES_REPORT_RESULT.customerName"/></td>
	       <td width="141"></td>
	       <td colspan="7"><s:date name="#session.CHAIN_SALES_REPORT_RESULT.startDate" format="yyyy-MM-dd"/> 至 <s:date name="#session.CHAIN_SALES_REPORT_RESULT.endDate" format="yyyy-MM-dd"/></td>
        </tr>
	    <tr class="InnerTableContent">
	      <td height="4" colspan="9"><hr width="100%" color="#FFCC00"/></td>
	    </tr>
	    <s:iterator value="#session.CHAIN_SALES_REPORT_RESULT.brandItems" status = "st" id="result" >
		    <tr class="PBAOuterTableTitale">
		       <td width="149">品牌</td>
		       <td width="141"><s:property value="#result.brand"/></td>
		       <td width="155"></td>
		       <td width="118">&nbsp;</td>
		       <td width="115">&nbsp;</td>
		       <td width="93">&nbsp;</td>
		       <td width="93">&nbsp;</td>
		       <td width="113">&nbsp;</td>
		       <td width="216">&nbsp;</td>
		    </tr>
		    <tr class="InnerTableContent">
			   <td height="19">货号</td>
			   <td>货品类别</td>
			   <td>条码</td>
			   <td>每手数量</td>
			   <td>配货数量</td>
			   <td>批发价格</td>
			   <td>批发总价</td>
			   <td>零售价格</td>
			   <td>零售总价</td>
			 </tr>
	         <s:iterator value="#result.items" status = "st" id="result2" >
		        <tr class="InnerTableContent">
			      <td height="19"><s:property value="#result2.product.productCode"/></td>
			      <td><s:property value="#result2.product.category.category_Name"/></td>
			      <td><s:property value="#result2.product.barcode"/></td>
			      <td><s:property value="#result2.product.numPerHand"/></td>
			      <td><s:property value="#result2.quantity"/></td>
			      <td><s:property value="#result2.product.wholeSalePrice"/></td>
			      <td><s:property value="#result2.wholeRevenue"/></td>
			      <td><s:property value="#result2.product.salesPrice"/></td>
			      <td><s:property value="#result2.salesRevenue"/></td>
			    </tr>
	        </s:iterator>
		    <tr class="PBAOuterTableTitale">
		      <td height="19">单品牌汇总</td>
		      <td>&nbsp;</td>
		      <td>&nbsp;</td>
		      <td>&nbsp;</td>
		      <td><s:property value="#result.totalQ"/></td>
		      <td></td>
		      <td><s:property value="#result.totalWholeSaleRevenue"/></td>
		      <td></td>
		      <td><s:property value="#result.totalRevenue"/></td>
		    </tr>
		    <tr class="InnerTableContent">
		      <td height="4" colspan="9"><hr width="100%" color="#FFCC00"/></td>
		    </tr>
	    </s:iterator>
		<tr class="PBAOuterTableTitale">
		      <td height="19">所有品牌汇总</td>
		      <td>&nbsp;</td>
		      <td>&nbsp;</td>
		      <td>&nbsp;</td>
		      <td><s:property value="#session.CHAIN_SALES_REPORT_RESULT.totalQ"/></td>
		      <td></td>
		      <td><s:property value="#session.CHAIN_SALES_REPORT_RESULT.totalWholeRevenue"/></td>
		      <td></td>
		      <td><s:property value="#session.CHAIN_SALES_REPORT_RESULT.totalRevenue"/></td>
		    </tr>
	  </table>
   </td>
   </tr>
   <tr align="right">
      <td>
          <s:form action="/action/chainSales!genSalesReportForExcel" method="post" theme="simple">
              <s:submit value="导出到Excel"/>
          </s:form>
      </td>
   </tr>
 </table>
 

</div>
</body>
</html>