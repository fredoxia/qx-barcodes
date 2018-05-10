<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %> 
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>  
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="Cache" content="no-cache">
<meta http-equiv="expires" content="0"> 
<meta name="viewport" content ="width=device-width, initial-scale=1">
<%@ include file="JQMStyle.jsp"%>
<script>
$(document).ready(function(){
	$("#clientPY").focus();

})
function chooseCust(clientId){
	$.mobile.loading("show",{ theme: "b", text: "正在加载数据", textonly: false});
	
	var params = "formBean.clientId=" + clientId;
	$.post('<%=request.getContextPath()%>/action/ipadJSON!chooseCust', params, 
			function(result) {
				if (result.returnCode == 2) {
					window.location.href = "<%=request.getContextPath()%>/jsp/headQ/pda2/StartOrder.jsp"

				} else {
					$.mobile.loading("hide");
					renderPopup("系统错误",result.msg);
				}
			}, 'JSON');
}
function clearCust(){
	$("#clientPY").focus();
	$("#clientPY").attr("value","");
	
    $("#clients").hide();
    
    $('#custBody tr').each(function () {                
        $(this).remove();
    });

}
function searchClient(){
	var clientPY= $("#clientPY").val(); 

	if (clientPY.length >= 2){
		var params = "formBean.pinyin=" + $("#clientPY").val();

		$.mobile.loading("show",{ theme: "b", text: "正在加载数据", textonly: false});
		
		$.post('<%=request.getContextPath()%>/action/ipadJSON!searchClientByPY', params, 
		function(result) {
			
			if (result.returnCode == 2) {
			    $('#custBody tr').each(function () {                
			        $(this).remove();
			    });
			    
			    var cops = result.returnValue;
			    if (cops != null && cops.length != 0){
				    for (var i = 0; i < cops.length; i++){
				    	
				    	var j = i +1;
				        if (cops[i] != "")  {
					          $("<tr id='pRow"+cops[i].client_id+"'><td style='vertical-align:middle;'>"+
					        		  cops[i].name +" "+
					        		  cops[i].region.name+" "+
										"<div name='btnGroup' data-role='controlgroup' data-type='horizontal'>"+
											"<input name='addBtn' type='button' value='选中' data-mini='true'  data-inline='true' onclick='chooseCust("+cops[i].client_id+");'/>"+
										"</div>"+
							          "</td></tr>").appendTo("#custBody");
				        }
				    }
			    } else {
			    	$("<tr><td colspan=5><font color='red'>没有查询到客户信息</font> </td></tr>").appendTo("#custBody");
			    }
			    
			    $("#clients").show();
			    $("[name='addBtn']").button();
			    $("[name='btnGroup']").controlgroup();

			    $.mobile.loading("hide");
			} else {
				$.mobile.loading("hide");
				renderPopup("系统错误",result.msg);
			}
		}, 'JSON');
    }
}

</script>
</head>
<body>
<!-- 按照品牌汇总的 MyOrder 汇总 -->
	<div id="myOrder" data-role="page">
		<script>
		    
		</script>
		<jsp:include  page="MobileHeader.jsp"/>

		<div  data-role="content" class="content">
				<table>
				    <tr>
						<td><label for="clientPY">客户名字: </label></td> 
						<td><input id="clientPY" size="12" name="clientPY"  placeholder="输入至少两位客户拼音" onkeyup="searchClient();"/></td>
					</tr>

				</table>
				<div class="ui-grid-a">
    				<div class="ui-block-a"><input type="button" id="searchBnt" data-theme="a" onclick ="searchClient();" value="查找客户"/></div>
    				<div class="ui-block-b"><input type="button" id="clearBnt" data-theme="b" onclick ="clearCust();" value="清空客户"/></div>
				</div>
				<div id="clients" style="display:none">
					<table data-role="table" id="table-column-toggle" class="ui-responsive table-stroke">

					     <tbody id="custBody">
					     </tbody>
				    </table>	
				</div>
		</div>


		<div data-role="footer" data-theme="b" data-position="fixed" data-tap-toggle="false">
			<div data-role="navbar">
		      <ul>
		      	<li><a href="<%=request.getContextPath()%>/action/ipadJSP!goToEditCustPDA" data-icon="edit" data-ajax="false" class="ui-btn-active ui-state-persist">选择客户</a></li>
		        <li><a href="<%=request.getContextPath()%>/jsp/headQ/pda2/StartOrder.jsp" data-icon="edit" data-ajax="false">选货</a></li>
		        <li><a href="<%=request.getContextPath()%>/action/ipadJSP!viewCurrentOrderPDA" data-icon="bullets" data-ajax="false">选货详情</a></li>
		        <li><a href="<%=request.getContextPath()%>/action/ipadJSP!listDraftOrderPDA" data-icon="bullets" data-ajax="false">草稿订单</a></li>
		      </ul>
		     </div>
		</div> 
		<jsp:include  page="Popup.jsp"/>

	</div>

</body>
</html>