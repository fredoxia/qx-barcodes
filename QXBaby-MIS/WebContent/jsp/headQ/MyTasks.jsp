<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.onlineMIS.ORM.entity.headQ.user.UserInfor" %>
<%@taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>我的任务</title>
<%@ include file="../common/Style.jsp"%>

<script>
var index_tabsMenu;
function reload() {
	parent.$.messager.progress({
		text : '数据处理中，请稍后....'
	});
	var index_tabs = $("#indexTab");
	var href = index_tabs.tabs('getSelected').panel('options').href;
	if (href) {/*说明tab是以href方式引入的目标页面*/
		var index = index_tabs.tabs('getTabIndex', index_tabs.tabs('getSelected'));
		index_tabs.tabs('getTab', index).panel('refresh');
	} else {/*说明tab是以content方式引入的目标页面*/
		var panel = index_tabs.tabs('getSelected').panel('panel');
		var frame = panel.find('iframe');
		try {
			if (frame.length > 0) {
				for ( var i = 0; i < frame.length; i++) {
					frame[i].contentWindow.document.write('');
					frame[i].contentWindow.close();
					frame[i].src = frame[i].src;
				}
				if (navigator.userAgent.indexOf("MSIE") > 0) {// IE特有回收内存方法
					try {
						CollectGarbage();
					} catch (e) {
					}
				}
			} else 
				parent.$.messager.progress('close'); 
		} catch (e) {
		}
	}
}

</script>
</head>
<body class="easyui-layout">
    <div data-options="region:'north',title:'North Title',split:false,border:false,noheader:true" style="height:60px;overflow:hidden;">
           <%@ include file="include/Header.jsp"%>
    </div>  
    <div data-options="region:'west',title:'功能导航',split:false,border:false" style="width:180px;overflow:hidden;">
           <%@ include file="layout/West.jsp"%>
    </div>  
    <div data-options="region:'center',title:'欢迎使用千禧宝贝信息系统',tools: [{  
        iconCls:'icon-reload',  
        handler:function(){reload();}  
    }] ">
		   <%@ include file="layout/Center.jsp"%>
    </div> 
</body>
</html>