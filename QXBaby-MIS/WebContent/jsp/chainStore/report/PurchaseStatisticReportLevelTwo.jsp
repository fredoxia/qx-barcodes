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
var baseurl = "<%=request.getContextPath()%>";
function getLevelThree(quarterId){
	$.messager.progress({
		title : '提示',
		text : '数据处理中，请稍后....'
	});
	$("#quarterId").attr("value", quarterId);
    document.chainPurchaseStatisForm.action="chainReportJSPAction!generatePurchaseStatisticReport";
    document.chainPurchaseStatisForm.submit();
}
function getLevelOne(){
	$.messager.progress({
		title : '提示',
		text : '数据处理中，请稍后....'
	});
	$("#yearId").attr("value", ALL_RECORD);
    document.chainPurchaseStatisForm.action="chainReportJSPAction!generatePurchaseStatisticReport";
    document.chainPurchaseStatisForm.submit();
}
</script>
</head>
<body>

    <s:form action="chainReportJSPAction!generatePurchaseStatisticReport" method="POST" name="chainPurchaseStatisForm" id="chainPurchaseStatisForm" theme="simple">
	<%@ include file="SalesPurchaseStatisticReportForm.jsp"%>
    <table width="80%" align="center"  class="OuterTable">
	    <tr><td>
	         <div class="errorAndmes"><s:actionerror cssStyle="color:red"/><s:actionmessage cssStyle="color:blue"/></div>
			 <table width="100%" border="0">
			    <tr>
			       <td height="50" colspan="7">
				   	 <table width="100%" border="0">
				       <tr class="PBAOuterTableTitale">
				         <td height="32" colspan="4">
				              	连锁店采购统计表
				         </td>
			           </tr>
				       <tr class="InnerTableContent">
				         <td colspan="4" height="20">连锁店:<s:property value="uiBean.purchaseTotalItem.chainStore.chain_name"/>
				         					 &nbsp;  采购日期 : <s:date name="uiBean.purchaseTotalItem.startDate"  format="yyyy-MM-dd"/> 至 <s:date name="uiBean.purchaseTotalItem.endDate"  format="yyyy-MM-dd"/></td>
				       </tr>
				     </table></td>
			    </tr>
			    <tr>
			      <td colspan="7">
			            <!-- table to display the product information -->
						<table width="100%"  align="left" class="OuterTable" id="org_table">
						  <tr class="PBAInnerTableTitale" align='left'>
						    <th width="3%" height="35">&nbsp;</th>
						    <th width="5%">年份</th>
						    <th width="8%">采购数量</th>
						    <th width="8%">退货数量</th>
						    <th width="8%">净采购量</th>
						    <th width="10%">采购单价</th>
						    <th width="10%">净采购金额</th>
						    <th width="5%"></th>
						  </tr>
						  <tbody id="orderTablebody">
						      <s:iterator value="uiBean.purchaseStatisLevelTwo" status = "st" id="ci" >
						  		<tr class="InnerTableContent" id="orderRow0" class="InnerTableContent" <s:if test="#st.odd">style='background-color: rgb(255, 250, 208);'</s:if>>   
							      <td height="25"><s:property value="#st.index +1"/></td>							      
							      <td align="center"><s:property value="#ci.year.year"/> <s:property value="#ci.quarter.quarter_Name"/></td>						      
							      <td align="right"><s:property value="#ci.purchaseQuantity"/></td>
							      <td align="right"><s:property value="#ci.returnQuantity"/></td>
							      <td align="right"><s:property value="#ci.netQuantity"/></td>
							      <td align="right"><s:if test="#session.LOGIN_CHAIN_USER.containFunction('purchaseAction!seeCost')"><s:text name="format.price"><s:param value="#ci.avgPrice"/></s:text></s:if><s:else>-</s:else></td>
							      <td align="right"><s:if test="#session.LOGIN_CHAIN_USER.containFunction('purchaseAction!seeCost')"><s:text name="format.price"><s:param value="#ci.purchaseTotalAmt"/></s:text></s:if><s:else>-</s:else></td>
							      <td align="center"><a href='#' onclick="getLevelThree(<s:property value="#ci.quarter.quarter_ID"/>)"><img src='<%=request.getContextPath()%>/conf_files/web-image/editor.gif' border='0'/></a></td>
							     </tr>
							   </s:iterator>  
						  </tbody>
						  <s:if test="uiBean.purchaseStatisLevelTwo.size >0">
							  <tr class="PBAInnerTableTitale">
							    <td colspan="2" align="left">合计</td>
							    <td align="right"><s:property value="uiBean.purchaseTotalItem.purchaseQuantity"/></td>
							    <td align="right"><s:property value="uiBean.purchaseTotalItem.returnQuantity"/></td>
							    <td align="right"><s:property value="uiBean.purchaseTotalItem.netQuantity"/></td>
							    <td align="right"><s:if test="#session.LOGIN_CHAIN_USER.containFunction('purchaseAction!seeCost')"><s:text name="format.price"><s:param value="uiBean.purchaseTotalItem.avgPrice"/></s:text></s:if><s:else>-</s:else></td>
							    <td align="right"><s:if test="#session.LOGIN_CHAIN_USER.containFunction('purchaseAction!seeCost')"><s:text name="format.price"><s:param value="uiBean.purchaseTotalItem.purchaseTotalAmt"/></s:text></s:if><s:else>-</s:else></td>
							    <td></td>
							  </tr>
						  </s:if><s:else>
							  <tr class="InnerTableContent">
							    <th colspan="6" align="left">无法找到库存信息</th>
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