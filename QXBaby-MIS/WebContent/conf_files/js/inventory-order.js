var index = 0;
var calculateRowNum = 5;
var formSubmit = false;


function BSkeyDown(e){

	 var ieKey = event.keyCode;

	 if (ieKey==13){
	   if (event.srcElement.id == "clientName"){
		   searchCustomer(); 
		   event.returnValue=false;
	   } else if (event.srcElement.id.substring(0,7)=="barcode"){
		   var index_trigger = event.srcElement.id.substring(7);

	       var barcodeInput =$("#barcode" +index_trigger);
	       barcodeInput.focus();
	       event.returnValue=false;
	
	       retrieveProductByBarcode(index_trigger, "",barcodeInput.val(),0);

	   } else if (event.srcElement.id.substring(0,11)=="productCode"){
		   var index_trigger = event.srcElement.id.substring(11);
		   event.returnValue=false;
		   searchProductsProductCode(index_trigger);
 	   } else if (event.srcElement.id.substring(0,8)=="quantity"){
 		   $("#barcode"+index).focus();
	   } else
		   event.returnValue=false; 
	 }  else if (ieKey==8){
		    if((event.srcElement.tagName.toLowerCase() == "input" && event.srcElement.readOnly != true) || event.srcElement.tagName.toLowerCase() == "textarea"){
		        event.returnValue = true; 
		    } else
		    	event.returnValue = false;
	 } 
} 


document.onkeydown = BSkeyDown; 


function recordSubmit(){
	formSubmit = true;
}

/**
 * user input the product code and press enter
 */
