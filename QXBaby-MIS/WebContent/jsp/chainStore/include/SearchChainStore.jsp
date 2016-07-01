<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags" %>
<script>
/**
 * this stub is for the pages which may need the VIP Card search
 */
 function searchChainStore(){
	 var param = "";
	 var indicator = $("#indicator").val();
	 if (indicator != undefined)
		 param = "formBean.indicator=" + indicator + "&";

	 var accessLevel = $("#accessLevel").val();
	 if (accessLevel != undefined)
		 param += "formBean.accessLevel=" + accessLevel;
		 	 
	 var url = "chainMgmtJSP!listChainStore" + "?" + param;

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
