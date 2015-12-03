//variable to identify it is form submit or close the page
var formSubmit = false;
var P_NUMBER = 2;
var N_PRINT = 1;
var P_DISCOUNT = 3;
var index = 0;

//constants
var SALES_ORDER = 1;
var RETURN_ORDER = 2;

var errorIndexes = new Array();

document.onkeydown = BSkeyDown; 
function BSkeyDown(e){

	 var ieKey = event.keyCode;
	 //enter key is pressed 
	 if (ieKey==13){
	   if (event.srcElement.id.substring(0,7)=="barcode"){
           var index_trigger = event.srcElement.id.substring(7); 

		   var currentBarcode =$("#barcode"+index_trigger).val();
	       event.returnValue=false;
	
	       retrieveProductByBarcode(index_trigger,"" , currentBarcode);
	   } else if (event.srcElement.id.substring(0,11)=="productCode"){
		   var index_trigger = event.srcElement.id.substring(11);
		   event.returnValue=false;
		   searchProductsProductCodeTF(index_trigger);
	   }else
		   event.returnValue=false;
	    
	 //back space key
	 }  else if (ieKey==8){
		    if((event.srcElement.tagName.toLowerCase() == "input" && event.srcElement.readOnly != true) || event.srcElement.tagName.toLowerCase() == "textarea"){
		        event.returnValue = true; 
		    } else
		    	event.returnValue = false;
	 }
} 


/**
 * user input the product code and press enter
 */
function searchProductsProductCode(index_trigger){
	var productCode = $.trim($("#productCode" +index_trigger).val());

	if (validateProductCodeInput(productCode)){
	    var url = encodeURI(encodeURI("actionChain/chainSalesJSPAction!scanByProductCode?" + "formBean.productCode=" + productCode + "&formBean.index=" + index_trigger +"&formBean.chainId=" + $("#chainStore").val())) ; 
		
	    window.open(url,'新窗口','height=530, width=600, toolbar=no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=no');    
	}
}

/**
 * retrieve the product by input barcode
 */
function retrieveProductByBarcode(index_trigger, suffix, currentBarcode){
	if (validateRowInput(index_trigger,currentBarcode)){		
	    var params= "formBean.barcode=" + currentBarcode + "&formBean.index=" + index_trigger+ "&formBean.chainId=" + $("#chainStore").val() ; 

	    $.post("actionChain/chainSalesJSONAction!scanByBarcode",params, backRetrievePByBarcodeProcess,"json");

	}
}

/**
 * to validate the product code input
 */
function validateProductCodeInput(productCode){
	if (productCode.length <= 1){
       alert("输入的货号太短");
       return false;
	}
	return true;
}


/**
 * to clear the corresponding row's data
 */
function clearRowCells(index_c){
	//clear the cell
	$("#productId" +index_c).attr("value","");  
   	$("#unit" +index_c).attr("value","");  
	$("#brand" +index_c).attr("value","");   
	$("#productCode"+index_c).attr("value",0); 
	$("#quantity"+index_c).attr("value",0); 
	$("#comment"+index_c).attr("value",""); 
	$("#barcode"+index_c).attr("value",""); 
	$("#year"+index_c).attr("value",""); 
	$("#quarter"+index_c).attr("value",""); 
	$("#inventoryQ"+index_c).attr("value",""); 
	calculateTotal();
}




function deleteRow(rowID){
	$("#"+rowID).remove(); 
 	calculateTotal();
    
}

function deleteOrder(){
	if (confirm("你确定删除此单据吗?")){
		   formSubmit = true;
		   document.chainInventoryFlowForm.action = "actionChain/inventoryFlowJSPAction!deleteOrder";
		   document.chainInventoryFlowForm.submit();
	}
}
function validateTransferOrderForm(){
	var fromChainStoreId = $("#chainStore").val();
	var toChainStoreId = $("#toChainStore").val();
	if (fromChainStoreId == toChainStoreId){
		alert("调入连锁店 与 调出连锁店不能为同一个连锁店选项");
		return false;
	} else {
		var error = "";
		var hasChar_q = false;

		for (var i = 0; i < index; i++){

			var q = $("#quantity" + i).val();

			if (q != undefined){
				if (isNaN(q) || q<0){
					hasChar_q = true;
					errorIndexes.push(i);
				}
			}
		}
		
		if (hasChar_q)
			error += "数量 - 必须为大于0的整数. 请检查后重新输入!\n";

		if (error != ""){
			for (var i =0; i < errorIndexes.length; i++){
				$("#orderRow" + errorIndexes[i]).css('background-color', '#EE8553');
			}
			
			alert(error);
			return false;
		}else{
			var totalQ = $("#totalQuantity").val();
			var file = $("#inventory").val();
			if (totalQ =="" && file == ""){
				alert("请输入货品或者导入盘点文件后再保存单据!");
				return false;
			}
            return true;
		}
		
	}

}

function changeFromChain(){
	var fromChainStoreId = $("#chainStore").val();
	var params = "formBean.flowOrder.fromChainStore.chain_id=" + fromChainStoreId;
	$.post(baseurl + "/actionChain/inventoryFlowJSONAction!changeFromChainStoreTransfer",params, backProcessChangeFromChainStore,"json");
}
function backProcessChangeFromChainStore(data){
	var response = data.response;
	if (response.returnCode == SUCCESS){
		$("#toChainStore").empty();
		var chainStores = response.returnValue;

		for (var i = 0; i < chainStores.length; i++)
			   $("#toChainStore").append("<option value='"+chainStores[i].chain_id+"'>"+chainStores[i].chain_name+"</option>"); 
	} else {
		alert("根据调出连锁店去获取调入连锁店列表发生错误,请再试一试此操作");
	}

}


function saveFinal(){
	if (validateTransferOrderForm()){
		   formSubmit = true;
		   document.chainInventoryFlowForm.action = "actionChain/inventoryFlowJSPAction!saveToFinal";
		   document.chainInventoryFlowForm.submit();
	}
}


/**
 * actions
 */
function saveDraft(){
	if (validateTransferOrderForm()){
	   formSubmit = true;
	   document.chainInventoryFlowForm.action = "actionChain/inventoryFlowJSPAction!saveToDraft";
	   document.chainInventoryFlowForm.submit();
	}
}

function cancelOrder(){
	if (confirm("你确定红冲此单据吗?")){
		   document.chainInventoryFlowForm.action = "actionChain/inventoryFlowJSPAction!cancelOrder";
		   document.chainInventoryFlowForm.submit();
	}
}

/**
 * user input the product code and press enter
 */
function searchProductsProductCodeTF(index_trigger){
	var productCode = $.trim($("#productCode" +index_trigger).val());
	var fromChainStoreId = $("#chainStore").val();
	var toChainStoreId = $("#toChainStore").val();
	var chainId = fromChainStoreId;
	if (chainId == 0)
		chainId = toChainStoreId;	
	
	if (validateProductCodeInput(productCode)){
	    var url = encodeURI(encodeURI("actionChain/chainSalesJSPAction!scanByProductCode?" + "formBean.productCode=" + productCode + "&formBean.index=" + index_trigger +"&formBean.chainId=" + chainId)) ; 
		
	    window.open(url,'新窗口','height=530, width=600, toolbar=no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=no');    
	}
}

/**
 * 打印单子到小票机
 */
function printOrder(){
	
}