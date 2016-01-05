<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib prefix="s" uri="/struts-tags" %>
<%@ page import="com.onlineMIS.ORM.entity.headQ.barcodeGentor.*,java.util.Date,java.text.SimpleDateFormat" %>
<html>
<head>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=8" />
<title>查询条形码</title>
<%@ include file="../../common/Style.jsp"%>
<script type="text/javascript" src="<%=request.getContextPath()%>/conf_files/js/BarcodeSearchKeys.js"></script>

<script type="text/javascript" src="<%=request.getContextPath()%>/conf_files/js/HtmlTable.js"></script>
<script>
$(document).ready(function(){
	parent.$.messager.progress('close'); 
	jQuery.excel('InnerTableContent');
	$("#brandName").focus();
});
/**
 * to ensure user has select some criteria
 */
function validateSearch(){
	$("#brands").find("option").attr("selected","selected"); 
	var area = $("#area_ID").val();
	var year = $("#year_ID").val();
	var quarter = $("#quarter_ID").val();
	var brand = $("#brands").val();
	var category = $("#category_ID").val();
	var productCode = $("#productCode").val();
	var barcode = $("#barcode").val();
	var time = $("#needCreateDate").is(':checked');
	if (year==0 && quarter==0 && brand==null && category==0 && productCode == "" && barcode=="" && time==false){
		alert("请在选项（年份，季度，品牌，货品，货号，条形码，录入时间）中选出你的条码范围，否则数据量太庞大!");
		return false;
	} else
		return true;
}

function exportBarcodeToExcel(){
	if (validateCheckbox()){
		var url = "action/productJSPAction.action";
		document.searchedBarcodeForm.action = url;
		document.searchedBarcodeForm.submit();	
	}
}

function exportToPrintBarcode(){
	if (validateCheckbox()){
		var url = "action/productJSPAction!exportToPrintBarcode";
		document.searchedBarcodeForm.action = url;
		document.searchedBarcodeForm.submit();	
	}
}

function synchronizePrice(){
	if (validateCheckbox()){
		parent.$.messager.progress({
			text : '数据更新中，请稍后....'
		});
		$("#error").html("");
	    var params=$("#searchedBarcodeForm").serialize();  
	    $.post("action/productJSONAction!synchronizePrice",params, backProcessS,"json");
	}
}

function backProcessS(data){
	parent.$.messager.progress('close');
	
	var ERROR_NOT_FOUND = data.ERROR_NOT_FOUND;
	var ERROR_PRICE_INCONSISTANT = data.ERROR_PRICE_INCONSISTANT;
	var ERROR_PRODUCT_CODE_INCONSISTANT = data.ERROR_PRODUCT_CODE_INCONSISTANT;
	var error = "";
	
	if (ERROR_NOT_FOUND == "" && ERROR_PRICE_INCONSISTANT== "" && ERROR_PRODUCT_CODE_INCONSISTANT== "")
		alert("价格同步成功，请重新查询刷新!");
	else {
		if (ERROR_NOT_FOUND != "")
			error += "以下条码未在精算中找到 ： " + ERROR_NOT_FOUND;
		
		if (ERROR_PRICE_INCONSISTANT != "")
			error += "<br/>以下货品价格与精算不一致 ： " + ERROR_PRICE_INCONSISTANT;
		
		if (ERROR_PRODUCT_CODE_INCONSISTANT != "")
			error += "<br/>以下货品的货号与精算不一致 ： " + ERROR_PRODUCT_CODE_INCONSISTANT;
		
		$("#error").html(error);
		
		if (error != "")
			alert("请核对错误信息!\n部分货品价格同步成功，请重新查询刷新!");
	}
}

function searchBarcode(){
	var categoryId = $("#category_ID").combo("getValue");
	if (categoryId != "0" && !isValidPositiveInteger(categoryId)){
		alert("货品类别不是一个正确的输入");
		return;
	}

    if (validateSearch()){
		parent.$.messager.progress({
			text : '数据获取中，请稍后....'
		});
       var params=$("#barcodeSearchForm").serialize();  
       $.post("action/productJSONAction.action",params, backProcess,"json");
    }
}

