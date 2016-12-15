<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib prefix="s" uri="/struts-tags" %>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>千禧宝贝连锁店管理信息系统</title>
<%@ include file="../../common/Style.jsp"%>
<link href="<%=request.getContextPath()%>/conf_files/css/pagination.css" rel="stylesheet" type="text/css"/>
<SCRIPT src="<%=request.getContextPath()%>/conf_files/js/pagenav1.1.js" type=text/javascript></SCRIPT>
<script>
$(document).ready(function(){
	renderPaginationBar($("#currentPage").val(), $("#totalPage").val());
	parent.$.messager.progress('close'); 
	});

pageNav.fn = function(page,totalPage){                
	$("#currentPage").attr("value",page);
    document.chainListForm.action="inventoryFlowJSPAction!preGetCurrentInventory";
    document.chainListForm.submit();
};
function getLevelOne(chainId){
	$.messager.progress({
		title : '提示',
		text : '数据处理中，请稍后....'
	});
	window.location.href = "inventoryFlowJSPAction!getLevelOneCurrentInventory?formBean.chainId=" + chainId;
}
</script>
</head>
<body>

   <s:form action="/actionChain/inventoryFlowJSPAction!preGetCurrentInventory" method="POST" name="chainListForm" id="chainListForm" theme="simple"> 
	<%@ include file="../../common/pageForm.jsp"%>
	<table width="70%" align="center"  class="OuterTable">
	    <tr><td>
	
		 <table width="100%" border="0">
		    <tr class="PBAOuterTableTitale">
		       <td height="50" colspan="3">连锁店当前库存</td>
	        </tr>
		    <tr>
		      <td colspan="3">
		            <!-- table to display the staff information -->
					<table width="100%"  align="left" class="OuterTable" id="org_table">
					  <tr class="PBAInnerTableTitale" align='left'>
					    <th width="40" height ="25">No.</th>
					    <th width="30"></th>
					    <th width="160">连锁店名称</th>
					    <th width="160">连锁店经营者</th>
					    <th></th>
					  </tr>
				      <s:iterator value="uiBean.chainStores" status="st" id="chainStore" >
						    <tr class="InnerTableContent" <s:if test="#st.even">style='background-color: rgb(255, 250, 208);'</s:if>>	      
						      <td><s:property value="#st.index +1 + formBean.pager.firstResult"/></td>
						      <td><s:property value="#chainStore.pinYin.substring(0,1) "/></td>
						      <td><s:property value="#chainStore.chain_name"/></td>
						      <td><s:property value="#chainStore.owner_name"/></td>
						      <td><a href='javascript:getLevelOne(<s:property value="#chainStore.chain_id"/>)'><img src='<%=request.getContextPath()%>/conf_files/web-image/editor.gif' border='0'/></a></td>
						    </tr>
				       </s:iterator>	
				       <s:if test="uiBean.chainStores.size == 0">
						    <tr class="InnerTableContent">	      
						      <td colspan="5">没有记录</td>
						     </tr>				       
				       </s:if><s:else>
						    <tr class="InnerTableContent">	      
						      <td colspan="5"><div id="pageNav"></div></td>
						    </tr>					       
				       </s:else>  
					</table></td>

		    </tr>
		    <tr class="InnerTableContent">
		      <td height="4" colspan="5"><hr width="100%" color="#FFCC00"/></td>

		    </tr>
		  </table>
	   </td></tr>
	 </table>
	 </s:form>
</body>
</html>