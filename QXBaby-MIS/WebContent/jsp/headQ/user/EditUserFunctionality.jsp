<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib prefix="s" uri="/struts-tags" %>
<html>
<head>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>员工权限管理</title>
<%@ include file="../../common/Style.jsp"%>
<script>
$(document).ready(function(){
	parent.$.messager.progress('close'); 
});
var functionPrefix = "function";
function saveOrUpdateFunction(){
	var userID = $("#user_id").val();
	if (userID == 0){
		alert("请先选择员工姓名");
		$("#user_id").focus();
	} else {
		var value = $("#function90").is(':checked');
		if (!value || (value && confirm("你赋予了此员工<权限管理>，请确认!")) ){
		    var params=$("#userInforForm").serialize();  
		    $.post("userJSON!saveUserFunctions",params, updateFunctionBackProcess,"json");	   
		}
	}
    
}

function updateFunctionBackProcess(data){
	var user = data.isSuccess;
	if (user == true)
		alert("更新成功");
	else
		alert("更新失败");	
}

function submitUser(){
	var userID = $("#user_id").val();
	if (userID == 0){
		clearAllData();
	} else {
	    var params=$("#userInforForm").serialize();  

	    $.post("userJSON!getUser",params, getUserBackProcess,"json");	
	}
}
function getUserBackProcess(data){
    var user = data.user;
    if (user != ""){
    	$("#name").attr("value",user.name);
    	var departmentCode = user.department;
    	var departSlect = $("#department");

    	var count=$("#department option").length;

    	for(var i=0;i<count;i++){     
    	    if(departSlect.get(0).options[i].value == departmentCode)  {  
    	    	    departSlect.get(0).options[i].selected = true;  
    	            break;  
    	    }  
    	}
    	$("#jobTitle").attr("value",user.jobTitle);
    	
    	var functions = user.userFunction_Set;	
    	clearAllFunctionData();
    	
	    if (functions.length != 0)
		    for (var i = 0; i < functions.length; i++){
		        var fun = functions[i].function_id;
		        $("#"+functionPrefix+fun).attr("checked",true);
		    }
    }else {
    	alert("获取信息发生错误!");
    }
}
function clearAllData(){
	clearAllFunctionData();
	$("#name").attr("value","");
	$("#jobTitle").attr("value","");
	$("#department").attr("value","");
}

function clearAllFunctionData(){
	$("input[name='functionalities']").attr("checked",false);
}



//if the manageAll is enable, the manage mine should be enabled as well
function changeFunction13(){
	var value = $("#function13").is(':checked');
	
	if (value){
		$("#function14").attr("checked",true);
	}	
}

//if the manage mine is disabled, the manage all should be disabled as well
function changeFunction14(){
	var value = $("#function14").is(':checked');
	
	if (!value){
		$("#function13").attr("checked",false);
	}	
}
</script>

</head>
<body>

