<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib prefix="s" uri="/struts-tags" %>

<html>
<head>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>系统运行日志</title>
<%@ include file="../../common/Style.jsp"%>
<script>
$(document).ready(function(){
	parent.$.messager.progress('close'); 
});
</script>

</head>
<body>

 <table width="90%" align="center"  class="OuterTable">
 <tr><td>

 <table width="100%" border="0">
    <tr class="PBAOuterTableTitale">
       <td colspan="6">日志</td>
    </tr>
    <tr>
       <td colspan="6" height="6"><s:fielderror/></td>
    </tr>
    <tr class="InnerTableContent">
      <td width="187" height="19"><strong>文件名</strong></td>
      <td width="100"><strong>文件大小</strong></td>
      <td width="88"><strong>读取</strong></td>
      <td width="25"></td>
      <td width="90">&nbsp;</td>
      <td width="392">
      </td>
    </tr>
    <s:iterator value="#request.SYSTEM_LOG_LIST" id = "log">
	    <tr class="InnerTableContent">
	      <td  height="19"><s:property value="#log.fileName"/></td>
	      <td><s:property value="#log.fileSize"/></td>
	      <td><a href="<%=request.getContextPath()%>/action/userJSP!downloadLog?fileName=<s:property value="#log.fileName"/>"><img src="<%=request.getContextPath()%>/conf_files/web-image/editor.gif" border="0"/></a></td>
	      <td></td>
	      <td>&nbsp;</td>
	      <td></td>
	    </tr>
    </s:iterator>
  </table>
  </td></tr>
 </table>


</body>
</html>