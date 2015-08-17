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
function saveProductUnit(){
	var params=$("#updateUnitForm").serialize();

    $.post("basicDataJSON!updateProductUnit",params, updateUnitBKProcess,"json");	
}
function updateUnitBKProcess(data){
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
    <s:form id="updateUnitForm" name="updateUnitForm" method="post" action="" theme="simple">
	    <table width="100%" border="0">
	       <tr>
	          <td colspan="2"></td>
	       </tr>
	       <tr class="InnerTableContent">
	          <td>单位:</td><td><s:hidden name="formBean.productUnit.id"/>
	          <s:textfield name="formBean.productUnit.productUnit" maxlength="10"/></td>
	       </tr>
	       <tr class="InnerTableContent">
	          <td colspan="2">
	          <a onclick="saveProductUnit();" href="javascript:void(0);" class="easyui-linkbutton">保存</a>
	          </td>
	       </tr>
	    </table>
	    </s:form>
      
</body>
</html>