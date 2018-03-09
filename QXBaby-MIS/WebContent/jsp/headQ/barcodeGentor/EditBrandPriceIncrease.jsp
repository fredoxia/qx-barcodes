<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib prefix="s" uri="/struts-tags" %>

<html>
<head>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=8" />
<title>产品条形码导入</title>
<%@ include file="../../common/Style.jsp"%>

<script>
$(document).ready(function(){
	parent.$.messager.progress('close'); 
});



</script>

</head>
<body>
<s:form id="BPIGenForm" action="" method="POST"  theme="simple">
 <table width="100%" border="0">
    <s:hidden name="formBean.productBarcode.product.area.area_ID" value="1"/>     
    <tr class="InnerTableContent">
      <td width="80" height="19"><strong>年份：</strong></td>
      <td width="180">
      		<s:select name="formBean.bpi.year.year_ID" size="1" id="yearId"  list="uiBean.basicData.yearList" listKey="year_ID" listValue="year"  />     
      </td>
      <td width="85"><strong>季度：</strong></td>
      <td width="180">
            <s:select name="formBean.bpi.quarter.quarter_ID" size="1" id="quarter_ID" list="uiBean.basicData.quarterList" listKey="quarter_ID" listValue="quarter_Name"  />
      </td>
     
    </tr>
    <tr class="InnerTableContent">
      <td height="70"><strong>品牌：</strong></td>
      <td>
         <%@ include file="SearchBrandStub.jsp"%>
      </td>
      <td><strong>调价为原价：</strong></td>
      <td>
         <s:textfield name="formBean.bpi.increase" size="8" cssClass="easyui-numberspinner" value="80" data-options="min:10,max:100,increment:5,precision:0"/> %
      </td>
      <td>&nbsp;</td>        
    </tr>
    <tr class="InnerTableContent">
      <td height="4" colspan="4"><hr width="100%" color="#FFCC00"/></td>
    </tr>
  </table>
</s:form>

<br/>
</body>
</html>