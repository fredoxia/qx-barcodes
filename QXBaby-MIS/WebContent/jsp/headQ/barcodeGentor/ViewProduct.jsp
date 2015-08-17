<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib prefix="s" uri="/struts-tags" %>
<%@ page import="com.onlineMIS.ORM.entity.headQ.barcodeGentor.*" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<title>千禧宝贝连锁店管理信息系统</title>
<%@ include file="../../common/Style.jsp"%>
</head>
<body>
    <table width="90%" align="center"  class="OuterTable">
    <tr><td>
	    <table width="100%" border="0">
	       <tr class="PBAOuterTableTitale">
	          <td colspan="2">货品信息</td>
	       </tr>
	       <tr>
	          <td colspan="2"><font color="red"><s:fielderror cssStyle="color:red"/></font><s:actionerror cssStyle="color:red"/></td>
	       </tr>
	       <tr class="InnerTableContent" style="background-color: rgb(255, 250, 208);">
	          <td height="18" width="90"><strong>条形码 </strong>: </td>
	          <td><s:property value="uiBean.product.barcode"/></td>
	       </tr>
	       <tr class="InnerTableContent">
	          <td height="18"><strong>品牌 </strong>:</td>
	          <td><s:property value="uiBean.product.product.brand.brand_Name"/></td>
	       </tr>

	       <tr class="InnerTableContent" style="background-color: rgb(255, 250, 208);">
	          <td height="18"><strong>地区</strong>:</td>
	          <td><s:property value="uiBean.product.product.area.area_Name"/></td>
	       </tr>
	       <tr class="InnerTableContent">
	          <td height="18"><strong>年份  </strong>:</td>
	          <td><s:property value="uiBean.product.product.year.year"/></td>
	       </tr>
	       <tr class="InnerTableContent" style="background-color: rgb(255, 250, 208);">
	          <td height="18"><strong>季度</strong>         :</td>
	          <td><s:property value="uiBean.product.product.quarter.quarter_Name"/></td>
	       </tr>
	       <tr class="InnerTableContent">
	          <td height="18"><strong>类别</strong>       :</td>
	          <td><s:property value="uiBean.product.product.category.category_Name"/></td>
	       </tr>
	       <tr class="InnerTableContent" style="background-color: rgb(255, 250, 208);">
	          <td height="18"><strong>产品货号</strong>    :</td>
	          <td><s:property value="uiBean.product.product.productCode"/></td>
	       </tr>
	       <tr class="InnerTableContent">
	          <td height="18"><strong>颜色</strong>       :</td>
	          <td><s:property value="uiBean.product.color.name"/></td>
	       </tr>
	       <tr class="InnerTableContent" style="background-color: rgb(255, 250, 208);">
	          <td height="18"><strong>尺码</strong>    :</td>
	          <td><s:property value="uiBean.product.size.name"/></td>
	       </tr>
	       <tr class="InnerTableContent">
	          <td height="18"><strong>齐码数量 </strong>   :</td>
	          <td><s:property value="uiBean.product.product.numPerHand"/></td>
	       </tr>
	       <tr class="InnerTableContent" style="background-color: rgb(255, 250, 208);">
	          <td height="18"><strong>单位 </strong> :</td>
	          <td><s:property value="uiBean.product.product.unit"/></td>
	       </tr>
	       <tr class="InnerTableContent">
	          <td height="18"><strong>连锁零售价 </strong>       :</td>
	          <td><s:property value="uiBean.product.product.salesPrice"/></td>
	       </tr>
	       <tr class="InnerTableContent" style="background-color: rgb(255, 250, 208);">
	          <td height="18"><strong>批发价 </strong>       :</td>
	          <td><s:property value="uiBean.product.product.lastChoosePrice"/></td>
	       </tr>
	       <s:if test="uiBean.canViewRecCost == true">
	       <tr class="InnerTableContent">
	          <td height="18"><strong>进价 </strong>       :</td>
	          <td><s:property value="uiBean.product.product.recCost"/></td>
	       </tr>
	       </s:if>
	       <s:if test="uiBean.canViewMyRecCost == true">
	       <tr class="InnerTableContent">
	          <td height="18"><strong>我的成本价 </strong>       :</td>
	          <td><s:property value="uiBean.product.product.lastInputPrice"/></td>
	       </tr>
	       </s:if>
	       <tr class="InnerTableContent" style="background-color: rgb(255, 250, 208);">
	          <td colspan="2"></td>
	       </tr>
	    </table>
	    </td>
	</tr>
	</table>   
</body>
</html>