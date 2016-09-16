<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags" %>
<!-- this Footer is used by the sales out order and sales exchange order for view-->

				  	<table width="100%" border="0">
					       <tr class="InnerTableContent">
					         <td height="20"></td>
					         <td><strong>备注</strong></td>
					         <td colspan="11"><s:property value="uiBean.chainSalesOrder.memo"/></td>
					         <td>&nbsp;</td>
				           </tr>
					       <tr class="InnerTableContent">
					         <td width="30" height="25"><br/>           </td>
					         <td width="98"><strong>预存金</strong></td>
					         <td width="88"><s:property value="uiBean.chainSalesOrder.chainPrepaidAmt"/></td>
					         <td width="90"><strong>积分换现金</strong></td>
					         <td width="93"><s:property value="uiBean.chainSalesOrder.vipScore"/></td>					    
					         <td width="90"><strong>优惠金额</strong></td>
					         <td width="93"><s:property value="uiBean.chainSalesOrder.discountAmount"/></td>
					         <td width="60"><strong>代金券</strong></td>
					         <td width="84"><s:property value="uiBean.chainSalesOrder.coupon"/></td>
					         <td width="44"><strong>应收</strong></td>
					         <td width="86"><s:text name="format.price"><s:param value="uiBean.chainSalesOrder.netAmount - uiBean.chainSalesOrder.discountAmount - uiBean.chainSalesOrder.coupon -uiBean.chainSalesOrder.netAmountR -uiBean.chainSalesOrder.chainPrepaidAmt -uiBean.chainSalesOrder.vipScore"/></s:text></td>
					         <td width="40">&nbsp;</td>
					         <td width="290">&nbsp;</td>
					         <td>&nbsp;</td>
					       </tr>
						   <tr class="InnerTableContent">
					         <td height="25"><br/></td>
					         <td><strong>微信金额</strong></td>
					         <td><s:property value="uiBean.chainSalesOrder.wechatAmount"/></td>
					         <td><strong>支付宝金额</strong></td>
					         <td><s:property value="uiBean.chainSalesOrder.alipayAmount"/></td>
					         <td><strong>刷卡</strong></td>
					         <td><s:property value="uiBean.chainSalesOrder.cardAmount"/></td>
					         <td><strong>现金</strong></td>
					         <td><s:property value="uiBean.chainSalesOrder.cashAmount"/></td>
					         <td><strong>找零</strong></td>
					         <td><s:property value="uiBean.chainSalesOrder.returnAmount"/></td>
					         <td>&nbsp;</td>
					         <td>&nbsp;</td>
					         <td>&nbsp;</td>
					       </tr>
					       <tr class="InnerTableContent">
					         <td colspan="13"><hr width="100%" color="#FFCC00"/></td>
				           </tr>
					       <tr class="InnerTableContent">
					         <td height="25">&nbsp;</td>
					         <td colspan="8"></td>
					         <td colspan="5">
					           <s:if test="#session.LOGIN_CHAIN_USER.containFunction('chainSalesJSPAction!cancelOrder') && formBean.canCancel">
					                <input type="button" value="红冲单据" onclick="cancelOrder();"/>
					           </s:if>
					           <s:if test="#session.LOGIN_CHAIN_USER.containFunction('chainSalesJSPAction!copyOrder') && formBean.canCopy">
					                <input type="button" value="复制单据" onclick="copyOrder();"/>
					           </s:if>
					            
					           <s:if test="#session.LOGIN_CHAIN_USER.containFunction('chainSalesJSONAction!getSalesOrderById')">
					                <input type="button" value="重打小票" onclick="printOrder();"/>
					           </s:if>
	                         </td>
			               </tr>
					  </table>