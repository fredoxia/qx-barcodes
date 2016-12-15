<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags" %>
<script>
/**
 * user input the product code and press enter
 */
function searchProductsProductCode(){
	var productCode = $("#productCode").val();
	var chainId = $("#chainId").val();

	if (validateProductCodeInput(productCode)){
	    var url = encodeURI(encodeURI("actionChain/chainSalesJSPAction!scanByProductCode?formBean.productCode=" + productCode + "&formBean.chainId=" + chainId))  ; 
	
	    window.open(url,'新窗口','height=510, width=600, toolbar=no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=no');    
	}
}

/**
 * to validate the product code input
 */
function validateProductCodeInput(productCode){
	if (productCode.length <= 1){
       alert("输入的货号太短");
       return false;
	}
	return true;
}
/**
 * retrieve the product by input barcode
 */
function retrieveProductByBarcode(index_trigger, suffix, currentBarcode){
	var params= "formBean.barcode=" + currentBarcode + "&formBean.chainId=" + $("#chainStore").val() ; 
	
	$.post("actionChain/chainSalesJSONAction!scanByBarcode",params, backRetrievePByBarcodeProcess,"json");
}
function backRetrievePByBarcodeProcess(data){
	var error = data.error;
	
	if (error == true){
		alert("无法找到对应货品.请检查，重新输入!");

	}else{
		var barcode = data.productBarcode.barcode;
		var productId = data.productBarcode.id;
		var product = data.productBarcode.product;
		
		var productCode = product.productCode;
		var brand = product.brand.brand_Name;
		var color = data.productBarcode.color;
		var colorName = "";
		if (color != null)
    		colorName = color.name;
		var productInfor = barcode  + " " + brand + " " + productCode + " " + colorName;
		$("#productInfo").html(productInfor);
		$("#productId").attr("value",productId);
	}
}
function validateRowInputFromChild(index_trigger,suffix, currentBarcode){
	return true;
}
function clearSearch(){
	$("#productInfo").html("");
	$("#productId").attr("value",-1);
	$("#productCode").attr("value","");
}
</script>
		<s:hidden name="formBean.productId" id="productId" value="-1"/>
		<s:textfield name="formBean.productCode" id="productCode" size="18"/><input type="button" id="checkBrandBt" value="查找货号" onclick="searchProductsProductCode();"/><input type="button" id="clearBtn" value="清除" onclick="clearSearch();"/>
		<div id="productInfo"></div>
