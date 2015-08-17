<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib prefix="s" uri="/struts-tags" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>更新成功</title>
<script type="text/javascript" src="<%=request.getContextPath()%>/conf_files/js/jquery-1.8.0.min.js"></script>
<LINK href="<%=request.getContextPath()%>/conf_files/css/qxbaby_css.css" type=text/css rel=STYLESHEET> 
<script type="text/javascript">
var prefix = "<%=request.getContextPath()%>";
var postAction = "<%=request.getParameter("location")%>";
var msg;

function clock(){
	i = i -1;

	if (postAction == "close"){
		
		msg = "<font color='red' size='5'><b>"+ i+"</b></font>秒后自动关闭页面";
	}else {
		msg = "<font color='red' size='5'><b>"+ i+"</b></font>秒后自动跳转到指定页面";
	}

	$("#info").html(msg);
	if(i > 0){

		setTimeout("clock();",1000);
	}else{
		
		if (postAction == "close")
			window.close(); 
		else 
			location.href= prefix + postAction;
	}
}
var i = 4;
clock();
</script>

</head>
<body>
   <div class="errorAndmes"><s:actionmessage />操作成功！</div>
   <div id="info" class="errorAndmes"></div>
</body>
</html>