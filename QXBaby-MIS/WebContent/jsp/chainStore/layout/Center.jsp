<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script>
$(document).ready(function(){

	$('#dataGrid').datagrid({
		url : 'chainUserJSON!refreshOrderStatisticsInformation',
		fit : true,
		fitColumns : true,
		border : false,
		checkOnSelect : false,
		selectOnCheck : false,
		singleSelect:true,
		showFooter:false,
		rownumbers:false,
		nowrap : false,
		columns : [ [
					{field:'desc', width:50, title: "统计项目"},
					{field:'quantity', width:40, title:"单据数量"}
			     ]]
	});
	
	$('#indexTab').tabs({  
		           onSelect:function(title,content){   
		               			//alert(title + "," + content); 
		               			if (title.startWith('新建零售单')){
		               				try {
		               				  var selectedTab = $('#indexTab').tabs("getTab",content);
		               				  var _refresh_ifram = selectedTab.find('iframe')[0]; 
		               				  if (_refresh_ifram != null && _refresh_ifram.contentWindow != undefined)
		               				      _refresh_ifram.contentWindow.onSelected();
		               				} catch (e){
		               					//console.log("error");
		               				}
		               			}
	                  		}   
		           });  

});
function reloadStatis(){
	$('#dataGrid').datagrid('load',''); 
}

</script>
<div id="indexTab" class="easyui-tabs" fit="true" border="false"> 
      <div title="首页">
		<table cellpadding='10px' cellspacing='10px'>
			<tr valign="top">
			   <td>
			    <div id="p" class="easyui-panel" title="消息栏"  
			 			style="width:450px;height:320px;padding:10px;background:#fafafa;"  
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
			   		<div id="p2" class="easyui-panel" title="单据统计"  
			 			style="width:450px;height:320px;padding:10px;background:#fafafa;"  
	        			data-options="collapsible:false,tools: [{  
        					iconCls:'icon-reload',  
       						 handler:function(){reloadStatis();}  
   						 }] ">  
			    		<table id="dataGrid">			       
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
								<tr class="InnerTableContent">
									<td><a href="/docs/QXBaby-MIS-Docs/contact.xls" target="_blank">连锁店联系电话</a></td>
							 	</tr>
							 	<tr height="10">
									<td><hr/></td>
								</tr>								
						</table>
			    </div> 
			   </td>
			   <td>
			    <div id="p" class="easyui-panel" title="连锁店排名"  
			 			style="width:450px;height:280px;padding:5px;background:#fafafa;"  
	        			data-options="collapsible:false">  
				        <table style="width: 100%" border="0">	
				        		<tr class="InnerTableContent">
									<th>排名</th>
									<th>销售量</th>
									<th>销售额</th>
							 	</tr>
				        	    <tr class="InnerTableContent">
									<th colspan="3" align="left">我的周排名</th>
							 	</tr>
				        		<tr class="InnerTableContent">
									<td colspan="3"><s:property value="uiBean.chainWMRank.startDate"/> 至 <s:property value="uiBean.chainWMRank.endDate"/></td>
							 	</tr> 
							 	<tr class="InnerTableContent" align="center">
									<td><s:property value="uiBean.chainWMRank.rank"/></td>
									<td><s:property value="uiBean.chainWMRank.saleQ"/></td>
									<td><s:property value="uiBean.chainWMRank.saleAmt"/></td>
							 	</tr>							 	
							 	<tr class="InnerTableContent">
									<th colspan="3" align="left">我的日排名</th>
							 	</tr>
					 	
							  <s:iterator value="uiBean.myDailyRank" status="st" id="rankItem" >	
							  	 <tr class="InnerTableContent">
									<td colspan="3"><s:property value="#rankItem.startDate"/></td>
							 	</tr> 		
							 	<tr class="InnerTableContent" align="center">
									<td><s:property value="#rankItem.rank"/></td>
									<td><s:property value="#rankItem.saleQ"/></td>
									<td><s:property value="#rankItem.saleAmt"/></td>
							 	</tr>
							  </s:iterator>							  
						</table>
			    </div> 			   
			   
			   </td>
			</tr>
		</table>
	</div>
</div>
