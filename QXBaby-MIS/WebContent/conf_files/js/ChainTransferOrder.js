//variable to identify it is form submit or close the page
var formSubmit = false;
var P_NUMBER = 2;
var N_PRINT = 1;
var P_DISCOUNT = 3;
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
	    var params= "formBean.barcode=" + currentBarcode + "&formBean.index=" + index_trigger; 

	    $.post("chainSalesJSONAction!scanByBarcode",params, backRetrievePByBarcodeProcess,"json");

	}
}

/**
 * to validate whether the product is selected or not
 */
function validateRowInput(index_trigger,currentBarcode){
//	var currentBarcode =$("#barcode"+index_trigger).val();
	if (currentBarcode.length != 12){
		alert(currentBarcode + " 不是一个正常条形.请重新输入.");
		clearRowCells(index_trigger);
		return false;
    } else {
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
    }
	return true;
}
function backRetrievePByBarcodeProcess(data){
	var error = data.error;
	var index_c = data.index;

	if (error == true){
		alert("无法找到对应货品.请检查条码，重新输入!");

		clearRowCells(index_c);
	}else{
		var productIdInput =  $("#productId" +index_c);  
       	var unitInput =  $("#unit" +index_c);  
    	var brandInput =  $("#brand" +index_c);   
    	var productCodeInput = $("#productCode"+index_c); 
    	var quantityInput =  $("#quantity"+index_c); 
    	var barcodeInput = $("#barcode"+index_c); 
    	var yearInput = $("#year"+index_c); 
    	var quarterInput = $("#quarter"+index_c); 
    	var colorInput = $("#color"+index_c); 
    	var salesPriceInput = $("#salesPrice" + index_c);
    	var totalSalesPriceInput = $("#totalSalesPrice" + index_c);
    	
		var barcode = data.productBarcode.barcode;
		var productId = data.productBarcode.id;
		var product = data.productBarcode.product;
		var productCode = product.productCode;
		var brand = product.brand.brand_Name;
		var unit = product.unit;
		var quantity = 1;
		var year = product.year.year;
		var quarter = product.quarter.quarter_Name;
		var color = data.productBarcode.color;
		var colorName = "";
		if (color != null)
    		colorName = color.name;
		var salesPrice = product.salesPrice;
		var totalSalesPrice = quantity * salesPrice;

		productIdInput.attr("value",productId);
		barcodeInput.attr("value",barcode);
		productCodeInput.attr("value",productCode);
		unitInput.attr("value",unit);
    	brandInput.attr("value",brand); 
    	quantityInput.attr("value",quantity); 
    	yearInput.attr("value",year); 
    	quarterInput.attr("value",quarter); 
    	colorInput.attr("value", colorName);
    	salesPriceInput.attr("value", salesPrice);
    	totalSalesPriceInput.attr("value", totalSalesPrice);
    	
    	//if the user just changed the original barcode information
    	//then no need to add the index by one
    	if (index_c == index){
    	   addNewRow();
    	} else
    		$("#barcode"+index).focus(); 
    	
    	calculateTotal();
    	
    	var row = $("#orderRow"+index_c);
    	row.css('background-color', '');
	}
}

