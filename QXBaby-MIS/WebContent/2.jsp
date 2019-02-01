<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@ include file="jsp/common/Style.jsp"%>
<title>千禧宝贝管理信息系统</title>	
<!-- 连锁店登录页面 -->
<script>
$(document).ready(function(){
	parent.$.messager.progress('close'); 

	if(!$.browser.msie && !($.browser.mozilla && $.browser.version == '11.0')) { 

		$.messager.alert('提示',"请使用电脑自带的Internet Explorer作为访问系统的浏览器，否则会出现小票无法打印等错误。\n\n修改默认浏览器设置或者联系总部技术支持(夏林 QQ13763339)","info"); 
	}
	$.ajaxSetup( {    
		timeout : 20000
	} );
	 
});
function login(){
    var params=$("#loginForm").serialize(); 
    if (validateLoginForm())
        $.post("<%=request.getContextPath()%>/actionChain/chainUserJSON!login",params, loginBackProcess,"json");
}
function loginBackProcess(data){
	var response = data.response;
	var returnCode = response.returnCode;
	if (returnCode != SUCCESS){
		$.messager.alert('错误提示',response.message,'warning');
	} else {
		//window.open ('<%=request.getContextPath()%>/actionChain/chainUserJSP!getNews', '千禧宝贝管理系统', 'height=100, width=400, top=0, left=0, toolbar=no, menubar=no, scrollbars=no, resizable=yes,location=no, status=no, fullscreen=yes');    
		window.location.href = "<%=request.getContextPath()%>/actionChain/chainUserJSP!prepareUIAfterLogin";
	}
}
function clear(){
	$("#userName").attr("value","");
	$("#password").attr("value","");
	
}
</script>
</head>
<body>

<div id="loginDialog" class="easyui-dialog" title="千禧宝贝连锁店管理系统" data-options="iconCls:'icon-status_online',resizable:false,modal:true,draggable:false,closable:false,buttons:[{text:'登陆',handler:function(){ login(); }},{text:'清除',handler:function(){ clear(); }}]" style="width:320px;height:220px;padding:10px">
	  <s:form id="loginForm" name="loginForm" method="post" action="/actionChain/chainUserJSON!login" onsubmit="return validateLoginForm();" theme="simple">
			  <table width="100%">
			    <tr>
			      <td colspan="2" align="center"><strong>连锁店用户登录</strong></td>
			    </tr>
			    <tr>
			      <td colspan="2" align="left"><div class="errorAndmes"><s:fielderror cssStyle="color:red"/><s:actionerror cssStyle="color:red"/></div></td>
			    </tr>
			    <tr>
			      <td height="30">用户名：</td>
			      <td><s:textfield name="formBean.chainUserInfor.user_name" id="userName" cssClass="easyui-validatebox" data-options="required:true"/></td>
			    </tr>
			    <tr>
			      <td height="30">密码：</td>
			      <td><s:password name="formBean.chainUserInfor.password" id="password" cssClass="easyui-validatebox" data-options="required:true"/>
		      </td>
			    </tr>
			  </table>
		</s:form>
</div>

</body>
</html>