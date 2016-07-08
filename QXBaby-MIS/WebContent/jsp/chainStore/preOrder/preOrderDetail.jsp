<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib prefix="s" uri="/struts-tags" %>
<%@ page import="com.onlineMIS.common.Common_util,java.util.Date,java.text.SimpleDateFormat" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>千禧宝贝连锁店管理信息系统</title>
<%@ include file="../../common/Style.jsp"%>

<script>
</script>
</head>
<body>
	<table class="easyui-datagrid"  style="height:540"  data-options="singleSelect:true,border : false">
		<thead>
		    <tr>
		      <th data-options="field:'2',width:80">季度</th>
		      <th data-options="field:'9',width:100">品牌</th>
		      <th data-options="field:'3',width:80">货号</th>
		      <th data-options="field:'4',width:80">颜色</th>
		      <th data-options="field:'5',width:80">类别</th>
		      <th data-options="field:'6',width:60">齐手数量</th>
		      
		      <th data-options="field:'7',width:60">订货(手)</th>
		      <th data-options="field:'8',width:80">批发总额</th>
		      <th data-options="field:'10',width:80">单件零售价</th>
		      <th data-options="field:'11',width:80">零售总额</th>
	
		    </tr>
		</thead>
		<tbody>
		    <s:iterator value="uiBean.order.productList" status = "st" id="item" >
			    <tr>
			      <td><s:property value="#item.productBarcode.product.year.year"/><s:property value="#item.productBarcode.product.quarter.quarter_Name"/></td>
			      <td><s:property value="#item.productBarcode.product.brand.brand_Name"/></td>
			      <td><s:property value="#item.productBarcode.product.productCode"/></td>
			      <td><s:property value="#item.productBarcode.color.name"/></td>
			      <td><s:property value="#item.productBarcode.product.category.category_Name"/></td>
			      <td><s:property value="#item.productBarcode.product.numPerHand"/></td>
			      
			      <td><s:property value="#item.totalQuantity"/></td>
			      <td><s:property value="#item.sumWholePrice"/></td>
			      <td><s:property value="#item.productBarcode.product.salesPrice"/></td>
			      <td><s:property value="#item.sumRetailPrice"/></td>
			    </tr>
		    </s:iterator>
		    <tr>
			      <td height="30">汇总</td>
			      <td></td>
			      <td></td>
			      <td></td>
			      <td></td>
			      <td></td>
			      <td><s:property value="uiBean.order.totalQuantity"/></td>
			      <td><s:property value="uiBean.order.sumWholePrice"/></td>
			      <td></td>
			      <td><s:property value="uiBean.order.sumRetailPrice"/></td>			      
			    </tr>
		 </tbody>
	</table>
</body>
</html>