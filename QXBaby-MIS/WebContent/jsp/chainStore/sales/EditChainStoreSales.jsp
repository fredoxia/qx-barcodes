<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib prefix="s" uri="/struts-tags" %>
<%@ page import="com.onlineMIS.common.Common_util" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=8" />
<title>千禧宝贝连锁店管理信息系统</title>
<%@ include file="../../common/Style.jsp"%>
<script type="text/javascript" src="<%=request.getContextPath()%>/conf_files/js/ChainSales.js?v=10.12"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/conf_files/js/ChainSalesKeys.js?v=8.4"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/conf_files/js/HtmlTable.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/conf_files/js/print/print.js?v=10.12"></script>

<script>
var baseurl = "<%=request.getContextPath()%>";

index = parseInt("<s:property value='formBean.chainSalesOrder.productList.size()'/>");
indexR = parseInt("<s:property value='formBean.chainSalesOrder.productListR.size()'/>");
indexF = parseInt("<s:property value='formBean.chainSalesOrder.productListF.size()'/>");
$(document).ready(function(){
	//准备某些框的focus状态
	$("#extralVipScore").focus(function(){
		  $("#extralVipScore").select();
	});
	$("#vipScore").focus(function(){
		  $("#vipScore").select();
	});
	$("#coupon").focus(function(){
		  $("#coupon").select();
	});
	$("#discountAmount").focus(function(){
		  $("#discountAmount").select();
	});
	$("#cardAmount").focus(function(){
		  $("#cardAmount").select();
	});
	$("#cashAmount").focus(function(){
		  $("#cashAmount").select();
	});
	$("#chainPrepaidAmt").focus(function(){
		  $("#chainPrepaidAmt").select();
	});	
});
/**
 * actions
 */
function saveDraftSales(){
	if (validateDraftSalesForm()){
		   formSubmit = true;
		   var params = $("#chainStoreSalesOrderForm").serialize();  
		   $.post("<%=request.getContextPath()%>/actionChain/chainSalesJSONAction!saveSalesToDraft",params, draftOrderBKProcess,"json");
	}
}
function draftOrderBKProcess(data){
	var response = data.response;

	var returnCode = response.returnCode;
	var returnMsg = response.message;
	if (returnCode == SUCCESS){		   
		alert("成功保存草稿");
		var salerId = data.salerId;
		window.location.href = "chainSalesJSPAction!preNewSalesOrder?formBean.chainSalesOrder.saler.user_id=" + salerId;
	} else {
		$.messager.progress('close'); 
        alert(returnMsg);
    }
}

function postSalesOrder(){
	$("#submitBt").attr("disabled", true);
	var isValidDraf = validateDraftSalesForm();
	var isValidSalesOrder = validateSalesOrder(SALES_ORDER);
	if (isValidDraf && isValidSalesOrder){
		  $.messager.progress({
			title : '提示',
			text : '单据过账中，打印小票....'
		   });
		   formSubmit = true;
		   var params = $("#chainStoreSalesOrderForm").serialize(); 
		   $.ajax({        
	    		type: "POST",               
	    		url: "<%=request.getContextPath()%>/actionChain/chainSalesJSONAction!postSalesOrder",        
	    		data: params,         
	    		timeout: 30000, 
	            dataType: 'json',        
	            error: function(XMLHttpRequest, textStatus, errorThrown){    
	            	alert("提交单据发生异常,请重新提交");
	            	$.messager.progress('close'); 
	            	$("#submitBt").removeAttr("disabled");
	            }, 
	            success: function(result) { 
	            	//alert(result);
	            	postOrderBKProcess(result);
	            }
				});
		   //$.post("<%=request.getContextPath()%>/actionChain/chainSalesJSONAction!postSalesOrder",params, postOrderBKProcess,"json");
	} else {
		$("#submitBt").removeAttr("disabled");
	}
}

function postOrderBKProcess(data){
	var response = data.response;

	var returnCode = response.returnCode;
	var returnMsg = response.message;
	var returnValue = response.returnValue;

	if (returnCode == SUCCESS){
        //print the pos bill
        try {
        	
	        printSalesOrder(returnValue);
        } catch (e){
			alert("小票打印出现问题 ,请联系总部管理员");
        }
				   
//		alert("成功过账");
		var salerId = data.salerId;
		window.location.href = "chainSalesJSPAction!preNewSalesOrder?formBean.chainSalesOrder.saler.user_id=" + salerId;
	} else {
		$.messager.progress('close'); 
        alert(returnMsg);
        $("#submitBt").removeAttr("disabled");
    }
}

