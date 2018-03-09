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
});
function save(){
	if ($("#yearId").val() == "" || $("#yearId").val() == "0")
		alert("年份不能为空");
	else if ($("#quarterId").val() == "" || $("#quarterId").val() == "0")
		alert("季度不能为空");
	else if ($("#brand_ID").val() == "" || $("#brand_ID").val() == "0")
		alert("品牌不能为空");
	else if ($("#inventory").val() == "")
		alert("批量增加条码文件不能为空");
	else {
		$.messager.progress({
			title : '提示',
			text : '数据处理中，请稍后....'
		});
    	document.barcodeForm.action="productJSPAction!batchInsertBarcode";
    	document.barcodeForm.submit();
	}
}
</script>
</head>
<body>

    <s:form action="/action/productJSPAction!batchInsertBarcode" method="POST" name="barcodeForm" id="barcodeForm" enctype="multipart/form-data" theme="simple">

    <table width="85%" align="center"  class="OuterTable">
	    <tr><td>
	         <div class="errorAndmes"><s:actionmessage cssStyle="color:blue"/></div>
			 <table width="100%" align="left" border="0">
			 	       <tr class="PBAOuterTableTitale" align="left">
	          				<th colspan="4">批量导入条码信息 <br/>
	          				- 请按照格式导入,任何一个错误条码都将导致所有新增条码失败
	          				</th>
	       				</tr>
	       					<tr class="InnerTableContent">
					         <td width="10%" height="40">年份</td>
					         <td width="30%"><s:select name="formBean.productBarcode.product.year.year_ID" size="1" id="yearId"  list="uiBean.basicData.yearList" listKey="year_ID" listValue="year"  /></td>
					         <td width="10%">季度</td>
					         <td width="50%"><s:select name="formBean.productBarcode.product.quarter.quarter_ID" size="1" id="quarter_ID" list="uiBean.basicData.quarterList" listKey="quarter_ID" listValue="quarter_Name"  /></td>
				           </tr>
				           <tr class="InnerTableContent">
				           	 <td height="40">品牌</td>
					         <td><%@ include file="SearchBrandStub.jsp"%></td>
					         <td>条码文件</td>
					         <td><input type="file" name="formBean.inventory" id="inventory"/></td>
				           </tr>
						   <tr class="InnerTableContent">
						    <td height="25" align='left'>&nbsp;</td>
						    <td align='left' colspan="3">
						      <s:if test="#session.LOGIN_USER.containFunction('productJSPAction!batchInsertBarcode')">
						           <input type="button" value="上传导入条码" onclick="save();" />
						      </s:if>				
						    </td>
					      </tr>
			</table>
			<div class="errorAndmes"><s:actionerror cssStyle="color:red"/></div>
	   </td></tr>
	 </table>
	 
	 </s:form>

</body>
</html>