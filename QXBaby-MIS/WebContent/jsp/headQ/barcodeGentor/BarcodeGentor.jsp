<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib prefix="s" uri="/struts-tags" %>

<html>
<head>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=8" />
<title>产品条形码导入</title>
<%@ include file="../../common/Style.jsp"%>
<script type="text/javascript" src="<%=request.getContextPath()%>/conf_files/js/BarcodeGenKeys.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/conf_files/js/HtmlTable.js"></script>
<script>
$(document).ready(function(){
	parent.$.messager.progress('close'); 
	jQuery.excel('InnerTableContent');
	jQuery.excel('PBAOuterTableTitale');
	$("#brandName").focus();
});
function checkProductCodeSerialNum(){
    var params=$("#barcodeGenForm").serialize(); 
    $.post("action/productJSONAction!checkProductCodeSerialNum",params, checkProductCodeBackProcess,"json");	
}
function checkProductCodeBackProcess(data){
	var returnCode = data.returnCode;
	var tips = data.tip;

	if (returnCode == FAIL){
       alert(tips);
    } else if (returnCode == WARNING){
		tips += "\n 你确定是否继续生成货品和条码?";
		var con = confirm(tips);
		if(con == true){
			generateBarcode();
		}
    } else if (returnCode == SUCCESS){
    	generateBarcode();
    }

    $("#saveButton").attr("disabled", false);
}
function generateBarcode(){
	$("#color").find("option").attr("selected","selected"); 
    var params=$("#barcodeGenForm").serialize(); 
    $.post("action/productJSONAction!generateProductBarcode",params, saveBarcodeBackProcess,"json");
}

function saveProduct(){
    $("#saveButton").attr("disabled", true);
    
	var error = "";
	if ($("#area_ID").val() == ""){
          error += "地区 - 是必选项\n";
	} 
	if ($("#year_ID").val() == ""){
          error += "年份 - 是必选项\n";
	} 
	if ($("#quarter_ID").val() == ""){
          error += "季度 - 是必选项\n";
	} 
	var brandId = $("#brand_ID").val();
	if (brandId == "" || brandId == 0){
          error += "品牌 - 是必选项\n";
	} 
	
	var categoryId = $("#category_ID").combo("getValue");
	if (categoryId == ""){
          error += "货品类 - 是必选项\n";
	} else if (!isValidPositiveInteger(categoryId)) {
        error += "货品类 - 必须是系统已经存在的类别，请检查\n";
	}
	if ($("#productCode").val() == ""){
          error += "货号 - 是必选项\n";
          $("#productCode").focus();
	}
	/**
	if ($("#serialNum").val() == ""){
        error += "商品编码 - 是必填项\n";
        $("#serialNum").focus();
	}*/
	var priceValue = $("#salesPrice").val();
	if (priceValue != "" && !isPositiveDouble(priceValue)){
        error += "连锁店零售价 - 必须是大于零数字\n";
	}
	var recCostValue = $("#recCost").val();
	if (recCostValue != "" && !isPositiveDouble(recCostValue)){
        error += "进价 - 必须是大于零数字\n";
	}
	var wholePriceValue = $("#wholeSalePrice").val();
	if (wholePriceValue != "" && !isPositiveDouble(wholePriceValue)){
        error += "预设价1 - 必须是大于零数字\n";
	}
	var wholePriceValue2 = $("#wholeSalePrice2").val();
	if (wholePriceValue2 != "" && !isPositiveDouble(wholePriceValue2)){
        error += "预设价2 - 必须是大于零数字\n";
	}
	var wholePriceValue3 = $("#wholeSalePrice3").val();
	if (wholePriceValue3 != "" && !isPositiveDouble(wholePriceValue3)){
        error += "预设价3 - 必须是大于零数字\n";
	}
	var salesPriceFactory = $("#salesPriceFactory").val();
	if (salesPriceFactory != "" && !isPositiveDouble(salesPriceFactory)){
        error += "厂家零售价 - 必须是大于零数字\n";
	}
	var discount = $("#discount").val();
	if (discount != "" && !isPositiveDouble(discount)){
        error += "折扣 - 必须是大于零数字\n";
	} else if (discount != "" && (discount < 0 || discount >1)){
        error += "折扣 - 必须是小于或者等于1的正数\n";
	}
	
	if (error == ""){
		var colors = $("#color").text();
		var sizes = $("#size").text();

        if (colors !="" || sizes != "" ){
        	var msg ="你确定要生成此颜色组和尺码组条码:\n" + colors + "\n" + sizes;
		    if (confirm(msg))
		       checkProductCodeSerialNum();
			else 
				$("#saveButton").attr("disabled", false);
        } else 
        	checkProductCodeSerialNum();
	} else{
		alert(error);
		$("#saveButton").attr("disabled", false);
	}
}

