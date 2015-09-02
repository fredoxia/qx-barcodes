<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib prefix="s" uri="/struts-tags" %>
<%@ page import="com.onlineMIS.common.Common_util,java.util.Date,java.text.SimpleDateFormat" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>千禧宝贝连锁店管理信息系统</title>
<%@ include file="../../common/Style.jsp"%>
<SCRIPT src="<%=request.getContextPath()%>/conf_files/js/ChainReport.js" type=text/javascript></SCRIPT>
<script src="<%=request.getContextPath()%>/conf_files/js/highChart/js/highcharts.js"></script>
<script src="<%=request.getContextPath()%>/conf_files/js/highChart/js/modules/exporting.js"></script>
<script src="<%=request.getContextPath()%>/conf_files/js/drawHighChartSales.js"></script>
<script>
var baseurl = "<%=request.getContextPath()%>";
$(document).ready(function(){
	parent.$.messager.progress('close'); 
});
function changeChainStore(chainId){
	var params = "formBean.chainStore.chain_id=" + chainId;
	$.post("<%=request.getContextPath()%>/actionChain/chainMgmtJSON!changeChainStore",params, backProcessChangeChainStore,"json");
}
function backProcessChangeChainStore(data){
	
	var users =  data.chainUsers;
	if (users != undefined){
		$("#chainSaler").empty();
		
		$("#chainSaler").append("<option value='<%=Common_util.ALL_RECORD%>'>---全部---</option>"); 
		
		for (var i = 0; i < users.length; i++)
			   $("#chainSaler").append("<option value='"+users[i].user_id+"'>"+users[i].name+"</option>"); 
	}
}

var dfPrinter ;

function printSalesReport(){
	try{
		dfPrinter = pazu.TPrinter.getDefaultPrinter();
	
		if (dfPrinter == null){
	        alert("还未设置默认小票打印机，请设置后打印小票");
		} else {
			$.messager.progress({
				title : '提示',
				text : '数据处理中，请稍后....'
			});
			
		    var params=$("#preGenReportForm").serialize();  
		    var url = "";
			var reportType = parseInt($("#reportType").val());
			url = "chainReportJSON!generateSalesReport";
		
		    $.post(url,params, generateReportBackProcess2,"json");	
		}
    } catch (e){
		alert("小票打印出现问题 ,请联系总部管理员");
    }
}

function generateReportBackProcess2(data){
	$.messager.progress('close');
	var report = data.report;

	if (report != undefined){
		var space = "   ";
		var space2 = "    ";
		var qSpace = "     ";
		dfPrinter.FontBold=false; 
		dfPrinter.FontSize=8;

		//打印连锁店名字
	 	var chainName = $("#chainName").val();
		pazu.TPrinter.printToDefaultPrinter(chainName + " 销售报表");

		//打印日期
		var startDate = $("#startDate").combo("getValue");
		var endDate = $("#endDate").combo("getValue");
		pazu.TPrinter.printToDefaultPrinter(startDate + " 至 " + endDate);
		var date = new Date();
	    var footer = "打印日期 : " + formatDate(date,"YYYY-MM-DD hh:mm:ss"); 
		pazu.TPrinter.printToDefaultPrinter(footer);
		
		//打印销售人员
		var salerId = $("#chainSaler").val();
		if (salerId != -1){
		    var obj = document.getElementById("chainSaler");
			var  salesName = obj.options[obj.selectedIndex].text;
			pazu.TPrinter.printToDefaultPrinter("销售人员 : " + salesName);
		}

		pazu.TPrinter.printToDefaultPrinter("-------------");
		
		//打印内容
		pazu.TPrinter.printToDefaultPrinter("销售量 : " + report.saleQuantitySum);
		pazu.TPrinter.printToDefaultPrinter("退货量 : " + report.returnQuantitySum);
		pazu.TPrinter.printToDefaultPrinter("净销售量 : " + report.netQuantitySum);
		pazu.TPrinter.printToDefaultPrinter("赠品量 : " + report.freeQantitySum);
		pazu.TPrinter.printToDefaultPrinter("销售额 : " + (report.salesAmtSum).toFixed(1));
		pazu.TPrinter.printToDefaultPrinter("退货额 :  " + (report.returnAmtSum).toFixed(1));
		pazu.TPrinter.printToDefaultPrinter("优惠总额  ：  " + (report.discountSum).toFixed(1));
		pazu.TPrinter.printToDefaultPrinter("代金券 ：  " + (report.couponSum).toFixed(1));
		pazu.TPrinter.printToDefaultPrinter("积分换现金 ：  " + (report.vipScoreSum).toFixed(1));
		pazu.TPrinter.printToDefaultPrinter("刷卡 ：  " + (report.cardAmtSum).toFixed(1));
		pazu.TPrinter.printToDefaultPrinter("现金 ：  " + (report.cashNetSum).toFixed(1));
		pazu.TPrinter.printToDefaultPrinter("销售折扣 ：  " + (report.salesDiscountAmtSum).toFixed(1));
		dfPrinter.EndDoc();
	} else {
        alert("无法获取销售报表");
	}
}

