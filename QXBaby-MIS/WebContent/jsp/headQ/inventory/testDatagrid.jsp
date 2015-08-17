<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib prefix="s" uri="/struts-tags" %>
<html>
<head>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@ include file="../../common/Style.jsp"%>
<LINK type=text/css href="<%=request.getContextPath()%>/conf_files/js/tableRowSelect/tableRowSelect.css" rel=STYLESHEET>
<script type="text/javascript" src="<%=request.getContextPath()%>/conf_files/js/jquery.hotkeys-0.7.9.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/conf_files/js/tableRowSelect/tableRowSelect.js"></script>
<title>无标题文档</title> 
</head> 
<body> 
<form id="form1" name="form1" method="post" action=""> 
<label> 
<input type="text" name="txt_no" id="txt_no" /> 
</label> 
<br /> 
<table width="600" border="0" cellpadding="3" cellspacing="1" class="dataTablegrid"> 
<tr> 
<td>1</td> 
<td> 张三</td> 
<td> </td> 
</tr> 
<tr> 
<td>2</td> 
<td>李四</td> 
<td> </td> 
</tr> 
<tr> 
<td>3</td> 
<td>王五</td> 
<td> </td> 
</tr> 
<tr> 
<td>4</td> 
<td>马六</td> 
<td> </td> 
</tr> 
<tr> 
<td>5</td> 
<td> </td> 
<td> </td> 
</tr> 
<tr> 
<td>6</td> 
<td> </td> 
<td> </td> 
</tr> 
<tr> 
<td>7</td> 
<td> </td> 
<td> </td> 
</tr> 
<tr> 
<td>8</td> 
<td> </td> 
<td> </td> 
</tr> 
<tr> 
<td>9</td> 
<td> </td> 
<td> </td> 
</tr> 
<tr> 
<td>10</td> 
<td> </td> 
<td> </td> 
</tr> 
</table> 
<input type="hidden" name="prevTrIndex" id="prevTrIndex" value="-1" /> 
</form> 
 
<script type="text/javascript"> 
$(document).ready(function(){ 
	initialize();
}) ;

function tableRowSelectEnter(){
	var selectedRow = $("#prevTrIndex").val();
	alert(selectedRow);
}
</script> 

</body> 
</html>