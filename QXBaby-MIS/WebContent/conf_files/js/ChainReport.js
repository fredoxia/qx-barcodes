function generateReport(){
	$.messager.progress({
		title : '提示',
		text : '数据处理中，请稍后....'
	});

    var params=$("#preGenReportForm").serialize();  
    var url = "";
	var reportType = parseInt($("#reportType").val());
	
	switch(reportType){
		case 1:
			url = "chainReportJSON!generateSalesReport";
		    break;
		case 2:
			url = "chainReportJSON!generatePurchaseReport";
		    break;
		case 3:
			url = "chainReportJSON!generateFinanceReport";
		    break;			
		default: alert("无法找到对应报表功能");
	}

    $.post(url,params, generateReportBackProcess,"json");	
}

function generateReportBackProcess(data){
    $('#reportTable tr').each(function () {                
        $(this).remove();
    });
    
	var report = data.report;

	if (report != undefined){
		showReport(report);
	}

	$("#report").show();
	$.messager.progress('close'); 
}

function showReport(report){
	var reportType = report.type;
	
	switch(reportType){
		case 1:
			showSalesReport(report);
		    break;
		case 2:
			showPurchaseReport(report);
		    break;
		case 3:
			showFinanceReport(report);
		    break;			
		default: alert("生成报表出现错误");
	}
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

function showLineChart(chainId, startDate, endDate){}
function genDailySalesReport(chainId){
	var startDate = $("#startDate").combo('getValue');
	var endDate = $("#endDate").combo('getValue');
	var param ="formBean.chainStore.chain_id=" +chainId + "&formBean.startDate="+ startDate +"&formBean.endDate=" + endDate;

	$.post(baseurl + "/actionChain/chainDailySalesJSON!genDailySalesReport",param, backProcessGenDailySalesReport,"json");
	
}
function backProcessGenDailySalesReport(data){
	var response = data.response;
	var returnCode = response.returnCode;
	if (returnCode != SUCCESS){
		alert("获取信息失败 : " + response.message);
	} else {
		var reportVO = response.returnValue;
	       
		drawHighChartDailySales("container", reportVO);
		
		$('#win').window('open');
	}
}
