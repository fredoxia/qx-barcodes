<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib prefix="s" uri="/struts-tags" %>
<html>
<head>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>千禧宝贝连锁店管理信息系统</title>
<%@ include file="../../common/Style.jsp"%>
<SCRIPT src="<%=request.getContextPath()%>/conf_files/js/datetimepicker_css.js" type=text/javascript></SCRIPT>

<script>
$(document).ready(function(){
	parent.$.messager.progress('close'); 
});
var functionPrefix = "function";
function saveOrUpdateFunction(){
	var userType = $("#userType").val();
	if (userType == 0){
		alert("请先选择用户类别");
		$("#userType").focus();
	} else {
		var value = $("#function10").is(':checked');
		if (!value || (value && confirm("你赋予了此用户类别<权限管理>，请确认!")) ){
		    var params=$("#userFunctionForm").serialize();  
		    $.post("chainUserJSON!saveRoleTypeFunctions",params, updateFunctionBackProcess,"json");	   
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

function changeUserType(){
	var userType = $("#userType").val();
	if (userType == 0){
		clearAllData();
	} else {
	    var params=$("#userFunctionForm").serialize();  
	    $.post("chainUserJSON!getUserTypeFunctions",params, getRoleTypeBackProcess,"json");	
	}
}
function getRoleTypeBackProcess(data){
    var functions = data.functions;
    
	clearAllData();
	
    if (functions != undefined && functions != ""){
	    if (functions.length != 0)
		    for (var i = 0; i < functions.length; i++){
		        var fun = functions[i].functionId;
		        $("#"+functionPrefix+fun).attr("checked",true);
		    }
    }
}
function clearAllData(){
	$("input[name='formBean.functions']").attr("checked",false);
}


</script>

</head>
<body>

<s:form id="userFunctionForm" name="formBean.userInfor" action="" method="POST" theme="simple">
 <table width="90%" align="center"  class="OuterTable">
 <tr><td>

 <table width="100%" border="0">
    <tr class="PBAOuterTableTitale">
       <td colspan="6" height="60" >连锁店用户权限管理<br/>
        - 系统管理员通过此功能修改每个用户种类的权限 (此功能仅系统管理员可以使用)
       </td>
    </tr>
    <tr class="InnerTableContent">
      <td height="19" colspan="2"><strong>现有连锁店用户类别：</strong>
        <s:select name="formBean.roleType.chainRoleTypeId" list="uiBean.chainRoleTypes" listKey="chainRoleTypeId" listValue="chainRoleTypeName"  headerKey="0" headerValue="-------"   id="userType" onchange="changeUserType();"/>
      </td>
      <td width="162">&nbsp;</td>
      <td width="192"></td>
      <td width="155">&nbsp;</td>
      <td width="344">      </td>
    </tr>
    <tr class="InnerTableContent">
      <td height="4" colspan="6"><hr width="100%" color="#FFCC00"/></td>
    </tr>
    <tr class="InnerTableContent">
      <td width="144" height="25"><strong>销售管理</strong></td>
      <td width="191">新建/修改/删除销售单据:
      <input type="checkbox" name="formBean.functions" id="function1" value="1"/></td>
      <td>搜索/查看:<input type="checkbox" name="formBean.functions" id="function2" value="2"/></td>
      <td>测试功能:<input type="checkbox" name="formBean.functions" id="function20" value="20"/></td>
      <td></td>
      <td></td>
    </tr>
    <tr class="InnerTableContent">
      <td height="25"><strong>采购管理</strong></td>
      <td>查看单据普通信息:<input type="checkbox" name="formBean.functions" id="function11" value="11"/></td>
      <td>查看单据明细:<input type="checkbox" name="formBean.functions" id="function3" value="3"/></td>
      <td>查看成本:<input type="checkbox" name="formBean.functions" id="function4" value="4"/></td>
      <td>流水帐查询:<input type="checkbox" name="formBean.functions" id="function18" value="18"/></td>
      <td></td>
    </tr>
    <tr class="InnerTableContent">
      <td height="25"><strong>调货管理:</strong></td>
      <td>新建/查询单据/流水
      <input type="checkbox" name="formBean.functions" id="function23" value="23"/></td>
      <td>&nbsp;</td>
      <td>&nbsp;</td>
      <td>&nbsp;</td>
      <td>&nbsp;</td>
    </tr>    
    <tr class="InnerTableContent">
      <td height="25"><strong>库存管理:</strong></td>
      <td>新建/修改/删除库存单据
      <input type="checkbox" name="formBean.functions" id="function5" value="5"/></td>
      <td>&nbsp;</td>
      <td>&nbsp;</td>
      <td>&nbsp;</td>
      <td>&nbsp;</td>
    </tr>
    <tr class="InnerTableContent">
      <td height="25"><strong>报表功能</strong> <br/></td>
      <td>销售报表:<input type="checkbox" name="formBean.functions" id="function12" value="12"/></td>
      <td>采购报表:<input type="checkbox" name="formBean.functions" id="function13" value="13"/></td>
      <td>财务报表:<input type="checkbox" name="formBean.functions" id="function14" value="14"/></td>
      <td>库存状况统计:<input type="checkbox" name="formBean.functions" id="function16" value="16"/></td>
      <td>商品销售统计:<input type="checkbox" name="formBean.functions" id="function17" value="17"/></td>
    </tr>
    <tr class="InnerTableContent">
      <td height="25"></td>
      <td>商品采购统计:<input type="checkbox" name="formBean.functions" id="function19" value="19"/></td>
      <td>综合报表:<input type="checkbox" name="formBean.functions" id="function22" value="22"/></td>
      <td></td>
      <td></td>
      <td></td>
    </tr>
    <tr class="InnerTableContent">
      <td height="25"><strong>VIP管理</strong></td>
      <td>VIP种类管理:<input type="checkbox" name="formBean.functions" id="function6" value="6"/></td>
      <td>VIP卡管理:<input type="checkbox" name="formBean.functions" id="function7" value="7"/></td>
      <td></td>
      <td></td>
      <td></td>
    </tr>
    <tr class="InnerTableContent">
      <td height="25"><strong>连锁店管理</strong></td>
      <td>员工信息管理:<input type="checkbox" name="formBean.functions" id="function8" value="8"/></td>
      <td>连锁店配置:<input type="checkbox" name="formBean.functions" id="function15" value="15"/></td>
      <td>允许修改零售价:<input type="checkbox" name="formBean.functions" id="function21" value="21"/></td>
      <td></td>
      <td></td>
    </tr>

    <tr class="InnerTableContent">
      <td height="25"><strong>系统管理员</strong> <br/>*此权限为总部管理人员</td>
      <td>连锁店管理权限:<input type="checkbox" name="formBean.functions" id="function9" value="9"/></td>
      <td>系统管理员权限:<input type="checkbox" name="formBean.functions" id="function10" value="10"/></td>
      <td></td>
      <td></td>
      <td></td>
    </tr>

    <tr class="InnerTableContent">
      <td height="30">&nbsp;</td>
      <td><s:if test="#session.LOGIN_CHAIN_USER.containFunction('chainUserJSON!saveRoleTypeFunctions')"><input type="button" value="更新" onclick="saveOrUpdateFunction();"/></s:if></td>
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