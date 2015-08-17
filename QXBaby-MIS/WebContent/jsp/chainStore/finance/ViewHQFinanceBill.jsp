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

</script>
</head>
<body>
    <table width="90%" align="center"  class="OuterTable">
	    <tr><td>
		 <table width="100%" border="0">
		    <tr>
		      <td colspan="7">
		      		    <div class="errorAndmes"><s:actionerror cssStyle="color:red"/><s:actionmessage cssStyle="color:blue"/></div>
						<table width="100%"  align="left" border="0" id="org_table">
							<tr class="PBAOuterTableTitale">
	                            <td height="50" colspan="3"> 财务单据  
	                            </td>
		    				</tr>
						   <tr class="InnerTableContent">
						     <td width="180" height="35">单据种类 ： <s:property value="uiBean.financeBill.typeChainS"/></td>
						     <td width="220">连锁店 ： <s:property value="uiBean.financeBill.chainStore.chain_name"/> </td>
						     <td width="220">过账日期:<s:property value="uiBean.financeBill.createDate"/></td>
					       </tr>
						   <tr class="InnerTableContent">	      
							 <td height="25">经手人:<s:property value="uiBean.financeBill.creatorHq.user_name"/></td>
							 <td colspan="2">备注 ： <s:property value="uiBean.financeBill.comment"/></td>
					      </tr>
					      <tr class="InnerTableContent">	      
							 <td height="25">折扣:<s:property value="uiBean.financeBill.invoiceDiscount"/></td>      
							 <td>上欠:<s:property value="uiBean.financeBill.preAcctAmt"/></td>
							 <td> 下欠 ： <s:property value="uiBean.financeBill.postAcctAmt"/></td>

					      </tr>
				       </table>
			      </td>
			    </tr>
			    
			    <tr class="InnerTableContent">
			      <td height="4" colspan="7"><hr width="100%" color="#FFCC00"/></td>
			    </tr>
				<tr>
				  <td><table width="100%"  align="left" class="OuterTable" id="org_table">
	                  <tr class="PBAInnerTableTitale" align='left'>
	                    <th width="60" height="35">序号</th>
	                    <th width="110">账户名称</th>
	                    <th width="120">金额</th>
	                    <th width="199">说明</th>
	                  </tr>
	                  <s:iterator value="uiBean.financeBill.financeBillItemList" status="st" id="billItem" >
	                    <tr class="InnerTableContent" <s:if test="#st.even"> style='<%=Common_util.EVEN_ROW_BG_STYLE %>'</s:if>>
	                      <td height="25"><s:property value="#st.index + 1"/></td>
	                      <td><s:property value="#billItem.financeCategory.itemName"/></td>
	                      <td><s:property value="#billItem.total"/></td>
	                      <td><s:property value="#billItem.comment"/></td>
	                    </tr>
	                  </s:iterator>
	                  <tr align='left' class="PBAInnerTableTitale">
	                    <td height="35" colspan="2">总计</td>
	                    <td><s:property value="uiBean.financeBill.invoiceTotal"/></td>
	                    <td></td>
	                  </tr>
	                </table></td>
				</tr>
			  </table>
		   </td></tr>
	   		    <tr class="InnerTableContent">
		      <td height="4" colspan="7"><hr width="100%" color="#FFCC00"/></td>
		    </tr>
	 </table>

</body>
</html>