function showSalesReport(report){
	$("<tr class='PBAInnerTableTitale'>"+
		       "<td height='20'>销售总量<br/>A</td>"+
			   "<td>退货总量<br/>B</td>"+
			   "<td>净销售量<br/>A-B</td>"+
			   "<td>赠品数量<br/>&nbsp;</td>"+
			   "<td>&nbsp;</td>"+
			   "<td>销售额<br/>C</td>"+
			   "<td>退货额<br/>D</td>"+
			   "<td>净销售额<br/>C-D</td>"+
			   "<td>净销售成本<br/>E</td>"+
			   "<td>赠品成本<br/>F</td>"+
			   "<td>优惠总额<br/>G</td>"+
			   "<td>净销售利润<br/>C-D-E-F-G</td>"+
			   "</tr>").appendTo("#reportTable");
	
    $("<tr class='InnerTableContent'><td>"+
	          report.saleQuantitySum +"</td><td>"+
	          report.returnQuantitySum +"</td><td>"+
	          (report.netQuantitySum) +"</td><td>"+
	          report.freeQantitySum +"</td><td>"+
	          "&nbsp;</td><td>"+
	          (report.salesAmtSum).toFixed(2)  +"</td><td>"+
	          (report.returnAmtSum).toFixed(2) +"</td><td>"+
	          "<a href='#' onclick='genDailySalesReport("+ report.chainStore.chain_id +");'>" +
	          (report.netAmtSum).toFixed(2) +
	          " <img src='<%=request.getContextPath()%>/conf_files/web-image/search.png' border='0'  style='cursor:pointer;'/></a></td><td>"+
	          <s:if test="#session.LOGIN_CHAIN_USER.containFunction('purchaseAction!seeCost')">(report.netSaleCostSum).toFixed(2)</s:if><s:else>"-"</s:else> +"</td><td>"+
	          <s:if test="#session.LOGIN_CHAIN_USER.containFunction('purchaseAction!seeCost')">(report.freeCostSum).toFixed(2)</s:if><s:else>"-"</s:else> + "</td><td>"+
	          (report.discountSum).toFixed(2) +"</td><td>" + 
	          <s:if test="#session.LOGIN_CHAIN_USER.containFunction('purchaseAction!seeCost')">(report.netProfit).toFixed(2)</s:if><s:else>"-"</s:else> +"</td></tr>").appendTo("#reportTable");
    
	$("<tr class='PBAInnerTableTitale'>"+
		       "<td height='20'>销售折扣<br/>&nbsp;</td>"+
			   "<td>代金券总额<br/>&nbsp;</td>"+
			   "<td>VIP积分换现金<br/>&nbsp;</td>"+
			   "<td>预存款消费<br/>&nbsp;</td>"+
			   "<td>&nbsp;</td>"+
			   "<td>刷卡总额<br/>&nbsp;</td>"+
			   "<td>现金总额<br/>&nbsp;</td>"+
			   "<td></td>"+
			   "<td>VIP净销售量<br/>&nbsp;</td>"+
			   "<td>VIP净销售额<br/>&nbsp;</td>"+
			   "<td>VIP销售额占比<br/>&nbsp;</td>"+
			   "<td></td>"+
			   "</tr>").appendTo("#reportTable");
			
    $("<tr class='InnerTableContent'><td>"+
    		  (report.salesDiscountAmtSum).toFixed(2)  +"</td><td>"+
	          (report.couponSum).toFixed(2) +"</td><td>"+
	          (report.vipScoreSum).toFixed(2) +"</td><td>"+
	          (report.vipPrepaidAmt).toFixed(1) +"</td><td>"+
	          "&nbsp;</td><td>"+
	          (report.cardAmtSum).toFixed(2) +"</td><td>"+
	          (report.cashNetSum).toFixed(2) +"</td><td></td><td>"+
	          report.vipSaleQ +"</td><td>"+
	          (report.vipSaleAmt).toFixed(2) +"</td><td>"+
	          (report.vipPercentage).toFixed(2) +"%</td><td>"+
	          "</td></tr>").appendTo("#reportTable");
	$("<tr class='PBAInnerTableTitale'>"+
		       "<td height='20'>千禧货品<br/>净销售量</td>"+
			   "<td>千禧货品<br/>净销售额</td>"+
			   "<td>千禧货品<br/>销售成本</td>"+
			   "<td></td>"+
			   "<td>&nbsp;</td>"+
			   "<td>其他货品<br/>净销售量</td>"+
			   "<td>其他货品<br/>净销售额</td>"+
			   "<td>其他货品<br/>销售成本</td>"+
			   "<td></td>"+ 
			   "<td>预存现金</td>"+
			   "<td>预存刷卡</td>"+
			   "<td>累计剩余预存款</td>"+
			   "</tr>").appendTo("#reportTable");
			
    $("<tr class='InnerTableContent'><td>"+
 		      report.qxQuantity  +"</td><td>"+
	          (report.qxAmount).toFixed(2) +"</td><td>"+
	          <s:if test="#session.LOGIN_CHAIN_USER.containFunction('purchaseAction!seeCost')">(report.qxCost).toFixed(2) </s:if><s:else>"-"</s:else> +"</td><td>"+
	          "</td><td>"+
	          "&nbsp;</td><td>"+
	          report.myQuantity +"</td><td>"+
	          (report.myAmount).toFixed(2) +"</td><td>"+
	          <s:if test="#session.LOGIN_CHAIN_USER.containFunction('purchaseAction!seeCost')">(report.myCost).toFixed(2) </s:if><s:else>"-"</s:else> +"</td><td>"+
	          "</td><td>"+ (report.vipPrepaidDepositCash).toFixed(2)+"</td><td>"+(report.vipPrepaidDepositCard).toFixed(2)+"</td><td>"+(report.vipPrepaidAccumulate).toFixed(2)+"</td></tr>").appendTo("#reportTable");    
}
</script>
</head>
<body>
   <s:form id="preGenReportForm" action="/actionChain/chainReportJSPAction!generateReport" theme="simple" method="POST"> 
     <s:hidden name="formBean.reportType" id="reportType"/>
     <table width="100%" align="center"  class="OuterTable">
	    <tr><td>
			 <table width="100%" border="0">
			    <tr>
			       <td height="50" colspan="7">
					   <table width="100%" border="0">
					    <tr class="InnerTableContent">
					      <td width="45" height="30">&nbsp;</td>
					      <td width="76"><strong>单据日期</strong></td>
					      <td width="284" colspan="2">
					        <s:textfield id="startDate" name="formBean.startDate" cssClass="easyui-datebox"  data-options="width:100,editable:false"/>
					        &nbsp;至&nbsp;
					        <s:textfield id="endDate" name="formBean.endDate" cssClass="easyui-datebox" data-options="width:100,editable:false"/>
					      </td>
					      <td></td>
					    </tr>
						<tr class="InnerTableContent">
					      <td height="30">&nbsp;</td>
					      <td><strong>连锁店</strong></td>
					      <td><%@ include file="../include/SearchChainStore.jsp"%></td>
					      <td></td>
					      <td></td>
					    </tr>
					    <tr class="InnerTableContent">
					      <td height="30">&nbsp;</td>
					      <td><strong>经手人</strong></td>
					      <td><s:select id="chainSaler" name="formBean.saler.user_id"  list="uiBean.chainSalers" listKey="user_id" listValue="user_name" headerKey="-1" headerValue="---全选---"/></td>
					      <td></td>
					      <td></td>
					    </tr>
	                    <tr class="InnerTableContent">
					      <td height="30">&nbsp;</td>
					      <td>&nbsp;</td>
					      <td colspan="2"><input type="button" value="生成报表" onclick="generateReport();"/>&nbsp;<input type="button" value="打印报表到小票机" onclick="printSalesReport();"/></td>
					      <td>&nbsp;</td>
					    </tr>
					  </table></td>
			    </tr>
	   			<tr>
                    <td>
            	      <div id="report" style="display:none">
            	         <table width="80%" border="0" id="reportTable">

					     </table>
            	      </div>
                    </td>
                </tr>
			  </table>
	   </td></tr>

	 </table>
	 </s:form>
	 <div id="win" class="easyui-window" title="连锁店日销售趋势图" style="width:800px;height:500px"  data-options="modal:true,closed:true">  
	      <div id="container" style="min-width: 750px; height: 450px; margin: 0 auto;"></div>
	 </div>
	 <object  classid="clsid:AF33188F-6656-4549-99A6-E394F0CE4EA4"       
         codebase="<%=request.getContextPath()%>/conf_files/sc_setup.exe"     
         id="pazu"       
         name="pazu" >       
    <param  name="License"  value="8F34B771723DCC171F931EA900F9967E"/>     
    </object>
</body>
</html>