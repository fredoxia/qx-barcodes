<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script>
var layout_west_tree;

$(function() {
		layout_west_tree = $('#treeMenu').tree({
			onClick : function(node) {
				if (node.attributes && node.attributes.url) {
					var url;
					url = node.attributes.url;
					parent.$.messager.progress({
								title : '提示',
								text : '数据处理中，请稍后....'
							});

     				addTab4({
						url : url,
						title : node.text,
						iconCls : node.iconCls
					});

				}
			},
			onLoadSuccess : function(node, data) {
				parent.$.messager.progress('close');
			}
		});
	}); 
</script>
<div id="menuAccordian" class="easyui-accordion" style="width:auto;height:auto;">  
    <div title="系统菜单" data-options="selected:true" style="overflow:false">
        <ul id="treeMenu" class="easyui-tree"  lines="true" >  
		      <s:if test="#session.LOGIN_USER.containFunction('basicData!preMaintainBasic') || #session.LOGIN_USER.containFunction('productJSPAction!preCreateProduct') || #session.LOGIN_USER.containFunction('productJSPAction!preSearch')">
			  	<li data-options="iconCls:'icon-text_1',state:'open',border:false">
			  	    <span>条型码管理</span>
			  		<ul>
			  			<s:if test="#session.LOGIN_USER.containFunction('basicData!preMaintainBasic')"><li data-options="iconCls:'icon-text_1',attributes:{url:'basicData!preMaintainBasic'}">条型码基础资料管理</li></s:if>
			    		<s:if test="#session.LOGIN_USER.containFunction('productJSPAction!preCreateProduct')"><li data-options="iconCls:'icon-text_1',attributes:{url:'productJSPAction!preCreateProduct'}">新建条型码</li></s:if>
						<s:if test="#session.LOGIN_USER.containFunction('productJSPAction!preSearch')"><li data-options="iconCls:'icon-text_1',attributes:{url:'productJSPAction!preSearch'}">查询修改条型码</li></s:if>
			    	</ul>  
			    </li>
			    </s:if>
			    
			    <s:if test="#session.LOGIN_USER.containFunction('userJSP!preEdit') || #session.LOGIN_USER.containFunction('hrEvalJSP!preEvaluationConf') || #session.LOGIN_USER.containFunction('hrEvalJSP!preEvaluationMgmtNew') || #session.LOGIN_USER.containFunction('hrEvalJSP!preEvaluationMgmtSearch') || #session.LOGIN_USER.containFunction('hrEvalJSP!preEvaluationSummaryMgmtSearch')">
			    <li data-options="iconCls:'icon-group',state:'closed',border:false">
			        <span>人力资源管理</span>
				  	<ul>
			    		<s:if test="#session.LOGIN_USER.containFunction('userJSP!preEdit')"><li data-options="iconCls:'icon-group',attributes:{url:'userJSP!preEdit'}">员工信息管理</li></s:if>
			    		<s:if test="#session.LOGIN_USER.containFunction('hrEvalJSP!preEvaluationConf')"><li data-options="iconCls:'icon-group',attributes:{url:'hrEvalJSP!preEvaluationConf'}">绩效考核管理配置</li></s:if>
			    		<s:if test="#session.LOGIN_USER.containFunction('hrEvalJSP!preEvaluationMgmtNew')"><li data-options="iconCls:'icon-group',attributes:{url:'hrEvalJSP!preEvaluationMgmtNew'}">新建员工绩效考核</li></s:if>
			    		<s:if test="#session.LOGIN_USER.containFunction('hrEvalJSP!preEvaluationMgmtSearch') || #session.LOGIN_USER.containFunction(14)"><li data-options="iconCls:'icon-group',attributes:{url:'hrEvalJSP!preEvaluationMgmtSearch'}">查询员工绩效考核</li></s:if>
			    		<s:if test="#session.LOGIN_USER.containFunction('hrEvalJSP!preEvaluationSummaryMgmtSearch')"><li data-options="iconCls:'icon-group',attributes:{url:'hrEvalJSP!preEvaluationSummaryMgmtSearch'}">查询员工考核月报表</li></s:if>
					</ul>
			 	</li>
			 	</s:if>
			 	
			 	<s:if test="#session.LOGIN_USER.containFunction('inventoryOrder!create') || #session.LOGIN_USER.containFunction('inventoryOrder!createReturnOrder') || #session.LOGIN_USER.containFunction('inventoryOrder!search') || #session.LOGIN_USER.containFunction('inventoryOrder!preSearch')">
			  	<li  data-options="iconCls:'icon-images',state:'open',border:false">
			  	    <span>单据管理</span>
			  		<ul style="width: 150%">
			    		<s:if test="#session.LOGIN_USER.containFunction('inventoryOrder!create')"><li data-options="iconCls:'icon-images',attributes:{url:'inventoryOrder!create'}">销售单据录入 </li></s:if>
			    		<s:if test="#session.LOGIN_USER.containFunction('inventoryOrder!createReturnOrder')"><li data-options="iconCls:'icon-images',attributes:{url:'inventoryOrder!createReturnOrder'}">退货单据录入 </li></s:if>
			    		<s:if test="#session.LOGIN_USER.containFunction('inventoryOrder!preSearch')"><li data-options="iconCls:'icon-images',attributes:{url:'inventoryOrder!preSearch?formBean.order.order_Status=<%=InventoryOrder.STATUS_PDA_COMPLETE%>'}">PDA完成单据</li></s:if>
			    		<s:if test="#session.LOGIN_USER.containFunction('inventoryOrder!preSearch')"><li data-options="iconCls:'icon-images',attributes:{url:'inventoryOrder!preSearch?formBean.order.order_Status=<%=InventoryOrder.STATUS_DRAFT%>'}">仓库草稿单据</li></s:if>
			    		<s:if test="#session.LOGIN_USER.containFunction('inventoryOrder!preSearch')"><li data-options="iconCls:'icon-images',attributes:{url:'inventoryOrder!preSearch?formBean.order.order_Status=<%=InventoryOrder.STATUS_COMPLETE%>'}">仓库完成单据</li></s:if>
			    		<s:if test="#session.LOGIN_USER.containFunction('inventoryOrder!preSearch')"><li data-options="iconCls:'icon-images',attributes:{url:'inventoryOrder!preSearch?formBean.order.order_Status=<%=InventoryOrder.STATUS_ACCOUNT_PROCESS%>'}">会计录入中单据</li></s:if>
			    		<s:if test="#session.LOGIN_USER.containFunction('inventoryOrder!preSearch')"><li data-options="iconCls:'icon-images',attributes:{url:'inventoryOrder!preSearch?formBean.order.order_Status=<%=InventoryOrder.STATUS_WAITING_AUDIT%>'}">待审核的单据</li></s:if>
			    		<s:if test="#session.LOGIN_USER.containFunction('inventoryOrder!preSearch')"><li data-options="iconCls:'icon-images',attributes:{url:'inventoryOrder!preSearch'}">搜索单据 </li></s:if>
			    	</ul>
			    </li>
			    </s:if>
			    
			    <s:if test="#session.LOGIN_USER.containFunction('chainSales!preSalesReport') || #session.LOGIN_USER.containFunction('financeHQJSP!preCreateFHQ') || #session.LOGIN_USER.containFunction('financeHQJSP!preSearchFHQ')">
			  	<li  data-options="iconCls:'icon-money_yen',state:'open',border:false">
			  	    <span>业务管理</span>
			  		<ul style="width: 150%">
			    		<s:if test="#session.LOGIN_USER.containFunction('chainSales!preSalesReport')"><li data-options="iconCls:'icon-money_yen',attributes:{url:'chainSales!preSalesReport'}">销售报表 </li></s:if>
			    		<s:if test="#session.LOGIN_USER.containFunction('financeHQJSP!preCreateFHQ')"><li data-options="iconCls:'icon-money_yen',attributes:{url:'financeHQJSP!preCreateFHQ'}">创建财务单据</li></s:if>
			    		<s:if test="#session.LOGIN_USER.containFunction('financeHQJSP!preSearchFHQ')"><li data-options="iconCls:'icon-money_yen',attributes:{url:'financeHQJSP!preSearchFHQ'}">查询财务单据 </li></s:if>
			    		<s:if test="#session.LOGIN_USER.containFunction('financeHQJSP!preSearchAcctFlow')"><li data-options="iconCls:'icon-money_yen',attributes:{url:'financeHQJSP!preSearchAcctFlow'}">查询连锁店往来账户</li></s:if>
			    	</ul>
			    </li>
			    </s:if>

			    <li  data-options="iconCls:'icon-status_online',state:'open',border:false">
			    	<span>我的系统</span>
				  	<ul style="width: 150%">
			    		<li data-options="iconCls:'icon-status_online',attributes:{url:'hrEvalJSP!preEvaluationSummaryPersonalSearch'}">我的考核月报表</A></li>
			    		<li data-options="iconCls:'icon-status_online',attributes:{url:'userJSP!preUpdateMyAcct'}">我的账户</A></li>
					</ul>
			 	</li>
				
				<s:if test="#session.LOGIN_USER.containFunction('userJSP!preEditFunctionality') || #session.LOGIN_USER.containFunction('userJSP!retrieveLog') || #session.LOGIN_USER.containFunction('userJSP!swithToChain') || #session.LOGIN_USER.roleType == 99">
				<li data-options="iconCls:'icon-cog',state:'closed',border:false">
				<span>管理员</span>
				  		<ul style="width: 150%">
				    		<s:if test="#session.LOGIN_USER.containFunction('userJSP!preEditFunctionality') || #session.LOGIN_USER.roleType == 99"><li data-options="iconCls:'icon-cog',attributes:{url:'userJSP!preEditFunctionality'}">总部权限管理</li></s:if>
				    		<s:if test="#session.LOGIN_USER.containFunction('chainSMgmtJSP!preEditChainPriceIncre')"><li data-options="iconCls:'icon-cog',attributes:{url:'chainSMgmtJSP!preEditChainPriceIncre'}">连锁店价格涨幅管理</li></s:if>
				    		<s:if test="#session.LOGIN_USER.containFunction('chainSMgmtJSP!preEditChainInfor')"><li data-options="iconCls:'icon-cog',attributes:{url:'chainSMgmtJSP!preEditChainInfor'}">连锁店管理</li></s:if>
				    		<s:if test="#session.LOGIN_USER.containFunction('chainSMgmtJSP!preCreateInitialStock')"><li data-options="iconCls:'icon-cog',attributes:{url:'chainSMgmtJSP!preCreateInitialStock'}">连锁店期初库存</li></s:if>
				    		<s:if test="#session.LOGIN_USER.containFunction('chainSMgmtJSP!preEditChainGroup')"><li data-options="iconCls:'icon-cog',attributes:{url:'chainSMgmtJSP!preEditChainGroup'}">连锁店关联管理</li></s:if>
				    		<s:if test="#session.LOGIN_USER.containFunction('userJSP!retrieveLog') || #session.LOGIN_USER.roleType == 99"><li data-options="iconCls:'icon-cog',attributes:{url:'userJSP!retrieveLog'}">系统运行日志</li></s:if>
				    	</ul>
				 </li>
		        </s:if>
		</ul>

	</div>  
    <div title="其他信息" style="padding:10px;">  
        	暂时无其他信息
    </div>  
</div>
