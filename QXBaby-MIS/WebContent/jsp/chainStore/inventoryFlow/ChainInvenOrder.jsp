<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib prefix="s" uri="/struts-tags" %>
<%@ page import="com.onlineMIS.ORM.entity.chainS.inventoryFlow.ChainInventoryFlowOrder" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>千禧宝贝连锁店管理信息系统</title>
<%@ include file="../../common/Style.jsp"%>
<script type="text/javascript" src="<%=request.getContextPath()%>/conf_files/js/ChainInvenShare.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/conf_files/js/ChainInvenOrder.js?v=7.26"></script>

<script>
$(document).ready(function(){
	parent.$.messager.progress('close'); 
});
var baseurl = "<%=request.getContextPath()%>";
index = parseInt("<s:property value='formBean.flowOrder.productList.size()'/>");
</script>
</head>
<body>
	<%@ include file="../../common/loadingDiv.jsp"%>  
    <s:form action="/actionChain/inventoryFlowAction!saveToDraft" method="POST" name="chainInventoryFlowForm" id="chainInventoryFlowForm" enctype="multipart/form-data" theme="simple">
	<s:hidden name="formBean.flowOrder.type"/>  
	<s:hidden name="formBean.flowOrder.id"/>   
	<s:hidden name="formBean.flowOrder.status"/>   
    <table width="82%" align="center"  class="OuterTable">
	    <tr><td>
	         <div class="errorAndmes"><s:actionerror cssStyle="color:red"/><s:actionmessage cssStyle="color:blue"/></div>
			 <table width="100%" border="0">
			    <tr>
			       <td height="50" colspan="7">
				   	 <table width="100%" border="0">
				       <tr class="PBAOuterTableTitale">
				         <td height="50" colspan="5">
				              	<s:property value="formBean.flowOrder.typeChainS"/><br/><br/>
				              	- 用户扫描仓库库存货品获取目前实际库存
				         </td>
			           </tr>
				       <tr class="InnerTableContent">
				         <td width="234" height="32">连锁店:<s:select id="chainStore" name="formBean.flowOrder.chainStore.chain_id"  list="uiBean.chainStores" listKey="chain_id" listValue="chain_name"/></td>
				         <td width="160">创建人:<s:property value="formBean.flowOrder.creator.name"/></td>
				         <td width="300">日期:<s:property value="formBean.flowOrder.orderDate"/></td>
				         <td width="200">状态:<s:property value="formBean.flowOrder.statusS"/></td>
				         <td width="200"></td>
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
						    <th width="93" height="35">条形码</th>
						    <th width="93">货号/名称</th>
						    <th width="40">颜色</th>
						    <th width="102">品牌</th>
						    <th width="40">年份</th>
						    <th width="40">季度</th>
						    <th width="40">单位</th>
						    <th width="52">实际数量</th>
						    <th width="52">电脑库存</th>
						    <th width="30">差额</th>
						    <th width="20"></th>
						  </tr>
						  <tbody id="orderTablebody">
						      <s:iterator value="formBean.flowOrder.productList" status = "st" id="orderProduct" >
						  		<tr class="InnerTableContent" id="orderRow<s:property value="#st.index"/>">   
							      <td height="25"><s:property value="#st.index +1"/></td>
							      <td><input type="text" name="formBean.flowOrder.productList[<s:property value="#st.index"/>].productBarcode.barcode" id="barcode<s:property value="#st.index"/>" size="13" value="<s:property value="#orderProduct.productBarcode.barcode"/>"/>
							          <input type="hidden" name="formBean.flowOrder.productList[<s:property value="#st.index"/>].id" size="13" value="<s:property value="#orderProduct.id"/>"/>
									  <input type="hidden" name="formBean.flowOrder.productList[<s:property value="#st.index"/>].productBarcode.Id" size="13" value="<s:property value="#orderProduct.productBarcode.Id"/>"/>
							      </td>
							      <td><input type="text" name="formBean.flowOrder.productList[<s:property value="#st.index"/>].productBarcode.product.productCode" id="productCode<s:property value="#st.index"/>" size="13" value="<s:property value="#orderProduct.productBarcode.product.productCode"/>"/></td>
							       <td><input type="text" name="formBean.flowOrder.productList[<s:property value="#st.index"/>].productBarcode.color.name" id="color<s:property value="#st.index"/>" size="5" value="<s:property value="#orderProduct.productBarcode.color.name"/>"/></td>
							      <td><input type="text" name="formBean.flowOrder.productList[<s:property value="#st.index"/>].productBarcode.product.brand.brand_Name" id="brand<s:property value="#st.index"/>"  size="17" readonly value="<s:property value="#orderProduct.productBarcode.product.brand.brand_Name"/>"/></td>
							      <td><input type="text" name="formBean.flowOrder.productList[<s:property value="#st.index"/>].productBarcode.product.year.year" id="year<s:property value="#st.index"/>"  size="4" readonly value="<s:property value="#orderProduct.productBarcode.product.year.year"/>"/></td>
							      <td><input type="text" name="formBean.flowOrder.productList[<s:property value="#st.index"/>].productBarcode.product.quarter.quarter_Name" id="quarter<s:property value="#st.index"/>"  size="4" readonly value="<s:property value="#orderProduct.productBarcode.product.quarter.quarter_Name"/>"/></td>
							      <td><input type="text" name="formBean.flowOrder.productList[<s:property value="#st.index"/>].productBarcode.product.unit" id="unit<s:property value="#st.index"/>" size="4" readonly value="<s:property value="#orderProduct.productBarcode.product.unit"/>"/></td>
							      <td><input type="text" name="formBean.flowOrder.productList[<s:property value="#st.index"/>].quantity" id="quantity<s:property value="#st.index"/>"  size="2" onchange="calculateTotal()" onkeypress="return is_number(event);" value="<s:property value="#orderProduct.quantity"/>"/></td>
							      <td><input type="text" name="formBean.flowOrder.productList[<s:property value="#st.index"/>].inventoryQ" id="inventoryQ<s:property value="#st.index"/>"  size="2" readonly onchange="calculateTotal()" onkeypress="return is_number(event);" value="<s:property value="#orderProduct.inventoryQ"/>"/></td>
							      <td><input type="text" name="formBean.flowOrder.productList[<s:property value="#st.index"/>].quantityDiff" id="quantityDiff<s:property value="#st.index"/>"  size="2" readonly value="<s:property value="#orderProduct.quantityDiff"/>"/></td>
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
							      <td><input type="text" name="formBean.flowOrder.productList[<s:property value='formBean.flowOrder.productList.size()'/>].productBarcode.product.brand.brand_Name" id="brand<s:property value='formBean.flowOrder.productList.size()'/>"  size="17" readonly/></td>
							      <td><input type="text" name="formBean.flowOrder.productList[<s:property value='formBean.flowOrder.productList.size()'/>].productBarcode.product.year.year" id="year<s:property value='formBean.flowOrder.productList.size()'/>"  size="4" readonly/></td>
							      <td><input type="text" name="formBean.flowOrder.productList[<s:property value='formBean.flowOrder.productList.size()'/>].productBarcode.product.quarter.quarter_Name" id="quarter<s:property value='formBean.flowOrder.productList.size()'/>"  size="4" readonly/></td>
							      <td><input type="text" name="formBean.flowOrder.productList[<s:property value='formBean.flowOrder.productList.size()'/>].productBarcode.product.unit" id="unit<s:property value='formBean.flowOrder.productList.size()'/>" size="4" readonly/></td>
							      <td><input type="text" name="formBean.flowOrder.productList[<s:property value='formBean.flowOrder.productList.size()'/>].quantity" id="quantity<s:property value='formBean.flowOrder.productList.size()'/>"  size="2" onchange="calculateTotal()" onkeypress="return is_number(event);"/></td>
							      <td><input type="text" name="formBean.flowOrder.productList[<s:property value='formBean.flowOrder.productList.size()'/>].inventoryQ" id="inventoryQ<s:property value='formBean.flowOrder.productList.size()'/>"  size="2" onchange="calculateTotal()" onkeypress="return is_number(event);"/></td>
							      <td><input type="text" name="formBean.flowOrder.productList[<s:property value='formBean.flowOrder.productList.size()'/>].quantityDiff" id="quantityDiff<s:property value='formBean.flowOrder.productList.size()'/>" readonly  size="2" /></td>
								  <td><div id="delIcon<s:property value='formBean.flowOrder.productList.size()'/>" style="display:none"><img src="<%=request.getContextPath()%>/conf_files/web-image/delete.png" border="0"  onclick="deleteRow('orderRow<s:property value='formBean.flowOrder.productList.size()'/>');" style="cursor:pointer;"/></div>
								  </td>
							     </tr>  
						  </tbody>
						  <tr class="PBAInnerTableTitale">
						    <th height="25" colspan="8" align='left'>合计</th>
						    <th align='left'><s:textfield name="formBean.flowOrder.totalQuantity" id="totalQuantity" readonly="true" size="2"/></th>
						    <th align='left'><s:textfield name="formBean.flowOrder.totalInventoryQ" id="totalInventoryQ" readonly="true" size="2"/></th>
						    <th align='left'><s:textfield name="formBean.flowOrder.totalQuantityDiff" id="totalQuantityDiff" readonly="true" size="2"/></th>
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
					         <td colspan="2"><s:textfield name="formBean.flowOrder.comment" size="50"/></td>
				           </tr>
				           <tr class="InnerTableContent">
					         <td height="40">盘点文件</td>
					         <td colspan="2"><input type="file" name="formBean.inventory" id="inventory"/></td>
				           </tr>
						  <tr class="InnerTableContent">
						    <td width="3%" height="25" align='left'>&nbsp;</td>
						    <td width="3%" align='left'>&nbsp;</td>
						    <td width="90%" align='left'>
						      <input type="button" value="存入草稿" onclick="saveDraft();" />
						      <input type="button" value="刷新电脑库存" onclick="calInventoryOrder();"/>	
						      <s:if test="formBean.flowOrder.id !=0">
						            <input type="button" value="删除单据" onclick="deleteOrder();"/>	
						      </s:if>

						      <input type="button" value="一键纠正库存" onclick="oneKeyToAdjustInventory();" title="此功能将根据此库存单,自动添加报损单和报溢单达到纠正库存目的"/>	
						      <input type="button" value="库存扎帐" onclick="oneKeyToClearAndAdjustInventory();" title="此功能将会使用此库存单据,清空原始库存再根据此单自动添加报损单和报溢单达到纠正库存目的"/>	
						      <input type="button" value="生成最终报表" onclick="genInventoryReport();"/>		
						      <input type="button" value="下载条码" onclick="getBarcodeFile();"/>						
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