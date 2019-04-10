<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib prefix="s" uri="/struts-tags" %>

<script>
$.extend($.fn.validatebox.defaults.rules, {
	notValue: {
        validator: function(value, param){
            return value != param[0];
        },
        message: '你输入的数字为 0, 请更正后输入'
    }
});
function updateExpense(){
    if (!$('#expenseForm').form('validate'))
    	return ;

	$.messager.progress({
		title : '提示',
		text : '数据处理中，请稍后....'
	});
	var params = $("#expenseForm").serialize();
	
	$.post("<%=request.getContextPath()%>/actionChain/expenseChainJSON!updateExpenseChain",params, backProcessSaveExpense,"json");
}

function backProcessSaveExpense(data){
	$.messager.progress('close'); 
    if (data.returnCode == SUCCESS){
    	$.modalDialog.handler.dialog('close');
    	$('#dataGrid').datagrid("reload");
    } else 
       $.messager.alert('操作失败', data.message, 'error');
}

function changeChainStore(chainId){
}
</script>

   <s:form action="" method="POST"  name="expenseForm" id="expenseForm"  cssClass="easyui-form" theme="simple"> 
   	<table width="95%" align="center"  class="OuterTable">
	    <tr><td>
		<table width="100%" border="0">
		    <tr class="PBAOuterTableTitale">
		       <td height="50" colspan="3" >连锁店消费记录
		       <s:hidden name="formBean.expense.id"/>
		       </td>
	        </tr>
		    <tr class="InnerTableContent">
		      <td width="18%">连锁店 *</td>
		      <td colspan="2">
					<s:hidden name="formBean.chainStore.chain_id" id="chainId"/>
					<s:textfield name="formBean.chainStore.chain_name" id="chainName" size="18" readonly="true"/>
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
		        <a href="javascript:void(0)" class="easyui-linkbutton" onclick="updateExpense();">保存消费</a>
		      </td>
		      <td>&nbsp;</td>
		    </tr>
		  </table>
		  	   </td></tr>
	 </table>
	 </s:form>
