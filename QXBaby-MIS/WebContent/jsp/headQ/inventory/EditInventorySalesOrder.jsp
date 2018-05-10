<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib prefix="s" uri="/struts-tags" %>
<html>
<head>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><s:property value="formBean.order.order_type_ws"/> <s:property value="formBean.order.order_Status_s"/></title>
<%@ include file="../../common/Style.jsp"%>
<script type="text/javascript" src="<%=request.getContextPath()%>/conf_files/js/inventory-order.js?v=4-15"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/conf_files/js/HtmlTable.js"></script>
<script type="text/javascript">
var baseurl = "<%=request.getContextPath()%>";

//the index is depends on the preview or not
<s:if test="formBean.isPreview == true">
index = parseInt("<s:property value='formBean.order.product_List.size()'/>");
</s:if><s:else>
index = parseInt("<s:property value='formBean.order.product_Set.size()'/>");
</s:else>

function saveToDraft(){
	calculateTotal();
	
	if (validateForm()){
		$.messager.progress({
			title : '提示',
			text : '数据处理中，请稍后....'
		});
	   document.inventoryOrderForm.action = "<%=request.getContextPath()%>/action/inventoryOrder!saveToDraft";
	   document.inventoryOrderForm.submit();
	}
}

/**
 * once acct finish the edit and save
 */
function save(){
	calculateTotal();
	if (validateForm()){
		$.messager.progress({
			title : '提示',
			text : '数据处理中，请稍后....'
		});
	   document.inventoryOrderForm.action = "<%=request.getContextPath()%>/action/inventoryOrder!acctSave";
	   document.inventoryOrderForm.submit();
	}
}

/**
 * button to preview the order
 */
function preview(){
	calculateTotal();
	if (validateForm()){
		$.messager.progress({
			title : '提示',
			text : '数据处理中，请稍后....'
		});
		   document.inventoryOrderForm.action = "<%=request.getContextPath()%>/action/inventoryOrder!previewOrder";
		   document.inventoryOrderForm.submit();
	}
}

/**
 * to submit the order to account
 */
function submitOrder(){
	calculateTotal();
	if (validateForm()){
		$.messager.progress({
			title : '提示',
			text : '数据处理中，请稍后....'
		});
		   document.inventoryOrderForm.action = "<%=request.getContextPath()%>/action/inventoryOrder!save";
		   document.inventoryOrderForm.submit();
	}
}

/**
 * 下载配货单
 */
function downloadOrder(){
	var url = "<%=request.getContextPath()%>/action/exportInventoryOrToExcel!downloadOrder";
	var msg = "请确认，你在下载配货单前 没有修改单据 或者已经保存了修改项目";
	if (confirm(msg)){
	   document.inventoryOrderForm.action = url;
	   document.inventoryOrderForm.submit();	
	}
}
function printPOSOrder(){
	var msg = "请确认，你在打印pos小票配货单前 没有修改单据 或者已经保存了修改项目";
	if (confirm(msg)){
		    printPOSOrderToPrinter();	
	}
}

function exportBarcodeToExcel(){
	var url = "<%=request.getContextPath()%>/action/exportInventoryOrToExcel!ExportJinSuanOrder";

	var msg = "请确认，你在下载订单条码前 没有修改单据 或者已经保存了修改项目";
	if (confirm(msg)){
	   document.inventoryOrderForm.action = url;
	   document.inventoryOrderForm.submit();	
	}
}

$(document).ready(function(){
	$("#clientName").focus();
	parent.$.messager.progress('close'); 
	jQuery.excel('excelTable');
	$("#org_table tr").mouseover(function(){      
		$(this).addClass("over");}).mouseout(function(){    
		$(this).removeClass("over");}); 
});
</script>

</head>
<body>

