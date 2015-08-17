<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib prefix="s" uri="/struts-tags" %>
<html>
<head>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>我的账户信息</title>
<%@ include file="../../common/Style.jsp"%>
<SCRIPT src="<%=request.getContextPath()%>/conf_files/js/datetimepicker_css.js" type=text/javascript></SCRIPT>
<script>
$(document).ready(function(){
	parent.$.messager.progress('close'); 
});
function updateInformation(){
	if (validate()){
		var params=$("#userInforForm").serialize();  
		$.post("userJSON!updateMyAcct",params, updateBackProcess,"json");	
	}
}

function updateBackProcess(data){
	var error = data.error;

	if (error != undefined)
		alert("错误 : " + error);
	else{

		var msg = data.success;
		alert(msg);
	    $("#passwordU").attr("value","");
	    $("#password2").attr("value","");
	    $("#password3").attr("value","");
	}
}

function validate(){
	var error = "";

	var homephone = $("#homePhone").val();
	if (homephone != "" && isNaN(homephone)){
		error +="住宅电话 - 包含了非数字信息\n";
	    $("#homePhone").focus();
    } else if ($("#homePhone").val().length > 14){
    	error +="住宅电话 - 超过最长14位\n";
    }		

	var mobilePhone = $("#mobilePhone").val();
    if (mobilePhone == ""){
		error +="手机 - 不能为空\n";
		$("#mobilePhone").focus();
    } else if (isNaN(mobilePhone)){
		error +="手机 - 包含了非数字信息\n";
	    $("#mobilePhone").focus();
    }else if ($("#mobilePhone").val().length > 11){
    	error +="手机 - 超过最长11位\n";
    }	
	
	var idNumber = $("#idNumber").val();
	if ($("#idNumber").val() != "" && isNaN(idNumber)){
		error +="身份证 - 包含了非数字信息\n";
	    $("#idNumber").focus();
    } else if ($("#idNumber").val().length > 18){
    	error +="身份证 - 超过最长18位\n";
    }

	if ($("#password2").val() != $("#password3").val()){
		error +="两次输入新密码不一致\n";
	} else if ($("#password2").val().length > 8){
    	error +="新密码 - 超过最长8位\n";
    } else {
        if ($("#password2").val() != "" && $("#passwordU").val() == ""){
        	error +="修改密码前，请输入旧密码\n";
        }
	}
    
	if (error == "")
		return true;
	else{
		alert(error);
		return false;
	}
		
}
</script>

</head>
<body>

<s:form id="userInforForm" name="userInforForm" action="" method="POST" theme="simple">
 <table width="90%" align="center"  class="OuterTable">
 <tr><td>

 <table width="100%" border="0">
    <tr class="PBAOuterTableTitale">
       <td colspan="6">我的账户信息</td>
    </tr>
    <tr>
       <td colspan="6" height="6"><s:fielderror/></td>
    </tr>
    <tr class="InnerTableContent">
      <td height="4" colspan="6"><hr width="100%" color="#FFCC00"/></td>
    </tr>
    <tr class="InnerTableContent">
      <td width="99" height="19"><strong>员工姓名：</strong></td>
      <td width="163">
         <s:textfield name="formBean.userInfor.name" id="name" disabled="true"  />      </td>
      <td width="87"><strong>部门：</strong></td>
      <td width="176"><s:textfield name="formBean.userInfor.departmentName" id="departmentName" disabled="true"  /></td>
      <td width="102"><strong>入职时间：</strong></td>
      <td width="447">
        <s:textfield id="onBoardDate" disabled="true" name="formBean.userInfor.onBoardDate">
             <s:param name="value"><s:date name="formBean.vipCard.cardIssueDate" format="yyyy-MM-dd" /></s:param>
        </s:textfield></td>
    </tr>
        <tr class="InnerTableContent">
      <td height="4" colspan="6"><hr width="100%" color="#FFCC00"/></td>
    </tr>
        <tr class="InnerTableContent">
      <td height="19"><strong>职位：</strong></td>
      <td>
        <s:textfield name="formBean.userInfor.jobTitle" id="jobTitle" disabled="true" />      </td>
      <td><strong>基本工资：</strong></td>
      <td>
        <s:textfield name="formBean.userInfor.baseSalary" id="baseSalary" disabled="true" />      </td>
      <td><strong>基本假期：</strong></td>
      <td>
        <s:textfield name="formBean.userInfor.baseVacation" id="baseVacation" disabled="true" />      </td>
    </tr>
   <tr class="InnerTableContent">
      <td height="4" colspan="6"><hr width="100%" color="#FFCC00"/></td>
    </tr>
    <tr class="InnerTableContent">
      <td height="19"><strong>住宅电话：</strong></td>
      <td>
      <s:textfield name="formBean.userInfor.homePhone" id="homePhone" />      </td>
      <td><strong>手机号：</strong></td>
      <td> 
         <s:textfield name="formBean.userInfor.mobilePhone" id="mobilePhone" />      </td>
      <td><strong>生日：</strong></td>
      <td>
        <s:textfield id="birthday" readonly="true" name="formBean.userInfor.birthday">
              <s:param name="value"><s:date name="formBean.userInfor.birthday" format="yyyy-MM-dd" /></s:param>
        </s:textfield> 
		<a href="javascript:NewCssCal('birthday','yyyymmdd','arrow')">
			<img src="<%=request.getContextPath()%>/conf_files/web-image/cal.gif" width="16" height="16" alt="Pick a date" border="0">		</a>      </td>
    </tr>



    <tr class="InnerTableContent">
      <td height="4" colspan="6"><hr width="100%" color="#FFCC00"/></td>
    </tr>
        <tr class="InnerTableContent">
      <td height="19"><strong>身份证：</strong></td>
      <td>
        <s:textfield name="formBean.userInfor.idNumber" id="idNumber" />      </td>
      <td><strong>用户名：</strong>      </td>
      <td>
         <s:textfield name="formBean.userInfor.user_name" id="user_name" disabled="true"/>      </td>
      <td><strong>用户ID：</strong></td>
      <td><s:property value="formBean.userInfor.user_id"/></td>
    </tr>
    <tr class="InnerTableContent">
      <td height="4" colspan="6"><hr width="100%" color="#FFCC00"/></td>
    </tr>
        <tr class="InnerTableContent">
      <td height="80"><strong>系统密码：</strong></td>
      <td colspan="2"><p>旧密码: 
        <s:password name="formBean.userInfor.password" id="passwordU" />
        <br />
        <br />
       	 新密码:
        <s:password name="formBean.password2" id="password2" />
        <br />
        	新密码:
        <s:password name="formBean.password3" id="password3" />
        <p/>* 如果需要更新密码，请输入旧密码和新密码
      </p>        </td>
      <td></td>
      <td></td>
      <td></td>
    </tr>
    <tr class="InnerTableContent">
      <td height="4" colspan="6"><hr width="100%" color="#FFCC00"/></td>
    </tr>
    <tr class="InnerTableContent">
      <td height="30">&nbsp;</td>
      <td><input type="button" value="更新" onclick="updateInformation();"/></td>
      <td>&nbsp;</td>
      <td>&nbsp;</td>
      <td>&nbsp;</td>
      <td>&nbsp;</td>
    </tr>
    <tr class="InnerTableContent">
      <td height="2" colspan="6"></td>
    </tr>
  </table>
  </td></tr>
 </table>
</s:form>

</body>
</html>