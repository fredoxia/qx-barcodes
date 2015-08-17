<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib prefix="s" uri="/struts-tags" %>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>千禧宝贝连锁店管理信息系统</title>
<%@ include file="../../common/Style.jsp"%>

<script>
$(document).ready(function(){
	parent.$.messager.progress('close'); 
});
function upload(){
	parent.$.messager.progress({
		title : '提示',
		text : '数据处理中，请稍后....'
	});
    document.vipCardUploadForm.action="chainVIPJSPAction!uploadVIPs";
    document.vipCardUploadForm.submit();
}

function changeChainStore(chainId){
}
</script>
</head>
<body>
   <s:form action="/actionChain/chainVIPJSPAction!uploadVIPs" method="POST"  enctype="multipart/form-data" name="vipCardUploadForm" id="vipCardUploadForm" theme="simple"> 

	<table width="95%" align="center"  class="OuterTable">
	    <tr><td>
	
		 <table width="100%" border="0">
		    <tr class="PBAOuterTableTitale">
		       <td height="50" colspan="3">VIP卡信息上传<br />
	           - 管理员通过此功能批量上传VIP卡信息</td>
	        </tr>
		    <tr class="InnerTableContent">
		      <td height="4" colspan="3"><div class="errorAndmes"><s:actionerror cssStyle="color:red"/><s:actionmessage cssStyle="color:blue"/></div><hr width="100%" color="#FFCC00"/></td>
		    </tr>
		    <tr class="InnerTableContent">
		      <td>连锁店</td>
		      <td colspan="2">
		      <%@ include file="../include/SearchChainStore.jsp"%>
		      </td>
		    </tr>
		    <tr class="InnerTableContent">
	         <td height="40">VIP文件</td>
	         <td colspan="2"><input type="file" name="formBean.vips" id="vips"/></td>
		    </tr>
		    <tr class="InnerTableContent">
	         <td height="40"></td>
	         <td colspan="2">遇到相同VIP卡号更新信息<input type="checkbox" name="formBean.overWrite" value="true"/></td>
		    </tr>
		    <tr class="InnerTableContent">
		      <td width="7%" height="30">
		      </td>
		      <td width="79%">
		            <input type="button" value="上传" onclick="upload();" />
		      </td>
		      <td>&nbsp;</td>
		    </tr>
		  </table>
	   </td></tr>
	 </table>
	 </s:form>
</body>
</html>