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
	$("#updtDateDiv").hide();
});
function searchProducts(){
	var year = $("#year").val();
	var quarter = $("#quarter").val();
	var productCode = $.trim($("#productCode").val());
	var brand = $("#brand_ID").val();
	var barcode = $("#barcode").val();
	var chainStoreId = $("#chainStoreId").val();
	var needUpdtTime = $("#needUpdtDate").attr("checked");
	if (chainStoreId == undefined || chainStoreId == 0){
		alert("连锁店是必选项");
		return "";
	}

	if (needUpdtTime != "checked")	{
		if (isEmpty(barcode)){
			var errorMsg = "";
	        if ((brand == "" || brand ==0) && (productCode == "")){
				errorMsg += "非条码和货号的查询下,品牌必须准确查找出来\n";
	        }
	        if (errorMsg != ""){
				alert(errorMsg);
				return "";
	        }
	            
		} else {
	        if (!isValidBarcode(barcode)){
				alert("输入的不是一个有效条码 (条码应该是12位的数字)");
				return "";
	        }
		}
	} else {
		var startDateS = $("#startDate").val();
		var endDateS = $("#endDate").val();
		if (startDateS == "" || endDateS ==""){
			alert("通过时间查找时,起始和截止时间不能为空");
			return "";
		}
	}

	var params = $.serializeObject($('#barcodeSearchForm'));
	
	$('#dataGrid').datagrid({
		url : 'chainMgmtJSON!searchChainBarcodes',
		queryParams: params,
		fit : true,
		fitColumns : true,
		border : false,
		checkOnSelect : false,
		selectOnCheck : false,
		singleSelect:true,
		nowrap : false,
		columns : [ [ {
			field : 'year',
			title : '年份',
			width : 40
		}, {
			field : 'quarter',
			title : '季度',
			width : 35
				
		}, {
			field : 'brand',
			title : '品牌',
			width : 70
		}, {
			field : 'category',
			title : '货品类',
			width : 60
		}, {
			field : 'productCode',
			title : '货号',
			width : 60
		}, {
			field : 'color',
			title : '颜色',
			width : 60
		}, {
			field : 'barcode',
			title : '条码',
			width : 80
		}, {
			field : 'chainSalePrice',
			title : '统一零售价',
			width : 60
		}, {
			field : 'mySalePrice',
			title : '我的零售价',
			width : 50,
			formatter: function (value, row, index){
				if (row.mySalePrice == 0)
					return "-";
				else 
					return row.mySalePrice;
			}
		
		}, {
			field : 'mySalesPricePercentage',
			title : '相对统一价%',
			width : 50
		}, {
			field : 'action',
			title : '查看/修改',
			width : 50,
			formatter : function(value, row, index) {
				var str = '';
				str += $.formatString('<a href="#" onclick="editChainSalesPrice(\'{0}\');"><img border="0" src="{1}" title="修改"/></a>', row.barcode, '<%=request.getContextPath()%>/conf_files/easyUI/themes/icons/text_1.png');
				return str;
			}
		}]]
	});
}

