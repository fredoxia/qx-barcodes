<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib prefix="s" uri="/struts-tags" %>
<html>
<body>
   <s:form id="vipUpdateForm" action="" theme="simple" method="POST"> 
	<table>
		    <tr>
			      <td height="40">VIP 卡号</td>
			      <td>
			            <s:hidden name="formBean.vipCard.id"/>
			      		<s:textfield name="formBean.vipCard.vipCardNo" readonly="true"/>
			      </td>
			</tr>
		    <tr>
			      <td height="40">调整积分</td>
			      <td><s:textfield name="formBean.vipScore" id="vipScore" cssClass="easyui-numberspinner" style="width:80px;" required="required" data-options=" increment:10,min:-5000,max:5000"/></td>
			</tr>
			<tr>
			      <td height="40">备注</td>
			      <td><s:textfield name="formBean.comment" id="comment" maxlength="15"/></td>
			</tr>

	</table>
	</s:form>
	</body>
</html>	