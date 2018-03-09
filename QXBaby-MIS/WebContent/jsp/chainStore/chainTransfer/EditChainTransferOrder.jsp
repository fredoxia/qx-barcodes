<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib prefix="s" uri="/struts-tags" %>
<%@ page import="com.onlineMIS.ORM.entity.chainS.inventoryFlow.ChainTransferOrder" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>千禧宝贝连锁店管理信息系统</title>
<%@ include file="../../common/Style.jsp"%>
<script type="text/javascript" src="<%=request.getContextPath()%>/conf_files/js/ChainTransferOrder.js?v4.6"></script>

<script>
$(document).ready(function(){
	parent.$.messager.progress('close'); 
});
var baseurl = "<%=request.getContextPath()%>";
index = parseInt("<s:property value='formBean.transferOrder.productList.size()'/>");
function draftOrder(){

	if (validateTransferOrderForm()){
		   var params = $("#chainTransferForm").serialize();  
//alert(params);
		   $.post("chainTransferActionJSON!saveToDraft",params, draftOrderBKProcess,"json");
	}
}
function draftOrderBKProcess(data){
	if (data.returnCode != SUCCESS){
		alert(data.message);
	} else {
		var orderId = data.returnValue;
		$("#orderId").attr("value", orderId);
		displayOrder();
	}
}
function displayOrder(){
    document.chainTransferForm.action="chainTransferJSPAction!loadTransferOrder";
    document.chainTransferForm.submit();
}
function postOrder(){
	var params = $("#chainTransferForm").serialize(); 

	$.post("chainTransferActionJSON!postOrder",params, postOrderBKProcess,"json");
}

