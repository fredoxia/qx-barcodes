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
		   searchProductsProductCode(index_trigger);
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


