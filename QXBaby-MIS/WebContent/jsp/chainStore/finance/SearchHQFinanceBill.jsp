<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib prefix="s" uri="/struts-tags" %>
<%@ page import="com.onlineMIS.common.Common_util,java.util.Date,java.text.SimpleDateFormat" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>千禧宝贝连锁店管理信息系统</title>
<%@ include file="../../common/Style.jsp"%>
<link href="<%=request.getContextPath()%>/conf_files/css/pagination.css" rel="stylesheet" type="text/css"/>
<SCRIPT src="<%=request.getContextPath()%>/conf_files/js/pagenav1.1.js" type=text/javascript></SCRIPT>
<script>
$(document).ready(function(){
	parent.$.messager.progress('close'); 
});
function getFinanceDetail(id){
	window.open ('financeChainJSP!getFHQ?formBean.financeBill.id='+id,'财务单据','height=500, width=600, toolbar=no,scrollbars=yes, resizable=yes,  menubar=no, location=no, status=no');
}
pageNav.fn = function(page,totalPage){                
	$("#currentPage").attr("value",page);
    var params=$("#financeBillSearchForm").serialize();  
    $.post("financeChainJSON!searchFHQBill",params, searchBillsBackProcess,"json");	
};
function searchBills(){
	resetSearchForm();
    pageNav.fn($("#currentPage").val(),$("#totalPage").val());
}
function changeChainStore(chainId){}
function searchBillsBackProcess(data){
	var bills = data.bills;
	var pager = data.pager;

    $('#bills tr').each(function () {                
        $(this).remove();
    });

    if (bills.length != 0){
	    for (var i = 0; i < bills.length; i++){
	    	var j = i+1;
	    	var bg = "";
	    	if ((i % 2) == 0)
	    		bg = "<%=Common_util.EVEN_ROW_BG_STYLE%>";
	        if (bills[i] != "")  
		          $("<tr class='InnerTableContent'  style='" + bg +"' align='center'><td>"+
				          bills[i].id+"</td><td>"+
				          bills[i].createDate+"</td><td>"+
				          bills[i].chainStore.chain_name+"</td><td>"+
				          bills[i].typeChainS+"</td><td>"+ 
				          bills[i].invoiceTotal+"</td><td>"+
				          bills[i].comment+"</td><td><s:if test="#session.LOGIN_CHAIN_USER.containFunction('financeChainJSP!getFHQ')"><a href='#'  onclick = 'getFinanceDetail(" + bills[i].id+ ");'><img src='<%=request.getContextPath()%>/conf_files/web-image/editor.gif' border='0'/></a></s:if></td></tr>").appendTo("#bills");
	    }
	    renderPaginationBar(pager.currentPage, pager.totalPage);
	    
		$("#org_table tr").mouseover(function(){      
			$(this).addClass("over");}).mouseout(function(){    
			$(this).removeClass("over");}); 
    }else {
    	$("<tr class='InnerTableContent'"+ bg +" align='center'><td colspan=7><font color='red'>对应条件没有查询信息</font> </td></tr>").appendTo("#bills");
    }

    $("#billsDiv").show();
}
</script>
</head>
<body>

    <s:form id="financeBillSearchForm" name="financeBillSearchForm" action="" theme="simple" method="POST"> 
     <%@ include file="../../common/pageForm.jsp"%>
     <table width="90%" align="center"  class="OuterTable">
	    <tr><td>
			 <table width="100%" border="0">
			    <tr>
			       <td height="50" colspan="7">
			            <div class="errorAndmes"><s:actionerror cssStyle="color:red"/><s:actionmessage cssStyle="color:blue"/></div>
				   		<table width="100%" border="0">
						    <tr class="PBAOuterTableTitale">
					          <td height="64" colspan="7">搜索财务单据<br />
				         		- 连锁客户通过此功能可以查看与总部之间的往来财务单据</td>
						    </tr>
						    
						    <tr class="InnerTableContent">
						      <td width="40" height="25">&nbsp;</td>
						      <td width="82"><strong>连锁店</strong></td>
						      <td width="220"><%@ include file="../include/SearchChainStore.jsp"%>
						      <input type="hidden" id="indicator" name="formBean.indicator" value="-1"/>
						      <input type="hidden" id="accessLevel" name="formBean.accessLevel" value="1"/>	
						      <td width="82"><strong>单据种类</strong></td>
						      <td width="165"><s:select name="formBean.financeBill.type"  list="formBean.financeBill.typeChainMap" listKey="key" listValue="value" headerKey="-1" headerValue="---全部---" /></td>
						      <td>&nbsp;</td>
						    </tr>
							<tr class="InnerTableContent">
						      <td height="40">&nbsp;</td>
						      <td><strong>单据日期</strong></td>
						      <td colspan="3">
						         <s:textfield id="startDate" name="formBean.searchStartTime" cssClass="easyui-datebox" data-options="width:100,editable:false"/>			      
					      			&nbsp; 至&nbsp;
					      		 <s:textfield id="endDate" name="formBean.searchEndTime" cssClass="easyui-datebox" data-options="width:100,editable:false"/>	
						      </td>
						    </tr>
		                    <tr class="InnerTableContent">
						      <td height="15">&nbsp;</td>
						      <td>&nbsp;</td>
						      <td colspan="3"><s:if test="#session.LOGIN_CHAIN_USER.containFunction('financeChainJSON!searchFHQBill')"><input type="button" value="搜索单据" onclick="searchBills();"/></s:if></td>
						    </tr>
						  </table>
					 </td>
			    </tr>
			    <tr class="InnerTableContent">
			      <td height="4" colspan="7"><hr width="100%" color="#FFCC00"/></td>
			    </tr>
			    <tr>
			      <td colspan="7">
			         <!-- table to display the draft order information -->
			         <div id="billsDiv" style="display: none">
						<table width="90%"  align="left" id="org_table">
						    <tr class="PBAInnerTableTitale">
						      <th width="40" height="32">编号</th>
						      <th width="155">过账日期</th>
						      <th width="110">连锁店</th>
						      <th width="102">单据种类</th>
						      <th width="70">金额</th>
						      <th width="140">备注</th>
						      <th width="60"></th>
						    </tr>
						    <tbody id="bills">
						   </tbody>
						   <tr class="InnerTableContent" id="pager">	      
						      <td colspan="8"><div id="pageNav"></div></td>
						   </tr>	
						</table>
					  </div>
			      </td>
			    </tr>
			  </table>
	   </td></tr>
	 </table>
	 </s:form>

</body>
</html>