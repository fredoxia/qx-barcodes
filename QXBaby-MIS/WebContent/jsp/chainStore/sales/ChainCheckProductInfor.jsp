<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib prefix="s" uri="/struts-tags" %>
<%@ page import="com.onlineMIS.common.Common_util,java.util.Date,java.text.SimpleDateFormat" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=8" /> 
<title>千禧宝贝连锁店管理信息系统</title>
<%@ include file="../../common/Style.jsp"%>
<link href="<%=request.getContextPath()%>/conf_files/css/pagination.css" rel="stylesheet" type="text/css"/>
<SCRIPT src="<%=request.getContextPath()%>/conf_files/js/pagenav1.1.js" type=text/javascript></SCRIPT>
<SCRIPT src="<%=request.getContextPath()%>/conf_files/js/ChainInvenTrace.js" type=text/javascript></SCRIPT>
<script>
var flag = false;
$(window).unload(function () {
	  if (flag == true){
		  var params= "formBean.chainId=" + $("#chainId").val() ; 
	      parent.$.post("actionChain/chainUserJSON!destoryOwnerLogin",params,back,"json");
		  alert("已经清除之前店主登陆信息");
	  }
	});

function back(data){}
/**
 * user input the product code and press enter
 */
function searchProductsProductCode(){
	var productCode = $("#productCode").val();
	var barcode = $("#barcode").val();

	 if (validateProductCodeInput(productCode)){
	    var url = encodeURI(encodeURI("actionChain/chainSalesJSPAction!scanByProductCode?formBean.productCode=" + productCode+"&formBean.chainId=" + $("#chainId").val()))  ; 
	
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
	var params= "formBean.barcode=" + currentBarcode + "&formBean.chainId=" + $("#chainId").val() ; 
	
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
		var yearName = "";
		var quarterName = "";
		
		if (color != null)
    		colorName = color.name;
		var year = data.productBarcode.product.year;
		if (year != null)
			yearName = year.year;
		var quarter = data.productBarcode.product.quarter;
		if (quarter != null)
			quarterName = quarter.quarter_Name;
		var salePrice = data.productBarcode.product.salesPrice;

		var barcodeS = "<a href='#' onclick=\"traceInventory('"+barcode+"', '')\">"+barcode+"</a>";
		$("#barcode").attr("value",barcode);
		$("#barcodeDiv").html(barcodeS);
		$("#brandDiv").html(brand);
		$("#yearDiv").html(yearName);
		$("#quarterDiv").html(quarterName);
		$("#categoryDiv").html(product.category.category_Name);
		$("#productCodeDiv").html(productCode);
		$("#colorDiv").html(colorName);
		$("#quantityDiv").html(product.numPerHand);
		$("#unitDiv").html(product.unit);
		$("#salePriceDiv").html(salePrice);
		$("#inventoryDiv").html(data.productBarcode.boughtBefore);
		$("#wholePriceDiv").html("");
		$("#costDiv").html("");
		
		checkProductDetail();
	}
}
function validateRowInputFromChild(index_trigger,suffix, currentBarcode){
	return true;
}
function clearSearch(){
	$("#productInfo").html("");
	$("#barcode").attr("value","");
	$("#productCode").attr("value","");
}
function ownerLoginSuccess(){
	checkProductDetail();
}
function checkProductDetail(){
	var barcode = $("#barcode").val();
	if (barcode.length == 12){
	   var params= "formBean.barcode=" + barcode + "&formBean.chainId=" + $("#chainId").val() ; 
	
	   $.post("actionChain/chainSalesJSONAction!checkProductInfor",params, backCheckProductDetailProcess,"json");
	} else {
		alert ("不是一个正常的条码");
	}
}
function backCheckProductDetailProcess(data){
	var response = data.response;
	var returnCode = response.returnCode;
    if (returnCode == ERROR){
        alert(response.message);
		$("#barcodeDiv").html("");
		$("#brandDiv").html("");
		$("#yearDiv").html("");
		$("#quarterDiv").html("");
		$("#categoryDiv").html("");
		$("#productCodeDiv").html("");
		$("#colorDiv").html("");
		$("#quantityDiv").html("");
		$("#unitDiv").html("");
		$("#salePriceDiv").html("");
		$("#wholePriceDiv").html("");
		$("#costDiv").html("");
	} else 	{
		var productBarcode = response.returnValue;
		var product = productBarcode.product;
		var year = product.year;
		var quarter = product.quarter;
		var color = productBarcode.color;

		var barcodeS = "<a href='#' onclick=\"traceInventory('"+productBarcode.barcode+"', '')\">"+productBarcode.barcode+"</a>";
		$("#barcodeDiv").html(barcodeS);
		$("#brandDiv").html(product.brand.brand_Name);
		$("#yearDiv").html(year.year);
		$("#quarterDiv").html(quarter.quarter_Name);
		$("#categoryDiv").html(product.category.category_Name);
		$("#productCodeDiv").html(product.productCode);
		if (color != null)
		    $("#colorDiv").html(color.name);
		else 
			$("#colorDiv").html("");
		$("#quantityDiv").html(product.numPerHand);
		$("#unitDiv").html(product.unit);
		$("#salePriceDiv").html(product.salesPrice);
		$("#inventoryDiv").html(productBarcode.boughtBefore);
		
		if (returnCode == NO_AUTHORITY){
		    authorizeDialog();
			$("#wholePriceDiv").html("");
			$("#costDiv").html("");
	    } else if (returnCode == SUCCESS){
			$("#wholePriceDiv").html(product.lastChoosePrice);
			$("#costDiv").html(product.lastInputPrice);
	    }
		
	}
}

