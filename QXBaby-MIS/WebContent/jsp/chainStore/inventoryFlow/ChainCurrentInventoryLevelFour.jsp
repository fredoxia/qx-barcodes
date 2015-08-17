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
var baseurl = "<%=request.getContextPath()%>";
$(document).ready(function(){
	renderPaginationBar($("#currentPage").val(), $("#totalPage").val());
	parent.$.messager.progress('close'); 
	$("#org_table tr").mouseover(function(){      
		$(this).addClass("over");}).mouseout(function(){    
		$(this).removeClass("over");});
});

pageNav.fn = function(page,totalPage){                
	$("#currentPage").attr("value",page);
    document.chainInventoryFlowForm.action="inventoryFlowJSPAction!getLevelFourCurrentInventory";
    document.chainInventoryFlowForm.submit();
};

function getLevelThree(yearId){
	pageNav.clearPager();
    document.chainInventoryFlowForm.action="inventoryFlowJSPAction!getLevelThreeCurrentInventory";
    document.chainInventoryFlowForm.submit();
}
</script>
</head>
<body>
 
    <s:form action="/actionChain/inventoryFlowJSPAction!getLevelFourCurrentInventory" method="POST" name="chainInventoryFlowForm" id="chainInventoryFlowForm" theme="simple">
	<s:hidden name="formBean.chainId" id="chainId"/>  
	<s:hidden name="formBean.yearId" id="yearId"/>
	<s:hidden name="formBean.quarterId" id="quarterId"/>
	<s:hidden name="formBean.brandId" id="brandId"/>
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
				              	连锁店库存状况表
				         </td>
			           </tr>
				       <tr class="InnerTableContent" style="<s:if test="uiBean.inventoryItem.productBarcode.product.brand.chainStore != null"> color:blue;</s:if>">
				         <td height="20" width="220">连锁店:<s:property value="uiBean.inventoryItem.chainStore.chain_name"/></td>
				         <td width="150">年份:<s:property value="uiBean.inventoryItem.productBarcode.product.year.year"/> <s:property value="uiBean.inventoryItem.productBarcode.product.quarter.quarter_Name"/></td>
				         <td width="150">品牌:<s:property value="uiBean.inventoryItem.productBarcode.product.brand.brand_Name"/></td>
				         <td ></td>
				       </tr>
				     </table></td>
			    </tr>
			    <tr>
			      <td colspan="7">
			            <!-- table to display the product information -->
						<table width="100%"  align="left" class="OuterTable" id="org_table">
						  <tr class="PBAInnerTableTitale" align='left'>
						    <th width="2%" height="35">&nbsp;</th>
						    <th width="5%">年份</th>
						    <th width="7%">品牌</th>
						    <th width="6%">货号</th>
						    <th width="8%">条码</th>
						    <th width="5%">库存量</th>
						    <th width="9%">库存成本金额</th>
						    <th width="9%">估算销售金额</th>
						  </tr>
						  <tbody id="orderTablebody">
						      <s:iterator value="uiBean.levelFourInventoryItem" status = "st" id="ci" >
						  		<tr class="InnerTableContent" id="orderRow0" class="InnerTableContent" <s:if test="#st.odd">style='background-color: rgb(255, 250, 208);'</s:if>>   
							      <td height="25"><s:property value="#st.index +1 + formBean.pager.firstResult"/></td>							      
							      <td align="center"><s:property value="#ci.productBarcode.product.year.year"/> <s:property value="#ci.productBarcode.product.quarter.quarter_Name"/></td>		
							      <td align="center"><s:property value="#ci.productBarcode.product.brand.brand_Name"/></td>
							      <td align="center"><s:property value="#ci.productBarcode.product.productCode"/> <s:property value="#ci.productBarcode.color.name"/></td>
							      <td align="center"><a href="#" onclick="traceInventory('<s:property value="#ci.productBarcode.barcode"/>', '')"><s:property value="#ci.productBarcode.barcode"/><img src="<%=request.getContextPath()%>/conf_files/web-image/search.png" border="0"/></a></td>					      
							      <td align="right"><s:property value="#ci.totalQuantity"/></td>
							      <td align="right"><s:if test="#session.LOGIN_CHAIN_USER.containFunction('purchaseAction!seeCost')"><s:text name="format.price"><s:param value="#ci.totalCostAmt"/></s:text></s:if><s:else>-</s:else></td>
							      <td align="right"><s:text name="format.price"><s:param value="#ci.totalSalesAmt"/></s:text></td>
							     </tr>
							   </s:iterator>  
						  </tbody>
						  <s:if test="uiBean.levelFourInventoryItem.size >0">
							  <tr class="PBAInnerTableTitale">
							    <td colspan="5" align="left">合计</td>
							    <td align="right"><s:property value="uiBean.inventoryItem.totalQuantity"/></td>
							    <td align="right"><s:if test="#session.LOGIN_CHAIN_USER.containFunction('purchaseAction!seeCost')">
							    						<s:property value="uiBean.inventoryItem.totalCostAmt"/>
							    				  </s:if><s:else>-</s:else>
							    </td>
							    <td align="right"><s:property value="uiBean.inventoryItem.totalSalesAmt"/></td>
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