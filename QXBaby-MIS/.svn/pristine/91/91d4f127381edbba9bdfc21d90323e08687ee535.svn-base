<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<input type="hidden" id="orderId" value="<s:property value="uiBean.chainSalesOrder.id"/>"/>
		            <!-- table to display the product information -->
						<table width="100%"  align="left" class="OuterTable" id="org_table">
						  <tr class="PBAInnerTableTitale" align='left'>
						    <th width="21">&nbsp;</th>
						    <th width="110" height="35">条形码</th>
						    <th width="110">货号/名称</th>
						    <th width="130">品牌</th>
						    <th width="45">单位</th>
						    <th width="45">颜色</th>
						    <th width="39">数量</th>
						    <th width="60">单价</th>
						    <th width="60">金额</th>
						    <th width="50">折扣</th>
						    <th width="60">折后单价</th>
						    <th width="60">折后金额</th>
						    <th width="130">摘要</th>
						    <th></th>
						  </tr>
						  <tbody id="orderTablebody">
						         <s:iterator value="uiBean.chainSalesOrder.productList" status = "st" id="orderProduct" >
							         <tr class="InnerTableContent"  <s:if test="#st.even"> style='<%=Common_util.EVEN_ROW_BG_STYLE %>'</s:if>>   
								      <td height="25"><s:property value="#st.index +1 "/></td>
								      <td><s:property value="#orderProduct.productBarcode.barcode"/></td>
								      <td><s:property value="#orderProduct.productBarcode.product.productCode"/></td>
								      <td><s:property value="#orderProduct.productBarcode.product.brand.brand_Name"/></td>
								      <td><s:property value="#orderProduct.productBarcode.product.unit"/></td>
								      <td><s:property value="#orderProduct.productBarcode.color.name"/></td>
								      <td><s:property value="#orderProduct.quantity"/></td>
								      <td><s:property value="#orderProduct.productBarcode.product.salesPrice"/></td>
								      <td><s:property value="#orderProduct.productBarcode.product.salesPrice * #orderProduct.quantity"/></td>
								      <td><s:property value="#orderProduct.discountRate"/></td>
								      <td><s:text name="format.price"><s:param value="#orderProduct.productBarcode.product.salesPrice * #orderProduct.discountRate"/></s:text></td>
								      <td><s:text name="format.price"><s:param value="#orderProduct.productBarcode.product.salesPrice * #orderProduct.discountRate * #orderProduct.quantity"/></s:text></td>
								      <td><s:property value="#orderProduct.memo"/></td>
								      <td><s:if test="#session.LOGIN_CHAIN_USER.containFunction('chainSalesJSPAction!viewProduct')"><img src="<%=request.getContextPath()%>/conf_files/web-image/search.png" border="0"  onclick="getProductInfor('<s:property value="#orderProduct.productBarcode.product.barcode"/>');" style="cursor:pointer;"/></s:if></td>
								     </tr>  
							     </s:iterator> 
						  </tbody>
						  <tr class="PBAInnerTableTitale">
						    <th height="25" colspan="6" align='left'>合计</th>
						    <th align='left'><s:property value="uiBean.chainSalesOrder.totalQuantity"/></th>
						    <th></th>
						    <th align='left'><s:property value="uiBean.chainSalesOrder.totalAmount"/></th>
						    <th></th>
						    <th></th>
						    <th align='left'><s:property value="uiBean.chainSalesOrder.netAmount"/></th>
						    <th></th>
						    <th></th>
						  </tr>
					</table>