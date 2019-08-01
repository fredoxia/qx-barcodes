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
var baseurl = "<%=request.getContextPath()%>";
function cancelPrepaid(id, rowIndex){
	$('#dataGrid').datagrid('selectRow',rowIndex);
	if (confirm("你确认红冲当前预付款?")){
		var params = "formBean.vipPrepaid.id="+id;
		$.post("<%=request.getContextPath()%>/actionChain/chainVIPJSONAction!cancelVIPPrepaid",params, backProcessCancelPrepaid,"json");
		
	}
}
function backProcessCancelPrepaid(data){
	if (data.returnCode == SUCCESS)
		genSalesReport();
	alert(data.message);
}

$(document).ready(function(){
	parent.$.messager.progress('close'); 
	
	var params= $.serializeObject($('#preGenReportForm'));
	
	$('#dataGrid').datagrid({
		url : 'chainVIPJSONAction!searchVIPPrepaidFlow',
		queryParams: params,
		fit : true,
		fitColumns : true,
		pagination : true,
		pageSize : 15,
		pageList : [ 15, 30],
		rowStyler: function(index,row){
			var style = "";
			if (row.status == 1)
				style += 'color:red;';
			return style;
		},
		border : false,
		checkOnSelect : false,
		selectOnCheck : false,
		sortName : '',
		sortOrder : '',
		singleSelect:true,
		showFooter:true,
		rownumbers:true,
		nowrap : false,
		columns : [ [
					{field:'chainName', width:70,title:'连锁店',fixed:true,
						formatter: function (value, row, index){
							return row.chainStore.chain_name;
						}},
					{field:'vipNo', width:50,title:'VIP No.',
						formatter: function (value, row, index){
							return row.vipCard.vipCardNo;
						}},
					{field:'vipName', width:50,title:'VIP名字',
						formatter: function (value, row, index){
							return row.vipCard.customerName;
						}},						
					{field:'dateUI', width:40,title:'单据日期'},
					{field:'statusS', width:45,title:'状态'},
					{field:'prepaidType', width:45,title:'交易类别'},
					{field:'depositCash', width:45,title:'现金'},
					{field:'depositCard', width:45,title:'刷卡'},
					{field:'depositAlipay', width:45,title:'支付宝'},
					{field:'depositWechat', width:45,title:'微信'},	
					{field:'amt2', width:40,title:'赠送金额'},	
					{field:'consump', width:50,title:'消费 预存金额'},
					{field:'calculatedAmts', width:45,title:'账户增减'},
					{field:'createDate', width:60,title:'操作时间'},
					{field:'comment', width:50,title:'备注'},
					{					
						field : 'action',
						title : '红冲预存金充值',
						width : 70,
						formatter : function(value, row, index) {
							var str = '';
							if (row.status == 0 && row.id != 0 && row.operationType == 'D'){
								str += $.formatString('<a href="#" onclick="cancelPrepaid(\'{0}\',\'{1}\');"><img border="0" src="{2}" title="红冲"/></a>', row.id,index,'<%=request.getContextPath()%>/conf_files/easyUI/themes/icons/text_1.png');
							}
							
							return str;
						}
					}
			     ]]
	});
});
function changeChainStore(chainId){}
function genSalesReport(){
	var params = $.serializeObject($('#preGenReportForm')); 
	$('#dataGrid').datagrid('load',params); 
}

</script>
</head>
<body>
	<div class="easyui-layout" data-options="fit : true,border : false">
		<div data-options="region:'north',border:false" style="height: 125px;">
		   <s:form id="preGenReportForm" name="preGenReportForm" action="" theme="simple" method="POST">  
			<table width="100%" border="0">
			    <tr class="InnerTableContent">
			      <td width="45" height="25">&nbsp;</td>
			      <td width="76"><strong>单据日期</strong></td>
			      <td width="284" colspan="3">
			        <s:textfield id="startDate" name="formBean.startDate" cssClass="easyui-datebox"  data-options="width:100,editable:false"/>
			        &nbsp;至&nbsp;
			        <s:textfield id="endDate" name="formBean.endDate" cssClass="easyui-datebox"  data-options="width:100,editable:false"/>
			      </td>
			    </tr>
			    <tr class="InnerTableContent">
			      <td width="45" height="25">&nbsp;</td>
			      <td width="76"><strong>单据状态</strong></td>
			      <td width="284" colspan="3">
			        <s:select id="status" name="formBean.vipPrepaid.status"  list="#{0:'完成',1:'红冲'}" listKey="key" listValue="value"  headerKey="-1" headerValue="--所有状态--" />
			      </td>
			    </tr>			    
				<tr class="InnerTableContent">
			      <td height="30">&nbsp;</td>
			      <td><strong>连锁店</strong></td>
			      <td colspan="3"><%@ include file="../include/SearchChainStore.jsp"%>
			                      <input type="hidden" id="indicator" name="formBean.indicator" value="-1"/></td>
			    </tr>
                   <tr class="InnerTableContent">
			      <td height="25">&nbsp;</td>
			      <td>&nbsp;</td>
			      <td colspan="2"><input type="button" value="查询预存金数据" onclick="genSalesReport();"/>&nbsp;
			      </td>
			      <td>&nbsp;</td>
			    </tr>
			</table>
			</s:form>
		</div>
		<div data-options="region:'center',border:false">
			    <table id="dataGrid">			       
		        </table>
		</div>
	</div>					  

</body>
</html>