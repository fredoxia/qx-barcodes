<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags" %>
<script>
$(function() {
	$("#mySalePrice").focus(function(){
		  $("#mySalePrice").select();
	});
});
</script>
<div class="easyui-layout" data-options="fit:true,border:false">
	<div data-options="region:'center',border:false" title="" style="overflow: hidden;">
		<s:form id="editSalesPriceForm" name="editSalesPriceForm" method="post" theme="simple">
			<table  class="OuterTable" width="100%">
				<tr>
					<th width="20%">年/季度</th>
					<td><s:property value="formBean.chainProductBarcodeVO.year"/> <s:property value="formBean.chainProductBarcodeVO.quarter"/></td>
				</tr>
				<tr>
					<th>品牌</th>
					<td><s:property value="formBean.chainProductBarcodeVO.brand"/></td>
				</tr>				
				<tr>
					<th>货号</th>
					<td><s:property value="formBean.chainProductBarcodeVO.productCode"/> <s:property value="formBean.chainProductBarcodeVO.color"/></td>
				</tr>
				<tr>
					<th>类别</th>
					<td><s:property value="formBean.chainProductBarcodeVO.category"/></td>
				</tr>				
				<tr>
					<th>条码</th>
					<td><s:property value="formBean.chainProductBarcodeVO.barcode"/></td>
				</tr>
				<tr>
					<th>统一零售价</th>
					<td><s:property value="formBean.chainProductBarcodeVO.chainSalePrice"/></td>
				</tr>
				<tr>
					<th>我的零售价</th>
					<td>
						<s:hidden name="formBean.chainProductBarcodeVO.productBarcodeId" />
					    <s:hidden name="formBean.chainProductBarcodeVO.barcode" />
					    <s:hidden name="formBean.chainProductBarcodeVO.chainId" />
					    <s:textfield id="mySalePrice" name="formBean.chainProductBarcodeVO.mySalePrice" /></td>
				</tr>				
			</table>
		</s:form>
	</div>
</div>