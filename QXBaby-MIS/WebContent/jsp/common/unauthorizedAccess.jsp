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

</script>

</head>
<body>
   <%@ include file="Style.jsp"%>
   <s:actionmessage/><s:fielderror/>
   <br/>
   您暂时还没有该功能访问权限。请联系管理人员。
   <a href="javascript:history.go(-1);">返回</a>
</body>
</html>