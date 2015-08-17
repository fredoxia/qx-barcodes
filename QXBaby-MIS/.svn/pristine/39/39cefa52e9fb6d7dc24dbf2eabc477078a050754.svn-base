<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib prefix="s" uri="/struts-tags" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>人力资源绩效评估</title>
<%@ include file="../../common/Style.jsp"%>
<script>
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
           - 通过此页面核对和查看员工当月或全年的绩效评估</td>
	    </tr>
	    <tr class="InnerTableContent">
	      <td width="71" height="32">
	         <strong>评估月份</strong><s:hidden name="PeopleEvalUI.id" id="evaluationMarkId"/></td>
	      <td width="149"><s:property value="PeopleEvalUI.evaluationYear"/>-<s:property value="PeopleEvalUI.evaluationMonth"/></td>
	      <td width="42"><strong>员工</strong></td>
	      <td width="111"><s:property value="PeopleEvalUI.evaluatee.user_name"/></td>
	      <td width="38"><strong>职位</strong></td>
	      <td width="111"><s:property value="PeopleEvalUI.evaluatee.jobTitle"/></td>
	      <td width="68"><strong>评估人员数</strong></td>
	      <td width="312"><s:property value="PeopleEvalUI.numOfEvaluater"/>&nbsp;
	      				  <s:if test="#session.LOGIN_USER.containFunction(13) || #session.LOGIN_USER.roleType == 99">
	      				       <s:property value="PeopleEvalUI.evaluaters"/>
	      				  </s:if>
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
					<td><div align="center"><strong>平均得分</strong></div></td>
				  </tr>
				 <s:iterator value="PeopleEvalUI.peopleEvalItemMark_set" status="st" id="evalMark" >
				  <tr>
				 	<td rowspan="3"><div align="center"><strong><s:property value="#st.index+1"/>.<s:property value="#evalMark.evaluationItem.item_name"/></strong></div></td>
				    <td height="25"><s:property value="#evalMark.evaluationItem.item_desp_1"/></td>
				    <td colspan="3"><s:property value="#evalMark.evaluationItem.item_desp_2"/></td>
				    <td colspan="3"><s:property value="#evalMark.evaluationItem.item_desp_3"/></td>
				    <td colspan="3"><s:property value="#evalMark.evaluationItem.item_desp_4"/></td>
				    <td></td>
			       </tr>
				  <tr>
					<td height="25"><div align="center">0
					    <input type="radio" disabled="disabled" <s:if test="0 <= #evalMark.item_mark && #evalMark.item_mark <0.5">checked</s:if> value="0"/>
					</div></td>
					<td width="8%"><div align="center">1
					    <input type="radio" disabled="disabled" <s:if test="0.5 <= #evalMark.item_mark && #evalMark.item_mark <1.5">checked</s:if>  value="1"/>
					</div></td>
					<td width="8%"><div align="center">2
					    <input type="radio" disabled="disabled" <s:if test="1.5 <= #evalMark.item_mark && #evalMark.item_mark <2.5">checked</s:if>  value="2"/>
					</div></td>
					<td width="8%"><div align="center">3
					    <input type="radio" disabled="disabled" <s:if test="2.5 <= #evalMark.item_mark && #evalMark.item_mark <3.5">checked</s:if>  value="3"/>
					</div></td>
					<td width="8%"><div align="center">4
					    <input type="radio" disabled="disabled" <s:if test="3.5 <= #evalMark.item_mark && #evalMark.item_mark <4.5">checked</s:if>  value="4"/>
					</div></td>
					<td width="9%"><div align="center">5
					    <input type="radio" disabled="disabled" <s:if test="4.5 <= #evalMark.item_mark && #evalMark.item_mark <5.5">checked</s:if>  value="5"/>
					</div></td>
					<td width="8%"><div align="center">6
					    <input type="radio" disabled="disabled" <s:if test="5.5 <= #evalMark.item_mark && #evalMark.item_mark <6.5">checked</s:if>  value="6"/>
					</div></td>
					<td width="8%"><div align="center">7
					    <input type="radio" disabled="disabled" <s:if test="6.5 <= #evalMark.item_mark && #evalMark.item_mark <7.5">checked</s:if>  value="7"/>
					</div></td>
					<td width="7%"><div align="center">8
					    <input type="radio" disabled="disabled" <s:if test="7.5 <= #evalMark.item_mark && #evalMark.item_mark <8.5">checked</s:if>  value="8"/>
					</div></td>
					<td width="7%"><div align="center">9
					    			<input type="radio" disabled="disabled" <s:if test="8.5 <= #evalMark.item_mark && #evalMark.item_mark <=9">checked</s:if>  value="9"/>
					</div></td>
					<td><div align="center"><s:text name="format.evalMark">  
    					                       <s:param value="#evalMark.item_mark"/>  
					                        </s:text></div></td>
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
				    <td colspan="10"><s:property value="PeopleEvalUI.comment"/></td>
				    <td><div align="center"><strong><s:text name="format.evalMark">  
    					                       			<s:param value="PeopleEvalUI.mark_avg"/>  
					                       		    </s:text>
					                        </strong></div></td>
			       </tr>
			</table>		  </td>
        </tr>
	    <tr class="InnerTableContent">
	      <td height="4" colspan="9"><hr width="100%" color="#FFCC00"/>&nbsp;</td>
	    </tr>
	  </table>
   </td></tr>
 </table>
</body>
</html>