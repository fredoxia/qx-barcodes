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
	    	var chainId = $("#chainId").val();
		    var params="formBean.vipCard.vipCardNo=" + vipCard +"&formBean.chainStore.chain_id="+chainId;  
		    $.post("chainVIPJSONAction!getVIPCardVIPPrepaid",params, checkVIPCardBackProcess,"json");	
	    }	
}

function checkVIPCardBackProcess(data){
	var response = data.response;
	if (response.returnCode != SUCCESS){
		alert(response.message);
	} else {
		var vipCard = response.returnValue;
		if ($.isEmptyObject(vipCard)){
	       alert("无法找到所对应的VIP卡，请检查");
	       $("#vipCardNo").select();
	    } else {
	       $("#vipCardNo").attr("disabled", "true");
	       $("#clearVIPBt").removeAttr("disabled");
	       $("#checkVIPBt").attr("disabled","true");
	       $("#vipCardIdHidden").attr("value", vipCard.id);
	
	       $("#maxVipCash").attr("value",data.totalCash);
	       $("#vipInfo").html(vipCard.issueChainStore.chain_name + " 姓名:" + vipCard.customerName + " 电话:" + vipCard.telephone + " VIP类型:" + vipCard.vipType.vipTypeName);
	       alert("冲值前请确认VIP信息,正确");
	    }
    }
}

function clearVIPCard(){
	$("#vipCardNo").attr("value", "");
	
	$("#vipCardNoHidden").attr("value", "");
	
	$("#vipCardNo").removeAttr("disabled");
	
	$("#checkVIPBt").removeAttr("disabled");
	$("#clearVIPBt").attr("disabled","true");

	$("#vipInfo").html("");
}

</script>
		<s:hidden name="formBean.vipCard.id" id="vipCardIdHidden"/>
		<s:textfield name="formBean.vipCardNo" id="vipCardNo" size="18"/><input type="button" id="checkVIPBt" value="查找" onclick="checkVIPCard();"/><input id="clearVIPBt" type="button" value="修改" onclick="clearVIPCard();" disabled/>&nbsp;&nbsp;&nbsp;&nbsp;
		<span id="vipInfo"></span>