function editChainSalesPrice(barcode){
	var chainStoreId = $("#chainStoreId").val();
	
	$.modalDialog({
		title : '编辑连锁店零售价',
		width : 500,
		height : 300,
		href : 'chainMgmtJSP!getProductBarcode?formBean.productBarcode.barcode=' + barcode + '&formBean.chainId=' + chainStoreId,
		buttons : [ {
			text : '保存更新',
			handler : function() {
					var mySalesPrice = $("#mySalePrice").val();
					if (isEmpty(mySalesPrice)){
						$("#mySalePrice").attr("value",0);
						mySalesPrice = 0;
					}
						
					if (!isPositiveDouble(mySalesPrice)){
						alert("零售价格必须是大于零的数字");
						return false;
					} else {
						$.messager.progress({
							title : '提示',
							text : '数据处理中，请稍后....'
						});
					}
				   var params = $("#editSalesPriceForm").serialize(); 
				   $.post('chainMgmtJSON!updateChainBarcode',
						    params,
						    function(data) {
								$.messager.progress('close');
								var result = data.response;
								if (result.returnCode == SUCCESS) {
									$("#dataGrid").datagrid('reload');
									$.modalDialog.handler.dialog('close');
							    } else 
								    alert(result.message);
					        },"json");
			}
		} , {
			text : '放弃修改',
			handler : function() {
					$.modalDialog.handler.dialog('close');
			}
		}]
	});
}
function showLastUpdtDate(){
	if ($("#needUpdtDate").attr("checked") == 'checked')
		$("#updtDateDiv").show();
	else
		$("#updtDateDiv").hide();
}
function updateBatch(){
    document.barcodeSearchForm.action="chainMgmtJSP!updateBatchPrice";
    document.barcodeSearchForm.submit();
}
</script>
</head>
<body>
 	<div class="easyui-layout"  data-options="fit : true,border : false">
		<div data-options="region:'north',border:false" style="height: 155px;">
		  <s:form id="barcodeSearchForm" name="barcodeSearchForm" action="" method="POST" theme="simple" enctype="multipart/form-data" >
		    <!--<s:hidden name="formBean.isAdmin" id="isAdmin"/>-->
		    <div class="errorAndmes"><s:actionerror cssStyle="color:red"/><s:actionmessage cssStyle="color:blue"/></div>
    		<table width="65%" border="0">
				    <tr class="InnerTableContent">
				      <td height="30" width="160"><strong>年份</strong>
				      		<s:select name="formBean.productBarcode.product.year.year_ID" size="1" id="year"  list="uiBean.yearList" listKey="year_ID" listValue="year"/>
				      </td>
				      <td width="160"><strong>季度</strong>
				      		<s:select name="formBean.productBarcode.product.quarter.quarter_ID" size="1" id="quarter"  list="uiBean.quarterList" listKey="quarter_ID" listValue="quarter_Name"/>
				      </td>
				      <td><strong>连锁店</strong>
				            <s:select id="chainStoreId" name="formBean.chainId"  list="uiBean.chainStores"  listKey="chain_id" listValue="chain_name"/>
				      </td>
				    </tr>
				    <tr class="InnerTableContent">
				      <td><strong>条码</strong>
				      		<input type="text" name="formBean.productBarcode.barcode" id="barcode" title="请输入12位的条码" size="12"/>
				      </td>
				      <td height="30"><strong>货号</strong>
				      		<input type="text" name="formBean.productBarcode.product.productCode" id="productCode" title="产品货号" size="12"/>
				      </td>
				      <td><strong>品牌</strong>
				            <%@ include file="../../headQ/barcodeGentor/SearchBrandStub.jsp"%>
				      </td>
				    </tr>	
				    <tr class="InnerTableContent">
				      <td><strong>查找某段时间<br/>修改的条码</strong>
				      	  <input type="checkbox" name="formBean.needUpdtDate" id="needUpdtDate" onClick="showLastUpdtDate();" value="true"/>
				      </td>
				      <td height="30" colspan="2">
				              <div id="updtDateDiv" style="display:block">
						                    <s:textfield id="startDate" name="formBean.startDate" cssClass="easyui-datebox"  data-options="width:100,editable:false"/>
								&nbsp;至&nbsp;
							           <s:textfield id="endDate" name="formBean.endDate" cssClass="easyui-datebox" data-options="width:100,editable:false"/>
						       </div>
				      </td>
				    </tr>
                    <tr class="InnerTableContent">
				      <td><input type="button" value="搜索货品" onclick="searchProducts();"/></td>
				      <td>|</td>
				      <td>
				         <s:if test="#session.LOGIN_CHAIN_USER.containFunction('chainMgmtJSP!updateBatchPrice')">
				           <input type="file" name="formBean.inventory" id="salePrices"/> <input type="button" value="批量导入修改价格" onclick="updateBatch();" />
				         </s:if>
				      </td>
				    </tr>
			</table>
		   </s:form>
		</div>
		<div data-options="region:'center',border:false">
			<table id="dataGrid" border="0">			       
		    </table>
		</div>
	</div>

</body>
</html>