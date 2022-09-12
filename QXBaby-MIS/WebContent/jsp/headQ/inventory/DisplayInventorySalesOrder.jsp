<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib prefix="s" uri="/struts-tags" %>
<%@ page import="com.onlineMIS.ORM.entity.headQ.user.UserInfor,com.onlineMIS.ORM.entity.headQ.inventory.*" %>
<html>
<head>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><s:property value="formBean.order.order_type_ws"/></title>
<%@ include file="../../common/Style.jsp"%>
<script type="text/javascript">
function exportOrderToExcel(){
	var url = "<%=request.getContextPath()%>/action/exportInventoryOrToExcel.action";
	document.inventoryOrderForm.action = url;
	document.inventoryOrderForm.submit();	
}
function exportBarcodeToExcel(){
	var url = "<%=request.getContextPath()%>/action/exportInventoryOrToExcel!ExportJinSuanOrder";
	document.inventoryOrderForm.action = url;
	document.inventoryOrderForm.submit();	
}
function exportOrderToJinSuan(){
	var info = "你确定要导入单据到精算?";
	if (confirm(info)){	
		var url = "<%=request.getContextPath()%>/action/exportInventoryOrToJinSuan!exportToJinSuan";
		document.inventoryOrderForm.action = url;
		document.inventoryOrderForm.submit();	
	}
}
function completeAuditOrder(){
	var info = "你确定完成了单据的审核?";
	if (confirm(info)){	
		var url = "<%=request.getContextPath()%>/action/inventoryOrder!acctAuditOrder";
		document.inventoryOrderForm.action = url;
		document.inventoryOrderForm.submit();	
	}
}
function edit(){
	var url = "<%=request.getContextPath()%>/action/inventoryOrder!acctUpdate";
	document.inventoryOrderForm.action = url;
	document.inventoryOrderForm.submit();	
}
function cancelOrder(){
	var info = "你确定红冲此单据?";
	if (confirm(info)){	
	    var url = "<%=request.getContextPath()%>/action/inventoryOrder!cancelOrder";
	    document.inventoryOrderForm.action = url;
	    document.inventoryOrderForm.submit();	
	}	
}
function updateOrderComment(){
    var url = "<%=request.getContextPath()%>/action/inventoryOrderJSON!updateOrderComment";
    var params=$("#inventoryOrderForm").serialize();  
    $.post(url,params, updateOrderCommentBackProcess,"json");	
}
function updateOrderCommentBackProcess(data){
	var returnValue = data.returnCode;
	if (returnValue == SUCCESS)
		alert(data.message);
	else 
		alert("错误 : " + data.message);
}

function copyOrder(){
	var info = "你确定复制此单据?";
	if (confirm(info)){	
	    var url = "<%=request.getContextPath()%>/action/inventoryOrderJSON!copyOrder";
	    var params=$("#inventoryOrderForm").serialize();  
	    $.post(url,params, copyOrderBackProcess,"json");	
	}	
}
function copyOrderBackProcess(data){
    var response = data.response;
	var returnCode = response.returnCode;
	if (returnCode != SUCCESS)
		alert("复制单据失败 ： " + response.message);
	else {
        alert("复制单据成功,单据号 " + response.returnValue);
    }
		
}
function deleteOrder(){
	$.modalDialog({
		title : '授权删除单据',
		width : 330,
		height : 180,
		modal : true,
		href : '<%=request.getContextPath()%>/jsp/headQ/inventory/ConfirmDelete.jsp',
		buttons : [ {
			text : '授权删除',
			handler : function() {
				confirmDelete(); 
			}
		} ]
		});
}
$(document).ready(function(){
	parent.$.messager.progress('close'); 
	$("#org_table tr").mouseover(function(){      
		$(this).addClass("over");}).mouseout(function(){    
		$(this).removeClass("over");}); 
});
</script>

</head>
<body>

