<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
		            <!-- table to display the product information -->
					      <tr class="PBAOuterTableTitale" align='left'>
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
						    <th>库存</th>
						    <th></th>
						    <th></th>
						  </tr>
						  <tbody id="orderTablebodyR">
						         <s:iterator value="formBean.chainSalesOrder.productListR" status = "st" id="orderProduct" >
							         <tr class="InnerTableContent" id="orderRowR<s:property value="#st.index"/>">   
								      <td><s:property value="#st.index +1 "/></td>
								      <td><input type="text" name="formBean.chainSalesOrder.productListR[<s:property value="#st.index"/>].productBarcode.barcode" id="barcodeR<s:property value="#st.index"/>" value="<s:property value="#orderProduct.productBarcode.barcode"/>" size="11"/>
								          <input type="hidden" name="formBean.chainSalesOrder.productListR[<s:property value="#st.index"/>].productBarcode.id" id="productIdR<s:property value="#st.index"/>" value="<s:property value="#orderProduct.productBarcode.id"/>"/>
								          <input type="hidden" name="formBean.chainSalesOrder.productListR[<s:property value="#st.index"/>].type" id="typeR<s:property value="#st.index"/>" value="<s:property value="#orderProduct.type"/>" size="13"/></td>
								      <td><input type="text" name="formBean.chainSalesOrder.productListR[<s:property value="#st.index"/>].productBarcode.product.productCode" id="productCodeR<s:property value="#st.index"/>" value="<s:property value="#orderProduct.productBarcode.product.productCode"/>" size="9"/></td>
								      <td><input type="text" name="formBean.chainSalesOrder.productListR[<s:property value="#st.index"/>].productBarcode.color.name" id="colorR<s:property value="#st.index"/>" value="<s:property value="#orderProduct.productBarcode.color.name"/>" size="4" readonly/></td>
								      <td><input type="text" name="formBean.chainSalesOrder.productListR[<s:property value="#st.index"/>].productBarcode.product.brand.brand_Name" id="brandR<s:property value="#st.index"/>" value="<s:property value="#orderProduct.productBarcode.product.brand.brand_Name"/>"  size="9" readonly/></td>
								      <td><input type="text" name="formBean.chainSalesOrder.productListR[<s:property value="#st.index"/>].productBarcode.product.quarter.quarter_Name" id="quarterR<s:property value="#st.index"/>" value="<s:property value="#orderProduct.productBarcode.product.year.year"/> <s:property value="#orderProduct.productBarcode.product.quarter.quarter_Name"/>"  size="6" readonly/></td>
								      <td><input type="text" name="formBean.chainSalesOrder.productListR[<s:property value="#st.index"/>].productBarcode.product.unit" id="unitR<s:property value="#st.index"/>" value="<s:property value="#orderProduct.productBarcode.product.unit"/>" size="4" readonly/></td>

								      <td><input type="text" name="formBean.chainSalesOrder.productListR[<s:property value="#st.index"/>].quantity" id="quantityR<s:property value="#st.index"/>" value="<s:property value="#orderProduct.quantity"/>"  size="2" onfocus="this.select();" onkeyup="changeRowValue('R',<s:property value="#st.index"/>)"  onkeypress='return is_number(event);'/></td>
								      <td><input type="text" name="formBean.chainSalesOrder.productListR[<s:property value="#st.index"/>].retailPrice" id="retailPriceR<s:property value="#st.index"/>" value="<s:property value="#orderProduct.retailPrice"/>"  size="2" readonly/></td>
								      <td><input type="text" id="amountR<s:property value="#st.index"/>" readonly value="<s:property value="#orderProduct.retailPrice * #orderProduct.quantity"/>"  size="2"/></td>
								      <td><input type="text" name="formBean.chainSalesOrder.productListR[<s:property value="#st.index"/>].discountRate" id="discountRateR<s:property value="#st.index"/>" value="<s:property value="#orderProduct.discountRate"/>"  size="2"  onfocus="this.select();" onkeyup="changeRowValue('R',<s:property value="#st.index"/>)"/></td>
								      <td><input type="text" id="dicountPriceR<s:property value="#st.index"/>" onfocus="this.select();"  onkeyup='changeDiscountPrice(3,<s:property value="#st.index"/>)' value="<s:text name="format.price"><s:param value="#orderProduct.retailPrice * #orderProduct.discountRate"/></s:text>" size="4"/></td>
								      <td><input type="text" id="discountAmountR<s:property value="#st.index"/>" readonly  value="<s:text name="format.price"><s:param value="#orderProduct.retailPrice * #orderProduct.discountRate * #orderProduct.quantity"/></s:text>" size="2"/></td>
								      <td><input type="text" id="inventoryR<s:property value="#st.index"/>" readonly size="1" value="<s:property value="#orderProduct.inventoryLevel"/>"/></td>
								      <td></td>
								      <td><div id="delIconR<s:property value="#st.index"/>" style="display:block"><img src="<%=request.getContextPath()%>/conf_files/web-image/delete.png" border="0"  onclick="deleteRow('orderRowR<s:property value="#st.index"/>');" style="cursor:pointer;"/></div></td>
								     </tr>  
							     </s:iterator>
							     <tr class="InnerTableContent" id="orderRowR<s:property value='formBean.chainSalesOrder.productListR.size()'/>">   
							      <td><s:property value='formBean.chainSalesOrder.productListR.size() + 1'/></td>
							      <td><input type="text" name="formBean.chainSalesOrder.productListR[<s:property value='formBean.chainSalesOrder.productListR.size()'/>].productBarcode.barcode" id="barcodeR<s:property value='formBean.chainSalesOrder.productListR.size()'/>" size="11"/>
							          <input type="hidden" name="formBean.chainSalesOrder.productListR[<s:property value='formBean.chainSalesOrder.productListR.size()'/>].productBarcode.id" id="productIdR<s:property value='formBean.chainSalesOrder.productListR.size()'/>"/>
							          <input type="hidden" name="formBean.chainSalesOrder.productListR[<s:property value='formBean.chainSalesOrder.productListR.size()'/>].type" id="typeR<s:property value='formBean.chainSalesOrder.productListR.size()'/>" size="13" value="2"/></td>
							      <td><input type="text" name="formBean.chainSalesOrder.productListR[<s:property value='formBean.chainSalesOrder.productListR.size()'/>].productBarcode.product.productCode" id="productCodeR<s:property value='formBean.chainSalesOrder.productListR.size()'/>" size="9"/></td>
							      <td><input type="text" name="formBean.chainSalesOrder.productListR[<s:property value='formBean.chainSalesOrder.productListR.size()'/>].productBarcode.color.name" id="colorR<s:property value='formBean.chainSalesOrder.productListR.size()'/>" size="4" readonly/></td>
							      <td><input type="text" name="formBean.chainSalesOrder.productListR[<s:property value='formBean.chainSalesOrder.productListR.size()'/>].productBarcode.product.brand.brand_Name" id="brandR<s:property value='formBean.chainSalesOrder.productListR.size()'/>"  size="9" readonly/></td>
							      <td><input type="text" name="formBean.chainSalesOrder.productListR[<s:property value='formBean.chainSalesOrder.productListR.size()'/>].productBarcode.product.quarter.quarter_Name" id="quarterR<s:property value='formBean.chainSalesOrder.productListR.size()'/>"  size="6" readonly/></td>
							      <td><input type="text" name="formBean.chainSalesOrder.productListR[<s:property value='formBean.chainSalesOrder.productListR.size()'/>].productBarcode.product.unit" id="unitR<s:property value='formBean.chainSalesOrder.productListR.size()'/>" size="4" readonly/></td>
							      <td><input type="text" name="formBean.chainSalesOrder.productListR[<s:property value='formBean.chainSalesOrder.productListR.size()'/>].quantity" id="quantityR<s:property value='formBean.chainSalesOrder.productListR.size()'/>"  size="2" onfocus="this.select();" onkeyup="changeRowValue('R',<s:property value='formBean.chainSalesOrder.productListR.size()'/>)"  onkeypress='return is_number(event);'/></td>
							      <td><input type="text" name="formBean.chainSalesOrder.productListR[<s:property value='formBean.chainSalesOrder.productListR.size()'/>].retailPrice" id="retailPriceR<s:property value='formBean.chainSalesOrder.productListR.size()'/>" readonly size="2"/></td>
							      <td><input type="text" id="amountR<s:property value='formBean.chainSalesOrder.productListR.size()'/>" readonly  size="2"/></td>
							      <td><input type="text" name="formBean.chainSalesOrder.productListR[<s:property value='formBean.chainSalesOrder.productListR.size()'/>].discountRate" id="discountRateR<s:property value='formBean.chainSalesOrder.productListR.size()'/>"  size="2" onfocus="this.select();" onkeyup="changeRowValue('R',<s:property value='formBean.chainSalesOrder.productListR.size()'/>)"/></td>
							      <td><input type="text" id="dicountPriceR<s:property value='formBean.chainSalesOrder.productListR.size()'/>" onfocus="this.select();"  onkeyup='changeDiscountPrice(3,<s:property value='formBean.chainSalesOrder.productListR.size()'/>)' size="4"/></td>
							      <td><input type="text" id="discountAmountR<s:property value='formBean.chainSalesOrder.productListR.size()'/>" readonly size="4"/></td>
							      <td><input type="text" id="inventoryR<s:property value="formBean.chainSalesOrder.productListR.size()"/>" readonly size="1"/></td>
							      <td></td>
							      <td><div id="delIconR<s:property value='formBean.chainSalesOrder.productListR.size()'/>" style="display:none"><img src="<%=request.getContextPath()%>/conf_files/web-image/delete.png" border="0"  onclick="deleteRow('orderRowR<s:property value='formBean.chainSalesOrder.productListR.size()'/>');" style="cursor:pointer;"/></div></td>
							     </tr>  
						  </tbody>
						  <tr class="PBAOuterTableTitale">
						    <th colspan="7" align='left'></th>
						    <th align='left'><s:textfield name="formBean.chainSalesOrder.totalQuantityR" id="totalQuantityR" readonly="true" size="2"/></th>
						    <th></th>
						    <th align='left'><s:textfield name="formBean.chainSalesOrder.totalAmountR" id="totalAmountR" readonly="true" size="2"/></th>
						    <th></th>
						    <th></th>
						    <th align='left'><s:textfield name="formBean.chainSalesOrder.netAmountR" id="netAmountR" readonly="true" size="4"/></th>
						    <th></th>
						    <th></th>
						    <th></th>
						  </tr>