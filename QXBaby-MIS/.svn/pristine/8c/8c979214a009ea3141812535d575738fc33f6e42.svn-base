function traceInventory(barcode, chainId){
	if (barcode == undefined ||  barcode =="" ){
		return ;
	} else {
		if (chainId == undefined || chainId == "")
	        chainId = $("#chainId").val();
	    
		if (chainId == undefined)
			return;
		else {
			var param = "formBean.chainId="+ chainId +"&formBean.barcode="+barcode;
			$.modalDialog({
				title : barcode + " 货品库存跟踪",
				width : 440,
				height : 220,
				modal : false,
				draggable:true,
				href : 'inventoryFlowJSPAction!openInvenTracePage?' + param,
			});
			}
	}
}