<s:form id="userInforForm" name="formBean.userInfor" action="" method="POST">
 <table width="90%" align="center"  class="OuterTable">
 <tr><td>

 <table width="100%" border="0">
    <tr class="PBAOuterTableTitale">
       <td colspan="6">员工信息管理</td>
    </tr>
    <tr class="InnerTableContent">
      <td height="19" colspan="2"><strong>现有员工：</strong>
        <select name="formBean.userInfor.user_id" size="1" id="user_id" onchange="submitUser();">
           <option value="0">---------</option>
           <s:iterator value="#request.ALL_USER" id = "user">
             <option value="<s:property value="#user.user_id"/>"><s:property value="#user.user_name"/></option>
           </s:iterator>
       </select>      </td>
      <td width="162">&nbsp;</td>
      <td width="192"></td>
      <td width="155">&nbsp;</td>
      <td width="344">      </td>
    </tr>
    <tr class="InnerTableContent">
      <td height="4" colspan="6">&nbsp;</td>
    </tr>
    <tr class="InnerTableContent">
      <td height="19" colspan="2"><strong>员工姓名：</strong>
      <input type="text" name="formBean.userInfor.name" id="name"  disabled="true" />      </td>
      <td><strong>部门：
        <select name="formBean.userInfor.department"  size="1" id="department"  disabled="disabled" >
          <option value="0">---------</option>
          <option value="01">会计部</option>
          <option value="02">销售部</option>
          <option value="03">物流部</option>
        </select>
        </strong></td>
      <td height="19" colspan="2"><strong>职位：
        <input type="text" name="formBean.userInfor.jobTitle" id="jobTitle" disabled="disabled" />
      </strong></td>
      <td>&nbsp;</td>
    </tr>
    <tr class="InnerTableContent">
      <td height="4" colspan="6"><hr width="100%" color="#FFCC00"/></td>
    </tr>
    <tr class="InnerTableContent">
      <td width="144" height="19"><strong>条形码管理权限</strong></td>
      <td width="191">基础资料管理:
      <input type="checkbox" name="functionalities" id="function1" value="1"/></td>
      <td>新建条形码:<input type="checkbox" name="functionalities" id="function2" value="2"/></td>
      <td>查询条形码:<input type="checkbox" name="functionalities" id="function3" value="3"/></td>
      <td>修改条形码:<input type="checkbox" name="functionalities" id="function4" value="4"/></td>
      <td>同步检查条码:<input type="checkbox" name="functionalities" id="function5" value="5"/></td>
    </tr>
    <tr class="InnerTableContent">
      <td height="4" colspan="6"><hr width="100%" color="#FFCC00"/></td>
    </tr>
    <tr class="InnerTableContent">
      <td height="19"><strong>人力资源管理权限</strong></td>
      <td>员工信息管理:<input type="checkbox" name="functionalities" id="function10" value="10"/></td>
      <td>绩效考核配置:<input type="checkbox" name="functionalities" id="function11" value="11"/></td>
      <td>创建员工考核管理:<input type="checkbox" name="functionalities" id="function12" value="12"/></td>
      <td>管理自己员工考核:<input type="checkbox" name="functionalities" id="function14" value="14" onclick="changeFunction14();"/></td>
      <td>管理所有员工考核:<input type="checkbox" name="functionalities" id="function13" value="13" onclick="changeFunction13();"/></td>
    </tr>
    <tr class="InnerTableContent">
      <td height="4">&nbsp;</td>
      <td height="4">查看员工考核月报表:
      <input type="checkbox" name="functionalities" id="function15" value="15"/></td>
      <td height="4">&nbsp;</td>
      <td height="4">&nbsp;</td>
      <td height="4">&nbsp;</td>
      <td height="4">&nbsp;</td>
    </tr>
    <tr class="InnerTableContent">
      <td height="4" colspan="6"><hr width="100%" color="#FFCC00"/></td>
    </tr>
    <tr class="InnerTableContent">
      <td height="19"><strong>单据管理权限</strong></td>
      <td>PDA单据录入:<input type="checkbox" name="functionalities" id="function24" value="24"/></td>
      <td>仓库单据录入:<input type="checkbox" name="functionalities" id="function21" value="21"/></td>
      <td>搜索单据:<input type="checkbox" name="functionalities" id="function22" value="22"/></td>
      <td>会计修改单据:<input type="checkbox" name="functionalities" id="function23" value="23"/></td>
      <td></td>
    </tr>
    <tr class="InnerTableContent">
      <td height="19"></td>
      <td>删除单据:<input type="checkbox" name="functionalities" id="function26" value="26"/></td>
      <td>导入精算:<input type="checkbox" name="functionalities" id="function27" value="27"/></td>
      <td>财务单据管理:<input type="checkbox" name="functionalities" id="function16" value="16"/></td>
      <td>连锁店流水:<input type="checkbox" name="functionalities" id="function17" value="17"/></td>
      <td>会计审核单据:<input type="checkbox" name="functionalities" id="function28" value="28"/></td>
    </tr>
    <tr class="InnerTableContent">
      <td height="4" colspan="6"><hr width="100%" color="#FFCC00"/></td>
    </tr>
    <tr class="InnerTableContent">
      <td height="19"><strong>连锁管理权限</strong></td>
      <td>销售报表:<input type="checkbox" name="functionalities" id="function31" value="31"/></td>
      <td>连锁店关联:<input type="checkbox" name="functionalities" id="function93" value="93"/></td>
      <td>连锁店起初库存:<input type="checkbox" name="functionalities" id="function94" value="94"/></td>
      <td></td>
      <td></td>
    </tr>
    <tr class="InnerTableContent">
      <td height="4" colspan="6"><hr width="100%" color="#FFCC00"/></td>
    </tr>
    <tr class="InnerTableContent">
      <td height="19"><strong>管理员权限</strong></td>
      <td>总部用户权限管理:<input type="checkbox" name="functionalities" id="function90" value="90"/></td>
      <td>连锁系统管理员:<input type="checkbox" name="functionalities" id="function91" value="91"/></td>
      <td>连锁经理:<input type="checkbox" name="functionalities" id="function92" value="92"/></td>
      <td>品牌价格调整:<input type="checkbox" name="functionalities" id="function32" value="32"/></td>
      <td></td>
    </tr>
    <tr class="InnerTableContent">
      <td height="30">&nbsp;</td>
      <td><input type="button" value="更新" onclick="saveOrUpdateFunction();"/></td>
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