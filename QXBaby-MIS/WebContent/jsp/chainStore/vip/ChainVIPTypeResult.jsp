<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib prefix="s" uri="/struts-tags" %>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>千禧宝贝连锁店管理信息系统</title>
<%@ include file="../../common/Style.jsp"%>

<script>
$(document).ready(function(){
	parent.$.messager.progress('close'); 
});
function openCardWin(url){
	window.open(url,'新窗口','height=470, width=400, toolbar=no, menubar=no, scrollbars=no, resizable=no, location=no, status=no');
}
function validateSubmit(){
	var vipTypeId = $("input:radio[name='formBean.vipType.id']:checked").val();
	if (vipTypeId == null){
       alert("请选中其中一个VIP类型,然后继续");
       return false;
	} else
		return true;
}

function add(){
	openCardWin('chainVIPJSPAction!preAddVIPType');
}

function update(){
	if (validateSubmit()){
		var vipTypeId = $("input:radio[name='formBean.vipType.id']:checked").val();
		openCardWin('chainVIPJSPAction!preSaveUpdateVIPType?formBean.vipType.id='+vipTypeId);		
	}
}
function deleteVIP(){
	if (confirm("你确定要删除VIP类型")){
	     document.vipTypeListForm.action="chainVIPJSPAction!deleteVIPType";
	     document.vipTypeListForm.submit();
	}
}
</script>
</head>
<body>
   <s:form action="/actionChain/chainVIPJSPAction" method="POST" name="vipTypeListForm" id="vipTypeListForm" theme="simple" onsubmit="return validateSubmit();"> 
	<table width="90%" align="center"  class="OuterTable">
	    <tr><td>
	
		 <table width="100%" border="0">
		    <tr class="PBAOuterTableTitale">
		       <td height="50">VIP卡类型管理<br />
	           - 管理员通过此功能增加/修改/删除VIP卡类型</td>
		    </tr>
		    <tr class="InnerTableContent">
		      <td height="4"><div class="errorAndmes"><s:actionerror cssStyle="color:red"/><s:actionmessage cssStyle="color:blue"/></div><hr width="100%" color="#FFCC00"/></td>
		    </tr>
		    <tr>
		      <td>
		            <!-- table to display the staff information -->
					<table width="60%"  align="left" class="OuterTable" id="org_table">
					  <tr class="PBAInnerTableTitale" align='left'>
					    <th width="35" height="35"></th>
					    <th width="120">卡类型名称</th>
					    <th width="80">折扣率</th>
					    <th width="80">积分系数</th>
					    <th width="120">备注</th>
					  </tr>
				      <s:iterator value="uiBean.chainVIPTypes" status="st" id="vipType" >
						    <tr class="InnerTableContent" <s:if test="#st.even">style='background-color: rgb(255, 250, 208);'</s:if>>	      
						      <td height="25"><input type="radio" name="formBean.vipType.id" value="<s:property value="#vipType.id"/>"/></td>
						      <td><s:property value="#vipType.vipTypeName"/></td>
						      <td><s:property value="#vipType.discountRate"/></td>
						      <td><s:property value="#vipType.couponRatio"/></td>
						      <td><s:property value="#vipType.comment"/></td>
						     </tr>
				       </s:iterator>	
				       <s:if test="uiBean.chainVIPTypes.size == 0">
						    <tr class="InnerTableContent">	      
						      <td colspan="5">没有记录</td>
						     </tr>				       
				       </s:if>  
					</table>
		      </td>
		    </tr>
		    <tr class="InnerTableContent">
		      <td height="4"><hr width="100%" color="#FFCC00"/></td>
		    </tr>
		    <tr class="InnerTableContent">
		      <td height="30"><s:if test="#session.LOGIN_CHAIN_USER.containFunction('chainVIPJSPAction!preAddVIPType')"><input type="button" value="添加" onClick="add();"/>&nbsp;</s:if>
		      				  <s:if test="#session.LOGIN_CHAIN_USER.containFunction('chainVIPJSPAction!preSaveUpdateVIPType')"><input type="button" value="更新" onclick="update();"/>&nbsp;</s:if>
		      				  <s:if test="#session.LOGIN_CHAIN_USER.containFunction('chainVIPJSPAction!deleteVIPType')"><input type="button" value="删除" onclick="deleteVIP();"/></s:if>
		      </td>
		    </tr>
		  </table>
	   </td></tr>
	 </table>
	 </s:form>
</body>
</html>