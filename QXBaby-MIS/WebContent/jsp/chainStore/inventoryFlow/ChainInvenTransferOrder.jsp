<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib prefix="s" uri="/struts-tags" %>
<%@ page import="com.onlineMIS.ORM.entity.chainS.inventoryFlow.ChainInventoryFlowOrder" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>千禧宝贝连锁店管理信息系统</title>
<%@ include file="../../common/Style.jsp"%>
<script type="text/javascript" src="<%=request.getContextPath()%>/conf_files/js/ChainInvenFlow.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/conf_files/js/ChainInvenTransfer.js?v12.3"></script>

<script>
$(document).ready(function(){
	parent.$.messager.progress('close'); 
});
var baseurl = "<%=request.getContextPath()%>";
index = parseInt("<s:property value='formBean.flowOrder.productList.size()'/>");

</script>
</head>
<body>

    <s:form action="/actionChain/inventoryFlowAction!saveToDraft" method="POST" name="chainInventoryFlowForm" id="chainInventoryFlowForm" enctype="multipart/form-data" theme="simple">
	<s:hidden name="formBean.flowOrder.type"/>  
	<s:hidden name="formBean.flowOrder.id"/>   
	<s:hidden name="formBean.flowOrder.status"/> 
    <table width="78%" align="center"  class="OuterTable">
	    <tr><td>
	         <div class="errorAndmes"><s:actionerror cssStyle="color:red"/><s:actionmessage cssStyle="color:blue"/></div>
			 <table width="100%" border="0">
			    <tr>
			       <td height="50" colspan="7">
				   	 <table width="100%" border="0">
				       <tr class="PBAOuterTableTitale">
				         <td height="32" colspan="5">
				              	<s:property value="formBean.flowOrder.typeChainS"/><p/>
				         </td>
			           </tr>
				       <tr class="InnerTableContent">
				         <td width="200" height="32">调出:<s:select id="chainStore" name="formBean.flowOrder.fromChainStore.chain_id"  list="uiBean.chainStores" listKey="chain_id" listValue="chain_name" onchange="changeFromChain();"/></td>
				         <td width="200">调入:<s:select id="toChainStore" name="formBean.flowOrder.toChainStore.chain_id"  list="uiBean.toChainStores" listKey="chain_id" listValue="chain_name" onchange="validateTransferOrderForm();"/></td>
				         <td width="120">创建人:<s:property value="formBean.flowOrder.creator.name"/></td>
				         <td width="200">日期:<s:property value="formBean.flowOrder.orderDate"/></td>
				         <td width="134">状态:<s:property value="formBean.flowOrder.statusS"/></td>
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
						    <th width="39">数量</th>
						    <th width="39">库存</th>
						    <th width="20"></th>
						  </tr>
						  <tbody id="orderTablebody">
						      <s:iterator value="formBean.flowOrder.productList" status = "st" id="orderProduct" >
						  		<tr class="InnerTableContent" id="orderRow<s:property value="#st.index"/>">   
							      <td height="25"><s:property value="#st.index +1"/></td>
							      <td><input type="text" name="formBean.flowOrder.productList[<s:property value="#st.index"/>].productBarcode.barcode" id="barcode<s:property value="#st.index"/>" size="13" value="<s:property value="#orderProduct.productBarcode.barcode"/>" readonly/>
							          <input type="hidden" name="formBean.flowOrder.productList[<s:property value="#st.index"/>].productBarcode.id" id="productId<s:property value="#st.index"/>" size="13" value="<s:property value="#orderProduct.productBarcode.id"/>"/>
							      	  <input type="hidden" name="formBean.flowOrder.productList[<s:property value="#st.index"/>].id" size="13" value="<s:property value="#orderProduct.id"/>"/>
							      </td>
							      <td><input type="text" name="formBean.flowOrder.productList[<s:property value="#st.index"/>].productBarcode.product.productCode" id="productCode<s:property value="#st.index"/>" size="13" value="<s:property value="#orderProduct.productBarcode.product.productCode"/>"/></td>
							      <td><input type="text" name="formBean.flowOrder.productList[<s:property value="#st.index"/>].productBarcode.color.name" id="color<s:property value="#st.index"/>"  size="5" value="<s:property value="#orderProduct.productBarcode.color.name"/>" readonly/></td>
							      <td><input type="text" name="formBean.flowOrder.productList[<s:property value="#st.index"/>].productBarcode.product.brand.brand_Name" id="brand0<s:property value="#st.index"/>"  size="17" readonly="readonly" value="<s:property value="#orderProduct.productBarcode.product.brand.brand_Name"/>"/></td>
							      <td><input type="text" name="formBean.flowOrder.productList[<s:property value="#st.index"/>].productBarcode.product.year.year" id="year<s:property value="#st.index"/>"  size="4" readonly="readonly" value="<s:property value="#orderProduct.productBarcode.product.year.year"/>"/></td>
							      <td><input type="text" name="formBean.flowOrder.productList[<s:property value="#st.index"/>].productBarcode.product.quarter.quarter_Name" id="quarter<s:property value="#st.index"/>"  size="4" readonly="readonly" value="<s:property value="#orderProduct.productBarcode.product.quarter.quarter_Name"/>"/></td>
							      <td><input type="text" name="formBean.flowOrder.productList[<s:property value="#st.index"/>].productBarcode.product.unit" id="unit<s:property value="#st.index"/>" size="4" readonly="readonly" value="<s:property value="#orderProduct.productBarcode.product.unit"/>"/></td>
							      <td><input type="text" name="formBean.flowOrder.productList[<s:property value="#st.index"/>].quantity" id="quantity<s:property value="#st.index"/>"  size="2" onchange="calculateTotal()" onkeypress="return is_number(event);" value="<s:property value="#orderProduct.quantity"/>"/></td>
							      <td><input type="text" name="formBean.flowOrder.productList[<s:property value="#st.index"/>].inventoryQ" id="inventoryQ<s:property value="#st.index"/>" readonly size="2" onchange="calculateTotal()" onkeypress="return is_number(event);" value="<s:property value="#orderProduct.inventoryQ"/>"/></td>
							      <td><div id="delIcon<s:property value="#st.index"/>" style="display:block"><img src="<%=request.getContextPath()%>/conf_files/web-image/delete.png" border="0"  onclick="deleteRow('orderRow<s:property value="#st.index"/>');" style="cursor:pointer;"/></div>
								  </td>
							     </tr>
							   </s:iterator>  
						         <tr class="InnerTableContent" id="orderRow<s:property value='formBean.flowOrder.productList.size()'/>">   
							      <td height="25"><s:property value='formBean.flowOrder.productList.size() + 1'/></td>
							      <td><input type="text" name="formBean.flowOrder.productList[<s:property value='formBean.flowOrder.productList.size()'/>].productBarcode.barcode" id="barcode<s:property value='formBean.flowOrder.productList.size()'/>" size="13"/>
							          <input type="hidden" name="formBean.flowOrder.productList[<s:property value='formBean.flowOrder.productList.size()'/>].productBarcode.id" id="productId<s:property value='formBean.flowOrder.productList.size()'/>" size="13"/></td>
							      <td><input type="text" name="formBean.flowOrder.productList[<s:property value='formBean.flowOrder.productList.size()'/>].productBarcode.product.productCode" id="productCode<s:property value='formBean.flowOrder.productList.size()'/>" size="13"/></td>
							      <td><input type="text" name="formBean.flowOrder.productList[<s:property value='formBean.flowOrder.productList.size()'/>].productBarcode.color.name" id="color<s:property value='formBean.flowOrder.productList.size()'/>" size="5"/></td>
							      <td><input type="text" name="formBean.flowOrder.productList[<s:property value='formBean.flowOrder.productList.size()'/>].productBarcode.product.brand.brand_Name" id="brand<s:property value='formBean.flowOrder.productList.size()'/>"  size="17" readonly="readonly"/></td>
							      <td><input type="text" name="formBean.flowOrder.productList[<s:property value='formBean.flowOrder.productList.size()'/>].productBarcode.product.year.year" id="year<s:property value='formBean.flowOrder.productList.size()'/>"  size="4" readonly="readonly"/></td>
							      <td><input type="text" name="formBean.flowOrder.productList[<s:property value='formBean.flowOrder.productList.size()'/>].productBarcode.product.quarter.quarter_Name" id="quarter<s:property value='formBean.flowOrder.productList.size()'/>"  size="4" readonly="readonly"/></td>
							      <td><input type="text" name="formBean.flowOrder.productList[<s:property value='formBean.flowOrder.productList.size()'/>].productBarcode.product.unit" id="unit<s:property value='formBean.flowOrder.productList.size()'/>" size="4" readonly="readonly"/></td>
							      <td><input type="text" name="formBean.flowOrder.productList[<s:property value='formBean.flowOrder.productList.size()'/>].quantity" id="quantity<s:property value='formBean.flowOrder.productList.size()'/>"  size="2" onchange="calculateTotal()" onkeypress="return is_number(event);"/></td>
							      <td><input type="text" name="formBean.flowOrder.productList[<s:property value='formBean.flowOrder.productList.size()'/>].inventoryQ" id="inventoryQ<s:property value='formBean.flowOrder.productList.size()'/>" readonly size="2" onchange="calculateTotal()" onkeypress="return is_number(event);"/></td>
							      <td><div id="delIcon<s:property value='formBean.flowOrder.productList.size()'/>" style="display:none"><img src="<%=request.getContextPath()%>/conf_files/web-image/delete.png" border="0"  onclick="deleteRow('orderRow<s:property value='formBean.flowOrder.productList.size()'/>');" style="cursor:pointer;"/></div>
								  </td>
							     </tr>  
						  </tbody>
						  <tr class="PBAInnerTableTitale">
						    <th height="25" colspan="8" align='left'>合计</th>
						    <th align='left'><s:textfield name="formBean.flowOrder.totalQuantity" id="totalQuantity" readonly="true" size="2"/></th>
						    <th align='left'><s:textfield name="formBean.flowOrder.totalInventoryQ" id="totalInventoryQ" readonly="true" size="2"/></th>
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
					         <td height="40">备注</td>
					         <td colspan="2"><textarea name="formBean.flowOrder.comment" id="comment" rows="2" cols="50"><s:property value="formBean.flowOrder.comment"/></textarea></td>
				           </tr>
				           <tr class="InnerTableContent">
					         <td height="40">盘点文件</td>
					         <td colspan="2"><input type="file" name="formBean.inventory" id="inventory"/></td>
				           </tr>
						   <tr class="InnerTableContent">
						    <td width="3%" height="25" align='left'>&nbsp;</td>
						    <td width="3%" align='left'>&nbsp;</td>
						    <td width="90%" align='left'>
						      <s:if test="#session.LOGIN_CHAIN_USER.containFunction('inventoryFlowJSPAction!saveToDraft') && formBean.canSaveDraft">
						           <input type="button" value="存入草稿" onclick="saveDraft();" />
						      </s:if>
						      <s:if test="#session.LOGIN_CHAIN_USER.containFunction('inventoryFlowJSPAction!saveToFinal') && formBean.canPost">
						           <input type="button" value="单据过账" onclick="saveFinal();"/>	
						      </s:if>
						      <s:if test="#session.LOGIN_CHAIN_USER.containFunction('inventoryFlowJSPAction!deleteOrder') && formBean.canDelete">
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