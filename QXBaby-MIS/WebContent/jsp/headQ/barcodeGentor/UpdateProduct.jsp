<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib prefix="s" uri="/struts-tags" %>
<%@ page import="com.onlineMIS.ORM.entity.headQ.barcodeGentor.*" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>修改条型码资料</title>
<%@ include file="../../common/Style.jsp"%>
<script>
$(document).ready(function(){
	parent.$.messager.progress('close'); 
	$("#org_table tr").mouseover(function(){      
		$(this).addClass("over");}).mouseout(function(){    
		$(this).removeClass("over");}); 
	$("#clearBt").removeAttr("disabled");
});

function del(){
	var info = "你确定删除此商品条码信息?\n此商品信息一经删除所有相关单据都将删除对应信息!";
	if (confirm(info)){
	    var params=$("#updateProductForm").serialize();  
	    $.post("action/productJSONAction!checkBarcode",params, backProcess,"json");
	} 
}
function backProcess(data){
	var result = data.result;
	if (result == false){
	  document.updateProductForm.action = "action/productJSPAction!delete";
	  document.updateProductForm.submit();
	} else {
	  alert("此条码已经使用无法删除！");
	}
}
function update(){
	var error ="";
	if ($("#productCode").val() == ""){
		error +="产品货号 - 不能为空\n";
		$("#productCode").focus();
	} 	
	
	if ($("#brand_ID").val() == ""){
		error +="产品牌 - 不能为空\n";
	} 	
	
	var categoryId = $("#category").combo("getValue");
	if (categoryId != "0" && !isValidPositiveInteger(categoryId)){
		alert("货品类别不是一个正确的输入");
		return;
	}
	var priceValue = $("#salesPrice").val();
	if (priceValue != "" && isNaN(priceValue)){
        error += "连锁店零售价 - 必须是数字\n";
        $("#salesPrice").focus();
	} else if (priceValue != "" && priceValue == 0){
        $("#salesPrice").attr("value","");
	} 

	var recCostValue = $("#recCost").val();
	if (recCostValue != "" && isNaN(recCostValue)){
        error += "进价 - 必须是数字\n";
        $("#recCost").focus();
	} else if (recCostValue != "" && recCostValue == 0){
        $("#recCost").attr("value","");
	} 
	
	var wholePriceValue = $("#wholeSalePrice").val();
	if (wholePriceValue != "" && isNaN(wholePriceValue)){
        error += "预设价1 - 必须是数字\n";
        $("#wholeSalePrice").focus();
	} else if (wholePriceValue != "" && wholePriceValue == 0){
        $("#wholeSalePrice").attr("value","");
	} 

	var wholePriceValue2 = $("#wholeSalePrice2").val();
	if (wholePriceValue2 != "" && isNaN(wholePriceValue2)){
        error += "预设价2 - 必须是数字\n";
        $("#wholeSalePrice").focus();
	} else if (wholePriceValue2 != "" && wholePriceValue2 == 0){
        $("#wholeSalePrice2").attr("value","");
	} 

	var wholePriceValue3 = $("#wholeSalePrice3").val();
	if (wholePriceValue3 != "" && isNaN(wholePriceValue3)){
        error += "预设价3 - 必须是数字\n";
        $("#wholeSalePrice3").focus();
	} else if (wholePriceValue3 != "" && wholePriceValue3 == 0){
        $("#wholeSalePrice3").attr("value","");
	} 

	var salesPriceFactoryValue = $("#salesPriceFactory").val();
	if (salesPriceFactoryValue != "" && isNaN(salesPriceFactoryValue)){
        error += "厂家零售价 - 必须是数字\n";
        $("#salesPriceFactory").focus();
	} else if (salesPriceFactoryValue != "" && salesPriceFactoryValue == 0){
        $("#salesPriceFactory").attr("value","");
	} 

	var discountValue = $("#discount").val();
	if (discountValue != "" && isNaN(discountValue)){
        error += "折扣 - 必须是数字\n";
        $("#discount").focus();
	} else if (discountValue != "" && (discountValue < 0 || discountValue >1)){
        error += "折扣 - 必须是小于或者等于1的正数\n";
        $("#discount").focus();
	} else if (discountValue != "" && discountValue == 0){
        $("#discount").attr("value","");
	} 
	
	if (error != "")
		alert(error);
	else {
		document.updateProductForm.action = "action/productJSPAction!update";
		document.updateProductForm.submit();
	}
}
</script>
</head>
<body>
    <table width="90%" align="center"  class="OuterTable">
    <tr><td>
        <s:form id="updateProductForm" name="updateProductForm" method="post" action="" theme="simple">
	    <table width="100%" border="0" id="org_table">
	       <tr class="PBAOuterTableTitale">
	          <th colspan="2">修改条形码信息</th>
	       </tr>
	       <tr>
	          <td colspan="2"><div class="errorAndmes"><s:actionerror cssStyle="color:red"/><s:actionmessage cssStyle="color:blue"/></div>
	          </td>
	       </tr>
	       <tr class="InnerTableContent" style="background-color: rgb(255, 250, 208);">
	          <td height="18"><strong>归属</strong>   :</td><td>
	              
	              <s:if test="uiBean.product.chainStore != null">
	              	<s:property value="uiBean.product.chainStore.chain_name"/>
	              </s:if><s:else>
	                总部
	              </s:else>
	          </td>
	       </tr>
	       <tr class="InnerTableContent">
	          <td height="18"><strong>商品编码</strong>    :</td>
	          <td><s:property value="uiBean.product.product.serialNum"/></td>
	       </tr>
	       <tr class="InnerTableContent" style="background-color: rgb(255, 250, 208);">
	          <td height="18"><strong>条形码 </strong>  : </td>
	          <td><s:property value="uiBean.product.barcode"/>
	              <input type="hidden" name="formBean.productBarcode.barcode" value="<s:property value="uiBean.product.barcode"/>"/>
	              <input type="hidden" name="formBean.productBarcode.id" value="<s:property value="uiBean.product.id"/>"/>
	              <input type="hidden" name="formBean.productBarcode.product.productId" value="<s:property value="uiBean.product.product.productId"/>"/>
	          </td>
	       </tr>
	       <tr class="InnerTableContent">
	          <td height="18"><strong>品牌 </strong>        :</td>
	          <td><%@ include file="SearchBrandStub.jsp"%></td>
	       </tr>
	       <tr class="InnerTableContent">
	          <td height="18"><strong>年份  </strong>       :</td>
	          <td><s:select name="formBean.productBarcode.product.year.year_ID" size="1" id="year" list="uiBean.basicData.yearList"  listKey="year_ID" listValue="year"/></td>
	       </tr>
	       <tr class="InnerTableContent" style="background-color: rgb(255, 250, 208);">
	          <td height="18"><strong>季度</strong>         :</td>
	          <td><s:select name="formBean.productBarcode.product.quarter.quarter_ID" size="1" id="quarter" list="uiBean.basicData.quarterList"  listKey="quarter_ID" listValue="quarter_Name"/></td>
	       </tr>
	       <tr class="InnerTableContent">
	          <td height="18"><strong>类别</strong>       :</td>
	          <td><s:select name="formBean.productBarcode.product.category.category_ID"  cssClass="easyui-combobox" size="1" id="category" list="uiBean.basicData.categoryList"  listKey="category_ID" listValue="category_Name"/></td>
	       </tr>
	       <tr class="InnerTableContent" style="background-color: rgb(255, 250, 208);">
	          <td height="18"><strong>产品货号</strong>:</td><td><s:textfield name="formBean.productBarcode.product.productCode" id="productCode"/>*</td>
	       </tr>

	       <tr class="InnerTableContent">
	          <td height="18"><strong>齐码数量 </strong>   :</td>
	          <td><s:select name="formBean.productBarcode.product.numPerHand" size="1" id="numPerHand" list="uiBean.basicData.numPerHandList" listKey="numPerHand" listValue="numPerHand"/></td>
	       </tr>
	       <tr class="InnerTableContent" style="background-color: rgb(255, 250, 208);">
	          <td height="18"><strong>单位 </strong> :</td>
	          <td><s:select name="formBean.productBarcode.product.unit" size="1" id="unit" list="uiBean.basicData.unitList" listKey="productUnit" listValue="productUnit"/></td>
	       </tr>
	       <tr class="InnerTableContent">
	          <td height="18"><strong>颜色</strong>         :</td><td><s:property value="uiBean.product.color.name"/></td>
	       </tr>
	       <tr class="InnerTableContent" style="background-color: rgb(255, 250, 208);">
	          <td height="18"><strong>尺码</strong>       :</td><td><s:property value="uiBean.product.size.name"/></td>
	       </tr>
	       <tr class="InnerTableContent">
	          <td height="18"><strong>连锁零售价 </strong>       :</td><td><input type="text" name="formBean.productBarcode.product.salesPrice" id="salesPrice" value="<s:if test="uiBean.product.product.salesPrice!=0"><s:property value="uiBean.product.product.salesPrice"/></s:if>" size="10"/></td>
	       </tr>
	       <tr class="InnerTableContent" style="background-color: rgb(255, 250, 208);">
	          <td height="18"><strong>进价 </strong>       :</td><td><input type="text" name="formBean.productBarcode.product.recCost" id="recCost" value="<s:if test="uiBean.product.product.recCost!=0"><s:property value="uiBean.product.product.recCost"/></s:if>" size="10"/></td>
	       </tr>
	       <tr class="InnerTableContent">
	          <td height="18"><strong>预设价1 </strong>       :</td><td><input type="text" name="formBean.productBarcode.product.wholeSalePrice" id="wholeSalePrice" value="<s:if test="uiBean.product.product.wholeSalePrice!=0"><s:property value="uiBean.product.product.wholeSalePrice"/></s:if>" size="10"/></td>
	       </tr>
	       <tr class="InnerTableContent" style="background-color: rgb(255, 250, 208);">
	          <td height="18"><strong>预设价2 </strong>       :</td><td><input type="text" name="formBean.productBarcode.product.wholeSalePrice2" id="wholeSalePrice2" value="<s:if test="uiBean.product.product.wholeSalePrice2!=0"><s:property value="uiBean.product.product.wholeSalePrice2"/></s:if>" size="10"/></td>
	       </tr>
	       <tr class="InnerTableContent">
	          <td height="18"><strong>预设价3 </strong>       :</td><td><input type="text" name="formBean.productBarcode.product.wholeSalePrice3" id="wholeSalePrice3" value="<s:if test="uiBean.product.product.wholeSalePrice3!=0"><s:property value="uiBean.product.product.wholeSalePrice3"/></s:if>" size="10"/></td>
	       </tr>	       
	       <tr class="InnerTableContent" style="background-color: rgb(255, 250, 208);">
	          <td height="18"><strong>厂家零售价 </strong>       :</td><td><input type="text" name="formBean.productBarcode.product.salesPriceFactory" id="salesPriceFactory" value="<s:if test="uiBean.product.product.salesPriceFactory!=0"><s:property value="uiBean.product.product.salesPriceFactory"/></s:if>" size="10"/></td>
	       </tr>
	       <tr class="InnerTableContent">
	          <td height="18"><strong>折扣 </strong>       :</td><td><input type="text" name="formBean.productBarcode.product.discount" id="discount" value="<s:if test="uiBean.product.product.discount!=0"><s:property value="uiBean.product.product.discount"/></s:if>" size="10"/></td>
	       </tr>	    
	       <tr class="InnerTableContent" style="background-color: rgb(255, 250, 208);">
	          <td colspan="2"> <input type="button" value="更新" onclick="update();"/>&nbsp;&nbsp;<input type="button" value="删除" onclick="del();"/>&nbsp;&nbsp;<input type="button" value="取消" onclick="window.close();"/></td>
	       </tr>
	    </table>
	    </s:form>
	    </td>
	</tr>
	</table>   
</body>
</html>