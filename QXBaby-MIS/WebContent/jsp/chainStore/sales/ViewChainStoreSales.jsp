<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib prefix="s" uri="/struts-tags" %>
<%@ page import="com.onlineMIS.common.Common_util" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=8" /> 
<title>千禧宝贝连锁店管理信息系统</title>
<%@ include file="../../common/Style.jsp"%>
<script type=text/javascript src="<%=request.getContextPath()%>/conf_files/js/ChainInvenTrace.js"></script>
<script type=text/javascript src="<%=request.getContextPath()%>/conf_files/js/print/printPage.js"></script>

<script>
$(document).ready(function(){
	parent.$.messager.progress('close');
});

var baseurl = "<%=request.getContextPath()%>";

/**
 * delete the sales order
 */
function cancelOrder(){
	var orderId = $("#orderId").val();
	formSubmit = true;
	location.href = "chainSalesJSPAction!cancelOrder?formBean.chainSalesOrder.id="+ orderId;
}

/**
 * delete the sales order
 */
function copyOrder(){
	var orderId = $("#orderId").val();
	formSubmit = true;
	location.href = "chainSalesJSPAction!copyOrder?formBean.chainSalesOrder.id="+ orderId;
}

/**
 * 再次打印小票
 */
function printOrder(){
	var orderId = $("#orderId").val();
	
	var params="formBean.chainSalesOrder.id=" + orderId;
	$.post("<%=request.getContextPath()%>/actionChain/chainSalesJSONAction!getSalesOrderById",params, backProcessGetChainSalesOrder,"json");
}

function backProcessGetChainSalesOrder(data){
	var response = data.response;
	var returnCode = response.returnCode;
	if (returnCode != SUCCESS){
		alert(response.message);
	} else {
		var salesOrder = response.returnValue;
		try {
			printSalesOrder(salesOrder);
        } catch (e){
			alert("小票打印出现问题 ,请联系总部管理员");
        }
	}
}

function getProductInfor(barcode, chainId){

	$.modalDialog({
		title : '查看产品信息',
		width : 500,
		height : 300,
		href : 'chainSalesJSPAction!viewProduct?formBean.barcode=' + barcode + '&formBean.chainId=' + chainId,
		buttons : [ {
			text : '关闭窗口',
			handler : function() {
					$.modalDialog.handler.dialog('close');
			}
		} ]
	});
}

