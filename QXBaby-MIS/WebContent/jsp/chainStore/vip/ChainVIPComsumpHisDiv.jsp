<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib prefix="s" uri="/struts-tags" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>千禧宝贝连锁店管理信息系统</title>
<script>
</script>
</head>
<body>
	<table class="easyui-datagrid"  style="height:280px"  data-options="singleSelect:true,border : false,showFooter:true">
		<thead>
		    <tr>
		      <th data-options="field:'2',width:100">VIP卡号</th>
		      <th data-options="field:'1',width:80">发生日期</th>
		      <th data-options="field:'6',width:80">种类</th>
		      <th data-options="field:'3',width:60">发生金额</th>
		      <th data-options="field:'4',width:60">积分</th>
		      <th data-options="field:'5',width:60">单据号</th>
		      <th data-options="field:'7',width:100">备注</th>
		    </tr>
		</thead>
		<tbody>
		    <s:iterator value="uiBean.vipConumps" status = "st" id="consumpItem" >
			    <tr>
			      <td><s:property value="#consumpItem.vipCardNo"/></td>
			      <td><s:property value="#consumpItem.date"/></td>
			      <td><s:property value="#consumpItem.typeS"/></td>
			      <td><s:property value="#consumpItem.salesValue"/></td>
			      <td><s:property value="#consumpItem.coupon"/></td>
			      <td><s:property value="#consumpItem.orderId"/></td>
			      <td><s:property value="#consumpItem.comment"/></td>
			    </tr>
		    </s:iterator>

		    <tr>
			      <td height="30">历史累计积分</td>
			      <td></td>
			      <td></td>
			      <td><s:property value="uiBean.vipConsumpFooter.salesValue"/></td>
			      <td><s:property value="uiBean.vipConsumpFooter.coupon"/></td>
			      <td></td>
			      <td></td>
			</tr>
	    </tbody>
	</table>
</body>
</html>