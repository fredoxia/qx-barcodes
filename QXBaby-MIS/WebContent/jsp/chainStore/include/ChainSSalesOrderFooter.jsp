<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags" %>
<!-- this Footer is used by the sales out order and sales exchange order for create/edit -->
				  	<table width="100%" border="0">
					       <tr class="PBAOuterTableTitale">
					         <td width="30"></td>
					         <td width="95"></td>
					         <td width="93"></td>
					         <td>优惠金额</td>
					         <td><s:textfield name="formBean.chainSalesOrder.discountAmount" id="discountAmount" size="14" onkeyup="changeDiscountCoupon();"/></td>
					         <td>代金券</td>
					         <td><s:textfield name="formBean.chainSalesOrder.coupon" id="coupon" size="14"  onkeyup="changeDiscountCoupon();"/></td>					         
					         <td width="95">应收+/应付-</td>
					         <td width="95"><input type="text" id="amountAfterDC" size="14" readonly value="<s:property value="formBean.chainSalesOrder.netAmount - formBean.chainSalesOrder.discountAmount - formBean.chainSalesOrder.coupon - formBean.chainSalesOrder.netAmountR"/>"/></td>
					         <td>&nbsp;</td>
					       </tr>				           
					       <tr class="PBAOuterTableTitale">
					         <td></td>
					         <td>预存金</td>
					         <td><s:textfield name="formBean.chainSalesOrder.chainPrepaidAmt" id="chainPrepaidAmt" size="14" disabled="true"  onkeyup="changeDiscountCoupon();" title="当vip使用预付金付款时"/></td>
							 <td width="70">微信金额</td>
					         <td width="93"><s:textfield name="formBean.chainSalesOrder.wechatAmount" id="wechatAmount" size="14" onkeyup="changeCashCardAmountValue();"/></td>
					         <td width="110">支付宝金额</td>
					         <td width="85"><s:textfield name="formBean.chainSalesOrder.alipayAmount" id="alipayAmount" size="14"  onkeyup="changeCashCardAmountValue();"/></td>
					         
					         <td></td>
					         <td></td>
					         <td>&nbsp;</td>
					       </tr>
						   <tr class="PBAOuterTableTitale">
					         <td> </td>
					         <td>积分抵现金(元)</td>
					         <td><s:textfield name="formBean.chainSalesOrder.vipScore" id="vipScore" size="14" onkeyup="changeDiscountCoupon();" disabled="true"/></td>
					         <td>刷卡</td>
					         <td><s:textfield name="formBean.chainSalesOrder.cardAmount" id="cardAmount" size="14"   onkeyup="changeCashCardAmountValue();"/></td>
					         <td>收现金+/付现金-</td>
					         <td><s:textfield name="formBean.chainSalesOrder.cashAmount" id="cashAmount" size="14"  onkeyup="changeCashCardAmountValue();"/></td>
					         <td>找零+/收零-</td>
					         <td colspan="2"><s:textfield name="formBean.chainSalesOrder.returnAmount" id="returnAmount" size="12" readonly="true" style="font-size:15px;color:red;font-weight:bold"/></td>

					       </tr>

					       <tr class="InnerTableContent">
					         <td>&nbsp;</td>
					         <td></td>
					         <td></td>
					         <td></td>
					         <td>
					           <s:if test="#session.LOGIN_CHAIN_USER.containFunction('chainSalesFunction!testPrint') && formBean.canPost">
					                <input id="printbt" type="button" value="测试打印" onclick="testPrint();" title="当模拟实际情况打印小票,但不会过账"/>
					           </s:if>
					         </td><td>
					           <input id="reCalculateBt" type="button" value="重新计算" onclick="calculateTotal();"/>
					         </td><td>
					           <s:if test="#session.LOGIN_CHAIN_USER.containFunction('chainSalesJSONAction!saveSalesToDraft') && formBean.canSaveDraft">
					                <input id="draftBt" type="button" value="保存草稿 F2" onclick="saveDraftSales();"/>
					           </s:if>
					         </td>
					         <td colspan = "3">
	                           <s:if test="(#session.LOGIN_CHAIN_USER.containFunction('chainSalesJSONAction!postSalesOrder')) && formBean.canPost">
	                               <input id="submitBt" type="button" value="单据过账 F3" onclick="postSalesOrder();"/>
	                           </s:if>

	                           <s:if test="#session.LOGIN_CHAIN_USER.containFunction('chainSalesJSPAction!cancelOrder') && formBean.canCancel">
					                <input type="button" value="红冲单据" onclick="cancelOrder();"/>
					           </s:if>
	                          <s:if test="#session.LOGIN_CHAIN_USER.containFunction('chainSalesJSPAction!deleteOrder') && formBean.canDelete">
					                <input type="button" value="删除单据" onclick="deleteOrder();"/>
					           </s:if>
					         </td>
			               </tr>
			               <tr class="PBAOuterTableTitale">
					         <td></td>
					         <td>备注</td>
					         <td colspan="8"><s:textfield name="formBean.chainSalesOrder.memo" id="orderMemo" size="100"/></td>
				           </tr>
					  </table>