</script>
</head>
<body>
<input type="hidden" id="orderId" value="<s:property value="uiBean.chainSalesOrder.id"/>"/>
<s:hidden id="address" name="uiBean.chainStoreConf.address"/>
<s:hidden name="uiBean.chainStoreConf.hideDiscountPrint" id="hideDiscountPrint"/>
<div class="errorAndmes"><s:actionerror cssStyle="color:red"/><s:actionmessage cssStyle="color:blue"/></div>
<table width="86%" align="center"  class="OuterTable">
	    <tr><td>
			 <table width="100%" border="0">
			    <tr>
			       <td height="50" colspan="14">
				   	 <table width="100%" border="0">
				       <tr class="PBAOuterTableTitale">
				         <td height="32" colspan="10">连锁店零售单据 - 
				              <s:if test="uiBean.chainSalesOrder.status == 3">
				              		 <font style="color:red"><s:property value="uiBean.chainSalesOrder.statusS"/></font>
				              </s:if><s:else>
				                     <s:property value="uiBean.chainSalesOrder.statusS"/>
				              </s:else>
				         </td>
			           </tr>
				       <tr class="InnerTableContent">
				         <td width="220" height="25">连锁店：<s:property value="uiBean.chainSalesOrder.chainStore.chain_name"/></td>
				         <td width="170">经手人：<s:property value="uiBean.chainSalesOrder.saler.user_name"/></td>
				         <td width="180">创建人:<s:property value="uiBean.chainSalesOrder.creator.user_name"/></td>
				         <td width="220">单据日期:<s:property value="uiBean.chainSalesOrder.orderDate"/><s:hidden name="uiBean.chainSalesOrder.id"/></td>
				         <td width="230"></td>
				       </tr>
				       <tr class="InnerTableContent">
				         <td height="20" colspan="2">VIP卡 : <s:property value="uiBean.chainSalesOrder.vipCard.vipCardNo"/></td>
				         <td></td>
				         <td></td>
				         <td></td>
				       </tr>
				     </table></td>
			    </tr>
			    <tr class="InnerTableContent">
			      <td colspan="14"><hr width="100%" color="#FFCC00"/></td>
			    </tr>
			   <s:if test="uiBean.chainSalesOrder.productList.size > 0">
				   <tr class="PBAInnerTableTitale" >
				      <td colspan="14">
				                <font size="2px">销售货品</font>
				      </td>
				    </tr>
				    <tr class="PBAOuterTableTitale">
						    <th width="21">&nbsp;</th>
						    <th width="110">条形码</th>
						    <th width="110">货号/名称</th>
						    <th width="45">颜色</th>
						    <th width="110">品牌</th>
						    <th width="70">季度</th>
						    <th width="45">单位</th>
						    <th width="39">数量</th>
						    <th width="60">单价</th>
						    <th width="60">金额</th>
						    <th width="50">折扣</th>
						    <th width="60">折后单价</th>
						    <th width="60">折后金额</th>
						    <th></th>
					 </tr>
				         <s:iterator value="uiBean.chainSalesOrder.productList" status = "st" id="orderProduct" >
					         <tr align="center" class="InnerTableContent"  <s:if test="#st.even"> style='<%=Common_util.EVEN_ROW_BG_STYLE %>'</s:if>>   
						      <td height="25"><s:property value="#st.index +1 "/></td>
						      <td><a href="#" onclick="traceInventory('<s:property value="#orderProduct.productBarcode.barcode"/>', '<s:property value="uiBean.chainSalesOrder.chainStore.chain_id"/>')"><s:property value="#orderProduct.productBarcode.barcode"/></a></td>
						      <td><s:property value="#orderProduct.productBarcode.product.productCode"/></td>
						      <td><s:property value="#orderProduct.productBarcode.color.name"/></td>
						      <td><s:property value="#orderProduct.productBarcode.product.brand.brand_Name"/></td>
						      <td><s:property value="#orderProduct.productBarcode.product.year.year"/> <s:property value="#orderProduct.productBarcode.product.quarter.quarter_Name"/></td>
						      <td><s:property value="#orderProduct.productBarcode.product.unit"/></td>
						      <td><s:property value="#orderProduct.quantity"/></td>
						      <td><s:property value="#orderProduct.retailPrice"/></td>
						      <td><s:property value="#orderProduct.retailPrice * #orderProduct.quantity"/></td>
						      <td><s:property value="#orderProduct.discountRate"/></td>
						      <td><s:text name="format.totalPrice">
						       			<s:param value="#orderProduct.retailPrice * #orderProduct.discountRate"/>
						       	  </s:text></td>
						      <td><s:text name="format.totalPrice">
						                <s:param value="#orderProduct.retailPrice * #orderProduct.discountRate * #orderProduct.quantity"/>
						          </s:text></td>
						      <td><s:if test="#session.LOGIN_CHAIN_USER.containFunction('chainSalesJSPAction!viewProduct')"><img src="<%=request.getContextPath()%>/conf_files/web-image/search.png" border="0"  onclick="getProductInfor('<s:property value="#orderProduct.productBarcode.barcode"/>','<s:property value="uiBean.chainSalesOrder.chainStore.chain_id"/>');" style="cursor:pointer;"/></s:if></td>
						     </tr>  
					     </s:iterator> 
					  <tr class="PBAOuterTableTitale">
					    <th height="25" colspan="7" align='left'>合计</th>
					    <th align='center'><s:property value="uiBean.chainSalesOrder.totalQuantity"/></th>
					    <th></th>
					    <th align='center'><s:property value="uiBean.chainSalesOrder.totalAmount"/></th>
					    <th></th>
					    <th></th>
					    <th align='center'><s:property value="uiBean.chainSalesOrder.netAmount"/></th>
					    <th></th>
					  </tr>
				    <tr class="InnerTableContent">
				      <td colspan="14"><hr width="100%" color="#FFCC00"/></td>
				    </tr>
			    </s:if>
			    <s:if test="uiBean.chainSalesOrder.productListR.size > 0">
				    <tr class="PBAOuterTableTitale">
				      <td colspan="14" class="PBAInnerTableTitale" >
				                          <font size="2px"> 退回货品</font>
				      </td>
				    </tr>
				    <tr align='left' class="PBAOuterTableTitale">
							    <th>&nbsp;</th>
							    <th>条形码</th>
						    	<th>货号/名称</th>
						    	<th>颜色</th>
						   	    <th>品牌</th>
						   	    <th>季度</th>
							    <th>单位</th>
							    <th>数量</th>
							    <th>单价</th>
							    <th>金额</th>
							    <th>折扣</th>
							    <th>折后单价</th>
							    <th>折后金额</th>
							    <th></th>
							  </tr>
							  <tbody id="orderTablebody">
							         <s:iterator value="uiBean.chainSalesOrder.productListR" status = "st" id="orderProduct" >
								         <tr align="center" class="InnerTableContent"  <s:if test="#st.even"> style='<%=Common_util.EVEN_ROW_BG_STYLE %>'</s:if>>   
									      <td height="25"><s:property value="#st.index +1 "/></td>
									      <td><a href="#" onclick="traceInventory('<s:property value="#orderProduct.productBarcode.barcode"/>', '<s:property value="uiBean.chainSalesOrder.chainStore.chain_id"/>')"><s:property value="#orderProduct.productBarcode.barcode"/></a></td>
									      <td><s:property value="#orderProduct.productBarcode.product.productCode"/></td>
									      <td><s:property value="#orderProduct.productBarcode.color.name"/></td>
									      <td><s:property value="#orderProduct.productBarcode.product.brand.brand_Name"/></td>
									      <td><s:property value="#orderProduct.productBarcode.product.year.year"/> <s:property value="#orderProduct.productBarcode.product.quarter.quarter_Name"/></td>
									      <td><s:property value="#orderProduct.productBarcode.product.unit"/></td>			      
									      <td><s:property value="#orderProduct.quantity"/></td>
									      <td><s:property value="#orderProduct.retailPrice"/></td>
									      <td><s:property value="#orderProduct.retailPrice * #orderProduct.quantity"/></td>
									      <td><s:property value="#orderProduct.discountRate"/></td>
									      <td><s:text name="format.totalPrice">
									               <s:param value="#orderProduct.retailPrice * #orderProduct.discountRate"/>
									          </s:text></td>
									      <td><s:text name="format.totalPrice">
									      		   <s:param value="#orderProduct.retailPrice * #orderProduct.discountRate * #orderProduct.quantity"/>
									          </s:text></td>
									      <td><s:if test="#session.LOGIN_CHAIN_USER.containFunction('chainSalesJSPAction!viewProduct')"><img src="<%=request.getContextPath()%>/conf_files/web-image/search.png" border="0"  onclick="getProductInfor('<s:property value="#orderProduct.productBarcode.barcode"/>');" style="cursor:pointer;"/></s:if></td>
									     </tr>  
								     </s:iterator> 
							  </tbody>
							  <tr class="PBAOuterTableTitale">
							    <th height="25" colspan="7" align='left'>合计</th>
							    <th align='center'><s:property value="uiBean.chainSalesOrder.totalQuantityR"/></th>
							    <th></th>
							    <th align='center'><s:property value="uiBean.chainSalesOrder.totalAmountR"/></th>
							    <th></th>
							    <th></th>
							    <th align='center'><s:property value="uiBean.chainSalesOrder.netAmountR"/></th>
							    <th></th>
							  </tr>			    
				    <tr class="InnerTableContent">
				      <td height="4" colspan="14"><hr width="100%" color="#FFCC00"/></td>
				    </tr>
			    </s:if>
			    <s:if test="uiBean.chainSalesOrder.productListF.size > 0">
				    <tr class="PBAOuterTableTitale">
				      <td colspan="14" class="PBAInnerTableTitale" >
				                          <font size="2px">赠品</font>
				      </td>
				    </tr>
				    <tr class="PBAOuterTableTitale" align='left'>
							    <th>&nbsp;</th>
							    <th>条形码</th>
							    <th>货号/名称</th>
							    <th>颜色</th>
							    <th>品牌</th>
							    <th>季度</th>
							    <th>单位</th>
							    <th>数量</th>
							    <th></th>
							    <th colspan="4"></th>
							  </tr>
							  <tbody id="orderTablebody">
							         <s:iterator value="uiBean.chainSalesOrder.productListF" status = "st" id="orderProduct" >
								         <tr align="center" class="InnerTableContent"  <s:if test="#st.even"> style='<%=Common_util.EVEN_ROW_BG_STYLE %>'</s:if>>   
									      <td height="25"><s:property value="#st.index +1 "/></td>
									      <td><a href="#" onclick="traceInventory('<s:property value="#orderProduct.productBarcode.barcode"/>', '<s:property value="uiBean.chainSalesOrder.chainStore.chain_id"/>')"><s:property value="#orderProduct.productBarcode.barcode"/></a></td>
									      <td><s:property value="#orderProduct.productBarcode.product.productCode"/></td>
									      <td><s:property value="#orderProduct.productBarcode.color.name"/></td>
									      <td><s:property value="#orderProduct.productBarcode.product.brand.brand_Name"/></td>
									      <td><s:property value="#orderProduct.productBarcode.product.year.year"/> <s:property value="#orderProduct.productBarcode.product.quarter.quarter_Name"/></td>
									      <td><s:property value="#orderProduct.productBarcode.product.unit"/></td> 
									      <td><s:property value="#orderProduct.quantity"/></td>
									      <td><s:if test="#session.LOGIN_CHAIN_USER.containFunction('chainSalesJSPAction!viewProduct')"><img src="<%=request.getContextPath()%>/conf_files/web-image/search.png" border="0"  onclick="getProductInfor('<s:property value="#orderProduct.productBarcode.barcode"/>','<s:property value="uiBean.chainSalesOrder.chainStore.chain_id"/>');" style="cursor:pointer;"/></s:if></td>
									      <td colspan="5"></td>
									     </tr>  
								     </s:iterator> 
							  </tbody>
							  <tr class="PBAOuterTableTitale">
							    <th height="25" colspan="7" align='left'>合计</th>
							    <th align='center'><s:property value="uiBean.chainSalesOrder.totalQuantityF"/></th>
							    <th colspan="6"></th>
							  </tr>
					<tr class="InnerTableContent">
				      <td height="4" colspan="14"><hr width="100%" color="#FFCC00"/></td>
				    </tr>
			    </s:if>
			    <tr class="InnerTableContent">
			      <td height="30" colspan="14">				  
				  	    <%@ include file="../include/ChainSSalesOrderDisplayFooter.jsp"%>
				  </td>
			    </tr>
			  </table>
	   </td></tr>
	 </table>
	 
	 <s:if test="uiBean.chainStoreConf.printTemplate == 2">
		<%@include file="../include/Print2.jsp"%>
	</s:if><s:else>
		<%@include file="../include/Print.jsp"%>
	</s:else>

</body>
</html>