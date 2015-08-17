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
						    <th>库存</th>
						    <th colspan ="7"></th>
						  </tr>
						  <tbody id="orderTablebodyF">
						         <s:iterator value="formBean.chainSalesOrder.productListF" status = "st" id="orderProduct" >
							         <tr class="InnerTableContent" id="orderRowF<s:property value="#st.index"/>">   
								      <td><s:property value="#st.index +1 "/></td>
								      <td><input type="text" name="formBean.chainSalesOrder.productListF[<s:property value="#st.index"/>].productBarcode.barcode" id="barcodeF<s:property value="#st.index"/>" value="<s:property value="#orderProduct.productBarcode.barcode"/>" size="11"/>
								          <input type="hidden" name="formBean.chainSalesOrder.productListF[<s:property value="#st.index"/>].productBarcode.id" id="productIdF<s:property value="#st.index"/>" value="<s:property value="#orderProduct.productBarcode.id"/>" size="11"/></td>
								      <td><input type="text" name="formBean.chainSalesOrder.productListF[<s:property value="#st.index"/>].productBarcode.product.productCode" id="productCodeF<s:property value="#st.index"/>" value="<s:property value="#orderProduct.productBarcode.product.productCode"/>" size="9"/></td>
									  <td><input type="text" name="formBean.chainSalesOrder.productListF[<s:property value="#st.index"/>].productBarcode.color.name" id="colorF<s:property value="#st.index"/>" value="<s:property value="#orderProduct.productBarcode.color.name"/>" size="4" readonly/></td>					    
								      <td><input type="text" name="formBean.chainSalesOrder.productListF[<s:property value="#st.index"/>].productBarcode.product.brand.brand_Name" id="brandF<s:property value="#st.index"/>" value="<s:property value="#orderProduct.productBarcode.product.brand.brand_Name"/>"  size="9" readonly/></td>
								      <td><input type="text" name="formBean.chainSalesOrder.productListF[<s:property value="#st.index"/>].productBarcode.product.quarter.quarter_Name" id="quarter<s:property value="#st.index"/>" value="<s:property value="#orderProduct.productBarcode.product.year.year"/> <s:property value="#orderProduct.productBarcode.product.quarter.quarter_Name"/>"  size="6" readonly/></td>
								      <td><input type="text" name="formBean.chainSalesOrder.productListF[<s:property value="#st.index"/>].productBarcode.product.unit" id="unitF<s:property value="#st.index"/>" value="<s:property value="#orderProduct.productBarcode.product.unit"/>" size="4" readonly/></td>
								      <td><input type="text" name="formBean.chainSalesOrder.productListF[<s:property value="#st.index"/>].quantity" id="quantityF<s:property value="#st.index"/>" value="<s:property value="#orderProduct.quantity"/>"  size="2" onfocus="this.select();" onkeyup="changeRowValue('F',<s:property value="#st.index"/>)"  onkeypress='return is_number(event);'/></td>
								      <td><input type="text" id="inventoryF<s:property value="#st.index"/>" value="<s:property value="#orderProduct.inventoryLevel"/>" readonly size="1"/></td>
								      <td colspan ="7"><div id="delIconF<s:property value="#st.index"/>" style="display:block"><img src="<%=request.getContextPath()%>/conf_files/web-image/delete.png" border="0"  onclick="deleteRow('orderRowF<s:property value="#st.index"/>');" style="cursor:pointer;"/></div></td>
								     </tr>  
							     </s:iterator>
							     <tr class="InnerTableContent" id="orderRowF<s:property value='formBean.chainSalesOrder.productListF.size()'/>">   
							      <td><s:property value='formBean.chainSalesOrder.productListF.size() + 1'/></td>
							      <td><input type="text" name="formBean.chainSalesOrder.productListF[<s:property value='formBean.chainSalesOrder.productListF.size()'/>].productBarcode.barcode" id="barcodeF<s:property value='formBean.chainSalesOrder.productListF.size()'/>" size="11"/>
							          <input type="hidden" name="formBean.chainSalesOrder.productListF[<s:property value='formBean.chainSalesOrder.productListF.size()'/>].productBarcode.id" id="productIdF<s:property value='formBean.chainSalesOrder.productListF.size()'/>" size="11"/></td>
							      <td><input type="text" name="formBean.chainSalesOrder.productListF[<s:property value='formBean.chainSalesOrder.productListF.size()'/>].productBarcode.product.productCode" id="productCodeF<s:property value='formBean.chainSalesOrder.productListF.size()'/>" size="9"/></td>
							      <td><input type="text" name="formBean.chainSalesOrder.productListF[<s:property value='formBean.chainSalesOrder.productListF.size()'/>].productBarcode.color.name" id="colorF<s:property value='formBean.chainSalesOrder.productListF.size()'/>" size="4" readonly/></td>
							      <td><input type="text" name="formBean.chainSalesOrder.productListF[<s:property value='formBean.chainSalesOrder.productListF.size()'/>].productBarcode.product.brand.brand_Name" id="brandF<s:property value='formBean.chainSalesOrder.productListF.size()'/>"  size="9" readonly/></td>
							      <td><input type="text" name="formBean.chainSalesOrder.productListF[<s:property value='formBean.chainSalesOrder.productListF.size()'/>].productBarcode.product.quarter.quarter_Name" id="quarterF<s:property value='formBean.chainSalesOrder.productList.size()'/>"  size="6" readonly/></td>
							      <td><input type="text" name="formBean.chainSalesOrder.productListF[<s:property value='formBean.chainSalesOrder.productListF.size()'/>].productBarcode.product.unit" id="unitF<s:property value='formBean.chainSalesOrder.productListF.size()'/>" size="4" readonly/></td>
							      <td><input type="text" name="formBean.chainSalesOrder.productListF[<s:property value='formBean.chainSalesOrder.productListF.size()'/>].quantity" id="quantityF<s:property value='formBean.chainSalesOrder.productListF.size()'/>"  size="2" onfocus="this.select();" onkeyup="changeRowValue('F',<s:property value='formBean.chainSalesOrder.productListF.size()'/>)"  onkeypress='return is_number(event);'/></td>
							      <td><input type="text" id="inventoryF<s:property value="formBean.chainSalesOrder.productListF.size()"/>" readonly size="1"/></td>
							      <td colspan ="7"><div id="delIconF<s:property value='formBean.chainSalesOrder.productListF.size()'/>" style="display:none"><img src="<%=request.getContextPath()%>/conf_files/web-image/delete.png" border="0"  onclick="deleteRow('orderRowF<s:property value='formBean.chainSalesOrder.productListF.size()'/>');" style="cursor:pointer;"/></div></td>
							     </tr>  
						  </tbody>
						  <tr class="PBAOuterTableTitale">
						    <th colspan="7" align='left'></th>
						    <th align='left'><s:textfield name="formBean.chainSalesOrder.totalQuantityF" id="totalQuantityF" readonly="true" size="2"/></th>
						    <th></th>
						    <th colspan ="7"></th>
						  </tr>