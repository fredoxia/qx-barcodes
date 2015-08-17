<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib prefix="s" uri="/struts-tags" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>人力资源绩效评估管理</title>
<%@ include file="../../common/Style.jsp"%>
<script>
var numOfItems = "<s:property value="peopleEvaluation.evaluation.evaluationItem_set.size"/>";

function validateForm(){
	var errors = "";
	var allValid = true;          //for the radio items
	for(var i=0;i<numOfItems;i++){
        var valid = false;
        var tagName = "peopleEvalMark.peopleEvalItemMark_list["+i+"].item_mark";
        var chkObjs = document.getElementsByName(tagName);
        for(var j=0;j<chkObjs.length;j++){
            if(chkObjs[j].checked){
                valid = true;
                break;
            }
        }
        
        if (valid == false){
        	var k = i+1;
        	allValid = false;
        	errors += k + ",";
        }            
	}

    if (allValid==false){
        alert("每个项目都都是必选项，某些项目还未填，请检查第" + errors + "项目");	   
	    return false;
    }

	var comment = $("#totalComment").val();

    if (comment == ""){
    	alert("经理必须填写,员工在当月的总体评价");	
    	return false;
    } 

    return true;
}
$(document).ready(function(){
	parent.$.messager.progress('close'); 
});

</script>
</head>
<body>

   <s:form id="hrEvalForm" action="/action/hrEvalJSP!CreatePeopleEval" method="POST" theme="simple" onsubmit="return validateForm();">
    <s:hidden name="peopleEvaluation.evaluationYear"/>
    <s:hidden name="peopleEvaluation.evaluationMonth"/>
    <s:hidden name="peopleEvaluation.evaluatee.user_id"/>
    <s:hidden name="peopleEvaluation.evaluation.id"/>
    <table width="90%" align="center"  class="OuterTable">
    <tr><td>

	 <table width="100%" border="0">
	    <tr class="PBAOuterTableTitale">
	       <td height="78" colspan="7">人力资源 - 新建绩效评估<br />
	         <br />
           - 管理人员通过此页面对员工的绩效进行评估</td>
	    </tr>
	    <tr class="InnerTableContent">
	      <td width="79" height="32">
	         <strong>评估月份</strong></td>
	      <td width="107"><s:property value="peopleEvaluation.evaluationYear"/>-<s:property value="peopleEvaluation.evaluationMonth"/></td>
	      <td width="38"><strong>员工</strong></td>
	      <td width="135"><s:property value="peopleEvaluation.evaluatee.user_name"/></td>
	      <td width="44"><strong>职位</strong></td>
	      <td width="220"><s:property value="peopleEvaluation.evaluatee.jobTitle"/></td>
	      <td width="447">&nbsp;</td>
	    </tr>
	    <tr class="InnerTableContent">
	      <td height="4" colspan="7"><hr width="100%" color="#FFCC00"/></td>
	    </tr>
	    <tr class="InnerTableContent">
	      <td height="32" colspan="7">
		    <table width="100%" border="1" cellpadding="0" cellspacing="0" bordercolor="#666666">
				  <tr>
					<td width="10%" height="25"><div align="center">&nbsp;</div></td>
					<td width="14%"><div align="center"><strong>不符合目标</strong></div></td>
					<td colspan="3"><div align="center"><strong>低</strong></div></td>
					<td colspan="3"><div align="center"><strong>达标</strong></div></td>
					<td colspan="3"><div align="center"><strong>高</strong></div></td>
				  </tr>
				 <s:iterator value="peopleEvaluation.evaluation.evaluationItem_set" status="st" id="evalSet" >
				  <tr>
				 	<td rowspan="3"><div align="center"><strong><s:property value="#st.index+1"/>.<s:property value="#evalSet.item_name"/></strong></div></td>
				    <td height="25"><s:property value="#evalSet.item_desp_1"/></td>
				    <td colspan="3"><s:property value="#evalSet.item_desp_2"/></td>
				    <td colspan="3"><s:property value="#evalSet.item_desp_3"/></td>
				    <td colspan="3"><s:property value="#evalSet.item_desp_4"/></td>
			       </tr>
				  <tr>
					<td height="25"><div align="center">0
					    <input type="hidden" name="peopleEvalMark.peopleEvalItemMark_list[<s:property value="#st.index"/>].evaluationItem.id" value="<s:property value="#evalSet.id"/>"/>
					    <input type="radio" name="peopleEvalMark.peopleEvalItemMark_list[<s:property value="#st.index"/>].item_mark" value=""/>
					</div></td>
					<td width="8%"><div align="center">1
					    <input type="radio" name="peopleEvalMark.peopleEvalItemMark_list[<s:property value="#st.index"/>].item_mark" value="1"/>
					</div></td>
					<td width="8%"><div align="center">2
					    <input type="radio" name="peopleEvalMark.peopleEvalItemMark_list[<s:property value="#st.index"/>].item_mark" value="2"/>
					</div></td>
					<td width="8%"><div align="center">3
					    <input type="radio" name="peopleEvalMark.peopleEvalItemMark_list[<s:property value="#st.index"/>].item_mark" value="3"/>
					</div></td>
					<td width="8%"><div align="center">4
					    <input type="radio" name="peopleEvalMark.peopleEvalItemMark_list[<s:property value="#st.index"/>].item_mark" value="4"/>
					</div></td>
					<td width="9%"><div align="center">5
					    <input type="radio" name="peopleEvalMark.peopleEvalItemMark_list[<s:property value="#st.index"/>].item_mark" value="5"/>
					</div></td>
					<td width="8%"><div align="center">6
					    <input type="radio" name="peopleEvalMark.peopleEvalItemMark_list[<s:property value="#st.index"/>].item_mark" value="6"/>
					</div></td>
					<td width="8%"><div align="center">7
					    <input type="radio" name="peopleEvalMark.peopleEvalItemMark_list[<s:property value="#st.index"/>].item_mark" value="7"/>
					</div></td>
					<td width="7%"><div align="center">8
					    <input type="radio" name="peopleEvalMark.peopleEvalItemMark_list[<s:property value="#st.index"/>].item_mark" value="8"/>
					</div></td>
					<td width="7%"><div align="center">9
					    <input type="radio" name="peopleEvalMark.peopleEvalItemMark_list[<s:property value="#st.index"/>].item_mark" value="9"/>
					</div></td>
				  </tr>

			       <tr>
				    <td height="50">对于此点评价：</td>
				    <td colspan="9"><textarea name="peopleEvalMark.peopleEvalItemMark_list[<s:property value="#st.index"/>].comment" cols="95" rows="3" style="OVERFLOW:auto;"></textarea> </td>
			       </tr>
			       <tr>
				    <td colspan="11">&nbsp;</td>
			       </tr>
				</s:iterator>
				<tr>
				    <td height="50"><div align="center"><strong>总体评价：</strong></div></td>
				    <td colspan="10"><textarea id="totalComment" name="peopleEvalMark.comment" cols="105" rows="3" style="OVERFLOW:auto;"></textarea> </td>
			       </tr>
			</table>
		  </td>
        </tr>
	    <tr class="InnerTableContent">
	      <td height="4" colspan="7"><hr width="100%" color="#FFCC00"/>&nbsp;</td>
	    </tr>
	    <tr class="InnerTableContent">
	      <td height="30" colspan="7"><input type="submit" value="完成评估"/></td>
	    </tr>
	  </table>
   </td></tr>
 </table>
</s:form>
</body>
</html>