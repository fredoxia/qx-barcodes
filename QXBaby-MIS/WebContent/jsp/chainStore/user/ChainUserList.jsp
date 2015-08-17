<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib prefix="s" uri="/struts-tags" %>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>连锁员工信息管理</title>
<%@ include file="../../common/Style.jsp"%>
<link href="<%=request.getContextPath()%>/conf_files/css/pagination.css" rel="stylesheet" type="text/css"/>
<SCRIPT src="<%=request.getContextPath()%>/conf_files/js/pagenav1.1.js" type=text/javascript></SCRIPT>
<script>
$(document).ready(function(){
	parent.$.messager.progress('close'); 
});
function getChainUserInWindow(user_id){
	window.open ('chainUserJSP!getChainUserByIDForUpdate?formBean.chainUserInfor.user_id='+user_id,'更新连锁店用户信息','height=400, width=400, toolbar=no, menubar=no, scrollbars=no, resizable=no, location=no, status=no');
}

function addChainUser(){
	window.open ('chainUserJSP!preAddChainUser','更新连锁店用户信息','height=400, width=400, toolbar=no, menubar=no, scrollbars=no, resizable=no, location=no, status=no');
}

pageNav.fn = function(page,totalPage){                
	$("#currentPage").attr("value",page);
    document.chainUserForm.action="chainUserJSP!getChainUsers";
    document.chainUserForm.submit();
};

</script>
</head>
<body>
    <s:form action="/actionChain/chainUserJSP!getChainUsers" method="POST" name="chainUserForm" id="vipCardListForm" theme="simple"> 
    <%@ include file="../../common/pageForm.jsp"%>
	<table width="90%" align="center"  class="OuterTable">
	    <tr><td>
	
		 <table width="100%" border="0">
		    <tr class="PBAOuterTableTitale">
		       <td height="40" colspan="7">
	           - 连锁店负责人通过此功能增加/修改/删除连锁店员工信息</td>
		    </tr>
		    <tr class="InnerTableContent">
		      <td height="4" colspan="7"><s:actionmessage/> <hr width="100%" color="#FFCC00"/></td>
		    </tr>
		    <tr>
		      <td colspan="7">
		            <!-- table to display the staff information -->
					<table width="65%"  align="left" class="OuterTable" id="org_table">
					  <tr class="PBAInnerTableTitale" align='left'>
					    <th width="15" height="35">No.</th>
					    <th width="45">姓名</th>
					    <th width="85">所属连锁店</th>
					    <th width="85">手机号</th>
					    <th width="70">系统用户名</th>
					    <th width="80">职位</th>
					    <th width="60">是否离职</th>
					    <th width="35">修改</th>
					  </tr>
				      <s:iterator value="uiBean.chainUserInfors" status="st" id="chainUserInfor" >
						    <tr class="InnerTableContent" <s:if test="#st.even">style='background-color: rgb(255, 250, 208);'</s:if>>	      
						      <td height="25"><s:property value="formBean.pager.firstResult + #st.index + 1"/></td>
						      <td><s:property value="#chainUserInfor.name"/></td>
						      <td><s:property value="#chainUserInfor.myChainStore.chain_name"/></td>
						      <td><s:property value="#chainUserInfor.mobilePhone"/></td>
						      <td><s:property value="#chainUserInfor.user_name"/></td>
						      <td><s:property value="#chainUserInfor.roleType.chainRoleTypeName"/></td>
						      <td><s:if test="#chainUserInfor.resign == 1">离职</s:if><s:else>正常</s:else></td>
						      <td><s:if test="#session.LOGIN_CHAIN_USER.containFunction('chainUserJSP!getChainUserByIDForUpdate')"><a href="#" onclick="getChainUserInWindow(<s:property value="#chainUserInfor.user_id"/>);"><img src='<%=request.getContextPath()%>/conf_files/web-image/editor.gif' border='0'/></a></s:if></td>
						    </tr>
				       </s:iterator>	
				       <s:if test="uiBean.chainUserInfors.size == 0">
						    <tr class="InnerTableContent">	      
						      <td colspan="8">没有记录</td>
						     </tr>				       
				       </s:if><s:else>
						    <tr class="InnerTableContent">	      
						      <td colspan="8"><div id="pageNav"></div></td>
						    </tr>					       
				       </s:else>   
					</table>
		      </td>
		    </tr>
		    <tr class="InnerTableContent">
		      <td height="4" colspan="7"><hr width="100%" color="#FFCC00"/></td>
		    </tr>
		    <tr class="InnerTableContent">
		      <td height="30" colspan="7"><s:if test="#session.LOGIN_CHAIN_USER.containFunction('chainUserJSP!preAddChainUser')"><input type="button" value="添加用户" onClick="addChainUser();"/></s:if></td>
		    </tr>
		  </table>
	   </td></tr>
	 </table>
	 </s:form>
<script>
$(document).ready(function(){
	renderPaginationBar($("#currentPage").val(), $("#totalPage").val());
	});
</script>
</body>
</html>