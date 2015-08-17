<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib prefix="s" uri="/struts-tags" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>操作失败</title>
<script type="text/javascript" src="<%=request.getContextPath()%>/conf_files/js/jquery-1.8.0.min.js"></script>
<LINK href="<%=request.getContextPath()%>/conf_files/css/qxbaby_css.css" type=text/css rel=STYLESHEET> 
<script type="text/javascript">

</script>

</head>
<body>
<%@ include file="Style.jsp"%>
   <div id="info">您的操作失败。</div>
   <s:actionmessage/><s:fielderror/>

</body>
</html>