<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib prefix="s" uri="/struts-tags" %>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>千禧宝贝连锁店管理信息系统</title>
<%@ include file="../../common/Style.jsp"%>

<script>
$(document).ready(function(){
	parent.$.messager.progress('close'); 
	$("#org_table tr").mouseover(function(){      
		$(this).addClass("over");}).mouseout(function(){    
		$(this).removeClass("over");}); 
});
var baseurl = "<%=request.getContextPath()%>";

function editOrder(){
    document.chainTransferOrder.action="chainTransferJSPAction!editOrder";
    document.chainTransferOrder.submit();
}


function deleteOrder(){
	var params = $("#chainTransferOrder").serialize(); 
	
	$.post("chainTransferActionJSON!deleteOrder",params, deleteOrderBKProcess,"json");
}

function deleteOrderBKProcess(data){
	if (data.returnCode != SUCCESS){
		alert(data.message);
	} else {
		$.messager.alert('成功提示', "成功删除单据", 'info');
		window.location.href = "chainTransferJSPAction!createTransferOrder";
	}
}

function rejectOrder(){
	var params = $("#chainTransferOrder").serialize(); 
	
	$.post("chainTransferActionJSON!rejectOrder",params, rejectOrderBKProcess,"json");
}

function rejectOrderBKProcess(data){
	if (data.returnCode != SUCCESS){
		alert(data.message);
	} else {
		$.messager.alert('成功提示', "成功退回单据", 'info');
		var orderId = data.returnValue;
		$("#orderId").attr("value", orderId);
		displayOrder();
	}
}
function displayOrder(){
    document.chainTransferOrder.action="chainTransferJSPAction!loadTransferOrder";
    document.chainTransferOrder.submit();
}
function confirmOrder(){
	var params = $("#chainTransferOrder").serialize(); 
	
	$.post("chainTransferActionJSON!confirmOrder",params, confirmOrderBKProcess,"json");
}

