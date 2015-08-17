//variable to identify it is form submit or close the page
var formSubmit = false;
var index = 0;

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
 * retrieve the product by input barcode
 */
function retrieveProductByBarcode(index_trigger, suffix, currentBarcode){
	if (validateRowInput(index_trigger,currentBarcode)){		
	    var params= "formBean.barcode=" + currentBarcode + "&formBean.index=" + index_trigger ; 

	    $.post("action/chainSMgmtJSON!getBarcodeForChainInitialStock",params, backRetrievePByBarcodeProcess,"json");

	}
}

function backRetrievePByBarcodeProcess(data){
	var error = data.error;
	var index_c = data.index;

	if (error == true){
    	var errorRow = $("#orderRow"+index_c);
    	if (errorRow.length != 0){
    	  errorRow.css('background-color', '#EE8553');
    	}
	}else{ 
       	var unitInput =  $("#unit" +index_c);  
    	var brandInput =  $("#brand" +index_c);   
    	var productCodeInput = $("#productCode"+index_c); 
    	var quantityInput =  $("#quantity"+index_c); 
    	var barcodeInput = $("#barcode"+index_c); 
    	var productBarcodeInput = $("#productId"+index_c); 
    	var yearInput = $("#year"+index_c); 
    	var quarterInput = $("#quarter"+index_c); 
    	var colorInput = $("#color"+index_c); 
    	var costInput = $("#cost"+index_c); 
    	var costTotalInput = $("#costTotal"+index_c); 
    	
		var barcode = data.chainStock.id.barcode;
		var quantity = data.chainStock.quantity;
		var product = data.chainStock.productBarcode.product;
		var productCode = product.productCode;
		var brand = product.brand.brand_Name;
		var unit = product.unit;
		var productBarcodeId = data.chainStock.productBarcode.id;

		var year = product.year.year;
		var quarter = product.quarter.quarter_Name;
		var color = data.chainStock.productBarcode.color;
		var colorName = "";

		if (color != null)
    		colorName = color.name;
		var cost = data.chainStock.cost;
		var costTotal = data.chainStock.costTotal;

		productBarcodeInput.attr("value", productBarcodeId);
		barcodeInput.attr("value",barcode);
		productCodeInput.attr("value",productCode);
		unitInput.attr("value",unit);
    	brandInput.attr("value",brand); 
    	quantityInput.attr("value",quantity); 
    	yearInput.attr("value",year); 
    	quarterInput.attr("value",quarter); 
    	colorInput.attr("value", colorName);
    	costInput.attr("value", (cost).toFixed(2));
    	costTotalInput.attr("value", (costTotal).toFixed(2));
    	
    	calculateTotal();
    	
    	var row = $("#orderRow"+index_c);
    	row.css('background-color', '');
	}
	
	//if the user just changed the original barcode information
	//then no need to add the index by one
	if (index_c == index){
	   addNewRow();
	} else 
       $("#barcode"+index).focus(); 

}

/**
 * to calculate the total value for the quantity
 */
function calculateTotal(indexC){
    
	if (indexC != undefined){
		var quantityThisInput =  $("#quantity"+indexC); 
		var costInput =  $("#cost"+indexC); 
		var costTotalInput =  $("#costTotal"+indexC); 
		if (costInput.val() != undefined && quantityThisInput.val() != undefined && quantityThisInput.val()!="" && costInput.val() !=""){
			var quantity = parseInt(quantityThisInput.val());
			var cost = parseFloat(costInput.val());
			
			costTotalInput.attr("value", quantity * cost);
		}
	}
	
    sumTotal();
}

function sumTotal(){
	var quantity_t =0;
	var sumCost = 0;
	
	for (var i = 0; i <= index; i++){
	   	var quantityInput =  $("#quantity"+i); 
	   	var costTotalInput =  $("#costTotal"+i); 
	   	
        if (quantityInput.val()!= undefined && quantityInput.val()!="" && costTotalInput.val() != undefined && costTotalInput.val() !=""){
		   quantity_t += parseInt(quantityInput.val());
		   sumCost += parseFloat(costTotalInput.val());
        }
	}

   	$("#totalQuantity").attr("value",quantity_t); 
   	$("#sumCost").attr("value",sumCost); 
}


function deleteRow(rowID){
	$("#"+rowID).remove(); 
	sumTotal();
    
}

/**
 * to validate whether the product is selected or not
 */
function validateRowInput(index_trigger,currentBarcode){
	for (var i = 0; i < index; i++){
		if (i != index_trigger){
			var barcodeInput =  $("#barcode"+i);
	
			if (currentBarcode == barcodeInput.val()){
				var currentQ = $("#quantity" +i).val();
				$("#quantity" +i).attr("value" , parseInt(currentQ) + 1);
				
				calculateTotal();
				
				$("#barcode"+index).attr("value",""); 
		    	$("#barcode"+index).focus(); 
				
				return false;
			}
		}
	}
	return true;
}

/**
 * to validate whether the product is selected or not
 * this is called from child
 */
function validateRowInputFromChild(currentBarcode){
	return true;
}

function validateForm(){
	var hasRecord = false;
	for (var i = 0; i < index; i++){
	   	var quantityInput =  $("#quantity"+i); 
	   	
        if (quantityInput.val()!= undefined){
        	hasRecord = true;
        	if (!isValidInteger(quantityInput.val())){
        		alert("数量不正确 : " + quantityInput.val());
        		return false;
        	}
        }
	}

    if (index == 0 || hasRecord==false ){
    	var file = $("#inventory").val();
    	if (file == ""){
    	   alert("请输入数据再保存");
    	   return false;
    	}
    }
	
	return true;
}