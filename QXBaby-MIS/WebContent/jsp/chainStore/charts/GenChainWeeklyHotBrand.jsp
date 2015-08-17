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
		url : 'chainDailySalesJSON!genWeeklyHotBrands',
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
			title : '平均销量',
			width : 120,
			formatter: function (value, row, index){
				return (row.salesQuantity).toFixed(2);
			}
		}, {
			field : 'mySalesQuantity',
			title : '我的销量',
			width : 120
		}, {
			field : 'action',
			title : '查询热销款式',
			width : 60,
			formatter : function(value, row, index) {
				var str = '';
				str += $.formatString('<a href="#" onclick="genWeeklyHotProductsInBrand(\'{0}\',\'{2}\',\'{3}\');"><img border="0" src="{1}" title="查看当前品牌热销款"/></a>', row.brand.brand_ID, '<%=request.getContextPath()%>/conf_files/easyUI/themes/icons/search.png', row.brand.brand_Name, row.reportDate);
				return str;
			}
		}]],
	});

});

function genWeeklyHotProductsInBrand(brandId, brandName,reportDate){
	var chainId = $("#chainId").val();
	//alert(brandId+","+brandName+","+chainId+","+reportDate);
	var param = "formBean.chainStore.chain_id="+ chainId +"&formBean.brandId="+brandId+"&formBean.startDate="+reportDate;
	$.modalDialog({
		title : brandName + " 热销款式",
		width : 520,
		height : 350,
		modal : false,
		draggable:false,
		href : 'chainDailySalesAction!genWeeklyHotProductsInBrand?' + param,
	});
}

function changeChainStore(chainId){
}
function validateForm(){
	var earliestDate = new Date("2013/11/4");
	var today = new Date();
	var fromDate = $('#startDate').datebox('getValue');

    var start=new Date(fromDate.replace("-", "/").replace("-", "/"));  

    if (start < earliestDate){
        alert("日期错误: 此功能在系统中最早的数据是2013-11-4这一周");
        $("#startDate").datebox("setValue","2013-11-4");
        return false;  
    }
	
    return true;  
}

function genWeeklyHot(){
	if (validateForm())
		$('#dataGrid').datagrid('load', $.serializeObject($('#searchForm')));
}
</script>
</head>
<body>
	<div class="easyui-layout" border="false" style="width:800px;height:550px">
		<div data-options="region:'north',border:false" style="height: 150px;">
    		<s:form id="searchForm" name="searchForm" action="/actionChain/chainDailySalesAction!genWeeklyHotBrands" theme="simple" method="POST" target="连锁店销售折线图" onsubmit="return validateForm();"> 
     			 <table width="100%" border="0">
						   	<tr class="InnerTableContent">
					          <td height="35" colspan="3">* 你选择某个日期,系统会计算那个日期所在的那一周情况.比如,你选择2012-11-13,系统会自动帮你计算11-11 到 11-17那一周的数据。
					          </td>
						    </tr>
						    <tr class="InnerTableContent">
						      <td height="25" width="80">&nbsp;</td>
						      <td width="30"><strong>查询周</strong></td>
						      <td ><s:textfield id="startDate" name="formBean.startDate" cssClass="easyui-datebox" editable="false"/>
						      	   
						      </td>
						    </tr>
						    <tr class="InnerTableContent">
						      <td height="25">&nbsp;</td>
						      <td><strong>连锁店</strong></td>
						      <td><%@ include file="../include/SearchChainStore.jsp"%></td>
						    </tr>	
		                    <tr class="InnerTableContent">
						      <td height="30">&nbsp;</td>
						      <td>&nbsp;</td>
						      <td><input type="button" value="查询报表" onclick="genWeeklyHot();"/></td>
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