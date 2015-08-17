<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib prefix="s" uri="/struts-tags" %>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@ include file="../../common/Style.jsp"%>
<title>千禧宝贝连锁店管理信息系统</title>
<script>

</script>
</head>
<body>
<script>
function saveIncrement(){
	var type = $("#priceIncreType").val();
	var value = $("#incrementValue").numberspinner('getValue'); ;
	if (value == 0)
		alert("请输入涨幅的值");
	else {
		var params=$("#updatePriceIncreForm").serialize();
    	$.post("chainSMgmtJSON!updatePriceIncre",params, updateIncreBKProcess,"json");	
	}
}
function updateIncreBKProcess(data){
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
    <s:form id="updatePriceIncreForm" name="updatePriceIncreForm" method="post" action="" theme="simple" >
	    <table width="100%" border="0">

	       <tr class="InnerTableContent">
	          <td>涨幅种类   :</td><td>
	          <s:hidden name="formBean.priceIncrement.id"/>
	          <s:select id="priceIncreType" name="formBean.priceIncrement.incrementType" list="#{1:'按照%上涨',2:'固定价格上涨'}"  listKey="key" listValue="value"/></td>
	       </tr>
	       <tr class="InnerTableContent">
	          <td>涨幅         :</td>
	          <td><s:textfield id="incrementValue" name="formBean.priceIncrement.increment" cssClass="easyui-numberspinner"  style="width:80px;" required="required" data-options="min:-100,max:100,editable:true"/></td>
	       </tr>
	       <tr class="InnerTableContent">
	          <td>描述        :</td>
	          <td><s:textfield id="description" name="formBean.priceIncrement.description" readonly="true"/> *系统会自动添加描述</td>
	       </tr>
	       <tr class="InnerTableContent">
	       <td colspan="2">
	          <a onclick="saveIncrement();" href="javascript:void(0);" class="easyui-linkbutton">保存</a>
	       </td>
	       </tr>
	    </table>
	    </s:form>

	      
</body>
</html>