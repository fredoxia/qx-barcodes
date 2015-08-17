<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.onlineMIS.ORM.entity.headQ.user.UserInfor" %>
<%@taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>我的任务</title>
<script>
var index = parseInt("<s:property value='formBean.order.product_List.size()'/>");
var selectedIndex;
var formSubmit = false;
var quantityReg = /quantity+\d/;	
document.onkeyup = BSkeyUp; 

function BSkeyUp(e){
	 var ieKey = event.keyCode;

	 if (ieKey==13){
	   if (event.srcElement.id=="barcode"){
	       var barcode =$("#barcode").val();
	       event.returnValue=false;
	       if (barcode != "")
	          retrieveProductByBarcode(index, "", barcode);
	   } 
	 } else if (ieKey == 40){
	   var sourceId = event.srcElement.id;
	   if (sourceId == "clientPY")
		   $("#barcode").select();
	   else if (sourceId == "")
		  $("#clientPY").select();
	   else if (sourceId == "barcode" || sourceId == "clientID"){
		   var inQuantity = false;
          var rowIndex = 0;
          for (var i = index; i >= rowIndex; i--){
            if ($("#quantity" + i).length >0){
            	$("#quantity" + i).select();
            	inQuantity = true;
            	break;
            }
          } 

          if (!inQuantity)
       	    $("#last").focus();
	   } else if (quantityReg.test(sourceId)){
		   var inQuantity = false;
           var rowIndex = parseInt(sourceId.substring(8)) - 1;
           for (var i = rowIndex; i >= 0; i--){
             if ($("#quantity" + i).length >0){
             	$("#quantity" + i).select();
             	inQuantity = true;
             	break;
             }
           } 

           if (!inQuantity)
        	   $("#last").focus();
	   } else 
		   $("#clientPY").select();

	   event.returnValue=false;
	 } else if (ieKey == 38){
		   var sourceId = event.srcElement.id;
		   if (sourceId == "clientPY")
			  $("#last").focus();
		   else if (sourceId == "")
			  $("#last").focus();
		   else if (sourceId == "last"){
			  var inQuantity = false;
	          var rowIndex = 0;
	          for (var i = rowIndex; i <=index ; i++){
	            if ($("#quantity" + i).length >0){
	            	$("#quantity" + i).select();
	            	inQuantity = true;
	            	break;
	            }
	          } 

	          if (!inQuantity)
	       	    $("#barcode").select();
		   } else if (quantityReg.test(sourceId)){
			   var inQuantity = false;
	           var rowIndex = parseInt(sourceId.substring(8)) + 1;
	           for (var i = rowIndex; i <= index ; i++){
	             if ($("#quantity" + i).length >0){
	             	$("#quantity" + i).select();
	             	inQuantity = true;
	             	break;
	             }
	           } 

	           if (!inQuantity)
	        	   $("#barcode").select();
		   } else if (sourceId == "barcode")
			   $("#clientPY").select();
		   else 
			   $("#last").focus();

	   event.returnValue=false;
	 } else if (ieKey == 113) {
	   submitOrder();
	 } else if (ieKey == 112) {
	   draftOrder();
	 } else if (ieKey == 114) {
	   reOrder();
	 } else if (ieKey == 106) {
		 calculateTotal();
	 } else {
	   if (event.srcElement.id=="clientPY"){
		   searchClient();
	   } 
     }
} 

var MSG_UNLOAD="你确定将要退出订单输入吗?";
var UnloadConfirm = {};
UnloadConfirm.set = function(confirm_msg){
   window.onbeforeunload = function(event){
       event = event || window.event;

       if (formSubmit == false)
          event.returnValue = confirm_msg;
       else
    	  formSubmit = false;
   }
}
UnloadConfirm.set(MSG_UNLOAD);

function retrieveProductByBarcode(index_trigger, suffix ,currentBarcode){
	var clientId = $("#clientID").val(); 
	if (clientId == null || clientId == undefined)
		clientId =0;
	var params= "formBean.productBarcode.barcode=" + currentBarcode + "&formBean.client_id=" + clientId; 

    $.post("<%=request.getContextPath()%>/action/productJSONAction!getProductFromPDA",params, backProcess,"json");
}

