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
</script>
</head>
<body>
	<table class="easyui-datagrid"  style="height:170px"  data-options="singleSelect:true,border : false">
		<thead>
		    <tr>
		      <th data-options="field:'1',width:120">发生日期</th>
		      <th data-options="field:'2',width:100">描述</th>
		      <th data-options="field:'3',width:60">数量</th>
		      <th data-options="field:'5',width:60">编码</th>
		      <th data-options="field:'6',width:60">单据号</th>
		    </tr>
		</thead>
		<tbody>
		    <s:iterator value="uiBean.traceItems" status = "st" id="traceItem" >
			    <tr>
			      <td><s:property value="#traceItem.date"/></td>
			      <td><s:property value="#traceItem.descp"/></td>
			      <td><s:property value="#traceItem.quantity"/></td>
			      <td><s:property value="#traceItem.actionCode"/></td>
			      <td><s:property value="#traceItem.orderId"/></td>
			    </tr>
		    </s:iterator>
		    <tr>
			      <td height="30">目前剩余</td>
			      <td></td>
			      <td><s:property value="uiBean.traceFooter.quantity"/></td>
			      <td></td>
			      <td></td>
			    </tr>
		 </tbody>
	</table>
</body>
</html>