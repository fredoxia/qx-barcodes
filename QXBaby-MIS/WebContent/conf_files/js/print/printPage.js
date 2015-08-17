
function printSalesOrder(order){

	dfPrinter=pazu.TPrinter.getDefaultPrinter();

	if (dfPrinter == null){
        alert("还未设置默认小票打印机，请设置后打印小票");
	} else {
		//alert(dfPrinter.TextWidth(space) + "," + dfPrinter.ScaleWidth);
		pageMargin=dfPrinter.TextWidth("   ");        
		maxLength = dfPrinter.ScaleWidth; 

		printOut(order);
	}
}

function printOut(order){
	//print header
	var chainName = order.chainStore.chain_name;
	printHeader(chainName);

	//print sales
	printChainSalesOrder(order);

	//print return
	printChainReturnOrder(order);
	
	//print free
	printChainFreeOrder(order);

	//print acct footer
	printAcctFooter(order);
	
    //print footer
	printFooter(order);
}
/**
 * 销售
 */
function printChainSalesOrder(order){
	var totalQ =  order.totalQuantity; 

	if (totalQ > 0) {
		var tempstr1 = "";
		var tempstr2 = "";
		
	    //print content
	    dfPrinter.FontBold=true;
	    dfPrinter.FontSize=fontSizeHead;
	    
	    pazu.TPrinter.printToDefaultPrinter("售出货品:");
	    
		dfPrinter.FontBold=false; 
		dfPrinter.FontSize=fontSize;
		pazu.TPrinter.printToDefaultPrinter(title);

	    var productList = order.productList;
	  	for (var i = 0; i < productList.length; i++){
		  	var salesProduct = productList[i];
		  	
		   	var quantity =  salesProduct.quantity; 
		   	var discountRate =  ""; 
    	    if (hideDiscountPrint == 0)
    	    	discountRate = "/" + salesProduct.discountRate;
			var discountPrice = salesProduct.retailPrice; 
			var discountAmount = (discountPrice * quantity * salesProduct.discountRate).toFixed(1); 
			var brand = salesProduct.productBarcode.product.brand.brand_Name; 
			var productCode = salesProduct.productBarcode.product.productCode; 
	
	        tempstr1 = productCode + "-" + brand;
	        pazu.TPrinter.printToDefaultPrinter(tempstr1);

	    	tempstr2 = qSpace + quantity + space2 + discountPrice    + discountRate  + space2 +  discountAmount;
	        pazu.TPrinter.printToDefaultPrinter(tempstr2);  
		}
	}
} 

/**
 * 退货
 */
function printChainReturnOrder(order){
	var totalQ =  order.totalQuantityR; 

	if (totalQ > 0) {
		var tempstr1 = "";
		var tempstr2 = "";
		
	    //print content
	    dfPrinter.FontBold=true;
	    dfPrinter.FontSize=fontSizeHead;
	      pazu.TPrinter.printToDefaultPrinter("退换货品:");
	    
		dfPrinter.FontBold=false; 
		dfPrinter.FontSize=fontSize;
 	      pazu.TPrinter.printToDefaultPrinter(title);
    
 		var productList = order.productListR;
 		for (var i = 0; i < productList.length; i++){
		  	var salesProduct = productList[i];
		  	
		   	var quantity =  salesProduct.quantity; 
		   	var discountRate =  ""; 
    	    if (hideDiscountPrint == 0)
    	    	discountRate = "/" + salesProduct.discountRate;
			var discountPrice = salesProduct.retailPrice; 
			var discountAmount = (discountPrice * quantity * salesProduct.discountRate).toFixed(1); 
			var brand = salesProduct.productBarcode.product.brand.brand_Name; 
			var productCode = salesProduct.productBarcode.product.productCode; 
	
	        tempstr1 = productCode + "-" + brand;
	        pazu.TPrinter.printToDefaultPrinter(tempstr1);

	    	tempstr2 = qSpace + quantity + space2 + discountPrice    +  discountRate  + space2 +  discountAmount;
	        pazu.TPrinter.printToDefaultPrinter(tempstr2);  
		}
	    
	}
}

/**
 * 赠品
 */
