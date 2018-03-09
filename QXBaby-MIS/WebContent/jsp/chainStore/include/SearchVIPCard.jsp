<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags" %>
<script>
/**
 * this stub is for the pages which may need the VIP Card search
 */
 function checkVIPCard(){
	    var vipCard = $("#vipCardNo").val();
	    if (vipCard == ""){
	        alert("VIP卡号不能为空");
	    } else {
		    var params="formBean.vipCard.vipCardNo=" + vipCard;  
		    $.post("chainVIPJSONAction!getVIPCard",params, checkVIPCardBackProcess,"json");	
	    }	
}

function checkVIPCardBackProcess(data){
	var vipCard = data.vipCard;
	if ($.isEmptyObject(vipCard)){
       alert("无法找到所对应的VIP卡，请检查");
       $("#vipCardNo").select();
    } else {
       var status = vipCard.status;
       if (status != 1){
    	   alert("VIP卡状态为 " + vipCard.statusS + ", 无法使用");
       } else {
    		$("#vipCardNo").attr("disabled", "true");
    		$("#clearVIPBt").removeAttr("disabled");
    		$("#checkVIPBt").attr("disabled","true");
    		$("#vipCardIdHidden").attr("value", vipCard.id);
    		
    		$("#vipDiscount").attr("value",vipCard.vipType.discountRate);
    		$("#vipScore").removeAttr("disabled");
    		$("#chainPrepaidAmt").removeAttr("disabled");
    		$("#refreshBt").removeAttr("disabled");

    		$("#maxVipCash").attr("value",data.totalCash);
    		$("#maxVipPrepaid").attr("value",data.totalVipPrepaid);
    		$("#vipInfo").html(vipCard.customerName + " " + vipCard.vipType.vipTypeName + " 积分可换现金 :" + (data.totalCash).toFixed(2) + " 剩余预存金 :" + (data.totalVipPrepaid).toFixed(2));
    		var msg = "VIP 卡主 : " + vipCard.customerName + "\n"+
    				  "开户连锁店 : " + vipCard.issueChainStore.chain_name + "\n"+
    				  "卡种类 : " + vipCard.vipType.vipTypeName + "\n" + 
    				  "累计积分 : " + (data.totalScore).toFixed(2) + "\n" + 
    		          "积分可换现金 : " + (data.totalCash).toFixed(2)+ "\n" + 
    		          "剩余预存金 : " + (data.totalVipPrepaid).toFixed(2);
    		alert(msg);
       }
       $("#barcode" + index).focus();
    }
}

function clearVIPCard(){
	$("#vipCardNo").attr("value", "");
	
	$("#vipCardIdHidden").attr("value", "");
	$("#vipDiscount").attr("value","");
	
	$("#vipCardNo").removeAttr("disabled");
	
	$("#checkVIPBt").removeAttr("disabled");
	$("#clearVIPBt").attr("disabled","true");

	$("#vipScore").attr("value", 0);
	$("#vipScore").attr("disabled","true");

	$("#chainPrepaidAmt").attr("value", 0);
	$("#chainPrepaidAmt").attr("disabled","true");
	
	changeDiscountCoupon();
	
	$("#refreshBt").attr("disabled","true");
	
	$("#barcode" + index).focus();

	$("#vipInfo").html("");
}

function refreshDiscount(){
	var discountRateOrg = "";
	var discountRate = $("#vipDiscount").val(); 
	for (var i = 0; i < index; i++){
		discountRateOrg = $("#discountRate" + i).val();
		if (discountRateOrg != undefined){
			//if (discountRate < discountRateOrg){
		    	$("#discountRate" + i).attr("value",discountRate);
		    	changeRowValue("",i);
			//}
		}
	}
}
</script>
		VIP卡号: <s:hidden name="formBean.chainSalesOrder.vipCard.id" id="vipCardIdHidden"/>
		        <s:hidden name="formBean.maxVipCash" id="maxVipCash"/>
		        <s:hidden name="formBean.maxVipPrepaid" id="maxVipPrepaid"/>
			    <s:textfield name="formBean.vipCardNo" id="vipCardNo" size="18"/><input type="button" id="checkVIPBt" value="输入" onclick="checkVIPCard();"/><input id="clearVIPBt" type="button" value="修改" onclick="clearVIPCard();" disabled/>&nbsp;&nbsp;&nbsp;&nbsp;
		VIP折扣：<s:textfield id="vipDiscount" name="formBean.discount" disabled="true" size="3"  style="color:black;font-weight:bold"/>
		<input type="button" id="refreshBt" onclick="refreshDiscount()" disabled value="刷新折扣" title="先开单，再报VIP的按钮"/>
		<span id="vipInfo"><s:property value="uiBean.msg"/></span>