<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib prefix="s" uri="/struts-tags" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<title>千禧宝贝连锁店管理信息系统</title>

</head>
<body>
<script>
function saveQuarter(){
	if ($("#quarterName").val() == "")
		alert("季度不能为空");
	else {
	    var params=$("#updateBrandForm").serialize();

        $.post("basicDataJSON!updateQuarter",params, updateQuarterBKProcess,"json");	
	}
}
function updateQuarterBKProcess(data){
	var response = data.response;
	var returnCode = response.returnCode;
	if (returnCode != SUCCESS)
		alert(response.message);
	else {
		$.modalDialog.handler.dialog('close');
		$("#dataGrid").datagrid('reload');
	}		
}
</script>
    <s:form id="updateBrandForm" name="updateBrandForm" method="post" action="action/basicData!saveUpdateBrand" theme="simple" onsubmit="return validateForm();">
	    <table width="100%" border="0">
	       <tr>
	          <td colspan="2"></td>
	       </tr>
	       <tr class="InnerTableContent">
	          <td>季度:</td><td><s:hidden name="formBean.quarter.quarter_ID"/>
	          <s:textfield id="quarterName" name="formBean.quarter.quarter_Name" maxLength="5" required="required" /></td>
	       </tr>
	       <tr class="InnerTableContent">
	          <td colspan="2">
	          <a onclick="saveQuarter();" href="javascript:void(0);" class="easyui-linkbutton">保存</a>
	          </td>
	       </tr>
	    </table>
	    </s:form>
      
</body>
</html>