<s:form action="/action/inventoryOrder!previewOrder" method="POST" name="inventoryOrderForm"  id="inventoryOrderForm" theme="simple" enctype="multipart/form-data">
 <s:hidden name="formBean.order.order_ID"  id="orderId"/>
 <s:hidden name="formBean.order.order_type" id="orderType"/>
 <s:hidden name="formBean.order.order_Status"/>
 <s:hidden name="formBean.order.importTimes"/>
 <s:hidden name="formBean.order.pdaScanner.user_id"/>
 <table cellpadding="0" border="0" cellspacing="0"  style="width: 98%" align="center" class="OuterTable">
	<tr class="title">
	     <td colspan="7"><s:if test="formBean.isPreview == true">预览</s:if>
	     <s:if test="formBean.order.order_type == 1">
	          <font style="color:red"><s:property value="formBean.order.order_type_ws"/> </font>
	     </s:if><s:else>
	     	  <s:property value="formBean.order.order_type_ws"/>
	     </s:else>
	     <s:property value="formBean.order.order_Status_s"/></td>
	</tr>
    
	<tr height="10">
	     <td colspan="7"><hr/></td>
	</tr>

	<%@ include file="../include/EditInventorySalesOrderDetail.jsp"%>
    <s:if test="formBean.isPreview == true">
      <tr height="10">
	  	     <td>&nbsp;</td>
			 <td><input type="button" value="导入文件" onclick="importFile();"/>&nbsp;<input type="button" onclick="submitOrder();" value="单据提交"/></td>			 					 		
			 <td>&nbsp;</td>
			 <td><input type="button" value="存入草稿" onclick="saveToDraft();"/><input type="button" value="重新计算" onclick="calculateTotal();"/></td>			 					 		
			 <td>排序<input type="checkbox" name="formBean.sorting" value="true"/></td>			 					 		
			 <td></td>
			 <td>&nbsp;</td>	
	  </tr>
    </s:if><s:elseif test="formBean.order.order_Status==0">
	  <tr height="10">
	  	     <td>&nbsp;</td>
			 <td><input type="button" value="导入文件" onclick="importFile();"/>&nbsp; <input type="button" onclick="submitOrder();" value="单据提交"/></td>			 					 		
			 <td>&nbsp;</td>
			 <td><input type="button" value="存入草稿" onclick="saveToDraft();"/><input type="button" value="重新计算" onclick="calculateTotal();"/></td>			 					 		
			 <td>排序<input type="checkbox" name="formBean.sorting" value="true"/></td>			 					 		
			 <td></td>
			 <td></td>	
	  </tr>
	</s:elseif><s:elseif test="formBean.order.order_Status==9">
	  <tr height="10">
	  	     <td>&nbsp;</td>
			 <td><input type="button" value="导入文件" onclick="importFile();"/>&nbsp; <input type="button" onclick="submitOrder();" value="单据提交"/></td>			 					 		
			 <td><input type="button" value="条码标签导出" onclick="exportBarcodeToExcel();"/></td>
			 <td><input type="button" value="存入草稿" onclick="saveToDraft();"/><input type="button" value="重新计算" onclick="calculateTotal();"/></td>			 					 		
			 <td>排序<input type="checkbox" name="formBean.sorting" value="true"/></td>
			 <td><input type="button" value="删除订单" onclick="deleteOrder();"/></td>			 					 		
			 <td><input type="button" value="下载配货单" onclick="downloadOrder();"/><input type="button" value="打印小票配货" onclick="printPOSOrder();"/></td>	
	  </tr>
	</s:elseif><s:elseif test="#session.LOGIN_USER.containFunction('inventoryOrder!acctProcess') || #session.LOGIN_USER.roleType == 99">
	  <tr height="10">
	         <td>&nbsp;</td>
	  	     <td>&nbsp;</td>
			 <td><input type="button" value="条码标签导出" onclick="exportBarcodeToExcel();"/></td>			 					 		
			 <td><input type="button" value="重新计算" onclick="calculateTotal();"/></td>
			 <td><input type="button" value="保存" onclick="save();"/></td>			 					 					 					 		
			 <td style="width: 18%">排序<input type="checkbox" name="formBean.sorting" value="true"/></td>
			 <td><input type="button" value="下载配货单" onclick="downloadOrder();"/></td>	
	  </tr>				 		      
	</s:elseif>
	  <tr height="10">
	     <td colspan="7"><s:fielderror/></td>
	  </tr>  
</table>

</s:form>
<object  classid="clsid:AF33188F-6656-4549-99A6-E394F0CE4EA4"       
         codebase="<%=request.getContextPath()%>/conf_files/sc_setup.exe"     
         id="pazu"       
         name="pazu" >       
    <param  name="License"  value="8F34B771723DCC171F931EA900F9967E"/>     
</object>
<bgsound id="bgs" src="bg.mp3" loop=1>
</body>
</html>