<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@ include file="jsp/common/Style.jsp"%>
<title>千禧宝贝管理信息系统 ab</title>
<script>
function login(typeOfRole){
	
    if (typeOfRole == 1)
	    window.location.href = 'indexHeadQ.jsp';
	else if (typeOfRole == 0)
	    window.location.href = 'indexChainS.jsp';
	else if (typeOfRole == 2)
	    window.location.href = 'indexHeadQPDA.jsp';
	else if (typeOfRole == 3)
	    window.location.href = 'index4Chain.jsp';
}
</script>
</head>
<body>
<table width="100%" height="181" border="0" align="center" cellspacing="0" >
  <tr>
    <td></td>
    <td>&nbsp;</td>
    <td></td>
  </tr>
  <tr class="PBAOuterTableTitale">
    <td height="36" align="left" valign="top"></td>
    <td align="left" valign="middle">请选择你登陆千禧宝贝系统的端口</td>
    <td align="left" valign="middle"></td>
  </tr>
  <tr class="PBAOuterTableTitale">
    <td width="20%" height="36" align="left" valign="top"></td>
     <td width="35%" align="left" valign="middle">
       <label>
       <input type="button" name="Submit" value="公司总部" onClick="login(1);">
        </label>
       <label>
       <input type="button" name="Submit2" value="连锁客户" onClick="login(0);">
       </label>
       <label>
       <input type="button" name="Submit2" value="连锁客户制作条码" onClick="login(3);">
       </label>       
       <label>
       <input type="button" name="Submit2" value="总部PDA登陆" onClick="login(2);">
       </label>
     </td>
     <td width="45%" align="left" valign="middle"></td>
  </tr>
 </table>


</body>
</html>
