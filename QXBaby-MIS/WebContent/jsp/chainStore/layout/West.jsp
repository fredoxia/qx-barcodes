<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script>
var layout_west_tree;
var layout_west_tree2;
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

     				addTab({
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
		layout_west_tree2 = $('#treeMenu2').tree({
			onClick : function(node) {
				if (node.attributes && node.attributes.url) {
					var url;
					url = node.attributes.url;
					parent.$.messager.progress({
								title : '提示',
								text : '数据处理中，请稍后....'
							});

     				addTab({
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
<div id="menuAccordian" class="easyui-accordion" style="fit:true,border:false">  
    <div title="系统菜单" data-options="selected:true" style="padding:10px;">
        <ul id="treeMenu" class="easyui-tree" lines="true">  
           <s:if test="#session.LOGIN_CHAIN_USER.containFunction('chainSalesJSPAction!preNewSalesOrder') ||#session.LOGIN_CHAIN_USER.containFunction('chainSalesJSPAction!preSearchDraftSalesOrder') ||#session.LOGIN_CHAIN_USER.containFunction('chainSalesJSPAction!preSearchSalesOrder')">
        	  	 <li data-options="iconCls:'icon-money_yen',state:'open',border:false">  
		            <span>销售管理</span>  
	        		<ul>
	        			 <s:if test="#session.LOGIN_CHAIN_USER.containFunction('chainSalesJSPAction!preNewSalesOrder')">
	        			 	<li data-options="iconCls:'icon-money_yen',attributes:{url:'chainSalesJSPAction!preNewSalesOrder'}">新建零售单</li>
	        			 </s:if>
	        			 <s:if test="#session.LOGIN_CHAIN_USER.containFunction('chainSalesJSPAction!preSearchDraftSalesOrder')">
	        			 	<li data-options="iconCls:'icon-money_yen',attributes:{url:'chainSalesJSPAction!preSearchDraftSalesOrder'}">草稿零售单</li>
	        			 </s:if>
	        			 <s:if test="#session.LOGIN_CHAIN_USER.containFunction('chainSalesJSPAction!preSearchSalesOrder')">
	        			 	<li data-options="iconCls:'icon-money_yen',attributes:{url:'chainSalesJSPAction!preSearchSalesOrder'}">搜索零售单据</li>
	        			 </s:if>
	        			 <li data-options="iconCls:'icon-money_yen',attributes:{url:'chainSalesJSPAction!preGetProduct'}">商品信息查询</li>
	        		</ul> 
		        </li>
		    </s:if>
		      
        	<s:if test="#session.LOGIN_CHAIN_USER.containFunction('purchaseAction!preSearch') || #session.LOGIN_CHAIN_USER.containFunction('financeChainJSP!preSearchFHQ') || #session.LOGIN_CHAIN_USER.containFunction('financeChainJSP!preSearchAcctFlow')">
        	    <li data-options="iconCls:'icon-package',state:'closed',border:false">
					<span>采购管理</span>
					<ul>
						<s:if test="#session.LOGIN_CHAIN_USER.containFunction('purchaseAction!preSearch')">
							<li data-options="iconCls:'icon-package',attributes:{url:'purchaseAction!preSearch'}">采购单据</li>
						</s:if>
						<s:if test="#session.LOGIN_CHAIN_USER.containFunction('financeChainJSP!preSearchFHQ')">
							<li data-options="iconCls:'icon-package',attributes:{url:'financeChainJSP!preSearchFHQ'}">财务单据</li>
						</s:if>
						<s:if test="#session.LOGIN_CHAIN_USER.containFunction('financeChainJSP!preSearchAcctFlow')">
							<li data-options="iconCls:'icon-package',attributes:{url:'financeChainJSP!preSearchAcctFlow'}">往来账目查询</li>
						</s:if>
					</ul>
				  </li>
			  </s:if>
				
			  <li data-options="iconCls:'icon-database',state:'closed'">
				    <span>库存管理</span>
					<ul>
						<s:if test="#session.LOGIN_CHAIN_USER.containFunction('inventoryFlowJSPAction!preCreateOverflowOrder')">
							<li data-options="iconCls:'icon-database',attributes:{url:'inventoryFlowJSPAction!preCreateOverflowOrder'}">报溢单</li>
						</s:if>
						<s:if test="#session.LOGIN_CHAIN_USER.containFunction('inventoryFlowJSPAction!preCreateflowLossOrder')">
							<li data-options="iconCls:'icon-database',attributes:{url:'inventoryFlowJSPAction!preCreateflowLossOrder'}">报损单</li>
						</s:if>
						<s:if test="#session.LOGIN_CHAIN_USER.containFunction('inventoryFlowJSPAction!preCreateInventoryTransferOrder')">
							<li data-options="iconCls:'icon-database',attributes:{url:'inventoryFlowJSPAction!preCreateInventoryTransferOrder'}">调货单</li>
						</s:if>			

							<li data-options="iconCls:'icon-database',attributes:{url:'inventoryFlowJSPAction!checkInvenTrace'}">商品库存跟踪</li>
							<li data-options="iconCls:'icon-database',attributes:{url:'inventoryFlowJSPAction!preCheckChainInven'}">连锁店商品库存查询</li>
						<s:if test="#session.LOGIN_CHAIN_USER.containFunction('inventoryFlowJSPAction!preCreateInventoryOrder')">
							<li data-options="iconCls:'icon-database',attributes:{url:'inventoryFlowJSPAction!preCreateInventoryOrder'}">库存单</li>
						</s:if>
						<s:if test="#session.LOGIN_CHAIN_USER.containFunction('inventoryFlowJSPAction!preSearchInvenFlowOrder')">
							<li data-options="iconCls:'icon-database',attributes:{url:'inventoryFlowJSPAction!preSearchInvenFlowOrder'}">搜索库存类单据</li>
						</s:if>
					</ul>
				  </li>

				
			     <s:if test="#session.LOGIN_CHAIN_USER.containFunction('chainReportJSPAction!preSalesReport') || #session.LOGIN_CHAIN_USER.containFunction('chainReportJSPAction!prePurchaseReport') || #session.LOGIN_CHAIN_USER.containFunction('chainReportJSPAction!preFinanceReport')|| #session.LOGIN_CHAIN_USER.containFunction('inventoryFlowJSPAction!preGetCurrentInventory') || #session.LOGIN_CHAIN_USER.containFunction('chainReportJSPAction!preSalesStatisticReport') || #session.LOGIN_CHAIN_USER.containFunction('chainReportJSPAction!prePurchaseStatisticReport')">
				  <li data-options="iconCls:'icon-chart_bar',state:'open'">
					<span>报表功能</span>
					<ul>
						<s:if test="#session.LOGIN_CHAIN_USER.containFunction('chainReportJSPAction!preSalesReport')">
							<li data-options="iconCls:'icon-chart_bar',attributes:{url:'chainReportJSPAction!preSalesReport'}">销售报表</li>
						</s:if>
						<s:if test="#session.LOGIN_CHAIN_USER.containFunction('chainReportJSPAction!preSalesReportBySaler')">
							<li data-options="iconCls:'icon-chart_bar',attributes:{url:'chainReportJSPAction!preSalesReportBySaler'}">员工销售报表</li>
						</s:if>						
						<s:if test="#session.LOGIN_CHAIN_USER.containFunction('chainReportJSPAction!prePurchaseReport')">
							<li data-options="iconCls:'icon-chart_bar',attributes:{url:'chainReportJSPAction!prePurchaseReport'}">采购报表</li>
						</s:if>
						<s:if test="#session.LOGIN_CHAIN_USER.containFunction('chainReportJSPAction!preFinanceReport')">
							<li data-options="iconCls:'icon-chart_bar',attributes:{url:'chainReportJSPAction!preFinanceReport'}">财务报表</li>
						</s:if>
						<s:if test="#session.LOGIN_CHAIN_USER.containFunction('inventoryFlowJSPAction!preGetCurrentInventory')">
							<li data-options="iconCls:'icon-chart_bar',attributes:{url:'inventoryFlowJSPAction!preGetCurrentInventory'}">库存状况统计</li>
						</s:if>
						<s:if test="#session.LOGIN_CHAIN_USER.containFunction('chainReportJSPAction!preSalesStatisticReport')">
							<li data-options="iconCls:'icon-chart_bar',attributes:{url:'chainReportJSPAction!preSalesStatisticReport'}">商品销售统计</li>
						</s:if>
						<s:if test="#session.LOGIN_CHAIN_USER.containFunction('chainReportJSPAction!prePurchaseStatisticReport')">
							<li data-options="iconCls:'icon-chart_bar',attributes:{url:'chainReportJSPAction!prePurchaseStatisticReport'}">商品采购统计</li>
						</s:if>
						<s:if test="#session.LOGIN_CHAIN_USER.containFunction('chainReportJSPAction!preAllInOneReport')">
							<li data-options="iconCls:'icon-chart_bar',attributes:{url:'chainReportJSPAction!preAllInOneReport'}">综合统计报表</li>
						</s:if>						
					</ul>
				  </li>
				</s:if>

				<li data-options="iconCls:'icon-medal_gold_2',state:'open'">
				    <span>VIP 管理</span>
					<ul>
						<s:if test="#session.LOGIN_CHAIN_USER.containFunction('chainVIPJSPAction!viewVIPTypes')">
							<li data-options="iconCls:'icon-medal_gold_2',attributes:{url:'chainVIPJSPAction!viewVIPTypes'}">卡类型管理</li>
						</s:if>
						<s:if test="#session.LOGIN_CHAIN_USER.containFunction('chainVIPJSPAction!viewAllVIPCards')">
							<li data-options="iconCls:'icon-medal_gold_2',attributes:{url:'chainVIPJSPAction!viewAllVIPCards'}">VIP 卡管理</li>
						</s:if>
						<li data-options="iconCls:'icon-medal_gold_2',attributes:{url:'chainReportJSPAction!preVIPConsumpReport'}">VIP 消费报表</li>
						<li data-options="iconCls:'icon-medal_gold_2',attributes:{url:'chainVIPJSPAction!preSearchSpecialVIPs'}">VIP 生日查找</li>
						<li data-options="iconCls:'icon-medal_gold_2',attributes:{url:'chainVIPJSPAction!preDepositVIPPrepaid'}">VIP 预存金充值</li>
						<li data-options="iconCls:'icon-medal_gold_2',attributes:{url:'chainVIPJSPAction!preSearchVIPPrepaid'}">VIP 预存金查询</li>
						<s:if test="#session.LOGIN_CHAIN_USER.containFunction('chainVIPJSPAction!preUploadVIPs')">
							<li data-options="iconCls:'icon-medal_gold_2',attributes:{url:'chainVIPJSPAction!preUploadVIPs'}">批量上传VIP卡</li>
						</s:if>
					</ul>
				 </li>

				<li data-options="iconCls:'icon-connect',state:'closed'">
					<span>连锁店管理</span>
					<ul>
						<li data-options="iconCls:'icon-connect',attributes:{url:'chainUserJSP!preEditMyAcct'}">我的账户</li>
						<s:if test="#session.LOGIN_CHAIN_USER.containFunction('chainMgmtJSP!preEditChainInfor')">
							<li data-options="iconCls:'icon-connect',attributes:{url:'chainMgmtJSP!preEditChainInfor'}">连锁店管理</li>
						</s:if>
						<s:if test="#session.LOGIN_CHAIN_USER.containFunction('chainMgmtJSP!preEditChainConf')">
							<li data-options="iconCls:'icon-connect',attributes:{url:'chainMgmtJSP!preEditChainConf'}">连锁店配置</li>
						</s:if>
						<s:if test="#session.LOGIN_CHAIN_USER.containFunction('chainUserJSP!getChainUsers')">
							<li data-options="iconCls:'icon-connect',attributes:{url:'chainUserJSP!getChainUsers'}">职员信息管理</li>
						</s:if>
						<s:if test="#session.LOGIN_CHAIN_USER.myChainStore.allowChangeSalesPrice == 1 && #session.LOGIN_CHAIN_USER.containFunction('chainMgmtJSP!preEditChainProductPrice')">
							<li data-options="iconCls:'icon-connect',attributes:{url:'chainMgmtJSP!preEditChainProductPrice'}">修改零售价</li>
						</s:if>						
					</ul>
				</li>
				<s:if test="#session.LOGIN_CHAIN_USER.containFunction('chainUserJSP!preMgmtUserFunction') || #session.LOGIN_CHAIN_USER.containFunction('chainUserJSP!switchToHeadq') || #session.LOGIN_CHAIN_USER.containFunction('chainDailyManualRptJSPAction!preCreateConf')">
			       <li data-options="iconCls:'icon-cog',state:'closed'">
					<span>系统管理</span>
					<ul>
						<s:if test="#session.LOGIN_CHAIN_USER.containFunction('chainUserJSP!preMgmtUserFunction')">
							<li data-options="iconCls:'icon-cog',attributes:{url:'chainUserJSP!preMgmtUserFunction'}">权限管理</li>
						</s:if>
						<s:if test="#session.LOGIN_CHAIN_USER.containFunction('chainMgmtJSP!preCreateConf')">	
	        		         <li data-options="iconCls:'icon-cog',attributes:{url:'chainMgmtJSP!preCreateConf'}">修改系统配置</li>
	        		    </s:if>
					</ul>
				   </li>
				</s:if>
		</ul>

	</div>  
    <div title="大家比一比" style="padding:10px;">  
        <ul id="treeMenu2" class="easyui-tree" lines="true">  
           <li data-options="iconCls:'icon-money_yen',state:'open',border:false">  
		            <span>销售比较</span>  
	        		<ul>
	        		    <li data-options="iconCls:'icon-money_yen',attributes:{url:'chainReportJSPAction!preSalesAnalysisReport'}">销售分析报表</li>
						
	        			<s:if test="#session.LOGIN_CHAIN_USER.containFunction('chainDailySalesAction!preGenChart')">
	        				<li data-options="iconCls:'icon-money_yen',attributes:{url:'chainDailySalesAction!preGenChart'}">日销售比较</li>
	        			</s:if>
	        			<s:if test="#session.LOGIN_CHAIN_USER.containFunction('chainDailySalesAction!preGenWeeklyHotBrand')">
	        				<li data-options="iconCls:'icon-money_yen',attributes:{url:'chainDailySalesAction!preGenWeeklyHotBrand'}">每周热销货品</li>
	        			</s:if>
	        			<s:if test="#session.LOGIN_CHAIN_USER.containFunction('chainDailySalesAction!preGenMonthlyHotBrand')">
	        				<li data-options="iconCls:'icon-money_yen',attributes:{url:'chainDailySalesAction!preGenMonthlyHotBrand'}">月/季度热销货品</li>
	        			</s:if>	        			
	        			
	        		</ul> 
	            </li>
	            <s:if test="#session.LOGIN_CHAIN_USER.containFunction('chainReportJSPAction!preChainAutoRptRepository')">
                <li data-options="iconCls:'icon-chart_bar',state:'open',border:false">  
		            <span>总部分析报表</span>  
	        		<ul>
	        		     <s:if test="#session.LOGIN_CHAIN_USER.containFunction('chainReportJSPAction!preChainAutoRptRepository')">
	        		        <li data-options="iconCls:'icon-chart_bar',attributes:{url:'chainReportJSPAction!preChainAutoRptRepository'}">总部分析报表库</li>       			
	        			</s:if>
	        			<s:if test="#session.LOGIN_CHAIN_USER.containFunction('chainReportJSPAction!preVIPSalesAnalysisRpt')">
	        		        <li data-options="iconCls:'icon-chart_bar',attributes:{url:'chainReportJSPAction!preVIPSalesAnalysisRpt'}">VIP销售占比报表</li>       			
	        			</s:if>
	        		</ul> 
	        	</li>
	        	</s:if>
		 </ul>

 
    </div>  
</div>
