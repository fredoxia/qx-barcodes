document.onkeydown = BSkeyDown; 

var saleBarcodeReg = /barcode+\d/;
var returnBarcodeReg = /barcodeR+\d/;
var freeBarcodeReg = /barcodeF+\d/;
function BSkeyDown(e){
	 //var keys = { left: 37, up: 38, right: 39, down: 40, home: 36, end: 35 };

	 var ieKey = event.keyCode;
	 var srcId = event.srcElement.id;
	 var srcType = event.srcElement.type;
	 switch(ieKey){
	 //home
	 case 35:
		   var vipCardDisable = $("#vipCardNo").attr("disabled");
		   var vipExtralScoreDisable = $("#extralVipScore").attr("disabled");
		   
		   if (srcId == "vipCardNo"){
			   $("#barcode" + index).focus();
		   } else if (saleBarcodeReg.test(srcId)) {
			   var index_trigger = srcId.substring(7); 
			   if (index_trigger == index)
			      $("#barcodeR" + indexR).focus();
		   } else if (returnBarcodeReg.test(srcId)) {
			   var index_trigger = srcId.substring(8); 
			   if (index_trigger == indexR)
			      $("#barcodeF" + indexF).focus();
		   } else if (freeBarcodeReg.test(srcId)) {
			   var index_trigger = srcId.substring(8); 
			   if (index_trigger == indexF)
			     if (vipExtralScoreDisable == "disabled")
				     $("#discountAmount").select();
			     else 
			         $("#extralVipScore").select();
		   } else if (srcId == "discountAmount" || srcId == "extralVipScore"){
			   $("#chainSaler").focus();
		   } else if (srcId == "chainSaler"){
 			     if (vipCardDisable == "disabled")
 			        $("#barcode" + index).focus();
 		         else 
 			        $("#vipCardNo").select();
		   } else {
			     if (vipExtralScoreDisable == "disabled")
				     $("#discountAmount").select();
			     else 
			         $("#extralVipScore").select();

		   }  
		   break;
    //end
	case 36:
		   var vipCardDisable = $("#vipCardNo").attr("disabled");
		   var vipExtralScoreDisable = $("#extralVipScore").attr("disabled");
		   if (srcId == "discountAmount" || srcId == "extralVipScore")
			   $("#barcodeF" + indexF).focus();
		   else if (freeBarcodeReg.test(srcId))
			   $("#barcodeR" + indexR).focus();
		   else if (returnBarcodeReg.test(srcId))
			   $("#barcode" + index).focus();
		   else if (saleBarcodeReg.test(srcId)){
			   if (vipCardDisable == "disabled")
				   $("#chainSaler").focus();
			   else 
				   $("#vipCardNo").select();
		   } else if (srcId == "vipCardNo"){
			   $("#chainSaler").focus();
		   }  else {
			     if (vipExtralScoreDisable == "disabled")
				     $("#discountAmount").select();
			     else 
			         $("#extralVipScore").select();
		   }  
		   break;
	//回车
	case 13:			 
			   if (srcId.substring(0,7)=="barcode"){
		           var index_trigger ;

				   var suffix = srcId.substring(7,8);
				   if (suffix == RETURN_SUFFIX){
					   index_trigger = srcId.substring(8); 
				   }else if (suffix == FREE_SUFFIX){
					   index_trigger = srcId.substring(8); 
				   }else{
				       suffix = "";
				       index_trigger = srcId.substring(7); 
				   } 
				   
				   var currentBarcode =$("#barcode"+ suffix + index_trigger).val();
				   if (currentBarcode == ""){
					  $("#cashAmount").focus();
				   } else {
			          event.returnValue=false;
			          retrieveProductByBarcode(index_trigger, suffix, currentBarcode);
				   }
			   } else if (srcId.substring(0,11)=="productCode"){
				   var index_trigger;
				   event.returnValue=false;
				   var suffix = srcId.substring(11,12);

				   if (suffix == RETURN_SUFFIX){
				       index_trigger = srcId.substring(12);
				   } else if (suffix == FREE_SUFFIX){
					   index_trigger = srcId.substring(12); 
				   } else {
				       suffix = "";
				       index_trigger = srcId.substring(11);	   
				   }
				   searchProductsProductCode(index_trigger,suffix);
			   } else if (srcId == "vipCardNo"){
				   checkVIPCard();
			   } else if (srcId == "cashAmount" || srcId == "cardAmount"){
				   var cashAmt = $("#cashAmount").val();
				   var cardAmt = $("#cardAmount").val();
				   
				   if (cashAmt > 50000 || cardAmt > 50000){
					   alert("请检查你的现金/卡金额输入是否正确?");
				   } else {
					   postSalesOrder(); 
				   }
				   event.returnValue=false;	
			   } else {
				   $("#barcode" + index).focus();
				   calculateTotal();
				   event.returnValue=false;	
			   }
		     break;
    //搜索商品名 
	case 8:	
			 var srcName = event.srcElement.tagName.toLowerCase();

			 if((srcName == "input" && (event.srcElement.disabled != true && event.srcElement.readOnly != true)) || srcName == "textarea"){
			        event.returnValue = true; 
			 } else
			    	event.returnValue = false;		   
			 break;
	//保存草稿 F2
	case 113:			 
			 saveDraftSales();
			 event.returnValue=false;			   
			 break;
   //过账 F3
   case 114:			 
			 postSalesOrder();
			 event.returnValue=false;			   
			 break;
		 default:;
	 }

}