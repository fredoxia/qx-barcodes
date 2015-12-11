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
pageNav.fn = function(page,totalPage){                
	$("#currentPage").attr("value",page);
    var params=$("#invenOrderSearchForm").serialize();  
    $.post("inventoryFlowJSONAction!searchInvenOrder",params, searchOrdersBackProcess,"json");	
};
function searchOrders(){
	resetSearchForm();
    pageNav.fn($("#currentPage").val(),$("#totalPage").val());
}
function searchOrdersBackProcess(data){
	var orders = data.orders;
	var pager = data.pager;
	
    $('#invenOrders tr').each(function () {                
        $(this).remove();
    });

    if (orders.length != 0){
	    for (var i = 0; i < orders.length; i++){
	    	var j = i+1;
	    	var bg = "";
	    	if ((i % 2) == 0)
	    		bg = "<%=Common_util.EVEN_ROW_BG_STYLE%>";
     	    var color = "";
    	    if (orders[i].status == 3)
    		    color =  "<%=Common_util.CANCEL_ROW_FONT_COLOR%>";
    		else if (orders[i].status == 1)
        		color =  "<%=Common_util.DRAFT_ROW_FONT_COLOR%>";
	        if (orders[i] != "")  {
			    var urlLink = "inventoryFlowJSPAction!loadOrder?formBean.flowOrder.id=" + orders[i].id;
		        var nodeTitle = orders[i].typeChainS;
		        $("<tr class='InnerTableContent' style='" + bg + color +"' align='center'><td height='25'>"+(j + pager.firstResult)+
		        		  "</td><td>"+orders[i].id+
		        		  "</td><td>"+orders[i].chainStore.chain_name+
				          "</td><td>"+orders[i].typeChainS+
				          "</td><td>"+orders[i].orderDate+
				          "</td><td>"+orders[i].comment+
				          "</td><td>"+orders[i].totalQuantity+
				          "</td><td>"+orders[i].statusS+
				          "</td><td><a href='#'  onclick='addTab3(\""+urlLink+"\",\""+nodeTitle+"\")'><img src='<%=request.getContextPath()%>/conf_files/web-image/editor.gif' border='0'/></a></td></tr>").appendTo("#invenOrders");
	        }
	    }
	    renderPaginationBar(pager.currentPage, pager.totalPage);

		$("#org_table tr").mouseover(function(){      
			$(this).addClass("over");}).mouseout(function(){    
			$(this).removeClass("over");}); 
    }else {
    	$("<tr class='InnerTableContent'"+ bg +" align='center'><td colspan=6><font color='red'>对应条件没有查询信息</font> </td></tr>").appendTo("#invenOrders");
    }

    $("#invenFlowOrderDiv").show();
}
function changeChainStore(chainId){

}
</script>
</head>
<body>
    <s:form id="invenOrderSearchForm" name="invenOrderSearchForm" action="/actionChain/inventoryFlowAction!searchInvenOrder" theme="simple" method="POST"> 
      <%@ include file="../../common/pageForm.jsp"%>
      <table width="90%" align="center"  class="OuterTable">
	    <tr><td>
			 <table width="100%" border="0">
			    <tr>
			       <td height="50" colspan="7">
			            <div class="errorAndmes"><s:actionerror cssStyle="color:red"/><s:actionmessage cssStyle="color:blue"/></div>
				   		<table width="100%" border="0">
						    <tr class="PBAOuterTableTitale">
					          <td height="30" colspan="6">搜索报溢/报损单据/库存单据/调货单</td>
						    </tr>
						    <tr class="InnerTableContent">
						      <td height="35">&nbsp;</td>
						      <td><strong>单据日期</strong></td>
						      <td>	
						         <s:textfield id="startDate"  name="formBean.searchStartTime" cssClass="easyui-datebox" data-options="width:100,editable:false"/>			      
					      			&nbsp; 至&nbsp;
					      		 <s:textfield id="endDate" name="formBean.searchEndTime" cssClass="easyui-datebox" data-options="width:100,editable:false"/>	
		                      </td>
		                      <td><strong>连锁店</strong></td>
						      <td><%@ include file="../include/SearchChainStore.jsp"%>
						      		<input type="hidden" id="indicator" name="formBean.indicator" value="-1"/>
						      		<input type="hidden" id="accessLevel" name="formBean.accessLevel" value="1"/>		
						      </td>
						      <td>&nbsp;</td>
						    </tr>
							<tr class="InnerTableContent">
						      <td width="50" height="35">&nbsp;</td>
						      <td width="78"><strong>单据种类</strong></td>
						      <td width="285"><s:select id="status" name="formBean.flowOrder.type"  list="uiBean.invenOrderTypes" listKey="key" listValue="value" headerKey="-1" headerValue="--所有种类--" /></td>
						      <td width="72"><strong>单据状态</strong></td>
						      <td width="393"><s:select id="chainSaler" name="formBean.flowOrder.status"  list="uiBean.invenOrderStatus" listKey="key" listValue="value" headerKey="-1" headerValue="--所有状态--" /></td>
						      <td width="193">&nbsp;</td>
						    </tr>
	
		                    <tr class="InnerTableContent">
						      <td height="35">&nbsp;</td>
						      <td>&nbsp;</td>
						      <td colspan="2"><input type="button" value="搜索单据" onclick="searchOrders();"/></td>
						      <td>&nbsp;</td>
						      <td>&nbsp;</td>
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
			         <div id="invenFlowOrderDiv" style="display: none">
						<table width="90%"  align="left" id="org_table">
						    <tr class="PBAInnerTableTitale">
						      <th width="40" height="32"></th>
						      <th width="40">单据号</th>
						      <th width="90">连锁店</th>
						      <th width="102">单据种类</th>
						 	  <th width="145">单据日期</th>
						 	  <th width="130">备注</th>
						      <th width="60">数量</th>
						      <th width="60">状态</th>
						      <th width="60">&nbsp;</th>
						    </tr>
						    <tbody id="invenOrders">
						   </tbody>
						   <tr class="InnerTableContent" id="pager">	      
						      	<td colspan="9"><div id="pageNav"></div></td>
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