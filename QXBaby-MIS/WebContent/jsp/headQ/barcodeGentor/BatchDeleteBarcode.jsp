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
    document.barcodeForm.action="productJSPAction!batchDeleteBarcode";
    document.barcodeForm.submit();
}
function saveUpdate(){
    document.barcodeForm.action="productJSPAction!batchUpdateBarcode";
    document.barcodeForm.submit();
}
</script>
</head>
<body>

    <s:form action="/action/productJSPAction!batchDeleteBarcode" method="POST" name="barcodeForm" id="barcodeForm" enctype="multipart/form-data" theme="simple">

    <table width="85%" align="center"  class="OuterTable">
	    <tr><td>
	         <div class="errorAndmes"><s:actionerror cssStyle="color:red"/><s:actionmessage cssStyle="color:blue"/></div>
			 <table width="100%" align="left" border="0">
			 	       <tr class="PBAOuterTableTitale" align="left">
	          				<th colspan="2">批量删除条码信息 <br/>
	          				- 必须是excel 2003的格式文件，也就是xls格式. 请从条码功能处下载条码文件再导入
	          				</th>
	       				</tr>
				           <tr class="InnerTableContent">
					         <td height="40">条码文件</td>
					         <td><input type="file" name="formBean.inventory" id="inventory"/></td>
				           </tr>
						   <tr class="InnerTableContent">
						    <td width="10%" height="25" align='left'>&nbsp;</td>
						    <td width="90%" align='left'>
						      <s:if test="#session.LOGIN_USER.containFunction('productJSPAction!batchUpdateBarcode')">
						           <input type="button" value="上传批量更新" onclick="saveUpdate();" />
						      </s:if>
						      <s:if test="#session.LOGIN_USER.containFunction('productJSPAction!batchDeleteBarcode')">
						           <input type="button" value="上传批量删除" onclick="save();" />
						      </s:if>				
						    </td>
					      </tr>
			</table>
	   </td></tr>
	 </table>
	 </s:form>

</body>
</html>