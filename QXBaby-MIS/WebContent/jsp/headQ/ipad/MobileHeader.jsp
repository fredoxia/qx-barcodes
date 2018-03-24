<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
<script>

function clearOrder(){
	$.mobile.loading("show",{ theme: "b", text: "正在加载数据", textonly: false});
	
      $.post('<%=request.getContextPath()%>/action/ipadJSON!clearOrders', "", 
		function(result) {
			
			if (result.returnCode == 2) {
				renderPopup("系统消息","当前订单已经清空，你可以继续开单")

			    $.mobile.loading("hide");
				window.location.href = "<%=request.getContextPath()%>/action/ipadJSP!goToEditCust";
			} else {
				$.mobile.loading("hide");
				renderPopup("系统错误",result.msg);
			}
		}, 'JSON');
}

function logoff(){
	window.location.href = "<%=request.getContextPath()%>/action/ipadJSP!logoff";
}
</script>
<div data-role="header" data-theme="b" data-position="fixed" data-tap-toggle="false">
      <a href="#" data-transition="fade" onclick="logoff();">退出登陆</a>
      <a href="#" data-rel="dialog" data-transition="fade" onclick="clearOrder();">重新开单</a>
	  <h4><c:out value="${sessionScope.HQ_SESSION_INFO_CUSTNAME}" escapeXml="false" default="未选客户"></c:out></h4>
</div> 