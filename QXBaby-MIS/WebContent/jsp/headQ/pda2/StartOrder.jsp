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
	$("#barcode").focus();

})
document.onkeyup = BSkeyUp; 

function BSkeyUp(e){
	var ieKey = window.event ? e.keyCode : e.which;

	 if (ieKey==13){
	   if (e.target.id=="barcode"){
	       var barcode =$("#barcode").val();
	       e.returnValue=false;
	       if (barcode != ""){
	    	  // alert("good");
	    	   checkSearch();
	       }
	   } 
	 } 
} 
function clearProduct(){
	$("#productCode").focus();
	$("#productCode").attr("value","");
	
    $("#products").hide();
    
    $('#productBody tr').each(function () {                
        $(this).remove();
    });

}
function checkSearch(){
	if ($.trim($("#barcode").val()).length == 12)
		addOrder();
}
function addOrder(){
	if (validateSearch()){
		var barcode = $("#barcode").val();

		$.mobile.loading("show",{ theme: "b", text: "正在加载数据", textonly: false});
		var params="formBean.barcode=" + barcode+ "&formBean.quantity=1";

		$("#barcodeHidden").attr("value",barcode);
		
		$.post('<%=request.getContextPath()%>/action/ipadJSON!orderByBarcode', params, 
		function(result) {
			$.mobile.loading("hide");
			if (result.returnCode != 2) {
				$("#msg").html(result.message);
				$("#remBtnDiv").hide();
			} else {
				$("#msg").html(result.returnValue.product);
				$("#remBtnDiv").show();
			}
				
			$("#barcodeHidden").attr("value",barcode);
			$("#barcode").attr("value","");
			$("#barcode").focus();

		}, 'JSON');
		
	}
}
function validateSearch(){

	if ($.trim($("#barcode").val()).length < 12){
		renderPopup("查询错误","条码错误");
		$("#barcode").focus();
		return false;
	} else 
		return true;
}

function deductOrder(){
	if ($.trim($("#barcodeHidden").val()).length >= 12){
		var barcode = $("#barcodeHidden").val();

		$.mobile.loading("show",{ theme: "b", text: "正在加载数据", textonly: false});
		var params="formBean.barcode=" + barcode+ "&formBean.quantity=-1";

		$.post('<%=request.getContextPath()%>/action/ipadJSON!orderByBarcode', params, 
		function(result) {
			$.mobile.loading("hide");
			if (result.returnCode != 2) {
				$("#msg").html(result.message);
			} else {
				$("#msg").html(result.returnValue.product);
			}
				
			$("#barcode").attr("value","");
			$("#barcode").focus();

		}, 'JSON');
		
	} else {
		renderPopup("查询错误","条码错误");
		$("#barcode").focus();
	}
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
						<td><label for="productCode">条码: </label></td> 
						<td><input id="barcode" name="barcode" placeholder="扫描条码"/></td>
					</tr>

				</table>

				<div id="msg" style="display:show">
						
				</div>
				<div id="remBtnDiv" style="display:none">
				        <input type="hidden" id="barcodeHidden" name="barcodeHidden"/>
						<input name='remBtn' type='button' value='退订' data-mini='true'  data-inline='true' onclick='deductOrder();'/>
				</div>				
		</div>


		<div data-role="footer" data-theme="b" data-position="fixed" data-tap-toggle="false">
			<div data-role="navbar">
		      <ul>
		      	<li><a href="<%=request.getContextPath()%>/action/ipadJSP!goToEditCustPDA" data-icon="edit" data-ajax="false">选择客户</a></li>
		      	<li><a href="<%=request.getContextPath()%>/jsp/headQ/pda2/StartOrder.jsp" data-icon="edit" data-ajax="false" class="ui-btn-active ui-state-persist">选货</a></li>
		      	<li><a href="<%=request.getContextPath()%>/action/ipadJSP!viewCurrentOrderPDA" data-icon="bullets" data-ajax="false">选货详情</a></li>
		        <li><a href="<%=request.getContextPath()%>/action/ipadJSP!listDraftOrderPDA" data-icon="bullets" data-ajax="false">草稿订单</a></li>
		      </ul>
		     </div>
		</div> 
		<jsp:include  page="Popup.jsp"/>

	</div>

</body>
</html>