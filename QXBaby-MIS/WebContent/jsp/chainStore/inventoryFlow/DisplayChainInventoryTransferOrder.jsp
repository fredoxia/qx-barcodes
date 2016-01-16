<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib prefix="s" uri="/struts-tags" %>
<%@ page import="com.onlineMIS.ORM.entity.chainS.inventoryFlow.ChainInventoryFlowOrder,com.onlineMIS.ORM.entity.chainS.user.ChainRoleType" %>
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
function downloadOrder(){
	
    document.chainInventoryFlowForm.action="inventoryFlowJSPAction!downloadFlowOrder";
    document.chainInventoryFlowForm.submit();
}


</script>
</head>
<body>

    <s:form action="/actionChain/inventoryFlowAction!editOrder" method="POST" name="chainInventoryFlowForm" id="chainInventoryFlowForm" theme="simple">
	<s:hidden name="formBean.flowOrder.id"/>   
    <table width="82%" align="center"  class="OuterTable">
	    <tr><td>
	         <div class="errorAndmes"><s:actionerror cssStyle="color:red"/><s:actionmessage cssStyle="color:blue"/></div>
			 <table width="100%" border="0">
			    <tr>
			       <td height="50" colspan="7">
				   	 <table width="100%" border="0">
				       <tr class="PBAOuterTableTitale">
				         <td height="32" colspan="5">
				              	<s:property value="uiBean.flowOrder.typeChainS"/>
				         </td>
			           </tr>
				       <tr class="InnerTableContent">
				         <td width="200" height="32">调出:<s:property value="uiBean.flowOrder.fromChainStore.chain_name"/></td>
				         <td width="200">调入:<s:property value="uiBean.flowOrder.toChainStore.chain_name"/></td>
				         <td width="120">创建人:<s:property value="uiBean.flowOrder.creator.name"/></td>
				         <td width="200">日期:<s:property value="uiBean.flowOrder.orderDate"/></td>
				         <td width="134">状态:
				         	 <s:if test="uiBean.flowOrder.status == 3">
				              		 <font style="color:red"><s:property value="uiBean.flowOrder.statusS"/></font>
				              </s:if><s:else>
				                     <s:property value="uiBean.flowOrder.statusS"/>
				              </s:else>
				         </td>
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
						    <th width="10%">颜色</th>
						    <th width="12%">条形码</th>
						    <th width="10%">单位</th>
						    <th width="10%">数量</th>
						    <th width="15%">摘要</th>
						  </tr>
						  <tbody id="orderTablebody">
						      <s:iterator value="uiBean.flowOrder.productList" status = "st" id="orderProduct" >
						  		<tr class="InnerTableContent" align='center' id="orderRow0" class="InnerTableContent" <s:if test="#st.odd">style='background-color: rgb(255, 250, 208);'</s:if>>   
							      <td height="25"><s:property value="#st.index +1"/></td>							      
							      <td><s:property value="#orderProduct.productBarcode.product.year.year"/></td>
							      <td><s:property value="#orderProduct.productBarcode.product.quarter.quarter_Name"/></td>
							      <td><s:property value="#orderProduct.productBarcode.product.brand.brand_Name"/></td>
							      <td><s:property value="#orderProduct.productBarcode.product.productCode"/></td>
							      <td><s:property value="#orderProduct.productBarcode.color.name"/></td>
							      <td><s:property value="#orderProduct.productBarcode.barcode"/></td>
							      <td><s:property value="#orderProduct.productBarcode.product.unit"/></td>
							      <td><s:property value="#orderProduct.quantity"/></td>
							      <td><s:property value="#orderProduct.comment"/></td>
							     </tr>
							   </s:iterator>  
						  </tbody>
						  <tr class="PBAInnerTableTitale">
						    <th height="25" colspan="8" align='left'>合计</th>
						    <th align='center'><s:property value="uiBean.flowOrder.totalQuantity"/></th>
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
					         <td colspan="2"><s:property value="uiBean.flowOrder.comment"/></td>
				           </tr>
						  <tr class="InnerTableContent">
						    <td width="5%" height="25" align='left'>&nbsp;</td>
						    <td width="3%" align='left'>&nbsp;</td>
						    <td width="90%" align='left'>
						      <s:if test="#session.LOGIN_CHAIN_USER.containFunction('inventoryFlowJSPAction!cancelOrder') && formBean.canCancel && (uiBean.flowOrder.chainStore.chain_id == #session.LOGIN_CHAIN_USER.myChainStore.chain_id || #session.LOGIN_CHAIN_USER.roleType.chainRoleTypeId == 1 || #session.LOGIN_CHAIN_USER.roleType.chainRoleTypeId == 2)">
						            <input type="button" value="红冲单据" onclick="cancelOrder();"/>	
						      </s:if>
						      <s:if test="formBean.flowOrder.id > 0">
						            <input type="button" value="下载单据" onclick="downloadOrder();"/>	
						      </s:if>
						      <input type="button" value="打印单据到POS机" onclick="printOrder();"/>	
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
<script type="text/javascript" src="<%=request.getContextPath()%>/conf_files/js/ChainInvenTransfer.js"></script>
</html>