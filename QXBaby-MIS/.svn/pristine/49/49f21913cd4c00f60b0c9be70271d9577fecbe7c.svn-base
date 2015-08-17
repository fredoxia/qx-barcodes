<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib prefix="s" uri="/struts-tags" %>
<%@ page import="com.onlineMIS.common.Common_util" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>千禧宝贝连锁店管理信息系统</title>
<script>
function selectColor(){
	var str = "";
	if ($("input[name='selectColor']:checked").length == 0){
		alert("请先选中颜色");
	} else {
		$("input[name='selectColor']:checked").each(function(){  
			str+=$(this).val()+";";  
		});
		window.opener.selectColor(str);
		window.close();
	}
}
</script>
</head>
<body>
   <%@ include file="../../common/Style.jsp"%>
   <table width="100%"  align="left" class="OuterTable">
		  <tr class="PBAInnerTableTitale" align='left'>
		    <th width="35" height="35"></th>
		    <th width="110">颜色</th>
		  </tr>
	      <s:iterator value="uiBean.colors" status="st" id="color" >
			    <tr class="InnerTableContent" <s:if test="#st.even"> style='<%=Common_util.EVEN_ROW_BG_STYLE %>'</s:if>>	      
			      <td height="25"><input type='checkbox' name='selectColor' value='<s:property value="#color.colorId"/>,<s:property value="#color.name"/>'/></td>
			      <td><s:property value="#color.name"/></td>
			    </tr>
	       </s:iterator>
	       <s:if test="uiBean.colors.size==0">
			 	<tr height="22" class="InnerTableContent" align="center">
			 	        <td colspan="2">没有找到相应结果</td>
			 	 </tr>
		   </s:if>	 
		   <tr class="PBAInnerTableTitale" align='left'>
		    <th height="35"><input type="button" value="选择" onclick ="selectColor();"/></th>
		    <th></th>
		  </tr> 
	</table>
	<br/>
</body>
</html>