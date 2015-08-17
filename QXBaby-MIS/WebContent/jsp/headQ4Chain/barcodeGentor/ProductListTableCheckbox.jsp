<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!-- used by SearchBarcode.jsp -->
<form name="searchedBarcodeForm" id="searchedBarcodeForm" action="" method="post">
<table width="95%"  align="center" class="OuterTable" id="org_table">
  <tr class="PBAOuterTableTitale" align='center'>
    <th width="10"><input type="checkbox" id="selectAllCheck" onclick="selectAll()"/></th>
    <th width="30">序号</th>
    <th width="53">年份</th>
    <th width="100">品牌</th>
    <th width="75">货品类</th>
    <th width="85">货号</th>
    <th width="35">颜色</th>
    <th width="70">齐码/单位</th>
    <th width="40">零售价</th>
    <th width="40">进价</th>
    <th width="95">货品条型码</th>
    <th width="85">录入时间</th>
    <td width="55">修改资料</td>	
    <td width="55">继续添加</td>	
  </tr>
  <tbody id="orgTablebody">
  </tbody>
  
</table>
</form>