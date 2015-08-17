<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib prefix="s" uri="/struts-tags" %>
<%@ page import="com.onlineMIS.common.Common_util" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>千禧宝贝连锁店管理信息系统</title>
<script>

</script>
</head>
<body>
    <%@ include file="../include/Header.jsp"%>
    
    <table width="90%" align="center"  class="OuterTable">
	    <tr><td>
			 <table width="100%" border="0">
			    <tr>
			       <td height="50" colspan="7">
				   	 <table width="100%" border="0">
				       <tr class="PBAOuterTableTitale">
				         <td height="32">连锁店<br/>           </td>
				         <td>&nbsp;</td>
				         <td>编号</td>
				         <td height="32">&nbsp;</td>
				       </tr>
				       <tr class="InnerTableContent">
				         <td width="112" height="32">日期</td>
				         <td width="385"></td>
				         <td width="50"></td>
				         <td width="155"></td>
			           </tr>
				     </table></td>
			    </tr>
			    <tr class="InnerTableContent">
			      <td height="4" colspan="7"><s:actionmessage/> <hr width="100%" color="#FFCC00"/></td>
			    </tr>
			    <tr>
			      <td colspan="7">
			            <!-- table to display the staff information -->
						<table width="45%"  align="left" class="OuterTable" id="org_table">
						  <tr class="PBAInnerTableTitale" align='left'>
						    <th width="35" height="35">姓名</th>
						    <th width="85">所属连锁店</th>
						    <th width="85">手机号</th>
						    <th width="70">系统用户名</th>
						    <th width="70">系统负责人</th>
						    <th width="60">是否离职</th>
						    <th width="25">修改</th>
						  </tr>
					      <s:iterator value="uiBean.chainUserInfors" status="st" id="chainUserInfor" >
							    <tr class="InnerTableContent" <s:if test="#st.even"><%=Common_util.EVEN_ROW_BG_STYLE %></s:if>>	      
							      <td height="25"><s:property value="#chainUserInfor.name"/></td>
							      <td><s:property value="#chainUserInfor.myChainStore.chain_name"/></td>
							      <td><s:property value="#chainUserInfor.mobilePhone"/></td>
							      <td><s:property value="#chainUserInfor.user_name"/></td>
							      <td><s:if test="#chainUserInfor.roleType == 9">是</s:if><s:else>否</s:else></td>
							      <td><s:if test="#chainUserInfor.resign == 1">离职</s:if><s:else>正常</s:else></td>
							      <td><a href="#" onclick="getChainUserInWindow(<s:property value="#chainUserInfor.user_id"/>);"><img src='<%=request.getContextPath()%>/conf_files/web-image/editor.gif' border='0'/></a></td>
							    </tr>
					       </s:iterator>	  
						</table>
			      </td>
			    </tr>
			    <tr class="InnerTableContent">
			      <td height="4" colspan="7"><hr width="100%" color="#FFCC00"/></td>
			    </tr>
			    <tr class="InnerTableContent">
			      <td height="30" colspan="7"><input type="button" value="添加用户" onClick="addChainUser();"/></td>
			    </tr>
			  </table>
	   </td></tr>
	 </table>
</body>
</html>