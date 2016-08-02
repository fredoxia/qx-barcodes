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
});
function generateReport(){
	$.messager.progress({
		title : '提示',
		text : '数据处理中，请稍后....'
	});

    var params=$("#preGenReportForm").serialize();  
    var url = "financeHQJSON!generateFinanceReport";

    $.post(url,params, generateReportBackProcess,"json");	
}

function generateReportBackProcess(data){
    $('#reportTable tr').each(function () {                
        $(this).remove();
    });
    
	var report = data.report;

	if (report != undefined){
		showFinanceReport(report);
	}

	$("#report").show();
	$.messager.progress('close'); 
}


function showFinanceReport(report){
	$("<tr class='PBAInnerTableTitale'>"+
       "<td height='20' width='200'>账目名称</td>"+
	   "<td>净付</td>"+
	   "</tr>").appendTo("#reportTable");
	
	var items = report.reportItems;
	
	for (var i = 0; i < items.length; i++){
      $("<tr class='InnerTableContent'><td>"+
    		items[i].category.itemName +"</td><td>"+
	          (items[i].amount).toFixed(2) +"</td></tr>").appendTo("#reportTable");
	}
}
function changeChainStore(chainId){

}
</script>
</head>
<body>
   <s:form id="preGenReportForm" action="" theme="simple" method="POST"> 
     <table width="90%" align="center"  class="OuterTable">
	    <tr><td>
			 <table width="100%" border="0">
			    <tr>
			       <td height="50" colspan="7">
					   	<table width="100%" border="0">
					    <tr class="InnerTableContent">
					      <td width="45" height="35">&nbsp;</td>
					      <td width="76"><strong>开始截止日期</strong></td>
					      <td width="284" colspan="2">
			      	 			<s:textfield id="startDate" name="formBean.searchStartTime" cssClass="easyui-datebox"  data-options="width:100,editable:false"/>			      
			      					&nbsp; 至&nbsp;
			         			<s:textfield id="endDate" name="formBean.searchEndTime" cssClass="easyui-datebox"  data-options="width:100,editable:false"/>	
				  		  </td>
					      <td></td>
					    </tr>
						<tr class="InnerTableContent">
					      <td height="35">&nbsp;</td>
					      <td><strong>连锁店</strong></td>
					      <td><%@ include file="../include/SearchChainStore.jsp"%>
					           <input type="hidden" id="isAll" name="formBean.isAll" value="1"/>
	 							<input type="hidden" id="indicator" name="formBean.indicator" value="0"/>
					      </td>
					      <td></td>
					      <td></td>
					    </tr>
	                    <tr class="InnerTableContent">
					      <td height="35">&nbsp;</td>
					      <td>&nbsp;</td>
					      <td colspan="2"><input type="button" value="生成报表" onclick="generateReport();"/></td>
					      <td>&nbsp;</td>
					    </tr>
					  </table></td>
			    </tr>
	   			<tr>
                    <td>
            	      <div id="report" style="display:none">
            	         <table width="70%" border="0" id="reportTable">

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