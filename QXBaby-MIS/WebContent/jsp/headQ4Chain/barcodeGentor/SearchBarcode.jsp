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
<script>
$(document).ready(function(){
	parent.$.messager.progress('close'); 
	$("#brandName").focus();
});

/**
 * to ensure user has select some criteria
 */
function validateSearch(){
	var area = $("#area_ID").val();
	var year = $("#year_ID").val();
	var quarter = $("#quarter_ID").val();
	var brand = $("#brand_ID").val();
	var category = $("#category_ID").val();
	var productCode = $("#productCode").val();
	var barcode = $("#barcode").val();
	var time = $("#needCreateDate").is(':checked');
	if (year == -1 && quarter==-1 && brand==0 && category==-1 && productCode == "" && barcode=="" && time==false){
		alert("请在选项（年份，季度，品牌，货品，货号，条形码，录入时间）中选出你的条码范围，否则数据量太庞大!");
		return false;
	} else
		return true;
}

function exportBarcodeToExcel(){
	if (validateCheckbox()){
		var url = "action4Chain/barcodeGenJSP!exportBarcode";
		document.searchedBarcodeForm.action = url;
		document.searchedBarcodeForm.submit();	
	}
}

function searchBarcode(){
    if (validateSearch()){
		parent.$.messager.progress({
			text : '数据获取中，请稍后....'
		});
       var params=$("#barcodeSearchForm").serialize();  
       $.post("action4Chain/barcodeGenJSON!searchBarcode",params, backProcess,"json");
    }
}

function backProcess(data){
	parent.$.messager.progress('close'); 
	clearAllData();

	var response = data.response;
	if (response.returnCode == WARNING)
		alert(response.message);
	else if (response.returnCode != SUCCESS){
        alert(response.message);
        return;
    }
	
	var barcodes = response.returnValue;
	
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
		          $("<tr align='center' class='InnerTableContent'" + bg +"><td><input type='checkbox' name='formBean.selectedBarcodes' value='"+barcodes[i].barcode+"'/></td><td>"+
				          j+"</td><td>"+
				          barcodes[i].product.year.year + " " + barcodes[i].product.quarter.quarter_Name +"</td><td>"+
				          barcodes[i].product.brand.brand_Name+"</td><td>"+
				          barcodes[i].product.category.category_Name+"</td><td>"+
				          barcodes[i].product.productCode+"</td><td>"+
				          parseValue(barcodes[i].color.name)+"</td><td>"+

				          barcodes[i].product.numPerHand + "/" + barcodes[i].product.unit +"</td><td>"+
				          barcodes[i].product.salesPrice+"</td><td>"+
				          barcodes[i].product.wholeSalePrice+"</td><td>"+
				          barcodes[i].barcode+"</td><td>"+
				          barcodes[i].createDate+"</td><td><a href='#' onclick=\"window.open ('barcodeGenJSP!searchForUpdate?formBean.productBarcode.barcode="+barcodes[i].barcode+"','新窗口','height=550, width=400, toolbar=no, menubar=no, scrollbars=no, resizable=no, location=no, status=no');\"><img src='<%=request.getContextPath()%>/conf_files/web-image/editor.gif' border='0'/></a></td><td><a href='#' onclick='addTab6(\"barcodeGenJSP!continueCreateBarcode?formBean.productBarcode.product.serialNum="+ barcodes[i].product.serialNum +"\",\"继续添加条码"+ barcodes[i].product.serialNum +"\");'><img border='0' src='<%=request.getContextPath()%>/conf_files/easyUI/themes/icons/text_1.png' title='继续为该货品的其他颜色补充条码'/></a></td></tr>").appendTo("#orgTablebody");
	        }
	    }

	    $("<tr class='InnerTableContent'><td colspan=13><div id='error' style='color:red;font-size:13px'></div><div id='tip'></div></td></tr>").appendTo("#orgTablebody");
        $("<tr class='InnerTableContent' style='background-color: rgb(255, 250, 208);' align='left'><td></td><td colspan=13><input type='button' value='导出产品信息' onclick='exportBarcodeToExcel();'/></td></tr>").appendTo("#orgTablebody");
    }else {
    	$("<tr class='InnerTableContent' style='background-color: rgb(255, 250, 208);' align='center'><td colspan=14><font color='red'>对应信息没有查询信息</font> </td></tr>").appendTo("#orgTablebody");
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
		$("input[name='formBean.selectedBarcodes']").attr("checked",true); 
	else
		$("input[name='formBean.selectedBarcodes']").attr("checked",false); 
	
}
function validateCheckbox(){
	if ($("input[name='formBean.selectedBarcodes']:checked").length == 0){
		alert("请先选中货品");
		return false;
	}
	return true;
}

</script>

</head>
<body>
<s:form id="barcodeSearchForm" action="" method="POST"  theme="simple">
 <table width="95%" align="center"  class="OuterTable">
    <tr><td>

	 <table width="100%" border="0">     
	    <tr class="InnerTableContent">
	      <td width="80" height="19"><strong>年份：</strong></td>
	      <td width="130">
	      		<s:select name="formBean.productBarcode.product.year.year_ID" size="1" id="year_ID"  list="uiBean.years" listKey="year_ID" listValue="year"   headerKey="-1" headerValue="---全选---"  />     
	      </td>
	      <td width="85"><strong>季度：</strong></td>
	      <td width="130">
	            <s:select name="formBean.productBarcode.product.quarter.quarter_ID" size="1" id="quarter_ID" list="uiBean.quarters" listKey="quarter_ID" listValue="quarter_Name"   headerKey="-1" headerValue="---全选---"  />
	      </td>
	      <td width="85"><strong>品牌：</strong></td>
	      <td width="250">
	         <%@ include file="SearchBrandStub.jsp"%>
	      </td>
	      <td width="70">&nbsp;</td>
	      <td>&nbsp;</td>        
	    </tr>
	    <tr class="InnerTableContent">
	      <td height="19"><strong>齐码数量：</strong></td>
	      <td>
	          <s:select name="formBean.productBarcode.product.numPerHand" size="1" id="numPerHand" list="uiBean.numPerHands" listKey="numPerHand" listValue="numPerHand"  headerKey="-1" headerValue="---全选---" />     
	      </td>
	      <td><strong>货品类：</strong></td>
	      <td>
	         <s:select name="formBean.productBarcode.product.category.category_ID" size="1" id="category_ID" list="uiBean.categories" listKey="category_ID" listValue="category_Name"   headerKey="-1" headerValue="---全选---"  />      </td>
		  <td><strong>货号：</strong></td>
		  <td><input type="text" name="formBean.productBarcode.product.productCode" id="productCode"  onfocus="this.select();" maxlength="20" size="9"/></td>
			      
	      <td>&nbsp;</td>
	      <td>&nbsp;</td>        
	    </tr>
	   <tr class="InnerTableContent">
	      <td height="4" colspan="8"><hr width="100%" color="#FFCC00"/></td>
	    </tr>
	    <tr class="InnerTableContent">
		      <td height="19"><strong>条形码：</strong></td>
		      <td><input type="text" name="formBean.productBarcode.barcode" id="barcode" title="请输入12位的条码"/></td>
	          <td></td>
	          <td></td>
	          <td>&nbsp;</td>
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
   </td>
   </tr>
 </table>
</s:form>

<jsp:include page="ProductListTableCheckbox.jsp"/>

</body>
</html>