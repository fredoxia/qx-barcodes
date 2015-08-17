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
    document.chainPurchaseStatisForm.action="chainReportJSPAction!generatePurchaseStatisticReport";
    document.chainPurchaseStatisForm.submit();
};
function getLevelThree(brandId){
	$("#brandId").attr("value", ALL_RECORD);
	pageNav.clearPager();
    document.chainPurchaseStatisForm.action="chainReportJSPAction!generatePurchaseStatisticReport";
    document.chainPurchaseStatisForm.submit();
}
</script>
</head>
<body>

    <s:form action="chainReportJSPAction!generatePurchaseStatisticReport" method="POST" name="chainPurchaseStatisForm" id="chainPurchaseStatisForm" theme="simple">
	<%@ include file="SalesPurchaseStatisticReportForm.jsp"%>
	<%@ include file="../../common/pageForm.jsp"%>
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
				       <tr class="InnerTableContent">
				         <td colspan="4" height="20">商品年份 : <s:property value="uiBean.purchaseTotalItem.year.year"/> <s:property value="uiBean.purchaseTotalItem.quarter.quarter_Name"/>
				         					 &nbsp;  品牌 : <s:property value="uiBean.purchaseTotalItem.brand.brand_Name"/>
				         </td>
				       </tr>
				     </table></td>
			    </tr>
			    <tr>
			      <td colspan="7">
			            <!-- table to display the product information -->
						<table width="100%"  align="left" class="OuterTable" id="org_table">
						  <tr class="PBAInnerTableTitale" align='left'>
						    <th width="3%" height="35">&nbsp;</th>
						    <th width="8%">货号</th>
						    <th width="11%">条码</th>
						    <th width="8%">采购数量</th>
						    <th width="8%">退货数量</th>
						    <th width="8%">净采购量</th>
						    <th width="9%">采购单价</th>
						    <th width="9%">净采购金额</th>
						  </tr>
						  <tbody id="orderTablebody">
						      <s:iterator value="uiBean.purchaseStatisLevelFour" status = "st" id="ci" >
						  		<tr class="InnerTableContent" id="orderRow0" class="InnerTableContent" <s:if test="#st.odd">style='background-color: rgb(255, 250, 208);'</s:if>>   
							      <td height="25"><s:property value="#st.index +1"/></td>							      
							      <td align="center"><s:property value="#ci.productBarcode.product.productCode"/> <s:property value="#ci.productBarcode.color.name"/></td>						      
							      <td align="right"><a href="#" onclick="traceInventory('<s:property value="#ci.productBarcode.barcode"/>', '')"><s:property value="#ci.productBarcode.barcode"/><img src="<%=request.getContextPath()%>/conf_files/web-image/search.png" border="0"/></a></td>
							      <td align="right"><s:property value="#ci.purchaseQuantity"/></td>
							      <td align="right"><s:property value="#ci.returnQuantity"/></td>
							      <td align="right"><s:property value="#ci.netQuantity"/></td>
							      <td align="right"><s:if test="#session.LOGIN_CHAIN_USER.containFunction('purchaseAction!seeCost')"><s:text name="format.price"><s:param value="#ci.avgPrice"/></s:text></s:if><s:else>-</s:else></td>
							      <td align="right"><s:if test="#session.LOGIN_CHAIN_USER.containFunction('purchaseAction!seeCost')"><s:text name="format.price"><s:param value="#ci.purchaseTotalAmt"/></s:text></s:if><s:else>-</s:else></td>
							     </tr>
							   </s:iterator>  
						  </tbody>
						  <s:if test="uiBean.purchaseStatisLevelFour.size >0">
							  <tr class="PBAInnerTableTitale">
							    <td colspan="3" align="left">合计</td>
							    <td align="right"><s:property value="uiBean.purchaseTotalItem.purchaseQuantity"/></td>
							    <td align="right"><s:property value="uiBean.purchaseTotalItem.returnQuantity"/></td>
							    <td align="right"><s:property value="uiBean.purchaseTotalItem.netQuantity"/></td>
							    <td align="right"><s:if test="#session.LOGIN_CHAIN_USER.containFunction('purchaseAction!seeCost')"><s:text name="format.price"><s:param value="uiBean.purchaseTotalItem.avgPrice"/></s:text></s:if><s:else>-</s:else></td>
							    <td align="right"><s:if test="#session.LOGIN_CHAIN_USER.containFunction('purchaseAction!seeCost')"><s:text name="format.price"><s:param value="uiBean.purchaseTotalItem.purchaseTotalAmt"/></s:text></s:if><s:else>-</s:else></td>
							  </tr>
							  <tr class="InnerTableContent">	      
						           <td colspan="8"><div id="pageNav"></div></td>
						      </tr>	
						  </s:if><s:else>
							  <tr class="InnerTableContent">
							    <th colspan="8" align="left">无法找到库存信息</th>
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