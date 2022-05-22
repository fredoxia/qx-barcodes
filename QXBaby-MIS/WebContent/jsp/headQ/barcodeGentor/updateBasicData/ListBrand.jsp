<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-Strict.dtd">
<%@taglib prefix="s" uri="/struts-tags" %>
<%@ page import="com.onlineMIS.common.Common_util,java.util.Date,java.text.SimpleDateFormat" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<meta http-equiv="X-UA-Compatible" content="IE=8" />
<title>千禧宝贝连锁店管理信息系统</title>
<%@ include file="../../../common/Style.jsp"%>
<script>
var dataGrid ;
$(document).ready(function(){
	parent.$.messager.progress('close'); 
	
	var params = $.serializeObject($('#basicDataForm'));

	dataGrid = $('#dataGrid').datagrid({
		url : 'basicDataJSON!getBasicData',
		queryParams: params,
		fit : true,
		border : false,
		pagination : true,
		pageSize : 15,
		pageList : [ 15, 30],		
		checkOnSelect : false,
		selectOnCheck : false,
		singleSelect:true,
		nowrap : false,
		rownumbers : true,
		sortName : 'pinyin',
		sortOrder : 'asc',
		columns : [ [ {
			field : 'pinyin',
			title : '名字拼音',
			width : 100,
			sortable:true,
			order:'desc',
		    },{
				field : 'brand_Name',
				title : '品牌名字',
				width : 120
			},{			    
			field : 'supplier',
			title : '供应商',
			width : 100
		    }, {
				field : 'comment',
				title : '商品备注',
				width : 120
			    }, {			    	
			field : 'chain_name',
			title : '所属',
			width : 90,
			formatter: function (value, row, index){
				return row.chainStore.chain_name;
			}
		    }, {			    
			field : 'action',
			title : '编辑',
			width : 70,
			formatter : function(value, row, index) {
				var str = '';
				str += $.formatString('<a href="#" onclick="EditBrand(\'{0}\');"><img border="0" src="{1}" title="编辑品牌"/></a>', row.brand_ID, '<%=request.getContextPath()%>/conf_files/easyUI/themes/icons/text_1.png');
				return str;
			}
		}]],
		toolbar : '#toolbar',
	});

});

function EditBrand(brandId){
	var params = "formBean.basicData=brand";
	if (brandId != 0)
	   params += "&formBean.basicDataId =" + brandId;

	$.modalDialog.opener_dataGrid = dataGrid;
	
	$.modalDialog({
		title : "添加/更新品牌",
		width : 540,
		height : 380,
		modal : false,
		draggable:false,
		href : 'basicData!preAddBasicData?' + params,
		
	});
}
function refresh(){
	$('#dataGrid').datagrid('load', $.serializeObject($('#basicDataForm')));
}
function clearSearch(){
	$("#brandName").val("");
	$("#supplier").val("");
	$("#comment").val("");
	refresh();
}
</script>
</head>
<body>
	<div class="easyui-layout" border="false" style="width:650px;height:560px">
		<div data-options="region:'north',border:false" style="height: 85px;">
			<script>
			function changeBasicData(value){
			    if (value != ""){
			    	document.basicDataForm.action="basicData!changeBasicData";
			    	document.basicDataForm.submit(); 
			    }
			}
			</script>
			<s:form id="basicDataForm" name="basicDataForm" action="" theme="simple">
				 <table width="90%" border="0">
				    <tr>
				      <td width="80">
				         <strong>基础资料类别：</strong>
				      </td>
					  <td colspan="5">
					      <s:select name="formBean.basicData" onchange="changeBasicData(this.value);"  list="#{'year':'年份','quarter':'季度','brand':'品牌','category':'货品类别','color':'颜色','productUnit':'货品单位','numPerHand':'齐手数量'}" listKey="key" listValue="value"  headerKey=""  headerValue="请选择"/>
					  </td>
				    </tr>
				    <tr>
				      <td> 品牌名字     </td>
					  <td width="50"><s:textfield id="brandName" name="formBean.brand.brand_Name" cssClass="easyui-textbox"  style="width:80px"/></td>
					  <td width="40"> 供应商     </td>
					  <td width="50"><s:textfield id="supplier" name="formBean.brand.supplier" cssClass="easyui-textbox"  style="width:80px"/></td>
					  <td width="40"> 品牌备注    </td>
					  <td width="50"><s:textfield id="comment" name="formBean.brand.comment" cssClass="easyui-textbox"  style="width:80px"/></td>
				    </tr>
				    <tr>
				      <td> </td>
					  <td colspan="5"><input type="button" value="查询品牌信息" onclick="refresh();"/><input type="button" value="清楚查询条件" onclick="clearSearch();"/></td>
				    </tr>				    
				 </table>
			</s:form>
		</div>
		<div data-options="region:'center',border:false">
				<table id="dataGrid">			       
		        </table>
	
			<div id="toolbar" style="display: none;">
		             <a onclick="EditBrand(0);" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-add'">添加</a>
                     <a onclick="refresh();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-reload'">刷新</a>
	        </div>
		</div>
	</div>
</body>
</html>