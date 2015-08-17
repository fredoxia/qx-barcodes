<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib prefix="s" uri="/struts-tags" %>
<%@ page import="com.onlineMIS.ORM.entity.chainS.inventoryFlow.ChainInventoryFlowOrder" %>
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
function getLevelTwo(yearId){
	$("#yearId").attr("value", yearId);
    document.chainInventoryFlowForm.action="inventoryFlowJSPAction!getLevelTwoCurrentInventory";
    document.chainInventoryFlowForm.submit();
}
function downloadInventory(yearId){
	$("#yearId").attr("value", yearId);
	$("#reportType").attr("value", 1);
    document.chainInventoryFlowForm.action="inventoryFlowJSPAction!generateChainInventoryExcelReport";
    document.chainInventoryFlowForm.submit();
}
function downloadAllInventory(){
	$("#reportType").attr("value", 0);
    document.chainInventoryFlowForm.action="inventoryFlowJSPAction!generateChainInventoryExcelReport";
    document.chainInventoryFlowForm.submit();
}
</script>
</head>
<body>

    <s:form action="/actionChain/inventoryFlowJSPAction!getLevelTwoCurrentInventory" method="POST" name="chainInventoryFlowForm" id="chainInventoryFlowForm" theme="simple">
	<s:hidden name="formBean.chainId"/>  
	<s:hidden name="formBean.yearId" id="yearId"/>  
	<s:hidden name="formBean.reportType" id="reportType" value="1"/> 
    <table width="60%" align="center"  class="OuterTable">
	    <tr><td>
	         <div class="errorAndmes"><s:actionerror cssStyle="color:red"/><s:actionmessage cssStyle="color:blue"/></div>
			 <table width="100%" border="0">
			    <tr>
			       <td height="50" colspan="7">
				   	 <table width="100%" border="0">
				       <tr class="PBAOuterTableTitale">
				         <td height="32" colspan="4">
				              	连锁店库存状况表
				         </td>
			           </tr>
				       <tr class="InnerTableContent">
				         <td colspan="4" height="20">连锁店:<s:property value="uiBean.inventoryItem.chainStore.chain_name"/></td>
				       </tr>
				     </table></td>
			    </tr>
			    <tr>
			      <td colspan="7">
			            <!-- table to display the product information -->
						<table width="100%"  align="left" class="OuterTable" id="org_table">
						  <tr class="PBAInnerTableTitale" align='left'>
						    <th width="3%" height="35">&nbsp;</th>
						    <th width="5%">年份</th>
						    <th width="8%">库存量</th>
						    <th width="10%">库存成本金额</th>
						    <th width="10%">估算销售金额</th>
						    <th width="5%">查看 下载</th>

						  </tr>
						  <tbody id="orderTablebody">
						      <s:iterator value="uiBean.levelOneInventoryItem" status = "st" id="ci" >
						  		<tr class="InnerTableContent" id="orderRow0" class="InnerTableContent" <s:if test="#st.odd">style='background-color: rgb(255, 250, 208);'</s:if>>   
							      <td height="25"><s:property value="#st.index +1"/></td>							      
							      <td align="center"><s:property value="#ci.year.year"/></td>						      
							      <td align="right"><s:property value="#ci.totalQuantity"/></td>
							      <td align="right"><s:if test="#session.LOGIN_CHAIN_USER.containFunction('purchaseAction!seeCost')"><s:text name="format.price"><s:param value="#ci.totalCostAmt"/></s:text></s:if><s:else>-</s:else></td>
							      <td align="right"><s:text name="format.price"><s:param value="#ci.totalSalesAmt"/></s:text></td>
							      <td align="center"><a href='#' onclick="getLevelTwo(<s:property value="#ci.year.year_ID"/>)"><img src='<%=request.getContextPath()%>/conf_files/web-image/editor.gif' border='0' title="查看"/></a>&nbsp;
							      					 <a href='#' onclick="downloadInventory(<s:property value="#ci.year.year_ID"/>)"><img src='<%=request.getContextPath()%>/conf_files/easyUI/themes/icons/filesave.png' border='0' title="下载"/></a></td>
							     </tr>
							   </s:iterator>  
						  </tbody>
						  <s:if test="uiBean.levelOneInventoryItem.size >0">
							  <tr class="PBAInnerTableTitale">
							    <td colspan="2" align="left">合计</td>
							    <td align="right"><s:property value="uiBean.inventoryItem.totalQuantity"/></td>
							    <td align="right"><s:if test="#session.LOGIN_CHAIN_USER.containFunction('purchaseAction!seeCost')">
							    						<s:text name="format.price"><s:param value="uiBean.inventoryItem.totalCostAmt"/></s:text>
							    				  </s:if><s:else>-</s:else>
							    </td>
							    <td align="right"><s:text name="format.price"><s:param value="uiBean.inventoryItem.totalSalesAmt"/></s:text></td>
							    <td align="center">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href='#' onclick="downloadAllInventory()"><img src='<%=request.getContextPath()%>/conf_files/easyUI/themes/icons/filesave.png' border='0' title="下载"/></a></td>

							  </tr>
						  </s:if><s:else>
							  <tr class="InnerTableContent">
							    <th colspan="6" align="left">无法找到库存信息</th>
							  </tr>						  
						  </s:else>
					     </table>
			      </td>
			    </tr>
			    <tr class="InnerTableContent">
			      <td height="10" colspan="7">

				  </td>
			    </tr>
			  </table>
	   </td></tr>
	 </table>
	 </s:form>
</body>
</html>