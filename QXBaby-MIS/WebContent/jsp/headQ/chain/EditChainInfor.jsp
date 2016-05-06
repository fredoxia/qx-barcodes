<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib prefix="s" uri="/struts-tags" %>

<%@ page import="java.util.Date,java.text.SimpleDateFormat" %>
<html>
<head>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>连锁店/人员信息管理</title>
<%@ include file="../../common/Style.jsp"%>
<script>
$(document).ready(function(){
	parent.$.messager.progress('close'); 
});
function editChainStore(){
	var params=$("#EditChainInforForm").serialize(); 
	if (validateChainStore()) 
	    $.post("<%=request.getContextPath()%>/action/chainSMgmtJSON!saveChainStore",params, backProcessEditChainStore,"json");
}
function backProcessEditChainStore(data){
	var error = data.error;

    if (error == true){
       var msg = data.msg;
       alert("更新失败 : " + msg);
    } else {
    	var chainStore =  data.chainStore;
		if (chainStore != undefined){
			if ($("#chainStoreId").val() != 0)
			    $("#chainStoreId").find("option:selected").text(chainStore.chain_name);
			else {
				$("#chainStoreId").append("<option value='"+chainStore.chain_id+"'>"+chainStore.chain_name+"</option>"); 
				$("#chainStoreId").attr("value", chainStore.chain_id);
			}
			alert("成功更新");
		} else 
			alert("更新失败");
    }
}
function deleteChainStore(){
	$.messager.prompt("密码验证","一旦删除这个连锁店，所有当前连锁店的信息将会被永久删除.输入密码:", function(password){
		if (password == "vj7683c688"){
			var params="formBean.chainStore.chain_id=" + $("#chainStoreId").val();
			$.post("<%=request.getContextPath()%>/action/chainSMgmtJSON!deleteChainStore",params, backProcessDeleteChainStore,"json");
		} else {
			alert("密码错误");
		}	   
	});

}
function backProcessDeleteChainStore(data){
	if (data.returnCode == SUCCESS){
		window.location.reload();
	} else {
		alert("删除失败 : " + data.message);
	}
		
}


function getChainStore(){
	var chainStoreId = $("#chainStoreId").val();
	if (chainStoreId == 0){
		clearChainStore();
	} else {
		var params="formBean.chainStore.chain_id=" + chainStoreId;
		$.post("<%=request.getContextPath()%>/action/chainSMgmtJSON!getChainStore",params, backProcessGetChainStore,"json");
	}
}
function backProcessGetChainStore(data){
	var chainStore =  data.chainStoreInfor;

	if (chainStore != undefined){
		$("#chainOwner").attr("value", chainStore.owner_name);
		$("#chainName").attr("value", chainStore.chain_name);
		$("#clientId").attr("value", chainStore.client_id);
		$("#initialAcctAmt").attr("value",chainStore.initialAcctAmt);
		$("#status").attr("value",chainStore.status);
		$("#allowEdit").attr("value", chainStore.allowChangeSalesPrice);
		$("#initialAcctAmt").attr("readonly", "readonly");
        $("#clientId").attr("readonly", "readonly");

        var priceIncrement = chainStore.priceIncrement;
        var priceIncreTF = $("#priceIncrement");
        if (priceIncrement == null || priceIncrement.id == undefined){
            priceIncreTF.attr("value", 0);
        } else {
        	priceIncreTF.attr("value", priceIncrement.id);
        }
        $("#allowAddBarcode").attr("value", chainStore.allowAddBarcode);
	}

}
function clearChainStore(){
	$("#chainOwner").attr("value", "");
	$("#chainName").attr("value", "");
	$("#initialAcctAmt").attr("value","");
	$("#clientId").attr("value","");
	$("#allowEdit").attr("value", "1");
	$("#initialAcctAmt").removeAttr("readonly");
	$("#clientId").removeAttr("readonly");
	$("#priceIncrement").attr("value", 0);
	$("#allowAddBarcode").attr("value", 0);
}


function validateChainStore(){
	var error = "";
	var chainOwner = $("#chainOwner").val();
	var chainNm = $("#chainName").val();

	if (chainNm == "")
		error += "连锁店名字 - 不能为空.\n";
	else if (chainNm.length > 20)
		error += "连锁店名字 - 超过最长20字.\n";

	if (chainOwner =="")
		error += "客户名字 - 不能为空.\n";

	if (error != ""){
		alert(error);
		return false;
	} else
		return true;
}


