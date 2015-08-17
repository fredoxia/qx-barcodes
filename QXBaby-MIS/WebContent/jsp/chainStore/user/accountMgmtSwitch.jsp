<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib prefix="s" uri="/struts-tags" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>多账户关联管理</title>
<%@ include file="../../common/Style.jsp"%>
<script>

</script>
</head>
<body>
<%@ include file="../include/Header.jsp"%>

   <s:form id="chainUserAcctForm" action="/actionChain/chainUserJSP!connectAcct" method="POST" theme="simple" >
   <table width="90%" align="center"  class="OuterTable">
    <tr><td>

	 <table width="100%" border="0">
	    <tr class="PBAOuterTableTitale">
	       <td height="78" colspan="6">1. 多账户关联管理<br />
           - 如果用户具有多个连锁店（连锁店账户），为方便用户多账户之间切换本功能可以将多个账户关联。然后只需要通过切换功能就能多账户之间切换，而无需重新登录。<br/>
           </td>
	    </tr>
	    <tr class="InnerTableContent">
	      <td width="57" height="32">
	         <strong>账户名</strong></td>
	      <td width="122"><input type="text" name="formBean.chainUserInfor.user_name"/></td>
	      <td width="19"></td>
	      <td width="186"></td>
	      <td width="495">&nbsp;</td>
	      <td width="195">&nbsp;</td>
	    </tr>
	    <tr class="InnerTableContent">
	      <td width="57" height="32">
	         <strong>密码</strong></td>
	      <td width="122"><input type="password" name="formBean.chainUserInfor.password"/></td>
	      <td width="19"></td>
	      <td width="186"></td>
	      <td width="495">&nbsp;</td>
	      <td width="195">&nbsp;</td>
	    </tr>
	    <tr class="InnerTableContent">
	      <td height="4" colspan="6"><hr width="100%" color="#FFCC00"/></td>
	    </tr>
	    <tr class="InnerTableContent">
	      <td height="30" colspan="6">
	            <input type="button" value="创建关联" onclick="connectAcct();"/>
	            <input type="button" value="取消关联" onclick="disconnectAcct();"/>
	      </td>
	    </tr>
	  </table>
   </td></tr>
 </table>
</s:form>
<br/>
   <table width="90%" align="center"  class="OuterTable">
    <tr><td>
	 <table width="100%" border="0">
	    <tr class="PBAOuterTableTitale">
	       <td height="78" colspan="6">本账户已经关联的其他账户</td>
	    </tr>
	    <tr class="InnerTableContent">
	      <td width="57" height="32">账户名</td>
	      <td width="122">姓名</td>
	      <td width="19">手机</td>
	      <td width="186">连锁店名字</td>
	      <td width="495">&nbsp;</td>
	      <td width="195">&nbsp;</td>
	    </tr>
	    <tr class="InnerTableContent">
	      <td width="57" height="32"></td>
	      <td width="122"></td>
	      <td width="19"></td>
	      <td width="186"></td>
	      <td width="495">&nbsp;</td>
	      <td width="195">&nbsp;</td>
	    </tr>
	  </table>
   </td></tr>
 </table>

</body>
</html>