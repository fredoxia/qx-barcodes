<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib prefix="s" uri="/struts-tags" %>

<html>
<head>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=8" />
<title>产品条形码导入</title>
<%@ include file="../../common/Style.jsp"%>
<script>
$(document).ready(function(){

	parent.$.messager.progress('close'); 

});
function saveConf(){
	var params = $('#qxbabyConfForm').serialize();
	$.post("<%=request.getContextPath()%>/actionChain/chainMgmtJSON!updateQxbabyConf",params, backProcessUpdateConf,"json");
}
function backProcessUpdateConf(data){
	var returnCode = data.returnCode;
	if (returnCode == SUCCESS)
		alert("成功更新");
	else
		alert("更新失败 : " + data.message);
}
</script>

</head>
<body>
<s:form id="qxbabyConfForm" action="" method="POST"  theme="simple">
 <table width="100%" border="0">
    <s:hidden name="formBean.qxbabyConf.id"/>    
    <tr class="InnerTableContent">
      <td height="19"></td>
      <td colspan="3">
      		<div class="errorAndmes"><s:actionerror cssStyle="color:red"/><s:actionmessage cssStyle="color:blue"/></div>
      </td>
    </tr> 
    <tr class="InnerTableContent">
      <td width="100" height="19"><strong>当前销售年份</strong></td>
      <td width="70">
      		<s:select name="formBean.qxbabyConf.yearId" size="1" id="yearId"  list="uiBean.yearList" listKey="year_ID" listValue="year"  />     
      </td>
      <td width="100"><strong>当前销售季度</strong></td>
      <td>
            <s:select name="formBean.qxbabyConf.quarterId" size="1" id="quarter" list="uiBean.quarterList" listKey="quarter_ID" listValue="quarter_Name"  />
      </td>
    </tr>
    <tr class="InnerTableContent">
      <td height="30">&nbsp;</td>
      <td><input type="button" id="saveButton" name="saveButton" value="保存配置信息 " onclick="saveConf();" /> </td>
      <td>&nbsp;</td>
      <td>&nbsp;</td>  
    </tr>
  </table>
</s:form>

</body>
</html>