function testPrint(){

	try {
	        testPrintF();
	    } catch (e){
			alert("小票打印有问题,请检查 : " + e.name
		            + "\n" + e.message);
	    }
}
function updateTabWithSaler(){
	var salerName = $("#chainSaler option[selected]").text();
	self.parent.updateTabName("新建零售单 " +salerName);
}
</script>
</head>
<body class="easyui-layout">
    <s:form action="/actionChain/chainSalesAction!save" method="POST" name="chainStoreSalesOrderForm" id="chainStoreSalesOrderForm" theme="simple">
    <input type="hidden" id="orderType" value="<s:property value="uiBean.chainSalesOrder.type"/>"/>
    <s:hidden name="formBean.chainSalesOrder.id"/>
    <s:hidden name="uiBean.chainStoreConf.printCopy" id="printCopy"/>
    <s:hidden name="uiBean.chainStoreConf.minDiscountRate" id="minDiscountRate"/>
    <s:hidden name="uiBean.chainStoreConf.discountAmtType" id="discountAmtType"/>
    <s:hidden name="uiBean.chainStoreConf.lowThanCostAlert" id="lowThanCostAlert"/>
    <s:hidden name="uiBean.chainStoreConf.defaultDiscount" id="defaultDiscount"/>
    <s:hidden name="uiBean.chainStoreConf.address" id="address"/>
    <s:hidden name="uiBean.chainStoreConf.hideDiscountPrint" id="hideDiscountPrint"/>
    
    <div data-options="region:'north',split:false,border:false,noheader:true" style="height:65px;overflow:hidden;">
    
			 <table width="100%" border="0" cellpadding="0" cellspacing="0">
			    <tr>
			       <td colspan="7">
			         <div class="errorAndmes"><s:actionerror cssStyle="color:red"/><s:actionmessage cssStyle="color:blue"/></div>
				   	 <table width="100%" border="0">
				       <tr class="InnerTableContent">
				         <td width="220" height="32">连锁店:<s:select id="chainStore" name="formBean.chainSalesOrder.chainStore.chain_id"  list="uiBean.chainStores" listKey="chain_id" listValue="chain_name"  onchange="changeChainStore();"/></td>
				         <td width="180">经手人： <s:select id="chainSaler" name="formBean.chainSalesOrder.saler.user_id"  list="uiBean.chainSalers" listKey="user_id" listValue="name" onchange ="updateTabWithSaler();"/></td>
				         <td width="190">创建人：<s:property value="uiBean.orderCreator.name"/></td>
				         <td width="230">单据日期 ：
				                         <s:if test="formBean.canEditOrderDate">
												<s:textfield id="orderDate" name="formBean.chainSalesOrder.orderDate" cssClass="easyui-datebox"  data-options="width:100,editable:false"/>
										 </s:if><s:else>
										 		<s:textfield id="orderDate" readonly="true" name="formBean.chainSalesOrder.orderDate" size="8"/>
										 </s:else>
						</td>
				         <td width="200"></td>
				       </tr>
				       <tr class="InnerTableContent">
				         <td colspan="5" >
				              <%@include file="../include/SearchVIPCard.jsp"%>
				         </td>
				       </tr>
				     </table>
				     </td>
				  </tr>
		    </table>
	     </div>
	     <div data-options="region:'center'" style="border:false">
				     <table width="100%"  align="left" class="OuterTable" id="org_table">
					    <tr class="PBAInnerTableTitale">
					      <td colspan="16">
					            <font size="2px">销售货品</font>
					      </td>
					    </tr>
					    
					    <%@ include file="../include/ChainSOrderEditContent.jsp"%>
					    
					    <tr class="InnerTableContent">
					      <td colspan="16"><hr width="100%" color="#FFCC00"/></td>
					    </tr>
					    
					    <tr class="PBAInnerTableTitale">
					      <td colspan="16">
					      	    <font size="2px">退换货品</font>
			              </td>
					    </tr>
					    
					    <%@ include file="../include/ChainSOrderEditRContent.jsp"%>
					    
					    <tr class="InnerTableContent">
					      <td colspan="16"><hr width="100%" color="#FFCC00"/></td>
					    </tr>	
					    
					    <tr class="PBAInnerTableTitale">
					      <td colspan="16">
					      	    <font size="2px">赠品</font>
			              </td>
					    </tr>
					    
					    <%@ include file="../include/ChainSOrderEditFreeContent.jsp"%>
					   </table>
	  </div>
	  <div data-options="region:'south'" style="height:118px;border:false">		  
				  	    <%@ include file="../include/ChainSSalesOrderFooter.jsp"%>
	  </div>
	 </s:form>
<script>
$(document).ready(function(){
    $("#barcode<s:property value='formBean.chainSalesOrder.productList.size()'/>").focus();

    //load the vip and discount buttons
    var vipCardId = $("#vipCardIdHidden").val();

    if (vipCardId != 0){	
	   $("#vipCardNo").attr("disabled","true");
	   $("#checkVIPBt").attr("disabled","true");
	   $("#clearVIPBt").removeAttr("disabled");
	   $("#vipScore").removeAttr("disabled");
	   $("#chainPrepaidAmt").removeAttr("disabled");
	   $("#refreshBt").removeAttr("disabled");
	   var vipScoreCash = $("#maxVipCash").val();
    }
	jQuery.excel('InnerTableContent');
	jQuery.excel('PBAOuterTableTitale');
	
	updateTabWithSaler();
	parent.$.messager.progress('close'); 
});
</script>
<s:if test="uiBean.chainStoreConf.printTemplate == 2">
	<%@include file="../include/Print2.jsp"%>
</s:if><s:else>
	<%@include file="../include/Print.jsp"%>
</s:else>
</body>
</html>