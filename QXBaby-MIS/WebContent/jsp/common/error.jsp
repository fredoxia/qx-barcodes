<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib prefix="s" uri="/struts-tags" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>访问失败</title>
<script type="text/javascript" src="<%=request.getContextPath()%>/conf_files/js/jquery-1.8.0.min.js"></script>
<LINK href="<%=request.getContextPath()%>/conf_files/css/qxbaby_css.css" type=text/css rel=STYLESHEET> 
<script type="text/javascript">
$(document).ready(function(){
	parent.$.messager.progress('close'); 
});
function viewLog(){
    var exceptionDiv = document.getElementById("exceptionDiv");

    if (exceptionDiv.style.display =="none")
       exceptionDiv.style.display ="block";
    else
       exceptionDiv.style.display ="none";
}
</script>

</head>
<body>
   <%@ include file="Style.jsp"%>
   <br/>
      你所访问的资源不存在或者存在错误，请检查。<p/>
   <div class="errorAndmes"><s:actionerror cssStyle="color:red"/><s:actionmessage cssStyle="color:blue"/></div>
   	     
</body>
</html>