function postOrderBKProcess(data){
	if (data.returnCode != SUCCESS){
		alert(data.message);
	} else {
		$.messager.alert('成功提示', "成功提交单据", 'info');
		var orderId = data.returnValue;
		$("#orderId").attr("value", orderId);
		displayOrder();
	}
}
function deleteOrder(){
	var params = $("#chainTransferForm").serialize(); 
	
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
</script>
</head>
<body>

    <s:form action="/actionChain/inventoryFlowAction!saveToDraft" method="POST" name="chainTransferForm" id="chainTransferForm" enctype="multipart/form-data" theme="simple">
	<s:hidden name="formBean.transferOrder.id" id="orderId"/>   
	<s:hidden name="formBean.transferOrder.status"/> 
    <table width="85%" align="center"  class="OuterTable">
	    <tr><td>
	         <div class="errorAndmes"><s:actionerror cssStyle="color:red"/><s:actionmessage cssStyle="color:blue"/></div>
			 <table width="100%" border="0">
			    <tr>
			       <td height="50" colspan="7">
				   	 <table width="100%" border="0">
				       <tr class="InnerTableContent">
				         <td width="200" height="32">调出: <%@ include file="SearchFromChainStore.jsp"%></td>
				         <td width="200">调入: <%@ include file="SearchToChainStore.jsp"%></td>
				         <td width="120">创建人:<s:property value="formBean.transferOrder.creator"/></td>
				         <td width="200">日期:<s:property value="formBean.transferOrder.orderDate"/></td>
				         <td width="134">状态:<s:property value="formBean.transferOrder.statusS"/></td>
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
						  <tr class="PBAInnerTableTitale" align='left'>
						    <th width="21">&nbsp;</th>
						    <th width="100" height="35">条形码</th>
						    <th width="100">货号/名称</th>
						    <th width="40">颜色</th>
						    <th width="110">品牌</th>
						    <th width="40">年份</th>
						    <th width="40">季度</th>
						    <th width="40">单位</th>
						    <th width="45">零售单价<br/>A</th>
						    <th width="39">数量<br/>B</th>
						    <th width="39">零售额<br/>A*B</th>
						    <th width="20"></th>
						  </tr>
						  <tbody id="orderTablebody">
						      <s:iterator value="formBean.transferOrder.productList" status = "st" id="orderProduct" >
						  		<tr class="InnerTableContent" id="orderRow<s:property value="#st.index"/>">   
							      <td height="25"><s:property value="#st.index +1"/></td>
							      <td><input type="text" name="formBean.transferOrder.productList[<s:property value="#st.index"/>].productBarcode.barcode" id="barcode<s:property value="#st.index"/>" size="13" value="<s:property value="#orderProduct.productBarcode.barcode"/>" readonly/>
							          <input type="hidden" name="formBean.transferOrder.productList[<s:property value="#st.index"/>].productBarcode.id" id="productId<s:property value="#st.index"/>" size="13" value="<s:property value="#orderProduct.productBarcode.id"/>"/>
							      </td>
							      <td><input type="text" name="formBean.transferOrder.productList[<s:property value="#st.index"/>].productBarcode.product.productCode" id="productCode<s:property value="#st.index"/>" size="13" value="<s:property value="#orderProduct.productBarcode.product.productCode"/>"/></td>
							      <td><input type="text" name="formBean.transferOrder.productList[<s:property value="#st.index"/>].productBarcode.color.name" id="color<s:property value="#st.index"/>"  size="5" value="<s:property value="#orderProduct.productBarcode.color.name"/>" readonly/></td>
							      <td><input type="text" name="formBean.transferOrder.productList[<s:property value="#st.index"/>].productBarcode.product.brand.brand_Name" id="brand0<s:property value="#st.index"/>"  size="17" readonly="readonly" value="<s:property value="#orderProduct.productBarcode.product.brand.brand_Name"/>"/></td>
							      <td><input type="text" name="formBean.transferOrder.productList[<s:property value="#st.index"/>].productBarcode.product.year.year" id="year<s:property value="#st.index"/>"  size="4" readonly="readonly" value="<s:property value="#orderProduct.productBarcode.product.year.year"/>"/></td>
							      <td><input type="text" name="formBean.transferOrder.productList[<s:property value="#st.index"/>].productBarcode.product.quarter.quarter_Name" id="quarter<s:property value="#st.index"/>"  size="4" readonly="readonly" value="<s:property value="#orderProduct.productBarcode.product.quarter.quarter_Name"/>"/></td>
							      <td><input type="text" name="formBean.transferOrder.productList[<s:property value="#st.index"/>].productBarcode.product.unit" id="unit<s:property value="#st.index"/>" size="4" readonly="readonly" value="<s:property value="#orderProduct.productBarcode.product.unit"/>"/></td>
							      <td><input type="text" name="formBean.transferOrder.productList[<s:property value="#st.index"/>].productBarcode.product.salesPrice" id="salesPrice<s:property value="#st.index"/>" size="2" readonly="readonly" value="<s:property value="#orderProduct.productBarcode.product.salesPrice"/>"/></td>
							      <td><input type="text" name="formBean.transferOrder.productList[<s:property value="#st.index"/>].quantity" id="quantity<s:property value="#st.index"/>"  size="2" onchange="calculateTotal()" onkeypress="return is_number(event);" value="<s:property value="#orderProduct.quantity"/>"/></td>
							      <td><input type="text" name="formBean.transferOrder.productList[<s:property value="#st.index"/>].totalSalesPrice" id="totalSalesPrice<s:property value="#st.index"/>" size="2" readonly="readonly" value="<s:property value="#orderProduct.totalSalesPrice"/>"/></td>
							      <td><div id="delIcon<s:property value="#st.index"/>" style="display:block"><img src="<%=request.getContextPath()%>/conf_files/web-image/delete.png" border="0"  onclick="deleteRow('orderRow<s:property value="#st.index"/>');" style="cursor:pointer;"/></div>
								  </td>
							     </tr>
							   </s:iterator>  
						         <tr class="InnerTableContent" id="orderRow<s:property value='formBean.transferOrder.productList.size()'/>">   
							      <td height="25"><s:property value='formBean.transferOrder.productList.size() + 1'/></td>
							      <td><input type="text" name="formBean.transferOrder.productList[<s:property value='formBean.transferOrder.productList.size()'/>].productBarcode.barcode" id="barcode<s:property value='formBean.transferOrder.productList.size()'/>" size="13"/>
							          <input type="hidden" name="formBean.transferOrder.productList[<s:property value='formBean.transferOrder.productList.size()'/>].productBarcode.id" id="productId<s:property value='formBean.transferOrder.productList.size()'/>" size="13"/></td>
							      <td><input type="text" name="formBean.transferOrder.productList[<s:property value='formBean.transferOrder.productList.size()'/>].productBarcode.product.productCode" id="productCode<s:property value='formBean.transferOrder.productList.size()'/>" size="13"/></td>
							      <td><input type="text" name="formBean.transferOrder.productList[<s:property value='formBean.transferOrder.productList.size()'/>].productBarcode.color.name" id="color<s:property value='formBean.transferOrder.productList.size()'/>" size="5"/></td>
							      <td><input type="text" name="formBean.transferOrder.productList[<s:property value='formBean.transferOrder.productList.size()'/>].productBarcode.product.brand.brand_Name" id="brand<s:property value='formBean.transferOrder.productList.size()'/>"  size="17" readonly="readonly"/></td>
							      <td><input type="text" name="formBean.transferOrder.productList[<s:property value='formBean.transferOrder.productList.size()'/>].productBarcode.product.year.year" id="year<s:property value='formBean.transferOrder.productList.size()'/>"  size="4" readonly="readonly"/></td>
							      <td><input type="text" name="formBean.transferOrder.productList[<s:property value='formBean.transferOrder.productList.size()'/>].productBarcode.product.quarter.quarter_Name" id="quarter<s:property value='formBean.transferOrder.productList.size()'/>"  size="4" readonly="readonly"/></td>
							      <td><input type="text" name="formBean.transferOrder.productList[<s:property value='formBean.transferOrder.productList.size()'/>].productBarcode.product.unit" id="unit<s:property value='formBean.transferOrder.productList.size()'/>" size="4" readonly="readonly"/></td>
							      <td><input type="text" name="formBean.transferOrder.productList[<s:property value='formBean.transferOrder.productList.size()'/>].productBarcode.product.salesPrice" id="salesPrice<s:property value='formBean.transferOrder.productList.size()'/>" size="2" readonly="readonly"/></td>
							      <td><input type="text" name="formBean.transferOrder.productList[<s:property value='formBean.transferOrder.productList.size()'/>].quantity" id="quantity<s:property value='formBean.transferOrder.productList.size()'/>"  size="2" onchange="calculateTotal()" onkeypress="return is_number(event);"/></td>
							      <td><input type="text" name="formBean.transferOrder.productList[<s:property value='formBean.transferOrder.productList.size()'/>].totalSalesPrice" id="totalSalesPrice<s:property value='formBean.transferOrder.productList.size()'/>" size="2" readonly="readonly"/></td>
							      <td><div id="delIcon<s:property value='formBean.transferOrder.productList.size()'/>" style="display:none"><img src="<%=request.getContextPath()%>/conf_files/web-image/delete.png" border="0"  onclick="deleteRow('orderRow<s:property value='formBean.transferOrder.productList.size()'/>');" style="cursor:pointer;"/></div>
								  </td>
							     </tr>  
						  </tbody>
						  <tr class="PBAInnerTableTitale">
						    <th height="25" colspan="9" align='left'>合计</th>
						    <th align='left'><s:textfield name="formBean.transferOrder.totalQuantity" id="totalQuantity" readonly="true" size="2"/></th>
						    <th align='left'><s:textfield name="formBean.transferOrder.totalSalesPrice" id="totalSalesPriceOrder" readonly="true" size="2"/></th>
						    <th></th>
						  </tr>
					     </table>
			      </td>
			    </tr>
			    <tr class="InnerTableContent">
			      <td height="30" colspan="7">
			            <!-- table to show the footer of the chain inventory flow -->
						<table width="100%" align="left" border="0">
						   <tr class="InnerTableContent">
					         <td height="40">运费</td>
					         <td colspan="2"><s:textfield name="formBean.transferOrder.transportationFee" id="transportationFee" cssClass="easyui-numberspinner"  data-options=" increment:1,min:0,max:50"/></td>
				           </tr>
						   <tr class="InnerTableContent">
					         <td height="40">备注</td>
					         <td colspan="2"><textarea name="formBean.transferOrder.userComment" id="userComment" rows="2" cols="50"><s:property value="formBean.transferOrder.userComment"/></textarea></td>
				           </tr>

						   <tr class="InnerTableContent">
						    <td width="3%" height="25" align='left'>&nbsp;</td>
						    <td width="3%" align='left'>&nbsp;</td>
						    <td width="90%" align='left'>
						      <s:if test="#session.LOGIN_CHAIN_USER.containFunction('chainTransferActionJSON!saveToDraft') && formBean.canSaveDraft">
							      <input type="button" value="保存草稿" onclick="draftOrder();"/>
							  </s:if>
						      <s:if test="#session.LOGIN_CHAIN_USER.containFunction('chainTransferActionJSON!postOrder') && formBean.canPost">
							      <input type="button" value="提交单据" onclick="postOrder();"/>
						      </s:if>
						      <s:if test="#session.LOGIN_CHAIN_USER.containFunction('chainTransferActionJSON!deleteOrder') && formBean.canDelete">
							       <input type="button" value="删除单据" onclick="deleteOrder();"/>	
							  </s:if>
				
						    </td>
					      </tr>
					    </table>
				  </td>
			    </tr>
			    <tr class="InnerTableContent">
			      <td height="4" colspan="7"><hr width="100%" color="#FFCC00"/></td>
			    </tr>
			  </table>
	   </td></tr>
	 </table>
	 </s:form>
 
</body>
</html>