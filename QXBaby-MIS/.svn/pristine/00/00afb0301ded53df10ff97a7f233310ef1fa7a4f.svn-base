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
<script>
$(document).ready(function(){
	parent.$.messager.progress('close'); 
	
	var params = $.serializeObject($('#searchForm'));

	$('#dataGrid').datagrid({
		url : 'chainDailySalesJSON!genMonthlyHotBrands',
		queryParams: params,
		fit : true,
		fitColumns : true,
		border : false,
		checkOnSelect : false,
		selectOnCheck : false,
		singleSelect:true,
		nowrap : false,
		columns : [ [ {
			field : 'rank',
			title : '销售排名',
			width : 40
		}, {
			field : 'brandName',
			title : '热销品牌',
			width : 120,
			formatter: function (value, row, index){
				return row.brand.brand_Name;
			}				
		}, {
			field : 'salesQuantity',
			title : '系统总销量',
			width : 120,
			formatter: function (value, row, index){
				return (row.salesQuantity).toFixed(0);
			}
		}, {
			field : 'mySalesQuantity',
			title : '我的销量',
			width : 120,
			formatter: function (value, row, index){
				return (row.mySalesQuantity).toFixed(0);
			}
		}, {
			field : 'action',
			title : '查询热销款式',
			width : 60,
			formatter : function(value, row, index) {
				var str = '';
				str += $.formatString('<a href="#" onclick="genMonthlyHotProductsInBrand(\'{0}\',\'{2}\');"><img border="0" src="{1}" title="查看当前品牌热销款"/></a>', row.brand.brand_ID, '<%=request.getContextPath()%>/conf_files/easyUI/themes/icons/search.png', row.brand.brand_Name);
				return str;
			}
		}]],
	});

});

function genMonthlyHotProductsInBrand(brandId, brandName){
	$("#brandId").attr("value", brandId);
	//var params = $.serializeObject($('#searchForm'));
	var params=$("#searchForm").serialize();  
	$.modalDialog({
		title : brandName + " 热销款式",
		width : 540,
		height : 380,
		modal : false,
		draggable:false,
		href : 'chainDailySalesAction!genMonthlyHotProductsInBrand?' + params,
	});
}

function changeChainStore(chainId){
}
function validateForm(){
	var year = $("#reportYear").val();
	if (year == undefined || year == null){
		alert("年份 是必选项");
		return false;
	}

	var months = $("#reportMonths").val();
	if (months == null){
		alert("月份 是必选项");
		return false;
	}	
	
    return true;  
}

function genMonthlyHot(){
	if (validateForm())
		$('#dataGrid').datagrid('load', $.serializeObject($('#searchForm')));
}
</script>
</head>
<body>
	<div class="easyui-layout" border="false" style="width:800px;height:550px">
		<div data-options="region:'north',border:false" style="height: 190px;">
    		<s:form id="searchForm" name="searchForm" action="/actionChain/chainDailySalesAction!genWeeklyHotBrands" theme="simple" method="POST" target="连锁店销售折线图" onsubmit="return validateForm();"> 
     			 <input type="hidden" name="formBean.brandId" id="brandId"/>
     			 <table width="100%" border="0">
     			 		    <tr class="InnerTableContent">
						      <td height="25">&nbsp;</td>
						      <td><strong>连锁店</strong></td>
						      <td><%@ include file="../include/SearchChainStore.jsp"%></td>
						    </tr>
						    <tr class="InnerTableContent">
						      <td height="25" width="80">&nbsp;</td>
						      <td width="50"><strong>年份</strong></td>
						      <td ><s:select name="formBean.reportYear" id="reportYear"  list="uiBean.reportYearList" style="width:94px"/>
						      	   
						      </td>
						    </tr>
						   	<tr class="InnerTableContent">
						      <td height="25">&nbsp;</td>
						      <td><strong>月份</strong></td>
						      <td ><s:select name="formBean.months" id="reportMonths" multiple="true" size="5"  style="width:94px" list="uiBean.reportMonthList"/>
						           * 可多选.当月以前的数据会自动生成.
						      </td>
						    </tr>	
		                    <tr class="InnerTableContent">
						      <td height="30">&nbsp;</td>
						      <td>&nbsp;</td>
						      <td><input type="button" value="查询报表" onclick="genMonthlyHot();"/></td>
						    </tr>
					</table>
			</s:form>
		</div>
		<div data-options="region:'center',border:false">
			    <table id="dataGrid">			       
		        </table>
		</div>
	</div>
</body>
</html>