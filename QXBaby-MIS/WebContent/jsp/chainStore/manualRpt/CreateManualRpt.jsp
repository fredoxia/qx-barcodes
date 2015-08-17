<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-Strict.dtd">
<%@taglib prefix="s" uri="/struts-tags" %>
<%@ page import="com.onlineMIS.common.Common_util,java.util.Date,java.text.SimpleDateFormat" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<meta http-equiv="X-UA-Compatible" content="IE=8" />
<title>千禧宝贝连锁店管理信息系统</title>
<%@ include file="../../common/Style.jsp"%>

</head>
<body>
   <div class="easyui-layout"  data-options="fit : true,border : false">
		<div data-options="region:'center',border:false">
		   <s:form id="manualRptForm" name="manualRptForm" action="chainDailyManualRptJSPAction!createNewManualRpt" theme="simple" method="POST" onsubmit="return checkPreCreateDailyManualRpt();">  			 
	        <s:hidden name="formBean.rpt.chainStore.chain_id"/>
	        <strong><s:property value="formBean.rpt.chainStore.chain_name"/> 每日销售报表</strong>
	        <p/>
			<table  style="table-layout:fixed; border-collapse:collapse; border-color:#808080;" border="1">
			  <tr>
			    <td colspan="2" height="29">日期 <s:textfield id="date" name="formBean.rpt.rptDate" cssClass="easyui-datebox" data-options="width:100,editable:false"/></td>
			    <td colspan="3"><s:textfield id="weekday" name="formBean.rpt.weekday" style="width:80px;" readonly="true"/></td>
			    <td colspan="3">天气 <s:select id="weather" name="formBean.rpt.weather"  list="{'晴','阴','热','寒冷','小雨','大雨','阵雨','风','雾','雪'}"/></td>
			    <td colspan="2">温度 <s:textfield id="temperature" name="formBean.rpt.temperature" style="width:80px;"  cssClass="easyui-numberbox" data-options="min:0,precision:2"/></td>
			    <td colspan="3">实到人数 <s:textfield name="formBean.rpt.actualAttend" id="actualAttend" cssClass="easyui-numberspinner" style="width:80px;" required="required" data-options=" increment:1,min:1,max:10"/></td>
			    <td colspan="3">应到人数 <s:textfield name="formBean.rpt.shouldAttend" id="shouldAttend" cssClass="easyui-numberspinner" style="width:80px;" required="required" data-options=" increment:1,min:1,max:10"/></td>
			  </tr>
			  <tr>
			    <td colspan="16" height="29"><strong>一. 昨天的总结</strong> </td>
			  </tr>
			  <tr>
			    <td colspan="3" height="29">1. 月目标 <s:textfield id="monthlyTarget" name="formBean.rpt.monthlyTarget" style="width:80px;"  cssClass="easyui-numberbox" data-options="max:100000,min:0,precision:1"/></td>
			    <td colspan="5">累计完成额 <s:textfield id="accumulateMonthlyTarget" name="formBean.rpt.accumulateMonthlyTarget" style="width:80px;" cssClass="easyui-numberbox" data-options="max:100000,min:0,precision:1"/></td>
			    <td colspan="4">达成率 <s:textfield id="monthlyAchievePercentage" name="formBean.rpt.monthlyAchievePercentage" style="width:80px;" readonly="true"  />%</td>
			    <td colspan="4">差额 <s:textfield id="monthlyDiff" name="formBean.rpt.monthlyDiff" style="width:80px;" readonly="true"/></td>
			  </tr>
			  <tr>
			    <td colspan="3" height="29">2. 日目标 <s:textfield id="dailyTarget" name="formBean.rpt.dailyTarget" style="width:80px;" cssClass="easyui-numberbox" data-options="max:100000,min:0,precision:1"/></td>
			    <td colspan="5">当日实际完成额 <s:textfield id="actualDailyTarget" name="formBean.rpt.actualDailyTarget" style="width:80px;" cssClass="easyui-numberbox" data-options="max:100000,min:0,precision:1"/></td>
			    <td colspan="4">达成率 <s:textfield id="dailyAchievePercentage" name="formBean.rpt.dailyAchievePercentage" style="width:80px;"  readonly="true"/>%</td>
			    <td colspan="4">差额 <s:textfield id="dailyDiff" name="formBean.rpt.dailyDiff" style="width:80px;" readonly="true"/></td>
			  </tr>
			  <tr>
			    <td colspan="8" height="29">3. 上周同日销售<s:textfield id="sameDayLastWeekSales" name="formBean.rpt.sameDayLastWeekSales" style="width:80px;"/></td>

			    <td colspan="8">升/降 <s:select id="rankSameDayLastWeek" name="formBean.rpt.rankSameDayLastWeek"  list="#{1:'升',2:'降'}" listKey="key" listValue="value" /></td>
			  </tr>
			  <tr>
			    <td colspan="16" height="29">4. 目标达成/未达成的总体原因分析:</td>
			  </tr>
			  <tr>
			    <td rowspan="2" height="58">姓名</td>
			    <td rowspan="2">月累计</td>
			    <td rowspan="2">排名</td>
			    <td colspan="3">销售金额</td>
			    <td colspan="2">连带率</td>
			    <td colspan="2">客单价</td>
			    <td colspan="3">VIP新增人数</td>
			    <td colspan="3">VIP销售占比</td>
			  </tr>
			  <tr>
			    <td height="29">目标</td>
			    <td>实际完成</td>
			    <td>达成率</td>
			    <td>目标</td>
			    <td>实际完成</td>
			    <td>目标</td>
			    <td>实际完成</td>
			    <td>目标</td>
			    <td>实际完成</td>
			    <td>达成率</td>
			    <td>目标</td>
			    <td>实际完成</td>
			    <td>达成率</td>
			  </tr>
			  <s:iterator value="formBean.salerSize" status = "st" id="s" >
			  <tr>
			    <td height="25">
			        <input type="hidden" name="formBean.rpt.analysisItems[<s:property value="#s"/>].rowIndex" value="<s:property value="#s"/>"/>
			        <input type="text" id="salerName<s:property value="#s"/>" name="formBean.rpt.analysisItems[<s:property value="#s"/>].salerName" style="width:80px;" maxlength="4"/></td>
			    <td><input type="text" id="monthlyAccumulate<s:property value="#s"/>" name="formBean.rpt.analysisItems[<s:property value="#s"/>].monthlyAccumulate" style="width:60px;" class="easyui-numberbox" data-options="max:100000,min:0,precision:1"/></td>
			    <td><input type="text" id="rank<s:property value="#s"/>" name="formBean.rpt.analysisItems[<s:property value="#s"/>].rank" style="width:40px;" class="easyui-numberbox" data-options="max:5,min:1,precision:0"/></td>
			    <td><input type="text" id="salesTarget<s:property value="#s"/>" name="formBean.rpt.analysisItems[<s:property value="#s"/>].salesTarget" style="width:50px;" class="easyui-numberbox" data-options="max:100000,min:0,precision:1" onchange="calSalePercentage(<s:property value="#s"/>)"/></td>
			    <td><input type="text" id="salesActual<s:property value="#s"/>" name="formBean.rpt.analysisItems[<s:property value="#s"/>].salesActual" style="width:50px;" class="easyui-numberbox" data-options="max:100000,min:0,precision:1" onchange="calSalePercentage(<s:property value="#s"/>)"/></td>
			    <td><input type="text" id="salesPercentage<s:property value="#s"/>" name="formBean.rpt.analysisItems[<s:property value="#s"/>].salesPercentage" style="width:35px;" readonly="true"/>%</td>
			    <td><input type="text" id="relatedSalesTarget<s:property value="#s"/>" name="formBean.rpt.analysisItems[<s:property value="#s"/>].relatedSalesTarget" style="width:50px;" class="easyui-numberbox" data-options="max:900,min:0,precision:1"/></td>
			    <td><input type="text" id="relatedSalesActual<s:property value="#s"/>" name="formBean.rpt.analysisItems[<s:property value="#s"/>].relatedSalesActual" style="width:50px;" class="easyui-numberbox" data-options="max:900,min:0,precision:1"/></td>
			    <td><input type="text" id="amountPerSalesTarget<s:property value="#s"/>" name="formBean.rpt.analysisItems[<s:property value="#s"/>].amountPerSalesTarget" style="width:50px;" class="easyui-numberbox" data-options="max:10000,min:0,precision:1"/></td>
			    <td><input type="text" id="amountPerSalesActual<s:property value="#s"/>" name="formBean.rpt.analysisItems[<s:property value="#s"/>].amountPerSalesActual" style="width:50px;" class="easyui-numberbox" data-options="max:200,min:0,precision:1"/></td>
			    <td><input type="text" id="vipIncreaseTarget<s:property value="#s"/>" name="formBean.rpt.analysisItems[<s:property value="#s"/>].vipIncreaseTarget" style="width:50px;" class="easyui-numberbox" data-options="max:100,min:0,precision:0" onchange="calVIPIncreasePercentage(<s:property value="#s"/>)"/></td>
			    <td><input type="text" id="vipIncreaseActual<s:property value="#s"/>" name="formBean.rpt.analysisItems[<s:property value="#s"/>].vipIncreaseActual" style="width:50px;" class="easyui-numberbox" data-options="max:100,min:0,precision:0" onchange="calVIPIncreasePercentage(<s:property value="#s"/>)"/></td>
			    <td><input type="text" id="vipIncreasePercentage<s:property value="#s"/>" name="formBean.rpt.analysisItems[<s:property value="#s"/>].vipIncreasePercentage" style="width:35px;" readonly/>%</td>
			    <td><input type="text" id="vipSalesTarget<s:property value="#s"/>" name="formBean.rpt.analysisItems[<s:property value="#s"/>].vipSalesTarget" style="width:50px;" class="easyui-numberbox" data-options="max:100,min:0,precision:1" onchange="calVIPSalePercentage(<s:property value="#s"/>)"/></td>
			    <td><input type="text" id="vipSalesActual<s:property value="#s"/>" name="formBean.rpt.analysisItems[<s:property value="#s"/>].vipSalesActual" style="width:50px;" class="easyui-numberbox" data-options="max:100,min:0,precision:1" onchange="calVIPSalePercentage(<s:property value="#s"/>)"/></td>
			    <td><input type="text" id="vipSalesPercentage<s:property value="#s"/>" name="formBean.rpt.analysisItems[<s:property value="#s"/>].vipSalesPercentage" style="width:35px;" readonly/>%</td>
			  </tr>	
			  </s:iterator>		  			  			  
			  <tr>
			    <td colspan="16" height="29">5. 货品分析</td>
			  </tr>
			  <tr>
			    <td colspan="16" height="29">各类别销售数量占比</td>
			  </tr>
			  
			  <tr>
			    <td height="25">销售排名</td>
			    <td colspan="2">品牌</td>
			    <td colspan="2">畅销货号和颜色</td>
			    <td colspan="2">销售数量</td>
			    <td colspan="2">库存数量</td>
			    <td colspan="2">&nbsp;</td>
			    <td colspan="2">&nbsp;</td>
			    <td colspan="2">&nbsp;</td>
			    <td>&nbsp;</td>
			  </tr>
			  <s:iterator value="formBean.brandSize" status = "st" id="s" >
			  <tr>
			    <td height="29">
			        <s:property value="#st.index +1"/>
			        <input type="hidden" name="formBean.rpt.salesItems[<s:property value="#s"/>].rowIndex" value="<s:property value="#s"/>" readonly/>
			    </td>
			    <td colspan="2"><input type="text" id="brand<s:property value="#s"/>" name="formBean.rpt.salesItems[<s:property value="#s"/>].brand" style="width:100px;"/></td>
			    <td colspan="2"><input type="text" id="product<s:property value="#s"/>" name="formBean.rpt.salesItems[<s:property value="#s"/>].product" style="width:100px;"/></td>
			    <td colspan="2"><input type="text" id="salesQ<s:property value="#s"/>" name="formBean.rpt.salesItems[<s:property value="#s"/>].salesQ" style="width:100px;" class="easyui-numberbox" data-options="max:100000,min:0,precision:0"/></td>
			    <td colspan="2"><input type="text" id="inventoryQ<s:property value="#s"/>" name="formBean.rpt.salesItems[<s:property value="#s"/>].inventoryQ" style="width:100px;" class="easyui-numberbox" data-options="max:100000,min:0,precision:0"/></td>
			    <td colspan="2">&nbsp;</td>
			    <td colspan="2">&nbsp;</td>
			    <td colspan="2">&nbsp;</td>
			    <td>&nbsp;</td>
			  </tr>
			  </s:iterator>
	
			  <tr>
			    <td colspan="16" height="29">6. 昨天店铺运营方面相对较好的地方 ( *最长100字)</td>
			  </tr>
			  <tr>
			    <td colspan="16" height="29">&nbsp;
			     <textarea id="yestordayGoodPoint" name="formBean.rpt.yestordayGoodPoint" rows="3" cols="70"></textarea>
			    </td>
			  </tr>
			  <tr>
			    <td colspan="16" height="29">7. 昨天店铺运营方面做得相对欠佳,需要进一步改善的地方 ( *最长100字)</td>
			  </tr>
			  <tr>
			    <td colspan="16" height="29">&nbsp;
			      <textarea id="yestordayWeakPoint" name="formBean.rpt.yestordayWeakPoint" rows="3" cols="70"></textarea>	    
			    </td>
			  </tr>
			  <tr>
			    <td colspan="16" height="29">8. 昨天是否有突发事件,怎样处理的 (*最长100字)</td>
			  </tr>
			  <tr>
			    <td colspan="16" height="29">&nbsp;
			    <textarea id="yestordayEmergency" name="formBean.rpt.yestordayEmergency" rows="2" cols="70"></textarea>
			  </td>
			  </tr>
			  <tr>
			    <td colspan="16" height="29"><strong>二. 今天的计划</strong></td>
			  </tr>
			  <tr>
			    <td colspan="16" height="29">1. 目标 (*最长100字)</td>
			  </tr>
			  <tr>
			    <td colspan="16" height="58">&nbsp;
			      <textarea id="todayTarget" name="formBean.rpt.todayTarget" rows="3" cols="70"></textarea>
			    
			    </td>
			  </tr>			  
			  <tr>
			    <td colspan="16" height="29">2. 主要商品推广内容(FAB介绍) *最长100字</td>
			  </tr>
			  <tr>
			    <td colspan="16" height="58">&nbsp;
			      <textarea id="mainProductPromotion" name="formBean.rpt.mainProductPromotion" rows="3" cols="70"></textarea>		    
			    </td>
			  </tr>

			  <tr>
			    <td colspan="16" height="29">3. 铺运营目标(服务目标和店铺运作目标) *最长100字</td>
			  </tr>
			   <tr>
			    <td colspan="16" height="58">&nbsp;
			      <textarea id="operationTarget" name="formBean.rpt.operationTarget" rows="3" cols="70"></textarea>		    
			    </td>
			  </tr>             
			</table>
			<input type="button" value="提交报表" onclick="saveDailyRpt();"/>
			</s:form>
		</div>
	</div>
