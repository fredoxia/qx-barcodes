<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags" %>
<script>
/**
 * once click the button, it will help to search brand
 */
function searchBrand(){

	var brandName = $.trim($("#brandName").val());
	var params= "formBean.productBarcode.product.brand.brand_Name=" + brandName ; 
    
    var url = encodeURI(encodeURI("barcodeGenJSP!scanByBrandName?" +params));
	
    window.open(url,'_blank','height=400, width=450, toolbar=no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=no');  
}

/**
 * user click the brand
 */
function selectBrand(brandName, brandId){
	if (brandName != "" && brandId != "" && brandId != 0){
        $("#brandName").attr("value", brandName);
        $("#brand_ID").attr("value", brandId);

        $("#brandName").attr("disabled", "disabled");
        $("#searchBt").attr("disabled", "disabled");
        $("#clearBt").removeAttr("disabled");
    }
}

function clearBrand(){
    $("#brandName").attr("value", "");
    $("#brand_ID").attr("value", "");

    $("#brandName").removeAttr("disabled");
    $("#searchBt").removeAttr("disabled");
    $("#clearBt").attr("disabled", "disabled");

    clearAllData();
}
</script>

         <s:textfield name="formBean.productBarcode.product.brand.brand_Name" id="brandName" size="10"/>
         <s:hidden name="formBean.productBarcode.product.brand.brand_ID" id="brand_ID"/> 
         <input id="searchBt" type="button" onclick="searchBrand();" value="查找"/>
         <input id="clearBt" type="button" onclick="clearBrand();" value="清除" disabled/>