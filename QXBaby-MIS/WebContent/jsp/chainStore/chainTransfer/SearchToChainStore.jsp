<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags" %>
<script>
/**
 * this stub is for the pages which may need the VIP Card search
 */
 function searchToChainStore(){
	 var param = "formBean.transferOrder.fromChainStore.chain_id=" + $("#fromChainId").val();
 	 
	 var url = "chainTransferJSPAction!listToChainStore?" + param;

	 window.open(url,'新窗口','height=400, width=400, toolbar=no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=no');  
}

function selectToChainStore(chainId, chainName){
	$("#toChainId").attr("value", chainId);
	$("#toChainName").attr("value", chainName);
}

</script>
		<s:hidden name="formBean.transferOrder.toChainStore.chain_id" id="toChainId"/>
		<s:textfield name="formBean.transferOrder.toChainStore.chain_name" id="toChainName" size="18" readonly="true"/><input type="button" id="checkToChainStoreBt" value="..." onclick="searchToChainStore();"/>
