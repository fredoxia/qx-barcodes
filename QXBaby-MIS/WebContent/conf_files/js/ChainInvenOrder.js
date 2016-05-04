
function validateInvenFlowForm(toValidate){
	  var confirmed = true;
	  if (toValidate){
		  confirmed = confirm("你确认提交单据?");
	  }
	  if (confirmed){

			var error = "";
			if ($("#chainStore").val() == 0){
				error += "连锁店 - 必须填!\n";
			}


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
	    } else {
	    	return false;
	    }
}
function backRetrievePByBarcodeProcess(data){
	var error = data.error;
	var index_c = data.index;

	if (error == true){
    	var errorRow = $("#orderRow"+index_c);
    	if (errorRow.length != 0){
    	  errorRow.css('background-color', '#EE8553');
		  clearRowCells(index_c);
    	}
	}else{
		var scanType = $("#scanType").is(':checked');
		var productIdInput =  $("#productId" +index_c);  
       	var unitInput =  $("#unit" +index_c);  
    	var brandInput =  $("#brand" +index_c);   
    	var productCodeInput = $("#productCode"+index_c); 
    	var quantityInput =  $("#quantity"+index_c); 
    	var barcodeInput = $("#barcode"+index_c); 
    	var yearInput = $("#year"+index_c); 
    	var quarterInput = $("#quarter"+index_c); 
    	var inventoryInput = $("#inventoryQ"+index_c); 
    	var quantityDiffInput = $("#quantityDiff"+index_c); 
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
		if (scanType)
		    quantity = product.numPerHand;

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
    	quantityDiffInput.attr("value", quantity - inventoryQ);
    	colorInput.attr("value", colorName);
    	salesPriceInput.attr("value", salesPrice);
    	totalSalesPriceInput.attr("value", totalSalesPrice);
    	
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
function calculateTotal(){
	var quantity_t =0;
	var inventory_t = 0;
	var quantityDiff_t = 0;
	var totalSalesPriceOrder = 0;

	for (var i = 0; i <= index; i++){
	   	var quantityInput =  $("#quantity"+i); 
	   	var inventoryInput =  $("#inventoryQ"+i); 
	   	var quantityDiffInput =  $("#quantityDiff"+i); 
	   	var salesPriceInput = $("#salesPrice"+i); 
	   	var totalSalesPriceInput = $("#totalSalesPrice"+i); 
	   	
        if (quantityInput.val()!= undefined && quantityInput.val()!="" ){
		   quantity_t += parseInt(quantityInput.val());
        }
        if (inventoryInput.val()!= undefined && inventoryInput.val()!=""){
        	inventory_t += parseInt(inventoryInput.val());
        }
        
        if (quantityInput.val()!= undefined && inventoryInput.val()!= undefined){
	        var diff = quantityInput.val() - inventoryInput.val();
	        quantityDiffInput.attr("value", diff);
	        quantityDiff_t += parseInt(diff);
        }
        if (quantityInput.val()!= undefined && quantityInput.val()!="" && salesPriceInput.val()!= undefined && salesPriceInput.val()!=""){
        	var totalSalesPrice = parseInt(quantityInput.val()) * salesPriceInput.val();
        	totalSalesPriceInput.attr("value", totalSalesPrice);
        	totalSalesPriceOrder += totalSalesPrice;
        }
	}

   	$("#totalQuantity").attr("value",quantity_t); 
   	$("#totalInventoryQ").attr("value",inventory_t); 
   	$("#totalQuantityDiff").attr("value",quantityDiff_t);
   	$("#totalSalesPriceOrder").attr("value",totalSalesPriceOrder); 
}

/**
 * once the quantity is changed, it will trigger this function
 * @param indexQ
 */
function changeQuantity(indexQ){
	
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
    str += "<td><input type='text' name='formBean.flowOrder.productList["+index+"].inventoryQ' id='inventoryQ"+index+"' readonly  size='2' onchange='calculateTotal("+index+")' onkeypress='return is_number(event);'/></td>";
    str += "<td><input type='text' name='formBean.flowOrder.productList["+index+"].quantityDiff' id='quantityDiff"+index+"'  size='2'/></td>";  
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
/**
 * actions
 */
function calInventoryOrder(){
	var file = $("#inventory").val();
	if (file != ""){
	  var confirmMsg = "计算库存将忽略上传的盘点文件,你要继续吗?";
		var con = confirm(confirmMsg);
		if(con == true){
		   var afile = $("#inventory");
		   afile.replaceWith(afile.clone());
		} else
		   return;
	}

   if (validateInvenFlowForm(false)){
		parent.$.messager.progress({
			text : '数据处理中，请稍后....'
		});
      formSubmit = true;
      document.chainInventoryFlowForm.action = "actionChain/inventoryFlowJSPAction!calculateInventoryOrder";
      document.chainInventoryFlowForm.submit();
   }
}
/**
 * actions
 */
function genInventoryReport(){
	var msg = "生成库存报表后，将不能再修改，是否继续?";

	if (confirm(msg) && validateInvenFlowForm(false)){
		parent.$.messager.progress({
			text : '数据处理中，请稍后....'
		});
	   formSubmit = true;
	   document.chainInventoryFlowForm.action = "actionChain/inventoryFlowJSPAction!genInventoryReport";
	   document.chainInventoryFlowForm.submit();
	}
}

function oneKeyToAdjustInventory(){
	var file = $("#inventory").val();
	if (file != ""){
	  var msg = "如果有盘点文件，请先上传文件然后保存到草稿中.";
	  alert(msg);
	} else if (confirm("你确定上传货品的实际库存已经正确.一键纠正库存将根据当前实际库存，纠正单据中物品电脑库存.继续?") && validateInvenFlowForm(false)){
		parent.$.messager.progress({
			text : '数据处理中，此功能耗时较长.请稍后....'
		});
	   formSubmit = true;
	   document.chainInventoryFlowForm.action = "actionChain/inventoryFlowJSPAction!oneKeyAdjustInventory";
	   document.chainInventoryFlowForm.submit();
	}
}

function oneKeyToClearAndAdjustInventory(){
	var file = $("#inventory").val();
	if (file != ""){
	  var msg = "如果有盘点文件，请先上传文件然后保存到草稿中.";
	  alert(msg);
	} else{
		if (validateInvenFlowForm(false)){
			parent.$.messager.progress({
				text : '数据处理中，请稍后....'
			});
	        var params = $("#chainInventoryFlowForm").serialize(); 
		    $.post("inventoryFlowJSONAction!getYearQuarterInFlowOrder",params, oneKeyToClearAndAdjustInventoryBK,"json");
	    }
	}
}

function oneKeyToClearAndAdjustInventoryBK(data){
	parent.$.messager.progress('close'); 
	var response = data.response;
	var returnCode = response.returnCode;
	if (returnCode == SUCCESS){
		var confirmMsg ="请确定此单据中只包含了如下季度货品: " + response.returnValue + "\n\n" +
						"此功能将使用单据中的实际库存完全取代连锁店的电脑库存.请确认这张单据里面的季度是你这次盘点所包含的季度吗?";
		if (confirm(confirmMsg)){
			parent.$.messager.progress({
				text : '数据处理中，此功能耗时较长.请稍后....'
			});
		   formSubmit = true;
		   document.chainInventoryFlowForm.action = "actionChain/inventoryFlowJSPAction!oneKeyClearAndAdjustInventory";
		   document.chainInventoryFlowForm.submit();
		}
	} else {
		alert("获取货品季度出现出错，请稍后再试");
	}
}

/**
 * 把条码保存下来
 */
function getBarcodeFile(){
	formSubmit = true;
	document.chainInventoryFlowForm.action = "actionChain/inventoryFlowJSPAction!getBarcodeFile";
	document.chainInventoryFlowForm.submit();	
}
/**
 * actions
 */
function saveDraft(){
	if (validateInvenFlowForm(true)){
		parent.$.messager.progress({
			text : '数据处理中，请稍后....'
		});
	   formSubmit = true;
	   document.chainInventoryFlowForm.action = "actionChain/inventoryFlowJSPAction!saveInventoryToDraft";
	   document.chainInventoryFlowForm.submit();
	}
}
function saveFinal(){
	if (validateInvenFlowForm(true)){
			parent.$.messager.progress({
				text : '数据处理中，请稍后....'
			});
		   formSubmit = true;
		   document.chainInventoryFlowForm.action = "actionChain/inventoryFlowJSPAction!saveInventoryToFinal";
		   document.chainInventoryFlowForm.submit();
	}
}
