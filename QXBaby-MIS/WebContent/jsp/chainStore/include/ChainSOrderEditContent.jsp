<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
		            <!-- table to display the product information -->
						
						  <tr class="PBAOuterTableTitale" align='left'>
						    <th width="20">&nbsp;</th>
						    <th width="90">条形码</th>
						    <th width="60">货号/名称</th>
						    <th width="45">颜色</th>
						    <th width="60">品牌</th>
						    <th width="40">季度</th>
						    <th width="45">单位</th>
						    <th width="39">数量</th>
						    <th width="45">单价</th>
						    <th width="45">金额</th>
						    <th width="50">折扣</th>
						    <th width="60">折后单价</th>
						    <th width="60">折后金额</th>
						    <th width="40">库存</th>
						    <th width="40"></th>
						    <th></th>
						  </tr>
						  <tbody id="orderTablebody">
						         <s:iterator value="formBean.chainSalesOrder.productList" status = "st" id="orderProduct" >
							         <tr class="InnerTableContent" id="orderRow<s:property value="#st.index"/>">   
								      <td><s:property value="#st.index +1 "/></td>
								      <td><input type="text" name="formBean.chainSalesOrder.productList[<s:property value="#st.index"/>].productBarcode.barcode" id="barcode<s:property value="#st.index"/>" value="<s:property value="#orderProduct.productBarcode.barcode"/>" size="11"/>
								          <input type="hidden" name="formBean.chainSalesOrder.productList[<s:property value="#st.index"/>].productBarcode.id" id="productId<s:property value="#st.index"/>" value="<s:property value="#orderProduct.productBarcode.id"/>"/></td>
								      <td><input type="text" name="formBean.chainSalesOrder.productList[<s:property value="#st.index"/>].productBarcode.product.productCode" id="productCode<s:property value="#st.index"/>" value="<s:property value="#orderProduct.productBarcode.product.productCode"/>" size="9"/></td>
								      <td><input type="text" name="formBean.chainSalesOrder.productList[<s:property value="#st.index"/>].productBarcode.color.name" id="color<s:property value="#st.index"/>" value="<s:property value="#orderProduct.productBarcode.color.name"/>" size="4" readonly/></td>
								      <td><input type="text" name="formBean.chainSalesOrder.productList[<s:property value="#st.index"/>].productBarcode.product.brand.brand_Name" id="brand<s:property value="#st.index"/>" value="<s:property value="#orderProduct.productBarcode.product.brand.brand_Name"/>"  size="9" readonly/></td>
								      <td><input type="text" name="formBean.chainSalesOrder.productList[<s:property value="#st.index"/>].productBarcode.product.quarter.quarter_Name" id="quarter<s:property value="#st.index"/>" value="<s:property value="#orderProduct.productBarcode.product.year.year"/> <s:property value="#orderProduct.productBarcode.product.quarter.quarter_Name"/>"  size="6" readonly/></td>
								      <td><input type="text" name="formBean.chainSalesOrder.productList[<s:property value="#st.index"/>].productBarcode.product.unit" id="unit<s:property value="#st.index"/>" value="<s:property value="#orderProduct.productBarcode.product.unit"/>" size="4" readonly/></td>
								      <td><input type="text" name="formBean.chainSalesOrder.productList[<s:property value="#st.index"/>].quantity" id="quantity<s:property value="#st.index"/>" value="<s:property value="#orderProduct.quantity"/>"  size="2" onkeyup="changeRowValue('',<s:property value="#st.index"/>)" onfocus="this.select();"  onkeypress='return is_number(event);'/></td>
								      <td><input type="text" name="formBean.chainSalesOrder.productList[<s:property value="#st.index"/>].retailPrice" id="retailPrice<s:property value="#st.index"/>" value="<s:property value="#orderProduct.retailPrice"/>"  size="2" readonly/></td>
								      <td><input type="text" id="amount<s:property value="#st.index"/>" readonly value="<s:property value="#orderProduct.retailPrice * #orderProduct.quantity"/>"  size="2"/></td>
								      <td><input type="text" name="formBean.chainSalesOrder.productList[<s:property value="#st.index"/>].discountRate" id="discountRate<s:property value="#st.index"/>" value="<s:property value="#orderProduct.discountRate"/>"  size="2"  onfocus="this.select();" onkeyup="changeRowValue('',<s:property value="#st.index"/>)"/></td>
								      <td><input type="text" id="dicountPrice<s:property value="#st.index"/>"  onfocus="this.select();" onkeyup='changeDiscountPrice(1,<s:property value="#st.index"/>)' value="<s:text name="format.totalPrice"><s:param value="#orderProduct.retailPrice * #orderProduct.discountRate"/></s:text>" size="4"/></td>
								      <td><input type="text" id="discountAmount<s:property value="#st.index"/>" readonly  value="<s:text name="format.totalPrice"><s:param value="#orderProduct.retailPrice * #orderProduct.discountRate * #orderProduct.quantity"/></s:text>" size="4"/></td>
								      <td><input type="text" id="inventory<s:property value="#st.index"/>" value="<s:property value="#orderProduct.inventoryLevel"/>" readonly size="1"/></td>
								      <td><select id="normalPrice<s:property value="#st.index"/>" name="formBean.chainSalesOrder.productList[<s:property value="#st.index"/>].normalSale"  onchange='changeSelect();'>
								               <option value="1" <s:if test="#orderProduct.normalSale == 1">selected</s:if>>正价</option>
								               <option value="2" <s:if test="#orderProduct.normalSale == 2">selected</s:if>>特价</option>   
								          </select></td>
								      <td><div id="delIcon<s:property value="#st.index"/>" style="display:block"><img src="<%=request.getContextPath()%>/conf_files/web-image/delete.png" border="0"  onclick="deleteRow('orderRow<s:property value="#st.index"/>');" style="cursor:pointer;"/></div></td>
								     </tr>  
							     </s:iterator>
							     <tr class="InnerTableContent" id="orderRow<s:property value='formBean.chainSalesOrder.productList.size()'/>">   
							      <td><s:property value='formBean.chainSalesOrder.productList.size() + 1'/></td>
							      <td><input type="text" name="formBean.chainSalesOrder.productList[<s:property value='formBean.chainSalesOrder.productList.size()'/>].productBarcode.barcode" id="barcode<s:property value='formBean.chainSalesOrder.productList.size()'/>" size="11"/>
							          <input type="hidden" name="formBean.chainSalesOrder.productList[<s:property value='formBean.chainSalesOrder.productList.size()'/>].productBarcode.id" id="productId<s:property value='formBean.chainSalesOrder.productList.size()'/>"/></td>
							      <td><input type="text" name="formBean.chainSalesOrder.productList[<s:property value='formBean.chainSalesOrder.productList.size()'/>].productBarcode.product.productCode" id="productCode<s:property value='formBean.chainSalesOrder.productList.size()'/>" size="9"/></td>
							      <td><input type="text" name="formBean.chainSalesOrder.productList[<s:property value='formBean.chainSalesOrder.productList.size()'/>].productBarcode.color.name" id="color<s:property value='formBean.chainSalesOrder.productList.size()'/>" size="4" readonly/></td>
							      <td><input type="text" name="formBean.chainSalesOrder.productList[<s:property value='formBean.chainSalesOrder.productList.size()'/>].productBarcode.product.brand.brand_Name" id="brand<s:property value='formBean.chainSalesOrder.productList.size()'/>"  size="9" readonly/></td>
							      <td><input type="text" name="formBean.chainSalesOrder.productList[<s:property value='formBean.chainSalesOrder.productList.size()'/>].productBarcode.product.quarter.quarter_Name" id="quarter<s:property value='formBean.chainSalesOrder.productList.size()'/>"  size="6" readonly/></td>
							      <td><input type="text" name="formBean.chainSalesOrder.productList[<s:property value='formBean.chainSalesOrder.productList.size()'/>].productBarcode.product.unit" id="unit<s:property value='formBean.chainSalesOrder.productList.size()'/>" size="4" readonly/></td>
							      <td><input type="text" name="formBean.chainSalesOrder.productList[<s:property value='formBean.chainSalesOrder.productList.size()'/>].quantity" id="quantity<s:property value='formBean.chainSalesOrder.productList.size()'/>"  size="2" onfocus="this.select();" onkeyup="changeRowValue('',<s:property value='formBean.chainSalesOrder.productList.size()'/>)"  onkeypress='return is_number(event);'/></td>
							      <td><input type="text" name="formBean.chainSalesOrder.productList[<s:property value='formBean.chainSalesOrder.productList.size()'/>].retailPrice" id="retailPrice<s:property value='formBean.chainSalesOrder.productList.size()'/>"  size="2" readonly /></td>
							      <td><input type="text" id="amount<s:property value='formBean.chainSalesOrder.productList.size()'/>" readonly  size="2"/></td>
							      <td><input type="text" name="formBean.chainSalesOrder.productList[<s:property value='formBean.chainSalesOrder.productList.size()'/>].discountRate" id="discountRate<s:property value='formBean.chainSalesOrder.productList.size()'/>"  size="2"  onfocus="this.select();" onkeyup="changeRowValue('',<s:property value='formBean.chainSalesOrder.productList.size()'/>)"/></td>
							      <td><input type="text" id="dicountPrice<s:property value='formBean.chainSalesOrder.productList.size()'/>" onfocus="this.select();"  onkeyup='changeDiscountPrice(1,<s:property value='formBean.chainSalesOrder.productList.size()'/>)' size="4"/></td>
							      <td><input type="text" id="discountAmount<s:property value='formBean.chainSalesOrder.productList.size()'/>" readonly size="4"/></td>
							      <td><input type="text" id="inventory<s:property value="formBean.chainSalesOrder.productList.size()"/>" readonly size="1"/></td>
							      <td>
							          <select id="normalPrice<s:property value="formBean.chainSalesOrder.productList.size()"/>" name="formBean.chainSalesOrder.productList[<s:property value="formBean.chainSalesOrder.productList.size()"/>].normalSale" onchange='changeSelect();'>
								               <option value="1">正价</option>
								               <option value="2">特价</option>   
								       </select>
							      </td>
							      <td><div id="delIcon<s:property value='formBean.chainSalesOrder.productList.size()'/>" style="display:none"><img src="<%=request.getContextPath()%>/conf_files/web-image/delete.png" border="0"  onclick="deleteRow('orderRow<s:property value='formBean.chainSalesOrder.productList.size()'/>');" style="cursor:pointer;"/></div></td>
							     </tr>  
						  </tbody>
						  <tr class="PBAOuterTableTitale">
						    <th colspan="7" align='left'></th>
						    <th align='left'><s:textfield name="formBean.chainSalesOrder.totalQuantity" id="totalQuantity" readonly="true" size="2"/></th>
						    <th></th>
						    <th align='left'><s:textfield name="formBean.chainSalesOrder.totalAmount" id="totalAmount" readonly="true" size="2"/></th>
						    <th></th>
						    <th></th>
						    <th align='left'><s:textfield name="formBean.chainSalesOrder.netAmount" id="netAmount" readonly="true" size="4"/></th>
						    <th></th>
						    <th></th>
						    <th></th>
						  </tr>