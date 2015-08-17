<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script>
function changeBasicData(value){
    if (value != ""){
    	document.basicDataForm.action="basicData!changeBasicData";
    	document.basicDataForm.submit(); 
    }
}
</script>
<s:form id="basicDataForm" name="basicDataForm" action="" theme="simple">
	 <table width="100%" border="0">
	    <tr class="InnerTableContent">
	      <td width="101" height="19">
	         <strong>基础资料类别：</strong>
	      </td>
		  <td>
		      <s:select name="formBean.basicData" onchange="changeBasicData(this.value);"  list="#{'year':'年份','quarter':'季度','brand':'品牌','category':'货品类别','color':'颜色','productUnit':'货品单位','numPerHand':'齐手数量'}" listKey="key" listValue="value"  headerKey=""  headerValue="请选择"/>
		  </td>
	    </tr>
	 </table>
</s:form>