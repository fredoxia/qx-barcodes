<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags" %>
<script>
$(function() {

});
</script>
<div class="easyui-layout" data-options="fit:true,border:false">
	<div data-options="region:'center',border:false" title="" style="overflow: hidden;">
		<table  class="OuterTable" width="100%">
				<tr>
					<th width="20%">年/季度</th>
					<td><s:property value="uiBean.cpbVO.year"/> <s:property value="uiBean.cpbVO.quarter"/></td>
				</tr>
				<tr>
					<th>品牌</th>
					<td><s:property value="uiBean.cpbVO.brand"/></td>
				</tr>				
				<tr>
					<th>货号</th>
					<td><s:property value="uiBean.cpbVO.productCode"/> <s:property value="uiBean.cpbVO.color"/></td>
				</tr>
				<tr>
					<th>类别</th>
					<td><s:property value="uiBean.cpbVO.category"/></td>
				</tr>				
				<tr>
					<th>条码</th>
					<td><s:property value="uiBean.cpbVO.barcode"/></td>
				</tr>
				<tr>
					<th>批发价</th>
					<td><s:property value="uiBean.cpbVO.cost"/></td>
				</tr>				
				<tr>
					<th>统一零售价</th>
					<td><s:property value="uiBean.cpbVO.chainSalePrice"/></td>
				</tr>
				<s:if test="#session.LOGIN_CHAIN_USER.myChainStore.allowChangeSalesPrice == 1 || #session.LOGIN_CHAIN_USER.myChainStore.priceIncrement != 0">
				<tr>
					<th></th>
				    <td></td>
				</tr>
				<tr>
					<th>我的零售价</th>
					<td><s:property value="uiBean.cpbVO.mySalePrice" /></td>
				</tr>
				<tr>
					<th>相对统一价%</th>
					<td><s:property value="uiBean.cpbVO.mySalesPricePercentage" /></td>
				</tr>
				</s:if>				
			</table>
	</div>
</div>