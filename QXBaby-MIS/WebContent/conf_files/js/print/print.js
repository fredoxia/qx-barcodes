function testPrintF(){
	dfPrinter=pazu.TPrinter.getDefaultPrinter();

	if (dfPrinter == null){
        alert("还未设置默认小票打印机，请设置后打印小票");
	} else {
		//alert($("#printHeader").val());
		pageMargin=dfPrinter.TextWidth("   ");        
		maxLength = dfPrinter.ScaleWidth; 

		printHeader("测试打印连锁店");
		
	    //print content
	    dfPrinter.FontBold=true;
	    dfPrinter.FontSize=fontSizeHead;
	    
	    pazu.TPrinter.printToDefaultPrinter("售出货品:");
	    
		dfPrinter.FontBold=false; 
		dfPrinter.FontSize=fontSize;
		pazu.TPrinter.printToDefaultPrinter(title);

        tempstr1 = "2011" + "-" + "测试品牌";
	    pazu.TPrinter.printToDefaultPrinter(tempstr1);
        tempstr2 = qSpace + "2" + space2 + "12.5"    + "/" +  "0.8"  + space2 +  "10";
	    pazu.TPrinter.printToDefaultPrinter(tempstr2);  

		pazu.TPrinter.printToDefaultPrinter("优惠 : 0 , " + "代金券 : 0");
		pazu.TPrinter.printToDefaultPrinter("应收 : 10 , " + "积分换现金 : 0" );
		pazu.TPrinter.printToDefaultPrinter("刷卡 : 0 , " + "现金 : 20");
		pazu.TPrinter.printToDefaultPrinter("找零 : 10");
		
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

	    var footer = "欢迎下次光临  2015-1-1" ; 
	      pazu.TPrinter.printToDefaultPrinter("");
	      pazu.TPrinter.printToDefaultPrinter("收银员 : 测试人员");
	      pazu.TPrinter.printToDefaultPrinter(footer);
	   
	    pazu.TPrinter.printToDefaultPrinter("地址： 成都大成市场6楼6号");	

	    dfPrinter.EndDoc();
	}
}
function printSalesOrder(vipInfor){

	dfPrinter=pazu.TPrinter.getDefaultPrinter();

	if (dfPrinter == null){
        alert("还未设置默认小票打印机，请设置后打印小票");
	} else {
		//alert(dfPrinter.TextWidth(space) + "," + dfPrinter.ScaleWidth);
		pageMargin=dfPrinter.TextWidth("   ");        
		maxLength = dfPrinter.ScaleWidth; 

		var printCopy = $("#printCopy").val();
		for (var i = 0; i < printCopy; i++){
			printOut(vipInfor);
		}
	}
}

function printOut(vipInfor){
	//print header
	printHeader();

	//print sales
	printChainSalesOrder();

	//print return
	printChainReturnOrder();
	
	//print free
	printChainFreeOrder();

	//print acct footer
	printAcctFooter();
	
    //print footer
	printFooter(vipInfor);
}
/**
 * 销售
 */
function printChainSalesOrder(){
	var totalQ =  $("#totalQuantity").val(); 

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
    
	  	for (var i = 0; i < index; i++){
		   	var quantityInput =  $("#quantity"+i); 
		   	var discountRateInput =  $("#discountRate"+i); 
			var discountPriceInput = $("#retailPrice"+i); 
			var discountAmountInput = $("#discountAmount"+i); 
			var brandInput = $("#brand"+i); 
			var productCodeInput = $("#productCode"+i);
			var normalPriceInput =  $("#normalPrice"+i);
	
	        if (quantityInput.val()!= undefined && discountAmountInput.val()!=undefined && discountPriceInput.val()!=undefined){
	        	tempstr1 = productCodeInput.val() + "-" + brandInput.val();
	        	pazu.TPrinter.printToDefaultPrinter(tempstr1);

	            var quantity = quantityInput.val();
		        var price = discountPriceInput.val();  
	    	    var discountAmount = discountAmountInput.val();
	    	    
	    	    var discountRate = "";
	    	    if (hideDiscountPrint == 0)
	    	    	discountRate =price + "/" + discountRateInput.val();
	    	    else 
	    	    	discountRate = "-";
	    	    
	    	    var normalPrice = normalPriceInput.val();

	    	    if (normalPrice == SPECIAL_PRICE)
	    	        tempstr2 = qSpace + quantity + space2 + discountRate + " 特价 "  + space2 +  discountAmount;
	    	    else 
	    	    	tempstr2 = qSpace + quantity + space2 + discountRate  + space2 +  discountAmount;
	    	    
		    	pazu.TPrinter.printToDefaultPrinter(tempstr2);  
	        }
		}
	  	
	  	dfPrinter.FontSize=fontSizeHead;
	  	var netAmt = $("#netAmount").val();
	  	pazu.TPrinter.printToDefaultPrinter("售出总计: " + totalQ + space2 + netAmt);
	  	
	}
} 

/**
 * 退货
 */
