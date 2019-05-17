<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib prefix="s" uri="/struts-tags" %>
<%@ page import="com.onlineMIS.ORM.entity.chainS.vip.ChainVIPCard" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>千禧宝贝连锁店管理信息系统</title>
<%@ include file="../../common/Style.jsp"%>
<link href="<%=request.getContextPath()%>/conf_files/css/pagination.css" rel="stylesheet" type="text/css"/>
<SCRIPT src="<%=request.getContextPath()%>/conf_files/js/pagenav1.1.js" type=text/javascript></SCRIPT>
<script src="<%=request.getContextPath()%>/conf_files/js/ChainVIPConsumpHis.js?v=7.9" type=text/javascript></script>
<script>
$(document).ready(function(){
	renderPaginationBar($("#currentPage").val(), $("#totalPage").val());
	parent.$.messager.progress('close'); 
	$("#org_table tr").mouseover(function(){      
		$(this).addClass("over");}).mouseout(function(){    
		$(this).removeClass("over");}); 
	});

function openCardWin(url){
	window.open(url,'cardWindow','height=640, width=460, toolbar=no, menubar=no, scrollbars=no, resizable=no, location=no, status=no');
}
function validateSubmit(){
	var vipTypeId = $("input:radio[name='formBean.selectedCardId']:checked").val();
	if (vipTypeId == null){
       $.messager.alert('失败警告', "请选中其中一个VIP卡,然后继续", 'error');
       return false;
	} else
		return true;
}
function searchVIIPCards(){
	resetSearchForm();
	
	pageNav.fn($("#currentPage").val(),$("#totalPage").val());
}

pageNav.fn = function(page,totalPage){                
	$("#currentPage").attr("value",page);
    document.vipCardListForm.action="chainVIPJSPAction!searchVIPCards";
    document.vipCardListForm.submit();
};


function add(){
//	resetSearchForm();
	openCardWin("chainVIPJSPAction!preAddVIPCard");
}

function update(){
	if (validateSubmit()){
//		resetSearchForm();
 		var vipCardNo = $("input:radio[name='formBean.selectedCardId']:checked").val();
 		var url = encodeURI(encodeURI('chainVIPJSPAction!preSaveUpdateVIPCard?formBean.selectedCardId='+vipCardNo));
 		openCardWin(url);
	}
}
function deleteVIP(){
	if ( validateSubmit() && confirm("你确定要删除VIP卡,删除卡片之后无法恢复")){
//		 resetSearchForm();
	     document.vipCardListForm.action="chainVIPJSPAction!deleteVIPCard";
	     document.vipCardListForm.submit();
	}
}

function startVIP(){
	if (validateSubmit()){
//		resetSearchForm();
       document.vipCardListForm.action="chainVIPJSPAction!startVIPCard";
       document.vipCardListForm.submit();
	}
}
function stopVIP(){
	if (validateSubmit()){
//   resetSearchForm();
       document.vipCardListForm.action="chainVIPJSPAction!stopVIPCard";
       document.vipCardListForm.submit();
	}
}
function lostVIP(){
	if (validateSubmit()){
//		resetSearchForm();
       document.vipCardListForm.action="chainVIPJSPAction!lostVIPCard";
       document.vipCardListForm.submit();
	}
}
function downloadVIPs(){
    document.vipCardListForm.action="chainVIPJSPAction!downloadVIPs";
    document.vipCardListForm.submit();	
}
function quickVIPSalesOrder(){
	if (validateSubmit()){
		var param = "formBean.chainSalesOrder.vipCard.id=" + $("input:radio[name='formBean.selectedCardId']:checked").val();
		addTab3('<%=request.getContextPath()%>/actionChain/chainSalesJSPAction!preNewSalesOrder?' + param,'新建零售单');	
	}
}
function upgradeVIPDialog(){
	if (validateSubmit()){
		var param = "formBean.vipCard.id="  + $("input:radio[name='formBean.selectedCardId']:checked").val() ;
		$.modalDialog({
			title : 'VIP升级',
			width : 350,
			height : 220,
			modal : false,
			href : '<%=request.getContextPath()%>/actionChain/chainVIPJSPAction!preUpgradeVIP?' + param,
			buttons : [ {
				text : '提交信息',
				handler : function() {
					upgradeVipCard(); 
				}
			} ]
			});
	}
}
function updateVIPScoreDialog(){
	if (validateSubmit()){
		var param = "formBean.vipCard.id="  + $("input:radio[name='formBean.selectedCardId']:checked").val() ;
		
		$.modalDialog({
			title : 'VIP积分调整',
			width : 350,
			height : 220,
			modal : false,
			href : '<%=request.getContextPath()%>/actionChain/chainVIPJSPAction!preUpdateVIPScore?' + param,
			buttons : [ {
				text : '确认调整积分',
				handler : function() {
					updateVipScore(); 
				}
			} ]
			});
	}
}
function updateVipScore(){
    var params = $("#vipUpdateForm").serialize(); 
    //var params += "&formBean.chainUserInfor.myChainStore.chain_id =" + chainId;
    $.post("<%=request.getContextPath()%>/actionChain/chainVIPJSONAction!updateVipScore",params, updateVipScoreBk,"json");
}