function saveBarcodeBackProcess(data){
	var returnCode = data.returnCode;

	if (returnCode != SUCCESS){
		alert(data.error);
	}else {
		clearAllData();
	
	    var barcodes = data.barcodes;

	    if (barcodes.length != 0){
		    for (var i = 0; i < barcodes.length; i++){
		    	var bg = "";
		    	if ((i % 2) == 0)
		    		bg = " style='background-color: rgb(255, 250, 208);'";
		        if (barcodes[i] != "")  {
			      var belong = "";
			      if (barcodes[i].chainStore.chainId != undefined)
				     belong = "连锁店"; 
		          $("<tr align='center' class='InnerTableContent'" + bg +"><td>"+barcodes[i].product.year.year + " " + barcodes[i].product.quarter.quarter_Name +"</td><td>"+
				          barcodes[i].product.brand.brand_Name+"</td><td>"+
				          barcodes[i].product.category.category_Name+"</td><td>"+
				          barcodes[i].product.productCode+"</td><td>"+
				          parseValue(barcodes[i].color.name)+"</td><td>"+
//Size will be implement later				          parseValue(barcodes[i].size.name)+"</td><td>"+ 
				          barcodes[i].product.numPerHand + "/" + barcodes[i].product.unit +"</td><td>"+
				          barcodes[i].product.salesPriceFactory +"</td><td>"+
				          barcodes[i].product.discount +"</td><td>"+
				          barcodes[i].product.salesPrice+"</td><td>"+
				          barcodes[i].product.recCost+"</td><td>"+
				          barcodes[i].product.wholeSalePrice+"</td><td>"+
				          barcodes[i].barcode+"</td><td>"+
				          barcodes[i].createDate+"</td><td>"+
				          belong+"</td><td><s:if test="#session.LOGIN_USER.containFunction('productJSPAction!searchForUpdate')"><a href='#' onclick=\"window.open ('productJSPAction!searchForUpdate?formBean.productBarcode.barcode="+barcodes[i].barcode+"','新窗口','height=550, width=400, toolbar=no, menubar=no, scrollbars=no, resizable=no, location=no, status=no');\"><img src='<%=request.getContextPath()%>/conf_files/web-image/editor.gif' border='0'/></a></s:if></td></tr>").appendTo("#orgTablebody");
		         }
		    }
		    
	        alert("成功生成条码");
	    }else {
	    	$("<tr class='InnerTableContent' style='background-color: rgb(255, 250, 208);' align='center'><td colspan=15><font color='red'>对应信息没有条形码存在</font> </td></tr>").appendTo("#orgTablebody");
	    }
    }

	$("#saveButton").attr("disabled", false);
}

function getProductColors(){
	var serialNum = $("#serialNum").val();
	var params = "formBean.productBarcode.product.serialNum=" + serialNum;
    $.post("action/productJSONAction!getProductInforBySerialNum",params, getProductBySNBackProcess,"json");
}

function getProductBySNBackProcess(data){
	var response = data.response;
	var returnCode = response.returnCode;
	if (returnCode != SUCCESS)
		alert(response.message);
	else {
		var returnValue = response.returnValue;
		var colors = returnValue.color;
		$("#colorsDiv").html(colors);

		var productInfor = returnValue.product;
		assignProductValue(productInfor);
	}
}
function assignProductValue(p){
	if (p != undefined && p != null){
		$("#salesPrice").attr("value","");
		$("#productCode").attr("value",p.productCode);
		$("#recCost").attr("value",p.recCost);
		$("#wholeSalePrice").attr("value",p.wholeSalePrice);
		if (p.wholeSalePrice2 > 0)
			$("#wholeSalePrice2").attr("value",p.wholeSalePrice2);
		if (p.wholeSalePrice3 > 0)
			$("#wholeSalePrice3").attr("value",p.wholeSalePrice3);
		if (p.salesPriceFactory > 0)
		    $("#salesPriceFactory").attr("value",p.salesPriceFactory);
		$("#discount").attr("value",p.discount);
		$("#numPerHand").attr("value",p.numPerHand);
		$("#unit").attr("value",p.unit);
		$("#year_ID").attr("value",p.year.year_ID);
		$("#quarter_ID").attr("value",p.quarter.quarter_ID);
		$("#brand_ID").attr("value",p.brand.brand_ID);
		$("#brandName").attr("value",p.brand.brand_Name);
		$("#salesPrice").attr("value",p.salesPrice);
		$("#category_ID").combobox("select",p.category.category_ID);
		
	}
}
function clearAllData(){
	$("#error").html("");
	$("#tip").html("");
	$("#salesPrice").attr("value","");
	$("#barcode").attr("value","");
	$("#productCode").attr("value","");
	$("#recCost").attr("value","");
	$("#wholeSalePrice").attr("value","");
	$("#wholeSalePrice2").attr("value","");
	$("#wholeSalePrice3").attr("value","");
	$("#salesPriceFactory").attr("value","");
	$("#serialNum").attr("value","");
	$("#color").empty();
	$("#colorName").attr("value","");
	
    $('#orgTablebody tr').each(function () {                
        $(this).remove();
    });
}
/**
 * once click the button, it will help to search brand
 */
function searchColor(){
	var colorName = $.trim($("#colorName").val());
	if (colorName != "") {
	    var params= "formBean.colorNames=" + colorName  ; 
    
        var url = encodeURI(encodeURI("productJSPAction!searchColor?" +params));
	
        window.open(url,'新窗口','height=400, width=300, toolbar=no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=no');  
	} else {
        alert("请输入颜色名称");
    }; 
}
function selectColor(data){
	var dataArray = data.split(";");
	$.each(dataArray,function(key,val){
	    if (val != ""){
		    var added = false;
            var colorArray = val.split(",");
            $("#color option").each(function(){
            	   if($(this).val() == colorArray[0])
            	      added = true;
            	   });
     	   if (added == false)
              $("#color").append("<option value='"+ colorArray[0]+"'>"+ colorArray[1]+"</option>");
		}
	});
}
function removeColor(){
	$("#color").find("option:selected").each(function(){
	   var removeColor = $(this).val();
	   if (removeColor != 0 && removeColor != undefined)
		   $("#color option[value='"+removeColor+"']").remove();  
       });
}
function clickSize(){
	var sizes = $("#size").val().toString().split(",");
	if (sizes[0] == "")
		$("#size").find("option:selected").attr("selected", false);
}

</script>

</head>
<body>
<s:form id="barcodeGenForm" action="" method="POST"  theme="simple">
 <table width="95%" align="center"  class="OuterTable">
 <tr><td>

 <table width="100%" border="0">
    <s:hidden name="formBean.productBarcode.product.area.area_ID" value="1"/>     
    <tr class="InnerTableContent">
      <td width="80" height="19"><strong>年份：</strong></td>
      <td width="130">
      		<s:select name="formBean.productBarcode.product.year.year_ID" size="1" id="year_ID"  list="uiBean.basicData.yearList" listKey="year_ID" listValue="year"  />     
      </td>
      <td width="85"><strong>季度：</strong></td>
      <td width="130">
            <s:select name="formBean.productBarcode.product.quarter.quarter_ID" size="1" id="quarter_ID" list="uiBean.basicData.quarterList" listKey="quarter_ID" listValue="quarter_Name"  />
      </td>
      <td width="85"><strong>品牌：</strong></td>
      <td colspan="2">
         <%@ include file="SearchBrandStub.jsp"%>
      </td>
      
      <td>&nbsp;</td>        
    </tr>
    <tr class="InnerTableContent">
      <td height="19"><strong>齐码数量：</strong></td>
      <td>
          <s:select name="formBean.productBarcode.product.numPerHand" size="1" id="numPerHand" list="uiBean.basicData.numPerHandList" listKey="numPerHand" listValue="numPerHand"/>     
      </td>
      <td><strong>货品类：</strong></td>
      <td>
          <s:select name="formBean.productBarcode.product.category.category_ID" cssClass="easyui-combobox" size="1" id="category_ID" list="uiBean.basicData.categoryList" listKey="category_ID" listValue="category_Name"  headerKey="-1" headerValue="" />      </td>
      <td><strong>单位：</strong></td>
      <td>
      	 <s:select name="formBean.productBarcode.product.unit" size="1" id="unit" list="uiBean.basicData.unitList" listKey="productUnit" listValue="productUnit"/>
      </td>
      <td>&nbsp;</td>
      <td>&nbsp;</td>        
    </tr>
   <tr class="InnerTableContent">
      <td height="4" colspan="8"><hr width="100%" color="#FFCC00"/></td>
    </tr>
    <tr class="InnerTableContent">
		      <td width="80" height="19"><strong>货号：</strong></td>
		      <td width="110">
		       <input type="text" name="formBean.productBarcode.product.productCode" id="productCode"  onfocus="this.select();" maxlength="20" size="9"/>*
		      </td>
		      <td width="80"><strong>进价:</strong></td>
		      <td width="110">  
		        <input type="text" name="formBean.productBarcode.product.recCost" id="recCost" onfocus="this.select();" size="9"/>
		      </td>
		      <td width="80"><strong>厂家零售价：</strong></td>
		      <td width="110"><input type="text" name="formBean.productBarcode.product.salesPriceFactory" id="salesPriceFactory" onfocus="this.select();" size="9"/></td>
		      <td width="60"><strong>折扣:</strong></td>
		      <td><input type="text" name="formBean.productBarcode.product.discount" id="discount" onfocus="this.select();" size="9"/></td>
		    </tr>
		    <tr class="InnerTableContent">
              <td><strong>连锁零售价：</strong></td>
		      <td><input type="text" name="formBean.productBarcode.product.salesPrice" id="salesPrice" onfocus="this.select();" size="9"/></td>
		      <td height="19"><strong>预设价1：</strong></td>
		      <td><input type="text" name="formBean.productBarcode.product.wholeSalePrice" id="wholeSalePrice" onfocus="this.select();" size="9"/></td>
		      <td><strong>预设价2：</strong></td>
		      <td><input type="text" name="formBean.productBarcode.product.wholeSalePrice2" id="wholeSalePrice2" onfocus="this.select();" size="9"/></td>
		      <td><strong>预设价3：</strong></td>
		      <td><input type="text" name="formBean.productBarcode.product.wholeSalePrice3" id="wholeSalePrice3" onfocus="this.select();" size="9"/></td>
		    </tr>  
    <tr class="InnerTableContent">
		  <td height="4" colspan="8"><hr width="100%" color="#FFCC00"/></td>
	</tr>
    <tr class="InnerTableContent">
      <td height="19"><strong>商品编码：</strong></td>
      <td colspan="7"><input type="text" name="formBean.productBarcode.product.serialNum" id="serialNum" onfocus="this.select();"/> <input type="button" value="获取已录信息" onclick="getProductColors();"/>(*当需要为已经存在的货品添加额外颜色和尺码的条码时，请输入) <br/> <div id="colorsDiv"></div></td>
    </tr>
    <tr class="InnerTableContent">
      <td height="4" colspan="8"><hr width="100%" color="#FFCC00"/></td>
    </tr>
    <tr class="InnerTableContent">
      <td height="19"><strong>颜色：</strong></td>
      <td colspan="4">*多个颜色间用 - 分开,比如"红-黑-黄"<br/>
          <input type="text" id="colorName" size="10" onfocus="this.select();"/><input id="searchColorBt" type="button" onclick="searchColor();" value="查找"/><br/><br/>
          <select name="formBean.colorIds" id="color" multiple size="5" style="width:94px"></select><input id="searchColorBt" type="button" onclick="removeColor();" value="删除"/></td>
      <td></td>
      <td><!--<strong>尺码：</strong>--></td>
      <td><!--<s:select name="formBean.sizeIds" id="size" list="uiBean.basicData.sizeList" listKey="sizeId" listValue="name"  headerKey="" headerValue="--------无尺码---------"  multiple="true" size="15" onclick="clickSize();"/>--></td>
    </tr>

    <tr class="InnerTableContent">
      <td height="4" colspan="8"><hr width="100%" color="#FFCC00"/></td>
    </tr>
    <tr class="InnerTableContent">
      <td height="30">&nbsp;</td>
      <td colspan="3"><input type="button" id="saveButton" name="saveButton" value="保存产品信息 " onclick="saveProduct();" /> </td>
      <td>&nbsp;</td>
      <td>&nbsp;</td>
      <td>&nbsp;</td>
      <td>&nbsp;</td>      
    </tr>
  </table>
   </td></tr>
 </table>
</s:form>

<jsp:include page="../include/ProductListTable.jsp"/>
<br/>
</body>
</html>