function backProcess(data){
	var barcodes = data.barcodes;

    if (barcodes.length != 0){

    	var barcode = barcodes[0].barcode;
		var productCode = barcodes[0].product.productCode;
		var numPerHand = barcodes[0].product.numPerHand;
		var brandName = barcodes[0].product.brand.brand_Name;
		var wholePrice = barcodes[0].product.lastChoosePrice;
		var color = barcodes[0].color.name;
		if (color == undefined)
			color ="-";
/*		var size = barcodes[0].size.name;
		if (size == undefined)
			size ="-";*/
	    var getBefore = barcodes[0].boughtBefore;
		var inventory = data.inventory;
		var hasInput = false;
        for (var i = 0; i <= index ; i++){
            if ($("#barcode" + i).val() == barcode){
            	var quantity_org = parseInt($("#quantity" + i).val());
            	$("#quantity" + i).attr("value", quantity_org + numPerHand);
            	hasInput = true;
            	break;
            }
        } 

        if (hasInput == false) {
			var row = "<tr style='font-size:11px' id='row"+ index +"'><td>"+productCode +
			          "<input type='hidden' id='barcode"+index+"' name='formBean.order.product_List["+index+"].productBarcode.barcode' value='"+barcode+"'/>"+
			          "</td><td>"+ brandName + 
			          "</td><td>"+ color + 
	//		          "</td><td>"+ size + 
			          "</td><td><input type='text' id='price"+ index +"' class='inputPrice' readonly value='"+wholePrice.toFixed(1)+"'/>"+
			          "</td><td>"+inventory+
			          "</td><td>"+getBefore+
			          "</td><td><input type='text' name='formBean.order.product_List["+index+"].quantity'  class='inputQuantity' id='quantity" + index + "' value='"+numPerHand+"' onkeypress='return is_number(event);' onchange='calculateTotal();'/>"+
			          "</td><td><a href='#' onclick='delRow("+ index +")'>x</a></td></tr>";
			$("#productList").prepend(row);			
			index++;
        }
		$("#barcode").attr("value","");

		if (index < 15)
		   calculateTotal();
    } else {
    	alert("条码扫描错误");
    	
    	$("#barcode").select();
    	$("#barcode").attr("value","");
    }
}
function calculateTotal(){
	 var totalQ = 0;
	 var totalP = 0;
	 
	 for (var i =0; i < index; i++){
		 var quantityInputs = $("#quantity" + i).val();
		 if (quantityInputs != undefined){
			 totalQ = totalQ + parseInt(quantityInputs);
		 } 

		 var priceInputs =  $("#price" + i).val();
		 if (priceInputs != undefined && quantityInputs!=undefined){
			 var quantity = parseInt(quantityInputs);
			 var price = parseFloat(priceInputs);
			 totalP = totalP + price * quantity;
		 } 
	 }
	 
	 $("#totalQ").attr("value",totalQ);
	 $("#totalP").attr("value",totalP.toFixed(0));
}
function delRow(rowId){
	$("#row"+rowId).remove(); 
	$("#barcode").select();
	alert("成功删除");

	if (index < 15)
	   calculateTotal();
}
function searchClient(){
	var clientPY= $("#clientPY").val(); 

	if (clientPY.length >= 2){
		$("#regionDiv").html("");
        var params = "formBean.pinyin=" + clientPY;
		
        $.post("<%=request.getContextPath()%>/action/inventoryOrderJSON!getClientName",params, getClientBackProcess,"json");
    }
}
function getClientBackProcess(data){
	var clients = data.clients;

	$("#clientID").empty();
	if (clients.length != 0){
		for (var i = 0; i < clients.length; i++)
           $("#clientID").append("<option value='"+clients[i].client_id+"'>"+clients[i].name+"</option>"); 
        if (clients.length == 1){
            getClientById();
        }
	}
}
function getClientById(){
	var clientId = $("#clientID").val();
	var params = "formBean.order.client_id=" + clientId;
	$.post("<%=request.getContextPath()%>/action/inventoryOrderJSON!getClientRegion",params, getClientByIdBackProcess,"json");	
}
function getClientByIdBackProcess(data){
	var client = data.client;
	if (client != null)
		$("#regionDiv").html("客户地址 " + client.region.name);
}
function submitOrder(){
	var info = "确认提交单据";
	if (validateOrder() && confirm(info)){
        var params = $("#inventoryOrderForm").serialize();
		
        $.post("<%=request.getContextPath()%>/action/inventoryOrderJSON!pdaSubmitOrder",params, submitBkProcess,"json");
    }
}
function draftOrder(){
	var info = "确认提交草稿";
	if (validateOrder() && confirm(info)){
        var params = $("#inventoryOrderForm").serialize();
		
        $.post("<%=request.getContextPath()%>/action/inventoryOrderJSON!pdaSubmitDraft",params, submitBkProcess,"json");
    }
}
function logoff(){
	window.location.href= "<%=request.getContextPath()%>/action/userJSP!pdaLogoff";
}

function reOrder(){
	window.location.href='<%=request.getContextPath()%>/jsp/headQ/PDA/PDAMainMenu.jsp';
}


function submitBkProcess(data){
	var response = data.response;
	if (response.returnCode != SUCCESS){
		alert("错误 : " + response.message);
	} else {
        alert("成功提交单据，返回");
        window.location.href='<%=request.getContextPath()%>/action/inventoryOrder!createPDAOrder';
	}
}

