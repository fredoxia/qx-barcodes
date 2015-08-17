<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script>

</script>
<div id="indexTab" class="easyui-tabs" fit="true" border="false">  
      <div title="首页">
		<table cellpadding='10px' cellspacing='10px'>
			<tr valign="top">
			   <td>
			    <div id="p" class="easyui-panel" title="消息栏"  
			 			style="width:450px;height:250px;padding:10px;background:#fafafa;"  
	        			data-options="collapsible:false">  
				        <table style="width: 100%" border="0">	 
							  <s:iterator value="uiBean.news" status="st" id="news" >	
							 	<tr class="InnerTableContent">
									<td><strong><s:property value="#news.title"/></strong><br/>
									    <s:property value="#news.content"/>
									</td>
							 	</tr>
							 	<tr height="10">
									<td><hr/></td>
								</tr>
							  </s:iterator>
						</table>
			    </div> 
			   </td>
			   <td>
			   		<div id="p2" class="easyui-panel" title="功能更新栏"  
			 			style="width:450px;height:250px;padding:10px;background:#fafafa;"  
	        			data-options="collapsible:false">  
				        <table style="width: 100%" border="0">
				        	<tr class="InnerTableContent">
								<td><font color=red><strong>开单货号查找快捷键</strong><img src='<%=request.getContextPath()%>/conf_files/web-image/new.gif'/><br/>
								    - 通过货号查找商品，可以通过如下快捷键操作选取货品：<p/>
								    -> 上下键 轮流选取当页货品<br/>
								    -> 左右键 翻页<br/>
								    -> 回车键 选中商品<br/>
								    -> Esc键 退出货号选取页<br/>
								    </font><p/>
								</td>
						 	</tr>
						 	<tr height="5">
								<td><hr/></td>
							</tr> 	
				        	<tr class="InnerTableContent">
								<td><strong>商品库存跟踪</strong><br/>
								    - 当发现你的电脑库存跟实际完全不同，让人摸不到头绪？<p/>
								    - 这个功能帮你查看货品库存增减情况，一目了然<p/>
								    -> 库存管理 -> 商品库存跟踪<br/>
								    -> 以及零售单,库存状况，销售统计，的条码链接上 <p/>
								</td>
						 	</tr>
						 	<tr height="5">
								<td><hr/></td>
							</tr> 				        
				        	<tr class="InnerTableContent">
								<td><font color=blue><strong>VIP快速开单</strong><br/>
								    - 当你查询到了客户VIP号之后<p/>
								    - 可以立刻选中当前VIP，然后点击"VIP快速开单",免除你复制vip卡号的繁琐<p/>
								    - VIP卡管理 -> 搜索VIP信息 -> 选中 -> VIP快速开单<p/>
								    </font><p/>
								</td>
						 	</tr>
						 	<tr height="5">
								<td><hr/></td>
							</tr> 
				        	<tr class="InnerTableContent">
								<td><strong>每周热销货品查询</strong><br/>
								    - 每周末，系统会自动根据当周所有连锁店销售情况对热销货品进行排名<p/>
								    - 你可以查看整体连锁店的热销情况和自己某个品牌或者款式的销售比较<p/>
								    - 左边菜单 -> 大家比一比 -> 每周热销货品<p/>
								</td>
						 	</tr>
						 	<tr height="5">
								<td><hr/></td>
							</tr> 
				        	<tr class="InnerTableContent">
								<td><strong>一键纠正库存/库存扎帐</strong><br/>
								    - 一键纠正库存 功能已经上线。免去你自己手动添加报溢单，报损单的麻烦，只需一键即可纠正盘点货品库存<p/>
								    - 库存扎帐。 帮你清理旧库存，让库存更准确<p/>
								    - 详细步骤请查看,千禧宝贝盘点使用手册V2.0<p/>
								</td>
						 	</tr>
						 	<tr height="5">
								<td><hr/></td>
							</tr> 
				        	<tr class="InnerTableContent">
								<td><strong>调货功能</strong><br/>
								    - 新的调货功能已经上线,可以调货到系统外的非连锁店以及使用新系统的连锁店,并同步更新连锁店库存<p/>
								    - 库存管理 -> 调货单 <p/>
								    - 调出 为非连锁店或者目前登录的连锁店账号,调入可以选择 同本连锁店相关联的连锁店<p/>
								</td>
						 	</tr>
						 	<tr height="5">
								<td><hr/></td>
							</tr> 
				        	<tr class="InnerTableContent">
								<td><strong>系统支持</strong><br/>
								    - 本系统完全支持IE,为了最好的使用体验，请使用IE8,点击下面链接下载<p/>
								    - <a href="http://www.microsoft.com/zh-cn/download/internet-explorer-8-details.aspx" target="_blank">下载</a><p/>
								    - * 下载时请注意你的操作系统，请选 Windows Internet Explorer 8 for Windows XP <p/>
								</td>
						 	</tr>
						 	<tr height="5">
								<td><hr/></td>
							</tr> 
						 	<tr class="InnerTableContent">
								<td><strong>零售单据功能</strong><br/>
								    - 用户可以通过 Home/End键,在VIP,销售,退货,赠品,收款, 输入框中切换<p/>
								    - 上 下 左 右键可以帮助在数量，折扣，折扣价，条码输入框中切换
								</td>
						 	</tr>
						 	<tr height="10">
								<td><hr/></td>
							</tr>

						</table>
			       </div>
			   </td>
			</tr>
			<tr valign="top">
			   <td>
			    <div id="p" class="easyui-panel" title="文档下载"  
			 			style="width:450px;height:280px;padding:10px;background:#fafafa;"  
	        			data-options="collapsible:false">  
				        <table style="width: 100%" border="0">	 
							 	<tr class="InnerTableContent">
									<td><a href="/docs/QXBaby-MIS-Docs/PanDianWenDang.doc" target="_blank">千禧宝贝盘点使用手册V2 (word下载格式)</a></td>
							 	</tr>
							 	<tr height="10">
									<td><hr/></td>
								</tr>
								<tr class="InnerTableContent">
									<td><a href="/docs/QXBaby-MIS-Docs/PanDianWenDang.html" target="_blank">千禧宝贝盘点使用手册V2 (html在线浏览)</a></td>
							 	</tr>
							 	<tr height="10">
									<td><hr/></td>
								</tr>
						</table>
			    </div> 
			   </td>
			   <td>
			    <div id="p" class="easyui-panel" title="连锁店周排名"  
			 			style="width:450px;height:280px;padding:10px;background:#fafafa;"  
	        			data-options="collapsible:false">  
				        <table style="width: 100%" border="0">	
				        		<tr class="InnerTableContent">
									<td colspan="4" height="25"><s:property value="uiBean.chainWMRank.startDate"/> 至 <s:property value="uiBean.chainWMRank.endDate"/></td>
							 	</tr> 
							 	<tr class="InnerTableContent">
									<th>排名</th>
									<th>连锁店</th>
									<th>销售量</th>
									<th>销售额</th>
							 	</tr>
							  <s:iterator value="uiBean.chainWMRank.items" status="st" id="rankItem" >	
							 	<tr class="InnerTableContent" align="center">
									<td><s:property value="#rankItem.rank"/></td>
									<td><s:property value="#rankItem.chainStore.chain_name"/></td>
									<td><s:property value="#rankItem.saleQ"/></td>
									<td><s:property value="#rankItem.saleAmt"/></td>
							 	</tr>
							  </s:iterator>
							 	<tr class="InnerTableContent">
									<th colspan="4" align="left">我的排名 (在线连锁店 <s:property value="uiBean.myRank.totalRank"/> 家) </th>
							 	</tr>
							 	<tr class="InnerTableContent" align="center">
									<td><s:property value="uiBean.myRank.rank"/></td>
									<td><s:property value="uiBean.myRank.chainStore.chain_name"/></td>
									<td><s:property value="uiBean.myRank.saleQ"/></td>
									<td><s:property value="uiBean.myRank.saleAmt"/></td>
							 	</tr>							  
						</table>
			    </div> 			   
			   
			   </td>
			</tr>
		</table>
	</div>
</div>
