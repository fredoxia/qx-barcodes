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
function saveYear(){
	var params=$("#updateYearForm").serialize();

    $.post("basicDataJSON!updateYear",params, updateYearBKProcess,"json");	
}
function updateYearBKProcess(data){
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
    <s:form id="updateYearForm" name="updateYearForm" method="post" action="action/basicData!saveUpdateBrand" theme="simple" onsubmit="return validateForm();">
	    <table width="100%" border="0">
	       <tr>
	          <td colspan="2"></td>
	       </tr>
	       <tr class="InnerTableContent">
	          <td>年份:</td><td><s:hidden name="formBean.year.year_ID"/>
	          <s:textfield name="formBean.year.year" cssClass="easyui-numberspinner" style="width:80px;"  
        required="required" data-options="min:2010,max:2030,editable:false"/></td>
	       </tr>
	       <tr class="InnerTableContent">
	          <td colspan="2">
	          <a onclick="saveYear();" href="javascript:void(0);" class="easyui-linkbutton">保存</a>
	          </td>
	       </tr>
	    </table>
	    </s:form>
      
</body>
</html>