function printChainReturnOrder(){
	var totalQ =  $("#totalQuantityR").val(); 

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
    
	  	for (var i = 0; i < indexR; i++){
		   	var quantityInput =  $("#quantityR"+i); 
		   	var discountRateInput =  $("#discountRateR"+i); 
			var discountPriceInput = $("#retailPriceR"+i); 
			var discountAmountInput = $("#discountAmountR"+i); 
			var brandInput = $("#brandR"+i); 
			var productCodeInput = $("#productCodeR"+i); 

	        if (quantityInput.val()!= undefined && discountAmountInput.val()!=undefined && discountPriceInput.val()!=undefined){
	        	tempstr1 = productCodeInput.val() + "-" + brandInput.val();
	        	pazu.TPrinter.printToDefaultPrinter(tempstr1);

	            var quantity = quantityInput.val();
		        var price = discountPriceInput.val();  
	    	    var discountAmount = discountAmountInput.val();

	    	    var discountRate = "";
	    	    if (hideDiscountPrint == 0)
	    	    	discountRate = "/" + discountRateInput.val();
	    	    
	    	    tempstr2 = qSpace + quantity + space2 + price  +  discountRate  + space2 +  discountAmount;
	        	pazu.TPrinter.printToDefaultPrinter(tempstr2);  	            
	        }
		}

	  	dfPrinter.FontSize=fontSizeHead;
	    
	  	pazu.TPrinter.printToDefaultPrinter("退货总计: " + totalQ + space2 + $("#netAmountR").val());
	}
}

/**
 * 赠品
 */
function printChainFreeOrder(){
	var totalQ =  $("#totalQuantityF").val(); 

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
	  	for (var i = 0; i < indexF; i++){
		   	var quantityInput =  $("#quantityF"+i); 
			var brandInput = $("#brandF"+i); 
			var productCodeInput = $("#productCodeF"+i); 

	        if (quantityInput.val()!= undefined){
	        	tempstr1 = productCodeInput.val() + "-" + brandInput.val();
	        	pazu.TPrinter.printToDefaultPrinter(tempstr1);

	            var quantity = quantityInput.val();

	    	    tempstr2 = qSpace + quantity   + space2 +  " - "   + " " +   " - "   + space2 +  " - ";
	        	pazu.TPrinter.printToDefaultPrinter(tempstr2);  	      
	        }
		}

	  	dfPrinter.FontSize=fontSizeHead;
	    
	  	pazu.TPrinter.printToDefaultPrinter("赠品总计: " + totalQ );
	}
}

/**
 * 单据头
 */
function printHeader(){
	 dfPrinter.FontSize=fontSizeHead;
	 dfPrinter.FontBold=true;
     var content = $("#printHeader").val();
       pazu.TPrinter.printToDefaultPrinter(content);
	 var obj = document.getElementById("chainStore");
	 var chainName = obj.options[obj.selectedIndex].text;
//     var chainName = $("#chainStore option:selected").text();
       pazu.TPrinter.printToDefaultPrinter(chainName);
     content = "--------------------";
       pazu.TPrinter.printToDefaultPrinter(content);       
}

/**
 * 账目尾
 */
function printAcctFooter(){
    content = "--------------------";
    pazu.TPrinter.printToDefaultPrinter(content);  
    
	var discount = $("#discountAmount").val();
	var coupon = $("#coupon").val();
	var amountAfterDC = $("#amountAfterDC").val();
	var vipScore = $("#vipScore").val();
	var cardAmount = $("#cardAmount").val();
	var cashAmount = $("#cashAmount").val();
	var returnAmount = $("#returnAmount").val();
	var vipPrepaid = $("#chainPrepaidAmt").val();
	var alipay = $("#alipayAmount").val();
	var wechat = $("#wechatAmount").val();
	
	pazu.TPrinter.printToDefaultPrinter("优惠 : " + discount + " , " + "代金券 : " + coupon);
	pazu.TPrinter.printToDefaultPrinter("应收 : " + amountAfterDC  + " , " + "积分换现金 : " + vipScore );
	pazu.TPrinter.printToDefaultPrinter("刷卡 : " + cardAmount + " , " + "现金 : " + cashAmount);
	pazu.TPrinter.printToDefaultPrinter("微信 : " + wechat + " , " + "支付宝 : " + alipay);
	pazu.TPrinter.printToDefaultPrinter("预存金消费 : " + vipPrepaid + " , " + "找零 : " + returnAmount);
}

/**
 * 单据尾
 */
function printFooter(vipInfor){
    pazu.TPrinter.printToDefaultPrinter("");
    if (vipInfor.vipCardNo != undefined) {
    	pazu.TPrinter.printToDefaultPrinter("VIP : " + vipInfor.vipCardNo + " " + vipInfor.customerName);
    	pazu.TPrinter.printToDefaultPrinter("当前积分 : " + vipInfor.accumulatedScore);
    	pazu.TPrinter.printToDefaultPrinter("剩余预存金 : " + vipInfor.accumulateVipPrepaid);
    }
    
//	var salesName = $("#chainSaler").find("option:selected").text();
    var obj = document.getElementById("chainSaler");
	var  salesName = obj.options[obj.selectedIndex].text;
	
	dfPrinter.FontSize=fontSize;
    var content = "此小票是七日换货凭证，请妥善保管";
      pazu.TPrinter.printToDefaultPrinter(content);
    content = "(洗涤,人为原因,特价商品概不调换,";
      pazu.TPrinter.printToDefaultPrinter(content);
    content = "特价商品不在三包范围之内)";
      pazu.TPrinter.printToDefaultPrinter(content);	
    content = "服务热线:400-606-0849";
      pazu.TPrinter.printToDefaultPrinter(content);
    content = "微信号：QXbaby-HK";
      pazu.TPrinter.printToDefaultPrinter(content);
	var date = new Date();
    var footer = "欢迎下次光临 " + formatDate(date,"YYYY-MM-DD hh:mm:ss"); 
      pazu.TPrinter.printToDefaultPrinter("");
      pazu.TPrinter.printToDefaultPrinter("收银员 : " + salesName);
      pazu.TPrinter.printToDefaultPrinter(footer);
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