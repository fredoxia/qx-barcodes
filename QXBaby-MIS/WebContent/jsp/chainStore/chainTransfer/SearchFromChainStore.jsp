<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags" %>
<script>
/**
 * this stub is for the pages which may need the VIP Card search
 */
 function searchFromChainStore(){
	 var param = "";
 	 
	 var url = "chainTransferJSPAction!listFromChainStore";

	 window.open(url,'新窗口','height=400, width=400, toolbar=no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=no');  
}

function selectFromChainStore(chainId, chainName){
	$("#fromChainId").attr("value", chainId);
	$("#fromChainName").attr("value", chainName);
	
	$("#toChainId").attr("value", 0);
	$("#toChainName").attr("value", "");
}

</script>
		<s:hidden name="formBean.transferOrder.fromChainStore.chain_id" id="fromChainId"/>
		<s:textfield name="formBean.transferOrder.fromChainStore.chain_name" id="fromChainName" size="18" readonly="true"/><input type="button" id="checkFromChainStoreBt" value="..." onclick="searchFromChainStore();"/>
