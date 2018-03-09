<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib prefix="s" uri="/struts-tags" %>

<%@ page import="java.util.Date,java.text.SimpleDateFormat" %>
<html>
<head>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>连锁店配置管理</title>
<%@ include file="../../common/Style.jsp"%>
<script>
$(document).ready(function(){
	parent.$.messager.progress('close'); 
});
function changeChainStore(){
	var chainStoreId = $("#chainId").val();
	if (chainStoreId == 0){
		clearChainStoreConf();
	} else {
		var params="formBean.chainStoreConf.chainId=" + chainStoreId;
		$.post("<%=request.getContextPath()%>/actionChain/chainMgmtJSON!getChainStoreConf",params, backProcessGetChainStore,"json");
	}
}

function backProcessGetChainStore(data){
	var response =  data.response;
	var returnCode = response.returnCode;

	if (returnCode != SUCCESS){
		alert(response.message);
		clearChainStoreConf();
	} else {
		var chainStoreConf = response.returnValue;
	
		if (chainStoreConf != null){
			 var printCopy = chainStoreConf.printCopy;
			 var discountAmtType = chainStoreConf.discountAmtType;
			 var minDiscountRate = chainStoreConf.minDiscountRate;
			 var defaultDiscount = chainStoreConf.defaultDiscount;
			 var vipScoreCashRatio = chainStoreConf.vipScoreCashRatio;
			 var printTemplate = chainStoreConf.printTemplate;
			 var address = chainStoreConf.address;
			 var shippingAddress = chainStoreConf.shippingAddress;
			 var hideDiscountPrint = chainStoreConf.hideDiscountPrint;
			 var prepaidUsage = chainStoreConf.allowMyPrepaidCrossStore;
			 var prepaidType = chainStoreConf.prepaidCalculationType;
			 var defaultVipScoreMultiple = chainStoreConf.defaultVipScoreMultiple;
			 var allowVIPScore = chainStoreConf.allowOtherVIPUseVIPScore;
			 
			 $("#amtType").val(discountAmtType).attr("selected", true);
			 $("#printCopy").attr("value", printCopy);
			 $("#minDiscountRate").attr("value", minDiscountRate);
			 $("#defaultDiscount").attr("value", defaultDiscount);
			 $("#vipScoreCashRatio").attr("value", vipScoreCashRatio);
			 $("#printTemplate").attr("value", printTemplate);
			 $("#address").attr("value",address);
			 $("#shippingAddress").attr("value",shippingAddress);
			 $("#hideDiscountPrint").attr("value",hideDiscountPrint);
			 $("#prepaidUsage").attr("value",prepaidUsage);
			 $("#prepaidType").attr("value",prepaidType);
			 $("#defaultVipScoreMultiple").attr("value",defaultVipScoreMultiple);
			 $("#vipScoreUsage").attr("value",allowVIPScore);
		}
	}
}

function editChainConf(){
	var params=$("#EditChainConfForm").serialize(); 
	if (validateChainConf()) 
	    $.post("<%=request.getContextPath()%>/actionChain/chainMgmtJSON!saveChainStoreConf",params, backProcessEditChainConf,"json");
}

function backProcessEditChainConf(data){
	var response =  data.response;
	var returnCode = response.returnCode;

	if (returnCode != SUCCESS){
		alert(response.message);
	} else {
		alert("成功更新。\n如果有开启的零售单据页面，预存金冲值页面，请关闭之后重新打开，配置才能生效");
	}
}

function validateChainConf(){
	var chainStoreId = $("#chainId").val();
	if (chainStoreId == 0){
		alert("选择连锁店之后更新");
		return false;
	} else {
 		//1. check the minDiscountRate
 		var minDiscountRate = $("#minDiscountRate").val();
 		if (!$.isNumeric(minDiscountRate)){
			alert("最低折扣必须是数字");
			return false;
 	 	} else if (minDiscountRate <0 || minDiscountRate>1){
 	 		alert("最低折扣必须是介于0和1之间的数字");
 	 		return false;
 	 	}

 	 	//2. 验证默认折扣
 		var defaultDiscount = $("#defaultDiscount").val();
 		if (!$.isNumeric(defaultDiscount)){
			alert("默认折扣必须是数字");
			return false;
 	 	} else if (defaultDiscount <=0 || defaultDiscount>1){
 	 		alert("默认折扣必须是介于0和1之间的数字");
 	 		return false;
 	 	} else if (defaultDiscount < minDiscountRate && minDiscountRate !=0){
 	 		alert("默认折扣不能低于连锁店最低折扣");
 	 		return false;
 	 	}
	}
	return true;
}

