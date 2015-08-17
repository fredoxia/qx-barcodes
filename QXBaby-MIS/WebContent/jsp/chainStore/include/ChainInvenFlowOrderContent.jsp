<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

			            <!-- table to display the product information -->
						<table width="100%"  align="left" class="OuterTable" id="org_table">
						  <tr class="PBAInnerTableTitale" align='left'>
						    <th width="21">&nbsp;</th>
						    <th width="100" height="35">条形码</th>
						    <th width="100">货号/名称</th>
						    <th width="40">颜色</th>
						    <th width="110">品牌</th>
						    <th width="40">年份</th>
						    <th width="40">季度</th>
						    <th width="40">单位</th>
						    <th width="39">数量</th>
						    <th width="39">库存</th>
						    <th width="130">摘要</th>
						    <th width="20"></th>
						  </tr>
						  <tbody id="orderTablebody">
						      <s:iterator value="formBean.flowOrder.productList" status = "st" id="orderProduct" >
						  		<tr class="InnerTableContent" id="orderRow<s:property value="#st.index"/>">   
							      <td height="25"><s:property value="#st.index +1"/></td>
							      <td><input type="text" name="formBean.flowOrder.productList[<s:property value="#st.index"/>].product.barcode" id="barcode<s:property value="#st.index"/>" size="13" value="<s:property value="#orderProduct.product.barcode"/>" />
							          <input type="hidden" name="formBean.flowOrder.productList[<s:property value="#st.index"/>].product.productId" id="productId<s:property value="#st.index"/>" size="13" value="<s:property value="#orderProduct.product.productId"/>"/>
							      </td>
							      <td><input type="text" name="formBean.flowOrder.productList[<s:property value="#st.index"/>].product.productCode" id="productCode<s:property value="#st.index"/>" size="13" value="<s:property value="#orderProduct.product.productCode"/>"/></td>
							      <td><input type="text" name="formBean.flowOrder.productList[<s:property value="#st.index"/>].color.name" id="color<s:property value="#st.index"/>" size="13" value="<s:property value="#orderProduct.color.name"/>"/></td>
							      <td><input type="text" name="formBean.flowOrder.productList[<s:property value="#st.index"/>].product.brand.brand_Name" id="brand0<s:property value="#st.index"/>"  size="17" readonly="readonly" value="<s:property value="#orderProduct.product.brand.brand_Name"/>"/></td>
							      <td><input type="text" name="formBean.flowOrder.productList[<s:property value="#st.index"/>].product.year.year" id="year<s:property value="#st.index"/>"  size="4" readonly="readonly" value="<s:property value="#orderProduct.product.year.year"/>"/></td>
							      <td><input type="text" name="formBean.flowOrder.productList[<s:property value="#st.index"/>].product.quarter.quarter_Name" id="quarter<s:property value="#st.index"/>"  size="4" readonly="readonly" value="<s:property value="#orderProduct.product.quarter.quarter_Name"/>"/></td>
							      <td><input type="text" name="formBean.flowOrder.productList[<s:property value="#st.index"/>].product.unit" id="unit<s:property value="#st.index"/>" size="4" readonly="readonly" value="<s:property value="#orderProduct.product.unit"/>"/></td>
							      <td><input type="text" name="formBean.flowOrder.productList[<s:property value="#st.index"/>].quantity" id="quantity<s:property value="#st.index"/>"  size="2" onchange="calculateTotal()" onkeypress="return is_number(event);" value="<s:property value="#orderProduct.quantity"/>"/></td>
							      <td><input type="text" name="formBean.flowOrder.productList[<s:property value="#st.index"/>].inventoryQ" id="inventoryQ<s:property value="#st.index"/>"  size="2" onchange="calculateTotal()" onkeypress="return is_number(event);" value="<s:property value="#orderProduct.inventoryQ"/>"/></td>
							      <td><input type="text" name="formBean.flowOrder.productList[<s:property value="#st.index"/>].comment" id="comment" size="17" value="<s:property value="#orderProduct.comment"/>"/></td>
								  <td><div id="delIcon<s:property value="#st.index"/>" style="display:block"><img src="<%=request.getContextPath()%>/conf_files/web-image/delete.png" border="0"  onclick="deleteRow('orderRow<s:property value="#st.index"/>');" style="cursor:pointer;"/></div>
								  </td>
							     </tr>
							   </s:iterator>  
						         <tr class="InnerTableContent" id="orderRow<s:property value='formBean.flowOrder.productList.size()'/>">   
							      <td height="25"><s:property value='formBean.flowOrder.productList.size() + 1'/></td>
							      <td><input type="text" name="formBean.flowOrder.productList[<s:property value='formBean.flowOrder.productList.size()'/>].product.barcode" id="barcode<s:property value='formBean.flowOrder.productList.size()'/>" size="13" />
							          <input type="hidden" name="formBean.flowOrder.productList[<s:property value='formBean.flowOrder.productList.size()'/>].product.productId" id="productId<s:property value='formBean.flowOrder.productList.size()'/>" size="13"/></td>
							      <td><input type="text" name="formBean.flowOrder.productList[<s:property value='formBean.flowOrder.productList.size()'/>].product.productCode" id="productCode<s:property value='formBean.flowOrder.productList.size()'/>" size="13"/></td>
							      <td><input type="text" name="formBean.flowOrder.productList[<s:property value='formBean.flowOrder.productList.size()'/>].color.name" id="color<s:property value='formBean.flowOrder.productList.size()'/>" size="13"/></td>
							      <td><input type="text" name="formBean.flowOrder.productList[<s:property value='formBean.flowOrder.productList.size()'/>].product.brand.brand_Name" id="brand<s:property value='formBean.flowOrder.productList.size()'/>"  size="17" readonly="readonly"/></td>
							      <td><input type="text" name="formBean.flowOrder.productList[<s:property value='formBean.flowOrder.productList.size()'/>].product.year.year" id="year<s:property value='formBean.flowOrder.productList.size()'/>"  size="4" readonly="readonly"/></td>
							      <td><input type="text" name="formBean.flowOrder.productList[<s:property value='formBean.flowOrder.productList.size()'/>].product.quarter.quarter_Name" id="quarter<s:property value='formBean.flowOrder.productList.size()'/>"  size="4" readonly="readonly"/></td>
							      <td><input type="text" name="formBean.flowOrder.productList[<s:property value='formBean.flowOrder.productList.size()'/>].product.unit" id="unit<s:property value='formBean.flowOrder.productList.size()'/>" size="4" readonly="readonly"/></td>
							      <td><input type="text" name="formBean.flowOrder.productList[<s:property value='formBean.flowOrder.productList.size()'/>].quantity" id="quantity<s:property value='formBean.flowOrder.productList.size()'/>"  size="2" onchange="calculateTotal()" onkeypress="return is_number(event);"/></td>
							      <td><input type="text" name="formBean.flowOrder.productList[<s:property value='formBean.flowOrder.productList.size()'/>].inventoryQ" id="inventoryQ<s:property value='formBean.flowOrder.productList.size()'/>"  size="2" onchange="calculateTotal()" onkeypress="return is_number(event);"/></td>
							      <td><input type="text" name="formBean.flowOrder.productList[<s:property value='formBean.flowOrder.productList.size()'/>].comment" id="comment" size="17"/></td>
								  <td><div id="delIcon<s:property value='formBean.flowOrder.productList.size()'/>" style="display:none"><img src="<%=request.getContextPath()%>/conf_files/web-image/delete.png" border="0"  onclick="deleteRow('orderRow<s:property value='formBean.flowOrder.productList.size()'/>');" style="cursor:pointer;"/></div>
								  </td>
							     </tr>  
						  </tbody>
						  <tr class="PBAInnerTableTitale">
						    <th height="25" colspan="8" align='left'>合计</th>
						    <th align='left'><s:textfield name="formBean.flowOrder.totalQuantity" id="totalQuantity" readonly="true" size="2"/></th>
						    <th align='left'><s:textfield name="formBean.flowOrder.totalInventoryQ" id="totalInventoryQ" readonly="true" size="2"/></th>
						    <th align='left'>&nbsp;</th>
						    <th></th>
						  </tr>
					     </table>