function updateVipScoreBk(data){
	var response = data.response;
	var returnCode = response.returnCode;

	if (returnCode == SUCCESS){
		flag = true;
		var dialogA = $.modalDialog.handler;
		dialogA.dialog('close');
	    document.vipCardListForm.action="chainVIPJSPAction!searchVIPCards";
	    document.vipCardListForm.submit();
	} else 
	    $.messager.alert('失败警告', response.message, 'error');
}
function showUpdatePasswordDialog(){
	if (validateSubmit()){
		var param = "formBean.vipCard.id="  + $("input:radio[name='formBean.selectedCardId']:checked").val() ;
		
		$.modalDialog({
			title : '修改VIP密码',
			width : 350,
			height : 220,
			modal : false,
			href : '<%=request.getContextPath()%>/actionChain/chainVIPJSPAction!preUpdateVIPPassword?' + param,
			buttons : [ {
				text : '修改密码',
				handler : function() {
					updatePassword(); 
				}
			} ]
			});
	}
}
function updatePassword(){
	 if ($('#vipPasswordUpdateForm').form('validate')){
		 var password1= $("#password1").val();
		 var password2= $("#password2").val();
		 if (password1.length >6 || password2.length >6){
			 $.messager.alert('失败警告', "密码不能超过六位字符", 'error');
			 return ;
		 } else if (password1 != password2){
			 $.messager.alert('失败警告', "两次输入的密码不一致", 'error');
			 return ;
		 }
		 
		    var params = $("#vipPasswordUpdateForm").serialize(); 
		    //var params += "&formBean.chainUserInfor.myChainStore.chain_id =" + chainId;
		    $.post("<%=request.getContextPath()%>/actionChain/chainVIPJSONAction!updateVIPPassword",params, updateVipPasswordBk,"json");
	 }
}
function updateVipPasswordBk(data){
	var returnCode = data.returnCode;

	if (returnCode == SUCCESS){

		var dialogA = $.modalDialog.handler;
		dialogA.dialog('close');
		$.messager.alert('消息', "成功更新密码", 'info');
	} else 
		$.messager.alert('失败警告', data.message, 'error');
}
</script>
</head>
<body>
   <s:form action="/actionChain/chainVIPJSPAction" method="POST" name="vipCardListForm" id="vipCardListForm" theme="simple"> 
	<%@ include file="../../common/pageForm.jsp"%>
	<table width="100%" align="center"  class="OuterTable">
	    <tr><td>
	
		 <table width="100%" border="0">
	        <tr class="InnerTableContent">
		      <td colspan="4"> 
		                     卡号 <s:textfield name="formBean.vipCard.vipCardNo" size="10" onfocus='this.select();'/>&nbsp;
		         
		                      状态 <s:select name="formBean.vipCard.status"  list="uiBean.cardStatus"  listKey="key" listValue="value"  headerKey="-1" headerValue="--所有状态--" />&nbsp;
		                      卡种类 <s:select name="formBean.vipCard.vipType.id" list="uiBean.chainVIPTypes" listKey="id" listValue="vipTypeName"   headerKey="-1" headerValue="--所有类型--"/>&nbsp;
		                      名字首字母 <s:textfield name="formBean.vipCard.pinyin"  size="4" title="比如,'夏林'可以用xl或x在这里搜索" onfocus='this.select();'/>&nbsp; 
		                      电话 <s:textfield name="formBean.vipCard.telephone"  size="9" title="电话号码搜索" onfocus='this.select();'/>&nbsp; 
		                       连锁店 <s:select id="chainId" name="formBean.vipCard.issueChainStore.chain_id"  list="uiBean.chainStores" listKey="chain_id" listValue="chain_name"/>&nbsp;
                 <input type="button" value="搜索VIP信息" onclick="searchVIIPCards();"/>
               </td>
	        </tr>
		    <tr class="InnerTableContent">
		      <td height="4" colspan="4"><div class="errorAndmes"><s:actionerror cssStyle="color:red"/><s:actionmessage cssStyle="color:blue"/></div><hr width="100%" color="#FFCC00"/></td>
		    </tr>
		    <tr>
		      <td colspan="4">
		            <!-- table to display the staff information -->
					<table width="100%"  align="left" id="org_table">
					  <tr class="PBAInnerTableTitale" align='left'>
					    <th width="29" height="35"></th>
					    <th width="26">No.</th>
					    <th width="80">VIP卡号</th>
					    <th width="60">类型</th>
					    <th width="36">状态</th>
					    <th width="98">发卡连锁店</th>
					    <th width="80">最近消费日期</th>
					    <th width="86">卡持有者</th>
					    <th width="80">卡主生日</th>
					    <th width="35">性别</th>
					    <th width="76">电话</th>
					    <th width="70">当前积分</th>
					    <th width="80">剩余预存款</th>
					    <th>备注</th>
					  </tr>
				      <s:iterator value="uiBean.chainVIPCards" status="st" id="vipCard" >
						    <tr class="InnerTableContent" <s:if test="#st.even">style='background-color: rgb(255, 250, 208);'</s:if>>	      
						      <td height="25"><input type="radio" id="selectedCardId" name="formBean.selectedCardId" value="<s:property value="#vipCard.id"/>"/></td>
						      <td><s:property value="#st.index +1 + formBean.pager.firstResult"/></td>
						      <td><a href="#" onclick="consumpHis('<s:property value="#vipCard.id"/>')"><s:property value="#vipCard.vipCardNo"/></a></td>
						      <td <s:if test="#vipCard.vipType.id ==1">style='color:blue'</s:if>
						          <s:elseif test="#vipCard.vipType.id ==3">style='color:green'</s:elseif>><s:property value="#vipCard.vipType.vipTypeName"/></td>
						      <td <s:if test="#vipCard.status != 1">style='color:red'</s:if>>
						          <s:property value="#vipCard.statusS"/></td>
						      <td><s:property value="#vipCard.issueChainStore.chain_name"/></td>
						      <td <s:if test="#vipCard.statusConsump == 1"> style="color:red" </s:if>><s:date name="#vipCard.lastConsumpDate" format="yyyy-MM-dd"/></td>
						      <td><s:property value="#vipCard.customerName"/></td>
						      <td><s:date name="#vipCard.customerBirthday" format="yyyy-MM-dd"/></td>
						      <td><s:property value="#vipCard.genderS"/></td>
						      <td><s:property value="#vipCard.telephone"/></td>
						      <td><s:text name="format.price">  
						             <s:param value="#vipCard.accumulatedScore - #vipCard.consumedScore"/>
						          </s:text></td>
						      <td><s:text name="format.price">  
						             <s:param value="#vipCard.accumulateVipPrepaid"/>
						          </s:text></td>
						      <td><s:property value="#vipCard.comment"/></td>
						    </tr>
				       </s:iterator>	
				       <s:if test="uiBean.chainVIPCards.size == 0">
						    <tr class="InnerTableContent">	      
						      <td colspan="14">没有记录</td>
						     </tr>				       
				       </s:if><s:else>
						    <tr class="InnerTableContent">	      
						      <td colspan="14"><div id="pageNav"></div></td>
						    </tr>					       
				       </s:else>  
					</table></td>

		    </tr>
		    <tr class="InnerTableContent">
		      <td height="4" colspan="4"><hr width="100%" color="#FFCC00"/></td>

		    </tr>
		    <tr class="InnerTableContent">
		      <td width="10%"><input type="button" value="VIP快速开单" onclick="quickVIPSalesOrder();"/></td>
		      <td width="25%" height="30"><s:if test="#session.LOGIN_CHAIN_USER.containFunction('chainVIPJSPAction!preAddVIPCard')"><input type="button" value="添加VIP" onClick="add();"/>&nbsp;</s:if>
		                                  <s:if test="#session.LOGIN_CHAIN_USER.containFunction('chainVIPJSPAction!preSaveUpdateVIPCard')"><input type="button" value="修改/查看" onclick="update();"/>&nbsp;</s:if>
		                                  <s:if test="#session.LOGIN_CHAIN_USER.containFunction('chainVIPJSPAction!deleteVIPCard')"><input type="button" value="删除" onclick="deleteVIP();"/></s:if>
		      </td>
		      <td width="20%"><s:if test="#session.LOGIN_CHAIN_USER.containFunction('chainVIPJSPAction!startVIPCard')"><input type="button" value="启用" onClick="startVIP();"/>&nbsp;</s:if>
		      				  <s:if test="#session.LOGIN_CHAIN_USER.containFunction('chainVIPJSPAction!stopVIPCard')"><input type="button" value="停用" onclick="stopVIP();"/>&nbsp;</s:if>
		                      <s:if test="#session.LOGIN_CHAIN_USER.containFunction('chainVIPJSPAction!lostVIPCard')"><input type="button" value="挂失" onclick="lostVIP();"/></s:if>
		                      <input type="button" value="重设密码" onclick="showUpdatePasswordDialog();"/>
		      </td>
		      <td width="45%"><input type="button" value="VIP积分调整" onclick="updateVIPScoreDialog();"/>
		                      <s:if test="#session.LOGIN_CHAIN_USER.containFunction('chainVIPJSPAction!preUpgradeVIP')"><input type="button" value="VIP升级" onclick="upgradeVIPDialog();"/></s:if>
		      				  <s:if test="#session.LOGIN_CHAIN_USER.containFunction('chainVIPJSPAction!downloadVIPs')"><input type="button" value="下载VIP信息" onclick="downloadVIPs();"/></s:if>
		      </td>
		    </tr>
		  </table>
	   </td></tr>
	 </table>
	 </s:form>
</body>
</html>