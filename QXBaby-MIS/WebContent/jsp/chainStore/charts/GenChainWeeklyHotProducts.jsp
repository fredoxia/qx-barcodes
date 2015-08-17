<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags" %>
<script>

</script>
	<table class="easyui-datagrid"  style="width:500px;height:300px" data-options="singleSelect:true,border : false">
		<thead>
		    <tr>
		      <th data-options="field:'1',width:40">排名</th>
		      <th data-options="field:'2',width:100">热销款式</th>
		      <th data-options="field:'3',width:90">货品类别</th>

		      <th data-options="field:'5',width:50">年份</th>
		      <th data-options="field:'6',width:50">季度</th>
		      <th data-options="field:'7',width:50">单价</th>
		      <th data-options="field:'8',width:50">平均销量</th>
		      <th data-options="field:'9',width:50">我的销量</th>
		    </tr>
		</thead>
		<tbody>
		    <s:iterator value="uiBean.hotProducts" status = "st" id="hotProduct" >
			    <tr>
			      <td height="30"><s:property value="#hotProduct.rank"/></td>
			      <td><s:property value="#hotProduct.productBarcode.product.productCode"/> <s:property value="#hotProduct.productBarcode.color.name"/></td>
			      <td><s:property value="#hotProduct.productBarcode.product.category.category_Name"/></td>

			      <td><s:property value="#hotProduct.productBarcode.product.year.year"/></td>
			      <td><s:property value="#hotProduct.productBarcode.product.quarter.quarter_Name"/></td>
			      <td><s:property value="#hotProduct.productBarcode.product.salesPrice"/></td>
			      <td><s:text name="format.price">  
    					   <s:param value="#hotProduct.salesQuantity"/>  
					  </s:text></td>
			      <td><s:property value="#hotProduct.mySalesQuantity"/></td>
			    </tr>
		    </s:iterator>
		 </tbody>
	</table>

