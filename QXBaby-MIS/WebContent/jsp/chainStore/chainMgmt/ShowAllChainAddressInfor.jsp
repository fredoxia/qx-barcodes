<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib prefix="s" uri="/struts-tags" %>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>千禧宝贝连锁店管理信息系统</title>
<%@ include file="../../common/Style.jsp"%>

<script>
$(document).ready(function(){
	parent.$.messager.progress('close'); 

});
</script>
</head>
<body>

	<table  class="easyui-datagrid"  style="width:900px;height:700px" data-options="singleSelect:true,border : false">
					  <thead>
					    <th data-options="field:'1',width:40">No.</th>
					    <th data-options="field:'2',width:40"></th>
					    <th data-options="field:'3',width:150">连锁店名称</th>
					    <th data-options="field:'4',width:120">经营者</th>
					    <th data-options="field:'6',width:500">邮寄联系地址</th>
					  </thead>
				      <s:iterator value="uiBean.chainStoreVOs" status="st" id="chainStore" >
						    <tr class="InnerTableContent">	      
						      <td><s:property value="#st.index +1 + formBean.pager.firstResult"/></td>
						      <td>&nbsp;<s:property value="#chainStore.pinYin.substring(0,1) "/></td>
						      <td><s:property value="#chainStore.chain_name"/></td>
						      <td><s:property value="#chainStore.owner_name"/></td>
						      <td><s:property value="#chainStore.shippingAddress"/></td>
						    </tr>
				       </s:iterator>	

	</table>
</body>
</html>