</script>
</head>
<body>
<s:form id="EditChainInforForm" action="/action/chainSMgmtJSP!save" method="POST" theme="simple" target="_blank" onsubmit="" >
  <table width="90%" align="center"  class="OuterTable">
    <tr><td>
		 <table width="100%" border="0">
	    <tr class="PBAOuterTableTitale">
	       <td height="78" colspan="8">连锁店/人员信息管理<br />
	         <br />
           - 通过这个界面管理员可以添加/修改/删除,连锁店信息以及连锁店员工信息<br/>           </td>
	    </tr>
	    <tr class="InnerTableContent">
	      <td width="90" height="25">
	         <strong>连锁店</strong></td>
	      <td><s:select id="chainStoreId" name="formBean.chainStore.chain_id"  list="uiBean.chainStores" headerKey="0" headerValue="----- 新增 -----"  listKey="chain_id" listValue="chain_name" onchange="getChainStore();"/></td>
	      <td width="68">&nbsp;</td>
	      <td width="314">&nbsp;</td>
	      <td width="182">&nbsp;</td>
	      <td width="137">&nbsp;</td>
	    </tr>
	    <tr class="InnerTableContent">
	      <td height="25"><strong>连锁店名字</strong></td>
	      <td><s:textfield id="chainName" name="formBean.chainStore.chain_name" size="25"/></td>
	      <td>&nbsp;</td>
	      <td>&nbsp;</td>
	      <td>&nbsp;</td>
	      <td>&nbsp;</td>
	    </tr>
	    <tr class="InnerTableContent">
	      <td height="25"><strong>负责人名字</strong></td>
	      <td colspan="2"><s:textfield id="chainOwner" name="formBean.chainStore.owner_name" size="25"/></td>
	      <td>&nbsp;</td>
	      <td>&nbsp;</td>
	      <td>&nbsp;</td>
	    </tr>
	    <tr class="InnerTableContent">
	      <td height="25"><strong>期初财务欠账</strong></td>
	      <td colspan="2"><s:textfield id="initialAcctAmt" name="formBean.chainStore.initialAcctAmt" size="25"/></td>
	      <td>&nbsp;</td>
	      <td>&nbsp;</td>
	      <td>&nbsp;</td>
	    </tr>
	    <tr class="InnerTableContent">
	      <td height="25"><strong>修改零售价</strong></td>
	      <td colspan="2"><s:select id="allowEdit" name="formBean.chainStore.allowChangeSalesPrice"  list="#{0:'不能修改',1:'允许修改'}" listKey="key" listValue="value"/>
	      </td>
	      <td colspan="3"></td>
	    </tr>
	    <tr class="InnerTableContent">
	      <td height="25"><strong>固定提价</strong></td>
	      <td colspan="5"><s:select id="priceIncrement" name="formBean.chainStore.priceIncrement.id"  list="uiBean.priceIncrements" listKey="id" listValue="description" headerKey="0" headerValue="------"/>
	      </td>
	    </tr>
	    <tr class="InnerTableContent">
	      <td height="25"><strong>创建条码</strong></td>
	      <td colspan="5">
	           <s:select id="allowAddBarcode" name="formBean.chainStore.allowAddBarcode"  list="#{0:'不能添加',1:'允许添加条码'}" listKey="key" listValue="value" />
	      </td>
	    </tr>	    
	    <!--  
	    <tr class="InnerTableContent">
	      <td height="25"><strong>打印模板</strong></td>
	      <td colspan="4"><s:textfield id="printTemplate" name="formBean.chainStore.printTemplate" size="25"/></td>
	      <td>&nbsp;</td>
	      <td>&nbsp;</td>
	      <td>&nbsp;</td>
	    </tr>
	    <tr class="InnerTableContent">
	      <td height="25"><strong>打印份数</strong></td>
	      <td colspan="4"><s:textfield id="printCopy" name="formBean.chainStore.printCopy" size="25"/></td>
	      <td>&nbsp;</td>
	      <td>&nbsp;</td>
	      <td>&nbsp;</td>
	    </tr>-->
	    <tr class="InnerTableContent">
	      <td height="25"><strong>精算账号</strong></td>
	      <td colspan="3"><s:textfield id="clientId" name="formBean.chainStore.client_id" size="15" onkeypress="return is_number(event);"/> *这个账号必须与精算客户账号对应，否则系统将无法匹配</td>
	      <td>&nbsp;</td>
	      <td>&nbsp;</td>
	    </tr>
	    <tr class="InnerTableContent">
	      <td height="25"><strong>状态</strong></td>
	      <td colspan="2"><s:select name="formBean.chainStore.status" list="uiBean.statusMap" id="status" listKey="key" listValue="value" /></td>
	      <td>&nbsp;</td>
	      <td>&nbsp;</td>
	      <td>&nbsp;</td>
	    </tr>
	    <tr class="InnerTableContent">
	      <td height="25"></td>
	      <td colspan="2"><s:if test="#session.LOGIN_USER.containFunction('chainSMgmtJSON!saveChainStore')"><input type="button" value="添加/更新连锁店" onclick="editChainStore();"/></s:if></td>
	      <td><s:if test="#session.LOGIN_USER.containFunction('chainSMgmtJSON!deleteChainStore')"><input type="button" value="删除连锁店" onclick="deleteChainStore();"/></s:if></td>
	      <td>&nbsp;</td>
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