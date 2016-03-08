
function validateInvenFlowForm(type){
	  if (confirm("你确认提交单据?")){

			var error = "";
			if ($("#chainStore").val() == 0 && type == undefined){
				error += "连锁店 - 必须填!\n";
			}

			var hasChar_q = false;

			for (var i = 0; i < index; i++){

				var q = $("#quantity" + i).val();

				if (q != undefined){
					if (isNaN(q) || q==0){
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
				if (totalQ =="" || totalQ==0){
					alert("请输入货品后再保存单据!");
					return false;
				}
                return true;
			}
	    } else {
	    	return false;
	    }
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
    	var inventoryInput = $("#inventoryQ"+index_c); 
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
		var inventoryQ = data.inventory;
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
    	inventoryInput.attr("value", inventoryQ);
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

/**
 * to calculate the total value for the quantity
 */
function calculateTotal(){
	var quantity_t =0;
	var inventory_t = 0;
	var totalSalesPriceOrder = 0;

	for (var i = 0; i <= index; i++){
	   	var quantityInput =  $("#quantity"+i); 
	   	var inventoryInput =  $("#inventoryQ"+i); 
	   	var salesPriceInput = $("#salesPrice"+i); 
	   	var totalSalesPriceInput = $("#totalSalesPrice"+i); 
        if (quantityInput.val()!= undefined && quantityInput.val()!="" ){
		   quantity_t += parseInt(quantityInput.val());
        }
        if (inventoryInput.val()!= undefined && inventoryInput.val()!=""){
        	inventory_t += parseInt(inventoryInput.val());
        }
        if (quantityInput.val()!= undefined && quantityInput.val()!="" && salesPriceInput.val()!= undefined && salesPriceInput.val()!=""){
        	var totalSalesPrice = parseInt(quantityInput.val()) * salesPriceInput.val();
        	totalSalesPriceInput.attr("value", totalSalesPrice);
        	totalSalesPriceOrder += totalSalesPrice;
        }
	}

   	$("#totalQuantity").attr("value",quantity_t); 
   	$("#totalInventoryQ").attr("value",inventory_t); 
   	
   	$("#totalSalesPriceOrder").attr("value",totalSalesPriceOrder); 
}

function addNewRow(){
    index = index +1;
	var pre_index = index -1;
	
	$("#delIcon"+pre_index).show();
    var str = "";
    str += "<tr class='InnerTableContent' id='orderRow"+index+"'>";   
    str += "<td height='25'>"+(index +1)+"</td>";  
    str += "<td><input type='text' name='formBean.flowOrder.productList["+index+"].productBarcode.barcode' id='barcode"+index+"' size='13'/>" +
               "<input type='hidden' name='formBean.flowOrder.productList["+index+"].productBarcode.id' id='productId"+index+"' size='13'/></td>";  
    str += "<td><input type='text' name='formBean.flowOrder.productList["+index+"].productBarcode.product.productCode' id='productCode"+index+"' size='13'/></td>";  
    str += "<td><input type='text' name='formBean.flowOrder.productList["+index+"].productBarcode.color.name' id='color"+index+"' size='5'/></td>"; 
    str += "<td><input type='text' name='formBean.flowOrder.productList["+index+"].productBarcode.product.brand.brand_Name' id='brand"+index+"'  size='17' readonly='readonly'/></td>";  
    str += "<td><input type='text' name='formBean.flowOrder.productList["+index+"].productBarcode.product.year.year' id='year"+index+"'  size='4' readonly='readonly'/></td>"; 
    str += "<td><input type='text' name='formBean.flowOrder.productList["+index+"].productBarcode.product.quarter.quarter_Name' id='quarter"+index+"'  size='4' readonly='readonly'/></td>"; 
    str += "<td><input type='text' name='formBean.flowOrder.productList["+index+"].productBarcode.product.unit' id='unit"+index+"' size='4' readonly='readonly'/></td>";  
    str += "<td><input type='text' name='formBean.flowOrder.productList["+index+"].productBarcode.product.salesPrice' id='salesPrice"+index+"'  size='2' readonly='readonly'/></td>";     
    str += "<td><input type='text' name='formBean.flowOrder.productList["+index+"].quantity' id='quantity"+index+"'  size='2' onchange='calculateTotal("+index+")' onkeypress='return is_number(event);'/></td>";  
    str += "<td><input type='text' name='formBean.flowOrder.productList["+index+"].totalSalesPrice' id='totalSalesPrice"+index+"'  size='2' readonly='readonly'/></td>"; 
    str += "<td><input type='text' name='formBean.flowOrder.productList["+index+"].inventoryQ' id='inventoryQ"+index+"' readonly size='2' onchange='calculateTotal("+index+")' onkeypress='return is_number(event);'/></td>";  
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



function saveFinal(){
	if (validateInvenFlowForm()){
		   formSubmit = true;
		   document.chainInventoryFlowForm.action = "actionChain/inventoryFlowJSPAction!saveToFinal";
		   document.chainInventoryFlowForm.submit();
	}
}


/**
 * actions
 */
function saveDraft(){
	if (validateInvenFlowForm()){
	   formSubmit = true;
	   document.chainInventoryFlowForm.action = "actionChain/inventoryFlowJSPAction!saveToDraft";
	   document.chainInventoryFlowForm.submit();
	}
}