<s:form action="" method="POST" id="inventoryOrderForm"  name="inventoryOrderForm">
<s:hidden name="formBean.order.order_ID" id="orderId"/>
 <table cellpadding="0" cellspacing="0"  style="width: 90%" align="center" class="OuterTable">
	<tr class="title">
	     <td colspan="7">
	     	     <s:if test="formBean.order.order_type == 1">
	          <font style="color:red"><s:property value="formBean.order.order_type_ws"/> </font>
	     </s:if><s:else>
	     	  <s:property value="formBean.order.order_type_ws"/>
	     </s:else>
	     -<s:property value="formBean.order.order_Status_s"/></td>
	</tr>
	<tr>	
	   <td colspan="7">
		<table cellpadding="0" cellspacing="0" border="0" style="width: 100%">
			 	<tr class="InnerTableContent" align="left">
			 		<td width="5%">&nbsp;</td>
			 		<td colspan="2">客户名字&nbsp; :&nbsp;<s:property value="formBean.order.client_name"/></td>			 					 				 					 				 					 		
			 		<td width="32%">仓库开始时间&nbsp; :&nbsp;<s:date name ="formBean.order.order_StartTime" format="yyyy-MM-dd hh:mm:ss" />  </td>
	 		        <td width="13%">订单号&nbsp; :&nbsp; <s:property value="formBean.order.order_ID"/> </td>
			 	</tr>
			 	<tr height="4">
					<td colspan="5"></td>
				</tr>
			 	<tr class="InnerTableContent" align="left">
			 		<td>&nbsp;</td>
	 		      <td width="20%">仓库录入人员&nbsp; :&nbsp; <s:property value="formBean.order.order_Keeper.name"/></td>	
			 		<td width="31%">仓库点数人员&nbsp; :&nbsp; <s:property value="formBean.order.order_Counter.name"/></td>		 					 				 		 							
			 		<td>仓库完成时间&nbsp; :&nbsp;<s:date name ="formBean.order.order_ComplTime" format="yyyy-MM-dd hh:mm:ss" /> </td>
					<td>&nbsp;</td>
			 	</tr>
				<tr height="4">
					<td colspan="5"></td>
				</tr>		
			 	<tr class="InnerTableContent" align="left">
			 		<td>&nbsp;</td>
			 		<td>单据扫描人员&nbsp; :&nbsp; <s:property value="formBean.order.order_scanner.name"/></td>	
			 		<td>精算导入人员&nbsp; :&nbsp; <s:property value="formBean.order.order_Auditor.name"/></td>		 					 				 					 			 					 		
			 		<td>单据完成时间&nbsp; :&nbsp;<s:date name ="formBean.order.order_EndTime" format="yyyy-MM-dd hh:mm:ss" /> </td>
					<td>导入次数&nbsp; :&nbsp;<s:property value="formBean.order.importTimes"/></td>
			 	</tr>
				<tr height="4">
					<td colspan="5"></td>
				</tr>
				<tr class="InnerTableContent" align="left">
			 		<td>&nbsp;</td>
			 		<td>上欠&nbsp; :&nbsp; <s:property value="formBean.order.preAcctAmt"/></td>	
			 		<td>下欠&nbsp; :&nbsp; <s:property value="formBean.order.postAcctAmt"/></td>		 					 				 					 			 					 		
			 		<td>优惠&nbsp; :&nbsp;<s:property value="formBean.order.totalDiscount"/></td>
					<td></td>
			 	</tr>
		 </table>
		 <table class="easyui-datagrid"  style="width:1000px;height:400px" data-options="singleSelect:true,border : false">			 	
			   <thead>
				 	<tr align="center" class="PBAOuterTableTitale" height="22">
				 		<th data-options="field:'1',width:40">序号</th>
				 		<th data-options="field:'2',width:90">条型码</th>	
				 		<th data-options="field:'3',width:140">产品品牌</th>			 					 		
				 		<th data-options="field:'4',width:90">产品货号</th>	
				 		<th data-options="field:'5',width:60">颜色</th>	
				 		<th data-options="field:'14',width:90">品类</th>	
				 		<th data-options="field:'6',width:50">年份</th>
				 		<th data-options="field:'7',width:40">季度</th>	 				 		
				 		<th data-options="field:'8',width:60">单位</th>	
				 		<th data-options="field:'9',width:50">数量</th>		 		
	                    <th data-options="field:'10',width:60">原批发价</th>
	                    <th data-options="field:'11',width:50">折扣</th>
	                    <th data-options="field:'12',width:80">折后批发价</th>		
				 		<th data-options="field:'13',width:80">连锁零售</th>		 		
	                </tr>
               </thead>
               <tbody>
               <s:iterator value="formBean.order.product_List" status = "st" id="order" >
				 	<tr>
				 		<td><s:property value="#st.index+1"/></td>
				 		<td><s:property value="#order.productBarcode.barcode"/></td>	
				 		<td><s:property value="#order.productBarcode.product.brand.brand_Name"/> <s:property value="#order.productBarcode.product.brand.comment"/></td>			 					 		
				 		<td><s:property value="#order.productBarcode.product.productCode"/></td>	
				 		<td><s:property value="#order.productBarcode.color.name"/></td> 	
				 		<td><s:property value="#order.productBarcode.product.category.category_Name"/></td> 
				 		<td><s:property value="#order.productBarcode.product.year.year"/></td>	
				 		<td><s:property value="#order.productBarcode.product.quarter.quarter_Name"/></td>	
				 		<td><s:property value="#order.productBarcode.product.unit"/></td>						 		
				 		<td><s:property value="#order.quantity"/>&nbsp;<s:if test="#order.moreThanTwoHan">*</s:if></td>
				 		<td><s:property value="#order.salePriceSelected"/></td>	
				 		<td><s:property value="#order.discount"/></td>	
				 		<td><s:property value="#order.wholeSalePrice"/></td>	
				 		<td><s:property value="#order.salesPrice"/></td>			 		
				 	</tr>
			 	</s:iterator>
			 	
			 	<tr align="center"  height="10" class="InnerTableContent" >
			  	     <td>总数</td>
					 <td>&nbsp;</td>			 					 		
					 <td>&nbsp;</td>
					 <td>&nbsp;</td>			 					 		
					 <td>&nbsp;</td>	
					 <td>&nbsp;</td>
					 <td>&nbsp;</td>			 					 		
					 <td>&nbsp;</td>		
					 <td>&nbsp;</td>	 					 		
					 <td><s:property value="formBean.order.totalQuantity"/></td>
					 <td>&nbsp;</td>
					 <td>&nbsp;</td>
					 <td><s:property value="formBean.order.totalWholePrice"/></td>
					 <td><s:property value="formBean.order.totalRetailPrice"/></td>
			    </tr>
				</tbody>
		 </table>
	     </td>
	  </tr>
	  <tr height="10">
	  	     <td colspan="7"></td>
	  </tr>
	  <tr height="10" class="InnerTableContent" >
	  	     <td align="center">备注</td>
			 <td colspan="4"><textarea name="formBean.order.comment" id="comment" rows="1" cols="80"><s:property value="formBean.order.comment"/></textarea></td>			 					 				 					 		
			 <td><input type="button" value="修改备注" onclick="updateOrderComment();"/></td>
			 <td>&nbsp;</td>	
	  </tr>
	  <tr height="10">
	  	     <td colspan="7"></td>
	  </tr>
	  <tr height="10">
	  	     <td>&nbsp;</td>
			 <td>
			     <!-- for user, 1. the order is not in Complete status, 2. the order status ==1 || 2 -->
				 <s:if test="#session.LOGIN_USER.containFunction('inventoryOrder!acctUpdate')  && (formBean.order.order_Status==1  || formBean.order.order_Status==2)">
				     <input type="button" value="会计修改" onclick="edit();"/>
				 </s:if>
			 </td>			 					 		
			 <td colspan="2"> 
			     <!-- for user, 1. This order is completed (status == 2) 2. They have the authority to do export -->
				 <s:if test="(#session.LOGIN_USER.containFunction('exportInventoryOrToJinSuan!exportToJinSuan') && formBean.order.order_Status==2) ">
				     <input type="button" value="导入单据到精算" onclick="exportOrderToJinSuan();"/>
				 </s:if>
				 
				 <!-- for user, 1. This order is completed (status == 2 || status==6) 2. They have the authority to do complete without import jinsuan -->
				 <s:if test="(#session.LOGIN_USER.containFunction('inventoryOrder!acctAuditOrder') && (formBean.order.order_Status==2 || formBean.order.order_Status == 6)) ">
				     <input type="button" value="完成单据审核" onclick="completeAuditOrder();"/>
				 </s:if>
				 
				 <!-- for user, 1. This order has been exported (status == 3) 2. They have the authority  -->
				 <s:if test="(#session.LOGIN_USER.containFunction('inventoryOrder!cancelOrder') && formBean.order.order_Status==3) ">
				     <input type="button" value="红冲单据" onclick="cancelOrder();"/>
				 </s:if>
			 </td>			 					 		
			 <td>
				 <s:if test="formBean.order.order_Status== 1 || formBean.order.order_Status==2 || formBean.order.order_Status==6  || formBean.order.order_Status==9">
				     <input type="button" value="删除单据" onclick="deleteOrder();"/>
				 </s:if> 
				 <s:if test="#session.LOGIN_USER.containFunction('inventoryOrderJSON!copyOrder') && formBean.order.order_Status==5">
				     <input type="button" value="复制单据" onclick="copyOrder();"/>
				 </s:if> 				 
			 </td>			 					 		
			 <td><input type="button" value="订单导出到Excel" onclick="exportOrderToExcel();"/></td>
			 <td><input type="button" value="条码标签导出" onclick="exportBarcodeToExcel();"/></td>	
	  </tr>
	  <tr height="10">
	  	     <td colspan="7"><s:actionerror cssStyle="color:red"/><s:actionmessage cssStyle="color:blue"/></td>
	  </tr>
</table>

</s:form>
</body>
</html>