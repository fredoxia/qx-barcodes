<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib prefix="s" uri="/struts-tags" %>
<%@ page import="com.onlineMIS.common.Common_util" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>千禧宝贝连锁店管理信息系统</title>
<%@ include file="../../common/Style.jsp"%>
<script>
$(document).ready(function(){
	$("#org_table tr").mouseover(function(){      
		$(this).addClass("over");}).mouseout(function(){    
		$(this).removeClass("over");});
});
function exportOrderToExcel(){
	var url = "<%=request.getContextPath()%>/actionChain/purchaseAction!exportPurchaseToExcel";
	document.inventoryOrderForm.action = url;
	document.inventoryOrderForm.submit();	
}
</script>
</head>
<body>
    <s:actionerror cssStyle="color:red"/>
    <s:form action="" method="POST" id="inventoryOrderForm"  name="inventoryOrderForm">
	<input type="hidden" name="formBean.order.order_ID" id="orderId" value="<s:property value="uiBean.order.order_ID"/>"/>
    <table width="90%" align="center"  class="OuterTable">
	    <tr><td>
			 <table width="100%" border="0">
			    <tr>
			       <td height="50" colspan="7">
				   	 <table width="100%" border="0">
				   	   <tr class="InnerTableContent">
				         <td width="85" height="25"><strong>编号/种类</strong></td>
				         <td><s:property value="uiBean.order.order_ID"/>/<s:property value="uiBean.order_type"/></td>
				       </tr>
				       <tr class="InnerTableContent">
				         <td height="25"><strong>客户名字</strong></td>
				         <td><s:property value="uiBean.order.client_name"/></td>
				       </tr>
				       <tr class="InnerTableContent">
				         <td height="25"><strong>上欠 / 下欠</strong></td>
				         <td><s:property value="uiBean.order.preAcctAmt"/> / <s:property value="uiBean.order.postAcctAmt"/></td>
				       </tr>
				       <tr class="InnerTableContent">
				         <td height="25"><strong>日期</strong></td>
				         <td><s:property value="uiBean.order.order_EndTime"/></td>
			           </tr>
			           <tr class="InnerTableContent">
				         <td height="25"><strong>备注</strong></td>
				         <td><s:property value="uiBean.order.comment"/></td>
			           </tr>
				     </table></td>
			    </tr>
			    <tr class="InnerTableContent">
			      <td height="4" colspan="7"><s:actionmessage/> <hr width="100%" color="#FFCC00"/></td>
			    </tr>
			    <tr>
			      <td colspan="7">
			            <!-- table to display the staff information -->
						<table width="99%" class="OuterTable" id="org_table">
						  <tr  class="PBAInnerTableTitale">
						    <th width="35" height="35">序号</th>
						    <th width="70">产品品牌</th>
						    <th width="70">产品货号</th>
						    <th width="50">颜色</th>
						    <th width="150">条形码</th>
						    <th width="40">年份</th>
						    <th width="40">季度</th>
						    <th width="50">单位</th>
						    <th width="40">数量</th>
						    <th width="60">成本单价</th>
						    <th width="40">折扣</th>
						    <th width="60">折后进价</th>
						    <th width="60">进价金额</th>
						    <th width="70">统一零售价</th>
						    <th width="60">零售金额</th>
						  </tr>
					      <s:iterator value="uiBean.order.product_List" status="st" id="orderProduct" >
							    <tr class="InnerTableContent" align="center" <s:if test="#st.even"> style='<%=Common_util.EVEN_ROW_BG_STYLE %>'</s:if>>	      
							      <td height="25"><s:property value="#st.index+1"/></td>
							      <td><s:property value="#orderProduct.productBarcode.product.brand.brand_Name"/></td>
							      <td><s:property value="#orderProduct.productBarcode.product.productCode"/></td>
							      <td><s:property value="#orderProduct.productBarcode.color.name"/></td>
							      <td><s:property value="#orderProduct.productBarcode.barcode"/></td>
							      <td><s:property value="#orderProduct.productBarcode.product.year.year"/></td>
							      <td><s:property value="#orderProduct.productBarcode.product.quarter.quarter_Name"/></td>
							      <td><s:property value="#orderProduct.productBarcode.product.unit"/></td>
							      <td><s:property value="#orderProduct.quantity"/></td>
							      <td><s:if test="#session.LOGIN_CHAIN_USER.containFunction('purchaseAction!seeCost')"><s:property value="#orderProduct.salePriceSelected"/></s:if><s:else>-</s:else></td>
						          <td><s:if test="#session.LOGIN_CHAIN_USER.containFunction('purchaseAction!seeCost')"><s:property value="#orderProduct.discount"/></s:if><s:else>-</s:else></td>
							      <td><s:if test="#session.LOGIN_CHAIN_USER.containFunction('purchaseAction!seeCost')"><s:property value="#orderProduct.wholeSalePrice"/></s:if><s:else>-</s:else></td>
							      <td><s:if test="#session.LOGIN_CHAIN_USER.containFunction('purchaseAction!seeCost')"><s:text name="format.price"><s:param value="#orderProduct.wholeSalePrice * #orderProduct.quantity"/></s:text></s:if><s:else>-</s:else></td>
							      <td><s:property value="#orderProduct.productBarcode.product.salesPrice"/></td>
							      <td><s:text name="format.price"><s:param value="#orderProduct.productBarcode.product.salesPrice * #orderProduct.quantity"/></s:text></td>
							    </tr>
					       </s:iterator>	  
					       <tr class="PBAOuterTableTitale" align="center">	      
						      <td height="25">汇总</td>
						      <td></td>
						      <td></td>
						      <td></td>
						      <td></td>
						      <td></td>
						      <td></td>
						      <td></td>
						      <td><s:property value="uiBean.order.totalQuantity"/></td>
						      <td></td>
						      <td></td>
						      <td></td>
						      <td><s:if test="#session.LOGIN_CHAIN_USER.containFunction('purchaseAction!seeCost')"><s:property value="uiBean.order.totalWholePrice"/></s:if><s:else>-</s:else></td>
						      <td></td>
						      <td><s:property value="uiBean.order.totalRetailPrice"/></td>
							</tr>
						</table>
			      </td>
			    </tr>
			    <tr class="InnerTableContent">
			      <td height="4" colspan="7"><hr width="100%" color="#FFCC00"/></td>
			    </tr>
			   <tr>
			       <td height="50" colspan="7">
				   	 <table width="100%" border="0">
				       <tr class="InnerTableContent">
				         <td height="25"><strong>优惠</strong> : <s:property value="uiBean.order.totalDiscount"/></td>
				         <td><input type="button" value="订单导出到Excel" onclick="exportOrderToExcel();"/></td>
				       </tr>
				     </table>
				    </td>
			    </tr>
			  </table>
	   </td></tr>
	 </table>
	 </s:form>
</body>
</html>