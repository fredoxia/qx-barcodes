<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib prefix="s" uri="/struts-tags" %>
<%@ page import="com.onlineMIS.common.Common_util,java.util.Date,java.text.SimpleDateFormat" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>千禧宝贝连锁店管理信息系统</title>
<%@ include file="../../common/Style.jsp"%>

<script>
$(document).ready(function(){
	parent.$.messager.progress('close'); 

	var params = $.serializeObject($('#vipSearchForm')); 
	$('#dataGrid').datagrid({
		url : 'chainVIPJSONAction!searchSpecialVIPs',
		queryParams: params,
		fit : true,
		fitColumns : true,
		pagination : true,
		pageSize : 15,
		pageList : [ 15, 30],
		border : false,
		checkOnSelect : false,
		selectOnCheck : false,
		singleSelect:true,
		rownumbers:true,
		nowrap : false,
		columns : [ [ {
			field : 'vipCardNo',
			title : 'VIP卡号',
			width : 40
		}, {
			field : 'quarter',
			title : '类型',
			width : 35,
			formatter: function (value, row, index){
				return row.vipType.vipTypeName;
			}		
		}, {
			field : 'statusS',
			title : '状态',
			width : 40
		}, {
			field : 'chain_name',
			title : '发卡连锁店',
			width : 60,
			formatter: function (value, row, index){
				return row.issueChainStore.chain_name;
			}
		}, {
			field : 'cardIssueDate',
			title : '发卡日期',
			width : 60
		}, {
			field : 'customerName',
			title : '持有者',
			width : 60
		}, {
			field : 'customerBirthday',
			title : '卡主生日',
			width : 80
		}, {
			field : 'genderS',
			title : '性别',
			width : 60
		}, {
			field : 'telephone',
			title : '电话',
			width : 60
		}]]
	});
});

function searchVIPs(){
	var params = $.serializeObject($('#vipSearchForm')); 
	$('#dataGrid').datagrid('load',params); 
}
</script>
</head>
<body>
 	<div class="easyui-layout"  data-options="fit : true,border : false">
		<div data-options="region:'north',border:false" style="height: 125px;">
		  <s:form id="vipSearchForm" action="" method="POST" theme="simple">
		    <!--<s:hidden name="formBean.isAdmin" id="isAdmin"/>-->
    		<table width="65%" border="0">
				    <tr class="InnerTableContent">
				      <td height="30" width="80"><strong>连锁店</strong></td>
				      <td><s:select id="chainStoreId" name="formBean.chainStore.chain_id"  list="uiBean.chainStores"  listKey="chain_id" listValue="chain_name"/></td>
				    </tr>    		
				    <tr class="InnerTableContent">
				      <td height="30"><strong>查询条件</strong> </td>
				      <td><s:select name="formBean.searchType" size="1" id="searchType"  list="uiBean.searchTypes" listKey="key" listValue="value"/></td>
				    </tr>

                    <tr class="InnerTableContent">
				      <td></td>
				      <td><input type="button" value="按条件搜索VIP" onclick="searchVIPs();"/></td>
				    </tr>
			</table>
		   </s:form>
		</div>
		<div data-options="region:'center',border:false">
			<table id="dataGrid" border="0">			       
		    </table>
		</div>
	</div>

</body>
</html>