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
		      <li data-options="iconCls:'icon-text_1',state:'open',border:false">
			  	    <span>我的条型码管理</span>
			  		<ul>
			  			<li data-options="iconCls:'icon-text_1',attributes:{url:'barcodeGenJSP!preMaintainBasic'}">条型码基础资料</li>
			  			<li data-options="iconCls:'icon-text_1',attributes:{url:'barcodeGenJSP!preGenBarcode'}">新建条型码</li>
						<li data-options="iconCls:'icon-text_1',attributes:{url:'barcodeGenJSP!preSearchBarcode'}">查询修改条型码</li>
			    	</ul>  
			    </li>
			    
		</ul>

	</div>  
</div>
