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
$(document).ready(function(){
	$("#productCode").focus();

})
function clearProduct(){
	$("#productCode").focus();
	$("#productCode").attr("value","");
	
    $("#products").hide();
    
    $('#productBody tr').each(function () {                
        $(this).remove();
    });

}
function checkSearch(){
	if ($.trim($("#productCode").val()).length >= 3)
		searchProduct();
}
function searchProduct(){
	if (validateSearch()){
		var params = "formBean.productCode=" + $("#productCode").val();

		$.mobile.loading("show",{ theme: "b", text: "正在加载数据", textonly: false});
		
		$.post('<%=request.getContextPath()%>/action/ipadJSON!searchByProductCode', params, 
		function(result) {
			
			if (result.returnCode == 2) {
			    $('#productBody tr').each(function () {                
			        $(this).remove();
			    });
			    
			    var cops = result.returnValue;
			    if (cops != null && cops.length != 0){
				    for (var i = 0; i < cops.length; i++){
				    	
				    	var j = i +1;
				        if (cops[i] != "")  {
					          $("<tr id='pRow"+cops[i].id+"'><td style='vertical-align:middle;'>"+
					        		  cops[i].brand +"</td><td style='vertical-align:middle;'>"+
					        		  cops[i].productCode+"</td><td style='vertical-align:middle;'>"+
					        		  cops[i].color+"</td><td style='vertical-align:middle;'>"+
					        		  cops[i].wholeSalePrice+"</td><td>"+
										"<div name='btnGroup' data-role='controlgroup' data-type='horizontal'>"+
											"<input name='addBtn' type='button' value='加订' data-mini='true'  data-inline='true' onclick='addOrder("+cops[i].id+");'/>"+
										"</div>"+
							          "</td></tr>").appendTo("#productBody");
				        }
				    }
			    } else {
			    	$("<tr><td colspan=5><font color='red'>没有查询到产品</font> </td></tr>").appendTo("#productBody");
			    }
			    
			    $("#products").show();
			    $("[name='addBtn']").button();
			    $("[name='btnGroup']").controlgroup();

			    $.mobile.loading("hide");
			} else {
				$.mobile.loading("hide");
				renderPopup("系统错误",result.msg)
			}
		}, 'JSON');
	}
}
function validateSearch(){

	if ($.trim($("#productCode").val()).length < 3){
		renderPopup("查询错误","请输入至少三位货号作为查询条件");
		$("#productCode").focus();
		return false;
	} else 
		return true;
}
function myOrder(pbId, quantity){
	$.mobile.loading("show",{ theme: "b", text: "正在加载数据", textonly: false});
	var params="formBean.pbId=" + pbId + "&formBean.quantity=" + quantity;

	$.post('<%=request.getContextPath()%>/action/ipadJSON!orderProduct', params, 
	function(result) {
		$.mobile.loading("hide");
		if (result.returnCode != 2) {
			renderPopup("系统错误",result.message)
		}
	}, 'JSON');
}
function deductOrder(pbId){
	myOrder(pbId, -1);
}
function addOrder(pbId){
	myOrder(pbId, 1);
}

</script>
</head>
<body>
<!-- 按照品牌汇总的 MyOrder 汇总 -->
	<div id="myOrder" data-role="page">
		<script>
		    
		</script>
		<jsp:include  page="ViewCurrentOrderHeader.jsp"/>

		<div  data-role="content" class="content">
				<div id="products" style="display:show">
					<table data-role="table" id="table-column-toggle" class="ui-responsive table-stroke">
						<thead>
					       <tr>
					         <th></th>
					         <th data-priority="1">品牌</th>
					         <th width="20%">货号</th>
					         <th width="15%">数量</th>
					         <th width="12%" data-priority="2">总批发价</th>
					         <th width="27%"></th>
					       </tr>
					     </thead>
					     <tbody id="productBody">
					        <s:iterator value="uiBean.orderProducts" status = "st" id="p" >
							 	<tr>
							 		<td><s:property value="#st.index+1"/></td>
							 		<td><s:property value="#p.brand"/></td>	
							 		<td><s:property value="#p.productCode"/> <s:property value="#p.color"/></td>			 					 		
							 		<td><s:property value="#p.quantity"/></td>	
							 		<td><s:property value="#p.wholeSalePrice"/></td> 	
							 		<td></td>				 		
							 	</tr>
						 	</s:iterator>
					     </tbody>
					     <tr align="center"  height="10" class="InnerTableContent" >
					  	     <td>&nbsp;</td>
							 <td>总数</td>			 					 		
							 <td>&nbsp;</td>
							 <td><s:property value="uiBean.totalQ"/></td>			 					 		
							 <td><s:property value="uiBean.totalW"/></td>	
							 <td>&nbsp;</td>		 					 		
					    </tr>
				    </table>	
				</div>
		</div>


		<div data-role="footer" data-theme="b" data-position="fixed" data-tap-toggle="false">
			<div data-role="navbar">
		      <ul>
		      	<li><a href="<%=request.getContextPath()%>/action/ipadJSP!goToEditCust" data-icon="edit" data-ajax="false">选择客户</a></li>
		      	<li><a href="<%=request.getContextPath()%>/jsp/headQ/ipad/StartOrder.jsp" data-icon="edit" data-ajax="false">选货</a></li>
		      	<li><a href="<%=request.getContextPath()%>/action/ipadJSP!viewCurrentOrder" data-icon="bullets" data-ajax="false" class="ui-btn-active ui-state-persist">选货详情</a></li>
		        <li><a href="<%=request.getContextPath()%>/action/ipadJSP!listDraftOrder" data-icon="bullets" data-ajax="false">草稿订单</a></li>
		      </ul>
		     </div>
		</div> 
		<jsp:include  page="Popup.jsp"/>

	</div>

</body>
</html>