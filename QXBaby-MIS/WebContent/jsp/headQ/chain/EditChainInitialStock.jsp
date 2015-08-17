<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib prefix="s" uri="/struts-tags" %>
<%@ page import="com.onlineMIS.ORM.entity.chainS.inventoryFlow.ChainInventoryFlowOrder" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>千禧宝贝连锁店管理信息系统</title>
<%@ include file="../../common/Style.jsp"%>
<script type="text/javascript" src="<%=request.getContextPath()%>/conf_files/js/ChainInitialStock.js"></script>
<LINK href="<%=request.getContextPath()%>/conf_files/css/qxbaby_css.css" type=text/css rel=STYLESHEET>
<script>
$(document).ready(function(){
	parent.$.messager.progress('close'); 
	sumTotal();
});

var baseurl = "<%=request.getContextPath()%>";
index = parseInt("<s:property value='formBean.stocks.size()'/>");

function getChainStock(){
	parent.$.messager.progress({
		title : '提示',
		text : '数据处理中，请稍后....'
	});
	var params = "formBean.chainStore.client_id=" + $("#chainStore").val();
	window.location.href = "chainSMgmtJSP!getChainInitialStocks?" + params;
}

function save(){

	if (validateForm()){
		parent.$.messager.progress({
			title : '提示',
			text : '数据处理中，请稍后....'
		});
   	 	document.chainInitialStockForm.action="chainSMgmtJSP!saveChainInitialStocks";
    	document.chainInitialStockForm.submit();
	}
}