function clearChainStoreConf(){
	 $("#printCopy").attr("value", 0);
	 $("#minDiscountRate").attr("value", 0);
	 $("#printTemplate").attr("value", 1);
	 $("#address").attr("value", "");
	 $("#hideDiscountPrint").attr("value", 0);
}


</script>
</head>
<body>

<s:form id="EditChainConfForm" action="/actionChain/chainMgmtJSP!save" method="POST" theme="simple" target="_blank" onsubmit="" >
  <table width="90%" align="center"  class="OuterTable">
    <tr><td>
		 <table width="100%" border="0">
	    <tr class="PBAOuterTableTitale">
	       <td height="48" colspan="6">
           - 通过这个界面管理员可以修改连锁店一些配置管理信息<br/>
           </td>
	    </tr>
	    <tr class="InnerTableContent">
	      <td width="180" height="32">
	         <strong>连锁店</strong></td>
	      <td width="250"><%@ include file="../include/SearchChainStore.jsp"%></td>
	      <td width="78">&nbsp;</td>
	      <td width="78">&nbsp;</td>
	      <td width="182">&nbsp;</td>
	      <td width="137">&nbsp;</td>
	    </tr>
   	    <tr class="InnerTableContent">
	      <td height="25"><strong>价格设置</strong></td>
	      <td><s:select id="amtType" name="formBean.chainStoreConf.discountAmtType"  list="uiBean.amtTypeMap" listKey="key" listValue="value" /></td>
	      <td></td>
	      <td></td>
	      <td>&nbsp;</td>
	      <td>&nbsp;</td>
   	    </tr>
   	    <tr class="InnerTableContent">
   	      <td height="25"><strong>小票机纸宽度</strong></td>
   	      <td><s:select id="printTemplate" name="formBean.chainStoreConf.printTemplate"  list="#{1:'58mm宽',2:'72mm宽'}" listKey="key" listValue="value" />
   	      </td>
   	      <td></td>
   	      <td></td>
   	      <td></td>
   	      <td></td>
       </tr>
   	    <tr class="InnerTableContent">
   	      <td height="25"><strong>小票打印份数</strong></td>
   	      <td><s:textfield id="printCopy" name="formBean.chainStoreConf.printCopy" size="5"   onkeypress='return is_number(event);'/></td>
   	      <td></td>
   	      <td></td>
   	      <td></td>
   	      <td></td>
       </tr>
   	    <tr class="InnerTableContent">
   	      <td height="25"><strong>小票折扣打印</strong></td>
   	      <td><s:select id="hideDiscountPrint" name="formBean.chainStoreConf.hideDiscountPrint"  list="#{0:'打印到小票',1:'隐藏折扣'}" listKey="key" listValue="value" /></td>
   	      <td></td>
   	      <td></td>
   	      <td></td>
   	      <td></td>
       </tr>       
       <tr class="InnerTableContent">
   	      <td height="25"><strong>销售最低折扣</strong></td>
   	      <td><s:textfield id="minDiscountRate" name="formBean.chainStoreConf.minDiscountRate" size="5"/></td>
   	      <td colspan="4">*当设置最低折扣为0.9，营业员输入货品折扣为0.8的时候，就不能过账.<br/>'0'表示可以任意折扣卖;'1'表示所有产品不打折</td>
       </tr>
       <tr class="InnerTableContent">
   	      <td height="25"><strong>销售默认折扣</strong></td>
   	      <td><s:textfield id="defaultDiscount" name="formBean.chainStoreConf.defaultDiscount" size="5"/></td>
   	      <td colspan="4">*在开单时，系统会自动使用默认折扣打折，比如在开张，做活动期间。之后请修改回'1'。</td>
       </tr>
       <tr class="InnerTableContent">
   	      <td height="25"><strong>VIP默认积分</strong></td>
   	      <td><s:select id="defaultVipScoreMultiple" name="formBean.chainStoreConf.defaultVipScoreMultiple"  list="#{1:'正常积分',2:'活动期间,两倍积分'}" listKey="key" listValue="value" /></td>
   	      <td colspan="4">*在活动期间,比如在开张，国庆，可以给所有vip客户设置双倍积分。之后请修改回正常积分体系。</td>
       </tr>       
       <tr class="InnerTableContent">
   	      <td height="25"><strong>VIP积分换现金</strong></td>
   	      <td><s:select id="vipScoreCashRatio" name="formBean.chainStoreConf.vipScoreCashRatio"  list="#{0.01:'一百分换一元',0.02:'一百分换两元',0.03:'一百分换三元',0.04:'一百分换四元',0.05:'一百分换五元',0.06:'一百分换六元',0.08:'一百分换八元',0.1:'一百分换十元'}" listKey="key" listValue="value" />
   	      </td>
   	      <td></td>
   	      <td></td>
   	      <td></td>
   	      <td></td>
       </tr>
       <tr class="InnerTableContent">
   	      <td height="35"><strong>跨连锁店使用vip积分兑换 </strong></td>
   	      <td><s:select id="vipScoreUsage" name="formBean.chainStoreConf.allowOtherVIPUseVIPScore"  list="#{0:'只能自己连锁店vip兑换积分',1:'关联连锁店的vip也可以在本店铺消费积分',2:'所有其他连锁店的vip都可以在本店兑换积分'}" listKey="key" listValue="value" />
   	      </td>
   	      <td></td>
   	      <td></td>
   	      <td></td>
   	      <td></td>
       </tr>          
       <tr class="InnerTableContent">
   	      <td height="35"><strong>跨连锁店使用预付金 </strong></td>
   	      <td><s:select id="prepaidUsage" name="formBean.chainStoreConf.allowMyPrepaidCrossStore"  list="#{0:'不允许我的vip的预存金在其他连锁店使用',1:'允许我的vip在关联连锁店充值/使用预存金'}" listKey="key" listValue="value" />
   	      </td>
   	      <td></td>
   	      <td></td>
   	      <td></td>
   	      <td></td>
       </tr>        
       <tr class="InnerTableContent">
   	      <td height="35"><strong>预存金 使用计划</strong></td>
   	      <td><s:select id="prepaidType" name="formBean.chainStoreConf.prepaidCalculationType"  list="#{0:'正常预存 - 预存100抵用100',1:'1.1倍 - 预存500抵用550,预存1000抵用1100'}" listKey="key" listValue="value" />
   	      </td>
   	      <td></td>
   	      <td></td>
   	      <td></td>
   	      <td></td>
       </tr>       
       <tr class="InnerTableContent">
   	      <td height="35"><strong>连锁店地址 打印于小票</strong></td>
   	      <td>
   	           <s:textfield id="address" name="formBean.chainStoreConf.address" maxLength="28" size="35"/>
   	      </td>
   	      <td></td>
   	      <td></td>
   	      <td></td>
   	      <td></td>
       </tr>
       <tr class="InnerTableContent">
   	      <td height="35"><strong>调货联系地址和电话</strong></td>
   	      <td>
   	           <s:textfield id="shippingAddress" name="formBean.chainStoreConf.shippingAddress" maxLength="80" size="80"/>
   	      </td>
   	      <td></td>
   	      <td></td>
   	      <td></td>
   	      <td></td>
       </tr>       
       <tr class="InnerTableContent">
   	      <td height="25"></td>
   	      <td></td>
   	      <td></td>
   	      <td></td>
   	      <td></td>
   	      <td></td>
       </tr>

   	    <tr class="InnerTableContent">
   	      <td height="25">&nbsp;</td>
   	      <td><input type="button" onclick="editChainConf();" value="更新配置信息"/></td>
   	      <td></td>
   	      <td></td>
   	      <td>&nbsp;</td>
   	      <td>&nbsp;</td>
       </tr>
	  </table>
   </td>
   </tr>

 </table>
</s:form>

</body>
</html>