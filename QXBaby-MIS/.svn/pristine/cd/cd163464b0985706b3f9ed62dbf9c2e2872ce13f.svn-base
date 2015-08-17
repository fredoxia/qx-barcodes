<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib prefix="s" uri="/struts-tags" %>
<%@ page import="com.onlineMIS.common.Common_util" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>千禧宝贝连锁店管理信息系统</title>
<%@ include file="../../common/Style.jsp"%>
<link href="<%=request.getContextPath()%>/conf_files/css/pagination.css" rel="stylesheet" type="text/css"/>
<SCRIPT src="<%=request.getContextPath()%>/conf_files/js/pagenav1.1.js" type=text/javascript></SCRIPT>
<LINK type=text/css href="<%=request.getContextPath()%>/conf_files/js/tableRowSelect/tableRowSelect.css" rel=STYLESHEET>
<script type="text/javascript" src="<%=request.getContextPath()%>/conf_files/js/jquery.hotkeys-0.7.9.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/conf_files/js/tableRowSelect/tableRowSelect.js"></script>

<script>
//页面查看，统一零售价+我的零售价
$(document).ready(function(){
	renderPaginationBar($("#currentPage").val(), $("#totalPage").val());
	$("#org_table tr").mouseover(function(){      
		$(this).addClass("over");}).mouseout(function(){    
		$(this).removeClass("over");}); 
	initialize();
	window.onblur=function(){window.focus();};
	});
	
function selectProductValue(barcode){
    var index_trigger = $("#index").val();
    var suffix = $("#suffix").val();
    
	var exist = window.opener.validateRowInputFromChild(index_trigger, suffix, barcode);
	if (exist){
       window.opener.retrieveProductByBarcode(index_trigger, suffix, barcode);  
	}
    window.close();
}
pageNav.fn = function(page,totalPage){                
	$("#currentPage").attr("value",page);
    document.searchProductCodeForm.action="chainSalesJSPAction!scanByProductCode";
    document.searchProductCodeForm.submit();
};
function tableRowSelectEnter(){
	var selectedRow = $("#prevTrIndex").val();
	var selectBarcode = $("#barcode"+selectedRow);
	//alert(selectedRow);
	if (selectBarcode == undefined)
		alert("没有找到你选总的条码");
	else {
		var selectedBarcodeValue = selectBarcode.val();
		selectProductValue(selectedBarcodeValue);
    }
}

function  clickRight(){
	pageNav.nextPage();
}
function clickLeft(){
	pageNav.prePage();
}
function clickEsc(){
	window.close();
}
</script>
</head>
<body>
   <!-- 这张页面新增连锁店的自己销售价格 -->
   <s:form name="searchProductCodeForm" id ="searchProductCodeForm" action="chainSalesJSPAction!scanByProductCode">
   <%@ include file="../../common/pageForm.jsp"%>
   <s:hidden id="suffix" name="formBean.suffix"/>
   <s:hidden id="index" name="formBean.index"/>
   <s:hidden id="productCode" name="formBean.productCode"/>
   <s:hidden id="chainStoreId" name="formBean.chainId"/>
   <table width="100%"  align="left" class="OuterTable" id="org_table">
		  <tr class="PBAInnerTableTitale" align='left'>
		    <th width="90" height="35">货号</th>
		    <th width="40" height="35">颜色</th>
		    <th width="95">品牌</th>
		    <th width="40">年</th>
		    <th width="30">季度</th>
		    <th width="95">条码</th>
		    <th width="50">单位</th>
		    <th width="30">库存</th>
		    <th width="55">统一售价</th>
		    <th width="55">我的售价</th>
		    <th>&nbsp;</th>
		  </tr>
		  <tbody class="dataTablegrid">
	      <s:iterator value="uiBean.cpbVOs" status="st" id="vo" >
			    <tr class="InnerTableContent"  style='<s:if test="#st.even"> <%=Common_util.EVEN_ROW_BG_STYLE %></s:if><s:if test="#vo.barcodeBelong > 0"> color:blue;</s:if>'>	      
			      <td height="25"><s:property value="#vo.productCode"/></td>
			      <td><s:property value="#vo.color"/></td>
			      <td><s:property value="#vo.brand"/></td>
			      <td><s:property value="#vo.year"/></td>
			      <td><s:property value="#vo.quarter"/></td>
			      <td><s:property value="#vo.barcode"/></td>
			      <td><s:property value="#vo.unit"/></td>
			      <td><s:property value="#vo.InventoryLevel"/></td>
			      <td><s:property value="#vo.chainSalePrice"/></td>
			      <td><s:property value="#vo.mySalePrice"/></td>
			      <td><input type="hidden" id="barcode<s:property value="#st.index"/>" value="<s:property value="#vo.barcode"/>"/><a href="#" onclick="selectProductValue('<s:property value="#vo.barcode"/>');"><img src='<%=request.getContextPath()%>/conf_files/web-image/editor.gif' border='0'/></a></td>
			    </tr>
	       </s:iterator>
	       </tbody>
	       <s:if test="uiBean.cpbVOs.size==0">
			 	<tr height="22" class="InnerTableContent" align="center">
			 	        <th colspan="10">没有找到相应结果</td>
			 	 </tr>
		   </s:if><s:else>
				<tr class="InnerTableContent" align="left">	      
						<th colspan="10"><div id="pageNav"></div></td>
				</tr>					       
			</s:else>  	  	  
	</table>
	</s:form>
	<input type="hidden" name="prevTrIndex" id="prevTrIndex" value="-1" /> 
	<br/>
</body>
</html>