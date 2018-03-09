//variable to identify it is form submit or close the page
var formSubmit = false;
var P_NUMBER = 1;
var N_PRINT = 1;
var P_DISCOUNT = 3;
var index = 0;
var indexR = 0;
var indexF = 0;
var RETURN_SUFFIX ="R";
var FREE_SUFFIX ="F";

//discount type
var DIS_NORMAL = 1;
var DIS_DOWN = 2;
var DIS_ROUND = 3;
var DIS_UP = 4;

//constants element type
var SALES_ORDER = 1;
var RETURN_ORDER = 2;
var SALES_EXCHANGE = 3;

var errorIndexes = new Array();
var errorIndexesR = new Array();
var errorIndexesF = new Array();


/**
 * user input the product code and press enter
 */
function searchProductsProductCode(index_trigger, suffix){
	var productCode = $.trim($("#productCode" + suffix +index_trigger).val());
    
	if (validateProductCodeInput(productCode)){
	    var url = encodeURI(encodeURI("actionChain/chainSalesJSPAction!scanByProductCode?formBean.productCode=" + productCode + "&formBean.index=" + index_trigger+ "&formBean.suffix=" + suffix +"&formBean.chainId=" + $("#chainStore").val()))  ; 
	
	    //window.showModelessDialog(url);
	    window.open(url,'销售单','height=530, width=650, toolbar=no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=no');    
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
 * retrieve the product by input barcode
 */
function retrieveProductByBarcode(index_trigger, suffix, currentBarcode){
	if (validateRowInput(index_trigger,suffix,currentBarcode)){	
		var discount = $("#vipDiscount").val();  
	    var params= "formBean.barcode=" + currentBarcode + "&formBean.index=" + index_trigger + "&formBean.discount=" + discount + "&formBean.suffix=" + suffix+ "&formBean.chainId=" + $("#chainStore").val() ; 
	
	    $.post("actionChain/chainSalesJSONAction!scanByBarcode",params, backRetrievePByBarcodeProcess,"json");
	}
}
function backRetrievePByBarcodeProcess(data){
	var error = data.error;
	var index_c = data.index;
	var suffix = data.suffix;
	
	if (error == true){
		alert("无法找到对应货品.请检查条码，重新输入!");

		clearRowCells(index_c);
		
		$("#barcode" + suffix +index_c).select();
	}else{
		
		var productIdInput =  $("#productId" +suffix+index_c);  
       	var unitInput =  $("#unit" +suffix+index_c);  
       	var colorInput =  $("#color" +suffix+index_c);  
    	var brandInput =  $("#brand" +suffix+index_c);   
    	var productCodeInput = $("#productCode"+suffix+index_c); 
    	var quantityInput =  $("#quantity"+suffix+index_c); 
    	var retailPriceInput = $("#retailPrice"+suffix+index_c); 
    	var amountInput = $("#amount"+suffix+index_c); 
    	var discountRateInput = $("#discountRate"+suffix+index_c); 
    	var dicountPriceInput = $("#dicountPrice"+suffix+index_c); 
    	var discountAmountInput = $("#discountAmount"+suffix+index_c); 
    	var barcodeInput = $("#barcode"+suffix+index_c); 
    	var inventoryInput = $("#inventory"+suffix+index_c); 
    	var defaultDiscountInput = $("#defaultDiscount"); 
    	var quarterInput = $("#quarter"+suffix+index_c); 
    	
    	var discount = 1;
    	var defaultDiscount = defaultDiscountInput.val();
    	if ($.isNumeric(defaultDiscount) && defaultDiscount > 0 && defaultDiscount <= 1){
    		discount = defaultDiscount;
    	}
    	
    	//@temp fix 2015.12月作废
    	//对于南充连锁店的2015秋的衣服0.8折处理
    	if ($("#chainStore").val()==84 && data.productBarcode.product.year.year_ID == 5 && data.productBarcode.product.quarter.quarter_ID == 3){
    		discount = 0.8;
    	}
    	
		var barcode = data.productBarcode.barcode;
		var productId = data.productBarcode.id;
		var product = data.productBarcode.product;
		var discountD = data.discount;
		if (discountD < discount)
			discount = discountD;
		
		var productCode = product.productCode;
		var brand = product.brand.brand_Name;
		var unit = product.unit;
		var color = data.productBarcode.color;
		var colorName = "";
		if (color != null)
    		colorName = color.name;
		
		var quantity = 1;
		var retailPrice = product.salesPrice;
		var inventory = data.inventory;
		var quarterName = product.year.year + " " + product.quarter.quarter_Name;

		colorInput.attr("value", colorName);
		productIdInput.attr("value",productId);
		barcodeInput.attr("value",barcode);
		productCodeInput.attr("value",productCode);
		unitInput.attr("value",unit);
    	brandInput.attr("value",brand); 
    	quantityInput.attr("value",quantity); 
    	retailPriceInput.attr("value",(retailPrice).toFixed(P_NUMBER));
    	amountInput.attr("value",(retailPrice * quantity).toFixed(P_NUMBER));
    	discountRateInput.attr("value",discount);
    	dicountPriceInput.attr("value",(discount * retailPrice).toFixed(P_NUMBER));
    	discountAmountInput.attr("value",(discount * retailPrice * quantity).toFixed(P_NUMBER)); 
    	inventoryInput.attr("value", inventory);
    	quarterInput.attr("value", quarterName);
 
    	//if the user just changed the original barcode information
    	//then no need to add the index by one
    	if (suffix == RETURN_SUFFIX){
	    	if (index_c == indexR){
		       indexR = indexR +1;	
		       addNewRowR();
		    }
    	} else if (suffix == ""){
	    	if (index_c == index){
	           index = index +1;	
	    	   addNewRow();
	    	}
    	} else if (suffix == FREE_SUFFIX){
	    	if (index_c == indexF){
		           indexF = indexF +1;	
		    	   addNewRowF();
		    	}
    	}
    	
    	if (suffix == RETURN_SUFFIX){
        	$("#barcodeR"+indexR).focus(); 
    	} else if (suffix == ""){
    		$("#barcode"+index).focus(); 
    	} else if (suffix == FREE_SUFFIX){
    		$("#barcodeF"+indexF).focus(); 
 	    }

    	calculateTotal();
	}
}

/**
 * to validate whether the product is selected or not
 */
function validateRowInput(index_trigger,suffix, currentBarcode){
	if (currentBarcode.length != 12){
		alert(currentBarcode + " 不是一个正常条形.请重新输入.");
		clearRowCells(index_trigger);
		$("#barcode" + suffix +index_trigger).select();
		return false;
    } else {
		for (var i = 0; i < index_trigger; i++){
			var barcodeInput =  $("#barcode" + suffix +i);
			if (currentBarcode == barcodeInput.val()){
				var currentQ = $("#quantity" + suffix +i).val();
				$("#quantity" + suffix +i).attr("value" , parseInt(currentQ) + 1);
				
				changeRowValue(suffix, i);
				
				clearRowCells(index_trigger,suffix);
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
function validateRowInputFromChild(index_trigger,suffix, currentBarcode){
	for (var i = 0; i < index_trigger; i++){
		var barcodeInput =  $("#barcode" + suffix +i);
		if (currentBarcode == barcodeInput.val()){
			var currentQ = $("#quantity" + suffix +i).val();
			$("#quantity" + suffix +i).attr("value" , parseInt(currentQ) + 1);
			
			changeRowValue(suffix, i);

			return false;
		}
	}
	return true;
}

/**
 * to clear the corresponding row's data
 */
function clearRowCells(index_c, suffix){
	//clear the cell
	$("#productId" +suffix+index_c).attr("value","");  
   	$("#unit" +suffix+index_c).attr("value","");  
	$("#brand" +suffix+index_c).attr("value","");   
	$("#productCode"+suffix+index_c).attr("value",0); 
	$("#quantity"+suffix+index_c).attr("value",0); 
	$("#retailPrice"+suffix+index_c).attr("value",0); 
	$("#amount"+suffix+index_c).attr("value",0); 
	$("#discountRate"+suffix+index_c).attr("value",0); 
	$("#dicountPrice"+suffix+index_c).attr("value",0); 
	$("#discountAmount"+suffix+index_c).attr("value",0); 	
	$("#memo"+suffix+index_c).attr("value",""); 
	$("#barcode"+suffix+index_c).attr("value",""); 

	calculateTotal();
}

/**
 * once the product row value is changed, the row data should be refreshed
 */
function changeRowValue(suffix, c_index){

   	var quantityInput =  $("#quantity"+suffix+c_index); 
	var retailPriceInput = $("#retailPrice"+suffix+c_index); 
	var amountInput = $("#amount"+suffix+c_index); 
	var discountRateInput = $("#discountRate"+suffix+c_index); 
	var dicountPriceInput = $("#dicountPrice"+suffix+c_index); 
	var discountAmountInput = $("#discountAmount"+suffix+c_index); 	

	var discount = discountRateInput.val();
	var quantity = quantityInput.val();
	var retailPrice = retailPriceInput.val();

	amountInput.attr("value",(retailPrice * quantity).toFixed(P_NUMBER));
	dicountPriceInput.attr("value",(discount * retailPrice).toFixed(P_NUMBER));
	discountAmountInput.attr("value",(discount * retailPrice * quantity).toFixed(P_NUMBER)); 

	calculateTotal();
}

/**
 * the onchange on the Discount Price
 * @param index_c
 */
function changeRetailPrice(suffix, index_c){
	var amountInput = $("#amount"+suffix+index_c); 
	var dicountPriceInput = $("#dicountPrice"+suffix+index_c); 
	var discountAmountInput = $("#discountAmount"+suffix+index_c); 
	var discount = $("#discountRate"+suffix+index_c).val();
	var retailPrice = $("#retailPrice"+suffix+index_c).val(); 
	var quantity =  $("#quantity"+suffix+index_c).val(); 

	
	//to calculate new value
	amountInput.attr("value",(retailPrice * quantity).toFixed(P_NUMBER));
	dicountPriceInput.attr("value",(discount * retailPrice).toFixed(P_NUMBER));
	discountAmountInput.attr("value",(discount * retailPrice * quantity).toFixed(P_NUMBER)); 
	
	calculateTotal();
}

/**
 * the onchange on the Discount Price
 * @param index_c
 */
function changeDiscountPrice(indicator,index_c){
	var suffix ="";
	if (indicator == 3)
		suffix = "R";
	
	var discountPrice = $("#dicountPrice" +suffix+index_c).val(); 
	var retailPrice = $("#retailPrice"+suffix+index_c).val(); 
	var amount = $("#amount"+suffix+index_c).val(); 
	
	//to get the discount rate
//	alert(discountPrice +","+ retailPrice);
	var rate_new = discountPrice / retailPrice;
	
	//to calculate new value
	var discountRateAmount = amount * rate_new;
	
	$("#discountRate"+suffix+index_c).attr("value", (rate_new).toFixed(P_DISCOUNT)); 
	$("#discountAmount"+suffix+index_c).attr("value", (discountRateAmount).toFixed(P_NUMBER)); 
	
	calculateTotal();
}

/**
 * change the discount amount or coupon
 * for sale out and sale return
 */
function changeDiscountCoupon(){
//	var orderType = $("#orderType").val();
	var discount = $("#discountAmount").val(); 
	var coupon = $("#coupon").val(); 
	var netAmount = $("#netAmount").val(); 
	var netAmountR = $("#netAmountR").val();
	var vipPrepaid = $("#chainPrepaidAmt").val();
	var vipScore = $("#vipScore").val();
	
	
	if (netAmount == undefined)
		   netAmount = 0;
	
	if (netAmountR == undefined)
	   netAmountR = 0;
		
	var amountAfterDC = 0;
	
	amountAfterDC = netAmount - coupon - discount - netAmountR - vipPrepaid - vipScore;	
	
	$("#amountAfterDC").attr("value", (amountAfterDC).toFixed(P_NUMBER));

	changeCashCardAmountValue();
}


/**
 * change the discount amount or coupon
 * for sale out and sale return
 */
function checkDiscountType(){
//	var orderType = $("#orderType").val();
	var discount = $("#discountAmount").val(); 
	var coupon = $("#coupon").val(); 
	var netAmount = $("#netAmount").val(); 
	var netAmountR = $("#netAmountR").val();
	var vipPrepaid = $("#chainPrepaidAmt").val();
	
	if (netAmount == undefined)
		   netAmount = 0;
	
	if (netAmountR == undefined)
	   netAmountR = 0;
		
	var amountAfterDC = 0;
	
	var discountType = $("#discountAmtType").val();
	if (discountType == DIS_DOWN){
		var floor = amountAfterDC;
		amountAfterDC = netAmount - coupon - netAmountR -vipPrepaid;
		if (amountAfterDC > 0)
		   floor = Math.floor(amountAfterDC);
		else if (amountAfterDC <0)
		   floor = Math.ceil(amountAfterDC);
		
		discount = (amountAfterDC - floor).toFixed(P_NUMBER);

		$("#discountAmount").attr("value", discount);
	} else if (discountType == DIS_ROUND){
		var round = amountAfterDC;
		amountAfterDC = netAmount - coupon - netAmountR -vipPrepaid;	
		round = Math.round(amountAfterDC);
		discount = (amountAfterDC - round).toFixed(P_NUMBER);

		$("#discountAmount").attr("value", discount);
		
	} else if (discountType == DIS_UP){
		var ceil = amountAfterDC;
		amountAfterDC = netAmount - coupon - netAmountR - vipPrepaid;
		if (amountAfterDC > 0)
			ceil = Math.ceil(amountAfterDC);
		else if (amountAfterDC <0)
			ceil = Math.floor(amountAfterDC);
		
		discount = (amountAfterDC - ceil).toFixed(P_NUMBER);

		$("#discountAmount").attr("value", discount);
	}
	
	amountAfterDC = netAmount - coupon - discount - netAmountR - vipPrepaid;	
	
	$("#amountAfterDC").attr("value", (amountAfterDC).toFixed(P_NUMBER));

	changeCashCardAmountValue();
}



/**
 * 改变现金，卡，微信支付，支付宝金额的输入框
 */
function changeCashCardAmountValue(){
	var cash = $("#cashAmount").val(); 
	var card = $("#cardAmount").val();
	var wechat = $("#wechatAmount").val();
	var alipay =  $("#alipayAmount").val();

	var amountAfterDC = $("#amountAfterDC").val(); 


	if ((card != 0 && card != "") || (cash!=0 && cash!="") || (wechat!=0 && wechat!="") || (alipay!=0 && alipay!="")   ){
        var returnValue = cash - (amountAfterDC - card - wechat - alipay);	
	    $("#returnAmount").attr("value", (returnValue).toFixed(P_NUMBER));
	} else 
		$("#returnAmount").attr("value", 0.0);
}

/**
 * to calculate the total value for the sales out order
 */
function calculateTotal(){
//	var orderType = $("#orderType").val();

	calculateSalesOutTotal();
	calculateSalesReturnTotal();
	calculateFreeTotal();
		
	//recalculate the amount
	checkDiscountType();
}

//
//calculate the sales out and sales return total
//
function calculateSalesOutTotal(){
	var quantity_t =0;
	var amount_t = 0;
	var dis_amount_t = 0;

	for (var i = 0; i < index; i++){
	   	var quantityInput =  $("#quantity"+i); 
		var amountInput = $("#amount"+i); 
		var discountAmountInput = $("#discountAmount"+i); 
        if (quantityInput.val()!= undefined && amountInput.val()!=undefined && discountAmountInput.val()!=undefined){
		   quantity_t += parseInt(quantityInput.val());
		   amount_t += parseFloat(amountInput.val());
		   dis_amount_t += parseFloat(discountAmountInput.val());
        }
	}

   	$("#totalQuantity").attr("value",quantity_t); 
	$("#totalAmount").attr("value",(amount_t).toFixed(P_NUMBER)); 
	$("#netAmount").attr("value",(dis_amount_t).toFixed(P_NUMBER)); 

}

//
//calculate the sales exchange total
//
function calculateSalesReturnTotal(){
	var quantity_t =0;
	var amount_t = 0;
	var dis_amount_t = 0;

	for (var i = 0; i < indexR; i++){
	   	var quantityInput =  $("#quantityR"+i); 
		var amountInput = $("#amountR"+i); 
		var discountAmountInput = $("#discountAmountR"+i); 
        if (quantityInput.val()!= undefined && amountInput.val()!=undefined && discountAmountInput.val()!=undefined){
		   quantity_t += parseInt(quantityInput.val());
		   amount_t += parseFloat(amountInput.val());
		   dis_amount_t += parseFloat(discountAmountInput.val());
        }
	}

   	$("#totalQuantityR").attr("value",quantity_t); 
	$("#totalAmountR").attr("value",(amount_t).toFixed(P_NUMBER)); 
	$("#netAmountR").attr("value",(dis_amount_t).toFixed(P_NUMBER)); 
}

//
//calculate the Free total 赠品
//
function calculateFreeTotal(){
	var quantity_f =0;

	for (var i = 0; i < indexF; i++){
	   	var quantityInput =  $("#quantityF"+i); 
        if (quantityInput.val()!= undefined){
		   quantity_f += parseInt(quantityInput.val());
        }
	}

 	$("#totalQuantityF").attr("value",quantity_f); 
}

function changeSelect(){
	$("#barcode" + index).focus();
}
//----------------------------------------------------------------------
function addNewRow(){
	var pre_index = index -1;
	
	$("#delIcon"+pre_index).show();
    var str = "";
    str += "<tr class='InnerTableContent' id='orderRow"+index+"'>";   
    str += "<td height='25'>"+(index +1)+"</td>";  
    str += "<td><input type='text' name='formBean.chainSalesOrder.productList["+index+"].productBarcode.barcode' id='barcode"+index+"' size='11'/>" +
               "<input type='hidden' name='formBean.chainSalesOrder.productList["+index+"].productBarcode.id' id='productId"+index+"'/></td>";  
    str += "<td><input type='text' name='formBean.chainSalesOrder.productList["+index+"].productBarcode.product.productCode' id='productCode"+index+"' size='9'/></td>";  
    str += "<td><input type='text' name='formBean.chainSalesOrder.productList["+index+"].productBarcode.color.name' id='color"+index+"' size='4' readonly/></td>"; 
    str += "<td><input type='text' name='formBean.chainSalesOrder.productList["+index+"].productBarcode.product.brand.brand_Name' id='brand"+index+"'  size='9' readonly/></td>";  
    str += "<td><input type='text' name='formBean.chainSalesOrder.productList["+index+"].productBarcode.product.quarter.quarter_Name' id='quarter"+index+"'  size='6' readonly/></td>";  
    str += "<td><input type='text' name='formBean.chainSalesOrder.productList["+index+"].productBarcode.product.unit' id='unit"+index+"' size='4' readonly/></td>";  
    str += "<td><input type='text' name='formBean.chainSalesOrder.productList["+index+"].quantity' id='quantity"+index+"'  size='2'  onfocus='this.select();' onchange='changeRowValue(\"\","+index+")' onkeypress='return is_number(event);'/></td>";  
    str += "<td><input type='text' name='formBean.chainSalesOrder.productList["+index+"].retailPrice' id='retailPrice"+index+"' readonly size='2'/></td>";  
    str += "<td><input type='text' id='amount"+index+"'  size='2' readonly='readonly'/></td>";  
    str += "<td><input type='text' name='formBean.chainSalesOrder.productList["+index+"].discountRate' id='discountRate"+index+"'  size='2'  onfocus='this.select();' onchange='changeRowValue(\"\","+index+")'/></td>";  
    str += "<td><input type='text' id='dicountPrice"+index+"'  size='4'  onfocus='this.select();' onchange='changeDiscountPrice(1,"+index+")'/></td>";  
    str += "<td><input type='text' id='discountAmount"+index+"'  size='4' readonly/></td>";  
    str += "<td><input type='text' id='inventory"+index+"'  size='1' readonly/></td>"; 
    str += "<td><select id='normalPrice"+index+"' name='formBean.chainSalesOrder.productList["+index+"].normalSale' onchange='changeSelect();'>" +
    			    "<option value='1'>正价</option>"+
    				"<option value='2'>特价</option> "+  
    				"</select></td>";

    str += "<td><div id='delIcon"+index+"' style='display:none'><a href='javascript:deleteRow(\"orderRow"+index +"\");'><img src='"+baseurl+"/conf_files/web-image/delete.png' border='0' style='cursor:pointer;'/></a></div></td>";  
    str += "</tr>";   

    $("#orderTablebody").append(str);

    //freeze the previous row
    var barcodeInput = $("#barcode"+pre_index);
    barcodeInput.attr("readonly",true);
    
    var productCodeInput = $("#productCode"+pre_index);
    productCodeInput.attr("readonly",true);
    
    $("#orderRow"+index).bind('keyup',onkeyup);
}

//ADD
function addNewRowR(){
	var pre_index = indexR -1;
	
	$("#delIconR"+pre_index).show();
    var str = "";
    str += "<tr class='InnerTableContent' id='orderRowR"+indexR+"'>";   
    str += "<td height='25'>"+(indexR +1)+"</td>";  
    str += "<td><input type='text' name='formBean.chainSalesOrder.productListR["+indexR+"].productBarcode.barcode' id='barcodeR"+indexR+"' size='11'/>" +
               "<input type='hidden' name='formBean.chainSalesOrder.productListR["+indexR+"].productBarcode.id' id='productIdR"+indexR+"' size='11'/>"+
               "<input type='hidden' name='formBean.chainSalesOrder.productListR["+indexR+"].type' value='2' size='13'/></td>";  
    str += "<td><input type='text' name='formBean.chainSalesOrder.productListR["+indexR+"].productBarcode.product.productCode' id='productCodeR"+indexR+"' size='9'/></td>";  
    str += "<td><input type='text' name='formBean.chainSalesOrder.productListR["+index+"].productBarcode.color.name' id='colorR"+index+"' size='4' readonly/></td>";  
    str += "<td><input type='text' name='formBean.chainSalesOrder.productListR["+indexR+"].productBarcode.product.brand.brand_Name' id='brandR"+indexR+"'  size='9' readonly/></td>";  
    str += "<td><input type='text' name='formBean.chainSalesOrder.productListR["+indexR+"].productBarcode.product.quarter.quarter_Name' id='quarterR"+indexR+"'  size='6' readonly/></td>";  
    str += "<td><input type='text' name='formBean.chainSalesOrder.productListR["+indexR+"].productBarcode.product.unit' id='unitR"+indexR+"' size='4' readonly/></td>";  
    str += "<td><input type='text' name='formBean.chainSalesOrder.productListR["+indexR+"].quantity' id='quantityR"+indexR+"'  size='2'  onfocus='this.select();' onchange='changeRowValue(\"R\","+indexR+");' onkeypress='return is_number(event);'/></td>";  
    str += "<td><input type='text' name='formBean.chainSalesOrder.productListR["+indexR+"].retailPrice' id='retailPriceR"+indexR+"' readonly size='2'/></td>";  
    str += "<td><input type='text' id='amountR"+indexR+"'  size='2' readonly='readonly'/></td>";  
    str += "<td><input type='text' name='formBean.chainSalesOrder.productListR["+indexR+"].discountRate' id='discountRateR"+indexR+"'  size='2'  onfocus='this.select();' onchange='changeRowValue(\"R\","+indexR+");'/></td>";  
    str += "<td><input type='text' id='dicountPriceR"+indexR+"'  size='4'  onfocus='this.select();' onchange='changeDiscountPrice(3,"+indexR+")'/></td>";  
    str += "<td><input type='text' id='discountAmountR"+indexR+"'  size='4' readonly/></td>";  
    str += "<td><input type='text' id='inventoryR"+indexR+"'  size='1' readonly/></td>"; 
    str += "<td></td>";
    str += "<td><div id='delIconR"+indexR+"' style='display:none'><a href='javascript:deleteRow(\"orderRowR"+indexR +"\");'><img src='"+baseurl+"/conf_files/web-image/delete.png' border='0' style='cursor:pointer;'/></a></div></td>";  
    str += "</tr>";   

    $("#orderTablebodyR").append(str);

    //freeze the previous row
    var barcodeInput = $("#barcodeR"+pre_index);
    barcodeInput.attr("readonly",true);

    var productCodeInput = $("#productCodeR"+pre_index);
    productCodeInput.attr("readonly",true);
    
    $("#orderRowR"+indexR).bind('keyup',onkeyup);
}

//add the row for 赠品
function addNewRowF(){
	var pre_index = indexF -1;
	
	$("#delIconF"+pre_index).show();
    var str = "";
    str += "<tr class='InnerTableContent' id='orderRowF"+indexF+"'>";   
    str += "<td height='25'>"+(indexF +1)+"</td>";  
    str += "<td><input type='text' name='formBean.chainSalesOrder.productListF["+indexF+"].productBarcode.barcode' id='barcodeF"+indexF+"' size='11'/>" +
               "<input type='hidden' name='formBean.chainSalesOrder.productListF["+indexF+"].productBarcode.id' id='productIdF"+indexF+"'/>"+
               "<input type='hidden' name='formBean.chainSalesOrder.productListF["+indexF+"].type' value='3'/></td>";  
    str += "<td><input type='text' name='formBean.chainSalesOrder.productListF["+indexF+"].productBarcode.product.productCode' id='productCodeF"+indexF+"' size='9'/></td>";  
    str += "<td><input type='text' name='formBean.chainSalesOrder.productListF["+index+"].productBarcode.color.name' id='colorF"+indexF+"' size='4' readonly/></td>";  
    str += "<td><input type='text' name='formBean.chainSalesOrder.productListF["+indexF+"].productBarcode.product.brand.brand_Name' id='brandF"+indexF+"'  size='9' readonly/></td>";  
    str += "<td><input type='text' name='formBean.chainSalesOrder.productListF["+indexF+"].productBarcode.product.quarter.quarter_Name' id='quarterF"+indexF+"'  size='6' readonly/></td>";  
    str += "<td><input type='text' name='formBean.chainSalesOrder.productListF["+indexF+"].productBarcode.product.unit' id='unitF"+indexF+"' size='4' readonly/></td>";  
    str += "<td><input type='text' name='formBean.chainSalesOrder.productListF["+indexF+"].quantity' id='quantityF"+indexF+"'  size='2' onfocus='this.select();' onchange='changeRowValue(\"F\","+indexF+")' onkeypress='return is_number(event);'/></td>";  
    str += "<td><input type='text' id='inventoryF"+indexF+"'  size='1' readonly/></td>"; 
    str += "<td><div id='delIconF"+indexF+"' style='display:none'><a href='javascript:deleteRow(\"orderRowF"+indexF +"\");'><img src='"+baseurl+"/conf_files/web-image/delete.png' border='0' style='cursor:pointer;'/></a></div></td>";  
    str += "</tr>";   

    $("#orderTablebodyF").append(str);

    //freeze the previous row
    var barcodeInput = $("#barcodeF"+pre_index);
    barcodeInput.attr("readonly",true);

    var productCodeInput = $("#productCodeF"+pre_index);
    productCodeInput.attr("readonly",true);
    
    $("#orderRowF"+indexF).bind('keyup',onkeyup);
}


function deleteRow(rowID){
	$("#"+rowID).remove(); 
 	calculateTotal();
 	
 	$("#barcode" + index).focus();
}

//-----------------------------------------------------------------------------------------

function validateDraftSalesForm(){
//	  if (confirm("你确认提交单据?")){
	    	for (var i =0; i < index; i++){
	    		errorIndexes.length = 0;
	    		$("#orderRow" + i).css('background-color', '#FFFFFF');
	    	}
	    	for (var i =0; i < indexR; i++){
	    		errorIndexesR.length = 0;
	    		$("#orderRowR" + i).css('background-color', '#FFFFFF');
	    	}
	    	for (var i =0; i < indexF; i++){
	    		errorIndexesF.length = 0;
	    		$("#orderRowF" + i).css('background-color', '#FFFFFF');
	    	}    	
			var error = "";
			if ($("#chainStore").val() == 0){
				error += "连锁店 - 必须填!\n";
			}

			if ($("#chainSaler").val() == 0){
				error += "经手人员 - 必须选!\n";
			}

			if (isNaN($("#discountAmount").val())){
				error += "优惠金额 必须是数字!\n";
			}	
			if (isNaN($("#coupon").val())){
				error += "代金券 必须是数字!\n";
			}	
			if (isNaN($("#cashAmount").val())){
				error += "现金 必须是数字!\n";
			}	
			if (isNaN($("#cardAmount").val())){
				error += "刷卡 必须是数字!\n";
			}	
			if (isNaN($("#vipScore").val())){
				error += "积分抵换现金 必须是数字!\n";
			}	
			if (isNaN($("#chainPrepaidAmt").val())){
				error += "预付款 必须是数字!\n";
			}	
			
			var hasChar_q = false;
			var hasChar_d = false;
			var hasChar_p = false;
			for (var i = 0; i < index; i++){
				var q = $("#quantity" + i).val();
				var d = $("#discountRate" + i).val();
				var p = $("#retailPrice" + i).val();
				if (q != undefined){
					if (isNaN(q) || q==0){
						hasChar_q = true;
						errorIndexes.push(i);
					}
				}
				if (d != undefined){
					if (isNaN(d) || d<=0){
						hasChar_d = true;
						errorIndexes.push(i);
					}
				}
				if (p != undefined){
					if (isNaN(p) || p<=0){
						hasChar_p = true;
						errorIndexes.push(i);
					}
				}
			}
			for (var i = 0; i < indexR; i++){
				var q = $("#quantityR" + i).val();
				var d = $("#discountRateR" + i).val();
				var p = $("#retailPriceR" + i).val();
				if (q != undefined){
					if (isNaN(q) || q==0){
						hasChar_q = true;
						errorIndexesR.push(i);
					}
				}
				if (d != undefined){
					if (isNaN(d) || d<=0){
						hasChar_d = true;
						errorIndexesR.push(i);
					}
				}
				if (p != undefined){
					if (isNaN(p) || p<=0){
						hasChar_p = true;
						errorIndexesR.push(i);
					}
				}
			}
			for (var i = 0; i < indexF; i++){
				var q = $("#quantityF" + i).val();
				if (q != undefined){
					if (isNaN(q) || q==0){
						hasChar_q = true;
						errorIndexesF.push(i);
					}
				}
			}
			if (hasChar_q)
				error += "数量 - 必须为大于0的整数. 请检查后重新输入!\n";
			if (hasChar_p)
				error += "单价 - 必须为大于0的数字. 请检查后重新输入!\n";
			if (hasChar_d)
				error += "折扣 - 必须为大于0的数字. 请检查后重新输入!\n";

			if (error != ""){
				for (var i =0; i < errorIndexes.length; i++){
					$("#orderRow" + errorIndexes[i]).css('background-color', '#EE8553');
				}
				for (var i =0; i < errorIndexesR.length; i++){
					$("#orderRowR" + errorIndexesR[i]).css('background-color', '#EE8553');
				}		
				for (var i =0; i < errorIndexesF.length; i++){
					$("#orderRowF" + errorIndexesF[i]).css('background-color', '#EE8553');
				}
				alert(error);
				return false;
			}else{
				var totalQ = $("#totalQuantity").val();
				var totalQR = $("#totalQuantityR").val();
				var totalQF = $("#totalQuantityF").val();
				if ((totalQ =="" || totalQ==0) && (totalQR =="" || totalQR==0) &&(totalQF =="" || totalQF==0)){
					alert("请输入货品后再保存单据!");
					return false;
				}

				if ($("#vipCardNoHidden").val() != ""){
					var vipMaxCash = parseFloat($("#maxVipCash").val());
					var vipCash = parseFloat($("#vipScore").val());

					if (vipCash != 0 && vipCash > vipMaxCash){
						alert("超过此VIP卡最多可换现金 : " + vipMaxCash);
						return false;
					} else if (vipCash < 0){
						alert("积分换现金  必须为大于或者等于0");
						return false;		
					}
				}
				
                return true;
			}
//	    } else {
//	    	return false;
//	    }
}

function validateSalesOrder(orderType){
//	calculateTotal();

	var cash = $("#cashAmount").val(); 
	var card = $("#cardAmount").val();
	var wechat = $("#wechatAmount").val();
	var alipay =  $("#alipayAmount").val();
	
	var amountAfterDC = $("#amountAfterDC").val(); 
	var returnAmount = (parseFloat($("#returnAmount").val())).toFixed(P_NUMBER);

	var returnValue = (cash - (amountAfterDC - card - wechat - alipay)).toFixed(P_NUMBER);
	
	if (amountAfterDC >= 0){
		if (returnAmount != returnValue || returnAmount < 0){
			alert("请检查你的收款金额，刷卡金额和找零金额，你应该收现金");
			return false;
		} else if (cash < 0 || card < 0  || returnAmount < 0){
    		if (cash < 0)
    			$("#cashAmount").select();
    		else if (card < 0)
    			$("#cardAmount").select();
    		
			alert("请检查你的收款金额，刷卡金额和找零金额，你应该收现金");
			return false;
		} else if (cash > 15000 || card > 15000  || returnAmount > 15000){
			alert("现金/刷卡 不是一个正常的收银数字(超过15000),请检查");
			return false;
		}
    } else {
    	 if (cash > 15000 || card > 15000 || returnAmount > 15000){
			alert("现金/刷卡 不是一个正常的收银数字(超过15000),请检查");
			return false;
		} else if ((cash == 0 && card == 0) || returnAmount >0){
	    	if (cash > 0)
				$("#cashAmount").select();
			else if (card > 0)
				$("#cardAmount").select();
			else 
				$("#cashAmount").select();
			
			alert("客户退货你应该付款。付款不够。请检查你的付现金 ");
			return false;
    	}
    }

	return true;
}

/**
 * after change the chain store, we need change the user list as well
 */
function changeChainStore(){
	var chainStore = $("#chainStore").val();
	var params = "formBean.chainSalesOrder.chainStore.chain_id=" + chainStore;
	$.post(baseurl + "/actionChain/chainSalesJSONAction!changeChainStore",params, backProcessChangeChainStore,"json");
}
function backProcessChangeChainStore(data){
	var users =  data.chainUsers;
	if (users != undefined){
		$("#chainSaler").empty();

		for (var i = 0; i < users.length; i++)
			   $("#chainSaler").append("<option value='"+users[i].user_id+"'>"+users[i].name+"</option>"); 
	}
	
	var chainConf = data.chainStoreConf;
	if (chainConf != undefined){
		$("#printCopy").attr("value",chainConf.printCopy);
	    $("#minDiscountRate").attr("value",chainConf.minDiscountRate);
	    $("#discountAmtType").attr("value",chainConf.discountAmtType);
	    $("#lowThanCostAlert").attr("value",chainConf.lowThanCostAlert);
	    $("#defaultDiscount").attr("value", chainConf.defaultDiscount);
	}
	
	var chainStore = data.chainStore;
	if (chainStore != undefined){
		$("#printHeader").attr("value",chainStore.printHeader);
	}
}



/**
 * delete the sales order
 */
function deleteOrder(){
	if (confirm("你确定要删除这张单据?")){
	   formSubmit = true;
	   document.chainStoreSalesOrderForm.action = "chainSalesJSPAction!deleteOrder";
	   document.chainStoreSalesOrderForm.submit();
	}
}





