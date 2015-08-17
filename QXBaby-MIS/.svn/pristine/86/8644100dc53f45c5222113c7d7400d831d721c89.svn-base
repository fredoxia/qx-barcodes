<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib prefix="s" uri="/struts-tags" %>
<%@ page import="com.onlineMIS.common.Common_util" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>千禧宝贝连锁店管理信息系统</title>
<%@ include file="../../common/Style.jsp"%>
<script>
function selectBrand(brandName, brandId){
	window.opener.selectBrand(brandName, brandId);
	window.close();
}
</script>
</head>
<body>
   
   <table width="100%"  align="left" class="OuterTable">
		  <tr class="PBAInnerTableTitale" align='left'>
		    <th width="95" height="35">品牌名</th>
		    <th width="95">供应商</th>
		    <th width="50">所属</th>
		    <th>&nbsp;</th>
		  </tr>
	      <s:iterator value="uiBean.brands" status="st" id="brand" >
			    <tr class="InnerTableContent" <s:if test="#st.even"> style='<%=Common_util.EVEN_ROW_BG_STYLE %>'</s:if>>	      
			      <td height="25"><s:property value="#brand.brand_Name"/></td>
			      <td><s:property value="#brand.supplier"/></td>
			      <td><s:property value="#brand.typeS"/>
			      </td>
			      <td><a href="#" onclick="selectBrand('<s:property value="#brand.brand_Name"/>','<s:property value="#brand.brand_ID"/>');"><img src='<%=request.getContextPath()%>/conf_files/web-image/editor.gif' border='0'/></a></td>
			    </tr>
	       </s:iterator>
	       <s:if test="uiBean.brands.size==0">
			 	<tr height="22" class="InnerTableContent" align="center">
			 	        <td colspan="5">没有找到相应结果</td>
			 	 </tr>
		   </s:if>	  
	</table>
	<br/>
</body>
</html>