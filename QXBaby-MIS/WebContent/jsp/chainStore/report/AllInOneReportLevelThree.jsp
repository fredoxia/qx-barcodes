<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib prefix="s" uri="/struts-tags" %>
<%@ page import="com.onlineMIS.ORM.entity.chainS.inventoryFlow.ChainInventoryFlowOrder" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>千禧宝贝连锁店管理信息系统</title>
<%@ include file="../../common/Style.jsp"%>
<link href="<%=request.getContextPath()%>/conf_files/css/pagination.css" rel="stylesheet" type="text/css"/>
<SCRIPT src="<%=request.getContextPath()%>/conf_files/js/pagenav1.1.js" type=text/javascript></SCRIPT>
<script>
$(document).ready(function(){
	renderPaginationBar($("#currentPage").val(), $("#totalPage").val());
	parent.$.messager.progress('close'); 
	$("#org_table tr").mouseover(function(){      
		$(this).addClass("over");}).mouseout(function(){    
		$(this).removeClass("over");});
});

pageNav.fn = function(page,totalPage){                
	$("#currentPage").attr("value",page);
    document.chainAllInOneForm.action="chainReportJSPAction!generateAllInOneReport";
    document.chainAllInOneForm.submit();
};
var baseurl = "<%=request.getContextPath()%>";
function getLevelFour(brandId){
	$("#brandId").attr("value", brandId);
	pageNav.clearPager();
    document.chainAllInOneForm.action="chainReportJSPAction!generateAllInOneReport";
    document.chainAllInOneForm.submit();
}
function preLevelTwo(){
	$("#quarterId").attr("value", ALL_RECORD);
    document.chainAllInOneForm.action="chainReportJSPAction!generateAllInOneReport";
    document.chainAllInOneForm.submit();
}
</script>
</head>
<body>

    <s:form action="chainReportJSPAction!generatePurchaseStatisticReport" method="POST" name="chainAllInOneForm" id="chainAllInOneForm" theme="simple">
	<%@ include file="SalesPurchaseStatisticReportForm.jsp"%>
	<%@ include file="../../common/pageForm.jsp"%>
    <table width="80%" align="center"  class="OuterTable">
	    <tr><td>
	         <div class="errorAndmes"><s:actionerror cssStyle="color:red"/><s:actionmessage cssStyle="color:blue"/></div>
			 <table width="100%" border="0">
			    <tr>
			       <td height="50" colspan="7">
				   	 <table width="100%" border="0">
				       <tr class="PBAOuterTableTitale">
				         <td height="32" colspan="4">连锁店综合报表</td>
			           </tr>
				       <tr class="InnerTableContent">
				         <td colspan="4" height="20">连锁店:<s:property value="uiBean.allInOneTotal.chainStore.chain_name"/>
				         					 &nbsp;  日期 : <s:date name="uiBean.allInOneTotal.startDate"  format="yyyy-MM-dd"/> 至 <s:date name="uiBean.allInOneTotal.endDate"  format="yyyy-MM-dd"/></td>
				       </tr>
				       <tr class="InnerTableContent">
				         <td colspan="4" height="20">商品年份 : <s:property value="uiBean.allInOneTotal.year.year"/> <s:property value="uiBean.allInOneTotal.quarter.quarter_Name"/>
				         </td>
				       </tr>				       
				     </table></td>
			    </tr>
			    <tr>
			      <td colspan="7">
			            <!-- table to display the product information -->
						<table width="100%"  align="left" class="OuterTable" id="org_table">
						  <tr class="PBAInnerTableTitale" align='left'>
						    <th width="3%" height="35">&nbsp;</th>
						    <th width="5%">品牌</th>
						    <th width="8%">采购数量</th>
						    <th width="8%">采购退货量</th>
						    <th width="8%">零售量</th>
						    <th width="8%">零售退货</th>
						    <th width="8%">赠品量</th>
						    <th width="8%">当前库存</th>
						    <th width="5%"></th>
						  </tr>
						  <tbody id="orderTablebody">
						      <s:iterator value="uiBean.allInOneLevelThree" status = "st" id="ci" >
						  		<tr class="InnerTableContent" id="orderRow0" class="InnerTableContent" <s:if test="#st.odd">style='background-color: rgb(255, 250, 208);'</s:if>>   
							      <td height="25"><s:property value="#st.index +1"/></td>							      
							      <td align="center"><s:property value="#ci.brand.brand_Name"/></td>						      
							      <td align="right"><s:property value="#ci.purchaseQ"/></td>
							      <td align="right"><s:property value="#ci.purchaseR"/></td>
							      <td align="right"><s:property value="#ci.salesQ"/></td>
							      <td align="right"><s:property value="#ci.salesR"/></td>
							      <td align="right"><s:property value="#ci.salesF"/></td>
							      <td align="right"><s:property value="#ci.currentInventory"/></td>
							      <td align="center"><a href='#' onclick="getLevelFour(<s:property value="#ci.brand.brand_ID"/>)"><img src='<%=request.getContextPath()%>/conf_files/web-image/editor.gif' border='0'/></a></td>
							     </tr>
							   </s:iterator>  
						  </tbody>
						  <s:if test="uiBean.allInOneLevelThree.size >0">
							  <tr class="PBAInnerTableTitale">
							    <td colspan="2" align="left">合计</td>
							    <td align="right"><s:property value="uiBean.allInOneTotal.purchaseQ"/></td>
							    <td align="right"><s:property value="uiBean.allInOneTotal.purchaseR"/></td>
							    <td align="right"><s:property value="uiBean.allInOneTotal.salesQ"/></td>
							    <td align="right"><s:property value="uiBean.allInOneTotal.salesR"/></td>
							    <td align="right"><s:property value="uiBean.allInOneTotal.salesF"/></td>
							    <td align="right"><s:property value="uiBean.allInOneTotal.currentInventory"/></td>
							    <td></td>
							  </tr>
							  <tr class="InnerTableContent">	      
						           <td colspan="9"><div id="pageNav"></div></td>
						      </tr>	
						  </s:if><s:else>
							  <tr class="InnerTableContent">
							    <th colspan="9" align="left">暂时没有信息</th>
							  </tr>						  
						  </s:else>
					     </table>
			      </td>
			    </tr>
			    <tr class="InnerTableContent">
			      <td height="10">
			          <input type="button" value="返回上层" onclick="preLevelTwo()"/>
			      </td>
			      <td colspan="6">

				  </td>
			    </tr>
			  </table>
	   </td></tr>
	 </table>
	 </s:form>
</body>
</html>