function authorizeDialog(){
	$.modalDialog({
		title : '授权登陆',
		width : 330,
		height : 180,
		modal : true,
		href : '<%=request.getContextPath()%>/jsp/chainStore/include/OwenerLoginDialog.jsp',
		buttons : [ {
			text : '授权登陆',
			handler : function() {
				confirmLogin(); 
			}
		} ]
		});
}
</script>
</head>
<body>
   <s:form id="CheckProductForm" action="/actionChain/salesAction!searchOrders" theme="simple" method="POST"> 

     <table width="90%" align="center"  class="OuterTable">
	    <tr><td>
			 <table width="100%" border="0">
			    <tr>
			       <td height="50" colspan="7">
					   <table width="100%" border="0">
					    <tr class="PBAOuterTableTitale">
				          <td height="30" colspan="3">搜索货品资料<br/>
				          - 此功能可以帮助用户查询商品一些详细信息，但是批发价,这些需要输入店主账号<br/>
				          - 关闭此页面之后，刚才的登陆信息会自动清空，下次还需要输入账号和密码 
				          </td>
					    </tr>
					    <tr class="InnerTableContent">
					      <td height="30">&nbsp;</td>
					      <td width="50"><strong>连锁店</strong></td>
					      <td>		
					      	<s:select id="chainId" name="formBean.chainSalesOrder.chainStore.chain_id"  list="uiBean.chainStores" listKey="chain_id" listValue="chain_name"/>
					      	<s:hidden id="owner" name="uiBean.orderCreator.user_name"/>
						  </td>
					    </tr>
					    <tr class="InnerTableContent">
					      <td height="30">&nbsp;</td>
					      <td><strong>条码</strong></td>
					      <td>		
					      	<s:textfield name="formBean.barcode" id="barcode"/>
						  </td>
					    </tr>
						<tr class="InnerTableContent">
					      <td height="30">&nbsp;</td>
					      <td><strong>货号</strong></td>
					      <td>		
						  	<s:textfield name="formBean.productCode" id="productCode" size="18"/><input type="button" id="checkBrandBt" value="查询" onclick="searchProductsProductCode();"/><input type="button" id="clearBtn" value="清除" onclick="clearSearch();"/>
						  	<div id="productInfo"></div>
						  </td>
					    </tr>					    
	                    <tr class="InnerTableContent">
					      <td height="30" colspan="2">&nbsp;</td>
					      <td ><input type="button" value="产品详细信息" onclick="checkProductDetail()"/></td>
					    </tr>
					  </table></td>
			    </tr>
			    <tr class="InnerTableContent">
			      <td height="4" colspan="7"><hr width="100%" color="#FFCC00"/></td>
			    </tr>
			    <tr>
			      <td colspan="7">
			         <!-- table to display the draft order information -->
			         <div id="productDiv" style="display: block">
						 <table width="35%" border="0" align="left">
					       <tr class="PBAOuterTableTitale">
					          <td colspan="2">货品信息</td>
					       </tr>
					       <tr class="InnerTableContent">
					          <td height="18" width="50"><strong>条形码 </strong>: </td>
					          <td><div id="barcodeDiv"></div></td>
					       </tr>
					       <tr class="InnerTableContent" style="background-color: rgb(255, 250, 208);">
					          <td height="18"><strong>品牌 </strong>:</td>
					          <td><div id="brandDiv"></div></td>
					       </tr>
					       <tr class="InnerTableContent">
					          <td height="18"><strong>年份  </strong>:</td>
					          <td><div id="yearDiv"></div></td>
					       </tr>
					       <tr class="InnerTableContent" style="background-color: rgb(255, 250, 208);">
					          <td height="18"><strong>季度</strong>         :</td>
					          <td><div id="quarterDiv"></div></td>
					       </tr>
					       <tr class="InnerTableContent">
					          <td height="18"><strong>类别</strong>       :</td>
					          <td><div id="categoryDiv"></div></td>
					       </tr>
					       <tr class="InnerTableContent" style="background-color: rgb(255, 250, 208);">
					          <td height="18"><strong>产品货号</strong>    :</td>
					          <td><div id="productCodeDiv"></div></td>
					       </tr>
					       <tr class="InnerTableContent">
					          <td height="18"><strong>颜色</strong>       :</td>
					          <td><div id="colorDiv"></div></td>
					       </tr>
					       <tr class="InnerTableContent" style="background-color: rgb(255, 250, 208);">
					          <td height="18"><strong>齐码数量 </strong>   :</td>
					          <td><div id="quantityDiv"></div></td>
					       </tr>
					       <tr class="InnerTableContent">
					          <td height="18"><strong>单位 </strong> :</td>
					          <td><div id="unitDiv"></div></td>
					       </tr>
					       <tr class="InnerTableContent" style="background-color: rgb(255, 250, 208);">
					          <td height="18"><strong>连锁零售价 </strong>       :</td>
					          <td><div id="salePriceDiv"></div></td>
					       </tr>
					       <tr class="InnerTableContent">
					          <td height="18"><strong>当前库存 </strong>       :</td>
					          <td><div id="inventoryDiv"></div></td>
					       </tr>
				          <tr class="PBAOuterTableTitale">
					          <td colspan="2">以下信息需要店主账号权限</td>
					       </tr>					      
					       <tr class="InnerTableContent" style="background-color: rgb(255, 250, 208);">
					          <td height="18"><strong>总部批发价 </strong>       :</td>
					          <td><div id="wholePriceDiv"></div></td>
					       </tr>
					       <tr class="InnerTableContent">
					          <td height="18"><strong>我的最新进价 </strong>       :</td>
					          <td><div id="costDiv"></div></td>
					       </tr>
					    </table>
					  </div>
			      </td>
			    </tr>
			  </table>
	   </td></tr>
	 </table>
	 </s:form>
<script>
$(document).ready(function(){
	parent.$.messager.progress('close'); 
});
</script>
</body>
</html>