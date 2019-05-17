<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib prefix="s" uri="/struts-tags" %>
<html>
<body>
   <s:form id="vipPasswordUpdateForm" action="" theme="simple" method="POST" cssClass="easyui-form"> 
	<table>
		    <tr>
			      <td height="40">VIP 卡号</td>
			      <td>
			            <s:hidden name="formBean.vipCard.id"/>
			      		<s:textfield name="formBean.vipCard.vipCardNo" readonly="true"/>
			      </td>
			</tr>
		    <tr>
			      <td height="40">密码</td>
			      <td><s:password name="formBean.vipCard.password" id="password" cssClass="easyui-validatebox"  style="width:80px;" data-options="required:true" /></td>
			</tr>

	</table>
	</s:form>
	</body>
</html>	