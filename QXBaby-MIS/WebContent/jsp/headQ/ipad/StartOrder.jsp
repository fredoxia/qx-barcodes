<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %> 
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>  
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
	if ($.trim($("#productCode").val()).length >= 4)
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
					        		  cops[i].productCode + cops[i].color +"</td><td style='vertical-align:middle;'>"+
					        		  cops[i].wholeSalePrice+"</td><td style='vertical-align:middle;'>"+
					        		  cops[i].inventory+"</td><td style='vertical-align:middle;'>"+
					        		  cops[i].orderHis+"</td><td style='vertical-align:middle;' id='cQ"+cops[i].id+"'>"+
					        		  cops[i].orderCurrent+"</td><td>"+
										"<div name='btnGroup' data-role='controlgroup' data-type='horizontal'>"+
											"<input name='addBtn' type='button' value='加订' data-mini='true'  data-inline='true' onclick='addOrder("+cops[i].id+");'/>"+
											"<input name='remBtn' type='button' value='退订' data-mini='true'  data-inline='true' onclick='deductOrder("+cops[i].id+");'/>"+
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
			renderPopup("系统错误",result.message);
		} else {
			var returnValue = result.returnValue;
			var quantity = returnValue.cq;
			var pbId = returnValue.pbId;
			
			$("#cQ" + pbId).html(quantity);
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
		<jsp:include  page="MobileHeader.jsp"/>

		<div  data-role="content" class="content">
				<table>
				    <tr>
						<td><label for="productCode">货号: </label></td> 
						<td><input id="productCode" type="number" name="productCode" placeholder="输入至少四位货号" onkeyup="checkSearch();"/></td>
					</tr>

				</table>
				<div class="ui-grid-a ui-responsive">
    				<div class="ui-block-a"><input type="button" id="searchBnt" data-theme="a" onclick ="searchProduct();" value="查找货品"/></div>
    				<div class="ui-block-b"><input type="button" id="clearBnt" data-theme="b" onclick ="clearProduct();" value="清空货品"/></div>
				</div>
				<div id="products" style="display:none">
					<table data-role="table" id="table-column-toggle" class="ui-responsive table-stroke">
						<thead>
					       <tr>
					         <th width="20%" data-priority="1">品牌</th>
					         <th width="20%" data-priority="2">货号</th>
					         <th width="12%">批发价</th>
					         <th width="10%">库存</th>
					         <th width="10%">已定</th>
					         <th width="10%">已选</th> 
					         <th width="27%"></th>
					       </tr>
					     </thead>
					     <tbody id="productBody">
					     </tbody>
				    </table>	
				</div>
		</div>


		<div data-role="footer" data-theme="b" data-position="fixed" data-tap-toggle="false">
			<div data-role="navbar">
		      <ul>
		      	<li><a href="<%=request.getContextPath()%>/action/ipadJSP!goToEditCust" data-icon="edit" data-ajax="false">选择客户</a></li>
		      	<li><a href="<%=request.getContextPath()%>/jsp/headQ/ipad/StartOrder.jsp" data-icon="edit" data-ajax="false" class="ui-btn-active ui-state-persist">选货</a></li>
		      	<li><a href="<%=request.getContextPath()%>/action/ipadJSP!viewCurrentOrder" data-icon="bullets" data-ajax="false">选货详情</a></li>
		        <li><a href="<%=request.getContextPath()%>/action/ipadJSP!listDraftOrder" data-icon="bullets" data-ajax="false">草稿订单</a></li>
		      </ul>
		     </div>
		</div> 
		<jsp:include  page="Popup.jsp"/>

	</div>

</body>
</html>