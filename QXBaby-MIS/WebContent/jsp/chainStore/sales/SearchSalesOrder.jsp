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

function searchOrders(){
	pageNav.clearPager();
    pageNav.fn($("#currentPage").val(),$("#totalPage").val());
}

function searchOrdersBackProcess(data){
	var response = data.response;
	
	var pager = data.pager;

    $('#salesOrders tr').each(function () {                
        $(this).remove();
    });

    if (response.returnCode == SUCCESS){
    	var result = response.returnValue;
    	var orders = result[0];
	    if (orders.length != 0){
		    //1.渲染结果
		    for (var i = 0; i < orders.length; i++){
		    	var j = i+1;
		    	var bg = "";
		    	if ((i % 2) == 0)
		    		bg = "<%=Common_util.EVEN_ROW_BG_STYLE%>";
	 	    	var color = "";
	 	    	var isVip = "";
	 	    	var nodeTitle = "连锁销售单据";
		    	if (orders[i].status == 3)
			    	color =  "<%=Common_util.CANCEL_ROW_FONT_COLOR%>";
			    else if (orders[i].status == 1){
				    color =  "<%=Common_util.DRAFT_ROW_FONT_COLOR%>";
				    nodeTitle = "新建零售单";
			    }

				if (orders[i].vipCard != null && orders[i].vipCard.id != undefined)
					isVip = orders[i].vipCard.vipCardNo;
		        if (orders[i] != "")  {
			        var urlLink = "chainSalesJSPAction!getSalesOrderById?formBean.chainSalesOrder.id=" + orders[i].id;
			          $("<tr class='InnerTableContent' style='" + bg + color +"' align='center'><td height='27'>"+ (j + pager.firstResult) +"</td><td>"+
			        		  orders[i].id+"</td><td>"+
			        		  orders[i].chainStore.chain_name+"</td><td>"+
					          orders[i].orderDate+"</td><td>"+
					          orders[i].orderCreateDate+"</td><td>"+
					          orders[i].statusS+"</td><td>"+
					          isVip+"</td><td>"+
					          orders[i].saler.name+"</td><td>"+
					          orders[i].totalQuantity+"</td><td>"+
					          orders[i].netAmount+"</td><td>"+
					          (orders[i].totalAmount - orders[i].netAmount).toFixed(2)+"</td><td>"+
					          orders[i].totalQuantityR+"</td><td>"+
					          orders[i].netAmountR+"</td><td>"+
					          orders[i].totalQuantityF +"</td><td>"+
					          orders[i].memo+"</td><td><s:if test="#session.LOGIN_CHAIN_USER.containFunction('chainSalesJSPAction!getSalesOrderById')"><a href='javascript:addTab3(\""+urlLink+"\",\""+nodeTitle+"\");'><img src='<%=request.getContextPath()%>/conf_files/web-image/editor.gif' border='0'/></a></s:if></td></tr>").appendTo("#salesOrders");
		        }
		    }
		    
		    //2.渲染总数
		    var totalResult = result[1];
	          $("<tr class='PBAInnerTableTitale' align='center'><td height='27' colspan='8'>总计</td><td>"+
	        		  totalResult.totalQuantity+"</td><td>"+
	        		  (totalResult.netAmount).toFixed(2)+"</td><td>"+
	        		  (totalResult.totalAmount - totalResult.netAmount).toFixed(2)+"</td><td>"+
	        		  totalResult.totalQuantityR+"</td><td>"+
	        		  (totalResult.netAmountR).toFixed(2)+"</td><td>"+
	        		  totalResult.totalQuantityF +"</td><td></td><td></td></tr>").appendTo("#salesOrders");
	    }else {
	    	$("<tr class='InnerTableContent'"+ bg +" align='center'><td colspan=13><font color='red'>对应条件没有查询信息</font> </td></tr>").appendTo("#salesOrders");
	    }

	    renderPaginationBar(pager.currentPage, pager.totalPage);
	    
	    $("#salesOrdersDIV").show();
	    
		$("#org_table tr").mouseover(function(){      
			$(this).addClass("over");}).mouseout(function(){    
			$(this).removeClass("over");});

    } else {
		alert("查询发生错误 : " + response.message + ". 请重新尝试")
    }

    $.messager.progress('close'); 
  }

pageNav.fn = function(page,totalPage){
	$.messager.progress({
		title : '提示',
		text : '数据处理中，请稍后....'
	});
	$("#currentPage").attr("value",page);
    var params=$("#SalesOrderSearchForm").serialize();  
    $.post("chainSalesJSONAction!searchSalesOrder",params, searchOrdersBackProcess,"json");	
};

/**
 * after change the chain store, we need change the user list and customer groups as well
 */
function changeChainStore(chainId){
	var params = "formBean.chainSalesOrder.chainStore.chain_id=" + chainId;
	$.post("chainSalesJSONAction!changeChainStore",params, backProcessChangeChainStore,"json");
}
function backProcessChangeChainStore(data){
	var chainUsers =  data.chainUsers;
	$("#chainSaler").empty();
	if (chainUsers.length != 0 ){
		$("#chainSaler").prepend("<option value='-1'>--所有人员--</option>");
		for (var i = 0; i < chainUsers.length; i++)
			   $("#chainSaler").append("<option value='"+chainUsers[i].user_id+"'>"+chainUsers[i].name+"</option>"); 
	}
}
</script>
</head>
<body>
   <s:form id="SalesOrderSearchForm" action="/actionChain/salesAction!searchOrders" theme="simple" method="POST"> 
     <input type="hidden" id="indicator" name="formBean.indicator" value="-1"/>
	 <input type="hidden" id="accessLevel" name="formBean.accessLevel" value="4"/>	
     <%@ include file="../../common/pageForm.jsp"%>
     <table width="98%" align="center"  class="OuterTable">
	    <tr><td>
			 <table width="100%" border="0">
			    <tr>
			       <td height="50" colspan="7">
					   	<table width="100%" border="0">
					    <tr class="InnerTableContent">
				          <td height="10" colspan="7"><s:actionerror cssStyle="color:red"/><s:actionmessage cssStyle="color:blue"/></td>
					    </tr>
					    <tr class="InnerTableContent">
					      <td width="45" height="30">&nbsp;</td>
					      <td width="76"><strong>单据日期</strong></td>
					      <td width="284">
					        <s:textfield id="startDate" name="formBean.search_Start_Time" cssClass="easyui-datebox" data-options="width:100,editable:false"/>			      
					      	&nbsp; 至&nbsp;
					      	<s:textfield id="endDate" name="formBean.search_End_Time" cssClass="easyui-datebox" data-options="width:100,editable:false"/>		
	                      </td>
	                      <td width="71"><strong>单据状态</strong></td>
					      <td width="140"><s:select id="status" name="formBean.chainSalesOrder.status"  list="uiBean.chainOrderStatus" listKey="key" listValue="value" headerKey="-1" headerValue="--所有状态--" /></td>
					      <td width="77"><strong>付款方式</strong></td>
					      <td><s:select id="chainOrderPay" name="formBean.chainOrderPay"  list="uiBean.chainOrderPay" listKey="key" listValue="value" headerKey="-1" headerValue="--所有付款方式--" /></td>
					    </tr>
						<tr class="InnerTableContent">
					      <td height="30">&nbsp;</td>
					      <td><strong>连锁店</strong></td>
					      <td><%@ include file="../include/SearchChainStore.jsp"%></td>
					      <td><strong>经手人</strong></td>
					      <td><s:select id="chainSaler" name="formBean.chainSalesOrder.saler.user_id"  list="uiBean.chainSalers" listKey="user_id" listValue="name" headerKey="-1" headerValue="--所有人员--" /></td>
					      <td></td>
					      <td></td>
					    </tr>
						<tr class="InnerTableContent">
					      <td height="30">&nbsp;</td>
					      <td><strong>包含货品</strong></td>
					      <td><%@ include file="../include/SearchProduct.jsp"%></td>
					      <td><strong>单据号</strong></td>
					      <td><s:textfield id="orderId" name="formBean.chainSalesOrder.id" cssClass="easyui-numberbox" value="0" data-options="min:0,precision:0" size="15"/></td>
						  <td></td>
					      <td></td>	    
					    </tr>					    
	                    <tr class="InnerTableContent">
					      <td height="25">&nbsp;</td>
					      <td>&nbsp;</td>
					      <td colspan="2"><s:if test="#session.LOGIN_CHAIN_USER.containFunction('chainSalesJSONAction!searchSalesOrder')"><input type="button" value="搜索单据" onclick="searchOrders();"/></s:if></td>
					      <td>&nbsp;</td>
					      <td>&nbsp;</td>
					      <td>&nbsp;</td>
					    </tr>
					  </table></td>
			    </tr>
			    <tr class="InnerTableContent">
			      <td height="4" colspan="7"><hr width="100%" color="#FFCC00"/></td>
			    </tr>
			    <tr>
			      <td colspan="7">
			         <!-- table to display the draft order information -->
			         <div id="salesOrdersDIV" style="display: none">
						<table width="100%"  align="left"  id="org_table">
						  <tr class="PBAInnerTableTitale" align="center">
						    <th width="20" height="35"></th>
						    <th width="45">单据号</th>
						    <th width="110">连锁店</th>
						    <th width="75">单据日期</th>
						    <th width="85">过账日期</th>
						    <th width="40">状态</th>
						    <th width="50">VIP</th>
						    <th width="70">经手人</th>
						    <th width="60">销售数量</th>
						    <th width="60">销售金额</th>
						    <th width="60">销售折扣</th>
						    <th width="60">退货数量</th>
						    <th width="60">退货金额</th>
						    <th width="60">赠品数量</th>
						    <th width="80">单据摘要</th>
						    <th width="25"></th>
						  </tr>
						  <tbody id="salesOrders">
						  </tbody>
						  <tr class="InnerTableContent" id="pager">	      
						      <td colspan="16"><div id="pageNav"></div></td>
						  </tr>					       
						</table>
					  </div>
			      </td>
			    </tr>
			  </table>
	   </td></tr>
	 </table>
	 </s:form>
<script>
$(document).ready(function(){
	<s:if test="formBean.initialSearch == true">
	      searchOrders();
	</s:if>
	parent.$.messager.progress('close'); 
});
</script>
</body>
</html>