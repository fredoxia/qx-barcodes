function consumpHis(vipCard){
	var chainId = ALL_RECORD;

	var param = "formBean.chainStore.chain_id="+ chainId +"&formBean.vipCard.id="+vipCard;
	$.modalDialog({
		title : "VIP消费跟踪 (近6个月记录)",
		width : 600,
		height : 320,
		modal : false,
		draggable:true,
		href : 'chainVIPJSPAction!getVIPConsumptionHis?' + param
	});
}