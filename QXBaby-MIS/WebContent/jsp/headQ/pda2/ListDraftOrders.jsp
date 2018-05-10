<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %> 
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>  
<%@taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="Cache" content="no-cache">
<meta http-equiv="expires" content="0"> 
<meta name="viewport" content ="width=device-width, initial-scale=1">
<%@ include file="JQMStyle.jsp"%>
<script>

function editOrder(orderId){
	$.mobile.loading("show",{ theme: "b", text: "正在加载数据", textonly: false});
	var params="formBean.orderId=" + orderId;

	$.post('<%=request.getContextPath()%>/action/ipadJSON!editOrder', params, 
	function(result) {
		$.mobile.loading("hide");
		if (result.returnCode == 2) {
			window.location.href = "<%=request.getContextPath()%>/action/ipadJSP!viewCurrentOrderPDA"
		} else 
			renderPopup("系统错误",result.message)
	}, 'JSON');
}

function tryToDeleteOrder(orderId){
	  $("#confirmPopup").popup("open");
	  $("#orderId").attr("value", orderId);
}

function yesToDeleteOrder(){
	$("#confirmPopup").popup("close");
	$.mobile.loading("show",{ theme: "b", text: "正在加载数据", textonly: false});
	
	var params="formBean.orderId=" + $("#orderId").val();

	$.post('<%=request.getContextPath()%>/action/ipadJSON!deleteOrder', params, 
	function(result) {
		$.mobile.loading("hide");
		if (result.returnCode == 2) {
			window.location.href = "<%=request.getContextPath()%>/action/ipadJSP!listDraftOrderPDA"
		} else 
			renderPopup("系统错误",result.message)
	}, 'JSON');
}
function noToDeleteOrder(){
	 $("#confirmPopup").popup("close");
}
</script>
</head>
<body>
<!-- 按照品牌汇总的 MyOrder 汇总 -->
	<div id="myOrder" data-role="page">
		<script>
		    
		</script>
		<jsp:include  page="MobileHeader.jsp"/>

		<div  data-role="content" class="content">
				<div id="products" style="display:show">
					<table data-role="table" id="table-column-toggle" class="ui-responsive table-stroke">
						<thead>
					       <tr>
					         <th></th>
					         <th data-priority="1">客户名字</th>
					         <th width="20%">日期</th>
					         <th width="15%">数量</th>
					         <th width="12%" data-priority="2">总批发价</th>
					         <th width="27%"></th>
					       </tr>
					     </thead>
					     <tbody id="productBody">
					        <s:iterator value="uiBean.orders" status = "st" id="p" >
							 	<tr>
							 		<td><s:property value="#st.index+1"/></td>
							 		<td><s:property value="#p.clientName"/></td>	
							 		<td><s:property value="#p.createDate"/></td>			 					 		
							 		<td><s:property value="#p.totalQ"/></td>	
							 		<td><s:property value="#p.totalW"/></td> 	
							 		<td><input name='EditBtn' type='button' value='修改' data-mini='true'  data-inline='true' onclick='editOrder(<s:property value="#p.orderId"/>);'/>
							 		    <input name='DeleteBtn' type='button' value='删除' data-mini='true'  data-inline='true' onclick='tryToDeleteOrder(<s:property value="#p.orderId"/>);'/>
							 		</td>				 		
							 	</tr>
						 	</s:iterator>
					     </tbody>
				    </table>	
				</div>
		</div>


		<div data-role="footer" data-theme="b" data-position="fixed" data-tap-toggle="false">
			<div data-role="navbar">
		      <ul>
		      	<li><a href="<%=request.getContextPath()%>/action/ipadJSP!goToEditCustPDA" data-icon="edit" data-ajax="false">选择客户</a></li>
		      	<li><a href="<%=request.getContextPath()%>/jsp/headQ/pda2/StartOrder.jsp" data-icon="edit" data-ajax="false">选货</a></li>
		      	<li><a href="<%=request.getContextPath()%>/action/ipadJSP!viewCurrentOrderPDA" data-icon="bullets" data-ajax="false">选货详情</a></li>
		        <li><a href="<%=request.getContextPath()%>/action/ipadJSP!listDraftOrderPDA" data-icon="bullets" data-ajax="false" class="ui-btn-active ui-state-persist">草稿订单</a></li>
		      </ul>
		     </div>
		</div> 
		<jsp:include  page="Popup.jsp"/>
		<div data-role="popup" id="confirmPopup"
                    data-dismissible="true" style="max-width: 400px; min-width: 250px"
                    class="ui-popup ui-body-inherit ui-overlay-shadow ui-corner-all">
                    <div data-role="header">
                        <h3 id="errorHeader">是否删除订单</h3>
                        <a class="ui-btn-right" href="#" data-role="button" data-iconpos="notext" data-icon="delete" data-rel="back" data-theme="a">Close</a>
                    </div>
                    <div role="main" class="ui-content">
                        <input name="orderId" id="orderId" type="hidden"/>
                        <input name='yesBtn' type='button' value='确认' data-mini='true'  data-inline='true' onclick='yesToDeleteOrder();'/>
                        <input name='noBtn' type='button' value='取消' data-mini='true'  data-inline='true' onclick='noToDeleteOrder();'/>	
                    </div>
          </div> 
	</div>

</body>
</html>