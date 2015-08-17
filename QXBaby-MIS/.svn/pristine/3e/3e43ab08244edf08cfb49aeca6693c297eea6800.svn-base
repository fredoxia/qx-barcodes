<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib prefix="s" uri="/struts-tags" %>
<%@ page import="com.onlineMIS.ORM.entity.chainS.inventoryFlow.ChainInventoryFlowOrder" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>千禧宝贝连锁店管理信息系统</title>
<%@ include file="../../common/Style.jsp"%>

<script>
$(document).ready(function(){
	parent.$.messager.progress('close'); 
	$("#org_table tr").mouseover(function(){      
		$(this).addClass("over");}).mouseout(function(){    
		$(this).removeClass("over");});
});
function getLevelThree(quarterId){
	$("#quarterId").attr("value", quarterId);
    document.chainSalesStatisticReportForm.action="chainReportJSPAction!generateSalesStatisticReport";
    document.chainSalesStatisticReportForm.submit();
}
function getLevelOne(){
	$("#yearId").attr("value", ALL_RECORD);
    document.chainSalesStatisticReportForm.action="chainReportJSPAction!generateSalesStatisticReport";
    document.chainSalesStatisticReportForm.submit();
}
function downloadReportByQuarter(quarterId){
	$("#quarterId").attr("value", quarterId);
    document.chainSalesStatisticReportForm.action="chainReportJSPAction!generateChainSalesStatisticExcelReport";
    document.chainSalesStatisticReportForm.submit();
}
function downloadReport(){
	$("#quarterId").attr("value", -1);
    document.chainSalesStatisticReportForm.action="chainReportJSPAction!generateChainSalesStatisticExcelReport";
    document.chainSalesStatisticReportForm.submit();
}
</script>
</head>
<body>

    <s:form action="/actionChain/chainReportJSPAction!generateSalesStatisticReport" method="POST" name="chainSalesStatisticReportForm" id="chainSalesStatisticReportForm" theme="simple">
	<%@ include file="SalesPurchaseStatisticReportForm.jsp"%>
    <table width="95%" align="center"  class="OuterTable">
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
				     </table></td>
			    </tr>
			    <tr>
			      <td colspan="7">
			            <!-- table to display the product information -->
						<table width="100%"  align="left" class="OuterTable" id="org_table">
						  <tr class="PBAInnerTableTitale" align='left'>
						    <th width="3%" height="35">&nbsp;</th>
						    <th width="5%">商品年份<br/>&nbsp;</th>
						    <th width="5%">销售数量<br/> A</th>
						    <th width="5%">退货数量<br/> B</th>
						    <th width="6%">净销售数量<br/> A-B</th>
						    <th width="5%">赠品数量<br/>&nbsp;</th>
						    <th width="7%">销售额<br/> C</th>
						    <th width="7%">退货额<br/> D</th>
						    <th width="7%">净销售额<br/> C-D</th>
						    <th width="7%">销售折扣<br/>&nbsp;</th>
						    <th width="7%">净销售成本<br/> E</th>
						    <th width="7%">赠品成本<br/> F</th>
						    <th width="7%">商品利润<br/> C-D-E-F</th>
						    <th width="5%"></th>
						  </tr>
						  <tbody id="orderTablebody">
						      <s:iterator value="uiBean.saleStatisLevelTwo" status = "st" id="sLeveTwo" >
						  		<tr class="InnerTableContent" id="orderRow0" class="InnerTableContent" <s:if test="#st.odd">style='background-color: rgb(255, 250, 208);'</s:if>>   
							      <td height="25"><s:property value="#st.index +1"/></td>							      
							      <td align="center"><s:property value="#sLeveTwo.year.year"/> <s:property value="#sLeveTwo.quarter.quarter_Name"/></td>						      
							      <td align="right"><s:property value="#sLeveTwo.salesQ"/></td>
							      <td align="right"><s:property value="#sLeveTwo.returnQ"/></td>
							      <td align="right"><s:property value="#sLeveTwo.netQ"/></td>
							      <td align="right"><s:property value="#sLeveTwo.freeQ"/></td>
							      <td align="right"><s:text name="format.price"><s:param value="#sLeveTwo.salesPrice"/></s:text></td>
							      <td align="right"><s:text name="format.price"><s:param value="#sLeveTwo.returnPrice"/></s:text></td>
							      <td align="right"><s:text name="format.price"><s:param value="#sLeveTwo.netPrice"/></s:text></td>
							      <td align="right"><s:text name="format.price"><s:param value="#sLeveTwo.salesDiscount"/></s:text></td>
							      <td align="right"><s:if test="#session.LOGIN_CHAIN_USER.containFunction('purchaseAction!seeCost')"><s:text name="format.price"><s:param value="#sLeveTwo.netCost"/></s:text></s:if><s:else>-</s:else></td>
							      <td align="right"><s:if test="#session.LOGIN_CHAIN_USER.containFunction('purchaseAction!seeCost')"><s:text name="format.price"><s:param value="#sLeveTwo.freeCost"/></s:text></s:if><s:else>-</s:else></td>
							      <td align="right"><s:if test="#session.LOGIN_CHAIN_USER.containFunction('purchaseAction!seeCost')"><s:text name="format.price"><s:param value="#sLeveTwo.netProfit"/></s:text></s:if><s:else>-</s:else></td>
							      <td align="center"><a href='#' onclick="getLevelThree(<s:property value="#sLeveTwo.quarter.quarter_ID"/>)"><img src='<%=request.getContextPath()%>/conf_files/web-image/editor.gif' border='0'/></a>
							                         <a href='#' onclick="downloadReportByQuarter(<s:property value="#sLeveTwo.quarter.quarter_ID"/>)"><img src='<%=request.getContextPath()%>/conf_files/easyUI/themes/icons/filesave.png' border='0' title="下载"/></a>
							      </td>
							     </tr>
							   </s:iterator>  
						  </tbody>
						  <s:if test="uiBean.saleStatisLevelTwo.size >0">
							  <tr class="PBAInnerTableTitale">
							    <td colspan="2" align="left">合计</td>
							    <td align="right"><s:property value="uiBean.totalItem.salesQ"/></td>
							    <td align="right"><s:property value="uiBean.totalItem.returnQ"/></td>
							    <td align="right"><s:property value="uiBean.totalItem.netQ"/></td>
							    <td align="right"><s:property value="uiBean.totalItem.freeQ"/></td>
							    <td align="right"><s:text name="format.price"><s:param value="uiBean.totalItem.salesPrice"/></s:text></th>
						        <td align="right"><s:text name="format.price"><s:param value="uiBean.totalItem.returnPrice"/></s:text></td>
						        <td align="right"><s:text name="format.price"><s:param value="uiBean.totalItem.netPrice"/></s:text></td>
						        <td align="right"><s:text name="format.price"><s:param value="uiBean.totalItem.salesDiscount"/></s:text></td>
						        <td align="right"><s:if test="#session.LOGIN_CHAIN_USER.containFunction('purchaseAction!seeCost')"><s:text name="format.price"><s:param value="uiBean.totalItem.netCost"/></s:text></s:if><s:else>-</s:else></td>
						        <td align="right"><s:if test="#session.LOGIN_CHAIN_USER.containFunction('purchaseAction!seeCost')"><s:text name="format.price"><s:param value="uiBean.totalItem.freeCost"/></s:text></s:if><s:else>-</s:else></td>
						        <td align="right"><s:if test="#session.LOGIN_CHAIN_USER.containFunction('purchaseAction!seeCost')"><s:text name="format.price"><s:param value="uiBean.totalItem.netProfit"/></s:text></s:if><s:else>-</s:else></td>
							    <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href='#' onclick="downloadReport()"><img src='<%=request.getContextPath()%>/conf_files/easyUI/themes/icons/filesave.png' border='0' title="下载"/></a></td>
							  </tr>
						  </s:if><s:else>
							  <tr class="InnerTableContent">
							    <th colspan="11" align="left">无法找到销售信息</th>
							  </tr>						  
						  </s:else>
					     </table>
			      </td>
			    </tr>
			    <tr class="InnerTableContent">
			      <td height="10">
			          <input type="button" value="返回上层" onclick="getLevelOne()"/>
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