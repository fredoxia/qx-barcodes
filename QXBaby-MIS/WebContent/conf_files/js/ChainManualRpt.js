function calMonthly(){
	var monthlyTarget = $("#monthlyTarget").val();
	var monthlyAccu = $("#accumulateMonthlyTarget").val();
	
	if (monthlyTarget != "" && monthlyTarget != 0){
		var percentage = (monthlyAccu / monthlyTarget) * 100;
		$("#monthlyAchievePercentage").attr("value", percentage.toFixed(1));
	} else 
		$("#monthlyAchievePercentage").attr("value", 0);
	
	var diff = (monthlyTarget - monthlyAccu).toFixed(1);
	$("#monthlyDiff").attr("value", diff);
}

function calDaily(){
	var dailyTarget = $("#dailyTarget").val();
	var dailyAccu = $("#actualDailyTarget").val();
	
	if (dailyTarget != "" && dailyTarget != 0){
		var percentage = (dailyAccu / dailyTarget) * 100;
		$("#dailyAchievePercentage").attr("value", percentage.toFixed(1));
	} else 
		$("#dailyAchievePercentage").attr("value", 0);
	
	var diff = (dailyTarget - dailyAccu).toFixed(1);
	$("#dailyDiff").attr("value", diff);
}

function calSalePercentage(num){
	var salesTarget = $("#salesTarget" + num).val();
	var salesActual = $("#salesActual" + num).val();
	if (salesTarget != "" && salesTarget != 0){
		var percentage = (salesActual / salesTarget) * 100;
		$("#salesPercentage" + num).attr("value", percentage.toFixed(1));
	} else 
		$("#salesPercentage" + num).attr("value", 0);
}

function calVIPIncreasePercentage(num){
	var vipIncreaseTarget = $("#vipIncreaseTarget" + num).val();
	var vipIncreaseActual = $("#vipIncreaseActual" + num).val();
	if (vipIncreaseTarget != "" && vipIncreaseTarget != 0){
		var percentage = (vipIncreaseActual / vipIncreaseTarget) * 100;
		$("#vipIncreasePercentage" + num).attr("value", percentage.toFixed(1));
	} else 
		$("#vipIncreasePercentage" + num).attr("value", 0);
}

function calVIPSalePercentage(num){
	var vipSalesTarget = $("#vipSalesTarget" + num).val();
	var vipSalesActual = $("#vipSalesActual" + num).val();
	if (vipSalesTarget != "" && vipSalesTarget != 0){
		var percentage = (vipSalesActual / vipSalesTarget) * 100;
		$("#vipSalesPercentage" + num).attr("value", percentage.toFixed(1));
	} else 
		$("#vipSalesPercentage" + num).attr("value", 0);
}

function validateManualRpt(){
	var error = "";
	var temperature = $("#temperature").val();
	if (temperature == "" || temperature == 0){
		error += "温度 - 是必填项\n";
	}
	
	var monthlyTarget = $("#monthlyTarget").val();
	if (monthlyTarget == "" || monthlyTarget == 0){
		error += "月目标 - 是必填项\n";
	}

	var dailyTarget = $("#dailyTarget").val();
	if (dailyTarget == "" || dailyTarget == 0){
		error += "日目标 - 是必填项\n";
	}

	var sameDayLastWeekSales = $("#sameDayLastWeekSales").val();
	if (sameDayLastWeekSales == "" || sameDayLastWeekSales == 0){
		error += "上周同日销售 - 是必填项\n";
	}	
	
	var yestordayGoodPoint = $("#yestordayGoodPoint").val();
	if (yestordayGoodPoint.length > 100){
		error += "6.昨天店铺运营好的方面 - 超过了最长100的规定限制\n";
	}	
	
	if (error != ""){
		alert(error);
		return false;
	} else 
		return true;
}