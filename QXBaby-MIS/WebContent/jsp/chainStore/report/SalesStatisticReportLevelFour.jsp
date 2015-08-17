<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib prefix="s" uri="/struts-tags" %>
<%@ page import="com.onlineMIS.ORM.entity.chainS.inventoryFlow.ChainInventoryFlowOrder" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=8" /> 
<title>千禧宝贝连锁店管理信息系统</title>
<%@ include file="../../common/Style.jsp"%>

<link href="<%=request.getContextPath()%>/conf_files/css/pagination.css" rel="stylesheet" type="text/css"/>
<SCRIPT src="<%=request.getContextPath()%>/conf_files/js/pagenav1.1.js" type=text/javascript></SCRIPT>
<SCRIPT src="<%=request.getContextPath()%>/conf_files/js/ChainInvenTrace.js" type=text/javascript></SCRIPT>
<script>
$(document).ready(function(){
	renderPaginationBar($("#currentPage").val(), $("#totalPage").val());
	parent.$.messager.progress('close'); 
	$("#org_table tr").mouseover(function(){      
		$(this).addClass("over");}).mouseout(function(){    
		$(this).removeClass("over");});
});

pageNav.fn = function(page,totalPage){                
	$("#currentPage").attr("value",page);
    document.chainSalesStatisticReportForm.action="chainReportJSPAction!generateSalesStatisticReport";
    document.chainSalesStatisticReportForm.submit();
};
function getLevelThree(brandId){
	$("#brandId").attr("value", ALL_RECORD);
	pageNav.clearPager();
    document.chainSalesStatisticReportForm.action="chainReportJSPAction!generateSalesStatisticReport";
    document.chainSalesStatisticReportForm.submit();
}
function downloadReport(){
    document.chainSalesStatisticReportForm.action="chainReportJSPAction!generateChainSalesStatisticExcelReport";
    document.chainSalesStatisticReportForm.submit();
}
</script>
</head>
<body>

    <s:form action="/actionChain/chainReportJSPAction!generateSalesStatisticReport" method="POST" name="chainSalesStatisticReportForm" id="chainSalesStatisticReportForm" theme="simple">
	<%@ include file="SalesPurchaseStatisticReportForm.jsp"%>
	<%@ include file="../../common/pageForm.jsp"%>
    <table width="98%" align="center"  class="OuterTable">
	    <tr><td>
	         <div class="errorAndmes"><s:actionerror cssStyle="color:red"/><s:actionmessage cssStyle="color:blue"/></div>
			 <table width="100%" border="0">
			    <tr>
			       <td height="50" colspan="7">
				   	 <table width="100%" border="0">
				       <tr class="PBAOuterTableTitale">
				         <td height="32" colspan="4">
				              	连锁店库商品统计表
				         </td>
			           </tr>
				       <tr class="InnerTableContent">
				         <td colspan="4" height="20">连锁店 : <s:property value="uiBean.totalItem.chainStore.chain_name"/>
				                                    &nbsp;  经手人 : <s:property value="uiBean.totalItem.saler.user_name"/>
				                                    &nbsp;  销售日期 : <s:date name="uiBean.totalItem.startDate"  format="yyyy-MM-dd"/> 至 <s:date name="uiBean.totalItem.endDate"  format="yyyy-MM-dd"/></td>
				       </tr>
				       <tr class="InnerTableContent"style='<s:if test="uiBean.totalItem.brand.chainStore != null">color: blue;</s:if>'>
				         <td colspan="4" height="20">商品年份 : <s:property value="uiBean.totalItem.year.year"/> <s:property value="uiBean.totalItem.quarter.quarter_Name"/>
				         							&nbsp;  品牌 : <s:property value="uiBean.totalItem.brand.brand_Name"/>
				         </td>
				       </tr>
				     </table></td>
			    </tr>
			    <tr>
			      <td colspan="7">
			            <!-- table to display the product information -->
						<table width="100%"  align="left" class="OuterTable" id="org_table">
						  <tr class="PBAInnerTableTitale" align='left'>
						    <th width="2%" height="35">&nbsp;</th>
						    <th width="9%">货号<br/>&nbsp;</th>
						    <th width="10%">条码<br/>&nbsp;</th>
						    <th width="4%">销售量<br/> A</th>
						    <th width="4%">退货量<br/> B</th>
						    <th width="5%">净销售量<br/> A-B</th>
						    <th width="4%">赠品量<br/>&nbsp;</th>
						    <th width="6%">销售额<br/> C</th>
						    <th width="6%">退货额<br/> D</th>
						    <th width="6%">净销售额<br/> C-D</th>
						    <th width="6%">销售折扣<br/>&nbsp;</th>
						    <th width="6%">净销售成本<br/> E</th>
						    <th width="5%">赠品成本<br/> F</th>
						    <th width="6%">商品利润<br/> C-D-E-F</th>
						    <th width="1%">-</th>
						  </tr>
						  <tbody id="orderTablebody">
						      <s:iterator value="uiBean.saleStatisLevelFour" status = "st" id="sLeveFour" >
						  		<tr class="InnerTableContent" id="orderRow0" class="InnerTableContent" <s:if test="#st.odd">style='background-color: rgb(255, 250, 208);'</s:if>>   
							      <td height="25"><s:property value="#st.index +1"/></td>							      
							      <td align="right"><s:property value="#sLeveFour.productBarcode.product.productCode"/> <s:property value="#sLeveFour.productBarcode.color.name"/></td>	
							      <td align="right"><a href="#" onclick="traceInventory('<s:property value="#sLeveFour.productBarcode.barcode"/>', '')"><s:property value="#sLeveFour.productBarcode.barcode"/><img src="<%=request.getContextPath()%>/conf_files/web-image/search.png" border="0"/></a></td>						      
							      <td align="right"><s:property value="#sLeveFour.salesQ"/></td>
							      <td align="right"><s:property value="#sLeveFour.returnQ"/></td>
							      <td align="right"><s:property value="#sLeveFour.netQ"/></td>
							      <td align="right"><s:property value="#sLeveFour.freeQ"/></td>
							      <td align="right"><s:text name="format.price"><s:param value="#sLeveFour.salesPrice"/></s:text></td>
							      <td align="right"><s:text name="format.price"><s:param value="#sLeveFour.returnPrice"/></s:text></td>
							      <td align="right"><s:text name="format.price"><s:param value="#sLeveFour.netPrice"/></s:text></td>
							      <td align="right"><s:text name="format.price"><s:param value="#sLeveFour.salesDiscount"/></s:text></td>
							      <td align="right"><s:if test="#session.LOGIN_CHAIN_USER.containFunction('purchaseAction!seeCost')"><s:text name="format.price"><s:param value="#sLeveFour.netCost"/></s:text></s:if><s:else>-</s:else></td>
							      <td align="right"><s:if test="#session.LOGIN_CHAIN_USER.containFunction('purchaseAction!seeCost')"><s:text name="format.price"><s:param value="#sLeveFour.freeCost"/></s:text></s:if><s:else>-</s:else></td>
							      <td align="right"><s:if test="#session.LOGIN_CHAIN_USER.containFunction('purchaseAction!seeCost')"><s:text name="format.price"><s:param value="#sLeveFour.netProfit"/></s:text></s:if><s:else>-</s:else></td>
							      <td></td>
							     </tr>
							   </s:iterator>  
						  </tbody>
						  <s:if test="uiBean.saleStatisLevelFour.size >0">
							  <tr class="PBAInnerTableTitale">
							    <td colspan="3" align="left">合计</td>
							    <td align="right"><s:property value="uiBean.totalItem.salesQ"/></td>
							    <td align="right"><s:property value="uiBean.totalItem.returnQ"/></td>
							    <td align="right"><s:property value="uiBean.totalItem.netQ"/></td>
							    <td align="right"><s:property value="uiBean.totalItem.freeQ"/></td>
							    <td align="right"><s:text name="format.price"><s:param value="uiBean.totalItem.salesPrice"/></s:text></td>
						        <td align="right"><s:text name="format.price"><s:param value="uiBean.totalItem.returnPrice"/></s:text></td>
						        <td align="right"><s:text name="format.price"><s:param value="uiBean.totalItem.netPrice"/></s:text></td>
						        <td align="right"><s:text name="format.price"><s:param value="uiBean.totalItem.salesDiscount"/></s:text></td>
						        <td align="right"><s:if test="#session.LOGIN_CHAIN_USER.containFunction('purchaseAction!seeCost')"><s:text name="format.price"><s:param value="uiBean.totalItem.netCost"/></s:text></s:if><s:else>-</s:else></td>
						        <td align="right"><s:if test="#session.LOGIN_CHAIN_USER.containFunction('purchaseAction!seeCost')"><s:text name="format.price"><s:param value="uiBean.totalItem.freeCost"/></s:text></s:if><s:else>-</s:else></td>
						        <td align="right"><s:if test="#session.LOGIN_CHAIN_USER.containFunction('purchaseAction!seeCost')"><s:text name="format.price"><s:param value="uiBean.totalItem.netProfit"/></s:text></s:if><s:else>-</s:else></td>
							    <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href='#' onclick="downloadReport()"><img src='<%=request.getContextPath()%>/conf_files/easyUI/themes/icons/filesave.png' border='0' title="下载"/></a></td>
							  </tr>
							  <tr class="InnerTableContent">	      
						           <td colspan="15"><div id="pageNav"></div></td>
						      </tr>	
						  </s:if><s:else>
							  <tr class="InnerTableContent">
							    <th colspan="15" align="left">无法找到销售信息</th>
							  </tr>						  
						  </s:else>
					     </table>
			      </td>
			    </tr>
			    <tr class="InnerTableContent">
			      <td height="10">
			          <input type="button" value="返回上层" onclick="getLevelThree()"/>
			      </td>
			      <td colspan="6">

				  </td>
			    </tr>
			  </table>
	   </td></tr>
	 </table>
	 </s:form>
</body>
</html>