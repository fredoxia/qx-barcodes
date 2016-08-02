<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags" %>
<script>
/**
 * this stub is for the pages which may need the VIP Card search
 */
 function searchChainStore(){
	 var param = "";
	 var indicator = $("#indicator").val();
	 var isAll = $("#isAll").val();

	 if (isAll != undefined)
		 param = "formBean.isAll=" + isAll;

	 if (indicator != undefined){
		 if (param != "")
			 param += "&";
		 param += "formBean.indicator=" + indicator;
	 }
	 

	 var url = "chainSMgmtJSP!listChainStoreHQ" + "?" + param;
	 
	 window.open(url,'新窗口','height=400, width=400, toolbar=no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=no');  
}

function selectChainStore(chainId, chainName){
	$("#chainId").attr("value", chainId);
	$("#chainName").attr("value", chainName);
	changeChainStore(chainId);
}
</script>
		<s:hidden name="formBean.chainStore.chain_id" id="chainId"/>
		<s:textfield name="formBean.chainStore.chain_name" id="chainName" size="18" readonly="true"/><input type="button" id="checkChainStoreBt" value="..." onclick="searchChainStore();"/>
