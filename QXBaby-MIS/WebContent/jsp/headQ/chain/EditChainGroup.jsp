<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib prefix="s" uri="/struts-tags" %>

<%@ page import="java.util.Date,java.text.SimpleDateFormat" %>
<html>
<head>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>千禧宝贝连锁店管理信息系统</title>
<%@ include file="../../common/Style.jsp"%>
<script>
$(document).ready(function(){
	parent.$.messager.progress('close'); 
});

/**
 * 当下来菜单改变时
 */
function changeChainGroup(){
	var chainGroupId = $("#chainGroup").val();
	if (chainGroupId > 0){
		parent.$.messager.progress({
			title : '提示',
			text : '数据处理中，请稍后....'
		});
		var params="formBean.chainGroup.id=" + chainGroupId;
		$.post("<%=request.getContextPath()%>/action/chainSMgmtJSON!getChainGroup",params, backProcessGetChainGroup,"json");
	} else {
		$("input[name='formBean.chainStoreIds']").attr("checked",false);
		$("#chainGroupName").attr("value","");
		$("#chainGroupName").removeAttr("readonly");
	}
}
function backProcessGetChainGroup(data){
	parent.$.messager.progress('close'); 
	var response = data.response;
	var returnCode = response.returnCode;
	if (returnCode != SUCCESS){
		alert(response.message);
	} else {
		var chainGroup = response.returnValue;
		$("#chainGroupName").attr("value",chainGroup.groupName);
		$("#chainGroupName").attr("readonly","readonly");
		$("input[name='formBean.chainStoreIds']").attr("checked",false);
		var chainEles = chainGroup.chainStoreGroupElementSet;
		for (var i = 0; i < chainEles.length; i++){
	        var chainId = chainEles[i].chainId;
	        $("#chain"+chainId).attr("checked",true);
		}
	}
	
}

/**
 * 修改chain group
 */
function updateChainGroup(){
	if (validateUpdateChainGroup()){
		parent.$.messager.progress({
			title : '提示',
			text : '数据处理中，请稍后....'
		});
		var params= $("#editChainGroupForm").serialize();  ;
		$.post("<%=request.getContextPath()%>/action/chainSMgmtJSON!updateChainGroup",params, backProcessUpdateChainGroup,"json");
	}
}
function backProcessUpdateChainGroup(data){
	parent.$.messager.progress('close'); 
	var response = data.response;
	var returnCode = response.returnCode;
	if (returnCode == SUCCESS){
		var action = response.action;
		if (action == ACTION_ADD){
			alert("成功添加");
			location.reload();
		} else 
			alert("成功更新");	
	} else {
		alert(response.message);
	}
}
function validateUpdateChainGroup(){
	var chainGroupName = $.trim($("#chainGroupName").val());
	if (chainGroupName == "" ){
		alert("关联名称 不能为空");
		return false;
	} else {
		var numOfChain = $("input[name='formBean.chainStoreIds']:checked").length;
		if (numOfChain < 2){
			alert("至少要关联两个连锁店");
			return false;
		} else {
			return true;
		}
	}
}
/**
 * 删除chain group
 */
function deleteChainGroup(){
	var chainGroupId = $("#chainGroup").val();
	if (chainGroupId == 0){
		alert("未保存的连锁关联不能删除");
	} else {
		var confirmMsg = "你确定要删除这个连锁店关联?";
		if (confirm(confirmMsg)){
		   var params="formBean.chainGroup.id=" + chainGroupId;
		   $.post("<%=request.getContextPath()%>/action/chainSMgmtJSON!deleteChainGroup",params, backProcessDeleteChainGroup,"json");
	    }
    }
}
function backProcessDeleteChainGroup(data){
	var response = data.response;
	var returnCode = response.returnCode;
	if (returnCode == SUCCESS){
		alert("成功删除");
		location.reload();
	} else {
		alert(response.message);
	}
}
</script>
</head>
<body>
<s:form id="editChainGroupForm" name="editChainGroupForm" action="/action/chainSMgmtJSON!editChainGroup" method="POST" theme="simple" target="_blank" onsubmit="" >
  <table width="90%" align="center"  class="OuterTable">
    <tr><td>
		 	 <table width="100%" border="0">
			    <tr class="PBAOuterTableTitale">
			       <td height="38" colspan="2">连锁店关联信息更新      </td>
			    </tr>
			   	<tr class="InnerTableContent">
			       <td height="58" colspan="2">- 比如关联某人的多个连锁店,方便以后调货等操作
			                                   <br/> - 目前一个连锁店只能允许存在于一个关联中，否者系统会提示错误
			       </td>
			    </tr>
			    <tr class="InnerTableContent">
			      <td width="77" height="32">
			         <strong>关联名称</strong></td>
			      <td width="826"><s:select id="chainGroup" name="formBean.chainGroup.id"  list="uiBean.chainGroups" listKey="id" listValue="groupName" headerKey="0" headerValue="---新建---" onChange="changeChainGroup();"/></td>
		        </tr>
		       	<tr class="InnerTableContent">
			      <td width="77" height="32">
			         <strong>新建关联名称</strong></td>
			      <td width="826"><s:textfield id="chainGroupName" name="formBean.chainGroup.groupName" maxlength="15"/></td>
		        </tr>
				<tr class="InnerTableContent">
			      <td height="32" colspan="2">
			         <strong>选择连锁店</strong>
			         <table width="100%" border="0" cellspacing="0" cellpadding="0">
		               <s:iterator value="uiBean.chainStores" status="st" id="chainStore" >
		                  <s:if test="(#st.index % 5) == 0"><tr></s:if>
		                         <td><input type="checkbox" id="chain<s:property value="#chainStore.chain_id"/>" name="formBean.chainStoreIds" value="<s:property value="#chainStore.chain_id"/>"/><s:property value="#chainStore.chain_name"/></td>
		                  <s:if test="((#st.index+1) % 5) == 0 && (#st.index != 0)"></tr></s:if>
		               </s:iterator>
		             </table>
		           </td>
		        </tr>
			    <tr class="InnerTableContent">
			      <td height="30" colspan="2"><input type="button" value="提交关联" onclick="updateChainGroup();"/><input type="button" value="删除当前关联" onclick="deleteChainGroup();"/></td>
			    </tr>
			</table>
   		</td>
   </tr>

 </table>
</s:form>

</body>
</html>