function backProcess(data){
	parent.$.messager.progress('close'); 
	clearAllData();
	
	var barcodes = data.barcodes;
	
    $('#orgTablebody tr').each(function () {                
        $(this).remove();
    });

    if (barcodes.length != 0){
	    for (var i = 0; i < barcodes.length; i++){
	    	var j = i+1;
	    	var bg = "";
	    	if ((i % 2) == 0)
	    		bg = " style='background-color: rgb(255, 250, 208);'";
	        if (barcodes[i] != "")  {
			      var belong = "";
			      if (barcodes[i].chainStore.chain_id != undefined)
				     belong = barcodes[i].chainStore.chain_name; 
		          $("<tr align='center' class='InnerTableContent'" + bg +"><td><input type='checkbox' name='selectedBarcodes' value='"+barcodes[i].barcode+"'/></td><td>"+
				          j+"</td><td>"+
				          barcodes[i].product.year.year + " " + barcodes[i].product.quarter.quarter_Name +"</td><td>"+
				          barcodes[i].product.brand.brand_Name+"</td><td>"+
				          barcodes[i].product.category.category_Name+"</td><td>"+
				          barcodes[i].product.productCode+"</td><td>"+
				          parseValue(barcodes[i].color.name)+"</td><td>"+
//				          parseValue(barcodes[i].size.name)+"</td><td>"+
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

	    $("<tr class='InnerTableContent'><td colspan=17><div id='error' style='color:red;font-size:13px'></div><div id='tip'></div></td></tr>").appendTo("#orgTablebody");
        $("<tr class='InnerTableContent' style='background-color: rgb(255, 250, 208);' align='left'><td></td><td colspan=16><input type='button' value='导出产品信息' onclick='exportBarcodeToExcel();'/><input type='button' value='导出条码打印' onclick='exportToPrintBarcode();'/><s:if test='#session.LOGIN_USER.containFunction(\"productJSONAction!synchronizePrice\")'><input type='button' value='同步价格' onclick='synchronizePrice();'/></s:if></td></tr>").appendTo("#orgTablebody");
    }else {
    	$("<tr class='InnerTableContent' style='background-color: rgb(255, 250, 208);' align='center'><td colspan=17><font color='red'>对应信息没有查询信息</font> </td></tr>").appendTo("#orgTablebody");
    }  
}


function clearAllData(){
	$("#error").html("");
	$("#tip").html("");

    $('#orgTablebody tr').each(function () {                
        $(this).remove();
    });
}
function showCreateDate(){
	if ($("#needCreateDate").attr("checked") == 'checked')
		$("#createDateDiv").show();
	else
		$("#createDateDiv").hide();
}
function selectAll(){
	if ($("#selectAllCheck").attr("checked") == 'checked')
		$("input[name='selectedBarcodes']").attr("checked",true); 
	else
		$("input[name='selectedBarcodes']").attr("checked",false); 
	
}
function validateCheckbox(){
	if ($("input[name='selectedBarcodes']:checked").length == 0){
		alert("请先选中货品");
		return false;
	}
	return true;
}
function removeBrand(){
	$("#brands").find("option:selected").each(function(){
	   var removeColor = $(this).val();
	   if (removeColor != 0 && removeColor != undefined)
		   $("#brands option[value='"+removeColor+"']").remove();  
       });
}
/**
 * once click the button, it will help to search brand
 */
function searchBrand(){
	var brandName = $.trim($("#brandName").val());
	if (brandName != "") {
	    var params= "formBean.productBarcode.product.brand.brand_Name=" + brandName  ; 
    
        var url = encodeURI(encodeURI("productJSPAction!scanBrand?" +params));
	
        window.open(url,'_blank','height=400, width=300, toolbar=no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=no');  
	} else {
        alert("请输入品牌名称");
    } 
}

/**
 * user click the brand
 */
function selectBrand(brandName, brandId){
	if (brandName != "" && brandId != "" && brandId != 0){
		 $("#brandName").attr("value", "");
    }
    
	var added = false;
     $("#brands option").each(function(){
     	   if($(this).val() == brandId)
     	      added = true;
     	   });
    if (added == false)
       $("#brands").append("<option value='"+ brandId+"'>"+ brandName+"</option>");
}
</script>


</head>
<body>
 	<div class="easyui-layout"  data-options="fit : true,border : false">
		<div data-options="region:'north',border:false" style="height: 170px;">
			<s:form id="barcodeSearchForm" action="" method="POST" theme="simple">
			 <input type="hidden" id="isInitialized" name="formBean.isInitialized"/>
			 <table width="100%" border="0">
			    <tr class="InnerTableContent">
			      <td width="94" height="19"><strong>年份：</strong></td>
			      <td width="234"><s:select name="formBean.productBarcode.product.year.year_ID" size="1" id="year_ID"  list="uiBean.basicData.yearList" listKey="year_ID" listValue="year"  headerKey="0" headerValue="---全选---" />			     </td>
			      <td width="79"><strong>季度：</strong></td>
			      <td width="175"><s:select name="formBean.productBarcode.product.quarter.quarter_ID" size="1" id="quarter_ID"  list="uiBean.basicData.quarterList" listKey="quarter_ID" listValue="quarter_Name"  headerKey="0" headerValue="---全选---" />			      </td>
			      <td width="59"><strong>品牌：</strong></td>
			      <td width="357" rowspan="4">
				  	<s:textfield name="formBean.productBarcode.product.brand.brand_Name" id="brandName" size="10"/>
                       <input id="searchBt" type="button" onclick="searchBrand();" value="查找"/><br/>
			           <select name="formBean.brandIds" id="brands" multiple size="5" style="width:94px"></select><input id="removeBt" type="button" onclick="removeBrand();" value="删除"/>
				  </td>
			    </tr>
			    <tr class="InnerTableContent">
			      <td height="19"><strong>货号：</strong></td>
			      <td><input type="text" name="formBean.productBarcode.product.productCode" id="productCode" title="产品货号"/>			      </td>
			      <td><strong>货品类：</strong></td>
			      <td>
			        <s:select name="formBean.productBarcode.product.category.category_ID" size="1" cssClass="easyui-combobox"  id="category_ID" list="uiBean.basicData.categoryList" listKey="category_ID" listValue="category_Name"  headerKey="0" headerValue="" />			      </td>
			      <td>&nbsp;</td>
		        </tr>
			    <tr class="InnerTableContent">
			      <td height="19"><strong>条形码：</strong></td>
			      <td><input type="text" name="formBean.productBarcode.barcode" id="barcode" title="请输入12位的条码"/></td>
		          <td colspan = "4">
		          	仅总部<input type="radio" name="formBean.productBarcode.chainStore.chain_id" value="99" checked/>
		          	仅连锁店<input type="radio" name="formBean.productBarcode.chainStore.chain_id" value="0"/>
		                            所有<input type="radio" name="formBean.productBarcode.chainStore.chain_id" value="-1"/>
		          </td>

	           </tr>
			    <tr class="InnerTableContent">
			      <td height="19"><strong>录入时间：</strong></td>
			      <td height="19" colspan="3">
			        <table border="0">
			           <tr>
			               <td><input type="checkbox" name="formBean.needCreateDate" id="needCreateDate" onClick="showCreateDate();" value="true" checked/></td>
			               <td>
			                    <div id="createDateDiv" style="display:block">
						                      开始日期 ：<s:textfield id="startDate" name="formBean.startDate" cssClass="easyui-datebox"  data-options="width:100,editable:false"/>
									&nbsp;&nbsp;&nbsp;
							             截止日期 ：<s:textfield id="endDate" name="formBean.endDate" cssClass="easyui-datebox"  data-options="width:100,editable:false"/>
						        </div>			               </td>
			           </tr>
			        </table>			       </td>
			      <td height="19">&nbsp;</td>
		        </tr>
			    <tr class="InnerTableContent">
			      <td height="30">&nbsp;</td>
			      <td><input type="button" name="saveButton" value="查询条形码 " onClick="searchBarcode();" /> </td>
			      <td>&nbsp;</td>
			      <td>&nbsp;</td>
			      <td>&nbsp;</td>
			      <td>&nbsp;</td>
			    </tr>
			    </table>
			</s:form>
		</div>
		<div data-options="region:'center',border:false">
			<jsp:include page="../include/ProductListTableCheckbox.jsp"/>
		</div>
	</div>
<br/>
</body>
</html>