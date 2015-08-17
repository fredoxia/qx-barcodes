<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags" %>
<script>
/**
 * this stub is for the pages which may need the VIP Card search
 */
 function searchBrand(){
	 var brandName = $("#brandName").val();
	 var year =  $("#year").val();
	 var quarter =  $("#quarter").val();
	 var param = encodeURI(encodeURI("formBean.brand.brand_Name="+brandName +"&formBean.year.year_ID=" + year +"&formBean.quarter.quarter_ID=" + quarter));
	 var url = "chainMgmtJSP!listBrands?" + param;
	 window.open(url,'_blank','height=400, width=400, toolbar=no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=no');  
}

function selectBrand(brandId, brandName){
	$("#brandId").attr("value", brandId);
	$("#brandName").attr("value", brandName);
}
</script>
		<s:hidden name="formBean.brand.brand_ID" id="brandId" value="-1"/>
		<s:textfield name="formBean.brand.brand_Name" id="brandName" size="18"/><input type="button" id="checkBrandBt" value="..." onclick="searchBrand();"/>