function addNewRow(){

    index = index +1;
	var pre_index = index -1;
	
	$("#delIcon"+pre_index).show();
    var str = "";
    str += "<tr class='InnerTableContent' id='orderRow"+index+"'>";   
    str += "<td height='25'>"+(index +1)+"</td>";  
    str += "<td><input type='text' name='formBean.transferOrder.productList["+index+"].productBarcode.barcode' id='barcode"+index+"' size='13'/>" +
               "<input type='hidden' name='formBean.transferOrder.productList["+index+"].productBarcode.id' id='productId"+index+"' size='13'/></td>";  
    str += "<td><input type='text' name='formBean.transferOrder.productList["+index+"].productBarcode.product.productCode' id='productCode"+index+"' size='13'/></td>";  
    str += "<td><input type='text' name='formBean.transferOrder.productList["+index+"].productBarcode.color.name' id='color"+index+"' size='5'/></td>"; 
    str += "<td><input type='text' name='formBean.transferOrder.productList["+index+"].productBarcode.product.brand.brand_Name' id='brand"+index+"'  size='17' readonly='readonly'/></td>";  
    str += "<td><input type='text' name='formBean.transferOrder.productList["+index+"].productBarcode.product.year.year' id='year"+index+"'  size='4' readonly='readonly'/></td>"; 
    str += "<td><input type='text' name='formBean.transferOrder.productList["+index+"].productBarcode.product.quarter.quarter_Name' id='quarter"+index+"'  size='4' readonly='readonly'/></td>"; 
    str += "<td><input type='text' name='formBean.transferOrder.productList["+index+"].productBarcode.product.unit' id='unit"+index+"' size='4' readonly='readonly'/></td>";  
    str += "<td><input type='text' name='formBean.transferOrder.productList["+index+"].productBarcode.product.salesPrice' id='salesPrice"+index+"'  size='2' readonly='readonly'/></td>";     
    str += "<td><input type='text' name='formBean.transferOrder.productList["+index+"].quantity' id='quantity"+index+"'  size='2' onchange='calculateTotal("+index+")' onkeypress='return is_number(event);'/></td>";  
    str += "<td><input type='text' name='formBean.transferOrder.productList["+index+"].totalSalesPrice' id='totalSalesPrice"+index+"'  size='2' readonly='readonly'/></td>";  
    str += "<td><div id='delIcon"+index+"' style='display:none'><img src='"+baseurl+"/conf_files/web-image/delete.png' border='0'  onclick='deleteRow(\"orderRow"+index +"\");' style='cursor:pointer;'/></div></td>";  
    str += "</tr>";   

    $("#orderTablebody").append(str);

    //freeze the previous row
    var barcodeInput = $("#barcode"+pre_index);
    barcodeInput.attr("readonly",true);
 
    var productCodeInput = $("#productCode"+pre_index);
    productCodeInput.attr("readonly",true);
    
	$("#barcode"+index).focus(); 
}

/**
 * to calculate the total value for the quantity
 */
function calculateTotal(){
	var quantity_t =0;

	var totalSalesPriceOrder = 0;

	for (var i = 0; i <= index; i++){
	   	var quantityInput =  $("#quantity"+i); 
	   	
	   	var salesPriceInput = $("#salesPrice"+i); 
	   	var totalSalesPriceInput = $("#totalSalesPrice"+i); 
        if (quantityInput.val()!= undefined && quantityInput.val()!="" ){
		   quantity_t += parseInt(quantityInput.val());
        }
  
        if (quantityInput.val()!= undefined && quantityInput.val()!="" && salesPriceInput.val()!= undefined && salesPriceInput.val()!=""){
        	var totalSalesPrice = parseInt(quantityInput.val()) * salesPriceInput.val();
        	totalSalesPriceInput.attr("value", totalSalesPrice);
        	totalSalesPriceOrder += totalSalesPrice;
        }
	}

   	$("#totalQuantity").attr("value",quantity_t); 
   	$("#totalSalesPriceOrder").attr("value",totalSalesPriceOrder); 
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
	var fromChainStoreId = $("#fromChainId").val();
	var toChainStoreId = $("#toChainId").val();

	if (fromChainStoreId == 0 || toChainStoreId==0){
		alert("调入连锁店 与 调出连锁店不能为空");
		return false;
	} else if (fromChainStoreId == toChainStoreId){
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
		}	
		return true;
	}

}



/**
 * user input the product code and press enter
 */
function searchProductsProductCodeTF(index_trigger){
	var productCode = $.trim($("#productCode" +index_trigger).val());
	var chainId = 0;

	if (validateProductCodeInput(productCode)){
	    var url = encodeURI(encodeURI("actionChain/chainSalesJSPAction!scanByProductCode?" + "formBean.productCode=" + productCode + "&formBean.index=" + index_trigger +"&formBean.chainId=" + chainId)) ; 
		
	    window.open(url,'新窗口','height=530, width=600, toolbar=no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=no');    
	}
}

/**
 * to validate whether the product is selected or not
 * this is called from child
 */
function validateRowInputFromChild(currentBarcode){

	for (var i = 0; i < index; i++){
			var barcodeInput =  $("#barcode"+i);
			if (currentBarcode == barcodeInput.val()){
				var currentQ = $("#quantity" + suffix +i).val();
				$("#quantity" +i).attr("value" , parseInt(currentQ) + 1);
				
				calculateTotal();
				
				return false;
			}
	}

	return true;
}