</script>
</head>
<body>

    <s:form action="/action/inventoryFlowAction!saveToDraft" method="POST" name="chainInitialStockForm" id="chainInitialStockForm" enctype="multipart/form-data"  theme="simple">
    <table width="72%" align="center"  class="OuterTable">
	    <tr><td>
	         <div class="errorAndmes"><s:actionerror cssStyle="color:red"/><s:actionmessage cssStyle="color:blue"/></div>
			 <table width="100%" border="0">
			    <tr>
			       <td height="50" colspan="7">
				   	 <table width="100%" border="0">
				       <tr class="InnerTableContent">
				         <td width="234" height="32">连锁店:<s:select id="chainStore" name="formBean.chainStore.client_id"  list="uiBean.chainStores" listKey="client_id" listValue="chain_name"/></td>
				         <td width="160"><input type="button" value="获取已输入库存" onclick="getChainStock();"/></td>
				         <td width="300"></td>
				         <td width="200"></td>
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
						    <th width="90">货号/名称</th>
						    <th width="40">颜色</th>
						    <th width="93">品牌</th>
						    <th width="40">年份</th>
						    <th width="40">季度</th>
						    <th width="40">单位</th>
						    <th width="65">库存数量</th>
						    <th width="52">成本</th>
						    <th width="52">总成本</th>
						    <th width="20"></th>
						  </tr>
						  <tbody id="orderTablebody">
						  	   <s:iterator value="formBean.stocks" status = "st" id="stock" >
							  	   <tr class="InnerTableContent" id="orderRow<s:property value="#st.index"/>">   
								      <td height="25"><s:property value="#st.index + 1"/></td>
								      <td><input type="text" name="formBean.stocks[<s:property value="#st.index"/>].id.barcode" id="barcode<s:property value="#st.index"/>" size="13" value="<s:property value="#stock.id.barcode"/>"/>
								      	  <input type="hidden" name="formBean.stocks[<s:property value="#st.index"/>].productBarcode.id" id="productId<s:property value="#st.index"/>" value="<s:property value="#stock.productBarcode.id"/>"/>
								      </td>
								      <td><input type="text" name="formBean.stocks[<s:property value="#st.index"/>].productBarcode.product.productCode" id="productCode<s:property value="#st.index"/>" size="12" value="<s:property value="#stock.productBarcode.product.productCode"/>"/></td>
								      <td><input type="text" name="formBean.stocks[<s:property value="#st.index"/>].productBarcode.color.name" id="color<s:property value="#st.index"/>" size="5" value="<s:property value="#stock.productBarcode.color.name"/>"/></td>
								      <td><input type="text" name="formBean.stocks[<s:property value="#st.index"/>].productBarcode.product.brand.brand_Name" id="brand<s:property value="#st.index"/>"  size="13" readonly value="<s:property value="#stock.productBarcode.product.brand.brand_Name"/>"/></td>
								      <td><input type="text" name="formBean.stocks[<s:property value="#st.index"/>].productBarcode.product.year.year" id="year<s:property value="#st.index"/>"  size="4" readonly value="<s:property value="#stock.productBarcode.product.year.year"/>"/></td>
								      <td><input type="text" name="formBean.stocks[<s:property value="#st.index"/>].productBarcode.product.quarter.quarter_Name" id="quarter<s:property value="#st.index"/>"  size="4" readonly value="<s:property value="#stock.productBarcode.product.quarter.quarter_Name"/>"/></td>
								      <td><input type="text" name="formBean.stocks[<s:property value="#st.index"/>].productBarcode.product.unit" id="unit<s:property value="#st.index"/>" size="4" readonly  value="<s:property value="#stock.productBarcode.product.unit"/>"/></td>
								      <td><input type="text" name="formBean.stocks[<s:property value="#st.index"/>].quantity" id="quantity<s:property value="#st.index"/>"  size="2" onchange="calculateTotal(<s:property value="#st.index"/>)" onkeypress="return is_number(event);"  value="<s:property value="#stock.quantity"/>"/></td>
								      <td><input type="text" name="formBean.stocks[<s:property value="#st.index"/>].cost" id="cost<s:property value="#st.index"/>"  size="4" readonly  value="<s:property value="#stock.cost"/>"/></td>
								      <td><input type="text" name="formBean.stocks[<s:property value="#st.index"/>].costTotal" id="costTotal<s:property value="#st.index"/>"  size="4" readonly  value="<s:property value="#stock.costTotal"/>"/></td>
								      <td><div id="delIcon<s:property value="#st.index"/>" style="display:block"><img src="<%=request.getContextPath()%>/conf_files/web-image/delete.png" border="0"  onclick="deleteRow('orderRow<s:property value="#st.index"/>');" style="cursor:pointer;"/></div>
									  </td>
								    </tr> 
							   </s:iterator>  
						  	   <tr class="InnerTableContent" id="orderRow<s:property value="formBean.stocks.size()"/>">   
							      <td height="25"><s:property value="formBean.stocks.size() + 1"/></td>
							      <td><input type="text" name="formBean.stocks[<s:property value="formBean.stocks.size()"/>].id.barcode" id="barcode<s:property value="formBean.stocks.size()"/>" size="13"/>
							      	  <input type="hidden" name="formBean.stocks[<s:property value="formBean.stocks.size()"/>].productBarcode.id" id="productId<s:property value="formBean.stocks.size()"/>"/>
							      </td>
							      <td><input type="text" name="formBean.stocks[<s:property value="formBean.stocks.size()"/>].productBarcode.product.productCode" id="productCode<s:property value="formBean.stocks.size()"/>" size="12"/></td>
							      <td><input type="text" name="formBean.stocks[<s:property value="formBean.stocks.size()"/>].productBarcode.color.name" id="color<s:property value="formBean.stocks.size()"/>" size="5"/></td>
							      <td><input type="text" name="formBean.stocks[<s:property value="formBean.stocks.size()"/>].productBarcode.product.brand.brand_Name" id="brand<s:property value="formBean.stocks.size()"/>"  size="13" readonly/></td>
							      <td><input type="text" name="formBean.stocks[<s:property value="formBean.stocks.size()"/>].productBarcode.product.year.year" id="year<s:property value="formBean.stocks.size()"/>"  size="4" readonly/></td>
							      <td><input type="text" name="formBean.stocks[<s:property value="formBean.stocks.size()"/>].productBarcode.product.quarter.quarter_Name" id="quarter<s:property value="formBean.stocks.size()"/>"  size="4" readonly/></td>
							      <td><input type="text" name="formBean.stocks[<s:property value="formBean.stocks.size()"/>].productBarcode.product.unit" id="unit<s:property value="formBean.stocks.size()"/>" size="4" readonly /></td>
							      <td><input type="text" name="formBean.stocks[<s:property value="formBean.stocks.size()"/>].quantity" id="quantity<s:property value="formBean.stocks.size()"/>"  size="2" onchange="calculateTotal(<s:property value="formBean.stocks.size()"/>)" onkeypress="return is_number(event);" value="<s:property value="#orderProduct.quantity"/>"/></td>
							      <td><input type="text" name="formBean.stocks[<s:property value="formBean.stocks.size()"/>].cost" id="cost<s:property value="formBean.stocks.size()"/>"  size="4" readonly value="<s:property value="#orderProduct.cost"/>"/></td>
							      <td><input type="text" name="formBean.stocks[<s:property value="formBean.stocks.size()"/>].costTotal" id="costTotal<s:property value="formBean.stocks.size()"/>"  size="4" readonly value="<s:property value="#orderProduct.totalCost"/>"/></td>
							      <td><div id="delIcon<s:property value="formBean.stocks.size()"/>" style="display:none"><img src="<%=request.getContextPath()%>/conf_files/web-image/delete.png" border="0"  onclick="deleteRow('orderRow<s:property value="formBean.stocks.size()"/>');" style="cursor:pointer;"/></div>
								  </td>
							    </tr> 
						  </tbody>
						  <tr class="PBAInnerTableTitale">
						    <th height="25" colspan="8" align='left'>合计</th>
						    <th align='left'><input type="text" id="totalQuantity" readonly size="2"/></th>
						    <th>&nbsp;</th>
						    <th align='left'><input type="text" id="sumCost" readonly size="4"/></th>
						    <th>&nbsp;</th>
						  </tr>
					     </table>
			      </td>
			    </tr>
			    <tr class="InnerTableContent">
					 <td height="40">库存文件</td>
					 <td colspan="2"><input type="file" name="formBean.inventory" id="inventory"/></td>
				</tr>
			    <tr class="InnerTableContent">
			      <td height="30" colspan="7">
			            <!-- table to show the footer of the chain inventory flow -->
						<table width="100%" align="left" border="0">
						  <tr class="InnerTableContent">
						    <td width="3%" height="25" align='left'>&nbsp;</td>
						    <td width="3%" align='left'>&nbsp;</td>
						    <td width="90%" align='left'>
						      <input type="button" value="保存" onclick="save();" />			
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