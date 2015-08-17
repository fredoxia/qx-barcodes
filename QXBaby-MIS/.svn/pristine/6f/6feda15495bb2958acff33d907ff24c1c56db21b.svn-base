<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib prefix="s" uri="/struts-tags" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>人力资源绩效评估</title>
<%@ include file="../../common/Style.jsp"%>
<script>
function deletePeopEvalMark(){
	if (confirm("你确定要删除这份评估表吗?")){
	   var id = $("#evaluationMarkId").val();
	   var param = "peopleEvalMark.id=" + id;
       $.post("hrEvalJSON!deletePeopleEvalMark",param, deletePeopEvalMarkBackProcess,"json");	
	}
}

function deletePeopEvalMarkBackProcess(data){
	var result = data.result;

	if (result == "SUCCESS"){

		window.location.href = "<%=request.getContextPath()%>/jsp/headQ/common/updateSuccess.jsp?location=close";
	}else {
		alert("删除评估失败。此月评估操作已经关闭或者你不是这份评估的评估管理员。请核对。");
	}
}
$(document).ready(function(){
	parent.$.messager.progress('close'); 
});
</script>
</head>
<body>
    <table width="100%" align="center"  class="OuterTable">
    <tr><td>

	 <table width="100%" border="0">
	    <tr class="PBAOuterTableTitale">
	       <td height="78" colspan="9">人力资源 - 员工绩效评估<br />
	         <br />
           - 管理人员通过此页面核对和查看员工的绩效评估
           - 在此月评估开通的情况下，管理人员能够删除此份评估</td>
	    </tr>
	    <tr class="InnerTableContent">
	      <td width="71" height="32">
	         <strong>评估月份</strong><s:hidden name="peopleEvalMark.id" id="evaluationMarkId"/></td>
	      <td width="149"><s:property value="peopleEvalMark.peopleEvaluation.evaluationYear"/>-<s:property value="peopleEvalMark.peopleEvaluation.evaluationMonth"/></td>
	      <td width="42"><strong>员工</strong></td>
	      <td width="111"><s:property value="peopleEvalMark.peopleEvaluation.evaluatee.user_name"/></td>
	      <td width="38"><strong>职位</strong></td>
	      <td width="111"><s:property value="peopleEvalMark.peopleEvaluation.evaluatee.jobTitle"/></td>
	      <td width="68"><strong>评估人员</strong></td>
	      <td width="312"><s:property value="peopleEvalMark.evaluater.user_name"/></td>
	      <td width="99">&nbsp;</td>
	    </tr>
	    <tr class="InnerTableContent">
	      <td height="4" colspan="9"><hr width="100%" color="#FFCC00"/></td>
	    </tr>
	    <tr class="InnerTableContent">
	      <td height="32" colspan="9">
		    <table width="100%" border="1" cellpadding="0" cellspacing="0" bordercolor="#666666">
				  <tr>
					<td width="10%" height="25"><div align="center">&nbsp;</div></td>
					<td width="14%"><div align="center"><strong>不符合目标</strong></div></td>
					<td colspan="3"><div align="center"><strong>低</strong></div></td>
					<td colspan="3"><div align="center"><strong>达标</strong></div></td>
					<td colspan="3"><div align="center"><strong>高</strong></div></td>
					<td><div align="center"><strong>得分</strong></div></td>
				  </tr>
				 <s:iterator value="peopleEvalMark.peopleEvalItemMark_set" status="st" id="evalMark" >
				  <tr>
				 	<td rowspan="3"><div align="center"><strong><s:property value="#st.index+1"/>.<s:property value="#evalMark.evaluationItem.item_name"/></strong></div></td>
				    <td height="25"><s:property value="#evalMark.evaluationItem.item_desp_1"/></td>
				    <td colspan="3"><s:property value="#evalMark.evaluationItem.item_desp_2"/></td>
				    <td colspan="3"><s:property value="#evalMark.evaluationItem.item_desp_3"/></td>
				    <td colspan="3"><s:property value="#evalMark.evaluationItem.item_desp_4"/></td>
				    <td>&nbsp;</td>
			       </tr>
				  <tr>
					<td height="25"><div align="center">0
					    <input type="radio" disabled="disabled" <s:if test="#evalMark.item_mark == 0">checked</s:if> value="0"/>
					</div></td>
					<td width="8%"><div align="center">1
					    <input type="radio" disabled="disabled" <s:if test="#evalMark.item_mark == 1">checked</s:if>  value="1"/>
					</div></td>
					<td width="8%"><div align="center">2
					    <input type="radio" disabled="disabled" <s:if test="#evalMark.item_mark == 2">checked</s:if>  value="2"/>
					</div></td>
					<td width="8%"><div align="center">3
					    <input type="radio" disabled="disabled" <s:if test="#evalMark.item_mark ==3">checked</s:if>  value="3"/>
					</div></td>
					<td width="8%"><div align="center">4
					    <input type="radio" disabled="disabled" <s:if test="#evalMark.item_mark == 4">checked</s:if>  value="4"/>
					</div></td>
					<td width="9%"><div align="center">5
					    <input type="radio" disabled="disabled" <s:if test="#evalMark.item_mark ==5">checked</s:if>  value="5"/>
					</div></td>
					<td width="8%"><div align="center">6
					    <input type="radio" disabled="disabled" <s:if test="#evalMark.item_mark == 6">checked</s:if>  value="6"/>
					</div></td>
					<td width="8%"><div align="center">7
					    <input type="radio" disabled="disabled" <s:if test="#evalMark.item_mark == 7">checked</s:if>  value="7"/>
					</div></td>
					<td width="7%"><div align="center">8
					    <input type="radio" disabled="disabled" <s:if test="#evalMark.item_mark == 8">checked</s:if>  value="8"/>
					</div></td>
					<td width="7%"><div align="center">9
					    <input type="radio" disabled="disabled" <s:if test="#evalMark.item_mark == 9">checked</s:if>  value="9"/>
					</div></td>
					<td><div align="center"><s:property value="#evalMark.item_mark"/></div></td>
				  </tr>

			       <tr>
				    <td height="50">对于此点评价：</td>
				    <td colspan="10"><s:property value="#evalMark.comment"/></td>
			       </tr>
			       <tr>
				    <td colspan="12">&nbsp;</td>
			       </tr>
				</s:iterator>
				<tr>
				    <td height="50"><div align="center"><strong>总体评价/得分：</strong></div></td>
				    <td colspan="10"><s:property value="peopleEvalMark.comment"/></td>
				    <td><div align="center"><strong><s:property value="peopleEvalMark.mark"/></strong></div></td>
			       </tr>
			</table>		  </td>
        </tr>
	    <tr class="InnerTableContent">
	      <td height="4" colspan="9"><hr width="100%" color="#FFCC00"/>&nbsp;</td>
	    </tr>
	    <tr class="InnerTableContent">
	      <td height="30" colspan="9"><input type="submit" value="删除评估" onClick="deletePeopEvalMark();"/></td>
	    </tr>
	  </table>
   </td></tr>
 </table>
</body>
</html>