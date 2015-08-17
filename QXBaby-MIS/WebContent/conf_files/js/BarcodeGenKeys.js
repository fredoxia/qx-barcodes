document.onkeydown = BSkeyDown; 


function BSkeyDown(e){

	 var ieKey = event.keyCode;
	 var srcId = event.srcElement.id;
	 var srcType = event.srcElement.type;
//	 alert(ieKey);
	 switch(ieKey){
		//回车
		case 13:			 
				   if (srcId=="brandName"){
					   searchBrand();
				   } else if (srcId=="serialNum"){
					   getProductColors();
				   } else if (srcId == "colorName"){
					   searchColor();
				   }
			     break;		     
			 default:;
	 }

}