function confirmOrderBKProcess(data){
	if (data.returnCode != SUCCESS){
		alert(data.message);
	} else {
		$.messager.alert('成功提示', "成功确认单据", 'info');
		var orderId = data.returnValue;
		$("#orderId").attr("value", orderId);
		displayOrder();
	}
}
</script>
</head>
<body>

    <s:form action="/actionChain/inventoryFlowAction!editOrder" method="POST" name="chainTransferOrder" id="chainTransferOrder" theme="simple">
	<s:hidden name="formBean.transferOrder.id" id="orderId"/>   
    <table width="82%" align="center"  class="OuterTable">
	    <tr><td>
	         <div class="errorAndmes"><s:actionerror cssStyle="color:red"/><s:actionmessage cssStyle="color:blue"/></div>
			 <table width="100%" border="0">
			    <tr>
			       <td height="50" colspan="7">
				   	 <table width="100%" border="0">
				       <tr class="InnerTableContent">
				         <td width="234" height="32">调出连锁店 : <s:property value="uiBean.order.fromChainStore.chain_name"/></td>
				         <td width="234">调入连锁店 : <s:property value="uiBean.order.toChainStore.chain_name"/></td>
				         <td width="120">创建人 : <s:property value="uiBean.order.creator"/></td>
				         <td width="250">创建日期 : <s:property value="uiBean.order.orderDate"/></td>
				         <td width="250">状态 : <s:property value="uiBean.order.statusS"/>
				         </td>
				       </tr>
				       <tr class="InnerTableContent">
				       	 <td>运费: <s:property value="uiBean.order.transportationFee"/></td>
				         <td height="32" colspan="4">备注 : <s:property value="uiBean.order.userComment"/></td>
				       </tr> 
				     </table></td>
			    </tr>
			    <tr class="InnerTableContent">
			      <td colspan="7"><hr width="100%" color="#FFCC00"/></td>
			    </tr>
			    <tr>
			      <td colspan="7">
			            <!-- table to display the product information -->
						<table width="100%"  align="left" class="OuterTable" id="org_table">
							  <tr class="PBAInnerTableTitale">
						    <th width="3%" height="35">&nbsp;</th>
						    <th width="5%">年份</th>
						    <th width="5%">季度</th>
						    <th width="12%">品牌</th>
						    <th width="10%">货号</th>
						    <th width="5%">颜色</th>
						    <th width="12%">条形码</th>
						    <th width="10%">单位</th>
						    <th width="8%">数量</th>
						    <th width="8%">零售单价</th>
						    <th width="8%">零售额</th>
						    <th width="8%">批发单价</th>
						    <th width="8%">批发额</th>						    
						  </tr>
						  <tbody id="orderTablebody">
						      <s:iterator value="uiBean.order.productList" status = "st" id="orderProduct" >
						  		<tr class="InnerTableContent" id="orderRow0" class="InnerTableContent" <s:if test="#st.odd">style='background-color: rgb(255, 250, 208);'</s:if> align='center'>   
							      <td height="25"><s:property value="#st.index +1"/></td>							      
							      <td><s:property value="#orderProduct.productBarcode.product.year.year"/></td>
							      <td><s:property value="#orderProduct.productBarcode.product.quarter.quarter_Name"/></td>
							      <td><s:property value="#orderProduct.productBarcode.product.brand.brand_Name"/></td>
							      <td><s:property value="#orderProduct.productBarcode.product.productCode"/></td>
							      <td><s:property value="#orderProduct.productBarcode.color.name"/></td>
							      <td><s:property value="#orderProduct.productBarcode.barcode"/></td>
							      <td><s:property value="#orderProduct.productBarcode.product.unit"/></td>
							      <td><s:property value="#orderProduct.quantity"/></td>
							      <td><s:property value="#orderProduct.salesPrice"/></td>
							      <td><s:property value="#orderProduct.totalSalesPrice"/></td>
							      <td><s:property value="#orderProduct.wholeSalesPrice"/></td>
							      <td><s:property value="#orderProduct.totalWholeSalesPrice"/></td>
							     </tr>
							   </s:iterator>  
						  </tbody>
						  <tr class="PBAInnerTableTitale">
						    <th height="25" colspan="8" align='left'>合计</th>
						    <th align='center'><s:property value="uiBean.order.totalQuantity"/></th>
						    <th align='center'>&nbsp;</th>
						    <th align='center'><s:property value="uiBean.order.totalSalesPrice"/></th>
						    <th align='center'>&nbsp;</th>
						    <th align='center'><s:property value="uiBean.order.totalWholeSalesPrice"/></th>
						  </tr>
					     </table>
			      </td>
			    </tr>
			    <tr class="InnerTableContent">
			      <td height="30" colspan="7">
			            <!-- table to show the footer of the chain inventory flow -->
						<table width="100%" align="left" border="0">

						  <tr class="InnerTableContent">
						    <td width="5%" height="25" align='left'>&nbsp;</td>
						    <td width="3%" align='left'>&nbsp;</td>
						    <td width="90%" align='left'>
						      <s:if test="#session.LOGIN_CHAIN_USER.containFunction('chainTransferJSPAction!editOrder') && formBean.canEdit">
						            <input type="button" value="修改单据" onclick="editOrder();"/>	
						      </s:if>	
						      <s:if test="#session.LOGIN_CHAIN_USER.containFunction('chainTransferActionJSON!deleteOrder') && formBean.canDelete">
						            <input type="button" value="删除单据" onclick="deleteOrder();"/>	
						      </s:if>
						      <s:if test="#session.LOGIN_CHAIN_USER.containFunction('chainTransferActionJSON!rejectOrder') && formBean.canReject">
						      		<input type="button" value="退回单据" onclick="rejectOrder();"/>	
						      </s:if>					      						      
						      <s:if test="#session.LOGIN_CHAIN_USER.containFunction('chainTransferActionJSON!confirmOrder') && formBean.canConfirm">
						            <input type="button" value="确认单据" onclick="confirmOrder();"/>	
						      </s:if>
				      
						    </td>
					      </tr>
					    </table>
				  </td>
			    </tr>
			    <tr class="InnerTableContent">
			      <td height="4" colspan="7"><hr width="100%" color="#FFCC00"/></td>
			    </tr>
			    <tr class="InnerTableContent">
			      <td height="30" colspan="7">
			            <!-- table to show the logs -->
			        	
					<table width="40%" class="OuterTable" id="org_table">
						  <tr class="InnerTableContent">
						    <th width="25%" align="left">单据更改日志</th>
						    <th width="25%"></th>			    
						  </tr>
						  <tbody id="orderTablebody">
						      <s:iterator value="uiBean.orderLogs" status = "st" id="orderLog" >
						  		<tr class="InnerTableContent" <s:if test="#st.odd">style='background-color: rgb(255, 250, 208);'</s:if>>   						      
							      <td><s:property value="#orderLog.logTime"/></td>
							      <td><s:property value="#orderLog.log"/></td>
							     </tr>
							   </s:iterator>  
						  </tbody>
						  </table>						
				  </td>
			    </tr>			    
			  </table>
	   </td></tr>
	 </table>
	 </s:form>
</body>
</html>