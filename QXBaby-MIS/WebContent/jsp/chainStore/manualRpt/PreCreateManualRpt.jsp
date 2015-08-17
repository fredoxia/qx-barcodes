<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-Strict.dtd">
<%@taglib prefix="s" uri="/struts-tags" %>
<%@ page import="com.onlineMIS.common.Common_util,java.util.Date,java.text.SimpleDateFormat" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<meta http-equiv="X-UA-Compatible" content="IE=8" />
<title>千禧宝贝连锁店管理信息系统</title>
<%@ include file="../../common/Style.jsp"%>
<script>
$(document).ready(function(){
	parent.$.messager.progress('close'); 
});
function changeChainStore(chainId){
}
function checkPreCreateDailyManualRpt(){
	var chainId = $("#chainId").val();
	var rptDate = $("#rptDate").val();
	if (chainId == 0 || rptDate == ""){
		alert("连锁店和报表日期都是必选项");
		return false;
	} else {
		return true;
	}
}

</script>
</head>
<body>
	<div class="easyui-layout" border="false" style="width:800px;height:550px">
		<div data-options="region:'north',border:false" style="height: 150px;">
    		<s:form id="manualRptForm" name="manualRptForm" action="chainDailyManualRptJSPAction!createNewManualRpt" theme="simple" method="POST" onsubmit="return checkPreCreateDailyManualRpt();"> 
     			 <table width="100%" border="0">
						   	<tr class="InnerTableContent">
					          <td height="35" colspan="3">
					              *所有连锁店必须在当天或者第二天完成总结。日期一过，即将自动锁定。<br/>
					              <div class="errorAndmes"><s:actionerror cssStyle="color:red"/></div>
					          </td>
						    </tr>
						    <tr class="InnerTableContent">
						      <td height="25" width="80">&nbsp;</td>
						      <td width="50"><strong>日期</strong></td>
						      <td ><s:textfield id="rptDate" name="formBean.rptDate" cssClass="easyui-datebox" editable="false"/>
						      	   
						      </td>
						    </tr>
						    <tr class="InnerTableContent">
						      <td height="25">&nbsp;</td>
						      <td><strong>连锁店</strong></td>
						      <td><%@ include file="../include/SearchChainStore.jsp"%><input type="hidden" id="indicator" name="formBean.indicator" value="-1"/></td>
						    </tr>	
		                    <tr class="InnerTableContent">
						      <td height="30">&nbsp;</td>
						      <td>&nbsp;</td>
						      <td><input type="submit" value="开始创建日报表"/></td>
						    </tr>
					</table>
			</s:form>
		</div>
		<div data-options="region:'center',border:false">

		</div>
	</div>
</body>
</html>