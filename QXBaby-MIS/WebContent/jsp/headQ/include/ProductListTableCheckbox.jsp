<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!-- used by SearchBarcode.jsp -->
<form name="searchedBarcodeForm" id="searchedBarcodeForm" action="" method="post">
<table width="100%"  align="center" class="OuterTable" id="org_table">
  <tr class="PBAOuterTableTitale">
       <td colspan="19">同类型条形码</td>
    </tr>
  <tr class="PBAOuterTableTitale" align='center'>
    <th width="10"><input type="checkbox" id="selectAllCheck" onclick="selectAll()"/></th>
    <th width="30">序号</th>
    <th width="53">年份</th>
    <th width="80">品牌</th>
    <td width="50">品牌备注</td>
    <th width="85">货品类</th>
    <th width="90">货号</th>
    <th width="35">颜色</th>
    <th width="70">齐码/单位</th>
    <th width="70">厂家零售价</th>
    <th width="40">折扣</th>
    <th width="40">零售价</th>
    <th width="40">进价</th>
    <th width="48">预设价1</th>
    <th width="110">货品条型码</th>
    <th width="90">录入时间</th>
    <th width="60">归属</th>
    <td width="35">修改</td>	
  </tr>
  <tbody id="orgTablebody">
  </tbody>
  
</table>
</form>