function validateOrder(){
	var hasChar = false;
	var hasChar_w = false;
	var error ="";

	if ($("#clientID").val() == null || $("#clientID").val()==0)
		error += "请输入客户名字后再提交\n";
    if ($("#totalQ").val() == 0)
        error += "请扫描货品后再提交\n";
	
	for (var i =0; i < index; i++){
 		var q = $("#quantity" + i).val();
		var w = $("#price" + i).val();

 		if (q != undefined){
			if (isNaN(q) || q<0){
				$("#row" + i).css('background-color', '#EE8553');
				hasChar = true;
			}
		}

		if (w != undefined){
			if (isNaN(w) || w<=0){
				$("#row" + i).css('background-color', '#EE8553');
				hasChar_w = true;
			}
		}
	}
	
	if (hasChar)
		error += "数量 - 不能为负数. 请检查后重新输入!\n";
	if (hasChar_w)
		error += "批发价格 - 必须为大于0数字. 请检查后重新输入!\n";	

	if (error != ""){
		alert(error);
		return false;
	}else{
		formSubmit = true;
		return true;
	}
}
</script>
</head>
<body>
    <jsp:include page="../../common/PDAStyle.jsp"/>
	<s:form action="/action/inventoryOrder!pdaSubmitOrder" method="POST" name="inventoryOrderForm"  id="inventoryOrderForm" theme="simple">
	<s:hidden name="formBean.order.order_ID" id="orderId"/>
	<table style="width: 100%" align="left">
		<tr>
			<td colspan="8">客户拼音&nbsp;<input type="text" id="clientPY"  class="input"/><br/>
			                                     客户名字&nbsp;<s:select id="clientID" list="formBean.clientMap"  listKey="key" listValue="value"  name="formBean.order.client_id" cssClass="inputSelect" onchange="getClientById();"/><br/>
			                <div id="regionDiv"></div></td>
		<tr>
		<tr><td colspan="8"><hr/></td></tr>
		<tr>
			<td colspan="8">条码输入&nbsp;<input type="text" class="input" size="12" id="barcode" class="input"/></td>	
		</tr>
		<tr>
		    <td>货号</td><td>品牌</td><td>颜色</td><td>价格</td><td>库存</td><td>已订</td><td>数量</td><td></td>
		</tr>
		<tbody id="productList">
			<s:iterator value="formBean.order.product_List" status = "st" id="orderProduct" >
				<tr style='font-size:11px' id="row<s:property value="formBean.order.product_List.size() - #st.index -1"/>">
				    <td><input type='hidden' name='formBean.order.product_List[<s:property value="formBean.order.product_List.size() - #st.index -1"/>].ID' value='<s:property value="#orderProduct.ID"/>'/>
						<input type='hidden' id='barcode<s:property value="formBean.order.product_List.size() - #st.index -1"/>' name='formBean.order.product_List[<s:property value="formBean.order.product_List.size() - #st.index -1"/>].productBarcode.barcode' value='<s:property value="#orderProduct.productBarcode.barcode"/>'/>
						<s:property value="#orderProduct.productBarcode.product.productCode"/></td>
					<td><s:property value="#orderProduct.productBarcode.product.brand.brand_Name"/></td>
					<td><s:property value="#orderProduct.productBarcode.color.name"/></td>
					<td><input type="text" class='inputPrice' id="price<s:property value="formBean.order.product_List.size() - #st.index -1"/>" value="<s:property value="#orderProduct.wholeSalePrice"/>"/></td>
					<td></td>
					<td></td>
					<td><input type='text' name='formBean.order.product_List[<s:property value="formBean.order.product_List.size() - #st.index -1"/>].quantity'  class='inputQuantity' id='quantity<s:property value="formBean.order.product_List.size() - #st.index -1"/>' value='<s:property value="#orderProduct.quantity"/>' onkeypress='return is_number(event);' onchange='calculateTotal();'/></td>
					<td><a href='#' onclick='delRow(<s:property value="formBean.order.product_List.size() - #st.index -1"/>)'>x</a></td>
				</tr>
			</s:iterator>
		</tbody>
		<tr><td colspan="8"><hr/></td></tr>
		<tr>
		    <td colspan="3">汇总</td><td colspan="2"><s:textfield name="formBean.order.totalWholePrice" id="totalP" cssClass="inputPriceTotal" readonly="true" size="4"/></td><td></td><td><s:textfield name="formBean.order.totalQuantity" id="totalQ" cssClass="inputPrice" readonly="true"/></td><td></td>
		</tr>	
		<tr>
		    <td colspan="8">
		    <input type="button" value="提交 F2" onclick="submitOrder();" class="inputButton"/>
		    <input type="button" value="存草稿 F1" onclick="draftOrder();" class="inputButton"/>
		    <input type="button" value="菜单 F3" onclick="reOrder();" class="inputButton"/>
		    <input type="button" value="汇总 *" onclick="calculateTotal();" class="inputButton"/>
		    <input type="button" value="退出" onclick="logoff();" class="inputButton"/>
		    <a href="#" id="last">.</a>
		    </td>
		</tr>		
	</table>
	</s:form>
	<script>
	$(document).ready(function(){
		if ($("#orderId").val() != 0)
			$("#barcode").select();
		else 
		    $("#clientPY").select();
		});
	</script>
</body>
</html>