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

});


function update(){
	var error ="";
	if ($("#productCode").val() == "" || $("#productCode").val().length <4){
		error +="产品货号 - 至少为长度为4的字符串\n";
		$("#productCode").focus();
	} 	
	
	var priceValue = $("#salesPrice").val();
	if (priceValue != "" && isNaN(priceValue) && priceValue > 0){
        error += "零售价 - 必须是数字\n";
        $("#salesPrice").focus();
	} else if (priceValue != "" && priceValue == 0){
        $("#salesPrice").attr("value","");
	} 

	
	var wholePriceValue = $("#wholeSalePrice").val();
	if (wholePriceValue != "" && isNaN(wholePriceValue) && wholePriceValue > 0){
        error += "进价 - 必须是数字\n";
        $("#wholeSalePrice").focus();
	} else if (wholePriceValue != "" && wholePriceValue == 0){
        $("#wholeSalePrice").attr("value","");
	} 
	
	if (error != "")
		alert(error);
	else {
		document.updateProductForm.action = "action4Chain/barcodeGenJSP!updateProduct";
		document.updateProductForm.submit();
	}
}

function del(){
	var info = "你确定删除此商品条码信息?\n请确认此条码并未使用!";
	if (confirm(info)){
		document.updateProductForm.action = "action4Chain/barcodeGenJSP!deleteBarcode";
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
	          <td colspan="2"><font color="red"><s:actionerror/></font><s:actionmessage/>
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
	          <td><s:select name="formBean.productBarcode.product.year.year_ID" size="1" id="year" list="uiBean.years"  listKey="year_ID" listValue="year"/></td>
	       </tr>
	       <tr class="InnerTableContent" style="background-color: rgb(255, 250, 208);">
	          <td height="18"><strong>季度</strong>         :</td>
	          <td><s:select name="formBean.productBarcode.product.quarter.quarter_ID" size="1" id="quarter" list="uiBean.quarters"  listKey="quarter_ID" listValue="quarter_Name"/></td>
	       </tr>
	       <tr class="InnerTableContent">
	          <td height="18"><strong>类别</strong>       :</td>
	          <td><s:select name="formBean.productBarcode.product.category.category_ID" size="1" id="category" list="uiBean.categories"  listKey="category_ID" listValue="category_Name"/></td>
	       </tr>
	       <tr class="InnerTableContent" style="background-color: rgb(255, 250, 208);">
	          <td height="18"><strong>产品货号</strong>:</td><td><s:textfield name="formBean.productBarcode.product.productCode" id="productCode"/>*</td>
	       </tr>

	       <tr class="InnerTableContent">
	          <td height="18"><strong>齐码数量 </strong>   :</td>
	          <td><s:select name="formBean.productBarcode.product.numPerHand" size="1" id="numPerHand" list="uiBean.numPerHands" listKey="numPerHand" listValue="numPerHand"/></td>
	       </tr>
	       <tr class="InnerTableContent" style="background-color: rgb(255, 250, 208);">
	          <td height="18"><strong>单位 </strong> :</td>
	          <td><s:select name="formBean.productBarcode.product.unit" size="1" id="unit" list="uiBean.units" listKey="productUnit" listValue="productUnit"/></td>
	       </tr>
	       <tr class="InnerTableContent">
	          <td height="18"><strong>颜色</strong>         :</td><td><s:property value="uiBean.product.color.name"/></td>
	       </tr>

	       <tr class="InnerTableContent">
	          <td height="18"><strong>零售价 </strong>       :</td><td><input type="text" name="formBean.productBarcode.product.salesPrice" id="salesPrice" value="<s:if test="uiBean.product.product.salesPrice!=0"><s:property value="uiBean.product.product.salesPrice"/></s:if>" size="10"/></td>
	       </tr>

	       <tr class="InnerTableContent">
	          <td height="18"><strong>进价 </strong>       :</td><td><input type="text" name="formBean.productBarcode.product.wholeSalePrice" id="wholeSalePrice" value="<s:if test="uiBean.product.product.wholeSalePrice!=0"><s:property value="uiBean.product.product.wholeSalePrice"/></s:if>" size="10"/></td>
	       </tr>
    
	       <tr class="InnerTableContent" style="background-color: rgb(255, 250, 208);">
	          <td colspan="2"> <input type="button" value="更新" onclick="update();"/>&nbsp;&nbsp;<input type="button" value="删除" onclick="del();"/>&nbsp;<input type="button" value="取消" onclick="window.close();"/></td>
	       </tr>
	    </table>
	    </s:form>
	    </td>
	</tr>
	</table>   
</body>
</html>