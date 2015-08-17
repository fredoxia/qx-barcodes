<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib prefix="s" uri="/struts-tags" %>
<%@ page import="com.onlineMIS.ORM.entity.headQ.barcodeGentor.*" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<title>千禧宝贝连锁店管理信息系统</title>
<script>
function validateForm(){
	var color = $("#color").val();

	var error = "";
	if (color == "" || color.length > 4){
       error = error + "颜色长度必须是大于0小于4\n";
	}
	if (error != ""){
	       alert(error);
	       return false;
	}
	return true;	
}
</script>
</head>
<body>
    <%@ include file="../../../common/Style.jsp"%>
    <table width="90%" align="center"  class="OuterTable">
    <tr><td>
        <s:form id="updateColorForm" name="updateColorForm" method="post" action="action/basicData!saveUpdateColor" theme="simple" onsubmit="return validateForm();">
	    <table width="100%" border="0">
	       <tr class="PBAOuterTableTitale">
	          <td colspan="2">新建/更新颜色信息</td>
	       </tr>
	       <tr>
	          <td colspan="2"><font color="red"><s:fielderror/></font><s:actionmessage/>
	          </td>
	       </tr>
	       <tr class="InnerTableContent" style="background-color: rgb(255, 250, 208);">
	          <td>颜色序号:</td><td><s:textfield name="formBean.color.colorId" readonly="true"/></td>
	       </tr>
	       <tr class="InnerTableContent">
	          <td>颜色    :</td><td><s:textfield id="color" name="formBean.color.color" length="4"/>*</td>
	       </tr>
	       <tr class="InnerTableContent" style="background-color: rgb(255, 250, 208);">
	          <td colspan="2"><input type="submit" value="新建/修改"/><input type="button" value="取消" onclick="window.close();"/></td>
	       </tr>
	    </table>
	    </s:form>
	    </td>
	</tr>
	</table>


	      
</body>
</html>