function searchProductsProductCode(index_trigger){

	var productCode = $("#productCode" +index_trigger).val();

	if (validateProductCodeInput(productCode)){
	    var params= "formBean.productBarcode.product.productCode=" + productCode + "&formBean.indexPage=" + index_trigger ; 
	    
	    var url = encodeURI(encodeURI("productJSPAction!scanByProductCode?" +params));
		
	    window.open(url,'新窗口','height=570, width=630, toolbar=no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=no');    
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
 * just override the function
 */
function validateRowInputFromChild(currentBarcode){
	return true;
}

function retrieveProductByBarcode(index_trigger, suffix ,currentBarcode, fromSrc){
	var client_id = $("#clientID").val();
	
	var orderType = $("#orderType").val();
	
    var params= "formBean.productBarcode.barcode=" + currentBarcode + "&formBean.client_id=" + client_id + "&formBean.indexPage=" + index_trigger +"&formBean.orderType=" + orderType+"&formBean.fromSrc=" + fromSrc ; 

    $.post(baseurl +"/action/productJSONAction!search",params, backProcess,"json");

	//if the user just changed the original barcode information
	//then no need to add the index by one
    //alert(index_trigger + "," + index + "," + suffix + "," + currentBarcode);
    //0: from the barcode
    //1: from the product code
	if (index_trigger == index){
	    addNewRow();
	}

}

function backProcess(data){
	var fullOrSingle = $("#fullOrSingle").val();

	
	var barcodes = data.barcodes;
	var preIndex = data.index;
	var orderType = data.orderType;
	var where = data.where;
	
	var barcodeInput =  $("#barcode" +preIndex); 
	var productIdInput =  $("#productId" +preIndex);  
   	var unitInput =  $("#unit" +preIndex);  
	var brandInput =  $("#brand" +preIndex);   
	var productCodeInput = $("#productCode"+preIndex); 
	var quantityInput =  $("#quantity"+preIndex); 
	var salePriceInput = $("#salesPrice"+preIndex); 
	var wholeSalePriceInput = $("#wholeSalePrice"+preIndex); 
	var discountInput = $("#discount"+preIndex); 
	//var recCostInput = $("#recCost"+preIndex); 
	var numPerHandInput = $("#numPerHand" + preIndex);
	var boughtBeforeInput = $("#boughtBefore" + preIndex);
	var takeBeforeDiv = $("#takeBefore" + preIndex);
	var yearInput = $("#year" + preIndex);
	var quarterInput = $("#quarter" + preIndex);
	var colorInput = $("#color" + preIndex);
	
	if (where == 1)
		quantityInput.focus();
	
    if (barcodes.length != 0){
   	
    	barcodeInput.attr("value",barcodes[0].barcode);
    	productIdInput.attr("value",barcodes[0].id);
		unitInput.attr("value",barcodes[0].product.unit);
		brandInput.attr("value",barcodes[0].product.brand.brand_Name);
		productCodeInput.attr("value",barcodes[0].product.productCode);
		if (fullOrSingle == 0)
		   quantityInput.attr("value",barcodes[0].product.numPerHand);
		else 
			quantityInput.attr("value",1);
		yearInput.attr("value", barcodes[0].product.year.year);
		quarterInput.attr("value", barcodes[0].product.quarter.quarter_Name);
		var color = barcodes[0].color;
		var colorName = "";
		if (color != null)
    		colorName = color.name;
		
		colorInput.attr("value", colorName);
        var salesPrice = barcodes[0].product.salesPrice;
        if (salesPrice == "")
        	salePriceInput.attr("value","");
        else
		    salePriceInput.attr("value",salesPrice);
        
        
        var wholeSalePrice = 0;
        var lastInputPrice = barcodes[0].product.lastInputPrice;
        var lastChoosePrice = barcodes[0].product.lastChoosePrice;
        
        var wholeSalePrice1 = barcodes[0].product.wholeSalePrice;
        var wholeSalePrice2 = barcodes[0].product.wholeSalePrice2;
        var wholeSalePrice3 = barcodes[0].product.wholeSalePrice3;
        var salesPriceFactory = barcodes[0].product.salesPriceFactory;
        var discount =  barcodes[0].product.discount;
        var wholeSalePrice4 = (salesPriceFactory * discount).toFixed(2);
        var choosePrice = 0;
        
        //to set the whole price with the recent one
        var select1 = "";
        var select2 = "";
        var select3 = "";
        var select4 = "";
//alert(lastInputPrice+","+lastChoosePrice+","+discount);
        if (lastInputPrice >0 && lastChoosePrice>0  && discount>0){
        	wholeSalePrice = lastInputPrice;
        	choosePrice = lastChoosePrice;
        } else if (orderType == 0){ 
        	if (wholeSalePrice3 > 0){
        		wholeSalePrice = wholeSalePrice3;
        		choosePrice = wholeSalePrice3;
        		discount =1;
        	} else if (wholeSalePrice2 > 0){
        		wholeSalePrice = wholeSalePrice2;
        		choosePrice = wholeSalePrice2;
        		discount =1;      	
        	} else if (wholeSalePrice1 > 0){
        		wholeSalePrice = wholeSalePrice1;
        		choosePrice = wholeSalePrice1;
        		discount =1;       	
        	} else if (wholeSalePrice4 > 0){
        		wholeSalePrice = wholeSalePrice4;
        		choosePrice = salesPriceFactory;
        	} 
        }

        switch (choosePrice){
        	case wholeSalePrice1:
        		select1 = "selected";
        		break;
        	case wholeSalePrice2:
        		select2 = "selected";
        		break;
        	case wholeSalePrice3:
            	select3 = "selected";
            	break;
        	case salesPriceFactory:
            	select4 = "selected";
            	break;
            default: ;
        }

        wholeSalePriceInput.attr("value",wholeSalePrice);
        discountInput.attr("value",discount);

        
        //set the price select drop down
        $("#priceSlect" +preIndex).empty();
        $("#priceSlect" +preIndex).append("<option value='"+wholeSalePrice1+"'"+select1+">预设价1 "+wholeSalePrice1+"</option>"); 
        $("#priceSlect" +preIndex).append("<option value='"+wholeSalePrice2+"'"+select2+">预设价2 "+wholeSalePrice2+"</option>"); 
        $("#priceSlect" +preIndex).append("<option value='"+wholeSalePrice3+"'"+select3+">预设价3 "+wholeSalePrice3+"</option>"); 
        $("#priceSlect" +preIndex).append("<option value='"+salesPriceFactory+"'"+select4+">零售价 "+salesPriceFactory+"</option>"); 
        
/*        var recCost = barcodes[0].product.recCost;
        if (recCost == "")
        	recCostInput.attr("value","");
        else
        	recCostInput.attr("value",recCost);*/
        
		numPerHandInput.attr("value",barcodes[0].product.numPerHand);
		
		if (barcodes[0].boughtBefore != 0){
			document.getElementById("bgs").src = baseurl+"/conf_files/web-image/already.mp3";
			boughtBeforeInput.attr("value",barcodes[0].boughtBefore);
			takeBeforeDiv.html("配" + barcodes[0].boughtBefore);
			takeBeforeDiv.show();
         }else
			takeBeforeDiv.hide();
		
		$("#row"+preIndex).css('background-color', '');		
    } else {
    	var errorRow = $("#row"+preIndex);
    	if (errorRow.length != 0){

    	  errorRow.css('background-color', '#EE8553');
    	  document.getElementById("bgs").src = baseurl+"/conf_files/web-image/error.mp3";
    	}
    	barcodeInput.attr("value","");
    	productIdInput.attr("value","");
		unitInput.attr("value","");
		brandInput.attr("value","");
		productCodeInput.attr("value","");
		quantityInput.attr("value","");
		yearInput.attr("value","");
		quarterInput.attr("value","");
		//recCostInput.attr("value","");
		wholeSalePriceInput.attr("value","");
		discountInput.attr("value","");
		salePriceInput.attr("value","");
		$("#priceSlect" +preIndex).empty();
    }

    var barcodeInput = $("#barcode"+preIndex);
    barcodeInput.attr("readonly",true);
    
    if (index < calculateRowNum)
        calculateTotal();
}


function calculateTotal(){
	 var totalQ = 0;
	 var totalRetailPrice = 0;
//	 var totalRecCost = 0;
	 var totalWholePrice = 0;
	 
	 for (var i =0; i < index; i++){
		 var quantityInputs = $("#quantity" + i).val();
		 var salesPriceInputs = $("#salesPrice" + i).val();
//		 var recCostInputs = $("#recCost" + i).val();
		 var wholeSalePriceInputs = $("#wholeSalePrice" + i).val();
		 
		 if (quantityInputs != undefined){
			 totalQ = totalQ + parseInt(quantityInputs);
		 } 
		 if (quantityInputs != undefined && salesPriceInputs != undefined && salesPriceInputs !=""){
			 totalRetailPrice = totalRetailPrice + parseInt(quantityInputs)*parseFloat(salesPriceInputs);
		 } 
//		 if (quantityInputs != undefined && recCostInputs != undefined && recCostInputs !=""){
//			 totalRecCost = totalRecCost + parseInt(quantityInputs)*parseFloat(recCostInputs);
//		 } 
		 if (quantityInputs != undefined && wholeSalePriceInputs != undefined && wholeSalePriceInputs !=""){
			 totalWholePrice = totalWholePrice + parseInt(quantityInputs)*parseFloat(wholeSalePriceInputs);
		 } 
	 }
	 
	 $("#totalQuantity").attr("value",totalQ);
	 $("#totalRetailPrice").attr("value",(totalRetailPrice).toFixed(2));
//	 $("#totalRecCost").attr("value",(totalRecCost).toFixed(2));
	 $("#totalWholePrice").attr("value",(totalWholePrice).toFixed(2));
}
/*
function calculateWholeTotal(){
	 var totalWholePrice = 0;
	 
	 for (var i =0; i < index; i++){
		 var quantityInputs = $("#quantity" + i).val();
		 var wholeSalePriceInputs = $("#wholeSalePrice" + i).val();

		 if (quantityInputs != undefined && wholeSalePriceInputs != undefined && wholeSalePriceInputs !=""){
			 totalWholePrice = totalWholePrice + parseInt(quantityInputs)*parseFloat(wholeSalePriceInputs);
		 } 
	 }
	 
	 $("#totalWholePrice").attr("value",(totalWholePrice).toFixed(2));
}*/

function deleteRow(rowID, delIndex){
	var brand = $("#brand" + delIndex).val();
	var productCode = $("#productCode" + delIndex).val();
	var msg = "你确定要删除第" + (delIndex + 1) + "行 : " + brand + " " + productCode;
	
	$("#row" + delIndex).css('background-color', '#99CC00');
	if (confirm(msg)){	
		$("#"+rowID).remove(); 
		
		if (index < calculateRowNum)
		   calculateTotal(); 
	} else {
		$("#row" + delIndex).css('background-color', '');
	}
}

function addNewRow(){
	$("#delIcon"+index).show();
	index = index +1;
    var str = "";
    str += "<tr height='22' id='row"+ index + "' class='excelTable'>";
    str += "<td align='center'>" + (index+1) +"</td>";
    str += "<td><input type='text' name='formBean.order.product_List["+index+"].productBarcode.barcode' id='barcode"+index+"' size='10'/>" +
               "<input type='hidden' name='formBean.order.product_List["+index+"].productBarcode.id' id='productId"+index+"'/></td>";		 					 		
    str += "<td><input type='text' name='formBean.order.product_List["+index+"].productBarcode.product.productCode'  id='productCode"+index+"'  size='8'/></td>";
    str += "<td><input type='text' name='formBean.order.product_List["+index+"].productBarcode.color.name' readonly  id='color"+index+"'  size='2'/></td>";	
    str += "<td><input type='text' name='formBean.order.product_List["+index+"].productBarcode.product.brand.brand_Name' readonly  id='brand"+index+"'  size='12'/></td>";			 					 		
    str += "<td><input type='text' name='formBean.order.product_List["+index+"].productBarcode.product.year.year' readonly  id='year"+index+"'  size='2'/></td>";	
    str += "<td><input type='text' name='formBean.order.product_List["+index+"].productBarcode.product.quarter.quarter_Name' readonly  id='quarter"+index+"'  size='2'/></td>";	
    str += "<td><input type='text' name='formBean.order.product_List["+index+"].quantity' id='quantity"+index+"' value='0' size='2'  onchange='onQuantityChange();'  onfocus='this.select();'/>  <input type='hidden' name='formBean.order.product_List["+index+"].productBarcode.product.numPerHand' id='numPerHand"+index+"' size='5' value='0' /></td>";
    str += "<td><input type='text' name='formBean.order.product_List["+index+"].productBarcode.product.unit' readonly  id='unit"+index+"'  size='3'/></td>";			 					 		
    str += "<td><select id='priceSlect"+index+"' name='formBean.order.product_List["+index+"].salePriceSelected' onchange='onWholeSalePriceChange(3,"+index+");'></select></td>";
    str += "<td><input type='text' name='formBean.order.product_List["+index+"].discount' id='discount"+index+"' onchange='onWholeSalePriceChange(1,"+index+");' onfocus='this.select();' size='3'/></td>";
    str += "<td><input type='text' name='formBean.order.product_List["+index+"].wholeSalePrice'  onchange='onWholeSalePriceChange(2,"+index+");' id='wholeSalePrice"+index+"'  size='8'   onfocus='this.select();'/></td>";
//    str += "<td><input type='text' name='formBean.order.product_List["+index+"].recCost' readonly id='recCost"+index+"'  size='8'/></td>";
    str += "<td><input type='text' name='formBean.order.product_List["+index+"].salesPrice' readonly id='salesPrice"+index+"'  size='8'/></td>" ;
    str += "<td><div id='takeBefore"+index+"' style='display:none;color:red'></div><input type='hidden' name='formBean.order.product_List["+index+"].productBarcode.boughtBefore' id='boughtBefore"+index+"' size='14' value='0'/></td>";
    str += "<td><div id='delIcon"+index+"' style='display:none'> <img src='"+baseurl+"/conf_files/web-image/delete.png' border='0' onclick='deleteRow(\"row"+index +"\","+index+")' style='cursor:pointer;'/></div></td>";			 		
    str += "</tr>";

    $("#inventoryTable").append(str);
    
    $("#barcode"+index).focus();

    $("#row"+ index).bind('keyup',onkeyup);
    
	$("#row"+ index).mouseover(function(){      
		$(this).addClass("over");}).mouseout(function(){    
		$(this).removeClass("over");}); 
}

function validateForm(){

    if (confirm("你确认提交仓库订单?")){
    	for (var i =0; i < index; i++)
    		$("#row" + i).css('background-color', '');
    	
		var error = "";
		if ($("#clientName").val() == "" || $("#clientID").val() == 0){
			error += "客户名字 - 必须填!\n";
			$("#clientName").focus();
		}

		if ($("#keeper_id").val() == 0){
			error += "单据输入人员 - 必须选!\n";
			$("#keeper_id").focus();
		}
		if ($("#scanner_id").val() == 0){
			error += "扫描人员 - 必须选!\n";
			$("#scanner_id").focus();
		}
		if ($("#counter_id").val() == 0){
			error += "点数人员 - 必须选!\n";
			$("#counter_id").focus();
		}

		if ($("#totalQuantity").val() <= 0){
			error += "必须录入数据后才能输入\n";
		}
		var totalDiscount = $("#totalDiscount").val();
		if (isNaN(totalDiscount) || totalDiscount < 0){
			error += "优惠 - 必须是大于或者等于0的数字!\n";
		}
		var hasChar = false;
		var hasChar_w = false;
		var invalid_d = false;
		var invalid_barcode = false;
		for (var i =0; i < index; i++){

			var q = $("#quantity" + i).val();
			var w = $("#wholeSalePrice" + i).val();
			var d = $("#discount" + i).val();
			var pbId = $("#productId" + i).val();

			if (q != undefined){
				if (isNaN(q) || q<=0){
					$("#row" + i).css('background-color', '#EE8553');
					hasChar = true;
				}
			}
			if (w != undefined){
				if (isNaN(w) || w<=0 || w=='Infinity'){
					$("#row" + i).css('background-color', '#EE8553');
					hasChar_w = true;
				}
			}
			if (d != undefined){
				if (isNaN(d) || d<=0 || d=='Infinity'){
					$("#row" + i).css('background-color', '#EE8553');
					invalid_d = true;
				}
			}
			if (pbId != undefined){
				if (isEmpty(pbId) || pbId == 0){
					$("#row" + i).css('background-color', '#EE8553');
					invalid_barcode = true;
				}
			}
		}
		
		if (hasChar)
			error += "数量 - 必须为大于0数字!\n";
		if (hasChar_w)
			error += "批发价格 - 必须为大于0数字!\n";	
		if (invalid_d)
			error += "折扣 - 必须为大于0的数字!\n";	
		if (invalid_barcode)
			error += "条码错误 - 货品条码无法找到,请删除再从新扫描!\n";	
		
		if (error != ""){
			alert(error);
			return false;
		}else{
			$("#barcode" + index).attr("value","");
		    recordSubmit();
			return true;
		}
    } else {
    	return false;
    }
}

/**
 * 数量改变
 */
function onQuantityChange(){
	if (index < calculateRowNum)
		calculateTotal();
}
/**
 * on the discount or whole sale price change, need re-calculate the total of the whole price
 * triggerSource 1 = discount
 *               2 = whole sale price
 *               3 = sale price drop down
 */
function onWholeSalePriceChange(triggerSource,triggerIndex){
	 var consistent = true;
     var wholePrice = 0;
	 var totalWholePrice = 0;
	 var discountInput = $("#discount" + triggerIndex);
	 var salePriceInput = $("#priceSlect" + triggerIndex);
	 var wholeSalePriceInput = $("#wholeSalePrice" + triggerIndex);
	 var salePrice = parseFloat(salePriceInput.val());
	 
	 //1. on change on the entity and at the sametime change the corresponding elements in the web page
	 if (triggerSource == 1){
		 var discount = parseFloat(discountInput.val());
		 wholePrice = salePrice * discount;
		 wholeSalePriceInput.attr("value",(wholePrice).toFixed(2));
	 } else if (triggerSource == 2){
		 wholePrice = parseFloat(wholeSalePriceInput.val());
		 var discount = wholePrice / salePrice;
		 discountInput.attr("value",(discount).toFixed(2));
	 } else if (triggerSource == 3){
		 var discount = parseFloat(discountInput.val());
		 wholePrice = salePrice * discount;
		 wholeSalePriceInput.attr("value",(wholePrice).toFixed(2));
	 }
	 
	 //2. calclate the total value and checkt the price consistent
	 if (index < calculateRowNum){
		 for (var i =0; i < index; i++){
			 //calate the total value
			 var quantityInputs = $("#quantity" + i).val();
			 var wholeSalePriceInputs = $("#wholeSalePrice" + i).val();
			 
			 if (quantityInputs != undefined && wholeSalePriceInputs != undefined && wholeSalePriceInputs !=""){
				 totalWholePrice = totalWholePrice + parseInt(quantityInputs)*parseFloat(wholeSalePriceInputs);
				 
				 //check the value consistent
				 var productIdChange = $("#productId" + triggerIndex).val();
				 var productIdLoop = $("#productId" + i).val();
				 if (i != triggerIndex && productIdChange == productIdLoop){
					 if (wholePrice != wholeSalePriceInputs)
						 consistent = false;
				 }
			 } 
		 }
	
		 $("#totalWholePrice").attr("value",(totalWholePrice).toFixed(2));
		 
		 if (!consistent){
			 var productCode = $("#productCode" + triggerIndex).val();
			 alert(productCode + " 该商品出现批发价不一致情况，请检查");
		 }
	 }
}

function importFile(){
	 window.open("action/inventoryOrder!preUploadFile",'新窗口','height=200, width=300, toolbar=no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=no');    
}

function retrieveProductByExcel(products){

    if (products.length != 0){
    	for (var i = 0 ; i < products.length; i++){
    		
    	 	var barcodeInput =  $("#barcode" +index); 
        	var productIdInput =  $("#productId" +index);  
           	var unitInput =  $("#unit" +index);  
        	var brandInput =  $("#brand" +index);   
        	var productCodeInput = $("#productCode"+index); 
        	var quantityInput =  $("#quantity"+index); 
        	var salePriceInput = $("#salesPrice"+index); 
        	var wholeSalePriceInput = $("#wholeSalePrice"+index); 
        	var discountInput = $("#discount"+index); 
//        	var recCostInput = $("#recCost"+index); 
        	var numPerHandInput = $("#numPerHand" + index);
        	var yearInput = $("#year" + index);
        	var quarterInput = $("#quarter" + index);
        	var colorInput = $("#color" + index);
        	
        	barcodeInput.attr("value",products[i].productBarcode.barcode);
        	productIdInput.attr("value",products[i].productBarcode.id);
    		unitInput.attr("value",products[i].productBarcode.product.unit);
    		brandInput.attr("value",products[i].productBarcode.product.brand.brand_Name);
    		productCodeInput.attr("value",products[i].productBarcode.product.productCode);
    		quantityInput.attr("value",products[i].quantity);
    		yearInput.attr("value", products[i].productBarcode.product.year.year);
    		quarterInput.attr("value", products[i].productBarcode.product.quarter.quarter_Name);
    		var color = products[i].productBarcode.color;
    		if (color != null)
    		    colorInput.attr("value", color.name);
    		
            var salesPrice = products[i].salesPrice;
            if (salesPrice == "")
            	salePriceInput.attr("value","");
            else
    		    salePriceInput.attr("value",(salesPrice).toFixed(2));

            var wholeSalePrice = products[i].wholeSalePrice;
            var priceSelected = products[i].salePriceSelected;
            var wholeSalePrice1 = products[i].productBarcode.product.wholeSalePrice;
            var wholeSalePrice2 = products[i].productBarcode.product.wholeSalePrice2;
            var wholeSalePrice3 = products[i].productBarcode.product.wholeSalePrice3;
            var salesPriceFactory = products[i].productBarcode.product.salesPriceFactory;
            var discount =  products[i].discount;
            
            //to set the whole price with the recent one
            var select1 = "";
            var select2 = "";
            var select3 = "";
            var select4 = "";
            
            switch (priceSelected){
            	case wholeSalePrice1:
            		select1 = "selected";
            		break;
            	case wholeSalePrice2:
            		select2 = "selected";
            		break;
            	case wholeSalePrice3:
                	select3 = "selected";
                	break;
            	case salesPriceFactory:
                	select4 = "selected";
                	break;
                default: alert("no price");
            }

            wholeSalePriceInput.attr("value",(wholeSalePrice).toFixed(2));
            discountInput.attr("value",(discount).toFixed(2));

            
            //set the price select drop down
            $("#priceSlect" +index).empty();
            $("#priceSlect" +index).append("<option value='"+wholeSalePrice1+"'"+select1+">预设价1 "+wholeSalePrice1+"</option>"); 
            $("#priceSlect" +index).append("<option value='"+wholeSalePrice2+"'"+select2+">预设价2 "+wholeSalePrice2+"</option>"); 
            $("#priceSlect" +index).append("<option value='"+wholeSalePrice3+"'"+select3+">预设价3 "+wholeSalePrice3+"</option>"); 
            $("#priceSlect" +index).append("<option value='"+salesPriceFactory+"'"+select4+">零售价 "+salesPriceFactory+"</option>"); 
            
//            var recCost = products[i].productBarcode.product.recCost;
//            recCostInput.attr("value",(recCost).toFixed(2));
            
    		numPerHandInput.attr("value",products[i].numPerHand);

    	    barcodeInput.attr("readonly",true);
    	    
	        addNewRow();
    	}
    	
    	calculateTotal();
    }
	
    alert("完成导入，请检查");
}

/**
 * 打印小票配货单
 */
function printPOSOrderToPrinter(){
		try {
			var dfPrinter=pazu.TPrinter.getDefaultPrinter();
		
			if (dfPrinter == null){
		        alert("还未设置默认打印机，请设置后打印订货单");
			} else {
				if (dfPrinter != "POS58"){
					pazu.TPrinter.setDefaultPrinter("POS58") ;
					dfPrinter=pazu.TPrinter.getDefaultPrinter();
				}
				
				var clientName = $("#clientName").val();
				var orderId = $("#orderId").val();
				pazu.TPrinter.printToDefaultPrinter(clientName);
				pazu.TPrinter.printToDefaultPrinter("单据号: " + orderId);
				
				dfPrinter.FontBold=true;
				dfPrinter.FontSize=9;
			  	for (var i = 0; i < index; i++){
				   	var quantityInput =  $("#quantity"+i); 
					var yearInput = $("#year"+i); 
					var unitInput = $("#unit"+i); 
					var quarterInput = $("#quarter"+i); 
					var colorInput = $("#color"+i); 
					var brandInput = $("#brand"+i); 
					var productCodeInput = $("#productCode"+i); 
					
					var yearS = yearInput.val();
					var colorS = colorInput.val();
					if (colorS == "")
						colorS = "-";
			
					var j = i +1;
			        if (quantityInput.val()!= undefined && yearInput.val()!=undefined && quarterInput.val()!=undefined && brandInput.val()!=undefined && productCodeInput.val()!=undefined){
			        	tempstr1 = j + "   "  +  brandInput.val()  + " " + productCodeInput.val()+ "  " + yearS.substring(2) + "-" + quarterInput.val() ;
			        	pazu.TPrinter.printToDefaultPrinter(tempstr1);
		
			    	    tempstr2 = "       " + colorS + " " + quantityInput.val() + " " + unitInput.val();
			        	pazu.TPrinter.printToDefaultPrinter(tempstr2);  
			        }
				}
			  	var totalInput = $("#totalQuantity");
			  	pazu.TPrinter.printToDefaultPrinter("        总数 : " + totalInput.val());  
			  	
			  	dfPrinter.EndDoc();
			  	pazu.TPrinter.setDefaultPrinter("TSC TTP-244 Plus") ;
			}
	    } catch (e){
		    alert("小票打印有问题,请检查 : " + e.name + "\n" + e.message);
		    pazu.TPrinter.setDefaultPrinter("TSC TTP-244 Plus") ;
		}
}
function deleteOrder(){
	$.modalDialog({
		title : '授权删除单据',
		width : 330,
		height : 180,
		modal : true,
		href : baseurl +'/jsp/headQ/inventory/ConfirmDelete.jsp',
		buttons : [ {
			text : '授权删除',
			handler : function() {
				confirmDelete(); 
			}
		} ]
		});
}