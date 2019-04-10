<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib prefix="s" uri="/struts-tags" %>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>千禧宝贝连锁店管理信息系统</title>
<%@ include file="../../common/Style.jsp"%>
<script>
$(document).ready(function(){
	parent.$.messager.progress('close'); 
});

$.extend($.fn.validatebox.defaults.rules, {
	notValue: {
        validator: function(value, param){
            return value != param[0];
        },
        message: '你输入的数字为 0, 请更正后输入'
    }
});
function save(){
    var chainId = $("#chainId").val();
    if (chainId == "" || chainId == 0){
		alert("连锁店是必选项");
		return ;
	}
    
    if (!$('#expenseForm').form('validate'))
    	return ;

	$.messager.progress({
		title : '提示',
		text : '数据处理中，请稍后....'
	});
	var params = $("#expenseForm").serialize();
	$.post("<%=request.getContextPath()%>/actionChain/expenseChainJSON!saveExpenseChain",params, backProcessSaveExpense,"json");
}

function backProcessSaveExpense(data){
	$.messager.progress('close'); 
    if (data.returnCode == SUCCESS){
        $.messager.alert('操作成功','消费记录创建成功',
        		setTimeout("goBack()",2000)
    	);

    } else 
       $.messager.alert('操作失败', data.message, 'error');
}
function goBack(){
	window.location.href = "actionChain/expenseChainJSP!preCreateExpenseChain";
}

function changeChainStore(chainId){
}
</script>
</head>
<body>

   <s:form action="" method="POST"  name="expenseForm" id="expenseForm"  cssClass="easyui-form" theme="simple"> 
   	<table width="95%" align="center"  class="OuterTable">
	    <tr><td>
		<table width="100%" border="0">
		    <tr class="PBAOuterTableTitale">
		       <td height="50" colspan="3">连锁店消费记录</td>
	        </tr>
		    <tr class="InnerTableContent">
		      <td>连锁店 *</td>
		      <td colspan="2">
		      	<%@ include file="../include/SearchChainStore.jsp"%>
		      	<s:hidden name="formBean.accessLevel" id="accessLevel" value="1"/>
		      </td>
		    </tr>
		    <tr class="InnerTableContent">
		      <td>费用日期*</td>
		      <td colspan="2">
		      	<s:textfield id="orderDate" name="formBean.expense.expenseDate" cssClass="easyui-datebox"  data-options="width:100,editable:false"/>
		      </td>
		    </tr>		    				                         
		    <tr class="InnerTableContent">
		      <td>费用类别 *</td>
		      <td colspan="2">
		      	<s:select name="formBean.expense.expenseType.id" id="expenseType" cssClass="easyui-combobox" data-options="width:100,editable:false" list="uiBean.expenseTypes" listKey="id" listValue="name"/>
		      </td>
		    </tr>	
		    <tr class="InnerTableContent">
		      <td>支付方式 *</td>
		      <td colspan="2">
		      	<s:select name="formBean.expense.feeType" id="feeType" cssClass="easyui-combobox" data-options="width:100,editable:false" list="#{1:'现金',2:'银行',3:'支付宝',4:'微信'}"  listKey="key" listValue="value" />
		      </td>
		    </tr>			        
		    <tr class="InnerTableContent">
		      <td>金额 *</td>
		      <td colspan="2">
		      	<s:textfield name="formBean.expense.amount" id="amount" cssClass="easyui-numberbox" style="width:100px;" data-options="validType:'notValue[0]',precision:1"/>
		      </td>
		    </tr>		
		    <tr class="InnerTableContent">
		      <td>备注</td>
		      <td colspan="2">
		      	<s:textfield name="formBean.expense.comment" id="comment" cssClass="easyui-validatebox" data-options="width:300,validType:'length[0,20]'"/>
		      </td>
		    </tr>			        
		    <tr class="InnerTableContent">
		      <td width="7%" height="30">
		      </td>
		      <td width="79%">
		        <a href="javascript:void(0)" class="easyui-linkbutton" onclick="save();">保存消费</a>
		      </td>
		      <td>&nbsp;</td>
		    </tr>
		  </table>
		  	   </td></tr>
	 </table>
	 </s:form>
</body>
</html>