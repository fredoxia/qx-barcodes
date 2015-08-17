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
function saveCategory(){
	if ($("#categoryName").val() == "")
		alert("类别不能为空");
	else {
	    var params=$("#updateCategoryForm").serialize();

        $.post("basicDataJSON!updateCategory",params, updateCategoryBKProcess,"json");	
	}
}
function updateCategoryBKProcess(data){
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
    <s:form id="updateCategoryForm" name="updateCategoryForm" method="post" action="" theme="simple" onsubmit="return validateForm();">
	    <table width="100%" border="0">
	       <tr>
	          <td colspan="2"></td>
	       </tr>
	       <tr class="InnerTableContent">
	          <td>类别:</td><td><s:hidden name="formBean.category.category_ID"/>
	          <s:textfield id="categoryName" name="formBean.category.category_Name" maxLength="15" required="required" /></td>
	       </tr>
	       <tr class="InnerTableContent">
	          <td>所属         :</td><td><s:select id="type" name="formBean.category.chainId" list="#{-1:'总部类别',1:'连锁店类别'}" listKey="key" listValue="value"/></td>
	       </tr>	       
	       <tr class="InnerTableContent" >
	          <td colspan="2">
	          <a onclick="saveCategory();" href="javascript:void(0);" class="easyui-linkbutton">保存</a>
	          </td>
	       </tr>
	    </table>
	    </s:form>
      
</body>
</html>