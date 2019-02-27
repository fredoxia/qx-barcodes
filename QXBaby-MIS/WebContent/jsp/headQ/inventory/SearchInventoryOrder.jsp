<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib prefix="s" uri="/struts-tags" %>

<%@ page import="com.onlineMIS.ORM.entity.headQ.user.*,java.util.Date,java.text.SimpleDateFormat" %>
<html>
<head>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>查询仓库单据</title>
<%@ include file="../../common/Style.jsp"%>

<script>

function BSkeyDown(e){

	 var ieKey = event.keyCode;

	 if (ieKey==13){
	   if (event.srcElement.id == "clientName"){
		   searchCustomer(); 
		   event.returnValue=false;
	   }
	 }  
} 

document.onkeydown = BSkeyDown; 

$(document).ready(function(){
	parent.$.messager.progress('close'); 

	var params = $.serializeObject($('#inventorySearchForm'));
	
	$('#dataGrid').datagrid({
		url : 'inventoryOrderJSON!search',
		queryParams: params,
		fit : true,
		border : false,
		checkOnSelect : false,
		selectOnCheck : false,
		singleSelect:true,
		nowrap : false,
		rowStyler: function(index,row){
			var style = "";
			if (row.status == 9)
				style += 'color:blue;';
			else if (row.status == 5)
				style += 'color:red;';

			if (row.orderTypeI == 1)
				style += 'background-color:#F9F8FA;';
			return style;
		},
		columns : [ [ {
			field : 'id',
			title : '编号',
			width : 50
		}, {
			field : 'clientName',
			title : '客户名称',
			width : 120
				
		}, {
			field : 'startTime',
			title : '开始时间',
			width : 125
		}, {
			field : 'completeTime',
			title : '过账时间',
			width : 125
		}, {
			field : 'PDAUserName',
			title : 'PDA录入',
			width : 60
		}, {
			field : 'keeperName',
			title : '仓库录入',
			width : 60
		}, {
			field : 'auditorName',
			title : '审核人员',
			width : 60
		}, {
			field : 'importTimes',
			title : '导精算',
			width : 50
		}, {
			field : 'totalQ',
			title : '总数',
			width : 60
		}, {
			field : 'totalWholeSales',
			title : '批发总额',
			width : 80
		}, {
			field : 'comment',
			title : '备注',
			width : 100
		}, {
			field : 'process',
			title : '单据进程',
			width : 70
		}, {
			field : 'orderType',
			title : '单据类型',
			width : 70
		}, {					
			field : 'action',
			title : '查看/修改',
			width : 70,
			formatter : function(value, row, index) {
				var str = '';
				if (row.isAuthorizedToEdit == true){
					var url = "<%=request.getContextPath()%>/action/inventoryOrder!loadOrder?formBean.order.order_ID=" + row.id;
					str += $.formatString('<a href="#" onclick="addTab6(\'{0}\',\'{1}\');"><img border="0" src="{2}" title="修改"/></a>', url, '单据 ' + row.id,'<%=request.getContextPath()%>/conf_files/easyUI/themes/icons/text_1.png');
				}
				
				return str;
			}
		}, {					
			field : 'action2',
			title : '权限转移',
			width : 150,
			formatter : function(value, row, index) {
				var str = '';
				if (row.isAuthorizedToTransfer == true){
					var dropDown = "<select id='transferTo"+row.id+"'>";
					<s:iterator value="uiBean.users" status = "st" id="user" >
					   dropDown +="<option value='<s:property value="#user.user_id"/>'><s:property value="#user.user_name"/></option>";
					</s:iterator>
					dropDown +="</select>";
					
					str += $.formatString(dropDown + ' <input type="button" value="转移单据" onclick="transferOrderToOther(\'{0}\',\'{1}\');"/>', row.id);
				}
				
				return str;}
		}
		]]
	});
});
function transferOrderToOther(orderId){
	var transferToUserId = $("#transferTo"+orderId).val();
	var params = "formBean.order.order_ID=" + orderId + "&formBean.user.user_id="+ transferToUserId;
	var url = "<%=request.getContextPath()%>/action/inventoryOrderJSON!transferOrderToOther";

	$.post(url,params, transferBk,"json");
	
}
function transferBk(data){
	var returnCode = data.returnCode;
	if (returnCode == SUCCESS){
		$.messager.alert('操作成功', "成功转移单据,即刻刷新单据", 'info');
		searchOrder();
	} else {
		$.messager.alert('操作失败', data.message, 'error');
	}
}
function searchOrder(){
	var params = $.serializeObject($('#inventorySearchForm')); 
	$('#dataGrid').datagrid('load',params); 
}
</script>

</head>
<body>
 	<div class="easyui-layout"  data-options="fit : true,border : false">
		<div data-options="region:'north',border:false" style="height: 175px;">
		<s:form id="inventorySearchForm" action="/action/inventoryOrder!search" theme="simple" method="POST">
		 <table width="100%" border="0">
		    <tr class="InnerTableContent">
		      <td height="19"><strong>客户名字：</strong></td>
		      <td colspan="3"><%@ include file="../include/ClientInput.jsp"%></td>

		      <td><strong>单据号：</strong></td>
		      <td><input name="formBean.order.order_ID" id="order_Status"  onkeypress="return is_number(event);" size="7"/></td>
		    </tr>	   
		    <tr class="InnerTableContent">
		      <td height="4" colspan="6"><hr width="100%" color="#FFCC00"/></td>
		    </tr>
		    <tr class="InnerTableContent">
		      <td width="95" height="32"><strong>单据种类：</strong></td>
		      <td width="100"><s:select name="formBean.order.order_type" id="order_type"  list="uiBean.orderTypeMap" listKey="key" listValue="value" headerKey="-1" headerValue="---全部---" /></td>
		      <td width="65"><strong>状态：</strong></td>
		      <td width="95"><s:select name="formBean.order.order_Status" id="order_Status"  list="uiBean.orderStatusMap" listKey="key" listValue="value" headerKey="-1" headerValue="---全部---" />      </td>
		      <td width="90"><strong>录入会计：</strong></td>
		      <td><s:select name="formBean.order.order_Auditor.user_id" id="accountant"  list="uiBean.users" listKey="user_id" listValue="user_name" headerKey="-1" headerValue="---全部---" />      </td>
		    </tr>
		
		    <tr class="InnerTableContent">
		      <td height="19"><strong>仓库开始时间：</strong></td>
		      <td colspan="5">
		        <s:textfield id="startDate" name="formBean.search_Start_Time" cssClass="easyui-datebox"  data-options="width:100,editable:false"/> 
		        &nbsp;至 &nbsp;
		        <s:textfield id="endDate" name="formBean.search_End_Time" cssClass="easyui-datebox"  data-options="width:100,editable:false"/></td>
      		</tr>

		    <tr class="InnerTableContent">
		      <td height="5" colspan="6"><hr width="100%" color="#FFCC00"/></td>
		    </tr>
		    <tr class="InnerTableContent">
		      <td height="30">&nbsp;</td>
		      <td><input type="button" onclick="searchOrder();" value="查询销售单据"/> </td>
		      <td>&nbsp;</td>
		      <td>&nbsp;</td>
		      <td>&nbsp;</td>
		      <td>&nbsp;</td>
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