function printChainFreeOrder(order){
	var totalQ =  order.totalQuantityF;

	if (totalQ > 0) {
		var tempstr1 = "";
		var tempstr2 = "";
		
	    //print content
	    dfPrinter.FontBold=true;
	    dfPrinter.FontSize=fontSizeHead;
	      pazu.TPrinter.printToDefaultPrinter("赠品:");
	      
	    dfPrinter.FontBold=false; 
	    dfPrinter.FontSize=fontSize;
 	      pazu.TPrinter.printToDefaultPrinter(title);

	    //1. print the 赠品   
 		var productList = order.productListF;
 		for (var i = 0; i < productList.length; i++){
 	 		
		  	var salesProduct = productList[i];
		   	var quantity =  salesProduct.quantity; 
			var brand = salesProduct.productBarcode.product.brand.brand_Name; 
			var productCode = salesProduct.productBarcode.product.productCode; 

        	tempstr1 = productCode + "-" + brand;
        	pazu.TPrinter.printToDefaultPrinter(tempstr1);

    	    tempstr2 = qSpace + quantity   + space2 +  " - "   + " " +   " - "   + space2 +  " - ";
        	pazu.TPrinter.printToDefaultPrinter(tempstr2);  	      
	    }
	}
}

/**
 * 单据头
 */
function printHeader(chainName){
	 dfPrinter.FontSize=fontSizeHead;
	 dfPrinter.FontBold=true;
     var content = "千禧宝贝童装连锁";
       pazu.TPrinter.printToDefaultPrinter(content);
       pazu.TPrinter.printToDefaultPrinter(chainName);
     content = "--------------------";
       pazu.TPrinter.printToDefaultPrinter(content);       
}

/**
 * 账目尾
 */
function printAcctFooter(order){
    content = "--------------------";
    pazu.TPrinter.printToDefaultPrinter(content);  

    var vipCard = order.vipCard;
    if (vipCard != null && vipCard != undefined && vipCard.vipCardNo != undefined)
    	pazu.TPrinter.printToDefaultPrinter("VIP卡 : " + vipCard.vipCardNo);

	var discount = order.discountAmount ;
	var coupon =  order.coupon ;
	var amountAfterDC =  order.netAmount - discount - coupon - order.netAmountR ;
	var vipScore =  order.vipScore ;
	var cardAmount =  order.cardAmount ;
	var cashAmount =  order.cashAmount ;
	var returnAmount =  order.returnAmount ;
	
	pazu.TPrinter.printToDefaultPrinter("优惠 : " + discount + " , " + "代金券 : " + coupon);
	pazu.TPrinter.printToDefaultPrinter("应收 : " + amountAfterDC  + " , " + "积分换现金 : " + vipScore );
	pazu.TPrinter.printToDefaultPrinter("刷卡 : " + cardAmount + " , " + "现金 : " + cashAmount);
	pazu.TPrinter.printToDefaultPrinter("找零 : " + returnAmount);
}

/**
 * 单据尾
 */
function printFooter(order){
    pazu.TPrinter.printToDefaultPrinter("");

	dfPrinter.FontSize=fontSize;
    var content = "此小票是七日换货凭证，请妥善保管";
      pazu.TPrinter.printToDefaultPrinter(content);
    content = "(洗涤,人为原因,特价商品概不调换)";
      pazu.TPrinter.printToDefaultPrinter(content);
    content = "服务热线:400-688-5581";
      pazu.TPrinter.printToDefaultPrinter(content);
    content = "微信号：QXbaby-HK";
      pazu.TPrinter.printToDefaultPrinter(content);
	var date = order.orderCreateDate;
    var footer = "欢迎下次光临 " + date; 
      pazu.TPrinter.printToDefaultPrinter("");
    var salesName = order.saler.name;
      pazu.TPrinter.printToDefaultPrinter("收银员 : " + salesName);
      pazu.TPrinter.printToDefaultPrinter(footer);
      
    var today = new Date();
    var footer2 = "重复打印时间 " + formatDate(today,"YYYY-MM-DD hh:mm:ss"); 
    pazu.TPrinter.printToDefaultPrinter(footer2);    
    var address = $("#address").val();
    if (address == "")
      pazu.TPrinter.printToDefaultPrinter("-");
    else {
        if (address.length > 16){
            pazu.TPrinter.printToDefaultPrinter("地址：" + address.substring(0,16));	
            pazu.TPrinter.printToDefaultPrinter("   " + address.substring(16));	
        } else 
      	  pazu.TPrinter.printToDefaultPrinter("地址：" + address);	
    }
    
    dfPrinter.EndDoc();
}