<script type="text/javascript" src="<%=request.getContextPath()%>/conf_files/js/ChainManualRpt.js"></script>
<script>
$(document).ready(function(){
	parent.$.messager.progress('close'); 
	$("#temperature").focus(function(){
		  $("#temperature").select();
	});
	$("#monthlyTarget").focus(function(){
		  $("#monthlyTarget").select();
	});
	$("#accumulateMonthlyTarget").focus(function(){
		  $("#accumulateMonthlyTarget").select();
	});
	$("#dailyTarget").focus(function(){
		  $("#dailyTarget").select();
	});
	$("#actualDailyTarget").focus(function(){
		  $("#actualDailyTarget").select();
	});
	$("#sameDayLastWeekSales").focus(function(){
		  $("#sameDayLastWeekSales").select();
	});
	
	$("#monthlyTarget").change(function(){calMonthly();});
	$("#accumulateMonthlyTarget").change(function(){calMonthly();});
	
	$("#dailyTarget").change(function(){calDaily();});
	$("#actualDailyTarget").change(function(){calDaily();});
	
});
function saveDailyRpt(){
    if (validateManualRpt()){
		var params=$("#manualRptForm").serialize(); 
		$.post("<%=request.getContextPath()%>/actionChain/chainDailyManualRptJSON!saveChainDailyManualRpt",params, backProcessGetChainStore,"json");
    }
}


function backProcessGetChainStore(data){
    alert(data.returnCode);
}

</script>	
</body>
</html>